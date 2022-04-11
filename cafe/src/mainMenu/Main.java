package mainMenu;
	
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.fxml.FXMLLoader;

/**
 The Main class is a method to launch the MainMenuController class.
 Simply run the class with the Virtual Machine arguments to launch the gui for this project.
 @author Jah C. Speed, Abe Vitangcol
 */
public class Main extends Application {
	/**
	 Starts the stage of the MainMenu to be used as the User Interface.
	 @param primaryStage The main menu stage that controls all the other scenes.
	 */
	@Override
	public void start(Stage primaryStage) {
		try {
			Pane root = (Pane)FXMLLoader.load(getClass().getResource("MainMenu.fxml"));
			Scene scene = new Scene(root,760,490);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 Launches the gui upon calling this class.
	 @param args The Virtual Machine Arguments, which includes JavaFX 17 or 18's library, needed
	 			 to run the gui.
	 			 (VM Arguments: --module-path "(Java 17 or 18 library location)" --add-modules javafx.controls,javafx.fxml)
	 */
	public static void main(String[] args) {
		launch(args);
	}
}
