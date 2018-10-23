package com.company;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

import textManager.*;

class ParserTest {
/*
* Test Core logic of TaskManager
* Usability Testing Not Covered
* Functional Testing of Core functions for Parser
*/
    @Test
    void selectKeyword() throws Exception{
        Parser parser = new Parser();
        String Keyword1 = parser.SelectKeyword("todo sample description");
        String Keyword2 = parser.SelectKeyword("deadline sample description 2 /by tomorrow noon");
        String Keyword3 = parser.SelectKeyword("done sample description 2");
        String Keyword4 = parser.SelectKeyword("delete sample description 2");
        String Keyword5 = parser.SelectKeyword("add sample description 2");
        String Keyword6 = parser.SelectKeyword("update sample description 2");

        assertEquals("todo", Keyword1);
        assertEquals("deadline", Keyword2);
        assertEquals("done", Keyword3);
        assertEquals("delete", Keyword4);
        assertEquals("add", Keyword5);
        assertEquals("update", Keyword6);
    }

    @Test
    void returnTaskDescription() {
        Parser parser = new Parser();

        // testing for deadline
        String deadlineDescrip = parser.returnTaskDescription("deadline sample description /by sample deadline"); // parse out description
        assertEquals(" sample description /by sample deadline", deadlineDescrip);

        // testing for todo
        String todoDescrip = parser.returnTaskDescription("todo sample description of todo"); // parse out description
        assertEquals(" sample description of todo", todoDescrip);
    }

    @Test
    void returnDoByofDeadline() {
        Parser parser = new Parser();
        String descrip1 = parser.returnTaskDescription("deadline sample description /by sample deadline"); // parse out description
        String doBy = parser.returnDoByofDeadline(descrip1);
        assertEquals("sample deadline", doBy);
    }

    @Test
    void returnDescriptionOfDeadline() {
        Parser parser = new Parser();
        String descrip1 = parser.returnTaskDescription("deadline sample description /by sample deadline"); // parse out description
        descrip1 = parser.returnDescriptionOfDeadline(descrip1);
        assertEquals(" sample description ", descrip1);
    }
}