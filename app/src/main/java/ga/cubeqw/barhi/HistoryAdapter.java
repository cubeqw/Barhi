package ga.cubeqw.barhi;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

import java.util.List;

public class HistoryAdapter extends RecyclerView.Adapter<RecyclerViewHolders>{
    private List<Barcode> itemList;
    public HistoryAdapter(List<Barcode> itemList) {
        this.itemList = itemList;

    }

    @Override
    public RecyclerViewHolders onCreateViewHolder(ViewGroup parent, int viewType) {
        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.history_item, null);
        RecyclerViewHolders  rcv = new RecyclerViewHolders(layoutView);
        return rcv;    }

    @Override
    public void onBindViewHolder(RecyclerViewHolders holder, int position) {
        String colors []={"#FF1744", "#651FFF", "#2979FF", "#00B0FF", "#1DE9B6", "#76FF03", "#FFEA00", "#FF3D00", "#5D4037", "#212121"};
        EAN13 code = new EAN13(itemList.get(position).getValue());
        code.setData(itemList.get(position).getValue());
        Bitmap bitmap = code.getBitmap(680, 300);
            holder.code.setImageBitmap(bitmap);
            holder.value.setText(itemList.get(position).getValue());
            holder.date.setText(itemList.get(position).getDate());
            holder.count.setText(String.valueOf(position+1));
            int color = position%10;
            holder.card.setCardBackgroundColor(Color.parseColor(colors[color]));
        }

    @Override
    public int getItemCount() {
        return this.itemList.size();
    }
}
