Big Data Pattern and Samples 
=============================================================
## Objectives
Some code are collected for learning big data and hadoop and reusing at work.

## Table of Content
#### The ptn0xx is about MapReduce & HDFS
* __/java/common:__ Commonly shared utility and tools
* __/java/ptn001:__ Default MapReduce program, ToolRunner, Debug&Counter, Invert index sample
* __/java/ptn002:__ Database to HDFS, HDFS to database
* __/java/ptn003:__ HBase to HDFS, HDFS to HBase
* __/java/ptn004:__ XML to HDFS, HDFS to XML, customized Inputformat for XML
* __/java/ptn005:__ Two ways to read and write Avro files
* __/java/ptn006:__ Sequence file reader and writer, customized WritableComparable
* __/java/ptn007:__ Customized key, value, InputFormat, RecordReader, Partitioner
* __/java/ptn008:__ Use distributed cache
* __/java/ptn009:__ Secondary and global sorting
* __/java/ptn010:__ Combine small files into big ones by Avro, SequenceFiles, CombineFileInputFormat
* __/java/ptn011:__ Read and write compressed files and LZOP
* __/java/ptn012:__ Log processing utility
* __/java/ptn013:__ Split reader to exam split content

#### The ptn1xx is about MRUnit
* __/java/ptn101:__ JUnit help class for MRUnit
* __/java/ptn102:__ Identity Map and Reduce test

#### The ptn2xx is about Hive
* __/java/ptn201:__ Hive UDF, UDAF, and GenericUDF
* __/java/ptn202:__ Hive SerDe

#### The ptn3xx is about Pig
* __/java/ptn301:__ Pig customized store/load function for common log and sequencefile
* __/java/ptn302:__ Pig UDF for LoadFunc, EvalFunc, and FilterFunc

#### The ptn4xx is about HBase
* __/java/ptn401:__ HBase CRUD Operations in terms of put, get, delete
* __/java/ptn402:__ HBase scan and row locking
* __/java/ptn403:__ HBase imports data from other source

## Hadoop Version

All the code has been exercised against CDH3u2, which for the purposes
of the code is the same has Hadoop 0.20.x.  There are a couple of places
where I utilize some features in Pig 0.9.1, which won't work with CDH3u1
which uses 0.8.1.


## Building and Running

#### Download 

<pre><code>git clone git://github.com/willddy/bigdata_pattern.git
</code></pre>

#### Build

<pre><code>cd bigdata_pattern
mvn package
</code></pre>

#### Runtime Dependencies

Many of the examples use Snappy and LZOP compression.  Therefore you may get runtime errors if you don't have them installed and configured
in your cluster.

Snappy can be installed on CDH by following the instructions at [here](https://ccp.cloudera.com/display/CDHDOC/Snappy+Installation)

To install LZOP follow the instructions [here](https://github.com/kevinweil/hadoop-lzo)

#### Run an example
<pre><code># copy the input files into HDFS
hadoop fs -mkdir /tmp
hadoop fs -put you-test-data/* /tmp/

# replace the path below with the location of your Hadoop installation
# this isn't required if you are running CDH3, for example
export HADOOP_HOME=/usr/local/hadoop

# run the map-reduce job
bin/run.sh ptn001.InvertedIndexMapReduce /tmp/file1.txt /tmp/file2.txt output
</code></pre>
