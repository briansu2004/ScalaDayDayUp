package com.sutek.ziosparktest.config

import zio.config._
import zio.config.magnolia._
import zio.config.typesafe.TypesafeConfigSource

import java.io.File

final case class MyConfig(
                          sourceCsvFilePath: String //Refined[String, NonEmpty]
                         )

object ConfigInMem {
  //private val confLayer = TypesafeConfigSource.fromHoconString("enableAlert = true").toLayer
  private val confLayer = TypesafeConfigSource.fromHoconFile(new File("C:\\Users\\zhiwi\\code\\ScalaDayDayUp\\zio-spark-test\\src\\main\\resources\\myconfig.conf")).toLayer

  // private val desc = descriptor[MyConfig]
  private val desc : ConfigDescriptor[MyConfig] = descriptor[MyConfig]

  val live = confLayer >>> configLayer(ConfigInMem.desc)
}
