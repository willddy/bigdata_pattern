package ptn006;
/*
 * Process sequence file with default identity mapper and reducer
 */
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.SequenceFile;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.io.compress.DefaultCodec;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.SequenceFileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.mapreduce.lib.output.SequenceFileOutputFormat;

public class SequenceFileStockMapReduce {
  public static void main(String... args) throws Exception {
    runJob(args[0], args[1]);
  }

  public static void runJob(String input,String output) throws Exception {
    Configuration conf = new Configuration();
    Job job = new Job(conf);
    job.setJarByClass(conf.getClass());
    job.setOutputKeyClass(Text.class);
    job.setOutputValueClass(StockPriceWritable.class);
    job.setInputFormatClass(SequenceFileInputFormat.class); 
    job.setOutputFormatClass(SequenceFileOutputFormat.class);  
    SequenceFileOutputFormat.setCompressOutput(job, true);  //compress output
    //set block level compression. You can also set to record level
    SequenceFileOutputFormat.setOutputCompressionType(job,SequenceFile.CompressionType.BLOCK);
    //default compression - DEFLATE
    SequenceFileOutputFormat.setOutputCompressorClass(job,DefaultCodec.class);

    FileInputFormat.setInputPaths(job, new Path(input));
    Path outPath = new Path(output);
    FileOutputFormat.setOutputPath(job, outPath);
    outPath.getFileSystem(conf).delete(outPath, true);

    job.waitForCompletion(true);
  }
}
