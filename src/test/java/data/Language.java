package data;

public enum Language {
    English ("Products"),
    Türkçe ("Ürünler");
    public final String description;

    Language(String description) {
        this.description = description;
    }
}
