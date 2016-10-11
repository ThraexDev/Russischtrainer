package de.learning.peter.russischtrainer;

import java.util.Date;

/**
 * Created by Peter on 11.10.2016.
 */
public class VerbForm {
    String id;
    Date lastRepeated;
    int level;

    public void setId(String id) {
        this.id = id;
    }

    public void setLastRepeated(Date lastRepeated) {
        this.lastRepeated = lastRepeated;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public String getId() {

        return id;
    }

    public Date getLastRepeated() {
        return lastRepeated;
    }

    public int getLevel() {
        return level;
    }
}
