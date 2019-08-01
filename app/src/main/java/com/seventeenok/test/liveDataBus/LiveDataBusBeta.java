package com.seventeenok.test.liveDataBus;

import java.util.HashMap;
import java.util.Map;

import androidx.lifecycle.MutableLiveData;

public class LiveDataBusBeta {
    private final Map<String, MutableLiveData<Object>> bus;

    private LiveDataBusBeta() {
        bus = new HashMap<>();
    }

    public static LiveDataBusBeta get() {
        return SingleHolder.DATA_BUS;
    }

    private static class SingleHolder {
        private static final LiveDataBusBeta DATA_BUS = new LiveDataBusBeta();
    }

    public <T> MutableLiveData<Object> with(String tag, Class<T> type) {
        if (!bus.containsKey(tag)) {
            bus.put(tag, new MutableLiveData<>());
        }
        return bus.get(tag);
    }

    public MutableLiveData<Object> with(String tag) {   
        return with(tag, Object.class);
    }
}
