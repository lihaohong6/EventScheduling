import models.DateRange;
import models.Day;
import models.Person;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static java.time.temporal.ChronoUnit.DAYS;

public class CalendarCreator {

    private static int dayDifference(LocalDate dateFirst, LocalDate dateSecond) {
        return (int)DAYS.between(dateFirst, dateSecond);
    }

    public static Day[] createCalendar(List<Person> people) {

        LocalDate firstDay = LocalDate.of(9999, 1, 1);
        LocalDate lastDay = LocalDate.of(0, 1, 1);
        for (Person person : people) {
            for (DateRange dateRange : person.getDateRanges()) {
                if (dateRange.getStart().isBefore(firstDay)) {
                    firstDay = dateRange.getStart();
                }
                if (dateRange.getEnd().isAfter(lastDay)) {
                    lastDay = dateRange.getEnd();
                }
            }
        }

        int daySpan = dayDifference(firstDay, lastDay);
        Day allDays[] = new Day[daySpan + 1];
        // use formatter to convert 5 to 05
        for (int deltaDays = 0; deltaDays <= daySpan; deltaDays++) {
            LocalDate currentDate = firstDay.plusDays(deltaDays);
            allDays[deltaDays] = new Day(currentDate, new ArrayList<>());
        }

        for (Person person : people) {
            for (DateRange range : person.getDateRanges()) {
                LocalDate start = range.getStart();
                LocalDate end = range.getEnd();
                int beginIndex = dayDifference(firstDay, start);
                int endIndex = dayDifference(firstDay, end);
                for (int j = beginIndex; j <= endIndex; j++) {
                    allDays[j].getNames().add(person.getName());
                }
            }
        }

        return allDays;
    }
}
