package example.sirapob.testgoogle;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AddEventActivity extends AppCompatActivity {

    EditText title_et, desc_et, dateSt_et, dateEn_et, timeSt_et, timeEn_et, place_et, img_url ;
    Button submit_btn;
    DatabaseReference reff;
    Activities ac;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_event);

        title_et = findViewById(R.id.title_et);
        desc_et = findViewById(R.id.desc_et);
        dateSt_et = findViewById(R.id.dateSt_et);
        dateEn_et = findViewById(R.id.dateEn_et);
        timeSt_et = findViewById(R.id.timeSt_et);
        timeEn_et = findViewById(R.id.timeEn_et);
        place_et = findViewById(R.id.place_et);
        img_url = findViewById(R.id.img_et);
        submit_btn = findViewById(R.id.submit_btn);

        reff = FirebaseDatabase.getInstance().getReference().child("activities");

        ac = new Activities();

        submit_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title = title_et.getText().toString().trim();
                String desc = desc_et.getText().toString().trim();
                String dateSt = dateSt_et.getText().toString().trim();
                String dateEn = dateEn_et.getText().toString().trim();
                String timeSt = timeSt_et.getText().toString().trim();
                String timeEn = timeEn_et.getText().toString().trim();
                String place = place_et.getText().toString().trim();
                String img_Url = img_url.getText().toString().trim();

                ac.setEv_title(title);
                ac.setEv_desc(desc);
                ac.setEv_dateSt(dateSt);
                ac.setEv_dateEn(dateEn);
                ac.setEv_timeSt(timeSt);
                ac.setEv_timeEn(timeEn);
                ac.setEv_place(place);
                ac.setEv_imgUrl(img_Url);

                reff.push().setValue(ac);

                Intent main_intent = new Intent(AddEventActivity.this, MainPageActivity.class);
                startActivity(main_intent);
            }
        });
    }
}
