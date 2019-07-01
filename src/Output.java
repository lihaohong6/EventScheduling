import models.Day;
import models.Person;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.List;

public class Output {

    private static void outputAllDays(Day allDays[], PrintWriter pw, boolean breakOnCondition) {
        pw.println(breakOnCondition ? "Outputting best days: " : "Outputting all dates: ");

        for (int i = 0; i < allDays.length - 1; i++) {
            pw.print(allDays[i].getDate().toString() + " can accommodate the following " + allDays[i].getNames().size() + " people: ");
            for (String j : allDays[i].getNames())
                pw.print(j + " ");
            pw.println();

            if (breakOnCondition && allDays[i].getNames().size() != allDays[i + 1].getNames().size())
                break;
        }
    }

    public static void output(Day[] allDays, List<Person> people) throws IOException {
        PrintWriter pw;
        if (Config.OUTPUT_SETTING == 1) {
            pw = new PrintWriter(new File("output.out"));
        }
        else if (Config.OUTPUT_SETTING == 2) {
            pw = new PrintWriter(System.out);
        }
        else {
            System.out.println("ERROR! Invalid output setting value. Check config. ");
        }

        pw.println("There are " + people.size() + " people total. ");

        outputAllDays(allDays, pw, false);

        Arrays.sort(allDays);

        outputAllDays(allDays, pw, true);

        pw.close();
    }
}
