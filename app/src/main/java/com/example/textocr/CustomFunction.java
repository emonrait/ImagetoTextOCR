package com.example.textocr;

import android.util.Log;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class CustomFunction {

    public static String dateParse(String dateStr) {
        int year = 0;
        int month = 0;
        int day = 0;
        String month1 = null;
        String day1 = null;
        try {
            String lastTwo = null;
            if (dateStr != null && dateStr.length() >= 8) {
                lastTwo = dateStr.substring(dateStr.length() - 8);
            }
            //String dateStr = "01Jun1988";
            DateFormat formatter = new SimpleDateFormat("ddMMMyyyy");
            Date date = (Date) formatter.parse(lastTwo);

            //Log.e("formatedDate : " , date.toString());

            Calendar cal = Calendar.getInstance();
            cal.setTime(date);
            year = cal.get(Calendar.YEAR);
            month = cal.get(Calendar.MONTH);
            day = cal.get(Calendar.DAY_OF_MONTH);

            if (month < 9) {
                month1 = "0" + (month + 1);
            } else {
                month1 = month + 1 + "";
            }
            if (day <= 9) {
                day1 = "0" + day;
            } else {
                day1 = day + "";
            }
            Log.e("formatedDate ", day1 + "/" + month1 + "/" + year);

        } catch (Exception e) {
            e.printStackTrace();
            Log.e("Errortag", e.getMessage().toString());
        }

        return day1 + "/" + month1 + "/" + year;
    }

    public static boolean isValidDate(String inDate) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        dateFormat.setLenient(false);
        try {
            dateFormat.parse(inDate.trim());
        } catch (ParseException pe) {
            return false;
        }
        return true;
    }

}
