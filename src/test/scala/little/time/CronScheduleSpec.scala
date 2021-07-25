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

import java.time.{ DayOfWeek, LocalTime, Month }
import DayOfWeek.*
import Month.*

import Implicits.TimeStringType

class CronScheduleSpec extends org.scalatest.flatspec.AnyFlatSpec:
  it should "create monthly schedule" in {
    val schedule = CronSchedule(
      times       = Seq("12:00".toLocalTime, "18:00".toLocalTime),
      daysOfMonth = Seq(15, 31),
      months      = Seq(FEBRUARY, APRIL, JUNE, AUGUST, OCTOBER, DECEMBER),
      daysOfWeek  = Nil)

    val between = schedule.between("2020-10-15T12:01".toLocalDateTime, "2020-12-15T12:00".toLocalDateTime)
    assert(between.next() == "2020-10-15T18:00".toLocalDateTime)
    assert(between.next() == "2020-10-31T12:00".toLocalDateTime)
    assert(between.next() == "2020-10-31T18:00".toLocalDateTime)
    assert(between.next() == "2020-12-15T12:00".toLocalDateTime)
    assertThrows[NoSuchElementException](between.next())

    var next = schedule.next("2020-10-31T18:00".toLocalDateTime)
    assert(next.contains("2020-12-15T12:00".toLocalDateTime))

    next = schedule.next(next.get)
    assert(next.contains("2020-12-15T18:00".toLocalDateTime))

    next = schedule.next(next.get)
    assert(next.contains("2020-12-31T12:00".toLocalDateTime))

    next = schedule.next(next.get)
    assert(next.contains("2020-12-31T18:00".toLocalDateTime))

    next = schedule.next(next.get)
    assert(next.contains("2021-02-15T12:00".toLocalDateTime))
  }

  it should "create weekly schedule" in {
    val schedule = CronSchedule(
      times       = Seq("12:00".toLocalTime, "18:00".toLocalTime),
      daysOfMonth = Nil,
      months      = Nil,
      daysOfWeek  = Seq(FRIDAY, SATURDAY))

    val between = schedule.between("2020-10-17T12:00".toLocalDateTime, "2020-11-13T00:00".toLocalDateTime)
    assert(between.next() == "2020-10-17T12:00".toLocalDateTime)
    assert(between.next() == "2020-10-17T18:00".toLocalDateTime)
    assert(between.next() == "2020-10-23T12:00".toLocalDateTime)
    assert(between.next() == "2020-10-23T18:00".toLocalDateTime)
    assert(between.next() == "2020-10-24T12:00".toLocalDateTime)
    assert(between.next() == "2020-10-24T18:00".toLocalDateTime)
    assert(between.next() == "2020-10-30T12:00".toLocalDateTime)
    assert(between.next() == "2020-10-30T18:00".toLocalDateTime)
    assert(between.next() == "2020-10-31T12:00".toLocalDateTime)
    assert(between.next() == "2020-10-31T18:00".toLocalDateTime)
    assert(between.next() == "2020-11-06T12:00".toLocalDateTime)
    assert(between.next() == "2020-11-06T18:00".toLocalDateTime)
    assert(between.next() == "2020-11-07T12:00".toLocalDateTime)
    assert(between.next() == "2020-11-07T18:00".toLocalDateTime)
    assertThrows[NoSuchElementException](between.next())

    val next = schedule.next("2020-10-17T12:00".toLocalDateTime)
    assert(next.contains("2020-10-17T18:00".toLocalDateTime))
  }

  it should "create daily schedule" in {
    val schedule = CronSchedule(
      times       = Seq("12:00:01".toLocalTime, "18:00:01".toLocalTime),
      months      = Nil,
      daysOfMonth = Nil,
      daysOfWeek  = Nil)

    val between = schedule.between("2020-01-27T12:00:01.111".toLocalDateTime, "2020-02-02T12:00:00.999".toLocalDateTime)
    assert(between.next() == "2020-01-27T18:00:01".toLocalDateTime)
    assert(between.next() == "2020-01-28T12:00:01".toLocalDateTime)
    assert(between.next() == "2020-01-28T18:00:01".toLocalDateTime)
    assert(between.next() == "2020-01-29T12:00:01".toLocalDateTime)
    assert(between.next() == "2020-01-29T18:00:01".toLocalDateTime)
    assert(between.next() == "2020-01-30T12:00:01".toLocalDateTime)
    assert(between.next() == "2020-01-30T18:00:01".toLocalDateTime)
    assert(between.next() == "2020-01-31T12:00:01".toLocalDateTime)
    assert(between.next() == "2020-01-31T18:00:01".toLocalDateTime)
    assert(between.next() == "2020-02-01T12:00:01".toLocalDateTime)
    assert(between.next() == "2020-02-01T18:00:01".toLocalDateTime)
    assertThrows[NoSuchElementException](between.next())

    val next = schedule.next("2020-01-27T18:00:01".toLocalDateTime)
    assert(next.contains("2020-01-28T12:00:01".toLocalDateTime))
 }

  it should "create empty schedule" in {
    val schedule = CronSchedule(
      times       = Seq("00:00".toLocalTime),
      months      = Nil,
      daysOfMonth = Nil,
      daysOfWeek  = Nil)

    val between = schedule.between("2020-03-31T00:00".toLocalDateTime, "2020-01-01T12:00".toLocalDateTime)
    assert(between.isEmpty)
  }

  it should "create schedule from formatted fields" in {
    val cron1 = CronSchedule("0 0,12 1,15,28-31 * *")
    assert(cron1.times       == Seq(LocalTime.MIDNIGHT, LocalTime.NOON))
    assert(cron1.daysOfMonth == Seq(1, 15, 28, 29, 30, 31))
    assert(cron1.months      == Nil)
    assert(cron1.daysOfWeek  == Nil)

    val cron2 = CronSchedule("*/20 8 * Jan,7,DECEMBER Mon-Fri")
    assert(cron2.times       == Seq("08:00".toLocalTime, "08:20".toLocalTime, "08:40".toLocalTime))
    assert(cron2.daysOfMonth == Nil)
    assert(cron2.months      == Seq(JANUARY, JULY,DECEMBER))
    assert(cron2.daysOfWeek  == Seq(MONDAY, TUESDAY, WEDNESDAY, THURSDAY, FRIDAY))

    val cron3 = CronSchedule("30 0 7,19 1-15/3 */4 *")
    assert(cron3.times       == Seq("07:00:30".toLocalTime, "19:00:30".toLocalTime))
    assert(cron3.daysOfMonth == Seq(1, 4, 7, 10, 13))
    assert(cron3.months      == Seq(JANUARY, MAY, SEPTEMBER))
    assert(cron3.daysOfWeek  == Nil)
  }

  it should "build schedule" in {
    var schedule = CronSchedule()

    assert(schedule.times == Seq(LocalTime.MIDNIGHT))
    assert(schedule.daysOfMonth.isEmpty)
    assert(schedule.months.isEmpty)
    assert(schedule.daysOfWeek.isEmpty)

    schedule = schedule.withTimes("16:00".toLocalTime, "08:00".toLocalTime)
      .withDaysOfMonth(1, 28, 14)
      .withMonths(JANUARY, MARCH, FEBRUARY, APRIL)
      .withDaysOfWeek(SUNDAY, SATURDAY)

    assert(schedule.times       == Seq("08:00".toLocalTime, "16:00".toLocalTime))
    assert(schedule.daysOfMonth == Seq(1, 14, 28))
    assert(schedule.months      == Seq(JANUARY, FEBRUARY, MARCH, APRIL))
    assert(schedule.daysOfWeek  == Seq(SATURDAY, SUNDAY))
  }
