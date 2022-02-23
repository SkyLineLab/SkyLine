/**
 * Copyright (c) 2022 SkyLineLab
 *
 * PLEASE CHECK LICENSE THE LICENSE OF THE PROJECT REPOSITORY
 */

package com.skyline.msgbot.nodejs.modules.dns

import org.graalvm.polyglot.Value
import java.net.InetAddress
import java.net.UnknownHostException


object DNS {
    fun getHostByAddress(address: String, callback: Value) {
        Thread {
            try {
                try {
                    val hostAddresses: Array<InetAddress> = InetAddress.getAllByName(address)
                    val hostNames = arrayOfNulls<String>(hostAddresses.size)
                    for (i in hostAddresses.indices) {
                        hostNames[i] = hostAddresses[i].getHostName()
                    }
                    callback.executeVoid(*arrayOf(address, hostNames))
                } catch (e: UnknownHostException) {
                }
            } catch (th: Throwable) {
                throw th
            }
        }.start()
    }


    fun getAddressByHost(hostname: String, callback: Value) {
        Thread {
            try {
                try {
                    val hostAddresses = InetAddress.getAllByName(hostname)
                    val addresses = arrayOfNulls<String>(hostAddresses.size)
                    for (i in hostAddresses.indices) {
                        addresses[i] = hostAddresses[i].hostAddress
                    }
                    callback.executeVoid(*arrayOf(hostname, hostAddresses))
                } catch (e: UnknownHostException) {
                }
            } catch (th: Throwable) {
                throw th
            }
        }.start()
    }
}