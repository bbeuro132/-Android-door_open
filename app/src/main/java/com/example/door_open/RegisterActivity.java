package com.example.door_open;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Rect;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

public class RegisterActivity extends AppCompatActivity {

    ListActivity lstact = new ListActivity();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
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

    public void InsertBtn_Click(View v) {

        EditText personNameEditText = (EditText) findViewById(R.id.IdForRegister);
        EditText personAgeEditText = (EditText) findViewById(R.id.PassForRegister);

        String id = personNameEditText.getText().toString();
        String pass = personAgeEditText.getText().toString();


        SqlDbFunction lst = new SqlDbFunction();
        lst.InsertList(id, pass);
        finish();

    }
}