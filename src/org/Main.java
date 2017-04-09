package org;

import static javafx.application.Application.launch;

import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
      CentralController cc = new CentralController();
      cc.startUI(primaryStage);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
