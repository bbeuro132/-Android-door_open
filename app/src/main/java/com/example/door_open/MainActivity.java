package com.example.door_open;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    ImageView CreditClick;
    long delay = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /* 현재 미사용
        CreditClick = (ImageView) findViewById(R.id.CreditImage);
        CreditClick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (System.currentTimeMillis() > delay) {
                    //한번 클릭
                    delay = System.currentTimeMillis() + 300;
                    return;
                }
                if (System.currentTimeMillis() <= delay) {

                    AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                    builder.setMessage("최종 업데이트 : 2022-07-08\n\n스터디 카페 전용으로 교체\n불필요 버튼 삭제");
                    builder.setTitle("Credit")
                            .setCancelable(false)
                            .setNegativeButton("닫기", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    dialogInterface.cancel();
                                }
                            });
                    builder.show();
                }

            }
        });
        */

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


    public void GetList(View v) {

        EditText personIdEditText = (EditText) findViewById(R.id.IdEditText);
        EditText personPassEditText = (EditText) findViewById(R.id.PassEditText);

        boolean loginable;
        String ID = personIdEditText.getText().toString();
        String Pass = personPassEditText.getText().toString();


        List<Map<String, String>> Mydatalist = null;
        SqlDbFunction Mydata = new SqlDbFunction();

        loginable = Mydata.LoginFunc(ID, Pass);


        if (loginable) {
            ID = "";
            Pass = "";
            Intent intent = new Intent(getApplicationContext(), ListActivity.class);
            startActivity(intent);
            Toast.makeText(MainActivity.this, "로그인 성공", Toast.LENGTH_LONG).show();
            personIdEditText.setText(null);
            personPassEditText.setText(null);
        }
        else {
            ID = "";
            Pass = "";
            Toast.makeText(MainActivity.this, "입력한 정보를 다시 확인해주세요.", Toast.LENGTH_LONG).show();
        }
    }


    public void ExitBtn(View v) {
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setMessage("종료하시겠습니까?");
        builder.setTitle("종료 알림")
                .setCancelable(false)
                .setPositiveButton("예", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        moveTaskToBack(true);
                        finishAndRemoveTask();
                        android.os.Process.killProcess(android.os.Process.myPid());
                    }
                })
                .setNegativeButton("아니오", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                });
        AlertDialog alert = builder.create();
        alert.setTitle("종료 알림");
        alert.show();
    }


    public void RegisterEnterBtn(View v) {
        Intent intent = new Intent(getApplicationContext(), RegisterActivity.class);
        startActivity(intent);
    }


    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setMessage("종료하시겠습니까?");
        builder.setTitle("종료 알림")
                .setCancelable(false)
                .setPositiveButton("예", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        moveTaskToBack(true);
                        finishAndRemoveTask();
                        android.os.Process.killProcess(android.os.Process.myPid());
                    }
                })
                .setNegativeButton("아니오", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                });
        AlertDialog alert = builder.create();
        alert.setTitle("종료 알림");
        alert.show();
    }
}