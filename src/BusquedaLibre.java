import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import org.apache.lucene.analysis.en.EnglishAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.IntPoint;
import org.apache.lucene.index.Term;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.*;
import java.util.ArrayList;


public class BusquedaLibre {

    EscenaResultadosTexto escenaResultados;

    ArrayList<QueryParser> consulta;

    TopDocs documentos;

    BooleanQuery bq;

    BooleanQuery.Builder bqbuilder;

    ObservableList<Documento> listaResultados;

    public BusquedaLibre(){

        escenaResultados = new EscenaResultadosTexto();

        consulta = new ArrayList<>();

        consulta.add(new QueryParser("title", new EnglishAnalyzer()));

        consulta.add(new QueryParser("abstract", new EnglishAnalyzer()));

        consulta.add(new QueryParser("source", new EnglishAnalyzer()));

    }

    public void inicializarVolverBuscar(){

        bqbuilder = new BooleanQuery.Builder();

        listaResultados = FXCollections.observableArrayList();
    }

    public void busquedaLibre(ChoiceBox<String> campo, TextField contenido,  TextField campoTextoPublished1,
                              TextField campoTextoPublished2, TextField campoTextoCited1,
                              TextField campoTextoCited2, IndexSearcher searcher) throws Exception{

        inicializarVolverBuscar();

        String campoABuscar = campo.getValue().toString();

        crearConsulta(contenido, campoABuscar, campoTextoPublished1, campoTextoPublished2, campoTextoCited1,
                campoTextoCited2, searcher);


        for (ScoreDoc sd : documentos.scoreDocs){

            Document d = searcher.doc(sd.doc);

            listaResultados.add(new Documento(d.get("author"), d.get("title"), d.get("abstract"), d.get("source"),
                    d.get("link"), d.get("keywords author"), d.get("keywords index"), Integer.parseInt(d.get("year")),
                    Integer.parseInt(d.get("cited by"))));
        }

        escenaResultados.crearTablaDatos(listaResultados);

    }

    public void crearConsulta(TextField contenido, String campo,TextField campoTextoPublished1,
                              TextField campoTextoPublished2, TextField campoTextoCited1,
                              TextField campoTextoCited2, IndexSearcher searcher) throws Exception{

        crearConsultaRangoPublicacion(campoTextoPublished1, campoTextoPublished2);
        crearConsultaRangoCitas(campoTextoCited1, campoTextoCited2);

        //En este segundo bloque comprobamos que subconsulta vamos a crear.
        if (campo == "Title"){
            crearConsultaTitulo(contenido);
        }
        else if (campo == "Abstract"){
            crearConsultaResumen(contenido);
        }
        else if (campo == "Source"){
            crearConsultaFuente(contenido);
        }
        else if (campo == "Keywords Index"){
            crearConsultaPalabrasClaveIndex(contenido);
        }
        else if (campo == "Keywords Author"){
            crearConsultaPalabrasClaveAutor(contenido);
        }
        else if (campo == "Author"){
            crearConsultaAutor(contenido);
        }
        else {
            crearConsultaTodosCampos(contenido);
        }

        bq = bqbuilder.build();

        documentos = searcher.search(bq, 2000);

    }

    public void crearConsultaTitulo(TextField contenido) throws Exception{

        Query consultaTitulo = consulta.get(0).parse("title: "+contenido.getText());

        BooleanClause bcCitas = new BooleanClause(consultaTitulo, BooleanClause.Occur.MUST);

        bqbuilder.add(bcCitas);

    }

    public void crearConsultaResumen(TextField contenido) throws Exception{

        Query consultaResumen = consulta.get(1).parse("abstract: "+contenido.getText());

        BooleanClause bcCitas = new BooleanClause(consultaResumen, BooleanClause.Occur.MUST);

        bqbuilder.add(bcCitas);

    }

    public void crearConsultaFuente(TextField contenido) throws Exception{

        Query consultaFuente = consulta.get(2).parse("source: "+contenido.getText());

        BooleanClause bcCitas = new BooleanClause(consultaFuente, BooleanClause.Occur.MUST);

        bqbuilder.add(bcCitas);

    }

    public void crearConsultaPalabrasClaveIndex(TextField contenido) throws Exception{

        Query consultaKeywordIndex = new TermQuery(new Term("keywords index",contenido.getText().toLowerCase()));

        BooleanClause bcCitas = new BooleanClause(consultaKeywordIndex, BooleanClause.Occur.MUST);

        bqbuilder.add(bcCitas);

    }

    public void crearConsultaPalabrasClaveAutor(TextField contenido) throws Exception{

        Query consultaKeywordAutor = new TermQuery(new Term("keywords author",contenido.getText().toLowerCase()));

        BooleanClause bcCitas = new BooleanClause(consultaKeywordAutor, BooleanClause.Occur.MUST);

        bqbuilder.add(bcCitas);
    }

    public void crearConsultaAutor(TextField contenido) throws Exception{

        Query consultaAutor = new TermQuery(new Term("author",contenido.getText().toLowerCase()));

        BooleanClause bcCitas = new BooleanClause(consultaAutor, BooleanClause.Occur.MUST);

        bqbuilder.add(bcCitas);
    }

    public void crearConsultaTodosCampos(TextField contenido) throws Exception{


        Query qtitulo = consulta.get(0).parse("title: "+contenido.getText());

        BooleanClause bc1 = new BooleanClause(qtitulo,BooleanClause.Occur.SHOULD);

        Query qresumen = consulta.get(1).parse("abstract: "+contenido.getText());

        BooleanClause bc2 = new BooleanClause(qresumen,BooleanClause.Occur.SHOULD);

        Query qfuente = consulta.get(2).parse("source: "+contenido.getText());

        BooleanClause bc3 = new BooleanClause(qfuente,BooleanClause.Occur.SHOULD);

        //Pasamos la entrada a minúscula para realizar la búsqueda por el campo Keywords y Autor.

        String contenidoMinuscula = contenido.getText().toLowerCase();

        Query qkindex = new TermQuery(new Term("keywords index",contenidoMinuscula));

        BooleanClause bc4 = new BooleanClause(qkindex,BooleanClause.Occur.SHOULD);

        Query qkautor = new TermQuery(new Term("keywords author",contenidoMinuscula));

        BooleanClause bc5 = new BooleanClause(qkautor,BooleanClause.Occur.SHOULD);

        Query qautor = new TermQuery(new Term("author",contenidoMinuscula));

        BooleanClause bc6 = new BooleanClause(qautor,BooleanClause.Occur.SHOULD);

        bqbuilder.add(bc1);
        bqbuilder.add(bc2);
        bqbuilder.add(bc3);
        bqbuilder.add(bc4);
        bqbuilder.add(bc5);
        bqbuilder.add(bc6);

    }

    public void crearConsultaRangoPublicacion(TextField campoTextoPublished1, TextField campoTextoPublished2){

        if (campoTextoPublished1.getText().length() > 0 && campoTextoPublished2.getText().length() > 0){

            Query qpublicacion = IntPoint.newRangeQuery("year",Integer.parseInt(campoTextoPublished1.getText()),
                    Integer.parseInt(campoTextoPublished2.getText()));

            BooleanClause bcPublicacion = new BooleanClause(qpublicacion, BooleanClause.Occur.MUST);

            bqbuilder.add(bcPublicacion);

        }
        else if (campoTextoPublished1.getText().length() > 0 && campoTextoPublished2.getText().length() == 0){

            Query qpublicacion = IntPoint.newExactQuery("year",Integer.parseInt(campoTextoPublished1.getText()));

            BooleanClause bcPublicacion = new BooleanClause(qpublicacion, BooleanClause.Occur.MUST);

            bqbuilder.add(bcPublicacion);

        }
    }

    public void crearConsultaRangoCitas(TextField campoTextoCited1, TextField campoTextoCited2){

        if (campoTextoCited1.getText().length() > 0 && campoTextoCited2.getText().length() > 0){

            Query qcitas = IntPoint.newRangeQuery("cited by",Integer.parseInt(campoTextoCited1.getText()),
                    Integer.parseInt(campoTextoCited2.getText()));

            BooleanClause bcCitas = new BooleanClause(qcitas, BooleanClause.Occur.MUST);

            bqbuilder.add(bcCitas);
        }
        else if (campoTextoCited1.getText().length() > 0 && campoTextoCited2.getText().length() == 0){

            Query qcitas = IntPoint.newExactQuery("cited by",Integer.parseInt(campoTextoCited1.getText()));

            BooleanClause bcCitas = new BooleanClause(qcitas, BooleanClause.Occur.MUST);

            bqbuilder.add(bcCitas);
        }
    }
}
