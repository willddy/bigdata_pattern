import java.io.IOException;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.InputSplit;
import org.apache.hadoop.mapreduce.RecordReader;
import org.apache.hadoop.mapreduce.TaskAttemptContext;
import org.apache.hadoop.mapreduce.lib.input.CombineFileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.CombineFileRecordReader;
import org.apache.hadoop.mapreduce.lib.input.CombineFileSplit;

public class MyMultiFileInputFormat extends CombineFileInputFormat<MultiFileInputWritableComparable, Text>  
{
    public RecordReader<MultiFileInputWritableComparable,Text> createRecordReader(InputSplit split,TaskAttemptContext context) throws IOException 
    {
      return new CombineFileRecordReader<MultiFileInputWritableComparable, Text>
      ((CombineFileSplit)split, context, CombineFileLineRecordReader.class);
    }
    
}