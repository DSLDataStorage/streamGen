#!/bin/bash

num=$1 #data number ( default : musical_xx)
com=$2 #dose need to compile?
class=$3
if [ ${com} -eq 1 ];then
	sbt clean assembly
fi


../spark-2.1.0-bin-hadoop2.7/bin/spark-submit --class $class /home/user/Desktop/hongji/streamGen/target/scala-2.11/Dima-DS-assembly-1.0.jar $num > log
