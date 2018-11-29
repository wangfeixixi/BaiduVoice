//package wangfeixixi.com.bdvoice;
//
//import android.content.Context;
//import android.os.Handler;
//import android.os.Message;
//import android.util.Pair;
//
//import com.baidu.tts.chainofresponsibility.logger.LoggerProxy;
//import com.baidu.tts.client.SpeechSynthesizer;
//import com.baidu.tts.client.SpeechSynthesizerListener;
//import com.baidu.tts.client.TtsMode;
//
//import java.io.IOException;
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.Map;
//
//import wangfeixixi.com.bdvoice.control.InitConfig;
//import wangfeixixi.com.bdvoice.control.MySyntherizer;
//import wangfeixixi.com.bdvoice.control.NonBlockSyntherizer;
//import wangfeixixi.com.bdvoice.listener.UiMessageListener;
//import wangfeixixi.com.bdvoice.util.AutoCheck;
//import wangfeixixi.com.bdvoice.util.OfflineResource;
//
//public class BaiduVoice {
//    // TtsMode.MIX; 离在线融合，在线优先； TtsMode.ONLINE 纯在线； 没有纯离线
//    public static TtsMode ttsMode = TtsMode.MIX;
//
//    // 离线发音选择，VOICE_FEMALE即为离线女声发音。
//    // assets目录下bd_etts_common_speech_m15_mand_eng_high_am-mix_v3.0.0_20170505.dat为离线男声模型；
//    // assets目录下bd_etts_common_speech_f7_mand_eng_high_am-mix_v3.0.0_20170512.dat为离线女声模型
//    public static String offlineVoice = OfflineResource.VOICE_MALE;
//
//    // ===============初始化参数设置完毕，更多合成参数请至getParams()方法中设置 =================
//
//    // 主控制类，所有合成控制方法从这个类开始
//    public static MySyntherizer synthesizer;
//
////    public static String DESC = "请先看完说明。之后点击“合成并播放”按钮即可正常测试。\n"
////            + "测试离线合成功能需要首次联网。\n"
////            + "纯在线请修改代码里ttsMode为TtsMode.ONLINE， 没有纯离线。\n"
////            + "本Demo的默认参数设置为wifi情况下在线合成, 其它网络（包括4G）使用离线合成。 在线普通女声发音，离线男声发音.\n"
////            + "合成可以多次调用，SDK内部有缓存队列，会依次完成。\n\n";
//
////    private static final String TAG = "SynthActivity";
//
//    /**
//     * 初始化引擎，需要的参数均在InitConfig类里
//     * <p>
//     * DEMO中提供了3个SpeechSynthesizerListener的实现
//     * MessageListener 仅仅用log.i记录日志，在logcat中可以看见
//     * UiMessageListener 在MessageListener的基础上，对handler发送消息，实现UI的文字更新
//     * FileSaveListener 在UiMessageListener的基础上，使用 onSynthesizeDataArrived回调，获取音频流
//     */
//    public static void initialTts(Context context,Handler handler, String appId, String appKey, String secretKey) {
//        mainHandler = handler;
//        LoggerProxy.printable(true); // 日志打印在logcat中
//        // 设置初始化参数
//        // 此处可以改为 含有您业务逻辑的SpeechSynthesizerListener的实现类
//        SpeechSynthesizerListener listener = new UiMessageListener(mainHandler);
//
//        Map<String, String> params = getParams(context);
//
//
//        // appId appKey secretKey 网站上您申请的应用获取。注意使用离线合成功能的话，需要应用中填写您app的包名。包名在build.gradle中获取。
//        InitConfig initConfig = new InitConfig(appId, appKey, secretKey, ttsMode, params, listener);
//
//        // 如果您集成中出错，请将下面一段代码放在和demo中相同的位置，并复制InitConfig 和 AutoCheck到您的项目中
//        // 上线时请删除AutoCheck的调用
//        AutoCheck.getInstance(context).check(initConfig, new Handler() {
//            @Override
//            public void handleMessage(Message msg) {
//                if (msg.what == 100) {
//                    AutoCheck autoCheck = (AutoCheck) msg.obj;
//                    synchronized (autoCheck) {
//                        String message = autoCheck.obtainDebugMessage();
//                        toPrint(message); // 可以用下面一行替代，在logcat中查看代码
//                        // Log.w("AutoCheckMessage", message);
//                    }
//                }
//            }
//
//        });
//        synthesizer = new NonBlockSyntherizer(context, initConfig, mainHandler); // 此处可以改为MySyntherizer 了解调用过程
//    }
//
//    public static Handler mainHandler;
//
//    public static void toPrint(String str) {
//        Message msg = Message.obtain();
//        msg.obj = str;
//        mainHandler.sendMessage(msg);
//    }
//
//    /**
//     * 合成的参数，可以初始化时填写，也可以在合成前设置。
//     *
//     * @return
//     */
//    public static Map<String, String> getParams(Context context) {
//        Map<String, String> params = new HashMap<String, String>();
//        // 以下参数均为选填
//        // 设置在线发声音人： 0 普通女声（默认） 1 普通男声 2 特别男声 3 情感男声<度逍遥> 4 情感儿童声<度丫丫>
//        params.put(SpeechSynthesizer.PARAM_SPEAKER, "0");
//        // 设置合成的音量，0-9 ，默认 5
//        params.put(SpeechSynthesizer.PARAM_VOLUME, "9");
//        // 设置合成的语速，0-9 ，默认 5
//        params.put(SpeechSynthesizer.PARAM_SPEED, "5");
//        // 设置合成的语调，0-9 ，默认 5
//        params.put(SpeechSynthesizer.PARAM_PITCH, "5");
//
//        params.put(SpeechSynthesizer.PARAM_MIX_MODE, SpeechSynthesizer.MIX_MODE_DEFAULT);
//        // 该参数设置为TtsMode.MIX生效。即纯在线模式不生效。
//        // MIX_MODE_DEFAULT 默认 ，wifi状态下使用在线，非wifi离线。在线状态下，请求超时6s自动转离线
//        // MIX_MODE_HIGH_SPEED_SYNTHESIZE_WIFI wifi状态下使用在线，非wifi离线。在线状态下， 请求超时1.2s自动转离线
//        // MIX_MODE_HIGH_SPEED_NETWORK ， 3G 4G wifi状态下使用在线，其它状态离线。在线状态下，请求超时1.2s自动转离线
//        // MIX_MODE_HIGH_SPEED_SYNTHESIZE, 2G 3G 4G wifi状态下使用在线，其它状态离线。在线状态下，请求超时1.2s自动转离线
//
//        // 离线资源文件， 从assets目录中复制到临时目录，需要在initTTs方法前完成
//        OfflineResource offlineResource = createOfflineResource(context, offlineVoice);
//        // 声学模型文件路径 (离线引擎使用), 请确认下面两个文件存在
//        params.put(SpeechSynthesizer.PARAM_TTS_TEXT_MODEL_FILE, offlineResource.getTextFilename());
//        params.put(SpeechSynthesizer.PARAM_TTS_SPEECH_MODEL_FILE,
//                offlineResource.getModelFilename());
//        return params;
//    }
//
//    public static OfflineResource createOfflineResource(Context context, String voiceType) {
//        OfflineResource offlineResource = null;
//        try {
//            offlineResource = new OfflineResource(context, voiceType);
//        } catch (IOException e) {
//            // IO 错误自行处理
//            e.printStackTrace();
//            toPrint("【error】:copy files from assets failed." + e.getMessage());
//        }
//        return offlineResource;
//    }
//
//    /**
//     * speak 实际上是调用 synthesize后，获取音频流，然后播放。
//     * 获取音频流的方式见SaveFileActivity及FileSaveListener
//     * 需要合成的文本text的长度不能超过1024个GBK字节。
//     */
//    public static void speak(String text) {
//        // 合成前可以修改参数：
//        // Map<String, String> params = getParams();
//        // synthesizer.setParams(params);
//        int result = synthesizer.speak(text);
//        checkResult(result, "speak");
//    }
//
//    /**
//     * 合成但是不播放，
//     * 音频流保存为文件的方法可以参见SaveFileActivity及FileSaveListener
//     */
//    public void synthesize(String text) {
//        int result = synthesizer.synthesize(text);
//        checkResult(result, "synthesize");
//    }
//
//    /**
//     * 批量播放
//     */
//    public void batchSpeak(ArrayList<Pair<String, String>> arrayList) {
////        List<Pair<String, String>> texts = new ArrayList<Pair<String, String>>();
////        texts.add(new Pair<String, String>("开始批量播放，", "a0"));
////        texts.add(new Pair<String, String>("123456，", "a1"));
////        texts.add(new Pair<String, String>("欢迎使用百度语音，，，", "a2"));
////        texts.add(new Pair<String, String>("重(chong2)量这个是多音字示例", "a3"));
//        int result = synthesizer.batchSpeak(arrayList);
//        checkResult(result, "batchSpeak");
//    }
//
//    /**
//     * 切换离线发音。注意需要添加额外的判断：引擎在合成时该方法不能调用
//     */
//    public void loadModel(Context context, String mode) {
//        offlineVoice = mode;
//        OfflineResource offlineResource = createOfflineResource(context, offlineVoice);
//        toPrint("切换离线语音：" + offlineResource.getModelFilename());
//        int result = synthesizer.loadModel(offlineResource.getModelFilename(), offlineResource.getTextFilename());
//        checkResult(result, "loadModel");
//    }
//
//    public static void checkResult(int result, String method) {
//        if (result != 0) {
//            toPrint("error code :" + result + " method:" + method + ", 错误码文档:http://yuyin.baidu.com/docs/tts/122 ");
//        }
//    }
//
//
//    /**
//     * 暂停播放。仅调用speak后生效
//     */
//    public void pause() {
//        int result = synthesizer.pause();
//        checkResult(result, "pause");
//    }
//
//    /**
//     * 继续播放。仅调用speak后生效，调用pause生效
//     */
//    public void resume() {
//        int result = synthesizer.resume();
//        checkResult(result, "resume");
//    }
//
//    /*
//     * 停止合成引擎。即停止播放，合成，清空内部合成队列。
//     */
//    public void stop() {
//        int result = synthesizer.stop();
//        checkResult(result, "stop");
//    }
//
//
////    @Override
////    public void onDestroy() {
////        synthesizer.release();
////        Log.i(TAG, "释放资源成功");
////        super.onDestroy();
////    }
//}
