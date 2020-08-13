package com.breakoutms.luct.reg;

import java.io.File;
import java.util.List;

import com.breakoutms.luct.reg.model.WorkbookReader;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.ScrollPane;
import javafx.stage.FileChooser;

public class MainController {

    @FXML private Label statusLabel;
	@FXML private ProgressBar progressBar;
	@FXML private Button okBtn;
	@FXML private Button openBtn;
	@FXML private ScrollPane scrollPane;
    @FXML private ListView<Object> listView;
    @FXML private Label title;
    private WorkbookReader workbookReader;
    private int steps = 0;
	
    @FXML
	void initialize() {
	}

	@FXML
	void open(ActionEvent event) {
		FileChooser fileChooser = new FileChooser();
		fileChooser.getExtensionFilters().addAll(
				new FileChooser.ExtensionFilter("Microsoft Excel Open XML Spreadsheet", "*.xlsx"),
				new FileChooser.ExtensionFilter("Microsoft Excel Binary File Format", "*.xls"),
				new FileChooser.ExtensionFilter("OpenDocument Spreadsheet", "*.ods"));
		List<File> files = fileChooser.showOpenMultipleDialog(null);
		
		workbookReader = new WorkbookReader(files);
		statusLabel.setText("Loaded "+workbookReader.getTotalWorkbooks()+" workbook(s)");
		okBtn.setDisable(false);
	}
	
    @FXML
    void process(ActionEvent event) {
    	switch (steps) {
		case 0:
			readStudents();
			break;
		case 1:
			sendSmsUI();
			break;

		default:
			break;
		}
    }

	private void sendSmsUI() {
		++steps;
	}

	private void readStudents() {
		var task = workbookReader.readStudentResults();
    	progressBar.progressProperty().bind(task.progressProperty());
    	listView.getItems().clear();
    	task.messageProperty().addListener((ob, oldv, newv)->{
    		listView.getItems().add(newv);
    		statusLabel.setText("Reading class: "+newv);
    	});
    	task.setOnFailed(ev ->{
    		var ex = ev.getSource().getException();
    		Alert alert = new Alert(AlertType.ERROR);
    		alert.setHeaderText("Error! Cannot continue");
    		Label label = new Label(ex.getMessage());
    		label.setPrefHeight(100);
    		label.setPrefWidth(350);
    		label.setWrapText(true);
    		alert.getDialogPane().setContent(label);
    		alert.showAndWait();
    	});
    	++steps;
	}

}
