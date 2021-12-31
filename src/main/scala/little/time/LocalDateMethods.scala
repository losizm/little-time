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

import java.time.{ DayOfWeek, LocalDate, Period, YearMonth }
import java.time.temporal.TemporalAmount

import DayOfWeek.*
import Stepping.*

/** Provides extension methods for `java.time.LocalDate` */
implicit class LocalDateMethods(date: LocalDate) extends AnyVal:
  /** Gets `YearMonth` part of date. */
  def toYearMonth: YearMonth =
    YearMonth.of(date.getYear, date.getMonth)

  /**
   * Gets date with specified number of days added.
   *
   * @param days number of days
   */
  def +(days: Long): LocalDate =
    date.plusDays(days)

  /**
   * Gets date with specified number of days subtracted.
   *
   * @param days number of days
   */
  def -(days: Long): LocalDate =
    date.minusDays(days)

  /**
   * Gets date with specified amount added.
   *
   * @param amount temporal amount
   */
  def +(amount: TemporalAmount): LocalDate =
    date.plus(amount)

  /**
   * Gets date with specified amount subtracted.
   *
   * @param amount temporal amount
   */
  def -(amount: TemporalAmount): LocalDate =
    date.minus(amount)

  /**
   * Compares to other date and returns the lesser value.
   *
   * @param other other date
   */
  def min(other: LocalDate): LocalDate =
    localDateOrdering.min(date, other)

  /**
   * Compares to other date and returns the greater value.
   *
   * @param other other date
   */
  def max(other: LocalDate): LocalDate =
    localDateOrdering.max(date, other)

  /** Gets date adjusted to first day of year. */
  def atStartOfYear: LocalDate =
    date.withDayOfYear(1)

  /** Gets date adjusted to last day of year. */
  def atEndOfYear: LocalDate =
    date.withDayOfYear(date.lengthOfYear)

  /** Gets date adjusted to first day of month. */
  def atStartOfMonth: LocalDate =
    date.withDayOfMonth(1)

  /** Gets date adjusted to last day of month. */
  def atEndOfMonth: LocalDate =
    date.withDayOfMonth(date.lengthOfMonth)

  /**
   * Gets date adjusted to first day of week.
   *
   * @note Sunday is first day of week.
   */
  def atStartOfWeek: LocalDate =
    atStartOfWeek(SUNDAY)

  /**
   * Gets date adjusted to specified first day of week.
   *
   * @param firstDay first day of week
   */
  def atStartOfWeek(firstDay: DayOfWeek): LocalDate =
    date.getDayOfWeek match
      case `firstDay` => date
      case dayOfWeek  =>
        date.minusDays {
          (dayOfWeek.getValue - firstDay.getValue + 7) % 7
        }

  /**
   * Gets date adjusted to last day of week.
   *
   * @note Saturday is last day of week.
   */
  def atEndOfWeek: LocalDate =
    atEndOfWeek(SATURDAY)

  /**
   * Gets date adjusted to last day of week.
   *
   * @param lastDay last day of week
   */
  def atEndOfWeek(lastDay: DayOfWeek): LocalDate =
    date.getDayOfWeek match
      case `lastDay` => date
      case dayOfWeek  =>
        date.plusDays {
          (lastDay.getValue - dayOfWeek.getValue + 7) % 7
        }

  /**
   * Creates iterator to end date (inclusive).
   *
   * @param end end date
   * @param step period by which to step
   *
   * @throws IllegalArgumentException if `step` is zero.
   */
  def iterateTo(end: LocalDate, step: Period = Period.ofDays(1)): Iterator[LocalDate] =
    inclusive(date, end, step)

  /**
   * Creates iterator to end date (exclusive).
   *
   * @param end end date
   * @param step period by which to step
   */
  def iterateUntil(end: LocalDate, step: Period = Period.ofDays(1)): Iterator[LocalDate] =
    exclusive(date, end, step)
