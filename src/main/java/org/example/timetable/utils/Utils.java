package org.example.timetable.utils;

import org.example.timetable.model.*;

import java.time.DayOfWeek;
import java.util.List;

public class Utils {
    public static List<Activity> getActivitiesByType(ActivityType type, List<Activity> activities) {
        return activities.stream().filter(activity -> activity.getActivityType().equals(type)).toList();
    }
    public static List<Activity> getActivitiesByType(Activity activityToChange, List<Activity> activities) {
        return activities.stream().filter(activity
                -> activity.getActivityType().equals(activityToChange.getActivityType())
                && !activity.equals(activityToChange)).toList(); // exclude the same activity we want to change in the list
    }
    public static List<Gene> getActivitiesByDay(Individual individual, int i) {
        return individual.getGenes().stream().filter(
                        gene ->
                                gene.getActivity().getTimeslot().getDay()
                                        .equals(DayOfWeek.of(i)))
                .toList();
    }
    public static int countOverlaps(List<Gene> activities) {
        int overlapCount = 0;
        for (int j = 0; j < activities.size() - 1; j++) {
            Timeslot a = activities.get(j).getActivity().getTimeslot();
            Timeslot b = activities.get(j + 1).getActivity().getTimeslot();
            if (a.getEnd().isAfter(b.getStart())) {
                overlapCount++; // add an overlap
            }
        }
        return overlapCount;
    }
    public static boolean hasOverlaps(List<Gene> activities){
        for (int j = 0; j < activities.size() - 1; j++) {
            Timeslot current = activities.get(j).getActivity().getTimeslot();
            Timeslot next = activities.get(j + 1).getActivity().getTimeslot();

            if (current.getEnd().isAfter(next.getStart())) {
                return true;// there is an overlap
            }
        }
        return false;
    }
}
