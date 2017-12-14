package CountYelp.CountYelp;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.Mapper.Context;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.util.GenericOptionsParser;
import org.apache.hadoop.conf.Configuration;
import org.apache.commons.lang.StringUtils;


public class CountYelp_Top10ZipCode {
	public static class BusinessMap extends Mapper<LongWritable, Text, Text, IntWritable> {
		
		@Override
		protected void setup(Context context) throws IOException, InterruptedException {
			
		}
		
		@Override
		public void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
			String delims = "^";
			String[] businessData = StringUtils.split(value.toString(), delims);
			
			if (businessData.length == 3) {
				String zipCode = businessData[1].substring(businessData[1].length() - 5);
				context.write(new Text(zipCode), new IntWritable(1));
			}
		}
	}
	
	public static class Reduce extends Reducer<Text, IntWritable, Text, IntWritable> {
		
		private Map<Text, IntWritable> countMap = new HashMap<Text, IntWritable>();
		@Override
		protected void reduce(Text key, Iterable<IntWritable> values, Context context) {
			int count = 0;
			for (IntWritable value : values) {
				count += value.get();
			}
			countMap.put(new Text(key), new IntWritable(count));
		}
		
		@Override
		protected void cleanup(Context context) throws IOException, InterruptedException {
			
			Map<Text, IntWritable> sortedMap = MiscUtils.sortByValues(countMap);
			
			int counter = 0;
			for (Text key : sortedMap.keySet()) {
				if (counter++ == 10) {
					break;
				}
				context.write(key, sortedMap.get(key));
			}
		}
	}
	
	public static class TopNCombiner extends Reducer<Text, IntWritable, Text, IntWritable> {
		
		@Override
		public void reduce(Text key, Iterable<IntWritable> values, Context context) throws IOException, InterruptedException {
			int sum = 0;
			for (IntWritable value : values) {
				sum += value.get();
			}
			context.write(key, new IntWritable(sum));
		}
	}
	
	public static void main(String[] args) throws Exception {
		Configuration conf = new Configuration();
		String[] otherArgs = new GenericOptionsParser(conf, args).getRemainingArgs();
	      if (otherArgs.length != 2) {
	          System.err.println("Usage: TopN <in> <out>");
	          System.exit(2);
	      }
	      Job job = Job.getInstance(conf);
	      job.setJobName("Top N");
	      job.setJarByClass(CountYelp_Top10ZipCode.class);
	      job.setMapperClass(BusinessMap.class);
	      job.setCombinerClass(TopNCombiner.class);
	      job.setReducerClass(Reduce.class);
	      job.setOutputKeyClass(Text.class);
	      job.setOutputValueClass(IntWritable.class);
	      FileInputFormat.addInputPath(job, new Path(otherArgs[0]));
	      FileOutputFormat.setOutputPath(job, new Path(otherArgs[1]));
	      System.exit(job.waitForCompletion(true) ? 0 : 1);
	}
}
