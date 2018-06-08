package ru.javarush.justjava;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.NumberFormat;

/**
 * Это приложение показывает форму заказа кофе.
 */
public class MainActivity extends AppCompatActivity {

    int quantity = 2;
    boolean isCream;
    boolean isChocolate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("quantity", quantity);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        quantity = savedInstanceState.getInt("quantity", 0);
        displayQuantity(quantity);
    }

    /**
     * Этот метод вызывается при нажатии кнопки.
     */
    public void increment(View view) {
        if (quantity >= 100) {
            quantity = 100;
            Toast.makeText(this,"Нельзя заказать больше 100 чашек", Toast.LENGTH_SHORT).show();
        } else {
            quantity = quantity + 1;
        }
        displayQuantity(quantity);
    }

    public void decrement(View view) {
        if (quantity <= 1) {
            quantity = 1;
            Toast.makeText(this,"Нельзя заказать меньше 1 чашки", Toast.LENGTH_SHORT).show();
        } else {
            quantity = quantity - 1;
        }
        displayQuantity(quantity);
    }

    /**
     * Этот метод отображает выбранное количество на экран.
     */
    private void displayQuantity(int numberOfCoffees) {
        TextView quantityTextView = (TextView) findViewById(R.id.quantity_text_view);
        quantityTextView.setText("" + numberOfCoffees);
    }

    /**
     * Этот метод вызывается при нажатии кнопки.
     */
    @SuppressLint("StringFormatInvalid")
    public void submitOrder(View view) {
        CheckBox creamCheckBox = (CheckBox) findViewById(R.id.cream_check_box);
        isCream = creamCheckBox.isChecked();
        CheckBox creamChocolate = (CheckBox) findViewById(R.id.chocolate_check_box);
        isChocolate = creamChocolate.isChecked();
        EditText nameEditText = (EditText) findViewById(R.id.name_edit_text);
        String name = String.format("%s", nameEditText.getText().toString());

        int price = calculatePrice();
        String priceMessage = createOrderSummary(price);

        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:"));
        intent.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.order_summary_email_subject, name));
        intent.putExtra(Intent.EXTRA_TEXT, priceMessage);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }

    private int calculatePrice() {
        int priceOfCoffee = 5;
        if (isCream) {
            priceOfCoffee += 1;
        }
        if (isChocolate) {
            priceOfCoffee += 2;
        }
        return quantity * priceOfCoffee;
    }

    @SuppressLint("StringFormatInvalid")
    private String createOrderSummary(int price) {
        EditText nameEditText = (EditText) findViewById(R.id.name_edit_text);
        String name = String.format("%s", nameEditText.getText().toString());
        String text = getString(R.string.order_summary_name, name) + "\n";
        if (isCream) {text += getString(R.string.order_summary_whipped_cream) + "\n";}
        if (isChocolate) {text += getString(R.string.order_summary_chocolate) + "\n";}
        text += getString(R.string.order_summary_quantity, quantity) + "\n";
        text += getString(R.string.order_summary_price, String.format("%s", NumberFormat.getCurrencyInstance().format(price))) + "\n";
        text += getString(R.string.thank_you);
        return text;
    }
}