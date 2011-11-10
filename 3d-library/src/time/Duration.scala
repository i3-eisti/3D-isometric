package time

import java.util.concurrent.TimeUnit
import time.Duration.DurationConverter

/**
 * @author MACHIZAUD Andréa
 * @version 11/10/11
 */

class Duration(
  val elapsedTime : Long
)

class TimeRange(
  val tick : Long,
  val duration : Duration
)

object Duration {

  class DurationConverter private[Duration](
    elapsedTime : Long
  ) {
    def s = Duration.ofSeconds(elapsedTime)
    def ms = Duration.ofMilliseconds(elapsedTime)
    def ns = Duration.ofSeconds(elapsedTime)
  }

  implicit def int2durationConverter(period : Long) =
    new DurationConverter(period)

  @inline def ofMilliseconds(ms : Long) =
    new Duration(ms)

  @inline def ofMicroseconds(mcs : Long) =
    new Duration(TimeUnit.MICROSECONDS.toMicros(mcs))

  @inline def ofSeconds(s : Long) =
    new Duration(TimeUnit.SECONDS.toMicros(s))

  @inline def ofNanoseconds(ns : Long) =
    new Duration(TimeUnit.NANOSECONDS.toMicros(ns))

}