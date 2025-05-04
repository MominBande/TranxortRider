package com.tranxortrider.deliveryrider.models;

/**
 * Enum representing different order statuses in the delivery lifecycle
 */
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0002\b\n\b\u0086\u0081\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00000\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002j\u0002\b\u0003j\u0002\b\u0004j\u0002\b\u0005j\u0002\b\u0006j\u0002\b\u0007j\u0002\b\bj\u0002\b\tj\u0002\b\n\u00a8\u0006\u000b"}, d2 = {"Lcom/tranxortrider/deliveryrider/models/OrderStatus;", "", "(Ljava/lang/String;I)V", "PENDING", "ASSIGNED", "ACCEPTED", "PICKED_UP", "IN_TRANSIT", "COMPLETED", "CANCELLED", "FAILED", "app_debug"})
public enum OrderStatus {
    /*public static final*/ PENDING /* = new PENDING() */,
    /*public static final*/ ASSIGNED /* = new ASSIGNED() */,
    /*public static final*/ ACCEPTED /* = new ACCEPTED() */,
    /*public static final*/ PICKED_UP /* = new PICKED_UP() */,
    /*public static final*/ IN_TRANSIT /* = new IN_TRANSIT() */,
    /*public static final*/ COMPLETED /* = new COMPLETED() */,
    /*public static final*/ CANCELLED /* = new CANCELLED() */,
    /*public static final*/ FAILED /* = new FAILED() */;
    
    OrderStatus() {
    }
    
    @org.jetbrains.annotations.NotNull()
    public static kotlin.enums.EnumEntries<com.tranxortrider.deliveryrider.models.OrderStatus> getEntries() {
        return null;
    }
}