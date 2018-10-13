package frontEnd;

import java.io.FileNotFoundException;
import java.io.IOException;

public class errorManager
{
    UserInterface UI;

    public errorManager()
    {
        UI = new UserInterface();
    }

    public void ManageCreateOpenFileError(IOException e)
    {
            UI.printOpenError();
            e.printStackTrace();
    }

    public void ManageReadFileError(FileNotFoundException e)
    {
        UI.printReadError();
        e.printStackTrace();
    }

    public void ManageWriteError(IOException e)
    {
        e.printStackTrace();
    }

    public boolean check_empty_string(String input, String keyword)
    {
        if(input.length() <= 0 && !keyword.equals("help")) {
            UI.printMissingDescrip();
            return true;
        }
        else
            return false;
    }
}
