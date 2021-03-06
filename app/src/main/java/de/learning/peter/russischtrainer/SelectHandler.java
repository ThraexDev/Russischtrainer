package de.learning.peter.russischtrainer;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.View;
import android.widget.Button;

import org.json.JSONObject;

/**
 * Created by Peter on 25.09.2016.
 */
public class SelectHandler implements View.OnClickListener {
    String word;
    Context c;
    public SelectHandler(String word, Context c){
        this.word = word;
        this.c = c;
    }
    @Override
    public void onClick(View v) {
        Commons.addLearnedWord(word);
        Commons.repeatWord(c, word, Commons.getAllForms());
    }
}
