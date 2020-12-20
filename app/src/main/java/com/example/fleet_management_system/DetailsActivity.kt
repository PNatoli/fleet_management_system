package com.example.fleet_management_system

import android.app.Activity
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Gravity
import android.view.MenuItem
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_details.*
import java.text.DecimalFormat

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

        // set listener for save button
        saveButton.setOnClickListener {
            handleSaveData()
        }
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

        // format the resale value to dollars
        val formatter = DecimalFormat("$###,###,##0.00")
        if (bus.resaleValue > 0.0){
            resaleAmount.text = formatter.format(bus.resaleValue)
        } else {
            resaleAmount.text = "Does not qualify"
        }
    }

    private fun handleSaveData(){
        // validation not necessary due to keyboard inputs and max length on fields
        // drop keyboard
        hideSoftKeyboard(this)

        // update room


        // update model

        // run bus.getResaleValue

        // update resale field

        // feedback to user
        val toast = Toast.makeText(this,"All changes saved", Toast.LENGTH_LONG)
        toast.setGravity(Gravity.BOTTOM or Gravity.CENTER_HORIZONTAL, 0, 300)
        toast.show()
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

    private fun hideSoftKeyboard(activity: Activity){
        if(activity.currentFocus != null) {
            val imm = activity.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(activity.currentFocus!!.windowToken, 0)
        }
    }
}