/*
 * Copyright 2019 Carlos Conyers
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package little.time

import java.time.{ Duration, LocalTime }

import org.scalatest.FlatSpec

import Implicits._
import TimePrecision._

class LocalTimeTypeSpec extends FlatSpec {
  private val time = LocalTime.parse("12:38:45.123456789")

  "LocalTime" should "have duration added" in {
    assert(time + Duration.ofSeconds(1) == LocalTime.parse("12:38:46.123456789"))
    assert(time + Duration.ofMinutes(2) == LocalTime.parse("12:40:45.123456789"))
    assert(time + Duration.ofHours(31) == LocalTime.parse("19:38:45.123456789"))
    assert(time + Duration.ofMillis(5) == LocalTime.parse("12:38:45.128456789"))
    assert(time + Duration.ofNanos(2) == LocalTime.parse("12:38:45.123456791"))
    assert(time + Duration.ofDays(7) == LocalTime.parse("12:38:45.123456789"))

    assert(time + Duration.ofSeconds(-1) == LocalTime.parse("12:38:44.123456789"))
    assert(time + Duration.ofMinutes(-2) == LocalTime.parse("12:36:45.123456789"))
    assert(time + Duration.ofHours(-31) == LocalTime.parse("05:38:45.123456789"))
    assert(time + Duration.ofMillis(-5) == LocalTime.parse("12:38:45.118456789"))
    assert(time + Duration.ofNanos(-2) == LocalTime.parse("12:38:45.123456787"))
    assert(time + Duration.ofDays(-7) == LocalTime.parse("12:38:45.123456789"))
  }

  it should "have duration subtracted" in {
    assert(time - Duration.ofSeconds(1) == LocalTime.parse("12:38:44.123456789"))
    assert(time - Duration.ofMinutes(2) == LocalTime.parse("12:36:45.123456789"))
    assert(time - Duration.ofHours(31) == LocalTime.parse("05:38:45.123456789"))
    assert(time - Duration.ofMillis(5) == LocalTime.parse("12:38:45.118456789"))
    assert(time - Duration.ofNanos(2) == LocalTime.parse("12:38:45.123456787"))
    assert(time - Duration.ofDays(7) == LocalTime.parse("12:38:45.123456789"))

    assert(time - Duration.ofSeconds(-1) == LocalTime.parse("12:38:46.123456789"))
    assert(time - Duration.ofMinutes(-2) == LocalTime.parse("12:40:45.123456789"))
    assert(time - Duration.ofHours(-31) == LocalTime.parse("19:38:45.123456789"))
    assert(time - Duration.ofMillis(-5) == LocalTime.parse("12:38:45.128456789"))
    assert(time - Duration.ofNanos(-2) == LocalTime.parse("12:38:45.123456791"))
    assert(time - Duration.ofDays(-7) == LocalTime.parse("12:38:45.123456789"))
  }

  it should "be compared to other" in {
    import Ordered.orderingToOrdered
    val other1 = LocalTime.parse("12:38:45")
    val other2 = LocalTime.parse("12:38:45.123456789")

    assert(time > other1)
    assert(time >= other1)
    assert(time != other1)

    assert(other1 < time)
    assert(other1 <= time)
    assert(other1 != time)

    assert(time >= other2)
    assert(time <= other2)
    assert(time == other2)

    assert(other2 >= time)
    assert(other2 <= time)
    assert(other2 == time)
  }

  it should "be greater than other" in {
    val other = LocalTime.parse("12:38:45")
    assert(time.max(other) == time)
    assert(other.min(time) == other)
  }

  it should "be less than other" in {
    val other = LocalTime.parse("15:32:46.789")
    assert(time.max(other) == other)
    assert(other.min(time) == time)
  }

  it should "be truncated to microsecond" in {
    assert(time.atStartOfMicros == LocalTime.parse("12:38:45.123456"))
  }

  it should "be truncated to millisecond" in {
    assert(time.atStartOfMillis == LocalTime.parse("12:38:45.123"))
  }

  it should "be truncated to second" in {
    assert(time.atStartOfSecond == LocalTime.parse("12:38:45"))
  }

  it should "be truncated to minute" in {
    assert(time.atStartOfMinute == LocalTime.parse("12:38"))
  }

  it should "be truncated to hour" in {
    assert(time.atStartOfHour == LocalTime.parse("12:00"))
  }

  it should "be truncated to day" in {
    assert(time.atStartOfDay == LocalTime.parse("00:00"))
  }

  it should "be adjusted to end microsecond" in {
    assert(time.atEndOfMicros(FractionalSeconds(9)) == LocalTime.parse("12:38:45.999999999"))
    assert(time.atEndOfMicros(FractionalSeconds(8)) == LocalTime.parse("12:38:45.99999999"))
    assert(time.atEndOfMicros(FractionalSeconds(7)) == LocalTime.parse("12:38:45.9999999"))
  }

  it should "be adjusted to end millisecond" in {
    assert(time.atEndOfMillis(FractionalSeconds(9)) == LocalTime.parse("12:38:45.999999999"))
    assert(time.atEndOfMillis(FractionalSeconds(8)) == LocalTime.parse("12:38:45.99999999"))
    assert(time.atEndOfMillis(FractionalSeconds(7)) == LocalTime.parse("12:38:45.9999999"))
    assert(time.atEndOfMillis(FractionalSeconds(6)) == LocalTime.parse("12:38:45.999999"))
    assert(time.atEndOfMillis(FractionalSeconds(5)) == LocalTime.parse("12:38:45.99999"))
    assert(time.atEndOfMillis(FractionalSeconds(4)) == LocalTime.parse("12:38:45.9999"))
  }

  it should "be adjusted to end second" in {
    assert(time.atEndOfSecond(FractionalSeconds(9)) == LocalTime.parse("12:38:45.999999999"))
    assert(time.atEndOfSecond(FractionalSeconds(8)) == LocalTime.parse("12:38:45.99999999"))
    assert(time.atEndOfSecond(FractionalSeconds(7)) == LocalTime.parse("12:38:45.9999999"))
    assert(time.atEndOfSecond(FractionalSeconds(6)) == LocalTime.parse("12:38:45.999999"))
    assert(time.atEndOfSecond(FractionalSeconds(5)) == LocalTime.parse("12:38:45.99999"))
    assert(time.atEndOfSecond(FractionalSeconds(4)) == LocalTime.parse("12:38:45.9999"))
    assert(time.atEndOfSecond(FractionalSeconds(3)) == LocalTime.parse("12:38:45.999"))
    assert(time.atEndOfSecond(FractionalSeconds(2)) == LocalTime.parse("12:38:45.99"))
    assert(time.atEndOfSecond(FractionalSeconds(1)) == LocalTime.parse("12:38:45.9"))
  }

  it should "be adjusted to end minute" in {
    assert(time.atEndOfMinute(FractionalSeconds(9)) == LocalTime.parse("12:38:59.999999999"))
    assert(time.atEndOfMinute(FractionalSeconds(8)) == LocalTime.parse("12:38:59.99999999"))
    assert(time.atEndOfMinute(FractionalSeconds(7)) == LocalTime.parse("12:38:59.9999999"))
    assert(time.atEndOfMinute(FractionalSeconds(6)) == LocalTime.parse("12:38:59.999999"))
    assert(time.atEndOfMinute(FractionalSeconds(5)) == LocalTime.parse("12:38:59.99999"))
    assert(time.atEndOfMinute(FractionalSeconds(4)) == LocalTime.parse("12:38:59.9999"))
    assert(time.atEndOfMinute(FractionalSeconds(3)) == LocalTime.parse("12:38:59.999"))
    assert(time.atEndOfMinute(FractionalSeconds(2)) == LocalTime.parse("12:38:59.99"))
    assert(time.atEndOfMinute(FractionalSeconds(1)) == LocalTime.parse("12:38:59.9"))
    assert(time.atEndOfMinute(Seconds)              == LocalTime.parse("12:38:59"))
  }

  it should "be adjusted to end hour" in {
    assert(time.atEndOfHour(FractionalSeconds(9)) == LocalTime.parse("12:59:59.999999999"))
    assert(time.atEndOfHour(FractionalSeconds(8)) == LocalTime.parse("12:59:59.99999999"))
    assert(time.atEndOfHour(FractionalSeconds(7)) == LocalTime.parse("12:59:59.9999999"))
    assert(time.atEndOfHour(FractionalSeconds(6)) == LocalTime.parse("12:59:59.999999"))
    assert(time.atEndOfHour(FractionalSeconds(5)) == LocalTime.parse("12:59:59.99999"))
    assert(time.atEndOfHour(FractionalSeconds(4)) == LocalTime.parse("12:59:59.9999"))
    assert(time.atEndOfHour(FractionalSeconds(3)) == LocalTime.parse("12:59:59.999"))
    assert(time.atEndOfHour(FractionalSeconds(2)) == LocalTime.parse("12:59:59.99"))
    assert(time.atEndOfHour(FractionalSeconds(1)) == LocalTime.parse("12:59:59.9"))
    assert(time.atEndOfHour(Seconds)              == LocalTime.parse("12:59:59"))
    assert(time.atEndOfHour(Minutes)              == LocalTime.parse("12:59"))
  }

  it should "be adjusted to end day" in {
    assert(time.atEndOfDay(FractionalSeconds(8)) == LocalTime.parse("23:59:59.99999999"))
    assert(time.atEndOfDay(FractionalSeconds(7)) == LocalTime.parse("23:59:59.9999999"))
    assert(time.atEndOfDay(FractionalSeconds(6)) == LocalTime.parse("23:59:59.999999"))
    assert(time.atEndOfDay(FractionalSeconds(5)) == LocalTime.parse("23:59:59.99999"))
    assert(time.atEndOfDay(FractionalSeconds(4)) == LocalTime.parse("23:59:59.9999"))
    assert(time.atEndOfDay(FractionalSeconds(3)) == LocalTime.parse("23:59:59.999"))
    assert(time.atEndOfDay(FractionalSeconds(2)) == LocalTime.parse("23:59:59.99"))
    assert(time.atEndOfDay(FractionalSeconds(1)) == LocalTime.parse("23:59:59.9"))
    assert(time.atEndOfDay(Seconds)              == LocalTime.parse("23:59:59"))
    assert(time.atEndOfDay(Minutes)              == LocalTime.parse("23:59"))
    assert(time.atEndOfDay(Hours)                == LocalTime.parse("23:00"))
  }
}

