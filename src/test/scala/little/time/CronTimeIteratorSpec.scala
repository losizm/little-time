/*
 * Copyright 2023 Carlos Conyers
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

class CronTimeIteratorSpec extends org.scalatest.flatspec.AnyFlatSpec:
  it should "create time iterator (1)" in {
    val iter = CronTimeIterator(0 to 1, 1 to 5, Seq(0))

    assert(iter.nonEmpty)
    assert(iter.next() == LocalTime.of(0, 1, 0))
    assert(iter.next() == LocalTime.of(0, 2, 0))
    assert(iter.next() == LocalTime.of(0, 3, 0))
    assert(iter.next() == LocalTime.of(0, 4, 0))
    assert(iter.next() == LocalTime.of(0, 5, 0))
    assert(iter.next() == LocalTime.of(1, 1, 0))
    assert(iter.next() == LocalTime.of(1, 2, 0))
    assert(iter.next() == LocalTime.of(1, 3, 0))
    assert(iter.next() == LocalTime.of(1, 4, 0))
    assert(iter.next() == LocalTime.of(1, 5, 0))
    assertThrows[NoSuchElementException](iter.next())
  }

  it should "create time iterator (2)" in {
    val iter = CronTimeIterator(Seq(5), 1 to 5, 2 to 3)

    assert(iter.nonEmpty)
    assert(iter.next() == LocalTime.of(5, 1, 2))
    assert(iter.next() == LocalTime.of(5, 1, 3))
    assert(iter.next() == LocalTime.of(5, 2, 2))
    assert(iter.next() == LocalTime.of(5, 2, 3))
    assert(iter.next() == LocalTime.of(5, 3, 2))
    assert(iter.next() == LocalTime.of(5, 3, 3))
    assert(iter.next() == LocalTime.of(5, 4, 2))
    assert(iter.next() == LocalTime.of(5, 4, 3))
    assert(iter.next() == LocalTime.of(5, 5, 2))
    assert(iter.next() == LocalTime.of(5, 5, 3))
    assertThrows[NoSuchElementException](iter.next())
  }

  it should "create time iterator (3)" in {
    val iter = CronTimeIterator(Seq(4, 5), 1 to 5, 2 to 3)

    assert(iter.nonEmpty)
    assert(iter.next() == LocalTime.of(4, 1, 2))
    assert(iter.next() == LocalTime.of(4, 1, 3))
    assert(iter.next() == LocalTime.of(4, 2, 2))
    assert(iter.next() == LocalTime.of(4, 2, 3))
    assert(iter.next() == LocalTime.of(4, 3, 2))
    assert(iter.next() == LocalTime.of(4, 3, 3))
    assert(iter.next() == LocalTime.of(4, 4, 2))
    assert(iter.next() == LocalTime.of(4, 4, 3))
    assert(iter.next() == LocalTime.of(4, 5, 2))
    assert(iter.next() == LocalTime.of(4, 5, 3))
    assert(iter.next() == LocalTime.of(5, 1, 2))
    assert(iter.next() == LocalTime.of(5, 1, 3))
    assert(iter.next() == LocalTime.of(5, 2, 2))
    assert(iter.next() == LocalTime.of(5, 2, 3))
    assert(iter.next() == LocalTime.of(5, 3, 2))
    assert(iter.next() == LocalTime.of(5, 3, 3))
    assert(iter.next() == LocalTime.of(5, 4, 2))
    assert(iter.next() == LocalTime.of(5, 4, 3))
    assert(iter.next() == LocalTime.of(5, 5, 2))
    assert(iter.next() == LocalTime.of(5, 5, 3))
    assertThrows[NoSuchElementException](iter.next())
  }

  it should "create time iterator (4)" in {
    val iter = CronTimeIterator(Nil, 1 to 5, 2 to 3)

    assert(iter.nonEmpty)

    for
      h <- 0 to 23
      m <- 1 to 5
      s <- 2 to 3
    do
      assert(iter.next() == LocalTime.of(h, m, s))

    assertThrows[NoSuchElementException](iter.next())
  }

  it should "create time iterator (5)" in {
    val iter = CronTimeIterator(Nil, 1 to 5, Nil)

    assert(iter.nonEmpty)

    for
      h <- 0 to 23
      m <- 1 to 5
      s <- 0 to 59
    do
      assert(iter.next() == LocalTime.of(h, m, s))

    assertThrows[NoSuchElementException](iter.next())
  }

  it should "create time iterator (6)" in {
    val iter = CronTimeIterator(Nil, Nil, Nil)

    assert(iter.nonEmpty)

    for
      h <- 0 to 23
      m <- 0 to 59
      s <- 0 to 59
    do
      assert(iter.next() == LocalTime.of(h, m, s))

    assertThrows[NoSuchElementException](iter.next())
  }
