package id.latiev.onlineregistration.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;

import com.github.paolorotolo.appintro.AppIntro2;
import com.github.paolorotolo.appintro.AppIntro2Fragment;

import id.latiev.onlineregistration.R;

public class IntroActivity extends AppIntro2 {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        addSlide(AppIntro2Fragment.newInstance(getResources().getString(R.string.title_intro_1), getResources().getString(R.string.desc_intro_1), R.drawable.intro1, ContextCompat.getColor(getBaseContext(), R.color.intro_1)));
        addSlide(AppIntro2Fragment.newInstance(getResources().getString(R.string.title_intro_2), getResources().getString(R.string.desc_intro_2), R.drawable.intro2, ContextCompat.getColor(getBaseContext(), R.color.intro_2)));
        addSlide(AppIntro2Fragment.newInstance(getResources().getString(R.string.title_intro_3), getResources().getString(R.string.desc_intro_3), R.drawable.intro3, ContextCompat.getColor(getBaseContext(), R.color.intro_3)));
        addSlide(AppIntro2Fragment.newInstance(getResources().getString(R.string.title_intro_4), getResources().getString(R.string.desc_intro_4), R.drawable.intro1, ContextCompat.getColor(getBaseContext(), R.color.intro_1)));

        showStatusBar(false);
        showSkipButton(false);

        setFlowAnimation();
    }

    @Override
    public void onSkipPressed(Fragment currentFragment) {
        super.onSkipPressed(currentFragment);
        startActivity(new Intent(IntroActivity.this, MainActivity.class));
        finish();
    }

    @Override
    public void onDonePressed(Fragment currentFragment) {
        super.onDonePressed(currentFragment);
        startActivity(new Intent(IntroActivity.this, MainActivity.class));
        finish();
    }
}
