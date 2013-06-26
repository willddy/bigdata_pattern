package ptn002;
/**
 * Import data from MySQL to files
 * Create table in MySQL
 * DROP TABLE IF EXISTS `hadoop`.`studentinfo`; 
 * CREATE TABLE studentinfo (id INTEGER NOT NULL PRIMARY KEY, name VARCHAR(32) NOT NULL);
 */
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.filecache.DistributedCache;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.*;
import org.apache.hadoop.mapreduce.*;
import org.apache.hadoop.mapreduce.lib.db.DBConfiguration;
import org.apache.hadoop.mapreduce.lib.db.DBInputFormat;
import org.apache.hadoop.mapreduce.lib.db.DBWritable;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

import java.io.*;
import java.sql.*;

public class DBToFileMapReduce {  
  
   public static class StudentinfoRecord implements Writable, DBWritable {  
     int id;  
     String name;  
     public StudentinfoRecord() {  
  
     }  
     public void readFields(DataInput in) throws IOException {  
        this.id = in.readInt();  
        this.name = Text.readString(in);  
     }  
     public void write(DataOutput out) throws IOException {  
        out.writeInt(this.id);  
        Text.writeString(out, this.name);  
     }  
     public void readFields(ResultSet result) throws SQLException {  
        this.id = result.getInt(1);  
        this.name = result.getString(2);  
     }  
     public void write(PreparedStatement stmt) throws SQLException {  
        stmt.setInt(1, this.id);  
        stmt.setString(2, this.name);  
     }  
     public String toString() {  
        return new String(this.id + " " + this.name);  
     }  
   }  
   public class DBInputMapper extends 
   Mapper <LongWritable, StudentinfoRecord, LongWritable, Text> {  
     public void map(LongWritable key, StudentinfoRecord value, Context context)  
          throws IOException,InterruptedException {  
        context.write(new LongWritable(value.id), new Text(value.toString()));  
     }  
   }  
   public static void main(String[] args) throws IOException, Exception {  
	   Configuration conf = new Configuration();
	   Job job = new Job(conf);
	   job.setJarByClass(DBToFileMapReduce.class);  
	   DistributedCache.addFileToClassPath(new Path(  
          "/lib/mysql-connector-java-5.1.0-bin.jar"), conf);  
       
	   job.setMapperClass(DBInputMapper.class);  
	   job.setReducerClass(Reducer.class); 
  
	   job.setMapOutputKeyClass(LongWritable.class);  
	   job.setMapOutputValueClass(Text.class);  
	   job.setOutputKeyClass(LongWritable.class);  
	   job.setOutputValueClass(Text.class);  
       
	   job.setInputFormatClass(DBInputFormat.class); 
	   
	   Path outputPath = new Path("/hua01");
	   FileOutputFormat.setOutputPath(job, outputPath);  
	   outputPath.getFileSystem(conf).delete(outputPath, true);
	   
	   DBConfiguration.configureDB(conf, "com.mysql.jdbc.Driver",  
          "jdbc:mysql://192.168.3.244:3306/hadoop", "hua", "hadoop");  
	   String[] fields = { "id", "name" };  
	   DBInputFormat.setInput(job, StudentinfoRecord.class, "studentinfo", null, "id", fields);   

	   job.waitForCompletion(true); 
   }  
} 