package model;

public class Book {
    private long id;
    private String title;
    private long subCategoryId;
    private String description;
    private double sellingPrice;
    private String format;
    private boolean active;
    private String slug;
    private String metaTitle;
    private String metaDescription;
    private String coverPath;
    private String ebookPath;
    private int quantity; // 🔹 thêm dòng này
    private String category;
    // ===== GETTER / SETTER =====
    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }
    public long getId() { return id; }
    public void setId(long id) { this.id = id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public long getSubCategoryId() { return subCategoryId; }
    public void setSubCategoryId(long subCategoryId) { this.subCategoryId = subCategoryId; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public double getSellingPrice() { return sellingPrice; }
    public void setSellingPrice(double sellingPrice) { this.sellingPrice = sellingPrice; }

    public String getFormat() { return format; }
    public void setFormat(String format) { this.format = format; }

    public boolean isActive() { return active; }
    public void setActive(boolean active) { this.active = active; }

    public String getSlug() { return slug; }
    public void setSlug(String slug) { this.slug = slug; }

    public String getMetaTitle() { return metaTitle; }
    public void setMetaTitle(String metaTitle) { this.metaTitle = metaTitle; }

    public String getMetaDescription() { return metaDescription; }
    public void setMetaDescription(String metaDescription) { this.metaDescription = metaDescription; }

    public String getCoverPath() { return coverPath; }
    public void setCoverPath(String coverPath) { this.coverPath = coverPath; }

    public String getEbookPath() { return ebookPath; }
    public void setEbookPath(String ebookPath) { this.ebookPath = ebookPath; }

    // 🔹 Getter / Setter mới cho quantity
    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) { this.quantity = quantity; }
}
