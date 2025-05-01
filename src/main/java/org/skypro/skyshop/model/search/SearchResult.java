package org.skypro.skyshop.model.search;

public class SearchResult {

    private final String id;          // ID результата поиска
    private final String name;        // Имя (например, название продукта или статьи)
    private final String contentType; // Тип контента (например, "PRODUCT" или "ARTICLE")

    public SearchResult(String id, String name, String contentType) {
        this.id = id;
        this.name = name;
        this.contentType = contentType;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getContentType() {
        return contentType;
    }

    // Статический метод для создания объекта SearchResult из Searchable
    public static SearchResult fromSearchable(Searchable searchable) {
        return new SearchResult(
                searchable.getId().toString(),   // Преобразуем UUID в строку
                searchable.getSearchTerm(),     // Имя товара или статьи
                searchable.getContentType()      // Тип контента
        );
    }
}