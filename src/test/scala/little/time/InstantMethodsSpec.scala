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

import java.time.{ Duration, Instant, Period }

class InstantMethodsSpec extends org.scalatest.flatspec.AnyFlatSpec:
  private val instant = Instant.parse("2019-07-11T12:38:45.123456789Z")

  it should "add duration to instant" in {
    assert(instant + Duration.ofSeconds(1) == Instant.parse("2019-07-11T12:38:46.123456789Z"))
    assert(instant + Duration.ofMinutes(2) == Instant.parse("2019-07-11T12:40:45.123456789Z"))
    assert(instant + Duration.ofHours(31)  == Instant.parse("2019-07-12T19:38:45.123456789Z"))
    assert(instant + Duration.ofMillis(5)  == Instant.parse("2019-07-11T12:38:45.128456789Z"))
    assert(instant + Duration.ofNanos(2)   == Instant.parse("2019-07-11T12:38:45.123456791Z"))
    assert(instant + Duration.ofDays(7)    == Instant.parse("2019-07-18T12:38:45.123456789Z"))

    assert(instant + Duration.ofSeconds(-1) == Instant.parse("2019-07-11T12:38:44.123456789Z"))
    assert(instant + Duration.ofMinutes(-2) == Instant.parse("2019-07-11T12:36:45.123456789Z"))
    assert(instant + Duration.ofHours(-31)  == Instant.parse("2019-07-10T05:38:45.123456789Z"))
    assert(instant + Duration.ofMillis(-5)  == Instant.parse("2019-07-11T12:38:45.118456789Z"))
    assert(instant + Duration.ofNanos(-2)   == Instant.parse("2019-07-11T12:38:45.123456787Z"))
    assert(instant + Duration.ofDays(-7)    == Instant.parse("2019-07-04T12:38:45.123456789Z"))
  }

  it should "subtract duration from instant" in {
    assert(instant - Duration.ofSeconds(1) == Instant.parse("2019-07-11T12:38:44.123456789Z"))
    assert(instant - Duration.ofMinutes(2) == Instant.parse("2019-07-11T12:36:45.123456789Z"))
    assert(instant - Duration.ofHours(31)  == Instant.parse("2019-07-10T05:38:45.123456789Z"))
    assert(instant - Duration.ofMillis(5)  == Instant.parse("2019-07-11T12:38:45.118456789Z"))
    assert(instant - Duration.ofNanos(2)   == Instant.parse("2019-07-11T12:38:45.123456787Z"))
    assert(instant - Duration.ofDays(7)    == Instant.parse("2019-07-04T12:38:45.123456789Z"))

    assert(instant - Duration.ofSeconds(-1) == Instant.parse("2019-07-11T12:38:46.123456789Z"))
    assert(instant - Duration.ofMinutes(-2) == Instant.parse("2019-07-11T12:40:45.123456789Z"))
    assert(instant - Duration.ofHours(-31)  == Instant.parse("2019-07-12T19:38:45.123456789Z"))
    assert(instant - Duration.ofMillis(-5)  == Instant.parse("2019-07-11T12:38:45.128456789Z"))
    assert(instant - Duration.ofNanos(-2)   == Instant.parse("2019-07-11T12:38:45.123456791Z"))
    assert(instant - Duration.ofDays(-7)    == Instant.parse("2019-07-18T12:38:45.123456789Z"))
  }

  it should "add period to instant" in {
    assert(instant + Period.ofDays(1)   == Instant.parse("2019-07-12T12:38:45.123456789Z"))
    assert(instant + Period.ofDays(2)   == Instant.parse("2019-07-13T12:38:45.123456789Z"))
    assert(instant + Period.ofDays(31)  == Instant.parse("2019-08-11T12:38:45.123456789Z"))
    assert(instant + Period.ofWeeks(2)  == Instant.parse("2019-07-25T12:38:45.123456789Z"))

    assert(instant + Period.ofDays(-1)  == Instant.parse("2019-07-10T12:38:45.123456789Z"))
    assert(instant + Period.ofDays(-2)  == Instant.parse("2019-07-09T12:38:45.123456789Z"))
    assert(instant + Period.ofDays(-11) == Instant.parse("2019-06-30T12:38:45.123456789Z"))
    assert(instant + Period.ofWeeks(-2) == Instant.parse("2019-06-27T12:38:45.123456789Z"))
  }

  it should "substract period from instant" in {
    assert(instant - Period.ofDays(1)   == Instant.parse("2019-07-10T12:38:45.123456789Z"))
    assert(instant - Period.ofDays(2)   == Instant.parse("2019-07-09T12:38:45.123456789Z"))
    assert(instant - Period.ofDays(11)  == Instant.parse("2019-06-30T12:38:45.123456789Z"))
    assert(instant - Period.ofWeeks(2)  == Instant.parse("2019-06-27T12:38:45.123456789Z"))

    assert(instant - Period.ofDays(-1)     == Instant.parse("2019-07-12T12:38:45.123456789Z"))
    assert(instant - Period.ofDays(-2)     == Instant.parse("2019-07-13T12:38:45.123456789Z"))
    assert(instant - Period.ofDays(-31)    == Instant.parse("2019-08-11T12:38:45.123456789Z"))
    assert(instant - Period.ofWeeks(-2)    == Instant.parse("2019-07-25T12:38:45.123456789Z"))
  }

  it should "compare instants" in {
    import Ordered.orderingToOrdered
    val other1 = Instant.parse("2019-06-05T12:38:45Z")
    val other2 = Instant.parse("2019-07-11T12:38:45.123456789Z")

    assert(instant > other1)
    assert(instant >= other1)
    assert(instant != other1)

    assert(other1 < instant)
    assert(other1 <= instant)
    assert(other1 != instant)

    assert(instant >= other2)
    assert(instant <= other2)
    assert(instant == other2)

    assert(other2 >= instant)
    assert(other2 <= instant)
    assert(other2 == instant)
  }

  it should "min/max instants" in {
    val other1 = Instant.parse("2019-06-05T12:38:45Z")
    val other2 = Instant.parse("2019-10-23T05:32:46.789Z")

    assert(other1.min(instant) == other1)
    assert(other2.min(instant) == instant)

    assert(instant.max(other1) == instant)
    assert(instant.max(other2) == other2)
  }
