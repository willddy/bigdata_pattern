package ptn002;

/*
 * Export data from HDFS to MySQL
 */
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.*;
import org.apache.hadoop.mapreduce.*;
import org.apache.hadoop.mapreduce.lib.db.DBConfiguration;
import org.apache.hadoop.mapreduce.lib.db.DBOutputFormat;
import org.apache.hadoop.mapreduce.lib.db.DBWritable;
import org.apache.hadoop.mapreduce.lib.input.*;

import java.io.*;
import java.sql.*;

public class HDFSToDBMapReduce {  
	  
	   public static class StudentinfoRecord implements Writable,  DBWritable {  
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
	   
	    
	   public static class MyReducer extends 
	   Reducer<LongWritable, Text, StudentinfoRecord, Text> {  
	     public void reduce(LongWritable key, Iterable<Text> values, Context context) 
	          throws IOException,InterruptedException {  
	        String[] splits = values.toString().split("/t");  
	        StudentinfoRecord r = new StudentinfoRecord();  
	        r.id = Integer.parseInt(splits[0]);  
	        r.name = splits[1];  
	        context.write(r, new Text(r.name));  
	     }  
	   }  
	  
	   public static void main(String[] args) throws IOException, Exception {  
		 Configuration conf = new Configuration();
		 Job job = new Job(conf);
		 job.setJarByClass(HDFSToDBMapReduce.class);
	     job.setInputFormatClass(TextInputFormat.class);  
	     job.setOutputFormatClass(DBOutputFormat.class);  
	     
	     Path inputPath = new Path("/hua/hua.bcp");
	     FileInputFormat.setInputPaths(job, inputPath);  
	     DBConfiguration.configureDB(conf, "com.mysql.jdbc.Driver",  
	          "jdbc:mysql://192.168.3.244:3306/hadoop", "hua", "hadoop");  
	     DBOutputFormat.setOutput(job, "studentinfo", "id", "name");  
	  
	     job.setMapperClass(Mapper.class);  
	     job.setReducerClass(MyReducer.class);  
	  
	     job.waitForCompletion(true);  
	   }  
	  
	}