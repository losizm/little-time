/*
 * Copyright 2021 Carlos Conyers
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

import java.time.{ DayOfWeek, Duration, LocalDateTime, Period, YearMonth }

import DayOfWeek.*
import Implicits.*
import TimePrecision.*

class LocalDateTimeTypeSpec extends org.scalatest.flatspec.AnyFlatSpec:
  private val dateTime = LocalDateTime.parse("2019-07-11T12:38:45.123456789")

  "LocalDateTime" should "have duration added" in {
    assert(dateTime + Duration.ofSeconds(1) == LocalDateTime.parse("2019-07-11T12:38:46.123456789"))
    assert(dateTime + Duration.ofMinutes(2) == LocalDateTime.parse("2019-07-11T12:40:45.123456789"))
    assert(dateTime + Duration.ofHours(31)  == LocalDateTime.parse("2019-07-12T19:38:45.123456789"))
    assert(dateTime + Duration.ofMillis(5)  == LocalDateTime.parse("2019-07-11T12:38:45.128456789"))
    assert(dateTime + Duration.ofNanos(2)   == LocalDateTime.parse("2019-07-11T12:38:45.123456791"))
    assert(dateTime + Duration.ofDays(7)    == LocalDateTime.parse("2019-07-18T12:38:45.123456789"))

    assert(dateTime + Duration.ofSeconds(-1) == LocalDateTime.parse("2019-07-11T12:38:44.123456789"))
    assert(dateTime + Duration.ofMinutes(-2) == LocalDateTime.parse("2019-07-11T12:36:45.123456789"))
    assert(dateTime + Duration.ofHours(-31)  == LocalDateTime.parse("2019-07-10T05:38:45.123456789"))
    assert(dateTime + Duration.ofMillis(-5)  == LocalDateTime.parse("2019-07-11T12:38:45.118456789"))
    assert(dateTime + Duration.ofNanos(-2)   == LocalDateTime.parse("2019-07-11T12:38:45.123456787"))
    assert(dateTime + Duration.ofDays(-7)    == LocalDateTime.parse("2019-07-04T12:38:45.123456789"))
  }

  it should "be have duration subtracted" in {
    assert(dateTime - Duration.ofSeconds(1) == LocalDateTime.parse("2019-07-11T12:38:44.123456789"))
    assert(dateTime - Duration.ofMinutes(2) == LocalDateTime.parse("2019-07-11T12:36:45.123456789"))
    assert(dateTime - Duration.ofHours(31)  == LocalDateTime.parse("2019-07-10T05:38:45.123456789"))
    assert(dateTime - Duration.ofMillis(5)  == LocalDateTime.parse("2019-07-11T12:38:45.118456789"))
    assert(dateTime - Duration.ofNanos(2)   == LocalDateTime.parse("2019-07-11T12:38:45.123456787"))
    assert(dateTime - Duration.ofDays(7)    == LocalDateTime.parse("2019-07-04T12:38:45.123456789"))

    assert(dateTime - Duration.ofSeconds(-1) == LocalDateTime.parse("2019-07-11T12:38:46.123456789"))
    assert(dateTime - Duration.ofMinutes(-2) == LocalDateTime.parse("2019-07-11T12:40:45.123456789"))
    assert(dateTime - Duration.ofHours(-31)  == LocalDateTime.parse("2019-07-12T19:38:45.123456789"))
    assert(dateTime - Duration.ofMillis(-5)  == LocalDateTime.parse("2019-07-11T12:38:45.128456789"))
    assert(dateTime - Duration.ofNanos(-2)   == LocalDateTime.parse("2019-07-11T12:38:45.123456791"))
    assert(dateTime - Duration.ofDays(-7)    == LocalDateTime.parse("2019-07-18T12:38:45.123456789"))
  }

  "LocalDateTime" should "have period added" in {
    assert(dateTime + Period.ofDays(1)   == LocalDateTime.parse("2019-07-12T12:38:45.123456789"))
    assert(dateTime + Period.ofDays(2)   == LocalDateTime.parse("2019-07-13T12:38:45.123456789"))
    assert(dateTime + Period.ofDays(31)  == LocalDateTime.parse("2019-08-11T12:38:45.123456789"))
    assert(dateTime + Period.ofMonths(5) == LocalDateTime.parse("2019-12-11T12:38:45.123456789"))
    assert(dateTime + Period.ofWeeks(2)  == LocalDateTime.parse("2019-07-25T12:38:45.123456789"))
    assert(dateTime + Period.of(6, 3, 7) == LocalDateTime.parse("2025-10-18T12:38:45.123456789"))

    assert(dateTime + Period.ofDays(-1)     == LocalDateTime.parse("2019-07-10T12:38:45.123456789"))
    assert(dateTime + Period.ofDays(-2)     == LocalDateTime.parse("2019-07-09T12:38:45.123456789"))
    assert(dateTime + Period.ofDays(-11)    == LocalDateTime.parse("2019-06-30T12:38:45.123456789"))
    assert(dateTime + Period.ofMonths(-5)   == LocalDateTime.parse("2019-02-11T12:38:45.123456789"))
    assert(dateTime + Period.ofWeeks(-2)    == LocalDateTime.parse("2019-06-27T12:38:45.123456789"))
    assert(dateTime + Period.of(-6, -3, -7) == LocalDateTime.parse("2013-04-04T12:38:45.123456789"))
  }

  it should "be have period subtracted" in {
    assert(dateTime - Period.ofDays(1)   == LocalDateTime.parse("2019-07-10T12:38:45.123456789"))
    assert(dateTime - Period.ofDays(2)   == LocalDateTime.parse("2019-07-09T12:38:45.123456789"))
    assert(dateTime - Period.ofDays(11)  == LocalDateTime.parse("2019-06-30T12:38:45.123456789"))
    assert(dateTime - Period.ofMonths(5) == LocalDateTime.parse("2019-02-11T12:38:45.123456789"))
    assert(dateTime - Period.ofWeeks(2)  == LocalDateTime.parse("2019-06-27T12:38:45.123456789"))
    assert(dateTime - Period.of(6, 3, 7) == LocalDateTime.parse("2013-04-04T12:38:45.123456789"))

    assert(dateTime - Period.ofDays(-1)     == LocalDateTime.parse("2019-07-12T12:38:45.123456789"))
    assert(dateTime - Period.ofDays(-2)     == LocalDateTime.parse("2019-07-13T12:38:45.123456789"))
    assert(dateTime - Period.ofDays(-31)    == LocalDateTime.parse("2019-08-11T12:38:45.123456789"))
    assert(dateTime - Period.ofMonths(-5)   == LocalDateTime.parse("2019-12-11T12:38:45.123456789"))
    assert(dateTime - Period.ofWeeks(-2)    == LocalDateTime.parse("2019-07-25T12:38:45.123456789"))
    assert(dateTime - Period.of(-6, -3, -7) == LocalDateTime.parse("2025-10-18T12:38:45.123456789"))
  }

  it should "be converted to YearMonth" in {
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
    assert(dateTime.atStartOfWeek(SUNDAY)    == LocalDateTime.parse("2019-07-07T00:00"))
    assert(dateTime.atStartOfWeek(MONDAY)    == LocalDateTime.parse("2019-07-08T00:00"))
    assert(dateTime.atStartOfWeek(TUESDAY)   == LocalDateTime.parse("2019-07-09T00:00"))
    assert(dateTime.atStartOfWeek(WEDNESDAY) == LocalDateTime.parse("2019-07-10T00:00"))
    assert(dateTime.atStartOfWeek(THURSDAY)  == LocalDateTime.parse("2019-07-11T00:00"))
    assert(dateTime.atStartOfWeek(FRIDAY)    == LocalDateTime.parse("2019-07-05T00:00"))
    assert(dateTime.atStartOfWeek(SATURDAY)  == LocalDateTime.parse("2019-07-06T00:00"))
  }

  it should "be set to start of month" in {
    assert(dateTime.atStartOfMonth == LocalDateTime.parse("2019-07-01T00:00"))
  }

  it should "be set to start of year" in {
    assert(dateTime.atStartOfYear == LocalDateTime.parse("2019-01-01T00:00"))
  }

  it should "be adjusted to end microsecond" in {
    assert(dateTime.atEndOfMicros(using FractionalSeconds(9)) == LocalDateTime.parse("2019-07-11T12:38:45.999999999"))
    assert(dateTime.atEndOfMicros(using FractionalSeconds(8)) == LocalDateTime.parse("2019-07-11T12:38:45.99999999"))
    assert(dateTime.atEndOfMicros(using FractionalSeconds(7)) == LocalDateTime.parse("2019-07-11T12:38:45.9999999"))
  }

  it should "be adjusted to end millisecond" in {
    assert(dateTime.atEndOfMillis(using FractionalSeconds(9)) == LocalDateTime.parse("2019-07-11T12:38:45.999999999"))
    assert(dateTime.atEndOfMillis(using FractionalSeconds(8)) == LocalDateTime.parse("2019-07-11T12:38:45.99999999"))
    assert(dateTime.atEndOfMillis(using FractionalSeconds(7)) == LocalDateTime.parse("2019-07-11T12:38:45.9999999"))
    assert(dateTime.atEndOfMillis(using FractionalSeconds(6)) == LocalDateTime.parse("2019-07-11T12:38:45.999999"))
    assert(dateTime.atEndOfMillis(using FractionalSeconds(5)) == LocalDateTime.parse("2019-07-11T12:38:45.99999"))
    assert(dateTime.atEndOfMillis(using FractionalSeconds(4)) == LocalDateTime.parse("2019-07-11T12:38:45.9999"))
  }

  it should "be adjusted to end second" in {
    assert(dateTime.atEndOfSecond(using FractionalSeconds(9)) == LocalDateTime.parse("2019-07-11T12:38:45.999999999"))
    assert(dateTime.atEndOfSecond(using FractionalSeconds(8)) == LocalDateTime.parse("2019-07-11T12:38:45.99999999"))
    assert(dateTime.atEndOfSecond(using FractionalSeconds(7)) == LocalDateTime.parse("2019-07-11T12:38:45.9999999"))
    assert(dateTime.atEndOfSecond(using FractionalSeconds(6)) == LocalDateTime.parse("2019-07-11T12:38:45.999999"))
    assert(dateTime.atEndOfSecond(using FractionalSeconds(5)) == LocalDateTime.parse("2019-07-11T12:38:45.99999"))
    assert(dateTime.atEndOfSecond(using FractionalSeconds(4)) == LocalDateTime.parse("2019-07-11T12:38:45.9999"))
    assert(dateTime.atEndOfSecond(using FractionalSeconds(3)) == LocalDateTime.parse("2019-07-11T12:38:45.999"))
    assert(dateTime.atEndOfSecond(using FractionalSeconds(2)) == LocalDateTime.parse("2019-07-11T12:38:45.99"))
    assert(dateTime.atEndOfSecond(using FractionalSeconds(1)) == LocalDateTime.parse("2019-07-11T12:38:45.9"))
  }

  it should "be adjusted to end minute" in {
    assert(dateTime.atEndOfMinute(using FractionalSeconds(9)) == LocalDateTime.parse("2019-07-11T12:38:59.999999999"))
    assert(dateTime.atEndOfMinute(using FractionalSeconds(8)) == LocalDateTime.parse("2019-07-11T12:38:59.99999999"))
    assert(dateTime.atEndOfMinute(using FractionalSeconds(7)) == LocalDateTime.parse("2019-07-11T12:38:59.9999999"))
    assert(dateTime.atEndOfMinute(using FractionalSeconds(6)) == LocalDateTime.parse("2019-07-11T12:38:59.999999"))
    assert(dateTime.atEndOfMinute(using FractionalSeconds(5)) == LocalDateTime.parse("2019-07-11T12:38:59.99999"))
    assert(dateTime.atEndOfMinute(using FractionalSeconds(4)) == LocalDateTime.parse("2019-07-11T12:38:59.9999"))
    assert(dateTime.atEndOfMinute(using FractionalSeconds(3)) == LocalDateTime.parse("2019-07-11T12:38:59.999"))
    assert(dateTime.atEndOfMinute(using FractionalSeconds(2)) == LocalDateTime.parse("2019-07-11T12:38:59.99"))
    assert(dateTime.atEndOfMinute(using FractionalSeconds(1)) == LocalDateTime.parse("2019-07-11T12:38:59.9"))
    assert(dateTime.atEndOfMinute(using Seconds)              == LocalDateTime.parse("2019-07-11T12:38:59"))
  }

  it should "be adjusted to end hour" in {
    assert(dateTime.atEndOfHour(using FractionalSeconds(9)) == LocalDateTime.parse("2019-07-11T12:59:59.999999999"))
    assert(dateTime.atEndOfHour(using FractionalSeconds(8)) == LocalDateTime.parse("2019-07-11T12:59:59.99999999"))
    assert(dateTime.atEndOfHour(using FractionalSeconds(7)) == LocalDateTime.parse("2019-07-11T12:59:59.9999999"))
    assert(dateTime.atEndOfHour(using FractionalSeconds(6)) == LocalDateTime.parse("2019-07-11T12:59:59.999999"))
    assert(dateTime.atEndOfHour(using FractionalSeconds(5)) == LocalDateTime.parse("2019-07-11T12:59:59.99999"))
    assert(dateTime.atEndOfHour(using FractionalSeconds(4)) == LocalDateTime.parse("2019-07-11T12:59:59.9999"))
    assert(dateTime.atEndOfHour(using FractionalSeconds(3)) == LocalDateTime.parse("2019-07-11T12:59:59.999"))
    assert(dateTime.atEndOfHour(using FractionalSeconds(2)) == LocalDateTime.parse("2019-07-11T12:59:59.99"))
    assert(dateTime.atEndOfHour(using FractionalSeconds(1)) == LocalDateTime.parse("2019-07-11T12:59:59.9"))
    assert(dateTime.atEndOfHour(using Seconds)              == LocalDateTime.parse("2019-07-11T12:59:59"))
    assert(dateTime.atEndOfHour(using Minutes)              == LocalDateTime.parse("2019-07-11T12:59"))
  }

  it should "be adjusted to end day" in {
    assert(dateTime.atEndOfDay(using FractionalSeconds(9)) == LocalDateTime.parse("2019-07-11T23:59:59.999999999"))
    assert(dateTime.atEndOfDay(using FractionalSeconds(8)) == LocalDateTime.parse("2019-07-11T23:59:59.99999999"))
    assert(dateTime.atEndOfDay(using FractionalSeconds(7)) == LocalDateTime.parse("2019-07-11T23:59:59.9999999"))
    assert(dateTime.atEndOfDay(using FractionalSeconds(6)) == LocalDateTime.parse("2019-07-11T23:59:59.999999"))
    assert(dateTime.atEndOfDay(using FractionalSeconds(5)) == LocalDateTime.parse("2019-07-11T23:59:59.99999"))
    assert(dateTime.atEndOfDay(using FractionalSeconds(4)) == LocalDateTime.parse("2019-07-11T23:59:59.9999"))
    assert(dateTime.atEndOfDay(using FractionalSeconds(3)) == LocalDateTime.parse("2019-07-11T23:59:59.999"))
    assert(dateTime.atEndOfDay(using FractionalSeconds(2)) == LocalDateTime.parse("2019-07-11T23:59:59.99"))
    assert(dateTime.atEndOfDay(using FractionalSeconds(1)) == LocalDateTime.parse("2019-07-11T23:59:59.9"))
    assert(dateTime.atEndOfDay(using Seconds)              == LocalDateTime.parse("2019-07-11T23:59:59"))
    assert(dateTime.atEndOfDay(using Minutes)              == LocalDateTime.parse("2019-07-11T23:59"))
    assert(dateTime.atEndOfDay(using Hours)                == LocalDateTime.parse("2019-07-11T23:00"))
  }

  it should "be adjusted to end week" in {
    assert(dateTime.atEndOfWeek(using FractionalSeconds(9)) == LocalDateTime.parse("2019-07-13T23:59:59.999999999"))
    assert(dateTime.atEndOfWeek(using FractionalSeconds(8)) == LocalDateTime.parse("2019-07-13T23:59:59.99999999"))
    assert(dateTime.atEndOfWeek(using FractionalSeconds(7)) == LocalDateTime.parse("2019-07-13T23:59:59.9999999"))
    assert(dateTime.atEndOfWeek(using FractionalSeconds(6)) == LocalDateTime.parse("2019-07-13T23:59:59.999999"))
    assert(dateTime.atEndOfWeek(using FractionalSeconds(5)) == LocalDateTime.parse("2019-07-13T23:59:59.99999"))
    assert(dateTime.atEndOfWeek(using FractionalSeconds(4)) == LocalDateTime.parse("2019-07-13T23:59:59.9999"))
    assert(dateTime.atEndOfWeek(using FractionalSeconds(3)) == LocalDateTime.parse("2019-07-13T23:59:59.999"))
    assert(dateTime.atEndOfWeek(using FractionalSeconds(2)) == LocalDateTime.parse("2019-07-13T23:59:59.99"))
    assert(dateTime.atEndOfWeek(using FractionalSeconds(1)) == LocalDateTime.parse("2019-07-13T23:59:59.9"))
    assert(dateTime.atEndOfWeek(using Seconds)              == LocalDateTime.parse("2019-07-13T23:59:59"))
    assert(dateTime.atEndOfWeek(using Minutes)              == LocalDateTime.parse("2019-07-13T23:59"))
    assert(dateTime.atEndOfWeek(using Hours)                == LocalDateTime.parse("2019-07-13T23:00"))

    Seq(SUNDAY -> 14, MONDAY -> 15, TUESDAY -> 16, WEDNESDAY -> 17, THURSDAY -> 11, FRIDAY -> 12, SATURDAY -> 13).foreach {
      case (dayOfWeek, dayOfMonth) =>
        assert(dateTime.atEndOfWeek(dayOfWeek)(using FractionalSeconds(9)) == LocalDateTime.parse(s"2019-07-${dayOfMonth}T23:59:59.999999999"))
        assert(dateTime.atEndOfWeek(dayOfWeek)(using FractionalSeconds(8)) == LocalDateTime.parse(s"2019-07-${dayOfMonth}T23:59:59.99999999"))
        assert(dateTime.atEndOfWeek(dayOfWeek)(using FractionalSeconds(7)) == LocalDateTime.parse(s"2019-07-${dayOfMonth}T23:59:59.9999999"))
        assert(dateTime.atEndOfWeek(dayOfWeek)(using FractionalSeconds(6)) == LocalDateTime.parse(s"2019-07-${dayOfMonth}T23:59:59.999999"))
        assert(dateTime.atEndOfWeek(dayOfWeek)(using FractionalSeconds(5)) == LocalDateTime.parse(s"2019-07-${dayOfMonth}T23:59:59.99999"))
        assert(dateTime.atEndOfWeek(dayOfWeek)(using FractionalSeconds(4)) == LocalDateTime.parse(s"2019-07-${dayOfMonth}T23:59:59.9999"))
        assert(dateTime.atEndOfWeek(dayOfWeek)(using FractionalSeconds(3)) == LocalDateTime.parse(s"2019-07-${dayOfMonth}T23:59:59.999"))
        assert(dateTime.atEndOfWeek(dayOfWeek)(using FractionalSeconds(2)) == LocalDateTime.parse(s"2019-07-${dayOfMonth}T23:59:59.99"))
        assert(dateTime.atEndOfWeek(dayOfWeek)(using FractionalSeconds(1)) == LocalDateTime.parse(s"2019-07-${dayOfMonth}T23:59:59.9"))
        assert(dateTime.atEndOfWeek(dayOfWeek)(using Seconds)              == LocalDateTime.parse(s"2019-07-${dayOfMonth}T23:59:59"))
        assert(dateTime.atEndOfWeek(dayOfWeek)(using Minutes)              == LocalDateTime.parse(s"2019-07-${dayOfMonth}T23:59"))
        assert(dateTime.atEndOfWeek(dayOfWeek)(using Hours)                == LocalDateTime.parse(s"2019-07-${dayOfMonth}T23:00"))
    }
  }

  it should "be adjusted to end month" in {
    assert(dateTime.atEndOfMonth(using FractionalSeconds(9)) == LocalDateTime.parse("2019-07-31T23:59:59.999999999"))
    assert(dateTime.atEndOfMonth(using FractionalSeconds(8)) == LocalDateTime.parse("2019-07-31T23:59:59.99999999"))
    assert(dateTime.atEndOfMonth(using FractionalSeconds(7)) == LocalDateTime.parse("2019-07-31T23:59:59.9999999"))
    assert(dateTime.atEndOfMonth(using FractionalSeconds(6)) == LocalDateTime.parse("2019-07-31T23:59:59.999999"))
    assert(dateTime.atEndOfMonth(using FractionalSeconds(5)) == LocalDateTime.parse("2019-07-31T23:59:59.99999"))
    assert(dateTime.atEndOfMonth(using FractionalSeconds(4)) == LocalDateTime.parse("2019-07-31T23:59:59.9999"))
    assert(dateTime.atEndOfMonth(using FractionalSeconds(3)) == LocalDateTime.parse("2019-07-31T23:59:59.999"))
    assert(dateTime.atEndOfMonth(using FractionalSeconds(2)) == LocalDateTime.parse("2019-07-31T23:59:59.99"))
    assert(dateTime.atEndOfMonth(using FractionalSeconds(1)) == LocalDateTime.parse("2019-07-31T23:59:59.9"))
    assert(dateTime.atEndOfMonth(using Seconds)              == LocalDateTime.parse("2019-07-31T23:59:59"))
    assert(dateTime.atEndOfMonth(using Minutes)              == LocalDateTime.parse("2019-07-31T23:59"))
    assert(dateTime.atEndOfMonth(using Hours)                == LocalDateTime.parse("2019-07-31T23:00"))
  }

  it should "be adjusted to end year" in {
    assert(dateTime.atEndOfYear(using FractionalSeconds(9)) == LocalDateTime.parse("2019-12-31T23:59:59.999999999"))
    assert(dateTime.atEndOfYear(using FractionalSeconds(8)) == LocalDateTime.parse("2019-12-31T23:59:59.99999999"))
    assert(dateTime.atEndOfYear(using FractionalSeconds(7)) == LocalDateTime.parse("2019-12-31T23:59:59.9999999"))
    assert(dateTime.atEndOfYear(using FractionalSeconds(6)) == LocalDateTime.parse("2019-12-31T23:59:59.999999"))
    assert(dateTime.atEndOfYear(using FractionalSeconds(5)) == LocalDateTime.parse("2019-12-31T23:59:59.99999"))
    assert(dateTime.atEndOfYear(using FractionalSeconds(4)) == LocalDateTime.parse("2019-12-31T23:59:59.9999"))
    assert(dateTime.atEndOfYear(using FractionalSeconds(3)) == LocalDateTime.parse("2019-12-31T23:59:59.999"))
    assert(dateTime.atEndOfYear(using FractionalSeconds(2)) == LocalDateTime.parse("2019-12-31T23:59:59.99"))
    assert(dateTime.atEndOfYear(using FractionalSeconds(1)) == LocalDateTime.parse("2019-12-31T23:59:59.9"))
    assert(dateTime.atEndOfYear(using Seconds)              == LocalDateTime.parse("2019-12-31T23:59:59"))
    assert(dateTime.atEndOfYear(using Minutes)              == LocalDateTime.parse("2019-12-31T23:59"))
    assert(dateTime.atEndOfYear(using Hours)                == LocalDateTime.parse("2019-12-31T23:00"))
  }

  it should "create iterator to other date-time (inclusive)(by period)" in {
    val other = LocalDateTime.parse("2019-07-13T12:38:45.123456789")

    var iter = dateTime.iterateTo(other, Period.ofDays(1))
    assert(iter.next() == dateTime)
    assert(iter.next() == LocalDateTime.parse("2019-07-12T12:38:45.123456789"))
    assert(iter.next() == other)
    assert(!iter.hasNext)
    assertThrows[NoSuchElementException](iter.next())

    iter = dateTime.iterateTo(other, Period.ofDays(2))
    assert(iter.next() == dateTime)
    assert(iter.next() == other)
    assert(!iter.hasNext)
    assertThrows[NoSuchElementException](iter.next())

    iter = dateTime.iterateTo(other, Period.ofDays(3))
    assert(iter.next() == dateTime)
    assert(!iter.hasNext)
    assertThrows[NoSuchElementException](iter.next())

    iter = other.iterateTo(dateTime, Period.ofDays(-1))
    assert(iter.next() == other)
    assert(iter.next() == LocalDateTime.parse("2019-07-12T12:38:45.123456789"))
    assert(iter.next() == dateTime)
    assert(!iter.hasNext)
    assertThrows[NoSuchElementException](iter.next())

    iter = other.iterateTo(dateTime, Period.ofDays(-2))
    assert(iter.next() == other)
    assert(iter.next() == dateTime)
    assert(!iter.hasNext)
    assertThrows[NoSuchElementException](iter.next())

    iter = other.iterateTo(dateTime, Period.ofDays(-3))
    assert(iter.next() == other)
    assert(!iter.hasNext)
    assertThrows[NoSuchElementException](iter.next())
  }

  it should "create iterator to other date-time (exclusive)(by period)" in {
    val other = LocalDateTime.parse("2019-07-13T12:38:45.123456789")

    var iter = dateTime.iterateUntil(other, Period.ofDays(1))
    assert(iter.next() == dateTime)
    assert(iter.next() == LocalDateTime.parse("2019-07-12T12:38:45.123456789"))
    assert(!iter.hasNext)
    assertThrows[NoSuchElementException](iter.next())

    iter = dateTime.iterateUntil(other, Period.ofDays(2))
    assert(iter.next() == dateTime)
    assert(!iter.hasNext)
    assertThrows[NoSuchElementException](iter.next())

    iter = dateTime.iterateUntil(other, Period.ofDays(3))
    assert(iter.next() == dateTime)
    assert(!iter.hasNext)
    assertThrows[NoSuchElementException](iter.next())

    iter = other.iterateUntil(dateTime, Period.ofDays(-1))
    assert(iter.next() == other)
    assert(iter.next() == LocalDateTime.parse("2019-07-12T12:38:45.123456789"))
    assert(!iter.hasNext)
    assertThrows[NoSuchElementException](iter.next())

    iter = other.iterateUntil(dateTime, Period.ofDays(-2))
    assert(iter.next() == other)
    assert(!iter.hasNext)
    assertThrows[NoSuchElementException](iter.next())

    iter = other.iterateUntil(dateTime, Period.ofDays(-3))
    assert(iter.next() == other)
    assert(!iter.hasNext)
    assertThrows[NoSuchElementException](iter.next())
  }

  it should "create iterator to other date-time (inclusive)(by duration)" in {
    val other = LocalDateTime.parse("2019-07-11T12:40:45.123456789")

    var iter = dateTime.iterateTo(other, Duration.ofMinutes(1))
    assert(iter.next() == dateTime)
    assert(iter.next() == LocalDateTime.parse("2019-07-11T12:39:45.123456789"))
    assert(iter.next() == other)
    assert(!iter.hasNext)
    assertThrows[NoSuchElementException](iter.next())

    iter = dateTime.iterateTo(other, Duration.ofMinutes(2))
    assert(iter.next() == dateTime)
    assert(iter.next() == other)
    assert(!iter.hasNext)
    assertThrows[NoSuchElementException](iter.next())

    iter = dateTime.iterateTo(other, Duration.ofMinutes(3))
    assert(iter.next() == dateTime)
    assert(!iter.hasNext)
    assertThrows[NoSuchElementException](iter.next())

    iter = other.iterateTo(dateTime, Duration.ofMinutes(-1))
    assert(iter.next() == other)
    assert(iter.next() == LocalDateTime.parse("2019-07-11T12:39:45.123456789"))
    assert(iter.next() == dateTime)
    assert(!iter.hasNext)
    assertThrows[NoSuchElementException](iter.next())

    iter = other.iterateTo(dateTime, Duration.ofMinutes(-2))
    assert(iter.next() == other)
    assert(iter.next() == dateTime)
    assert(!iter.hasNext)
    assertThrows[NoSuchElementException](iter.next())

    iter = other.iterateTo(dateTime, Duration.ofMinutes(-3))
    assert(iter.next() == other)
    assert(!iter.hasNext)
    assertThrows[NoSuchElementException](iter.next())
  }

  it should "create iterator to other date-time (exclusive)(by duration)" in {
    val other = LocalDateTime.parse("2019-07-11T12:40:45.123456789")

    var iter = dateTime.iterateUntil(other, Duration.ofMinutes(1))
    assert(iter.next() == dateTime)
    assert(iter.next() == LocalDateTime.parse("2019-07-11T12:39:45.123456789"))
    assert(!iter.hasNext)
    assertThrows[NoSuchElementException](iter.next())

    iter = dateTime.iterateUntil(other, Duration.ofMinutes(2))
    assert(iter.next() == dateTime)
    assert(!iter.hasNext)
    assertThrows[NoSuchElementException](iter.next())

    iter = dateTime.iterateUntil(other, Duration.ofMinutes(3))
    assert(iter.next() == dateTime)
    assert(!iter.hasNext)
    assertThrows[NoSuchElementException](iter.next())

    iter = other.iterateUntil(dateTime, Duration.ofMinutes(-1))
    assert(iter.next() == other)
    assert(iter.next() == LocalDateTime.parse("2019-07-11T12:39:45.123456789"))
    assert(!iter.hasNext)
    assertThrows[NoSuchElementException](iter.next())

    iter = other.iterateUntil(dateTime, Duration.ofMinutes(-2))
    assert(iter.next() == other)
    assert(!iter.hasNext)
    assertThrows[NoSuchElementException](iter.next())

    iter = other.iterateUntil(dateTime, Duration.ofMinutes(-3))
    assert(iter.next() == other)
    assert(!iter.hasNext)
    assertThrows[NoSuchElementException](iter.next())
  }
