package com.extrainteger.identitas

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.extrainteger.symbolic.ui.SymbolicLoginButton
import kotlinx.android.synthetic.main.activity_extras_test.*

class ExtrasActivityTest : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_extras_test)
        tes.setOnClickListener {
            SymbolicLoginButton.loadPage(this, "http://identitas.extrainteger.com/pantaubersama/invitation/accept?invitation_token=EvvfFg2ArzedEx5ucJFP",
                "com.pantaubersama.app://oauth")
        }
    }
}
