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

import java.time.Period

import org.scalatest.FlatSpec

import Implicits._

class PeriodTypeSpec extends FlatSpec {
  private val period = Period.of(1, 3, 7)

  "Period" should "be negated" in {
    assert(-period == Period.of(-1, -3, -7))
    assert(period == -Period.of(-1, -3, -7))
  }

  it should "have period added" in {
    assert(period + Period.ofYears(1) == Period.of(2, 3, 7))
    assert(period + Period.ofMonths(3) == Period.of(1, 6, 7))
    assert(period + Period.ofWeeks(2) == Period.of(1, 3, 21))
    assert(period + Period.ofDays(7) == Period.of(1, 3, 14))
    assert(period + period == Period.of(2, 6, 14))

    assert(period + Period.ofYears(-1) == Period.of(0, 3, 7))
    assert(period + Period.ofMonths(-3) == Period.of(1, 0, 7))
    assert(period + Period.ofWeeks(-2) == Period.of(1, 3, -7))
    assert(period + Period.ofDays(-7) == Period.of(1, 3, 0))
    assert(period + -period == Period.of(0, 0, 0))
  }

  it should "have period subtracted" in {
    assert(period - Period.ofYears(1) == Period.of(0, 3, 7))
    assert(period - Period.ofMonths(3) == Period.of(1, 0, 7))
    assert(period - Period.ofWeeks(2) == Period.of(1, 3, -7))
    assert(period - Period.ofDays(7) == Period.of(1, 3, 0))
    assert(period - period == Period.of(0, 0, 0))

    assert(period - Period.ofYears(-1) == Period.of(2, 3, 7))
    assert(period - Period.ofMonths(-3) == Period.of(1, 6, 7))
    assert(period - Period.ofWeeks(-2) == Period.of(1, 3, 21))
    assert(period - Period.ofDays(-7) == Period.of(1, 3, 14))
    assert(period - -period == Period.of(2, 6, 14))
  }
}

