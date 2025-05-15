package org.skypro.skyshop.service;

import org.skypro.skyshop.model.article.Article;
import org.skypro.skyshop.model.product.Product;
import org.skypro.skyshop.model.search.SearchResult;
import org.skypro.skyshop.model.search.Searchable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SearchService {

    private final StorageService storageService;

    @Autowired
    public SearchService(StorageService storageService) {
        this.storageService = storageService;
    }

    // Метод для получения всех объектов Searchable (продуктов и статей)
    private List<Searchable> getSearchableItems() {
        // Объединяем продукты и статьи из хранилища
        List<Product> products = (List<Product>) storageService.getAllProducts();
        List<Article> articles = (List<Article>) storageService.getAllArticles();

        return products.stream()
                .map(product -> (Searchable) product)  // Преобразуем Product в Searchable
                .collect(Collectors.toList());
    }

    public Collection<SearchResult> search(String pattern) {
        return storageService.getAllSearchable().stream()
                .filter(item -> item.getSearchTerm().toLowerCase().contains(pattern.toLowerCase()))
                .map(SearchResult::fromSearchable)
                .collect(Collectors.toList());
    }


}