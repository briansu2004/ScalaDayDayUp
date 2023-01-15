package com.sutek.ziosparktest.services

import com.sutek.ziopsparktest.config.ConfigInMem
import com.sutek.ziosparktest.services.SimpleApp._
import zio.ZIO
import zio.spark.parameter.localAllNodes
import zio.spark.sql.SparkSession
import zio.test.{ZIOSpecDefault, assertTrue}

object SimpleAppTest extends ZIOSpecDefault {

  def spec = suite("SimpleAppSpec")(
    test("SimpleApp works") {
      val session = SparkSession.builder.master(localAllNodes).appName("app").asLayer
      for {
        myFilePath <- job.provide(session, ConfigInMem.live)
      } yield {
        assertTrue(myFilePath.equals("src/main/resources"))
      }
    })
}
