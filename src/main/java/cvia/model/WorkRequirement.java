package cvia.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by johnkevin on 8/11/15.
 */
public class WorkRequirement {

    private List<String> keywords;
    private int duration;

    public WorkRequirement() {
        this.keywords = new ArrayList<String>();
        this.duration = 0;
    }

    public List<String> getKeywords() {
        return keywords;
    }

    public void setKeywords(List<String> keywords) {
        this.keywords = keywords;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }




}
