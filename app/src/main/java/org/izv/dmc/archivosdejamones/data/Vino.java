package org.izv.dmc.archivosdejamones.data;


public class Vino {
    private long id;
    private String nombre;
    private String bodega;
    private String color;
    private String origen;
    private Double graduacion;
    private Integer fecha;

    public Vino() {
        this(0,null,null,null,null,0.0,0);
    }

    public Vino(long id, String nombre, String bodega, String color, String origen, Double graduacion, Integer fecha) {
        this.id = id;
        this.nombre = nombre;
        this.bodega = bodega;
        this.color = color;
        this.origen = origen;
        this.graduacion = graduacion;
        this.fecha = fecha;
    }
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getBodega() {
        return bodega;
    }

    public void setBodega(String bodega) {
        this.bodega = bodega;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getOrigen() {
        return origen;
    }

    public void setOrigen(String origen) {
        this.origen = origen;
    }

    public Double getGraduacion() {
        return graduacion;
    }

    public void setGraduacion(Double graduacion) {
        this.graduacion = graduacion;
    }

    public Integer getFecha() {
        return fecha;
    }

    public void setFecha(Integer fecha) {
        this.fecha = fecha;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Vino{" +
                "id='"+id+'\''+
                "nombre='" + nombre + '\'' +
                ", bodega='" + bodega + '\'' +
                ", color='" + color + '\'' +
                ", origen='" + origen + '\'' +
                ", graduacion='" + graduacion + '\'' +
                ", fecha='" + fecha + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Vino)) return false;

        Vino vino = (Vino) o;

        if (nombre != null ? !nombre.equals(vino.nombre) : vino.nombre != null) return false;
        if (bodega != null ? !bodega.equals(vino.bodega) : vino.bodega != null) return false;
        return fecha != null ? fecha.equals(vino.fecha) : vino.fecha == null;
    }

    @Override
    public int hashCode() {
        int result = nombre != null ? nombre.hashCode() : 0;
        result = 31 * result + (bodega != null ? bodega.hashCode() : 0);
        result = 31 * result + (fecha != null ? fecha.hashCode() : 0);
        return result;
    }


  /*  @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Vino)) return false;
        Vino vino = (Vino) o;
        return Objects.equals(nombre, vino.nombre) && Objects.equals(bodega, vino.bodega) && Objects.equals(fecha, vino.fecha);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nombre, bodega, fecha);
    }*/
    //equals -hashcode
    //Java: si dos objetos son iguales su hashcode tiene que ser igual
    //pero si el hashCode de dos objetos es el mismo no significa que dos objetos sean iguales
    //pero si el hashCode es distinto no son iguales
    //o1,o2 -> if (o1.equals(o2))
}
