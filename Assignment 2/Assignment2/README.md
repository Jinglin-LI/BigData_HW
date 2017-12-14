############################### Q1 ###################################
# 1. Run the program: 
hadoop jar CountYelp-0.0.1-SNAPSHOT.jar CountYelp.CountYelp.CountYelpBusiness /yelp/business/business.csv Assignment2/Q1

# 2. Show the result:
hdfs dfs -cat Assignment2/Q1/part-r-00000


############################### Q2 ###################################
# 1. Run the program:  
hadoop jar CountYelp-0.0.1-SNAPSHOT.jar CountYelp.CountYelp.CountYelp_NYRestaurant /yelp/business/business.csv Assignment2/Q2

# 2. Show the result: 
hdfs dfs -cat Assignment2/Q2/part-r-00000


############################### Q3 ###################################
# 1. Run the program: 
hadoop jar CountYelp-0.0.1-SNAPSHOT.jar CountYelp.CountYelp.CountYelp_Top10ZipCode /yelp/business/business.csv Assignment2/Q3

# 2. Show the result:
hdfs dfs -cat Assignment2/Q3/part-r-00000

# 3. The result is: 

98105   477
02138   443
48104   428
19104   408
14850   404
90024   391
78705   362
94704   361
91711   349
92037   347

############################### Q4 ###################################
# 1. Run the program: 
hadoop jar CountYelp-0.0.1-SNAPSHOT.jar CountYelp.CountYelp.CountYelp_Top10Rating /yelp/review/review.csv Assignment2/Q4

# 2. Show the result:
hdfs dfs -cat Assignment2/Q4/part-r-00000

# 3. The result is:
as78VzatRI6dtQ2UOAu_vg  5.0
Lj12HPjpWrTGlrdtXevGXg  5.0
W4Fds51jY8ieOSEGhmVulA  5.0
xsmqDjeBD6zTSZB9kXO9nw  5.0
RD8nq2qTp2cVVC7Rtrhh-w  5.0
eBx35HFfnlOdI8Z33tfi1g  5.0
Aty5bm84NvuwsgFCk3EDqw  5.0
cXjr3JfZuNvKLC8JWOPE2Q  5.0
00rrqw4O0Flfatd0VV6QAg  5.0
g-d0cQi8706YG3tVQwwVUw  5.0


############################### Q5 ###################################
# 1. Run the program: 
hadoop jar CountYelp-0.0.1-SNAPSHOT.jar CountYelp.CountYelp.CountYelp_LowestTop10Rating /yelp/review/review.csv Assignment2/Q5

# 2. Show the result:
hdfs dfs -cat Assignment2/Q5/part-r-00000

# 3. The result is:
nlC4PLy6JzACltMB3dYhtg  1.0
Ue2Wajj5-yCo-EB-g95vOQ  1.0
iH_PCm07gWwjokJsh0h6bg  1.0
ndb5hbNa97bghHQPn_TwNw  1.0
x06iP8Y7h-nyYJVanews5A  1.0
WiUGrkuPstg7Jfq_n5Vjog  1.0
XimJ342n6UGKDFCnhF_mqQ  1.0
Dpak23eEJ-OG-kVX_5hOLA  1.0
LpVQCCcynziDA74fkLWslw  1.0
a8-gpi6C9uNuRGvc8hEVwA  1.0
