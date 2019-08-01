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

import TimePrecision._

class TimePrecisionSpec extends FlatSpec {
  "TimePrecision" should "have limit" in {
    assert { Hours.limit        == LocalTime.parse("23:00") }
    assert { Minutes.limit      == LocalTime.parse("23:59") }
    assert { Seconds.limit      == LocalTime.parse("23:59:59") }
    assert { Milliseconds.limit == LocalTime.parse("23:59:59.999") }
    assert { Microseconds.limit == LocalTime.parse("23:59:59.999999") }
    assert { Nanoseconds.limit  == LocalTime.parse("23:59:59.999999999") }

    assert { FSeconds(1).limit == LocalTime.parse("23:59:59.9") }
    assert { FSeconds(2).limit == LocalTime.parse("23:59:59.99") }
    assert { FSeconds(3).limit == LocalTime.parse("23:59:59.999") }
    assert { FSeconds(4).limit == LocalTime.parse("23:59:59.9999") }
    assert { FSeconds(5).limit == LocalTime.parse("23:59:59.99999") }
    assert { FSeconds(6).limit == LocalTime.parse("23:59:59.999999") }
    assert { FSeconds(7).limit == LocalTime.parse("23:59:59.9999999") }
    assert { FSeconds(8).limit == LocalTime.parse("23:59:59.99999999") }
    assert { FSeconds(9).limit == LocalTime.parse("23:59:59.999999999") }
  }

  it should "be compared" in {
    assert { Hours < Minutes }
    assert { Minutes < Seconds }
    assert { Seconds < Milliseconds }
    assert { Milliseconds < Microseconds }
    assert { Microseconds < Nanoseconds }

    assert { Nanoseconds > Microseconds }
    assert { Microseconds > Milliseconds }
    assert { Milliseconds > Seconds }
    assert { Seconds > Minutes }
    assert { Minutes > Hours }

    assert { FSeconds(1) < FSeconds(2) }
    assert { FSeconds(2) < FSeconds(3) }
    assert { FSeconds(3) < FSeconds(4) }
    assert { FSeconds(4) < FSeconds(5) }
    assert { FSeconds(5) < FSeconds(6) }
    assert { FSeconds(6) < FSeconds(7) }
    assert { FSeconds(7) < FSeconds(8) }
    assert { FSeconds(8) < FSeconds(9) }

    assert { FSeconds(9) > FSeconds(8) }
    assert { FSeconds(8) > FSeconds(7) }
    assert { FSeconds(7) > FSeconds(6) }
    assert { FSeconds(6) > FSeconds(5) }
    assert { FSeconds(5) > FSeconds(4) }
    assert { FSeconds(4) > FSeconds(3) }
    assert { FSeconds(3) > FSeconds(2) }
    assert { FSeconds(2) > FSeconds(1) }

    assert { Milliseconds == FSeconds(3) }
    assert { Microseconds == FSeconds(6) }
    assert { Nanoseconds == FSeconds(9) }

    assert { (1 to 9).forall(Hours        < FSeconds(_)) }
    assert { (1 to 9).forall(Minutes      < FSeconds(_)) }
    assert { (1 to 9).forall(Seconds      < FSeconds(_)) }
    assert { (4 to 9).forall(Milliseconds < FSeconds(_)) }
    assert { (7 to 9).forall(Microseconds < FSeconds(_)) }

    assert { (1 to 9).forall(FSeconds(_) > Hours) }
    assert { (1 to 9).forall(FSeconds(_) > Minutes) }
    assert { (1 to 9).forall(FSeconds(_) > Seconds) }
    assert { (4 to 9).forall(FSeconds(_) > Milliseconds) }
    assert { (7 to 9).forall(FSeconds(_) > Microseconds) }
  }

  "FSeconds" should "have scale" in {
    assert { Milliseconds.scale == 3 }
    assert { Microseconds.scale == 6 }
    assert { Nanoseconds.scale == 9 }

    assert { (1 to 9).forall(scale => FSeconds(scale).scale == scale) }
  }

  it should "throw IllegalArgumentException for scale out of range" in {
    assertThrows[IllegalArgumentException] { FSeconds(-1) }
    assertThrows[IllegalArgumentException] { FSeconds(0) }
    assertThrows[IllegalArgumentException] { FSeconds(10) }
    assertThrows[IllegalArgumentException] { FSeconds(11) }
  }
}

