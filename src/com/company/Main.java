package com.company;

import java.io.*;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class Main
{
    private static final String MESSAGE_ADDED = "Welcome to TaskManager-Level1!";
    private static final String SYSTEM_PROMPT = "Your task?";
    private static final String PRINT_TASK_LIST = "Tasks in the list: ";

    private static final String ERROR_ON_OPEN_CREATE = "Error: Cannot create or open file";

    private static final String ERROR_ON_CLOSE_FILE = "Error: Cannot close file. Changes may not be saved";
    private static final String PATH = "tasklist.txt";

    public static void main(String[] args)
    {
	    System.out.println(MESSAGE_ADDED);

	    boolean runner = true; // To determine if program should continue
        Scanner GetString = new Scanner(System.in); // define Scanner object
        ArrayList<task> TaskList = new ArrayList<task>(); // create dynamic array using Collections to store task from user input
        ArrayList<String> TaskFromFile; // Dynamic array to store input from file
        FileHelper theFile = new FileHelper(PATH);
        int index = 0;
        String isDoneForFile = null;
        Printer printer = new Printer();
        errorManager ErrManager = new errorManager();

        // Sandbox the following procedure: create file if it does not exist. Open file if exist
        ErrManager.SandBox_CreateOpenFile(theFile);

        // Sandbox the following procedure: Read from File and store file result as string in TaskFromFile
        TaskFromFile = ErrManager.Sandbox_ReadFile(theFile);

        while(runner)
	    {
            String input = null;
            String keyword = null;
	        // loop through the TaskFromFile first, the variable 'input' and 'keyword' will hold everything from TaskFromFile
	        // use int index to track which row from file you are currently reading from and when to stop
            if(FinishLoadFromFileStatus(index, TaskFromFile)) // not finish loading from file
            {
                TextManager textManager = new TextManager(TaskFromFile, index);
                textManager.LoadText();

                input = TextManager.getInput();
                keyword = textManager.getKeyword();
                isDoneForFile = textManager.getIsDoneForFile();
                index++;
            }
            else
            {
                // after it is finished execute this block:
                System.out.print(SYSTEM_PROMPT);
                input = GetString.nextLine().trim(); // obtain next line from user through scanner object and remove trailing spaces
                keyword = SelectKeyword(input).toLowerCase().trim(); // grab keyword

            }

            if( CheckValidInput(keyword) ) // check if user gave a valid command
            {
                if(check_keyword(keyword) ) //check if user gave an Action keyword
                {
                    input = returnTaskDescription(input); // parse out description

                    // Sandbox the following procedure:
                    if(ErrManager.errorHandling(input)) {continue;}; // if description is empty

                    if(keyword.equals("update"))
                    {
                        String[] Array = input.split(" ");

                        try
                        {
                                if (Integer.parseInt(Array[1]) > 0 && TaskList.size() > 0)  // a valid index is selected to be updated
                                {
                                    // grab task to be updated
                                    task taskToBeUpdated = null;

                                    if (Integer.parseInt(Array[1]) - 1 <= Array.length) {
                                        taskToBeUpdated = TaskList.get(Integer.parseInt(Array[1]) - 1);
                                        // get details for update
                                        input = TextManager.Textparser(input);
                                    } else
                                        System.out.println("Please list a valid index to update");

                                    System.out.println("Updated index " + Integer.parseInt(Array[1]));


                                    // check if task in a Todo or a Deadline obj
                                    if (taskToBeUpdated instanceof deadline) {
                                        String desc = returnDescriptionOfDeadline(input);
                                        String do_by = returnDoByofDeadline(input);
                                        taskToBeUpdated.updateisDone(Array[Array.length - 1]);
                                        ((deadline) taskToBeUpdated).updateDo_by(do_by);
                                        taskToBeUpdated.updateDescription(desc);
                                    } else if (taskToBeUpdated instanceof todo) {
                                        taskToBeUpdated.updateDescription(input);
                                        taskToBeUpdated.updateisDone(Array[Array.length - 1]);
                                    }
                                }

                        }catch (NumberFormatException e) // catch if user did not enter a valid index
                        {
                            System.out.println("Please list a valid index to update");
                            System.out.println("How to update DEADLINE: [UPDATE] [IDX] [DESCRIPTION] /BY [DEADLINE] [DONE_STATUS] ");
                            System.out.println("How to update TODO: [UPDATE] [IDX] [DESCRIPTION] [DONE_STATUS] ");
                        }

                    }
                    else if(keyword.equals("todo"))
                    {
                        TaskList.add(new todo(input)); // add To.do to the list of task
                    }
                    else if( keyword.equals("deadline") )
                    {
                        try
                        {
                            String desc = returnDescriptionOfDeadline(input);
                            String do_by = returnDoByofDeadline(input);
                            deadline deadlines = new deadline(desc, do_by); // create deadline object
                            TaskList.add(deadlines); // add deadline object
                        }
                        catch (StringIndexOutOfBoundsException e)
                        {
                            System.out.println("Enter a valid Deadline task ");
                        }
                    }
                    else if(keyword.equals("done") )
                    {
                        int Doneindex = Integer.parseInt(input.trim()) - 1;
                        TaskList.get(Doneindex).SetAsDone(true);
                    }
                    else if(keyword.equals("delete"))
                    {
                        String[] Array = input.split(" ");

                        if(Array[1].equals("all")) { TaskList.clear(); }
                        else if ( Integer.parseInt(Array[1]) >  0 && TaskList.size() > 0)  // a valid index is selected to be deleted
                        {
                            TaskList.remove(Integer.parseInt(Array[1])-1);
                            System.out.println("Deleted index " + Integer.parseInt(Array[1]));
                        }
                        else { System.out.println("Please list a valid index to delete");}
                    }

                    // perform file writting operation
                    isDoneForFile = theFile.load_isDone_For_File(isDoneForFile, TaskList, index);

                    System.out.println(PRINT_TASK_LIST + TaskList.size());

                    if(!FinishLoadFromFileStatus(index, TaskFromFile)) // if finish loading from file
                    {
                        // Sandbox the following procedure:
                        int SucessStatus = ErrManager.SandBox_WriteToFile(theFile, TaskList); // determine if write is sucessful

                        if(SucessStatus == 1) { return; } //exit main if write to file fail
                    }
                    // end of file writting operation
                }
                else if(keyword.equals("print") ) //check if user gave a passive keyword "print"
                {
                    printer.printOut(TaskList);
                }
                else if(keyword.equals("exit")) //check if user gave a passive keyword "exit"
                {
                    Printer.printExit();
                    break;
                }
                else if(keyword.equals("view"))
                {
                    System.out.println("Generating Calender...");
                    NewCalendar calendar = new NewCalendar();
                    calendar.GenerateCalendar();
                }

            }
            else
            {
                Printer.printError();
            }
        }
    }

    public static boolean FinishLoadFromFileStatus(int index, ArrayList<String> TaskFromFile) // return true if not finish loading from file
    {
        return index < TaskFromFile.size();
    }

    public static String SelectKeyword(String input)
    {
        String[] array = input.split(" "); // split user input into array
        String keyword = array[0]; // grab keyword
        return keyword;
    }

    public static String returnDescriptionOfDeadline(String input)
    {
        int Index = input.indexOf("/by"); // calculate where the do_by keyword starts in input
        String desc = input.substring(0, Index ); // grab the description part (the one before do_by)
        return desc;
    }

    public static String returnDoByofDeadline(String input)
    {
        int Index = input.indexOf("/by"); // calculate where the do_by keyword starts in input
        String do_by = input.substring(Index, input.length()); // returns the unformatted do_by segment of user input
        do_by = returnTaskDescription(do_by).trim(); // Format the do_by segment of user input

        return do_by;
    }

    public static String returnTaskDescription(String input)
    {
        int StartPoint = input.indexOf(" "); // pick out index where keyword "add" ends
        StartPoint = StartPoint < 0 ? input.length() : StartPoint;
        input = input.substring(StartPoint, input.length()); // remove the add from user input

        return input;
    }

    public static boolean check_keyword(String keyword)
    {
        inputKeyword key;
        try{ key = inputKeyword.valueOf(keyword);}
        catch (Exception e) { return false; };

        switch(key)
        {
            case add:
            case deadline:
            case todo:
            case done:
            case delete:
            case update:
                return true;

            default:
                return false;
        }
    }

    public static boolean CheckValidInput(String keyword)
    {
        inputKeyword key;
        try{ key = inputKeyword.valueOf(keyword);}
        catch (Exception e) { return false; };

        switch(key)
        {
            case add:
            case deadline:
            case print:
            case exit:
            case todo:
            case done:
            case delete:
            case update:
            case view:
                return true;

            default:
                return false;
        }
    }


}
