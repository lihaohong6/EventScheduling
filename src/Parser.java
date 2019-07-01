import models.DateRange;
import models.Person;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Parser {

    @Nullable
    private static LocalDate toLocalDate(String dateInput) {
        if (!dateInput.contains("-")) {
            System.out.println((String.format("ERROR! %s is not a valid date. Attempting to continue. ", dateInput)));
            return null;
        }
        String dateTransformed;
        // if the year is NOT included (formatted like MM-DD)
        if (dateInput.length() == 5) {
            dateTransformed = Config.DEFAULT_YEAR + "-" + dateInput;
        }
        else if (dateInput.length() == 8){
            // Format MM-DD-YY
            // take the first two digits of DEFAULT_YEAR (use 20 from 2019, for example),
            // and then take the last two digits of year, and then take MM-DD
            dateTransformed = String.format("%s-%s",
                    Integer.toString(Config.DEFAULT_YEAR).substring(0, 2) + dateInput.substring(6),
                    dateInput.substring(0, 5));
        }
        else if (dateInput.length() == 10) {
            // Format MM-DD-YYYY
            dateTransformed = String.format("%s-%s", dateInput.substring(6), dateInput.substring(0, 5));
        }
        else {
            dateTransformed = dateInput;
            System.out.println(String.format(
                    "WARNING! Cannot recognize the format of date input %s. Attempting to parse it anyway. ",
                    dateInput));
        }
        try {
            return LocalDate.parse(dateTransformed);
        }
        catch (DateTimeException exception) {
            System.out.println(String.format(
                    "ERROR! Input formatted illegally. " +
                            "Failed to parse %s, which is transformed into %s. " +
                            "Continuing without this date pair. ",
                    dateInput,
                    dateTransformed)
                    );
            return null;
        }
    }

    @NotNull
    public static List<Person> parse(Scanner sc) {
        /*
        File Format:
        Name
        Start date
        End date
        (Optional)
        Start date2
        End date2
        */
        /*
        Valid date formats:
        MM/DD (use DEFAULT_YEAR)
        MM/DD/YY (use the first two digits of DEFAULT_YEAR)
        MM/DD/YYYY
         */

        List<Person> people = new ArrayList<>();

        while (sc.hasNextLine()) {
            String str = sc.nextLine();

            if (str.contains("-")) {
                LocalDate start = toLocalDate(str);
                LocalDate end = toLocalDate(sc.nextLine());
                if (start == null || end == null) {
                    continue;
                }
                if (start.isAfter(end)) {
                    System.out.println("ERROR! Start date is after end date for person named "
                            + people.get(people.size() - 1).getName() + ". Continuing without date pair. ");
                    continue;
                }
                people.get(people.size() - 1).getDateRanges().add(new DateRange(start, end));
            }
            else {
                List<DateRange> dates = new ArrayList<>();
                Person temp = new Person(str, dates);
                people.add(temp);
            }
        }
        sc.close();
        return people;
    }
}
