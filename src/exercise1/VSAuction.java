package exercise1;

import java.io.Serializable;

public class VSAuction implements Serializable {

    // CLASS VARIABLES

    private final String name;
    private int price = 0;

    // CONSTRUCTORS

    VSAuction(String name) {
        this.name = name;
    }

    // GETTER & SETTER

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
