package com.sdl.knowlegdetrove;

public class bookproduct {
    private String imageUrl,bookname,bookdesc,bookprice,phoneno,parentKey;

    public bookproduct(String imageUrl, String bookname, String bookdesc, String bookprice, String phoneno) {
        this.imageUrl = imageUrl;
        this.bookname = bookname;
        this.bookdesc = bookdesc;
        this.bookprice = bookprice;
        this.phoneno = phoneno;
    }

    public String getParentKey() {
        return parentKey;
    }

    public void setParentKey(String parentKey) {
        this.parentKey = parentKey;
    }

    public bookproduct(String imageUrl, String bookname, String bookdesc, String bookprice, String phoneno, String parentKey) {
        this.imageUrl = imageUrl;
        this.bookname = bookname;
        this.bookdesc = bookdesc;
        this.bookprice = bookprice;
        this.phoneno = phoneno;
        this.parentKey = parentKey;
    }

    public bookproduct() {
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getBookname() {
        return bookname;
    }

    public void setBookname(String bookname) {
        this.bookname = bookname;
    }

    public String getBookdesc() {
        return bookdesc;
    }

    public void setBookdesc(String bookdesc) {
        this.bookdesc = bookdesc;
    }

    public String getBookprice() {
        return bookprice;
    }

    public void setBookprice(String bookprice) {
        this.bookprice = bookprice;
    }

    public String getPhoneno() {
        return phoneno;
    }

    public void setPhoneno(String phoneno) {
        this.phoneno = phoneno;
    }
    public boolean comparePhoneno(String phoneno_param){
        return phoneno_param.equals(phoneno);
    }
}
