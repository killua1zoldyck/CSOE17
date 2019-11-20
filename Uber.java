import java.io.IOException;
import java.util.StringTokenizer;
 
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;
import org.apache.hadoop.mapreduce.lib.map.MultithreadedMapper;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.mapreduce.lib.output.MultipleOutputs;
import org.apache.hadoop.mapreduce.lib.output.TextOutputFormat;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.*;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import org.apache.hadoop.mapred.OutputCollector;
import java.util.*;

class monthlyMR {

  static class TokenizerMapper extends Mapper<LongWritable, Text, Text, IntWritable> {

    private final IntWritable one = new IntWritable(1);
    private Text word = new Text();

    @Override
    public void map(LongWritable key, Text value, Context output) throws IOException, InterruptedException {

      String valueString = value.toString();
      String[] SingleCountryData = valueString.split(",");
      String[] dateAndTime = SingleCountryData[0].split(" ");
      output.write(new Text(dateAndTime[0]), one);
    }
  }

  static class IntSumReducer
          extends Reducer<Text, IntWritable, Text, IntWritable> {
    private IntWritable result = new IntWritable();

    @Override
    public void reduce(Text key, Iterable<IntWritable> values, Context output) throws IOException, InterruptedException {
      int sum = 0;
      for(IntWritable value : values) {
        sum += value.get();
      }
      result.set(sum);
      output.write(key, result);
    }
  }
}


class hourlyMR {

  static class TokenizerMapper extends Mapper<LongWritable, Text, Text, IntWritable> {

    private final IntWritable one = new IntWritable(1);
    private Text word = new Text();

    @Override
    public void map(LongWritable key, Text value, Context output) throws IOException, InterruptedException {

      String valueString = value.toString();
      String[] SingleCountryData = valueString.split(",");
      String[] dateAndTime = SingleCountryData[0].split(" ");
      if(dateAndTime.length > 1) {
        String[] hhmmss = dateAndTime[1].split(":");
        output.write(new Text(hhmmss[0]), one);
      }

      // The if statement because the column names also come in the output
    }
  }

  static class IntSumReducer
          extends Reducer<Text, IntWritable, Text, IntWritable> {
    private IntWritable result = new IntWritable();

    @Override
    public void reduce(Text key, Iterable<IntWritable> values, Context output) throws IOException, InterruptedException {
      int sum = 0;
      for(IntWritable value : values) {
        sum += value.get();
      }
      result.set(sum);
      output.write(key, result);
    }
  }
}


public class Uber {
 
  public static void main(String[] args) throws Exception {


    Job job = new Job();
    job.setJarByClass(Uber.class);

    FileInputFormat.addInputPath(job, new Path(args[0]));
    FileOutputFormat.setOutputPath(job, new Path(args[1]));

//    MultithreadedMapper.setMapperClass(job, monthlyMR.TokenizerMapper.class);
//    MultithreadedMapper.setNumberOfThreads(job, 10);
//
//    job.setMapperClass(monthlyMR.TokenizerMapper.class);
//    job.setCombinerClass(monthlyMR.IntSumReducer.class);
//    job.setReducerClass(monthlyMR.IntSumReducer.class);
//
//    job.setOutputKeyClass(Text.class);
//    job.setOutputValueClass(IntWritable.class);
//
//    /* begin defaults */
//    job.setInputFormatClass(TextInputFormat.class);
//    job.setOutputFormatClass(TextOutputFormat.class);
//    /* end defaults */
//
//    job.waitForCompletion(true);
//

    //    For hourly map reducer

    MultithreadedMapper.setMapperClass(job, hourlyMR.TokenizerMapper.class);
    MultithreadedMapper.setNumberOfThreads(job, 1);

    job.setMapperClass(hourlyMR.TokenizerMapper.class);
    job.setCombinerClass(hourlyMR.IntSumReducer.class);
    job.setReducerClass(hourlyMR.IntSumReducer.class);

    job.setOutputKeyClass(Text.class);
    job.setOutputValueClass(IntWritable.class);

    /* begin defaults */
    job.setInputFormatClass(TextInputFormat.class);
    job.setOutputFormatClass(TextOutputFormat.class);
    /* end defaults */

    job.waitForCompletion(true);
  }
}
