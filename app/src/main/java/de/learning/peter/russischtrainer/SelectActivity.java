package de.learning.peter.russischtrainer;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;

public class SelectActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select);
        Commons.init(this);
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

}
