package DecompressZip.DecompressZip;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URI;
import java.net.URL;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IOUtils;
import org.apache.hadoop.util.Progressable;

public class ZipDecompressor {

	public static void main(String[] args) throws Exception {
		
		URL url = new URL(args[0]);
		String pdst = args[1];
		Configuration conf = new Configuration();

        InputStream in = null;
        ZipInputStream zin = null;

        in = new BufferedInputStream(url.openStream());
        zin = new ZipInputStream(in);

        ZipEntry entry;
        while((entry = zin.getNextEntry()) != null) {
            String dst = pdst + "/" + entry.getName();
            FileSystem fs = FileSystem.get(URI.create(dst), conf);
            Path dstPath = new Path(dst);
            OutputStream out = fs.create(dstPath, new Progressable() {
                public void progress() {
                    System.out.print(".");
                }
            });

            IOUtils.copyBytes(zin, out, 4096, false);
            IOUtils.closeStream(out);
            fs.close();
        }
       
        IOUtils.closeStream(zin);
        IOUtils.closeStream(in);
    }
}
