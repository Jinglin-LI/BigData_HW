# Assignment1b, part1

# Delete if directory exist
hdfs dfs -rm -r Assignment1b/part1

# Count the number of positive and negative words in the files in the Assignment1/part1 and store into Assignment1b/part1
hadoop jar WordCount_PosiNega-0.0.1-SNAPSHOT.jar WordCount_PosiNega.WordCount_PosiNega.CountPosiNega hdfs://cshadoop1/user/jxl163530/Assignment1/part1 hdfs://cshadoop1/user/jxl163530/Assignment1b/part1

# Display the result
hdfs dfs -cat Assignment1b/part1/part-r-00000



######### The result shows ####################
#     Number of negative words:       20160   #
#     Number of positive words:       27763   #
###############################################


