package com.rdev.tryp.network

import java.net.NetworkInterface
import java.util.Collections

/**
 * Created by Alexey Matrosov on 07.03.2019.
 */


object Utils {

    val ipAddress: String
        get() {
            try {
                val interfaces = Collections.list(NetworkInterface.getNetworkInterfaces())
                for (tempInterface in interfaces) {
                    val addresses = Collections.list(tempInterface.inetAddresses)
                    for (address in addresses) {
                        if (!address.isLoopbackAddress) {
                            val tempAddress = address.hostAddress
                            val isIPv4 = tempAddress.indexOf(':') < 0

                            if (isIPv4)
                                return tempAddress
                        }
                    }
                }
            } catch (ignored: Exception) { }
            return ""
        }

}