package ro.ctrln.javafx.calculator;

import javafx.scene.Scene;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class ShapesUtils {
    public static void showPrimaryStage(Stage primaryStage, Scene scene, String title) {
        primaryStage.setTitle(title);
        primaryStage.setScene(scene);
//        primaryStage.setX(Screen.getScreens().get(0).getBounds().getMinX() +200);//setare pe care ecrean
//        primaryStage.setY(Screen.getScreens().get(0).getBounds().getMinY() +200);// vrei sa afisezi
        primaryStage.show();
    }
}
