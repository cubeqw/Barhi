package ga.cubeqw.barhi;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Typeface;
import android.mtp.MtpStorageInfo;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

import java.util.List;

import static android.content.Context.MODE_PRIVATE;

public class HistoryAdapter extends RecyclerView.Adapter<RecyclerViewHolders>{
    private List<Barcode> itemList;
    Context context;
    public HistoryAdapter(List<Barcode> itemList, Context context) {
        this.itemList = itemList;
        this.context = context;
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
            holder.card.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    final BottomSheetDialog mBottomSheetDialog = new BottomSheetDialog(v.getContext(), R.style.SheetDialog);
                    LayoutInflater inflater= LayoutInflater.from(v.getContext());
                    View sheetView = inflater.inflate(R.layout.delete_dialog, null);;
                    mBottomSheetDialog.setContentView(sheetView);
                    mBottomSheetDialog.show();
                    LinearLayout yes=sheetView.findViewById(R.id.yes);
                    LinearLayout no=sheetView.findViewById(R.id.no);
                    no.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            mBottomSheetDialog.cancel();
                        }
                    });
                    yes.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            itemList.remove(position);
                            edit(itemList);
                            notifyDataSetChanged();
                            mBottomSheetDialog.cancel();
                        }
                    });
                    return false;
                }
            });
        }
    void edit(List s) {
        Gson gson;
        GsonBuilder builder = new GsonBuilder();
        gson = builder.create();
        String json= gson.toJson(s);
        SharedPreferences sPref = context.getSharedPreferences("sPref", MODE_PRIVATE);
        SharedPreferences.Editor ed = sPref.edit();
        ed.putString("history", json);
        ed.apply();
    }
    @Override
    public int getItemCount() {
        return this.itemList.size();
    }
}
