spark-shell --master yarn

val review = sc.textFile("/yelp/review/review.csv").map(a => a.split('^')).map(a => (a(1), 1)).reduceByKey((a,b) => a + b).sortBy(a => -a._2)

val user = sc.textFile("/yelp/user/user.csv").map(a => a.split('^')).map(a => (a(0), (a(0), a(1))))

val join = user.join(sc.parallelize(review.take(10)))

val result = join.map(a => a._2._1)

result.collect

result.saveAsTextFile("Assignment3/Q4")