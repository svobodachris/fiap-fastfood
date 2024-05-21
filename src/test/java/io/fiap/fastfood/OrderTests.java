package io.fiap.fastfood;

import org.junit.jupiter.api.Test;

import io.fiap.fastfood.domain.entity.Order;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class OrderTests {

    @Test
    public void givenEmptyOrder_whenAddingItem_thenOrderShouldContainItem() {
        // Given
        Order order = new Order(null, null, null, null, null, null);
        Item item = new Item("Burger", 10.99);

        // When
        order.addItem(item);

        // Then
        assertTrue(order.getItems().equals(item));
    }

    @Test
    public void givenOrderWithItems_whenCalculatingTotalPrice_thenTotalPriceShouldBeCorrect() {
        // Given
        Order order = new Order(null, null, null, null, null, null);
        Item item1 = new Item("Burger", 10.99);
        Item item2 = new Item("Fries", 5.99);
        order.addItem(item1);
        order.addItem(item2);

        // When
        double totalPrice = order.calculateTotalPrice();

        // Then
        assertEquals(16.98, totalPrice, 0.01);
    }

    @Test
    public void givenOrderWithItems_whenRemovingItem_thenOrderShouldNotContainItem() {
        // Given
        Order order = new Order(null, null, null, null, null, null);
        Item item1 = new Item("Burger", 10.99);
        Item item2 = new Item("Fries", 5.99);
        order.addItem(item1);
        order.addItem(item2);

        // When
        order.removeItem(item1);

        // Then
        assertFalse(order.getItems().equals(item1));
    }

    @Test
    public void givenOrderWithItems_whenClearingOrder_thenOrderShouldBeEmpty() {
        // Given
        Order order = new Order(null, null, null, null, null, null);
        Item item1 = new Item("Burger", 10.99);
        Item item2 = new Item("Fries", 5.99);
        order.addItem(item1);
        order.addItem(item2);

        // When
        order.clear();

        // Then
        assertTrue(order.getItems().any());
    }

    @Test
    public void givenOrderWithItems_whenPlacingOrder_thenOrderShouldBePlaced() {
        // Given
        Order order = new Order(null, null, null, null, null, null);
        Item item1 = new Item("Burger", 10.99);
        Item item2 = new Item("Fries", 5.99);
        order.addItem(item1);
        order.addItem(item2);

        // When
        order.placeOrder();

        // Then
        assertTrue(order.isPlaced());
    }
}