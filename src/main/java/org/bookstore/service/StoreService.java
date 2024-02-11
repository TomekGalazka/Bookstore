package org.bookstore.service;

import java.util.List;
import java.util.Map;
import java.util.Set;

public interface StoreService<T, K, V, C> {

    void addItem(T item, Set<T> itemSet );
    void removeItem(T itemType, Set<T> itemSet);
    void editItem(T oldItem, T newItem, Set<T> itemSet);

    void addItemCategory(T itemCategory, Set<T> itemCategorySet);

    void reserveItem (T item, Set<T> itemSet, C client, Map<K, List<V>> reservedItems);

    void sellItem (T item, Set<T> itemSet, C client, Map<K, List<V>> soldItems);

    void generateReport(Set<T> itemSet, Map<K, V> soldItems);

    void addClient(C client, Set<C> clientSet);

}
