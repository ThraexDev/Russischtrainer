package de.learning.peter.russischtrainer;

import android.app.Activity;
import android.view.View;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Peter on 09.10.2016.
 */
public class RepeatController implements View.OnClickListener {
    RepeatActivity activity;
    int formNumber = 0;
    String word;
    String[] forms;
    public RepeatController(RepeatActivity activity, String word, String[] forms){
        this.activity = activity;
        this.word = word;
        this.forms = forms;
        setWord();
    }
    @Override
    public void onClick(View v) {
        formNumber++;
        if(checkForFinish()) return;
        setWord();
    }
    public void setWord(){
        while(!Commons.wordHasForm(word,forms[formNumber])){
            formNumber++;
            if(checkForFinish()) return;
        }

        activity.presentWordAndForm(word, forms[formNumber]);
    }
    public boolean checkForFinish(){
        if(formNumber >= forms.length){

            activity.finish();
            return true;
        }
        return false;
    }
}
