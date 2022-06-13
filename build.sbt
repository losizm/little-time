organization := "com.github.losizm"
name         := "little-time"
version      := "4.0.0"
description  := "The Scala library that provides extension methods for java.time"
homepage     := Some(url("https://github.com/losizm/little-time"))
licenses     := List("Apache License, Version 2" -> url("http://www.apache.org/licenses/LICENSE-2.0.txt"))

versionScheme := Some("early-semver")

scalaVersion := "3.1.2"

scalacOptions := Seq("-deprecation", "-feature", "-new-syntax", "-Werror", "-Yno-experimental")

Compile / doc / scalacOptions := Seq(
  "-project", name.value,
  "-project-version", version.value
)


libraryDependencies += "org.scalatest" %% "scalatest" % "3.2.12" % "test"

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
