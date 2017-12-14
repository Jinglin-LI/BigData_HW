package DecompressFile.DecompressFile;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URI;
import java.net.URL;
import org.apache.commons.io.FileUtils;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IOUtils;
import org.apache.log4j.BasicConfigurator;

public class DownloadFile {
	public static void main(String[] args) throws IOException {
		String url = args[0];
		String fileName = args[1];
		DownloadFile df = new DownloadFile();
		df.download(url, fileName);
	}
	public void download(String urlString, String fileName) throws IOException {
		URL url = new URL(urlString);
		BasicConfigurator.configure();
		File destination = new File("file.txt.bz2");
		FileUtils.copyURLToFile(url, destination);
		
		String localStr = "file.txt.bz2";
		String dst = fileName;
		InputStream in = new BufferedInputStream(new FileInputStream(localStr));
		Configuration conf = new Configuration();
		conf.addResource(new Path("/usr/local/hadoop-2.4.1/etc/hadoop/core-site.xml"));
	    conf.addResource(new Path("/usr/local/hadoop-2.4.1/etc/hadoop/hdfs-site.xml"));
	    FileSystem fs = FileSystem.get(URI.create(dst), conf);
	    OutputStream out = fs.create(new Path(dst));
	    IOUtils.copyBytes(in, out, 4096, true);
	}
}
