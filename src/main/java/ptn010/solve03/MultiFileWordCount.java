import java.io.IOException;
import java.util.StringTokenizer;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.mapreduce.lib.reduce.IntSumReducer;
import org.apache.hadoop.util.GenericOptionsParser;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;

@SuppressWarnings("deprecation")
public class MultiFileWordCount extends Configured implements Tool {


  public static class MapClass extends 
      Mapper<MultiFileInputWritableComparable, Text, Text, IntWritable> {
    private final static IntWritable one = new IntWritable(1);
    private Text word = new Text();
    
    public void map(MultiFileInputWritableComparable key, Text value, Context context)
        throws IOException, InterruptedException {
      
      String line = value.toString();
      StringTokenizer itr = new StringTokenizer(line);
      while (itr.hasMoreTokens()) {
        word.set(itr.nextToken());
        context.write(word, one);
      }
    }
  }
  
  private void printUsage() {
    System.out.println("Usage : multifilewc <input_dir> <input_dir> <output>" );
  }

  public int run(String[] args) throws Exception {


	Configuration conf = new Configuration();
	String[] otherArgs = new GenericOptionsParser(conf, args).getRemainingArgs();
    if(otherArgs.length < 3) { //修改为3，前2个参数为输入文件，后1个为输出目录
      printUsage();
      return 2;
    }

    Job job = new Job(getConf());
    job.setJobName("MultiFileWordCount");
    job.setJarByClass(MultiFileWordCount.class);

    //set the InputFormat of the job to our InputFormat
    job.setInputFormatClass(MyMultiFileInputFormat.class);
    
    // the keys are words (strings)
    job.setOutputKeyClass(Text.class);
    // the values are counts (ints)
    job.setOutputValueClass(IntWritable.class);

    //use the defined mapper
    job.setMapperClass(MapClass.class);
    //use the WordCount Reducer
    job.setCombinerClass(IntSumReducer.class);
    job.setReducerClass(IntSumReducer.class);

    FileInputFormat.addInputPaths(job, otherArgs[0]);//修改为3，前2个参数为输入文件，后1个为输出目录
    FileInputFormat.addInputPaths(job, otherArgs[1]);
    FileOutputFormat.setOutputPath(job, new Path(otherArgs[2]));

    return job.waitForCompletion(true) ? 0 : 1;
  }

  public static void main(String[] args) throws Exception {
    int ret = ToolRunner.run(new MultiFileWordCount(), args);
    System.exit(ret);
  }

}