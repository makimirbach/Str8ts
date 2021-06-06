package application;

import java.net.URL;
import java.util.ResourceBundle;

import application.Constants.GameState;
import application.Constants.Messages;
import javafx.beans.property.ReadOnlyProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;

public class MainController implements Initializable {
	@FXML
	private GridPane gpBase;
	
	@FXML
	private TextField[][] gridTf;
	
	@FXML
	private ComboBox<Integer> cbSize;
	ObservableList<Integer> lstSizes = FXCollections.observableArrayList(6);
	
	@FXML
	private Label lblMain;
	private Str8t str8t;
	
	@FXML
	private Button btnHelp;
	@FXML
	private Button btnStart;
	
	@FXML
	private CheckBox checkHints;
	
	private boolean showCorrect;
	
	private GameState state = GameState.BEFORE;
	
	
	
	
	
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
					newField.getStyleClass().addAll("blocked-black", "blocked");
					// white numbers on black cells
					if (str8t.getSolution()[i][j] < 0) newField.setText(Integer.toString(str8t.getState()[i][j]));
					newField.setEditable(false);
				}
				// black numbers on white cells from state
				else if (str8t.getState()[i][j] != 0) {
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
	
	public void startGame(int n) {

		if (state == GameState.BEFORE) {
			gpBase.setVisible(true);
			showCorrect = false;
			cbSize.setVisible(false);
			lblMain.setVisible(false);
			btnStart.setVisible(false);
			checkHints.setVisible(true);
			checkHints.setText(Messages.HINTS);
			int[][] m = {{-4,6,5,0,1,2},{5,4,6,3,2,1},{6,5,0,4,3,0},{0,1,2,0,6,5},{2,3,1,-6,5,4},{3,2,0,5,4,0}}; 
			//int[][] s = {{4,6,5,0,1,2},{5,4,6,3,2,1},{0,0,0,4,3,0},{0,0,0,0,0,0},{2,3,1,6,5,4},{3,2,0,5,4,0}};
			int[][] s = {{4,6,0,0,0,0},{0,0,0,0,0,1},{0,0,0,4,3,0},{0,0,0,0,0,0},{0,3,1,6,0,0},{3,0,0,0,4,0}};
			str8t = new Str8t(n, m, s);
			initGrid(n);
			state = GameState.PLAYING;
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
					tf.setText(str8t.getNotesString(i, j));
					str8t.enterNumber(i, j, 0);
				} else {
					if (Character.isDigit(tf.getText().toCharArray()[0])) {
						tf.getStyleClass().remove("notes");
						if (!str8t.enterNumber(i, j, Integer.valueOf(tf.getText()))) {
							tf.setText("");
						}
						str8t.print();
					} else {
						tf.setText("");
						str8t.enterNumber(i, j, 0);
					}
				}
				if (showCorrect) showThisCorrect(i,j);
			} else str8t.enterNumber(i, j, 0);
			
			if (str8t.gameOver()) {
				gameOver();
				
			}
		}
	}
	/*
	 * init layout
	 */
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		cbSize.setItems(lstSizes);
		cbSize.setPromptText(Messages.CHOOSE_SIZE);
		btnStart.setText(Messages.CLICK_START);
		state = GameState.BEFORE;
		init();	
	}
	
	public void init() {
		if (state != GameState.BEFORE) {
			gpBase.setVisible(false);
			cbSize.setVisible(true);
			lblMain.setVisible(true);
			btnStart.setVisible(true);
			state = GameState.BEFORE;
		}
		lblMain.setText(Messages.START_GAME);
		checkHints.setVisible(false);
	}
	/*
	 * very beginning: user chooses size
	 */
	public void clickStart(ActionEvent event) {
		
		if (!cbSize.getSelectionModel().isEmpty()) startGame(cbSize.getValue());
		else System.out.println("nah");
		
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
			showCurrentCorrect();
			showCorrect = true;
		} else {
			unshowCorrect();
			showCorrect = false;
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
	
	public void gameOver() {
		if (state != GameState.AFTER) {
			state = GameState.AFTER;
			Alert alert = new Alert(Alert.AlertType.INFORMATION);
		    alert.setTitle(Messages.OVER_MENU);
		    alert.setHeaderText(Messages.OVER_HEADER);
		    alert.setContentText(Messages.GAME_OVER);
		    alert.setOnHidden(e -> init());
		    alert.show();
		}
	    
	}
	
}
