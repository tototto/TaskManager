package task;

public class task
{
    private String description;
    private boolean isDone;
    private String Do_by;
    private String date;

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

    public void putDate(String... date)
    {
        this.date = String.join(" ", date);
    }

    public boolean checkDateNULL()
    {
        return date != null;
    }

    public String getDate() { return date; }

    public void setDateToNULL()
    {
        date = null;
    }

}



