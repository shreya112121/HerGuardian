package com.org.service;

import com.org.model.EmergencyContact;
import com.org.model.SosAlert;
import com.org.model.User;
import com.org.repository.EmergencyContactRepository;
import com.org.repository.SosAlertRepository;
import com.org.repository.UserRepository;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SosAlertService {

    @Autowired
    private EmergencyContactRepository emergencyContactRepository;

    @Autowired
    private SosAlertRepository sosAlertRepository;

    @Autowired
    private UserRepository userRepository;

    @Value("${twilio.account.sid}")
    private String accountSid;

    @Value("${twilio.auth.token}")
    private String authToken;

    @Value("${twilio.phone.number}")
    private String fromNumber;

    @PostConstruct
    public void initTwilio() {
        Twilio.init(accountSid, authToken);
    }

    public SosAlert sendSmsToContacts(Long userId, Double lat, Double lng) {

        SosAlert alert = new SosAlert();
        alert.setUserId(userId);
        alert.setLatitude(lat);
        alert.setLongitude(lng);
        SosAlert savedAlert = sosAlertRepository.save(alert);

        User user = userRepository.findById(userId).orElse(null);
        String userName = (user != null && user.getFullName() != null) ? user.getFullName() : "HerGuardian User";
        String userPhone = (user != null && user.getPhone() != null) ? user.getPhone() : "";

        List<EmergencyContact> contacts = emergencyContactRepository.findByUserId(userId);

        if (contacts == null || contacts.isEmpty()) {
            System.out.println("[SOS] No emergency contacts for userId: " + userId);
            savedAlert.setStatus("no_contacts");
            sosAlertRepository.save(savedAlert);
            return savedAlert;
        }

        String mapsLink = (lat != null && lng != null)
                ? "https://maps.google.com/?q=" + lat + "," + lng
                : "Location unavailable";

        String message = "🚨 HerGuardian SOS ALERT!\n"
                + userName + " (" + userPhone + ") "
                + "mla madat havi ahe!\n"
                + "Location: " + mapsLink
                + "\nplease lvkr yaa!";

        boolean allSent = true;
        for (EmergencyContact contact : contacts) {
            String phone = formatPhone(contact.getPhone());
            boolean sent = sendTwilioSms(phone, message);
            if (!sent) allSent = false;
        }

        savedAlert.setStatus(allSent ? "sms_sent" : "sms_failed");
        sosAlertRepository.save(savedAlert);

        return savedAlert;
    }

    public SosAlert resolveAlert(Long sosId, Long userId) {
        SosAlert alert = sosAlertRepository.findById(sosId)
                .orElseThrow(() -> new RuntimeException("SOS alert not found: " + sosId));
        alert.setStatus("resolved");
        sosAlertRepository.save(alert);

     
        if (userId != null) {
            User user = userRepository.findById(userId).orElse(null);
            String userName = (user != null && user.getFullName() != null) ? user.getFullName() : "HerGuardian User";

            List<EmergencyContact> contacts = emergencyContactRepository.findByUserId(userId);

            if (contacts != null && !contacts.isEmpty()) {
                String safeMessage = "✅ HerGuardian SAFE ALERT!\n"
                        + userName + " is now SAFE.\n"
                        + "Kalji kru nka, me safe ahe! 🙏";

                for (EmergencyContact contact : contacts) {
                    String phone = formatPhone(contact.getPhone());
                    sendTwilioSms(phone, safeMessage);
                }
            }
        }

        return alert;
    }

    public List<SosAlert> getAlertHistory(Long userId) {
        return sosAlertRepository.findByUserIdOrderByTriggeredAtDesc(userId);
    }

    public long getSosCount(Long userId) {
        return sosAlertRepository.countByUserId(userId);
    }

    private String formatPhone(String phone) {
        phone = phone.replace(" ", "").trim();
        if (!phone.startsWith("+")) {
            phone = "+91" + phone.replace("+91", "");
        }
        return phone;
    }

    private boolean sendTwilioSms(String toNumber, String message) {
        try {
            Message msg = Message.creator(
                    new PhoneNumber(toNumber),
                    new PhoneNumber(fromNumber),
                    message
            ).create();
            System.out.println("[SOS] SMS sent to " + toNumber + " | SID: " + msg.getSid());
            return true;
        } catch (Exception e) {
            System.err.println("[SOS] SMS failed to " + toNumber + ": " + e.getMessage());
            return false;
        }
    }
}