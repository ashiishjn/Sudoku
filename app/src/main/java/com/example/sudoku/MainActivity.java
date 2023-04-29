package com.example.sudoku;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    int random_position;
    GridView gridView;
    int[] number = new int[81];
    List<String> allsudokulist = new ArrayList<>();
    int level=-2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        playMusic(R.raw.background_music);
        try {
            ReadTextFile();
            createTable();
        }
        catch (Exception e){}
    }
    public static MediaPlayer music;
    public void playMusic(int id)
    {
        music = MediaPlayer.create(MainActivity.this, id);
        music.setLooping(true);
        music.start();
    }
    public void createTable()
    {
        if(level == 10)
            level = 0;
        else
            level+=2;
        char[] ch=allsudokulist.get(level).toCharArray();
        for(int i=0;i<81;i++)
            number[i]=ch[i]-48;
        gridView = findViewById(R.id.grid);
        grid_adapter adapter = new grid_adapter(MainActivity.this, number);
        gridView.setAdapter(adapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                adapter.setItem(view, i);
                playTapSound();
            }
        });
        Button btn = findViewById(R.id.check);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                playTapSound();
                if(btn.getText().equals("Check"))
                {
                    int flag = 0;
                    if (adapter.completed()) {
                        for (int i = 0; i < 81; i++) {
                            if (allsudokulist.get(level + 1).charAt(i) - '0' != adapter.getNum(i)) {
                                flag = 1;
                                adapter.ColorSet(i, gridView.getChildAt(i));
                            }
                        }
                        if (flag == 0) {
                            Toast.makeText(MainActivity.this, "Perfect!", Toast.LENGTH_SHORT).show();
                            Button bt = findViewById(R.id.check);
                            bt.setText("Restart");
                        }
                    } else {
                        Toast.makeText(MainActivity.this, "All the blanks are not filled.", Toast.LENGTH_SHORT).show();
                    }
                }
                else
                {
                    btn.setText("Check");
                    createTable();
                }
            }
        });
    }
    public void ReadTextFile() throws IOException {
        String string = "";
        int i=0;
        InputStream is = this.getResources().openRawResource(R.raw.sudoku_list);
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        while (true) {
            try {
                if ((string = reader.readLine()) == null) break;
            }
            catch (IOException e) {
                e.printStackTrace();
            }
            allsudokulist.add(string);
        }
        is.close();
    }
    MediaPlayer media;
    public void playTapSound()
    {
        media= MediaPlayer.create(MainActivity.this, R.raw.tap);
        media.start();
        media.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            public void onCompletion(MediaPlayer mp) {
                mp.reset();
                mp.release();
            }
        });
    }

    @Override
    protected void onPause(){
        super.onPause();
        music.pause();
    }

    @Override
    protected void onResume(){
        super.onResume();
        music.start();
    }
}