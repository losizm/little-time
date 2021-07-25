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

import java.time.{ LocalDate, LocalDateTime, LocalTime }

import Implicits.localDateTimeOrdering

/** Defines utility for scheduled times. */
trait Schedule:
  /**
   * Gets scheduled times in given period.
   *
   * @param start start of period
   * @param end   end of period
   *
   * @note Both `start` and `end` are inclusive.
   */
  def between(start: LocalDateTime, end: LocalDateTime): Iterator[LocalDateTime]

  /**
   * Gets scheduled times in given period.
   *
   * _Equivalent to:_ `between(start.atTime(LocalTime.MIN), end.atTime(LocalTime.MAX))`
   *
   * @param start start of period
   * @param end   end of period
   *
   * @note Both `start` and `end` are inclusive.
   */
  def between(start: LocalDate, end: LocalDate): Iterator[LocalDateTime] =
    between(start.atTime(LocalTime.MIN), end.atTime(LocalTime.MAX))

  /**
   * Gets next scheduled time after given time.
   *
   * @param after time after which scheduled time occurs
   */
  def next(after: LocalDateTime = LocalDateTime.now()): Option[LocalDateTime] =
    val iter = between(after.plusNanos(1), LocalDateTime.MAX)

    iter.hasNext match
      case true  => Some(iter.next())
      case false => None

  /**
   * Creates new schedule by combining supplied schedule.
   *
   * @param other schedule
   *
   * @return new schedule
   */
  def combine(other: Schedule): Schedule =
    CombinedSchedule(this, other)

  /**
   * Creates new schedule by adding supplied scheduled time.
   *
   * @param time schedule time
   *
   * @return new schedule
   */
  def add(time: LocalDateTime): Schedule =
    CombinedSchedule(this, Schedule(time))

  /**
   * Creates new schedule by combining supplied schedule.
   *
   * @param other schedule
   *
   * @return new schedule
   *
   * @note Alias to `combine`
   */
  def ++(other: Schedule): Schedule = combine(other)

  /**
   * Creates new schedule by adding supplied scheduled time.
   *
   * @param time schedule time
   *
   * @return new schedule
   *
   * @note Alias to `add`
   */
  def +(time: LocalDateTime): Schedule = add(time)

/** Provides `Schedule` factory. */
object Schedule:
  /**
   * Creates schedule with supplied times.
   *
   * @param times schedule times
   */
  def apply(times: Seq[LocalDateTime]): Schedule =
    SeqSchedule(SortedSeq(times))

  /**
   * Creates schedule with supplied times.
   *
   * @param one  schedule time
   * @param more additional scheduled times
   */
  def apply(one: LocalDateTime, more: LocalDateTime*): Schedule =
    SeqSchedule(SortedSeq(one +: more))
