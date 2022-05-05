package com.example.v1;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class VisualController {
    @FXML
    private Canvas canvas;

    public void SwitchToScene1(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("dev_ver.fxml")));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void Draw() {
        GraphicsContext gc = canvas.getGraphicsContext2D();
        double X = canvas.getWidth();
        double Y = canvas.getHeight();
        gc.setStroke(Color.BLACK);
        gc.setLineWidth(2d);
        gc.setGlobalAlpha(0.5d);
        gc.strokeOval(X / 6, Y / 6, X * 2 / 3, Y * 2 / 3);
        gc.fillOval(X / 2 - X / 7, Y / 2 - Y / 6, 8, 8);
        gc.fillOval(X / 2 + X / 7, Y / 2 - Y / 6, 8, 8);
        gc.strokeLine(X / 2 - X / 8, Y / 2 - Y / 8, X / 2 + X / 8, Y / 2 - Y / 8);
    }
}
