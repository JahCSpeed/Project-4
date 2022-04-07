package orders;

public class OrderNumbers {
	private static int number = 0;
	
	public static int getOrderNumber() {
		return ++number;
	}
}
