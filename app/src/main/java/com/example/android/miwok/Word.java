package com.example.android.miwok;

public class Word {
    private String defaultTransalation;
    private String miwokTransalation;

    public Word(String defaultTransalation, String miwokTransalation){
        this.defaultTransalation = defaultTransalation;
        this.miwokTransalation = miwokTransalation;
    }

    public String getDefaultTransalation() {
        return defaultTransalation;
    }

    public String getMiwokTransalation() {
        return miwokTransalation;
    }
}
