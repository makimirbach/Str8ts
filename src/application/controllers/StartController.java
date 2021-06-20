package application.controllers;

import java.net.URL;
import java.util.ResourceBundle;

import application.constants.Messages;
import application.listeners.IGameListener;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

public class StartController implements Initializable {

	@FXML
	private AnchorPane start;
	
	@FXML
	private ComboBox<Integer> cbSize;
	ObservableList<Integer> lstSizes = FXCollections.observableArrayList(6);
	
	@FXML
	private Label lblMain;
	
	@FXML
	private Button btnStart;
	
	private int n;
	
	public int getN() {
		return this.n;
	}
	
	private IGameListener gameListener;
	
	
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		init();
	} 
	
	public void setGameListener(IGameListener gameListener) {
		this.gameListener = gameListener;
	}

	public void init() {
		cbSize.setItems(lstSizes);
		cbSize.setPromptText(Messages.CHOOSE_SIZE);
		btnStart.setText(Messages.CLICK_START);
		start.setVisible(true);
		lblMain.setText(Messages.START_GAME);
	}
	
	public void closeStart() {
		start.setVisible(false);;
	}
	
	/*
	 * very beginning: user chooses size
	 */
	public void clickStart(ActionEvent event) {
		if (!cbSize.getSelectionModel().isEmpty()) {
			this.n = cbSize.getValue();
			this.gameListener.onGameStart();
		}
	}
}
