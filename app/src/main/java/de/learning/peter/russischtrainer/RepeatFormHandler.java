package de.learning.peter.russischtrainer;

import android.content.Context;
import android.view.View;

/**
 * Created by Peter on 13.10.2016.
 */
public class RepeatFormHandler implements View.OnClickListener{
    Context c; String word; String form;
    public RepeatFormHandler(Context c, String word, String form){
        this.c = c;
        this.word = word;
        this.form = form;
    }

    @Override
    public void onClick(View v) {
        String[] forms = new String[1];
        forms[0]=form;
        Commons.repeatWord(c,word,forms);
    }
}
