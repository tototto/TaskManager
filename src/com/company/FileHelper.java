package com.company;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class FileHelper {

    private String fileName;
    private File theFile;

    private static final String ERROR_ON_CLOSE_FILE = "Error: Cannot close file. Changes may not be saved";

    public FileHelper(String fileName) {
        this.fileName = fileName;
    }

    public void Create_or_Open_File() throws IOException
    {
        File textfile = new File(fileName);
        System.out.println("Opening and loading Task from file" + fileName);

        if(!textfile.exists())
        {
            System.out.println(fileName + "not found. Creating one in directory now.");
            textfile.createNewFile();
        }

        this.theFile = textfile;
    }

    public ArrayList<String> readFile() throws FileNotFoundException {

        Scanner s = new Scanner(theFile); // create a Scanner using the File as the source
        ArrayList<String> TaskInFile = new ArrayList<String>();

        while (s.hasNext()) {
            TaskInFile.add(s.nextLine());
        }

        return TaskInFile;
    }

    public void writeFile(ArrayList<String> lines){

    }

    public FileWriter Enable_Write_Mode(File textfile) throws IOException
    {
        FileWriter fw = new FileWriter(textfile);
        return fw;
    }

    public void Write_to_File(ArrayList<task> TaskList) throws IOException
    {
        // OPEN FILE IN WRITE MODE
        FileWriter fw = Enable_Write_Mode(theFile);

        // PERFORM WRITING
        for(int i = 0; i < TaskList.size(); i++)
        {
            task obj = TaskList.get(i);

            if(obj instanceof deadline)
            {
                fw.write("[" + (i + 1) + "] | " + obj.getClass().getSimpleName() + " | " + obj.getDesciption().trim() + " | " + obj.getDo_by() + " | " + obj.getDone());
                fw.write( System.lineSeparator());
            }
            else
            {
                fw.write("[" + (i + 1) + "] | " + obj.getClass().getSimpleName() + " | " + obj.getDesciption().trim() + " | " + obj.getDone());
                fw.write( System.lineSeparator());
            }

        }

        closeFile(fw);

    }

    public void closeFile(FileWriter fw)
    {
        try
        {
            fw.close();
        }
        catch (IOException e)
        {
            System.out.println(ERROR_ON_CLOSE_FILE);
            e.printStackTrace();
        }

        theFile = new File(fileName);
    }

    public static String load_isDone_For_File(String isDoneForFile, ArrayList<task> TaskList, int index)
    {
        if(isDoneForFile != null) // set the isDone for task loaded from file correctly
        {
            if(isDoneForFile.equals("Yes"))
                TaskList.get(index-1).SetAsDone(true);

            isDoneForFile = null;
        }

        return isDoneForFile;
    }


}
