package angio102.filmmanager;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class FilmDetails extends Activity {

    Button stopPlaying;
    TextView filmNameDet, filmDateDet, filmFileDet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.film_details_layout);

        stopPlaying = findViewById(R.id.stop_service_button);
        filmNameDet = findViewById(R.id.film_name_details);
        filmDateDet = findViewById(R.id.date_released_details);
        filmFileDet = findViewById(R.id.file_name_details);

        stopPlaying.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent s = new Intent(FilmDetails.this, FilmList.class);
                s.putExtra("stop","stop");
                stopService(s);
                Toast.makeText(FilmDetails.this,"Service Stopped!",Toast.LENGTH_SHORT).show();
                startActivity(s);

            }
        });
        Intent i = getIntent();

        filmNameDet.setText(i.getStringExtra("name"));
        filmDateDet.setText(i.getStringExtra("date"));
        filmFileDet.setText(i.getStringExtra("file"));
    }
}
