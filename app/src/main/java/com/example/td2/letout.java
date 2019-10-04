package com.example.td2;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class letout extends AppCompatActivity {
    ListView maliste;
    ArrayList<Integer> image;
    ArrayList<String> nom;
    String filename = "time.ser";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_letout);
        Button buttonAdd = findViewById(R.id.button);
        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myIntent = new Intent(letout.this, MainActivity.class);
                startActivity(myIntent);
            }
        });
        FileInputStream fis = null;
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
        }

    /*   FileOutputStream fos = null;
        ObjectOutputStream out = null;
        try {
            fos =  openFileOutput(filename, Context.MODE_PRIVATE);
            out = new ObjectOutputStream(fos);
            out.writeObject(Contact.getListeContact());

            out.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }*/
        final MonAdapter MonAdapter = new MonAdapter();
        maliste = findViewById(R.id.list);
        maliste.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                new AlertDialog.Builder(letout.this)
                        .setTitle("Formulaire")
                        .setMessage("Est-ce que tout est ok?"+ "\n Nom:"+Contact.listeContact.get(i).getNom()+"\n Prenom: "+Contact.listeContact.get(i).getPrenom()+"\n Telephone: "+Contact.listeContact.get(i).getTel())


                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {

                        }
            })

                    .setNegativeButton(android.R.string.no, null)
                                .setIcon(android.R.drawable.ic_dialog_alert)
                                .show();
        }});
        maliste.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                Contact.listeContact.remove(i);
                MonAdapter.notifyDataSetChanged();
                FileOutputStream fos = null;
                ObjectOutputStream out = null;
                try {
                    fos =  openFileOutput(filename, Context.MODE_PRIVATE);
                    out = new ObjectOutputStream(fos);
                    out.writeObject(Contact.getListeContact());

                    out.close();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
                return true;
            }
        });
        maliste.setAdapter(MonAdapter);
        MonAdapter.notifyDataSetChanged();
       /* TextView nom = findViewById(R.id.nom);
        TextView prenom = findViewById(R.id.prenom);
        TextView datenais = findViewById(R.id.datenais);
        TextView email = findViewById(R.id.email);
        TextView addresse = findViewById(R.id.addresse);
        TextView cp = findViewById(R.id.cp);
        TextView tel = findViewById(R.id.tel);
        //TextView nom = findViewById(R.id.nom);
        ArrayList<String> letout= getIntent().getStringArrayListExtra("KEYA");
        if(letout!=null)
        {
            System.out.println("LETOUT "+letout.get(0));
            nom.setText("Nom: "+letout.get(0)+"");
            prenom.setText("Prenom: "+letout.get(1)+"");
            datenais.setText("Date de naissance: "+letout.get(2)+"");
            email.setText("Email: "+letout.get(3)+"");
            addresse.setText("Addresse: "+letout.get(4)+"");
            cp.setText("Code Postal: "+letout.get(5)+"");
            tel.setText("Telephone :"+letout.get(6)+"");
        }*/
    }

    class MonAdapter extends BaseAdapter {


        @Override
        public int getCount() {
            return Contact.listeContact.size();
        }

        @Override
        public Object getItem(int i) {
            return Contact.listeContact.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            view = getLayoutInflater().inflate(R.layout.listview,null);
            ImageView imagev = view.findViewById(R.id.imageView);
            TextView montext = view.findViewById(R.id.textView);
            imagev.setImageBitmap(Contact.listeContact.get(i).getImg().getBitmap());
            montext.setText(Contact.listeContact.get(i).getNom());
            return view;
        }
    }
}
