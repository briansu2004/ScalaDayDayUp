val projectName = IO.readLines(new File("PROJECT_NAME")).head
val v = IO.readLines(new File("VERSION")).head

lazy val rootSettings = Seq(
  organization := "com.sutek",
  scalaVersion := "2.13.8",
  version      := v
)

lazy val sparkVersion = "3.3.1"

lazy val root = (project in file("."))
  .settings(
    name := projectName,
    rootSettings,
    libraryDependencies ++= Seq(
      "io.univalence" %% "zio-spark" % "0.9.2",
      // https://mvnrepository.com/artifact/org.apache.spark/spark-core
      "org.apache.spark" %% "spark-core" % sparkVersion,
      // https://mvnrepository.com/artifact/org.apache.spark/spark-sql
      "org.apache.spark" %% "spark-sql"    % sparkVersion,
      // https://www.scalatest.org/

        // Optional Dependency with magnolia module (Auto derivation)
      "dev.zio" %% "zio-config-magnolia" % "3.0.7",

      // Optional Dependency with refined module (Integration with refined library)
      "dev.zio" %% "zio-config-refined" % "3.0.7",

      // Optional Dependency with typesafe module (HOCON/Json source)
       "dev.zio" %% "zio-config-typesafe" % "3.0.7",

      // Optional Dependency with yaml module (Yaml source)
      "dev.zio" %% "zio-config-yaml" % "3.0.7",

      // Optional Dependency for a random generation of a config
      "dev.zio" %% "zio-config-gen" % "3.0.7",

      "org.scalatest" %% "scalatest" % "3.2.14" % Test,
      "dev.zio" %% "zio-test" % "2.0.5" % Test,
      "dev.zio" %% "zio-test-sbt" % "2.0.5" % Test
    )
  )

