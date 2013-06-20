Big Data Pattern and Samples 
=============================================================
## Objectives
Some code are collected for learning big data and hadoop and reusing at work.

## Table of Content
#### The ptn0xx is about MapReduce
* ptn001: Default MapReduce program, extends Configured implements Tool, invert index Sample
* ptn002: Database to HDFS, HDFS to database
* ptn003: HBase to HDFS, HDFS to HBase
* ptn004: XML to HDFS, HDFS to XML, customized Inputformat for XML
* ptn005: Two ways to read and write Avro files
* ptn006: Sequence file reader and writer, customized WritableComparable
* ptn007: Customized key, value, InputFormat, RecordReader, Partitioner
* ptn008: Use distributed cache
* ptn009: Secondary and global sorting
* ptn010: 
* ptn011: 
* ptn012:
* ptn013: 
* ptn014: 
* ptn015:

#### The ptn1xx is about MRUnit
#### The ptn2xx is about Hive
#### The ptn3xx is about Pig
#### The ptn4xx is about HBase
* ptn400: CRUD Operations in terms of put, get, delete.

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

Snappy can be installed on CDH by following the instructions at
 https://ccp.cloudera.com/display/CDHDOC/Snappy+Installation.

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
