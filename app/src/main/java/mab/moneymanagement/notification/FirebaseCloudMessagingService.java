package mab.moneymanagement.notification;


import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import java.util.Map;


public class FirebaseCloudMessagingService extends FirebaseMessagingService {

    private static final String TAG = "MyFireBaseMsgService";


    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);

       if (remoteMessage.getData().size() >0 ){

            try {
                Map<String , String> map =  remoteMessage.getData();
                sendPushNotification(map);

            } catch (Exception e) {
                System.out.println(e.getMessage());
            }

          }
         }



    private void sendPushNotification(Map map) {

    }



}
