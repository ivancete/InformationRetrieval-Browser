import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.apache.lucene.document.Document;
import org.apache.lucene.facet.*;
import org.apache.lucene.facet.taxonomy.FastTaxonomyFacetCounts;
import org.apache.lucene.search.ScoreDoc;

import java.util.ArrayList;
import java.util.List;

public class EscenaResultadosTexto{

    Stage ventana;
    ScrollPane barraScrollVertical;

    Label etiquetaError;

    //Este es el plan para cuando ocurre un error.
    GridPane plan;

    TableView<Documento> tablaDocumentos;

    Button botonVolver;
    Label totalDocumentosRescatados;

    Facets facetas;
    List<FacetResult> todasDimensiones;

    ArrayList<VBox> contenido;
    ArrayList<TitledPane> tituloPanel;

    HBox escenaHorizontal;
    VBox escenaVerticalFacetas, zonaizquierda;

    Scene escenaTablaDatos, escenaDocumento;

    ScrollPane sp;

    public EscenaResultadosTexto(){

        ventana = new Stage();

        plan = new GridPane();

        //Parte de las facetas

        escenaHorizontal = new HBox();
        escenaHorizontal.minWidth(600);
        escenaVerticalFacetas  = new VBox();

        zonaizquierda = new VBox();

        sp = new ScrollPane();

        sp.setContent(escenaVerticalFacetas);
        //____________________________________________________________

        TableColumn<Documento, String> autoresCol = new TableColumn<>("Author");
        autoresCol.setCellValueFactory(new PropertyValueFactory<>("autores"));

        TableColumn<Documento, String> tituloCol = new TableColumn<>("Title");
        tituloCol.setCellValueFactory(new PropertyValueFactory<>("titulo"));

        TableColumn<Documento, String> resumenCol = new TableColumn<>("Abstract");
        resumenCol.setCellValueFactory(new PropertyValueFactory<>("resumen"));

        TableColumn<Documento, String> fuenteCol = new TableColumn<>("Source");
        fuenteCol.setCellValueFactory(new PropertyValueFactory<>("fuente"));

        TableColumn<Documento, String> linkCol = new TableColumn<>("Link");
        linkCol.setCellValueFactory(new PropertyValueFactory<>("link"));

        TableColumn<Documento, String> palabrasClaveAutorCol = new TableColumn<>("Keywords Author");
        palabrasClaveAutorCol.setCellValueFactory(new PropertyValueFactory<>("palabrasClaveAutor"));

        TableColumn<Documento, String> palabrasClaveIndiceCol = new TableColumn<>("Keywords Index");
        palabrasClaveIndiceCol.setCellValueFactory(new PropertyValueFactory<>("palabrasClaveIndice"));

        TableColumn<Documento, Integer> fechaPublicacionCol = new TableColumn<>("Date Published");
        fechaPublicacionCol.setCellValueFactory(new PropertyValueFactory<>("fechaPublicacion"));

        TableColumn<Documento, Integer> citadoPorCol = new TableColumn<>("Cited by");
        citadoPorCol.setCellValueFactory(new PropertyValueFactory<>("citadoPor"));

        tablaDocumentos = new TableView<>();

        //Código para mostrar una fila en otra escena diferente.
        tablaDocumentos.setRowFactory(tv -> {
            TableRow<Documento> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (! row.isEmpty() && event.getButton()== MouseButton.PRIMARY
                        && event.getClickCount() == 2) {

                    Documento clickedRow = row.getItem();
                    mostrarFila(clickedRow);
                }
            });
            return row ;
        });

        botonVolver = new Button("Volver a buscar");

        totalDocumentosRescatados = new Label();

        tablaDocumentos.getColumns().addAll(autoresCol,tituloCol,resumenCol, fuenteCol, linkCol, palabrasClaveAutorCol,
                palabrasClaveIndiceCol, fechaPublicacionCol, citadoPorCol);

        tablaDocumentos.setEditable(false);

        zonaizquierda.getChildren().setAll(totalDocumentosRescatados, botonVolver);

        escenaHorizontal.getChildren().setAll(tablaDocumentos, sp, zonaizquierda);

        escenaTablaDatos = new Scene(escenaHorizontal);
    }

    public void mostrarFila(Documento clickedRow){


        Label autor;
        if (clickedRow.getFuente() != null)
            autor = new Label("Autores:: "+clickedRow.getAutores());
        else
            autor = new Label("Autores: No hay autores.");

        Label resumen;
        if (clickedRow.getFuente() != null)
            resumen = new Label("Resumen:: "+clickedRow.getResumen());
        else
            resumen = new Label("Resumen: No hay resumen.");

        Label titulo;
        if (clickedRow.getFuente() != null)
            titulo = new Label("Título:: "+clickedRow.getTitulo());
        else
            titulo = new Label("Título: No hay título.");


        Label citas = new Label("Cited by: "+Integer.toString(clickedRow.getCitadoPor()));
        Label anio = new Label("Año: "+Integer.toString(clickedRow.getFechaPublicacion()));


        Label keywords;
        if (clickedRow.getPalabrasClaveAutor() != null && clickedRow.getPalabrasClaveIndice() != null )
            keywords = new Label("Keywords: "+clickedRow.getPalabrasClaveAutor() + clickedRow.getPalabrasClaveIndice());
        else if (clickedRow.getPalabrasClaveAutor() == null && clickedRow.getPalabrasClaveIndice() != null )
            keywords = new Label("Keywords: " + clickedRow.getPalabrasClaveIndice());
        else if (clickedRow.getPalabrasClaveAutor() != null && clickedRow.getPalabrasClaveIndice() == null )
            keywords = new Label("Keywords: " + clickedRow.getPalabrasClaveAutor());
        else
            keywords = new Label("Keywords:  No hay Keywords." );

        Label link;
        if (clickedRow.getLink() != null)
            link = new Label("Link: "+clickedRow.getLink());
        else
            link = new Label("Link: No hay link.");

        Label source;
        if (clickedRow.getFuente() != null)
            source = new Label("Source: "+clickedRow.getFuente());
        else
            source = new Label("Source: No hay fuente.");



        plan = new GridPane();

        plan.setHgap(10);
        plan.setVgap(10);
        GridPane.setConstraints(titulo, 1,1);
        GridPane.setConstraints(autor, 1,2);
        GridPane.setConstraints(resumen, 1,3);
        GridPane.setConstraints(anio, 1,4);
        GridPane.setConstraints(citas, 1,5);
        GridPane.setConstraints(keywords, 1,6);
        GridPane.setConstraints(link, 1,7);
        GridPane.setConstraints(source, 1,8);

        plan.getChildren().setAll(autor, resumen, titulo, citas, anio, keywords, link, source);
        barraScrollVertical = new ScrollPane();
        barraScrollVertical.setContent(plan);
        barraScrollVertical.setContent(plan);
        escenaDocumento = new Scene(barraScrollVertical, 600, 600);
        ventana.setScene(escenaDocumento);
        ventana.show();
    }

    public void crearTablaDatos(ObservableList<Documento> listaDocumentos, FacetsCollector colectorFacetas) throws Exception{

        try{

            facetas = new FastTaxonomyFacetCounts(EscenaPrincipal.taxonomyReader,EscenaPrincipal.fconfig, colectorFacetas);

            todasDimensiones = facetas.getAllDims(2000);

            crearZonaFacetas();

            Scene escena = EscenaPrincipal.devolverEscena().devolverContenidoEscena();

            botonVolver.setOnAction(e -> InterfazUsuario.window.setScene(escena));

            totalDocumentosRescatados.setText("Total documentos encontrados: "+Integer.toString(listaDocumentos.size()));

            tablaDocumentos.setItems(listaDocumentos);

            System.out.println("Pasa");

            InterfazUsuario.window.setScene(escenaTablaDatos);
            InterfazUsuario.window.show();

        }catch (Exception e){
            etiquetaError = new Label("Se ha producido un error al intentar crear la tabla con los resultados de la búsqueda.");
            plan.getChildren().setAll(etiquetaError);
            escenaTablaDatos = new Scene(plan,400,400);
            InterfazUsuario.window.setScene(escenaTablaDatos);
            InterfazUsuario.window.setTitle("Error");
            InterfazUsuario.window.show();
        }
    }

    public void bajarDimension(String campo, String etiqueta){

        try {

            int anio, citedby;

            DrillDownQuery dq = new DrillDownQuery(EscenaPrincipal.fconfig, EscenaPrincipal.bq);

            dq.add(campo, etiqueta);

            DrillSideways ds = new DrillSideways(EscenaPrincipal.searcher,
                    EscenaPrincipal.fconfig, EscenaPrincipal.taxonomyReader);

            DrillSideways.DrillSidewaysResult dsresult = ds.search(dq, 100);

            ObservableList<Documento> listaDocumentos = FXCollections.observableArrayList();

            for (ScoreDoc sd : dsresult.hits.scoreDocs){

                Document d = EscenaPrincipal.searcher.doc(sd.doc);

                anio = 0;
                if (d.get("year") != null) {
                    anio = Integer.parseInt(d.get("year"));

                }

                citedby = 0;
                if (d.get("cited by") != null) {
                    citedby = Integer.parseInt(d.get("cited by"));

                }

                listaDocumentos.add(new Documento(d.get("author"), d.get("title"), d.get("abstract"), d.get("source"),
                        d.get("link"), d.get("keywords author"), d.get("keywords index"), anio,
                        citedby));
            }

            tablaDocumentos.setItems(listaDocumentos);

            totalDocumentosRescatados.setText("Total documentos encontrados: "+Integer.toString(listaDocumentos.size()));

        }catch (Exception e){
            etiquetaError = new Label("Se ha producido un error al intentar acceder una faceta.");
            plan.getChildren().setAll(etiquetaError);
            escenaTablaDatos = new Scene(plan,400,400);
            InterfazUsuario.window.setScene(escenaTablaDatos);
            InterfazUsuario.window.setTitle("Error");
            InterfazUsuario.window.show();
        }

    }

    public void crearZonaFacetas() throws Exception{

        try {

            contenido = new ArrayList<>();
            tituloPanel = new ArrayList<>();

            Button boton;

            int i = 0;

            for (FacetResult fr : todasDimensiones) {

                if (!fr.dim.contains("keywords")) {

                    tituloPanel.add(new TitledPane());
                    tituloPanel.get(i).setText(fr.dim);
                    tituloPanel.get(i).setExpanded(false);

                    contenido.add(new VBox());

                    for (LabelAndValue lv : fr.labelValues) {

                        boton = new Button(lv.label);
                        boton.setOnAction(e -> bajarDimension(fr.dim, lv.label));

                        contenido.get(i).getChildren().add(boton);

                    }

                    tituloPanel.get(i).setContent(contenido.get(i));

                    i++;
                }
            }

            escenaVerticalFacetas.getChildren().setAll(tituloPanel);
        }

        catch (Exception e){
            etiquetaError = new Label("Se ha producido un error al intentar crear la columna con facetas.");
            plan.getChildren().setAll(etiquetaError);
            escenaTablaDatos = new Scene(plan,400,400);
            InterfazUsuario.window.setScene(escenaTablaDatos);
            InterfazUsuario.window.setTitle("Error");
            InterfazUsuario.window.show();
        }

    }
}