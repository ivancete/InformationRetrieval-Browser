import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;



public class EscenaResultadosTexto implements EventHandler<ActionEvent> {

    TableView<Documento> tablaDocumentos;
    Button botonVolver;

    public EscenaResultadosTexto(){
    }

    public void crearTablaDatos(Stage window, ObservableList<Documento> listaDocumentos){

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

        tablaDocumentos.setItems(listaDocumentos);

        tablaDocumentos.getColumns().addAll(autoresCol,tituloCol,resumenCol, fuenteCol, linkCol, palabrasClaveAutorCol,
                palabrasClaveIndiceCol, fechaPublicacionCol, citadoPorCol);

        tablaDocumentos.setEditable(false);

        VBox root = new VBox();
        root.getChildren().add(tablaDocumentos);

        botonVolver = new Button("Volver a buscar");
        root.getChildren().add(botonVolver);

        Scene escenaTablaDatos = new Scene(root);

        window.setScene(escenaTablaDatos);
        window.show();
    }

    @Override
    public void handle(ActionEvent event){

        if (event.getSource()==botonVolver){

        }
    }
}


