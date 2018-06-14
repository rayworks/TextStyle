package com.rayworks.textstyle;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.Annotation;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.SpannedString;
import android.text.style.ForegroundColorSpan;
import android.text.style.ImageSpan;
import android.widget.TextView;

import java.io.File;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView textView = findViewById(R.id.textView);
        textView.setText(buildSpannableString());
    }

    private Spannable buildSpannableString() {
        SpannedString emptyText = (SpannedString) getText(R.string.sample_text);
        SpannableStringBuilder ssb = new SpannableStringBuilder(emptyText);
        final Annotation[] annotations =
                emptyText.getSpans(0, emptyText.length(), Annotation.class);

        if (annotations != null) {
            for (Annotation annotation : annotations) {
                String key = annotation.getKey();

                int resId;
                if (key.equals("font")) {
                    String name = annotation.getValue();
                    Typeface typeface =
                            Typeface.createFromAsset(
                                    getAssets(), "font" + File.separator + name + ".ttf");
                    ssb.setSpan(
                            new CustomFontSpan(typeface),
                            emptyText.getSpanStart(annotation),
                            emptyText.getSpanEnd(annotation),
                            Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

                } else if (key.equals("src")) {
                    // image
                    String name = annotation.getValue();
                    resId = getResources().getIdentifier(name, null, getPackageName());
                    if (resId == 0) continue;
                    ssb.setSpan(
                            new ImageSpan(this, resId, ImageSpan.ALIGN_BOTTOM),
                            emptyText.getSpanStart(annotation),
                            emptyText.getSpanEnd(annotation),
                            Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

                } else if (key.equals("foregroundColor")) {
                    // foreground color
                    String name = annotation.getValue();
                    resId = getResources().getIdentifier(name, null, getPackageName());
                    if (resId == 0) continue;

                    ssb.setSpan(
                            new ForegroundColorSpan(ContextCompat.getColor(this, resId)),
                            emptyText.getSpanStart(annotation),
                            emptyText.getSpanEnd(annotation),
                            Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                }
            }
        }
        return ssb;
    }
}
