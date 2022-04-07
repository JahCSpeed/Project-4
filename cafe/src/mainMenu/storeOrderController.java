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

public class storeOrderController implements Initializable{
	@FXML
	public Button backToMain,submitOrder,exportOrder;
	@FXML
	private TextArea printArea;
	@FXML
	private ListView<ComboBox<String>> ordersListView;
	private String [] options = {"Remove Order"};
	public void swapToMainMenuScene(ActionEvent event) throws IOException {
		Pane root = (Pane)FXMLLoader.load(getClass().getResource("MainMenu.fxml"));
		Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		stage.setScene(new Scene(root));
		stage.show();
	}
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
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		addToListView();
	}
	
	
}
