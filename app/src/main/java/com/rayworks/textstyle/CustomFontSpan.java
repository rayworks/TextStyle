package com.rayworks.textstyle;

import android.graphics.Typeface;
import android.text.TextPaint;
import android.text.style.MetricAffectingSpan;

public class CustomFontSpan extends MetricAffectingSpan {
    private Typeface typeface;

    public CustomFontSpan(Typeface typeface) {
        this.typeface = typeface;
    }

    @Override
    public void updateMeasureState(TextPaint p) {
        update(p);
    }

    @Override
    public void updateDrawState(TextPaint tp) {
        update(tp);
    }

    private void update(TextPaint textPaint) {
        Typeface old = textPaint.getTypeface();
        int oldStyle = old != null ? old.getStyle() : Typeface.NORMAL;

        // respect the old style
        Typeface font = Typeface.create(typeface, oldStyle);
        textPaint.setTypeface(font);
    }
}
