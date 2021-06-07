package application.controllers;

import java.net.URL;
import java.util.ResourceBundle;

import application.constants.GameState;
import application.constants.Messages;
import application.listener.IGameOverListener;
import application.listener.IGameStartListener;
import application.service.Str8t;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Tooltip;

public class MainController implements Initializable, IGameOverListener, IGameStartListener {
	
	@FXML 
	private GameController gameController;
	@FXML 
	private StartController startController;
	
	
	@FXML
	private Button btnHelp;
	@FXML
	private CheckBox checkHints;
	
	
	
	/*
	 * init layout
	 */
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
		gameController.state = GameState.BEFORE;
		gameController.setGameOverListener(this);
		
		startController.setGameStartListener(this);
		init();	
	}
	
	
	public void init() {
		if (gameController.state != GameState.BEFORE) {
			gameController.gpBase.setVisible(false);
			gameController.state = GameState.BEFORE;
			startController.init();
		}
		
		checkHints.setVisible(false);
	}
	
	public void startGame(int n) {

		if (gameController.state == GameState.BEFORE) {
			gameController.gpBase.setVisible(true);
			gameController.showCorrect = false;
			startController.closeStart();
			checkHints.setVisible(true);
			checkHints.setText(Messages.HINTS);
			int[][] m = {{-4,6,5,0,1,2},{5,4,6,3,2,1},{6,5,0,4,3,0},{0,1,2,0,6,5},{2,3,1,-6,5,4},{3,2,0,5,4,0}}; 
			//int[][] s = {{4,6,5,0,1,2},{5,4,6,3,2,1},{0,0,0,4,3,0},{0,0,0,0,0,0},{2,3,1,6,5,4},{3,2,0,5,4,0}};
			int[][] s = {{4,6,0,0,0,0},{0,0,0,0,0,1},{0,0,0,4,3,0},{0,0,0,0,0,0},{0,3,1,6,0,0},{3,0,0,0,4,0}};
			gameController.str8t = new Str8t(n, m, s);
			gameController.initGrid(n);
			gameController.state = GameState.PLAYING;
		}
		
	}
	
	/*
	 * mouse hover on help button
	 */
	public void showHelpTip() {
		btnHelp.setTooltip(new Tooltip(Messages.HELP_MENU));
	}
	/*
	 * click on help button
	 */
	public void showHelp() {
		Alert alert = new Alert(Alert.AlertType.INFORMATION);
	    alert.setTitle(Messages.HELP_MENU);
	    alert.setHeaderText(Messages.RULES_HEADER);
	    alert.setContentText(Messages.RULES);
	    alert.showAndWait();
	    
	}
	/*
	 * click on checkbox 'hints'
	 */
	public void handleCheckHints() {
		if (checkHints.isSelected()) {
			gameController.showCurrentCorrect();
			gameController.showCorrect = true;
		} else {
			gameController.unshowCorrect();
			gameController.showCorrect = false;
		}
	}

	@Override
	public void onGameOver() {
		if (gameController.state != GameState.AFTER) {
			gameController.state = GameState.AFTER;
			Alert alert = new Alert(Alert.AlertType.INFORMATION);
		    alert.setTitle(Messages.OVER_MENU);
		    alert.setHeaderText(Messages.OVER_HEADER);
		    alert.setContentText(Messages.GAME_OVER);
		    alert.setOnHidden(e -> init());
		    alert.show();
		}
	}




	@Override
	public void onGameStart() {
		startGame(startController.getN());
		
	}
	
}
