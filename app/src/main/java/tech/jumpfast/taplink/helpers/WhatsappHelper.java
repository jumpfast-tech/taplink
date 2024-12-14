package tech.jumpfast.taplink.helpers;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;

public class WhatsappHelper {
    public static void openWhatsAppChat(Context ctx, String phoneNumber, TextInputLayout numberLayout) {
        String url = "https://wa.me/" + phoneNumber;

        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(url));

        try {
            ctx.startActivity(intent);
        } catch (ActivityNotFoundException e) {
            numberLayout.setError("WhatsApp is not installed on your device!");
        }
    }
}
