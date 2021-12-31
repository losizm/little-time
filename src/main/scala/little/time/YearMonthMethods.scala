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

import java.time.{ Period, YearMonth }
import java.time.temporal.TemporalAmount

import Stepping.*

/** Provides extension methods for `java.time.YearMonth` */
implicit class YearMonthMethods(month: YearMonth) extends AnyVal:
  /**
   * Gets month with specified number of months added.
   *
   * @param months number of months
   */
  def +(months: Long): YearMonth =
    month.plusMonths(months)

  /**
   * Gets month with specified number of months subtracted.
   *
   * @param months number of months
   */
  def -(months: Long): YearMonth =
    month.minusMonths(months)

  /**
   * Gets month with specified amount added.
   *
   * @param amount temporal amount
   */
  def +(amount: TemporalAmount): YearMonth =
    month.plus(amount)

  /**
   * Gets month with specified amount subtracted.
   *
   * @param amount temporal amount
   */
  def -(amount: TemporalAmount): YearMonth =
    month.minus(amount)

  /**
   * Compares to other month and returns the lesser value.
   *
   * @param other other month
   */
  def min(other: YearMonth): YearMonth =
    yearMonthOrdering.min(month, other)

  /**
   * Compares to other month and returns the greater value.
   *
   * @param other other month
   */
  def max(other: YearMonth): YearMonth =
    yearMonthOrdering.max(month, other)

  /** Gets month adjusted to first month of year. */
  def atStartOfYear: YearMonth =
    month.withMonth(1)

  /** Gets month adjusted to last month of year. */
  def atEndOfYear: YearMonth =
    month.withMonth(12)

  /**
   * Creates iterator to end month (inclusive).
   *
   * @param end end month
   * @param step period by which to step
   *
   * @throws IllegalArgumentException if `step` is zero.
   */
  def iterateTo(end: YearMonth, step: Period = Period.ofMonths(1)): Iterator[YearMonth] =
    inclusive(month, end, step)

  /**
   * Creates iterator to end month (exclusive).
   *
   * @param end end month
   * @param step period by which to step
   */
  def iterateUntil(end: YearMonth, step: Period = Period.ofMonths(1)): Iterator[YearMonth] =
    exclusive(month, end, step)
