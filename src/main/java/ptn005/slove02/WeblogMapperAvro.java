package com.packt.hadoop.hdfs.ch2.avro;

import java.io.IOException;
import org.apache.avro.mapred.AvroWrapper;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapred.MapReduceBase;
import org.apache.hadoop.mapred.Mapper;
import org.apache.hadoop.mapred.OutputCollector;
import org.apache.hadoop.mapred.Reporter;

public class WeblogMapperAvro extends MapReduceBase
            implements Mapper<AvroWrapper<WeblogRecord>, NullWritable, Text, NullWritable>
{
    private Text text = new Text();
    public void map(AvroWrapper<WeblogRecord> key, NullWritable value, OutputCollector<Text, NullWritable> oc, Reporter rprtr) throws IOException {
        text.set(key.datum().toString());
        oc.collect(text, NullWritable.get());
    }
}
