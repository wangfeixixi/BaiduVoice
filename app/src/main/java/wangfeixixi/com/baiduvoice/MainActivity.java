package wangfeixixi.com.baiduvoice;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import wangfeixixi.com.bdvoice.VoiceUtil;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        VoiceUtil.getInstance().initKey(this, "14965484", "v4RMPNcaeHB53pYHEmEBohPw", "T1yulG4Zfq9vDQMPwulWG2AIYXIimNN1");

//        BDV.getInstance().test();
//        BDV.getInstance().init(this, "14978642", "GifbS55fIgKvHDxGUv0x9LzY", "cyXGqI8XczrNhGUFV2nZ35MGcBBwbmKR");

        findViewById(R.id.btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                VoiceUtil.speek("开始了没");
//                BaiduVoice.speak("开始了没");
//                BDV.getInstance().speek("阿斯顿发生发说的");
                VoiceUtil.getInstance().speek("测试开始了啊");
            }
        });

//        try {
//            byte[] buff = new byte[4];
//            DatagramSocket ds = new DatagramSocket(1935);
//            DatagramPacket dp = new DatagramPacket(buff, buff.length);
//            while (true) {
//                ds.receive(dp);//此方法会一直阻塞，直到获取到数据
//                System.out.println(new String(buff));
//            }
//        } catch (IOException e) {
//            System.out.println("e " + e.getMessage());
//            e.printStackTrace();
//
//        }
    }
}
