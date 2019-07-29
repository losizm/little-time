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
    assert(dateTime.atStartOfMicrosecond == LocalDateTime.parse("2019-07-11T12:38:45.123456"))
  }

  it should "be truncated to millisecond" in {
    assert(dateTime.atStartOfMillisecond == LocalDateTime.parse("2019-07-11T12:38:45.123"))
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
}

