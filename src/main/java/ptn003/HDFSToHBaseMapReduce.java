package ptn003;
/*
 * Export data from HDFS to HBase using map and reduce
 */
import java.io.IOException; 

import org.apache.hadoop.conf.Configuration; 
import org.apache.hadoop.hbase.HBaseConfiguration; 
import org.apache.hadoop.hbase.client.Put; 
import org.apache.hadoop.hbase.io.ImmutableBytesWritable; 
import org.apache.hadoop.hbase.mapreduce.TableMapReduceUtil; 
import org.apache.hadoop.hbase.util.Bytes; 
import org.apache.hadoop.io.LongWritable; 
import org.apache.hadoop.io.Text; 
import org.apache.hadoop.mapreduce.Job; 
import org.apache.hadoop.mapreduce.Mapper; 
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat; 
import org.apache.hadoop.util.GenericOptionsParser; 

import common.HBaseHelper;
 
public class HDFSToHBaseMapReduce { 
     
    public static class MapperClass extends Mapper<LongWritable,Text,ImmutableBytesWritable,Put>{   
        private Put put; 
        private byte[] family,qulifier1,qulifier2,qulifier3; 
        
        //Read configured parameters from map
        public void setup(Context context){ 
            family=Bytes.toBytes(context.getConfiguration().get("FAMILY")); 
            qulifier1=Bytes.toBytes(context.getConfiguration().get("QULIFIER1")); 
            qulifier2=Bytes.toBytes(context.getConfiguration().get("QULIFIER2")); 
            qulifier3=Bytes.toBytes(context.getConfiguration().get("QULIFIER3")); 
        } 
         
        public void map(LongWritable key,Text line,Context context)throws IOException,InterruptedException{   
            String[] values=line.toString().split(",");   
            if(values.length!=3){ // if there are not three args,then return   
                return ;   
            }   
            //  set the row value  
            int tempRow=Math.round(Float.parseFloat(key.toString())/(line.toString().length())); 
            byte [] row=Bytes.toBytes(tempRow); 
            byte [] qulifier1_value = Bytes.toBytes(values[0]);   
            byte [] qulifier2_value = Bytes.toBytes(values[1]);   
            byte [] qulifier3_value = Bytes.toBytes(values[2]);   
            put=new Put(row);   
            put.add(family,qulifier1,qulifier1_value);  //  user  
            context.write(new ImmutableBytesWritable(row),put);   
            put=new Put(row);   
            put.add(family,qulifier2,qulifier2_value);  //  item  
            context.write(new ImmutableBytesWritable(row),put);   
            put=new Put(row);   
            put.add(family,qulifier3,qulifier3_value);  //  item  
            context.write(new ImmutableBytesWritable(row),put);   
        }   
    } 
    
    public static void runMyJob(String[] args) { 
        Configuration conf = HBaseConfiguration.create();   
        try{ 
            String[] otherArgs = new GenericOptionsParser(conf, args).getRemainingArgs();   
            if(otherArgs.length != 6) {   
              System.out.println("Wrong number of arguments: " + otherArgs.length);   
              System.out.println("Usage: <input> <tablename> <family> <qulifier1> <qulifier2> <qulifier3>");   
              System.exit(-1);   
            } 
             
            String family=otherArgs[2];   
            String qulifier1=otherArgs[3]; 
            String qulifier2=otherArgs[4]; 
            String qulifier3=otherArgs[5]; 
            conf.set("FAMILY", family); 
            conf.set("QULIFIER1", qulifier1); 
            conf.set("QULIFIER2", qulifier2); 
            conf.set("QULIFIER3", qulifier3); 
             
            Job job=new Job(conf,"import data to hbase");   
            job.setJarByClass(HDFSToHBaseMapReduce.class);   
            job.setMapperClass(MapperClass.class);   
            job.setMapOutputKeyClass(ImmutableBytesWritable.class);   
            job.setMapOutputValueClass(Put.class);   
            TableMapReduceUtil.initTableReducerJob(otherArgs[1], null, job);   
            job.setNumReduceTasks(0);   
            FileInputFormat.setInputPaths(job, otherArgs[0]);   
            System.exit(job.waitForCompletion(true) ? 0 : 1); 
         }catch(Exception e){ 
           e.printStackTrace(); 
         } 
    } 
    
    public static void main(String[] args) throws IOException {         
        //check if there exist the table then delete the data and alter the family 
    	
        if(args.length<3){ 
            System.out.println("Wrong number of arguments "+args.length); 
            System.out.println("Usage: <input> <tablename> <family> <qulifier1> <qulifier2> <qulifier3>");   
            System.exit(-1);   
        } 
        String tableName=args[1]; 
        String family=args[2]; 
        
        HBaseHelper helper = HBaseHelper.getHelper(HBaseConfiguration.create());
        helper.dropTable("testtable");
        helper.createTable("testtable", "colfam1");
        if(helper.existsTable(tableName)){
        	System.out.println("table "+tableName+" exists ,delete it  ..."); 
        	helper.disableTable(tableName);
        	helper.dropTable(tableName);
        }
        helper.createTable(tableName,family);
        
        // import data to HBase core
        HDFSToHBaseMapReduce.runMyJob(args); 
    } 
    
    
     
    
 
}  