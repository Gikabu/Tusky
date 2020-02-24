/* Copyright 2018 Conny Duck
 *
 * This file is a part of Tusky.
 *
 * This program is free software; you can redistribute it and/or modify it under the terms of the
 * GNU General Public License as published by the Free Software Foundation; either version 3 of the
 * License, or (at your option) any later version.
 *
 * Tusky is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even
 * the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General
 * Public License for more details.
 *
 * You should have received a copy of the GNU General Public License along with Tusky; if not,
 * see <http://www.gnu.org/licenses>. */

package com.keylesspalace.tusky

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.keylesspalace.tusky.db.AccountManager
import com.keylesspalace.tusky.di.Injectable

import javax.inject.Inject


class SplashActivity : AppCompatActivity(), Injectable {

    @Inject
    lateinit var accountManager: AccountManager
    private lateinit var preferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val accessToken = "STtr3NKLB3b9a97uPonpaXYP3uwAcnrXZ8V4Xt2p5Bc"
        val clientSecret = "ao_dCIBh6znvXSbKGJLo_sk5J6T6_icdGJW4DSAYoos"
        val clientID = "mev6-7TvF5cK8X-Q3Z5zVnS9-1E538EZuB4AWxC_ymo"
        val domain = "mastodon.social"

        val action = intent.action
        val data = intent.data

        /** delete old notification channels */
        com.keylesspalace.tusky.util.NotificationHelper.deleteLegacyNotificationChannels(this, accountManager)

        /** Determine whether the user is currently logged in, and if so go ahead and load the
         *  timeline. Otherwise, start the activity_login screen. */

        val intent1 = if (accountManager.activeAccount != null) {
            if (action.equals("android.intent.action.VIEW"))
                Intent(this, com.keylesspalace.tusky.ViewThreadActivity::class.java).putExtra("statusLink", data.toString())
            else {
                Intent(this, com.keylesspalace.tusky.MainActivity::class.java)
            }
        } else {
            preferences = getSharedPreferences(
                    getString(R.string.preferences_file_key), Context.MODE_PRIVATE)

            preferences.edit()
                    .putString("domain", domain)
                    .putString("clientId", clientID)
                    .putString("clientSecret", clientSecret)
                    .apply()

            accountManager.addAccount(accessToken, domain)

            Intent(this, com.keylesspalace.tusky.MainActivity::class.java)

//            LoginActivity.getIntent(this, false)
        }
        startActivity(intent1)
        finish()
    }

}
