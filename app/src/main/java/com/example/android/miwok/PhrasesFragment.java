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

public class PhrasesFragment extends Fragment {

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
        words.add(new Word("Where are you going?", "minto wuksus", R.raw.phrase_where_are_you_going));
        words.add(new Word("What is your name?", "tinnә oyaase'nә", R.raw.phrase_what_is_your_name));
        words.add(new Word("My name is...", "oyaaset...", R.raw.phrase_my_name_is));
        words.add(new Word("How are you feeling?", "michәksәs?", R.raw.phrase_how_are_you_feeling));
        words.add(new Word("I’m feeling good.", "kuchi achit", R.raw.phrase_im_feeling_good));
        words.add(new Word("Are you coming?", "әәnәs'aa?", R.raw.phrase_are_you_coming));
        words.add(new Word("Yes, I’m coming.", "hәә’ әәnәm", R.raw.phrase_yes_im_coming));
        words.add(new Word("I’m coming.", "әәnәm", R.raw.phrase_im_coming));
        words.add(new Word("Let’s go.", "yoowutis", R.raw.phrase_lets_go));
        words.add(new Word("Come here.", "әnni'nem", R.raw.phrase_come_here));

        //ArrayAdapter uses Scrap View. It is not used because in ArrayAdapter there is only 1 TextView available so we have created our own Adapter that is WordAdapter.
        //Show the elements of the ArrayList without using loop.
        // adapter knows how to create list items for each item in the list.
        WordAdapter adapter = new WordAdapter(getActivity() ,words, R.color.category_phrases);

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