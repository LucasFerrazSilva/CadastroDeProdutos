package br.com.ferraz.produtos.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import br.com.ferraz.produtos.R;
import br.com.ferraz.produtos.dao.ProductDAO;
import br.com.ferraz.produtos.helper.ProductFormHelper;
import br.com.ferraz.produtos.model.Product;

public class ProductFormActivity extends AppCompatActivity {

    private ProductFormHelper helper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_form);

        helper = new ProductFormHelper(this);

        Intent intent = getIntent();

        Product product = (Product) intent.getSerializableExtra("product");

        if(product != null){
            helper.fillWith(product);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_product_form, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.menu_formulario_ok:
                Product product = helper.getProduct();

                ProductDAO dao = new ProductDAO(this);

                if (product.getId() != null){
                    dao.update(product);
                }
                else {
                    dao.insert(product);
                }

                dao.close();

                Toast.makeText(ProductFormActivity.this, "Produto " + product.getName() + " salvo!", Toast.LENGTH_SHORT).show();
                finish();

                break;
        }

        return super.onOptionsItemSelected(item);
    }
}
