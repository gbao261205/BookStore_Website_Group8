/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Model;


/**
 *
 * @author POW
 */

public class BookCard {

    private long id;
    private String title;
    private String authors;
    private String publisher;
    private String category;
    private String subcategory;
    private double price;
    private String coverUrl;

    // --- Phương thức GETTER và SETTER ---

    /**
     * Lấy giá trị của id
     * @return id
     */
    public long getId() {
        return id;
    }

    /**
     * Thiết lập giá trị cho id
     * @param id The id to set
     */
    public void setId(long id) {
        this.id = id;
    }

    /**
     * Lấy giá trị của title (Tiêu đề)
     * @return title
     */
    public String getTitle() {
        return title;
    }

    /**
     * Thiết lập giá trị cho title (Tiêu đề)
     * @param title The title to set
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Lấy giá trị của authors (Tác giả)
     * @return authors
     */
    public String getAuthors() {
        return authors;
    }

    /**
     * Thiết lập giá trị cho authors (Tác giả)
     * @param authors The authors to set
     */
    public void setAuthors(String authors) {
        this.authors = authors;
    }

    /**
     * Lấy giá trị của publisher (Nhà xuất bản)
     * @return publisher
     */
    public String getPublisher() {
        return publisher;
    }

    /**
     * Thiết lập giá trị cho publisher (Nhà xuất bản)
     * @param publisher The publisher to set
     */
    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    /**
     * Lấy giá trị của category (Danh mục)
     * @return category
     */
    public String getCategory() {
        return category;
    }

    /**
     * Thiết lập giá trị cho category (Danh mục)
     * @param category The category to set
     */
    public void setCategory(String category) {
        this.category = category;
    }

    /**
     * Lấy giá trị của subcategory (Danh mục phụ)
     * @return subcategory
     */
    public String getSubcategory() {
        return subcategory;
    }

    /**
     * Thiết lập giá trị cho subcategory (Danh mục phụ)
     * @param subcategory The subcategory to set
     */
    public void setSubcategory(String subcategory) {
        this.subcategory = subcategory;
    }

    /**
     * Lấy giá trị của price (Giá)
     * @return price
     */
    public double getPrice() {
        return price;
    }

    /**
     * Thiết lập giá trị cho price (Giá)
     * @param price The price to set
     */
    public void setPrice(double price) {
        this.price = price;
    }

    /**
     * Lấy giá trị của coverUrl (Đường dẫn ảnh bìa)
     * @return coverUrl
     */
    public String getCoverUrl() {
        return coverUrl;
    }

    /**
     * Thiết lập giá trị cho coverUrl (Đường dẫn ảnh bìa)
     * @param coverUrl The coverUrl to set
     */
    public void setCoverUrl(String coverUrl) {
        this.coverUrl = coverUrl;
    }
}

