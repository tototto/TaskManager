package com.company;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.text.ParseException;

public class NewCalendar
{
    Calendar calendar;

    private int CurrentYear;
    private int CurrentMonth;
    private UserInterface UI;

    public NewCalendar()
    {
        calendar = Calendar.getInstance(); // create calendar obj
        CurrentYear = calendar.get(calendar.YEAR);
        CurrentMonth = calendar.get(calendar.MONTH); // give you current month - 1
        UI = new UserInterface();
    }

    public int TheYearSixMonthAgo()
    {
        int SixMonthBefore = TheMonthSixMonthsAgo();

        if(SixMonthBefore > CurrentMonth)
            return CurrentYear-1;

        return CurrentYear;
    }

    public int TheMonthSixMonthsAgo()
    {
        return ((CurrentMonth - 6) % 12) ;
    }

    public void GenerateCalendar(ArrayList<task> TaskList)
    {
        UI.printGenerateCalendar();

        int year = TheYearSixMonthAgo();
        int month = TheMonthSixMonthsAgo();

        System.out.println();

        for(int i = 0; i < 12; i++)
        {
            DisplayCalendar(month+1, year, TaskList);
            month = (++month) % 12;
            if(month == 0) year++;
        }
    }

    public void DisplayCalendar(int monthInput, int yearInput, ArrayList<task> TaskList)
    {
        int month = monthInput;    // month (Jan = 1, Dec = 12)
        int year = yearInput;     // year

        // months[i] = name of month i
        String[] months = {
                "",                               // leave empty so that months[1] = "January"
                "January", "February", "March",
                "April", "May", "June",
                "July", "August", "September",
                "October", "November", "December"
        };

        // days[i] = number of days in month i
        int[] days = { 0, 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31 };

        // check for leap year
        if (month == 2 && isLeapYear(year)) days[month] = 29;


        // print calendar header
        System.out.println("   " + months[month] + " " + year);
        System.out.println(" S  M Tu  W Th  F  S");

        // starting day
        int d = day(month, 1, year);

        // print the calendar
        for (int i = 0; i < d; i++)
            System.out.print("   ");

        for (int i = 1; i <= days[month]; i++) // i is the individual date of the month
        {
            if(DateIsBooked(i,month,year, TaskList))
                UI.printBookedDay();
            else
                UI.printDay(i);

            if (((i + d) % 7 == 0) || (i == days[month]))
                System.out.println();
        }

        System.out.println();

    }



    private boolean DateIsBooked(int day, int month, int year, ArrayList<task> taskList)
    {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        boolean AnyoneMatches = false;

        try {
            Date DateToCheck = dateFormat.parse(day + "/" + month + "/" + year);

            for (task x : taskList) {
                String TaskDate = x.getDate();

                if(TaskDate == null)
                    continue;

                String[] Array = TaskDate.split(" ");
                String StartDate = Array[0];
                String EndDate = Array[2];

                Date Start = dateFormat.parse(StartDate);
                Date End = dateFormat.parse(EndDate);

                if(!AnyoneMatches)
                    AnyoneMatches = isWithinRange(Start, End, DateToCheck, dateFormat);

            }
        } catch (Exception e) { }

        if(AnyoneMatches)
            return true;

        return false;
    }

    boolean isWithinRange(Date startDate, Date endDate, Date testDate, SimpleDateFormat dateFormat)
    {
        return testDate.after(startDate) && testDate.before(endDate) || (dateFormat.format(testDate).equals(dateFormat.format(startDate)) || dateFormat.format(testDate).equals(dateFormat.format(endDate)) );
    }
    /***************************************************************************
     *  Given the month, day, and year, return which day
     *  of the week it falls on according to the Gregorian calendar.
     *  For month, use 1 for January, 2 for February, and so forth.
     *  Returns 0 for Sunday, 1 for Monday, and so forth.
     ***************************************************************************/
    public  int day(int month, int day, int year)
    {
        int y = year - (14 - month) / 12;
        int x = y + y/4 - y/100 + y/400;
        int m = month + 12 * ((14 - month) / 12) - 2;
        int d = (day + x + (31*m)/12) % 7;
        return d;
    }

    // return true if the given year is a leap year
    public  boolean isLeapYear(int year)
    {
        if  ((year % 4 == 0) && (year % 100 != 0)) return true;
        if  (year % 400 == 0) return true;
        return false;
    }

    public  void AddToCalendar(ArrayList<task> taskList, String input) {
        String[] Array = input.split(" ");

        Checker checker = new Checker();

        try {
            if (checker.checkValidIndex(Array[1], taskList)) {
                // grab task to be updated
                task taskToBeUpdated = null;

                if (Integer.parseInt(Array[1]) - 1 < Array.length) {
                    taskToBeUpdated = taskList.get(Integer.parseInt(Array[1]) - 1);
                    DateFormat df = new SimpleDateFormat("dd/mm/yyyy");

                    try
                    {
                        // check if it is in date format
                        Date start = DateValidation(Array[2]);
                        Date end = DateValidation(Array[4]);

                        if(!CheckBeforeAfter(start, end))
                            throw new Exception();

                        if(Array[3].equals("to"))
                            taskToBeUpdated.putDate(Array[2], Array[3], Array[4]); // add it in
                        else
                            throw new Exception();
                    }
                    catch(Exception e)
                    {
                        UI.printInValidDayError();
                    }

                    UI.printCalendarAdded();

                } else
                    UI.printInValidIndex();

            }

        }catch (ArrayIndexOutOfBoundsException e) { UI.printDateFormatError();}
        catch (NumberFormatException e) {UI.printInValidIndex();;}
    }

    private Date DateValidation(String date) throws ParseException {
            DateFormat format = new SimpleDateFormat("dd/MM/yyyy");
            // Input to be parsed should strictly follow the defined date format
            // above.
            format.setLenient(false);
            return format.parse(date);
    }

    private boolean CheckBeforeAfter(Date start, Date end) {
        return end.after(start) && start.before(end);
    }

}
