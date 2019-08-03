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

import java.time.Duration

import org.scalatest.FlatSpec

import Implicits._

class DurationTypeSpec extends FlatSpec {
  private val duration = Duration.parse("P33DT5H6M4.123S")

  "Duration" should "be negated" in {
    assert(-duration == Duration.parse("-P33DT5H6M4.123S"))
    assert(duration == -Duration.parse("-P33DT5H6M4.123S"))
  }

  it should "have duration added" in {
    assert(duration + Duration.ofSeconds(1) == Duration.parse("P33DT5H6M5.123S"))
    assert(duration + Duration.ofMinutes(2) == Duration.parse("P33DT5H8M4.123S"))
    assert(duration + Duration.ofHours(31) == Duration.parse("P34DT12H6M4.123S"))
    assert(duration + Duration.ofMillis(5) == Duration.parse("P33DT5H6M4.128S"))
    assert(duration + Duration.ofNanos(2) == Duration.parse("P33DT5H6M4.123000002S"))
    assert(duration + Duration.ofDays(7) == Duration.parse("P40DT5H6M4.123S"))

    assert(duration + Duration.ofSeconds(-1) == Duration.parse("P33DT5H6M3.123S"))
    assert(duration + Duration.ofMinutes(-2) == Duration.parse("P33DT5H4M4.123S"))
    assert(duration + Duration.ofHours(-31) == Duration.parse("P31DT22H6M4.123S"))
    assert(duration + Duration.ofMillis(-5) == Duration.parse("P33DT5H6M4.118S"))
    assert(duration + Duration.ofNanos(-2) == Duration.parse("P33DT5H6M4.122999998S"))
    assert(duration + Duration.ofDays(-7) == Duration.parse("P26DT5H6M4.123S"))
  }

  it should "have duration subtracted" in {
    assert(duration - Duration.ofSeconds(1) == Duration.parse("P33DT5H6M3.123S"))
    assert(duration - Duration.ofMinutes(2) == Duration.parse("P33DT5H4M4.123S"))
    assert(duration - Duration.ofHours(31) == Duration.parse("P31DT22H6M4.123S"))
    assert(duration - Duration.ofMillis(5) == Duration.parse("P33DT5H6M4.118S"))
    assert(duration - Duration.ofNanos(2) == Duration.parse("P33DT5H6M4.122999998S"))
    assert(duration - Duration.ofDays(7) == Duration.parse("P26DT5H6M4.123S"))

    assert(duration - Duration.ofSeconds(-1) == Duration.parse("P33DT5H6M5.123S"))
    assert(duration - Duration.ofMinutes(-2) == Duration.parse("P33DT5H8M4.123S"))
    assert(duration - Duration.ofHours(-31) == Duration.parse("P34DT12H6M4.123S"))
    assert(duration - Duration.ofMillis(-5) == Duration.parse("P33DT5H6M4.128S"))
    assert(duration - Duration.ofNanos(-2) == Duration.parse("P33DT5H6M4.123000002S"))
    assert(duration - Duration.ofDays(-7) == Duration.parse("P40DT5H6M4.123S"))
  }

  it should "be compared to other" in {
    import Ordered.orderingToOrdered
    val other1 = Duration.parse("P33DT5H6M4S")
    val other2 = Duration.parse("P33DT5H6M4.123S")

    assert(duration > other1)
    assert(duration >= other1)
    assert(duration != other1)

    assert(other1 < duration)
    assert(other1 <= duration)
    assert(other1 != duration)

    assert(duration >= other2)
    assert(duration <= other2)
    assert(duration == other2)

    assert(other2 >= duration)
    assert(other2 <= duration)
    assert(other2 == duration)
  }

  it should "be greater than other" in {
    val other = Duration.parse("P33DT5H6M4S")
    assert(duration.max(other) == duration)
    assert(other.min(duration) == other)
  }

  it should "be less than other" in {
    val other = Duration.parse("P33DT5H6M4.124S")
    assert(duration.max(other) == other)
    assert(other.min(duration) == duration)
  }

  it should "create iterator to other duration (inclusive)" in {
    val other = Duration.parse("P33DT5H8M4.123S")

    var iter = duration.stepTo(other, Duration.ofMinutes(1))
    assert(iter.next() == duration)
    assert(iter.next() == Duration.parse("P33DT5H7M4.123S"))
    assert(iter.next() == other)
    assert(!iter.hasNext)
    assertThrows[NoSuchElementException](iter.next())

    iter = duration.stepTo(other, Duration.ofMinutes(2))
    assert(iter.next() == duration)
    assert(iter.next() == other)
    assert(!iter.hasNext)
    assertThrows[NoSuchElementException](iter.next())

    iter = duration.stepTo(other, Duration.ofMinutes(3))
    assert(iter.next() == duration)
    assert(!iter.hasNext)
    assertThrows[NoSuchElementException](iter.next())

    iter = other.stepTo(duration, Duration.ofMinutes(-1))
    assert(iter.next() == other)
    assert(iter.next() == Duration.parse("P33DT5H7M4.123S"))
    assert(iter.next() == duration)
    assert(!iter.hasNext)
    assertThrows[NoSuchElementException](iter.next())

    iter = other.stepTo(duration, Duration.ofMinutes(-2))
    assert(iter.next() == other)
    assert(iter.next() == duration)
    assert(!iter.hasNext)
    assertThrows[NoSuchElementException](iter.next())

    iter = other.stepTo(duration, Duration.ofMinutes(-3))
    assert(iter.next() == other)
    assert(!iter.hasNext)
    assertThrows[NoSuchElementException](iter.next())
  }

  it should "create iterator to other duration (exclusive)" in {
    val other = Duration.parse("P33DT5H8M4.123S")

    var iter = duration.stepUntil(other, Duration.ofMinutes(1))
    assert(iter.next() == duration)
    assert(iter.next() == Duration.parse("P33DT5H7M4.123S"))
    assert(!iter.hasNext)
    assertThrows[NoSuchElementException](iter.next())

    iter = duration.stepUntil(other, Duration.ofMinutes(2))
    assert(iter.next() == duration)
    assert(!iter.hasNext)
    assertThrows[NoSuchElementException](iter.next())

    iter = duration.stepUntil(other, Duration.ofMinutes(3))
    assert(iter.next() == duration)
    assert(!iter.hasNext)
    assertThrows[NoSuchElementException](iter.next())

    iter = other.stepUntil(duration, Duration.ofMinutes(-1))
    assert(iter.next() == other)
    assert(iter.next() == Duration.parse("P33DT5H7M4.123S"))
    assert(!iter.hasNext)
    assertThrows[NoSuchElementException](iter.next())

    iter = other.stepUntil(duration, Duration.ofMinutes(-2))
    assert(iter.next() == other)
    assert(!iter.hasNext)
    assertThrows[NoSuchElementException](iter.next())

    iter = other.stepUntil(duration, Duration.ofMinutes(-3))
    assert(iter.next() == other)
    assert(!iter.hasNext)
    assertThrows[NoSuchElementException](iter.next())
  }
}

