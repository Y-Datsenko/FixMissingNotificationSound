package com.datsenko.fixmissingnotificationsound

import android.app.Notification
import android.content.ContentResolver
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.NotificationChannelCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.fragment.app.Fragment
import com.datsenko.fixmissingnotificationsound.databinding.FragmentFirstBinding
import kotlin.random.Random

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class FirstFragment : Fragment() {

    private var _binding: FragmentFirstBinding? = null

    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initNotificationChannel()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFirstBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.tvChannelInfo.text = getChannelsInfo()
        binding.tvRawIdText.text = getString(R.string.raw_file_id_title, R.raw.iphone_ringtone)
        binding.buttonFirst.setOnClickListener {
            showNotification()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun showNotification() {
        val builder = NotificationCompat.Builder(this.requireContext(), CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentTitle(getString(R.string.notification_title))
            .setContentText(getString(R.string.notification_body))
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setSound(Uri.parse("${ContentResolver.SCHEME_ANDROID_RESOURCE}://${requireContext().packageName}/${R.raw.iphone_ringtone}"))
        val notificationManager = NotificationManagerCompat.from(requireContext())
        notificationManager.notify(Random.nextInt(), builder.build())
    }

    private fun initNotificationChannel() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O) return
        val channelName = getString(R.string.general_channel_title)
        val channelDescription = getString(R.string.general_channel_description)
        val importance = NotificationManagerCompat.IMPORTANCE_HIGH
        val channel = NotificationChannelCompat.Builder(CHANNEL_ID, importance).apply {
            setName(channelName)
            setDescription(channelDescription)
            setSound(
                Uri.parse("${ContentResolver.SCHEME_ANDROID_RESOURCE}://${requireContext().packageName}/${R.raw.iphone_ringtone}"),
                Notification.AUDIO_ATTRIBUTES_DEFAULT
            )
        }
        NotificationManagerCompat.from(requireContext()).createNotificationChannel(channel.build())
    }

    private fun getChannelsInfo(): String =
        NotificationManagerCompat.from(requireContext())
            .notificationChannelsCompat
            .joinToString(separator = "\n,") { "id =[${it.id}] with sound URI =[${it.sound}]" }
            .takeIf { it.isNotEmpty() } ?: "empty channel info"


    companion object {
        const val CHANNEL_ID: String = "general_channel"
    }
}