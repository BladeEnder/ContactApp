package com.example.td2;

import android.Manifest;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.Toast;

import androidx.core.content.ContextCompat;

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class MainActivity extends  RuntimePermissionsActivity  {
    private static int CHARGEMENT_IMAGE = 1;
    private static final int REQUEST_PERMISSIONS = 20;
    EditText nom;
    EditText prenom;
    EditText datenaiss;
    EditText tele;
    EditText email;
    EditText cp;
    EditText adr;
    Button valider;
    ImageButton avatar;
    Calendar myCalendar;
    String filename = "time.ser";
        @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


           /* FileInputStream fis = null;
            ObjectInputStream in = null;
            try {
                File yourFile = new File(this.getFilesDir().getPath().toString() + "/time.ser");
                yourFile.createNewFile();
                fis = new FileInputStream(yourFile);
                in = new ObjectInputStream(fis);
                Contact.listeContact = (ArrayList<Contact>) in.readObject();
                in.close();
            } catch (Exception ex) {
                ex.printStackTrace();
            }*/
            nom = findViewById(R.id.nom);
            prenom = findViewById(R.id.prenom);
            datenaiss = findViewById(R.id.datenaiss);
            tele = findViewById(R.id.tele);
            email = findViewById(R.id.email);
            cp = findViewById(R.id.cp);
            avatar = findViewById(R.id.avatar);

            adr = findViewById(R.id.adr);
            valider = findViewById(R.id.valider);
        final ImageButton avatar = findViewById(R.id.avatar);

        avatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ContextCompat.checkSelfPermission(MainActivity.this,
                        Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                    MainActivity.super.requestAppPermissions(new
                                    String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, R.string
                                    .runtime_permissions_txt
                            , REQUEST_PERMISSIONS);


                }else{
                    Intent i = new Intent(
                            Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(i, CHARGEMENT_IMAGE);
                }
            }
        });

            valider.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (nom.getText().toString().equals("") || !nom.getText().toString().matches("^[a-zA-Z]+$")) {
                        nom.setError("Merci de saisir un nom");
                    } else if (prenom.getText().toString().equals("") || !prenom.getText().toString().matches("^[a-zA-Z]+$")) {
                        prenom.setError("Merci de saisir un prenom");
                    } else if (tele.getText().toString().equals("") || !tele.getText().toString().matches("^((\\+)33|0)[1-9](\\d{2}){4}$")) {
                        tele.setError("Merci de saisir un numéro de tel");
                    } else if (!email.getText().toString().equals("") && !email.getText().toString().matches("^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?(?:\\.[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?)*$")) {
                        email.setError("Merci de saisir une email valide");
                    } else if (!cp.getText().toString().equals("") && !cp.getText().toString().matches("/^(?:[0-8]\\d|9[0-8])\\d{3}$/")) {
                        cp.setError("Merci de saisir un code postal valide");
                    } else if (!datenaiss.getText().toString().equals("") && !datenaiss.getText().toString().matches("^([0-2][0-9]|(3)[0-1])(\\/)(((0)[0-9])|((1)[0-2]))(\\/)\\d{4}$")) {
                        datenaiss.setError("Merci de saisir une date valide");
                    } else {
                        if (ContextCompat.checkSelfPermission(MainActivity.this,
                                Manifest.permission.SEND_SMS)
                                != PackageManager.PERMISSION_GRANTED) {
                            MainActivity.super.requestAppPermissions(new
                                            String[]{Manifest.permission.SEND_SMS,
                                            Manifest.permission.READ_EXTERNAL_STORAGE}, R.string
                                            .runtime_permissions_txt
                                    , REQUEST_PERMISSIONS);
                        } else {
                            new AlertDialog.Builder(MainActivity.this)
                                    .setTitle("Formulaire")
                                    .setMessage("Est-ce que tout est ok?" + "\n Nom:" + nom.getText().toString() + "\n Prenom: " + prenom.getText().toString() + "\n Telephone: " + tele.getText().toString())


                                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int which) {
                                            Toast.makeText(getApplicationContext(), "Vous êtes inscrit ! ", Toast.LENGTH_LONG).show();
                                            Bitmap bitmap = ((BitmapDrawable) avatar.getDrawable()).getBitmap();
                                            ByteArrayOutputStream stream = new ByteArrayOutputStream();
                                            bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
                                            byte[] byteArray = stream.toByteArray();
                                            Bitmap bmp = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
                                            ProxyBitmap b = new ProxyBitmap(bmp);
                                            RadioButton homme = findViewById(R.id.radioButton2);
                                            RadioButton femme = findViewById(R.id.radioButton);
                                            String sexe = "";
                                            if (homme.isChecked()) {
                                                sexe = "homme";
                                            }
                                            if (femme.isChecked()) {
                                                sexe = "femme";
                                            }
                                            Contact cc = new Contact(nom.getText().toString(), prenom.getText().toString(), datenaiss.getText().toString(), email.getText().toString(), adr.getText().toString(), cp.getText().toString(), tele.getText().toString(), sexe, b);
                                            Contact.listeContact.add(cc);
                                            FileOutputStream fos = null;
                                            ObjectOutputStream out = null;
                                            try {
                                                fos = openFileOutput(filename, Context.MODE_PRIVATE);
                                                out = new ObjectOutputStream(fos);
                                                out.writeObject(Contact.getListeContact());

                                                out.close();
                                            } catch (Exception ex) {
                                                ex.printStackTrace();
                                            }


                                            SmsManager smgr = SmsManager.getDefault();
                                            smgr.sendTextMessage("15555215554", null, "Vous êtes bien inscrit", null, null);

                                            Intent myIntent = new Intent(MainActivity.this, letout.class);
                                            /*ArrayList<String> letout = new ArrayList();
                                            letout.add(nom.getText().toString());
                                            letout.add(prenom.getText().toString());
                                            letout.add(datenaiss.getText().toString());
                                            letout.add(email.getText().toString());
                                            letout.add(adr.getText().toString());
                                            letout.add(cp.getText().toString());
                                            letout.add(tele.getText().toString());
                                            letout.add(tele.getText().toString());
                                            myIntent.putExtra("KEYA", letout);*/
                                            startActivity(myIntent);
                                        }
                                    })

                                    .setNegativeButton(android.R.string.no, null)
                                    .setIcon(android.R.drawable.ic_dialog_alert)
                                    .show();
                        }
                    }
                }
            });
            myCalendar = Calendar.getInstance();

            final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

                @Override
                public void onDateSet(DatePicker view, int year, int monthOfYear,
                                      int dayOfMonth) {
                    myCalendar.set(Calendar.YEAR, year);
                    myCalendar.set(Calendar.MONTH, monthOfYear);
                    myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                    updateLabel();
                }

            };

           datenaiss.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    new DatePickerDialog(MainActivity.this, date, myCalendar
                            .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                            myCalendar.get(Calendar.DAY_OF_MONTH)).show();
                }
            });
    }

    @Override
    public void onPermissionsGranted(int requestCode) {
        Toast.makeText(this, "Permissions OK.", Toast.LENGTH_LONG).show();
    }

   @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == CHARGEMENT_IMAGE && resultCode == RESULT_OK && null != data) {
            Uri selectedImage = data.getData();
            String[] filePathColumn = {MediaStore.Images.Media.DATA};

            Cursor cursor = getContentResolver().query(selectedImage,
                    filePathColumn, null, null, null);
            cursor.moveToFirst();

            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            String picturePath = cursor.getString(columnIndex);
            cursor.close();
            avatar.setImageBitmap(BitmapFactory.decodeFile(picturePath));

        }
    }
   private void updateLabel() {
        String myFormat = "dd/MM/yyyy";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        datenaiss.setText(sdf.format(myCalendar.getTime()));
    }
}
