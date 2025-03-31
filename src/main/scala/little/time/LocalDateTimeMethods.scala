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
import java.time.temporal.{ ChronoUnit, TemporalAmount }

import DayOfWeek.*
import Stepping.*

/** Provides extension methods for `java.time.LocalDateTime` */
implicit class LocalDateTimeMethods(dateTime: LocalDateTime) extends AnyVal:
  /** Gets `YearMonth` part of date-time. */
  def toYearMonth: YearMonth =
    YearMonth.of(dateTime.getYear, dateTime.getMonth)

  /**
   * Gets `Instant` by applying time zone.
   *
   * @param zone time zone
   */
  def toInstant(zone: ZoneId = ZoneId.systemDefault): Instant =
    dateTime.atZone(zone)
      .toOffsetDateTime
      .toInstant

  /**
   * Gets `Instant` by applying time zone.
   *
   * @param zone time zone
   */
  def toInstant(zone: String): Instant =
    toInstant(ZoneId.of(zone))

  /**
   * Gets date-time with specified amount added.
   *
   * @param amount temporal amount
   */
  def +(amount: TemporalAmount): LocalDateTime =
    dateTime.plus(amount)

  /**
   * Gets date-time with specified amount subtracted.
   *
   * @param amount temporal amount
   */
  def -(amount: TemporalAmount): LocalDateTime =
    dateTime.minus(amount)

  /**
   * Compares to other date-time and returns the lesser value.
   *
   * @param other other date-time
   */
  def min(other: LocalDateTime): LocalDateTime =
    localDateTimeOrdering.min(dateTime, other)

  /**
   * Compares to other date-time and returns the greater value.
   *
   * @param other other date-time
   */
  def max(other: LocalDateTime): LocalDateTime =
    localDateTimeOrdering.max(dateTime, other)

  /** Gets date-time adjusted to first day of year. */
  def atStartOfYear: LocalDateTime =
    LocalDateTime.of(dateTime.toLocalDate.atStartOfYear, LocalTime.MIN)

  /**
   * Gets date-time adjusted to last day of year.
   *
   * @param precision time precision
   */
  def atEndOfYear(using precision: TimePrecision): LocalDateTime =
    LocalDateTime.of(dateTime.toLocalDate.atEndOfYear, precision.limit)

  /** Gets date-time adjusted to first day of month. */
  def atStartOfMonth: LocalDateTime =
    LocalDateTime.of(dateTime.toLocalDate.atStartOfMonth, LocalTime.MIN)

  /**
   * Gets date-time adjusted to last day of month.
   *
   * @param precision time precision
   */
  def atEndOfMonth(using precision: TimePrecision): LocalDateTime =
    LocalDateTime.of(dateTime.toLocalDate.atEndOfMonth, precision.limit)

  /**
   * Gets date-time adjusted to first day of week.
   *
   * @note Sunday is first day of week.
   */
  def atStartOfWeek: LocalDateTime = atStartOfWeek(SUNDAY)

  /**
   * Gets date-time adjusted to specified first day of week.
   *
   * @param firstDay first day of week
   */
  def atStartOfWeek(firstDay: DayOfWeek): LocalDateTime =
    LocalDateTime.of(dateTime.toLocalDate.atStartOfWeek(firstDay), LocalTime.MIN)

  /**
   * Gets date-time adjusted to last day of week.
   *
   * @note Saturday is last day of week.
   */
  def atEndOfWeek(using precision: TimePrecision): LocalDateTime =
    atEndOfWeek(SATURDAY)

  /**
   * Gets date-time adjusted to specified last day of week.
   *
   * @param lastDay last day of week
   * @param precision time precision
   */
  def atEndOfWeek(lastDay: DayOfWeek)(using precision: TimePrecision): LocalDateTime =
    LocalDateTime.of(dateTime.toLocalDate.atEndOfWeek(lastDay), precision.limit)

  /** Gets date-time truncated to day. */
  def atStartOfDay: LocalDateTime =
    dateTime.truncatedTo(ChronoUnit.DAYS)

  /**
   * Gets date-time adjusted to end of day.
   *
   * @param precision time precision
   */
  def atEndOfDay(using precision: TimePrecision): LocalDateTime =
    LocalDateTime.of(dateTime.toLocalDate, dateTime.toLocalTime.atEndOfDay)

  /** Gets date-time truncated to hour. */
  def atStartOfHour: LocalDateTime =
    dateTime.truncatedTo(ChronoUnit.HOURS)

  /**
   * Gets date-time adjusted to end of hour.
   *
   * @param precision time precision
   */
  def atEndOfHour(using precision: TimePrecision): LocalDateTime =
    LocalDateTime.of(dateTime.toLocalDate, dateTime.toLocalTime.atEndOfHour)

  /** Gets date-time truncated to minute. */
  def atStartOfMinute: LocalDateTime =
    dateTime.truncatedTo(ChronoUnit.MINUTES)

  /**
   * Gets date-time adjusted to end of minute.
   *
   * @param precision time precision
   */
  def atEndOfMinute(using precision: TimePrecision): LocalDateTime =
    LocalDateTime.of(dateTime.toLocalDate, dateTime.toLocalTime.atEndOfMinute)

  /** Gets date-time truncated to second. */
  def atStartOfSecond: LocalDateTime =
    dateTime.truncatedTo(ChronoUnit.SECONDS)

  /**
   * Gets date-time adjusted to end of second.
   *
   * @param precision time precision
   */
  def atEndOfSecond(using precision: TimePrecision): LocalDateTime =
    LocalDateTime.of(dateTime.toLocalDate, dateTime.toLocalTime.atEndOfSecond)

  /** Gets date-time truncated to millisecond. */
  def atStartOfMillis: LocalDateTime =
    dateTime.truncatedTo(ChronoUnit.MILLIS)

  /**
   * Gets date-time adjusted to end of millisecond.
   *
   * @param precision time precision
   */
  def atEndOfMillis(using precision: TimePrecision): LocalDateTime =
    LocalDateTime.of(dateTime.toLocalDate, dateTime.toLocalTime.atEndOfMillis)

  /** Gets date-time truncated to microsecond. */
  def atStartOfMicros: LocalDateTime =
    dateTime.truncatedTo(ChronoUnit.MICROS)

  /**
   * Gets date-time adjusted to end of microsecond.
   *
   * @param precision time precision
   */
  def atEndOfMicros(using precision: TimePrecision): LocalDateTime =
    LocalDateTime.of(dateTime.toLocalDate, dateTime.toLocalTime.atEndOfMicros)

  /**
   * Creates iterator to end date-time (inclusive).
   *
   * @param end end date-time
   * @param step temporal amount by which to step
   *
   * @throws IllegalArgumentException if `step` is zero.
   */
  def iterateTo(end: LocalDateTime, step: TemporalAmount = Duration.ofSeconds(1)): Iterator[LocalDateTime] =
    inclusive(dateTime, end, step)

  /**
   * Creates iterator to end date-time (exclusive).
   *
   * @param end end date-time
   * @param step temporal amount by which to step
   */
  def iterateUntil(end: LocalDateTime, step: TemporalAmount = Duration.ofSeconds(1)): Iterator[LocalDateTime] =
    exclusive(dateTime, end, step)
