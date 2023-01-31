package com.sutek.ziosparktest.config


import com.sutek.ziosparktest.io.ReadFile.readFileLines
import eu.timepit.refined.api.Refined
import zio._
import zio.config._
import zio.config.typesafe.TypesafeConfigSource
import zio.config.refined.refine
import eu.timepit.refined._
import string._
import zio.config.refined._
import zio.config.magnolia.Descriptor.descriptor

final case class MyConfig(
                          sourceCsvFilePath: Refined[String, Uri]
                         )

object ConfigInMem {
  val live = ZLayer.fromZIO(readFileLines("src/main/resources/myconfig.conf")).flatMap(
    x => TypesafeConfigSource.fromHoconString(x.get).toLayer
  ) >>> configLayer(descriptor[MyConfig])
}
