package de.learning.peter.russischtrainer;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

/**
 * Created by Peter on 15.09.2016.
 */
public class TextHandler implements TextView.OnEditorActionListener {
    private EditText et;
    private View next;
    private TextView tv;
    private String word;
    public TextHandler(EditText et, View next, String word, TextView tv){
        this.et = et;
        this.next = next;
        this.word = word;
        this.tv = tv;
    }

    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        Log.i("EVENT","ID "+actionId);
        if(actionId == 5 || actionId == 6){
            String userin = v.getText().toString();
            if(userin.equals(word)){
                tv.setText("richtig");
            }
            else {
                tv.setText(word);
            }
            next.requestFocus();
        }
        return true;
    }
}
