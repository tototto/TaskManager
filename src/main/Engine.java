package main;

import checker.Checker;
import frontEnd.UserInterface;
import frontEnd.errorManager;
import task.task;
import taskManager.*;
import textManager.*;
import textManager.TextManager;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Represents The core logic of the TaskManager program.
 * This can be classified as the main body.
 */

public class Engine
{
    /**
     * The Other classes used in the operation of TaskManager core logic
     */
     private TaskManager taskmanager;
     private FileHelper theFile;
     private FileHelper theCalendar;
     private UserInterface UI;
     private NewCalendar calendar;
     private ArrayList<task> TaskList; // create dynamic array using Collections to store taskManager from user input
     private ArrayList<String> TaskFromFile;
     private ArrayList<String> CalendarList;

     private final int FAIL = 0;
     private final int PASSED = 0;

    int index = 0;
    int indexForCalendar = 0;
    String isDoneForFile = null;

    /**
     * Constructor initialise all objects that needs to be used
     * e.g. parsing of input / checking keyword / task management
     * Also Loads from file and calendar before program runs
     * When program runs data is fully loaded
     */
    public Engine()
    {
        taskmanager = new TaskManager();
        theFile = new FileHelper("tasklist.txt");
        theCalendar = new FileHelper("calendar.txt");
        calendar = new NewCalendar();
        UI = new UserInterface();

        TaskList = new ArrayList<task>();

        // create file if it does not exist. Open file if exist
        /** @throws e if file cannot created or opened */
        try{
            theFile.Create_or_Open_File();
            theCalendar.Create_or_Open_File();
        }catch(IOException e){errorManager.ManageCreateOpenFileError(e);}

        //  Read from File and store file result as string in TaskFromFile

        /** @throws e if file cannot be read */
        try {
            TaskFromFile = theFile.readFile();
            CalendarList = theCalendar.readFile();
        } catch (FileNotFoundException e) { errorManager.ManageReadFileError(e);}
    }

    /**
     * Provides for the running of the Task Manager program
     * Enables the storing of user input Task into program memory
     * provides provision for the different operation that can be performed using other classes
     */
    public void run()
    {
        UI.printMessageAdded();

        boolean runner = true; // To determine if program should continue

        while(runner)
        {
            String input = null;
            String keyword = null;
            // loop through the TaskFromFile first, the variable 'input' and 'keyword' will hold everything from TaskFromFile
            // use int index to track which row from file you are currently reading from and when to stop
            if(theFile.FinishLoadFromFileStatus(index, TaskFromFile)) // not finish loading from file (tasklist.txt)
            {
                TextManager textManager = new TextManager(TaskFromFile, index);
                textManager.LoadText();

                input = textManager.getInput();
                keyword = textManager.getKeyword();
                isDoneForFile = textManager.getIsDoneForFile();
                index++;
            }
            else if(theFile.FinishLoadFromFileStatus(indexForCalendar, CalendarList))
            {
                TextManager textManager = new TextManager(CalendarList, indexForCalendar, "calendar");
                input = textManager.getCalendarInput();
                keyword = Parser.SelectKeyword(input).toLowerCase().trim(); // grab keyword
                indexForCalendar++;
            }
            else
            {
                // after it is finished execute this block:
                UI.printSystemPrompt();
                input = UI.getUserInput(); // obtain next line from user through scanner object and remove trailing spaces
                keyword = Parser.SelectKeyword(input).toLowerCase().trim(); // grab keyword

            }

            if( Checker.CheckValidInput(keyword) ) // check if user gave a valid command
            {
                if(Checker.check_keyword(keyword) ) //check if user gave an Action keyword
                {
                    input = Parser.returnTaskDescription(input); // parse out description

                    if (taskmanager.ExecuteTaskCommand(TaskList, input, keyword)) continue;

                    // perform file writing operation
                    isDoneForFile = theFile.load_isDone_For_File(isDoneForFile, TaskList, index);

                    UI.printPrintTaskList(TaskList.size());

                    if(!theFile.FinishLoadFromFileStatus(index, TaskFromFile)) // if finish loading from file
                    {
                        int SucessStatus = 0;

                        try {
                            theFile.Write_to_File(TaskList); // determine if write is sucessful
                            theCalendar.Write_to_Calendar(TaskList); // determine if write is sucessful
                        }catch (IOException e) {
                            SucessStatus = 1;
                            errorManager.ManageWriteError(e);
                        }

                        if(SucessStatus == 1) { return; } //exit main if write to file fail
                    }
                    // end of file writting operation
                }
                else if(keyword.equals("print") ) //check if user gave a passive keyword "print"
                {
                    UI.printOut(TaskList);
                }
                else if(keyword.equals("exit")) //check if user gave a passive keyword "exit"
                {
                    UI.printExit();
                    break;
                }
                else if(keyword.equals("view"))
                {
                    calendar.GenerateCalendar(TaskList);
                }
            }
            else
            {
                UI.printError();
            }
        }
    }


}
