package store.makejewelry.BE.service;

import store.makejewelry.BE.entity.ProductTemplate;

public interface CartService {
    ProductTemplate add(Integer id);

    ProductTemplate update(Integer id, int quantity);

    void remove(Integer id);
}
