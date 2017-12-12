import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import org.apache.lucene.analysis.en.EnglishAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.facet.FacetsCollector;
import org.apache.lucene.index.Term;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.*;

import java.util.ArrayList;

public class BusquedaBooleana {

    Label etiquetaError;

    //Este es el plan para cuando ocurre un error.
    GridPane plan;

    //Esta es la escena que hay dentro del plan para cuando ocurre un error.
    Scene escenaErrorBusquedaBooleana;

    ArrayList<QueryParser> consulta;

    TopDocs documentos;

    EscenaResultadosTexto escenaResultados;

    ArrayList<BooleanClause> clausulasBooleanas;

    BooleanQuery.Builder bqbuilder;

    ObservableList<Documento> listaResultados;


    public BusquedaBooleana(){

        plan = new GridPane();

        escenaResultados = new EscenaResultadosTexto();

        consulta = new ArrayList<>();

        consulta.add(new QueryParser("title", new EnglishAnalyzer()));

        consulta.add(new QueryParser("abstract", new EnglishAnalyzer()));

        consulta.add(new QueryParser("source", new EnglishAnalyzer()));
    }

    //Función que nos permite incrementar los campos de búsqueda.
    public void incrementarBusqueda(ArrayList<ChoiceBox<String> > cbCampos,
                                    ArrayList<TextField> campoTextoBuscar,
                                    ArrayList<ChoiceBox<String> > cbBooleanos,
                                    Button botonMenos,
                                    GridPane layout,
                                    TextField campoTextoPublished1,
                                    TextField campoTextoPublished2,
                                    TextField campoTextoCited1,
                                    TextField campoTextoCited2,
                                    Label etiquetaTextoPublished1,
                                    Label etiquetaTextoPublished2,
                                    Label etiquetaTextoCited1,
                                    Label etiquetaTextoCited2){

        try {
            if (campoTextoBuscar.size() == 1) {
                botonMenos.setVisible(true);
            }

            int total = cbCampos.size();

            GridPane.setConstraints(campoTextoPublished1, 2, total + 2);
            GridPane.setConstraints(campoTextoPublished2, 4, total + 2);
            GridPane.setConstraints(campoTextoCited1, 2, total + 3);
            GridPane.setConstraints(campoTextoCited2, 4, total + 3);
            GridPane.setConstraints(etiquetaTextoPublished1, 1, total + 2);
            GridPane.setConstraints(etiquetaTextoPublished2, 3, total + 2);
            GridPane.setConstraints(etiquetaTextoCited1, 1, total + 3);
            GridPane.setConstraints(etiquetaTextoCited2, 3, total + 3);

            cbCampos.add(new ChoiceBox(FXCollections.observableArrayList(
                    "All Fields", "Title", "Abstract", "Author" , "Source", "Keywords Index", "Keywords Author")
            ));

            cbCampos.get(total).setId("cbNormal" + total);
            GridPane.setConstraints(cbCampos.get(total), 1, total + 1);
            cbCampos.get(total).setPrefWidth(150);
            cbCampos.get(total).getSelectionModel().selectFirst();

            campoTextoBuscar.add(new TextField());
            campoTextoBuscar.get(total).setId("fieldSearch" + total);
            GridPane.setConstraints(campoTextoBuscar.get(total), 2, total + 1);
            campoTextoBuscar.get(total).setMaxWidth(300);
            campoTextoBuscar.get(total).setPrefWidth(300);

            //Decrementamos la variable total porque no empezamos con 1 choicebox desde el inicio de la aplicación.
            total--;

            cbBooleanos.add(new ChoiceBox(FXCollections.observableArrayList(
                    "AND", "OR", "NOT")));
            cbBooleanos.get(total).setId("cbBooleano" + total);
            GridPane.setConstraints(cbBooleanos.get(total), 3, total + 2);
            cbBooleanos.get(total).setPrefWidth(100);
            cbBooleanos.get(total).getSelectionModel().selectFirst();

            layout.getChildren().addAll(cbCampos.get(total + 1), campoTextoBuscar.get(total + 1), cbBooleanos.get(total));

        }catch (Exception e){

            etiquetaError = new Label("Se ha producido un error al incrementar las cajas de búsqueda.");
            plan.getChildren().add(etiquetaError);
            escenaErrorBusquedaBooleana = new Scene(plan,400,400);
            InterfazUsuario.window.setScene(escenaErrorBusquedaBooleana);
            InterfazUsuario.window.setTitle("Error");
            InterfazUsuario.window.show();
        }
    }

    //Función que nos permite decrementar los campos de búsqueda.
    public void decrementarBusqueda(ArrayList<ChoiceBox<String> > cbCampos,
                                    ArrayList<TextField> campoTextoBuscar,
                                    ArrayList<ChoiceBox<String> > cbBooleanos,
                                    Button botonMenos,
                                    GridPane layout,
                                    TextField campoTextoPublished1,
                                    TextField campoTextoPublished2,
                                    TextField campoTextoCited1,
                                    TextField campoTextoCited2,
                                    Label etiquetaTextoPublished1,
                                    Label etiquetaTextoPublished2,
                                    Label etiquetaTextoCited1,
                                    Label etiquetaTextoCited2){

        try {
            int total = campoTextoBuscar.size();

            if (total == 2) {
                botonMenos.setVisible(false);
            }

            GridPane.setConstraints(campoTextoPublished1, 2,total+1);
            GridPane.setConstraints(campoTextoPublished2, 4,total+1);
            GridPane.setConstraints(campoTextoCited1, 2,total+2);
            GridPane.setConstraints(campoTextoCited2, 4,total+2);
            GridPane.setConstraints(etiquetaTextoPublished1, 1,total+1);
            GridPane.setConstraints(etiquetaTextoPublished2, 3,total+1);
            GridPane.setConstraints(etiquetaTextoCited1, 1,total+2);
            GridPane.setConstraints(etiquetaTextoCited2, 3,total+2);

            layout.getChildren().removeAll(cbCampos.get(total - 1), campoTextoBuscar.get(total - 1),
                    cbBooleanos.get(total - 2));

            cbCampos.remove(total - 1);
            campoTextoBuscar.remove(total - 1);
            cbBooleanos.remove(total - 2);

        }catch (Exception e){

            etiquetaError = new Label("Se ha producido un error al decrementar las cajas de búsqueda.");
            plan.getChildren().add(etiquetaError);
            escenaErrorBusquedaBooleana = new Scene(plan,400,400);
            InterfazUsuario.window.setScene(escenaErrorBusquedaBooleana);
            InterfazUsuario.window.setTitle("Error");
            InterfazUsuario.window.show();
        }
    }

    public void inicializarVolverBuscar(){

        bqbuilder = new BooleanQuery.Builder();

        clausulasBooleanas = new ArrayList<>();

        listaResultados = FXCollections.observableArrayList();
    }

    //Función que nos permite realizar la búsqueda booleana.
    public void busquedaBooleana(ArrayList<ChoiceBox<String> > cbCampos,
                                 ArrayList<TextField> campoTextoBuscar,
                                 ArrayList<ChoiceBox<String> > cbBooleanos){

        try{

            inicializarVolverBuscar();

            int i = -1;
            String valorLogico = "AND";

            for (ChoiceBox<String> campo : cbCampos){

                if (i != -1){
                    valorLogico = cbBooleanos.get(i).getValue();
                }

                crearConsulta(campoTextoBuscar.get(i+1), valorLogico, campo.getValue());

                i++;
            }


            EscenaPrincipal.bq = bqbuilder.build();

            FacetsCollector colectorFacetas = new FacetsCollector();

            documentos = FacetsCollector.search(EscenaPrincipal.searcher, EscenaPrincipal.bq, 100, colectorFacetas);

            for (ScoreDoc sd : documentos.scoreDocs){

                Document d = EscenaPrincipal.searcher.doc(sd.doc);

                listaResultados.add(new Documento(d.get("author"), d.get("title"), d.get("abstract"), d.get("source"),
                        d.get("link"), d.get("keywords author"), d.get("keywords index"),
                        Integer.parseInt(d.get("year")), Integer.parseInt(d.get("cited by"))));
            }

            escenaResultados.crearTablaDatos(listaResultados,colectorFacetas);


        }catch (Exception e){

            etiquetaError = new Label("Se ha producido un error al realizar la búsqueda Booleana.");
            plan.getChildren().add(etiquetaError);
            escenaErrorBusquedaBooleana = new Scene(plan,400,400);
            InterfazUsuario.window.setScene(escenaErrorBusquedaBooleana);
            InterfazUsuario.window.setTitle("Error");
            InterfazUsuario.window.show();
        }
    }

    //Función que se encarga de dirigir hacia adonde va la consulta.
    //Crea una consulta u otra dependiendo el valor de la variable CAMPO.
    public void crearConsulta(TextField contenido, String valorLogico, String campo) throws Exception{

        //En este primer bloque comprobamos que operador lógico requiere la subconsulta.
        BooleanClause.Occur operadorLogico = BooleanClause.Occur.SHOULD;

        if (valorLogico == "AND")
            operadorLogico = BooleanClause.Occur.MUST;
        else if (valorLogico == "NOT")
            operadorLogico = BooleanClause.Occur.MUST_NOT;


        //En este segundo bloque comprobamos que subconsulta vamos a crear.
        if (campo == "Title"){
            crearConsultaTitulo(contenido, operadorLogico);
        }
        else if (campo == "Abstract"){
            crearConsultaResumen(contenido, operadorLogico);
        }
        else if (campo == "Source"){
            crearConsultaFuente(contenido, operadorLogico);
        }
        else if (campo == "Keywords Index"){
            crearConsultaPalabrasClaveIndex(contenido,operadorLogico);
        }
        else if (campo == "Keywords Author"){
            crearConsultaPalabrasClaveAutor(contenido,operadorLogico);
        }
        else if (campo == "Author"){
            crearConsultaAutor(contenido, operadorLogico);
        }
        else {
            crearConsultaTodosCampos(contenido, operadorLogico);
        }

    }

    public void crearConsultaTodosCampos(TextField contenido, BooleanClause.Occur operadorLogico) throws Exception{

        try{
            Query q1 = consulta.get(0).parse("title: "+contenido.getText());

            clausulasBooleanas.add(new BooleanClause(q1,operadorLogico));

            Query q2 = consulta.get(1).parse("abstract: "+contenido.getText());

            clausulasBooleanas.add(new BooleanClause(q2,operadorLogico));

            Query q3 = consulta.get(2).parse("source: "+contenido.getText());

            clausulasBooleanas.add(new BooleanClause(q3,operadorLogico));

            Query q4 = new TermQuery(new Term("keywords index",contenido.getText()));

            clausulasBooleanas.add(new BooleanClause(q4,operadorLogico));

            Query q5 = new TermQuery(new Term("keywords author",contenido.getText()));

            clausulasBooleanas.add(new BooleanClause(q5,operadorLogico));

            Query q6 = new TermQuery(new Term("author",contenido.getText()));

            clausulasBooleanas.add(new BooleanClause(q6,operadorLogico));

            int totalClausulas = clausulasBooleanas.size();

            bqbuilder.add(clausulasBooleanas.get(totalClausulas-6));
            bqbuilder.add(clausulasBooleanas.get(totalClausulas-5));
            bqbuilder.add(clausulasBooleanas.get(totalClausulas-4));
            bqbuilder.add(clausulasBooleanas.get(totalClausulas-3));
            bqbuilder.add(clausulasBooleanas.get(totalClausulas-2));
            bqbuilder.add(clausulasBooleanas.get(totalClausulas-1));

        }catch (Exception e){

            etiquetaError = new Label("Se ha producido un error al crear la consulta para todos los campos.");
            plan.getChildren().add(etiquetaError);
            escenaErrorBusquedaBooleana = new Scene(plan,400,400);
            InterfazUsuario.window.setScene(escenaErrorBusquedaBooleana);
            InterfazUsuario.window.setTitle("Error");
            InterfazUsuario.window.show();
        }

    }

    public void crearConsultaTitulo(TextField contenido, BooleanClause.Occur operadorLogico) throws Exception{

        try{

            Query q = consulta.get(0).parse("title: "+contenido.getText());

            clausulasBooleanas.add(new BooleanClause(q,operadorLogico));

            bqbuilder.add(clausulasBooleanas.get(clausulasBooleanas.size()-1));

        }catch (Exception e){

            etiquetaError = new Label("Se ha producido un error al crear la consulta para el título.");
            plan.getChildren().add(etiquetaError);
            escenaErrorBusquedaBooleana = new Scene(plan,400,400);
            InterfazUsuario.window.setScene(escenaErrorBusquedaBooleana);
            InterfazUsuario.window.setTitle("Error");
            InterfazUsuario.window.show();
        }
    }

    public void crearConsultaResumen(TextField contenido, BooleanClause.Occur operadorLogico) throws Exception{

        try{

            Query q = consulta.get(1).parse("abstract: "+contenido.getText());

            clausulasBooleanas.add(new BooleanClause(q,operadorLogico));

            bqbuilder.add(clausulasBooleanas.get(clausulasBooleanas.size()-1));

        }catch (Exception e){

            etiquetaError = new Label("Se ha producido un error al crear la consulta para el resumen.");
            plan.getChildren().add(etiquetaError);
            escenaErrorBusquedaBooleana = new Scene(plan,400,400);
            InterfazUsuario.window.setScene(escenaErrorBusquedaBooleana);
            InterfazUsuario.window.setTitle("Error");
            InterfazUsuario.window.show();
        }

    }

    public void crearConsultaAutor(TextField contenido, BooleanClause.Occur operadorLogico) throws Exception{

        try{

            Query q = new TermQuery(new Term("author",contenido.getText().toLowerCase()));

            clausulasBooleanas.add(new BooleanClause(q,operadorLogico));

            bqbuilder.add(clausulasBooleanas.get(clausulasBooleanas.size()-1));

        }catch (Exception e){

            etiquetaError = new Label("Se ha producido un error al crear la consulta para el autor.");
            plan.getChildren().add(etiquetaError);
            escenaErrorBusquedaBooleana = new Scene(plan,400,400);
            InterfazUsuario.window.setScene(escenaErrorBusquedaBooleana);
            InterfazUsuario.window.setTitle("Error");
            InterfazUsuario.window.show();
        }

    }

    public void crearConsultaPalabrasClaveIndex(TextField contenido, BooleanClause.Occur operadorLogico) throws Exception{

        try{

            Query q = new TermQuery(new Term("keywords index",contenido.getText().toLowerCase()));

            clausulasBooleanas.add(new BooleanClause(q,operadorLogico));

            bqbuilder.add(clausulasBooleanas.get(clausulasBooleanas.size()-1));

        }catch (Exception e){

            etiquetaError = new Label("Se ha producido un error al crear la consulta para las palabras claves index.");
            plan.getChildren().add(etiquetaError);
            escenaErrorBusquedaBooleana = new Scene(plan,400,400);
            InterfazUsuario.window.setScene(escenaErrorBusquedaBooleana);
            InterfazUsuario.window.setTitle("Error");
            InterfazUsuario.window.show();
        }

    }

    public void crearConsultaPalabrasClaveAutor(TextField contenido, BooleanClause.Occur operadorLogico) throws Exception{

        try{

            Query q2 = new TermQuery(new Term("keywords author",contenido.getText().toLowerCase()));

            clausulasBooleanas.add(new BooleanClause(q2,operadorLogico));

            bqbuilder.add(clausulasBooleanas.get(clausulasBooleanas.size()-1));

        }catch (Exception e){

            etiquetaError = new Label("Se ha producido un error al crear la consulta para las palabras claves autor.");
            plan.getChildren().add(etiquetaError);
            escenaErrorBusquedaBooleana = new Scene(plan,400,400);
            InterfazUsuario.window.setScene(escenaErrorBusquedaBooleana);
            InterfazUsuario.window.setTitle("Error");
            InterfazUsuario.window.show();
        }

    }

    public void crearConsultaFuente(TextField contenido, BooleanClause.Occur operadorLogico) throws Exception{

        try{

            Query q = consulta.get(2).parse("source: "+contenido.getText());

            clausulasBooleanas.add(new BooleanClause(q,operadorLogico));

            bqbuilder.add(clausulasBooleanas.get(clausulasBooleanas.size()-1));

        }catch (Exception e){

            etiquetaError = new Label("Se ha producido un error al crear la consulta para la fuente.");
            plan.getChildren().add(etiquetaError);
            escenaErrorBusquedaBooleana = new Scene(plan,400,400);
            InterfazUsuario.window.setScene(escenaErrorBusquedaBooleana);
            InterfazUsuario.window.setTitle("Error");
            InterfazUsuario.window.show();
        }

    }
}
