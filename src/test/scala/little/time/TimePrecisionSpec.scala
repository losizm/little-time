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

    assert { FractionalSeconds(1).limit == LocalTime.parse("23:59:59.9") }
    assert { FractionalSeconds(2).limit == LocalTime.parse("23:59:59.99") }
    assert { FractionalSeconds(3).limit == LocalTime.parse("23:59:59.999") }
    assert { FractionalSeconds(4).limit == LocalTime.parse("23:59:59.9999") }
    assert { FractionalSeconds(5).limit == LocalTime.parse("23:59:59.99999") }
    assert { FractionalSeconds(6).limit == LocalTime.parse("23:59:59.999999") }
    assert { FractionalSeconds(7).limit == LocalTime.parse("23:59:59.9999999") }
    assert { FractionalSeconds(8).limit == LocalTime.parse("23:59:59.99999999") }
    assert { FractionalSeconds(9).limit == LocalTime.parse("23:59:59.999999999") }
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

    assert { FractionalSeconds(1) < FractionalSeconds(2) }
    assert { FractionalSeconds(2) < FractionalSeconds(3) }
    assert { FractionalSeconds(3) < FractionalSeconds(4) }
    assert { FractionalSeconds(4) < FractionalSeconds(5) }
    assert { FractionalSeconds(5) < FractionalSeconds(6) }
    assert { FractionalSeconds(6) < FractionalSeconds(7) }
    assert { FractionalSeconds(7) < FractionalSeconds(8) }
    assert { FractionalSeconds(8) < FractionalSeconds(9) }

    assert { FractionalSeconds(9) > FractionalSeconds(8) }
    assert { FractionalSeconds(8) > FractionalSeconds(7) }
    assert { FractionalSeconds(7) > FractionalSeconds(6) }
    assert { FractionalSeconds(6) > FractionalSeconds(5) }
    assert { FractionalSeconds(5) > FractionalSeconds(4) }
    assert { FractionalSeconds(4) > FractionalSeconds(3) }
    assert { FractionalSeconds(3) > FractionalSeconds(2) }
    assert { FractionalSeconds(2) > FractionalSeconds(1) }

    assert { Milliseconds == FractionalSeconds(3) }
    assert { Microseconds == FractionalSeconds(6) }
    assert { Nanoseconds == FractionalSeconds(9) }

    assert { (1 to 9).forall(Hours        < FractionalSeconds(_)) }
    assert { (1 to 9).forall(Minutes      < FractionalSeconds(_)) }
    assert { (1 to 9).forall(Seconds      < FractionalSeconds(_)) }
    assert { (4 to 9).forall(Milliseconds < FractionalSeconds(_)) }
    assert { (7 to 9).forall(Microseconds < FractionalSeconds(_)) }

    assert { (1 to 9).forall(FractionalSeconds(_) > Hours) }
    assert { (1 to 9).forall(FractionalSeconds(_) > Minutes) }
    assert { (1 to 9).forall(FractionalSeconds(_) > Seconds) }
    assert { (4 to 9).forall(FractionalSeconds(_) > Milliseconds) }
    assert { (7 to 9).forall(FractionalSeconds(_) > Microseconds) }
  }

  "FractionalSeconds" should "have scale" in {
    assert { Milliseconds.scale == 3 }
    assert { Microseconds.scale == 6 }
    assert { Nanoseconds.scale == 9 }

    assert { (1 to 9).forall(scale => FractionalSeconds(scale).scale == scale) }
  }

  it should "throw IllegalArgumentException for scale out of range" in {
    assertThrows[IllegalArgumentException] { FractionalSeconds(-1) }
    assertThrows[IllegalArgumentException] { FractionalSeconds(0) }
    assertThrows[IllegalArgumentException] { FractionalSeconds(10) }
    assertThrows[IllegalArgumentException] { FractionalSeconds(11) }
  }
}

