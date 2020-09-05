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

import java.time.{ DayOfWeek, LocalDate, Period, YearMonth }

import Implicits._
import DayOfWeek._

class LocalDateTypeSpec extends org.scalatest.flatspec.AnyFlatSpec {
  private val date =  LocalDate.parse("2019-07-11")

  "LocalDate" should "be converted to YearMonth" in {
    assert(date.toYearMonth == YearMonth.parse("2019-07"))
  }

  it should "have days added" in {
    assert(date + 1 == LocalDate.parse("2019-07-12"))
    assert(date + 2 == LocalDate.parse("2019-07-13"))
    assert(date + 31 == LocalDate.parse("2019-08-11"))

    assert(date + -1 == LocalDate.parse("2019-07-10"))
    assert(date + -2 == LocalDate.parse("2019-07-09"))
    assert(date + -11 == LocalDate.parse("2019-06-30"))
  }

  it should "have days subtracted" in {
    assert(date - 1 == LocalDate.parse("2019-07-10"))
    assert(date - 2 == LocalDate.parse("2019-07-09"))
    assert(date - 11 == LocalDate.parse("2019-06-30"))

    assert(date - -1 == LocalDate.parse("2019-07-12"))
    assert(date - -2 == LocalDate.parse("2019-07-13"))
    assert(date - -31 == LocalDate.parse("2019-08-11"))
  }

  it should "have period added" in {
    assert(date + Period.ofDays(1) == LocalDate.parse("2019-07-12"))
    assert(date + Period.ofDays(2) == LocalDate.parse("2019-07-13"))
    assert(date + Period.ofDays(31) == LocalDate.parse("2019-08-11"))
    assert(date + Period.ofMonths(5) == LocalDate.parse("2019-12-11"))
    assert(date + Period.ofWeeks(2) == LocalDate.parse("2019-07-25"))
    assert(date + Period.of(6, 3, 7) == LocalDate.parse("2025-10-18"))

    assert(date + Period.ofDays(-1) == LocalDate.parse("2019-07-10"))
    assert(date + Period.ofDays(-2) == LocalDate.parse("2019-07-09"))
    assert(date + Period.ofDays(-11) == LocalDate.parse("2019-06-30"))
    assert(date + Period.ofMonths(-5) == LocalDate.parse("2019-02-11"))
    assert(date + Period.ofWeeks(-2) == LocalDate.parse("2019-06-27"))
    assert(date + Period.of(-6, -3, -7) == LocalDate.parse("2013-04-04"))
  }

  it should "have period subtracted" in {
    assert(date - Period.ofDays(1) == LocalDate.parse("2019-07-10"))
    assert(date - Period.ofDays(2) == LocalDate.parse("2019-07-09"))
    assert(date - Period.ofDays(11) == LocalDate.parse("2019-06-30"))
    assert(date - Period.ofMonths(5) == LocalDate.parse("2019-02-11"))
    assert(date - Period.ofWeeks(2) == LocalDate.parse("2019-06-27"))
    assert(date - Period.of(6, 3, 7) == LocalDate.parse("2013-04-04"))

    assert(date - Period.ofDays(-1) == LocalDate.parse("2019-07-12"))
    assert(date - Period.ofDays(-2) == LocalDate.parse("2019-07-13"))
    assert(date - Period.ofDays(-31) == LocalDate.parse("2019-08-11"))
    assert(date - Period.ofMonths(-5) == LocalDate.parse("2019-12-11"))
    assert(date - Period.ofWeeks(-2) == LocalDate.parse("2019-07-25"))
    assert(date - Period.of(-6, -3, -7) == LocalDate.parse("2025-10-18"))
  }

  it should "be compared to other" in {
    import Ordered.orderingToOrdered
    val other1 = LocalDate.parse("2019-06-05")
    val other2 = LocalDate.parse("2019-07-11")

    assert(date > other1)
    assert(date >= other1)
    assert(date != other1)

    assert(other1 < date)
    assert(other1 <= date)
    assert(other1 != date)

    assert(date >= other2)
    assert(date <= other2)
    assert(date == other2)

    assert(other2 >= date)
    assert(other2 <= date)
    assert(other2 == date)
  }

  it should "be greater than other" in {
    val other = LocalDate.parse("2019-06-05")
    assert(date.max(other) == date)
    assert(other.min(date) == other)
  }

  it should "be less than other" in {
    val other = LocalDate.parse("2019-10-23")
    assert(date.max(other) == other)
    assert(other.min(date) == date)
  }

  it should "create iterator to other date (inclusive)" in {
    val other = LocalDate.parse("2019-07-13")

    var iter = date.iterateTo(other)
    assert(iter.next() == date)
    assert(iter.next() == LocalDate.parse("2019-07-12"))
    assert(iter.next() == other)
    assert(!iter.hasNext)
    assertThrows[NoSuchElementException](iter.next())

    iter = date.iterateTo(other, Period.ofDays(2))
    assert(iter.next() == date)
    assert(iter.next() == other)
    assert(!iter.hasNext)
    assertThrows[NoSuchElementException](iter.next())

    iter = date.iterateTo(other, Period.ofDays(3))
    assert(iter.next() == date)
    assert(!iter.hasNext)
    assertThrows[NoSuchElementException](iter.next())

    iter = other.iterateTo(date, Period.ofDays(-1))
    assert(iter.next() == other)
    assert(iter.next() == LocalDate.parse("2019-07-12"))
    assert(iter.next() == date)
    assert(!iter.hasNext)
    assertThrows[NoSuchElementException](iter.next())

    iter = other.iterateTo(date, Period.ofDays(-2))
    assert(iter.next() == other)
    assert(iter.next() == date)
    assert(!iter.hasNext)
    assertThrows[NoSuchElementException](iter.next())

    iter = other.iterateTo(date, Period.ofDays(-3))
    assert(iter.next() == other)
    assert(!iter.hasNext)
    assertThrows[NoSuchElementException](iter.next())
  }

  it should "create iterator to other date (exclusive)" in {
    val other = LocalDate.parse("2019-07-13")

    var iter = date.iterateUntil(other)
    assert(iter.next() == date)
    assert(iter.next() == LocalDate.parse("2019-07-12"))
    assert(!iter.hasNext)
    assertThrows[NoSuchElementException](iter.next())

    iter = date.iterateUntil(other, Period.ofDays(2))
    assert(iter.next() == date)
    assert(!iter.hasNext)
    assertThrows[NoSuchElementException](iter.next())

    iter = date.iterateUntil(other, Period.ofDays(3))
    assert(iter.next() == date)
    assert(!iter.hasNext)
    assertThrows[NoSuchElementException](iter.next())

    iter = other.iterateUntil(date, Period.ofDays(-1))
    assert(iter.next() == other)
    assert(iter.next() == LocalDate.parse("2019-07-12"))
    assert(!iter.hasNext)
    assertThrows[NoSuchElementException](iter.next())

    iter = other.iterateUntil(date, Period.ofDays(-2))
    assert(iter.next() == other)
    assert(!iter.hasNext)
    assertThrows[NoSuchElementException](iter.next())

    iter = other.iterateUntil(date, Period.ofDays(-3))
    assert(iter.next() == other)
    assert(!iter.hasNext)
    assertThrows[NoSuchElementException](iter.next())
  }

  it should "be set to start of week" in {
    assert(date.atStartOfWeek == LocalDate.parse("2019-07-07"))
    assert(date.atStartOfWeek(SUNDAY) == LocalDate.parse("2019-07-07"))
    assert(date.atStartOfWeek(MONDAY) == LocalDate.parse("2019-07-08"))
    assert(date.atStartOfWeek(TUESDAY) == LocalDate.parse("2019-07-09"))
    assert(date.atStartOfWeek(WEDNESDAY) == LocalDate.parse("2019-07-10"))
    assert(date.atStartOfWeek(THURSDAY) == LocalDate.parse("2019-07-11"))
    assert(date.atStartOfWeek(FRIDAY) == LocalDate.parse("2019-07-05"))
    assert(date.atStartOfWeek(SATURDAY) == LocalDate.parse("2019-07-06"))
  }

  it should "be set to end of week" in {
    assert(date.atEndOfWeek == LocalDate.parse("2019-07-13"))
    assert(date.atEndOfWeek(SUNDAY) == LocalDate.parse("2019-07-14"))
    assert(date.atEndOfWeek(MONDAY) == LocalDate.parse("2019-07-15"))
    assert(date.atEndOfWeek(TUESDAY) == LocalDate.parse("2019-07-16"))
    assert(date.atEndOfWeek(WEDNESDAY) == LocalDate.parse("2019-07-17"))
    assert(date.atEndOfWeek(THURSDAY) == LocalDate.parse("2019-07-11"))
    assert(date.atEndOfWeek(FRIDAY) == LocalDate.parse("2019-07-12"))
    assert(date.atEndOfWeek(SATURDAY) == LocalDate.parse("2019-07-13"))
  }

  it should "be set to start of month" in {
    assert(date.atStartOfMonth == LocalDate.parse("2019-07-01"))
  }

  it should "be set to end of month" in {
    assert(date.atEndOfMonth == LocalDate.parse("2019-07-31"))
  }

  it should "be set to start of year" in {
    assert(date.atStartOfYear == LocalDate.parse("2019-01-01"))
  }

  it should "be set to end of year" in {
    assert(date.atEndOfYear == LocalDate.parse("2019-12-31"))
  }
}

