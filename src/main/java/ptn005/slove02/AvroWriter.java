package com.packt.hadoop.hdfs.ch2.avro;

import org.apache.avro.Schema;
import org.apache.avro.mapred.AvroJob;
import org.apache.avro.mapred.AvroWrapper;
import org.apache.avro.reflect.ReflectData;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.mapred.FileInputFormat;
import org.apache.hadoop.mapred.FileOutputFormat;
import org.apache.hadoop.mapred.JobClient;
import org.apache.hadoop.mapred.JobConf;
import org.apache.hadoop.mapred.RunningJob;
import org.apache.hadoop.mapred.TextInputFormat;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;

public class AvroWriter extends Configured implements Tool {

    public int run(String[] args) throws Exception {

        if (args.length < 2) {
            System.err.println("AvroWriter [hdfs input path] [hdfs output dir]");
            return 1;
        }
        //instiantiate input and output paths
        Path inputPath = new Path(args[0]);
        Path outputPath = new Path(args[1]);

        //get schema
        Schema schema = ReflectData.get().getSchema(WeblogRecord.class);

        //get configuration from classpath
        Configuration conf = getConf();
        JobConf weblogJob = new JobConf(conf, getClass());
        weblogJob.setJobName("Avro Writer");
        weblogJob.setNumReduceTasks(0);
        weblogJob.setMapperClass(WeblogMapper.class);
        weblogJob.setMapOutputKeyClass(AvroWrapper.class);
        weblogJob.setMapOutputValueClass(NullWritable.class);
        weblogJob.setInputFormat(TextInputFormat.class);
        AvroJob.setOutputSchema(weblogJob, schema);
        FileInputFormat.setInputPaths(weblogJob, inputPath);
        FileOutputFormat.setOutputPath(weblogJob, outputPath);
        
        RunningJob job = JobClient.runJob(weblogJob);
        if(job.isSuccessful()) {
            return 0;
        }
        return 1;
    }

    public static void main(String[] args) throws Exception {
        int returnCode = ToolRunner.run(new AvroWriter(), args);
        System.exit(returnCode);
    }
}
