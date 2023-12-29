package com.example.ujianpraktikum1;import java.io.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class CartManager {
    private static final String CART_FILE_PATH = "cart_data.txt";

    public static void addToCart(String bikeType, int quantity) {
        List<String> cartItems = getCartItems();
        String item = bikeType + " - Quantity: " + quantity;
        cartItems.add(item);
        saveCartItems(cartItems);
    }

    public static List<String> getCartItems() {
        try (BufferedReader reader = new BufferedReader(new FileReader(CART_FILE_PATH))) {
            List<String> cartItems = new ArrayList<>();
            String line;
            while ((line = reader.readLine()) != null) {
                cartItems.add(line);
            }
            return cartItems;
        } catch (IOException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    private static void saveCartItems(List<String> cartItems) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(CART_FILE_PATH))) {
            for (String item : cartItems) {
                writer.write(item);
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Implement update and delete methods as needed
}
