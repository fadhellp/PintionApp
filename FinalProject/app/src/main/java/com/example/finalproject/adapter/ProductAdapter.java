package com.example.finalproject.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.finalproject.R;
import com.example.finalproject.models.Product;
import java.util.ArrayList;
import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ViewHolder> {
    private List<Product> productList; // Menampung list produk yang akan ditampilkan
    private Context context; // Context dari Activity atau Fragment
    private List<Product> productListFull; // Variabel untuk menyimpan salinan penuh dari list produk

    // Konstruktor untuk adapter
    public ProductAdapter(Context context, List<Product> productList) {
        this.context = context;
        this.productList = productList;
        this.productListFull = new ArrayList<>(productList); // Menginisialisasi list produk penuh
    }

    // Metode yang dipanggil ketika ViewHolder baru perlu dibuat
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflate layout item_product
        View view = LayoutInflater.from(context).inflate(R.layout.item_product, parent, false);
        return new ViewHolder(view);
    }

    // Metode yang mengikat data ke ViewHolder
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Product product = productList.get(position);
        holder.productName.setText(product.getName()); // Set nama produk ke TextView productName

        // Load gambar menggunakan Glide jika ada gambar produk
        if (product.getImages() != null && product.getImages().size() > 0) {
            String imageUrl = product.getImages().get(0).getBaseUrl();
            Glide.with(context)
                    .load(imageUrl)
                    .placeholder(R.drawable.ic_launcher_background) // Gambar placeholder jika gagal memuat
                    .error(R.drawable.ic_launcher_background) // Gambar error jika gagal memuat
                    .into(holder.productImage);
        }
        // Set data lainnya jika diperlukan
    }

    // Metode untuk mengembalikan jumlah item dalam RecyclerView
    @Override
    public int getItemCount() {
        return productList.size();
    }

    // Kelas ViewHolder yang menyimpan referensi ke elemen tampilan di setiap item RecyclerView
    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView productImage;
        TextView productName;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            productImage = itemView.findViewById(R.id.product_image);
            productName = itemView.findViewById(R.id.product_name);
        }
    }

    // Metode untuk menerapkan filtrasi pada list produk
    public void filterList(List<Product> filteredList) {
        productList = filteredList; // Ganti list produk dengan list yang difilter
        notifyDataSetChanged(); // Beri tahu adapter bahwa dataset telah berubah
    }
}
