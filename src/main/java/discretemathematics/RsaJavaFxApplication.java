package discretemathematics;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;

public class RsaJavaFxApplication extends Application {
    
    public static void main(String[] args) {
        launch(args);
    }
    
    @Override
    public void start(Stage primaryStage) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/MainFrame.fxml"));
        
//        URL old = getClass().getClassLoader().getResource("/MainFrame.fxml");
//        URL oldNoPrecedingSlash = getClass().getClassLoader().getResource("MainFrame.fxml");
//        URL fullPathFromSrc = getClass().getClassLoader().getResource("src/main/resources/MainFrame.fxml");
//        URL fullPathFromSrcPrecedingSlash = getClass().getClassLoader().getResource("/src/main/resources/MainFrame.fxml");
//
//        System.out.println("old                           = " + old);
//        System.out.println("oldNoPrecedingSlash           = " + oldNoPrecedingSlash);
//        System.out.println("fullPathFromSrc               = " + fullPathFromSrc);
//        System.out.println("fullPathFromSrcPrecedingSlash = " + fullPathFromSrcPrecedingSlash);
//
//        Parent root = FXMLLoader.load(old);
    
        Scene scene = new Scene(root, root.prefWidth(400), root.prefHeight(400));
    
        primaryStage.setTitle("Discrete Mathematics PA3 - Sebastiaan Westheim en Jeroen Logger");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
