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

private case class CronTimeIterator(
  hours:   Seq[Int],
  minutes: Seq[Int],
  seconds: Seq[Int],
) extends Iterator[LocalTime]:

  private val effectiveHours   = if hours.isEmpty then 0 to 23 else hours
  private val effectiveMinutes = if minutes.isEmpty then 0 to 59 else minutes
  private val effectiveSeconds = if seconds.isEmpty then 0 to 59 else seconds

  private val hourCount   = effectiveHours.size
  private val minuteCount = effectiveMinutes.size
  private val secondCount = effectiveSeconds.size

  var hourIndex   = 0
  var minuteIndex = 0
  var secondIndex = 0

  private var nextTime = getNextTime()

  def hasNext = nextTime.isDefined

  def next() =
    val time = nextTime.get
    nextTime = getNextTime()
    time

  private def getNextTime(): Option[LocalTime] =
    if secondIndex < secondCount then
      val time = getCurrentTime()
      secondIndex += 1
      Some(time)
    else if minuteIndex < minuteCount - 1 then
      minuteIndex += 1
      secondIndex = 0
      val time = getCurrentTime()
      secondIndex += 1
      Some(time)
    else if hourIndex < hourCount - 1 then
      hourIndex += 1
      minuteIndex = 0
      secondIndex = 0
      val time = getCurrentTime()
      secondIndex += 1
      Some(time)
    else
      None

  private def getCurrentTime(): LocalTime =
    LocalTime.of(
      effectiveHours(hourIndex),
      effectiveMinutes(minuteIndex),
      effectiveSeconds(secondIndex)
    )
