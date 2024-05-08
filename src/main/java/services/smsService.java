package services;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;

public class smsService {

    // Twilio API credentials
    private static final String ACCOUNT_SID = "AC0002e7c9fb46359fc7c0884c8313a819";
    private static final String AUTH_TOKEN = "f360ede7d59e486841fb22e444801015";

    // Twilio phone number
    private static final String TWILIO_NUMBER = "+12563339571";

    static {
        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
    }

    public static void sendSMS(String recipientPhoneNumber, String message) {
        Message.creator(
                        new PhoneNumber(recipientPhoneNumber),
                        new PhoneNumber(TWILIO_NUMBER),
                        message)
                .create();
    }
}
