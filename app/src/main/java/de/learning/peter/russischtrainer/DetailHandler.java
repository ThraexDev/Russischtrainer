package de.learning.peter.russischtrainer;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.widget.Button;

/**
 * Created by Peter on 12.10.2016.
 */
public class DetailHandler implements View.OnClickListener {
    String word;
    Context c;
    public DetailHandler(String word, Context c){
        this.word = word;
        this.c = c;
    }
    @Override
    public void onClick(View v) {
        Commons.showDetailWord(c, word);
    }
}
