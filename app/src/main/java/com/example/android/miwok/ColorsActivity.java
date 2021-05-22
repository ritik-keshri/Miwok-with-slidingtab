package com.example.android.miwok;

import android.os.Bundle;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class ColorsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.word_list);

        ArrayList<Word> words = new ArrayList<Word>();
        words.add(new Word("red", "weṭeṭṭi"));
        words.add(new Word("mustard yellow", "chiwiiṭә"));
        words.add(new Word("dusty yellow", "ṭopiisә"));
        words.add(new Word("green", "chokokki"));
        words.add(new Word("brown", "ṭakaakki"));
        words.add(new Word("gray", "ṭopoppi"));
        words.add(new Word("black", "kululli"));
        words.add(new Word("white", "kelelli"));

        //ArrayAdapter uses Scrap View. It is not used because in ArrayAdapter there is only 1 TextView available so we have created our own Adapter that is WordAdapter.
        //Show the elements of the ArrayList without using loop.
        // adapter knows how to create list items for each item in the list.
        WordAdapter adapter = new WordAdapter(this ,words);

        //finding the view where word will be displayed
        ListView listView = (ListView) findViewById(R.id.list);

        //For displaying the words
        listView.setAdapter(adapter);
    }
}