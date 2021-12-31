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

import java.time.{ DateTimeException, Duration, LocalTime }
import java.time.temporal.{ ChronoUnit, TemporalAmount }

import Stepping.*
import TimePrecision.*

/** Provides extension methods for `java.time.LocalTime` */
implicit class LocalTimeMethods(time: LocalTime) extends AnyVal:
  /**
   * Gets time with specified amount added.
   *
   * @param amount temporal amount
   */
  def +(amount: TemporalAmount): LocalTime =
    time.plus(amount)

  /**
   * Gets time with specified amount subtracted.
   *
   * @param amount temporal amount
   */
  def -(amount: TemporalAmount): LocalTime = time.minus(amount)

  /**
   * Compares to other time and returns the lesser value.
   *
   * @param other other time
   */
  def min(other: LocalTime): LocalTime =
    localTimeOrdering.min(time, other)

  /**
   * Compares to other time and returns the greater value.
   *
   * @param other other time
   */
  def max(other: LocalTime): LocalTime =
    localTimeOrdering.max(time, other)

  /** Gets time truncated to day. */
  def atStartOfDay: LocalTime =
    LocalTime.MIN

  /**
   * Gets time adjusted to end of day.
   *
   * @param precision time precision
   */
  def atEndOfDay(using precision: TimePrecision): LocalTime =
    precision.limit

  /** Gets time truncated to hour. */
  def atStartOfHour: LocalTime =
    time.truncatedTo(ChronoUnit.HOURS)

  /**
   * Gets time adjusted to end of hour.
   *
   * @param precision time precision
   */
  def atEndOfHour(using precision: TimePrecision): LocalTime =
    precision match
      case Minutes              => LocalTime.of(time.getHour, 59)
      case Seconds              => LocalTime.of(time.getHour, 59, 59)
      case FractionalSeconds(_) => LocalTime.of(time.getHour, 59, 59, precision.limit.getNano)
      case _                    => throw DateTimeException(s"Precision unit too large: $precision")

  /** Gets time truncated to minute. */
  def atStartOfMinute: LocalTime =
    time.truncatedTo(ChronoUnit.MINUTES)

  /**
   * Gets time adjusted to end of minute.
   *
   * @param precision time precision
   */
  def atEndOfMinute(using precision: TimePrecision): LocalTime =
    precision match
      case Seconds              => LocalTime.of(time.getHour, time.getMinute, 59)
      case FractionalSeconds(_) => LocalTime.of(time.getHour, time.getMinute, 59, precision.limit.getNano)
      case _                    => throw DateTimeException(s"Precision unit too large: $precision")

  /** Gets time truncated to second. */
  def atStartOfSecond: LocalTime =
    time.truncatedTo(ChronoUnit.SECONDS)

  /**
   * Gets time adjusted to end of second.
   *
   * @param precision time precision
   */
  def atEndOfSecond(using precision: TimePrecision): LocalTime =
    (precision > Seconds) match
      case true  => LocalTime.of(time.getHour, time.getMinute, time.getSecond, precision.limit.getNano)
      case false => throw DateTimeException(s"Precision unit too large: $precision")

  /** Gets time truncated to millisecond. */
  def atStartOfMillis: LocalTime =
    time.truncatedTo(ChronoUnit.MILLIS)

  /**
   * Gets time adjusted to end of millisecond.
   *
   * @param precision time precision
   */
  def atEndOfMillis(using precision: TimePrecision): LocalTime =
    precision > Milliseconds match
      case true  => LocalTime.of(time.getHour, time.getMinute, time.getSecond, precision.limit.getNano)
      case false => throw DateTimeException(s"Precision unit too large: $precision")

  /** Gets time truncated to microsecond. */
  def atStartOfMicros: LocalTime =
    time.truncatedTo(ChronoUnit.MICROS)

  /**
   * Gets time adjusted to end of microsecond.
   *
   * @param precision time precision
   */
  def atEndOfMicros(using precision: TimePrecision): LocalTime =
    precision > Microseconds match
      case true  => LocalTime.of(time.getHour, time.getMinute, time.getSecond, precision.limit.getNano)
      case false => throw DateTimeException(s"Precision unit too large: $precision")

  /**
   * Creates iterator to end time (inclusive).
   *
   * @param end end time
   * @param step duration by which to step
   *
   * @throws IllegalArgumentException if `step` is zero.
   */
  def iterateTo(end: LocalTime, step: Duration = Duration.ofSeconds(1)): Iterator[LocalTime] =
    inclusive(time, end, step)

  /**
   * Creates iterator to end time (exclusive).
   *
   * @param end end time
   * @param step duration by which to step
   */
  def iterateUntil(end: LocalTime, step: Duration = Duration.ofSeconds(1)): Iterator[LocalTime] =
    exclusive(time, end, step)
