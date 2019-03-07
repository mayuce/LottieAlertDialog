package com.labters.lottiealertdialog

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.labters.lottiealertdialoglibrary.ClickListener
import com.labters.lottiealertdialoglibrary.DialogTypes
import com.labters.lottiealertdialoglibrary.LottieAlertDialog

class MainActivity : AppCompatActivity() {
    var parentActivity : MainActivity = this
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        var alertDialog : LottieAlertDialog
        alertDialog= LottieAlertDialog.Builder(this,DialogTypes.TYPE_LOADING)
            .setTitle("Loading")
            .setDescription("Please Wait")
            .build()
        alertDialog.show()

        Handler().postDelayed(Runnable {
            alertDialog.changeVariables(LottieAlertDialog.Builder(parentActivity,DialogTypes.TYPE_QUESTION)
                .setTitle("What Type")
                .setDescription("Would you like to see ?")
                .setPositiveText("Error")
                .setNegativeText("Warning")
                .setNoneText("None")
                .setPositiveListener(object: ClickListener{
                    override fun onClick(dialog: LottieAlertDialog) {
                        alertDialog.changeVariables(LottieAlertDialog.Builder(parentActivity,DialogTypes.TYPE_ERROR)
                            .setTitle("Error")
                            .setDescription("Some error has happened.")
                            .setPositiveText("Okay")
                            .setPositiveListener(object : ClickListener{
                                override fun onClick(dialog: LottieAlertDialog) {
                                    dialog.dismiss()
                                }
                            })
                        )
                    }
                })
                .setNegativeListener(object : ClickListener
                {
                    override fun onClick(dialog: LottieAlertDialog) {
                        alertDialog.changeVariables(LottieAlertDialog.Builder(parentActivity,DialogTypes.TYPE_ERROR)
                            .setTitle("Warning")
                            .setDescription("Some warning.")
                            .setPositiveText("Okay")
                            .setPositiveListener(object : ClickListener{
                                override fun onClick(dialog: LottieAlertDialog) {
                                    dialog.dismiss()
                                }
                            }))
                    }
                })
                .setNoneListener(object: ClickListener
                {
                    override fun onClick(dialog: LottieAlertDialog) {
                        dialog.dismiss()
                    }
                })
            )
        },2000)
    }
}
