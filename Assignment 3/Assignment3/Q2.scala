val line = Console.readLine()

Matt J

val user = sc.textFile("/yelp/user/user.csv").map(a => a.split('^')).filter(a => a(1).contains(line)).map(a => (a(0), ""))

val review = sc.textFile("/yelp/review/review.csv").map(a => a.split('^')).map(a => (a(1), (a(3).toDouble, 1))).reduceByKey((a, b) => (a._1 + b._1, a._2 + b._2)).map(a => (a._1, a._2._1 / a._2._2))

val join = user.join(review)

val result = join.map(a => (a._1, a._2._2))

result.collect

result.saveAsTextFile("Assignment3/Q2")



