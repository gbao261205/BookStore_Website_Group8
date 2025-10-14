package model;

import java.util.Date;

public class Book {
    private long id;
    private String isbn;
    private String title;
    private String description;
    private double sellingPrice;
    private int quantity;
    private Date publicationDate;
    private int edition;
    private double height;
    private double width;
    private double weight;

    private int formatId;
    private int recommendAgeId;
    private int languageId;
    private long authorId;
    private long publisherId;
    private long metadataId;

    // ===== Các trường hiển thị (từ JOIN) =====
    private String format;
    private String language;
    private String author;
    private String publisher;
    private double importPrice;

    // ===== GETTER / SETTER =====
    public long getId() {
        return id;
    }
    public void setId(long id) {
        this.id = id;
    }

    public String getIsbn() {
        return isbn;
    }
    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }

    public double getSellingPrice() {
        return sellingPrice;
    }
    public void setSellingPrice(double sellingPrice) {
        this.sellingPrice = sellingPrice;
    }

    public int getQuantity() {
        return quantity;
    }
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Date getPublicationDate() {
        return publicationDate;
    }
    public void setPublicationDate(Date publicationDate) {
        this.publicationDate = publicationDate;
    }

    public int getEdition() {
        return edition;
    }
    public void setEdition(int edition) {
        this.edition = edition;
    }

    public double getHeight() {
        return height;
    }
    public void setHeight(double height) {
        this.height = height;
    }

    public double getWidth() {
        return width;
    }
    public void setWidth(double width) {
        this.width = width;
    }

    public double getWeight() {
        return weight;
    }
    public void setWeight(double weight) {
        this.weight = weight;
    }

    public int getFormatId() {
        return formatId;
    }
    public void setFormatId(int formatId) {
        this.formatId = formatId;
    }

    public int getRecommendAgeId() {
        return recommendAgeId;
    }
    public void setRecommendAgeId(int recommendAgeId) {
        this.recommendAgeId = recommendAgeId;
    }

    public int getLanguageId() {
        return languageId;
    }
    public void setLanguageId(int languageId) {
        this.languageId = languageId;
    }

    public long getAuthorId() {
        return authorId;
    }
    public void setAuthorId(long authorId) {
        this.authorId = authorId;
    }

    public long getPublisherId() {
        return publisherId;
    }
    public void setPublisherId(long publisherId) {
        this.publisherId = publisherId;
    }

    public long getMetadataId() {
        return metadataId;
    }
    public void setMetadataId(long metadataId) {
        this.metadataId = metadataId;
    }

    public String getFormat() {
        return format;
    }
    public void setFormat(String format) {
        this.format = format;
    }

    public String getLanguage() {
        return language;
    }
    public void setLanguage(String language) {
        this.language = language;
    }

    public String getAuthor() {
        return author;
    }
    public void setAuthor(String author) {
        this.author = author;
    }

    public String getPublisher() {
        return publisher;
    }
    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public double getImportPrice() {
        return importPrice;
    }
    public void setImportPrice(double importPrice) {
        this.importPrice = importPrice;
    }
}
