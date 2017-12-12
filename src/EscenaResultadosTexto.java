import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import org.apache.lucene.document.Document;
import org.apache.lucene.facet.*;
import org.apache.lucene.facet.taxonomy.FastTaxonomyFacetCounts;
import org.apache.lucene.search.ScoreDoc;

import java.util.ArrayList;
import java.util.List;

public class EscenaResultadosTexto{

    TableView<Documento> tablaDocumentos;

    Button botonVolver;
    Label totalDocumentosRescatados;

    Facets facetas;
    List<FacetResult> todasDimensiones;

    ArrayList<VBox> contenido;
    ArrayList<TitledPane> tituloPanel;

    HBox escenaHorizontal;

    Scene escenaTablaDatos;

    public EscenaResultadosTexto(){

        contenido = new ArrayList<>();
        tituloPanel = new ArrayList<>();

        escenaHorizontal = new HBox();

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
        botonVolver = new Button("Volver a buscar");

        totalDocumentosRescatados = new Label();

        escenaHorizontal.getChildren().add(totalDocumentosRescatados);

        escenaHorizontal.getChildren().add(botonVolver);

        tablaDocumentos.getColumns().addAll(autoresCol,tituloCol,resumenCol, fuenteCol, linkCol, palabrasClaveAutorCol,
                palabrasClaveIndiceCol, fechaPublicacionCol, citadoPorCol);

        tablaDocumentos.setEditable(false);

        escenaHorizontal.getChildren().add(tablaDocumentos);

        escenaTablaDatos = new Scene(escenaHorizontal);
    }

    public void crearTablaDatos(ObservableList<Documento> listaDocumentos, FacetsCollector colectorFacetas) throws Exception{

        facetas = new FastTaxonomyFacetCounts(EscenaPrincipal.taxonomyReader,EscenaPrincipal.fconfig, colectorFacetas);

        todasDimensiones = facetas.getAllDims(2000);

        crearZonaFacetas();

        Scene escena = EscenaPrincipal.devolverEscena().devolverContenidoEscena();

        botonVolver.setOnAction(e -> InterfazUsuario.window.setScene(escena));

        totalDocumentosRescatados.setText("Total documentos encontrados: "+Integer.toString(listaDocumentos.size()));

        tablaDocumentos.setItems(listaDocumentos);

        InterfazUsuario.window.setScene(escenaTablaDatos);
        InterfazUsuario.window.show();
    }

    public void bajarDimension(String campo, String etiqueta){

        try {

            DrillDownQuery dq = new DrillDownQuery(EscenaPrincipal.fconfig, EscenaPrincipal.bq);

            dq.add(campo, etiqueta);

            DrillSideways ds = new DrillSideways(EscenaPrincipal.searcher,
                    EscenaPrincipal.fconfig, EscenaPrincipal.taxonomyReader);

            DrillSideways.DrillSidewaysResult dsresult = ds.search(dq, 100);

            ObservableList<Documento> listaDocumentos = FXCollections.observableArrayList();

            for (ScoreDoc sd : dsresult.hits.scoreDocs){

                Document d = EscenaPrincipal.searcher.doc(sd.doc);

                listaDocumentos.add(new Documento(d.get("author"), d.get("title"), d.get("abstract"), d.get("source"),
                        d.get("link"), d.get("keywords author"), d.get("keywords index"), Integer.parseInt(d.get("year")),
                        Integer.parseInt(d.get("cited by"))));
            }

            tablaDocumentos.setItems(listaDocumentos);

            totalDocumentosRescatados.setText("Total documentos encontrados: "+Integer.toString(listaDocumentos.size()));

        }catch (Exception e){
            System.out.println("ERROOOOOR");
        }

    }

    public void crearZonaFacetas() throws Exception{

        try {
            Button boton;

            int i = 0;

            for (FacetResult fr : todasDimensiones) {

                if (!fr.dim.contains("keywords")) {

                    tituloPanel.add(new TitledPane());
                    tituloPanel.get(i).setText(fr.dim);
                    tituloPanel.get(i).setMaxWidth(200);
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

            VBox escenaVerticalFacetas = new VBox();

            escenaVerticalFacetas.getChildren().addAll(tituloPanel);

            ScrollPane sp = new ScrollPane();

            sp.setContent(escenaVerticalFacetas);

            escenaHorizontal.getChildren().add(sp);
        }
        catch (Exception e){
            System.out.println("ERROOOOOR");
        }

    }
}