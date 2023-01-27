package com.sutek.ziosparktest.services

import com.sutek.ziosparktest.config.{ConfigInMem, MyConfig}
import zio._
import zio.config.getConfig
import zio.spark.experimental
import zio.spark.parameter._
import zio.spark.sql._
import zio.spark.sql.implicits._

object SimpleApp extends ZIOAppDefault {
  import zio.spark.sql.TryAnalysis.syntax.throwAnalysisException

  final case class Person(name: String, age: Int)

  val filePath: String = "src/main/resources/data.csv"

  def read(srcFilePath: String): SIO[DataFrame] = SparkSession.read.schema[Person].withHeader.withDelimiter(";").csv(srcFilePath)
  def transform(inputDs: DataFrame): Dataset[Person] = inputDs.as[Person]

  def output(transformedDs: Dataset[Person]): Task[Dataset[Person]] = ZIO.succeed(transformedDs)

  val job = {
    for {
      config <- getConfig[MyConfig]
      maybePeople <- experimental.Pipeline(read(config.sourceCsvFilePath), transform, output).run
      _ <- maybePeople.foreach(x => println(x))
    } yield {
      config.sourceCsvFilePath
    }
  }

  private val session = SparkSession.builder.master(yarn).appName("app").asLayer

  override def run: ZIO[ZIOAppArgs, Any, Any] = job.provide(session, ConfigInMem.live)
}
