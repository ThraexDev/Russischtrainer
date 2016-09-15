package de.learning.peter.russischtrainer;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;

public class MainActivity extends AppCompatActivity {
    private JSONArray wordsArray;
    private String[] froms = {"Präsens","Präteritum","Futur","Präteritum abgeschlossen","Futur abgeschlossen"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        JSONObject jsonobj = null;
        try {
            EditText et = (EditText) this.findViewById(R.id.editInf);
            EditText et1 = (EditText) this.findViewById(R.id.edit1);
            TextView tv = (TextView)this.findViewById(R.id.textInf);
            et.addTextChangedListener(new TextHandler(et,et1, "test", tv));
            //et1.addTextChangedListener(new TextHandler());
            //et1.requestFocus();
            jsonobj = new JSONObject(loadJSONFromAsset());
            wordsArray = jsonobj.getJSONArray("words");
            //testing
            JSONObject word = (JSONObject) wordsArray.get(0);
            TextView tf = (TextView)this.findViewById(R.id.wordView);
            tf.setText(word.get("german").toString());
            tf = (TextView)this.findViewById(R.id.formView);
            tf.setText(froms[0]);
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

}
