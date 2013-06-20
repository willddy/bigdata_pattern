package ptn009;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Partitioner;

public class PersonNamePartitioner extends
    Partitioner<Person, Text> {

  @Override
  public int getPartition(Person key, Text value, int numPartitions) {
    return Math.abs(key.getLastName().hashCode() * 127) %
        numPartitions;
  }
}
