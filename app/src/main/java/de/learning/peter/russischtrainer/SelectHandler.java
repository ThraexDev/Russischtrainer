package de.learning.peter.russischtrainer;

import android.content.Intent;
import android.view.View;

import org.json.JSONObject;

/**
 * Created by Peter on 25.09.2016.
 */
public class SelectHandler implements View.OnClickListener {
    JSONObject word;
    JSONObject formObject;
    SelectActivity s;
    public SelectHandler(JSONObject word, JSONObject formObject, SelectActivity s){
        this.word = word;
        this.formObject = formObject;
        this.s = s;
    }
    @Override
    public void onClick(View v) {
        s.repeatWord(word,formObject);
    }
}
