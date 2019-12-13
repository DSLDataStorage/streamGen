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

object streamGen{
	val listener = new ServerSocket(9999)
	val socket = listener.accept()
	val out = new PrintWriter(socket.getOutputStream(), true)
	
	def main(args: Array[String]){
		val conf = new SparkConf().setMaster("local[2]").setAppName("streamGen")
		val sc = new SparkContext(conf)
		val num = args(0).toString

		val file_name = "/home/user/Desktop/hongji/ref/review_data/Musical_Instruments_"+num+".json"
		val mongoData = sc.textFile(file_name)

		println("Got client connected from : "+socket.getInetAddress)

		mongoData.foreach{ s =>
			out.write(s)
			out.write("\n")
			out.flush()
			Thread.sleep(280)
		}
	socket.close()

	}

}

