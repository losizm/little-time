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

import Implicits.TimeStringType

class ScheduleSpec extends org.scalatest.flatspec.AnyFlatSpec:
  it should "create schedule" in {
    val schedule = Schedule(
      "2020-10-31T12:00".toLocalDateTime,
      "2020-12-15T12:00".toLocalDateTime,
      "2020-10-31T18:00".toLocalDateTime,
      "2020-10-15T18:00".toLocalDateTime)

    val between = schedule.between("2020-10-15T12:01".toLocalDateTime, "2020-12-15T12:00".toLocalDateTime)
    assert(between.next() == "2020-10-15T18:00".toLocalDateTime)
    assert(between.next() == "2020-10-31T12:00".toLocalDateTime)
    assert(between.next() == "2020-10-31T18:00".toLocalDateTime)
    assert(between.next() == "2020-12-15T12:00".toLocalDateTime)
    assertThrows[NoSuchElementException](between.next())

    var next = schedule.next("2020-10-31T18:00".toLocalDateTime)
    assert(next.contains("2020-12-15T12:00".toLocalDateTime))

    next = schedule.next(next.get)
    assert(next.isEmpty)
  }

  it should "combine schedules" in {
    val schedule = Schedule(
      "2020-10-15T18:00".toLocalDateTime,
      "2020-10-31T12:00".toLocalDateTime,
      "2020-10-31T18:00".toLocalDateTime,
      "2020-12-15T12:00".toLocalDateTime) ++
      Schedule(
      "2020-10-15T18:00".toLocalDateTime,
      "2020-10-31T11:00".toLocalDateTime,
      "2020-10-31T18:30".toLocalDateTime,
      "2020-12-15T00:00".toLocalDateTime)

    val between = schedule.between("2020-10-15T12:01".toLocalDateTime, "2020-12-15T12:00".toLocalDateTime)
    assert(between.next() == "2020-10-15T18:00".toLocalDateTime)
    assert(between.next() == "2020-10-31T11:00".toLocalDateTime)
    assert(between.next() == "2020-10-31T12:00".toLocalDateTime)
    assert(between.next() == "2020-10-31T18:00".toLocalDateTime)
    assert(between.next() == "2020-10-31T18:30".toLocalDateTime)
    assert(between.next() == "2020-12-15T00:00".toLocalDateTime)
    assert(between.next() == "2020-12-15T12:00".toLocalDateTime)
    assertThrows[NoSuchElementException](between.next())
  }

  it should "add time to schedule" in {
    val schedule = Schedule(
      "2020-10-31T12:00".toLocalDateTime,
      "2020-12-15T12:00".toLocalDateTime,
      "2020-10-31T18:00".toLocalDateTime,
      "2020-10-15T18:00".toLocalDateTime) +
      "2020-10-15T13:00".toLocalDateTime +
      "2020-10-15T18:15".toLocalDateTime

    val between = schedule.between("2020-10-15T12:01".toLocalDateTime, "2020-12-15T12:00".toLocalDateTime)
    assert(between.next() == "2020-10-15T13:00".toLocalDateTime)
    assert(between.next() == "2020-10-15T18:00".toLocalDateTime)
    assert(between.next() == "2020-10-15T18:15".toLocalDateTime)
    assert(between.next() == "2020-10-31T12:00".toLocalDateTime)
    assert(between.next() == "2020-10-31T18:00".toLocalDateTime)
    assert(between.next() == "2020-12-15T12:00".toLocalDateTime)
    assertThrows[NoSuchElementException](between.next())
  }
