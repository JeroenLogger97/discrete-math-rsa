package discretemathematics.controller;


import discretemathematics.RsaDriver;
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

public class DecryptionController implements Initializable {

    private BigInteger n;
    private BigInteger p;
    private BigInteger q;
    private BigInteger e;
    private BigInteger d;
    private long timeTakenToFactorNInMs;

    // Step 1
    public Button stepOneButton;
    public Button generateNFromPAndQButton;
    public Label pLabel;
    public Label qLabel;
    public Label dLabel;
    public Label timeTakenToFactorizeN;
    public TextField pTextField;
    public TextField qTextField;
    public TextField nTextField;

    // Step 2
    public Button stepTwoButton;
    public TextField eTextField;

    // Step 3
    public Button stepThreeButton;
    public TextArea encryptedMessageTextArea;
    public TextArea encodedMessageArea;
    public TextArea DecryptedMessageArea;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        EncryptionController.makeTextFieldNumericOnly(nTextField);
        EncryptionController.makeTextFieldNumericOnly(eTextField);
    }

    public void stepOne_calculatePandQ() {
        if (nTextField.getText().isEmpty()) {
            return;
        }

        n = new BigInteger(nTextField.getText());
        stepOne_setPrimeFactorsOfNAndSetLabels();

        if (p != null && q != null) {
            pTextField.setText(p.toString());
            qTextField.setText(q.toString());
        }

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

        stepTwoButton.setDisable(false);
    }

    /***
     * Calculate the d value
     * @param actionEvent
     */
    public void stepTwoClicked(ActionEvent actionEvent) {
        stepOne_calculatePandQ();
        
        if (eTextField.getText().isEmpty()) {
            return;
        }
        e = new BigInteger(eTextField.getText());

        // Calculate phi = (p-1) * (q-1)
        BigInteger phi = RsaUtil.getPhi(p, q);

        // d = multiplicative inverse of e modulo phi.
        d = RsaUtil.findD(e, phi);

        dLabel.setText("Value of d found: " + d.toString());

        // Once done
        stepThreeButton.setDisable(false);
    }

    /***
     * Decrypt the message
     * @param actionEvent
     */
    public void stepThreeClicked(ActionEvent actionEvent) {
        if (encryptedMessageTextArea.getText().isEmpty()) {
            return;
        }
        String encryptedMessage = encryptedMessageTextArea.getText();
        encryptedMessage = encryptedMessage.replaceAll("\\s*", "");

        // foreach character = x, x ^ d mod N
        String decryptedMessage = RsaDriver.decrypt(n, d, encryptedMessage);

        // Show the encoded message
        for (int i = 0; i < decryptedMessage.length(); i++) {
            if (i > 0) {
                encodedMessageArea.appendText(",");
            }
            char c = decryptedMessage.charAt(i);

            // append to encodedMessageArea
            encodedMessageArea.appendText(String.valueOf((int) c));
        }

        // Show the message
        DecryptedMessageArea.setText(decryptedMessage);
    }
    
}
