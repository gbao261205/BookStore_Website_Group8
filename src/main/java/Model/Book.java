package Model;

public class Book {
    private int id;
    private String title;
    private String author;
    private double price;
    private String coverUrl;

    public Book() {}
    public Book(int id, String title, String author, double price, String coverUrl) {
        this.id=id; this.title=title; this.author=author; this.price=price; this.coverUrl=coverUrl;
    }
    public int getId(){return id;}            public void setId(int id){this.id=id;}
    public String getTitle(){return title;}   public void setTitle(String t){this.title=t;}
    public String getAuthor(){return author;} public void setAuthor(String a){this.author=a;}
    public double getPrice(){return price;}   public void setPrice(double p){this.price=p;}
    public String getCoverUrl(){return coverUrl;} public void setCoverUrl(String u){this.coverUrl=u;}
}   