package com.qfix;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.View;

import androidx.annotation.NonNull;

import com.google.android.material.bottomsheet.BottomSheetDialog;

public class HelpDialog extends BottomSheetDialog {
    public HelpDialog(@NonNull Context context) {
        super(context);
        setContentView(R.layout.help_dialog);
        show();
        View decor = getWindow().getDecorView();
        decor.findViewById(R.id.whatsapp).setOnClickListener(v -> {
            String phoneNumberWithCountryCode = "+256705 954303";
            String message = "*This message was sent from Quick Fix*";
            context.startActivity(
                    new Intent(Intent.ACTION_VIEW,
                            Uri.parse(
                                    String.format("https://api.whatsapp.com/send?phone=%s&text=%s",
                                            phoneNumberWithCountryCode,
                                            message)
                            )
                    )
            );
        });
        decor.findViewById(R.id.call).setOnClickListener(v -> {
            Intent dial = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:+256705 954303"));
            context.startActivity(dial);
        });
    }
}
