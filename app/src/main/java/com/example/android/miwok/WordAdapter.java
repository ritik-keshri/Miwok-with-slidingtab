package com.example.android.miwok;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import java.util.ArrayList;

public class WordAdapter extends ArrayAdapter<Word> {

    private int textBackgroundColor;

    public WordAdapter(Context context, ArrayList<Word> words, int textBackgroundColor) {
        super(context,0, words);
        this.textBackgroundColor = textBackgroundColor;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Check if the existing view is being reused, otherwise inflate(fill) the view
        View listItemView = convertView;
        if(listItemView == null)
            listItemView = LayoutInflater.from(getContext()).inflate(R.layout.list_item, parent, false);

        //For accessing the word from the Word class.
        // gives the index postion.
        Word currentWord = getItem(position);

        //Setting the Image to the ImageView in Activity
        //if image is not present(Phrases Activity) then ImageView will be removed for it.
        ImageView image = (ImageView) listItemView.findViewById(R.id.image);
        if(currentWord.hasImage()) {
            image.setImageResource(currentWord.getImageResourceId());
            image.setVisibility(View.VISIBLE);
        }
        else
            image.setVisibility(View.GONE);

        //Finding the layout where color will be changed
        // Find the color that the resource ID maps to(2nd line)
        LinearLayout textContainer = listItemView.findViewById(R.id.backgroundColor);
        int color = ContextCompat.getColor(getContext(), textBackgroundColor);
        textContainer.setBackgroundColor(color);

        //Setting the Miwok text to the textview in Activity
        TextView miwokText = (TextView) listItemView.findViewById(R.id.miwok_text_view);
        miwokText.setText(currentWord.getMiwokTransalation());

        //Setting the default text to the textview in Activity
        TextView defaultText = (TextView) listItemView.findViewById(R.id.default_text_view);
        defaultText.setText(currentWord.getDefaultTransalation());

        return listItemView;
    }
}
