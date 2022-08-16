package com.example.door_open;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SqlDbFunction {

    Connection connect;
    String ConnectionResult = "";
    Boolean isSuccess = false;
    public static String shop_id;

    public String GetShopName() {
        String ShopName = "";

        try {

            ConnectionHelper connectionHelper = new ConnectionHelper();
            connect = connectionHelper.connectionclass();
            if (connect != null) {

                String qu = "Select top 1 STUDY_CAFE_ID, STUDY_CAFE_NAME, MIN_PREPAID, MINIMUM_MIN, PHONE_NUMBER, SEAT_SIZE, OWNER_NAME, " +
                        "BUSINESS_ID, BUSINESS_NUMBER, ADDRESS, FLOORS, IMP, TARIFF_ROW, RECEIPT_MSG, BARCODE, START_SMS, END_SMS, RESERVATION, USER_ID from STUDY_CAFE_TB " +
                        "where STUDY_CAFE_ID = '" + shop_id + "'";
                Statement statement = connect.createStatement();
                ResultSet resultSet = statement.executeQuery(qu);

                while (resultSet.next()) {
                    ShopName = resultSet.getString("STUDY_CAFE_NAME");
                }

                ConnectionResult = "Success";
                isSuccess = true;
                connect.close();
            }
            else {
                ConnectionResult = "Failed";
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        return ShopName;
    }

    /*
    public List<Map<String, String>> getlist() {

        List<Map<String, String>> data = null;
        data = new ArrayList<Map<String, String>>();
        try {

            ConnectionHelper connectionHelper = new ConnectionHelper();
            connect = connectionHelper.connectionclass();
            if (connect != null) {
                String qu = "Select top 1 SHOP_ID, NAME, GROUP_ID, DOOR, PASS, DOOR_OPEN, DOOR_CLOSE from SHOP_TB from SHOP_TB " +
                        "where SHOP_ID = " + shop_id;
                Statement statement = connect.createStatement();
                ResultSet resultSet = statement.executeQuery(qu);

                while (resultSet.next()) {
                    Map<String, String> dtname = new HashMap<String, String>();
                    dtname.put("MS_SHOP_NAME", resultSet.getString("NAME"));
                    dtname.put("MS_SHOP_ID", resultSet.getString("SHOP_ID"));
                    dtname.put("MS_DOOR", resultSet.getString("DOOR"));
                    data.add(dtname);
                }
                ConnectionResult = "Success";
                isSuccess = true;
                connect.close();
            }
            else {
                ConnectionResult = "Failed";
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return data;
    }
    */

    //가입 시에 사용하는 메서드
    public void InsertList(String ID, String Pass) {

        int num = ReturnNum();
        List<Map<String, String>> data = null;
        data = new ArrayList<Map<String, String>>();
        try {

            ConnectionHelper connectionHelper = new ConnectionHelper();
            connect = connectionHelper.connectionclass();
            if (connect != null) {

                String qu = "Insert into APP_MEMBER_TB values (" + "'" + ID + "', '" + Pass + "', NULL)";
                Statement statement = connect.createStatement();
                statement.executeUpdate(qu);

                ConnectionResult = "Success";
                isSuccess = true;
                connect.close();
            }
            else {
                ConnectionResult = "Failed";
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }


    public boolean LoginFunc(String ID, String Pass) {

        boolean loginable = false;
        String msId = "";
        String msPass = "";

        try {

            ConnectionHelper connectionHelper = new ConnectionHelper();
            connect = connectionHelper.connectionclass();
            if (connect != null) {
                String qu = "Select APP_ID, APP_PW, SHOP_ID from APP_MEMBER_TB";
                Statement statement = connect.createStatement();
                ResultSet resultSet = statement.executeQuery(qu);

                while (resultSet.next()) {
                    msId = resultSet.getString("APP_ID");
                    msPass = resultSet.getString("APP_PW");
                    msId = msId.replace(" ", "");
                    msPass = msPass.replace(" ", "");
                    //입력한 아이디, 패스워드에 공백 제거

                    if (msId.equals(ID) && msPass.equals(Pass))
                        break;
                    //아이디, 패스워드 모두가 동일한 DB 데이터가 있다면 반복 끝
                }

                if (ID.equals(msId) && Pass.equals(msPass)) {
                    loginable = true;
                    shop_id = resultSet.getString("SHOP_ID");
                }
                else {
                    loginable = false;
                }

                ConnectionResult = "Success";
                isSuccess = true;
                connect.close();
            }
            else {
                ConnectionResult = "Failed";
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return loginable;
    }

    public int ReturnNum() {
        int num = 0;
        int[] arrayNum;
        int count = 0;
        int maxNum = 0;

        try {

            ConnectionHelper connectionHelper = new ConnectionHelper();
            connect = connectionHelper.connectionclass();
            if (connect != null) {
                String qu = "Select COUNT(*) as cnt from TEST_TABLE";
                Statement statement = connect.createStatement();
                ResultSet resultSet = statement.executeQuery(qu);

                while (resultSet.next()) {
                    num = resultSet.getInt("cnt");
                }

                arrayNum = new int[num];

                qu = "Select NUM from TEST_TABLE";
                statement = connect.createStatement();
                resultSet = statement.executeQuery(qu);

                while (resultSet.next()) {
                    arrayNum[count] = resultSet.getInt("NUM");
                    count++;
                }
                Arrays.sort(arrayNum);

                maxNum = arrayNum[arrayNum.length - 1];

                ConnectionResult = "Success";
                isSuccess = true;
                connect.close();
            }
            else {
                ConnectionResult = "Failed";
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        return maxNum;
    }

    //로그인한 스터디카페의 총 층수를 받는 메서드
    public int ReturnFloorCount(String id) {
        int num = 0;

        try {
            ConnectionHelper connectionHelper = new ConnectionHelper();
            connect = connectionHelper.connectionclass();
            if (connect != null) {
                String qu = "Select COUNT(*) as cnt from REMOTE_DOOR_TB where STUDY_CAFE_ID = '" + id + "'";
                Statement statement = connect.createStatement();
                ResultSet resultSet = statement.executeQuery(qu);

                while (resultSet.next()) {
                    num = resultSet.getInt("cnt");
                }

                ConnectionResult = "Success";
                isSuccess = true;
                connect.close();
            }
            else {
                ConnectionResult = "Failed";
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        return num;
    }

    //1층 열기
    public void OpenDoor1(String name, String id) {

            try {

                ConnectionHelper connectionHelper = new ConnectionHelper();
                connect = connectionHelper.connectionclass();
                if (connect != null) {

                    String qu = "update REMOTE_DOOR_TB SET DOOR = 'T' where STUDY_CAFE_NAME = " + "N'" + name + "' and " + "FLOOR = '1' and STUDY_CAFE_ID = '" + shop_id + "'";
                    Statement statement = connect.createStatement();
                    statement.executeUpdate(qu);

                    ConnectionResult = "Success";
                    isSuccess = true;
                    connect.close();
                }
                else {
                    ConnectionResult = "Failed";
                }
            }
            catch (Exception e) {
                e.printStackTrace();
            }
    }

    //2층 열기
    public void OpenDoor2(String name, String id) {

        try {

            ConnectionHelper connectionHelper = new ConnectionHelper();
            connect = connectionHelper.connectionclass();
            if (connect != null) {

                String qu = "update REMOTE_DOOR_TB SET DOOR = 'T' where STUDY_CAFE_NAME = " + "N'" + name + "' and " + "FLOOR = '2' and STUDY_CAFE_ID = '" + shop_id + "'";
                Statement statement = connect.createStatement();
                statement.executeUpdate(qu);

                ConnectionResult = "Success";
                isSuccess = true;
                connect.close();
            }
            else {
                ConnectionResult = "Failed";
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    //프로그램 실행여부를 확인하는 메서드
    public boolean ReturnDoorNullable(String name, String shop_id, int floor) {

        boolean nullable = false;
        String door = "";
        try {

            ConnectionHelper connectionHelper = new ConnectionHelper();
            connect = connectionHelper.connectionclass();
            if (connect != null) {
                String qu = "Select DOOR from REMOTE_DOOR_TB where STUDY_CAFE_ID = '" + shop_id + "' and FLOOR = " + floor;
                Statement statement = connect.createStatement();
                ResultSet resultSet = statement.executeQuery(qu);

                while (resultSet.next()) {
                    door = resultSet.getString("DOOR");
                }

                if (door == null) {
                    nullable = true;
                }
                else {
                    nullable = false;
                }

                ConnectionResult = "Success";
                isSuccess = true;
                connect.close();
            }
            else {
                ConnectionResult = "Failed";
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        return nullable;
    }
}
