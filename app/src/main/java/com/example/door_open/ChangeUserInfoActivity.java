package com.example.door_open;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Rect;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

public class ChangeUserInfoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_user_info);
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

    public void ChangePassBtn_Click(View v) {
        EditText presentIdEditText = (EditText) findViewById(R.id.IdCheckEditText);
        EditText presentPassEditText = (EditText) findViewById(R.id.PassCheckEditText);
        EditText changePassEditText = (EditText) findViewById(R.id.ChangePass);

        String id = presentIdEditText.getText().toString();
        String pass = presentPassEditText.getText().toString();
        String changePass = changePassEditText.getText().toString();

        SqlDbFunction sql = new SqlDbFunction();
        sql.ChangePass(id, pass, changePass);
        finish();
    }
}