package com.example.sparta_personal4_applemarket.main

import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.DialogInterface
import android.content.Intent
import android.graphics.BitmapFactory
import android.media.AudioAttributes
import android.media.RingtoneManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.animation.AnimationUtils
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.sparta_personal4_applemarket.ListData
import com.example.sparta_personal4_applemarket.R
import com.example.sparta_personal4_applemarket.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.rvSaleList.adapter = MainRecyclerViewAdapter(ListData.data)
        binding.rvSaleList.layoutManager = LinearLayoutManager(this)

        setSupportActionBar(binding.tbMain)
        supportActionBar?.setDisplayShowTitleEnabled(true)

        floating()
    }


    @SuppressLint("MissingSuperCall")
    override fun onBackPressed() {
        setAlertBack()
    }

    fun setAlertBack() {
        val builder = AlertDialog.Builder(this)
        builder.setMessage("종료하시겠습니까?")

        val listener = DialogInterface.OnClickListener { dialog, which ->
            when (which) {
                DialogInterface.BUTTON_POSITIVE -> finish()
            }
        }

        builder.setPositiveButton("예", listener)
        builder.setNegativeButton("아니오", listener)

        builder.show()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_toolbar, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item?.itemId) {
            R.id.bell_notification -> {
                notification()
                return true
            }

            else -> return super.onOptionsItemSelected(item)
        }
    }

    private fun notification() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (!NotificationManagerCompat.from(this).areNotificationsEnabled()) {
                // 알림 권한이 없다면, 사용자에게 권한 요청
                val intent = Intent(Settings.ACTION_APP_NOTIFICATION_SETTINGS).apply {
                    putExtra(Settings.EXTRA_APP_PACKAGE, packageName)
                }
                startActivity(intent)
            }

            val manager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager

            val builder: NotificationCompat.Builder
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                val channelId = "one-channel"
                val channelName = "My Channel One"
                val channel = NotificationChannel(
                    channelId,
                    channelName,
                    NotificationManager.IMPORTANCE_DEFAULT
                ).apply {
                    // 채널에 다양한 정보 설정
                    description = "apple market notification"
                    setShowBadge(true)
                    val uri: Uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
                    val audioAttributes = AudioAttributes.Builder()
                        .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                        .setUsage(AudioAttributes.USAGE_ALARM)
                        .build()
                    setSound(uri, audioAttributes)
                    enableVibration(true)
                }

                manager.createNotificationChannel(channel)
                builder = NotificationCompat.Builder(this, channelId)
            } else builder = NotificationCompat.Builder(this)

            val bitmap = BitmapFactory.decodeResource(resources, R.drawable.ic_launcher_background)
            val intent = Intent(this, MainActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
            val pendingIntent = PendingIntent.getActivity(
                this,
                0,
                intent,
                PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
            )

            builder.run {
                setSmallIcon(R.mipmap.ic_launcher)
                setWhen(System.currentTimeMillis())
                setContentTitle("키워드 알림")
                setContentText("설정한 키워드 알림입니다..")
                setStyle(
                    NotificationCompat.BigTextStyle()
                        .bigText("설정한 키워드 알림입니다. 감사합니다.")
                )
                setLargeIcon(bitmap)
//            setStyle(NotificationCompat.BigPictureStyle()
//                    .bigPicture(bitmap)
//                    .bigLargeIcon(null))  // hide largeIcon while expanding
                addAction(R.mipmap.ic_launcher, "Action", pendingIntent)
            }


            manager.notify(11, builder.build())
        }
    }

    private fun floating() {
        val button = binding.ivFloating
        button.setOnClickListener {
            binding.rvSaleList.scrollToPosition(0)
            button.visibility = View.INVISIBLE
        }

        var isButtonVisible = true
        button.visibility = View.INVISIBLE
        binding.rvSaleList.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                val firstVisibleItemPosition =
                    (recyclerView.layoutManager as LinearLayoutManager).findFirstVisibleItemPosition()

                val fadeIn = AnimationUtils.loadAnimation(
                    binding.root.context,
                    R.anim.floating_fadein
                )
                val fadeOut = AnimationUtils.loadAnimation(
                    binding.root.context,
                    R.anim.floating_fadeout
                )

                if (firstVisibleItemPosition == 0 && !isButtonVisible) {
                    button.startAnimation(fadeIn)
                    button.visibility = View.INVISIBLE
                    isButtonVisible = true
                } else if (firstVisibleItemPosition != 0 && isButtonVisible) {
                    button.startAnimation(fadeOut)
                    button.visibility = View.VISIBLE
                    isButtonVisible = false
                }
            }
        })

    }
}