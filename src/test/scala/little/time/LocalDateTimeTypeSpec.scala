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

import java.time.{ DayOfWeek, LocalDateTime, YearMonth }

import org.scalatest.FlatSpec

import DayOfWeek._
import Implicits._
import TimePrecision._

class LocalDateTimeTypeSpec extends FlatSpec {
  private val dateTime = LocalDateTime.parse("2019-07-11T12:38:45.123456789")

  "LocalDateTime" should "be converted to YearMonth" in {
    assert(dateTime.toYearMonth == YearMonth.parse("2019-07"))
  }

  it should "be compared to other" in {
    import Ordered.orderingToOrdered
    val other1 = LocalDateTime.parse("2019-06-05T12:38:45")
    val other2 = LocalDateTime.parse("2019-07-11T12:38:45.123456789")
    
    assert(dateTime > other1)
    assert(dateTime >= other1)
    assert(dateTime != other1)

    assert(other1 < dateTime)
    assert(other1 <= dateTime)
    assert(other1 != dateTime)
    
    assert(dateTime >= other2)
    assert(dateTime <= other2)
    assert(dateTime == other2)

    assert(other2 >= dateTime)
    assert(other2 <= dateTime)
    assert(other2 == dateTime)
  }

  it should "be greater than other" in {
    val other = LocalDateTime.parse("2019-06-05T12:38:45")
    assert(dateTime.max(other) == dateTime)
    assert(other.min(dateTime) == other)
  }

  it should "be less than other" in {
    val other = LocalDateTime.parse("2019-10-23T05:32:46.789")
    assert(dateTime.max(other) == other)
    assert(other.min(dateTime) == dateTime)
  }

  it should "be truncated to microsecond" in {
    assert(dateTime.atStartOfMicros == LocalDateTime.parse("2019-07-11T12:38:45.123456"))
  }

  it should "be truncated to millisecond" in {
    assert(dateTime.atStartOfMillis == LocalDateTime.parse("2019-07-11T12:38:45.123"))
  }

  it should "be truncated to second" in {
    assert(dateTime.atStartOfSecond == LocalDateTime.parse("2019-07-11T12:38:45"))
  }

  it should "be truncated to minute" in {
    assert(dateTime.atStartOfMinute == LocalDateTime.parse("2019-07-11T12:38"))
  }

  it should "be truncated to hour" in {
    assert(dateTime.atStartOfHour == LocalDateTime.parse("2019-07-11T12:00"))
  }

  it should "be truncated to day" in {
    assert(dateTime.atStartOfDay == LocalDateTime.parse("2019-07-11T00:00"))
  }

  it should "be set to start of week" in {
    assert(dateTime.atStartOfWeek == LocalDateTime.parse("2019-07-07T00:00"))
    assert(dateTime.atStartOfWeek(SUNDAY) == LocalDateTime.parse("2019-07-07T00:00"))
    assert(dateTime.atStartOfWeek(MONDAY) == LocalDateTime.parse("2019-07-08T00:00"))
    assert(dateTime.atStartOfWeek(TUESDAY) == LocalDateTime.parse("2019-07-09T00:00"))
    assert(dateTime.atStartOfWeek(WEDNESDAY) == LocalDateTime.parse("2019-07-10T00:00"))
    assert(dateTime.atStartOfWeek(THURSDAY) == LocalDateTime.parse("2019-07-11T00:00"))
    assert(dateTime.atStartOfWeek(FRIDAY) == LocalDateTime.parse("2019-07-05T00:00"))
    assert(dateTime.atStartOfWeek(SATURDAY) == LocalDateTime.parse("2019-07-06T00:00"))
  }

  it should "be set to start of month" in {
    assert(dateTime.atStartOfMonth == LocalDateTime.parse("2019-07-01T00:00"))
  }

  it should "be set to start of year" in {
    assert(dateTime.atStartOfYear == LocalDateTime.parse("2019-01-01T00:00"))
  }

  it should "be adjusted to end microsecond" in {
    assert(dateTime.atEndOfMicros(FSeconds(9)) == LocalDateTime.parse("2019-07-11T12:38:45.999999999"))
    assert(dateTime.atEndOfMicros(FSeconds(8)) == LocalDateTime.parse("2019-07-11T12:38:45.99999999"))
    assert(dateTime.atEndOfMicros(FSeconds(7)) == LocalDateTime.parse("2019-07-11T12:38:45.9999999"))
  }

  it should "be adjusted to end millisecond" in {
    assert(dateTime.atEndOfMillis(FSeconds(9)) == LocalDateTime.parse("2019-07-11T12:38:45.999999999"))
    assert(dateTime.atEndOfMillis(FSeconds(8)) == LocalDateTime.parse("2019-07-11T12:38:45.99999999"))
    assert(dateTime.atEndOfMillis(FSeconds(7)) == LocalDateTime.parse("2019-07-11T12:38:45.9999999"))
    assert(dateTime.atEndOfMillis(FSeconds(6)) == LocalDateTime.parse("2019-07-11T12:38:45.999999"))
    assert(dateTime.atEndOfMillis(FSeconds(5)) == LocalDateTime.parse("2019-07-11T12:38:45.99999"))
    assert(dateTime.atEndOfMillis(FSeconds(4)) == LocalDateTime.parse("2019-07-11T12:38:45.9999"))
  }

  it should "be adjusted to end second" in {
    assert(dateTime.atEndOfSecond(FSeconds(9)) == LocalDateTime.parse("2019-07-11T12:38:45.999999999"))
    assert(dateTime.atEndOfSecond(FSeconds(8)) == LocalDateTime.parse("2019-07-11T12:38:45.99999999"))
    assert(dateTime.atEndOfSecond(FSeconds(7)) == LocalDateTime.parse("2019-07-11T12:38:45.9999999"))
    assert(dateTime.atEndOfSecond(FSeconds(6)) == LocalDateTime.parse("2019-07-11T12:38:45.999999"))
    assert(dateTime.atEndOfSecond(FSeconds(5)) == LocalDateTime.parse("2019-07-11T12:38:45.99999"))
    assert(dateTime.atEndOfSecond(FSeconds(4)) == LocalDateTime.parse("2019-07-11T12:38:45.9999"))
    assert(dateTime.atEndOfSecond(FSeconds(3)) == LocalDateTime.parse("2019-07-11T12:38:45.999"))
    assert(dateTime.atEndOfSecond(FSeconds(2)) == LocalDateTime.parse("2019-07-11T12:38:45.99"))
    assert(dateTime.atEndOfSecond(FSeconds(1)) == LocalDateTime.parse("2019-07-11T12:38:45.9"))
  }

  it should "be adjusted to end minute" in {
    assert(dateTime.atEndOfMinute(FSeconds(9)) == LocalDateTime.parse("2019-07-11T12:38:59.999999999"))
    assert(dateTime.atEndOfMinute(FSeconds(8)) == LocalDateTime.parse("2019-07-11T12:38:59.99999999"))
    assert(dateTime.atEndOfMinute(FSeconds(7)) == LocalDateTime.parse("2019-07-11T12:38:59.9999999"))
    assert(dateTime.atEndOfMinute(FSeconds(6)) == LocalDateTime.parse("2019-07-11T12:38:59.999999"))
    assert(dateTime.atEndOfMinute(FSeconds(5)) == LocalDateTime.parse("2019-07-11T12:38:59.99999"))
    assert(dateTime.atEndOfMinute(FSeconds(4)) == LocalDateTime.parse("2019-07-11T12:38:59.9999"))
    assert(dateTime.atEndOfMinute(FSeconds(3)) == LocalDateTime.parse("2019-07-11T12:38:59.999"))
    assert(dateTime.atEndOfMinute(FSeconds(2)) == LocalDateTime.parse("2019-07-11T12:38:59.99"))
    assert(dateTime.atEndOfMinute(FSeconds(1)) == LocalDateTime.parse("2019-07-11T12:38:59.9"))
    assert(dateTime.atEndOfMinute(Seconds)     == LocalDateTime.parse("2019-07-11T12:38:59"))
  }

  it should "be adjusted to end hour" in {
    assert(dateTime.atEndOfHour(FSeconds(9)) == LocalDateTime.parse("2019-07-11T12:59:59.999999999"))
    assert(dateTime.atEndOfHour(FSeconds(8)) == LocalDateTime.parse("2019-07-11T12:59:59.99999999"))
    assert(dateTime.atEndOfHour(FSeconds(7)) == LocalDateTime.parse("2019-07-11T12:59:59.9999999"))
    assert(dateTime.atEndOfHour(FSeconds(6)) == LocalDateTime.parse("2019-07-11T12:59:59.999999"))
    assert(dateTime.atEndOfHour(FSeconds(5)) == LocalDateTime.parse("2019-07-11T12:59:59.99999"))
    assert(dateTime.atEndOfHour(FSeconds(4)) == LocalDateTime.parse("2019-07-11T12:59:59.9999"))
    assert(dateTime.atEndOfHour(FSeconds(3)) == LocalDateTime.parse("2019-07-11T12:59:59.999"))
    assert(dateTime.atEndOfHour(FSeconds(2)) == LocalDateTime.parse("2019-07-11T12:59:59.99"))
    assert(dateTime.atEndOfHour(FSeconds(1)) == LocalDateTime.parse("2019-07-11T12:59:59.9"))
    assert(dateTime.atEndOfHour(Seconds)     == LocalDateTime.parse("2019-07-11T12:59:59"))
    assert(dateTime.atEndOfHour(Minutes)     == LocalDateTime.parse("2019-07-11T12:59"))
  }

  it should "be adjusted to end day" in {
    assert(dateTime.atEndOfDay(FSeconds(9)) == LocalDateTime.parse("2019-07-11T23:59:59.999999999"))
    assert(dateTime.atEndOfDay(FSeconds(8)) == LocalDateTime.parse("2019-07-11T23:59:59.99999999"))
    assert(dateTime.atEndOfDay(FSeconds(7)) == LocalDateTime.parse("2019-07-11T23:59:59.9999999"))
    assert(dateTime.atEndOfDay(FSeconds(6)) == LocalDateTime.parse("2019-07-11T23:59:59.999999"))
    assert(dateTime.atEndOfDay(FSeconds(5)) == LocalDateTime.parse("2019-07-11T23:59:59.99999"))
    assert(dateTime.atEndOfDay(FSeconds(4)) == LocalDateTime.parse("2019-07-11T23:59:59.9999"))
    assert(dateTime.atEndOfDay(FSeconds(3)) == LocalDateTime.parse("2019-07-11T23:59:59.999"))
    assert(dateTime.atEndOfDay(FSeconds(2)) == LocalDateTime.parse("2019-07-11T23:59:59.99"))
    assert(dateTime.atEndOfDay(FSeconds(1)) == LocalDateTime.parse("2019-07-11T23:59:59.9"))
    assert(dateTime.atEndOfDay(Seconds)     == LocalDateTime.parse("2019-07-11T23:59:59"))
    assert(dateTime.atEndOfDay(Minutes)     == LocalDateTime.parse("2019-07-11T23:59"))
    assert(dateTime.atEndOfDay(Hours)       == LocalDateTime.parse("2019-07-11T23:00"))
  }

  it should "be adjusted to end week" in {
    assert(dateTime.atEndOfWeek(FSeconds(9)) == LocalDateTime.parse("2019-07-13T23:59:59.999999999"))
    assert(dateTime.atEndOfWeek(FSeconds(8)) == LocalDateTime.parse("2019-07-13T23:59:59.99999999"))
    assert(dateTime.atEndOfWeek(FSeconds(7)) == LocalDateTime.parse("2019-07-13T23:59:59.9999999"))
    assert(dateTime.atEndOfWeek(FSeconds(6)) == LocalDateTime.parse("2019-07-13T23:59:59.999999"))
    assert(dateTime.atEndOfWeek(FSeconds(5)) == LocalDateTime.parse("2019-07-13T23:59:59.99999"))
    assert(dateTime.atEndOfWeek(FSeconds(4)) == LocalDateTime.parse("2019-07-13T23:59:59.9999"))
    assert(dateTime.atEndOfWeek(FSeconds(3)) == LocalDateTime.parse("2019-07-13T23:59:59.999"))
    assert(dateTime.atEndOfWeek(FSeconds(2)) == LocalDateTime.parse("2019-07-13T23:59:59.99"))
    assert(dateTime.atEndOfWeek(FSeconds(1)) == LocalDateTime.parse("2019-07-13T23:59:59.9"))
    assert(dateTime.atEndOfWeek(Seconds)     == LocalDateTime.parse("2019-07-13T23:59:59"))
    assert(dateTime.atEndOfWeek(Minutes)     == LocalDateTime.parse("2019-07-13T23:59"))
    assert(dateTime.atEndOfWeek(Hours)       == LocalDateTime.parse("2019-07-13T23:00"))

    Seq(SUNDAY -> 14, MONDAY -> 15, TUESDAY -> 16, WEDNESDAY -> 17, THURSDAY -> 11, FRIDAY -> 12, SATURDAY -> 13).foreach {
      case (dayOfWeek, dayOfMonth) =>
        assert(dateTime.atEndOfWeek(dayOfWeek)(FSeconds(9)) == LocalDateTime.parse(s"2019-07-${dayOfMonth}T23:59:59.999999999"))
        assert(dateTime.atEndOfWeek(dayOfWeek)(FSeconds(8)) == LocalDateTime.parse(s"2019-07-${dayOfMonth}T23:59:59.99999999"))
        assert(dateTime.atEndOfWeek(dayOfWeek)(FSeconds(7)) == LocalDateTime.parse(s"2019-07-${dayOfMonth}T23:59:59.9999999"))
        assert(dateTime.atEndOfWeek(dayOfWeek)(FSeconds(6)) == LocalDateTime.parse(s"2019-07-${dayOfMonth}T23:59:59.999999"))
        assert(dateTime.atEndOfWeek(dayOfWeek)(FSeconds(5)) == LocalDateTime.parse(s"2019-07-${dayOfMonth}T23:59:59.99999"))
        assert(dateTime.atEndOfWeek(dayOfWeek)(FSeconds(4)) == LocalDateTime.parse(s"2019-07-${dayOfMonth}T23:59:59.9999"))
        assert(dateTime.atEndOfWeek(dayOfWeek)(FSeconds(3)) == LocalDateTime.parse(s"2019-07-${dayOfMonth}T23:59:59.999"))
        assert(dateTime.atEndOfWeek(dayOfWeek)(FSeconds(2)) == LocalDateTime.parse(s"2019-07-${dayOfMonth}T23:59:59.99"))
        assert(dateTime.atEndOfWeek(dayOfWeek)(FSeconds(1)) == LocalDateTime.parse(s"2019-07-${dayOfMonth}T23:59:59.9"))
        assert(dateTime.atEndOfWeek(dayOfWeek)(Seconds)     == LocalDateTime.parse(s"2019-07-${dayOfMonth}T23:59:59"))
        assert(dateTime.atEndOfWeek(dayOfWeek)(Minutes)     == LocalDateTime.parse(s"2019-07-${dayOfMonth}T23:59"))
        assert(dateTime.atEndOfWeek(dayOfWeek)(Hours)       == LocalDateTime.parse(s"2019-07-${dayOfMonth}T23:00"))
    }
  }

  it should "be adjusted to end month" in {
    assert(dateTime.atEndOfMonth(FSeconds(9)) == LocalDateTime.parse("2019-07-31T23:59:59.999999999"))
    assert(dateTime.atEndOfMonth(FSeconds(8)) == LocalDateTime.parse("2019-07-31T23:59:59.99999999"))
    assert(dateTime.atEndOfMonth(FSeconds(7)) == LocalDateTime.parse("2019-07-31T23:59:59.9999999"))
    assert(dateTime.atEndOfMonth(FSeconds(6)) == LocalDateTime.parse("2019-07-31T23:59:59.999999"))
    assert(dateTime.atEndOfMonth(FSeconds(5)) == LocalDateTime.parse("2019-07-31T23:59:59.99999"))
    assert(dateTime.atEndOfMonth(FSeconds(4)) == LocalDateTime.parse("2019-07-31T23:59:59.9999"))
    assert(dateTime.atEndOfMonth(FSeconds(3)) == LocalDateTime.parse("2019-07-31T23:59:59.999"))
    assert(dateTime.atEndOfMonth(FSeconds(2)) == LocalDateTime.parse("2019-07-31T23:59:59.99"))
    assert(dateTime.atEndOfMonth(FSeconds(1)) == LocalDateTime.parse("2019-07-31T23:59:59.9"))
    assert(dateTime.atEndOfMonth(Seconds)     == LocalDateTime.parse("2019-07-31T23:59:59"))
    assert(dateTime.atEndOfMonth(Minutes)     == LocalDateTime.parse("2019-07-31T23:59"))
    assert(dateTime.atEndOfMonth(Hours)       == LocalDateTime.parse("2019-07-31T23:00"))
  }

  it should "be adjusted to end year" in {
    assert(dateTime.atEndOfYear(FSeconds(9)) == LocalDateTime.parse("2019-12-31T23:59:59.999999999"))
    assert(dateTime.atEndOfYear(FSeconds(8)) == LocalDateTime.parse("2019-12-31T23:59:59.99999999"))
    assert(dateTime.atEndOfYear(FSeconds(7)) == LocalDateTime.parse("2019-12-31T23:59:59.9999999"))
    assert(dateTime.atEndOfYear(FSeconds(6)) == LocalDateTime.parse("2019-12-31T23:59:59.999999"))
    assert(dateTime.atEndOfYear(FSeconds(5)) == LocalDateTime.parse("2019-12-31T23:59:59.99999"))
    assert(dateTime.atEndOfYear(FSeconds(4)) == LocalDateTime.parse("2019-12-31T23:59:59.9999"))
    assert(dateTime.atEndOfYear(FSeconds(3)) == LocalDateTime.parse("2019-12-31T23:59:59.999"))
    assert(dateTime.atEndOfYear(FSeconds(2)) == LocalDateTime.parse("2019-12-31T23:59:59.99"))
    assert(dateTime.atEndOfYear(FSeconds(1)) == LocalDateTime.parse("2019-12-31T23:59:59.9"))
    assert(dateTime.atEndOfYear(Seconds)     == LocalDateTime.parse("2019-12-31T23:59:59"))
    assert(dateTime.atEndOfYear(Minutes)     == LocalDateTime.parse("2019-12-31T23:59"))
    assert(dateTime.atEndOfYear(Hours)       == LocalDateTime.parse("2019-12-31T23:00"))
  }
}

