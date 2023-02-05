package com.example.searchactivity;

import static com.example.searchactivity.SearchMovieNaverAPI.responseBody;
import static com.example.searchactivity.SearchMovieNaverAPI.searchMovie;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    ArrayList<MovieData> movieDataList;
    public static StringBuilder sb;
    ListView movieListView;
    Button btSearchMovie;
    Button btHistorySearch;
    EditText tvMovieName;

    MovieAdapter myAdapter;
    int listMaxCount = 0;
    private int page = 0;                           // 페이징변수. 초기 값은 0 이다.
    private final int OFFSET = 20;                  // 한 페이지마다 로드할 데이터 갯수.
    private ProgressBar progressBar;
    private boolean mLockListView = false;

    private int currentPage = 0;
    private int preTotal = 0;
    private int totalPageCount = 0;
    private int pageItemCount = 10;
    private UserRepository historyRepository;

    static String mHistoryReSearchName = null;
    static boolean mHistoryReSearchFlag = false;

    Toast toast = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        movieDataList = new ArrayList<MovieData>();

        movieListView = (ListView)findViewById(R.id.listView_moviedata);
        myAdapter = new MovieAdapter(this, movieDataList);

        mLockListView = true;

        movieListView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView absListView, int i) {

            }

            @Override
            public void onScroll(AbsListView absListView, int firstItem, int visibleItemCount, int totalItemCount) {
                int count = totalItemCount - visibleItemCount;

                if(totalPageCount == 0){
                    return;
                }

                if(totalPageCount == currentPage){
                    if(toast != null)
                        toast.cancel();

                    toast = Toast.makeText(MainActivity.this,"마지막 페이지 입니다.",Toast.LENGTH_SHORT);
                    toast.show();
                } else if(firstItem >= count && totalItemCount != 0 && mLockListView == false) {
                    if(currentPage == 10){
                        if(toast != null)
                            toast.cancel();
                        toast = Toast.makeText(MainActivity.this,"최대 100개까지 검색 가능합니다.",Toast.LENGTH_SHORT);
                        toast.show();
                        return;
                    }
                    if(toast != null)
                        toast.cancel();
                    toast = Toast.makeText(MainActivity.this,"다음 페이지를 로딩중입니다.",Toast.LENGTH_SHORT);
                    toast.show();
                    makeMovieList(responseBody);
                    movieListView.setSelection((currentPage-1)*10);
                }
            }
        });
        movieListView.setAdapter(myAdapter);

        tvMovieName = (EditText) findViewById(R.id.textView_movieName);

        btSearchMovie = (Button) findViewById(R.id.button_search);
        btHistorySearch = (Button) findViewById(R.id.button_history);

        AppDatabase db= Room.databaseBuilder(getApplicationContext(),AppDatabase.class,"db-mary")
                .fallbackToDestructiveMigration() //스키마 버전 변경 가능
                .allowMainThreadQueries() // 메인 스레드에서 DB에 IO를 가능하게 함
                .build();

        historyRepository = db.userRepository();


        listMaxCount = 10;

        btSearchMovie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String strMovieName = tvMovieName.getText().toString();

                searchNaverAPI(strMovieName);
            }
        });



        btHistorySearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,HistoryActivity.class);
                startActivity(intent);
            }
        });

    }

    private void searchNaverAPI(String searchMovieName){
        currentPage = 0;
        totalPageCount = 0;

        SearchMovieNaverAPI thread = new SearchMovieNaverAPI(searchMovieName, listMaxCount);
        thread.start();

        try{
            thread.join();
            makeMovieList(responseBody);
        } catch (InterruptedException e){
            e.printStackTrace();
        }

        List<SearchHistory> listHistory = historyRepository.findAll();
        System.out.println("DebugPoint ListHistory : " + listHistory);
        if(listHistory != null) {
            if (listHistory.size() >= 10) {
                System.out.println("DebugPoint listHistory.size() : " + listHistory.size());
                SearchHistory hDeleteMovieName = historyRepository.findByName(listHistory.get(0).getMovieName());
                System.out.println("DebugPoint hDeleteMovieName : " + hDeleteMovieName);
                System.out.println("DebugPoint listHistory.get(0).toString() : " + listHistory.get(0).getMovieName());
                System.out.println("DebugPoint hDeleteMovieName.getMovieName() : " + hDeleteMovieName.getMovieName());
                historyRepository.delete(hDeleteMovieName);
            }
        }
        SearchHistory hMovieName = new SearchHistory(searchMovieName);
        historyRepository.insert(hMovieName);
    }

    private void makeMovieList(String responseMovieData){
        if(responseMovieData == null){
            return;
        }
        String title;
        JSONObject jsonObject = null;

        mLockListView = true;

        movieDataList = new ArrayList<MovieData>();

        try {
            jsonObject = new JSONObject(responseMovieData);

            int totalCount  = jsonObject.getInt("total");
            totalPageCount = totalCount / pageItemCount;
            if(totalCount % pageItemCount != 0)
                totalPageCount++;

            JSONArray jsonArray = jsonObject.getJSONArray("items");
            for (int i = 0; i < ((currentPage * pageItemCount) + pageItemCount); i++) {
                if(jsonArray.length() < i){
                    break;
                }
                JSONObject item = jsonArray.getJSONObject(i);
                String adaptPoster = item.getString("image");

                String adaptName = item.getString("title");
                adaptName = adaptName.replace("</b>","");
                adaptName = adaptName.replace("<b>","");

                String adaptRelease = item.getString("pubDate");
                double adaptGrade = item.getDouble("userRating");
                String adaptURL = item.getString("link");

                System.out.println("image : " + adaptPoster);
                System.out.println("title : " + adaptName);
                System.out.println("pubDate : " + adaptRelease);
                System.out.println("userRating : " + adaptGrade);
                System.out.println("userRating : " + adaptURL);

                movieDataList.add(new MovieData(adaptPoster, adaptName, adaptGrade, adaptRelease, adaptURL));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        myAdapter = new MovieAdapter(this, movieDataList);
        movieListView.setAdapter(myAdapter);
        //myAdapter.notifyDataSetChanged();


        currentPage = currentPage + 1;
        mLockListView = false;
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(mHistoryReSearchFlag == true){
            searchNaverAPI(mHistoryReSearchName);
            tvMovieName.setText(mHistoryReSearchName);
            mHistoryReSearchFlag = false;
        }
    }
}