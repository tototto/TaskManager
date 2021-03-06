package taskManager;

import checker.Checker;
import frontEnd.UserInterface;
import frontEnd.errorManager;
import main.*;
import task.*;
import textManager.Parser;
import textManager.TextManager;

import java.util.ArrayList;

/**
 * <h1>Task Manager</h1>
 *  * Contains Operation to interact with program
 *  * current list of task
 *  *
 *  * Allows for features of TaskManager CLI to operate
 *  * 1. Perform Adding of task
 *  * 2. Perform removal of task
 *  * 3. Perform Searching of task
 *  * 4. Perform Update of task - allows different type of update
 *  * 5. Display user aid text manual
 *  *
 *  * Contains set of core logic for which operation to be used
 *  * according to different user instructions
 *  * <p>
 *  *
 *  * @author  Tay Jun Wen
 *  * @version 1.0
 *  * @since   2018-10-12
 */

public class TaskManager
{
    private UserInterface UI;

    public TaskManager()
    {
        UI = new UserInterface();
    }

    public void AddDeadline(ArrayList<task> taskList, String input) {
        try
        {
            String desc = Parser.returnDescriptionOfDeadline(input);
            String do_by = Parser.returnDoByofDeadline(input);
            deadline deadlines = new deadline(desc, do_by); // create deadline object
            assert desc != null : "missing description for deadline";
            assert do_by != null : "missing description for deadline";
            taskList.add(deadlines); // add deadline object
        }
        catch (StringIndexOutOfBoundsException e)
        {
            System.out.println("Enter a valid Deadline task  ");
        }
    }

    public void AddTodo(ArrayList<task> taskList, String input) {
        taskList.add(new todo(input)); // add To.do to the list of taskManager
    }

    public void setToDone(ArrayList<task> taskList, String input) {
        int Doneindex = Integer.parseInt(input.trim()) - 1;
        taskList.get(Doneindex).SetAsDone(true);
        assert taskList.get(Doneindex).getDone() == "Yes" : "operation: set to done for current task failed";
    }

    public void removeDate(ArrayList<task> taskList, String input) {
        int Taskindex = Integer.parseInt(input.trim()) - 1;
        taskList.get(Taskindex).setDateToNULL();
        assert taskList.get(Taskindex).getDate() == null : "operation: remove date for current task failed";
    }

    public void deleteTask(ArrayList<task> taskList, String input) {
        String[] Array = input.split(" ");

        try{
            if(Array[1].equals("all")) { taskList.clear(); }
            else if ( Integer.parseInt(Array[1]) >  0 && taskList.size() > 0)  // a valid index is selected to be deleted
            {
                taskList.remove(Integer.parseInt(Array[1])-1);
                System.out.println("Deleted index " + Integer.parseInt(Array[1]));
            }
            else { System.out.println("Please do not leave description blank");}
        }catch (IndexOutOfBoundsException e) { UI.printInValidIndex();}
    }

    /**
     * update the existing task in program as per user input
     * @param taskList task specified will be extracted from the list of task
     * @param input the new data to be updated into the task in taskList. contains info on which task to update.
     */
    public void updater(ArrayList<task> taskList, String input) {
        String[] Array = input.split(" ");

        try
        {
            if (Checker.checkValidIndex(Array[1], taskList))  // a valid index is selected to be updated
            {
                // grab taskManager to be updated
                task taskToBeUpdated = null;

                if (Integer.parseInt(Array[1]) - 1 < taskList.size()) {
                    taskToBeUpdated = taskList.get(Integer.parseInt(Array[1]) - 1);
                    assert taskToBeUpdated != null : "Task to be updated is missing. Could be an invalid index";
                    // get details for update
                    input = TextManager.Textparser(input);
                } else {
                    UI.printInValidIndex();
                    return;
                }

                // check if taskManager in a Todo or a Deadline obj
                PerformUpdate(input, Array, taskToBeUpdated);
                System.out.println("Updated index " + Integer.parseInt(Array[1]));
            }

        }catch (Exception e){ // catch if user did not enter a valid index
                UI.printUpdateError();
        }
    }

    private void PerformUpdate(String input, String[] array, task taskToBeUpdated) {
        if (taskToBeUpdated instanceof deadline) {
            updateDeadline(input, array[array.length - 1], taskToBeUpdated);
        } else if (taskToBeUpdated instanceof todo) {
            updateTodo(input, array[array.length - 1], taskToBeUpdated);
        }
    }

    private void updateTodo(String input, String isDone, task taskToBeUpdated) {
        taskToBeUpdated.updateDescription(input);
        taskToBeUpdated.updateisDone(isDone);
    }

    private void updateDeadline(String input, String isDone, task taskToBeUpdated) {
        String desc = Parser.returnDescriptionOfDeadline(input);
        String do_by = Parser.returnDoByofDeadline(input);
        taskToBeUpdated.updateisDone(isDone);
        ((deadline) taskToBeUpdated).updateDo_by(do_by);
        taskToBeUpdated.updateDescription(desc);
    }

    /**
     * Contains core logic on the operation to perform
     * This is based on user inputted keyword
    */
    public boolean ExecuteTaskCommand(ArrayList<task> taskList, String input, String keyword) {
        errorManager ErrManager = new errorManager();
        NewCalendar calendar = new NewCalendar();;

        if( ErrManager.check_empty_string(input, keyword)) {
            return true;
        }

        // Task and keyword CANNOT be empty at this point
        assert !input.isEmpty() : "Input is empty";
        assert !keyword.isEmpty() : "Keyword is empty";

        if(keyword.equals("update")) {
            updater(taskList, input);
        }
        else if(keyword.equals("todo")) {
            AddTodo(taskList, input);
        }
        else if( keyword.equals("deadline") ) {
            AddDeadline(taskList, input);
        }
        else if(keyword.equals("done") ) {
            setToDone(taskList, input);
        }
        else if(keyword.equals("delete")) {
            deleteTask(taskList, input);
        }
        else if(keyword.equals("add")) { // add dates to calendar
            calendar.AddToCalendar(taskList, input);
        }
        else if(keyword.equals("remove")) {
            removeDate(taskList, input);
        }
        else if(keyword.equals("search")) {
            SearchTaskByType(taskList, input);
        }
        else if(keyword.equals("help")) {
            UI.printHelp();
        }

        return false;
    }

    private void SearchTaskByType(ArrayList<task> taskList, String input) {
                String type = input.trim();
                deadline Deadline = new deadline("sample", "sample"); // create sample deadline obj
                todo TODO = new todo("sample");// create sample todo obj
                int indx = 0;
                boolean isEmpty = true;

                for(task x : taskList) {
                    if(type.equals("deadline") && x.getClass() == Deadline.getClass()) {
                        UI.PrintDetails(x, indx);
                        isEmpty = false;
                    }
                    else if(type.equals("todo") && x.getClass() == TODO.getClass()) {
                        UI.PrintDetails(x, indx);
                        isEmpty = false;
                    }
                    indx++;
                }

        if(isEmpty)
            UI.printNoMatch();

    }

}
