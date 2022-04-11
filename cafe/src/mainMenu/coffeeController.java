package mainMenu;

import java.io.IOException;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.ResourceBundle;

import coffee.Coffee;
import coffee.CoffeePrices;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.InputEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

/**
 The coffeeController class handles the main functionalities as seen in Coffee.java and can customize Coffees in a user interface.
 Able to create a coffee of different sizes with different addins as well as notice if invalid coffee inputs were attempted
 to be entered into the system.
 @author Jah C. Speed, Abe Vitangcol
 */
public class coffeeController implements Initializable {
	@FXML
	private Label allOrdersubtotalLabel, subtotalLabel;
	@FXML
	private Button backToMain,completeOrder;
	@FXML
	private ComboBox<String> drinkSizesBox;
	@FXML
	private TextField amountField;
	@FXML
	private TextArea errorField;
	@FXML
	private RadioButton smallCheck,tallCheck,grandeCheck,ventiCheck;
	@FXML
	private CheckBox creamCheck,syrupCheck,milkCheck,caramelCheck,whippedCheck;
	@FXML
	private ListView<ComboBox<String>> coffeeOrdersListView;
	private String [] options = {"Edit Order", "Remove Order"};
	private final double ADD_IN_PRICE = 0.30;
	private final DecimalFormat format = new DecimalFormat("$###,##0.00");
	
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
	 Initializes the GUI by initializing size checklist and the list view full of current coffee orders.
	 @param arg0 used to resolve relative paths for the root object, or null if the location is not known.
	        arg1 used to localize the root object, or null if the root object was not localized.
	 */
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		ToggleGroup tg = new ToggleGroup();
		this.smallCheck.setToggleGroup(tg);
		this.tallCheck.setToggleGroup(tg);
		this.grandeCheck.setToggleGroup(tg);
		this.ventiCheck.setToggleGroup(tg);
		addToListView();

	}
	
	/**
	 Forces the amount field box to contain only numbers in it.
	 Any non-number entered will be deleted immediately.
	 @param event A type event into the amount field.
	 */
	public void numbersOnly(InputEvent event) {
		String text = (amountField.getText());
		String newText = "";
		try{
			Double.parseDouble(text);
			String[]broken = text.split("");
			if(broken[0].equals("0")) {
				for(int i = 1; i < broken.length;i++) {
					if(isNumeric(broken[i])) {
							newText+=broken[i];
					}
				}
				amountField.setText(newText);
			}
			
		}catch(NumberFormatException e) {
			String[]broken = text.split("");
			for(int i = 0; i < broken.length;i++) {
				if(isNumeric(broken[i])) {
					if(!(text.length() == 0 && Integer.parseInt(text) == 0))
						newText+=broken[i];
				}
			}
			amountField.setText(newText);
		}
		
	}
	
	/**
	 Checks if the number on the amount line is a number value.
	 @param str The number entered on the quantity line.
	 @return true if the line is a valie number, false if it is not a number or negative.
	 */
	public static boolean isNumeric(String str) { 
		  try {  
		    Double.parseDouble(str);  
		    return true;
		  } catch(NumberFormatException e){  
		    return false;  
		  }  
		}
	
	/**
	 Performs a specific action based on the checkboxes filled.
	 Calculates the prices correctly and checks if there are any unfilled necessary boxes.
	 Capable of throwing errors for not selecting a size. If the amount field textbox was not filled, it is assumed to be 1.
	 @param event A mouse click on the "Submit Order" button.
	 */
	public void actionDone(ActionEvent event) {
		String size = "";
		errorField.setStyle("-fx-text-fill: green ;");
		errorField.setText("");
		double total = 0;
		if(smallCheck.isSelected()) {
			total = CoffeePrices.SMALL.price;
			size = "Small";
		}
		if(tallCheck.isSelected()) {
			total = CoffeePrices.TALL.price;
			size = "Tall";
		}
		if(grandeCheck.isSelected()) {
			total = CoffeePrices.GRANDE.price;
			size = "Grande";
		}
		if(ventiCheck.isSelected()) {
			total = CoffeePrices.VENTI.price;
			size = "Venti";
		}
		if(!checkForSize()) {
			errorField.setStyle("-fx-text-fill: red ;");
			errorField.setText("Must select size first!");
			resetAddChecks();
			return;
		}
		if(creamCheck.isSelected()) {
			total+= ADD_IN_PRICE;
		}
		if(syrupCheck.isSelected()) {
			total+= ADD_IN_PRICE;
		}
		if(milkCheck.isSelected()) {
			total+= ADD_IN_PRICE;
		}
		if(caramelCheck.isSelected()) {
			total+= ADD_IN_PRICE;
		}
		if(whippedCheck.isSelected()) {
			total+= ADD_IN_PRICE;
		}
		int amount = 1;
		if(!amountField.getText().isEmpty()) {
			amount = Integer.parseInt(amountField.getText());
		}
		total = total * amount;
		subtotalLabel.setText(format.format(total));
		if(event.getSource().equals(completeOrder)) {
			Coffee orderCoffee = new Coffee(size,"Black",total,amount);
			if(creamCheck.isSelected()) {
				orderCoffee.addIns.add(creamCheck.getText());
			}
			if(syrupCheck.isSelected()) {
				orderCoffee.addIns.add(syrupCheck.getText());
			}
			if(milkCheck.isSelected()) {
				orderCoffee.addIns.add(milkCheck.getText());
			}
			if(caramelCheck.isSelected()) {
				orderCoffee.addIns.add(caramelCheck.getText());
			}
			if(whippedCheck.isSelected()) {
				orderCoffee.addIns.add(whippedCheck.getText());
			}
			errorField.setStyle("-fx-text-fill: green ;");
			errorField.setText("Order added to cart");
			MainMenuController.currentOrder.add(orderCoffee);
			subtotalLabel.setText(format.format(0));
			amountField.setText("");
			addToListView();
			resetAddChecks();
			resetSizeChecks();
		}
	}
	
	/**
	 Adds the coffee order into the list of coffee orders made so far.
	 Can be edited to change the order or be removed before going to the basket to confirm the order.
	 */
	public void addToListView() {
		coffeeOrdersListView.getItems().clear();
		double subTotal = 0;
		for(MenuItem e : MainMenuController.currentOrder.orderList) {
			if(!(e instanceof Coffee)) {
				continue;
			}
			ComboBox<String> temp = new ComboBox<String>();
			temp.setPromptText(e.toString() + "  | Price: " + format.format(e.itemPrice()));
			temp.getItems().addAll(options);
			temp.setOnAction(event -> {
				if(temp.getValue().equals("Edit Order")) {
					MainMenuController.currentOrder.remove(e);
					setEdit(e.itemName,e.amount,((Coffee)e).addIns,e.itemPrice());
					addToListView();
					
				}
				if(temp.getValue().equals("Remove Order")) {
					MainMenuController.currentOrder.remove(e);
		    		addToListView();	
				}
			});
			coffeeOrdersListView.getItems().add(temp);
			subTotal+= e.itemPrice();
			
		}
		allOrdersubtotalLabel.setText("Subtotal: " + format.format(subTotal));
		

	}
	
	/**
	 Edit the selected order and change various fields before submitting it again.
	 @param size The size of the coffee.
	        amount The amount of coffees of these specific parameters desired.
	        addins The addins for this specific coffee.
	        price The current price for this coffee order.
	 */
	private void setEdit(String size,int amount,ArrayList<String> addins,double price) {
		switch(size) {
			case "Small":
				smallCheck.setSelected(true);
				break;
			case "Tall":
				tallCheck.setSelected(true);
				break;
			case "Grande":
				grandeCheck.setSelected(true);
				break;
			case "Venti":
				ventiCheck.setSelected(true);
				break;
		}
		for(String add: addins) {
			if(add.equals(creamCheck.getText())) {
				creamCheck.setSelected(true);
			}
			if(add.equals(syrupCheck.getText())) {
				syrupCheck.setSelected(true);
			}
			if(add.equals(milkCheck.getText())) {
				milkCheck.setSelected(true);
			}
			if(add.equals(caramelCheck.getText())) {
				caramelCheck.setSelected(true);
			}
			if(add.equals(whippedCheck.getText())) {
				whippedCheck.setSelected(true);
			}
		}
		subtotalLabel.setText(format.format(price));
		amountField.setText(String.valueOf(amount));
	}
	
	/**
	 Checks if any of the size boxes were selected or not.
	 @return True if any of them have been selected, false otherwise.
	 */
	public boolean checkForSize() {
		if(!smallCheck.isSelected() && !tallCheck.isSelected() && !grandeCheck.isSelected() && !ventiCheck.isSelected()) {
			return false;
		}
		return true;
	}
	
	/**
	 Resets the addin selections after processing a coffee order.
	 */
	public void resetAddChecks() {
		creamCheck.setSelected(false);
		syrupCheck.setSelected(false);
		milkCheck.setSelected(false);
		caramelCheck.setSelected(false);
		whippedCheck.setSelected(false);
	}
	
	/**
	 Resets the size selection after processing a coffee order.
	 */
	public void resetSizeChecks() {
		smallCheck.setSelected(false);
		tallCheck.setSelected(false);
		grandeCheck.setSelected(false);
		ventiCheck.setSelected(false);
	}
}
