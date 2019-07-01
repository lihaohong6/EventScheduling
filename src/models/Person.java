package models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import models.DateRange;

import java.util.List;

@AllArgsConstructor
@Getter
@Setter
public class Person {
    String name;
    List<DateRange> dateRanges;
}