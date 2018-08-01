package com.wifi.vivoguide.event;

import android.content.Intent;

import java.lang.ref.SoftReference;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class SystemEvent {
    private static Map<SystemEventConst, ArrayList<SoftReference<EventListener>>> mEventMap = new HashMap<>();


    public static void release() {
        if (mEventMap != null) {
            for (Map.Entry<SystemEventConst, ArrayList<SoftReference<EventListener>>> entry : mEventMap.entrySet()) {
                if (entry.getValue() != null) {
                    entry.getValue().clear();
                }
            }
            mEventMap.clear();
            mEventMap = null;
        }
    }

    public interface EventListener {
        void onEvent(SystemEventConst eventType, Intent data);
    }

    public synchronized static boolean addListener(SystemEventConst eventType, EventListener eventListener) {
        if (mEventMap == null) {
            return false;
        }
        ArrayList<SoftReference<EventListener>> list = mEventMap.get(eventType);
        if (list == null) {
            list = new ArrayList<>();
        }

        for (SoftReference<EventListener> softListener : list) {
            if (softListener != null) {
                EventListener sEventListener = softListener.get();
                if (sEventListener != null && sEventListener == eventListener) {
                    return false;
                }
            }
        }

        SoftReference<EventListener> listener = new SoftReference<>(eventListener);
        list.add(listener);
        mEventMap.put(eventType, list);
        return true;
    }

    public synchronized static void removeListener(SystemEventConst eventType, EventListener listener) {
        ArrayList<SoftReference<EventListener>> list = mEventMap.get(eventType);
        if (null == list) {
            return;
        }
        for (int i = 0; i < list.size(); i++) {
            EventListener l = list.get(i).get();
            if (l == listener) {
                list.remove(i);
                break;
            }
        }
    }

    public synchronized static void removeListener(SystemEventConst eventType) {
        mEventMap.remove(eventType);
    }

    public synchronized static void fireEvent(SystemEventConst eventType, Intent data) {
        ArrayList<SoftReference<EventListener>> list = mEventMap.get(eventType);
        if (list != null) {
            for (int i = 0; i < list.size(); i++) {
                EventListener listener = list.get(i).get();
                if (listener != null) {
                    listener.onEvent(eventType, data);
                }
            }
        }
    }

    public synchronized static void fireEvent(SystemEventConst eventType) {
        fireEvent(eventType, null);
    }
}
