package org.izv.dmc.archivosdejamones;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.izv.dmc.archivosdejamones.data.Vino;
import org.izv.dmc.archivosdejamones.data.util.Csv;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class NewVino extends AppCompatActivity {
    public static final String TAG=MainActivity.class.getName()+"xyzyx";
    TextView tvVino;
    EditText etIdn,etNombre,etBodega,etColor,etOrigen,etGraduacion,etFecha;
    Button btAdd,btCancel;
    public String fileName="data.csv";
    int id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_vino);
        initialize();
    }
    private void initialize() {
        //long id, String nombre, String bodega, String color, String origen, Double graduacion, Integer fecha
        tvVino=findViewById(R.id.tvVino);
        etIdn=findViewById(R.id.etIdn);
        etNombre=findViewById(R.id.etNombre);
        etBodega=findViewById(R.id.etBodega);
        etColor=findViewById(R.id.etColor);
        etOrigen=findViewById(R.id.etOrigen);
        etGraduacion=findViewById(R.id.etGraduación);
        etFecha=findViewById(R.id.etFecha);
        btAdd=findViewById(R.id.btAñadir);
        btCancel=findViewById(R.id.btCancel);

        btAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                    if(!repitedID()){
                        //escribe en el archivo csv
                        writeInternalFile();

                    }else{
                        tvVino.setText(R.string.id_existe);

                    }
            }
        });
        btCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent refresh = new Intent(NewVino.this, MainActivity.class);
                startActivity(refresh);
                finish();
            }
        });

    }
    //comprueba si la ID del nuevo vino ya existe en el catálogo
    private Boolean repitedID() {
        Boolean repit = false;
        try {
        String[] vinos =readFile(getFilesDir(),fileName).toString().split("\n");
        for (String vino:vinos) {
            Log.v(TAG,vino);
        }
            for (int i = 0; i < vinos.length; i++) {
                if (vinos[i].split(";")[0].equals(etIdn.getText().toString())) {
                    repit = true;
                }
            }}catch (NullPointerException ne){
        Log.v(TAG,"no");
    }
            return repit;
        }
    //crea y comprueba los campos del vino
    private Vino createVino() {String content=
                    etIdn.getText().toString()+";"+
                    etNombre.getText().toString()+";"+
                    etBodega.getText().toString()+";"+
                    etColor.getText().toString()+";"+
                    etOrigen.getText().toString()+";"+
                    etGraduacion.getText().toString()+";"+
                    etFecha.getText().toString();

        Vino vino= Csv.getVino(content);
        return vino;
    }


    private boolean writeFile(File file, String fileName, String string){
        File f=new File(file, fileName);
        FileWriter fw;
        Boolean ok=true;
        try {
            fw=new FileWriter(f,true);
            fw.write(string+"\n");
            fw.flush();
            fw.close();
        }catch (IOException io){
            ok=false;
            Log.v(TAG,io.toString());
        }
        return ok;
    }
    private void writeInternalFile() {
        //comprueba que los campos no estén vacios, si lo están da error
        if(etIdn.getText().toString().equals("")
                ||etNombre.getText().toString().equals("")
                ||etBodega.getText().toString().equals("")
                ||etColor.getText().toString().equals("")
                ||etOrigen.getText().toString().equals("")){
            tvVino.setText(R.string.error);
        }else{
            String text=Csv.getCsv(createVino());
            writeResult(writeFile(getFilesDir(),fileName,text));
        }
    }
    private void writeResult(Boolean result) {
        Intent refresh = new Intent(NewVino.this, MainActivity.class);
        startActivity(refresh);
        finish();


    }
    private String readFile(File file, String fileName){
        File f=new File(file,fileName);
        String texto="";
        try{
            BufferedReader br=new BufferedReader(new FileReader(f));
            String linea;
            while ((linea=br.readLine())!=null){
                texto+=linea+"\n";
            }
            br.close();
        }catch (IOException io){
            texto=null;
            Log.v(TAG,io.toString());
        }
        return texto;
    }
}