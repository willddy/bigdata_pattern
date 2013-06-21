package ptn008;
/**
 * Without using symbolic link, it is needed to download the distributed cached files to local
 * for processing.
 */
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.filecache.DistributedCache;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.mapreduce.lib.output.TextOutputFormat;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.URI;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Pattern;

public class LinesWithMatchingWordsJob extends Configured implements Tool {

    public static final String NAME = "linemarker";

    public int run(String[] args) throws Exception {
    	Configuration conf = new Configuration();
        Job job = new Job(conf, "Line Marker");
        job.setJarByClass(getClass());
        job.setInputFormatClass(TextInputFormat.class);
        job.setOutputFormatClass(TextOutputFormat.class);
        job.setMapperClass(LineMarkerMapper.class);
        job.setNumReduceTasks(0);
        job.setOutputKeyClass(LongWritable.class);
        job.setOutputValueClass(Text.class);
        
        DistributedCache.addCacheFile(new Path("/cache_files/news_keywords.txt").toUri(), conf);

        FileInputFormat.addInputPath(job, new Path(args[0]));
        FileOutputFormat.setOutputPath(job, new Path(args[1]));

        return job.waitForCompletion(true) ? 1: 0;
    }
    
    public static void main(String[] args) throws Exception {
         if(args.length != 2) {
             System.err.println("Usage: linemarker <input> <output>");
             System.exit(1);
         }
        ToolRunner.run(new LinesWithMatchingWordsJob(), args);
    }
   
    public static class LineMarkerMapper extends Mapper<LongWritable, Text, LongWritable, Text> {

        private Pattern space_pattern = Pattern.compile("[ ]");
        private Set<String> keywords = new HashSet<String>();

        @Override
        protected void setup(Context context) throws IOException, InterruptedException {
            URI[] uris = DistributedCache.getCacheFiles(context.getConfiguration());
            FileSystem fs = FileSystem.get(context.getConfiguration());
            if(uris == null || uris.length == 0) {
               throw new IOException("Error reading file from distributed cache. No URIs found.");
            }
            String localPath = "./keywords.txt";
            fs.copyToLocalFile(new Path(uris[0]), new Path(localPath));
            BufferedReader reader = new BufferedReader(new FileReader(localPath));
            String word = null;
            while((word = reader.readLine()) != null) {
                 keywords.add(word);
            }
            reader.close();
        }

        @Override
        protected void map(LongWritable key, Text value,
                           Context context) throws IOException, InterruptedException {
            String[] tokens = space_pattern.split(value.toString());
            for(String token : tokens) {
                if(keywords.contains(token)) {
                    context.write(key, new Text(token));
                }
            }

        }

    }
}
