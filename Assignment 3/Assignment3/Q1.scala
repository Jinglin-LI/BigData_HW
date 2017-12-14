
spark-shell --master yarn

val business = sc.textFile("/yelp/business/business.csv").map(a => a.split('^')).filter(a => a.length == 3).map(a => (a(0), (a(0), a(1), a(2))))

val review = sc.textFile("/yelp/review/review.csv").map(a => a.split('^')).filter(a => a.length == 4).map(a => (a(2), (a(3).toDouble, 1))).reduceByKey((a,b) => (a._1 + b._1, a._2 + b._2)).map(a => (a._1, a._2._1/a._2._2)).sortBy(a => -a._2)

val join = business.join(sc.parallelize(review.take(10)))

val result = join.map(a => (a._2._1, a._2._2))

result.collect

result.saveAsTextFile("Assignment3/Q1")




/*
result: 


{cs6360:~} hdfs dfs -cat Assignment3/Q1/part-00001
((pSNEDaUGljLSHBZz5JNpuA,8935 Towne Centre DrSuite 105San Diego, CA 92122,List(Active Life, Golf)),5.0)
((Vv51G8x0tmJrJUjd687Icw,200 Medical PlazaSte 430WestwoodLos Angeles, CA 90095,List(Doctors, Health and Medical, Obstetricians and Gynecologists)),5.0)
((gqlCplBbmlMsGFZLNWd5wg,183 Angell StCollege HillProvidence, RI 02906,List(Fashion, Shopping)),5.0)
((2LzZkqb5PLf0zzUVa4O9Gg,1821 N Fordham BlvdSte 1Chapel Hill, NC 27514,List(Hair Salons, Beauty and Spas)),5.0)
((aVbVhFMQOyoIXsvEiOBghQ,4507 Brooklyn Ave NEUniversity DistrictSeattle, WA 98105,List(Event Planning & Services, Party & Event Planning, Caterers)),5.0)
((iIw-ahkNV8c_xPCGesMfoA,304 W Weaver StSte 203Carrboro, NC 27510,List(Active Life, Pilates, Fitness & Instruction)),5.0)
((P9O4_6XLsft3-aBQtRoUrg,2422 Robinhood StWest UniversityHouston, TX 77005,List(Veterinarians, Pets)),5.0)
((pqKqkaM46QVqpuIKArXqcQ,29 Saratoga AvenueWaterford, NY 12188,List(Hair Salons, Beauty and Spas)),5.0)
((biO9p_7oUtoS6ONFFnm4ig,937 E Green StPasadenaPasadena, CA 91106,List(Shopping, Drugstores)),5.0)
((27b5Ah4h47s9Zvmw-RiEuQ,1016 Howell Mill RdSte 3207Westside / Home ParkAtlanta, GA 30318,List(Used, Vintage & Consignment, Fashion, Shopping)),5.0)

*/