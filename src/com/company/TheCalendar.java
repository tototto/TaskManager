package com.company;
import java.time.DateTimeException;
import java.util.Calendar;

public class TheCalendar
{
    Calendar calendar;

    private int CurrentYear;
    private int CurrentMonth;

    public TheCalendar()
    {
        calendar = Calendar.getInstance(); // create calendar obj
        CurrentYear = calendar.get(calendar.YEAR);
        CurrentMonth = calendar.get(calendar.MONTH); // give you current month - 1
    }

    public void GenerateCalendar()
    {
        int year = TheYearSixMonthAgo();
        int month = TheMonthSixMonthsAgo();

        for(int i = 0; i < 12; i++)
        {
            calendar.set(year, month, 1);

            printer(year, month, calendar);

            month++;
            month = month % 12;

            if(month == 0)
                year++;
        }
    }

    public void printer(int year, int month, Calendar calendar)
    {
        int maxDay = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);

        System.out.println( printMonth(month+1) + "-" + year + " | Total Day in Month: "  + maxDay + " ");
        System.out.println(" S  M Tu  W Th  F  S");

    }

    private String printMonth(int i)
    {
        switch(i)
        {
            case 1: return "January";
            case 2: return "Feburary";
            case 3: return "March";
            case 4: return "April";
            case 5: return "May";
            case 6: return "June";
            case 7: return "July";
            case 8: return "August";
            case 9: return "September";
            case 10: return "October";
            case 11: return "November";
            case 12: return "December";
        }

        return null;
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

}
