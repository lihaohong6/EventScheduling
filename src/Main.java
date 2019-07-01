import models.Day;
import models.Person;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws IOException {
        // gives the parser a scanner and let it parse the file into a List of people
        List<Person> people = Parser.parse(new Scanner(new File(Config.INPUT_FILE_NAME)));

        // creates an array of all the days and each's respective availability.
        Day allDays[] = CalendarCreator.createCalendar(people);

        // output each day's availability and the best days
        Output.output(allDays, people);
    }
}
