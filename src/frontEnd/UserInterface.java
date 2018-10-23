package frontEnd;

import task.*;

import java.util.ArrayList;
import java.util.Scanner;

public class UserInterface
{
    private Scanner GetString; // define Scanner object

    private  final String SYSTEM_PROMPT = "Your task?";
    private  final String MESSAGE_ADDED = "Welcome to TaskManager-Level1!";
    private  final String PRINT_TASK_LIST = "Tasks in the list: ";
    private  final String ERROR_ON_OPEN_CREATE = "Error: Cannot create or open file";
    private  final String ERROR_ON_CLOSE_FILE = "Error: Cannot close file. Changes may not be saved";
    private  final String PATH = "tasklist.txt";
    private  final String CalendarPath = "calendar.txt";
    private  final String NO_DEADLINE = "THIS TASK HAS NO DEADLINE";
    private  final String EXIT_MESSAGE = "Bye!";
    private  final String ERROR_MESSAGE = "Unknown command! please try again";
    private  final String MISSING_DESCRIPTION = "Error: Empty description";
    private  final String ERROR_ON_READ_FILE = "Error: Cannot read from file";
    private  final String Generate_Calendar_Msg = "Generating Calender...";
    private  final String inValidIndex = "Please Enter a valid Indx";

    public UserInterface()
    {
        GetString = new Scanner(System.in);
    }

    public void printOpenMsg(String fileName) {
        System.out.println("Opening and loading Task from file" + fileName);
    }

    public void printInValidIndex()
    {
        System.out.println(inValidIndex);
    }

    public void printDateFormatError()
    {
        System.out.println("follow the following format: [DATE 1] to [DATE 2]");
    }

    public void printNotFoundMsg(String fileName) {
        System.out.println(fileName + "not found. Creating one in directory now.");
    }

    public void printHelp()
    {
        System.out.println("How to add TODO taskManager: TODO [DESCRIPTION.....]");
        System.out.println("How to add DEADLINE taskManager: DEADLINE [DESCRIPTION...../by .......]");
        System.out.println("How to set existing taskManager to Done: DONE [INDX]");
        System.out.println("How to delete a specific Task by it's index: DELETE [INDX]");
        System.out.println("How to delete all Task: DELETE all");
        System.out.println("How to add/update a specific Task by it's index to the calendar: ADD [INDX] [DATE 1] to [DATE 2]");
        System.out.println("How to update DEADLINE: UPDATE [IDX] [DESCRIPTION] /BY [DEADLINE] [DONE_STATUS] ");
        System.out.println("How to update TODO: UPDATE [IDX] [DESCRIPTION] [DONE_STATUS] ");
        System.out.println("How to remove an existing date: REMOVE [IDX]");
        System.out.println("How to search list by Task type: SEARCH [TODO OR DEADLINE]");
        System.out.println("How to view calendar: view");
    }

    public void printInValidDayError()
    {
        System.out.println("Please enter a valid date");
    }

    public void printCalendarAdded()
    {
        System.out.println("Added to Calendar");
    }

    public void printBookedDay()
    {
        System.out.printf(".X ");
    }

    public void printDay(int i)
    {
        System.out.printf("%2d ", i);
    }

    public String getUserInput()
    {
        return GetString.nextLine().trim();
    }

    public void printSystemPrompt() {
        System.out.println(SYSTEM_PROMPT);
    }

    public void printCloseError() {
        System.out.println(ERROR_ON_CLOSE_FILE);
    }

    public void printGenerateCalendar() {
        System.out.println(Generate_Calendar_Msg);
    }

    public void printReadError() {
        System.out.println(ERROR_ON_READ_FILE);
    }

    public void printOpenError() {
        System.out.println(ERROR_ON_OPEN_CREATE);
    }

    public  void printPrintTaskList(int size) {
        System.out.println(PRINT_TASK_LIST + size);
    }

    public String getCalendarPath() {
        return CalendarPath;
    }

    public  String getPATH() {
        return PATH;
    }

    public void printMessageAdded() {
        System.out.println(MESSAGE_ADDED);
    }


    public void printOut(ArrayList<task> TaskList)
    {
        for(int i = 0; i < TaskList.size(); i++)
        {
            task obj = TaskList.get(i);
            PrintDetails(obj, i);
        }
    }

    public void PrintDetails(task obj, int i)
    {
        System.out.printf("[%d] description: %s \n", i + 1, obj.getDesciption().trim());
        System.out.println("\tis done? " + obj.getDone());

        if(obj instanceof deadline) // if we are printing deadline class
        {
            if(obj.getDo_by().length() > 0)
                System.out.println("\tDo by: " + obj.getDo_by());
            else
                System.out.println("\tDo by: " + NO_DEADLINE);
        }

        if(obj.checkDateNULL())
        {
            System.out.println("\tCalendar: " + obj.getDate());
        }
    }

    public  void printExit()
    {
        System.out.println(EXIT_MESSAGE);
    }

    public  void printError()
    {
        System.out.println(ERROR_MESSAGE);
    }

    public  void printMissingDescrip()
    {
        System.out.println(MISSING_DESCRIPTION);
    }

    public void printNoMatch()
    {
        System.out.println("There is no Task of the specified type");
    }

}
