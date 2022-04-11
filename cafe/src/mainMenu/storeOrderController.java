package mainMenu;

import java.io.FileWriter;
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
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import orders.Order;

/**
 The storeOrderController handles all the main functionalities seen in storeOrder.java on a user interface.
 Capable of viewing the current items in an order and can delete items before the order is submitted.
 @author Jah C. Speed, Abe Vitangcol
 */
public class storeOrderController implements Initializable{
	@FXML
	public Button backToMain,submitOrder,exportOrder;
	@FXML
	private TextArea printArea;
	@FXML
	private ListView<ComboBox<String>> ordersListView;
	private String [] options = {"Remove Order"};
	
	/**
	 Swaps the current scene back to the main menu in order to do other actions.
	 @param event A mouse click on the "Main Menu" button.
	 */
	public void swapToMainMenuScene(ActionEvent event) throws IOException {
		Pane root = (Pane)FXMLLoader.load(getClass().getResource("MainMenu.fxml"));
		Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		stage.setScene(new Scene(root));
		stage.show();
	}
	
	/**
	 Exports the list of orders from the current session to a file locally.
	 Cannot do so if the list of orders is empty.
	 @param event A mouse click on the "Export" button.
	 */
	public void exportData(ActionEvent event) {
		if(MainMenuController.storeOrders.isEmpty()) {
			 printArea.setStyle("-fx-text-fill: red ;");
			 printArea.setText("No orders to export!");
			 return;
		}
		String data = "";
		for(Order e : MainMenuController.storeOrders.storeOrders) {
			data+= e.toString();
			data+= "\n";
		}
		try {
			FileWriter output = new FileWriter("All Store Orders.txt");
		    output.write(data);
		    output.close();
		    printArea.setStyle("-fx-text-fill: green ;");
			printArea.setText("All orders exported!");
		}catch (Exception e) {}
	}
	
	/**
	 Adds a list view of all the orders made so far, giving the option to remove the said order before they are submitted.
	 */
	public void addToListView() {
		ordersListView.getItems().clear();
		for(Order e : MainMenuController.storeOrders.storeOrders) {
			ComboBox<String> temp = new ComboBox<String>();
			temp.setPromptText(e.toString());
			temp.getItems().addAll(options);
			temp.setOnAction(event -> {
				if(temp.getValue().equals("Remove Order")) {
					MainMenuController.storeOrders.remove(e);
		    		addToListView();
				}
			});
			ordersListView.getItems().add(temp);
		}
	}
	
	/**
	 Initializes the GUI by showing the list of all orders from the current session.
	 @param arg0 used to resolve relative paths for the root object, or null if the location is not known.
	        arg1 used to localize the root object, or null if the root object was not localized.
	 */
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		addToListView();
	}
	
	
}
