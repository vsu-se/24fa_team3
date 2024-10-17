package com.example.demo;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class HelloController implements Initializable {

    @FXML
    public Button removeCategory_Btn;
    public Button addCategory_Btn;
    public TextField categorySubmission_Lbl;
    public ListView<String> categoryList_LV;
    public Label categoryCheck_Lbl;


    List<String> categorylist = new ArrayList<>();

    private ObservableList<String> observableList;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        categorylist = new ArrayList<>();
        observableList = FXCollections.observableArrayList(categorylist);
        categoryList_LV.setItems(observableList);
    }

    public void addToCategory(ActionEvent actionEvent) {
        String categoryName = categorySubmission_Lbl.getText();


        if (categorylist.isEmpty()) {
            categorylist.add(categoryName);
            observableList.add(categoryName);
            categorySubmission_Lbl.clear();
        }
        else {
            boolean exists = false;
            for (String s : categorylist) {
                if (s.equals(categoryName)) {
                    exists = true;
                    break;
                }
            }

            if (exists) {
                categoryCheck_Lbl.setText("Category is already created");
                categorySubmission_Lbl.clear();
            }
            else {
                categorylist.add(categoryName);
                observableList.add(categoryName);
                categorySubmission_Lbl.clear();
            }

        }



    }

    public void removeFromCategory(ActionEvent actionEvent) {

        // String categoryName = categorySubmission_Lbl.getText();
        String categoryName = categoryList_LV.getSelectionModel().getSelectedItem();

        for (int i = 0; i < categorylist.size(); i++) {
            if (categoryName.equals(categorylist.get(i))) {
                categorylist.remove(categoryName);
                observableList.remove(categoryName);
            }
        }
        categorySubmission_Lbl.clear();
    }
}