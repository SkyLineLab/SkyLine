package com.skyline.msgbot.reflow.util

import java.lang.reflect.Modifier

object ToStringUtil {

    /**
     * Returns a string reporting the value of each declared field, via reflection.
     * Static, transient, private, protected fields are automatically skipped. Produces output like
     * "SimpleClassName[integer=1234,string="hello",character='c',intArray=[1,2,3]]".
     */
    fun toStringFormat(obj: Any): String {
        val clazz = obj.javaClass
        val stringBuilder = StringBuilder()
        stringBuilder.append(clazz.simpleName).append("[")
        var i = 0
        for (field in clazz.declaredFields) {
            if (field.modifiers and (Modifier.STATIC or Modifier.FINAL or Modifier.PRIVATE or Modifier.PROTECTED) != 0) {
                continue
            }

            field.isAccessible = true
            try {
                val value = field[obj]
                if (i++ > 0) {
                    stringBuilder.append(", ")
                }
                stringBuilder.append(field.name).append("=")
                if (value.javaClass.isArray) {
                    when (value.javaClass) {
                        BooleanArray::class.java -> {
                            stringBuilder.append((value as BooleanArray).contentToString())
                        }

                        ByteArray::class.java -> {
                            stringBuilder.append((value as ByteArray).contentToString())
                        }

                        CharArray::class.java -> {
                            stringBuilder.append((value as CharArray).contentToString())
                        }

                        DoubleArray::class.java -> {
                            stringBuilder.append((value as DoubleArray).contentToString())
                        }

                        FloatArray::class.java -> {
                            stringBuilder.append((value as FloatArray).contentToString())
                        }

                        IntArray::class.java -> {
                            stringBuilder.append((value as IntArray).contentToString())
                        }

                        LongArray::class.java -> {
                            stringBuilder.append((value as LongArray).contentToString())
                        }

                        ShortArray::class.java -> {
                            stringBuilder.append((value as ShortArray).contentToString())
                        }

                        else -> {
                            stringBuilder.append((value as Array<*>).contentToString())
                        }
                    }
                } else if (value.javaClass == Char::class.java) {
                    stringBuilder.append('\'').append(value).append('\'')
                } else if (value.javaClass == String::class.java) {
                    stringBuilder.append('"').append(value).append('"')
                } else {
                    stringBuilder.append(value)
                }
            } catch (e: Exception) {
                throw RuntimeException(e)
            }
        }
        stringBuilder.append("]")

        return stringBuilder.toString()
    }

}