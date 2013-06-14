package com.packt.ch5.advjoin.redis;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;
import redis.clients.jedis.Jedis;

public class WeblogMapper extends Mapper<Object, Text, Text, Text> {

    private Map<String, String> ipCountryMap = new HashMap<String, String>();
    private Jedis jedis = null;
    private Text outputKey = new Text();
    private Text outputValue = new Text();
    
    private String getCountry(String ip) {
        String country = ipCountryMap.get(ip);
        if (country == null) {
            if (jedis == null) {
                jedis = new Jedis("localhost");
                jedis.select(0);
            }
            country = jedis.get(ip);
            ipCountryMap.put(ip, country);
        }
        return country;
    }

    @Override
    protected void map(Object key, Text value, Context context) throws IOException, InterruptedException {
        String row = value.toString();
        String[] tokens = row.split("\t");
        String ip = tokens[0];
        String country = getCountry(ip);
        outputKey.set(country);
        outputValue.set(row);
        context.write(outputKey, outputValue);
    }
    
}
