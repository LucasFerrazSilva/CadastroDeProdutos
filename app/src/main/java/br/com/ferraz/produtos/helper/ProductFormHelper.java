package br.com.ferraz.produtos.helper;

import android.widget.EditText;

import br.com.ferraz.produtos.R;
import br.com.ferraz.produtos.activity.ProductFormActivity;
import br.com.ferraz.produtos.model.Product;

public class ProductFormHelper {

    private EditText nameField;
    private EditText descriptionField;
    private EditText categoryField;
    private final EditText urlField;
    private EditText valueField;
    private EditText addressField;
    private EditText numberField;

    private Product product;


    public ProductFormHelper(ProductFormActivity activity) {
        this.nameField = activity.findViewById(R.id.product_form_name);
        this.descriptionField = activity.findViewById(R.id.product_form_description);
        this.categoryField = activity.findViewById(R.id.product_form_category);
        this.urlField = activity.findViewById(R.id.product_form_url);
        this.valueField = activity.findViewById(R.id.product_form_value);
        this.addressField = activity.findViewById(R.id.product_form_address);
        this.numberField = activity.findViewById(R.id.product_form_number);

        this.product = new Product();
    }

    public void fillWith(Product product) {
        nameField.setText(product.getName());
        descriptionField.setText(product.getDescription());
        categoryField.setText(product.getCategory());
        urlField.setText(product.getUrl());
        addressField.setText(product.getAddress());
        numberField.setText(product.getNumber());
        valueField.setText(product.getValue().toString());

        this.product = product;
    }

    public Product getProduct() {
        product.setName(nameField.getText().toString());
        product.setDescription(descriptionField.getText().toString());
        product.setCategory(categoryField.getText().toString());
        product.setUrl(urlField.getText().toString());
        product.setAddress(addressField.getText().toString());
        product.setNumber(numberField.getText().toString());
        product.setValue(Double.parseDouble(valueField.getText().toString()));

        return product;
    }
}
