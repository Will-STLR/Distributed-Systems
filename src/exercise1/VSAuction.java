package exercise1;

import java.io.Serializable;

//Serializable erlaubt es uns die Infomrationen wie name und price bzw das ganze Objekt in bytes an unsern Server zu schicken.
//Das muessen wir so machen, da das Netzwerk nur einen Bytestrom akzeptiert.
public class VSAuction implements Serializable {
    // INSTANC/CLASS VARIABLES
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
