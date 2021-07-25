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
import java.time.temporal.TemporalAmount

private object Stepping:
  private class SteppingIterator[T](start: T, end: T, stepper: (T) => T, tester: (T, T) => Boolean) extends Iterator[T]:
    private var nextValue = start

    def hasNext: Boolean = tester(nextValue, end)

    def next(): T =
      hasNext match
        case true =>
          val value = nextValue
          nextValue = stepper(value)
          value
        case false => throw new NoSuchElementException()

  def isZero(amount: TemporalAmount): Boolean =
    amount match
      case p: Period   => p.isZero
      case d: Duration => d.isZero
      case _           => throw IllegalArgumentException(s"Unsupported temporal amount: $amount")

  def isNegative(amount: TemporalAmount): Boolean =
    amount match
      case p: Period   => p.isNegative
      case d: Duration => d.isNegative
      case _           => throw IllegalArgumentException(s"Unsupported temporal amount: $amount")

  def inclusive(start: Duration, end: Duration, step: Duration): Iterator[Duration] =
    require(!step.isZero, s"Invalid step: $step")

    val stepper = (x: Duration) => x.plus(step)
    val tester  = (a: Duration, b: Duration) => a.compareTo(b) * { if step.isNegative then -1 else 1 } <= 0

    SteppingIterator(start, end, stepper, tester)

  def exclusive(start: Duration, end: Duration, step: Duration): Iterator[Duration] =
    require(!step.isZero, s"Invalid step: $step")

    val stepper = (x: Duration) => x.plus(step)
    val tester  = (a: Duration, b: Duration) => a.compareTo(b) * { if step.isNegative then -1 else 1 } < 0

    SteppingIterator(start, end, stepper, tester)

  def inclusive(start: YearMonth, end: YearMonth, step: Period): Iterator[YearMonth] =
    require(!step.isZero, s"Invalid step: $step")

    val stepper = (x: YearMonth) => x.plus(step)
    val tester  = (a: YearMonth, b: YearMonth) => a.compareTo(b) * { if step.isNegative then -1 else 1 } <= 0

    SteppingIterator(start, end, stepper, tester)

  def exclusive(start: YearMonth, end: YearMonth, step: Period): Iterator[YearMonth] =
    require(!step.isZero, s"Invalid step: $step")

    val stepper = (x: YearMonth) => x.plus(step)
    val tester  = (a: YearMonth, b: YearMonth) => a.compareTo(b) * { if step.isNegative then -1 else 1 } < 0

    SteppingIterator(start, end, stepper, tester)

  def inclusive(start: LocalDate, end: LocalDate, step: Period): Iterator[LocalDate] =
    require(!step.isZero, s"Invalid step: $step")

    val stepper = (x: LocalDate) => x.plus(step)
    val tester  = (a: LocalDate, b: LocalDate) => a.compareTo(b) * { if step.isNegative then -1 else 1 } <= 0

    SteppingIterator(start, end, stepper, tester)

  def exclusive(start: LocalDate, end: LocalDate, step: Period): Iterator[LocalDate] =
    require(!isZero(step), s"Invalid step: $step")

    val stepper = (x: LocalDate) => x.plus(step)
    val tester  = (a: LocalDate, b: LocalDate) => a.compareTo(b) * { if step.isNegative then -1 else 1 } < 0

    SteppingIterator(start, end, stepper, tester)

  def inclusive(start: LocalTime, end: LocalTime, step: Duration): Iterator[LocalTime] =
    require(!step.isZero, s"Invalid step: $step")

    val stepper = (x: LocalTime) => x.plus(step)
    val tester  = (a: LocalTime, b: LocalTime) => a.compareTo(b) * { if step.isNegative then -1 else 1 } <= 0

    SteppingIterator(start, end, stepper, tester)

  def exclusive(start: LocalTime, end: LocalTime, step: Duration): Iterator[LocalTime] =
    require(!isZero(step), s"Invalid step: $step")

    val stepper = (x: LocalTime) => x.plus(step)
    val tester  = (a: LocalTime, b: LocalTime) => a.compareTo(b) * { if step.isNegative then -1 else 1 } < 0

    SteppingIterator(start, end, stepper, tester)

  def inclusive(start: LocalDateTime, end: LocalDateTime, step: TemporalAmount): Iterator[LocalDateTime] =
    require(!isZero(step), s"Invalid step: $step")

    val stepper = (x: LocalDateTime) => x.plus(step)
    val tester  = (a: LocalDateTime, b: LocalDateTime) => a.compareTo(b) * { if isNegative(step) then -1 else 1 } <= 0

    SteppingIterator(start, end, stepper, tester)

  def exclusive(start: LocalDateTime, end: LocalDateTime, step: TemporalAmount): Iterator[LocalDateTime] =
    require(!isZero(step), s"Invalid step: $step")

    val stepper = (x: LocalDateTime) => x.plus(step)
    val tester  = (a: LocalDateTime, b: LocalDateTime) => a.compareTo(b) * { if isNegative(step) then -1 else 1 } < 0

    SteppingIterator(start, end, stepper, tester)
