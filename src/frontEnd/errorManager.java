package frontEnd;

import java.io.FileNotFoundException;
import java.io.IOException;

public class errorManager
{
    private static UserInterface UI;

    public errorManager()
    {
        UI = new UserInterface();
    }

    public static void ManageCreateOpenFileError(IOException e)
    {
            UI.printOpenError();
            e.printStackTrace();
    }

    public static void ManageReadFileError(FileNotFoundException e)
    {
        UI.printReadError();
        e.printStackTrace();
    }

    public static void ManageWriteError(IOException e)
    {
        e.printStackTrace();
    }

    public static boolean check_empty_string(String input, String keyword)
    {
        if(input.length() <= 0 && !keyword.equals("help")) {
            UI.printMissingDescrip();
            return true;
        }
        else
            return false;
    }

    public static void printCloseError()
    {
        UI.printCloseError();
    }

    public static void printInValidDayError()
    {
        UI.printInValidDayError();
    }

    public static void printInValidIndex()
    {
        UI.printInValidIndex();
    }
}
