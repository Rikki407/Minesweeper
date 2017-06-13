package com.darklightning.minesweeper;

import android.content.Context;
import android.widget.Button;

/**
 * Created by rikki on 1/16/17.
 */

public class MyButton extends Button
{
    private int value;
    public int row ,column;
    private boolean flag=true;
    public boolean isMine,marker;
    public MyButton(Context context)
    {
        super(context);
    }

    public int getValue()
    {
        return value;
    }

    public void setValue(int value)
    {
        this.value = value;
    }
    public void changeFlag()
    {
        if(flag==true)
        {
            flag = false;
        }
        else
        {
            flag=true;
        }
    }
    public boolean returnFlag()
    {
        return flag;
    }
    public void refresh_flags()
    {
        flag=true;
        isMine=false;
        marker=false;
    }
}
