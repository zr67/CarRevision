package com.example.carrevision.util;

import com.example.carrevision.R;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Enum Status class used to define the revision's status
 */
public enum Status {
    AWAITING(0),
    ONGOING(1),
    FINISHED(2),
    CANCELED(3);

    private final int value;
    private static Map map = new HashMap();

    /**
     * Enum constructor
     * @param value
     */
    Status(int value) {
        this.value = value;
    }

    /**
     * Static constructor to get all enum values in a map
     */
    static {
        for (Status status : Status.values()) {
            map.put(status.value, status);
        }
    }

    /**
     * Gets the Status value of an integer
     * @param status Integer to convert in a status
     * @return Status value of the integer
     */
    public static Status valueOf(int status) {
        return (Status) map.get(status);
    }

    /**
     * Gets the integer value of the status
     * @return Integer value of the status
     */
    public int getValue() {
        return this.value;
    }

    /**
     * Gets all the status
     * @return All the status
     */
    public static List<Status> getAllStatus() {
        return Arrays.asList(Status.AWAITING, Status.ONGOING, Status.FINISHED, Status.CANCELED);
    }

    /**
     * Gets the resource associated with the status
     * @return Resource associated with the status
     */
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
