package ru.javarush.justjava;

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
    String name = "Anonymous";
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
    public void submitOrder(View view) {
        CheckBox creamCheckBox = (CheckBox) findViewById(R.id.cream_check_box);
        isCream = creamCheckBox.isChecked();
        CheckBox creamChocolate = (CheckBox) findViewById(R.id.chocolate_check_box);
        isChocolate = creamChocolate.isChecked();

        int price = calculatePrice();
        String priceMessage = createOrderSummary(price);

        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:"));
        intent.putExtra(Intent.EXTRA_SUBJECT, "Заказ Just Java");
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

    private String createOrderSummary(int price) {
        EditText nameEditText = (EditText) findViewById(R.id.name_edit_text);
        name = nameEditText.getText().toString();
        String text = "Имя: " + name + "\n";
        if (isCream) {text += "Добавить взбитые сливки.\n";}
        if (isChocolate) {text += "Добавить шоколад.\n";}
        text += "Количество: " + quantity + "\n";
        text += "Всего: " + NumberFormat.getCurrencyInstance().format(price) + "\n";
        text += "Спасибо!";
        return text;
    }
}