package com.company;

public class Parser {

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
}
