public class Documento {

    private String autores;
    private String titulo;
    private String resumen;
    private String fuente;
    private String link;
    private String palabrasClaveAutor;
    private String palabrasClaveIndice;
    private int fechaPublicacion;
    private int citadoPor;

    public Documento(){

        this("","","","","","","",0,0);
    }

    public Documento(String a, String t, String r, String f, String l, String pA, String pI, int fechaP, int c){

        autores = a;
        titulo = t;
        resumen = r;
        fuente = f;
        link = l;
        palabrasClaveAutor = pA;
        palabrasClaveIndice = pI;
        fechaPublicacion = fechaP;
        citadoPor = c;
    }

    public Documento(String a){

        autores = a;
    }

    public void setAutores(String a){

        autores = a;

    }

    public String getAutores(){

        return autores;

    }

    public void setTitulo(String t){

        titulo = t;

    }

    public String getTitulo(){

        return titulo;

    }

    public void setResumen(String r){

        resumen = r;

    }

    public String getResumen(){

        return resumen;

    }

    public void setFuente(String f){

        fuente = f;

    }

    public String getFuente(){

        return fuente;

    }

    public void setLink(String l){

        link = l;

    }

    public String getLink(){

        return link;

    }

    public void setPalabrasClaveAutor(String pA){

        palabrasClaveAutor = pA;

    }

    public String getPalabrasClaveAutor(){

        return palabrasClaveAutor;

    }

    public void setPalabrasClaveIndice(String pI){

        palabrasClaveAutor = pI;

    }

    public String getPalabrasClaveIndice(){

        return palabrasClaveIndice;

    }

    public void setFechaPublicacion(int f){

        fechaPublicacion = f;

    }

    public int getFechaPublicacion(){

        return fechaPublicacion;

    }

    public int getCitadoPor(){

        return citadoPor;

    }
}
