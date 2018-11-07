package angio102.filmmanager;

import android.app.Service;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;
import android.database.Cursor;


import java.util.ArrayList;
import java.util.IdentityHashMap;


public class FilmList extends AppCompatActivity {
    //declared global variables
    ListView filmList;
    ArrayList<String> listOfFilms, listOfDates, listOfFileNames;
    ArrayAdapter<String> adapter;
    Button addFilm;
    final int REQUEST_CODE_1 = 1;
    DatabaseHelper myDB;
    Cursor data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.flim_list_layout);

        //linked XML to Java

        filmList = findViewById(R.id.film_list);
        listOfFilms = new ArrayList<>();
        listOfDates = new ArrayList<>();
        listOfFileNames = new ArrayList<>();
        myDB = new DatabaseHelper(this);
        addFilm = findViewById(R.id.add_film_button);

          data = myDB.getListContents();

        if (data.getCount() == 0) {
            Toast.makeText(FilmList.this, "The database is empty!", Toast.LENGTH_SHORT);
        } else {
            while (data.moveToNext()) {
                listOfFilms.add(data.getString(1));
                listOfDates.add(data.getString(2));
                listOfFileNames.add(data.getString(3));
                adapter = new ArrayAdapter<String>(FilmList.this, android.R.layout.simple_list_item_1, listOfFilms);
                filmList.setAdapter(adapter);
                adapter.notifyDataSetChanged();
            }
        }


        Intent stop=getIntent();
        if(stop!=null){
            stopService(stop);
            //Toast.makeText(FilmList.this,"Service Stopped!",Toast.LENGTH_SHORT).show();
        }


        addFilm.setOnClickListener((new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(FilmList.this, AddFilm.class);
                startActivityForResult(i,REQUEST_CODE_1);
            }
        }));


        //setting a long click listener in order to delete films from the view
        filmList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                 String x = (String)parent.getItemAtPosition(position);
                 Cursor datax = myDB.getItemID(x);
                int ID = -1;
                while (datax.moveToNext()){
                    ID = datax.getInt(0);
                }
                if (ID > -1){
                    Toast.makeText(FilmList.this, "Successfully removed the film from the datasabase.", Toast.LENGTH_SHORT).show();
                    myDB.deleteFilm(ID,x);
                }
                else{

                }
                data=myDB.getListContents();

                if (data.getCount() == 0) {
                    Toast.makeText(FilmList.this, "The database is empty!", Toast.LENGTH_SHORT).show();

                    listOfFilms.clear();
                    listOfDates.clear();
                    listOfFileNames.clear();
                    adapter.notifyDataSetChanged();



                } else {
                    listOfFilms.clear();
                    listOfDates.clear();
                    listOfFileNames.clear();
                    while (data.moveToNext()) {
                        listOfFilms.add(data.getString(1));
                        listOfDates.add(data.getString(2));
                        listOfFileNames.add(data.getString(3));
                        adapter = new ArrayAdapter<String>(FilmList.this, android.R.layout.simple_list_item_1, listOfFilms);
                        filmList.setAdapter(adapter);
                        adapter.notifyDataSetChanged();
                    }
                }

                return true;
            }
        });

        filmList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(FilmList.this,"Starting Service... Pull down and click on the notification!", Toast.LENGTH_SHORT).show();

                Intent s = new Intent(FilmList.this, FilmService.class);
                s.putExtra("filmN",listOfFilms.get(position));
                s.putExtra("filmD",listOfDates.get(position));
                s.putExtra("filmF",listOfFileNames.get(position));

                startService(s);

            }
        });




    }
        @Override
        //transfers the variables from AddFilm to the FilmList
        protected void onActivityResult ( int requestCode, int resultCode, Intent data){
            super.onActivityResult(requestCode, resultCode, data);
            if (requestCode == REQUEST_CODE_1) {
                if (resultCode == RESULT_OK) {

                    listOfFilms.add(data.getStringExtra("name"));
                    listOfDates.add(data.getStringExtra("date"));
                    listOfFileNames.add(data.getStringExtra("file"));

                    System.out.println(data.getStringExtra("name"));
                    System.out.println(data.getStringExtra("date"));
                    System.out.println(data.getStringExtra("file"));


                    adapter = new ArrayAdapter<String>(FilmList.this, android.R.layout.simple_list_item_1, listOfFilms);
                    filmList.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                }
            }
        }

    }

