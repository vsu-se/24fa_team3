package com.example.demo;

import javafx.scene.control.Tab;
import javafx.scene.control.TextArea;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;

public class ReportsTab {
    private Tab reportsTab;
    private TextArea reportArea;
    private Button generateReportButton;

    public ReportsTab() {
        reportsTab = new Tab("Reports");
        reportArea = new TextArea();
        generateReportButton = new Button("Generate Report");

        reportArea.setEditable(false);
        VBox reportsContent = new VBox(generateReportButton, reportArea);
        reportsTab.setContent(reportsContent);

        // Add event handler for the button
        generateReportButton.setOnAction(event -> {
            // Generate the seller's report
            SellersReportController report = new SellersReportController();
            report.addItem("Item 1", 100.00, 10.00, 5.00);
            report.addItem("Item 2", 200.00, 20.00, 10.00);
            report.addItem("Item 3", 150.00, 15.00, 7.50);
            String reportText = report.generateReport();
            reportArea.setText(reportText);
        });
    }

    public Tab getReportsTab() {
        return reportsTab;
    }
}
