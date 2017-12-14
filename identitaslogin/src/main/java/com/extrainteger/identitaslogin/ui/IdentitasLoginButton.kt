package com.extrainteger.identitaslogin.ui

import android.app.Activity.RESULT_OK
import android.content.Context
import android.content.Intent
import android.util.AttributeSet
import android.widget.Button
import android.util.TypedValue
import android.view.View
import com.extrainteger.identitaslogin.*
import com.extrainteger.identitaslogin.models.AuthToken


/**
 * Created by ali on 04/12/17.
 */
class IdentitasLoginButton: Button{
    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet, defStyle: Int): super(context, attrs, defStyle)

    private var config: IdentitasConfig? = null
    private var mCallback: Callback<AuthToken>? = null

    init {
        setupButton()
    }

    private fun setupButton() {
        setText("Login with Identitas")
        setBackgroundColor(resources.getColor(R.color.green))
        setTextColor(resources.getColor(R.color.white))
        setTextSize(TypedValue.COMPLEX_UNIT_DIP, resources.getDimension(R.dimen.button_text_size))
        setCompoundDrawables(resources.getDrawable(R.drawable.ic_person_black_24dp), null, null, null)
        setPadding(20, 0,
                20, 0);
        setOnClickListener(LoginClickListener())
    }

    private inner class LoginClickListener : OnClickListener {
        override fun onClick(v: View?) {
            gotoOAuthActivity()
        }
    }

    private fun gotoOAuthActivity(){
        val intent = Intent(config?.activity, OauthActivity::class.java)
        intent.putExtra(IdentitasConstants.LOGIN_URL_FIELD, IdentitasConstants.LOGIN_URL_VALUE)
        intent.putExtra(IdentitasConstants.CLIENT_ID_FIELD, config?.CLIENT_ID)
        intent.putExtra(IdentitasConstants.CLIENT_SECRET_FIELD, config?.CLIENT_SCRET)
        intent.putExtra(IdentitasConstants.REDIRECT_URI_FIELD, config?.REDIRECT_URI)
        intent.putExtra(IdentitasConstants.SCOPE_FIELD, getScope(config?.SCOPES))
        config?.activity?.startActivityForResult(intent, IdentitasConstants.LOGIN_ACTIVITY_REQUEST_CODE)
    }

    private fun getScope(scopes: List<String>?): String? {
        val joined = scopes?.joinToString("+")
        return joined
    }

    fun configure(config: IdentitasConfig){
        this.config = config
    }

    fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == IdentitasConstants.LOGIN_ACTIVITY_REQUEST_CODE){
            if (resultCode == RESULT_OK){
                mCallback?.success(Result(AuthToken(
                        data?.getStringExtra(IdentitasConstants.ACCESS_TOKEN_FIELD),
                        data?.getStringExtra(IdentitasConstants.REFRESH_TOKEN_FIELD),
                        data?.getStringExtra(IdentitasConstants.TOKEN_TYPE_FIELD)), null)
                )
            }
            else if (data?.getIntExtra(IdentitasConstants.GET_TOKEN_ERROR_CODE_FIELD, 0)==400){
                mCallback?.failure(IdentitasException("Unauthorized"))
            }
            else if(data?.getStringExtra(IdentitasConstants.GET_TOKEN_EXCEPTION_FIELD)!=null){
                mCallback?.failure(IdentitasException(data.getStringExtra(IdentitasConstants.GET_TOKEN_EXCEPTION_FIELD)))
            }
        }
    }

    fun setCallback(callback: Callback<AuthToken>){
        this.mCallback = callback
    }
}