package localapp.zingohotels.com.localapp.Activty;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import localapp.zingohotels.com.localapp.Login.LoginNew;
import localapp.zingohotels.com.localapp.R;

public class SlideOne extends AppCompatActivity {

    Button mSkip,mNext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_slide_one);

        mSkip = (Button) findViewById(R.id.skipBtn);
        mNext = (Button) findViewById(R.id.nextBtn);

        mSkip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent login = new Intent(SlideOne.this,LoginNew.class);
                startActivity(login);
            }
        });

        mNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent next = new Intent(SlideOne.this,SlideTwo.class);
                startActivity(next);
            }
        });
    }
}
