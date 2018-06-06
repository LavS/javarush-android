/**
 * IMPORTANT: Make sure you are using the correct package name. 
 * This example uses the package name:
 * package com.example.android.justjava
 * If you get an error when copying this code into Android studio, update it to match teh package name found
 * in the project's AndroidManifest.xml file.
 **/

package ru.javarush.justjava;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import java.text.NumberFormat;

/**
 * Это приложение показывает форму заказа кофе.
 */
public class MainActivity extends AppCompatActivity {

    int quantity = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("quantity", quantity);
        TextView orderSummaryTextView = (TextView) findViewById(R.id.order_text_view);
        outState.putString("order_text", orderSummaryTextView.getText().toString());
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        quantity = savedInstanceState.getInt("quantity", 0);
        String orderMessage = savedInstanceState.getString("order_text", "0");

        displayQuantity(quantity);
        displayMessage(orderMessage);
    }

    /**
     * Этот метод вызывается при нажатии кнопки.
     */
    public void increment(View view) {
        quantity = quantity + 1;
        displayQuantity(quantity);
    }

    public void decrement(View view) {
        quantity = quantity - 1;
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
        String messagePrice = "Всего: " + NumberFormat.getCurrencyInstance().format(quantity * 5);
        displayMessage(messagePrice + "\nСпасибо!");
    }

    /**
     * Этот метод отображает переданное сообщение на экране.
     */
    private void displayMessage(String message) {
        TextView orderSummaryTextView = (TextView) findViewById(R.id.order_text_view);
        orderSummaryTextView.setText(message);
    }
}