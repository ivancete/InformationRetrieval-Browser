import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import org.apache.lucene.facet.FacetsConfig;
import org.apache.lucene.facet.taxonomy.TaxonomyReader;
import org.apache.lucene.facet.taxonomy.directory.DirectoryTaxonomyReader;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.store.FSDirectory;

import java.nio.file.Paths;
import java.util.ArrayList;

public class EscenaPrincipal implements EventHandler<ActionEvent> {

    private static EscenaPrincipal escenaprincipal;

    BusquedaLibre busquedaLibre;
    BusquedaBooleana busquedaBooleana;

    ScrollPane barraScrollVertical;
    GridPane layout;

    Scene scene;

    Button botonBuscar, botonMas, botonMenos;
    ArrayList<ChoiceBox<String> > cbCampos;
    ArrayList<ChoiceBox<String> > cbBooleanos;
    ArrayList<TextField> campoTextoBuscar;
    TextField campoTextoPublished1, campoTextoPublished2, campoTextoCited1, campoTextoCited2;
    Label etiquetaTextoPublished1, etiquetaTextoPublished2, etiquetaTextoCited1, etiquetaTextoCited2;

    //Variables para leer del indice.
    static IndexSearcher searcher;
    static IndexReader reader;
    static FSDirectory directorioIndice;
    static FSDirectory taxodir;
    static TaxonomyReader taxonomyReader;
    static FacetsConfig fconfig;

    static BooleanQuery bq;

    public EscenaPrincipal() throws Exception{

        directorioIndice = FSDirectory.open(Paths.get(System.getProperty("user.dir") + "/indiceNuevo/"));

        taxodir = FSDirectory.open(Paths.get(System.getProperty("user.dir") + "/facetasNuevo/"));

        reader = DirectoryReader.open(directorioIndice);

        searcher = new IndexSearcher(reader);

        taxonomyReader = new DirectoryTaxonomyReader(taxodir);

        fconfig = new FacetsConfig();

        busquedaLibre = new BusquedaLibre();
        busquedaBooleana = new BusquedaBooleana();

        cbCampos = new ArrayList<>();
        cbBooleanos = new ArrayList<>();
        campoTextoBuscar = new ArrayList<>();

        barraScrollVertical = new ScrollPane();

        layout = new GridPane();
        layout.setPadding(new Insets(10,10,10,10));
        layout.setHgap(10);
        layout.setVgap(10);

        this.crearEtiquetasPublishedCited(layout);
        this.crearBotones(layout);
        this.crearChoiceBox(layout);
        this.crearCamposTexto(layout);
        this.crearCampoTextoPublished(layout);
        this.crearCampoTextoCited(layout);

        barraScrollVertical.setContent(layout);

        scene = new Scene(barraScrollVertical, 800, 600);
        scene.getStylesheets().add("estiloEscenaPrincipal.css");
    }

    final static public EscenaPrincipal devolverEscena() throws Exception{

        if (escenaprincipal == null){
            escenaprincipal = new EscenaPrincipal();
        }

        return escenaprincipal;
    }

    public Scene devolverContenidoEscena() throws Exception{
        return scene;
    }

    @Override
    public void handle(ActionEvent event){

        if (event.getSource()==botonBuscar && cbBooleanos.size() > 0){

            try {
                busquedaBooleana.busquedaBooleana(cbCampos, campoTextoBuscar, cbBooleanos, campoTextoPublished1, campoTextoPublished2,
                        campoTextoCited1, campoTextoCited2);
            }catch (Exception e){

            }

        }
        else if(event.getSource()==botonBuscar && cbBooleanos.size() == 0){

            try {

                busquedaLibre.busquedaLibre(cbCampos.get(0), campoTextoBuscar.get(0), campoTextoPublished1, campoTextoPublished2,
                        campoTextoCited1, campoTextoCited2);

            }catch (Exception e){

            }

        }
    }

    public void crearBotones(GridPane layout){

        botonBuscar = new Button("Search");
        botonBuscar.setId("botonBuscar");
        botonBuscar.setPrefWidth(100);
        GridPane.setConstraints(botonBuscar, 3,1);

        botonBuscar.setOnAction(this);

        botonMas = new Button("+");
        botonMas.setId("botonMas");
        botonMas.setPrefWidth(50);
        GridPane.setConstraints(botonMas, 4,1);

        botonMas.setOnMouseClicked(e -> busquedaBooleana.incrementarBusqueda(cbCampos, campoTextoBuscar, cbBooleanos
                , botonMenos, layout, campoTextoPublished1, campoTextoPublished2, campoTextoCited1, campoTextoCited2,
                etiquetaTextoPublished1, etiquetaTextoPublished2, etiquetaTextoCited1, etiquetaTextoCited2));

        botonMenos = new Button("-");
        botonMenos.setId("botonMenos");
        botonMenos.setPrefWidth(50);
        botonMenos.setVisible(false);
        GridPane.setConstraints(botonMenos, 5,1);

        botonMenos.setOnMouseClicked(e -> busquedaBooleana.decrementarBusqueda(cbCampos, campoTextoBuscar, cbBooleanos
                , botonMenos, layout, campoTextoPublished1, campoTextoPublished2, campoTextoCited1, campoTextoCited2,
                etiquetaTextoPublished1, etiquetaTextoPublished2, etiquetaTextoCited1, etiquetaTextoCited2));

        layout.getChildren().addAll(botonBuscar, botonMas, botonMenos);
    }

    public void crearChoiceBox(GridPane layout){

        cbCampos.add(new ChoiceBox(FXCollections.observableArrayList(
                "All Fields", "Title", "Abstract", "Author" , "Source", "Keywords Index", "Keywords Author")
        ));

        cbCampos.get(0).setId("cbNormal0");
        cbCampos.get(0).setPrefWidth(150);
        cbCampos.get(0).getSelectionModel().selectFirst();
        GridPane.setConstraints(cbCampos.get(0), 1,1);

        layout.getChildren().add(cbCampos.get(0));
    }

    public void crearCamposTexto(GridPane layout){

        campoTextoBuscar.add(new TextField());
        campoTextoBuscar.get(0).setId("fieldSearch0");
        campoTextoBuscar.get(0).setMaxWidth(350);
        campoTextoBuscar.get(0).setPrefWidth(300);
        GridPane.setConstraints(campoTextoBuscar.get(0), 2,1);

        layout.getChildren().add(campoTextoBuscar.get(0));
    }

    public void crearCampoTextoPublished(GridPane layout){
        campoTextoPublished1 = new TextField();
        campoTextoPublished1.setId("fieldPublished0");
        campoTextoPublished1.setMaxWidth(50);
        campoTextoPublished1.setPrefWidth(50);
        GridPane.setConstraints(campoTextoPublished1, 2,20);

        campoTextoPublished2 = new TextField();
        campoTextoPublished2.setId("fieldPublished1");
        campoTextoPublished2.setMaxWidth(50);
        campoTextoPublished2.setPrefWidth(50);
        GridPane.setConstraints(campoTextoPublished2, 4,20);

        layout.getChildren().addAll(campoTextoPublished1, campoTextoPublished2);
    }

    public void crearCampoTextoCited(GridPane layout){
        campoTextoCited1 = new TextField();
        campoTextoCited1.setId("fieldCited0");
        campoTextoCited1.setMaxWidth(50);
        campoTextoCited1.setPrefWidth(50);
        GridPane.setConstraints(campoTextoCited1, 2,21);

        campoTextoCited2 = new TextField();
        campoTextoCited2.setId("fieldCited1");
        campoTextoCited2.setMaxWidth(50);
        campoTextoCited2.setPrefWidth(50);
        GridPane.setConstraints(campoTextoCited2, 4,21);

        layout.getChildren().addAll(campoTextoCited1, campoTextoCited2);
    }

    public void crearEtiquetasPublishedCited(GridPane layout){

        etiquetaTextoPublished1 = new Label("Published");
        etiquetaTextoPublished1.setId("labelPublished0");
        GridPane.setConstraints(etiquetaTextoPublished1, 1,20);

        etiquetaTextoPublished2 = new Label("to");
        etiquetaTextoPublished2.setId("labelPublished1");
        GridPane.setConstraints(etiquetaTextoPublished2, 3,20);

        etiquetaTextoCited1 = new Label("Cited by");
        etiquetaTextoCited1.setId("labelCited0");
        GridPane.setConstraints(etiquetaTextoCited1, 1,21);

        etiquetaTextoCited2 = new Label("to");
        etiquetaTextoCited2.setId("labelCited1");
        GridPane.setConstraints(etiquetaTextoCited2, 3,21);

        layout.getChildren().addAll(etiquetaTextoPublished1, etiquetaTextoPublished2, etiquetaTextoCited1,
                etiquetaTextoCited2);
    }


}
