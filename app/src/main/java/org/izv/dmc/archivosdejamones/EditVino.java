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

public class EditVino extends AppCompatActivity {
    public static final String TAG=MainActivity.class.getName()+"xyzyx";
    TextView tvVino,tvId;
    EditText etNombre,etBodega,etColor,etOrigen,etGraduacion,etFecha;
    Button btEdit,btBorrar,btCancel;
    String fileName="data.csv";
    int id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_vino);
        initialize();
    }

    private void initialize() {
        //long id, String nombre, String bodega, String color, String origen, Double graduacion, Integer fecha
        try {
            id=Integer.parseInt(getIntent().getStringExtra("id"));
        }catch (NumberFormatException ne){
            finish();
        }
        tvId=findViewById(R.id.tvId);
        tvVino=findViewById(R.id.tvVino);
        etNombre=findViewById(R.id.etNombre);
        etBodega=findViewById(R.id.etBodega);
        etColor=findViewById(R.id.etColor);
        etOrigen=findViewById(R.id.etOrigen);
        etGraduacion=findViewById(R.id.etGraduación);
        etFecha=findViewById(R.id.etFecha);
        btEdit=findViewById(R.id.btAñadir);
        btBorrar=findViewById(R.id.btBorrar);
        btCancel=findViewById(R.id.btCancel);

        tvId.setText("Id: "+id);
        if (existeId()){
            btEdit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    borraVino(getFilesDir(),String.valueOf(id),fileName);
                    writeInternalFile();
                    Intent refresh = new Intent(EditVino.this, MainActivity.class);
                    startActivity(refresh);
                    finish();
                }
            });
            btCancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent refresh = new Intent(EditVino.this, MainActivity.class);
                    startActivity(refresh);
                    finish();
                }
            });
        }else{
            finish();
        }
        btBorrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                borraVino(getFilesDir(),String.valueOf(id),fileName);
                Intent refresh = new Intent(EditVino.this, MainActivity.class);
                startActivity(refresh);
                finish();
            }
        });
    }

    //crea y comprueba los campos del vino
    private Vino createVino() {String content=
                    String.valueOf(id)+";"+
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
        //comprueba que los campos no estén vacios, si lo están no lo cambia
        if(     etNombre.getText().toString().equals("")
                ||etBodega.getText().toString().equals("")
                ||etColor.getText().toString().equals("")
                ||etOrigen.getText().toString().equals("")){

        }else{
            String text=Csv.getCsv(createVino());
            writeResult(writeFile(getFilesDir(),fileName,text));
        }
    }
    private void writeResult(Boolean result) {
        String mensaje = getString(R.string.message_ok);
        if (!result) {
            mensaje = getString(R.string.message_no);
        }
        tvVino.setText(mensaje);
    }

    //Comprueba si la id anteriormente introducida existe en el archivo de vinos
    private Boolean existeId() {
        String content="";
        String[] vinos=readFile(getFilesDir(),fileName).toString().split("\n");
        for (String vino:vinos) {
            //si existe tomará los valores del vino y los pondrá en su correspondiente edit text
            if(vino.split(";")[0].equals(String.valueOf(id))){

                        etNombre.setText(vino.split(";")[1]);
                        etBodega.setText(vino.split(";")[2]);
                        etColor.setText(vino.split(";")[3]);
                        etOrigen.setText(vino.split(";")[4]);
                        etGraduacion.setText(vino.split(";")[5]);
                        etFecha.setText(vino.split(";")[6]);
                        return true;
            }
        }
      return false;
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

    //busca en el archivo el id del vino y borra esa fila
    public static void borraVino(File file, String id, String archivo) {
        File f = new File(file, archivo);
        File f2 = new File(file, "temp.tmp");

        String str[];
        String tmp;
        try {
            FileWriter fw = new FileWriter(f2);
            BufferedReader br = new BufferedReader(new FileReader(f));
            String linea;
            while ((linea = br.readLine()) != null) {
                str = linea.split(";");
                if (!id.equals(str[0])) {
                    tmp = linea;
                    fw.write(tmp);
                    fw.write("\n");
                    fw.flush();
                }
            }
            fw.close();
            br.close();

            f.delete();
            f2.renameTo(f);
        } catch (Exception e) {
            Log.v(TAG, e.toString());
        }

    }

}