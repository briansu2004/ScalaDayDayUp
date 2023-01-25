package com.sutek.ziosparktest.config

import zio.config._
import zio.config.magnolia._
import zio.config.typesafe.TypesafeConfigSource

final case class MyConfig(
                          sourceCsvFilePath: String //Refined[String, NonEmpty]
                         )

object ConfigInMem {
  private val confLayer = TypesafeConfigSource.fromHoconString("sourceCsvFilePath = \"src/main/resources/data.csv\"").toLayer
  //private val confLayer = TypesafeConfigSource.fromHoconFile(new File("c:/tmp/myconfig.conf")).toLayer

  // private val desc = descriptor[MyConfig]
  private val desc : ConfigDescriptor[MyConfig] = descriptor[MyConfig]

  val live = confLayer >>> configLayer(ConfigInMem.desc)
}
