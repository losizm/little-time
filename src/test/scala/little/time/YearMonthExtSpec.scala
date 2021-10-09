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

import java.time.{ Period, YearMonth }

class YearMonthExtSpec extends org.scalatest.flatspec.AnyFlatSpec:
  private val month = YearMonth.parse("2019-07")

  "YearMonth" should "be compared to other" in {
    import Ordered.orderingToOrdered
    val other1 = YearMonth.parse("2019-06")
    val other2 = YearMonth.parse("2019-07")

    assert(month >  other1)
    assert(month >= other1)
    assert(month != other1)

    assert(other1 <  month)
    assert(other1 <= month)
    assert(other1 != month)

    assert(month >= other2)
    assert(month <= other2)
    assert(month == other2)

    assert(other2 >= month)
    assert(other2 <= month)
    assert(other2 == month)
  }

  it should "have months added" in {
    assert(month +  1 == YearMonth.parse("2019-08"))
    assert(month +  2 == YearMonth.parse("2019-09"))
    assert(month + 12 == YearMonth.parse("2020-07"))

    assert(month +  -1 == YearMonth.parse("2019-06"))
    assert(month +  -2 == YearMonth.parse("2019-05"))
    assert(month + -12 == YearMonth.parse("2018-07"))
  }

  it should "have months subtracted" in {
    assert(month -  1 == YearMonth.parse("2019-06"))
    assert(month -  2 == YearMonth.parse("2019-05"))
    assert(month - 12 == YearMonth.parse("2018-07"))

    assert(month -  -1 == YearMonth.parse("2019-08"))
    assert(month -  -2 == YearMonth.parse("2019-09"))
    assert(month - -12 == YearMonth.parse("2020-07"))
  }

  it should "have period added" in {
    assert(month + Period.ofMonths(1)  == YearMonth.parse("2019-08"))
    assert(month + Period.ofMonths(2)  == YearMonth.parse("2019-09"))
    assert(month + Period.ofMonths(12) == YearMonth.parse("2020-07"))
    assert(month + Period.ofYears(2)   == YearMonth.parse("2021-07"))

    assert(month + Period.ofMonths(-1)  == YearMonth.parse("2019-06"))
    assert(month + Period.ofMonths(-2)  == YearMonth.parse("2019-05"))
    assert(month + Period.ofMonths(-12) == YearMonth.parse("2018-07"))
    assert(month + Period.ofYears(-2)   == YearMonth.parse("2017-07"))
  }

  it should "have period subtracted" in {
    assert(month - Period.ofMonths(1)  == YearMonth.parse("2019-06"))
    assert(month - Period.ofMonths(2)  == YearMonth.parse("2019-05"))
    assert(month - Period.ofMonths(12) == YearMonth.parse("2018-07"))
    assert(month - Period.ofYears(2)   == YearMonth.parse("2017-07"))

    assert(month - Period.ofMonths(-1)  == YearMonth.parse("2019-08"))
    assert(month - Period.ofMonths(-2)  == YearMonth.parse("2019-09"))
    assert(month - Period.ofMonths(-12) == YearMonth.parse("2020-07"))
    assert(month - Period.ofYears(-2)   == YearMonth.parse("2021-07"))
  }

  it should "be greater than other" in {
    val other = YearMonth.parse("2019-06")
    assert(month.max(other) == month)
    assert(other.min(month) == other)
  }

  it should "be less than other" in {
    val other = YearMonth.parse("2019-10")
    assert(month.max(other) == other)
    assert(other.min(month) == month)
  }

  it should "create iterator to other month (inclusive)" in {
    val other  = YearMonth.parse("2019-09")
    val other2 = YearMonth.parse("2021-07")

    var iter = month.iterateTo(other)
    assert(iter.next() == month)
    assert(iter.next() == YearMonth.parse("2019-08"))
    assert(iter.next() == other)
    assert(!iter.hasNext)
    assertThrows[NoSuchElementException](iter.next())

    iter = month.iterateTo(other, Period.ofMonths(2))
    assert(iter.next() == month)
    assert(iter.next() == other)
    assert(!iter.hasNext)
    assertThrows[NoSuchElementException](iter.next())

    iter = month.iterateTo(other, Period.ofMonths(3))
    assert(iter.next() == month)
    assert(!iter.hasNext)
    assertThrows[NoSuchElementException](iter.next())

    iter = other.iterateTo(month, Period.ofMonths(-1))
    assert(iter.next() == other)
    assert(iter.next() == YearMonth.parse("2019-08"))
    assert(iter.next() == month)
    assert(!iter.hasNext)
    assertThrows[NoSuchElementException](iter.next())

    iter = other.iterateTo(month, Period.ofMonths(-2))
    assert(iter.next() == other)
    assert(iter.next() == month)
    assert(!iter.hasNext)
    assertThrows[NoSuchElementException](iter.next())

    iter = other.iterateTo(month, Period.ofMonths(-3))
    assert(iter.next() == other)
    assert(!iter.hasNext)
    assertThrows[NoSuchElementException](iter.next())
  }

  it should "create iterator to other month (exclusive)" in {
    val other = YearMonth.parse("2019-09")

    var iter = month.iterateUntil(other)
    assert(iter.next() == month)
    assert(iter.next() == YearMonth.parse("2019-08"))
    assert(!iter.hasNext)
    assertThrows[NoSuchElementException](iter.next())

    iter = month.iterateUntil(other, Period.ofMonths(2))
    assert(iter.next() == month)
    assert(!iter.hasNext)
    assertThrows[NoSuchElementException](iter.next())

    iter = month.iterateUntil(other, Period.ofMonths(3))
    assert(iter.next() == month)
    assert(!iter.hasNext)
    assertThrows[NoSuchElementException](iter.next())

    iter = other.iterateUntil(month, Period.ofMonths(-1))
    assert(iter.next() == other)
    assert(iter.next() == YearMonth.parse("2019-08"))
    assert(!iter.hasNext)
    assertThrows[NoSuchElementException](iter.next())

    iter = other.iterateUntil(month, Period.ofMonths(-2))
    assert(iter.next() == other)
    assert(!iter.hasNext)
    assertThrows[NoSuchElementException](iter.next())

    iter = other.iterateUntil(month, Period.ofMonths(-3))
    assert(iter.next() == other)
    assert(!iter.hasNext)
    assertThrows[NoSuchElementException](iter.next())
  }

  it should "be set to start of year" in {
    assert(month.atStartOfYear == YearMonth.parse("2019-01"))
  }

  it should "be set to end of year" in {
    assert(month.atEndOfYear == YearMonth.parse("2019-12"))
  }
