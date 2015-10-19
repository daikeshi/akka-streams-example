name := "akka-reddit-wordcount"
 
version := "0.1.0 "
 
scalaVersion := "2.11.7"

val akkaVersion = "2.3.7"

libraryDependencies ++= Seq(
  "com.typesafe.akka" %% "akka-actor" % akkaVersion,
  "com.typesafe.akka" %% "akka-cluster" % akkaVersion,
  "com.typesafe.akka" %% "akka-slf4j" % akkaVersion,
  "com.typesafe.akka" %% "akka-agent" % akkaVersion,
  "com.typesafe.akka" % "akka-stream-experimental_2.11" % "1.0-M4",
  "net.databinder.dispatch" %% "dispatch-core" % "0.11.2",
  "net.databinder.dispatch" %% "dispatch-json4s-native" % "0.11.2",
  "com.google.protobuf" % "protobuf-java" % "2.5.0",
  "com.fasterxml.jackson.module" %% "jackson-module-scala" % "2.4.2",
  "org.scalaz" %% "scalaz-core" % "7.1.0" ,
  "joda-time" % "joda-time"    % "2.8.2",
  "org.joda"  % "joda-convert" % "1.8.1",
  "org.joda" % "joda-money" % "0.10.0",
  "org.json4s" %% "json4s-ext" % "3.3.0"
)

scalacOptions ++= Seq("-feature")
