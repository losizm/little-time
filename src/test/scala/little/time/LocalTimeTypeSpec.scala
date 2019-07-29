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

import java.time.LocalTime

import org.scalatest.FlatSpec

import Implicits._

class LocalTimeTypeSpec extends FlatSpec {
  private val time = LocalTime.parse("12:38:45.123456789")

  "LocalTime" should "be compared to other" in {
    import Ordered.orderingToOrdered
    val other1 = LocalTime.parse("12:38:45")
    val other2 = LocalTime.parse("12:38:45.123456789")
    
    assert(time > other1)
    assert(time >= other1)
    assert(time != other1)

    assert(other1 < time)
    assert(other1 <= time)
    assert(other1 != time)
    
    assert(time >= other2)
    assert(time <= other2)
    assert(time == other2)

    assert(other2 >= time)
    assert(other2 <= time)
    assert(other2 == time)
  }

  it should "be greater than other" in {
    val other = LocalTime.parse("12:38:45")
    assert(time.max(other) == time)
    assert(other.min(time) == other)
  }

  it should "be less than other" in {
    val other = LocalTime.parse("15:32:46.789")
    assert(time.max(other) == other)
    assert(other.min(time) == time)
  }

  it should "be truncated to microsecond" in {
    assert(time.atStartOfMicrosecond == LocalTime.parse("12:38:45.123456"))
  }

  it should "be truncated to millisecond" in {
    assert(time.atStartOfMillisecond == LocalTime.parse("12:38:45.123"))
  }

  it should "be truncated to second" in {
    assert(time.atStartOfSecond == LocalTime.parse("12:38:45"))
  }

  it should "be truncated to minute" in {
    assert(time.atStartOfMinute == LocalTime.parse("12:38"))
  }

  it should "be truncated to hour" in {
    assert(time.atStartOfHour == LocalTime.parse("12:00"))
  }
}

