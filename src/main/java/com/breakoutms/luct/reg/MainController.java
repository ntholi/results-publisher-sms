package com.breakoutms.luct.reg;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.ScrollPane;
import javafx.stage.FileChooser;
import javafx.stage.Window;

import java.io.File;
import java.util.List;

import com.breakoutms.luct.reg.model.WorkbookReader;

import javafx.event.ActionEvent;

public class MainController {

	@FXML private ProgressBar progressBar;
	@FXML private Button okBtn;
	@FXML private Button openBtn;
	@FXML private ScrollPane scrollPane;

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
		WorkbookReader reader = new WorkbookReader();
		reader.read(files);
	}
}
