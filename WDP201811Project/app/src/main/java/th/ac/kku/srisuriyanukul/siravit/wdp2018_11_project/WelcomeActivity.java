package th.ac.kku.srisuriyanukul.siravit.wdp2018_11_project;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class WelcomeActivity extends AppCompatActivity {

    Button guess_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        guess_btn = findViewById(R.id.btn_guess);
        guess_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent go_main = new Intent(WelcomeActivity.this,MainActivity.class);
                startActivity(go_main);
            }
        });
    }
}
