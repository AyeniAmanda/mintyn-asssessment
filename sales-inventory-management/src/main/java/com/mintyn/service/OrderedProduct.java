package com.mintyn.service;

import java.util.List;

public interface OrderedProduct {
    List<OrderedProduct> createOrder(List<OrderedProduct> productsOrdered);
    OrderedProduct getOrder(Long id);

    List<OrderedProduct> getAllOrders();
}
