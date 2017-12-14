package CountYelp.CountYelp;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.DoubleWritable;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.util.GenericOptionsParser;
import org.apache.hadoop.conf.Configuration;
import org.apache.commons.lang.StringUtils;


public class CountYelp_LowestTop10Rating {
	public static class BusinessMap extends Mapper<LongWritable, Text, Text, DoubleWritable> {
		
		@Override
		public void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
			String delims = "^";
			String[] businessData = StringUtils.split(value.toString(), delims);
			
			if (businessData.length == 4) {
				double rating = Double.parseDouble(businessData[3]);
				context.write(new Text(businessData[2]), new DoubleWritable(rating));
			}
		}
		
		@Override
		protected void setup(Context context) throws IOException, InterruptedException {
		}
	}
	
	public static class Reduce extends Reducer<Text, DoubleWritable, Text, DoubleWritable> {
		private Map<Text, DoubleWritable> countMap = new HashMap<Text, DoubleWritable>();
		
		@Override
		public void reduce(Text key, Iterable<DoubleWritable> values, Context context) throws IOException, InterruptedException {
			int count = 0;
			double sum = 0.0;
			for (DoubleWritable value : values) {
				sum += value.get();
				count++;
			}
			Double average = (double) sum / (double) count;
			countMap.put(new Text(key.toString()), new DoubleWritable(average));
		}
		
		@Override
		protected void cleanup(Context context) throws IOException, InterruptedException {
			Map<Text, DoubleWritable> sortedMap = MiscUtilsReverse.sortByValues(countMap);
			
			int counter = 0;
			for (Text key : sortedMap.keySet()) {
				if (counter++ == 10) {
					break;
				}
				context.write(key, sortedMap.get(key));
			}
		}
	}
	public static void main(String[] args) throws Exception {
		Configuration conf = new Configuration();
		String[] otherArgs = new GenericOptionsParser(conf, args).getRemainingArgs();
	      if (otherArgs.length != 2) {
	          System.err.println("Usage: CountYelp_LowestTop10Rating <in> <out>");
	          System.exit(2);
	      }
	      Job job = Job.getInstance(conf);
	      job.setJobName("CountYelp_LowestTop10Rating");
	      job.setJarByClass(CountYelp_LowestTop10Rating.class);
	      job.setMapperClass(BusinessMap.class);
	      job.setReducerClass(Reduce.class);
	      job.setOutputKeyClass(Text.class);
	      job.setOutputValueClass(DoubleWritable.class);
	      FileInputFormat.addInputPath(job, new Path(otherArgs[0]));
	      FileOutputFormat.setOutputPath(job, new Path(otherArgs[1]));
	      System.exit(job.waitForCompletion(true) ? 0 : 1);
	}
}
