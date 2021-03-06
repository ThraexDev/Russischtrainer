package de.learning.peter.russischtrainer;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

public class DashActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_dash);
        Commons.init(this);
        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);
    }

    @Override
    protected void onStart() {
        super.onStart();
        TextView tv = (TextView) findViewById(R.id.verben);
        tv.setText("Du kennst "+Commons.getLearnedWords().length+" Verb(en)");
        TextView tv2 = (TextView) findViewById(R.id.verbformen);
        tv2.setText("Du musst "+Commons.getWordsToRepeat().length+" Verbform(en) wiederholen");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.mainmenu, menu);
        return true;
    }


    public void onWords(MenuItem item){
        Commons.showAllWords(this);
    }

    public void onList(MenuItem item){
        Commons.showLearnedWords(this);
    }

    public void onRepeat(MenuItem item){
        Commons.showRepeats(this);
    }

    public void onWordsB(View v){
        Commons.showAllWords(this);
    }

    public void onRepeatB(View v){
        Commons.showRepeats(this);
    }

}
