package de.learning.peter.russischtrainer;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;

public class SelectActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            TableLayout tl = (TableLayout) this.findViewById(R.id.tableWordLayout);
            String[] wordsArray = Commons.getAllWords();
            for(int i = 0; i < wordsArray.length; i++){
                TableRow row = new TableRow(this);
                Button b = new Button(this);
                b.setText(Commons.nativeNameOf(wordsArray[i]));
                b.setOnClickListener(new SelectHandler(wordsArray[i] ,this));
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
