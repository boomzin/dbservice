package ru.mediatel.icc.dbservice.common.data;

import java.util.Collection;

public class AssertionConcern {
    protected void assertNotNull(Object target, String message) {
        if(target == null) {
            throw new IllegalArgumentException(message);
        }
    }

    protected void assertNotEmpty(String target, String message) {
        if(target == null || target.length() == 0) {
            throw new IllegalArgumentException(message);
        }
    }

    protected void assertNotEmpty(Collection<?> target, String message) {
        if(target == null || target.isEmpty()) {
            throw new IllegalArgumentException(message);
        }
    }

    protected void assertTrue(boolean target, String message) {
        if(!target) {
            throw new IllegalArgumentException(message);
        }
    }

    protected void assertMin(int min, int target, String message) {
        if(target < min) {
            throw new IllegalArgumentException(message);
        }
    }

    protected void assertMin(long min, long target, String message) {
        if(target < min) {
            throw new IllegalArgumentException(message);
        }
    }

    protected void assertEquals(Object arg1, Object arg2, String message) {
        if(!arg1.equals(arg2)) {
            throw new IllegalArgumentException(message);
        }
    }

    protected void assertMax(int max, int target, String message) {
        if(target > max) {
            throw new IllegalArgumentException(message);
        }
    }
}
