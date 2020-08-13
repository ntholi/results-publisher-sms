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
    @FXML private ListView<?> listView;
    private WorkbookReader workbookReader;
	
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
    	var task = workbookReader.readStudentResults();
    	progressBar.progressProperty().bind(task.progressProperty());
    	task.messageProperty().addListener((ob, oldv, newv)->
    		statusLabel.setText(newv));
    	task.setOnSucceeded(ev ->{
    		System.out.println("Done");
    	});
    	task.setOnFailed(ev ->{
    		var ex = ev.getSource().getException();
    		Alert alert = new Alert(AlertType.ERROR);
    		alert.setHeaderText("Error! Cannot continue");
    		alert.setContentText(ex.getMessage());
    		alert.showAndWait();
    	});
    }

}
