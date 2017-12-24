package iii_conventions

data class MyDate(val year: Int, val month: Int, val dayOfMonth: Int): Comparable<MyDate> {
    override fun compareTo(other: MyDate) = when {
        year != other.year -> year.compareTo(other.year)
        month != other.month -> month.compareTo(other.month)
        else -> dayOfMonth.compareTo(other.dayOfMonth)
    }
}

operator fun MyDate.rangeTo(other: MyDate): DateRange = DateRange(this, other)
class DateRange(override val start: MyDate, override val endInclusive: MyDate) : ClosedRange<MyDate>, Iterable<MyDate> {
    override fun contains(value: MyDate): Boolean { return start <= value && value <= endInclusive }
    override fun iterator(): Iterator<MyDate> = object : Iterator<MyDate> {
        var cur: MyDate = start
        override fun next(): MyDate {
            val ret = cur
            cur = cur.nextDay()
            return ret
        }

        override fun hasNext(): Boolean = cur <= endInclusive
    }
}

class RepeatedTimeInterval(val timeInterval: TimeInterval, val number: Int)
operator fun MyDate.plus(timeInterval: TimeInterval ) = addTimeIntervals(timeInterval, 1)
operator fun MyDate.plus(timeIntervals: RepeatedTimeInterval) = addTimeIntervals(timeIntervals.timeInterval, timeIntervals.number)
operator fun TimeInterval.times(number: Int) = RepeatedTimeInterval(this, number)


enum class TimeInterval {
    DAY,
    WEEK,
    YEAR
}