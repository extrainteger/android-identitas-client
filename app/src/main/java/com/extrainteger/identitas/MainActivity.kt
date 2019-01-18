package com.extrainteger.identitas

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import com.extrainteger.symbolic.*
import com.extrainteger.symbolic.models.SymbolicToken
import kotlinx.android.synthetic.main.activity_main.*
import java.util.ArrayList

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val client_id = "[your client id]"
        val client_secret = "[your client secret]"
        val redirect_uri = "[your redirect uri]"
        val base_url = "[base url]"
        val app_referer = "[app_referer]"
        //scope
        val scope: MutableList<String> = ArrayList()
        scope.add(SymbolicScope.PUBLIC)
        scope.add(SymbolicScope.EMAIL)
        //add all data to config
        val config = SymbolicConfig(
            this@MainActivity,
            base_url,
            client_id,
            client_secret,
            redirect_uri,
            scope
        )
        //use config to configure oAuth
        tvLogin.configure(config)
        //set callback to login
        tvLogin.setCallback(object : Callback<SymbolicToken>(){
            override fun success(result: Result<SymbolicToken>) {
                //do some action after this login authorized and got some token
                Toast.makeText(this@MainActivity, "Logged in provider", Toast.LENGTH_SHORT).show()
                tvLogin.visibility = View.INVISIBLE
            }

            override fun failure(exception: SymbolicException) {
                //do some handle when this login got error
                Log.e("auth error", exception.message)
            }

        })
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        //handle onActivityResult(...) like this
        tvLogin.onActivityResult(requestCode, resultCode, data)
    }
}
