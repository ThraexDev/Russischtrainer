package de.learning.peter.russischtrainer;

import android.content.Context;
import android.content.Intent;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;

/**
 * Created by Peter on 09.10.2016.
 */
public final class Commons {
    private static JSONObject allWordsAndForms = null;
    private final static String WORDSFILENAME = "words";
    private final static String WORDSARRAYNAME = "words";
    private final static String FORMSARRAYNAME = "forms";
    private final static String WORDID = "id";
    private final static String FORMID = "id";
    private final static String WORDNATIVE = "german";
    private final static String FORMNAME = "name";
    private final static String FORMPRONOUN = "pronouns";
    private static JSONArray wordsArray = null;
    private static JSONArray formsArray = null;
    private static HashMap<String, JSONObject> wordMap = null;
    private static HashMap<String, JSONObject> formMap = null;

    private Commons() {
    }
    private static JSONArray getFormsArray() {
        if (formsArray == null) {
            try {
                formsArray = (JSONArray) allWordsAndForms.getJSONArray(FORMSARRAYNAME);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return formsArray;
    }

    private static HashMap getFormsHashMap() {
        if (formMap == null) {
            try {
                formMap = new HashMap<String, JSONObject>();
                JSONArray array = getFormsArray();
                for (int i = 0; i < array.length(); i++) {

                    JSONObject o = (JSONObject) array.get(i);

                    formMap.put(o.getString(FORMID), o);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return formMap;
    }

    private static JSONArray getWordsArray() {
        if (wordsArray == null) {
            try {
                wordsArray = (JSONArray) allWordsAndForms.getJSONArray(WORDSARRAYNAME);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return wordsArray;
    }

    private static HashMap getWordsHashMap() {
        if (wordMap == null) {
            wordMap = new HashMap<String, JSONObject>();
            try {
                JSONArray array = getWordsArray();
                for (int i = 0; i < array.length(); i++) {

                    JSONObject o = (JSONObject) array.get(i);

                    wordMap.put(o.getString(WORDID), o);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return wordMap;
    }

    public static void init(Context context) {
        if (allWordsAndForms == null) {
            allWordsAndForms = loadJSONFromAsset(context, WORDSFILENAME);
        }
    }

    static JSONObject loadJSONFromAsset(Context context, String name) {
        String json = null;
        try {
            InputStream is = context.getAssets().open(name + ".json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        try {
            return new JSONObject(json);
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String[] getAllWords() {
        try {
            JSONArray wordsArray = getWordsArray();
            String[] allWords = new String[wordsArray.length()];
            for (int i = 0; i < wordsArray.length(); i++) {
                JSONObject o = (JSONObject) wordsArray.get(i);
                allWords[i] = o.getString(WORDID);
            }
            return allWords;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String[] getAllForms() {
        try {
            JSONArray formsArray = getFormsArray();
            String[] allForms = new String[formsArray.length()];
            for (int i = 0; i < formsArray.length(); i++) {
                JSONObject o = (JSONObject) formsArray.get(i);
                allForms[i] = o.getString(WORDID);
            }
            return allForms;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String nativeNameOf(String id) {
        try {
            JSONObject o = (JSONObject) getWordsHashMap().get(id);
            return o.getString(WORDNATIVE);
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String NativeFormNameOf(String id) {
        try {
            JSONObject o = (JSONObject) getFormsHashMap().get(id);
            return o.getString(FORMNAME);
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void repeatWord(Context c, String word, String[] Forms){
        Intent intent = new Intent(c, RepeatActivity.class);
        intent.putExtra("Word", word);
        intent.putExtra("Forms",Forms);
        c.startActivity(intent);
    }

    public static boolean wordHasForm(String word, String form){
        return ((JSONObject) getWordsHashMap().get(word)).has(form);
    }

    public static String[] pronounsArrayOf(String id){
        JSONObject o = formMap.get(id);
        try {
            JSONArray a = (JSONArray) o.get(FORMPRONOUN);
            String pronounsArray[] = new String[a.length()];
            for(int i = 0; i < a.length(); i++){
                pronounsArray[i]=a.get(i).toString();
            }
            return pronounsArray;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String[] wordInForm(String idWord, String idForm){
        JSONObject o = wordMap.get(idWord);
        try {
            JSONArray a = (JSONArray) o.get(idForm);
            String wordsArray[] = new String[a.length()];
            for(int i = 0; i < a.length(); i++){
                wordsArray[i]=a.get(i).toString();
            }
            return wordsArray;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }
}
