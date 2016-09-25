package de.learning.peter.russischtrainer;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Space;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;

public class RepeatActivity extends AppCompatActivity {
    private JSONArray wordsArray, formsArray;
    private int formNumber = 0;

    public void setWordAndForms(JSONObject word, JSONArray formsArray) {
        this.word = word;
        this.formsArray = formsArray;
        formNumber = -1;
        presentNextForm();
    }

    private void presentNextForm(){
        formNumber++;
        if(formNumber >= formsArray.length()){
            finish();
            return;
        }
        try {
            if(!this.presentWordAndForm(word,(JSONObject) formsArray.get(formNumber))){
                presentNextForm();
            }
        } catch (JSONException e) {
            e.printStackTrace();
            presentNextForm();
        }
    }

    JSONObject word = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_repeat);
        Intent i = getIntent();
        String wordString = i.getStringExtra("Word");
        String formString = i.getStringExtra("Forms");
        try {
            JSONObject jsonobj = new JSONObject(formString);
            JSONArray array = jsonobj.getJSONArray("forms");
            this.setWordAndForms(new JSONObject(wordString), array );
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    private boolean presentWordAndForm(JSONObject word, JSONObject form){
        clearView();
        TableLayout tl = (TableLayout) this.findViewById(R.id.verticalLayout);
        try {
            String formId = form.getString("id");
            if (word.has(formId)){
                TextView wordView = (TextView)this.findViewById(R.id.wordView);
                wordView.setText(word.getString("german"));
                TextView formView = (TextView)this.findViewById(R.id.formView);
                formView.setText(form.getString("name"));
                JSONArray pronounsArray = form.getJSONArray("pronouns");
                EditText previousEdit = null;
                TextView previousView = null;
                for(int i = 0; i < pronounsArray.length(); i++){
                    TableRow row = new TableRow(this);
                    TableRow.LayoutParams rowStyle = new TableRow.LayoutParams();
                    rowStyle.setMargins(0,10,0,0);
                    row.setLayoutParams(rowStyle);
                    TextView pronounView = new TextView(this);
                    pronounView.setTextAppearance(android.R.style.TextAppearance_Medium);
                    pronounView.setText((String) form.getJSONArray("pronouns").get(i));
                    row.addView(pronounView);
                    EditText wordEdit = new EditText(this);
                    wordEdit.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                    row.addView(wordEdit);
                    Space space  = new Space(this);
                    space.setMinimumWidth(20);
                    row.addView(space);
                    TextView answerView = new TextView(this);
                    answerView.setTextAppearance(android.R.style.TextAppearance_Medium);
                    row.addView(answerView);
                    tl.addView(row);
                    if(i > 0){
                        String verbInRightForm = (String) word.getJSONArray(form.getString("id")).get(i-1);
                        previousEdit.setOnEditorActionListener(new TextHandler(previousEdit,wordEdit, verbInRightForm, previousView));
                    }
                    if(pronounsArray.length()-1 == i){
                        String verbInRightForm = (String) word.getJSONArray(form.getString("id")).get(i);
                        wordEdit.setOnEditorActionListener(new TextHandler(wordEdit, this.findViewById(R.id.finishButton),verbInRightForm, answerView));
                    }
                    previousEdit = wordEdit;
                    previousView = answerView;
                }
                return true;
            }
            else{
                return false;
            }
        } catch (JSONException e) {
            e.printStackTrace();
            return false;
        }
    }

    public void onFinish(View v){
        Log.i("Finish","Finish");
        this.presentNextForm();
    }

    public void clearView(){
        TableLayout tl = (TableLayout) this.findViewById(R.id.verticalLayout);
        tl.removeAllViews();
    }
}
