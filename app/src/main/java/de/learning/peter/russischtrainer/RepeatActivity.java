package de.learning.peter.russischtrainer;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.InputType;
import android.widget.EditText;
import android.widget.Space;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

public class RepeatActivity extends Activity {
    RepeatController controller;

    JSONObject word = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_repeat);
        Intent i = getIntent();
        String word = i.getStringExtra("Word");
        String[] forms = i.getStringArrayExtra("Forms");
        controller = new RepeatController(this, word, forms);
        this.findViewById(R.id.finishButton).setOnClickListener(controller);
    }
    public void presentWordAndForm(RepeatController con, String word, String form){
        clearView();
        TableLayout tl = (TableLayout) this.findViewById(R.id.verticalLayout);
                TextView wordView = (TextView)this.findViewById(R.id.wordView);
                wordView.setText(Commons.nativeNameOf(word));
                TextView formView = (TextView)this.findViewById(R.id.formView);
                formView.setText(Commons.NativeFormNameOf(form));
                String[] pronounsArray = Commons.pronounsArrayOf(form);
                String[] wordInForm = Commons.wordInForm(word, form);
                EditText previousEdit = null;
                TextView previousView = null;
                for(int i = 0; i < pronounsArray.length; i++){
                    TableRow row = new TableRow(this);
                    TableRow.LayoutParams rowStyle = new TableRow.LayoutParams();
                    //rowStyle.setMargins(0,200,0,0);
                    row.setLayoutParams(rowStyle);
                    TextView pronounView = new TextView(this);
                    pronounView.setTextAppearance(android.R.style.TextAppearance_Medium);
                    pronounView.setText(pronounsArray[i]);
                    pronounView.setTextColor(Color.BLACK);
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
                        String verbInRightForm = wordInForm[i-1];
                        TextHandler th = new TextHandler(previousEdit,wordEdit, verbInRightForm, previousView);
                        previousEdit.setOnEditorActionListener(th);
                        con.getTextHandlers().add(th);
                    }
                    if(pronounsArray.length - 1 == i){
                        String verbInRightForm = wordInForm[i];
                        TextHandler th = new TextHandler(wordEdit, this.findViewById(R.id.finishButton),verbInRightForm, answerView);
                        wordEdit.setOnEditorActionListener(th);
                        con.getTextHandlers().add(th);
                    }
                    previousEdit = wordEdit;
                    previousView = answerView;
                }
            }

    public void clearView(){
        TableLayout tl = (TableLayout) this.findViewById(R.id.verticalLayout);
        tl.removeAllViews();
    }
}
