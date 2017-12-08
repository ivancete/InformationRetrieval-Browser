import javafx.application.Application;
import javafx.stage.Stage;

public class InterfazUsuario extends Application{

    static Stage window;
    EscenaPrincipal escenaprincipal;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage)throws Exception {

        window = primaryStage;

        escenaprincipal = EscenaPrincipal.devolverEscena();

        window.setScene(escenaprincipal.devolverContenidoEscena());

        window.show();
    }
}

