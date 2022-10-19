package com._4paradigm.scala

import org.apache.spark.rdd.RDD
import org.apache.spark.sql.{DataFrame, SQLContext, SparkSession}
import org.apache.spark.{SparkConf, SparkContext}

import java.text.SimpleDateFormat
import java.util.{Properties, TimeZone}


object test {
  def f2(path:String,shijian:String): String = {
    val sparkConf = new SparkConf().setMaster("local[6]").setAppName("spark sql test");
    val sc = new SparkContext(sparkConf);
    val sqlContext = new SQLContext(sc);
    val spark=  SparkSession.builder()
      .appName("Spark Sql basic example")
      .master("local[6]")
      .getOrCreate()
    //我们只需要提供Driver的url，需要查询的表名，以及连接表相关属性properties
    val url = "jdbc:mysql://172.25.3.102/wuliaolishi?user=root&password=Admin@4pd";
    val prop = new Properties();
    val df = sqlContext.read.jdbc(url, "single", prop);
    var format = new SimpleDateFormat("yyyyMMddHHmmss");
    format.setTimeZone(TimeZone.getTimeZone("GMT+8"));
    import spark.implicits._        //这是一行代码,做隐式转换 spark是上边定义的变量

    val rdd1: RDD[String] =  spark.sparkContext.textFile(path)

    val frame = rdd1.map(xx => xx.split("\\|"))
      .filter(xxx => xxx.length == 7)
      .filter(xxx=>xxx(3).equals("1"))
      .map(p => {
        (p(0), p(1), p(2), p(3), p(4), p(5), p(6))
      }).toDF("name", "age", "1", "2", "3", "4", "id")
      frame.createOrReplaceTempView("t_user")

    df.createOrReplaceTempView("single")
    val sqlDF2: DataFrame = spark.sql("select t_user.*,single.servercode servercode from t_user left join single on t_user.id=single.programcode")
    sqlDF2.coalesce(1).write.option("timestampFormat", "yyyy/MM/dd HH:mm:ss ZZ").csv("/tmp/"+shijian)
    val l = sqlDF2.count()
    println(l)

    try {
      sc.stop()
      spark.stop()
    } catch {
      case x =>
    }
    return "/tmp/"+shijian

  }

}
