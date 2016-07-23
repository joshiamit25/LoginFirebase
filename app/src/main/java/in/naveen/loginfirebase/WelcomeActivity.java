package in.naveen.loginfirebase;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;


public class WelcomeActivity extends AppCompatActivity {
    ImageView iw;
    TextView tv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        Bundle bundle = getIntent().getExtras();
        final String name = bundle.getString("usernamekey");
        tv = (TextView) findViewById(R.id.textView);
        tv.setVisibility(View.INVISIBLE);
        iw = (ImageView) findViewById(R.id.welcomeimage);

        Animation an = AnimationUtils.loadAnimation(getApplication(), R.anim.fadein);
        iw.startAnimation(an);

        an.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                tv.setText("Welcome To App \n" +name);
                Animation an2 = AnimationUtils.loadAnimation(getApplication(), R.anim.fadein);

                tv.startAnimation(an2);
                tv.setVisibility(View.VISIBLE);

            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }
}
