name := """profile-service"""
organization := "com.thetiphub"

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayScala)

scalaVersion := "2.11.8"

libraryDependencies ++= Seq(
  filters,
  "org.scalatestplus.play" %% "scalatestplus-play" % "2.0.0" % Test,
  ws,
  "joda-time" % "joda-time" % "2.9.7"
)

// Adds additional packages into Twirl
//TwirlKeys.templateImports += "com.thetiphub.controllers._"

// Adds additional packages into conf/routes
// play.sbt.routes.RoutesKeys.routesImport += "com.thetiphub.binders._"
