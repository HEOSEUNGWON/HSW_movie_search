package com.example.searchactivity;

import static com.example.searchactivity.MainActivity.mHistoryReSearchFlag;
import static com.example.searchactivity.MainActivity.mHistoryReSearchName;

import androidx.room.Room;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;
import java.util.List;

public class HistoryActivity extends Activity {
    private UserRepository historyRepository;
    Button btHistoryMovie1;
    Button btHistoryMovie2;
    Button btHistoryMovie3;
    Button btHistoryMovie4;
    Button btHistoryMovie5;
    Button btHistoryMovie6;
    Button btHistoryMovie7;
    Button btHistoryMovie8;
    Button btHistoryMovie9;
    Button btHistoryMovie10;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        AppDatabase db= Room.databaseBuilder(getApplicationContext(),AppDatabase.class,"db-mary")
                .fallbackToDestructiveMigration() //스키마 버전 변경 가능
                .allowMainThreadQueries() // 메인 스레드에서 DB에 IO를 가능하게 함
                .build();

        historyRepository = db.userRepository();


        List<SearchHistory> listHistory = historyRepository.findAll();

        List<String> strHistoryName = new ArrayList<>();

        btHistoryMovie1 = (Button) findViewById(R.id.button1_History);
        btHistoryMovie2 = (Button) findViewById(R.id.button2_History);
        btHistoryMovie3 = (Button) findViewById(R.id.button3_History);
        btHistoryMovie4 = (Button) findViewById(R.id.button4_History);
        btHistoryMovie5 = (Button) findViewById(R.id.button5_History);
        btHistoryMovie6 = (Button) findViewById(R.id.button6_History);
        btHistoryMovie7 = (Button) findViewById(R.id.button7_History);
        btHistoryMovie8 = (Button) findViewById(R.id.button8_History);
        btHistoryMovie9 = (Button) findViewById(R.id.button9_History);
        btHistoryMovie10 = (Button) findViewById(R.id.button10_History);

        setButtonInVisible();

        for(int i=0; i<listHistory.size(); i++) {
            strHistoryName.add(listHistory.get(i).getMovieName());
        }
        setHistoryButton(listHistory.size(), strHistoryName);
    }

    private void setButtonInVisible(){
        btHistoryMovie1.setVisibility(View.INVISIBLE);
        btHistoryMovie2.setVisibility(View.INVISIBLE);
        btHistoryMovie3.setVisibility(View.INVISIBLE);
        btHistoryMovie4.setVisibility(View.INVISIBLE);
        btHistoryMovie5.setVisibility(View.INVISIBLE);
        btHistoryMovie6.setVisibility(View.INVISIBLE);
        btHistoryMovie7.setVisibility(View.INVISIBLE);
        btHistoryMovie8.setVisibility(View.INVISIBLE);
        btHistoryMovie9.setVisibility(View.INVISIBLE);
        btHistoryMovie10.setVisibility(View.INVISIBLE);
    }

    private void setHistoryButton(int historyCount, List<String> strHistoryName){
        int currentCount = 0;
        if(currentCount == historyCount){
            return;
        }
        setButtonText(btHistoryMovie1, strHistoryName.get(currentCount));
        currentCount++;
        if(currentCount == historyCount){
            return;
        }
        setButtonText(btHistoryMovie2, strHistoryName.get(currentCount));
        currentCount++;
        if(currentCount == historyCount){
            return;
        }

        setButtonText(btHistoryMovie3, strHistoryName.get(currentCount));
        currentCount++;
        if(currentCount == historyCount){
            return;
        }

        setButtonText(btHistoryMovie4, strHistoryName.get(currentCount));
        currentCount++;
        if(currentCount == historyCount){
            return;
        }

        setButtonText(btHistoryMovie5, strHistoryName.get(currentCount));
        currentCount++;
        if(currentCount == historyCount){
            return;
        }

        setButtonText(btHistoryMovie6, strHistoryName.get(currentCount));
        currentCount++;
        if(currentCount == historyCount){
            return;
        }

        setButtonText(btHistoryMovie7, strHistoryName.get(currentCount));
        currentCount++;
        if(currentCount == historyCount){
            return;
        }

        setButtonText(btHistoryMovie8, strHistoryName.get(currentCount));
        currentCount++;
        if(currentCount == historyCount){
            return;
        }

        setButtonText(btHistoryMovie9, strHistoryName.get(currentCount));
        currentCount++;
        if(currentCount == historyCount){
            return;
        }

        setButtonText(btHistoryMovie10, strHistoryName.get(currentCount));
        currentCount++;
        if(currentCount == historyCount){
            return;
        }
        return;
    }
    private void setButtonText(Button historyButton, String strHistoryName){
        historyButton.setText(strHistoryName);
        historyButton.setVisibility(View.VISIBLE);

        historyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mHistoryReSearchName = historyButton.getText().toString();
                mHistoryReSearchFlag = true;
                finish();
            }
        });
        return;
    }
}
