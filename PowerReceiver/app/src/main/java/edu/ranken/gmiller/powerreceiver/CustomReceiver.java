package edu.ranken.gmiller.powerreceiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class CustomReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        String intentAction = intent.getAction();

        if (intentAction != null) {
            String toastMsg;
            switch (intentAction) {
                case Intent.ACTION_POWER_CONNECTED:
                    toastMsg = context.getString(R.string.power_connected);
                    break;
                case Intent.ACTION_POWER_DISCONNECTED:
                    toastMsg = context.getString(R.string.power_disconnected);
                    break;
                case Intent.ACTION_HEADSET_PLUG:
                    toastMsg =
                        intent.getIntExtra("state", -1) == 1 ?
                        context.getString(R.string.headset_plug_connected) :
                        context.getString(R.string.headset_plug_disconnected);
                    break;
                case MainActivity.ACTION_CUSTOM_BROADCAST:
                    toastMsg = context.getString(R.string.custom_broadcast_received);
                    break;
                default:
                    toastMsg = context.getString(R.string.unknown_intent_action);
                    break;
            }
            Toast.makeText(context, toastMsg, Toast.LENGTH_SHORT).show();
        }
    }
}
