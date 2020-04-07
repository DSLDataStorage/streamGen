import java.io.PrintWriter
import java.net.ServerSocket
import java.text.{SimpleDateFormat, DateFormat}
import java.util.Date

import org.apache.spark.SparkConf
import org.apache.spark.SparkContext
import org.apache.spark.SparkContext._
import org.apache.spark.streaming.{Seconds, StreamingContext}

import scala.util.Random
import scala.io.Source

object streamGenRandom_v1{
	val listener = new ServerSocket(9999)
	val socket = listener.accept()
	val out = new PrintWriter(socket.getOutputStream(), true)
	
	def main(args: Array[String]){
		val conf = new SparkConf().setMaster("local[4]").setAppName("streamGenRandom")
		val sc = new SparkContext(conf)
		val num = args(0).toString


//		val file_name = "/home/user/Desktop/hongji/ref/review_data/Musical_Instruments_"+num+".json"
		val file_name = "/home/user/Desktop/hongji/ref/SF_"+num+"k.json"
		val mongoData = sc.textFile(file_name)
                mongoData.cache()
		val dataCount = mongoData.count().toInt
		val indexKey = mongoData.zipWithIndex.map{ case(k,v) => (v,k)}
		val randomValue = scala.util.Random


		println("Got client connected from : "+socket.getInetAddress)
		
		while(true){
			val num = randomValue.nextInt(dataCount-1)+1
			val sst = indexKey.lookup(num)(0)
			out.write(sst)
			out.write("\n")
			out.flush()
			//Thread.sleep(25)
                        Thread.sleep(5)
		}


	socket.close()
        mongoData.unpersist()

	}

}


// stream data rate are changed
// data rate is increased slowly
/*
object streamGenRandom{ 
	val listener = new ServerSocket(9999)
	val socket = listener.accept()
	val out = new PrintWriter(socket.getOutputStream(), true)
	
	def main(args: Array[String]){
		val conf = new SparkConf().setMaster("local[4]").setAppName("streamGenRandom")
		val sc = new SparkContext(conf)
		val num = args(0).toString


		val file_name = "/home/user/Desktop/hongji/ref/review_data/Musical_Instruments_"+num+".json"
		val mongoData = sc.textFile(file_name)
		val dataCount = mongoData.count().toInt
		val indexKey = mongoData.zipWithIndex.map{ case(k,v) => (v,k)}
		val randomValue = scala.util.Random


		println("Got client connected from : "+socket.getInetAddress)
		var cnt = 0
		var sleep = 300
		while(true){
			cnt = cnt + 1
			val num = randomValue.nextInt(dataCount-1)+1
			val sst = indexKey.lookup(num)(0)
			out.write(sst)
			out.write("\n")
			out.flush()
			if(cnt % 100 == 0 ) sleep = sleep-10 
			Thread.sleep(sleep)			
		}


	socket.close()

	}

}
*/
/*
// stream data rate are changed
// data rate is increased fast
object streamGenRandom{ 
	val listener = new ServerSocket(9999)
	val socket = listener.accept()
	val out = new PrintWriter(socket.getOutputStream(), true)
	
	def main(args: Array[String]){
		val conf = new SparkConf().setMaster("local[4]").setAppName("streamGenRandom")
		val sc = new SparkContext(conf)
		val num = args(0).toString


		val file_name = "/home/user/Desktop/hongji/ref/review_data/Musical_Instruments_"+num+".json"
		val mongoData = sc.textFile(file_name)
		val dataCount = mongoData.count().toInt
		val indexKey = mongoData.zipWithIndex.map{ case(k,v) => (v,k)}
		val randomValue = scala.util.Random


		println("Got client connected from : "+socket.getInetAddress)
		var cnt = 0
		var sleep = 300
		while(true){
			cnt = cnt + 1
			val num = randomValue.nextInt(dataCount-1)+1
			val sst = indexKey.lookup(num)(0)
			out.write(sst)
			out.write("\n")
			out.flush()
			if(cnt > 1000 ) {
				if(cnt % 200 == 0 ) sleep = sleep-50 
			}
			
			Thread.sleep(sleep)			
		}


	socket.close()

	}

}
*/

