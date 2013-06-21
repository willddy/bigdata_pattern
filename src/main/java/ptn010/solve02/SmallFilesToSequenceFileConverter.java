package ptn010.solve02;
/**
 * SmallFilesToSequenceFileConverter 
 * A MapReduce program for packaging a collection of small files as a single SequenceFile
 */
import java.io.IOException;

import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.BytesWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.InputSplit;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.FileSplit;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.mapreduce.lib.output.SequenceFileOutputFormat;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;

//SmallFilesToSequenceFileConverter
public class SmallFilesToSequenceFileConverter extends Configured implements Tool {
  
  static class SequenceFileMapper
      extends Mapper<NullWritable, BytesWritable, Text, BytesWritable> {
    
    private Text filenameKey;
    
    @Override
    //fetch file name as map key
    protected void setup(Context context) throws IOException, InterruptedException {
      InputSplit split = context.getInputSplit();
      Path path = ((FileSplit) split).getPath();
      filenameKey = new Text(path.toString());
    }
    
    @Override
    protected void map(NullWritable key, BytesWritable value, Context context)
        throws IOException, InterruptedException {
      context.write(filenameKey, value);
    }
    
  }

  @Override
  public int run(String[] args) throws Exception {
	  if (args.length != 2) {
	      System.err.printf("Usage: %s [generic options] <input> <output>\n",
	          getClass().getSimpleName());
	      ToolRunner.printGenericCommandUsage(System.err);
	      return -1;
	  }
	  
	  Job job = new Job(getConf());
	  job.setJarByClass(getClass());
	  FileInputFormat.addInputPath(job, new Path(args[0]));
	  FileOutputFormat.setOutputPath(job, new Path(args[1]));
	       
	  job.setInputFormatClass(WholeFileInputFormat.class);
	  job.setOutputFormatClass(SequenceFileOutputFormat.class);
    
	  job.setOutputKeyClass(Text.class);
	  job.setOutputValueClass(BytesWritable.class);

	  job.setMapperClass(SequenceFileMapper.class);

	  return job.waitForCompletion(true) ? 0 : 1;
  }
  
  public static void main(String[] args) throws Exception {
	  int exitCode = ToolRunner.run(new SmallFilesToSequenceFileConverter(), args);
	  System.exit(exitCode);
  }
}
