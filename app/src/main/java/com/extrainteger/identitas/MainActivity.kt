package com.extrainteger.identitas

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import com.extrainteger.identitaslogin.*
import com.extrainteger.identitaslogin.models.AuthToken
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
        scope.add(IdentitasScope.PUBLIC)
        scope.add(IdentitasScope.EMAIL)
        //add all data to config
        val config = IdentitasConfig(
            this@MainActivity,
            base_url,
            client_id,
            client_secret,
            redirect_uri,
            scope,
            app_referer
        )
        //use config to configure oAuth
        tvLogin.configure(config)
        //set callback to login
        tvLogin.setCallback(object : Callback<AuthToken>(){
            override fun success(result: Result<AuthToken>) {
                //do some action after this login authorized and got some token
                Toast.makeText(this@MainActivity, "Logged in provider", Toast.LENGTH_SHORT).show()
                tvLogin.visibility = View.INVISIBLE
            }

            override fun failure(exception: IdentitasException) {
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
