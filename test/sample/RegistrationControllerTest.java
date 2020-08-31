package sample;

import com.sun.javafx.robot.FXRobot;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;


import java.io.File;

import static javafx.scene.layout.Region.USE_COMPUTED_SIZE;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(ApplicationExtension.class)
class RegistrationControllerTest {
    Stage theStage;
    RegistrationController ctrl;


    @Start
    public void start (Stage stage) throws Exception {
        RentACarDAO.removeInstance();
        File dbfile = new File("rentacar.db");
        dbfile.delete();
        RentACarDAO dao = RentACarDAO.getInstance();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/registration.fxml"));
        ctrl = new RegistrationController(null, "", "client");
        loader.setController(ctrl);
        Parent root = loader.load();
        stage.setTitle("Registracija");
        stage.setScene(new Scene(root, USE_COMPUTED_SIZE, USE_COMPUTED_SIZE));
        stage.setResizable(false);
        stage.show();
        stage.toFront();
        theStage = stage;
    }
    @Test
    void registrationConfirmAction(FxRobot robot) {
        robot.clickOn("#fldFirstName");
        robot.write("Klijent");
        robot.clickOn("#fldLastName");
        robot.write("Klijentijević");
        robot.clickOn("#fldEmail");
        robot.write("klijent@gmail.com");
        robot.clickOn("#fldUsername");
        robot.write("klijent123");
        robot.clickOn("#fldPassword");
        robot.write("password");
        robot.clickOn("#fldRetypePassword");
        robot.write("password");
        robot.clickOn("#fldAddress");
        robot.write("Klijentova adresa");
        robot.clickOn("#fldTelephone");
        robot.write("062333457");
        // Klik na dugme ok
        robot.clickOn("#btnRegistrationConfirm");
        robot.clickOn("OK");
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        robot.lookup("#lblWelcomeClient").tryQuery().isPresent();
        Label lbl=robot.lookup("#lblWelcomeClient").queryAs(Label.class);
        assertNotNull(lbl);
        Stage stage= (Stage) lbl.getScene().getWindow();
        Platform.runLater(() -> stage.close());
    }
    @Test
    void backRegistrationAction(FxRobot robot) {
        robot.clickOn("#btnBackRegistration");
        assertFalse(theStage.isShowing());
        robot.lookup("#rent").tryQuery().isPresent();
        Label lbl=robot.lookup("#rent").queryAs(Label.class);
        assertNotNull(lbl);
        Stage stage= (Stage) lbl.getScene().getWindow();
        Platform.runLater(() -> stage.close());
    }

}