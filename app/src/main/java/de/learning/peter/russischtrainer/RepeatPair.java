package de.learning.peter.russischtrainer;

import android.content.Context;

/**
 * Created by Peter on 14.10.2016.
 */
public class RepeatPair {
    public RepeatPair(String word, String form){
        this. word = word;
        this.form = form;
    }
    public String getWord() {
        return word;
    }

    String word;

    public String getForm() {
        return form;
    }

    String form;

    public void repeat(Context c){
        String[] forms = new String[1];
        forms[0]=form;
        Commons.repeatWord(c, word, forms);
    }
}
