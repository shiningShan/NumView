package com.example.liushan.dropgooddemo;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewPropertyAnimator;
import android.view.animation.AnimationSet;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    NumberView numberView;
    int translationY = 0;
    private   EditText et;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        et = (EditText)findViewById(R.id.getNumEt);
        Button btnGetNum = (Button)findViewById(R.id.getNumBtn);
        btnGetNum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String num = et.getText().toString();
                numberView.setNum(Integer.parseInt(num));
            }
        });

        numberView = (NumberView)findViewById(R.id.number_view);

        numberView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.v("ls","onClick");
                translationY = translationY +200;
                numberView.startAnim(1);

              //  ViewPropertyAnimator animator2 = numberView.animate().alpha(0);
//                AnimatorSet animatorSet = new AnimatorSet();
//                animatorSet.playTogether(animator1,animator2);

            }
        });
        Button btnPlus = (Button)findViewById(R.id.plus);
        btnPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                numberView.startAnim(1);
            }
        });

        Button btnMinus = (Button)findViewById(R.id.minus);
        btnMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                numberView.startAnim(0);
            }
        });
    }


}
