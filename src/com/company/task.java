package com.company;

public class task
{
    private String description;
    private boolean isDone;
    private String Do_by;

    public task(String desc)
    {
        description = desc;
        isDone = false;
    }

    public void updateDescription(String description) { this.description = description; }

    public void updateisDone(String isDone)
    {
        SetAsDone(isDone.equals("Yes") ? true : false);
    }

    public String getDesciption()
    {
        return description;
    }

    public String getDone()
    {
        return isDone? "Yes": "No";
    }

    public void SetAsDone(boolean value) { isDone = value; }

    public String getDo_by()
    {
        return Do_by;
    }
}
