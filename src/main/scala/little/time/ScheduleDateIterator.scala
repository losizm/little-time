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

private case class ScheduleDateIterator(
  startDate: LocalDate,
  endDate: LocalDate,
  months: Seq[Month],
  daysOfMonth: Seq[Int],
  daysOfWeek: Seq[DayOfWeek],
  dates: Seq[LocalDate],
) extends Iterator[LocalDate] {
  private val epochDays = dates.map(_.toEpochDay)
  private val lastDay   = endDate.toEpochDay
  private var currDay   = firstDay
  private var nextDate  = getNextDate()

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
        advanceDay()

        schedule(date) match {
          case true  => Some(date)
          case false => getNextDate()
        }
    }

  private def firstDay: Long = {
    val startDay = startDate.toEpochDay
    epochDays.isEmpty match {
      case true  => startDay
      case false => epochDays.find(startDay.<=).getOrElse(lastDay + 1)
    }
  }

  private def advanceDay(): Unit =
    currDay = epochDays.isEmpty match {
      case true  => currDay + 1
      case false => epochDays.find(currDay.<).getOrElse(lastDay + 1)
    }

  private def schedule(date: LocalDate): Boolean =
    (months.isEmpty      || months.contains(date.getMonth)) &&
    (daysOfMonth.isEmpty || daysOfMonth.contains(date.getDayOfMonth)) &&
    (daysOfWeek.isEmpty  || daysOfWeek.contains(date.getDayOfWeek))
}
