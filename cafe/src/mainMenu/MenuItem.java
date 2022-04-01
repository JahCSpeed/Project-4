package mainMenu;

public abstract class MenuItem {
	protected double price;
	protected int amount;
	protected String itemName;
	protected String itemType;
	public  double itemPrice() {
		return price;
	}
	public int getAmount() {
		return amount;
	}
	public String getItemName() {
		return itemName;
	}
	public String getType() {
		return itemType;
	}
	public int isEqual(Object obj) {
		if(obj instanceof MenuItem) {
			obj = (MenuItem)obj;
			if(((MenuItem) obj).getItemName() == getItemName()) {
				if(((MenuItem) obj).getType() == getType()) {
					return 0;
				}
			}
		}
		return -1;
		
	}
	
	public String toString() {
		return "Item: " + this.itemName + "  | Flavor: " + this.itemType + "  | Quantity: " + this.amount;
	}
}
