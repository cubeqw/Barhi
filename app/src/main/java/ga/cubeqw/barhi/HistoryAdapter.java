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
        EAN13 code = new EAN13(itemList.get(position).getValue());
        code.setData(itemList.get(position).getValue());
        Bitmap bitmap = code.getBitmap(680, 300);
            holder.code.setImageBitmap(bitmap);
            holder.value.setText(itemList.get(position).getValue());
            holder.date.setText(itemList.get(position).getDate());
        }

    @Override
    public int getItemCount() {
        return this.itemList.size();
    }
}
