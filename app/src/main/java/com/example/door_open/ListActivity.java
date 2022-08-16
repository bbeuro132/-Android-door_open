package com.example.door_open;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.Connection;
import java.util.List;
import java.util.Map;

public class ListActivity extends AppCompatActivity {

    SimpleAdapter ad;
    Connection connect;
    String ConnectionResult = "";
    Boolean isSuccess = false;
    Boolean isNullable = false; //false = 비어있지 않음, 열 수 있다는 뜻
    Button visibleBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        NameUpdate();
        FloorBtnUpdate();
    }

    public void ExitBtn(View v) {
        finish();
    }

    ListView lstv;
    TextView textView;

    public void NameUpdate() {
        textView = (TextView) findViewById(R.id.ShopNameTextView);

        SqlDbFunction Mydata = new SqlDbFunction();
        textView.setText(Mydata.GetShopName());

        String name = Mydata.GetShopName();
        String shopID = SqlDbFunction.shop_id;

        //Toast.makeText(ListActivity.this, shopID + ", " + name, Toast.LENGTH_LONG).show();
    }

    public void FloorBtnUpdate() {
        SqlDbFunction Mydata = new SqlDbFunction();
        visibleBtn = findViewById(R.id.OpenDoorBtn2);

        String name = Mydata.GetShopName();
        String shopID = SqlDbFunction.shop_id;
        int floor = 0;

        floor = Mydata.ReturnFloorCount(shopID);

        if (floor == 1) {
            visibleBtn.setVisibility(visibleBtn.INVISIBLE);
        }
        else {
            visibleBtn.setVisibility(visibleBtn.VISIBLE);
        }
    }

    public void OpenDoorBtn1_Click(View v) {
        List<Map<String, String>> Mydatalist = null;
        SqlDbFunction Mydata = new SqlDbFunction();

        String name = Mydata.GetShopName();
        String shopID = SqlDbFunction.shop_id;
        isNullable = Mydata.ReturnDoorNullable(name, shopID, 1);

        if (isNullable == true) {
            Toast.makeText(ListActivity.this, "프로그램이 실행되어있지 않습니다.", Toast.LENGTH_LONG).show();
        }
        else {
            AlertDialog.Builder builder = new AlertDialog.Builder(ListActivity.this);
            builder.setMessage("1층 문을 여시겠습니까?");
            builder.setTitle("변경 팝업")
                    .setCancelable(false)
                    .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            Mydata.OpenDoor1(name, shopID);
                            //Toast.makeText(ListActivity.this, shopID + ", " + name, Toast.LENGTH_LONG).show();
                        }
                    })
                    .setNegativeButton("닫기", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.cancel();
                        }
                    });
            builder.show();
        }
    }

    public void OpenDoorBtn2_Click(View v) {
        List<Map<String, String>> Mydatalist = null;
        SqlDbFunction Mydata = new SqlDbFunction();

        String name = Mydata.GetShopName();
        String shopID = SqlDbFunction.shop_id;
        isNullable = Mydata.ReturnDoorNullable(name, shopID, 2);

        if (isNullable == true) {
            Toast.makeText(ListActivity.this, "프로그램이 실행되어있지 않습니다.", Toast.LENGTH_LONG).show();
        }
        else {
            AlertDialog.Builder builder = new AlertDialog.Builder(ListActivity.this);
            builder.setMessage("2층 문을 여시겠습니까?");
            builder.setTitle("변경 팝업")
                    .setCancelable(false)
                    .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            Mydata.OpenDoor2(name, shopID);
                            //Toast.makeText(ListActivity.this, shopID + ", " + name, Toast.LENGTH_LONG).show();
                        }
                    })
                    .setNegativeButton("닫기", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.cancel();
                        }
                    });
            builder.show();
        }
    }

}