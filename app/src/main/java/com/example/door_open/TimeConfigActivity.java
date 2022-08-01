package com.example.door_open;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Rect;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class TimeConfigActivity extends AppCompatActivity {

    Spinner spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time_config);
        WeekSpinnerActivate();
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        View focusView = getCurrentFocus();
        if (focusView != null) {
            Rect rect = new Rect();
            focusView.getGlobalVisibleRect(rect);
            int x = (int) ev.getX(), y = (int) ev.getY();
            if (!rect.contains(x, y)) {
                InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                if (imm != null)
                    imm.hideSoftInputFromWindow(focusView.getWindowToken(), 0);
                focusView.clearFocus();
            }
        }
        return super.dispatchTouchEvent(ev);
    }

    public void ExitBtn(View v) {
        finish();
    }


    public void AcceptBtn_Click(View v) {
        EditText OpenTimeEditText = (EditText) findViewById(R.id.OpenTimeEditText);
        EditText CloseTimeEditText = (EditText) findViewById(R.id.CloseTimeEditText);
        Spinner weekSpinner = (Spinner) findViewById(R.id.weekSpinner);

        String open = OpenTimeEditText.getText().toString();
        String close = CloseTimeEditText.getText().toString();
        String weekText = weekSpinner.getSelectedItem().toString();

        weekText = WeekNameChange(weekText);

        //Toast.makeText(TimeConfigActivity.this, "'" + open + "', '" + close + "'", Toast.LENGTH_LONG).show();

        SqlDbFunction sql = new SqlDbFunction();
        sql.UpdateTime(open, close, weekText);
        finish();

    }

    public void WeekSpinnerActivate() {
        spinner = findViewById(R.id.weekSpinner);

        ArrayAdapter weekAdapter = ArrayAdapter.createFromResource(this, R.array.week_array, R.layout.spinner_text);
        weekAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setPrompt("요일을 선택해주세요.");
        spinner.setAdapter(weekAdapter);
    }

    public String WeekNameChange(String week) {
        String changedName = "";

        switch (week) {
            case "월요일" :
                changedName = "Monday";
                break;
            case "화요일" :
                changedName = "Tuesday";
                break;
            case "수요일" :
                changedName = "Wednesday";
                break;
            case "목요일" :
                changedName = "Thursday";
                break;
            case "금요일" :
                changedName = "Friday";
                break;
            case "토요일" :
                changedName = "Saturday";
                break;
            case "일요일" :
                changedName = "Sunday";
                break;
        }

        return changedName;
    }

}