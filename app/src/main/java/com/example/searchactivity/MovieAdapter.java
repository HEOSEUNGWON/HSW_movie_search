package com.example.searchactivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;;

public class MovieAdapter extends BaseAdapter {

    private Context mContext = null;
    private LayoutInflater mLayoutInflater = null;
    private ArrayList<MovieData> movie;
    private Bitmap bitmap;

    public MovieAdapter(Context context, ArrayList<MovieData> data) {
        mContext = context;
        movie = data;
        mLayoutInflater = LayoutInflater.from(mContext);
    }

    @Override
    public int getCount() {
        return movie.size();
    }

    @Override
    public MovieData getItem(int i) {
        return movie.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View listView = mLayoutInflater.inflate(R.layout.list_movie, null);

        ImageView imageView = (ImageView)listView.findViewById(R.id.imageView_poster);
        TextView movieName = (TextView)listView.findViewById(R.id.textView_movie_name);
        TextView release = (TextView)listView.findViewById(R.id.textView_release);
        TextView grade = (TextView)listView.findViewById(R.id.textView_grade);

        Thread mURLThread = new Thread(){
            @Override
            public void run(){
                try{
                    URL url = new URL(movie.get(i).getPoster());

                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setDoInput(true);
                    conn.connect();

                    InputStream is = conn.getInputStream();
                    bitmap = BitmapFactory.decodeStream(is);
                } catch(MalformedURLException e) {
                    e.printStackTrace();
                } catch(IOException e) {
                    e.printStackTrace();
                }
            }
        };
        mURLThread.start();

        try{
            mURLThread.join();
            imageView.setImageBitmap(bitmap);
        } catch (InterruptedException e){
            e.printStackTrace();
        }

        movieName.setText("제목 : " + movie.get(i).getMovieName());
        release.setText("출시 : " + movie.get(i).getRelease());
        grade.setText("평점 : " + movie.get(i).getGrade());

        LinearLayout movieArea = (LinearLayout)listView.findViewById(R.id.linearLayout_movieArea);
        movieArea.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(movie.get(i).getMovieURL()));
                mContext.startActivity(browserIntent);
            }
        });


        return listView;
    }
}
