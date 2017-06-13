package com.darklightning.minesweeper;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

public class Main2Activity extends AppCompatActivity
{

    public static final String row = "row";
    public static final String column = "column";
    int i;



    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

    }



    public void sendMessage(View v)
    {
        Intent intent = new Intent(this,MainActivity.class);
        if(i==1)
        {
            intent.putExtra(row,9);
        }
        else if(i==2)
        {
            intent.putExtra(row,12);
        }
        else if(i==3)
        {
            intent.putExtra(row,16);
        }
        startActivity(intent);

    }
    public void easy(View view)
    {
        i=1;
    }
    public void medium(View view)
    {
        i=2;
    }
    public void hard(View view)
    {
       i=3;
    }

}
