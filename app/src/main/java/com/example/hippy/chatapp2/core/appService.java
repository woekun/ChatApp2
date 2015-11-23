package com.example.hippy.chatapp2.core;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;

import com.example.hippy.chatapp2.helper.utils.Const;
import com.sinch.android.rtc.ClientRegistration;
import com.sinch.android.rtc.Sinch;
import com.sinch.android.rtc.SinchClient;
import com.sinch.android.rtc.SinchClientListener;
import com.sinch.android.rtc.SinchError;
import com.sinch.android.rtc.calling.Call;
import com.sinch.android.rtc.calling.CallClient;
import com.sinch.android.rtc.calling.CallClientListener;
import com.sinch.android.rtc.messaging.MessageClient;
import com.sinch.android.rtc.video.VideoController;

public class appService extends Service {

    private Intent broadcastIntent = new Intent(Const.ACTION_SINCH_SERVICE);
    private final ServiceInterface serviceInterface = new ServiceInterface();
    private SinchClient sinchClient = null;
    private CallClient callClient = null;

    public appService() {

    }

    @Override
    public void onCreate() {
        super.onCreate();

    }

    private void startSinchClient(String username) {
        sinchClient = Sinch.getSinchClientBuilder()
                .context(this)
                .userId(username)
                .applicationKey(Const.SINCH_APP_KEY)
                .applicationSecret(Const.SINCH_APP_SECRET)
                .environmentHost(Const.SINCH_ENVIRONMENT)
                .build();

        sinchClient.addSinchClientListener(new ClientListener());

        sinchClient.setSupportCalling(true);
        sinchClient.setSupportMessaging(true);
        sinchClient.setSupportActiveConnectionInBackground(true);
        sinchClient.checkManifest();
        sinchClient.startListeningOnActiveConnection();
        sinchClient.start();
    }

    private class ClientListener implements SinchClientListener {

        @Override
        public void onClientStarted(SinchClient client) {
            broadcastIntent.putExtra("success", true);
            sendBroadcast(broadcastIntent);

            client.startListeningOnActiveConnection();

            callClient = client.getCallClient();
            callClient.addCallClientListener(new DefaultCallListener());

        }

        @Override
        public void onClientStopped(SinchClient client) {
            sinchClient = null;
        }

        @Override
        public void onClientFailed(SinchClient client, SinchError sinchError) {
            broadcastIntent.putExtra("success", false);
            sendBroadcast(broadcastIntent);
            sinchClient = null;
        }

        @Override
        public void onRegistrationCredentialsRequired(SinchClient sinchClient, ClientRegistration clientRegistration) {

        }

        @Override
        public void onLogMessage(int i, String s, String s1) {

        }
    }

    private class DefaultCallListener implements CallClientListener {
        @Override
        public void onIncomingCall(CallClient callClient, Call call) {
            Intent intent = new Intent(Const.RECEIVER_CALL);
            intent.putExtra("sender_calling", call.getRemoteUserId());
            sendBroadcast(intent);
        }
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public IBinder onBind(Intent intent) {
        return serviceInterface;
    }

    public class ServiceInterface extends Binder {

        public void sendMessage(String recipientUserId, String textBody) {
            appService.this.sendMessage(recipientUserId, textBody);
        }

        public Call callUser(String recipientUserId) {
            return sinchClient.getCallClient().callUser(recipientUserId);
        }

        public Call getCall(String recipientUserId) {
            return sinchClient.getCallClient().getCall(recipientUserId);
        }

        public MessageClient getMessageClient() {
            return sinchClient.getMessageClient();
        }

        public boolean isSinchClientStarted() {
            return appService.this.isSinchClientStarted();
        }

        public VideoController getVideoController() {
            return sinchClient.getVideoController();
        }
    }

    private void sendMessage(String recipientUserId, String textBody) {
        Commands.sendMessageCommand(recipientUserId, textBody);
    }

    private boolean isSinchClientStarted() {
        return sinchClient != null && sinchClient.isStarted();
    }

    @Override
    public void onDestroy() {
        if (isSinchClientStarted()) {
            sinchClient.terminateGracefully();
        }
        super.onDestroy();
    }
}
