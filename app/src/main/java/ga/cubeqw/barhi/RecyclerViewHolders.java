package ga.cubeqw.barhi;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

public class RecyclerViewHolders extends RecyclerView.ViewHolder{
    TextView value, date, count;
    ImageView code;
    CardView card;
    public RecyclerViewHolders(View itemView) {
        super(itemView);
        value = itemView.findViewById(R.id.value);
        date = itemView.findViewById(R.id.date);
        code = itemView.findViewById(R.id.code);
        count = itemView.findViewById(R.id.counter);
        card = itemView.findViewById(R.id.card);
    }



}
