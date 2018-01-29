package agtzm.tareapermisos;

import android.Manifest;
import android.app.Activity;
import android.app.PendingIntent;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;


public class App extends Activity {
    private ListView lista;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.app);

        this.lista = (ListView) findViewById(R.id.list);

        showContacts();


    }

    private void showContacts(){
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED){
            if(ActivityCompat.shouldShowRequestPermissionRationale(this,Manifest.permission.READ_CONTACTS)){

            }else{
                int permiso = 1;
                ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.READ_CONTACTS}, permiso);

            }
        }

            List<String> contacts = getContactNames();
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, contacts);
            lista.setAdapter(adapter);

    }

    private List<String> getContactNames(){
        List<String> contacts = new ArrayList<>();

        ContentResolver cr = getContentResolver();

        Cursor cursor = cr.query(ContactsContract.Contacts.CONTENT_URI, null,null,null,null);

        if(cursor.moveToFirst()){
            do{
                String name = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
                contacts.add(name);
            }while(cursor.moveToNext());

        }
        cursor.close();

        return contacts;
    }

    public void llamada(View view) {

        if(ContextCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED){
            if(ActivityCompat.shouldShowRequestPermissionRationale(this,Manifest.permission.CALL_PHONE)){

            }else{
                int permiso = 1;
                ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.CALL_PHONE}, permiso);

            }

        }else {

            String number = "6142434422";
            Intent intento = new Intent(Intent.ACTION_CALL);
            intento.setData(Uri.parse("tel:" + number));
            startActivity(intento);
        }
    }

    public void mensaje(View view) {
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED){
            if(ActivityCompat.shouldShowRequestPermissionRationale(this,Manifest.permission.SEND_SMS)){

            }else{
                int permiso = 1;
                ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.SEND_SMS}, permiso);

            }

        }else {
            PendingIntent p = PendingIntent.getActivity(this,0,new Intent(this,Object.class),0);
            SmsManager sms = SmsManager.getDefault();
            sms.sendTextMessage("6142434422", null,"hola",p,null);
            Toast to;
            Toast.makeText(this, "Mensaje enviado: Revise su aplicacion de mensajes", Toast.LENGTH_SHORT).show();

        }
    }
}