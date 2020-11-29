package com.sdl.knowlegdetrove;

public class Ebookproduct {
    String phoneno,ebookname,ebookurl;

    public Ebookproduct(String phoneno, String ebookname, String ebookurl) {
        this.phoneno = phoneno;
        this.ebookname = ebookname;
        this.ebookurl = ebookurl;
    }

    public Ebookproduct() {
    }

    public String getPhoneno() {
        return phoneno;
    }

    public void setPhoneno(String phoneno) {
        this.phoneno = phoneno;
    }

    public String getEbookname() {
        return ebookname;
    }

    public void setEbookname(String ebookname) {
        this.ebookname = ebookname;
    }

    public String getEbookurl() {
        return ebookurl;
    }

    public void setEbookurl(String ebookurl) {
        this.ebookurl = ebookurl;
    }
}
