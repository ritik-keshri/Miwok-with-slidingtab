package com.example.android.miwok;

public class Word {
    private String defaultTransalation;
    private String miwokTransalation;
    private int audioId;
    private int imageResourceId = -1;

    public Word(String defaultTransalation, String miwokTransalation, int audioId){
        this.defaultTransalation = defaultTransalation;
        this.miwokTransalation = miwokTransalation;
        this.audioId = audioId;
    }

    public Word(String defaultTransalation, String miwokTransalation, int imageResourceId, int audioId){
        this.defaultTransalation = defaultTransalation;
        this.miwokTransalation = miwokTransalation;
        this.imageResourceId = imageResourceId;
        this.audioId = audioId;
    }

    public String getDefaultTransalation() {
        return defaultTransalation;
    }

    public String getMiwokTransalation() {
        return miwokTransalation;
    }

    public int getImageResourceId(){
        return imageResourceId;
    }

    public int getAudioId(){
        return audioId;
    }

    public boolean hasImage(){
        if(imageResourceId != -1)
            return true;
        else
            return false;
    }
}
