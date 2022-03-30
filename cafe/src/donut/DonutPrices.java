package donut;

public enum DonutPrices {
	YEAST("Yeast Donut",1.59),
	CAKE("Cake Donut",1.79),
	HOLE("Donut Hole",0.39);
	
	public final double price;
	public final String name;
	
	private DonutPrices(String name,double price) {
		this.name = name;
		this.price = price;
	}
}