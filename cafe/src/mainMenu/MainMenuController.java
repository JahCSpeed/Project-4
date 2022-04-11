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
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import orders.Order;
import orders.OrderNumbers;
import orders.StoreOrder;

/**
 The MainMenuController class handles all the functionalities seen on main menu user interface.
 It is able to open and switch to a different scene depending on the action and holds information
 about the current orders made. 
 @author Jah C. Speed, Abe Vitangcol
 */
public class MainMenuController implements Initializable {
	@FXML
	private Button orderDonutsBtn;
	@FXML
	private Button orderCoffeeBtn;
	@FXML
	private Button gotoBasketBtn;
	@FXML
	private Button gotoStoreOrdersBtn;
	@FXML
	private ImageView donutImageView;
	@FXML
	private ImageView coffeeImageView;
	public static Order currentOrder;
	public static StoreOrder storeOrders;
	private final String DONUT_PIC = String.valueOf(getClass().getResource("donuts.png"));
	private final String COFEE_PIC = String.valueOf(getClass().getResource("coffee.jpeg"));
	
	/**
	 Opens a new scene to keep the main menu open and to do donut-related actions.
	 @param event A button click to change to the donut scene.
	 */
	public void swapToDonutScene(ActionEvent event) throws IOException {
		Pane root = (Pane)FXMLLoader.load(getClass().getResource("donutsScene.fxml"));
		Stage stage = new Stage();
		stage.setScene(new Scene(root));
		stage.show();	
	}
	
	/**
	 Opens a new scene to keep the main menu open and to do storeOrder-related actions.
	 @param event A button click to change to the storeOrder scene.
	 */
	public void swapToStoreOrderScene(ActionEvent event) throws IOException {
		Pane root = (Pane)FXMLLoader.load(getClass().getResource("storeOrders.fxml"));
		Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		stage.setScene(new Scene(root));
		stage.show();	
	}
	
	/**
	 Opens a new scene to keep the main menu open and to do coffee-related actions.
	 @param event A button click to change to the coffee scene.
	 */
	public void swapToCoffeeScene(ActionEvent event) throws IOException {
		Pane root = (Pane)FXMLLoader.load(getClass().getResource("coffeeScene.fxml"));
		Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		stage.setScene(new Scene(root));
		stage.show();	
	}
	
	/**
	 Opens a new scene to keep the main menu open and to do basket-related actions.
	 @param event A button click to change to the basket scene.
	 */
	public void swapToOrderScene(ActionEvent event) throws IOException {
		Pane root = (Pane)FXMLLoader.load(getClass().getResource("basketScene.fxml"));
		Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		stage.setScene(new Scene(root));
		stage.show();	
	}
	
	/**
	 Initializes the GUI by initializing the current order list, the list of store orders, and adds images to the GUI.
	 @param arg0 used to resolve relative paths for the root object, or null if the location is not known.
	        arg1 used to localize the root object, or null if the root object was not localized.
	 */
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		if(currentOrder == null)
			currentOrder = new Order(OrderNumbers.getOrderNumber());
		if(storeOrders == null)
			storeOrders = new StoreOrder();
		Image donutImage = new Image(DONUT_PIC);
		Image cartImage = new Image(COFEE_PIC);
		donutImageView.setImage(donutImage);
		coffeeImageView.setImage(cartImage);
		
	}
	
	/**
	 Resets the current order list in the basket after submitting an order.
	 */
	public static void resetOrder() {
		currentOrder = new Order(OrderNumbers.getOrderNumber());
	}
	
}
