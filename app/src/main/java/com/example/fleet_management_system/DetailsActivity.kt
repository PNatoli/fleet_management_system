package com.example.fleet_management_system

import android.app.Activity
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.Gravity
import android.view.MenuItem
import android.view.View
import android.view.animation.AlphaAnimation
import android.view.animation.Animation
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_details.*
import java.text.DecimalFormat

class DetailsActivity : AppCompatActivity() {
    lateinit var bus: BusModel
    private val dbManager = DBManager()
    private val formatter = DecimalFormat("$###,###,##0.00")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)
        // setup nav bar
        setupActionBar()

        // initialize db manager
        dbManager.initialize(this)

        // bring in bus from last activity
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
        if (bus.resaleValue > 0.0){
            resaleAmount.text = formatter.format(bus.resaleValue)
        } else {
            resaleAmount.text = "Does not qualify"
        }
    }

    private fun handleSaveData(){
        // validation not necessary due to keyboard inputs limitations and max length on fields
        // drop keyboard
        hideSoftKeyboard(this)

        // get current status
        val tempCurrentStatus: CurrentStatusEnum = when {
            readyForUseRadio.isChecked -> {
                CurrentStatusEnum.READY_FOR_USE
            }
            schedForMaintenanceRadio.isChecked -> {
                CurrentStatusEnum.SCHEDULED_FOR_MAINTENANCE
            }
            else -> {
                CurrentStatusEnum.UNDERGOING_REPAIRS
            }
        }

        // update model
        bus.make = make.text.toString()
        bus.model = model.text.toString()
        bus.year = year.text.toString().toInt()
        bus.odometer = odometer.text.toString().toInt()
        bus.wheels = numOfWheels.text.toString().toInt()
        bus.maxCapacity = maxCapacity.text.toString().toInt()
        bus.airCond = yesACRadio.isChecked
        bus.currentStatus = tempCurrentStatus
        bus.getResaleValue()

        dbManager.insertUpdateBusInDB(bus){
            println("done updating")
        }

        // update resale field if changed, provide feedback
        if (resaleAmount.text.toString() != formatter.format(bus.resaleValue)) {
            // run getResaleValue to get new value
            resaleAmount.text = formatter.format(bus.resaleValue)
            blink(warningImg)
            blink(resaleAmount)
        }

        // feedback to user for saved changes and new resale value available
        val toast = Toast.makeText(this,"All changes saved", Toast.LENGTH_LONG)
        toast.setGravity(Gravity.BOTTOM or Gravity.CENTER_HORIZONTAL, 0, 300)
        toast.show()
    }

    private fun blink(view: View){
        // could be moved into an extension for views for more reusable code
        val anim: Animation = AlphaAnimation(0.0f, 1.0f)
        anim.duration = 500
        anim.startOffset = 20
        anim.repeatMode = Animation.REVERSE
        anim.repeatCount = 5
        view.startAnimation(anim)
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
        window.navigationBarColor = resources.getColor(R.color.background)
    }

    private fun hideSoftKeyboard(activity: Activity){
        if(activity.currentFocus != null) {
            val imm = activity.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(activity.currentFocus!!.windowToken, 0)
        }
    }
}