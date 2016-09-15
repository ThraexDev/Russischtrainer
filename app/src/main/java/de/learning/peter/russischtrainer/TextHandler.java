package de.learning.peter.russischtrainer;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

/**
 * Created by Peter on 15.09.2016.
 */
public class TextHandler implements TextWatcher {
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
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        if(count > 0) {
            Log.i("s",Character.toString(s.charAt(count-1)));
            String key = s.subSequence(s.length()-1, s.length()).toString();
            if(key.equalsIgnoreCase("\n")){
                Log.i("Enter","true");
                String userin = s.subSequence(0, s.length()-1).toString();
                if(userin.equals(word)){
                    tv.setText("richtig");
                    next.requestFocus();
                }
                else {
                    tv.setText("falsch");
                }
                et.setText(userin);
            }
        }
        Log.i("start", Integer.toString(start));
        Log.i("before", Integer.toString(before));
        Log.i("count", Integer.toString(count));
    }

    @Override
    public void afterTextChanged(Editable s) {

    }
}
