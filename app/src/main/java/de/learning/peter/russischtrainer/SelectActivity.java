package de.learning.peter.russischtrainer;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TableRow;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;

public class SelectActivity extends AppCompatActivity {
    JSONArray wordsArray;
    JSONArray formsArray;
    JSONObject allObject;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select);
        try {
            allObject = new JSONObject(loadJSONFromAsset());
            wordsArray = allObject.getJSONArray("words");
            formsArray = allObject.getJSONArray("forms");

            TableLayout tl = (TableLayout) this.findViewById(R.id.tableWordLayout);

            for(int i = 0; i < wordsArray.length(); i++){
                TableRow row = new TableRow(this);
                Button b = new Button(this);
                JSONObject o = (JSONObject) wordsArray.get(i);
                b.setText(o.getString("german"));
                b.setOnClickListener(new SelectHandler((JSONObject) wordsArray.get(i),allObject,this));
                row.addView(b);
                tl.addView(row);
            }
            JSONObject word = (JSONObject) wordsArray.get(0);
            Intent intent = new Intent(this, RepeatActivity.class);
            intent.putExtra("Word",word.toString());
            intent.putExtra("Forms",allObject.toString());
            //startActivity(intent);
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    public void repeatWord(JSONObject word, JSONObject allForms){
        Intent intent = new Intent(this, RepeatActivity.class);
        intent.putExtra("Word",word.toString());
        intent.putExtra("Forms",allForms.toString());
        startActivity(intent);
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
}
