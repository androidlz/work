package com.seventeenok.test.UI.custom;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import com.seventeenok.test.R;

import java.text.SimpleDateFormat;
import java.util.Date;


public class CustomActivity extends AppCompatActivity {

    private TextView tv1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom);
        tv1 = (TextView) findViewById(R.id.tv1);
//        tv1.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                ViewCompat.offsetTopAndBottom(v, 5);
//            }
//        });
        tv1.setText(Greetings());
    }


    public void click(View view) {

    }

    public String Greetings() {
        Date date = new Date(System.currentTimeMillis());
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm:ss");
        String format = simpleDateFormat.format(date);
        String[] split = format.split(":");
        int minuteOfDay = Integer.parseInt(split[0]) * 60 + Integer.parseInt(split[1]);
//        minuteOfDay.logE("当前--minuteOfDay--")
//凌晨
//        Calendar cal = Calendar.getInstance();// 当前日期
//        int hour = cal.get(Calendar.HOUR_OF_DAY);// 获取小时
//        int minute = cal.get(Calendar.MINUTE);// 获取分钟
//        int minuteOfDay = hour * 60 + minute;// 从0:00分开是到目前为止的分钟数
        //凌晨
        int dawnStart = 0 * 60 + 0;// 00:00-05:59：凌晨有我相伴
        int dawnEnd = 5 * 60 + 59;
        //上午
        int morningStart = 6 * 60 + 0;// 06:00-11:59：Hi，上午好
        int morningEnd = 11 * 60 + 59;
        //下午
        int afternoonStart = 12 * 60 + 0;// 12:00-18:59：Hi，下午好
        int afternoonEnd = 18 * 60 + 59;
        //晚上
        int nightStart = 19 * 60 + 0;// 19:00-23:00：Hi，晚上好
        int nightEnd = 23 * 60 + 0;
        if (minuteOfDay > dawnStart && minuteOfDay < dawnEnd) {
            return "凌晨有我相伴";
        } else if (minuteOfDay > dawnStart && minuteOfDay < morningEnd) {
            return "Hi，上午好";
        } else if (minuteOfDay > afternoonStart && minuteOfDay < afternoonEnd) {
            return "Hi，下午好";
        } else if (minuteOfDay > nightStart && minuteOfDay < nightEnd) {
            return "Hi，晚上好";
        }
        return "";


    }
}

