package br.com.ferraz.produtos.dao;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

import br.com.ferraz.produtos.model.Product;

public class ProductDAO extends SQLiteOpenHelper {


    public ProductDAO(Context context) {
        super(context, "Produtos", null, 4);
    }

    public List<Product> listProducts(){
        List<Product> products = new ArrayList<>();

        SQLiteDatabase db = getReadableDatabase();

        Cursor c = db.rawQuery("SELECT * FROM Produtos", null);

        while(c.moveToNext()){
            Product product = new Product();

            product.setId(c.getLong(c.getColumnIndex("id")));
            product.setName(c.getString(c.getColumnIndex("name")));
            product.setDescription(c.getString(c.getColumnIndex("description")));
            product.setCategory(c.getString(c.getColumnIndex("category")));
            product.setValue(c.getDouble(c.getColumnIndex("value")));
            product.setUrl(c.getString(c.getColumnIndex("url")));
            product.setAddress(c.getString(c.getColumnIndex("address")));
            product.setNumber(c.getString(c.getColumnIndex("number")));

            products.add(product);
        }

        c.close();

        return products;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE Produtos(id INTEGER PRIMARY KEY, name TEXT NOT NULL, description TEXT, category TEXT, value REAL, url TEXT, address TEXT, " +
                                            "number TEXT);";

        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        String sql = "DROP TABLE IF EXISTS Produtos";

        db.execSQL(sql);

        onCreate(db);
    }

    public void update(Product product) {
        SQLiteDatabase db = getWritableDatabase();

        String[] params = {product.getId().toString()};

        db.update("Produtos", product.getAsContentValues(), "id = ?", params);
    }

    public void insert(Product product) {
        SQLiteDatabase db = getWritableDatabase();

        db.insert("Produtos", null, product.getAsContentValues());
    }

    public void delete(Product product) {
        SQLiteDatabase db = getWritableDatabase();

        String[] params = {product.getId().toString()};

        db.delete("Produtos", "id = ?", params);
    }
}
