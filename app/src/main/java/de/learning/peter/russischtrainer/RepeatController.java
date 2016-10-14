package de.learning.peter.russischtrainer;

import android.app.Activity;
import android.view.View;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Peter on 09.10.2016.
 */
public class RepeatController implements View.OnClickListener {
    RepeatActivity activity;
    int formNumber = 0;
    String word;
    String[] forms;

    public ArrayList<TextHandler> getTextHandlers() {
        return textHandlers;
    }

    ArrayList<TextHandler> textHandlers;
    public RepeatController(RepeatActivity activity, String word, String[] forms){
        this.activity = activity;
        this.word = word;
        this.forms = forms;
        textHandlers = new ArrayList<TextHandler>();
        setWord();
    }
    @Override
    public void onClick(View v) {
        boolean right = true;
        for(int i = 0; i < textHandlers.size(); i++){
            if(!textHandlers.get(i).isRight()){
                right=false;
                break;
            }
        }
        if(right){
            Commons.increaseLevel(word, forms[formNumber]);
        }
        else Commons.decreaseLevel(word, forms[formNumber]);
        Commons.setLastRepeated(word, forms[formNumber], new Date());
        textHandlers = new ArrayList<TextHandler>();
        formNumber++;
        if(checkForFinish()) return;
        setWord();
    }
    public void setWord(){
        while(!Commons.wordHasForm(word,forms[formNumber])){
            formNumber++;
            if(checkForFinish()) return;
        }

        activity.presentWordAndForm(this, word, forms[formNumber]);
    }
    public boolean checkForFinish(){
        if(formNumber >= forms.length){

            activity.finish();
            return true;
        }
        return false;
    }
}
