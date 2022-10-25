package com.example.carrevision.util;

import com.example.carrevision.R;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public enum Status {
    AWAITING(0),
    ONGOING(1),
    FINISHED(2),
    CANCELED(3);

    private final int value;
    private static Map map = new HashMap();

    Status(int value) {
        this.value = value;
    }

    static {
        for (Status status : Status.values()) {
            map.put(status.value, status);
        }
    }
    public static Status valueOf(int status) {
        return (Status) map.get(status);
    }
    public int getValue() {
        return this.value;
    }
    public static List<Status> getAllStatus() {
        return Arrays.asList(Status.AWAITING, Status.ONGOING, Status.FINISHED, Status.CANCELED);
    }
    public int getStringResourceId() {
        switch (this) {
            case AWAITING:
                return R.string.stat_awaiting;
            case ONGOING:
                return R.string.stat_ongoing;
            case FINISHED:
                return R.string.stat_finished;
            case CANCELED:
                return R.string.stat_canceled;
        }
        return 0;
    }
}
