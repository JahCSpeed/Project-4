package mainMenu;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import orders.Order;
import orders.OrderNumbers;

public class MainMenuController implements Initializable {
	@FXML
	private Button orderDonutsBtn;
	@FXML
	private Button orderCoffeeBtn;
	@FXML
	private Button gotoBasketBtn;
	@FXML
	private Button gotoStoreOrdersBtn;
	public static Order currentOrder;
	public void swapToDonutScene(ActionEvent event) throws IOException {
		Pane root = (Pane)FXMLLoader.load(getClass().getResource("donutsScene.fxml"));
		Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		stage.setScene(new Scene(root));
		stage.show();	
	}
	public void swapToCoffeeScene(ActionEvent event) throws IOException {
		Pane root = (Pane)FXMLLoader.load(getClass().getResource("coffeeScene.fxml"));
		Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		stage.setScene(new Scene(root));
		stage.show();	
	}
	public void swapToOrderScene(ActionEvent event) throws IOException {
		Pane root = (Pane)FXMLLoader.load(getClass().getResource("basketScene.fxml"));
		Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		stage.setScene(new Scene(root));
		stage.show();	
	}
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		if(currentOrder == null)
			currentOrder = new Order(OrderNumbers.getOrderNumber());
		
	}
}
