package net.atlassin.teamioanaraluca;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import net.atlassin.teamioanaraluca.Services.AppointmentsService;
import net.atlassin.teamioanaraluca.Services.DentistFacilitiesService;
import net.atlassin.teamioanaraluca.Services.FileSystemService;
import net.atlassin.teamioanaraluca.Services.UserService;

import java.nio.file.Files;
import java.nio.file.Path;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        UserService.initDatabase();
        DentistFacilitiesService.initDatabase();
        AppointmentsService.initDatabase();
        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("Login.fxml"));
        primaryStage.setTitle("Dental Healthcare Assistant");
        primaryStage.setScene(new Scene(root, 600, 460));
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
