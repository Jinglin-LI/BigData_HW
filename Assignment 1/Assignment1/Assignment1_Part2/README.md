# The part2 of Assignment1 contains source code of ZipDecompressor, DecompressZip-0.0.1-SNAPSHOT.jar and README.md
# The linear text we chose is "GloWbE: 2.1 mw" on the website provided. 

# 0. Delete Assignment1/part2 if exists

hdfs dfs -rm -r Assignment1/part2

# 1. Copy DecompressZip-0.0.1-SNAPSHOT.jar into cluster and run the command to download from URL, decompress the zip file and upload into cshadoop1:

hadoop jar DecompressZip-0.0.1-SNAPSHOT.jar DecompressZip.DecompressZip.ZipDecompressor https://corpus.byu.edu/glowbetext/samples/text.zip hdfs://cshadoop1/user/jxl163530/Assignment1/part2


# 2. Desplay the content of Assignment1/part2:
hdfs dfs -ls Assignment1/part2


