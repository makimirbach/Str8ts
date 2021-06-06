package application;

import javafx.beans.property.ReadOnlyProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;

public class MainController {
	@FXML
	private GridPane gpBase;
	
	@FXML
	private TextField[][] gridTf;
	
	private Str8t str8t;
	
	
	
	
	
	/*
	 * init n x n- grid layout of labels
	 */
	public void initGrid(int n) {
		double gridHeight = gpBase.getHeight();
		double cellMeasure = gridHeight/n;
		
		gpBase.setGridLinesVisible(true);
		// n x n layout grid
		for (int i = 0; i < n; i++) {
			gpBase.getColumnConstraints().add(new ColumnConstraints(cellMeasure));
			gpBase.getRowConstraints().add(new RowConstraints(cellMeasure));
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
					newField.getStyleClass().add("blocked-black");
					// white numbers on black cells
					if (str8t.getSolution()[i][j] < 0) newField.setText(Integer.toString(str8t.getState()[i][j]));
					newField.setEditable(false);
				}
				// black numbers on white cells from state
				else if (str8t.getState()[i][j] != 0) {
					newField.setText(Integer.toString(str8t.getState()[i][j]));
					newField.getStyleClass().add("blocked-white");
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
	
	public void startGame() {
		
		int[][] m = {{-4,6,5,0,1,2},{5,4,6,3,2,1},{6,5,0,4,3,0},{0,1,2,0,6,5},{2,3,1,-6,5,4},{3,2,0,5,4,0}}; 
		int[][] state = {{4,6,0,0,0,0},{0,0,0,0,0,1},{0,0,0,4,3,0},{0,0,0,0,0,0},{0,3,1,6,0,0},{3,0,0,0,4,0}};
		str8t = new Str8t(6, m, state);
		initGrid(6);
	}
	
	/*
	 * handle exiting cell after writing numbers
	 * just one number: enter it
	 * several: -> notes
	 */
	public void tfExit(int i, int j) {
		
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
				}
			}
		}
	}
	
	public void writeTest() {
		
		String t = gridTf[0][1].getText();
		System.out.println(t);
	}
}
