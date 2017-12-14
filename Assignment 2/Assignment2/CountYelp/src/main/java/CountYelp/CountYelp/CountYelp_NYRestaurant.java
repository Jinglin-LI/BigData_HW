package CountYelp.CountYelp;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.*;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.util.GenericOptionsParser;


public class CountYelp_NYRestaurant {
	public static class BusinessMap extends Mapper<LongWritable, Text, Text, Text> {		
		static Set<String> businessId = new HashSet<String>();
		
		@Override
		protected void setup(Context context) throws IOException, InterruptedException {
			
		}
		
		@Override
		public void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
			String delims = "^";
			String[] businessData = StringUtils.split(value.toString(), delims);
			
			if (businessData.length == 3) {
				if (businessData[1].contains("NY") && businessData[2].contains("Res")) {
					context.write(new Text(businessData[0]), new Text(businessData[1]));
					BusinessMap.businessId.add(businessData[0]);
				}
			}
		}
	}
	
	public static class Reduce extends Reducer<Text, Text, Text, Text> {
		
		@Override
		public void reduce(Text key, Iterable<Text> values, Context context) throws IOException, InterruptedException {
			context.write(key, values.iterator().next());
		}
	}
	
	public static void main(String[] args) throws Exception {
		Configuration conf = new Configuration();
		String[] otherArgs = new GenericOptionsParser(conf, args).getRemainingArgs();
		if (otherArgs.length != 2) {
			System.err.println("Usage: CountYelpBusiness <in> <out>");
			System.exit(2);
		}
		
		Job job = Job.getInstance(conf, "CountYelp");
		job.setJarByClass(CountYelp_NYRestaurant.class);
		
		job.setMapperClass(BusinessMap.class);
		job.setReducerClass(Reduce.class);
		
		job.setOutputKeyClass(Text.class);
		job.setOutputValueClass(Text.class);
		
		FileInputFormat.addInputPath(job, new Path(otherArgs[0]));
		FileOutputFormat.setOutputPath(job, new Path(otherArgs[1]));
		
		System.exit(job.waitForCompletion(true)? 0 : 1);
	}
}
