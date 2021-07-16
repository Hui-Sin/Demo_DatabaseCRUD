package sg.edu.rp.c346.id20018354.demodatabasecrud;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class EditActivity extends AppCompatActivity {

    TextView tvID;
    EditText etEditContent;
    Button btnUpdate, btnDelete;
    Note data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        //initialize the variables with UI here
        tvID=findViewById(R.id.tvID);
        btnUpdate=findViewById(R.id.btnUpdate);
        btnDelete=findViewById(R.id.btnDelete);
        etEditContent=findViewById(R.id.etEditContent);

        Intent edit = getIntent();
        data = (Note) edit.getSerializableExtra("data");

        tvID.setText("ID: " + data.getId());
        etEditContent.setText(data.getNoteContent());
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DBHelper dbh = new DBHelper(EditActivity.this);
                data.setNoteContent(etEditContent.getText().toString());
                dbh.updateNote(data);
                dbh.close();
                Intent back = new Intent(EditActivity.this,
                        MainActivity.class);
                startActivity(back);
                Toast.makeText(EditActivity.this, "Update successful",
                        Toast.LENGTH_SHORT).show();
            }
        });
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DBHelper dbh = new DBHelper(EditActivity.this);
                dbh.deleteNote(data.getId());
                Intent back = new Intent(EditActivity.this,
                        MainActivity.class);
                startActivity(back);
                Toast.makeText(EditActivity.this, "Delete successful",
                        Toast.LENGTH_SHORT).show();
            }
        });
    }
}
