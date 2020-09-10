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

class ScheduleSpec extends org.scalatest.flatspec.AnyFlatSpec {
  it should "create monthly schedule" in {
    val schedule = Schedule(
      start       = LocalDateTime.parse("2020-10-15T12:01"),
      end         = LocalDateTime.parse("2020-12-15T12:00"),
      months      = Seq(FEBRUARY, APRIL, JUNE, AUGUST, OCTOBER, DECEMBER),
      daysOfMonth = Seq(15, 31),
      daysOfWeek  = Nil,
      dates       = Nil,
      times       = Seq(LocalTime.parse("12:00"), LocalTime.parse("18:00"))
    )

    assert(schedule.nonEmpty)
    assert(schedule.size == 4)
    assert(schedule.head == LocalDateTime.parse("2020-10-15T18:00"))
    assert(schedule.last == LocalDateTime.parse("2020-12-15T12:00"))

    assert(schedule.drop(1).head == LocalDateTime.parse("2020-10-31T12:00"))
    assert(schedule.drop(2).head == LocalDateTime.parse("2020-10-31T18:00"))
    assert(schedule.drop(3).head == LocalDateTime.parse("2020-12-15T12:00"))
    assert(schedule.drop(4).isEmpty)

    assert(schedule.next(schedule.head).contains(LocalDateTime.parse("2020-10-31T12:00")))
    assert(schedule.next(schedule.last).isEmpty)
  }

  it should "create weekly schedule" in {
    val schedule = Schedule(
      start       = LocalDateTime.parse("2020-10-17T12:00"),
      end         = LocalDateTime.parse("2020-11-13T00:00"),
      months      = Nil,
      daysOfMonth = Nil,
      daysOfWeek  = Seq(FRIDAY, SATURDAY),
      dates       = Nil,
      times       = Seq(LocalTime.parse("12:00"), LocalTime.parse("18:00"))
    )

    assert(schedule.nonEmpty)
    assert(schedule.size == 14)
    assert(schedule.head == LocalDateTime.parse("2020-10-17T12:00"))
    assert(schedule.last == LocalDateTime.parse("2020-11-07T18:00"))

    assert(schedule.drop(1).head  == LocalDateTime.parse("2020-10-17T18:00"))
    assert(schedule.drop(2).head  == LocalDateTime.parse("2020-10-23T12:00"))
    assert(schedule.drop(3).head  == LocalDateTime.parse("2020-10-23T18:00"))
    assert(schedule.drop(4).head  == LocalDateTime.parse("2020-10-24T12:00"))
    assert(schedule.drop(5).head  == LocalDateTime.parse("2020-10-24T18:00"))
    assert(schedule.drop(6).head  == LocalDateTime.parse("2020-10-30T12:00"))
    assert(schedule.drop(7).head  == LocalDateTime.parse("2020-10-30T18:00"))
    assert(schedule.drop(8).head  == LocalDateTime.parse("2020-10-31T12:00"))
    assert(schedule.drop(9).head  == LocalDateTime.parse("2020-10-31T18:00"))
    assert(schedule.drop(10).head == LocalDateTime.parse("2020-11-06T12:00"))
    assert(schedule.drop(11).head == LocalDateTime.parse("2020-11-06T18:00"))
    assert(schedule.drop(12).head == LocalDateTime.parse("2020-11-07T12:00"))
    assert(schedule.drop(13).head == LocalDateTime.parse("2020-11-07T18:00"))
    assert(schedule.drop(14).isEmpty)

    assert(schedule.next(schedule.head).contains(LocalDateTime.parse("2020-10-17T18:00")))
    assert(schedule.next(schedule.last).isEmpty)
  }

  it should "create daily schedule" in {
    val schedule = Schedule(
      start       = LocalDateTime.parse("2020-01-27T12:00:01.111"),
      end         = LocalDateTime.parse("2020-02-02T12:00:00.999"),
      months      = Nil,
      daysOfMonth = Nil,
      daysOfWeek  = Nil,
      dates       = Nil,
      times       = Seq(LocalTime.parse("12:00:01"), LocalTime.parse("18:00:01"))
    )

    assert(schedule.nonEmpty)
    assert(schedule.size == 11)
    assert(schedule.head == LocalDateTime.parse("2020-01-27T18:00:01"))
    assert(schedule.last == LocalDateTime.parse("2020-02-01T18:00:01"))

    assert(schedule.drop(1).head  == LocalDateTime.parse("2020-01-28T12:00:01"))
    assert(schedule.drop(2).head  == LocalDateTime.parse("2020-01-28T18:00:01"))
    assert(schedule.drop(3).head  == LocalDateTime.parse("2020-01-29T12:00:01"))
    assert(schedule.drop(4).head  == LocalDateTime.parse("2020-01-29T18:00:01"))
    assert(schedule.drop(5).head  == LocalDateTime.parse("2020-01-30T12:00:01"))
    assert(schedule.drop(6).head  == LocalDateTime.parse("2020-01-30T18:00:01"))
    assert(schedule.drop(7).head  == LocalDateTime.parse("2020-01-31T12:00:01"))
    assert(schedule.drop(8).head  == LocalDateTime.parse("2020-01-31T18:00:01"))
    assert(schedule.drop(9).head  == LocalDateTime.parse("2020-02-01T12:00:01"))
    assert(schedule.drop(10).head == LocalDateTime.parse("2020-02-01T18:00:01"))
    assert(schedule.drop(11).isEmpty)

    assert(schedule.next(schedule.head).contains(LocalDateTime.parse("2020-01-28T12:00:01")))
    assert(schedule.next(schedule.last).isEmpty)
  }

  it should "create custom schedule" in {
    val schedule = Schedule(
      start       = LocalDateTime.parse("2020-01-01T12:00"),
      end         = LocalDateTime.parse("2020-03-31T12:00"),
      months      = Nil,
      daysOfMonth = Nil,
      daysOfWeek  = Nil,
      dates       = Seq(LocalDate.parse("2019-12-15"), LocalDate.parse("2020-01-15")),
      times       = Seq(LocalTime.parse("12:00:01"), LocalTime.parse("12:00:02"))
    )

    assert(schedule.nonEmpty)
    assert(schedule.size == 2)
    assert(schedule.head == LocalDateTime.parse("2020-01-15T12:00:01"))
    assert(schedule.last == LocalDateTime.parse("2020-01-15T12:00:02"))

    assert(schedule.next(schedule.head).contains(LocalDateTime.parse("2020-01-15T12:00:02")))
    assert(schedule.next(schedule.last).isEmpty)
  }

  it should "create empty schedule" in {
    val schedule = Schedule(
      start       = LocalDateTime.parse("2020-03-31T00:00"),
      end         = LocalDateTime.parse("2020-01-01T12:00"), // end < start
      months      = Nil,
      daysOfMonth = Nil,
      daysOfWeek  = Nil,
      dates       = Seq(LocalDate.parse("2020-01-15")),
      times       = Seq(LocalTime.parse("00:00"))
    )

    assert(schedule.isEmpty)
  }

  it should "build schedule" in {
    var schedule = Schedule(
      start = LocalDateTime.parse("2020-01-01T12:00"),
      end   = LocalDateTime.parse("2020-03-31T12:00")
    )

    assert(schedule.start == LocalDateTime.parse("2020-01-01T12:00"))
    assert(schedule.end   == LocalDateTime.parse("2020-03-31T12:00"))
    assert(schedule.times == Seq(LocalTime.MIDNIGHT))
    assert(schedule.daysOfMonth.isEmpty)
    assert(schedule.months.isEmpty)
    assert(schedule.daysOfWeek.isEmpty)
    assert(schedule.dates.isEmpty)

    schedule = schedule.withStart(LocalDateTime.parse("2020-01-15T12:00"))
      .withEnd(LocalDateTime.parse("2020-03-15T00:00"))
      .withTimes(LocalTime.parse("16:00"), LocalTime.parse("08:00"))
      .withDaysOfMonth(1, 28, 14)
      .withMonths(JANUARY, MARCH, FEBRUARY, APRIL)
      .withDaysOfWeek(SUNDAY, SATURDAY)
      .withDates(LocalDate.parse("2020-03-03"), LocalDate.parse("2020-02-02"))

    assert(schedule.start       == LocalDateTime.parse("2020-01-15T12:00"))
    assert(schedule.end         == LocalDateTime.parse("2020-03-15T00:00"))
    assert(schedule.times       == Seq(LocalTime.parse("08:00"), LocalTime.parse("16:00")))
    assert(schedule.daysOfMonth == Seq(1, 14, 28))
    assert(schedule.months      == Seq(JANUARY, FEBRUARY, MARCH, APRIL))
    assert(schedule.daysOfWeek  == Seq(SATURDAY, SUNDAY))
    assert(schedule.dates       == Seq(LocalDate.parse("2020-02-02"), LocalDate.parse("2020-03-03")))

    schedule = schedule.withEffective(
      LocalDateTime.parse("2020-01-01T00:00"),
      LocalDateTime.parse("2020-12-31T23:59")
    )

    assert(schedule.start       == LocalDateTime.parse("2020-01-01T00:00"))
    assert(schedule.end         == LocalDateTime.parse("2020-12-31T23:59"))
    assert(schedule.times       == Seq(LocalTime.parse("08:00"), LocalTime.parse("16:00")))
    assert(schedule.daysOfMonth == Seq(1, 14, 28))
    assert(schedule.months      == Seq(JANUARY, FEBRUARY, MARCH, APRIL))
    assert(schedule.daysOfWeek  == Seq(SATURDAY, SUNDAY))
    assert(schedule.dates       == Seq(LocalDate.parse("2020-02-02"), LocalDate.parse("2020-03-03")))
  }
}
