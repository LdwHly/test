package com.dx.log.logapplication.service;

import android.text.SpannableStringBuilder;

import java.util.ArrayList;

public class LogBean {
    public String tag;
    public int max;
    public final SpannableStringBuilder sb = new SpannableStringBuilder();
    public ArrayList<CharSequence> data = new ArrayList<>();
}
