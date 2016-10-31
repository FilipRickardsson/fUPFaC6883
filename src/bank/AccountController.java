package bank;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

public class AccountController extends BaseController {

    private ObservableList<String> listan;

    @FXML
    private TextField amount;

    @FXML
    private ListView transactions;

    @FXML
    private Label error;

    @FXML
    private void makeDeposit() {
        try{
            if (!amount.getText().isEmpty()){
                error.setText("Amount is empty");
            }
        }
        bankLogic.deposit(selectedCustomerSSN, selectedCustomerAccountID, Double.parseDouble(amount.getText()));
        updateInfo();
    }

    @FXML
    private void makeWithdrawal() {
        boolean success = bankLogic.withdraw(selectedCustomerSSN, selectedCustomerAccountID, Double.parseDouble(amount.getText()));
        updateInfo();
        if (!success) {
            error.setText("Not enough money");
        }
    }

    @Override
    protected void popupYes() {
        popup.close();
    }

    public void updateInfo() {
        listan = FXCollections.observableArrayList((ArrayList) bankLogic.getTransactions(selectedCustomerSSN, selectedCustomerAccountID));
        transactions.setItems(listan);
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        updateInfo();

        try {
            loadPopup();
        } catch (IOException ex) {
            Logger.getLogger(HomeController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

}
