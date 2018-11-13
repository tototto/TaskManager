package main;

import frontEnd.UserInterface;
import frontEnd.errorManager;
import task.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * help to load Tasks from the hard disk and
 * save tasks to the hard disk
 * Performs reading from Task File and Calendar File
 *
 * Able to determine if loading of file into program is complete
 * Writes to file in specific format
 *
 *  * @author  Tay Jun Wen
 *  * @version 1.0
 *  * @since   2018-10-12
 */

public class FileHelper {

    private String fileName;
    private File theFile;


    public FileHelper(String fileName) {
        this.fileName = fileName;

    }

    /**
     * Create file or open file
     * @throws IOException if operation fails
     */
    public void Create_or_Open_File() throws IOException {
        assert !fileName.isEmpty(): "Missing filename";

        File textfile = new File(fileName);
        UserInterface UI = new UserInterface();
        UI.printOpenMsg(fileName);

        if(!textfile.exists()) {
            System.out.println();
            textfile.createNewFile();
        }
        this.theFile = textfile;
    }

    /**
     * Extract data from text file
     * @return all task in file
     * @throws FileNotFoundException if file reading fails
     */
    public ArrayList<String> readFile() throws FileNotFoundException {

        assert !fileName.isEmpty(): "Missing filename";
        Scanner s = new Scanner(theFile); // create a Scanner using the File as the source
        ArrayList<String> TaskInFile = new ArrayList<String>();

        while (s.hasNext()) {
            TaskInFile.add(s.nextLine());
        }
        return TaskInFile;
    }

    /**
     *
     * @param textfile to be read from
     * @return return object that allows for file writing operation to be performed
     * @throws IOException if file cannot be opened for writing
     */
    public FileWriter Enable_Write_Mode(File textfile) throws IOException {
        FileWriter fw = new FileWriter(textfile);
        return fw;
    }

    /**
     * Write to file in specific format according to the type of task
     * and close file
     * @param TaskList data to be written to file
     * @throws IOException if write operation fails
     */
    public void Write_to_File(ArrayList<task> TaskList) throws IOException {
        // OPEN FILE IN WRITE MODE
        assert theFile.exists(): "Missing file";
        FileWriter fw = Enable_Write_Mode(theFile);

        // PERFORM WRITING
        for(int i = 0; i < TaskList.size(); i++) {
            task obj = TaskList.get(i);

            if(obj instanceof deadline) {
                fw.write("[" + (i + 1) + "] | " + obj.getClass().getSimpleName() + " | " + obj.getDesciption().trim() + " | " + obj.getDo_by() + " | " + obj.getDone() );
                fw.write( System.lineSeparator());
            }
            else {
                fw.write("[" + (i + 1) + "] | " + obj.getClass().getSimpleName() + " | " + obj.getDesciption().trim() + " | " + obj.getDone());
                fw.write( System.lineSeparator());
            }

        }
        closeFile(fw);
    }

    public void closeFile(FileWriter fw) {
        try {
            fw.close();
        }
        catch (IOException e) {
            errorManager.printCloseError();
            e.printStackTrace();
        }

        theFile = new File(fileName);
    }

    public static String load_isDone_For_File(String isDoneForFile, ArrayList<task> TaskList, int index) {
        if(isDoneForFile != null){ // set the isDone for taskManager loaded from file correctly

            if(isDoneForFile.equals("Yes"))
                TaskList.get(index-1).SetAsDone(true);

            isDoneForFile = null;
        }
        return isDoneForFile;
    }

    public void Write_to_Calendar(ArrayList<task> taskList) throws IOException {
        // OPEN FILE IN WRITE MODE
        assert theFile.exists(): "Missing file";
        FileWriter fw = Enable_Write_Mode(theFile);

        // PERFORM WRITING
        for(int i = 0; i < taskList.size(); i++) {
            task obj = taskList.get(i);

            if(obj.checkDateNULL()) {
                fw.write("add " + (i+1) + " "+ obj.getDate());
                fw.write(System.lineSeparator());
            }
        }
        closeFile(fw);
    }

    public static boolean FinishLoadFromFileStatus(int index, ArrayList<String> TaskFromFile){ // return true if not finish loading from file
        return index < TaskFromFile.size();
    }

    public boolean writeToHardDisk(FileHelper theFile, FileHelper theCalendar, ArrayList<task> TaskList) {
        boolean SucessStatus = true;

        try {
            theFile.Write_to_File(TaskList); // determine if write is sucessful
            theCalendar.Write_to_Calendar(TaskList); // determine if write is sucessful
        }catch (IOException e) {
            SucessStatus = false;
            errorManager.ManageWriteError(e);
        }

        if(SucessStatus == false) {
            return true;
        } //exit main if write to file fail
        return false;
    }
}
