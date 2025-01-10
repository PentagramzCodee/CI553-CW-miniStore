package catalogue;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Currency;
import java.util.Formatter;
import java.util.Locale;

/**
 * A collection of products,
 * used to record the products that are to be wished to be purchased.
 * @author  Mike Smith University of Brighton
 * @version 2.2
 *
 */
public class Basket extends ArrayList<Product> implements Serializable {
  private static final long serialVersionUID = 1;
  private int theOrderNum = 0; // Order number
  private double totalPrice = 0.00; // Total price of items in the basket

  /**
   * Constructor for a basket which is
   *  used to represent a customer order/ wish list
   */
  public Basket() {
    theOrderNum = 0;
  }

  /**
   * Set the customers unique order number
   * Valid order Numbers 1 .. N
   * @param anOrderNum A unique order number
   */
  public void setOrderNum(int anOrderNum) {
    theOrderNum = anOrderNum;
  }

  /**
   * Returns the customers unique order number
   * @return the customers order number
   */
  public int getOrderNum() {
    return theOrderNum;
  }

  /**
   * Add a product to the Basket.
   * Product is appended to the end of the existing products
   * in the basket.
   * @param pr A product to be added to the basket
   * @return true if successfully adds the product
   */
  @Override
  public boolean add(Product pr) {
    return super.add(pr); // Call add in ArrayList
  }

  /**
   * Remove a product item by product number
   * @param productNum Product number to remove
   * @return true if the item was removed successfully
   */
  public boolean removeItem(String productNum) {
    for (int i = 0; i < this.size(); i++) {
      if (this.get(i).getProductNum().equals(productNum)) {
        this.remove(i); // Remove the item from the basket
        return true; // Return success
      }
    }
    return false; // Item not found
  }

  /**
   * Returns a description of the products in the basket suitable for printing.
   * @return a string description of the basket products
   */
  public String getDetails() {
    Locale uk = Locale.UK;
    StringBuilder sb = new StringBuilder(256);
    Formatter fr = new Formatter(sb, uk);
    String csign = (Currency.getInstance(uk)).getSymbol();
    double total = 0.00;

    if (theOrderNum != 0) {
      fr.format("Order number: %03d\n", theOrderNum);
    }

    if (this.size() > 0) {
      for (Product pr : this) {
        int number = pr.getQuantity();
        fr.format("%-7s", pr.getProductNum());
        fr.format("%-14.14s ", pr.getDescription());
        fr.format("(%3d) ", number);
        fr.format("%s%7.2f", csign, pr.getPrice() * number);
        fr.format("\n");
        total += pr.getPrice() * number;
      }
      fr.format("----------------------------\n");
      fr.format("Total                       ");
      fr.format("%s%7.2f\n", csign, total);
      fr.close();
    }

    // Apply 10% discount
    double discount = total * 0.10;
    double discountedTotal = total - discount;

    // Include the discounted total in the details
    sb.append("----------------------------\n");
    sb.append("Discount (10%)             ");
    sb.append(csign).append(String.format("%7.2f\n", discount));
    sb.append("Total after Discount       ");
    sb.append(csign).append(String.format("%7.2f\n", discountedTotal));

    // Set the final discounted total price
    setTotalPrice(discountedTotal);

    return sb.toString();
  }

  /**
   * Get the total price of the basket (after discount)
   * @return total price of items in the basket after discount
   */
  public double getTotalPrice() {
    return totalPrice;
  }

  /**
   * Set the total price of the basket (after discount)
   * @param totalPrice the new total price
   */
  public void setTotalPrice(double totalPrice) {
    this.totalPrice = totalPrice;
  }

  /**
   * Method to apply a 10% discount to the total price
   */
  public void applyDiscount() {
    double discount = totalPrice * 0.10;
    totalPrice -= discount;
  }
}
