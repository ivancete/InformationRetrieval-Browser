import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;

public class EscenaResultadosTexto{

    TableView<Documento> tablaDocumentos;
    Button botonVolver;
    Label totalDocumentosRescatados;

    public EscenaResultadosTexto(){

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

        tablaDocumentos.getColumns().addAll(autoresCol,tituloCol,resumenCol, fuenteCol, linkCol, palabrasClaveAutorCol,
                palabrasClaveIndiceCol, fechaPublicacionCol, citadoPorCol);

        tablaDocumentos.setEditable(false);
    }

    public void crearTablaDatos(ObservableList<Documento> listaDocumentos) throws Exception{

        tablaDocumentos.setItems(listaDocumentos);

        VBox root = new VBox();
        root.getChildren().add(tablaDocumentos);

        botonVolver = new Button("Volver a buscar");

        //Recuperamos la escena principal, y decimos que si se presiona el boton de volver, se vuelva a cargar esa escena.
        Scene escena = EscenaPrincipal.devolverEscena().devolverContenidoEscena();

        botonVolver.setOnAction(e -> InterfazUsuario.window.setScene(escena));

        root.getChildren().add(botonVolver);

        totalDocumentosRescatados = new Label("Total documentos encontrados: "+Integer.toString(listaDocumentos.size()));

        root.getChildren().add(totalDocumentosRescatados);

        Scene escenaTablaDatos = new Scene(root);

        InterfazUsuario.window.setScene(escenaTablaDatos);
        InterfazUsuario.window.show();
    }
}


