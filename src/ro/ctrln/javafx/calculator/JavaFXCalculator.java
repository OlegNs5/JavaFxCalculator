package ro.ctrln.javafx.calculator;

import com.sun.deploy.util.FXLoader;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class JavaFXCalculator extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("Calculator.fxml"));
        Scene scene = new Scene(root,400,400);

        ShapesUtils.showPrimaryStage(primaryStage,scene,"SceneBuilder Calculator ");
    }
}
