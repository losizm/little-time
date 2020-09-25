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

import java.time.{ DayOfWeek, LocalDate, Month }

private case class CronDateIterator(
  startDate:   LocalDate,
  endDate:     LocalDate,
  months:      Seq[Month],
  daysOfMonth: Seq[Int],
  daysOfWeek:  Seq[DayOfWeek]) extends Iterator[LocalDate] {

  private var currDay  = startDate.toEpochDay
  private val lastDay  = endDate.toEpochDay
  private var nextDate = getNextDate()

  def hasNext = nextDate.isDefined

  def next() = {
    val date = nextDate.get
    nextDate = getNextDate()
    date
  }

  @annotation.tailrec
  private def getNextDate(): Option[LocalDate] =
    currDay > lastDay match {
      case true  => None
      case false =>
        val date = LocalDate.ofEpochDay(currDay)
        currDay = currDay + 1

        isScheduled(date) match {
          case true  => Some(date)
          case false => getNextDate()
        }
    }

  private def isScheduled(date: LocalDate): Boolean =
    isScheduled(date.getMonth) &&
      isScheduled(date.getDayOfMonth, date.getDayOfWeek)

  private def isScheduled(month: Month): Boolean =
    months.isEmpty || months.contains(month)

  private def isScheduled(dayOfMonth: Int, dayOfWeek: DayOfWeek): Boolean =
    (daysOfMonth.isEmpty && daysOfWeek.isEmpty) ||
      daysOfMonth.contains(dayOfMonth) ||
      daysOfWeek.contains(dayOfWeek)
}
