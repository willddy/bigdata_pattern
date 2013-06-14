package com.packt.ch5.advjoin.redis;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.mapreduce.lib.output.TextOutputFormat;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;
import redis.clients.jedis.Jedis;

public class MapSideJoinRedis extends Configured implements Tool {
    
    private Jedis jedis = null;
    
    private void loadRedis(String ipCountryTable) throws IOException {
        
        FileReader freader = new FileReader(ipCountryTable);
        BufferedReader breader = new BufferedReader(freader);
        jedis = new Jedis("localhost");
        jedis.select(0);
        jedis.flushDB();
        String line = breader.readLine();
        while(line != null) {
            String[] tokens = line.split("\t");
            String ip = tokens[0];
            String country = tokens[1];
            jedis.set(ip, country);
            line = breader.readLine();
        }
        System.err.println("db size = " + jedis.dbSize());
    }
    
    public int run(String[] args) throws Exception {
        
        Path inputPath = new Path(args[0]);
        String ipCountryTable = args[1];
        Path outputPath = new Path(args[2]);
        
        loadRedis(ipCountryTable);
        
        Configuration conf = getConf();
        Job weblogJob = new Job(conf);
        weblogJob.setJobName("MapSideJoinRedis");
        weblogJob.setNumReduceTasks(0);
        weblogJob.setJarByClass(getClass());
        weblogJob.setMapperClass(WeblogMapper.class);        
        weblogJob.setMapOutputKeyClass(Text.class);
        weblogJob.setMapOutputValueClass(Text.class);
        weblogJob.setOutputKeyClass(Text.class);
        weblogJob.setOutputValueClass(Text.class);
        weblogJob.setInputFormatClass(TextInputFormat.class);
        weblogJob.setOutputFormatClass(TextOutputFormat.class);
        FileInputFormat.setInputPaths(weblogJob, inputPath);
        FileOutputFormat.setOutputPath(weblogJob, outputPath);
        
       
        if(weblogJob.waitForCompletion(true)) {
            return 0;
        }
        return 1;
    }
    
    public static void main( String[] args ) throws Exception {
        int returnCode = ToolRunner.run(new MapSideJoinRedis(), args);
        System.exit(returnCode);
    }
}
