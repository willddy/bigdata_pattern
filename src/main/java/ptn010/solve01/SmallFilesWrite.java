package ptn010.solve01;
/**
 * Using Avro to store multiple small files as record
 * Reads a directory containing small files and produce a single Avro files in HDFS
 */
import org.apache.avro.Schema;
import org.apache.avro.file.*;
import org.apache.avro.generic.*;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.io.FileUtils;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.*;
import org.apache.hadoop.io.IOUtils;

import java.io.*;
import java.nio.ByteBuffer;

public class SmallFilesWrite {

  public static final String FIELD_FILENAME = "filename";
  public static final String FIELD_CONTENTS = "contents";
  /*
   * Avro use JSON to define the data structure per record
   */
  private static final String SCHEMA_JSON = 
          "{\"type\": \"record\", \"name\": \"SmallFilesTest\", "
          + "\"fields\": ["
          + "{\"name\":\"" + FIELD_FILENAME
          + "\", \"type\":\"string\"},"
          + "{\"name\":\"" + FIELD_CONTENTS
          + "\", \"type\":\"bytes\"}]}";
  @SuppressWarnings("deprecation")
public static final Schema SCHEMA = Schema.parse(SCHEMA_JSON);

  public static void writeToAvro(File srcPath, OutputStream outputStream)
          throws IOException {
    @SuppressWarnings("resource")
	DataFileWriter<Object> writer =
            new DataFileWriter<Object>(
                new GenericDatumWriter<Object>()).setSyncInterval(100); //create a Avro writer
    writer.setCodec(CodecFactory.snappyCodec());    
    writer.create(SCHEMA, outputStream);  //associate the schema and output stream with writer
    for (Object obj : FileUtils.listFiles(srcPath, null, false)) {
      File file = (File) obj;
      String filename = file.getAbsolutePath();
      byte content[] = FileUtils.readFileToByteArray(file);
      GenericRecord record = new GenericData.Record(SCHEMA);  //a Avro generic wrapper around a rec.
      record.put(FIELD_FILENAME, filename);                   
      record.put(FIELD_CONTENTS, ByteBuffer.wrap(content));   
      writer.append(record); 
      /*
       * add MD5 ease of visual compare
       */
      System.out.println(file.getAbsolutePath() + ": " + DigestUtils.md5Hex(content));
    }

    IOUtils.cleanup(null, writer);
    IOUtils.cleanup(null, outputStream);
  }

  public static void main(String... args) throws Exception {
    Configuration config = new Configuration();
    FileSystem hdfs = FileSystem.get(config);

    File sourceDir = new File(args[0]);
    Path destFile = new Path(args[1]);

    OutputStream os = hdfs.create(destFile);
    writeToAvro(sourceDir, os);
  }
}
