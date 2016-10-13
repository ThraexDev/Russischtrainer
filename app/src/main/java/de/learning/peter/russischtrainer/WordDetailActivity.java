package de.learning.peter.russischtrainer;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

public class WordDetailActivity extends AppCompatActivity {
 String word ="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_word_detail);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Intent intent = getIntent();
        String word = intent.getStringExtra("ID");
        this.word = word;
        TextView verView = (TextView) this.findViewById(R.id.VerbView);
        verView.setText(Commons.nativeNameOf(word));
    }
    @Override
    protected void onStart() {
        super.onStart();
        TableLayout tl = (TableLayout) this.findViewById(R.id.tableWordLayout);
        tl.removeAllViews();
        String[] formsArray = Commons.getAllForms();
        for(int i = 0; i < formsArray.length; i++){
            TableRow row = new TableRow(this);
            TextView tf = new TextView(this);
            tf.setText(Commons.NativeFormNameOf(formsArray[i]));
            TextView lf = new TextView(this);
            lf.setText("Level: "+Commons.getVerbForm(word, formsArray[i]).getLevel());
            Button b = new Button(this);
            b.setText("Üben");
            b.setOnClickListener(new RepeatFormHandler(this,word,formsArray[i]));
            row.addView(b);
            row.addView(tf);
            row.addView(lf);
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

    public void deleteWord(View v){
        Commons.removeLearnedWord(this.word);
        finish();
    }
}
