package checker;

import checker.inputKeyword;
import task.task;

import java.util.ArrayList;

/**
 * <h1>Checker</h1>
 * Checks if user entered a valid keyword
 * Ensures that
 * <p>
 *
 * @author  Tay Jun Wen
 * @version 1.0
 * @since   2018-10-12
 */


public class Checker {

    public static boolean checkValidIndex(String index, ArrayList<task> TaskList)
    {
        return  Integer.parseInt(index) > 0 && TaskList.size() > 0;
    }

    /** @param keyword check if keyword is valid
     *  @return determines validity
     *  Applicable to both functions below
     */

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
            case help:
            case remove:
            case search:
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
            case help:
            case remove:
            case search:
                return true;

            default:
                return false;
        }
    }
}
