package com.ui;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import java.util.ArrayList;
import java.util.List;

public class Main extends Application {


    private static List<TabUI> uis;
    static {
        uis = new ArrayList<>(1);
        uis.add(new WordTab());
    }



    @Override
    public void start(Stage primaryStage) {

        //创建面板
        TabPane tabPane = new TabPane();
        for (TabUI ui : uis) {
            tabPane.getTabs().add(ui.getTab(primaryStage));
        }

        Scene scene = new Scene(tabPane, 700, 500);
        primaryStage.setScene(scene);
        primaryStage.setTitle("数据处理");
        primaryStage.setScene(scene);
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}