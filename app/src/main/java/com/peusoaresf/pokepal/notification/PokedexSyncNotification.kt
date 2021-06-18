package com.peusoaresf.pokepal.notification

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.peusoaresf.pokepal.R

class PokedexSyncNotification(appContext: Context) {
    companion object {
        const val NOTIFICATION_ID = 1
        const val CHANNEL_ID = "POKEPAL_POKEDEX-SYNC"
    }

    private val notificationManager = NotificationManagerCompat.from(appContext)
    private val notificationBuilder = NotificationCompat.Builder(appContext, CHANNEL_ID).apply {
        setContentTitle("Pokedex Synchronization")
        setSmallIcon(R.drawable.ic_launcher_foreground)
        setNotificationSilent()
        priority = NotificationCompat.PRIORITY_LOW
    }

    init {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                CHANNEL_ID,
                "Pokedex Synchronization",
                NotificationManager.IMPORTANCE_DEFAULT
            )
            channel.description = "Notifications about pokedex synchronization's progress"

            notificationManager.createNotificationChannel(channel)
        }
    }

    fun notifyProgress(progress: Int) {
        when (progress >= 100) {
            true -> notificationBuilder
                    .setContentText("Sync complete")
                    .setProgress(0, 0, false)
            else -> notificationBuilder
                    .setContentText("Sync in progress")
                    .setProgress(100, progress, false)
        }

        notificationManager.notify(NOTIFICATION_ID, notificationBuilder.build())
    }
}