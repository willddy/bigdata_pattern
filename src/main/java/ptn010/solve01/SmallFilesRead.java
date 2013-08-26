package ptn010.solve01;
/**
 * Using Avro to store multiple small files
 * read Avro files from HDFS for verification
 */
import org.apache.avro.file.DataFileStream;
import org.apache.avro.generic.*;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.*;
import org.apache.hadoop.io.IOUtils;

import java.io.*;
import java.nio.ByteBuffer;

public class SmallFilesRead {

  private static final String FIELD_FILENAME = "filename";
  private static final String FIELD_CONTENTS = "contents";

  public static void readFromAvro(InputStream is) throws IOException {
    /*
     * create reader without schema since Avro encodes that in the Avro file
     */
	  DataFileStream<Object> reader =               
        new DataFileStream<Object>(is, new GenericDatumReader<Object>());
    for (Object o : reader) {                         //loop every record in Avro file
      GenericRecord r = (GenericRecord) o;            //cast each record to generic record
      System.out.println(                             //retrieve filename and content from records
          r.get(FIELD_FILENAME) + ": " +
          DigestUtils.md5Hex(((ByteBuffer) r.get(FIELD_CONTENTS)).array()));
    }
    IOUtils.cleanup(null, is);
    IOUtils.cleanup(null, reader);
  }

  public static void main(String... args) throws Exception {
    Configuration config = new Configuration();
    FileSystem hdfs = FileSystem.get(config);

    Path destFile = new Path(args[0]);

    InputStream is = hdfs.open(destFile);
    readFromAvro(is);
  }
}
