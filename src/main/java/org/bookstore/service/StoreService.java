package org.bookstore.service;

import java.util.Map;
import java.util.Set;

public interface StoreService<T, K, V> {

    void addItem(T item, Set<T> itemSet );
    void removeItem(T itemType, Set<T> itemSet);
    void editItem(T oldItem, T newItem, Set<T> itemSet);

    void addItemCategory(T itemCategory, Set<T> itemCategorySet);

    void reserveItem (T item, Set<T> itemSet, Map<K, V> reservedItems);

    void sellItem (T item, Set<T> itemSet, Map<K, V> soldItems);

    void generateReport(Set<T> itemSet, Map<K, V> soldItems);

}
