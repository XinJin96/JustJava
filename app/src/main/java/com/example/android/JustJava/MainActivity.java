/**
 * IMPORTANT: Make sure you are using the correct package name.
 * This example uses the package name:
 * package com.example.android.justjava
 * If you get an error when copying this code into Android studio, update it to match teh package name found
 * in the project's AndroidManifest.xml file.
 **/

package com.example.android.JustJava;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.example.JustJava.R;


/**
 * This app displays an order form to order coffee.
 */
public class MainActivity extends AppCompatActivity {

    int quantity = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     * This method is called when the plus button is clicked.
     */
    public void increment(View view) {
        if (quantity>=100)
        {
            quantity=100;
            displayQuantity(quantity);
        }
        else
        {
            quantity = quantity + 1;
            displayQuantity(quantity);
        }
        CheckBox whippedCreamCheckBox = (CheckBox) findViewById(R.id.whipped_cream_checkbox);
        boolean hasWhippedCream = whippedCreamCheckBox.isChecked();

        // Figure out if the user wants chocolate topping
        CheckBox chocolateCheckBox = (CheckBox) findViewById(R.id.chocolate_checkbox);
        boolean hasChocolate = chocolateCheckBox.isChecked();

        // Calculate the price
        int price = calculatePrice(hasWhippedCream, hasChocolate);
        displayMessage((String.valueOf(price)));
    }

    /**
     * This method is called when the minus button is clicked.
     */
    public void decrement(View view) {
        if(quantity==0)
        {
            quantity=0;
            displayQuantity(quantity);
        }
        else {
            quantity = quantity - 1;
            displayQuantity(quantity);
        }
        CheckBox whippedCreamCheckBox = (CheckBox) findViewById(R.id.whipped_cream_checkbox);
        boolean hasWhippedCream = whippedCreamCheckBox.isChecked();

        // Figure out if the user wants chocolate topping
        CheckBox chocolateCheckBox = (CheckBox) findViewById(R.id.chocolate_checkbox);
        boolean hasChocolate = chocolateCheckBox.isChecked();

        // Calculate the price
        int price = calculatePrice(hasWhippedCream, hasChocolate);
        displayMessage((String.valueOf(price)));
    }

    /**
     * This method is called when the order button is clicked.
     */
    public void submitOrder(View view) {
        // Figure out if the user wants whipped cream topping
        CheckBox whippedCreamCheckBox = (CheckBox) findViewById(R.id.whipped_cream_checkbox);
        boolean hasWhippedCream = whippedCreamCheckBox.isChecked();

        // Figure out if the user wants chocolate topping
        CheckBox chocolateCheckBox = (CheckBox) findViewById(R.id.chocolate_checkbox);
        boolean hasChocolate = chocolateCheckBox.isChecked();

        // Calculate the price
        int price = calculatePrice(hasWhippedCream, hasChocolate);

        EditText name=(EditText)findViewById(R.id.name_input);
        String customerName=name.getText().toString();

        // Display the order summary on the screen
        String message = createOrderSummary(price, hasWhippedCream, hasChocolate, customerName);

        Intent intent=new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:"));
        intent.putExtra(Intent.EXTRA_SUBJECT,"Just Java order for "+customerName);
        intent.putExtra(Intent.EXTRA_TEXT,message);
        if(intent.resolveActivity(getPackageManager())!=null)
        {
            startActivity(intent);
        }
        else
        {
            displayMessage(message);
        }
//        Intent intent=new Intent(Intent.ACTION_VIEW);
//        intent.setData(Uri.parse("geo:30.216522,111.909104"));
//        if(intent.resolveActivity(getPackageManager())!=null)
//        {
//            startActivity(intent);
//        }
//        else
//        {
//            displayMessage(message);
//        }
    }

    /**
     * Calculates the price of the order.
     *
     * @return total price
     */
    private int calculatePrice(boolean cream, boolean chocolate) {
        if (cream==true&&chocolate==false)
        {
            return quantity*5+1*quantity;
        }
        else if(chocolate==true && cream==false)
        {
            return quantity*5+2*quantity;
        }
        else if (chocolate==true&& cream==true)
        {
            return quantity*5+quantity*1+quantity*2;
        }
        else{return quantity * 5;}
    }

    /**
     * Create summary of the order.
     *
     * @param price           of the order
     * @param addWhippedCream is whether or not to add whipped cream to the coffee
     * @param addChocolate    is whether or not to add chocolate to the coffee
     * @return text summary
     */
    private String createOrderSummary(int price, boolean addWhippedCream, boolean addChocolate, String name) {

        String priceMessage = "Name: "+name;
        priceMessage += "\nAdd whipped cream? " + addWhippedCream;
        priceMessage += "\nAdd chocolate? " + addChocolate;
        priceMessage += "\nQuantity: " + quantity;
        priceMessage += "\nTotal: $" + price;
        priceMessage += "\nThank you!";
        return priceMessage;
    }

    /**
     * This method displays the given quantity value on the screen.
     */
    private void displayQuantity(int numberOfCoffees) {
        TextView quantityTextView = (TextView) findViewById(
                R.id.quantity_text_view);
        quantityTextView.setText("" + numberOfCoffees);
    }

    /**
     * This method displays the given text on the screen.
     */
    private void displayMessage(String message) {
        TextView orderSummaryTextView = (TextView) findViewById(R.id.order_summary_text_view);
        orderSummaryTextView.setText(message);
    }

}