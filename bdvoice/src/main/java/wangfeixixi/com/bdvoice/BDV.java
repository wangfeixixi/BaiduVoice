//package wangfeixixi.com.bdvoice;
//
//import android.content.Context;
//
//import com.baidu.tts.client.SpeechError;
//import com.baidu.tts.client.SpeechSynthesizer;
//import com.baidu.tts.client.SpeechSynthesizerListener;
//import com.baidu.tts.client.TtsMode;
//
//public class BDV {
//
//    private final SpeechSynthesizer mSpeechSynthesizer;
//
//    private BDV() {
//        mSpeechSynthesizer = SpeechSynthesizer.getInstance();
//
//    }
//
//    private static class AAA {
//        private static BDV INSTANCE = new BDV();
//    }
//
//    public static BDV getInstance() {
//        return AAA.INSTANCE;
//    }
//
//    public void init(Context context, String appId, String apiKey, String secreteKey) {
//        mSpeechSynthesizer.setContext(context);
//        mSpeechSynthesizer.setSpeechSynthesizerListener(new SpeechSynthesizerListener() {
//            @Override
//            public void onSynthesizeStart(String s) {
//
//            }
//
//            @Override
//            public void onSynthesizeDataArrived(String s, byte[] bytes, int i) {
//
//            }
//
//            @Override
//            public void onSynthesizeFinish(String s) {
//
//            }
//
//            @Override
//            public void onSpeechStart(String s) {
//
//            }
//
//            @Override
//            public void onSpeechProgressChanged(String s, int i) {
//
//            }
//
//            @Override
//            public void onSpeechFinish(String s) {
//
//            }
//
//            @Override
//            public void onError(String s, SpeechError speechError) {
//
//            }
//        });
//        mSpeechSynthesizer.setAppId(appId);
//        mSpeechSynthesizer.setApiKey(apiKey, secreteKey);
//        mSpeechSynthesizer.auth(TtsMode.MIX); // 离在线混合
//        mSpeechSynthesizer.setParam(SpeechSynthesizer.PARAM_SPEAKER, "0"); // 设置发声的人声音，在线生效
//        mSpeechSynthesizer.initTts(TtsMode.MIX); // 初始化离在线混合模式，如果只需要在线合成功能，使用 TtsMode.ONLINE
//        mSpeechSynthesizer.setStereoVolume (1f, 1f);
//    }
//
//    public void speek(String text) {
//        mSpeechSynthesizer.speak(text);
//    }
//}
