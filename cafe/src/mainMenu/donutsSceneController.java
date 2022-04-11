package mainMenu;
import java.io.IOException;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Random;
import java.util.ResourceBundle;

import donut.Donut;
import donut.DonutPrices;
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
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

/**
 The donutsSceneController class handles the main functionalities in ordering and customizing a donut.
 Randomizes the available flavors in the store and handles the ordering of either cake donuts, yeast donuts,
 or donut holes for an order and can handle different amounts.
 @author Jah C. Speed, Abe Vitangcol
 */
public class donutsSceneController implements Initializable{
	@FXML
	private Button backToMain,confirmOrder;
	@FXML
	private ComboBox<String> donutTypesComboBox,donutFlavorComboBox;
	@FXML
	private TextField amountField;
	@FXML
	private TextArea printArea;
	@FXML
	private Label subtotalLabel;
	@FXML
	private ListView<ComboBox<String>> donutOrdersListView;
	private final String[] donutTypes = {"Yeast Donut","Cake Donut","Donut Holes"};
	private static String[] donutFlavors = {"Glazed", "Old Fassioned", "Jelly", "Blueberry", "Chocloate Frosted",
				"Boston Creme", "Chocolate Glazed", "Butter Nut", "Chocolate Creme", "French Cruller", "Double Chocolate",
				"Maple Frosted"};
	private final String [] options = {"Edit Order", "Remove Order"};
	private final int MIN_FLAVORS = 3;
	private final int MAX_FLAVORS = 5;
	private static ArrayList<String> flavorsOfTheDay;
	private int amount;
	
	/**
	 Initializes the GUI by initializing the randomized flavors of the day, setting print areas to blank, and the list view full of donut orders.
	 @param arg0 used to resolve relative paths for the root object, or null if the location is not known.
	        arg1 used to localize the root object, or null if the root object was not localized.
	 */
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		donutTypesComboBox.getItems().addAll(donutTypes);
		if(flavorsOfTheDay == null) {
			flavorsOfTheDay = getRandomFlavors(donutFlavors,randomIntFromInterval(MIN_FLAVORS,MAX_FLAVORS));
		}
		donutFlavorComboBox.getItems().addAll(flavorsOfTheDay);
		printArea.setStyle("-fx-text-fill: green ;");
		addToListView();
	}
	
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
	 Confirms the order to add it to the basket to be checked out.
	 Correctly compiles the clicked and customized areas to have a donut order.
	 @param event A mouse click on the "Confirm" button.
	 */
	public void confirmOrder(ActionEvent event) {
		String type = donutTypesComboBox.getValue();
		String flavor = donutFlavorComboBox.getValue();
		if(checkDonutType() == -1 || checkDonutFlavor() == -1 || checkAmount() == -1) {
			return;
		}
		switch(type){
			case "Yeast Donut":
				MainMenuController.currentOrder.add(new Donut(DonutPrices.YEAST.name,flavor,DonutPrices.YEAST.price,this.amount));
				break;
			case "Cake Donut":
				MainMenuController.currentOrder.add(new Donut(DonutPrices.CAKE.name,flavor,DonutPrices.CAKE.price,this.amount));
				break;
			case "Donut Holes":
				MainMenuController.currentOrder.add(new Donut(DonutPrices.HOLE.name,flavor,DonutPrices.HOLE.price,this.amount));
				break;
			default:
				return;		
		}
		printArea.setStyle("-fx-text-fill: green ;");
		printArea.setText("Order added to cart");
		addToListView();
		reset();
	}
	
	/**
	 Resets all the changeable fields back to normal after processing an order.
	 */
	public void reset() {
		this.amountField.clear();
		this.donutTypesComboBox.valueProperty().set(null);
		this.donutFlavorComboBox.valueProperty().set(null);
	}
	
	/**
	 Adds the donut order into the list of donut orders made so far.
	 Can be edited to change the order or be removed before going to the basket to confirm the order.
	 */
	public void addToListView() {
		DecimalFormat format = new DecimalFormat("$###,##0.00");
		donutOrdersListView.getItems().clear();
		double subTotal = 0;
		for(MenuItem e : MainMenuController.currentOrder.orderList) {
			if(!(e instanceof Donut)) {
				continue;
			}
			ComboBox<String> temp = new ComboBox<String>();
			temp.setPromptText(e.toString() + "  | Price: " + format.format(e.itemPrice()));
			temp.getItems().addAll(options);
			temp.setOnAction(event -> {
				if(temp.getValue().equals("Edit Order")) {
					MainMenuController.currentOrder.remove(e);
					setEdit(e.itemName,e.itemType,e.amount);
					addToListView();
					
				}
				if(temp.getValue().equals("Remove Order")) {
					MainMenuController.currentOrder.remove(e);
		    		addToListView();	
				}
			});
			donutOrdersListView.getItems().add(temp);
			subTotal+= e.itemPrice();
		}
		subtotalLabel.setText("Subtotal: " + format.format(subTotal));
	}
	
	/**
	 Edit the selected order and change various fields before submitting it again.
	 @param donutType The type of donut selected of the specific order.
	        flavor The flavor of the donut of the specific order.
	        amount The amount of donuts desired as specified on the order.
	 */
	private void setEdit(String donutType, String flavor, int amount) {
		this.amountField.setText(String.valueOf(amount));
		this.donutTypesComboBox.valueProperty().set(donutType);
		this.donutFlavorComboBox.valueProperty().set(flavor);
		
	}
	
	/**
	 Checks if the amount field is a valid number.
	 @return -1 if the number is less than or equal to 0 or not a number, 0 otherwise.
	 */
	private int checkAmount() {
		try {
			this.amount = Integer.parseInt(amountField.getText());
			if(this.amount <= 0) {
				printArea.setStyle("-fx-text-fill: red ;");
				this.printArea.setText("Amount can not be less than or equal to 0!");
				return -1;
			}
		}catch(NumberFormatException e) {
			printArea.setStyle("-fx-text-fill: red ;");
			this.printArea.setText("Amount must be a valid number!");
			return -1;
		}
		
		return 0;
	}
	
	/**
	 Checks if a donut type was selected in the order process.
	 @return -1 if the donut type was not selected, 0 otherwise.
	 */
	private int checkDonutType() {
		if(donutTypesComboBox.getValue() == null) {
			printArea.setStyle("-fx-text-fill: red ;");
			printArea.setText("Must select Donut Type");
			return -1;
		}
		
		return 0;
	}
	
	/**
	 Checks if the donut flavor was selected in the order process.
	 @return -1 if the donut flavor was not selected, 0 otherwise.
	 */
	private int checkDonutFlavor() {
		if(donutFlavorComboBox.getValue() == null) {
			printArea.setStyle("-fx-text-fill: red ;");
			printArea.setText("Must select Donut Flavor");
			return -1;
		}
		
		return 0;
	}
	
	/**
	 Gets a random number of flavors to put into the flavors menu.
	 The flavor numbers are as defined at the top as MIN_FLAVORS and MAX_FLAVORS
	 @param list The list of flavors possible.
	        pullItems The number of flavors to pull from the list.
	 @return The list of random flavors the menu will have.
	 */
	public ArrayList<String>getRandomFlavors(String[]list, int pullItems){
        Random rand = new Random();
        ArrayList<String> newList = new ArrayList<String>();
        for (int i = 0; i < pullItems; i++) {
            int randomIndex = rand.nextInt(list.length);
            if(!newList.contains(list[randomIndex]))
            	newList.add(list[randomIndex]);
            else {
            	i--;
            }
        }
        return newList;
    }
	
	/**
	 Generates a random integer value from a stated range.
	 @param min The minimum value of the range.
	        max The maximum value of the range.
	 @return The random integer generated.
	 */
	public int randomIntFromInterval(int min, int max) {
		  return (int) Math.floor(Math.random() * (max - min + 1) + min);
	}
	
}
