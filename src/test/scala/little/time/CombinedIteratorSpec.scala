/*
 * Copyright 2020 Carlos Conyers
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

import java.time.LocalDateTime

import Implicits.{ LocalDateTimeType, TimeStringType, localDateTimeOrdering }

class CombinedIteratorSpec extends org.scalatest.flatspec.AnyFlatSpec {
  it should "iterate over two Int iterators" in {
    val a = 10 to 50 by 10
    val b = 7  to 19 by 3

    val iter1 = new CombinedIterator[Int](a.iterator, b.iterator)
    assert(iter1.nonEmpty)
    assert(iter1.next() == 7)
    assert(iter1.next() == 10)
    assert(iter1.next() == 13)
    assert(iter1.next() == 16)
    assert(iter1.next() == 19)
    assert(iter1.next() == 20)
    assert(iter1.next() == 30)
    assert(iter1.next() == 40)
    assert(iter1.next() == 50)
    assertThrows[NoSuchElementException](iter1.next())

    val iter2 = new CombinedIterator[Int](b.iterator, a.iterator)
    assert(iter2.nonEmpty)
    assert(iter2.next() == 7)
    assert(iter2.next() == 10)
    assert(iter2.next() == 13)
    assert(iter2.next() == 16)
    assert(iter2.next() == 19)
    assert(iter2.next() == 20)
    assert(iter2.next() == 30)
    assert(iter2.next() == 40)
    assert(iter2.next() == 50)
    assertThrows[NoSuchElementException](iter2.next())

    val iter3 = new CombinedIterator[Int](a.iterator, Iterator.empty)
    assert(iter3.nonEmpty)
    assert(iter3.next() == 10)
    assert(iter3.next() == 20)
    assert(iter3.next() == 30)
    assert(iter3.next() == 40)
    assert(iter3.next() == 50)
    assertThrows[NoSuchElementException](iter3.next())

    val iter4 = new CombinedIterator[Int](b.iterator, Iterator.empty)
    assert(iter4.nonEmpty)
    assert(iter4.next() == 7)
    assert(iter4.next() == 10)
    assert(iter4.next() == 13)
    assert(iter4.next() == 16)
    assert(iter4.next() == 19)
    assertThrows[NoSuchElementException](iter4.next())
  }

  it should "iterate over two LocalDateTime iterators" in {
    val a = "2020-10-01T00:10".toLocalDateTime.iterateTo("2020-10-01T00:50".toLocalDateTime, "PT10M".toDuration).toSeq
    val b = "2020-10-01T00:07".toLocalDateTime.iterateTo("2020-10-01T00:19".toLocalDateTime, "PT03M".toDuration).toSeq

    val iter1 = new CombinedIterator[LocalDateTime](a.iterator, b.iterator)
    assert(iter1.nonEmpty)
    assert(iter1.next() == "2020-10-01T00:07".toLocalDateTime)
    assert(iter1.next() == "2020-10-01T00:10".toLocalDateTime)
    assert(iter1.next() == "2020-10-01T00:13".toLocalDateTime)
    assert(iter1.next() == "2020-10-01T00:16".toLocalDateTime)
    assert(iter1.next() == "2020-10-01T00:19".toLocalDateTime)
    assert(iter1.next() == "2020-10-01T00:20".toLocalDateTime)
    assert(iter1.next() == "2020-10-01T00:30".toLocalDateTime)
    assert(iter1.next() == "2020-10-01T00:40".toLocalDateTime)
    assert(iter1.next() == "2020-10-01T00:50".toLocalDateTime)
    assertThrows[NoSuchElementException](iter1.next())

    val iter2 = new CombinedIterator[LocalDateTime](b.iterator, a.iterator)
    assert(iter2.nonEmpty)
    assert(iter2.next() == "2020-10-01T00:07".toLocalDateTime)
    assert(iter2.next() == "2020-10-01T00:10".toLocalDateTime)
    assert(iter2.next() == "2020-10-01T00:13".toLocalDateTime)
    assert(iter2.next() == "2020-10-01T00:16".toLocalDateTime)
    assert(iter2.next() == "2020-10-01T00:19".toLocalDateTime)
    assert(iter2.next() == "2020-10-01T00:20".toLocalDateTime)
    assert(iter2.next() == "2020-10-01T00:30".toLocalDateTime)
    assert(iter2.next() == "2020-10-01T00:40".toLocalDateTime)
    assert(iter2.next() == "2020-10-01T00:50".toLocalDateTime)
    assertThrows[NoSuchElementException](iter2.next())

    val iter3 = new CombinedIterator[LocalDateTime](a.iterator, Iterator.empty)
    assert(iter3.nonEmpty)
    assert(iter3.next() == "2020-10-01T00:10".toLocalDateTime)
    assert(iter3.next() == "2020-10-01T00:20".toLocalDateTime)
    assert(iter3.next() == "2020-10-01T00:30".toLocalDateTime)
    assert(iter3.next() == "2020-10-01T00:40".toLocalDateTime)
    assert(iter3.next() == "2020-10-01T00:50".toLocalDateTime)
    assertThrows[NoSuchElementException](iter3.next())

    val iter4 = new CombinedIterator[LocalDateTime](b.iterator, Iterator.empty)
    assert(iter4.nonEmpty)
    assert(iter4.next() == "2020-10-01T00:07".toLocalDateTime)
    assert(iter4.next() == "2020-10-01T00:10".toLocalDateTime)
    assert(iter4.next() == "2020-10-01T00:13".toLocalDateTime)
    assert(iter4.next() == "2020-10-01T00:16".toLocalDateTime)
    assert(iter4.next() == "2020-10-01T00:19".toLocalDateTime)
    assertThrows[NoSuchElementException](iter4.next())
  }
}
