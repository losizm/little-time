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

import java.time.*

import scala.math.Ordered.orderingToOrdered

/**
 * Defines _cron_-like utility for scheduled times.
 *
 * ## Scheduled Times
 *
 * `CronSchedule` determines its scheduled times using fields, where each field
 * is a sorted sequence of distinct values specifying a component of time.
 *
 * The `times` field specifies local times in schedule. It must include at least
 * one value.
 *
 * The `daysOfMonth` field specifies days of month in schedule. If empty, then
 * every day is implied unless `daysOfWeek` is non-empty.
 *
 * The `months` field specifies months in schedule. If empty, then every month
 * is implied.
 *
 * The `daysOfWeek` field specifies days of week in schedule. If empty, then
 * every day is implied unless `daysOfMonth` is non-empty.
 *
 * If both `daysOfMonth` and `daysOfWeek` are non-empty, then scheduled times
 * are determined as a union of both fields.
 *
 * {{{
 * import java.time.LocalTime.NOON
 * import java.time.Month.{ OCTOBER, NOVEMBER, DECEMBER }
 *
 * import little.time.{ CronSchedule, TimeStringMethods }
 *
 * // Create schedule
 * val schedule = CronSchedule(
 *   times       = Seq(NOON),
 *   daysOfMonth = Seq(1, 15),
 *   months      = Seq(OCTOBER, NOVEMBER, DECEMBER))
 *
 * val start = "2020-10-01".toLocalDate
 * val end   = "2020-12-31".toLocalDate
 *
 * // Iterate over scheduled times
 * schedule.between(start, end).foreach { time =>
 *   println(s"\${time.toLocalDate} at \${time.toLocalTime}")
 * }
 *
 * // Create schedule using cron-like syntax
 * val altSchedule = CronSchedule("0 12 1,15 Oct-Dec *")
 * }}}
 */
sealed trait CronSchedule extends Schedule:
  /** Gets times. */
  def times: Seq[LocalTime]

  /** Gets days of month. */
  def daysOfMonth: Seq[Int]

  /** Gets months. */
  def months: Seq[Month]

  /** Gets days of week. */
  def daysOfWeek: Seq[DayOfWeek]

  /**
   * Creates new cron schedule by replacing times.
   *
   * @param times times
   *
   * @return new cron schedule
   */
  def withTimes(times: Seq[LocalTime]): CronSchedule

  /**
   * Creates new cron schedule by replacing times.
   *
   * @param one  time
   * @param more additional times
   *
   * @return new cron schedule
   */
  def withTimes(one: LocalTime, more: LocalTime*): CronSchedule =
    withTimes(one +: more)

  /**
   * Creates new cron schedule by replacing days of month.
   *
   * @param days days of month
   *
   * @return new cron schedule
   */
  def withDaysOfMonth(days: Seq[Int]): CronSchedule

  /**
   * Creates new cron schedule by replacing days of month.
   *
   * @param one  day of month
   * @param more additional days of month
   *
   * @return new cron schedule
   */
  def withDaysOfMonth(one: Int, more: Int*): CronSchedule =
    withDaysOfMonth(one +: more)

  /**
   * Creates new cron schedule by replacing months.
   *
   * @param months months
   *
   * @return new cron schedule
   */
  def withMonths(months: Seq[Month]): CronSchedule

  /**
   * Creates new cron schedule by replacing months.
   *
   * @param one  month
   * @param more additional months
   *
   * @return new cron schedule
   */
  def withMonths(one: Month, more: Month*): CronSchedule =
    withMonths(one +: more)

  /**
   * Creates new cron schedule by replacing days of week.
   *
   * @param days days of week
   *
   * @return new cron schedule
   */
  def withDaysOfWeek(days: Seq[DayOfWeek]): CronSchedule

  /**
   * Creates new cron schedule by replacing days of week.
   *
   * @param one  day of week
   * @param more additional days of week
   *
   * @return new cron schedule
   */
  def withDaysOfWeek(one: DayOfWeek, more: DayOfWeek*): CronSchedule =
    withDaysOfWeek(one +: more)

/**
 * Provides `CronSchedule` factory.
 *
 * @define asterisk *
 */
object CronSchedule:
  private val any    = "\\*(?:/(\\w+))?".r
  private val single = "(\\w+)".r
  private val range  = "(\\w+)-(\\w+)(?:/(\\w+))?".r

  /**
   * Creates cron schedule with supplied fields.
   *
   * ### Examples
   *
   * {{{
   * import java.time.DayOfWeek.SUNDAY
   * import java.time.Month.{ OCTOBER, NOVEMBER, DECEMBER }
   *
   * import little.time.{ CronSchedule, TimeStringMethods }
   *
   * // At noon on 1st and 15th in October thru December
   * val monthly = CronSchedule(
   *   times       = Seq("12:00".toLocalTime),
   *   daysOfMonth = Seq(1, 15),
   *   months      = Seq(OCTOBER, NOVEMBER, DECEMBER))
   *
   * // At 8 AM every Sunday
   * val weekly = CronSchedule(
   *   times      = Seq("08:00".toLocalTime),
   *   daysOfWeek = Seq(SUNDAY)
   * )
   *
   * // At 6 AM and 5 PM every day.
   * val daily = CronSchedule(
   *   times = Seq("06:00".toLocalTime, "17:00".toLocalTime)
   * )
   * }}}
   *
   * @param times       times
   * @param daysOfMonth days of months
   * @param months      months
   * @param daysOfWeek  days of week
   *
   * @note If `times` is empty, it is converted to `Seq(LocalTime.MIDNIGHT)`.
   */
  def apply(
    times:       Seq[LocalTime] = Seq(LocalTime.MIDNIGHT),
    daysOfMonth: Seq[Int]       = Nil,
    months:      Seq[Month]     = Nil,
    daysOfWeek:  Seq[DayOfWeek] = Nil
  ): CronSchedule =

    CronScheduleImpl(
      times.isEmpty match
        case true  => Seq(LocalTime.MIDNIGHT)
        case false => SortedSeq(times)
      ,
      SortedSeq(daysOfMonth),
      SortedSeq(months),
      SortedSeq(daysOfWeek))

  /**
   * Parses fields to cron schedule.
   *
   * ## Cron Schedule Format
   *
   * The formatted cron schedule must be supplied as either 5 or 6 fields
   * separated by space. If only 5 fields are supplied, then `second` defaults
   * to `0`.
   *
   * | Field        | Required | Allowed Values |
   * | -----        | -------- | -------------- |
   * | `second`     | No       | `0-59` |
   * | `minute`     | Yes      | `0-59` |
   * | `hour`       | Yes      | `0-23` |
   * | `dayOfMonth` | Yes      | `1-31` |
   * | `month`      | Yes      | `1-12` or `Jan-Dec` |
   * | `dayOfWeek`  | Yes      | `0-7` or `Sun-Sat` _(Note: Sun is `0` or `7`)_ |
   *
   * A field may be specified as an asterisk (`*`), which denotes `first-last`
   * based on field's allowed values.
   *
   * A field may be specified as a list, where each value is separated by comma
   * (`,`). For example, `10,11,12` or `Oct,Nov,Dec` in `month`.
   *
   * A field may be specified as an inclusive range, where the range endpoints
   * are separated by hyphen (`-`). For example, `5-7` or `Fri-Sun` in
   * `dayOfWeek`.
   *
   * A step may be appended to a range to include only incremental values in
   * range, where range and step are supplied as `<range>/<step>`. For example,
   * `0-30/5` in `minute` indicates _every 5 minutes from 0 to 30_, which
   * alternatively could be specified as `0,5,10,15,20,25,30`.
   *
   * A step may also be appended to an asterisk to apply similar semantics. For
   * example, `*``/15` in `minute` indicates _every 15 minutes_.
   *
   * ### Examples
   *
   * {{{
   * // At 8 AM every Sunday
   * val weekly = CronSchedule("0 8 * * Sun")
   *
   * // At noon every day in October thru December
   * val daily = CronSchedule("0 12 * Oct-Dec *")
   *
   * // Every hour on 1st and 15th of every month
   * val hourly = CronSchedule("0 * 1,15 * *")
   *
   * // Every 15 seconds
   * val frequently = CronSchedule("$asterisk/15 * * * * *")
   * }}}
   *
   * @param fields cron fields
   *
   * @return cron schedule
   *
   * @throws IllegalArgumentException if fields cannot be parsed to a valid
   * schedule
   */
  def apply(fields: String): CronSchedule =
    fields.trim.split("\\s+") match
      case Array(second, minute, hour, dayOfMonth, month, dayOfWeek) =>
        CronScheduleImpl(
          LazyList.from(
            CronTimeIterator(
              SortedSeq(parseHour(hour)),
              SortedSeq(parseMinute(minute)),
              SortedSeq(parseSecond(second)))
          ),
          SortedSeq(parseDayOfMonth(dayOfMonth)),
          SortedSeq(parseMonth(month)),
          SortedSeq(parseDayOfWeek(dayOfWeek)))

      case Array(minute, hour, dayOfMonth, month, dayOfWeek) =>
        CronScheduleImpl(
          LazyList.from(
            CronTimeIterator(
              SortedSeq(parseHour(hour)),
              SortedSeq(parseMinute(minute)),
              Seq(0))
          ),
          SortedSeq(parseDayOfMonth(dayOfMonth)),
          SortedSeq(parseMonth(month)),
          SortedSeq(parseDayOfWeek(dayOfWeek)))

      case _ =>
        throw IllegalArgumentException("Invalid cron syntax")

  private def parseSecond(s: String): Seq[Int] =
    try
      s.split(",", -1).flatMap(parseField(_, 0, 59)).toSeq match
        case Nil    => Nil
        case second => second
    catch case _: Exception =>
      throw IllegalArgumentException("Invalid cron second")

  private def parseMinute(m: String): Seq[Int] =
    try
      m.split(",", -1).flatMap(parseField(_, 0, 59)).toSeq match
        case Nil    => Nil
        case minute => minute
    catch case _: Exception =>
      throw IllegalArgumentException("Invalid cron minute")

  private def parseHour(h: String): Seq[Int] =
    try
      h.split(",", -1).flatMap(parseField(_, 0, 23)).toSeq match
        case Nil  => Nil
        case hour => hour
    catch case _: Exception =>
      throw IllegalArgumentException("Invalid cron hour")

  private def parseDayOfMonth(d: String): Seq[Int] =
    try
      d.split(",", -1)
        .flatMap(parseField(_, 1, 31))
        .toSeq
    catch case _: Exception =>
        throw IllegalArgumentException("Invalid cron day of month")

  private def parseMonth(m: String): Seq[Month] =
    try
      m.split(",", -1)
        .flatMap(parseField(_, 1, 12, toMonth))
        .map(Month.of)
        .toSeq
    catch case _: Exception =>
      throw IllegalArgumentException("Invalid cron month")

  private def parseDayOfWeek(d: String): Seq[DayOfWeek] =
    try
      d.split(",", -1)
        .flatMap(parseField(_, 0, 7, toDayOfWeek))
        .map(_.max(1))
        .map(DayOfWeek.of)
        .toSeq
    catch case _: Exception =>
      throw IllegalArgumentException("Invalid cron day of week")

  private def parseField(value: String, min: Int, max: Int, toInt: String => Int = _.toInt): Seq[Int] =
    val field = value match
      case any(null)               => Nil
      case any(step)               => min to max by step.toInt
      case single(_)               => Seq(toInt(value))
      case range(start, end, null) => toInt(start) to toInt(end)
      case range(start, end, step) => toInt(start) to toInt(end) by step.toInt
      case _                       => throw RuntimeException()

    if field.headOption.exists(x => x < min || x > max) then throw RuntimeException()
    if field.lastOption.exists(x => x < min || x > max) then throw RuntimeException()

    field

  private def toMonth(s: String): Int =
    s.toLowerCase match
      case "jan" | "january"   => 1
      case "feb" | "february"  => 2
      case "mar" | "march"     => 3
      case "apr" | "april"     => 4
      case "may"               => 5
      case "jun" | "june"      => 6
      case "jul" | "july"      => 7
      case "aug" | "august"    => 8
      case "sep" | "september" => 9
      case "oct" | "october"   => 10
      case "nov" | "november"  => 11
      case "dec" | "december"  => 12
      case _                   => s.toInt

  private def toDayOfWeek(s: String): Int =
    s.toLowerCase match
      case "mon" | "monday"    => 1
      case "tue" | "tuesday"   => 2
      case "wed" | "wednesday" => 3
      case "thu" | "thursday"  => 4
      case "fri" | "friday"    => 5
      case "sat" | "saturday"  => 6
      case "sun" | "sunday"    => 7
      case _                   => s.toInt

private case class CronScheduleImpl(
  times:       Seq[LocalTime],
  daysOfMonth: Seq[Int],
  months:      Seq[Month],
  daysOfWeek:  Seq[DayOfWeek]
) extends CronSchedule:

  private lazy val isEmpty =
    daysOfMonth.isEmpty || months.isEmpty || daysOfWeek.nonEmpty match
      case true  => false
      case false =>
        daysOfMonth.dropWhile(_ < 29) match
          case Nil  => false
          case days => days.forall(day => months.forall(_.maxLength < day))

  def between(start: LocalDateTime, end: LocalDateTime) =
    isEmpty match
      case true  => Iterator.empty
      case false =>
        val dateIterator = CronDateIterator(
          start.toLocalDate,
          end.toLocalDate,
          months,
          daysOfMonth,
          daysOfWeek)

        dateIterator.flatMap(date => times.map(date.atTime))
          .filter(time => time >= start && time <= end)

  def withTimes(times: Seq[LocalTime]) =
    copy(times = times.isEmpty match
      case true  => Seq(LocalTime.MIDNIGHT)
      case false => SortedSeq(times)
    )

  def withDaysOfMonth(days: Seq[Int]) =
    copy(daysOfMonth = SortedSeq(days))

  def withMonths(months: Seq[Month]) =
    copy(months = SortedSeq(months))

  def withDaysOfWeek(days: Seq[DayOfWeek]) =
    copy(daysOfWeek = SortedSeq(days))
