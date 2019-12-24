package com.dexy.car

class Car {
    private var isEngineWorking: Boolean
    private var isParkingBrakeWorking: Boolean
    private var gear: Int
    private var speed: Int
    private var way: Way

    private var callback: OnTextCallback? = null

    private var arrayStatesGear = arrayOf(-1, 0, 1, 2, 3, 4, 5)

    init {
        isEngineWorking = false
        isParkingBrakeWorking = false
        gear = 0
        speed = 0
        way = Way.PLACE
    }

    fun setCallback(callback: OnTextCallback) {
        this.callback = callback
    }

    fun TurnOnEngine() : Boolean {
        return if (isEngineWorking) {
            false
        } else {
            isEngineWorking = true
            gear = 0
            way = Way.PLACE
            true
        }
    }

    fun TurnOffEngine() : Boolean {
        return if (isEngineWorking
            && gear == 0
            && way == Way.PLACE
            && speed == 0) {
            isEngineWorking = false
            way = Way.PLACE
            true
        } else {
            false
        }
    }

    fun SetGear(newGear: Int) : Boolean {
        return if (newGear in arrayStatesGear
            && isEngineWorking
            && isCorrectSpeedForChangeGear(newGear)
            && isCorrectGearForChange(newGear)) {
            gear = newGear
            way = getWayByGear()
            true
        } else if (!isEngineWorking && newGear == 0) {
            way = Way.PLACE
            true
        } else {
            callback?.onText("Check engine, gear or speed")
            false
        }
    }

    fun SetSpeed(newSpeed: Int) : Boolean {
        return if (isCorrectSpeedForChange(newSpeed)) {
            way = getWayByGear()
            speed = newSpeed
            true
        } else {
            callback?.onText("Check gear")
            false
        }
    }

    fun TurnOnParkingBrake() {
        if (speed == 0) {
            isParkingBrakeWorking = true
        } else {
            callback?.onText("Check brake")
        }
    }

    fun TurnOffParkingBrake() {
        isParkingBrakeWorking = false
    }

    private fun getSpeedByGear(gear: Int) : Pair<Int, Int> {
        return when(gear) {
            -1 -> Pair(0, 20)
            0 -> Pair(Int.MIN_VALUE, Int.MAX_VALUE)
            1 -> Pair(0, 30)
            2 -> Pair(20, 50)
            3 -> Pair(30, 60)
            4 -> Pair(40, 90)
            5 -> Pair(50, 150)
            else -> throw IllegalAccessException()
        }
    }

    private  fun isCorrectSpeedForChangeGear(gear: Int) : Boolean {
        val pair = getSpeedByGear(gear)

        return speed >= pair.first && speed <= pair.second
    }

    private  fun isCorrectSpeedForChange(newSpeed: Int) : Boolean {
        val pair = getSpeedByGear(gear)

        return newSpeed >= pair.first && newSpeed <= pair.second
    }

    private  fun isCorrectGearForChange(newGear: Int) : Boolean {
        return (newGear == -1 && gear == 0) || (newGear >= 0 && gear >= 0)
    }

    private fun getWayByGear() : Way {
        return when {
            gear <= -1 -> Way.DOWN
            gear == 0 -> Way.PLACE
            else -> Way.UP
        }
    }

    override fun toString(): String {
        return "Engine is work: $isEngineWorking; \nParking brake: $isParkingBrakeWorking; \nGear: $gear; \nSpeed: $speed; \nWay: ${way.way}"
    }

    enum class Way(val way: String){
        UP("UP"),
        DOWN("DOWN"),
        PLACE("PLACE")
    }
}

interface OnTextCallback{
    fun onText(value: String)
}