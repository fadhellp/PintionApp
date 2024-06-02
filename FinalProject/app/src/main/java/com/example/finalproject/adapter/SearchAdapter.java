package com.example.finalproject.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.finalproject.R;
import com.example.finalproject.models.Product;

import java.util.ArrayList;
import java.util.List;

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.ProductViewHolder> implements Filterable {

    private Context context;
    private List<Product> productList; // Menampung list produk yang belum difilter
    private List<Product> productListFiltered; // Menampung list produk yang telah difilter

    // Konstruktor untuk inisialisasi adapter dengan context dan list produk
    public SearchAdapter(Context context, List<Product> productList) {
        this.context = context;
        this.productList = productList;
        this.productListFiltered = new ArrayList<>(productList);
    }

    // Metode untuk membuat ViewHolder baru
    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflate layout item_product
        View view = LayoutInflater.from(context).inflate(R.layout.item_product, parent, false);
        return new ProductViewHolder(view);
    }

    // Metode untuk mengikat data ke ViewHolder
    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {
        Product product = productListFiltered.get(position);
        holder.bind(product);
    }

    // Metode untuk mendapatkan jumlah item dalam RecyclerView
    @Override
    public int getItemCount() {
        return productListFiltered.size();
    }

    // Metode untuk mendapatkan objek Filter
    @Override
    public Filter getFilter() {
        return new Filter() {
            // Metode untuk melakukan proses filter pada list produk
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                String charString = constraint.toString().toLowerCase().trim();
                if (charString.isEmpty()) {
                    // Jika inputan pencarian kosong, kembalikan list produk utuh
                    productListFiltered = new ArrayList<>(productList);
                } else {
                    // Jika tidak kosong, filter list produk berdasarkan inputan pencarian
                    List<Product> filteredList = new ArrayList<>();
                    for (Product product : productList) {
                        if (product.getName().toLowerCase().contains(charString)) {
                            filteredList.add(product);
                        }
                    }
                    productListFiltered = filteredList;
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = productListFiltered;
                return filterResults;
            }

            // Metode untuk mengaplikasikan hasil filter ke RecyclerView
            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                productListFiltered = (ArrayList<Product>) results.values;
                notifyDataSetChanged();
            }
        };
    }

    // Kelas ViewHolder yang menyimpan referensi ke elemen tampilan di setiap item RecyclerView
    public class ProductViewHolder extends RecyclerView.ViewHolder {
        private TextView productName;

        public ProductViewHolder(@NonNull View itemView) {
            super(itemView);
            productName = itemView.findViewById(R.id.product_name);
        }

        // Metode untuk mengatur data produk ke tampilan ViewHolder
        public void bind(Product product) {
            productName.setText(product.getName());
        }
    }
}
