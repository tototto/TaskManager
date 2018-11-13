package main;

import checker.Checker;
import frontEnd.*;
import task.task;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.text.ParseException;

/**
 * help to produce calendar
 * Calculate the time period for calendar to show
 * Generate and display the calendar
 *
 *  * @author  Tay Jun Wen
 *  * @version 1.0
 *  * @since   2018-10-12
 */

public class NewCalendar
{
    private Calendar calendar;

    private int CurrentYear;
    private int CurrentMonth;
    private UserInterface UI;

    private final int FEBURARY = 2;
    private final int LEAP_YEAR = 29;
    private final int START = 0;
    private final int END = 2;
    private final int INDX = 1;
    private final int CALANDAR_START = 2;
    private final int CALANDAR_END = 4;
    private final int JOINER = 3;
    private final int START_DAY = 1;
    private final int MAX_DAY = 31;
    private final int TOTAL_WEEK_DAY = 7;

    public NewCalendar() {
        calendar = Calendar.getInstance(); // create calendar obj
        CurrentYear = calendar.get(calendar.YEAR);
        CurrentMonth = calendar.get(calendar.MONTH); // give you current month - 1
        UI = new UserInterface();
    }

    /***************************************************************************
     *  Given the month, day, and year, of today
     *  Calculate the months 6 months before and after today
     *  Show to facillitate tracking of task within 1 year
     ***************************************************************************/
    private int TheYearSixMonthAgo() {
        int SixMonthBefore = TheMonthSixMonthsAgo();

        if(SixMonthBefore > CurrentMonth)
            return CurrentYear-1;

        return CurrentYear;
    }

    private int TheMonthSixMonthsAgo()
    {
        return ((CurrentMonth - 6) % 12) ;
    }

    /**
     *  Generate calendar from the list of task
     * @param TaskList contains calendar details for each task
     */
    public void GenerateCalendar(ArrayList<task> TaskList) {
        UI.printGenerateCalendar();

        int year = TheYearSixMonthAgo();
        int month = TheMonthSixMonthsAgo();

        System.out.println();

        for(int i = 0; i < 12; i++) {
            DisplayCalendar(month+1, year, TaskList);
            month = (++month) % 12;
            if(month == 0) year++;
        }
    }

    /**
     * Display the calendar within a 1 year period.
     * Dates of task are showned as booked with 'X' denoting such.
     * @param monthInput the month to display
     * @param yearInput the year of the month to display
     * @param TaskList obtain dates of task to highlight in calendar
     */
    private void DisplayCalendar(int monthInput, int yearInput, ArrayList<task> TaskList) {

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
        if (month == FEBURARY && isLeapYear(year)) days[month] = LEAP_YEAR;

        // print calendar header
        System.out.println("   " + months[month] + " " + year);
        System.out.println(" S  M Tu  W Th  F  S");

        // starting day
        int d = day(month, START_DAY, year);
        assert d < TOTAL_WEEK_DAY : "Calendar starting date calculation error";

        // print the calendar
        for (int i = 0; i < d; i++)
            System.out.print("   ");

        for (int i = START_DAY; i <= days[month]; i++) {// i is the individual date of the month

            assert i <= MAX_DAY : "There is more than 31 days in a month. date logic error";

            if(DateIsBooked(i,month,year, TaskList))
                UI.printBookedDay();
            else
                UI.printDay(i);

            if (((i + d) % TOTAL_WEEK_DAY == 0) || (i == days[month]))
                System.out.println();
        }

        System.out.println();

    }

    /***************************************************************************
     *  Given the month, day, and year, return if day is booked
     ***************************************************************************/

    private boolean DateIsBooked(int day, int month, int year, ArrayList<task> taskList) {

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        boolean AnyoneMatches = false;

        try {
            Date DateToCheck = dateFormat.parse(day + "/" + month + "/" + year);

            for (task x : taskList) {
                String TaskDate = x.getDate();

                if(TaskDate == null)
                    continue;

                String[] Array = TaskDate.split(" ");
                String StartDate = Array[START];
                String EndDate = Array[END];

                Date Start = dateFormat.parse(StartDate);
                Date End = dateFormat.parse(EndDate);

                if(!AnyoneMatches)
                    AnyoneMatches = isWithinRange(Start, End, DateToCheck, dateFormat);

            }
        } catch (Exception e) {
            //silently handle error. Avoid printing to console else will disrupt calendar UI.
        }

        if(AnyoneMatches)
            return true;

        return false;
    }

    private boolean isWithinRange(Date startDate, Date endDate, Date testDate, SimpleDateFormat dateFormat)
    {
        return testDate.after(startDate) && testDate.before(endDate) || (dateFormat.format(testDate).equals(dateFormat.format(startDate)) || dateFormat.format(testDate).equals(dateFormat.format(endDate)) );
    }
    /***************************************************************************
     *  Given the month, day, and year, return which day
     *  of the week it falls on according to the Gregorian calendar.
     *  For month, use 1 for January, 2 for February, and so forth.
     *  Returns 0 for Sunday, 1 for Monday, and so forth.
     ***************************************************************************/
    private  int day(int month, int day, int year)
    {
        int y = year - (14 - month) / 12;
        int x = y + y/4 - y/100 + y/400;
        int m = month + 12 * ((14 - month) / 12) - 2;
        int d = (day + x + (31*m)/12) % 7;
        return d;
    }

    // return true if the given year is a leap year
    private  boolean isLeapYear(int year)
    {
        if  ((year % 4 == 0) && (year % 100 != 0)) return true;
        if  (year % 400 == 0) return true;
        return false;
    }

    /**
     * Write the dates user inputted into the task list's task obj
     * @param taskList contains the  list of task where date will be loaded into
     * @param input contains the date string to be added to the task
     */
    public void AddToCalendar(ArrayList<task> taskList, String input) {
        String[] Array = input.split(" ");
        Checker checker = new Checker();

        try {
            if (checker.checkValidIndex(Array[INDX], taskList)) {
                // grab taskManager to be updated
                task taskToBeUpdated = null;

                if (Integer.parseInt(Array[INDX]) - 1 < Array.length) {
                    taskToBeUpdated = taskList.get(Integer.parseInt(Array[INDX]) - 1);
                    DateFormat df = new SimpleDateFormat("dd/mm/yyyy");

                    try {
                        // check if it is in date format
                        Date start = DateValidation(Array[CALANDAR_START]);
                        Date end = DateValidation(Array[CALANDAR_END]);

                        if(!CheckBeforeAfter(start, end))
                            throw new Exception();

                        if(Array[JOINER].equals("to")) {
                            taskToBeUpdated.putDate(Array[CALANDAR_START], Array[JOINER], Array[CALANDAR_END]); // add it in
                            assert !taskToBeUpdated.getDate().isEmpty() : "Add to calendar failed";
                            UI.printCalendarAdded();
                        }
                        else
                            throw new Exception();
                    }
                    catch(Exception e) {
                        errorManager.printInValidDayError();
                    }

                } else
                    errorManager.printInValidIndex();

            }

        }catch (ArrayIndexOutOfBoundsException e) { UI.printDateFormatError();}
        catch (NumberFormatException e) {UI.printInValidIndex();;}
    }

    /**
     * Ensure that date in String represents a valid date format
     * @param date Accepts a date as String
     * @return Date in given format
     * @throws ParseException when Format mismatch
     */
    private Date DateValidation(String date) throws ParseException {
            DateFormat format = new SimpleDateFormat("dd/MM/yyyy");
            // Input to be parsed should strictly follow the defined date format
            // above.
            format.setLenient(false);
            return format.parse(date);
    }

    /**
     * Check that start date does not begin after end
     * @param start
     * @param end
     * @return true if date range is valid, false otherwise
     */
    private boolean CheckBeforeAfter(Date start, Date end) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        return end.after(start) && start.before(end) || (dateFormat.format(start).equals(dateFormat.format(end)));
    }

}
