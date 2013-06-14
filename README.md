Big Data Pattern and Samples 
=============================================================
## Objectives
Some code are collected for learning big data and hadoop and reusing at work.

## Table Content
#### The ptn0xx is about Hadoop Map and Reduce
* ptn001: default and standard map and reduce program
* ptn002: 
* ptn003:
* ptn004:   
* ptn005: 
* ptn006:
* ptn007: 
* ptn008: 
* ptn009:
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
* To be added
## Code Stability

The book is currently in the middle of being authored, and as such the
code in this repository will be changing until we get closer to the
production stage.  With that being said, most of the code in GitHub has been
exercised at least once.

##  Issues

If you hit any compilation or execution problems please create an issue
and I'll look into it as soon as I can.

## Hadoop Version

All the code has been exercised against CDH3u2, which for the purposes
of the code is the same has Hadoop 0.20.x.  There are a couple of places
where I utilize some features in Pig 0.9.1, which won't work with CDH3u1
which uses 0.8.1.


## Building and running

####  Download from github

<pre><code>git clone git://github.com/willddy/bigdata_pattern.git
</code></pre>

####  Build

<pre><code>cd bigdata_pattern
mvn package
</code></pre>

#### Runtime Dependencies

Many of the examples use Snappy and LZOP compression.  Therefore
you may get runtime errors if you don't have them installed and configured
in your cluster.

Snappy can be installed on CDH by following the instructions at
 https://ccp.cloudera.com/display/CDHDOC/Snappy+Installation.

To install LZOP follow the instructions at https://github.com/kevinweil/hadoop-lzo.

####  Run an example
<pre><code># copy the input files into HDFS
hadoop fs -mkdir /tmp
hadoop fs -put test-data/ch1/* /tmp/

# replace the path below with the location of your Hadoop installation
# this isn't required if you are running CDH3
export HADOOP_HOME=/usr/local/hadoop

# run the map-reduce job
bin/run.sh com.manning.hip.ch1.InvertedIndexMapReduce /tmp/file1.txt /tmp/file2.txt output
</code></pre>
