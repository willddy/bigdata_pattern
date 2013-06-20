package ptn009;
/*
 * Grouping, only cares about LastName
 */
import org.apache.hadoop.io.*;

public class PersonNameComparator extends WritableComparator {

  protected PersonNameComparator() {
    super(Person.class, true);
  }

  @Override
  @SuppressWarnings("rawtypes")
  public int compare(WritableComparable o1, WritableComparable o2) {

    Person p1 = (Person) o1;
    Person p2 = (Person) o2;

    return p1.getLastName().compareTo(p2.getLastName());

  }
}
