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
import java.time.temporal.{ ChronoUnit, TemporalAmount }

import DayOfWeek._
import Stepping._
import TimePrecision._

/** Provides extension methods to `java.time`. */
object Implicits {
  /** Provides ordering for `java.time.Duration`. */
  implicit val durationOrdering: Ordering[Duration] =
    (a, b) => a.compareTo(b)

  /** Provides ordering for `java.time.YearMonth`. */
  implicit val yearMonthOrdering: Ordering[YearMonth] =
    (a, b) => a.compareTo(b)

  /** Provides ordering for `java.time.LocalDate`. */
  implicit val localDateOrdering: Ordering[LocalDate] =
    (a, b) => a.compareTo(b)

  /** Provides ordering for `java.time.LocalTime`. */
  implicit val localTimeOrdering: Ordering[LocalTime] =
    (a, b) => a.compareTo(b)

  /** Provides ordering for `java.time.LocalDateTime`. */
  implicit val localDateTimeOrdering: Ordering[LocalDateTime] =
    (a, b) => a.compareTo(b)

  /** Provides extension methods to `java.time.Duration` */
  implicit class DurationType(private val duration: Duration) extends AnyVal {
    /** Gets negated duration. */
    def unary_- : Duration =
      duration.negated()

    /**
     * Gets duration with specified amount added.
     *
     * @param amount duration to add
     */
    def +(amount: Duration): Duration =
      duration.plus(amount)

    /**
     * Gets duration with specified amount subtracted.
     *
     * @param amount duration to subtract
     */
    def -(amount: Duration): Duration =
      duration.minus(amount)

    /**
     * Gets duration after multiplication.
     *
     * @param n number by which duration is multiplied
     */
    def *(n: Long): Duration =
      duration.multipliedBy(n)

    /**
     * Gets duration after division.
     *
     * @param n number by which duration is divided
     */
    def /(n: Long): Duration =
      duration.dividedBy(n)

    /**
     * Compares to other duration and returns the lesser value.
     *
     * @param other other duration
     */
    def min(other: Duration): Duration =
      durationOrdering.min(duration, other)

    /**
     * Compares to other duration and returns the greater value.
     *
     * @param other other duration
     */
    def max(other: Duration): Duration =
      durationOrdering.max(duration, other)

    /**
     * Creates iterator to end duration (inclusive).
     *
     * @param end end duration
     *
     * @throws IllegalArgumentException if `step` is zero.
     */
    def iterateTo(end: Duration, step: Duration = Duration.ofSeconds(1)): Iterator[Duration] =
      inclusive(duration, end, step)

    /**
     * Creates iterator to end duration (exclusive).
     *
     * @param end end duration
     */
    def iterateUntil(end: Duration, step: Duration = Duration.ofSeconds(1)): Iterator[Duration] =
      exclusive(duration, end, step)
  }

  /** Provides extension methods to `java.time.Period` */
  implicit class PeriodType(private val period: Period) extends AnyVal {
    /** Gets negated period. */
    def unary_- : Period =
      period.negated()

    /**
     * Gets period with specified amount added.
     *
     * @param amount period to add
     */
    def +(amount: Period): Period =
      period.plus(amount)

    /**
     * Gets period with specified amount subtracted.
     *
     * @param amount period to subtract
     */
    def -(amount: Period): Period =
      period.minus(amount)

    /**
     * Gets duration after multiplication.
     *
     * @param n number by which period is multiplied
     */
    def *(n: Int): Period =
      period.multipliedBy(n)
  }

  /** Provides extension methods to `java.time.YearMonth` */
  implicit class YearMonthType(private val month: YearMonth) extends AnyVal {
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
  }

  /** Provides extension methods to `java.time.LocalDate` */
  implicit class LocalDateType(private val date: LocalDate) extends AnyVal {
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
    def atEndOfWeek: LocalDate =
      atEndOfWeek(SATURDAY)

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
  }

  /** Provides extension methods to `java.time.LocalTime` */
  implicit class LocalTimeType(private val time: LocalTime) extends AnyVal {
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
    def atEndOfDay(implicit precision: TimePrecision): LocalTime =
      precision.limit

    /** Gets time truncated to hour. */
    def atStartOfHour: LocalTime =
      time.truncatedTo(ChronoUnit.HOURS)

    /**
     * Gets time adjusted to end of hour.
     *
     * @param precision time precision
     */
    def atEndOfHour(implicit precision: TimePrecision): LocalTime =
      precision match {
        case Minutes              => LocalTime.of(time.getHour, 59)
        case Seconds              => LocalTime.of(time.getHour, 59, 59)
        case FractionalSeconds(_) => LocalTime.of(time.getHour, 59, 59, precision.limit.getNano)
        case _                    => throw new DateTimeException(s"Precision unit too large: $precision")
      }

    /** Gets time truncated to minute. */
    def atStartOfMinute: LocalTime =
      time.truncatedTo(ChronoUnit.MINUTES)

    /**
     * Gets time adjusted to end of minute.
     *
     * @param precision time precision
     */
    def atEndOfMinute(implicit precision: TimePrecision): LocalTime =
      precision match {
        case Seconds              => LocalTime.of(time.getHour, time.getMinute, 59)
        case FractionalSeconds(_) => LocalTime.of(time.getHour, time.getMinute, 59, precision.limit.getNano)
        case _                    => throw new DateTimeException(s"Precision unit too large: $precision")
      }

    /** Gets time truncated to second. */
    def atStartOfSecond: LocalTime =
      time.truncatedTo(ChronoUnit.SECONDS)

    /**
     * Gets time adjusted to end of second.
     *
     * @param precision time precision
     */
    def atEndOfSecond(implicit precision: TimePrecision): LocalTime =
      precision > Seconds match {
        case true  => LocalTime.of(time.getHour, time.getMinute, time.getSecond, precision.limit.getNano)
        case false => throw new DateTimeException(s"Precision unit too large: $precision")
      }

    /** Gets time truncated to millisecond. */
    def atStartOfMillis: LocalTime =
      time.truncatedTo(ChronoUnit.MILLIS)

    /**
     * Gets time adjusted to end of millisecond.
     *
     * @param precision time precision
     */
    def atEndOfMillis(implicit precision: TimePrecision): LocalTime =
      precision > Milliseconds match {
        case true  => LocalTime.of(time.getHour, time.getMinute, time.getSecond, precision.limit.getNano)
        case false => throw new DateTimeException(s"Precision unit too large: $precision")
      }

    /** Gets time truncated to microsecond. */
    def atStartOfMicros: LocalTime =
      time.truncatedTo(ChronoUnit.MICROS)

    /**
     * Gets time adjusted to end of microsecond.
     *
     * @param precision time precision
     */
    def atEndOfMicros(implicit precision: TimePrecision): LocalTime =
      precision > Microseconds match {
        case true  => LocalTime.of(time.getHour, time.getMinute, time.getSecond, precision.limit.getNano)
        case false => throw new DateTimeException(s"Precision unit too large: $precision")
      }

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
  }

  /** Provides extension methods to `java.time.LocalDateTime` */
  implicit class LocalDateTimeType(private val dateTime: LocalDateTime) extends AnyVal {
    /** Gets `YearMonth` part of date-time. */
    def toYearMonth: YearMonth =
      YearMonth.of(dateTime.getYear, dateTime.getMonth)

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
    def atEndOfYear(implicit precision: TimePrecision): LocalDateTime =
      LocalDateTime.of(dateTime.toLocalDate.atEndOfYear, precision.limit)

    /** Gets date-time adjusted to first day of month. */
    def atStartOfMonth: LocalDateTime =
      LocalDateTime.of(dateTime.toLocalDate.atStartOfMonth, LocalTime.MIN)

    /**
     * Gets date-time adjusted to last day of month.
     *
     * @param precision time precision
     */
    def atEndOfMonth(implicit precision: TimePrecision): LocalDateTime =
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
    def atEndOfWeek(implicit precision: TimePrecision): LocalDateTime =
      atEndOfWeek(SATURDAY)

    /**
     * Gets date-time adjusted to specified last day of week.
     *
     * @param lastDay last day of week
     * @param precision time precision
     */
    def atEndOfWeek(lastDay: DayOfWeek)(implicit precision: TimePrecision): LocalDateTime =
      LocalDateTime.of(dateTime.toLocalDate.atEndOfWeek(lastDay), precision.limit)

    /** Gets date-time truncated to day. */
    def atStartOfDay: LocalDateTime =
      dateTime.truncatedTo(ChronoUnit.DAYS)

    /**
     * Gets date-time adjusted to end of day.
     *
     * @param precision time precision
     */
    def atEndOfDay(implicit precision: TimePrecision): LocalDateTime =
      LocalDateTime.of(dateTime.toLocalDate, dateTime.toLocalTime.atEndOfDay)

    /** Gets date-time truncated to hour. */
    def atStartOfHour: LocalDateTime =
      dateTime.truncatedTo(ChronoUnit.HOURS)

    /**
     * Gets date-time adjusted to end of hour.
     *
     * @param precision time precision
     */
    def atEndOfHour(implicit precision: TimePrecision): LocalDateTime =
      LocalDateTime.of(dateTime.toLocalDate, dateTime.toLocalTime.atEndOfHour)

    /** Gets date-time truncated to minute. */
    def atStartOfMinute: LocalDateTime =
      dateTime.truncatedTo(ChronoUnit.MINUTES)

    /**
     * Gets date-time adjusted to end of minute.
     *
     * @param precision time precision
     */
    def atEndOfMinute(implicit precision: TimePrecision): LocalDateTime =
      LocalDateTime.of(dateTime.toLocalDate, dateTime.toLocalTime.atEndOfMinute)

    /** Gets date-time truncated to second. */
    def atStartOfSecond: LocalDateTime =
      dateTime.truncatedTo(ChronoUnit.SECONDS)

    /**
     * Gets date-time adjusted to end of second.
     *
     * @param precision time precision
     */
    def atEndOfSecond(implicit precision: TimePrecision): LocalDateTime =
      LocalDateTime.of(dateTime.toLocalDate, dateTime.toLocalTime.atEndOfSecond)

    /** Gets date-time truncated to millisecond. */
    def atStartOfMillis: LocalDateTime =
      dateTime.truncatedTo(ChronoUnit.MILLIS)

    /**
     * Gets date-time adjusted to end of millisecond.
     *
     * @param precision time precision
     */
    def atEndOfMillis(implicit precision: TimePrecision): LocalDateTime =
      LocalDateTime.of(dateTime.toLocalDate, dateTime.toLocalTime.atEndOfMillis)

    /** Gets date-time truncated to microsecond. */
    def atStartOfMicros: LocalDateTime =
      dateTime.truncatedTo(ChronoUnit.MICROS)

    /**
     * Gets date-time adjusted to end of microsecond.
     *
     * @param precision time precision
     */
    def atEndOfMicros(implicit precision: TimePrecision): LocalDateTime =
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
  }

  /** Provides time-related extension methods to `java.lang.String`. */
  implicit class TimeStringType(private val string: String) extends AnyVal {
    /** Converts formatted string to `Period`. */
    def toPeriod: Period =
      Period.parse(string)

    /** Converts formatted string to `Duration`. */
    def toDuration: Duration =
      Duration.parse(string)

    /** Converts formatted string to `YearMonth`. */
    def toYearMonth: YearMonth =
      YearMonth.parse(string)

    /** Converts formatted string to `LocalDate`. */
    def toLocalDate: LocalDate =
      LocalDate.parse(string)

    /** Converts formatted string to `LocalTime`. */
    def toLocalTime: LocalTime =
      LocalTime.parse(string)

    /** Converts formatted string to `LocalDateTime`. */
    def toLocalDateTime: LocalDateTime =
      LocalDateTime.parse(string)
  }
}
