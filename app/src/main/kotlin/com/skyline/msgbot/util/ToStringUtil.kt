/*
 * MIT License
 *
 * Copyright (c) 2022 SkyLineLab
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package com.skyline.msgbot.util

import java.lang.reflect.Modifier

object ToStringUtil {

    /**
     * Returns a string reporting the value of each declared field, via reflection.
     * Static and transient fields are automatically skipped. Produces output like
     * "SimpleClassName[integer=1234,string="hello",character='c',intArray=[1,2,3]]".
     */
    fun toString(o: Any): String {
        val c: Class<*> = o.javaClass
        val sb = StringBuilder()
        sb.append(c.simpleName).append('[')
        var i = 0
        for (f in c.declaredFields) {
            if (f.modifiers and (Modifier.STATIC or Modifier.TRANSIENT) !== 0) {
                continue
            }
            f.isAccessible = true
            try {
                val value = f[o]
                if (i++ > 0) {
                    sb.append(',')
                }
                sb.append(f.name)
                sb.append('=')
                if (value.javaClass.isArray) {
                    if (value.javaClass == BooleanArray::class.java) {
                        sb.append((value as BooleanArray).contentToString())
                    } else if (value.javaClass == ByteArray::class.java) {
                        sb.append((value as ByteArray).contentToString())
                    } else if (value.javaClass == CharArray::class.java) {
                        sb.append((value as CharArray).contentToString())
                    } else if (value.javaClass == DoubleArray::class.java) {
                        sb.append((value as DoubleArray).contentToString())
                    } else if (value.javaClass == FloatArray::class.java) {
                        sb.append((value as FloatArray).contentToString())
                    } else if (value.javaClass == IntArray::class.java) {
                        sb.append((value as IntArray).contentToString())
                    } else if (value.javaClass == LongArray::class.java) {
                        sb.append((value as LongArray).contentToString())
                    } else if (value.javaClass == ShortArray::class.java) {
                        sb.append((value as ShortArray).contentToString())
                    } else {
                        sb.append((value as Array<*>).contentToString())
                    }
                } else if (value.javaClass == Char::class.java) {
                    sb.append('\'').append(value).append('\'')
                } else if (value.javaClass == String::class.java) {
                    sb.append('"').append(value).append('"')
                } else {
                    sb.append(value)
                }
            } catch (unexpected: IllegalAccessException) {
                throw AssertionError(unexpected)
            }
        }
        sb.append("]")
        return sb.toString()
    }

}