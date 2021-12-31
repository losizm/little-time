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

import java.time.Period

/** Provides extension methods for `java.time.Period` */
implicit class PeriodMethods(period: Period) extends AnyVal:
  /** Gets negated period. */
  def unary_- : Period =
    period.negated()

  /**
   * Gets period with specified amount added.
   *
   * @param amount period to add
   */
  def +(amount: Period): Period =
    period.plus(amount)

  /**
   * Gets period with specified amount subtracted.
   *
   * @param amount period to subtract
   */
  def -(amount: Period): Period =
    period.minus(amount)

  /**
   * Gets duration after multiplication.
   *
   * @param n number by which period is multiplied
   */
  def *(n: Int): Period =
    period.multipliedBy(n)
