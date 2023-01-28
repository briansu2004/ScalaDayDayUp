package com.sutek.ziosparktest.config

import com.sutek.ziosparktest.io.ReadFile._
import zio.config._
import zio.config.magnolia._
import zio.config.typesafe.TypesafeConfigSource

import java.io.File

final case class MyConfig(
                          sourceCsvFilePath: String //Refined[String, NonEmpty]
                         )

object ConfigInMem {
  // private val confLayer = TypesafeConfigSource.fromHoconFile(new File("c:/tmp/myconfig.conf")).toLayer

  // readFileLinesStream("c:/tmp/myconfig.conf").runCollect.map(_.mkString("\n"))

  //  val live = ZLayer.fromZIO(readFileLines("src/main/resources/myconfig.conf").map(TypesafeConfigSource
  //    .fromHoconString)) >>> configLayer(descriptor[MyConfig])
  
  val live = TypesafeConfigSource.fromHoconFile(new File("src/main/resources/myconfig.conf")).toLayer >>> configLayer(descriptor[MyConfig])
}
