package de.learning.peter.russischtrainer;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

public class RepeatManager extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_repeat_manager);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
    @Override
    protected void onStart() {
        super.onStart();
        TableLayout tl = (TableLayout) this.findViewById(R.id.tableWordLayout);
        tl.removeAllViews();
        RepeatPair[] rp = Commons.getWordsToRepeat();
        for(int i = 0; i < rp.length; i++){
            TableRow row = new TableRow(this);
            TextView tf = new TextView(this);
            tf.setText(Commons.nativeNameOf(rp[i].getWord()));
            TextView ff = new TextView(this);
            ff.setText(Commons.NativeFormNameOf(rp[i].getForm()));
            Button b = new Button(this);
            b.setText("Üben");
            b.setOnClickListener(new RepeatFormHandler(this,rp[i].getWord(), rp[i].getForm()));
            row.addView(b);
            row.addView(tf);
            row.addView(ff);
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
