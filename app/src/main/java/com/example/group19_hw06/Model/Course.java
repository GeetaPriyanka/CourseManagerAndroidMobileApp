package com.example.group19_hw06.Model;


/**
 * Created by Geeta Priyanka Janapareddy
 * Sai Vikhyat on 11/3/2017.
 */


public class Course {
    String title, instructor;
    String day, timeHours, timeMinutes;
    String period;
    int creditHours;
    String semester, username;


    public Course() {

    }

    public Course(String title, String instructor, String day, String timeHours, String timeMinutes, String period, int creditHours, String semester, String username) {
        this.title = title;
        this.instructor = instructor;
        this.day = day;
        this.timeHours = timeHours;
        this.timeMinutes = timeMinutes;
        this.period = period;
        this.creditHours = creditHours;
        this.semester = semester;
        this.username = username;
    }

    @Override
    public String toString() {
        return "Course{" +
                "title='" + title + '\'' +
                ", instructor='" + instructor + '\'' +
                ", day='" + day + '\'' +
                ", timeHours='" + timeHours + '\'' +
                ", timeMinutes='" + timeMinutes + '\'' +
                ", period='" + period + '\'' +
                ", creditHours=" + creditHours +
                ", semester='" + semester + '\'' +
                ", username='" + username + '\'' +
                '}';
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public boolean equals(Object o) {

        if (this == o) return true;
        if (!(o instanceof Course)) return false;

        Course course = (Course) o;

        if (creditHours != course.creditHours) return false;
        if (title != null ? !title.equals(course.title) : course.title != null) return false;
        if (instructor != null ? !instructor.equals(course.instructor) : course.instructor != null)
            return false;
        if (day != null ? !day.equals(course.day) : course.day != null) return false;
        if (timeHours != null ? !timeHours.equals(course.timeHours) : course.timeHours != null)
            return false;
        if (timeMinutes != null ? !timeMinutes.equals(course.timeMinutes) : course.timeMinutes != null)
            return false;
        if (period != null ? !period.equals(course.period) : course.period != null) return false;
        if (semester != null ? !semester.equals(course.semester) : course.semester != null)
            return false;
        return username != null ? username.equals(course.username) : course.username == null;
    }

    @Override
    public int hashCode() {
        int result = title != null ? title.hashCode() : 0;
        result = 31 * result + (instructor != null ? instructor.hashCode() : 0);
        result = 31 * result + (day != null ? day.hashCode() : 0);
        result = 31 * result + (timeHours != null ? timeHours.hashCode() : 0);
        result = 31 * result + (timeMinutes != null ? timeMinutes.hashCode() : 0);
        result = 31 * result + (period != null ? period.hashCode() : 0);
        result = 31 * result + creditHours;
        result = 31 * result + (semester != null ? semester.hashCode() : 0);
        result = 31 * result + (username != null ? username.hashCode() : 0);
        return result;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getInstructor() {
        return instructor;
    }

    public void setInstructor(String instructor) {
        this.instructor = instructor;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getTimeHours() {
        return timeHours;
    }

    public void setTimeHours(String timeHours) {
        this.timeHours = timeHours;
    }

    public String getTimeMinutes() {
        return timeMinutes;
    }

    public void setTimeMinutes(String timeMinutes) {
        this.timeMinutes = timeMinutes;
    }

    public String getPeriod() {
        return period;
    }

    public void setPeriod(String period) {
        this.period = period;
    }

    public int getCreditHours() {
        return creditHours;
    }

    public void setCreditHours(int creditHours) {
        this.creditHours = creditHours;
    }

    public String getSemester() {
        return semester;
    }

    public void setSemester(String semester) {
        this.semester = semester;
    }
}