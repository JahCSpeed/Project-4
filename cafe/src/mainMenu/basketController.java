package mainMenu;

import java.io.IOException;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

/**
 The basketController class handles the processing of current orders made and finalizes them as store orders.
 Orders can be deleted before being finalized, and the breakdown of how much the order costs (subtotal, sales tax, total cost)
 is shown as well.
 @author Jah C. Speed, Abe Vitangcol
 */
public class basketController implements Initializable{
	@FXML
	private Button backToMain,submitOrder;
	@FXML
	private Label orderNumberLabel,subTotalLabel,salesTaxLabel,totalLabel;
	@FXML
	private ListView<ComboBox<String>> ordersListView;
	@FXML
	private TextArea textField;
	private String [] options = {"Remove Order"};
	private final double SALES_TAX = ((6.625)/100);
	
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
	 Adds all coffee and donut orders into a list of all orders made so far.
	 Can choose to remove specific orders before submitting this set of orders.
	 */
	public void addToListView() {
		ordersListView.getItems().clear();
		for(MenuItem e : MainMenuController.currentOrder.orderList) {
			ComboBox<String> temp = new ComboBox<String>();
			temp.setPromptText(e.toString());
			temp.getItems().addAll(options);
			temp.setOnAction(event -> {
				if(temp.getValue().equals("Remove Order")) {
					MainMenuController.currentOrder.remove(e);
		    		addToListView();
		    		setLabels();
				}
			});
			ordersListView.getItems().add(temp);
		}
	}
	
	/**
	 Finalizes the orders made and places them into the list of store orders to be exported.
	 Later resets the current orders and labels to allow for the next set of orders to do the same actions again.
	 @param event A mouse click event on the "Submit Order" button.
	 */
	public void finilizeOrder(ActionEvent event) throws IOException {
		if(MainMenuController.currentOrder.isEmpty()) {
			textField.setStyle("-fx-text-fill: red ;");
			textField.setText("Can't place order with no items in basket!");
			return;
		}
		MainMenuController.storeOrders.add(MainMenuController.currentOrder);
		MainMenuController.resetOrder();
		textField.setStyle("-fx-text-fill: green ;");
		textField.setText("Order Placed!");
		addToListView();
		setLabels();
	}
	
	/**
	 Sets the labels to show how much the subtotal, sales tax, and the end total of these orders.
	 */
	private void setLabels() {
		double subtotal = 0;
		double salesTax = 0;
		double finalTotal = 0;
		for(MenuItem e : MainMenuController.currentOrder.orderList) {
			subtotal+= e.price;
		}
		salesTax = subtotal * SALES_TAX;
		finalTotal = subtotal + salesTax;
		DecimalFormat format = new DecimalFormat("$###,##0.00");
		subTotalLabel.setText("Subtotal: " + format.format(subtotal));
		salesTaxLabel.setText("Sales Tax: " + format.format(salesTax));
		totalLabel.setText("Total: " + format.format(finalTotal));
		orderNumberLabel.setText("Order Number: " + MainMenuController.currentOrder.orderNumber);
		
	}
	
	/**
	 Initializes the GUI by setting up the labels for the basket, setting up the text areas to either blank or the respective description.
	 @param arg0 used to resolve relative paths for the root object, or null if the location is not known.
	        arg1 used to localize the root object, or null if the root object was not localized.
	 */
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		orderNumberLabel.setText("Order Number: " + MainMenuController.currentOrder.orderNumber);
		textField.setText("");

		addToListView();
		setLabels();
	}
	
}
