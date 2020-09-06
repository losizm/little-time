/*
 * Copyright 2020 Carlos Conyers
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

import java.time._
import DayOfWeek._
import Month._

class ScheduleDateIteratorSpec extends org.scalatest.flatspec.AnyFlatSpec {
  it should "create monthly iterator" in {
    val iter = ScheduleDateIterator(
      startDate   = LocalDate.parse("2020-10-01"),
      endDate     = LocalDate.parse("2020-12-01"),
      months      = Seq(FEBRUARY, APRIL, JUNE, AUGUST, OCTOBER, DECEMBER),
      daysOfMonth = Seq(1, 15, 31),
      daysOfWeek  = Nil,
      dates       = Nil
    )

    assert(iter.nonEmpty)
    assert(iter.next() == LocalDate.parse("2020-10-01"))
    assert(iter.next() == LocalDate.parse("2020-10-15"))
    assert(iter.next() == LocalDate.parse("2020-10-31"))
    assert(iter.next() == LocalDate.parse("2020-12-01"))
    assertThrows[NoSuchElementException](iter.next())
  }

  it should "create weekly iterator" in {
    val iter = ScheduleDateIterator(
      startDate   = LocalDate.parse("2020-10-17"),
      endDate     = LocalDate.parse("2020-11-13"),
      months      = Nil,
      daysOfMonth = Nil,
      daysOfWeek  = Seq(FRIDAY, SATURDAY),
      dates       = Nil
    )

    assert(iter.nonEmpty)
    assert(iter.next() == LocalDate.parse("2020-10-17"))
    assert(iter.next() == LocalDate.parse("2020-10-23"))
    assert(iter.next() == LocalDate.parse("2020-10-24"))
    assert(iter.next() == LocalDate.parse("2020-10-30"))
    assert(iter.next() == LocalDate.parse("2020-10-31"))
    assert(iter.next() == LocalDate.parse("2020-11-06"))
    assert(iter.next() == LocalDate.parse("2020-11-07"))
    assert(iter.next() == LocalDate.parse("2020-11-13"))
    assertThrows[NoSuchElementException](iter.next())
  }

  it should "create daily iterator" in {
    val iter = ScheduleDateIterator(
      startDate   = LocalDate.parse("2020-01-27"),
      endDate     = LocalDate.parse("2020-02-02"),
      months      = Nil,
      daysOfMonth = Nil,
      daysOfWeek  = Nil,
      dates       = Nil
    )

    assert(iter.nonEmpty)
    assert(iter.next() == LocalDate.parse("2020-01-27"))
    assert(iter.next() == LocalDate.parse("2020-01-28"))
    assert(iter.next() == LocalDate.parse("2020-01-29"))
    assert(iter.next() == LocalDate.parse("2020-01-30"))
    assert(iter.next() == LocalDate.parse("2020-01-31"))
    assert(iter.next() == LocalDate.parse("2020-02-01"))
    assert(iter.next() == LocalDate.parse("2020-02-02"))
    assertThrows[NoSuchElementException](iter.next())
  }

  it should "create custom iterator" in {
    val iter = ScheduleDateIterator(
      startDate   = LocalDate.parse("2020-01-01"),
      endDate     = LocalDate.parse("2020-03-31"),
      months      = Nil,
      daysOfMonth = Nil,
      daysOfWeek  = Nil,
      dates       = Seq(
        LocalDate.parse("2019-12-15"),
        LocalDate.parse("2020-01-15"),
        LocalDate.parse("2020-01-20"),
        LocalDate.parse("2020-02-20"),
        LocalDate.parse("2020-03-30"),
        LocalDate.parse("2020-04-01"),
        LocalDate.parse("2020-04-02")
      )
    )

    assert(iter.nonEmpty)
    assert(iter.next() == LocalDate.parse("2020-01-15"))
    assert(iter.next() == LocalDate.parse("2020-01-20"))
    assert(iter.next() == LocalDate.parse("2020-02-20"))
    assert(iter.next() == LocalDate.parse("2020-03-30"))
    assertThrows[NoSuchElementException](iter.next())
  }

  it should "create empty iterator" in {
    val iter = ScheduleDateIterator(
      startDate   = LocalDate.parse("2020-03-31"),
      endDate     = LocalDate.parse("2020-01-01"), // endDate < startDate
      months      = Nil,
      daysOfMonth = Nil,
      daysOfWeek  = Nil,
      dates       = Seq(LocalDate.parse("2020-01-15"))
    )

    assert(iter.isEmpty)
    assertThrows[NoSuchElementException](iter.next())
  }
}
