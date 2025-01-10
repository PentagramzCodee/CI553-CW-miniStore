package clients.cashier;

import catalogue.Basket;
import middle.MiddleFactory;
import middle.OrderProcessing;
import middle.StockReadWriter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.Observable;
import java.util.Observer;

public class CashierView implements Observer {
  private static final int H = 300; // Height of window pixels
  private static final int W = 400; // Width of window pixels

  private static final String CHECK = "Check";
  private static final String BUY = "Buy";
  private static final String BOUGHT = "Bought/Pay";
  private static final String REMOVE = "Remove";

  private final JLabel pageTitle = new JLabel();
  private final JLabel theAction = new JLabel();
  private final JTextField theInput = new JTextField();
  private final JTextArea theOutput = new JTextArea();
  private final JScrollPane theSP = new JScrollPane();
  private final JButton theBtCheck = new JButton(CHECK);
  private final JButton theBtBuy = new JButton(BUY);
  private final JButton theBtBought = new JButton(BOUGHT);
  private final JButton theBtRemove = new JButton(REMOVE);
  private final JButton theBtDiscount = new JButton("10% Off");

  private StockReadWriter theStock = null;
  private OrderProcessing theOrder = null;
  private CashierController cont = null;

  public CashierView(RootPaneContainer rpc, MiddleFactory mf, int x, int y) {
    try {
      theStock = mf.makeStockReadWriter(); // Database access
      theOrder = mf.makeOrderProcessing(); // Process order
    } catch (Exception e) {
      System.out.println("Exception: " + e.getMessage());
    }

    Container cp = rpc.getContentPane(); // Content Pane
    Container rootWindow = (Container) rpc; // Root Window
    cp.setLayout(null); // No layout manager
    rootWindow.setSize(W, H); // Size of Window
    rootWindow.setLocation(x, y);

    Font f = new Font("Monospaced", Font.PLAIN, 12); // Font f is

    pageTitle.setBounds(110, 0, 270, 20);
    pageTitle.setText("Thank You for Shopping at MiniStore");
    cp.add(pageTitle);

    theBtCheck.setBounds(16, 25 + 60 * 0, 80, 40); // Check Button
    theBtCheck.addActionListener(e -> cont.doCheck(theInput.getText()));
    cp.add(theBtCheck); // Add to canvas

    theBtBuy.setBounds(16, 25 + 60 * 1, 80, 40); // Buy button
    theBtBuy.addActionListener(e -> cont.doBuy());
    cp.add(theBtBuy); // Add to canvas

    theBtBought.setBounds(16, 25 + 60 * 2, 80, 40); // Bought Button
    theBtBought.addActionListener(e -> cont.doBought());
    cp.add(theBtBought); // Add to canvas

    // 10% Discount Button
    theBtDiscount.setBounds(110, 25 + 60 * 5, 270, 40);
    theBtDiscount.addActionListener(e -> cont.doDiscount()); // Trigger discount
    cp.add(theBtDiscount);

    // Add "Remove" button to canvas
    theBtRemove.setBounds(110, 25 + 60 * 3, 270, 40); // Adjust position
    theBtRemove.setText("Remove Item");
    theBtRemove.addActionListener(e -> cont.doRemove(0)); // Modify to remove the correct item
    cp.add(theBtRemove); // Add to canvas

    theAction.setBounds(110, 25, 270, 20); // Message area
    theAction.setText(""); // Blank
    cp.add(theAction); // Add to canvas

    theInput.setBounds(110, 50, 270, 40); // Input Area
    theInput.setText(""); // Blank
    cp.add(theInput); // Add to canvas

    theSP.setBounds(110, 100, 270, 160); // Scrolling pane
    theOutput.setText(""); // Blank
    theOutput.setFont(f); // Uses font
    cp.add(theSP); // Add to canvas
    theSP.getViewport().add(theOutput); // In TextArea

    rootWindow.setVisible(true); // Make visible
    theInput.requestFocus(); // Focus is here
  }

  public void setController(CashierController c) {
    cont = c;
  }

  @Override
  public void update(Observable modelC, Object arg) {
    CashierModel model = (CashierModel) modelC;
    String message = (String) arg;
    theAction.setText(message);

    Basket basket = model.getBasket();
    if (basket == null)
      theOutput.setText("Customer's order");
    else {
      // Display updated total price after discount
      theOutput.setText(basket.getDetails());
    }

    theInput.requestFocus();  // Focus is here
  }

  public void updateWithString(Observable modelC, Object arg) {
    if (arg instanceof String) {
      theOutput.setText((String) arg); // Display messages from the model
    }
    if (arg instanceof Basket) {
      Basket basket = (Basket) arg;
      theOutput.setText(basket.getDetails()); // Refresh basket details
    }
  }
}
