package org.example

open class SmartDevice(
    val name: String,
    val category: String,
    val deviceType: String
) {
    var deviceStatus: String = "off"

    fun printDeviceInfo() {
        println("Device name: $name, category: $category, type: $deviceType")
    }
}

class SmartTvDevice(
    name: String,
    category: String
) : SmartDevice(name, category, "TV") {
    var volume: Int = 10
    var channel: Int = 1

    fun decreaseVolume() {
        if (volume > 0) volume--
    }

    fun previousChannel() {
        if (channel > 1) channel--
    }
}

class SmartLightDevice(
    name: String,
    category: String
) : SmartDevice(name, category, "Light") {
    var brightness: Int = 100

    fun decreaseBrightness() {
        if (brightness > 0) brightness -= 10
    }
}

class SmartHome(
    val tv: SmartTvDevice,
    val light: SmartLightDevice
) {
    var deviceTurnOnCount: Int = 0

    private fun canPerformAction(device: SmartDevice): Boolean {
        return if (device.deviceStatus == "on") true
        else {
            println("${device.name} chua bat")
            false
        }
    }

    fun decreaseTvVolume() {
        if (canPerformAction(tv)) tv.decreaseVolume()
    }

    fun changeTvChannelToPrevious() {
        if (canPerformAction(tv)) tv.previousChannel()
    }

    fun printSmartTvInfo() {
        if (canPerformAction(tv)) tv.printDeviceInfo()
    }

    fun printSmartLightInfo() {
        if (canPerformAction(light)) light.printDeviceInfo()
    }

    fun decreaseLightBrightness() {
        if (canPerformAction(light)) light.decreaseBrightness()
    }

    fun turnOnDevice(device: SmartDevice) {
        if (device.deviceStatus != "on") {
            device.deviceStatus = "on"
            deviceTurnOnCount++
        }
    }
}

fun main() {
    val tv = SmartTvDevice("Samsung", "Entertainment")
    val light = SmartLightDevice("Philips Hue", "Lighting")
    val home = SmartHome(tv, light)

    home.turnOnDevice(tv)
    home.turnOnDevice(light)

    home.printSmartTvInfo()
    home.printSmartLightInfo()

    home.decreaseTvVolume()
    home.changeTvChannelToPrevious()
    home.decreaseLightBrightness()

    println("Tv volume: ${tv.volume}, Tv channel: ${tv.channel}, Light brightness: ${light.brightness}")
    println("So lan thiet bi duoc bat: ${home.deviceTurnOnCount}")
}
