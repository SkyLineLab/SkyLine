package com.skyline.msgbot.core;

import android.os.FileObserver;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import org.graalvm.polyglot.Value;

import java.io.File;

public class Test extends FileObserver {
    private final Value value;

    public Test(Value value, Object... args) {
        super((String) args[0]);
        this.value = value;
    }

    public void onEvent(int event, @Nullable String path) {
        value.execute(event, path).as(Object.class);

        new Test(Value.asValue(null)).onEvent(12, "f");
    }

}