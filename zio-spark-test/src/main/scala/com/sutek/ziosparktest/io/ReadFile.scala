package com.sutek.ziosparktest.io

import zio._
import zio.stream.ZStream

import scala.io.Source

object ReadFile {
  val readFileLinesStream = (filePath: String) => ZStream.fromIterableZIO(
    ZIO.fromAutoCloseable(ZIO.attemptBlockingIO(Source.fromFile(filePath))).map(_.getLines().iterator.to(Iterable))
  )

  val readFileLines = (filePath: String) =>
    ZIO.fromAutoCloseable(ZIO.attemptBlockingIO(Source.fromFile(filePath))).map(_.getLines()).map(_.mkString("\n"))
}
