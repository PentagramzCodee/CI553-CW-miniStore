package clients.cashier;
import catalogue.Basket;

/**
 * The Cashier Controller
 */
public class CashierController
{
  private CashierModel model = null;
  private CashierView  view  = null;

  /**
   * Constructor
   * @param model The model
   * @param view  The view from which the interaction came
   */
  public CashierController( CashierModel model, CashierView view )
  {
    this.view  = view;
    this.model = model;
  }

  /**
   * Apply a 10% discount to the basket
   */
  public void doDiscount() {
    Basket basket = model.getBasket();
    if (basket != null && !basket.isEmpty()) {
      // Calculate 10% discount and update the basket total
      double discount = 0.10;
      double originalTotal = basket.getTotalPrice();
      double discountedTotal = originalTotal * (1 - discount);

      basket.setTotalPrice(discountedTotal); // Update the total price in the basket
      model.askForUpdate();  // Notify the model to update the view with the new total
    }
  }

  /**
   * Check interaction from view
   * @param pn The product number to be checked
   */
  public void doCheck(String pn)
  {
    model.doCheck(pn);
  }

  /**
   * Buy interaction from view
   */
  public void doBuy()
  {
    model.doBuy();
  }

  /**
   * Bought interaction from view
   */
  public void doBought()
  {
    model.doBought();
  }

  /**
   * Remove selected item from the basket.
   * @param i The index of the item to be removed
   */
  public void doRemove(int i) {
    Basket basket = model.getBasket();
    if (basket != null && i >= 0 && i < basket.size()) {
      basket.remove(i);  // Remove item at the specified index
      model.askForUpdate();  // Notify the model to update the view
    }
  }
}
