name := "akka-reddit-wordcount"
 
version := "0.1.0 "
 
scalaVersion := "2.11.7"

val akkaVersion = "2.3.7"

scalikejdbcSettings

libraryDependencies ++= Seq(
  "com.typesafe.akka" %% "akka-actor" % akkaVersion,
  "com.typesafe.akka" %% "akka-cluster" % akkaVersion,
  "com.typesafe.akka" %% "akka-slf4j" % akkaVersion,
  "com.typesafe.akka" %% "akka-agent" % akkaVersion,
  "ch.qos.logback" % "logback-classic" % "1.1.3",
  "net.databinder.dispatch" %% "dispatch-core" % "0.11.2",
  "org.scalikejdbc" %% "scalikejdbc" % "2.2.9",
  "org.scalikejdbc" %% "scalikejdbc-config"  % "2.2.9",
  "org.postgresql" % "postgresql" % "9.4-1201-jdbc41",
  "net.databinder.dispatch" %% "dispatch-json4s-native" % "0.11.2",
  "com.fasterxml.jackson.module" %% "jackson-module-scala" % "2.4.2",
  "joda-time" % "joda-time"    % "2.8.2",
  "org.joda"  % "joda-convert" % "1.8.1",
  "org.joda" % "joda-money" % "0.10.0",
  "org.json4s" %% "json4s-ext" % "3.3.0"
)

scalacOptions ++= Seq("-feature")
