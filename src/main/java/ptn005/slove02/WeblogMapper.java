package com.packt.hadoop.hdfs.ch2.avro;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.apache.avro.mapred.AvroWrapper;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapred.MapReduceBase;
import org.apache.hadoop.mapred.Mapper;
import org.apache.hadoop.mapred.OutputCollector;
import org.apache.hadoop.mapred.Reporter;

public class WeblogMapper extends MapReduceBase implements Mapper<LongWritable, Text, AvroWrapper, NullWritable> {
    
    private AvroWrapper<WeblogRecord> outputRecord = new AvroWrapper<WeblogRecord>();
    private WeblogRecord weblogRecord = new WeblogRecord();
    private SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd:HH:mm:ss");
    public void map(LongWritable key, Text value, OutputCollector<AvroWrapper, NullWritable> oc, Reporter rprtr) throws IOException {
        String[] tokens = value.toString().split("\t");
        String cookie = tokens[0];
        String page = tokens[1];
        String date = tokens[2];
        String time = tokens[3];
        String formatedDate = date + ":" + time;
        Date timestamp = null;
        try {
            timestamp = dateFormatter.parse(formatedDate);
        } catch(ParseException ex) {
            //ignore records with invalid dates
            return;
        }
        String ip = tokens[4];
        
        weblogRecord.setCookie(cookie);
        weblogRecord.setDate(timestamp);
        weblogRecord.setIp(ip);
        weblogRecord.setPage(page);
        outputRecord.datum(weblogRecord);
        oc.collect(outputRecord, NullWritable.get());
    }
    
}
