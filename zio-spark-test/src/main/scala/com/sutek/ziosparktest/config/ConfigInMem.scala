package com.sutek.ziosparktest.config

import zio.config._
import zio.config.magnolia._
import zio.config.typesafe.TypesafeConfigSource
import com.sutek.ziosparktest.io.ReadFile._
import zio.ZLayer

final case class MyConfig(
                          sourceCsvFilePath: String //Refined[String, NonEmpty]
                         )

object ConfigInMem {
  //private val confLayer = TypesafeConfigSource.fromHoconFile(new File("c:/tmp/myconfig.conf")).toLayer

  // readFileLinesStream("c:/tmp/myconfig.conf").runCollect.map(_.mkString("\n"))
  val live = ZLayer.fromZIO(readFileLines("c:/tmp/myconfig.conf").map(TypesafeConfigSource.fromHoconString)) >>> configLayer(descriptor[MyConfig])
}
