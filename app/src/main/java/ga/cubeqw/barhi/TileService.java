package ga.cubeqw.barhi;

import android.content.Context;
import android.content.Intent;
import android.service.quicksettings.Tile;

public class TileService extends android.service.quicksettings.TileService {
    @Override
    public void onTileAdded() {
    }

    @Override
    public void onStartListening() {

        Tile tile = getQsTile();
    }

    @Override
    public void onClick() {
        Context context=this;
        if (!isSecure()) {
            Intent intent=new Intent(context, Scanner.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        } else {

            unlockAndRun(new Runnable() {
                @Override
                public void run() {
                    Intent intent=new Intent(context, Scanner.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                }
            });
        }
    }


}
