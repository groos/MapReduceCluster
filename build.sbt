val akkaVersion = "2.4.4"

lazy val root = (project in file(".")).
settings (
  name := "MapReduce",
  version := "1.0",
  scalaVersion := "2.11.8",
  scalacOptions in ThisBuild ++= Seq("-unchecked", "-deprecation"),
  resolvers += "Typesafe Repository" at "http://repo.typesafe.com/typesafe/releases/",
  libraryDependencies += "com.typesafe.akka" %% "akka-actor" % "2.4.3",
  libraryDependencies ++= Seq("com.typesafe.akka" %% "akka-cluster" % "2.3.3"),
  libraryDependencies ++= Seq(
      "com.typesafe.akka" %% "akka-actor" % akkaVersion,
      "com.typesafe.akka" %% "akka-remote" % akkaVersion,
      "com.typesafe.akka" %% "akka-cluster" % akkaVersion,
      "com.typesafe.akka" %% "akka-cluster-metrics" % akkaVersion,
      "com.typesafe.akka" %% "akka-cluster-tools" % akkaVersion,
      "com.typesafe.akka" %% "akka-multi-node-testkit" % akkaVersion,
      "org.scalatest" %% "scalatest" % "2.2.1" % "test",
      "io.kamon" % "sigar-loader" % "1.6.6-rev002"),
  javaOptions in run ++= Seq(
  "-Xms128m", "-Xmx1024m", "-Djava.library.path=./target/native"),
  Keys.fork in run := true,
  mainClass in (Compile, run) := Some("MapReduce.MapReduceApp")
)
