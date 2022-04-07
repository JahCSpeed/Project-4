package donut;

import mainMenu.MenuItem;

public class Donut extends MenuItem {
	public Donut(String donutName, String donutType, double price, int amount ) {
		this.itemName = donutName;
		this.itemType = donutType;
		this.amount = amount;
		this.price = price;
	}

}
