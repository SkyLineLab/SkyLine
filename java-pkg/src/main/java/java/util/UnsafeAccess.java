/*
 * Written by Stefan Zobel and released to the
 * public domain, as explained at
 * http://creativecommons.org/publicdomain/zero/1.0/
 */
package java.util;

import android.annotation.SuppressLint;

import com.skyline.unsafe.UnsafeUtil;

import java.lang.reflect.Field;

@SuppressLint("DiscouragedPrivateApi")
class UnsafeAccess {

    static final UnsafeUtil unsafe;

    static {
        try {
            Field field = null;
            try {
                field = Class.forName("sun.misc.Unsafe").getDeclaredField("theUnsafe");
            } catch (NoSuchFieldException oldAndroid) {
                field = Class.forName("sun.misc.Unsafe").getDeclaredField("THE_ONE");
            }
            field.setAccessible(true);
            unsafe = (UnsafeUtil) field.get(null);
        } catch (Exception e) {
            throw new Error(e);
        }
    }

    private UnsafeAccess() {
    }
}
