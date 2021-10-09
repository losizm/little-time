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

class StringExtSpec extends org.scalatest.flatspec.AnyFlatSpec:
  "String" should "be converted to YearMonth" in {
    assert("2019-07".toYearMonth == YearMonth.parse("2019-07"))
  }

  it should "be converted to LocalDate" in {
    assert("2019-07-11".toLocalDate == LocalDate.parse("2019-07-11"))
  }

  it should "be converted to LocalTime" in {
    assert("12:38".toLocalTime              == LocalTime.parse("12:38"))
    assert("12:38:45".toLocalTime           == LocalTime.parse("12:38:45"))
    assert("12:38:45.1".toLocalTime         == LocalTime.parse("12:38:45.1"))
    assert("12:38:45.123".toLocalTime       == LocalTime.parse("12:38:45.123"))
    assert("12:38:45.123456".toLocalTime    == LocalTime.parse("12:38:45.123456"))
    assert("12:38:45.123456789".toLocalTime == LocalTime.parse("12:38:45.123456789"))
  }

  it should "be converted to LocalDateTime" in {
    assert("2019-07-11T12:38".toLocalDateTime              == LocalDateTime.parse("2019-07-11T12:38"))
    assert("2019-07-11T12:38:45".toLocalDateTime           == LocalDateTime.parse("2019-07-11T12:38:45"))
    assert("2019-07-11T12:38:45.1".toLocalDateTime         == LocalDateTime.parse("2019-07-11T12:38:45.1"))
    assert("2019-07-11T12:38:45.123".toLocalDateTime       == LocalDateTime.parse("2019-07-11T12:38:45.123"))
    assert("2019-07-11T12:38:45.123456".toLocalDateTime    == LocalDateTime.parse("2019-07-11T12:38:45.123456"))
    assert("2019-07-11T12:38:45.123456789".toLocalDateTime == LocalDateTime.parse("2019-07-11T12:38:45.123456789"))
  }

  it should "be converted to Instant" in {
    assert("2019-07-11T12:38:45Z".toInstant           == Instant.parse("2019-07-11T12:38:45Z"))
    assert("2019-07-11T12:38:45.1Z".toInstant         == Instant.parse("2019-07-11T12:38:45.1Z"))
    assert("2019-07-11T12:38:45.123Z".toInstant       == Instant.parse("2019-07-11T12:38:45.123Z"))
    assert("2019-07-11T12:38:45.123456Z".toInstant    == Instant.parse("2019-07-11T12:38:45.123456Z"))
    assert("2019-07-11T12:38:45.123456789Z".toInstant == Instant.parse("2019-07-11T12:38:45.123456789Z"))
  }

  it should "be converted to Period" in {
    assert("P12Y6M5D".toPeriod == Period.parse("P12Y6M5D"))
  }

  it should "be converted to Duration" in {
    assert("P5DT3H2M1.999S".toDuration == Duration.parse("P5DT3H2M1.999S"))
  }
