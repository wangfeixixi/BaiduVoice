package wangfeixixi.com.bdvoice;

import android.content.Context;
import android.content.res.AssetManager;
import android.os.Environment;
import android.util.Log;
import android.util.Pair;

import com.baidu.tts.client.SpeechSynthesizeBag;
import com.baidu.tts.client.SpeechSynthesizer;
import com.baidu.tts.client.TtsMode;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;


public class VoiceUtil {
    private VoiceUtil() {

    }

    private static class Instance {
        private static VoiceUtil voiceUtil = new VoiceUtil();
    }

    public static VoiceUtil getInstance() {
        return Instance.voiceUtil;
    }

    private SpeechSynthesizer mSpeechSynthesizer;
    private static String TAG = "voiceutil";


    public void initKey(Context context, String appId, String apiKey, String secretKey) {
// 1. 获取实例
        mSpeechSynthesizer = SpeechSynthesizer.getInstance();
        mSpeechSynthesizer.setContext(context);

//listener是SpeechSynthesizerListener 的实现类，需要实现您自己的业务逻辑。SDK合成后会对这个类的方法进行回调。
//        mSpeechSynthesizer.setSpeechSynthesizerListener(new SpeechSynthesizerListener());

        // 3. 设置appId，appKey.secretKey
        int result = mSpeechSynthesizer.setAppId(appId);
        Log.d(TAG, "setAppid" + result);
        result = mSpeechSynthesizer.setApiKey(apiKey, secretKey);
        Log.d(TAG, "setApiKey" + result);

//        mSpeechSynthesizer.auth(TtsMode.ONLINE);  // 纯在线
        mSpeechSynthesizer.auth(TtsMode.MIX); // 离在线混合

        mSpeechSynthesizer.setParam(SpeechSynthesizer.PARAM_SPEAKER, "0"); // 设置发声的人声音，在线生效
        mSpeechSynthesizer.initTts(TtsMode.MIX); // 初始化离在线混合模式，如果只需要在线合成功能，使用 TtsMode.ONLINE

//        try {
//            mSpeechSynthesizer.loadModel(copyAssetsFile("bd_etts_speech_female.dat"), copyAssetsFile("bd_etts_text.dat"));
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
        mSpeechSynthesizer.setStereoVolume(1.0f, 1.0f);
    }

    public void close() {
        int result = mSpeechSynthesizer.release();
    }

    public void speek(String s) {
        Log.d(TAG, "语音播放：" + s);
        mSpeechSynthesizer.speak(s);
    }


    public int batchSpeak(List<Pair<String, String>> texts) {
        List<SpeechSynthesizeBag> bags = new ArrayList<SpeechSynthesizeBag>();
        for (Pair<String, String> pair : texts) {
            SpeechSynthesizeBag speechSynthesizeBag = new SpeechSynthesizeBag();
            speechSynthesizeBag.setText(pair.first);
            if (pair.second != null) {
                speechSynthesizeBag.setUtteranceId(pair.second);
            }
            bags.add(speechSynthesizeBag);

        }
        return mSpeechSynthesizer.batchSpeak(bags);
    }

    private String copyAssetsFile(Context context, String sourceFilename) throws IOException {
        String destFilename = createTmpDir(context) + "/" + sourceFilename;
        copyFromAssets(context.getAssets(), sourceFilename, destFilename, false);
        Log.i(TAG, "文件复制成功：" + destFilename);
        return destFilename;
    }

    // 创建一个临时目录，用于复制临时文件，如assets目录下的离线资源文件
    private String createTmpDir(Context context) {
        String sampleDir = "baiduTTS";
        String tmpDir = Environment.getExternalStorageDirectory().toString() + "/" + sampleDir;
        if (!makeDir(tmpDir)) {
            tmpDir = context.getExternalFilesDir(sampleDir).getAbsolutePath();
            if (!makeDir(sampleDir)) {
                throw new RuntimeException("create model resources dir failed :" + tmpDir);
            }
        }
        return tmpDir;
    }

    private boolean makeDir(String dirPath) {
        File file = new File(dirPath);
        if (!file.exists()) {
            return file.mkdirs();
        } else {
            return true;
        }
    }

    private void copyFromAssets(AssetManager assets, String source, String dest, boolean isCover)
            throws IOException {
        File file = new File(dest);
        if (isCover || (!isCover && !file.exists())) {
            InputStream is = null;
            FileOutputStream fos = null;
            try {
                is = assets.open(source);
                String path = dest;
                fos = new FileOutputStream(path);
                byte[] buffer = new byte[1024];
                int size = 0;
                while ((size = is.read(buffer, 0, 1024)) >= 0) {
                    fos.write(buffer, 0, size);
                }
            } finally {
                if (fos != null) {
                    try {
                        fos.close();
                    } finally {
                        if (is != null) {
                            is.close();
                        }
                    }
                }
            }
        }
    }
}
