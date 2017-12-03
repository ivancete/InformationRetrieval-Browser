import javafx.collections.FXCollections;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.util.ArrayList;

public class BusquedaBooleana {

    Label etiquetaError;
    GridPane plan;
    Scene escenaErrorBusquedaBooleana;

    public BusquedaBooleana(){

        plan = new GridPane();
    }

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
                                    Label etiquetaTextoCited2,
                                    Stage window){

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
                    "All Fields", "Title", "Abstract", "Author", "Source")
            ));

            cbCampos.get(total).setId("cbNormal" + total);
            /*cbCampos.get(total).setTranslateX(20);
            cbCampos.get(total).setTranslateY(10 + 100 * total);*/
            GridPane.setConstraints(cbCampos.get(total), 1, total + 1);
            cbCampos.get(total).setPrefWidth(150);
            cbCampos.get(total).getSelectionModel().selectFirst();

            campoTextoBuscar.add(new TextField());
            campoTextoBuscar.get(total).setId("fieldSearch" + total);
            /*campoTextoBuscar.get(total).setTranslateX(200);
            campoTextoBuscar.get(total).setTranslateY(10 + 100 * total);*/
            GridPane.setConstraints(campoTextoBuscar.get(total), 2, total + 1);
            campoTextoBuscar.get(total).setMaxWidth(300);
            campoTextoBuscar.get(total).setPrefWidth(300);

            //Decrementamos la variable total porque no empezamos con 1 choicebox desde el inicio de la aplicación.
            total--;

            cbBooleanos.add(new ChoiceBox(FXCollections.observableArrayList(
                    "AND", "OR", "NOT")));
            cbBooleanos.get(total).setId("cbBooleano" + total);
            /*cbBooleanos.get(total).setTranslateX(500);
            cbBooleanos.get(total).setTranslateY(10 + 100 * (total + 1));*/
            GridPane.setConstraints(cbBooleanos.get(total), 3, total + 2);
            cbBooleanos.get(total).setPrefWidth(100);
            cbBooleanos.get(total).getSelectionModel().selectFirst();

            layout.getChildren().addAll(cbCampos.get(total + 1), campoTextoBuscar.get(total + 1), cbBooleanos.get(total));
        }catch (Exception e){

            etiquetaError = new Label("Se ha producido un error al incrementar las cajas de búsqueda.");
            plan.getChildren().add(etiquetaError);
            escenaErrorBusquedaBooleana = new Scene(plan,400,400);
            window.setScene(escenaErrorBusquedaBooleana);
            window.setTitle("Error");
            window.show();
        }
    }

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
                                    Label etiquetaTextoCited2,
                                    Stage window){

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
            window.setScene(escenaErrorBusquedaBooleana);
            window.setTitle("Error");
            window.show();
        }
    }

    /*public void busquedaBooleana(ArrayList<ChoiceBox<String> > cbCampos,
                                 ArrayList<TextField> campoTextoBuscar,
                                 ArrayList<ChoiceBox<String> > cbBooleanos,
                                 IndexSearcher searcher){*/
    public void busquedaBooleana(InterfazUsuario interfazUsuario){


        try{

        }catch (Exception e){

            etiquetaError = new Label("Se ha producido un error al realizar la búsqueda Booleana.");
            plan.getChildren().add(etiquetaError);
            escenaErrorBusquedaBooleana = new Scene(plan,400,400);
            interfazUsuario.window.setScene(escenaErrorBusquedaBooleana);
            interfazUsuario.window.setTitle("Error");
            interfazUsuario.window.show();
        }

    }
}
