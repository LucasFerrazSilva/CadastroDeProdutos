package br.com.ferraz.produtos.model;

import android.content.ContentValues;
import android.net.Uri;

import java.io.Serializable;

public class Product implements Serializable{

    private Long id;
    private String name;
    private String description;
    private String category;
    private Double value;
    private String url;
    private String address;
    private String number;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
    }

    public String getUrl() {
        return url;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getUri(){
        String uri = url;

        if(!uri.startsWith("http://"))
            uri = "http://" + uri;

        return uri;
    }

    public Uri getNumberAsSmsUri() {
        String numberAsUri = (number.startsWith("sms:") ? number : "sms:" + number);

        return Uri.parse(numberAsUri);
    }

    public Uri getAddressAsUri() {
        return Uri.parse("geo:0,0?q=" + address);
    }

    public Uri getNumberAsCallUri() {
        return Uri.parse("tel:" + number);
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public String toString() {
        return name;
    }

    public ContentValues getAsContentValues() {
        ContentValues values = new ContentValues();

        values.put("name", name);
        values.put("description", description);
        values.put("category", category);
        values.put("value", value);
        values.put("url", url);
        values.put("address", address);
        values.put("number", number);

        return values;
    }
}