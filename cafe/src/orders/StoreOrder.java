package orders;

import java.util.ArrayList;

public class StoreOrder implements Customizable {
	public ArrayList<Order> storeOrders;
	public StoreOrder() {
		this.storeOrders = new ArrayList<Order>();
	}
	@Override
	public boolean add(Object obj) {
		listInitilized();
		if(obj instanceof Order) {
			this.storeOrders.add((Order)obj);
			return true;
		}
		return false;
	}
	@Override
	public boolean remove(Object obj) {
		listInitilized();
		if(obj instanceof Order) {
			this.storeOrders.remove((Order)obj);
			return true;
		}
		return false;
	}
	
	private void listInitilized(){
		if(this.storeOrders == null) {
			this.storeOrders = new ArrayList<Order>();
		}
	}
	public boolean isEmpty() {
		if(this.storeOrders.isEmpty())
			return true;
		else
			return false;
	}
}
