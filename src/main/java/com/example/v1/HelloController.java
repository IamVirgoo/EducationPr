package com.example.v1;

import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.TextField;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import javax.imageio.ImageIO;
import java.awt.image.RenderedImage;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Vector;

public class HelloController {
    @FXML
    private TextField input_path;
    @FXML
    private TextField input_row;
    @FXML
    private TextField input_columns;
    @FXML
    private GridPane grid;
    @FXML
    private GridPane grid1;

    public Canvas canvas = new Canvas(400,400);
    public void SwitchToScene2(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("Visual.fxml")));
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    public void getMatrix(GridPane Grid, int number) throws IOException {
        Canvas canvas =new Canvas(300,300);
        GraphicsContext gc = canvas.getGraphicsContext2D();
        int rows_number = Integer.parseInt(input_row.getText());
        int column_number = Integer.parseInt(input_columns.getText());
        StringBuilder first_array = new StringBuilder();
        int counter = 1;

        for (int i = 0; i < rows_number * column_number; i++) {
            first_array.append(Integer.valueOf(((TextField) Grid.getChildren().get(i)).getText()));
            first_array.append(" ");
            if (counter % column_number == 0) {
                first_array.append('\n');
            }
            counter++;
        }
        String[] array = first_array.toString().split("\n");
        for (int i = 0; i < array.length; i++) {
            gc.strokeText(array[i],10,20+i*30);
        }
        System.out.println(first_array);
        File file = new File("file" + number + ".png");
        try {
            WritableImage writableImage = new WritableImage(150, 150);
            canvas.snapshot(null, writableImage);
            RenderedImage renderedImage = SwingFXUtils.fromFXImage(writableImage, null);
            ImageIO.write(renderedImage, "png", file);
        } catch (IOException ex) {
            ex.printStackTrace();
            System.out.println("Error!");
        }
        Runtime.getRuntime().exec("chromium /home/karen/IdeaProjects/EducationalProject-main/V1/web/index.html");
    }

    public void drawImage() throws IOException {
        getMatrix(grid, 1);
        getMatrix(grid1, 2);
    }

    @FXML
    private void onButtonClick() {
        int rows_number = Integer.parseInt(input_row.getText());
        int column_number = Integer.parseInt(input_columns.getText());
        for (int i = 0; i < rows_number; i++) {
            for (int j = 0; j < column_number; j++) {
                TextField tf = new TextField();
                tf.setPrefHeight(50);
                tf.setPrefWidth(50);
                GridPane.setRowIndex(tf, i);
                GridPane.setColumnIndex(tf, j);
                grid.getChildren().add(tf);
            }
        }
    }

    @FXML
    private void getText() throws IOException {
        int rows_number = Integer.parseInt(input_row.getText());
        int column_number = Integer.parseInt(input_columns.getText());
        Vector<Integer> array = new Vector<>();
        for (int i = 0; i < rows_number * column_number; i++) {
            array.add(Integer.valueOf(((TextField) grid.getChildren().get(i)).getText()));
        }
        ShellSort(array.size(), array);
        for (int i = 0; i < rows_number; i++) {
            for (int j = 0; j < column_number; j++) {
                TextField tf = new TextField();
                tf.setPrefHeight(50);
                tf.setPrefWidth(50);
                GridPane.setRowIndex(tf, i);
                GridPane.setColumnIndex(tf, j);
                grid1.getChildren().add(tf);
            }
        }
        for (int i = 0; i < array.size(); i++) {
            ((TextField) grid1.getChildren().get(i)).setText(String.valueOf(array.get(i)));
        }
    }

    public GridPane getGrid(int rows, int columns, int[][] matrix) {
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                TextField tf = new TextField();
                tf.setPrefHeight(50);
                tf.setPrefWidth(50);
                tf.setText(String.valueOf(matrix[i][j]));
                GridPane.setRowIndex(tf, i);
                GridPane.setColumnIndex(tf, j);
                grid.getChildren().add(tf);
            }
        }
        return grid;
    }
    @FXML
    private void fileWrite() throws IOException {
        int rows_number = Integer.parseInt(input_row.getText());
        int column_number = Integer.parseInt(input_columns.getText());
        String path = input_path.getText();
        Vector<Integer> array = new Vector<>();
        for (int i = 0; i < rows_number * column_number; i++) {
            array.add(Integer.valueOf(((TextField) grid.getChildren().get(i)).getText()));
        }
        try (FileWriter writer = new FileWriter(path + ".txt", false)) {
            int counter = 1;
            for (int i = 0; i < array.size(); i++) {
                writer.write(array.get(i).toString());
                writer.write(" ");
                if (counter % column_number == 0) {
                    writer.write('\n');
                }
                counter++;
            }
            writer.flush();
        }
        catch(IOException ex) {
            System.out.println(ex.getMessage());
        }
    }

    @FXML
    private void fileRead() throws IOException {
        String path = input_path.getText();
        BufferedReader br = new BufferedReader(new FileReader(path));
        List<String> lines = new ArrayList<>();
        while (br.ready()) {
            lines.add(br.readLine());
        }
        int matrixWidth = lines.get(0).split(" ").length;
        int matrixHeight = lines.size();
        input_row.setText(String.valueOf(matrixHeight));
        input_columns.setText(String.valueOf(matrixWidth));

        int[][] matrix = new int[matrixHeight][matrixWidth];

        for (int i = 0; i < matrixHeight; i++) {
            for (int j = 0; j < matrixWidth; j++) {
                String[] line = lines.get(i).split(" ");
                matrix[i][j] = Integer.parseInt(line[j]);
            }
        }
        getGrid(matrixHeight, matrixWidth, matrix);
    }

    @FXML
    private void getGenerate() {
        int rows_number = Integer.parseInt(input_row.getText());
        int column_number = Integer.parseInt(input_columns.getText());
        for (int i = 0; i < rows_number * column_number; i++) {
            ((TextField) grid.getChildren().get(i)).setText(String.valueOf((int) (Math.random() * 200) - 100));
        }
    }

    public static void ShellSort(int number, Vector<Integer> array) throws IOException {
        int i, j, step;
        int temp;
        for (step = number / 2; step > 0; step /= 2) {
            for (i = step; i < number; i++) {
                temp = array.get(i);
                for (j = i; j >= step; j -= step) {
                    if (temp < array.get(j - step)) {
                        array.set(j, array.get(j - step));
                    }
                    else break;
                }
                array.set(j, temp);
            }
        }
    }
}

