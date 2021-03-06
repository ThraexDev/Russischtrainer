package de.learning.peter.russischtrainer;

import android.content.Context;
import android.content.Intent;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Date;
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
    private final static String LEARNEDWORDSFILE = "learned_words";
    private final static String LEARNEDWORDS = "words";
    private final static String LEARNEDWORDSFORMS = "forms";
    private final static String DATEADD = "adddate";
    private final static String DATEREP = "repdate";
    private final static String LEVEL = "level";
    private final static String MUSTBEREPEATED="repeat";
    private static JSONArray wordsArray = null;
    private static JSONArray formsArray = null;
    private static HashMap<String, JSONObject> wordMap = null;
    private static HashMap<String, JSONObject> formMap = null;
    private static HashMap<String, Verb> learnedMap = null;
    private static ArrayList<Verb> learnedWords = null;
    private static Context mainActivity = null;
    private static long repeatTimes[] = {0,86400000,172800000,345600000,691200000,1382400000};
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

    private static HashMap getLearnedHashMap() {
        if (learnedMap == null) {
                learnedMap = new HashMap<String, Verb>();
                for (int i = 0; i < learnedWords.size(); i++) {
                    learnedMap.put(learnedWords.get(i).getId(), learnedWords.get(i));
                }
        }
        return learnedMap;
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
        loadLearnedWords(context);
        mainActivity = context;
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

    public static void repeatWord(Context c, String word, String[] Forms) {
        Intent intent = new Intent(c, RepeatActivity.class);
        intent.putExtra("Word", word);
        intent.putExtra("Forms", Forms);
        c.startActivity(intent);
    }

    public static boolean wordHasForm(String word, String form) {
        return ((JSONObject) getWordsHashMap().get(word)).has(form);
    }

    public static String[] pronounsArrayOf(String id) {
        JSONObject o = formMap.get(id);
        try {
            JSONArray a = (JSONArray) o.get(FORMPRONOUN);
            String pronounsArray[] = new String[a.length()];
            for (int i = 0; i < a.length(); i++) {
                pronounsArray[i] = a.get(i).toString();
            }
            return pronounsArray;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String[] wordInForm(String idWord, String idForm) {
        JSONObject o = wordMap.get(idWord);
        try {
            JSONArray a = (JSONArray) o.get(idForm);
            String wordsArray[] = new String[a.length()];
            for (int i = 0; i < a.length(); i++) {
                wordsArray[i] = a.get(i).toString();
            }
            return wordsArray;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void showAllWords(Context c) {
        Intent intent = new Intent(c, SelectActivity.class);
        c.startActivity(intent);
    }

    public static void showLearnedWords(Context c) {
        Intent intent = new Intent(c, LearnedWordsActivity.class);
        c.startActivity(intent);
    }

    public static void showRepeats(Context c) {
        Intent intent = new Intent(c, RepeatManager.class);
        c.startActivity(intent);
    }

    public static void showDetailWord(Context c, String id) {
        Intent intent = new Intent(c, WordDetailActivity.class);
        intent.putExtra("ID", id);
        c.startActivity(intent);
    }

    private static void saveOwnWord(Context c, String id) {
        Verb v = (Verb) getLearnedHashMap().get(id);
        FileOutputStream fos = null;
        JSONObject word = new JSONObject();
        try {
                word.put(WORDID, v.getId());
                word.put(DATEADD, v.getAdded().getTime());
                JSONArray formArray = new JSONArray();
                for(int j = 0; j < v.getVerbforms().length; j++){
                    JSONObject form = new JSONObject();
                    form.put(FORMID, v.getVerbforms()[j].getId());
                    form.put(DATEREP, v.getVerbforms()[j].getLastRepeated().getTime());
                    form.put(LEVEL, v.getVerbforms()[j].getLevel());
                    form.put(MUSTBEREPEATED, v.getVerbforms()[j].isMustBeRepeated());
                    formArray.put(form);
                word.put(LEARNEDWORDSFORMS, formArray);
            }
            fos = c.openFileOutput(v.getId(), Context.MODE_PRIVATE);
            fos.write(word.toString().getBytes());
            fos.close();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static String loadFile(Context c, String FileName) {
        FileInputStream fis = null;
        StringBuilder sb = new StringBuilder();
        try {
            fis = c.openFileInput(FileName);
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader bufferedReader = new BufferedReader(isr);
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                sb.append(line);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return sb.toString();
    }

    private static void loadLearnedWords(Context c) {
        learnedWords = new ArrayList<Verb>();
        File files[] = c.getFilesDir().listFiles();
        for(File file: files){
            if(getWordsHashMap().containsKey(file.getName())){
                try {
                    String s = loadFile(c, file.getName());
                    if(s==null){
                        return;
                    }
                    JSONObject word = new JSONObject(s);
                        Verb v = new Verb();
                        v.setId(word.getString(WORDID));
                        v.setAdded(new Date(word.getInt(DATEADD)));
                        JSONArray formArray = word.getJSONArray(LEARNEDWORDSFORMS);
                        VerbForm[] vf = new VerbForm[formArray.length()];
                        for (int j = 0; j < formArray.length(); j++) {
                            JSONObject form = (JSONObject) formArray.get(j);
                            VerbForm verbForm = new VerbForm();
                            verbForm.setId(form.getString(FORMID));
                            verbForm.setLastRepeated(new Date(form.getLong(DATEREP)));
                            verbForm.setLevel(form.getInt(LEVEL));
                            verbForm.setMustBeRepeated(form.getBoolean(MUSTBEREPEATED));
                            vf[j] = verbForm;
                        }
                        v.setVerbforms(vf);
                        learnedWords.add(v);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    public static void addLearnedWord(String id){
        if(getLearnedHashMap().containsKey(id)){
            return;
        }
        learnedMap = null;
        Verb verb = new Verb();
        verb.setId(id);
        verb.setAdded(new Date());
        String[] forms = getAllForms();
        VerbForm[] vf = new VerbForm[forms.length];
        for(int i = 0; i < forms.length; i++){
            VerbForm verbForm = new VerbForm();
            verbForm.setId(forms[i]);
            verbForm.setLastRepeated(new Date());
            verbForm.setLevel(1);
            verbForm.setMustBeRepeated(false);
            vf[i]= verbForm;
        }
        verb.setVerbforms(vf);
        learnedWords.add(verb);
        saveOwnWord(mainActivity, id);
    }

    public static void removeLearnedWord(String id){
        if(!getLearnedHashMap().containsKey(id)){
            return;
        }
        learnedMap = null;
        Verb verb = new Verb();
        for(int i = 0; i < learnedWords.size(); i++){
            if(learnedWords.get(i).getId().equals(id)){
                learnedWords.remove(i);
                break;
            }
        }
        File files[] = mainActivity.getFilesDir().listFiles();
        for(File file: files){
            if(file.getName().equals(id)){
                file.delete();
            }
        }

    }

    public static boolean isLearned(String id){
        if(getLearnedHashMap().containsKey(id)) return true;
        return false;
    }

    public static String[] getLearnedWords(){
        String[] r = new String[learnedWords.size()];
        for(int i = 0; i < learnedWords.size(); i++){
            r[i]= learnedWords.get(i).getId();
        }
        return r;
    }

    public static VerbForm getVerbForm(String wordId, String formId){
        Verb v =(Verb) getLearnedHashMap().get(wordId);

        if(v !=null){
            for(VerbForm vf : v.getVerbforms()){
                if(vf.getId().equals(formId)) return vf;
            }
        }
        return null;

    }

    public static void increaseLevel(String word, String form) {
        VerbForm vf = getVerbForm(word, form);
        if(vf.getLevel() < 6){
            vf.setMustBeRepeated(false);
            vf.setLevel(vf.getLevel()+1);
            saveOwnWord(mainActivity, word);
        }
    }

    public static void decreaseLevel(String word, String form) {
        VerbForm vf = getVerbForm(word, form);
        if(vf.getLevel() > 0){
            vf.setMustBeRepeated(true);
            vf.setLevel(vf.getLevel()-1);
            saveOwnWord(mainActivity, word);
        }
    }

    public static void setLastRepeated(String word, String form, Date d) {
        VerbForm vf = getVerbForm(word, form);
        vf.setLastRepeated(d);
    }

    public static RepeatPair[] getWordsToRepeat(){
        ArrayList<RepeatPair> list = new ArrayList<RepeatPair>();
        for(int i = 0; i < learnedWords.size(); i++){
            Verb v =  learnedWords.get(i);
            for(int j = 0; j < v.getVerbforms().length; j++){
                VerbForm vf = v.getVerbforms()[j];
                if(vf.mustBeRepeated || new Date().getTime() - vf.lastRepeated.getTime() >  repeatTimes[vf.getLevel()]){
                    list.add(new RepeatPair(v.getId(), vf.getId()));
                }
            }
        }
        return list.toArray(new RepeatPair[list.size()]);
    }
}