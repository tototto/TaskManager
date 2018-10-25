package textManager;

/**
 * <h1>Paser - Allows program to manipulate input</h1>
 * The Parser retrieves certain words
 * or format input text in a way
 * that this CLI program will accept
 * <p>
 *
 * @author  Tay Jun Wen
 * @version 1.0
 * @since   2018-10-12
 */

public class Parser {

    /**
     * This method is used to retrieve keyword.
     * @param input This is the first parameter contains user input
     * @return String This returns the keyword.
     */
    public static String SelectKeyword(String input)
    {
        String[] array = input.split(" "); // split user input into array
        String keyword = array[0]; // grab keyword
        return keyword;
    }

    /**
     * This method is used to retrieve description for Deadline task.
     * @param input This is the first parameter contains user input
     * @return String This returns the description for Deadline task.
     */
    public static String returnDescriptionOfDeadline(String input)
    {
        int Index = input.indexOf("/by"); // calculate where the do_by keyword starts in input
        String desc = input.substring(0, Index ); // grab the description part (the one before do_by)
        return desc;
    }

    /**
     * This method is used to retrieve Timeline description of Deadline task.
     * @param input This is the first parameter contains user input
     * @return String This returns the description of the Timeline.
     */
    public static String returnDoByofDeadline(String input)
    {
        int Index = input.indexOf("/by"); // calculate where the do_by keyword starts in input
        String do_by = input.substring(Index, input.length()); // returns the unformatted do_by segment of user input
        do_by = returnTaskDescription(do_by).trim(); // Format the do_by segment of user input
        return do_by;
    }

    /**
     * This method is used to retrieve description of task.
     * @param input This is the first parameter contains user input
     * @return String This returns the description.
     */
    public static String returnTaskDescription(String input)
    {
        int StartPoint = input.indexOf(" "); // pick out index where keyword "add" ends
        StartPoint = StartPoint < 0 ? input.length() : StartPoint;
        input = input.substring(StartPoint, input.length()); // remove the add from user input
        return input;
    }
}
