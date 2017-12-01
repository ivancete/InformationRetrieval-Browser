import javafx.collections.FXCollections;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import java.util.ArrayList;

public class BusquedaBooleana {

    public BusquedaBooleana(){

    }

    public void incrementarBusqueda(ArrayList<ChoiceBox<String> > cbCampos,
                                    ArrayList<TextField> campoTextoBuscar,
                                    ArrayList<ChoiceBox<String> > cbBooleanos,
                                    Button botonMenos,
                                    GridPane layout){

        if (campoTextoBuscar.size() == 1){
            botonMenos.setVisible(true);
        }

        int total = cbCampos.size();

        cbCampos.add(new ChoiceBox(FXCollections.observableArrayList(
                "All Fields", "Title", "Abstract", "Author" , "Source")
        ));

        cbCampos.get(total).setId("cbNormal"+total);
        cbCampos.get(total).setTranslateX(20);
        cbCampos.get(total).setTranslateY(10 + 100 * total);
        cbCampos.get(total).setPrefWidth(150);
        cbCampos.get(total).getSelectionModel().selectFirst();

        campoTextoBuscar.add(new TextField());
        campoTextoBuscar.get(total).setId("fieldSearch"+total);
        campoTextoBuscar.get(total).setTranslateX(200);
        campoTextoBuscar.get(total).setTranslateY(10 + 100 * total);
        campoTextoBuscar.get(total).setMaxWidth(300);
        campoTextoBuscar.get(total).setPrefWidth(300);

        //Decrementamos la variable total porque no empezamos con 1 choicebox desde el inicio de la aplicación.
        total--;

        cbBooleanos.add(new ChoiceBox(FXCollections.observableArrayList(
                "AND", "OR", "NOT")));
        cbBooleanos.get(total).setId("cbBooleano"+total);
        cbBooleanos.get(total).setTranslateX(500);
        cbBooleanos.get(total).setTranslateY(10 + 100 * (total + 1));
        cbBooleanos.get(total).setPrefWidth(100);
        cbBooleanos.get(total).getSelectionModel().selectFirst();

        layout.getChildren().addAll(cbCampos.get(total+1), campoTextoBuscar.get(total+1), cbBooleanos.get(total));
    }

    public void decrementarBusqueda(ArrayList<ChoiceBox<String> > cbCampos,
                                    ArrayList<TextField> campoTextoBuscar,
                                    ArrayList<ChoiceBox<String> > cbBooleanos,
                                    Button botonMenos,
                                    GridPane layout){

        int total = campoTextoBuscar.size();

        if (total == 2){
            botonMenos.setVisible(false);
        }

        layout.getChildren().removeAll(cbCampos.get(total-1), campoTextoBuscar.get(total-1), cbBooleanos.get(total-2));

        cbCampos.remove(total-1);
        campoTextoBuscar.remove(total-1);
        cbBooleanos.remove(total-2);
    }
}
