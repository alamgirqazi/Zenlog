package io.github.alamgirqazi.zenlog;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;

import static io.github.alamgirqazi.zenlog.R.id.btnGithub;

public class AnimationActivity extends AppCompatActivity implements View.OnClickListener

{

    private Button btnGithub;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_animation);


        ImageView img = (ImageView)findViewById(R.id.imageView);
        Animation animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.animation_2);
        img.setAnimation(animation);

        btnGithub= (Button) findViewById(R.id.btnGithub);
        btnGithub.setOnClickListener(this);



        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                //When you want delay some time
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                //When animate finish, load main activity
//                finish();
//                startActivity(new Intent(getApplicationContext(), ProfileActivity.class));
//
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }

    @Override
    public void onClick(View v) {
        if (v == btnGithub) {
            //Open Login Activity
            startActivity(new Intent(this, GithubActivity.class));
    }
}}