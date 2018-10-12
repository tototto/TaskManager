package com.company;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

public class Engine
{
     private Parser parser;
     private TaskManager taskmanager;
     private Checker checker;
     private FileHelper theFile;
     private FileHelper theCalendar;
     private UserInterface UI;
     private errorManager ErrManager;
     private NewCalendar calendar;
     private TaskList taskList;

    int index = 0;
    int indexForCalendar = 0;
    String isDoneForFile = null;

    public Engine()
    {
        parser = new Parser();
        taskmanager = new TaskManager();
        checker = new Checker();
        theFile = new FileHelper("tasklist.txt");
        theCalendar = new FileHelper("calendar.txt");
        ErrManager = new errorManager();
        calendar = new NewCalendar();
        UI = new UserInterface();
        taskList = new TaskList();
    }

    public void run()
    {
        UI.printMessageAdded();

        boolean runner = true; // To determine if program should continue

        // Sandbox the following procedure: create file if it does not exist. Open file if exist
        try{
            theFile.Create_or_Open_File();
            theCalendar.Create_or_Open_File();
        }catch(IOException e){ErrManager.ManageCreateOpenFileError(e);}

        // Sandbox the following procedure: Read from File and store file result as string in TaskFromFile
        ArrayList<task> TaskList = new ArrayList<task>(); // create dynamic array using Collections to store task from user input
        ArrayList<String> TaskFromFile = null;
        ArrayList<String> CalendarList = null;

        try {
            TaskFromFile = theFile.readFile();
            CalendarList = theCalendar.readFile();
        } catch (FileNotFoundException e) { ErrManager.ManageReadFileError(e);}

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

                input = TextManager.getInput();
                keyword = textManager.getKeyword();
                isDoneForFile = textManager.getIsDoneForFile();
                index++;
            }
            else if(theFile.FinishLoadFromFileStatus(indexForCalendar, CalendarList))
            {
                TextManager textManager = new TextManager(CalendarList, indexForCalendar, "calendar");
                input = TextManager.getCalendarInput();
                keyword = parser.SelectKeyword(input).toLowerCase().trim(); // grab keyword
                indexForCalendar++;
            }
            else
            {
                // after it is finished execute this block:
                UI.printSystemPrompt();
                input = UI.getUserInput(); // obtain next line from user through scanner object and remove trailing spaces
                keyword = parser.SelectKeyword(input).toLowerCase().trim(); // grab keyword

            }

            if( checker.CheckValidInput(keyword) ) // check if user gave a valid command
            {
                if(checker.check_keyword(keyword) ) //check if user gave an Action keyword
                {
                    input = parser.returnTaskDescription(input); // parse out description

                    if (taskmanager.ExecuteTaskCommand(TaskList, input, keyword)) continue;

                    // perform file writing operation
                    isDoneForFile = theFile.load_isDone_For_File(isDoneForFile, TaskList, index);

                    UI.printPrintTaskList(TaskList.size());

                    if(!theFile.FinishLoadFromFileStatus(index, TaskFromFile)) // if finish loading from file
                    {
                        // Sandbox the following procedure:
                        int SucessStatus = 0;

                        try {
                            theFile.Write_to_File(TaskList); // determine if write is sucessful
                            theCalendar.Write_to_Calendar(TaskList); // determine if write is sucessful
                        }catch (IOException e) {
                            SucessStatus = 1;
                            ErrManager.ManageWriteError(e);
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
