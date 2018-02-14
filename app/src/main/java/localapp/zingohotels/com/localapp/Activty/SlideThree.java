package localapp.zingohotels.com.localapp.Activty;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import localapp.zingohotels.com.localapp.Login.LoginNew;
import localapp.zingohotels.com.localapp.R;

public class SlideThree extends AppCompatActivity {

    Button mSkip,mNext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_slide_three);

        mSkip = (Button) findViewById(R.id.skipBtn);

        mSkip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent login = new Intent(SlideThree.this,LoginNew.class);
                startActivity(login);
            }
        });




    }
}
