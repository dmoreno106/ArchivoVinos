package org.izv.dmc.archivosdejamones.data.util;

import android.nfc.Tag;
import android.util.Log;

import org.izv.dmc.archivosdejamones.MainActivity;
import org.izv.dmc.archivosdejamones.data.Vino;

public  class Csv {
   public static Vino  getVino(String str){

    String[] atributos=str.split(";");
    Vino vino=null;
    //nombre,bodega,color,origen,graduacion,fecha
        if(atributos.length==7) {
            vino=new Vino();
            vino.setId(Long.parseLong(atributos[0].trim()));
            vino.setNombre(atributos[1].trim());
            vino.setBodega(atributos[2].trim());
            vino.setColor(atributos[3].trim());
            vino.setOrigen(atributos[4].trim());
            try {
                vino.setGraduacion(Double.parseDouble(atributos[5].trim()));
            }catch (NumberFormatException e){
                vino.setGraduacion(0.0);
            }
            try {
                vino.setFecha(Integer.parseInt(atributos[6].trim()));
            }catch (NumberFormatException e){
                vino.setFecha(0);
                Log.v(MainActivity.TAG,e.getMessage());
            }
        }

    return vino;
    }
    public static String getCsv(Vino v){
        return  v.getId()+"; "+
                v.getNombre()+"; "+
                v.getBodega()+"; "+
                v.getColor()+"; "+
                v.getOrigen()+"; "+
                v.getGraduacion()+"; "+
                v.getFecha();
    }
}
