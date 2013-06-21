package ptn009;
/**
 * Total sorting without using single reducer
 */

import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.*;
import org.apache.hadoop.mapred.*;
import org.apache.hadoop.mapred.lib.*;

public final class TotalSortMapReduce {
  public static void main(String... args) throws Exception {
    runSortJob(args);
  }

  public static void runSortJob(String ... args)
      throws Exception {

    int numReducers = 2;
    Path input = new Path(args[0]);
    Path partitionFile = new Path(args[1]);
    Path output = new Path(args[2]);

    InputSampler.Sampler<Text, Text> sampler = 
    		new InputSampler.RandomSampler<Text,Text> (0.1, 10000, 10);

    JobConf job = new JobConf();
    job.setJarByClass(TotalSortMapReduce.class);
    job.setNumReduceTasks(numReducers);

    job.setInputFormat(KeyValueTextInputFormat.class);
    job.setOutputFormat(TextOutputFormat.class);
    job.setPartitionerClass(TotalOrderPartitioner.class);

    job.setMapOutputKeyClass(Text.class);
    job.setMapOutputValueClass(Text.class);

    TotalOrderPartitioner.setPartitionFile(job, partitionFile); //not migrate to New API yet
    InputSampler.writePartitionFile(job, sampler);

    FileInputFormat.setInputPaths(job, input);
    FileOutputFormat.setOutputPath(job, output);
    
    output.getFileSystem(job).delete(output, true);

    JobClient.runJob(job);
  }
}
