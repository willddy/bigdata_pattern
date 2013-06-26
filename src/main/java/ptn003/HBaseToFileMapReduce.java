package ptn003;
/**
 * Import data from HBase to Files using map and reduce
 */
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.client.*;
import org.apache.hadoop.hbase.mapreduce.*;
import org.apache.hadoop.hbase.util.Bytes;
import org.apache.hadoop.io.*;
import org.apache.hadoop.mapreduce.*;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

import common.HBaseHelper;

import java.io.IOException;

public class HBaseToFileMapReduce
    extends Mapper<LongWritable, Text, LongWritable, Put> {

  @Override
  protected void map(LongWritable key, Text value,
                     Context context)
      throws IOException, InterruptedException {

    Put put = new Put(Bytes.toBytes(key.toString()));  
    put.add(Bytes.toBytes("colfam1"), Bytes.toBytes("qual1"), Bytes.toBytes("val1")); 
    context.write(key, put);
  }

  public static void main(String[] args) throws Exception {
    Configuration conf = new Configuration();

    HBaseHelper helper = HBaseHelper.getHelper(HBaseConfiguration.create());
    helper.dropTable("testtable");
    helper.createTable("testtable", "colfam1");

    Job job = new Job(conf);

    job.setJarByClass(HBaseToFileMapReduce.class);

    TableMapReduceUtil.initTableReducerJob("testtable",IdentityTableReducer.class,job);

    job.setMapperClass(HBaseToFileMapReduce.class);

    job.setMapOutputValueClass(Put.class);

    Path inputPath = new Path(args[0]);
    Path outputPath = new Path(args[1]);

    FileInputFormat.setInputPaths(job, inputPath);
    FileOutputFormat.setOutputPath(job, outputPath);

    outputPath.getFileSystem(conf).delete(outputPath, true);

    job.waitForCompletion(true);
  }
}