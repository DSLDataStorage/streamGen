lazy val root = (project in file(".")).settings(
name := "Dima-DS",
version := "1.0",
scalaVersion := "2.11.0"
)
libraryDependencies ++= Seq(
	"org.apache.spark" %% "spark-core" % "2.1.0" % "provided",
	"org.apache.spark" %% "spark-streaming" % "2.0.0" % "provided",
	"org.apache.spark" %% "spark-sql" % "2.2.2",
)

mergeStrategy in assembly := {
	case PathList("META-INF", xs @ _*) => MergeStrategy.discard
	case x => MergeStrategy.first
}

javaOptions in run ++= Seq(
	"-Dlog4j.debug=true",
	"-Dlog4j.configuration=log4j.properties"
)

