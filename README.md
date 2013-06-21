Big Data Pattern and Samples 
=============================================================
## Objectives
Some code are collected for learning big data and hadoop and reusing at work.

## Table of Content
#### The ptn0xx is about MapReduce & HDFS
* ../java/common: Commonly shared utility and tools
* ../java/ptn001: Default MapReduce program, ToolRunner, Debug&Counter, Invert index sample
* ../java/ptn002: Database to HDFS, HDFS to database
* ../java/ptn003: HBase to HDFS, HDFS to HBase
* ../java/ptn004: XML to HDFS, HDFS to XML, customized Inputformat for XML
* ../java/ptn005: Two ways to read and write Avro files
* ../java/ptn006: Sequence file reader and writer, customized WritableComparable
* ../java/ptn007: Customized key, value, InputFormat, RecordReader, Partitioner
* ../java/ptn008: Use distributed cache
* ../java/ptn009: Secondary and global sorting
* ../java/ptn010: Combine small files into big ones by Avro, sequence files, and CombineFileInputFormat
* ../java/ptn011: Read and write compressed files and LZOP
* ../java/ptn012: Log processing utility
* ../java/ptn013: Split reader to exam split content

#### The ptn1xx is about MRUnit
* ../java/ptn101: JUnit help class for MRUnit
* ../java/ptn102: Identity Map and Reduce test

#### The ptn2xx is about Hive
* ../java/ptn201: Hive UDF, UDAF, and GenericUDF
* ../java/ptn202: Hive SerDe

#### The ptn3xx is about Pig
* ../java/ptn301: Pig customized store/load function for common log and sequencefile (sequencefile is not pig natively support)
* ../java/ptn302: Pig UDF for LoadFunc, EvalFunc, and FilterFunc
* ../java/ptn303: Pig UDF for LoadFunc, EvalFunc, and FilterFunc

#### The ptn4xx is about HBase
* ../java/ptn400: CRUD Operations in terms of put, get, delete.

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

Snappy can be installed on CDH by following the instructions at https://ccp.cloudera.com/display/CDHDOC/Snappy+Installation.

To install LZOP follow the instructions at https://github.com/kevinweil/hadoop-lzo.

#### Run an example
<pre><code># copy the input files into HDFS
hadoop fs -mkdir /tmp
hadoop fs -put you-test-data/* /tmp/

# replace the path below with the location of your Hadoop installation
# this isn't required if you are running CDH3
export HADOOP_HOME=/usr/local/hadoop

# run the map-reduce job
bin/run.sh ptn001.InvertedIndexMapReduce /tmp/file1.txt /tmp/file2.txt output
</code></pre>
