package application.controllers;

import java.net.URL;
import java.util.ResourceBundle;

import application.constants.Messages;
import application.listener.IGameOverListener;
import application.listener.IGameStartListener;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;

public class StartController implements Initializable {

	
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
	
	private IGameStartListener gameStartListener;
	
	
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		init();
		
	} 
	
	public void setGameStartListener(IGameStartListener gameStartListener) {
		this.gameStartListener = gameStartListener;
	}

	private void init() {
		
		cbSize.setItems(lstSizes);
		cbSize.setPromptText(Messages.CHOOSE_SIZE);
		btnStart.setText(Messages.CLICK_START);
		cbSize.setVisible(true);
		lblMain.setVisible(true);
		btnStart.setVisible(true);
		lblMain.setText(Messages.START_GAME);
	}
	
	public void closeStart() {
		cbSize.setVisible(false);
		lblMain.setVisible(false);
		btnStart.setVisible(false);
	}
	
	/*
	 * very beginning: user chooses size
	 */
	public void clickStart(ActionEvent event) {
		
		if (!cbSize.getSelectionModel().isEmpty()) {
			this.n = cbSize.getValue();
			this.gameStartListener.onGameStart();
		}
		else System.out.println("nah");
		
	}

	
}
