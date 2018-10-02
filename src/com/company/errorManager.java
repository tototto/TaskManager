package com.company;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

public class errorManager
{
    private static final String ERROR_ON_OPEN_CREATE = "Error: Cannot create or open file";
    private static final String ERROR_ON_READ_FILE = "Error: Cannot read from file";

    public void SandBox_CreateOpenFile(FileHelper theFile)
    {
        try { theFile.Create_or_Open_File(); }
        catch (IOException e)
        {
            System.out.println(ERROR_ON_OPEN_CREATE);
            e.printStackTrace();
            return; //exit main if file cannot be created
        }
    }

    public ArrayList<String> Sandbox_ReadFile(FileHelper theFile)
    {
        try
        {
            return theFile.readFile();
        }
        catch (FileNotFoundException e)
        {
            System.out.println(ERROR_ON_READ_FILE);
            e.printStackTrace();
        }

        return null;
    }

    public int SandBox_WriteToFile(FileHelper theFile, ArrayList<task> TaskList)
    {
        try
        {
            theFile.Write_to_File(TaskList);
        } catch (IOException e)
        {
            return 1; // return 1 to exit main if write to file fail
        }

        return 0;
    }

    public static boolean errorHandling(String input)
    {
        if(check_empty_string(input))
        {
            try { throw new TaskManagerException("missing description"); } // produce exception for missing description
            catch (TaskManagerException e) // catch error
            {
                Printer.printMissingDescrip();
            }
            return true;
        }

        return false;
    }

    private static boolean check_empty_string(String input)
    {
        if(input.length() <= 0 )
            return true;
        else
            return false;
    }
}
