package View;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.File;
import java.net.URL;

public class MainInterpreter extends Application {

        public static void main(String[] args) {
            launch(args);
        }

        @Override
        public void start(Stage mainStage) throws Exception {

            // Read file fxml and draw interface.
            Parent root = FXMLLoader.load(getClass().getResource("MainLayout.fxml"));
            Scene scene = new Scene(root, 800, 250, Color.RED);
            mainStage.setTitle("Toy Language - Please select a program");
            mainStage.setScene(scene);
            mainStage.show();
        }

}


