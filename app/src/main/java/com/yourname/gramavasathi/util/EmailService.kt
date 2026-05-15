package com.yourname.gramavasathi.util

import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

object EmailService {

    private val firestore = FirebaseFirestore.getInstance()

    private suspend fun sendEmail(
        toEmail: String,
        subject: String,
        htmlBody: String
    ) {
        try {
            val mailDoc = mapOf(
                "to" to listOf(toEmail),
                "message" to mapOf(
                    "subject" to subject,
                    "html" to htmlBody
                )
            )
            firestore.collection("mail").add(mailDoc).await()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    suspend fun sendWelcomeHostEmail(
        toEmail: String,
        hostName: String
    ) {
        sendEmail(
            toEmail = toEmail,
            subject = "Welcome to Grama-Vasathi! 🌾",
            htmlBody = """
                <div style="font-family:Arial,sans-serif;max-width:600px;margin:0 auto;">
                  <div style="background:#4A7C59;padding:24px;text-align:center;">
                    <h1 style="color:white;margin:0;">🌾 Grama-Vasathi</h1>
                    <p style="color:rgba(255,255,255,0.8);margin:8px 0 0;">
                      Rural Home-stay Accelerator
                    </p>
                  </div>
                  <div style="padding:32px;background:#faf7f2;">
                    <h2 style="color:#2C2C2A;">Namaskara, $hostName! 🙏</h2>
                    <p style="color:#5F5E5A;line-height:1.6;">
                      Welcome to Grama-Vasathi — Karnataka's premier rural
                      home-stay platform. Your host account is ready!
                    </p>
                    <div style="background:#EAF3DE;border-radius:12px;
                      padding:20px;margin:20px 0;">
                      <h3 style="color:#3B6D11;margin:0 0 12px;">
                        Your next steps:
                      </h3>
                      <p style="margin:6px 0;color:#5F5E5A;">
                        ✅ Complete the Host Readiness Checklist
                      </p>
                      <p style="margin:6px 0;color:#5F5E5A;">
                        📝 Create your farm stay listing
                      </p>
                      <p style="margin:6px 0;color:#5F5E5A;">
                        📖 Read the Host Guidance section
                      </p>
                      <p style="margin:6px 0;color:#5F5E5A;">
                        🎉 Start welcoming city guests!
                      </p>
                    </div>
                    <p style="color:#888780;font-size:13px;margin-top:32px;">
                      With warm regards,<br>
                      <strong style="color:#4A7C59;">Team Grama-Vasathi</strong>
                    </p>
                  </div>
                  <div style="background:#4A7C59;padding:16px;text-align:center;">
                    <p style="color:rgba(255,255,255,0.7);font-size:12px;margin:0;">
                      ಮಟ್ಟಿ ವಾಸನೆ — Scent of the Soil
                    </p>
                  </div>
                </div>
            """.trimIndent()
        )
    }

    suspend fun sendWelcomeGuestEmail(
        toEmail: String,
        guestName: String
    ) {
        sendEmail(
            toEmail = toEmail,
            subject = "Welcome to Grama-Vasathi! 🌾",
            htmlBody = """
                <div style="font-family:Arial,sans-serif;max-width:600px;margin:0 auto;">
                  <div style="background:#4A7C59;padding:24px;text-align:center;">
                    <h1 style="color:white;margin:0;">🌾 Grama-Vasathi</h1>
                    <p style="color:rgba(255,255,255,0.8);margin:8px 0 0;">
                      Discover Authentic Rural Karnataka
                    </p>
                  </div>
                  <div style="padding:32px;background:#faf7f2;">
                    <h2 style="color:#2C2C2A;">Namaskara, $guestName! 🙏</h2>
                    <p style="color:#5F5E5A;line-height:1.6;">
                      Welcome to Grama-Vasathi! Your guest account is ready.
                      Start exploring authentic farm stays across Karnataka.
                    </p>
                    <div style="background:#E6F1FB;border-radius:12px;
                      padding:20px;margin:20px 0;">
                      <h3 style="color:#185FA5;margin:0 0 12px;">
                        Start exploring:
                      </h3>
                      <p style="margin:6px 0;color:#5F5E5A;">
                        🐄 Cow milking and farm activities
                      </p>
                      <p style="margin:6px 0;color:#5F5E5A;">
                        🍛 Authentic local home-cooked meals
                      </p>
                      <p style="margin:6px 0;color:#5F5E5A;">
                        🦜 Birdwatching and nature walks
                      </p>
                      <p style="margin:6px 0;color:#5F5E5A;">
                        🌾 The real Matti-Vasane experience!
                      </p>
                    </div>
                    <p style="color:#888780;font-size:13px;margin-top:32px;">
                      With warm regards,<br>
                      <strong style="color:#4A7C59;">Team Grama-Vasathi</strong>
                    </p>
                  </div>
                  <div style="background:#4A7C59;padding:16px;text-align:center;">
                    <p style="color:rgba(255,255,255,0.7);font-size:12px;margin:0;">
                      ಮಟ್ಟಿ ವಾಸನೆ — Scent of the Soil
                    </p>
                  </div>
                </div>
            """.trimIndent()
        )
    }

    suspend fun sendListingAddedEmail(
        toEmail: String,
        hostName: String,
        listingTitle: String,
        village: String,
        score: Int
    ) {
        sendEmail(
            toEmail = toEmail,
            subject = "Your listing is live on Grama-Vasathi! 🎉",
            htmlBody = """
                <div style="font-family:Arial,sans-serif;max-width:600px;margin:0 auto;">
                  <div style="background:#4A7C59;padding:24px;text-align:center;">
                    <h1 style="color:white;margin:0;">🌾 Grama-Vasathi</h1>
                  </div>
                  <div style="padding:32px;background:#faf7f2;">
                    <h2 style="color:#2C2C2A;">Your listing is live! 🎉</h2>
                    <p style="color:#5F5E5A;line-height:1.6;">
                      Namaskara $hostName! Your farm stay is now
                      published and visible to guests.
                    </p>
                    <div style="background:#EAF3DE;border-radius:12px;
                      padding:20px;margin:20px 0;">
                      <p style="margin:6px 0;color:#2C2C2A;font-weight:bold;">
                        $listingTitle
                      </p>
                      <p style="margin:6px 0;color:#5F5E5A;">📍 $village</p>
                      <p style="margin:6px 0;color:#3B6D11;font-weight:bold;">
                        ✓ Readiness Score: $score%
                      </p>
                    </div>
                    <div style="background:#FAEEDA;border-radius:12px;
                      padding:20px;margin:20px 0;">
                      <h3 style="color:#854F0B;margin:0 0 12px;">
                        Tips to get more bookings:
                      </h3>
                      <p style="margin:6px 0;color:#5F5E5A;">
                        ⭐ Keep readiness score above 80%
                      </p>
                      <p style="margin:6px 0;color:#5F5E5A;">
                        📱 Share on WhatsApp groups
                      </p>
                      <p style="margin:6px 0;color:#5F5E5A;">
                        💬 Ask guests to leave reviews
                      </p>
                    </div>
                    <p style="color:#888780;font-size:13px;margin-top:32px;">
                      With warm regards,<br>
                      <strong style="color:#4A7C59;">Team Grama-Vasathi</strong>
                    </p>
                  </div>
                </div>
            """.trimIndent()
        )
    }

    suspend fun sendBookingConfirmationToGuest(
        toEmail: String,
        guestName: String,
        bookingRef: String,
        listingTitle: String,
        hostName: String,
        checkIn: String,
        checkOut: String,
        numGuests: Int,
        totalAmount: Int
    ) {
        sendEmail(
            toEmail = toEmail,
            subject = "Booking Confirmed — $bookingRef 🎉",
            htmlBody = """
                <div style="font-family:Arial,sans-serif;max-width:600px;margin:0 auto;">
                  <div style="background:#4A7C59;padding:24px;text-align:center;">
                    <h1 style="color:white;margin:0;">🌾 Grama-Vasathi</h1>
                  </div>
                  <div style="padding:32px;background:#faf7f2;">
                    <div style="text-align:center;margin-bottom:24px;">
                      <div style="font-size:48px;">✅</div>
                      <h2 style="color:#4A7C59;margin:8px 0;">
                        Booking Confirmed!
                      </h2>
                    </div>
                    <p style="color:#5F5E5A;line-height:1.6;">
                      Namaskara $guestName! Your rural adventure awaits!
                    </p>
                    <div style="background:white;border-radius:12px;
                      padding:20px;margin:20px 0;border:1px solid #E2DDD5;">
                      <div style="text-align:center;border-bottom:1px solid #E2DDD5;
                        padding-bottom:16px;margin-bottom:16px;">
                        <p style="font-size:12px;color:#888780;margin:0;">
                          BOOKING REFERENCE
                        </p>
                        <p style="font-size:24px;font-weight:bold;color:#4A7C59;
                          margin:4px 0;letter-spacing:2px;">
                          $bookingRef
                        </p>
                      </div>
                      <table style="width:100%;border-collapse:collapse;">
                        <tr>
                          <td style="padding:8px 0;color:#888780;font-size:13px;">
                            Stay</td>
                          <td style="padding:8px 0;color:#2C2C2A;font-size:13px;
                            font-weight:bold;text-align:right;">$listingTitle</td>
                        </tr>
                        <tr>
                          <td style="padding:8px 0;color:#888780;font-size:13px;">
                            Host</td>
                          <td style="padding:8px 0;color:#2C2C2A;font-size:13px;
                            text-align:right;">$hostName</td>
                        </tr>
                        <tr>
                          <td style="padding:8px 0;color:#888780;font-size:13px;">
                            Check-in</td>
                          <td style="padding:8px 0;color:#2C2C2A;font-size:13px;
                            text-align:right;">$checkIn</td>
                        </tr>
                        <tr>
                          <td style="padding:8px 0;color:#888780;font-size:13px;">
                            Check-out</td>
                          <td style="padding:8px 0;color:#2C2C2A;font-size:13px;
                            text-align:right;">$checkOut</td>
                        </tr>
                        <tr>
                          <td style="padding:8px 0;color:#888780;font-size:13px;">
                            Guests</td>
                          <td style="padding:8px 0;color:#2C2C2A;font-size:13px;
                            text-align:right;">$numGuests</td>
                        </tr>
                        <tr style="border-top:1px solid #E2DDD5;">
                          <td style="padding:12px 0;color:#2C2C2A;font-weight:bold;">
                            Total</td>
                          <td style="padding:12px 0;color:#4A7C59;font-weight:bold;
                            font-size:18px;text-align:right;">₹$totalAmount</td>
                        </tr>
                      </table>
                    </div>
                    <div style="background:#EAF3DE;border-radius:12px;
                      padding:20px;margin:20px 0;">
                      <h3 style="color:#3B6D11;margin:0 0 12px;">
                        Tips for your rural stay:
                      </h3>
                      <p style="margin:6px 0;color:#5F5E5A;">
                        💵 Carry cash — ATMs may be far away
                      </p>
                      <p style="margin:6px 0;color:#5F5E5A;">
                        👗 Wear comfortable cotton clothes
                      </p>
                      <p style="margin:6px 0;color:#5F5E5A;">
                        🙏 Respect local customs and farming work
                      </p>
                      <p style="margin:6px 0;color:#5F5E5A;">
                        🍛 Try the local home-cooked food!
                      </p>
                    </div>
                    <p style="color:#888780;font-size:13px;margin-top:32px;">
                      We wish you a wonderful Matti-Vasane experience!<br>
                      <strong style="color:#4A7C59;">Team Grama-Vasathi</strong>
                    </p>
                  </div>
                  <div style="background:#4A7C59;padding:16px;text-align:center;">
                    <p style="color:rgba(255,255,255,0.7);font-size:12px;margin:0;">
                      ಮಟ್ಟಿ ವಾಸನೆ — Scent of the Soil
                    </p>
                  </div>
                </div>
            """.trimIndent()
        )
    }

    suspend fun sendBookingNotificationToHost(
        hostEmail: String,
        hostName: String,
        guestName: String,
        bookingRef: String,
        listingTitle: String,
        checkIn: String,
        checkOut: String,
        numGuests: Int,
        totalAmount: Int
    ) {
        sendEmail(
            toEmail = hostEmail,
            subject = "New Booking — $guestName is coming! 🎉",
            htmlBody = """
                <div style="font-family:Arial,sans-serif;max-width:600px;margin:0 auto;">
                  <div style="background:#4A7C59;padding:24px;text-align:center;">
                    <h1 style="color:white;margin:0;">🌾 Grama-Vasathi</h1>
                  </div>
                  <div style="padding:32px;background:#faf7f2;">
                    <div style="text-align:center;margin-bottom:24px;">
                      <div style="font-size:48px;">🎉</div>
                      <h2 style="color:#4A7C59;margin:8px 0;">
                        New Booking Received!
                      </h2>
                    </div>
                    <p style="color:#5F5E5A;line-height:1.6;">
                      Namaskara $hostName! $guestName has booked your stay!
                    </p>
                    <div style="background:white;border-radius:12px;
                      padding:20px;margin:20px 0;border:1px solid #E2DDD5;">
                      <table style="width:100%;border-collapse:collapse;">
                        <tr>
                          <td style="padding:8px 0;color:#888780;font-size:13px;">
                            Booking Ref</td>
                          <td style="padding:8px 0;color:#4A7C59;font-size:13px;
                            font-weight:bold;text-align:right;">$bookingRef</td>
                        </tr>
                        <tr>
                          <td style="padding:8px 0;color:#888780;font-size:13px;">
                            Guest</td>
                          <td style="padding:8px 0;color:#2C2C2A;font-size:13px;
                            text-align:right;">$guestName</td>
                        </tr>
                        <tr>
                          <td style="padding:8px 0;color:#888780;font-size:13px;">
                            Stay</td>
                          <td style="padding:8px 0;color:#2C2C2A;font-size:13px;
                            text-align:right;">$listingTitle</td>
                        </tr>
                        <tr>
                          <td style="padding:8px 0;color:#888780;font-size:13px;">
                            Check-in</td>
                          <td style="padding:8px 0;color:#2C2C2A;font-size:13px;
                            text-align:right;">$checkIn</td>
                        </tr>
                        <tr>
                          <td style="padding:8px 0;color:#888780;font-size:13px;">
                            Check-out</td>
                          <td style="padding:8px 0;color:#2C2C2A;font-size:13px;
                            text-align:right;">$checkOut</td>
                        </tr>
                        <tr>
                          <td style="padding:8px 0;color:#888780;font-size:13px;">
                            Guests</td>
                          <td style="padding:8px 0;color:#2C2C2A;font-size:13px;
                            text-align:right;">$numGuests</td>
                        </tr>
                        <tr style="border-top:1px solid #E2DDD5;">
                          <td style="padding:12px 0;color:#2C2C2A;font-weight:bold;">
                            Earnings</td>
                          <td style="padding:12px 0;color:#4A7C59;font-weight:bold;
                            font-size:18px;text-align:right;">₹$totalAmount</td>
                        </tr>
                      </table>
                    </div>
                    <div style="background:#FAEEDA;border-radius:12px;
                      padding:20px;margin:20px 0;">
                      <h3 style="color:#854F0B;margin:0 0 12px;">
                        Prepare for your guests:
                      </h3>
                      <p style="margin:6px 0;color:#5F5E5A;">
                        🛏 Clean and air the room before arrival
                      </p>
                      <p style="margin:6px 0;color:#5F5E5A;">
                        💧 Keep fresh drinking water ready
                      </p>
                      <p style="margin:6px 0;color:#5F5E5A;">
                        🙏 Greet them warmly with Namaskara
                      </p>
                      <p style="margin:6px 0;color:#5F5E5A;">
                        📱 Keep your phone available for queries
                      </p>
                    </div>
                    <p style="color:#888780;font-size:13px;margin-top:32px;">
                      Thank you for being a wonderful host!<br>
                      <strong style="color:#4A7C59;">Team Grama-Vasathi</strong>
                    </p>
                  </div>
                </div>
            """.trimIndent()
        )
    }
}