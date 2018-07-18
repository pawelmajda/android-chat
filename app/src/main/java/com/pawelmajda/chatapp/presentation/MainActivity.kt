package com.pawelmajda.chatapp.presentation

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.Fragment
import com.pawelmajda.chatapp.R
import com.pawelmajda.chatapp.presentation.messages.MessagesFragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
        setTitle(R.string.messages_title)

        if (savedInstanceState == null) {
            changeFragment(MessagesFragment())
        }
    }

    fun changeFragment(fragment: Fragment) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.setCustomAnimations(R.anim.abc_fade_in, R.anim.abc_fade_out, R.anim.abc_popup_enter, R.anim.abc_popup_exit)
        transaction.replace(R.id.fragmentContainer, fragment)
        transaction.commit()
        supportFragmentManager.executePendingTransactions()
    }
}
