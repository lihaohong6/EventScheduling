package models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@AllArgsConstructor
@Getter
@Setter
public class Day implements Comparable<Day> {
    private LocalDate date;
    private List<String> names;

    public int compareTo(Day o) {
        return Integer.compare(o.names.size(), this.names.size());
    }
}