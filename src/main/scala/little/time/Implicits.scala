/*
 * Copyright 2019 Carlos Conyers
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
import java.time.temporal.ChronoUnit

import DayOfWeek._

/** Provides extension methods to `java.time`. */
object Implicits {
  private val startOfDay = LocalTime.parse("00:00")

  private class Inclusive[T](start: T, end: T, advance: (T) => T)(implicit order: Ordering[T]) extends Iterator[T] {
    private var nextValue = start

    def hasNext: Boolean = order.lteq(nextValue, end)

    def next(): T =
      hasNext match {
        case true =>
          val value = nextValue
          nextValue = advance(value)
          value
        case false => throw new NoSuchElementException()
      }
  }

  private class Exclusive[T](start: T, end: T, advance: (T) => T)(implicit order: Ordering[T]) extends Iterator[T] {
    private var nextValue = start

    def hasNext: Boolean = order.lt(nextValue, end)

    def next(): T =
      hasNext match {
        case true =>
          val value = nextValue
          nextValue = advance(value)
          value
        case false => throw new NoSuchElementException()
      }
  }

  /** Provides ordering for `java.time.YearMonth`. */
  implicit val yearMonthOrdering: Ordering[YearMonth] = (a, b) => a.compareTo(b)

  /** Provides ordering for `java.time.LocalDate`. */
  implicit val localDateOrdering: Ordering[LocalDate] = (a, b) => a.compareTo(b)

  /** Provides ordering for `java.time.LocalTime`. */
  implicit val localTimeOrdering: Ordering[LocalTime] = (a, b) => a.compareTo(b)

  /** Provides ordering for `java.time.LocalDateTime`. */
  implicit val localDateTimeOrdering: Ordering[LocalDateTime] = (a, b) => a.compareTo(b)
  
  /** Provides extension methods to `java.time.YearMonth` */
  implicit class YearMonthType(val month: YearMonth) extends AnyVal {
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

    /**
     * Creates iterator forward to end month.
     *
     * @param end end month
     * @param inclusive indicates if end is inclusive
     */
    def to(end: YearMonth, inclusive: Boolean = true): Iterator[YearMonth] =
      inclusive match {
        case true  => new Inclusive(month, end, (x: YearMonth) => x.plusMonths(1))
        case false => new Exclusive(month, end, (x: YearMonth) => x.plusMonths(1))
      }
  }

  /** Provides extension methods to `java.time.LocalDate` */
  implicit class LocalDateType(val date: LocalDate) extends AnyVal {
    /** Get `YearMonth` part of date. */
    def toYearMonth: YearMonth = YearMonth.of(date.getYear, date.getMonth)

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
    def atStartOfWeek: LocalDate = atStartOfWeek(SUNDAY)

    /**
     * Gets date adjusted to specified first day of week.
     *
     * @param firstDay first day of week
     */
    def atStartOfWeek(firstDay: DayOfWeek): LocalDate =
      date.getDayOfWeek match {
        case `firstDay` => date
        case dayOfWeek  =>
          date.minusDays {
            (dayOfWeek.getValue - firstDay.getValue + 7) % 7
          }
      }

    /**
     * Gets date adjusted to last day of week.
     *
     * @note Saturday is last day of week.
     */
    def atEndOfWeek: LocalDate = atEndOfWeek(SATURDAY)

    /**
     * Gets date adjusted to last day of week.
     *
     * @param lastDay last day of week
     */
    def atEndOfWeek(lastDay: DayOfWeek): LocalDate =
      date.getDayOfWeek match {
        case `lastDay` => date
        case dayOfWeek  =>
          date.plusDays {
            (lastDay.getValue - dayOfWeek.getValue + 7) % 7
          }
      }

    /**
     * Creates iterator forward to end date.
     *
     * @param end end date
     * @param inclusive indicates if end is inclusive
     */
    def to(end: LocalDate, inclusive: Boolean = true): Iterator[LocalDate] =
      inclusive match {
        case true  => new Inclusive(date, end, (x: LocalDate) => x.plusDays(1))
        case false => new Exclusive(date, end, (x: LocalDate) => x.plusDays(1))
      }
  }

  /** Provides extension methods to `java.time.LocalTime` */
  implicit class LocalTimeType(val time: LocalTime) extends AnyVal {
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

    /** Gets time truncated to hour. */
    def atStartOfHour: LocalTime =
      time.truncatedTo(ChronoUnit.HOURS)

    /** Gets time truncated to minute. */
    def atStartOfMinute: LocalTime =
      time.truncatedTo(ChronoUnit.MINUTES)

    /** Gets time truncated to second. */
    def atStartOfSecond: LocalTime =
      time.truncatedTo(ChronoUnit.SECONDS)

    /** Gets time truncated to millisecond. */
    def atStartOfMillisecond: LocalTime =
      time.truncatedTo(ChronoUnit.MILLIS)

    /** Gets time truncated to microsecond. */
    def atStartOfMicrosecond: LocalTime =
      time.truncatedTo(ChronoUnit.MICROS)
  }

  /** Provides extension methods to `java.time.LocalDateTime` */
  implicit class LocalDateTimeType(val dateTime: LocalDateTime) extends AnyVal {
    /** Get `YearMonth` part of date-time. */
    def toYearMonth: YearMonth = YearMonth.of(dateTime.getYear, dateTime.getMonth)

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
      LocalDateTime.of(dateTime.toLocalDate.atStartOfYear, startOfDay)

    /** Gets date-time adjusted to first day of month. */
    def atStartOfMonth: LocalDateTime =
      LocalDateTime.of(dateTime.toLocalDate.atStartOfMonth, startOfDay)

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
      LocalDateTime.of(dateTime.toLocalDate.atStartOfWeek(firstDay), startOfDay)

    /** Gets date-time truncated to day. */
    def atStartOfDay: LocalDateTime =
      dateTime.truncatedTo(ChronoUnit.DAYS)

    /** Gets date-time truncated to hour. */
    def atStartOfHour: LocalDateTime =
      dateTime.truncatedTo(ChronoUnit.HOURS)

    /** Gets date-time truncated to minute. */
    def atStartOfMinute: LocalDateTime =
      dateTime.truncatedTo(ChronoUnit.MINUTES)

    /** Gets date-time truncated to second. */
    def atStartOfSecond: LocalDateTime =
      dateTime.truncatedTo(ChronoUnit.SECONDS)

    /** Gets date-time truncated to millisecond. */
    def atStartOfMillisecond: LocalDateTime =
      dateTime.truncatedTo(ChronoUnit.MILLIS)

    /** Gets date-time truncated to microsecond. */
    def atStartOfMicrosecond: LocalDateTime =
      dateTime.truncatedTo(ChronoUnit.MICROS)
  }
}

