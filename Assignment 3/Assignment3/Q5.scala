spark-shell --master yarn

val business = sc.textFile("/yelp/business/business.csv").map(a => a.split('^')).filter(a => a(1).contains("TX")).map(a => (a(0), ""))

val review = sc.textFile("/yelp/review/review.csv").map(a => a.split('^')).map(a => (a(2), 1)).reduceByKey((a, b) => (a + b))

val join = review.join(business)

val result = join.map(a => (a._1, a._2._1))

result.collect

result.saveAsTextFile("Assignment3/Q5")