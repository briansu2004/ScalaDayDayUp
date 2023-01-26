package com.sutek.ziopsparktest.config

import zio.config._
import zio.config.magnolia._
import zio.config.typesafe.{TypesafeConfigSource, _}

final case class MyConfig(
                          sourceCsvFilePath: String //Refined[String, NonEmpty]
                         )

object ConfigInMem {
  val hocon =
    s"""
      {
         sourceCsvFilePath : src/main/resources
      }
    """
  private val confLayer = TypesafeConfigSource.fromHoconString(hocon).toLayer

  private val desc : ConfigDescriptor[MyConfig] = descriptor[MyConfig]

  val live = confLayer >>> configLayer(ConfigInMem.desc)
}
