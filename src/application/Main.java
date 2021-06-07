package application;
	
import application.service.Helper;
import application.service.Str8t;
import application.service.Str8tSolver;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;


public class Main extends Application {
	
	
	@Override
	public void start(Stage primaryStage) {
		try {
			Parent root = FXMLLoader.load(getClass().getResource("resources/view/Main.fxml"));
			Scene scene = new Scene(root);
			scene.getStylesheets().add(getClass().getResource("resources/css/application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
		int[][] m = {{-4,6,5,0,1,2},{5,4,6,3,2,1},{6,5,0,4,3,0},{0,1,2,0,6,5},{2,3,1,-6,5,4},{3,2,0,5,4,0}};
		//int[][] state = {{4,6,5,0,1,2},{5,4,6,3,2,1},{0,0,0,4,3,0},{0,0,0,0,0,0},{2,3,1,6,5,4},{3,2,0,5,4,0}};
		//int[][] state = {{4,6,5,0,1,2},{5,4,6,3,2,1},{6,5,0,4,3,0},{0,1,2,0,6,5},{2,3,1,6,5,4},{3,2,0,5,4,0}};
		int[][] state = {{4,6,0,0,0,0},{0,0,0,0,0,1},{0,0,0,4,3,0},{0,0,0,0,0,0},{0,3,1,6,0,0},{3,0,0,0,4,0}};
		Str8tSolver str8t = new Str8tSolver(Helper.cellMatrixFromEntries(state, m), Helper.cellMatrixFromEntries(m,m), 6);
		
		
		System.out.println(str8t.getState()[0][0]);
		System.out.println(str8t.getAllStreets());
	}
}
