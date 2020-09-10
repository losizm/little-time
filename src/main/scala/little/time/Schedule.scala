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

import scala.math.Ordered.orderingToOrdered

import Implicits._

/**
 * Defines ''cron''-like utility for specifying times at which ''something''
 * should occur.
 *
 * A `Schedule` may specify `months`, `daysOfMonth`, `daysOfWeek`, `dates`, and
 * `times`. To constrain it to a finite period, its effective `start` and `end`
 * are always specified.
 *
 * {{{
 * import java.time.DayOfWeek._
 * import java.time.LocalTime
 * import java.time.Month._
 *
 * import little.time.Schedule
 * import little.time.Implicits._
 *
 * // Create schedule that:
 * //   - starts on Jan 15
 * //   - ends before noon on Apr 15
 * //   - includes Jan, Feb, and Apr
 * //   - includes 1st and 15th of each month
 * //   - occurs at midnight and noon
 * val schedule = Schedule(
 *   start       = "2020-01-15T00:00:00".toLocalDateTime,
 *   end         = "2020-04-15T11:59:59".toLocalDateTime,
 *   months      = Seq(JANUARY, FEBRUARY, APRIL),
 *   daysOfMonth = Seq(1, 15),
 *   times       = Seq(LocalTime.MIDNIGHT, LocalTime.NOON)
 * )
 * }}}
 *
 * A `Schedule` is an `Iterable[LocalDateTime]`, so there are various ways to
 * access its times.
 *
 * {{{
 * // Zip scheduled times with indices and print each
 * schedule.zipWithIndex.foreach {
 *   case (time, index) => println(s"\$index: \$time")
 * }
 *
 * // Filter to times that are on Wednesday
 * val humpDays = schedule.filter(_.getDayOfWeek == WEDNESDAY)
 * }}}
 *
 * And there are other utilities provided for working with a schedule.
 *
 * {{{
 * // Add Mondays and Fridays to schedule
 * val moreDays = schedule.withDaysOfWeek(MONDAY, FRIDAY)
 *
 * // Get next scheduled time after specified time
 * val nextTime = moreDays.next("2020-02-01T12:00:01".toLocalDateTime)
 * }}}
 */
sealed trait Schedule extends Iterable[LocalDateTime] {
  /** Gets effective start. */
  def start: LocalDateTime

  /** Gets effective end. */
  def end: LocalDateTime

  /** Gets times. */
  def times: Seq[LocalTime]

  /** Gets days of month. */
  def daysOfMonth: Seq[Int]

  /** Gets months. */
  def months: Seq[Month]

  /** Gets days of week. */
  def daysOfWeek: Seq[DayOfWeek]

  /** Gets dates. */
  def dates: Seq[LocalDate]

  /** Gets iterator of scheduled times. */
  def iterator: Iterator[LocalDateTime]

  /**
   * Gets next scheduled time.
   *
   * @param after time after which to check
   */
  def next(after: LocalDateTime = LocalDateTime.now()): Option[LocalDateTime] =
    iterator.find(after.isBefore)

  /**
   * Creates new schedule by replacing start and end.
   *
   * @param start start
   * @param end end
   *
   * @return new schedule
   */
  def withEffective(start: LocalDateTime, end: LocalDateTime): Schedule

  /**
   * Creates new schedule by replacing start.
   *
   * @param start start
   *
   * @return new schedule
   */
  def withStart(start: LocalDateTime): Schedule

  /**
   * Creates new schedule by replacing end.
   *
   * @param end end
   *
   * @return new schedule
   */
  def withEnd(end: LocalDateTime): Schedule

  /**
   * Creates new schedule by replacing times.
   *
   * @param times times
   *
   * @return new schedule
   */
  def withTimes(times: Seq[LocalTime]): Schedule

  /**
   * Creates new schedule by replacing times.
   *
   * @param one  time
   * @param more additional times
   *
   * @return new schedule
   */
  def withTimes(one: LocalTime, more: LocalTime*): Schedule =
    withTimes(one +: more)

  /**
   * Creates new schedule by replacing days of month.
   *
   * @param days days of month
   *
   * @return new schedule
   */
  def withDaysOfMonth(days: Seq[Int]): Schedule

  /**
   * Creates new schedule by replacing days of month.
   *
   * @param one  day of month
   * @param more additional days of month
   *
   * @return new schedule
   */
  def withDaysOfMonth(one: Int, more: Int*): Schedule =
    withDaysOfMonth(one +: more)

  /**
   * Creates new schedule by replacing months.
   *
   * @param months months
   *
   * @return new schedule
   */
  def withMonths(months: Seq[Month]): Schedule

  /**
   * Creates new schedule by replacing months.
   *
   * @param one  month
   * @param more additional months
   *
   * @return new schedule
   */
  def withMonths(one: Month, more: Month*): Schedule =
    withMonths(one +: more)

  /**
   * Creates new schedule by replacing days of week.
   *
   * @param days days of week
   *
   * @return new schedule
   */
  def withDaysOfWeek(days: Seq[DayOfWeek]): Schedule

  /**
   * Creates new schedule by replacing days of week.
   *
   * @param one  day of week
   * @param more additional days of week
   *
   * @return new schedule
   */
  def withDaysOfWeek(one: DayOfWeek, more: DayOfWeek*): Schedule =
    withDaysOfWeek(one +: more)

  /**
   * Creates new schedule by replacing dates.
   *
   * @param dates dates
   *
   * @return new schedule
   */
  def withDates(dates: Seq[LocalDate]): Schedule

  /**
   * Creates new schedule by replacing dates.
   *
   * @param one  date
   * @param more additional dates
   *
   * @return new schedule
   */
  def withDates(one: LocalDate, more: LocalDate*): Schedule =
    withDates(one +: more)
}

/** Provides `Schedule` factory. */
object Schedule {
  /**
   * Creates schedule with supplied attributes.
   *
   * @param start       start
   * @param end         end
   * @param times       times
   * @param daysOfMonth days of month
   * @param months      months
   * @param daysOfWeek  days of week
   * @param dates       dates
   */
  def apply(
    start: LocalDateTime,
    end: LocalDateTime,
    times: Seq[LocalTime] = Seq(LocalTime.MIDNIGHT),
    daysOfMonth: Seq[Int] = Nil,
    months: Seq[Month] = Nil,
    daysOfWeek: Seq[DayOfWeek] = Nil,
    dates: Seq[LocalDate] = Nil
  ): Schedule =
    StandardSchedule(
      start,
      end,
      times.isEmpty match {
        case true  => Seq(LocalTime.MIDNIGHT)
        case false => SortedSeq(times)
      },
      SortedSeq(daysOfMonth),
      SortedSeq(months),
      SortedSeq(daysOfWeek),
      SortedSeq(dates)
    )
}

private case class StandardSchedule(
  start: LocalDateTime,
  end: LocalDateTime,
  times: Seq[LocalTime],
  daysOfMonth: Seq[Int],
  months: Seq[Month],
  daysOfWeek: Seq[DayOfWeek],
  dates: Seq[LocalDate]
) extends Schedule {
  require(start != null && end != null)
  require(daysOfMonth.forall(day => day >= 1 && day <= 31))

  private lazy val isKnownEmpty =
    dates.isEmpty && (daysOfMonth.isEmpty || months.isEmpty || daysOfWeek.nonEmpty match {
      case true  => false
      case false =>
        daysOfMonth.dropWhile(_ < 29) match {
          case Nil  => false
          case days => days.forall(day => months.forall(_.maxLength < day))
        }
    })

  def iterator = isKnownEmpty match {
    case true  => Iterator.empty
    case false =>
      dateIterator
        .flatMap(date => times.map(date.atTime))
        .filter(time => time >= start && time <= end)
  }

  def withEffective(start: LocalDateTime, end: LocalDateTime) =
    copy(start = start, end = end)

  def withStart(start: LocalDateTime) =
    copy(start = start)

  def withEnd(end: LocalDateTime) =
    copy(end = end)

  def withTimes(times: Seq[LocalTime]) =
    copy(times = times.isEmpty match {
      case true  => Seq(LocalTime.MIDNIGHT)
      case false => SortedSeq(times)
    })

  def withDaysOfMonth(days: Seq[Int]) =
    copy(daysOfMonth = SortedSeq(days))

  def withMonths(months: Seq[Month]) =
    copy(months = SortedSeq(months))

  def withDaysOfWeek(days: Seq[DayOfWeek]) =
    copy(daysOfWeek = SortedSeq(days))

  def withDates(dates: Seq[LocalDate]) =
    copy(dates = SortedSeq(dates))

  override lazy val toString = s"StandardSchedule(start=$start,end=$end,isEmpty=$isEmpty)"

  private def dateIterator =
    ScheduleDateIterator(
      start.toLocalDate,
      end.toLocalDate,
      months,
      daysOfMonth,
      daysOfWeek,
      dates
    )
}
