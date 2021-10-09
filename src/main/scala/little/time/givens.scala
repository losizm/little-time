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

import java.time.*

/** Provides ordering for `java.time.Duration`. */
given durationOrdering: Ordering[Duration] =
  (a, b) => a.compareTo(b)

/** Provides ordering for `java.time.YearMonth`. */
given yearMonthOrdering: Ordering[YearMonth] =
  (a, b) => a.compareTo(b)

/** Provides ordering for `java.time.LocalDate`. */
given localDateOrdering: Ordering[LocalDate] =
  (a, b) => a.compareTo(b)

/** Provides ordering for `java.time.LocalTime`. */
given localTimeOrdering: Ordering[LocalTime] =
  (a, b) => a.compareTo(b)

/** Provides ordering for `java.time.LocalDateTime`. */
given localDateTimeOrdering: Ordering[LocalDateTime] =
  (a, b) => a.compareTo(b)

/** Provides ordering for `java.time.Instant`. */
given instantOrdering: Ordering[Instant] =
  (a, b) => a.compareTo(b)
