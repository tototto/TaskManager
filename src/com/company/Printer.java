package com.company;

import java.util.ArrayList;

public class Printer
{
    private static final String NO_DEADLINE = "THIS TASK HAS NO DEADLINE";
    private static final String EXIT_MESSAGE = "Bye!";
    private static final String ERROR_MESSAGE = "Unknown command! please try again";
    private static final String MISSING_DESCRIPTION = "Error: Empty description";

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
    }

    public static void printExit()
    {
        System.out.println(EXIT_MESSAGE);
    }

    public static void printError()
    {
        System.out.println(ERROR_MESSAGE);
    }

    public static void printMissingDescrip()
    {
        System.out.println(MISSING_DESCRIPTION);
    }

}
