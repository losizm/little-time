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

/** Provides time-related extension methods for `java.lang.String`. */
implicit class TimeStringMethods(string: String) extends AnyVal:
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

  /** Converts formatted string to `Instant`. */
  def toInstant: Instant =
    Instant.parse(string)
