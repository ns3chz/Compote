//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package cn.hu.zc.listener;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;

public class JustListener {
    private JustListener() {
    }

    public static class ListenDelay {
        public ListenDelay() {
        }

        private final int DELAY = 0;
        private JustListener.JustaListener mJustaListener;

        private Handler mDelayListenerHandler = new Handler(Looper.getMainLooper()) {
            public void handleMessage(Message msg) {
                if (msg.what == 0 && ListenDelay.this.mJustaListener != null) {
                    ListenDelay.this.mJustaListener.listen();
                }
                super.handleMessage(msg);
            }
        };

        public static void listen(JustListener.JustaListener listener) {
            if (listener != null) {
                listener.listen();
            }
        }

        public static void listen(long delay, JustListener.JustaListener listener) {
            JustListener.ListenDelay listenDelay = new JustListener.ListenDelay();
            listenDelay.listenDelay(delay, listener);
        }

        public void listenDelay(long delay, JustListener.JustaListener listener) {
            this.mJustaListener = listener;
            this.mDelayListenerHandler.sendEmptyMessageDelayed(0, delay);
        }
    }

    public interface JustaListener {
        void listen();
    }
}
