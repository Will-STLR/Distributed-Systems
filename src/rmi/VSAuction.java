package vsue.rmi;

import java.io.Serializable;

public class VSAuction implements Serializable {

	private static final long serialVersionUID = 1L;

	/* The auction name. */
	private final String name;

	/* The currently highest bid for this auction. */
	private int price;

	public VSAuction(String name, int startingPrice) {
		this.name = name;
		this.price = startingPrice;
	}

	public String getName() {
		return name;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}
}