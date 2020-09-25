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

private class CombinedIterator[T](left: Iterator[T], right: Iterator[T])
    (implicit ord: Ordering[T]) extends Iterator[T] {

  private var nextLeft  = getNext(left)
  private var nextRight = getNext(right)

  def hasNext = nextLeft.isDefined || nextRight.isDefined

  def next() = (nextLeft, nextRight) match {
    case (Some(l), Some(r)) =>
      if (ord.lt(l, r)) {
        nextLeft  = getNext(left)
        l
      } else if (ord.gt(l, r)) {
        nextRight = getNext(right)
        r
      } else {
        nextLeft  = getNext(left)
        nextRight = getNext(right)
        l
      }

    case (Some(l), None) =>
      nextLeft = getNext(left)
      l

    case (None, Some(r)) =>
      nextRight = getNext(right)
      r

    case (None, None) =>
      throw new NoSuchElementException()
  }

  private def getNext(iter: Iterator[T]): Option[T] =
    iter.hasNext match {
      case true  => Some(iter.next())
      case false => None
    }
}
