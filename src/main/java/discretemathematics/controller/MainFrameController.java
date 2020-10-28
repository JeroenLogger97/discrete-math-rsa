package discretemathematics.controller;

import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Tab;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MainFrameController implements Initializable {
    
    public Tab encryptionTab;
    public Tab decryptionTab;
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            AnchorPane anchorPaneOne = FXMLLoader.load(getClass().getResource("/encryption.fxml"));
            encryptionTab.setContent(anchorPaneOne);
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        try {
            AnchorPane anchorPaneTwo = FXMLLoader.load(getClass().getResource("/decryption.fxml"));
            decryptionTab.setContent(anchorPaneTwo);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
