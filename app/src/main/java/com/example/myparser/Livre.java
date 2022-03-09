package com.example.myparser;

public class Livre {
    private int _LivreID;
    private String _nom;
    private double _price;
    private String _imgUrl;

    public Livre(int id, String nom, double price, String imgUrl)
    {
        _LivreID = id;
        _nom = nom;
        _price = price;
        _imgUrl = imgUrl;
    }
    public String print()
    {
        return _LivreID + " :" + _nom + "\n" + "Price: " + _price + "\n";
    }

    public String get_imgUrl()
    {
        return _imgUrl;
    }

    public String get_nom() {
        return _nom;
    }

    public double get_price() {
        return _price;
    }

    public int get_LivreID() {
        return _LivreID;
    }
}
