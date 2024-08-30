package com.chiachen.carousellnews.utils

import android.content.Context
import com.chiachen.carousellnews.R
import java.time.Instant
import java.time.LocalDate
import java.time.Period
import java.time.ZoneId

object DateFormat {

    fun format(context: Context, time: Long): String {
        return Period.between(
            Instant.ofEpochSecond(time).atZone(ZoneId.systemDefault()).toLocalDate(),
            LocalDate.now()
        ).let { period ->
            when {
                period.years > 0 -> context.resources.getQuantityString(
                    R.plurals.year,
                    period.years,
                    period.years
                );
                period.months > 0 -> context.resources.getQuantityString(
                    R.plurals.month,
                    period.months,
                    period.months
                );
                period.days < 7 -> context.resources.getQuantityString(
                    R.plurals.day,
                    period.days,
                    period.days
                );
                else -> {
                    val weeks = (period.days / 7)
                    context.resources.getQuantityString(R.plurals.weeks, weeks, weeks);
                }
            }
        }
    }
}