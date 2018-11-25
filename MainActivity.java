package com.example.junt.dialogfragmentdemo;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;

public class MainActivity extends AppCompatActivity {

    private DialogUtil dialog;
    private DialogUtil.Builder builder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        builder = new DialogUtil
                .Builder(MainActivity.this)
                .View(R.layout.dialog)//必须首先设置该项
                .style(R.style.Dialog)//设置窗体Style，背景透明等
//                .sizeScale(0.8f,1.0f)//宽度根据屏宽进行缩放，高度基于Dialog宽度进行调整
                .size(-1, -2)//设置Dialog宽高为固定值时Dialog的布局中宽高将无效(WrapContent无效),-2代表WrapContent，-1代表MatchParent
//                .position(0, 0, Gravity.TOP | Gravity.LEFT)//设置Dialog显示位置，0代表不设置，Gravity必须设置，否则坐标无效
//                .setBackgroundColor(Color.TRANSPARENT)//设置Dialog中布局的背景为透明(颜色，shape，drawable都行)
//                .setBackgroundResource(R.drawable.shape_dialog)//shape中设置Solid为<solid android:color="@android:color/transparent"/>亦可达到全透明效果
                .setButtonText(R.id.btn, "", Color.GREEN, 15)//设置按钮字体，""代表内容不做改动
                .setText(R.id.tv,
                        "更换测试",
                        Color.parseColor("#000000"),
                        15,
                        Gravity.CENTER)//设置布局中TextView的文字，颜色，大小，位置，填0为系统默认值
                .setCanceledOnTouchOutside(true)
                .setOnViewClick(R.id.btn, new View.OnClickListener() {//设置Dialog中的控件点击事件，适应一切控件
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });
        dialog = builder.build();//实例化Dialog对象
        dialog.show();

        new Handler().postDelayed(new Runnable() {
            @SuppressLint("SetTextI18n")
            @Override
            public void run() {
                Button button = (Button) builder.getWidget(R.id.btn);
                button.setText("getWidget");
            }
        }, 5000);
    }


}
