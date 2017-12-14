package WordCount_POS.WordCount_POS;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

import org.apache.commons.io.FilenameUtils;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IOUtils;


public class ResultPrinter {
	private String resultPath;
    private String resultContent;

    public ResultPrinter(String resultPath) throws IOException {
        this.resultPath = resultPath;

        Configuration conf = new Configuration();
        Path _resultPath = new Path(this.resultPath);
        FileSystem fs = FileSystem.get(conf);
        BufferedInputStream bf = new BufferedInputStream(fs.open(_resultPath));
        Scanner sc = new Scanner(bf);
        StringBuilder sb = new StringBuilder("");
        int next;
        while (sc.hasNextLine()) {
            sb.append("Length: ").append(sc.nextInt()).append('\n')
                    .append("Count of Words: ").append(sc.nextInt()).append('\n')
                    .append("Distribution of POS: {\n");
            if ((next = sc.nextInt()) != 0)
                sb.append("\tNoun:\t\t\t").append(next).append('\n');
            if ((next = sc.nextInt()) != 0)
                sb.append("\tPronoun:\t\t").append(next).append('\n');
            if ((next = sc.nextInt()) != 0)
                sb.append("\tVerb (usu participle):\t").append(next).append('\n');
            if ((next = sc.nextInt()) != 0)
                sb.append("\tVerb (transitive):\t").append(next).append('\n');
            if ((next = sc.nextInt()) != 0)
                sb.append("\tVerb (intransitive):\t").append(next).append('\n');
            if ((next = sc.nextInt()) != 0)
                sb.append("\tAdverb:\t\t\t").append(next).append('\n');
            if ((next = sc.nextInt()) != 0)
                sb.append("\tAdjective:\t\t").append(next).append('\n');
            if ((next = sc.nextInt()) != 0)
                sb.append("\tConjunction:\t\t").append(next).append('\n');
            if ((next = sc.nextInt()) != 0)
                sb.append("\tPreposition:\t\t").append(next).append('\n');
            if ((next = sc.nextInt()) != 0)
                sb.append("\tInterjection:\t\t").append(next).append('\n');
            if ((next = sc.nextInt()) != 0)
                sb.append("\tNo Matching POS:\t").append(next).append('\n');
            sb.append("}\n");
            sc.nextLine();
        }
        sc.close();
        bf.close();
        this.resultContent = sb.toString();
    }

    void printResult() {
        System.out.print(this);
    }

    void writeResult() throws IOException {
        Configuration conf = new Configuration();
        FileSystem fs = FileSystem.get(conf);

        InputStream in = new ByteArrayInputStream
                (this.resultContent.getBytes(StandardCharsets.UTF_8));

        Path dstPath = new Path(FilenameUtils.getFullPath(this.resultPath) + "result.txt");
        OutputStream out = fs.create(dstPath);

        IOUtils.copyBytes(in, out, 4096, false);
        IOUtils.closeStream(out);
        fs.close();
    }

    @Override
    public String toString() {
        return resultContent;
    }
    	
}
