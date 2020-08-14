package com.breakoutms.luct.reg;

import java.io.File;
import java.text.DecimalFormat;
import java.util.List;
import java.util.Set;

import com.breakoutms.luct.reg.model.ResultPublisher;
import com.breakoutms.luct.reg.model.WorkbookReader;
import com.breakoutms.luct.reg.model.beans.StudentResult;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.Region;
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
	private Set<StudentResult> studentResults;
	
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
		case 2:
			sendSms();
			break;

		default:
			break;
		}
    }

	private void sendSms() {
		ResultPublisher publisher = new ResultPublisher();
		var task = publisher.publish(studentResults);
		progressBar.progressProperty().unbind();
		progressBar.progressProperty().bind(task.progressProperty());
		task.setOnRunning(ev -> {
			title.setText("Sending SMSs...");
			statusLabel.setText("Sending SMSs...");
			progressBar.setVisible(true);
			okBtn.setDisable(true);
		});
    	task.messageProperty().addListener((ob, oldv, newv)->{
    		listView.getItems().add(newv);
    	});
    	task.progressProperty().addListener((ob, oldv, newv)->{
    		DecimalFormat df = new DecimalFormat("##.##%");
    		statusLabel.setText(df.format(newv));
    	});
		
	}

	private void sendSmsUI() {
		title.setText(studentResults.size()+" student(s) loaded");
		statusLabel.setText("Ready to send");
		okBtn.setText("Send");
		listView.getItems().clear();
		progressBar.setVisible(false);
		openBtn.setDisable(true);
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
    		alert.setHeaderText("Error! Unable continue");
    		alert.setContentText(ex.getMessage());
    		alert.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
    		alert.showAndWait();
    	});
    	task.setOnSucceeded(ev ->{
    		statusLabel.setText("Done!");
    		studentResults = task.getValue();
    	});
    	++steps;
	}

}
