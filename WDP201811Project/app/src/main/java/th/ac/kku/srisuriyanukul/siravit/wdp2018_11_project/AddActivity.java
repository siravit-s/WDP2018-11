package th.ac.kku.srisuriyanukul.siravit.wdp2018_11_project;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class AddActivity extends AppCompatActivity {

    EditText name, date, place, phone;
    Button addButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        addButton = findViewById(R.id.submitBut);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    public void finish(){
        Intent intent = new Intent();
        name = findViewById(R.id.nameEvent);
        date = findViewById(R.id.dateEvent);
        phone = findViewById(R.id.phoneEvent);
        place = findViewById(R.id.placeEvent);

        intent.putExtra("returnName", name.getText().toString());
        intent.putExtra("returnDate", date.getText().toString());
        intent.putExtra("returnPhone", phone.getText().toString());
        intent.putExtra("returnPlace", place.getText().toString());
        setResult(RESULT_OK, intent);
        super.finish();
    }
}
