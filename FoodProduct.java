package productSiteTerminal;

public class FoodProduct {
    int id, price;
    String SKU, description, category;

    public FoodProduct(String SKU, String description, String category,int price) {
        this.price = price;
        this.SKU = SKU;
        this.description = description;
        this.category = category;
    }

    public FoodProduct(int id, String SKU, String description, String category,int price) {
        this.id = id;
        this.price = price;
        this.SKU = SKU;
        this.description = description;
        this.category = category;
    }

    public int getId() {
        return id;
    }

    public int getPrice() {
        return price;
    }

    public String getSKU() {
        return SKU;
    }

    public String getDescription() {
        return description;
    }

    public String getCategory() {
        return category;
    }


}
