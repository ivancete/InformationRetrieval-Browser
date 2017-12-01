import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.store.FSDirectory;
import java.nio.file.Paths;
import java.util.ArrayList;

public class InterfazUsuario extends Application {

    BusquedaLibre busquedaLibre;
    BusquedaBooleana busquedaBooleana;
    Stage window;
    Scene scene;
    Button botonBuscar, botonMas, botonMenos;
    GridPane layout;
    ArrayList<ChoiceBox<String> > cbCampos;
    ArrayList<ChoiceBox<String> > cbBooleanos;
    ArrayList<TextField> campoTextoBuscar;
    TextField campoTextoPublished1, campoTextoPublished2, campoTextoCited1, campoTextoCited2;
    Label etiquetaTextoPublished1, etiquetaTextoPublished2, etiquetaTextoCited1, etiquetaTextoCited2;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage)throws Exception {

        FSDirectory directorioIndice = FSDirectory.open(Paths.get(System.getProperty("user.dir") + "/indice/"));

        IndexReader reader = DirectoryReader.open(directorioIndice);

        IndexSearcher searcher = new IndexSearcher(reader);

        busquedaLibre = new BusquedaLibre();
        busquedaBooleana = new BusquedaBooleana();

        cbCampos = new ArrayList<>();
        cbBooleanos = new ArrayList<>();
        campoTextoBuscar = new ArrayList<>();

        window = primaryStage;
        window.setTitle("Browser Article");

        layout = new GridPane();
        layout.setPadding(new Insets(2,2,2,2));

        this.createLabelPublishedCited(layout);
        this.createButton(layout);
        this.createChoiceBox(layout);
        this.createFieldSearch(layout);
        this.createFieldsPublished(layout);
        this.createFieldsCited(layout);


        scene = new Scene(layout, 800, 600);
        window.setScene(scene);
        window.show();

    }

    public void createButton(GridPane layout){

        botonBuscar = new Button("Search");
        botonBuscar.setId("botonBuscar");
        botonBuscar.setPrefWidth(100);
        botonBuscar.setTranslateX(510);
        botonBuscar.setTranslateY(10);

        botonBuscar.setOnMouseClicked(e -> busquedaLibre.busquedaLibre(cbCampos,
                campoTextoBuscar, cbBooleanos));

        botonMas = new Button("+");
        botonMas.setId("botonMas");
        botonMas.setPrefWidth(50);
        botonMas.setTranslateX(650);
        botonMas.setTranslateY(10);

        botonMas.setOnMouseClicked(e -> busquedaBooleana.incrementarBusqueda(cbCampos, campoTextoBuscar, cbBooleanos
                , botonMenos, layout));

        botonMenos = new Button("-");
        botonMenos.setId("botonMenos");
        botonMenos.setPrefWidth(50);
        botonMenos.setTranslateX(710);
        botonMenos.setTranslateY(10);
        botonMenos.setVisible(false);

        botonMenos.setOnMouseClicked(e -> busquedaBooleana.decrementarBusqueda(cbCampos, campoTextoBuscar, cbBooleanos
                , botonMenos, layout));

        layout.getChildren().addAll(botonBuscar, botonMas, botonMenos);
    }

    public void createChoiceBox(GridPane layout){

        cbCampos.add(new ChoiceBox(FXCollections.observableArrayList(
                "All Fields", "Title", "Abstract", "Author" , "Source")
        ));

        cbCampos.get(0).setId("cbNormal0");
        cbCampos.get(0).setTranslateX(20);
        cbCampos.get(0).setTranslateY(10);
        cbCampos.get(0).setPrefWidth(150);
        cbCampos.get(0).getSelectionModel().selectFirst();

        layout.getChildren().add(cbCampos.get(0));
    }

    public void createFieldSearch(GridPane layout){

        campoTextoBuscar.add(new TextField());
        campoTextoBuscar.get(0).setId("fieldSearch0");
        campoTextoBuscar.get(0).setMaxWidth(350);
        campoTextoBuscar.get(0).setPrefWidth(300);
        campoTextoBuscar.get(0).setTranslateX(200);
        campoTextoBuscar.get(0).setTranslateY(10);
        layout.getChildren().add(campoTextoBuscar.get(0));
    }

    public void createFieldsPublished(GridPane layout){
        campoTextoPublished1 = new TextField();
        campoTextoPublished1.setId("fieldPublished0");
        campoTextoPublished1.setMaxWidth(50);
        campoTextoPublished1.setPrefWidth(10);
        campoTextoPublished1.setTranslateX(250);
        campoTextoPublished1.setTranslateY(400);

        campoTextoPublished2 = new TextField();
        campoTextoPublished2.setId("fieldPublished1");
        campoTextoPublished2.setMaxWidth(50);
        campoTextoPublished2.setPrefWidth(10);
        campoTextoPublished2.setTranslateX(400);
        campoTextoPublished2.setTranslateY(400);
        layout.getChildren().addAll(campoTextoPublished1, campoTextoPublished2);
    }

    public void createFieldsCited(GridPane layout){
        campoTextoCited1 = new TextField();
        campoTextoCited1.setId("fieldCited0");
        campoTextoCited1.setMaxWidth(50);
        campoTextoCited1.setPrefWidth(10);
        campoTextoCited1.setTranslateX(250);
        campoTextoCited1.setTranslateY(500);

        campoTextoCited2 = new TextField();
        campoTextoCited2.setId("fieldCited1");
        campoTextoCited2.setMaxWidth(50);
        campoTextoCited2.setPrefWidth(10);
        campoTextoCited2.setTranslateX(400);
        campoTextoCited2.setTranslateY(500);
        layout.getChildren().addAll(campoTextoCited1, campoTextoCited2);
    }

    public void createLabelPublishedCited(GridPane layout){

        etiquetaTextoPublished1 = new Label("Published");
        etiquetaTextoPublished1.setId("labelPublished0");
        etiquetaTextoPublished1.setTranslateX(180);
        etiquetaTextoPublished1.setTranslateY(400);

        etiquetaTextoPublished2 = new Label("to");
        etiquetaTextoPublished2.setId("labelPublished1");
        etiquetaTextoPublished2.setTranslateX(350);
        etiquetaTextoPublished2.setTranslateY(400);

        etiquetaTextoCited1 = new Label("Cited by");
        etiquetaTextoCited1.setId("labelCited0");
        etiquetaTextoCited1.setTranslateX(180);
        etiquetaTextoCited1.setTranslateY(500);

        etiquetaTextoCited2 = new Label("to");
        etiquetaTextoCited2.setId("labelCited1");
        etiquetaTextoCited2.setTranslateX(350);
        etiquetaTextoCited2.setTranslateY(500);

        layout.getChildren().addAll(etiquetaTextoPublished1, etiquetaTextoPublished2, etiquetaTextoCited1,
                etiquetaTextoCited2);
    }
}

