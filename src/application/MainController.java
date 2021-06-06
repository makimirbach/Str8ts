package application;

import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.TextField;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.text.Font;

public class MainController {
	@FXML
	private GridPane gpBase;
	
	@FXML
	private TextField[][] gridTf;
	
	
	
	
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
				newField.setAlignment(Pos.CENTER);
				newField.setFont(Font.font("Calibri Light", 40));
				gpBase.add(newField, i, j);  // column=i row=j
				gridTf[i][j] = newField;
			}
		}
	}
	
	public void startGame() {
		initGrid(9);
		System.out.println("go!");
	}
	
	/*
	 * handle exiting cell after writing numbers
	 * just one number: enter it
	 * several: -> notes
	 */
	public void tfExit() {
		
	}
	
	public void writeTest() {
		String t = gridTf[0][0].getText();
		gridTf[4][5].setText(t);
	}
}
