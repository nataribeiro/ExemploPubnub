package br.com.natanaelribeiro.www.exemplopubnub;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

import com.pubnub.api.Callback;
import com.pubnub.api.Pubnub;
import com.pubnub.api.PubnubException;

public class MainActivity extends AppCompatActivity {

    private ImageView image;
    public final String TAG = "TEST_PUBNUB";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        image = (ImageView) findViewById(R.id.image);

        pubNub();
    }

    public void pubNub(){
        final Pubnub pubnub = new Pubnub("pub-c-2e25ef2a-b358-437e-938d-cca47b438d56", "sub-c-6814b2de-4934-11e6-85a4-0619f8945a4f");

        try {
            pubnub.subscribe("my_channel", new Callback() {
                @Override
                public void successCallback(String channel, Object message) {
                    if(message.toString().contains("Desligou")){
                        image.post(new Runnable() {
                            @Override
                            public void run() {
                                image.setImageResource(R.drawable.lampada_apagada);
                            }
                        });
                    } else if (message.toString().contains("Ligou")){
                        image.post(new Runnable() {
                            @Override
                            public void run() {
                                image.setImageResource(R.drawable.lampada_acessa);
                            }
                        });
                    }
                }

                @Override
                public void connectCallback(String channel, Object message) { }

                @Override
                public void reconnectCallback(String channel, Object message) { }

                @Override
                public void disconnectCallback(String channel, Object message) { }
            });
        }
        catch (PubnubException e){
            System.out.println(e.getMessage());
        }
    }
}
