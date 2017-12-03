import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.store.FSDirectory;

import java.nio.file.Paths;
import java.util.ArrayList;

public class InterfazUsuario extends Application implements EventHandler<ActionEvent>{

    BusquedaLibre busquedaLibre;
    BusquedaBooleana busquedaBooleana;
    Stage window;
    Scene scene;
    Button botonBuscar, botonMas, botonMenos;
    ScrollPane barraScrollVertical;
    GridPane layout;
    ArrayList<ChoiceBox<String> > cbCampos;
    ArrayList<ChoiceBox<String> > cbBooleanos;
    ArrayList<TextField> campoTextoBuscar;
    TextField campoTextoPublished1, campoTextoPublished2, campoTextoCited1, campoTextoCited2;
    Label etiquetaTextoPublished1, etiquetaTextoPublished2, etiquetaTextoCited1, etiquetaTextoCited2;
    IndexSearcher searcher;
    IndexReader reader;
    FSDirectory directorioIndice;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage)throws Exception {

        directorioIndice = FSDirectory.open(Paths.get(System.getProperty("user.dir") + "/indice/"));

        reader = DirectoryReader.open(directorioIndice);

        searcher = new IndexSearcher(reader);

        busquedaLibre = new BusquedaLibre();
        busquedaBooleana = new BusquedaBooleana();

        cbCampos = new ArrayList<>();
        cbBooleanos = new ArrayList<>();
        campoTextoBuscar = new ArrayList<>();

        barraScrollVertical = new ScrollPane();

        window = primaryStage;
        window.setTitle("Article Browser");

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
        window.setScene(scene);
        window.show();

    }

    @Override
    public void handle(ActionEvent event){

        if (event.getSource()==botonBuscar && cbBooleanos.size() > 0){

            try {
                busquedaBooleana.busquedaBooleana(this);
            }catch (Exception e){

            }

        }
        else if(event.getSource()==botonBuscar && cbBooleanos.size() == 0){

            try {

                busquedaLibre.busquedaLibre(cbCampos.get(0), campoTextoBuscar.get(0), searcher, window);

            }catch (Exception e){

            }

        }
    }

    public void crearBotones(GridPane layout){

        botonBuscar = new Button("Search");
        botonBuscar.setId("botonBuscar");
        botonBuscar.setPrefWidth(100);
        /*botonBuscar.setTranslateX(510);
        botonBuscar.setTranslateY(10);*/
        GridPane.setConstraints(botonBuscar, 3,1);

        botonBuscar.setOnAction(this);

        botonMas = new Button("+");
        botonMas.setId("botonMas");
        botonMas.setPrefWidth(50);
        /*botonMas.setTranslateX(650);
        botonMas.setTranslateY(10);*/
        GridPane.setConstraints(botonMas, 4,1);

        botonMas.setOnMouseClicked(e -> busquedaBooleana.incrementarBusqueda(cbCampos, campoTextoBuscar, cbBooleanos
                , botonMenos, layout, campoTextoPublished1, campoTextoPublished2, campoTextoCited1, campoTextoCited2,
                etiquetaTextoPublished1, etiquetaTextoPublished2, etiquetaTextoCited1, etiquetaTextoCited2, window));

        botonMenos = new Button("-");
        botonMenos.setId("botonMenos");
        botonMenos.setPrefWidth(50);
        /*botonMenos.setTranslateX(710);
        botonMenos.setTranslateY(10);*/
        botonMenos.setVisible(false);
        GridPane.setConstraints(botonMenos, 5,1);

        botonMenos.setOnMouseClicked(e -> busquedaBooleana.decrementarBusqueda(cbCampos, campoTextoBuscar, cbBooleanos
                , botonMenos, layout, campoTextoPublished1, campoTextoPublished2, campoTextoCited1, campoTextoCited2,
                etiquetaTextoPublished1, etiquetaTextoPublished2, etiquetaTextoCited1, etiquetaTextoCited2, window));

        layout.getChildren().addAll(botonBuscar, botonMas, botonMenos);
    }

    public void crearChoiceBox(GridPane layout){

        cbCampos.add(new ChoiceBox(FXCollections.observableArrayList(
                "All Fields", "Title", "Abstract", "Author" , "Source", "Keywords")
        ));

        cbCampos.get(0).setId("cbNormal0");
        /*cbCampos.get(0).setTranslateX(20);
        cbCampos.get(0).setTranslateY(10);*/
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
        /*campoTextoBuscar.get(0).setTranslateX(200);
        campoTextoBuscar.get(0).setTranslateY(10);*/
        GridPane.setConstraints(campoTextoBuscar.get(0), 2,1);

        layout.getChildren().add(campoTextoBuscar.get(0));
    }

    public void crearCampoTextoPublished(GridPane layout){
        campoTextoPublished1 = new TextField();
        campoTextoPublished1.setId("fieldPublished0");
        campoTextoPublished1.setMaxWidth(50);
        campoTextoPublished1.setPrefWidth(50);
        /*campoTextoPublished1.setTranslateX(250);
        campoTextoPublished1.setTranslateY(400);*/
        GridPane.setConstraints(campoTextoPublished1, 2,5);

        campoTextoPublished2 = new TextField();
        campoTextoPublished2.setId("fieldPublished1");
        campoTextoPublished2.setMaxWidth(50);
        campoTextoPublished2.setPrefWidth(50);
        /*campoTextoPublished2.setTranslateX(400);
        campoTextoPublished2.setTranslateY(400);*/
        GridPane.setConstraints(campoTextoPublished2, 4,5);

        layout.getChildren().addAll(campoTextoPublished1, campoTextoPublished2);
    }

    public void crearCampoTextoCited(GridPane layout){
        campoTextoCited1 = new TextField();
        campoTextoCited1.setId("fieldCited0");
        campoTextoCited1.setMaxWidth(50);
        campoTextoCited1.setPrefWidth(50);
        /*campoTextoCited1.setTranslateX(250);
        campoTextoCited1.setTranslateY(500);*/
        GridPane.setConstraints(campoTextoCited1, 2,6);

        campoTextoCited2 = new TextField();
        campoTextoCited2.setId("fieldCited1");
        campoTextoCited2.setMaxWidth(50);
        campoTextoCited2.setPrefWidth(50);
        /*campoTextoCited2.setTranslateX(400);
        campoTextoCited2.setTranslateY(500);*/
        GridPane.setConstraints(campoTextoCited2, 4,6);

        layout.getChildren().addAll(campoTextoCited1, campoTextoCited2);
    }

    public void crearEtiquetasPublishedCited(GridPane layout){

        etiquetaTextoPublished1 = new Label("Published");
        etiquetaTextoPublished1.setId("labelPublished0");
        /*etiquetaTextoPublished1.setTranslateX(180);
        etiquetaTextoPublished1.setTranslateY(400);*/
        GridPane.setConstraints(etiquetaTextoPublished1, 1,5);

        etiquetaTextoPublished2 = new Label("to");
        etiquetaTextoPublished2.setId("labelPublished1");
        /*etiquetaTextoPublished2.setTranslateX(350);
        etiquetaTextoPublished2.setTranslateY(400);*/
        GridPane.setConstraints(etiquetaTextoPublished2, 3,5);

        etiquetaTextoCited1 = new Label("Cited by");
        etiquetaTextoCited1.setId("labelCited0");
        /*etiquetaTextoCited1.setTranslateX(180);
        etiquetaTextoCited1.setTranslateY(500);*/
        GridPane.setConstraints(etiquetaTextoCited1, 1,6);

        etiquetaTextoCited2 = new Label("to");
        etiquetaTextoCited2.setId("labelCited1");
        /*etiquetaTextoCited2.setTranslateX(350);
        etiquetaTextoCited2.setTranslateY(500);*/
        GridPane.setConstraints(etiquetaTextoCited2, 3,6);

        layout.getChildren().addAll(etiquetaTextoPublished1, etiquetaTextoPublished2, etiquetaTextoCited1,
                etiquetaTextoCited2);
    }

    public void volverAtras(){
        window.setScene(scene);
        window.show();
    }
}

