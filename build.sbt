name := "little-time"
version := "0.6.1"
organization := "com.github.losizm" 

scalaVersion := "2.13.3"
scalacOptions ++= Seq("-deprecation", "-feature", "-Xcheckinit")

crossScalaVersions := Seq("2.12.12")

Compile / doc / scalacOptions ++= Seq(
  "-doc-title", "little-time",
  "-doc-version", version.value
)

unmanagedSourceDirectories in Compile += {
  (sourceDirectory in Compile).value / s"scala-${scalaBinaryVersion.value}"
}

libraryDependencies += "org.scalatest" %% "scalatest" % "3.2.0" % "test"

scmInfo := Some(
  ScmInfo(
    url("https://github.com/losizm/little-time"),
    "scm:git@github.com:losizm/little-time.git"
  )
)

developers := List(
  Developer(
    id    = "losizm",
    name  = "Carlos Conyers",
    email = "carlos.conyers@hotmail.com",
    url   = url("https://github.com/losizm")
  )
)

description := "The Scala library that provides extension methods to java.time"
licenses := List("Apache License, Version 2" -> url("http://www.apache.org/licenses/LICENSE-2.0.txt"))
homepage := Some(url("https://github.com/losizm/little-time"))

pomIncludeRepository := { _ => false }

publishTo := {
  val nexus = "https://oss.sonatype.org"
  if (isSnapshot.value) Some("snaphsots" at s"$nexus/content/repositories/snapshots")
  else Some("releases" at s"$nexus/service/local/staging/deploy/maven2")
}

publishMavenStyle := true
