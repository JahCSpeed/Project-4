package coffee;

import java.util.ArrayList;

import mainMenu.MenuItem;
import orders.Customizable;

public class Coffee extends MenuItem implements Customizable {
	public ArrayList<String>addIns;
	public Coffee(String size, String coffee, double price, int amount) {
		this.itemName = size;
		this.itemType = coffee;
		this.amount = amount;
		this.price = price;
		this.addIns = new ArrayList<String>();
	}
	
	@Override
	public boolean add(Object obj) {
		listInitilized();
		if(obj instanceof String) {
			this.addIns.add((String)obj);
			return true;
		}
		return false;
	}
	@Override
	public boolean remove(Object obj) {
		listInitilized();
		if(obj instanceof String) {
			this.addIns.remove((String)obj);
			return true;
			
		}
		return false;
	}
	private void listInitilized(){
		if(this.addIns == null) {
			this.addIns = new ArrayList<String>();
		}
	}
	@Override
	public String toString() {
		String returnString = ("Size: " + this.itemName + "  | Flavor: " + this.itemType + "  | Addins: ");
		if(addIns.size() == 0) {
			returnString+= "NONE";
		}else {
			for(int i = 0; i < addIns.size(); i++) {
				if(i != addIns.size()  -1)
					returnString+= addIns.get(i) + ", ";
				else
					returnString+= addIns.get(i);
			}
		}
		returnString+= "  |Quantity: " + this.amount;
		return returnString;
		
	}
}
