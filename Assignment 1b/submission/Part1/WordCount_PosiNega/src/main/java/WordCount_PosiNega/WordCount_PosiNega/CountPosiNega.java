package WordCount_PosiNega.WordCount_PosiNega;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashSet;
import java.util.Scanner;
import java.util.regex.Pattern;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;
import org.apache.log4j.Logger;


public class CountPosiNega extends Configured implements Tool {
	private static final Logger LOG = Logger.getLogger(CountPosiNega.class);
	public static void main(String[] args) throws Exception {
		int res = ToolRunner.run(new CountPosiNega(), args);
		System.exit(res);
	}
	
	public int run(String[] args) throws Exception {
		Job job = Job.getInstance(getConf(), "wordcount");
		job.setJarByClass(this.getClass());
		FileInputFormat.addInputPath(job, new Path(args[0]));
	    FileOutputFormat.setOutputPath(job, new Path(args[1]));
	    job.setMapperClass(CountPosiNegaMapper.class);
	    job.setCombinerClass(CountPosiNegaReducer.class);
	    job.setReducerClass(CountPosiNegaReducer.class);
	    job.setOutputKeyClass(Text.class);
	    job.setOutputValueClass(IntWritable.class);
	    return job.waitForCompletion(true) ? 0 : 1;
	}
	
	public static class CountPosiNegaMapper extends Mapper<LongWritable, Text, Text, IntWritable> {
		private final static IntWritable one = new IntWritable(1);
		private final static Text positiveText = new Text("Number of positive words: ");
		private final static Text negativeText = new Text("Number of negative words: ");
		private boolean caseSensitive = false;
		private static final Pattern WORD_BOUNDARY = Pattern.compile("\\s*\\b\\s*");
		private final static HashSet<String> positiveWords = getWords("opinion-lexicon-English/positive-words.txt");
		private final static HashSet<String> negativeWords = getWords("opinion-lexicon-English/negative-words.txt");
		
		protected void setup(Mapper.Context context) throws IOException, InterruptedException {
			Configuration config = context.getConfiguration();
			this.caseSensitive = config.getBoolean("wordcount.case.sensitive", false);
		}
		
		public void map(LongWritable offset, Text lineText, Context context) throws IOException, InterruptedException {
			String line = lineText.toString();
			if (!caseSensitive) {
				line = line.toLowerCase();
			}
			for (String word : WORD_BOUNDARY.split(line)) {
				if (word.isEmpty()) {
					continue;
				}
				if (positiveWords.contains(word)) {
					context.write(positiveText, one);
				} else if (negativeWords.contains(word)) {
					context.write(negativeText, one);
				}
			}
		}
	}
	
	public static class CountPosiNegaReducer extends Reducer<Text, IntWritable, Text, IntWritable> {
		@Override
		public void reduce(Text word, Iterable<IntWritable> counts, Context context) throws IOException, InterruptedException {
			int sum = 0;
			for (IntWritable count : counts) {
				sum += count.get();
			}
			context.write(word, new IntWritable(sum));
		}
	}
	
	private static HashSet<String> getWords(String fileName) {
		HashSet<String> words = new HashSet<String>();
		InputStream inputStream = CountPosiNegaMapper.class.getClassLoader().getResourceAsStream(fileName);
		Scanner scanner = new Scanner(inputStream);
		while (scanner.hasNextLine()) {
			words.add(scanner.nextLine());
		}
		scanner.close();
		return words;
	}
}