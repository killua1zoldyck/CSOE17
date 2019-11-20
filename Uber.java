import java.io.IOException;
import java.util.StringTokenizer;
 
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapred.*;
import org.apache.hadoop.mapred.FileInputFormat;
import org.apache.hadoop.mapred.FileOutputFormat;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.*;


import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import org.apache.hadoop.mapred.OutputCollector;
import java.util.*;

class TokenizerMapper
        extends MapReduceBase implements Mapper<LongWritable, Text, Text, IntWritable>{

  private final static IntWritable one = new IntWritable(1);
  private Text word = new Text();

  @Override
  public void map(LongWritable key, Text value, OutputCollector <Text, IntWritable> output, Reporter reporter) throws IOException {

    String valueString = value.toString();
    String[] SingleCountryData = valueString.split(",");
    output.collect(new Text(SingleCountryData[0]), one);
  }
}

class IntSumReducer
        extends MapReduceBase implements Reducer<Text,IntWritable,Text,IntWritable> {
  private IntWritable result = new IntWritable();

  @Override
  public void reduce(Text key, Iterator<IntWritable> values, OutputCollector<Text,IntWritable> output, Reporter reporter) throws IOException {
    int sum = 0;
    while (values.hasNext()) {
      IntWritable value = (IntWritable) values.next();
      sum += value.get();
    }
    result.set(sum);
    output.collect(key, result);
  }
}

public class Uber {
 
  public static void main(String[] args) throws Exception {


    JobClient my_client = new JobClient();
    // Create a configuration object for the job
    JobConf job_conf = new JobConf(Uber.class);

    // Set a name of the Job
    job_conf.setJobName("uber data analysis");

    // Specify data type of output key and value
    job_conf.setOutputKeyClass(Text.class);
    job_conf.setOutputValueClass(IntWritable.class);

    // Specify names of Mapper and Reducer Class
    job_conf.setMapperClass(TokenizerMapper.class);
    job_conf.setReducerClass(IntSumReducer.class);

    // Specify formats of the data type of Input and output
    job_conf.setInputFormat(TextInputFormat.class);
    job_conf.setOutputFormat(TextOutputFormat.class);

    // Set input and output directories using command line arguments,
    //arg[0] = name of input directory on HDFS, and arg[1] =  name of output directory to be created to store the output file.

    FileInputFormat.setInputPaths(job_conf, new Path(args[0]));
    FileOutputFormat.setOutputPath(job_conf, new Path(args[1]));

    my_client.setConf(job_conf);
    try {
      // Run the job
      JobClient.runJob(job_conf);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
