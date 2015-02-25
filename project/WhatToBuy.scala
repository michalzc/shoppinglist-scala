import net.virtualvoid.sbt.graph.Plugin.graphSettings
import sbt.Keys._
import sbt._
import spray.revolver.RevolverPlugin.Revolver

object WhatToBuy extends Build {

  lazy val projectSettings =
    super.settings ++
      Revolver.settings ++
      graphSettings ++
      Seq(
        name := "What To Buy",
        organization := "michalz",
        version := "0.1-SNAPSHOT",
        scalaVersion := "2.11.5",
        mainClass in(Compile, run) := Some("michalz.shoppinglist.ShoppingListApp"),
        scalacOptions ++= Seq(
          "-feature",
          "-deprecation",
          "-explaintypes",
          "-target:jvm-1.8",
          "-unchecked",
          "-encoding", "utf-8",
          "-Xfatal-warnings"
        ),
        unmanagedResourceDirectories in Compile += file("src/main/webapp"),
        libraryDependencies ++= Seq(
          "io.spray" %% "spray-can" % "1.3.2",
          "io.spray" %% "spray-routing" % "1.3.2",
          "org.json4s" %% "json4s-jackson" % "3.2.10",
          "com.typesafe.akka" %% "akka-actor" % "2.3.9",
          "com.typesafe.akka" %% "akka-slf4j" % "2.3.9",
          "org.slf4j" % "slf4j-api" % "1.7.10",
          "org.apache.logging.log4j" % "log4j-api" % "2.1",
          "org.apache.logging.log4j" % "log4j-core" % "2.1",
          "org.apache.logging.log4j" % "log4j-slf4j-impl" % "2.1",
          "org.reactivemongo" %% "reactivemongo" % "0.10.5.0.akka23",

          //test dependencies
          "io.spray" %% "spray-testkit" % "1.3.2" % "test",
          "com.typesafe.akka" %% "akka-testkit" % "2.3.9" % "test",
          "junit" % "junit" % "4.12" % "test",
          "org.mockito" % "mockito-all" % "1.10.19" % "test"
        ),
        resolvers ++= Seq(
          "Sonatype Releases" at "http://repo.typesafe.com/typesafe/releases/"
        ),
        dependencyOverrides += "org.scala-lang" % "scala-compiler" % scalaVersion.value


      )


  lazy val shoppingList = Project(
    id = "shopping-list",
    base = file("."),
    settings = projectSettings
  )
}

// vim: set ts=4 sw=4 et:
