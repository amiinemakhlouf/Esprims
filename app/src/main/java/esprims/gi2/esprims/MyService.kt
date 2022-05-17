package esprims.gi2.esprims

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.IBinder
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class MyService : Service() {
    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onCreate() {
        super.onCreate()
        createNotificationChannel()
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        var shouldFetch = false
        val id = intent!!.getIntExtra("gradeID", 0)
        val db = Firebase.firestore
        db.collection("matiere").whereEqualTo("class_id", id)
            .addSnapshotListener { value, error ->
                if (shouldFetch) {
                    sendNotification()

                }


                shouldFetch=true
            }
        return START_NOT_STICKY


    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = "Notification Title"
            val descriptionText = "Notification Description"
            val importance: Int = NotificationManager.IMPORTANCE_DEFAULT
            val channel: NotificationChannel = NotificationChannel("0", name, importance).apply {
                description = descriptionText
            }
            val notificationManager: NotificationManager =
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)


        }
    }

    private fun sendNotification() {
        val intent: Intent =
            Intent(this, MainActivity::class.java).putExtra("origin", "service").apply {
                flags = Intent.FLAG_ACTIVITY_NEW_TASK or  Intent.FLAG_ACTIVITY_SINGLE_TOP
            }
        var pendingIntent:PendingIntent? =null
        if(android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
             pendingIntent =
                PendingIntent.getActivity(this, 0, intent,
                    PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_MUTABLE)

        }
        val builder: NotificationCompat.Builder = NotificationCompat.Builder(this, "0")
            .setSmallIcon(R.drawable.esp)
            .setContentTitle("ESPRIMS")
            .setContentText("notification importante")
            .setContentIntent(pendingIntent)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
        with(NotificationManagerCompat.from(this)) {
            notify(0, builder.build())
        }
    }
}