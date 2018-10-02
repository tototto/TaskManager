package com.company;

import java.util.ArrayList;
import java.util.Arrays;

public class TextManager
{
   private static String input;
   private static String keyword;
   private static String isDoneForFile;

   public TextManager(ArrayList<String> TaskFromFile, int index)
   {
       input = TaskFromFile.get(index); // grab data sentence from array taken from file
       keyword = SelectKeywordFromFile(input).toLowerCase().trim(); // grab keyword from each sentence
       isDoneForFile = grabIsDone(input);// grab isDone details
   }

   public static String getInput()
   {
       return input;
   }

   public String getKeyword()
   {
       return keyword.toLowerCase().trim();
   }

   public String getIsDoneForFile()
   {
       return isDoneForFile;
   }

   public void LoadText()
   {
       input = ConvertToNormal(input).trim(); // convert to a normal string
       input = ParseWord(input, keyword); // check if the task is a Deadline task and format it accordingly
   }

    public static String SelectKeywordFromFile(String input)
    {
        String[] array = input.split("\\|"); // split user input into array
        String keyword = array[1]; // grab keyword
        return keyword;
    }

    private String grabIsDone(String input)
    {
        return input.substring(input.lastIndexOf(" ")+1);
    }

    public String ConvertToNormal(String input)  // convert input string seperated by | delimeter into string seperated by ' ' spaces
    {
        String[] Array = input.split("\\|");

        input = ArraysToString(Array);
        input = Textparser(input);
        return input;
    }

    private String ArraysToString(String[] Array)
    {
        StringBuilder newString = new StringBuilder("");

        for(String x : Array)
            newString.append(" " + x);

        return newString.toString();
    }

    public static String Textparser(String input)// just to remove the first 2 and return rest
    {
        int indx = input.indexOf(" ", input.indexOf(" ")+1 );
        return input.substring(indx, input.lastIndexOf(" "));
    }

    private static String ParseWord(String input, String keyword)
    {
        if(keyword.equals("deadline"))
        {
            StringBuffer sb = new StringBuffer(input);
            sb.insert(input.lastIndexOf("   "), " /by");
            input = sb.toString();
        }

        return input;
    }
}
