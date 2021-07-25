organization := "com.github.losizm"
name         := "little-time"
version      := "1.0.0-SNAPSHOT"
description  := "The Scala library that provides extension methods to java.time"
homepage     := Some(url("https://github.com/losizm/little-time"))
licenses     := List("Apache License, Version 2" -> url("http://www.apache.org/licenses/LICENSE-2.0.txt"))

scalaVersion := "3.0.1"

scalacOptions ++= Seq("-deprecation", "-feature", "-new-syntax", "-Yno-experimental")

Compile / doc / scalacOptions ++= Seq("-project-version", version.value)

libraryDependencies += "org.scalatest" %% "scalatest" % "3.2.9" % "test"

developers := List(
  Developer(
    id    = "losizm",
    name  = "Carlos Conyers",
    email = "carlos.conyers@hotmail.com",
    url   = url("https://github.com/losizm"))
)

scmInfo := Some(
  ScmInfo(
    url("https://github.com/losizm/little-time"),
    "scm:git@github.com:losizm/little-time.git")
)

publishTo := {
  val nexus = "https://oss.sonatype.org"

  isSnapshot.value match {
    case true  => Some("snaphsots" at s"$nexus/content/repositories/snapshots")
    case false => Some("releases" at s"$nexus/service/local/staging/deploy/maven2")
  }
}

publishMavenStyle := true

pomIncludeRepository := { _ => false }
