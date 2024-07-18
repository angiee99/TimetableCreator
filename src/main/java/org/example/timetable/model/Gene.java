package org.example.timetable.model;

public class Gene {
    final Activity activity;

    public Gene(Activity activity) {
        this.activity = activity;
    }

    public Activity getActivity() {
        return activity;
    }

    public Gene withActivity(Activity activity) {
        return new Gene(activity);
    }
}
