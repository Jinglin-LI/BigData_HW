The Part1 file contains src code of decompress and JavaHDFS, DecompressFile-0.0.1-SNAPSHOT.jar，JavaHDFS-0.0.1-SNAPSHOT.jar  and Readme. 

To run the program to load .bz files and decompress them, you need:

1. Copy DecompressFile-0.0.1-SNAPSHOT.jar file into the cluster (you can also find it in the DecompressFile\target)
2. ssh to cs6360.utdallas.edu, input the command to download .bz2 file and store into HDFS directory:


hadoop jar DecompressFile-0.0.1-SNAPSHOT.jar DecompressFile.DecompressFile.DownloadFile "http://www.utdallas.edu/~axn112530/cs6350/lab2/input/20417.txt.bz2" hdfs://cshadoop1/user/jxl163530/Assignment1/20417.txt.bz2

hadoop jar DecompressFile-0.0.1-SNAPSHOT.jar DecompressFile.DecompressFile.DownloadFile "http://www.utdallas.edu/~axn112530/cs6350/lab2/input/5000-8.txt.bz2" hdfs://cshadoop1/user/jxl163530/Assignment1/5000-8.txt.bz2

hadoop jar DecompressFile-0.0.1-SNAPSHOT.jar DecompressFile.DecompressFile.DownloadFile "http://www.utdallas.edu/~axn112530/cs6350/lab2/input/132.txt.bz2" hdfs://cshadoop1/user/jxl163530/Assignment1/132.txt.bz2

hadoop jar DecompressFile-0.0.1-SNAPSHOT.jar DecompressFile.DecompressFile.DownloadFile "http://www.utdallas.edu/~axn112530/cs6350/lab2/input/1661-8.txt.bz2" hdfs://cshadoop1/user/jxl163530/Assignment1/1661-8.txt.bz2

hadoop jar DecompressFile-0.0.1-SNAPSHOT.jar DecompressFile.DecompressFile.DownloadFile "http://www.utdallas.edu/~axn112530/cs6350/lab2/input/972.txt.bz2" hdfs://cshadoop1/user/jxl163530/Assignment1/972.txt.bz2


hadoop jar DecompressFile-0.0.1-SNAPSHOT.jar DecompressFile.DecompressFile.DownloadFile "http://www.utdallas.edu/~axn112530/cs6350/lab2/input/19699.txt.bz2" hdfs://cshadoop1/user/jxl163530/Assignment1/19699.txt.bz2




3. Use the Maven sample file and type in the command to decompress the files into .txt files. 


hadoop jar JavaHDFS-0.0.1-SNAPSHOT.jar JavaHDFS.JavaHDFS.FileDecompressor "Assignment1/20417.txt.bz2"
hadoop jar JavaHDFS-0.0.1-SNAPSHOT.jar JavaHDFS.JavaHDFS.FileDecompressor "Assignment1/5000-8.txt.bz2"
hadoop jar JavaHDFS-0.0.1-SNAPSHOT.jar JavaHDFS.JavaHDFS.FileDecompressor "Assignment1/132.txt.bz2"
hadoop jar JavaHDFS-0.0.1-SNAPSHOT.jar JavaHDFS.JavaHDFS.FileDecompressor "Assignment1/1661-8.txt.bz2"
hadoop jar JavaHDFS-0.0.1-SNAPSHOT.jar JavaHDFS.JavaHDFS.FileDecompressor "Assignment1/972.txt.bz2"
hadoop jar JavaHDFS-0.0.1-SNAPSHOT.jar JavaHDFS.JavaHDFS.FileDecompressor "Assignment1/19699.txt.bz2"


4. To delete the .bz2 files, run:
hdfs dfs -rm -f -r Assignment1/20417.txt.bz2
hdfs dfs -rm -f -r Assignment1/5000-8.txt.bz2
hdfs dfs -rm -f -r Assignment1/132.txt.bz2
hdfs dfs -rm -f -r Assignment1/1661-8.txt.bz2
hdfs dfs -rm -f -r Assignment1/972.txt.bz2
hdfs dfs -rm -f -r Assignment1/19699.txt.bz2


