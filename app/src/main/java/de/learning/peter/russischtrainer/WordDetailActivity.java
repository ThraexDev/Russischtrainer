package de.learning.peter.russischtrainer;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

public class WordDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_word_detail);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        TableLayout tl = (TableLayout) this.findViewById(R.id.tableWordLayout);
        String[] formsArray = Commons.getAllForms();
        for(int i = 0; i < formsArray.length; i++){
            TableRow row = new TableRow(this);
            TextView tf = new TextView(this);
            tf.setText(Commons.NativeFormNameOf(formsArray[i]));
            Button b = new Button(this);
            b.setText("Üben");
            b.setOnClickListener(new SelectHandler(formsArray[i] ,this));
            Button l = new Button(this);
            l.setText("Löschen");
            l.setOnClickListener(new SelectHandler(formsArray[i] ,this));
            if(Commons.isLearned(formsArray[i])) b.setTextColor(Color.BLUE);
            row.addView(b);
            row.addView(tf);
            row.addView(l);
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
