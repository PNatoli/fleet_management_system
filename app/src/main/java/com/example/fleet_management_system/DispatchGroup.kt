package com.example.fleet_management_system

class DispatchGroup {
    // waiting for DB calls to finish, similar to async await

    private var count = 0
    private var runnable: Runnable? = null

    init {
        count = 0
    }

    @Synchronized
    fun enter() {
        count++
    }

    @Synchronized
    fun leave() {
        count--
        notifyGroup()
    }

    fun notify(r: Runnable) {
        runnable = r
        notifyGroup()
    }

    private fun notifyGroup() {
        if (count <= 0 && runnable != null) {
            runnable!!.run()
        }
    }
}