package com.example.fleet_management_system

import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import kotlinx.android.synthetic.main.activity_details.*

class DetailsActivity : AppCompatActivity() {
    lateinit var bus: BusModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)
        // setup nav bar
        setupActionBar()

        bus = intent.extras?.get("bus") as BusModel

        // fill bus data in
        setData()
    }

    private fun setData(){
        busID.text = bus.id
        vinNumber.text = bus.vin
        make.setText(bus.make)
        model.setText(bus.model)

        if (bus.year != null){
            year.setText(bus.year.toString())
        }
        if (bus.odometer != null){
            odometer.setText(bus.odometer.toString())
        }
        if (bus.wheels != null){
            numOfWheels.setText(bus.wheels.toString())
        }
        if (bus.maxCapacity != null){
            maxCapacity.setText(bus.maxCapacity.toString())
        }

        noACRadio.isChecked = !bus.airCond

        // switch on current status enum
        when (bus.currentStatus){
            CurrentStatusEnum.READY_FOR_USE -> readyForUseRadio.isChecked = true
            CurrentStatusEnum.SCHEDULED_FOR_MAINTENANCE -> schedForMaintenanceRadio.isChecked = true
            CurrentStatusEnum.UNDERGOING_REPAIRS -> undergoingRepairsRadio.isChecked = true
        }

        if (bus.resaleValue > 0.0){
            resaleAmount.text = bus.resaleValue.toString()
        } else {
            resaleAmount.text = "N/A"
        }
    }

    fun saveData(){
        // validation
        // update room
        // update model
        // run bus.getResaleValue
        // update resalefield
    }

    fun calculateResale(){

    }

    // navigation back
    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId){
            android.R.id.home -> {
                finish()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    // back soft key
    override fun onBackPressed() {
        finish()
    }

    private fun setupActionBar() {
        supportActionBar?.title = ""
        supportActionBar?.elevation = 0f
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setBackgroundDrawable(ColorDrawable(getColor(R.color.background)))
    }
}