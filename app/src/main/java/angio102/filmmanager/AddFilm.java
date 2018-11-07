package angio102.filmmanager;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class AddFilm extends Activity {
    //global variables
    Button submit;
    EditText filmName, filmDate, fileName;
    DatabaseHelper myDB;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_film_layout);
        //binding the Java to the XML
        submit = findViewById(R.id.add_film_button);
        filmName = findViewById(R.id.film_name_input);
        filmDate = findViewById(R.id.date_released_input);
        fileName = findViewById(R.id.file_name_input);

        myDB = new DatabaseHelper(this);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String newEntry = filmName.getText().toString();
                String newEntry2 =filmDate.getText().toString();
                String newEntry3 = fileName.getText().toString();

                if (filmName.length() == 0 || filmDate.length() == 0 || fileName.length() == 0){
                    Toast.makeText(AddFilm.this, "You must enter all parameters!", Toast.LENGTH_SHORT).show();


                }
                else{

                    AddData(newEntry, newEntry2, newEntry3);
                    Intent i = new Intent();
                    String fName = filmName.getText().toString();
                    String fDate = filmDate.getText().toString();
                    String fURL = fileName.getText().toString();
                    i.putExtra("name",fName);
                    i.putExtra("date", fDate);
                    i.putExtra("file", fURL);
                    setResult(RESULT_OK,i);
                    finish();


                }


            }
        });

    }
    public void AddData(String newEntry, String newEntry2, String newEntry3){
        boolean insertData = myDB.addData(newEntry, newEntry2, newEntry3);

        if(insertData == true){
            Toast.makeText(AddFilm.this, "Successfully added the film information to the database!", Toast.LENGTH_SHORT).show();
        }
        else{
            Toast.makeText(AddFilm.this, "There was a problem adding your film to the database!", Toast.LENGTH_SHORT).show();
        }

        }
}
