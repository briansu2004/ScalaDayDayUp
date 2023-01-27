package com.sutek.ziosparktest.config

import zio.config._
import zio.config.magnolia._
import zio.config.typesafe.TypesafeConfigSource
import com.sutek.ziosparktest.io.ReadFile._
import zio.{Scope, ZLayer}

final case class MyConfig(
                          sourceCsvFilePath: String //Refined[String, NonEmpty]
                         )

object ConfigInMem {
  //private val confLayer = TypesafeConfigSource.fromHoconFile(new File("c:/tmp/myconfig.conf")).toLayer
  val hocon =
    s"""
      {
         sourceCsvFilePath : src/main/resources/data.csv
      }
    """
  private val confLayer = TypesafeConfigSource.fromHoconString(hocon).toLayer
  // readFileLinesStream("c:/tmp/myconfig.conf").runCollect.map(_.mkString("\n"))

  //val test = ZLayer.fromZIO(readResourceFileLines("myconfig.conf"))
  val live =
    ZLayer.fromZIO(readResourceFileLines("myconfig.conf").map(TypesafeConfigSource.fromHoconString)) >>>
      configLayer(descriptor[MyConfig])



  //val live = TypesafeConfigSource.fromHoconString(hocon).toLayer >>> configLayer(descriptor[MyConfig])
}
