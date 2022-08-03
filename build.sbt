name := "mvc-java-play-framework"
organization := "com.myzonesoft.todo.mvc"

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayJava, PlayEbean)

scalaVersion := "2.13.8"

libraryDependencies ++= Seq(
  guice,
  "com.google.inject"            % "guice"                % "5.1.0",
  "com.google.inject.extensions" % "guice-assistedinject" % "5.1.0",
  jdbc,
  javaJdbc,
  //Configure Database driver dependency
  "com.h2database" % "h2" % "2.1.212"
)


