package com.sutek.ziosparktest.services

import com.sutek.ziosparktest.config.ConfigInMem
import com.sutek.ziosparktest.services.SimpleApp._
import zio.Scope
import zio.config._
import zio.config.magnolia._
import zio.config.typesafe._
import zio.spark.parameter.localAllNodes
import zio.spark.sql.SparkSession
import zio.test.{ZIOSpecDefault, assertTrue}

object SimpleAppTest extends ZIOSpecDefault {
  def spec = suite("SimpleAppSpec")(
    test("SimpleApp works") {
      val session = SparkSession.builder.master(localAllNodes).appName("app").asLayer
      for {
        _ <- job.provide(session, ConfigInMem.live, Scope.default)
      } yield {
        assertTrue(0 == 1 - 1)
      }
    })
}
