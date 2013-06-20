package ptn008;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.URI;
import java.util.StringTokenizer;
import java.util.HashSet;

import org.apache.hadoop.filecache.DistributedCache;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.*;
import org.apache.hadoop.conf.Configuration;    
import org.apache.hadoop.mapreduce.Job;  
import org.apache.hadoop.mapreduce.Mapper;  
import org.apache.hadoop.mapreduce.Reducer;  
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;  
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;  
import org.apache.hadoop.mapreduce.lib.output.TextOutputFormat;


public class WordCountDC {
	private static HashSet<String> strset = new HashSet<String>();
	
    public static void UseDistributedCacheBySymbolicLink() throws Exception {
        FileReader reader = new FileReader("god.txt");
        BufferedReader br = new BufferedReader(reader);
        String s1 = null;
        while ((s1 = br.readLine()) != null){
            System.out.println(s1);
            strset.add(s1);
        }
        br.close();
        reader.close();
    }
    

    public static class Map extends Mapper<LongWritable, Text, Text, IntWritable> {

        public void setup(Context context) {
            System.out.println("Now, use the distributed cache and syslink");
            try {
                UseDistributedCacheBySymbolicLink();
            }
            catch (Exception e) {
                e.printStackTrace();
            }

        }

        private final static IntWritable one = new IntWritable(1);
        private Text word = new Text();

        public void map(LongWritable key, Text value, Context context) 
        		throws IOException, InterruptedException {
            String line = value.toString();
            
            //process rows only existing in the file at distribute cache
            if (strset.contains(line)){          
	            StringTokenizer tokenizer = new StringTokenizer(line);
	            while (tokenizer.hasMoreTokens()) {
	                word.set(tokenizer.nextToken());
	                context.write(word, one);
	            }
            }
        }
    }

    public static class Reduce extends Reducer<Text, IntWritable, Text, IntWritable>{
        public void reduce(Text key, Iterable<IntWritable> values, Context context) 
        		throws IOException, InterruptedException  
        {
            int sum = 0;
            for (IntWritable val : values) {
                sum += val.get();
            }
            context.write(key, new IntWritable(sum));
        }
    }

    public static void main(String[] args) throws Exception {
    	Configuration conf = new Configuration();

        Job job = new Job(conf);
        job.setJarByClass(WordCountDC.class);
        job.setJobName("WordCountDC");
        
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(IntWritable.class);

        job.setMapperClass(Map.class);
        job.setCombinerClass(Reduce.class);
        job.setReducerClass(Reduce.class);
 
        job.setInputFormatClass(TextInputFormat.class);
        job.setOutputFormatClass(TextOutputFormat.class);
        
        FileInputFormat.addInputPath(job, new Path(args[0]));
        FileOutputFormat.setOutputPath(job, new Path(args[1]));

        DistributedCache.createSymlink(conf);
        String path = "/feed/in/WordCountDC.java";
        Path filePath = new Path(path);
        String uriWithLink = filePath.toUri().toString() + "#" + "god.txt";
        DistributedCache.addCacheFile(new URI(uriWithLink), conf);

        job.waitForCompletion(true);
    }

} 
