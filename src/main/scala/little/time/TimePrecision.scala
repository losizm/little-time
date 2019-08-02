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

import java.time.{ LocalDateTime, LocalTime }

/** Specifies precision of time. */
sealed trait TimePrecision extends Ordered[TimePrecision] {
  /** Gets maximum time with applied precision. */
  def limit: LocalTime

  /**
   * Compares to other time precision.
   *
   * @param that other time precision
   */
  def compare(that: TimePrecision): Int =
    limit.compareTo(that.limit)
}

/** Provides available `TimePrecision`s .*/
object TimePrecision {
  /** Indicates time precision of hours. */
  case object Hours extends TimePrecision {
    val limit: LocalTime = LocalTime.of(23, 0, 0)
  }

  /** Indicates time precision of minutes. */
  case object Minutes extends TimePrecision {
    val limit: LocalTime = LocalTime.of(23, 59, 0)
  }

  /** Indicates time precision of seconds. */
  case object Seconds extends TimePrecision {
    val limit: LocalTime = LocalTime.of(23, 59, 59)
  }

  /** Indicates time precision of fractional seconds. */
  trait FractionalSeconds extends TimePrecision {
    /** Number of digits in fractional seconds. */
    def scale: Int

    lazy val limit: LocalTime = LocalTime.parse(s"23:59:59.${"9" * scale}")
  }

  /** Factory for time precision of fractional seconds. */ 
  object FractionalSeconds {
    /**
     * Gets time precision with applied scale of fractional seconds.
     *
     * @param scale scale of fractional seconds
     *
     * @throws IllegalArgumentException if `scale < 1` or `scale > 9`
     */
    def apply(scale: Int): FractionalSeconds =
      scale match {
        case 1 => FractionalSeconds1
        case 2 => FractionalSeconds2
        case 3 => Milliseconds
        case 4 => FractionalSeconds4
        case 5 => FractionalSeconds5
        case 6 => Microseconds
        case 7 => FractionalSeconds7
        case 8 => FractionalSeconds8
        case 9 => Nanoseconds
        case _ => throw new IllegalArgumentException(s"Invalid scale: $scale")
      }

    /**
     * Destructures fractional seconds to its scale value.
     *
     * @param seconds
     */
    def unapply(fsecs: FractionalSeconds): Option[Int] =
      if (fsecs == null ) None
      else Some(fsecs.scale)
  }

  /**
   * Indicates time precision of milliseconds.
   *
   * @note Scale is 3.
   */
  case object Milliseconds extends FractionalSeconds { val scale: Int = 3 }

  /**
   * Indicates time precision of microseconds.
   *
   * @note Scale is 6.
   */
  case object Microseconds extends FractionalSeconds { val scale: Int = 6 }

  /**
   * Indicates time precision of nanoseconds.
   *
   * @note Scale is 9.
   */
  case object Nanoseconds extends FractionalSeconds { val scale: Int = 9 }

  private case object FractionalSeconds1 extends FractionalSeconds { val scale: Int = 1 }
  private case object FractionalSeconds2 extends FractionalSeconds { val scale: Int = 2 }
  private case object FractionalSeconds4 extends FractionalSeconds { val scale: Int = 4 }
  private case object FractionalSeconds5 extends FractionalSeconds { val scale: Int = 5 }
  private case object FractionalSeconds7 extends FractionalSeconds { val scale: Int = 7 }
  private case object FractionalSeconds8 extends FractionalSeconds { val scale: Int = 8 }
}

