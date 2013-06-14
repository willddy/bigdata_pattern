package com.packt.hadoop.hdfs.ch2.avro;


import org.apache.avro.Schema;
import org.apache.avro.mapred.AvroJob;
import org.apache.avro.reflect.ReflectData;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapred.FileInputFormat;
import org.apache.hadoop.mapred.FileOutputFormat;
import org.apache.hadoop.mapred.JobClient;
import org.apache.hadoop.mapred.JobConf;
import org.apache.hadoop.mapred.RunningJob;
import org.apache.hadoop.mapred.TextOutputFormat;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;

public class AvroReader extends Configured implements Tool {
    
        public int run(String[] args) throws Exception {

        if (args.length < 2) {
            System.err.println("AvroReader [hdfs input path] [hdfs output dir]");
            return 1;
        }
        Path inputPath = new Path(args[0]);
        Path outputPath = new Path(args[1]);

        Schema schema = ReflectData.get().getSchema(WeblogRecord.class);
        
        Configuration conf = getConf();
        JobConf weblogJob = new JobConf(conf, getClass());
        weblogJob.setJobName("Avro Reader");
        weblogJob.setNumReduceTasks(0);
        AvroJob.setInputSchema(weblogJob, schema);
        weblogJob.setMapperClass(WeblogMapperAvro.class);
        AvroJob.setReflect(weblogJob);
        weblogJob.setOutputKeyClass(Text.class);
        weblogJob.setOutputValueClass(NullWritable.class);
        weblogJob.setOutputFormat(TextOutputFormat.class);
        FileInputFormat.setInputPaths(weblogJob, inputPath);
        FileOutputFormat.setOutputPath(weblogJob, outputPath);
        RunningJob job = JobClient.runJob(weblogJob);
        if(job.isSuccessful()) {
            return 0;
        }
        return 1;
    }

    public static void main(String[] args) throws Exception {
        int returnCode = ToolRunner.run(new AvroReader(), args);
        System.exit(returnCode);
    }
}
