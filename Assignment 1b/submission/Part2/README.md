# Delete if directory exist.
hdfs dfs -rm -r Assignment1b/part2

# Process files and cound POS of texts in Assignment1/part2
hadoop jar WordCount_POS-0.0.1-SNAPSHOT.jar WordCount_POS.WordCount_POS.CountPOS hdfs://cshadoop1/user/jxl163530/Assignment1/part2 hdfs://cshadoop1/user/jxl163530/Assignment1b/part2

# Display result
hdfs dfs -cat hdfs://cshadoop1/user/jxl163530/Assignment1b/part2/result.txt
hdfs dfs -cat Assignment1b/part2/part-r-00000

