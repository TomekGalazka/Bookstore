package org.bookstore.service;

import java.util.List;
import java.util.Map;
import java.util.Set;

public interface StoreService<T, K, V, C, M> {

    Set<T> addItem(T item, Set<T> itemSet) throws Exception;
    Set<T> removeItem(T itemType, Set<T> itemSet) throws Exception;
    Set<T> editItem(T oldItem, T newItem, Set<T> itemSet) throws Exception;
    Set<M> addItemCategory(M itemCategory, Set<M> itemCategorySet) throws Exception;
    Map<K, List<V>> reserveItem(T item,
                                Set<T> itemSet,
                                C client,
                                Set<C> clientSet,
                                Map<K, List<V>> reservedItems) throws Exception;
    Map<K, List<V>> sellItem(T item,
                             Set<T> itemSet,
                             C client,
                             Set<C> clientSet,
                             Map<K, List<V>> soldItems) throws Exception;
    void generateReport(Set<T> itemSet,
                        Set<C> clientSet,
                        Map<K, List<V>> reservedItems,
                        Map<K, List<V>> soldItems);
    Set<C> addClient(C client, Set<C> clientSet) throws Exception;
}
