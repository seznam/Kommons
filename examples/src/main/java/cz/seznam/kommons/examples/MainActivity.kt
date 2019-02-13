package cz.seznam.kommons.examples

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import cz.seznam.kommons.kexts.guardAction

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

        val view = View(this)
        view.setOnClickListener(guardAction<View> {

        })
    }
}
