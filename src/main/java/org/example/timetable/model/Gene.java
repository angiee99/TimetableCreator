package org.example.timetable.model;

public class Gene {
    // is this class really needed
    Activity activity;
    //TODO: fitness, mutation,
    public Gene( Activity activity) {
        this.activity = activity;
    }

    public Gene() {
    }

    public Activity getActivity() {
        return activity;
    }

    public void setActivity(Activity activity) {
        this.activity = activity;
    }
}
