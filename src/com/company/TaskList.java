package com.company;

import java.util.ArrayList;

public class TaskList
{
    private ArrayList<task> TaskList;
    private ArrayList<String> TaskFromFile;
    private ArrayList<String> CalendarList;

    public ArrayList<String> getTaskFromFile() {
        return TaskFromFile;
    }
    public ArrayList<String> getCalendarList() {
        return CalendarList;
    }

    public void setTaskFromFile(ArrayList<String> taskFromFile) {
        TaskFromFile = taskFromFile;
    }
    public void setCalendarList(ArrayList<String> calendarList) {
        CalendarList = calendarList;
    }

    public TaskList()
    {
        TaskList = new ArrayList<task>();
    }


    public enum inputKeyword  // implicitly public static void
    {
        add,
        todo,
        deadline,
        print,
        exit,
        done,
        update,
        delete,
        help,
        remove,
        search,
        view;
    }
}
