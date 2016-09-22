package de.learning.peter.russischtrainer;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
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

public class MainActivity extends AppCompatActivity {
    private JSONArray wordsArray, formsArray;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        JSONObject jsonobj = null;
        try {
            jsonobj = new JSONObject(loadJSONFromAsset());
            wordsArray = jsonobj.getJSONArray("words");
            formsArray = jsonobj.getJSONArray("forms");
            //testing
            JSONObject word = (JSONObject) wordsArray.get(0);
            JSONObject form = (JSONObject) formsArray.get(0);
            this.setWord(word,form);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    private String loadJSONFromAsset() {
        String json = null;
        try {
            InputStream is = this.getAssets().open("words.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }

    public boolean setWord(JSONObject word, JSONObject form){
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
                Log.i("event","loop");
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
                    row.addView(wordEdit);
                    Space space  = new Space(this);
                    space.setMinimumWidth(20);
                    row.addView(space);
                    TextView answerView = new TextView(this);
                    answerView.setTextAppearance(android.R.style.TextAppearance_Medium);
                    row.addView(answerView);
                    tl.addView(row);
                    if(i > 0){
                        previousEdit.addTextChangedListener(new TextHandler(previousEdit,wordEdit, "test", previousView));
                    }
                    if(pronounsArray.length()-1 == i){
                        wordEdit.addTextChangedListener(new TextHandler(wordEdit, this.findViewById(R.id.button),"test", answerView));
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

}
