package ptn006;

import org.apache.commons.io.FileUtils;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.SequenceFile;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.io.compress.DefaultCodec;

import java.io.File;
import java.io.IOException;

public class SequenceFileStockWriter {

  public static void main(String... args) throws IOException {
    write(new File(args[0]), new Path(args[1]));
  }

  public static void write(File inputFile, Path outputPath)
      throws IOException {
    Configuration conf = new Configuration();
    FileSystem fs = FileSystem.get(conf);

    SequenceFile.Writer writer =    //sequence file writer with block level compression
        SequenceFile.createWriter(fs, conf, outputPath, Text.class,
            StockPriceWritable.class,
            SequenceFile.CompressionType.BLOCK,
            new DefaultCodec());
    try {
      Text key = new Text();

      for (String line : FileUtils.readLines(inputFile)) {   
        StockPriceWritable stock = StockPriceWritable.fromLine(line);
        key.set(stock.getSymbol());
        writer.append(key, stock);        //append a record to the sequence file
      }
    } finally {
      writer.close();
    }
  }
}
