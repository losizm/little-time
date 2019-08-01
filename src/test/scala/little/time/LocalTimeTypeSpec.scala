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

import java.time.LocalTime

import org.scalatest.FlatSpec

import Implicits._
import TimePrecision._

class LocalTimeTypeSpec extends FlatSpec {
  private val time = LocalTime.parse("12:38:45.123456789")

  "LocalTime" should "be compared to other" in {
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
    assert(time.atEndOfMicros(FSeconds(9)) == LocalTime.parse("12:38:45.999999999"))
    assert(time.atEndOfMicros(FSeconds(8)) == LocalTime.parse("12:38:45.99999999"))
    assert(time.atEndOfMicros(FSeconds(7)) == LocalTime.parse("12:38:45.9999999"))
  }

  it should "be adjusted to end millisecond" in {
    assert(time.atEndOfMillis(FSeconds(9)) == LocalTime.parse("12:38:45.999999999"))
    assert(time.atEndOfMillis(FSeconds(8)) == LocalTime.parse("12:38:45.99999999"))
    assert(time.atEndOfMillis(FSeconds(7)) == LocalTime.parse("12:38:45.9999999"))
    assert(time.atEndOfMillis(FSeconds(6)) == LocalTime.parse("12:38:45.999999"))
    assert(time.atEndOfMillis(FSeconds(5)) == LocalTime.parse("12:38:45.99999"))
    assert(time.atEndOfMillis(FSeconds(4)) == LocalTime.parse("12:38:45.9999"))
  }

  it should "be adjusted to end second" in {
    assert(time.atEndOfSecond(FSeconds(9)) == LocalTime.parse("12:38:45.999999999"))
    assert(time.atEndOfSecond(FSeconds(8)) == LocalTime.parse("12:38:45.99999999"))
    assert(time.atEndOfSecond(FSeconds(7)) == LocalTime.parse("12:38:45.9999999"))
    assert(time.atEndOfSecond(FSeconds(6)) == LocalTime.parse("12:38:45.999999"))
    assert(time.atEndOfSecond(FSeconds(5)) == LocalTime.parse("12:38:45.99999"))
    assert(time.atEndOfSecond(FSeconds(4)) == LocalTime.parse("12:38:45.9999"))
    assert(time.atEndOfSecond(FSeconds(3)) == LocalTime.parse("12:38:45.999"))
    assert(time.atEndOfSecond(FSeconds(2)) == LocalTime.parse("12:38:45.99"))
    assert(time.atEndOfSecond(FSeconds(1)) == LocalTime.parse("12:38:45.9"))
  }

  it should "be adjusted to end minute" in {
    assert(time.atEndOfMinute(FSeconds(9)) == LocalTime.parse("12:38:59.999999999"))
    assert(time.atEndOfMinute(FSeconds(8)) == LocalTime.parse("12:38:59.99999999"))
    assert(time.atEndOfMinute(FSeconds(7)) == LocalTime.parse("12:38:59.9999999"))
    assert(time.atEndOfMinute(FSeconds(6)) == LocalTime.parse("12:38:59.999999"))
    assert(time.atEndOfMinute(FSeconds(5)) == LocalTime.parse("12:38:59.99999"))
    assert(time.atEndOfMinute(FSeconds(4)) == LocalTime.parse("12:38:59.9999"))
    assert(time.atEndOfMinute(FSeconds(3)) == LocalTime.parse("12:38:59.999"))
    assert(time.atEndOfMinute(FSeconds(2)) == LocalTime.parse("12:38:59.99"))
    assert(time.atEndOfMinute(FSeconds(1)) == LocalTime.parse("12:38:59.9"))
    assert(time.atEndOfMinute(Seconds)     == LocalTime.parse("12:38:59"))
  }

  it should "be adjusted to end hour" in {
    assert(time.atEndOfHour(FSeconds(9)) == LocalTime.parse("12:59:59.999999999"))
    assert(time.atEndOfHour(FSeconds(8)) == LocalTime.parse("12:59:59.99999999"))
    assert(time.atEndOfHour(FSeconds(7)) == LocalTime.parse("12:59:59.9999999"))
    assert(time.atEndOfHour(FSeconds(6)) == LocalTime.parse("12:59:59.999999"))
    assert(time.atEndOfHour(FSeconds(5)) == LocalTime.parse("12:59:59.99999"))
    assert(time.atEndOfHour(FSeconds(4)) == LocalTime.parse("12:59:59.9999"))
    assert(time.atEndOfHour(FSeconds(3)) == LocalTime.parse("12:59:59.999"))
    assert(time.atEndOfHour(FSeconds(2)) == LocalTime.parse("12:59:59.99"))
    assert(time.atEndOfHour(FSeconds(1)) == LocalTime.parse("12:59:59.9"))
    assert(time.atEndOfHour(Seconds)     == LocalTime.parse("12:59:59"))
    assert(time.atEndOfHour(Minutes)     == LocalTime.parse("12:59"))
  }

  it should "be adjusted to end day" in {
    assert(time.atEndOfDay(FSeconds(8)) == LocalTime.parse("23:59:59.99999999"))
    assert(time.atEndOfDay(FSeconds(7)) == LocalTime.parse("23:59:59.9999999"))
    assert(time.atEndOfDay(FSeconds(6)) == LocalTime.parse("23:59:59.999999"))
    assert(time.atEndOfDay(FSeconds(5)) == LocalTime.parse("23:59:59.99999"))
    assert(time.atEndOfDay(FSeconds(4)) == LocalTime.parse("23:59:59.9999"))
    assert(time.atEndOfDay(FSeconds(3)) == LocalTime.parse("23:59:59.999"))
    assert(time.atEndOfDay(FSeconds(2)) == LocalTime.parse("23:59:59.99"))
    assert(time.atEndOfDay(FSeconds(1)) == LocalTime.parse("23:59:59.9"))
    assert(time.atEndOfDay(Seconds)     == LocalTime.parse("23:59:59"))
    assert(time.atEndOfDay(Minutes)     == LocalTime.parse("23:59"))
    assert(time.atEndOfDay(Hours)       == LocalTime.parse("23:00"))
  }
}

