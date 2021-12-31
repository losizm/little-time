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

import java.time.Duration

import Stepping.*

/** Provides extension methods for `java.time.Duration` */
implicit class DurationMethods(duration: Duration) extends AnyVal:
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
