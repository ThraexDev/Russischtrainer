package de.learning.peter.russischtrainer;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

public class SelectActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    protected void onStart() {
        super.onStart();
        TableLayout tl = (TableLayout) this.findViewById(R.id.tableWordLayout);
        tl.removeAllViews();
        String[] wordsArray = Commons.getAllWords();
        for(int i = 0; i < wordsArray.length; i++){
            TableRow row = new TableRow(this);
            Button b = new Button(this);
            b.setText(Commons.nativeNameOf(wordsArray[i]));
            b.setOnClickListener(new SelectHandler(wordsArray[i] ,this));
            if(Commons.isLearned(wordsArray[i])) b.setTextColor(Color.BLUE);
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
