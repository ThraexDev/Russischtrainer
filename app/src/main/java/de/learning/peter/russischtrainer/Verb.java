package de.learning.peter.russischtrainer;

import java.util.Date;

/**
 * Created by Peter on 11.10.2016.
 */
public class Verb {
    String id;
    Date added;

    public void setId(String id) {
        this.id = id;
    }

    public void setAdded(Date added) {
        this.added = added;
    }

    public void setVerbforms(VerbForm[] verbforms) {
        this.verbforms = verbforms;
    }

    public VerbForm[] getVerbforms() {

        return verbforms;
    }

    public String getId() {
        return id;
    }

    public Date getAdded() {
        return added;
    }

    VerbForm[] verbforms;

    boolean mustBeRepeated;

    public boolean isMustBeRepeated() {
        return mustBeRepeated;
    }

    public void setMustBeRepeated(boolean mustBeRepeated) {
        this.mustBeRepeated = mustBeRepeated;
    }
}
