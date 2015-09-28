package com.example.docente05.sqlitev1;

import android.database.Cursor;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import Conexion.Sqlite;


public class MainActivity extends ActionBarActivity {
    Sqlite cx;
    EditText edt1, edt2;
    ListView lista;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        cx = new Sqlite(this, "BDusuario", null, 1);
        edt1 = (EditText) findViewById(R.id.editText1);
        edt2 = (EditText) findViewById(R.id.editText2);
        lista = (ListView) findViewById(R.id.listView);
        read_us();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void insert_us(View v) {
        if (cx.getWritableDatabase() != null) {
            cx.getWritableDatabase().execSQL("INSERT INTO usuario(campo1, campo2) " +
                    " VALUES ('" + edt1.getText().toString() + "', '" + edt2.getText().toString() + "')");
            limpiar();
            Toast.makeText(getApplicationContext(), "Insertado", Toast.LENGTH_SHORT).show();
        }
        read_us();

    }
    public void read_us(){

        Cursor cur = cx.getReadableDatabase().rawQuery("SELECT campo1||' '||campo2 as us  FROM usuario ",null);
        String[] data = new String[cur.getCount()];

        int n=0;
       if(cur.moveToFirst()) {
            do {
                data[n]=cur.getString(0).toString();
                //edt1.setText(cur.getString(0).toString()+"");
                n++;
            } while (cur.moveToNext());
        }
        lista.setAdapter(new ArrayAdapter(this,android.R.layout.simple_list_item_1,data));
    }
    public void limpiar(){
        edt1.setText("");
        edt2.setText("");
    }
}
