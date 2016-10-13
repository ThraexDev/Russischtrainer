package de.learning.peter.russischtrainer;

import android.annotation.SuppressLint;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.os.Handler;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;

public class LearnedWordsActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_learned_words);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }

    @Override
    protected void onStart() {
        super.onStart();
        TableLayout tl = (TableLayout) this.findViewById(R.id.tableWordLayout);
        tl.removeAllViews();
        String[] wordsArray = Commons.getLearnedWords();
        for(int i = 0; i < wordsArray.length; i++){
            TableRow row = new TableRow(this);
            Button b = new Button(this);
            b.setText(Commons.nativeNameOf(wordsArray[i]));
            b.setOnClickListener(new DetailHandler(wordsArray[i] ,this));
            row.addView(b);
            tl.addView(row);
        }

    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
