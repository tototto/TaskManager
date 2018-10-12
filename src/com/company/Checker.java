package com.company;

import java.util.ArrayList;

public class Checker {

    public static boolean checkValidIndex(String index, ArrayList<task> TaskList)
    {
        return  Integer.parseInt(index) > 0 && TaskList.size() > 0;
    }

    public static boolean check_keyword(String keyword)
    {
        TaskList.inputKeyword key;
        try{ key = TaskList.inputKeyword.valueOf(keyword);}
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
        TaskList.inputKeyword key;
        try{ key = TaskList.inputKeyword.valueOf(keyword);}
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
