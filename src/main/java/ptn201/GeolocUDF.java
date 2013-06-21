package ptn201;
/**
 * Hive Genetic UDF. which can support complex type and are more efficient as
 * they do not require reflection to call the evaluate method as opposed to simple UDF
 * This is a geolocation lookup UDF
 */
import com.maxmind.geoip.LookupService;
import org.apache.hadoop.hive.ql.exec.Description;
import org.apache.hadoop.hive.ql.exec.UDFArgumentException;
import org.apache.hadoop.hive.ql.exec.UDFArgumentLengthException;
import org.apache.hadoop.hive.ql.metadata.HiveException;
import org.apache.hadoop.hive.ql.udf.generic.GenericUDF;
import org.apache.hadoop.hive.serde2.objectinspector.ObjectInspector;
import org.apache.hadoop.hive.serde2.objectinspector.ObjectInspectorConverters;
import org.apache.hadoop.hive.serde2.objectinspector.PrimitiveObjectInspector;
import org.apache.hadoop.hive.serde2.objectinspector.primitive.PrimitiveObjectInspectorFactory;

import java.io.IOException;
import java.net.URL;

@Description(
    name = "country",
    value = "_FUNC_(ip, geolocfile) - Returns the geolocated country code " +
    " for the IP"
)
//Above annotation can be show using "DESCRIBE FUNCTION <function_name>" in hive
public class GeolocUDF extends GenericUDF {
  private LookupService geoloc;
  private ObjectInspectorConverters.Converter[] converters;

  @Override
  public ObjectInspector initialize(ObjectInspector[] arguments) throws UDFArgumentException {
    if (arguments.length != 2) {
      throw new UDFArgumentLengthException(
          "The function COUNTRY(ip, geolocfile) takes exactly 2 arguments.");
    }
    /*
	 * create covert used in evaluate method to convert all the arguments from their
	 * native type into java String
	 */
    converters = new ObjectInspectorConverters.Converter[arguments.length];
    for (int i = 0; i < arguments.length; i++) {
    	
      converters[i] = ObjectInspectorConverters.getConverter(arguments[i],
          PrimitiveObjectInspectorFactory.javaStringObjectInspector);
    }

    return PrimitiveObjectInspectorFactory
        .getPrimitiveJavaObjectInspector(PrimitiveObjectInspector.PrimitiveCategory.STRING);
  }

  @Override
  public Object evaluate(GenericUDF.DeferredObject[] arguments) throws HiveException {
    assert (arguments.length == 2);

    if (arguments[0].get() == null || arguments[1].get() == null) {
      return null;
    }

    String ip = (String) converters[0].convert(arguments[0].get());
    String filename = (String) converters[1].convert(arguments[1].get());

    return lookup(ip, filename);
  }

  protected String lookup(String ip, String filename) throws HiveException {
    try {
      if (geoloc == null) {
    	//load geolocation data file from the distribute cache
        URL u = getClass().getClassLoader().getResource(filename);
        if (u == null) {
          throw new HiveException("Couldn't find geolocation file '" + filename + "'");
        }
        geoloc =
            new LookupService(u.getFile(), LookupService.GEOIP_MEMORY_CACHE);
      }
      //perform the geolocation and extract the country code
      String countryCode = geoloc.getCountry(ip).getCode();

      if ("--".equals(countryCode)) {
        return null;
      }

      return countryCode;
    } catch (IOException e) {
      throw new HiveException("Caught IO exception", e);
    }
  }

  @Override
  public String getDisplayString(String[] children) {
    assert (children.length == 2);
    return "country(" + children[0] + ", " + children[1] + ")";
  }
}
