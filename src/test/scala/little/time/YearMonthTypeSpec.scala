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

import java.time.YearMonth

import org.scalatest.FlatSpec

import Implicits._

class YearMonthTypeSpec extends FlatSpec {
  private val month = YearMonth.parse("2019-07")

  "YearMonth" should "be compared to other" in {
    import Ordered.orderingToOrdered
    val other1 = YearMonth.parse("2019-06")
    val other2 = YearMonth.parse("2019-07")
    
    assert(month > other1)
    assert(month >= other1)
    assert(month != other1)

    assert(other1 < month)
    assert(other1 <= month)
    assert(other1 != month)
    
    assert(month >= other2)
    assert(month <= other2)
    assert(month == other2)

    assert(other2 >= month)
    assert(other2 <= month)
    assert(other2 == month)
  }

  it should "have days added" in {
    assert(month + 1 == YearMonth.parse("2019-08"))
    assert(month + 2 == YearMonth.parse("2019-09"))
    assert(month + 12 == YearMonth.parse("2020-07"))

    assert(month + -1 == YearMonth.parse("2019-06"))
    assert(month + -2 == YearMonth.parse("2019-05"))
    assert(month + -12 == YearMonth.parse("2018-07"))
  }

  it should "have days subtracted" in {
    assert(month - 1 == YearMonth.parse("2019-06"))
    assert(month - 2 == YearMonth.parse("2019-05"))
    assert(month - 12 == YearMonth.parse("2018-07"))

    assert(month - -1 == YearMonth.parse("2019-08"))
    assert(month - -2 == YearMonth.parse("2019-09"))
    assert(month - -12 == YearMonth.parse("2020-07"))
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

  it should "create iterator to end month (inclusive)" in {
    val end = YearMonth.parse("2019-09")
    val iter = month.to(end, true)
    
    assert(iter.next() == month)
    assert(iter.next() == YearMonth.parse("2019-08"))
    assert(iter.next() == end)
    assert(!iter.hasNext)
    assertThrows[NoSuchElementException](iter.next())
  }

  it should "create iterator to end month (exclusive)" in {
    val end = YearMonth.parse("2019-09")
    val iter = month.to(end, false)
    
    assert(iter.next() == month)
    assert(iter.next() == YearMonth.parse("2019-08"))
    assert(!iter.hasNext)
    assertThrows[NoSuchElementException](iter.next())
  }
}

