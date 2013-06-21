package ptn010.solve03;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.io.WritableComparable;

@SuppressWarnings("rawtypes")
public class MultiFileInputWritableComparable implements WritableComparable {

    private long offset;       //offset of this file block
    private String fileName;   //filename of this block

    public long getOffset() {
		return offset;
	}

	public void setOffset(long offset) {
		this.offset = offset;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public void readFields(DataInput in) throws IOException {
      this.offset = in.readLong();
      this.fileName = Text.readString(in);
    }

    public void write(DataOutput out) throws IOException {
      out.writeLong(offset);
      Text.writeString(out, fileName);
    }

    public int compareTo(Object object) {
      MultiFileInputWritableComparable that = (MultiFileInputWritableComparable)object;

      int compare = this.fileName.compareTo(that.fileName);
      if(compare == 0) {
        return (int)Math.signum((double)(this.offset - that.offset));
      }
      return compare;
    }
    @Override
    public boolean equals(Object object) {
      if(object instanceof MultiFileInputWritableComparable)
        return this.compareTo(object) == 0;
      return false;
    }
    @Override
    public int hashCode() {
      assert false : "hashCode not designed";
      return 42; //an arbitrary constant
    }
}