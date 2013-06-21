package ptn005.slove01; 
/**
 * Auto generated by Avro
 * 
 * DO NOT EDIT DIRECTLY
 */
@SuppressWarnings("all")
public class Stock extends org.apache.avro.specific.SpecificRecordBase implements org.apache.avro.specific.SpecificRecord {
  public static final org.apache.avro.Schema SCHEMA$ = org.apache.avro.Schema.parse("{\"type\":\"record\",\"name\":\"Stock\",\"namespace\":\"com.manning.hip.ch3.avro.gen\",\"fields\":[{\"name\":\"symbol\",\"type\":\"string\"},{\"name\":\"date\",\"type\":\"string\"},{\"name\":\"open\",\"type\":\"double\"},{\"name\":\"high\",\"type\":\"double\"},{\"name\":\"low\",\"type\":\"double\"},{\"name\":\"close\",\"type\":\"double\"},{\"name\":\"volume\",\"type\":\"int\"},{\"name\":\"adjClose\",\"type\":\"double\"}]}");
  @Deprecated public java.lang.CharSequence symbol;
  @Deprecated public java.lang.CharSequence date;
  @Deprecated public double open;
  @Deprecated public double high;
  @Deprecated public double low;
  @Deprecated public double close;
  @Deprecated public int volume;
  @Deprecated public double adjClose;
  public org.apache.avro.Schema getSchema() { return SCHEMA$; }
  // Used by DatumWriter.  Applications should not call. 
  public java.lang.Object get(int field$) {
    switch (field$) {
    case 0: return symbol;
    case 1: return date;
    case 2: return open;
    case 3: return high;
    case 4: return low;
    case 5: return close;
    case 6: return volume;
    case 7: return adjClose;
    default: throw new org.apache.avro.AvroRuntimeException("Bad index");
    }
  }
  // Used by DatumReader.  Applications should not call. 
  @SuppressWarnings(value="unchecked")
  public void put(int field$, java.lang.Object value$) {
    switch (field$) {
    case 0: symbol = (java.lang.CharSequence)value$; break;
    case 1: date = (java.lang.CharSequence)value$; break;
    case 2: open = (java.lang.Double)value$; break;
    case 3: high = (java.lang.Double)value$; break;
    case 4: low = (java.lang.Double)value$; break;
    case 5: close = (java.lang.Double)value$; break;
    case 6: volume = (java.lang.Integer)value$; break;
    case 7: adjClose = (java.lang.Double)value$; break;
    default: throw new org.apache.avro.AvroRuntimeException("Bad index");
    }
  }

  /**
   * Gets the value of the 'symbol' field.
   */
  public java.lang.CharSequence getSymbol() {
    return symbol;
  }

  /**
   * Sets the value of the 'symbol' field.
   * @param value the value to set.
   */
  public void setSymbol(java.lang.CharSequence value) {
    this.symbol = value;
  }

  /**
   * Gets the value of the 'date' field.
   */
  public java.lang.CharSequence getDate() {
    return date;
  }

  /**
   * Sets the value of the 'date' field.
   * @param value the value to set.
   */
  public void setDate(java.lang.CharSequence value) {
    this.date = value;
  }

  /**
   * Gets the value of the 'open' field.
   */
  public java.lang.Double getOpen() {
    return open;
  }

  /**
   * Sets the value of the 'open' field.
   * @param value the value to set.
   */
  public void setOpen(java.lang.Double value) {
    this.open = value;
  }

  /**
   * Gets the value of the 'high' field.
   */
  public java.lang.Double getHigh() {
    return high;
  }

  /**
   * Sets the value of the 'high' field.
   * @param value the value to set.
   */
  public void setHigh(java.lang.Double value) {
    this.high = value;
  }

  /**
   * Gets the value of the 'low' field.
   */
  public java.lang.Double getLow() {
    return low;
  }

  /**
   * Sets the value of the 'low' field.
   * @param value the value to set.
   */
  public void setLow(java.lang.Double value) {
    this.low = value;
  }

  /**
   * Gets the value of the 'close' field.
   */
  public java.lang.Double getClose() {
    return close;
  }

  /**
   * Sets the value of the 'close' field.
   * @param value the value to set.
   */
  public void setClose(java.lang.Double value) {
    this.close = value;
  }

  /**
   * Gets the value of the 'volume' field.
   */
  public java.lang.Integer getVolume() {
    return volume;
  }

  /**
   * Sets the value of the 'volume' field.
   * @param value the value to set.
   */
  public void setVolume(java.lang.Integer value) {
    this.volume = value;
  }

  /**
   * Gets the value of the 'adjClose' field.
   */
  public java.lang.Double getAdjClose() {
    return adjClose;
  }

  /**
   * Sets the value of the 'adjClose' field.
   * @param value the value to set.
   */
  public void setAdjClose(java.lang.Double value) {
    this.adjClose = value;
  }

  /** Creates a new Stock RecordBuilder */
  public static Stock.Builder newBuilder() {
    return new Stock.Builder();
  }
  
  /** Creates a new Stock RecordBuilder by copying an existing Builder */
  public static Stock.Builder newBuilder(Stock.Builder other) {
    return new Stock.Builder(other);
  }
  
  /** Creates a new Stock RecordBuilder by copying an existing Stock instance */
  public static Stock.Builder newBuilder(Stock other) {
    return new Stock.Builder(other);
  }
  
  /**
   * RecordBuilder for Stock instances.
   */
  public static class Builder extends org.apache.avro.specific.SpecificRecordBuilderBase<Stock>
    implements org.apache.avro.data.RecordBuilder<Stock> {

    private java.lang.CharSequence symbol;
    private java.lang.CharSequence date;
    private double open;
    private double high;
    private double low;
    private double close;
    private int volume;
    private double adjClose;

    /** Creates a new Builder */
    private Builder() {
      super(Stock.SCHEMA$);
    }
    
    /** Creates a Builder by copying an existing Builder */
    private Builder(Stock.Builder other) {
      super(other);
    }
    
    /** Creates a Builder by copying an existing Stock instance */
    private Builder(Stock other) {
            super(Stock.SCHEMA$);
      if (isValidValue(fields()[0], other.symbol)) {
        this.symbol = (java.lang.CharSequence) data().deepCopy(fields()[0].schema(), other.symbol);
        fieldSetFlags()[0] = true;
      }
      if (isValidValue(fields()[1], other.date)) {
        this.date = (java.lang.CharSequence) data().deepCopy(fields()[1].schema(), other.date);
        fieldSetFlags()[1] = true;
      }
      if (isValidValue(fields()[2], other.open)) {
        this.open = (java.lang.Double) data().deepCopy(fields()[2].schema(), other.open);
        fieldSetFlags()[2] = true;
      }
      if (isValidValue(fields()[3], other.high)) {
        this.high = (java.lang.Double) data().deepCopy(fields()[3].schema(), other.high);
        fieldSetFlags()[3] = true;
      }
      if (isValidValue(fields()[4], other.low)) {
        this.low = (java.lang.Double) data().deepCopy(fields()[4].schema(), other.low);
        fieldSetFlags()[4] = true;
      }
      if (isValidValue(fields()[5], other.close)) {
        this.close = (java.lang.Double) data().deepCopy(fields()[5].schema(), other.close);
        fieldSetFlags()[5] = true;
      }
      if (isValidValue(fields()[6], other.volume)) {
        this.volume = (java.lang.Integer) data().deepCopy(fields()[6].schema(), other.volume);
        fieldSetFlags()[6] = true;
      }
      if (isValidValue(fields()[7], other.adjClose)) {
        this.adjClose = (java.lang.Double) data().deepCopy(fields()[7].schema(), other.adjClose);
        fieldSetFlags()[7] = true;
      }
    }

    /** Gets the value of the 'symbol' field */
    public java.lang.CharSequence getSymbol() {
      return symbol;
    }
    
    /** Sets the value of the 'symbol' field */
    public Stock.Builder setSymbol(java.lang.CharSequence value) {
      validate(fields()[0], value);
      this.symbol = value;
      fieldSetFlags()[0] = true;
      return this; 
    }
    
    /** Checks whether the 'symbol' field has been set */
    public boolean hasSymbol() {
      return fieldSetFlags()[0];
    }
    
    /** Clears the value of the 'symbol' field */
    public Stock.Builder clearSymbol() {
      symbol = null;
      fieldSetFlags()[0] = false;
      return this;
    }

    /** Gets the value of the 'date' field */
    public java.lang.CharSequence getDate() {
      return date;
    }
    
    /** Sets the value of the 'date' field */
    public Stock.Builder setDate(java.lang.CharSequence value) {
      validate(fields()[1], value);
      this.date = value;
      fieldSetFlags()[1] = true;
      return this; 
    }
    
    /** Checks whether the 'date' field has been set */
    public boolean hasDate() {
      return fieldSetFlags()[1];
    }
    
    /** Clears the value of the 'date' field */
    public Stock.Builder clearDate() {
      date = null;
      fieldSetFlags()[1] = false;
      return this;
    }

    /** Gets the value of the 'open' field */
    public java.lang.Double getOpen() {
      return open;
    }
    
    /** Sets the value of the 'open' field */
    public Stock.Builder setOpen(double value) {
      validate(fields()[2], value);
      this.open = value;
      fieldSetFlags()[2] = true;
      return this; 
    }
    
    /** Checks whether the 'open' field has been set */
    public boolean hasOpen() {
      return fieldSetFlags()[2];
    }
    
    /** Clears the value of the 'open' field */
    public Stock.Builder clearOpen() {
      fieldSetFlags()[2] = false;
      return this;
    }

    /** Gets the value of the 'high' field */
    public java.lang.Double getHigh() {
      return high;
    }
    
    /** Sets the value of the 'high' field */
    public Stock.Builder setHigh(double value) {
      validate(fields()[3], value);
      this.high = value;
      fieldSetFlags()[3] = true;
      return this; 
    }
    
    /** Checks whether the 'high' field has been set */
    public boolean hasHigh() {
      return fieldSetFlags()[3];
    }
    
    /** Clears the value of the 'high' field */
    public Stock.Builder clearHigh() {
      fieldSetFlags()[3] = false;
      return this;
    }

    /** Gets the value of the 'low' field */
    public java.lang.Double getLow() {
      return low;
    }
    
    /** Sets the value of the 'low' field */
    public Stock.Builder setLow(double value) {
      validate(fields()[4], value);
      this.low = value;
      fieldSetFlags()[4] = true;
      return this; 
    }
    
    /** Checks whether the 'low' field has been set */
    public boolean hasLow() {
      return fieldSetFlags()[4];
    }
    
    /** Clears the value of the 'low' field */
    public Stock.Builder clearLow() {
      fieldSetFlags()[4] = false;
      return this;
    }

    /** Gets the value of the 'close' field */
    public java.lang.Double getClose() {
      return close;
    }
    
    /** Sets the value of the 'close' field */
    public Stock.Builder setClose(double value) {
      validate(fields()[5], value);
      this.close = value;
      fieldSetFlags()[5] = true;
      return this; 
    }
    
    /** Checks whether the 'close' field has been set */
    public boolean hasClose() {
      return fieldSetFlags()[5];
    }
    
    /** Clears the value of the 'close' field */
    public Stock.Builder clearClose() {
      fieldSetFlags()[5] = false;
      return this;
    }

    /** Gets the value of the 'volume' field */
    public java.lang.Integer getVolume() {
      return volume;
    }
    
    /** Sets the value of the 'volume' field */
    public Stock.Builder setVolume(int value) {
      validate(fields()[6], value);
      this.volume = value;
      fieldSetFlags()[6] = true;
      return this; 
    }
    
    /** Checks whether the 'volume' field has been set */
    public boolean hasVolume() {
      return fieldSetFlags()[6];
    }
    
    /** Clears the value of the 'volume' field */
    public Stock.Builder clearVolume() {
      fieldSetFlags()[6] = false;
      return this;
    }

    /** Gets the value of the 'adjClose' field */
    public java.lang.Double getAdjClose() {
      return adjClose;
    }
    
    /** Sets the value of the 'adjClose' field */
    public Stock.Builder setAdjClose(double value) {
      validate(fields()[7], value);
      this.adjClose = value;
      fieldSetFlags()[7] = true;
      return this; 
    }
    
    /** Checks whether the 'adjClose' field has been set */
    public boolean hasAdjClose() {
      return fieldSetFlags()[7];
    }
    
    /** Clears the value of the 'adjClose' field */
    public Stock.Builder clearAdjClose() {
      fieldSetFlags()[7] = false;
      return this;
    }

    @Override
    public Stock build() {
      try {
        Stock record = new Stock();
        record.symbol = fieldSetFlags()[0] ? this.symbol : (java.lang.CharSequence) defaultValue(fields()[0]);
        record.date = fieldSetFlags()[1] ? this.date : (java.lang.CharSequence) defaultValue(fields()[1]);
        record.open = fieldSetFlags()[2] ? this.open : (java.lang.Double) defaultValue(fields()[2]);
        record.high = fieldSetFlags()[3] ? this.high : (java.lang.Double) defaultValue(fields()[3]);
        record.low = fieldSetFlags()[4] ? this.low : (java.lang.Double) defaultValue(fields()[4]);
        record.close = fieldSetFlags()[5] ? this.close : (java.lang.Double) defaultValue(fields()[5]);
        record.volume = fieldSetFlags()[6] ? this.volume : (java.lang.Integer) defaultValue(fields()[6]);
        record.adjClose = fieldSetFlags()[7] ? this.adjClose : (java.lang.Double) defaultValue(fields()[7]);
        return record;
      } catch (Exception e) {
        throw new org.apache.avro.AvroRuntimeException(e);
      }
    }
  }
}