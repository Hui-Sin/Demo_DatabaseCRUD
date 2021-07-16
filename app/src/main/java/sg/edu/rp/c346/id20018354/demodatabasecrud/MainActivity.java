package sg.edu.rp.c346.id20018354.demodatabasecrud;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    Button btnAdd, btnEdit, btnRetrieve;
    EditText etContent;
    ArrayList<Note> al;
    ListView lv;
    ArrayAdapter<Note> aa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etContent=findViewById(R.id.etContent);
        btnAdd=findViewById(R.id.btnAdd);
        btnEdit=findViewById(R.id.btnEdit);
        btnRetrieve=findViewById(R.id.btnRetrieve);
        lv=findViewById(R.id.lv);

        al = new ArrayList<Note>();
        aa = new ArrayAdapter<Note>(this,
                android.R.layout.simple_list_item_1, al);
        lv.setAdapter(aa);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int
                    position, long identity) {
                Note data = al.get(position);
                Intent edit = new Intent(MainActivity.this,
                        EditActivity.class);
                edit.putExtra("data", data);
                startActivity(edit);
            }
        });
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String data = etContent.getText().toString();
                DBHelper dbh = new DBHelper(MainActivity.this);
                long inserted_id = dbh.insertNote(data);

                if (inserted_id != -1){
                    al.clear();
                    al.addAll(dbh.getAllNotes());
                    aa.notifyDataSetChanged();
                    Toast.makeText(MainActivity.this, "Insert successful",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });
        btnRetrieve.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DBHelper dbh = new DBHelper(MainActivity.this);
                al.clear();
//                ArrayList<Note> notes=dbh.getAllNotes();
//                al.addAll(notes);
                String filterText = etContent.getText().toString().trim();
                if(filterText.length() == 0) {
                    al.addAll(dbh.getAllNotes());
                }
                else{
                    al.addAll(dbh.getAllNotes(filterText));
                }

//                al.addAll(dbh.getAllNotes());
                aa.notifyDataSetChanged();
            }
        });
        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Note target = al.get(0);
                Intent edit = new Intent(MainActivity.this,
                        EditActivity.class);
                edit.putExtra("data", target);
                startActivity(edit);
            }
        });
    }
    @Override
    protected void onResume() {
        super.onResume();
        btnRetrieve.performClick();
    }

}
