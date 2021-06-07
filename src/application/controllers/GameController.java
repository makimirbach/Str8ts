package application.controllers;

import java.net.URL;
import java.util.ResourceBundle;

import application.Str8t;
import application.constants.GameState;
import application.listener.IGameOverListener;
import javafx.beans.property.ReadOnlyProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;

public class GameController implements Initializable{
	@FXML
	public GridPane gpBase;
	
	@FXML
	public TextField[][] gridTf;
	
	public Str8t str8t;
	
	public boolean showCorrect;
	
	public  GameState state = GameState.BEFORE;
	
	private IGameOverListener gameOverListener;
	
	public void setGameOverListener(IGameOverListener gameOverListener) {
		this.gameOverListener = gameOverListener;
	}
	

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
	}	
	
	/*
	 * init n x n- grid layout of labels
	 */
	public void initGrid(int n) {
		double gridHeight = gpBase.getHeight();
		double cellMeasure = gridHeight/n;
		
		gpBase.setGridLinesVisible(true);
		// n x n layout grid
		if (gpBase.getColumnConstraints().size() == 0) {
			for (int i = 0; i < n; i++) {
				gpBase.getColumnConstraints().add(new ColumnConstraints(cellMeasure));
				gpBase.getRowConstraints().add(new RowConstraints(cellMeasure));
			}
		}
		
		
		gridTf = new TextField[n][n];
		// fill with textfields
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				TextField newField = new TextField();
				newField.setId("tf"+i+j);
				newField.setMaxSize(cellMeasure, cellMeasure);
			
				// paint black cells
				if (str8t.getSolution()[i][j] <= 0) {
					System.out.println("blocked " + i + j);
					newField.getStyleClass().addAll("blocked-black", "blocked");
					// white numbers on black cells
					if (str8t.getSolution()[i][j] < 0) newField.setText(Integer.toString(str8t.getState()[i][j]));
					newField.setEditable(false);
				}
				// black numbers on white cells from state
				else if (str8t.getState()[i][j] != 0) {
					System.out.println("white blocked " + i + j);
					newField.setText(Integer.toString(str8t.getState()[i][j]));
					newField.getStyleClass().addAll("blocked-white", "blocked");
					newField.setEditable(false);
					//newField.setDisable(true); // makes it gray. When do numbers get small by clicking on them??
				}
				// normal white cells: action on leaving one
				else {
					newField.focusedProperty().addListener(new ChangeListener<Boolean>() {
						@Override
						public void changed(ObservableValue<? extends Boolean> arg0, Boolean arg1, Boolean arg2) {
							if (!arg2)
					        { // leaving textfield in row r and column c
					        	TextField t = (TextField)((ReadOnlyProperty<Boolean>) arg0).getBean();
					        	int r = t.getId().charAt(2) - 48;
					        	int c = t.getId().charAt(3) - 48;
					        	tfExit(r,c);
					        }
						}
					});
				}
				
				gpBase.add(newField, j, i);  // column=i row=j
				gridTf[i][j] = newField;
			}
		}
	}
	/*
	 * handle exiting cell after writing numbers
	 * just one number: enter it
	 * several: -> notes
	 */
	public void tfExit(int i, int j) {
		if (state == GameState.PLAYING) {
			TextField tf = gridTf[i][j]; //todo get current field
			
			if (!tf.getText().equals("")) {
				// more than one number entered
				if (tf.getText().length() > 1) {
					str8t.updateNotes(i, j, tf.getText());
					tf.getStyleClass().add("notes");
					if (showCorrect) unshowThisCorrect(i,j);
					tf.setText(str8t.getNotesString(i, j));
					str8t.enterNumber(i, j, 0);
				} else {
					if (Character.isDigit(tf.getText().toCharArray()[0])) {
						tf.getStyleClass().remove("notes");
						if (!str8t.enterNumber(i, j, Integer.valueOf(tf.getText()))) {
							tf.setText("");
						} else {
							if (showCorrect) showThisCorrect(i,j);
						}
						str8t.print();
					} else {
						tf.setText("");
						str8t.enterNumber(i, j, 0);
					}
				}
				
			} else str8t.enterNumber(i, j, 0);
			
			if (str8t.gameOver()) {
				this.gameOverListener.onGameOver();
				
			}
		}
	}
	
	/*
	 * color this one entry
	 */
	public void showThisCorrect(int i,int j) {
		if (str8t.checkNumberCorrect(i, j)) {
			gridTf[i][j].getStyleClass().remove("false");
			gridTf[i][j].getStyleClass().add("correct");
		} else  {
			gridTf[i][j].getStyleClass().remove("correct");
			gridTf[i][j].getStyleClass().add("false");
		}
	}
	
	/*
	 * uncolor this one entry
	 */
	public void unshowThisCorrect(int i,int j) {
		gridTf[i][j].getStyleClass().removeAll("false", "correct");
	}
	
	/*
	 * color all (by user entered) numbers red (false) or green (correct)
	 */
	public void showCurrentCorrect() {
		for (int i = 0; i < str8t.getN(); i++) {
			for (int j = 0; j< str8t.getN(); j++) {
				if (!gridTf[i][j].getStyleClass().contains("blocked") && str8t.getState()[i][j] != 0) {
						if (str8t.checkNumberCorrect(i, j)) {
						//correct and none of the initially displayed numbers
						 gridTf[i][j].getStyleClass().add("correct");
					} else gridTf[i][j].getStyleClass().add("false");
				}
				
			}
		}
	}
	
	/*
	 * uncolor all numbers
	 */
	public void unshowCorrect() {
		for (int i = 0; i < str8t.getN(); i++) {
			for (int j = 0; j < str8t.getN(); j++) {
				gridTf[i][j].getStyleClass().removeAll("false", "correct");
			}
		}
	}


}
