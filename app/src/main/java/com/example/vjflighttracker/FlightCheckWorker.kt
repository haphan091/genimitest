package com.example.vjflighttracker

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.work.Worker
import androidx.work.WorkerParameters
import kotlin.random.Random

class FlightCheckWorker(
    private val context: Context,
    workerParams: WorkerParameters
) : Worker(context, workerParams) {

    data class FlightInfo(
        val flightNumber: String,
        val route: String,
        val status: String
    )

    override fun doWork(): Result {
        // Danh sách các chuyến bay định tuyến đến Pleiku (PXU)
        val allFlights = listOf(
            FlightInfo("VJ392", "SGN - PXU", "Đang cất cánh"),
            FlightInfo("VJ394", "SGN - PXU", "Đang bay trên không"),
            FlightInfo("VJ396", "SGN - PXU", "Chuẩn bị hạ cánh"),
            FlightInfo("VJ421", "HAN - PXU", "Đang cất cánh"),
            FlightInfo("VJ423", "HAN - PXU", "Đang bay trên không"),
            FlightInfo("VJ425", "HAN - PXU", "Hạ cánh an toàn")
        )

        // CHỈ LỌC RA CÁC CHUYẾN BAY CÓ TÀU BAY XUẤT HIỆN / THAY ĐỔI TRẠNG THÁI
        val activeFlights = allFlights.filter { flight ->
            isFlightActive(flight.flightNumber)
        }

        // Chỉ hiển thị thông tin và bắn thông báo khi có tàu bay xuất hiện thực sự
        if (activeFlights.isNotEmpty()) {
            activeFlights.forEachIndexed { index, flight ->
                sendNotification(
                    "Phát hiện tàu bay ${flight.flightNumber}",
                    "Chuyến bay ${flight.flightNumber} (${flight.route}): ${flight.status} hướng về PXU!",
                    notificationId = 2000 + index
                )
            }
        }

        return Result.success()
    }

    private fun isFlightActive(flightNumber: String): Boolean {
        // [QUAN TRỌNG]: Sau này đây sẽ là nơi gọi API kiểm tra trạng thái thật.
        // Hiện tại giả lập: Trả về ngẫu nhiên true/false để mô phỏng thời điểm tàu bay xuất hiện.
        return Random.nextBoolean()
    }

    private fun sendNotification(title: String, message: String, notificationId: Int) {
        val channelId = "vj_flight_channel"
        val notificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                channelId,
                "Chuyến bay Vietjet PXU",
                NotificationManager.IMPORTANCE_HIGH
            )
            notificationManager.createNotificationChannel(channel)
        }

        val notification = NotificationCompat.Builder(context, channelId)
            .setContentTitle(title)
            .setContentText(message)
            .setSmallIcon(android.R.drawable.ic_dialog_info)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setAutoCancel(true)
            .build()

        notificationManager.notify(notificationId, notification)
    }
}package com.example.vjflighttracker

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.work.Worker
import androidx.work.WorkerParameters
import kotlin.random.Random

class FlightCheckWorker(
    private val context: Context,
    workerParams: WorkerParameters
) : Worker(context, workerParams) {

    data class FlightInfo(
        val flightNumber: String,
        val route: String,
        val status: String
    )

    override fun doWork(): Result {
        // Danh sách các chuyến bay định tuyến đến Pleiku (PXU)
        val allFlights = listOf(
            FlightInfo("VJ392", "SGN - PXU", "Đang cất cánh"),
            FlightInfo("VJ394", "SGN - PXU", "Đang bay trên không"),
            FlightInfo("VJ396", "SGN - PXU", "Chuẩn bị hạ cánh"),
            FlightInfo("VJ421", "HAN - PXU", "Đang cất cánh"),
            FlightInfo("VJ423", "HAN - PXU", "Đang bay trên không"),
            FlightInfo("VJ425", "HAN - PXU", "Hạ cánh an toàn")
        )

        // CHỈ LỌC RA CÁC CHUYẾN BAY CÓ TÀU BAY XUẤT HIỆN / THAY ĐỔI TRẠNG THÁI
        val activeFlights = allFlights.filter { flight ->
            isFlightActive(flight.flightNumber)
        }

        // Chỉ hiển thị thông tin và bắn thông báo khi có tàu bay xuất hiện thực sự
        if (activeFlights.isNotEmpty()) {
            activeFlights.forEachIndexed { index, flight ->
                sendNotification(
                    "Phát hiện tàu bay ${flight.flightNumber}",
                    "Chuyến bay ${flight.flightNumber} (${flight.route}): ${flight.status} hướng về PXU!",
                    notificationId = 2000 + index
                )
            }
        }

        return Result.success()
    }

    private fun isFlightActive(flightNumber: String): Boolean {
        // [QUAN TRỌNG]: Sau này đây sẽ là nơi gọi API kiểm tra trạng thái thật.
        // Hiện tại giả lập: Trả về ngẫu nhiên true/false để mô phỏng thời điểm tàu bay xuất hiện.
        return Random.nextBoolean()
    }

    private fun sendNotification(title: String, message: String, notificationId: Int) {
        val channelId = "vj_flight_channel"
        val notificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                channelId,
                "Chuyến bay Vietjet PXU",
                NotificationManager.IMPORTANCE_HIGH
            )
            notificationManager.createNotificationChannel(channel)
        }

        val notification = NotificationCompat.Builder(context, channelId)
            .setContentTitle(title)
            .setContentText(message)
            .setSmallIcon(android.R.drawable.ic_dialog_info)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setAutoCancel(true)
            .build()

        notificationManager.notify(notificationId, notification)
    }
}
