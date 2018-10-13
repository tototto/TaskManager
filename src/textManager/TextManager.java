package textManager;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * <h1>Text Manager</h1>
 * Manages all the operation pertaining to text handling
 * that CLI program accepts
 * contains list of crucial operation
 * <p>
 *
 * @author  Tay Jun Wen
 * @version 1.0
 * @since   2018-10-12
 */


public class TextManager
{
   private  String input;
   private  String keyword;
   private  String isDoneForFile;
   private  String calendar;

   public TextManager(ArrayList<String> TaskFromFile, int index)
   {
       input = TaskFromFile.get(index); // grab data sentence from array taken from file
       keyword = SelectKeywordFromFile(input).toLowerCase().trim(); // grab keyword from each sentence
       isDoneForFile = grabIsDone(input);// grab isDone details
   }

   public TextManager(ArrayList<String> CalendarFromFile, int index, String Calendar)
   {
       input = CalendarFromFile.get(index);
   }


   public String getInput()
   {
       return input;
       //return input.substring(0, input.lastIndexOf(" "));
   }

    public String getCalendarInput()
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
       int indxOfDeadline = Finding_Nth_occurrence(input, "|", 3);
       input = ParseWord(input, keyword, indxOfDeadline); // check if the taskManager is a Deadline taskManager and format it accordingly
       input = ConvertToNormal(input).trim(); // convert to a normal string
   }

    public String SelectKeywordFromFile(String input)
    {
        String[] array = input.split("\\|"); // split user input into array
        String keyword = array[1]; // grab keyword
        return keyword;
    }

    private String grabIsDone(String input)
    {
        return input.substring(input.lastIndexOf(" ")+1);
    }


    private String ArraysToString(String[] Array)
    {
        StringBuilder newString = new StringBuilder("");

        for(String x : Array)
            newString.append(" " + x);

        return newString.toString();
    }

    public  String getCalendar()
    {
        return calendar;
    }

    private  int Finding_Nth_occurrence(String str, String substr, int n) {
        int pos = str.indexOf(substr);
        while (--n > 0 && pos != -1)
            pos = str.indexOf(substr, pos + 1);
        return pos;
    }

    public static String Textparser(String input) throws StringIndexOutOfBoundsException// just to remove the first 2 and return rest
    {
        int indx = input.indexOf(" ", input.indexOf(" ")+1 );
        return input.substring(indx, input.lastIndexOf(" "));
    }

    private  String ParseWord(String input, String keyword, int indxOfDeadline)
    {
        if(keyword.equals("deadline"))
        {
            StringBuffer sb = new StringBuffer(input);
            sb.insert(indxOfDeadline, " /by ");
            input = sb.toString();
        }

        return input;
    }

    public String ConvertToNormal(String input)  // convert input string seperated by | delimeter into string seperated by ' ' spaces
    {
        String[] Array = input.split("\\|");

        input = ArraysToString(Array);
        input = Textparser(input);
        return input;
    }


}
