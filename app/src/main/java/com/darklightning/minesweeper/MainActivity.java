package com.darklightning.minesweeper;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;
import static com.darklightning.minesweeper.Main2Activity.row;
import java.util.Random;

public class MainActivity extends AppCompatActivity implements View.OnClickListener , View.OnLongClickListener
{


    int n;
    int m;
    int tsize,decide;
    private int counter,countermines,open;
    boolean game=true,win_game;
    LinearLayout mainLayout;
    LinearLayout[] rows;
    MyButton buttons[][];
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mainLayout = (LinearLayout) findViewById(R.id.activity_main);
        mainLayout.setPadding(1,1,1,1);
        Intent intent = getIntent();
        if (null != intent)
        {
            m = intent.getIntExtra(row, 9);
        }
        if(m==9)
        {
            n=6;
            tsize=15;
            decide=8;
        }
        else if(m==12)
        {
            n=8;
            tsize=12;
            decide=15;
        }
        else if(m==16)
        {
            n=10;
            tsize=10;
            decide=20;
        }
        setUpBoard();
        setMines();
    }
    @Override

    public boolean onCreateOptionsMenu(Menu menu)
    {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.my_menu, menu);
        return true;
    }


    private void setUpBoard()
    {
        rows = new LinearLayout[m];
        buttons = new MyButton[m][n];
        for(int i=0;i<m;i++)
        {
            rows[i] = new LinearLayout(this);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 0, 1);
            rows[i].setLayoutParams(params);
            rows[i].setOrientation(LinearLayout.HORIZONTAL);
            mainLayout.addView(rows[i]);

        }

        for(int i=0;i<m;i++)
        {
            for(int j=0;j<n;j++)
            {
                buttons[i][j]=new MyButton(this);
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(0,ViewGroup.LayoutParams.MATCH_PARENT, 1);
                buttons[i][j].setLayoutParams(params);
                buttons[i][j].setOnClickListener(this);
                buttons[i][j].setOnLongClickListener(this);
                buttons[i][j].setTextSize(tsize);
                buttons[i][j].setBackgroundColor(Color.parseColor("#B9770E"));
                params.setMargins(1,1,1,1);
                buttons[i][j].setPadding(1,1,1,1);
                buttons[i][j].column = j;
                buttons[i][j].row = i;
                buttons[i][j].setValue(0);
                rows[i].addView(buttons[i][j]);
            }
        }
        randomMines();
    }
    public void randomMines()
    {
        for(int t=0;t<=decide;t++)
        {
            Random x = new Random();
            Random y = new Random();
            int xcord = x.nextInt(m);
            int ycord = y.nextInt(n);
            if(buttons[xcord][ycord].isMine==false)
            {
                buttons[xcord][ycord].setValue(-1);
                buttons[xcord][ycord].isMine = true;
                countermines++;
            }
            else
            {
                t--;
            }
        }
    }
    @Override

    public boolean onOptionsItemSelected(MenuItem item)
    {
        Toast.makeText(this,"refresh",Toast.LENGTH_SHORT).show();
        game = true;
        win_game = false;
        counter=0;
        countermines=0;
        open=0;
        for(int i=0;i<m;i++)
        {
            for(int j=0;j<n;j++)
            {
                buttons[i][j].setBackgroundColor(Color.parseColor("#B9770E"));
                buttons[i][j].refresh_flags();
                buttons[i][j].setText(" ");
                buttons[i][j].setTextColor(Color.parseColor("black"));
                buttons[i][j].setValue(0);
            }

        }
        randomMines();
        setMines();
        return true;
    }

    public void setMines()
    {
        for(int i=0;i<m;i++)
        {
            for(int j=0;j<n;j++)
            {
                buttons[i][j].setTextSize(10);
                if(buttons[i][j].getValue()==-1)
                {

                    for(int k=i-1;k<=i+1;k++)
                    {
                        for(int l =j-1;l<=j+1;l++)
                        {
                            if(check(k,l))
                            {
                                if(buttons[k][l].getValue()!=-1)
                                {
                                    buttons[k][l].setValue(buttons[k][l].getValue()+1);
                                }
                            }
                        }
                    }
                }
            }
        }
    }
    public void getzeros(int i, int j)
    {
        buttons[i][j].changeFlag();
        buttons[i][j].setBackgroundColor(Color.parseColor("#E0961F"));
        for(int k = i-1;k<=i+1;k++)
        {
            for(int l=j-1;l<=j+1;l++)
            {
                if((k!=i)||(l!=j))
                {
                    if(check(k,l))
                    {
                        if(buttons[k][l].returnFlag())
                        {
                            buttons[k][l].setTextColor(Color.parseColor("black"));
                            buttons[k][l].setText("" + buttons[k][l].getValue());
                            buttons[k][l].setTextSize(tsize);
                            buttons[k][l].changeFlag();
                            buttons[k][l].setBackgroundColor(Color.parseColor("#E0961F"));
                            open++;
                            if(buttons[k][l].getValue()==0)
                            {
                                buttons[k][l].setText(" ");
                                buttons[k][l].changeFlag();
                                getzeros(k,l);
                            }
                        }
                    }
                }
            }
        }
    }
    public boolean check(int k ,int l)
    {
        if(k<0||l<0||k>=m||l>=n)
            return false;
            return true;
    }
    public void onClick(View v)
    {
       if(game)
       {
           MyButton b = (MyButton) v;
           if(b.returnFlag())
           {
               if (b.getValue() == -1)
               {

                   gameOver();

               }
               else if (b.getValue()==0)
               {
                   b.setText(" ");
                   open++;
                   getzeros(b.row,b.column);
                   winGame();
               }
               else
               {
                   b.setText("" + b.getValue());
                   b.setTextSize(tsize);
                   b.setTextColor(Color.parseColor("black"));
                   b.changeFlag();

                   open++;
                   winGame();
                   b.setBackgroundColor(Color.parseColor("#E0961F"));
               }
           }
       }

    }

    private void gameOver()
    {
        game=false;
        if(win_game==false)
        {
            for(int i=0;i<m;i++)
            {
                for(int j=0;j<n;j++)
                {
                    if(buttons[i][j].isMine==true)
                    {
                        buttons[i][j].setBackgroundResource(R.drawable.unnamed);
                        buttons[i][j].setText(" ");
                        buttons[i][j].changeFlag();
                    }
                }
            }
        }
    }

    @Override
    public boolean onLongClick(View v)
    {
        MyButton b = (MyButton) v;

        if(game)
        {
            if(b.returnFlag())
            {
                b.setText("!");
                b.setTextSize(25);
                b.changeFlag();
                b.setTextColor(Color.parseColor("#7D3C98"));
                b.setBackgroundColor(Color.parseColor("#D4AC0D"));
                if(b.getValue()==-1)
                {
                    counter++;
                }
                b.marker=true;
                winGame();
                return  true;
            }
            else if(b.marker)
            {
                b.setText(" ");
                b.changeFlag();
                b.setTextSize(10);
                b.setBackgroundColor(Color.parseColor("#B9770E"));
                b.marker=false;
                if(b.getValue()==-1)
                {
                    counter--;
                }
                b.setTextColor(Color.parseColor("#010101"));
                winGame();
                return true;
            }
        }
        return false;
    }
    public void winGame()
    {
        if(countermines==counter)
        {
            Toast.makeText(this,"GAME WON!!!", Toast.LENGTH_LONG).show();
            win_game = true;
            gameOver();
        }
        else if(open==(m*n)-countermines)
        {
            Toast.makeText(this,"GAME WON!!!", Toast.LENGTH_LONG).show();
            win_game = true;
            gameOver();
        }
    }
}
