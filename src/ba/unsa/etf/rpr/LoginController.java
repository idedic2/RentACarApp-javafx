package ba.unsa.etf.rpr;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.scene.image.Image;

import java.io.IOException;

import static javafx.scene.layout.Region.USE_COMPUTED_SIZE;

public class LoginController {
    public TextField fldUsername;
    public PasswordField fldPassword;
    public RadioButton radioClient;
    public RadioButton radioAdmin;
    public RadioButton radioEmployee;
    public ToggleGroup toggleGroup;
    private User user;
    private RentACarDAO rentACarDAO;
    private boolean usernameOk=true;
    private boolean passwordOk=true;

    public LoginController() {
        rentACarDAO = RentACarDAO.getInstance();
    }
    @FXML
    public void initialize() {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                fldUsername.requestFocus();
            }
        });
        fldUsername.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String oldValue, String newValue) {
                if (fldUsername.getText().trim().isEmpty()) {
                    fldUsername.getStyleClass().removeAll("ispravnoPolje");
                    fldUsername.getStyleClass().add("neispravnoPolje");
                    usernameOk = false;
                } else {
                    fldUsername.getStyleClass().removeAll("neispravnoPolje");
                    fldUsername.getStyleClass().add("ispravnoPolje");
                    usernameOk = true;
                }
            }
        });
        fldPassword.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String oldValue, String newValue) {
                if (fldPassword.getText().trim().isEmpty()) {
                    fldPassword.getStyleClass().removeAll("ispravnoPolje");
                    fldPassword.getStyleClass().add("neispravnoPolje");
                    usernameOk = false;
                } else {
                    fldPassword.getStyleClass().removeAll("neispravnoPolje");
                    fldPassword.getStyleClass().add("ispravnoPolje");
                    usernameOk = true;
                }
            }
        });
    }

    private void showAlert(String title, String headerText, Alert.AlertType type) {
        Alert error = new Alert(type);
        error.setTitle(title);
        error.setHeaderText(headerText);
        error.show();
    }
    private Stage getNewStage(String stageName) {
        try {
            FXMLLoader loader;
            Stage mainStage = new Stage();
            switch (stageName) {
                case "Admin":
                    loader = new FXMLLoader(getClass().getResource("/fxml/employeePage.fxml"));
                    mainStage.getIcons().add(new Image("/images/admin.png"));
                    EmployeePageController employeePageController = new EmployeePageController(fldUsername.getText(), "yes");
                    loader.setController(employeePageController);
                    //mainStage.setOnHidden(event -> writeAdminView(controller.getTabsConfig()));
                    break;
                case "Klijent":
                    loader = new FXMLLoader(getClass().getResource("/fxml/clientPage.fxml"));
                    mainStage.getIcons().add(new Image("/images/client.jpg"));
                    ClientPageController clientController = new ClientPageController(fldUsername.getText());
                    loader.setController(clientController);
                    //mainStage.setOnHidden(event -> writeStudentView(studentController.getTabConfig()));
                    break;
                case "Zaposlenik":
                    loader = new FXMLLoader(getClass().getResource("/fxml/employeePage.fxml"));
                    mainStage.getIcons().add(new Image("/images/admin.png"));
                    EmployeePageController employeePageController2 = new EmployeePageController(fldUsername.getText(), "no");
                    loader.setController(employeePageController2);
                    //mainStage.setOnHidden(event -> writeStudentView(studentController.getTabConfig()));
                    break;
                default:
                    return null;
            }
            Parent root = loader.load();
            mainStage.setTitle(stageName);
            mainStage.setResizable(false);
            mainStage.setScene(new Scene(root, USE_COMPUTED_SIZE, USE_COMPUTED_SIZE));
            return mainStage;
        } catch (IOException ignored) {
            return null;
        }
    }
    public void loginConfirmAction(ActionEvent actionEvent) {
        boolean usernameOk=true, passwordOk=true;
        if (fldUsername.getText().isEmpty()) {
            showAlert("Greška", "Unesite korisničko ime", Alert.AlertType.ERROR);
            usernameOk=false;
            return;
        }
        if (fldPassword.getText().isEmpty()) {
            showAlert("Greška", "Unesite šifru", Alert.AlertType.ERROR);
            passwordOk=false;
            return;
        }
        if(usernameOk && passwordOk && toggleGroup.getSelectedToggle()==null){
            showAlert("Greška", "Odaberite tip korisnika", Alert.AlertType.ERROR);
            return;
        }
        RadioButton selectedRadio= (RadioButton) toggleGroup.getSelectedToggle();
        boolean doesExistUser=rentACarDAO.doesExistUser(fldUsername.getText(), fldPassword.getText(), selectedRadio.getText());
        if (!doesExistUser) {
            showAlert("Greška", "Ne postoji korisnik sa datim podacima", Alert.AlertType.ERROR);
            return;
        }
        Stage mainStage = getNewStage(selectedRadio.getText());
        //ovdje je sigurno neko dugme odabrano
        if (mainStage == null) {
            //System.out.println("lfsgl");
            return;
        }
        Stage currentStage = (Stage) fldUsername.getScene().getWindow();
        currentStage.close();
        mainStage.show();

    }
    public void backLoginAction(ActionEvent actionEvent){
        Stage stage= (Stage) fldUsername.getScene().getWindow();
        stage.close();
        Stage oldstage = new Stage();
        Parent root = null;
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/start.fxml"));
            StartController startController = new StartController();
            loader.setController(startController);
            root = loader.load();
            oldstage.setTitle("Dobrodošli");
            oldstage.setScene(new Scene(root, USE_COMPUTED_SIZE, USE_COMPUTED_SIZE));
            oldstage.setResizable(false);
            oldstage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
