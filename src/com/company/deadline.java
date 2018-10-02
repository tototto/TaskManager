package com.company;

public class deadline extends todo
{
    private String Do_by;

    public deadline(String desc, String Do_by)
    {
        super(desc);
        this.Do_by = Do_by;
    }

    public String getDo_by()
    {
        return Do_by;
    }

    public void updateDo_by(String update)
    {
        this.Do_by = update;
    }

}
