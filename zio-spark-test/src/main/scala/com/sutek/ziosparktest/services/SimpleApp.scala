package com.sutek.ziosparktest.services

import org.apache.spark.sql.Row
import zio._
import zio.spark.experimental
import zio.spark.experimental.Pipeline
import zio.spark.parameter._
import zio.spark.sql._
import zio.spark.sql.implicits._

object SimpleApp extends ZIOAppDefault {
  import zio.spark.sql.TryAnalysis.syntax.throwAnalysisException

  final case class Person(name: String, age: Int)

  val filePath: String = "src/main/resources/data.csv"

  def read: SIO[DataFrame] = SparkSession.read.schema[Person].withHeader.withDelimiter(";").csv(filePath)
  def transform(inputDs: DataFrame): Dataset[Person] = inputDs.as[Person]

  def output(transformedDs: Dataset[Person]): Task[Option[Person]] = transformedDs.headOption

  val pipeline: Pipeline[Row, Person, Option[Person]] = experimental.Pipeline(read, transform, output)

  val job: ZIO[SparkSession, Throwable, Unit] = {
    for {
      maybePeople <- pipeline.run
      _ <-
        maybePeople match {
          case None => Console.printLine("There is nobody :(.")
          case Some(p) => Console.printLine(s"The first person's name is ${p.name}.")
        }
    } yield ()
  }

  // Use "yarn" in Cloud / Server side
  // User "localAllNodes" in local side and test code
  private val session = SparkSession.builder.master(yarn).appName("app").asLayer

  override def run: ZIO[ZIOAppArgs, Any, Any] = job.provide(session)
}
