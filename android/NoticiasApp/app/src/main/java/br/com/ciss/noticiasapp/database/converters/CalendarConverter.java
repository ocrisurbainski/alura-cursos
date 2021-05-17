package br.com.ciss.noticiasapp.database.converters;

import java.util.Calendar;

import androidx.room.TypeConverter;

/**
 * @author Cristian Urbainski
 * @since 1.0 (29/09/20)
 */
public class CalendarConverter {

    @TypeConverter
    public static Long calendarToLong(Calendar calendar) {

        return calendar != null ? calendar.getTimeInMillis() : null;
    }

    @TypeConverter
    public static Calendar longToCalendar(Long time) {

        if (time == null) {

            return null;
        }

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(time);
        return calendar;
    }

}