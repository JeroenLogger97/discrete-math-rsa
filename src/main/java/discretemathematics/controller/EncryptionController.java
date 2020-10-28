package discretemathematics.controller;

import discretemathematics.RsaUtil;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Paint;

import java.math.BigInteger;
import java.net.URL;
import java.util.Arrays;
import java.util.ResourceBundle;

public class EncryptionController implements Initializable {
    
    private BigInteger n;
    private BigInteger p;
    private BigInteger q;
    private BigInteger e;
    private long timeTakenToFactorNInMs;
    
    // Step 1
    public Button stepOneButton;
    public Button generateNFromPAndQButton;
    public Label pLabel;
    public Label qLabel;
    public Label timeTakenToFactorizeN;
    public TextField pTextField;
    public TextField qTextField;
    public TextField nTextField;
    
    // Step 2
    public Button stepTwoButton;
    public TextField eTextField;
    public Label eValidityLabel;
    public Label phiLabel;
    public Label phiFactorsLabel;
    
    // Step 3
    public Button stepThreeButton;
    public TextArea messageTextArea;
    public TextArea encodedMessageArea;
    public TextArea encryptedMessageArea;
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        messageTextArea.setText("To live a creative life, we must lose our fear of being wrong. ~Anonymous");
        
        makeTextFieldNumericOnly(pTextField);
        makeTextFieldNumericOnly(qTextField);
        makeTextFieldNumericOnly(nTextField);
        makeTextFieldNumericOnly(eTextField);
    }
    
    public static void makeTextFieldNumericOnly(TextField textField) {
        textField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                textField.setText(newValue.replaceAll("[^\\d]", ""));
            }
        });
    }
    
    //---------- STEP ONE ----------\\
    
    public void stepOneClicked(ActionEvent actionEvent) {
        if (nTextField.getText().isEmpty()) {
            return;
        }
        
        n = new BigInteger(nTextField.getText());
        stepOne_setPrimeFactorsOfNAndSetLabels();
        
        if (p != null && q != null) {
            pTextField.setText(p.toString());
            qTextField.setText(q.toString());
        }
        
        stepTwoButton.setDisable(false);
        stepThreeButton.setDisable(false);
    }
    
    private void stepOne_setPrimeFactorsOfNAndSetLabels() {
        long start = System.currentTimeMillis();
        BigInteger[] primeFactors = RsaUtil.getPrimeFactors(n);
        timeTakenToFactorNInMs = System.currentTimeMillis() - start;
    
        if (primeFactors.length != 2) {
            stepOne_setLabelsAndTextFieldsOnError(primeFactors);
            return;
        }
        
        p = primeFactors[0];
        q = primeFactors[1];
        
        stepOne_setLabelsAndTextFieldsOnSuccess();
    }
    
    private void stepOne_setLabelsAndTextFieldsOnError(BigInteger[] actualPrimeFactors) {
        pLabel.setText("Cannot use " + n + " as n:");
        qLabel.setText(n + " has " + actualPrimeFactors.length + " factors!");
        timeTakenToFactorizeN.setText(Arrays.toString(actualPrimeFactors));
    
        pLabel.setTextFill(Paint.valueOf("red"));
        qLabel.setTextFill(Paint.valueOf("red"));
        timeTakenToFactorizeN.setTextFill(Paint.valueOf("red"));
    }
    
    private void stepOne_setLabelsAndTextFieldsOnSuccess() {
        pLabel.setText("p is " + p);
        qLabel.setText("q is " + q);
        timeTakenToFactorizeN.setText("Time busy finding p and q: " + timeTakenToFactorNInMs + "ms");
    
        pLabel.setTextFill(Paint.valueOf("black"));
        qLabel.setTextFill(Paint.valueOf("black"));
        timeTakenToFactorizeN.setTextFill(Paint.valueOf("black"));
    }
    
    public void onGenerateNFromPAndQClicked(ActionEvent actionEvent) {
        BigInteger pInput = new BigInteger(pTextField.getText());
        BigInteger qInput = new BigInteger(qTextField.getText());
    
        if (pInput.equals(qInput)) {
            // cannot use same integers for p and q
            nTextField.clear();
            
            pLabel.setText("Cannot use same integers for p and q");
            qLabel.setText("");
            timeTakenToFactorizeN.setText("");
    
            pLabel.setTextFill(Paint.valueOf("red"));
            return;
        }
    
        if (isNotPrime(pInput)) {
            return;
        }
    
        if (isNotPrime(qInput)) {
            return;
        }
    
        p = pInput;
        q = qInput;
        n = p.multiply(q);
        nTextField.setText(n.toString());
    }
    
    private boolean isNotPrime(BigInteger input) {
        if (!input.isProbablePrime(100)) {
            // p must be prime!
            nTextField.clear();
    
            pLabel.setText(input + " is not prime!");
            qLabel.setText("");
            timeTakenToFactorizeN.setText("");
    
            pLabel.setTextFill(Paint.valueOf("red"));
            qLabel.setTextFill(Paint.valueOf("red"));
            return true;
        }
        return false;
    }
    
    //---------- STEP TWO ----------\\
    
    public void stepTwoClicked(ActionEvent actionEvent) {
        e = RsaUtil.chooseE(p, q);
    
        // we know e is valid
        eTextField.setText(e.toString());
        setLabelsOnValidE();
    }
    
    public void onKeyTypedETextField(KeyEvent keyEvent) {
        if (!Character.isDigit(keyEvent.getCharacter().charAt(0))) {
            return;
        }
        
        BigInteger phi = RsaUtil.getPhi(p, q);
        BigInteger eInput = new BigInteger(eTextField.getText() + keyEvent.getCharacter());
        
        // check if provided e is valid
        if (phi.gcd(eInput).equals(BigInteger.ONE)) {
            e = eInput;
            setLabelsOnValidE();
        } else {
            setLabelsOnInvalidE(eInput);
        }
    }
    
    private void setLabelsOnValidE() {
        eValidityLabel.setText("e is valid");
        eValidityLabel.setTextFill(Paint.valueOf("black"));
    
        BigInteger phi = RsaUtil.getPhi(p, q);
        phiLabel.setText("phi is " + phi.toString());
        phiLabel.setTextFill(Paint.valueOf("black"));
    
        phiFactorsLabel.setText("phi factors are: " + Arrays.toString(RsaUtil.getPrimeFactors(phi)));
        phiFactorsLabel.setTextFill(Paint.valueOf("black"));
    }
    
    private void setLabelsOnInvalidE(BigInteger eInput) {
        eValidityLabel.setText("e is NOT valid");
        eValidityLabel.setTextFill(Paint.valueOf("red"));
    
        BigInteger phi = RsaUtil.getPhi(p, q);
        phiLabel.setText("phi is " + phi.toString());
        phiLabel.setTextFill(Paint.valueOf("red"));
    
        phiFactorsLabel.setText("e shares factor " + phi.gcd(eInput).toString() + " with phi");
        phiFactorsLabel.setTextFill(Paint.valueOf("red"));
    }
    
    //---------- STEP THREE ----------\\
    
    public void stepThreeClicked(ActionEvent actionEvent) {
    
        if (messageTextArea.getText().isEmpty() && encodedMessageArea.getText().isEmpty()) {
            return;
        }
        
        // fill encodedMessageArea if message is in messageArea
        if (encodedMessageArea.getText().isEmpty()) {
            // encode from messageArea
            String message = messageTextArea.getText();

            for (int i = 0; i < message.length(); i++) {
                if (i > 0) {
                    encodedMessageArea.appendText(",");
                }
                char c = message.charAt(i);

                // append to encodedMessageArea
                encodedMessageArea.appendText(String.valueOf((int) c));
            }
        }

        StringBuilder sb = new StringBuilder();
    
        for (int i = 0; i < messageTextArea.getText().length(); i++) {
            if (i > 0) {
                sb.append(",");
            }
            char c = messageTextArea.getText().charAt(i);
            
            sb.append(BigInteger.valueOf((int) c).modPow(e, n));
        }
        
        encryptedMessageArea.setText(sb.toString());
    }
}
