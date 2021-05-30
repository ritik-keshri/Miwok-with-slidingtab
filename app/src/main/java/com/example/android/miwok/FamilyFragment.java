package com.example.android.miwok;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.fragment.app.Fragment;

import java.util.ArrayList;

public class FamilyFragment extends Fragment {

    private MediaPlayer mediaPlayer;
    private AudioManager audioManager;

    AudioManager.OnAudioFocusChangeListener afChangeListner = new AudioManager.OnAudioFocusChangeListener() {
        @Override
        public void onAudioFocusChange(int focusChange) {
            if (focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT || focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK) {
                // Pause playback immediately
                mediaPlayer.pause();
                mediaPlayer.seekTo(0);
            } else if (focusChange == AudioManager.AUDIOFOCUS_LOSS) {
                // Pause playback
                mediaPlayer.stop();
                releaseMediaPlayer();
            } else if (focusChange == AudioManager.AUDIOFOCUS_GAIN) {
                // Resume playback
                mediaPlayer.start();
            }
        }
    };
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.word_list, container, false);

        //Use for the request Audio Focus
        audioManager = (AudioManager) getActivity().getSystemService(Context.AUDIO_SERVICE);

        //Array for list of words
        ArrayList<Word> words = new ArrayList<>();
        words.add(new Word("father", "әpә", R.drawable.family_father, R.raw.family_father));
        words.add(new Word("mother", "әṭa", R.drawable.family_mother, R.raw.family_mother));
        words.add(new Word("son", "angsi", R.drawable.family_son, R.raw.family_son));
        words.add(new Word("daughter", "tune", R.drawable.family_daughter, R.raw.family_daughter));
        words.add(new Word("older brother", "taachi", R.drawable.family_older_brother, R.raw.family_older_brother));
        words.add(new Word("younger brother", "chalitti", R.drawable.family_younger_brother, R.raw.family_younger_brother));
        words.add(new Word("older sister", "teṭe", R.drawable.family_older_sister, R.raw.family_older_sister));
        words.add(new Word("younger sister", "kolliti", R.drawable.family_younger_sister, R.raw.family_younger_sister));
        words.add(new Word("grandmother ", "ama", R.drawable.family_grandmother, R.raw.family_grandmother));
        words.add(new Word("grandfather", "paapa", R.drawable.family_grandfather, R.raw.family_grandfather));

        //ArrayAdapter uses Scrap View. It is not used because in ArrayAdapter there is only 1 TextView available so we have created our own Adapter that is WordAdapter.
        //Show the elements of the ArrayList without using loop.
        // adapter knows how to create list items for each item in the list.
        WordAdapter adapter = new WordAdapter(getActivity() ,words, R.color.category_family);

        //finding the view where word will be displayed
        ListView listView = (ListView) rootView.findViewById(R.id.list);

        //For displaying the words
        listView.setAdapter(adapter);

        listView.setOnItemClickListener((parent, view, position, id) -> {
            Word word = words.get(position);
            //releaseMediaPlayer() is used to not play the sound simultaneously when user played new audio while current audio is playing.
            //It help in stopping th old audio and playing the new audio.
            releaseMediaPlayer();

            int result = audioManager.requestAudioFocus(afChangeListner, AudioManager.STREAM_MUSIC, AudioManager.AUDIOFOCUS_GAIN_TRANSIENT);
            if (result == AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {

                //To get the id of the presses word
                mediaPlayer = MediaPlayer.create(getActivity(), word.getAudioId());
                //Start playing the media
                mediaPlayer.start();
                //For releasing the resource after competion
                mediaPlayer.setOnCompletionListener(completionListener);
            }
        });
        return rootView;
    }

    private MediaPlayer.OnCompletionListener completionListener = new MediaPlayer.OnCompletionListener() {
        @Override
        public void onCompletion(MediaPlayer mediaPlayer) {
            // Now that the sound file has finished playing, release the media player resources.
            releaseMediaPlayer();
        }
    };

    private void releaseMediaPlayer(){
        if(mediaPlayer != null){
            mediaPlayer.release();
            // Set the media player back to null. For our code, we've decided that setting the media player to null is an easy way to tell that the media player is not configured to play an audio file at the moment.
            mediaPlayer = null;

            //Abandon the Audio Manager Resource
            audioManager.abandonAudioFocus(afChangeListner);
        }
    }

    //Prevent playing audio when user leave the app.
    @Override
    public void onStop() {
        super.onStop();
        releaseMediaPlayer();
    }
}