package br.com.ferraz.produtos.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;

import br.com.ferraz.produtos.R;
import br.com.ferraz.produtos.dao.ProductDAO;
import br.com.ferraz.produtos.model.Product;

public class ProductListActivity extends AppCompatActivity {

    private ListView productList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_list);

        Button buttonAddProduct = findViewById(R.id.button_add_product);
        buttonAddProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ProductListActivity.this, ProductFormActivity.class);
                startActivity(intent);
            }
        });

        productList = findViewById(R.id.listview_product_list);
        productList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> list, View item, int i, long id) {
                Product product = (Product) list.getItemAtPosition(i);

                Intent intent = new Intent(ProductListActivity.this, ProductFormActivity.class);
                intent.putExtra("product", product);
                startActivity(intent);
            }
        });

        registerForContextMenu(productList);
    }

    @Override
    protected void onResume() {
        loadProducts();

        super.onResume();
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, final ContextMenu.ContextMenuInfo menuInfo) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) menuInfo;
        final Product product = (Product) productList.getItemAtPosition(info.position);

        MenuItem itemCall = menu.add("Ligar para fornecedor");
        itemCall.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                if(ActivityCompat.checkSelfPermission(ProductListActivity.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED){
                    ActivityCompat.requestPermissions(ProductListActivity.this, new String[]{Manifest.permission.CALL_PHONE}, 123);
                }
                else {
                    Intent intentCall = new Intent(Intent.ACTION_CALL);
                    intentCall.setData(product.getNumberAsCallUri());

                    startActivity(intentCall);
                }

                return false;
            }
        });

        MenuItem itemSms = menu.add("Enviar SMS para fornecedor");
        Intent intentSms = new Intent(Intent.ACTION_VIEW);
        intentSms.setData(product.getNumberAsSmsUri());
        itemSms.setIntent(intentSms);

        MenuItem itemViewLocation = menu.add("Visualizar localização do fornecedor");
        Intent intentViewLocation = new Intent(Intent.ACTION_VIEW);
        intentViewLocation.setData(product.getAddressAsUri());
        itemViewLocation.setIntent(intentViewLocation);

        MenuItem itemSeeAtSite = menu.add("Abrir site do fornecedor");
        //Intent intentSite = new Intent(ProductListActivity.this, Browser.class); //Intent explícita (o Browser já foi escolhido)
        Intent intentSite = new Intent(Intent.ACTION_VIEW); //Intent implícita (da opção para o usuário escolher o Browser)
        //o método putExtra indica que os dados são opcionais, enquanto o setData indica que os dados são fundamentais pra ação
        intentSite.setData(Uri.parse(product.getUri()));
        itemSeeAtSite.setIntent(intentSite);

        MenuItem itemDelete = menu.add("Remover produto");
        itemDelete.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                ProductDAO dao = new ProductDAO(ProductListActivity.this);
                dao.delete(product);
                dao.close();

                loadProducts();

                Toast.makeText(ProductListActivity.this, "Produto " + product.getName() + " removido!", Toast.LENGTH_SHORT).show();

                return false;
            }
        });
    }

    private void loadProducts(){
        ProductDAO dao = new ProductDAO(this);
        List<Product> products = dao.listProducts();
        dao.close();

        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, products);

        productList.setAdapter(adapter);
    }

}