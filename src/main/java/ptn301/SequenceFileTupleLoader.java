package ptn301;
/**
 * A basic SequenceFile Load Func.  Handles loading sequence file.
 */
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.hadoop.io.Writable;
import org.apache.hadoop.mapreduce.InputFormat;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.RecordReader;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.SequenceFileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.SequenceFileRecordReader;
import org.apache.pig.FileInputLoadFunc;
import org.apache.pig.backend.hadoop.executionengine.mapReduceLayer.PigSplit;
import org.apache.pig.data.Tuple;
import org.apache.pig.data.TupleFactory;

import java.io.IOException;

public class SequenceFileTupleLoader extends FileInputLoadFunc {
  
  private SequenceFileRecordReader<Writable, Writable> reader;
 
  protected static final Log LOG = LogFactory.getLog(
    SequenceFileTupleLoader.class);
  protected TupleFactory mTupleFactory = TupleFactory.getInstance();

  @SuppressWarnings("rawtypes")
  @Override
  public InputFormat getInputFormat() throws IOException {
    return new SequenceFileInputFormat<Writable, Writable>();
  }
  
  @Override
  public void setLocation(String location, Job job) throws IOException {
    FileInputFormat.setInputPaths(job, location);    
  }

  @SuppressWarnings({ "rawtypes", "unchecked" })
  @Override
  public void prepareToRead(RecordReader reader, PigSplit split)
        throws IOException {
    this.reader = (SequenceFileRecordReader) reader;
  }

  @Override
  public Tuple getNext() throws IOException {
    boolean next;
    try {
      next = reader.nextKeyValue();
    } catch (InterruptedException e) {
      throw new IOException(e);
    }
    
    if (!next) return null;
    
    Object value = reader.getCurrentValue();
    
    if (value == null) {
      return null;
    }
    if(!(value instanceof Tuple)) {
      return null;
    }

    return (Tuple) value;
  }


}
