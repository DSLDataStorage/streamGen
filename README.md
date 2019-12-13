# Streaming data Generator


## Build And Install
After downloading the source code, enter into the directory, and run the script to build the code by using sbt
```shell
$sbt clean assembly 
$./run.sh (streaming set number) (isRecompile) (class name) 
```
ex)./run.sh 4 1 streamGen
run streamGen class and recompile source code using sbt
If the streaming set is SF_?k.json , we can use SF_4k.json

## Use Guide
### Configuration
```
def main(args: Array[String]){
          val conf = new SparkConf().setMaster("local[4]").setAppName("streamGenRandom") // class name
          val sc = new SparkContext(conf)
          val num = args(0).toString

          // we can change the stream source file set and location 
          val file_name = "/home/user/Desktop/hongji/ref/SF_"+num+"k.json"
          val mongoData = sc.textFile(file_name)
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
                 Thread.sleep(2) // we can change sleep parameter like 10, 100 ( ms ) 
          }


```


