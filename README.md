# little-time

The Scala library that provides extension methods to _java.time_.

[![Maven Central](https://img.shields.io/maven-central/v/com.github.losizm/little-time_2.12.svg?label=Maven%20Central)](https://search.maven.org/search?q=g:%22com.github.losizm%22%20AND%20a:%22little-time_2.12%22)

## Getting Started

To use **little-time**, add it as a dependency to your project:

```scala
libraryDependencies += "com.github.losizm" %% "little-time" % "0.6.1"
```

## A Taste of little-time

Here's a taste of what **little-time** offers.

### Iterating over Dates

You can create an iterator from one `LocalDate` to another, stepping thru each
date between the two.

```scala
import java.time._
import little.time.Implicits._

// Convert String to LocalDate
val start = "2018-04-01".toLocalDate
val end   = "2018-07-01".toLocalDate

// Iterate over dates, one day at a time (end inclusive)
start iterateTo end foreach { date =>
  println(s"$date is on a ${date.getDayOfWeek}")
}

// Iterate over dates, one day at a time (end exclusive)
start iterateUntil end foreach { date =>
  println(s"$date is on a ${date.getDayOfWeek}")
}
```

Or, if you prefer, use the `~>` and `~>|` shorthands.

```scala
// Iterate over dates, one day at a time (end inclusive)
start ~> end foreach { date =>
  println(s"$date is on a ${date.getDayOfWeek}")
}

// Iterate over dates, one day at a time (end exclusive)
start ~>| end foreach { date =>
  println(s"$date is on a ${date.getDayOfWeek}")
}
```

### Specifying Step Unit

When creating the iterator, you can specify the `Period` by which to step.

```scala
// Iterate over dates, two weeks at a time
start.iterateTo(end, Period.ofWeeks(2)) foreach { date =>
  println(s"$date is on a ${date.getDayOfWeek}")
}
```

You can iterate forward or backward, based on the supplied period. If the period
is negative, you step backward to the end date.

```scala
import java.time.temporal.ChronoUnit

val _1day = Period.ofDays(1)
val startDate = LocalDate.now()
val endDate   = startDate - 7

// Iterator backward over dates, 1 day at a time
startDate.iterateUntil(endDate, -_1day) foreach { date =>
  val daysAgo = date.until(startDate, ChronoUnit.DAYS)
  println(s"$date is $daysAgo days(s) ago")
}
```

### Iterating over Other Types

You can create iterators of other types, like `YearMonth` and `LocalTime`.
Similar to `LocalDate`, you specify a `Period` by which to step when creating an
iterator of `YearMonth`; whereas, for `LocalTime`, you must specify a `Duration`.

```scala
// Convert String to YearMonth
val startMonth = "2018-04".toYearMonth
val endMonth   = "2020-03".toYearMonth

// Iterate over months, 3 months at a time
startMonth.iterateTo(endMonth, Period.ofMonths(3)) foreach { month =>
  val diff = startMonth.until(month, ChronoUnit.MONTHS)
  println(s"$month is $diff month(s) after $startMonth")
}

// Convert String to LocalTime
val startTime = "09:00".toLocalTime
val endTime   = "17:00".toLocalTime

// Iterate over times, 15 minutes at a time
startTime.iterateUntil(endTime, Duration.ofMinutes(15)) foreach {
  case time if time.getMinute == 0  => println(s"It's $time, back to work")
  case time if time.getMinute == 45 => println(s"It's $time, take a break")
  case time                         => println(s"It's $time")
}
```

For `LocalDateTime`, you can specify either a `Period` or `Duration` by which to step.

```scala
val _2yrs = Period.ofYears(2)
val startDateTime = "2019-06-15T12:30:45".toLocalDateTime
val endDateTime   = startDateTime + _2yrs * 3

// Iterate over 6 years, 2 years at a time
startDateTime.iterateTo(endDateTime, _2yrs).foreach { dateTime =>
  println(s"Date-time is $dateTime")
}

val _45secs = Duration.ofSeconds(45)
val otherDateTime = startDateTime + Duration.ofMinutes(3)

// Iterate over 3 minutes, 45 seconds at a time
startDateTime.iterateTo(otherDateTime, _45secs).foreach { dateTime =>
  println(s"Date-time is $dateTime")
}
```

Lastly, you can iterate between one `Duration` and another.

```scala
val _17mins = Duration.ofMinutes(17)

// Iterate from 17 minutes to 34 minutes, 45 seconds at a time
_17mins.iterateTo(_17mins * 2, _45secs).foreach { duration =>
  println(s"${duration.toMillis / 1000} secs from now is ${LocalTime.now() + duration}")
}
```

### Setting Dates to Common Boundaries

When working with dates, you can set them to common boundaries.

```scala
import DayOfWeek._

val now = LocalDate.now()

// Get first and last date of current month
val startOfMonth = now.atStartOfMonth
val endOfMonth = now.atEndOfMonth

// Get first and last date of current week
val startOfWeek = now.atStartOfWeek
val endOfWeek = now.atEndOfWeek

// Print first and last date of current week, with Monday as first day of week
println { now.atStartOfWeek(MONDAY) }
println { now.atEndOfWeek(SUNDAY) }
```

### Setting Times to Common Boundaries

When working with times, you can set them to common boundaries, too. However,
to set an end boundary, you must specify the `TimePrecision`. You may do this
explicitly by passing the precision in the method call, or keep an implicit
precision in scope.

```scala
import little.time.TimePrecision

// Implicitly set time precision to milliseconds
implicit val precision = TimePrecision.Milliseconds

val now = LocalTime.now()

// Get first and last millisecond of current hour
val startOfHour = now.atStartOfHour
val endOfHour = now.atEndOfHour

// Get first and last millisecond of current minute
val startOfMinute = now.atStartOfMinute
val endOfMinute = now.atEndOfMinute

// Get first and last millisecond of current second
val startOfSecond = now.atStartOfSecond
val endOfSecond = now.atEndOfSecond
```

The same applies to `LocalDateTime`, which is both a date and a time. You must
ensure an implicit `TimePrecision` is in scope when setting to an end boundary
&ndash; or supply one explicitly in the method call.

```scala
implicit val precision = TimePrecision.Seconds

val dateTime = "2017-05-12T15:23:17.123456789".toLocalDateTime

// Get last second of year
val endOfYear = dateTime.atEndOfYear

// Get last microsecond of day
val endOfDay = dateTime.atEndOfDay(TimePrecision.Microseconds)
```

## Working with Schedule

A `Schedule` offers a _cron_-like utility for specifying times at which
_something_ should occur. In particular, it may specify `months`, `daysOfMonth`,
`daysOfWeek`, `dates`, and `times`. To constrain a schedule to a finite period,
its effective `start` and `end` are always specified.

```scala
import java.time.DayOfWeek._
import java.time.LocalTime
import java.time.Month._

import little.time.Schedule
import little.time.Implicits._

// Create schedule that:
//   - starts on Jan 15
//   - ends before noon on Apr 15
//   - includes Jan, Feb, and Apr
//   - includes 1st and 15th of each month
//   - occurs at midnight and noon
val schedule = Schedule(
  start       = "2020-01-15T00:00:00".toLocalDateTime,
  end         = "2020-04-15T11:59:59".toLocalDateTime,
  months      = Seq(JANUARY, FEBRUARY, APRIL),
  daysOfMonth = Seq(1, 15),
  times       = Seq(LocalTime.MIDNIGHT, LocalTime.NOON)
)
```

A `Schedule` is an `Iterable[LocalDateTime]`, so there are various ways to
access its times.

```scala
// Zip scheduled times with indices and print each
schedule.zipWithIndex.foreach {
  case (time, index) => println(s"$index: $time")
}

// Filter times to those on Wednesdays
val humpDays = schedule.filter(_.getDayOfWeek == WEDNESDAY)
```

And there are other utilities provided for working with a schedule.

```scala
// Add Mondays and Fridays to schedule
val moreDays = schedule.withDaysOfWeek(MONDAY, FRIDAY)

// Get next scheduled time after specified time
val nextTime = moreDays.next("2020-02-01T12:00:01".toLocalDateTime)
```

## API Documentation

See [scaladoc](https://losizm.github.io/little-time/latest/api/little/time/index.html)
for additional details.

## License
**little-time** is licensed under the Apache License, Version 2. See LICENSE file
for more information.

