/**
 * Add your package below. Package name can be found in the project's AndroidManifest.xml file.
 * This is the package name our example uses:
 * <p>
 * package com.example.android.justjava;
 */
package com.example.android.justjava;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.NumberFormat;

/**
 * This app displays an order form to order coffee.
 */
public class MainActivity extends AppCompatActivity {

    int quantity = 0;
    int basePrice = 5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public String getTextFrom() {
        EditText namee = (EditText) (findViewById(R.id.get_name));
        String nameEnt = namee.getText().toString();
        //  Log.v("entered","Text : " + nameEnt);
        return nameEnt;

    }

    /**
     * This method is called when the order button is clicked.
     */
    public void submitOrder(View view) {
        // int price = calculatePrice();
        CheckBox whippedCreamCheckBox = (CheckBox) (findViewById(R.id.whipped_cream_checkbox));
        boolean hasWhippedCream = whippedCreamCheckBox.isChecked();
        CheckBox choco = (CheckBox) (findViewById(R.id.chocolate_add));
        boolean chocoAdd = choco.isChecked();
        //Log.v("Main activity", "has whipped Cream" + hasWhippedCream);
        displayMessage(createOrderSummary(calculatePrice(chocoAdd, hasWhippedCream, basePrice), hasWhippedCream, chocoAdd));


        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:")); // only email apps should handle this
        // intent.putExtra(Intent.EXTRA_EMAIL, addresses);
        intent.putExtra(Intent.EXTRA_SUBJECT, "ORDER SUMMARY");
        intent.putExtra(Intent.EXTRA_TEXT, createOrderSummary(calculatePrice(chocoAdd, hasWhippedCream, basePrice), hasWhippedCream, chocoAdd));
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }

    }

    /*
    This method is used to display the text
     */
    private void displayMessage(String message) {
        TextView orderSummaryTextView = (TextView) findViewById(R.id.order_summary_text_view);
        orderSummaryTextView.setText(message);
    }

    /**
     * This button is called when the plus button is clicked
     *
     * @param view
     */
    public void increment(View view) {
        if (quantity < 100) {
            quantity = quantity + 1;
            displayQuantity(quantity);

        }
        if (quantity == 100) {
            Context context = getApplicationContext();
            CharSequence text = "Maximum order amount reached";
            int duration = Toast.LENGTH_SHORT;

            Toast toast = Toast.makeText(context, text, duration);
            toast.show();
        }
    }

    /**
     * This button is called when minus button is clicked
     *
     * @param view
     */
    public void decrement(View view) {
        if (quantity >= 1) {
            quantity = quantity - 1;
            displayQuantity(quantity);
            //    displayPrice(quantity * 3);
        }
        if (quantity == 0) {
            Context context = getApplicationContext();
            CharSequence text = "Order quantity cant be less than zero";
            int duration = Toast.LENGTH_SHORT;

            Toast toast = Toast.makeText(context, text, duration);
            toast.show();
            /*
            Alternatively use
            Toast.makeTextt(this,"MESSAGE", Toast.LENGTH_SHORT).show();
             */
        }
    }

    /**
     * This method displays the given price on the screen.
     */
    private void displayQuantity(int number) {
        TextView quantityTextView = (TextView) findViewById(R.id.quantity_text_view);
        quantityTextView.setText("" + number);
    }

    private int calculatePrice(boolean whip, boolean choco, int basePrice) {

        if (whip && choco) {
            return (basePrice + 1 + 2) * quantity;
        } else if (!whip && choco) {
            return (basePrice + 2) * quantity;
        } else if (whip && !choco) {
            return (basePrice + 1) * quantity;
        } else
            return quantity * basePrice;
    }

    /**
     * Create summary of the order.
     *
     * @param whip     is whether or not the user wants whipped cream topping
     * @param chocoAdd is whether or not the user wants whipped cream topping
     * @param price    of the order
     * @return text summary
     */
    private String createOrderSummary(int price, boolean whip, boolean chocoAdd) {
        //int price = calculatePrice();
        String orderSum = "Name: " + getTextFrom() + '\n' + "Quantity = " + quantity + "\nAsdd chocolate :" + chocoAdd +
                "\nAdd whipped cream: " + whip
                + "\nTotal: $" + price + "\n Thank yuu!";
        // String subject = "ORDER SUMMARY";

        return orderSum;

    }
}

