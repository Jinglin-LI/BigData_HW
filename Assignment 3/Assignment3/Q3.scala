spark-shell --master yarn

val business = sc.textFile("/yelp/business/business.csv").map(a => a.split('^')).filter(a => a.length == 3).filter(a => a(1).contains("Stanford")).map(a => (a(0), ""))

val review = sc.textFile("/yelp/review/review.csv").map(a => a.split('^')).map(a => (a(2), (a(1), a(3))))

val join = business.join(review)

val result = join.map(a => a._2._2)

result.collect

result.saveAsTextFile("Assignment3/Q3")