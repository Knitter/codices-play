ThisBuild / scalaVersion := "3.3.1"

ThisBuild / version := "1.0-SNAPSHOT"

lazy val root = (project in file("."))
  .enablePlugins(PlayScala)
  .settings(
    name := """codices-play""",
    libraryDependencies ++= Seq(
      guice,
      jdbc,
      "org.playframework.anorm" %% "anorm" % "2.7.0",
      "org.xerial" % "sqlite-jdbc" % "3.45.1.0"
      //"com.microsoft.sqlserver" % "mssql-jdbc" % "11.2.3.jre17",
      //"org.postgresql" % "postgresql" % "42.7.1",
    )
  )