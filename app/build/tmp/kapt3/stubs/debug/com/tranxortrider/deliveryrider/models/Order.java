package com.tranxortrider.deliveryrider.models;

/**
 * Data class representing a delivery order
 */
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000>\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0002\b\b\n\u0002\u0010 \n\u0000\n\u0002\u0010\u0006\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0002\bT\n\u0002\u0010\u000b\n\u0002\b\u0004\b\u0086\b\u0018\u00002\u00020\u0001B\u00a7\u0002\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003\u0012\u0006\u0010\u0005\u001a\u00020\u0003\u0012\u0006\u0010\u0006\u001a\u00020\u0003\u0012\u0006\u0010\u0007\u001a\u00020\u0003\u0012\u0006\u0010\b\u001a\u00020\u0003\u0012\u0006\u0010\t\u001a\u00020\u0003\u0012\u0006\u0010\n\u001a\u00020\u0003\u0012\f\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\u00010\f\u0012\u0006\u0010\r\u001a\u00020\u000e\u0012\u0006\u0010\u000f\u001a\u00020\u000e\u0012\u0006\u0010\u0010\u001a\u00020\u0011\u0012\u0006\u0010\u0012\u001a\u00020\u0003\u0012\n\b\u0002\u0010\u0013\u001a\u0004\u0018\u00010\u0003\u0012\u0006\u0010\u0014\u001a\u00020\u000e\u0012\u0006\u0010\u0015\u001a\u00020\u0016\u0012\u0006\u0010\u0017\u001a\u00020\u0018\u0012\n\b\u0002\u0010\u0019\u001a\u0004\u0018\u00010\u0018\u0012\n\b\u0002\u0010\u001a\u001a\u0004\u0018\u00010\u0018\u0012\n\b\u0002\u0010\u001b\u001a\u0004\u0018\u00010\u0018\u0012\n\b\u0002\u0010\u001c\u001a\u0004\u0018\u00010\u0018\u0012\n\b\u0002\u0010\u001d\u001a\u0004\u0018\u00010\u0003\u0012\n\b\u0002\u0010\u001e\u001a\u0004\u0018\u00010\u000e\u0012\n\b\u0002\u0010\u001f\u001a\u0004\u0018\u00010\u000e\u0012\n\b\u0002\u0010 \u001a\u0004\u0018\u00010\u000e\u0012\n\b\u0002\u0010!\u001a\u0004\u0018\u00010\u000e\u0012\n\b\u0002\u0010\"\u001a\u0004\u0018\u00010\u000e\u0012\n\b\u0002\u0010#\u001a\u0004\u0018\u00010\u000e\u0012\n\b\u0002\u0010$\u001a\u0004\u0018\u00010\u0003\u00a2\u0006\u0002\u0010%J\t\u0010M\u001a\u00020\u0003H\u00c6\u0003J\t\u0010N\u001a\u00020\u000eH\u00c6\u0003J\t\u0010O\u001a\u00020\u000eH\u00c6\u0003J\t\u0010P\u001a\u00020\u0011H\u00c6\u0003J\t\u0010Q\u001a\u00020\u0003H\u00c6\u0003J\u000b\u0010R\u001a\u0004\u0018\u00010\u0003H\u00c6\u0003J\t\u0010S\u001a\u00020\u000eH\u00c6\u0003J\t\u0010T\u001a\u00020\u0016H\u00c6\u0003J\t\u0010U\u001a\u00020\u0018H\u00c6\u0003J\u000b\u0010V\u001a\u0004\u0018\u00010\u0018H\u00c6\u0003J\u000b\u0010W\u001a\u0004\u0018\u00010\u0018H\u00c6\u0003J\t\u0010X\u001a\u00020\u0003H\u00c6\u0003J\u000b\u0010Y\u001a\u0004\u0018\u00010\u0018H\u00c6\u0003J\u000b\u0010Z\u001a\u0004\u0018\u00010\u0018H\u00c6\u0003J\u000b\u0010[\u001a\u0004\u0018\u00010\u0003H\u00c6\u0003J\u0010\u0010\\\u001a\u0004\u0018\u00010\u000eH\u00c6\u0003\u00a2\u0006\u0002\u0010/J\u0010\u0010]\u001a\u0004\u0018\u00010\u000eH\u00c6\u0003\u00a2\u0006\u0002\u0010/J\u0010\u0010^\u001a\u0004\u0018\u00010\u000eH\u00c6\u0003\u00a2\u0006\u0002\u0010/J\u0010\u0010_\u001a\u0004\u0018\u00010\u000eH\u00c6\u0003\u00a2\u0006\u0002\u0010/J\u0010\u0010`\u001a\u0004\u0018\u00010\u000eH\u00c6\u0003\u00a2\u0006\u0002\u0010/J\u0010\u0010a\u001a\u0004\u0018\u00010\u000eH\u00c6\u0003\u00a2\u0006\u0002\u0010/J\u000b\u0010b\u001a\u0004\u0018\u00010\u0003H\u00c6\u0003J\t\u0010c\u001a\u00020\u0003H\u00c6\u0003J\t\u0010d\u001a\u00020\u0003H\u00c6\u0003J\t\u0010e\u001a\u00020\u0003H\u00c6\u0003J\t\u0010f\u001a\u00020\u0003H\u00c6\u0003J\t\u0010g\u001a\u00020\u0003H\u00c6\u0003J\t\u0010h\u001a\u00020\u0003H\u00c6\u0003J\u000f\u0010i\u001a\b\u0012\u0004\u0012\u00020\u00010\fH\u00c6\u0003J\u00d0\u0002\u0010j\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00032\b\b\u0002\u0010\u0005\u001a\u00020\u00032\b\b\u0002\u0010\u0006\u001a\u00020\u00032\b\b\u0002\u0010\u0007\u001a\u00020\u00032\b\b\u0002\u0010\b\u001a\u00020\u00032\b\b\u0002\u0010\t\u001a\u00020\u00032\b\b\u0002\u0010\n\u001a\u00020\u00032\u000e\b\u0002\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\u00010\f2\b\b\u0002\u0010\r\u001a\u00020\u000e2\b\b\u0002\u0010\u000f\u001a\u00020\u000e2\b\b\u0002\u0010\u0010\u001a\u00020\u00112\b\b\u0002\u0010\u0012\u001a\u00020\u00032\n\b\u0002\u0010\u0013\u001a\u0004\u0018\u00010\u00032\b\b\u0002\u0010\u0014\u001a\u00020\u000e2\b\b\u0002\u0010\u0015\u001a\u00020\u00162\b\b\u0002\u0010\u0017\u001a\u00020\u00182\n\b\u0002\u0010\u0019\u001a\u0004\u0018\u00010\u00182\n\b\u0002\u0010\u001a\u001a\u0004\u0018\u00010\u00182\n\b\u0002\u0010\u001b\u001a\u0004\u0018\u00010\u00182\n\b\u0002\u0010\u001c\u001a\u0004\u0018\u00010\u00182\n\b\u0002\u0010\u001d\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\u001e\u001a\u0004\u0018\u00010\u000e2\n\b\u0002\u0010\u001f\u001a\u0004\u0018\u00010\u000e2\n\b\u0002\u0010 \u001a\u0004\u0018\u00010\u000e2\n\b\u0002\u0010!\u001a\u0004\u0018\u00010\u000e2\n\b\u0002\u0010\"\u001a\u0004\u0018\u00010\u000e2\n\b\u0002\u0010#\u001a\u0004\u0018\u00010\u000e2\n\b\u0002\u0010$\u001a\u0004\u0018\u00010\u0003H\u00c6\u0001\u00a2\u0006\u0002\u0010kJ\u0013\u0010l\u001a\u00020m2\b\u0010n\u001a\u0004\u0018\u00010\u0001H\u00d6\u0003J\t\u0010o\u001a\u00020\u0016H\u00d6\u0001J\t\u0010p\u001a\u00020\u0003H\u00d6\u0001R\u0013\u0010\u0019\u001a\u0004\u0018\u00010\u0018\u00a2\u0006\b\n\u0000\u001a\u0004\b&\u0010\'R\u0013\u0010$\u001a\u0004\u0018\u00010\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b(\u0010)R\u0013\u0010\u001d\u001a\u0004\u0018\u00010\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b*\u0010)R\u0013\u0010\u001c\u001a\u0004\u0018\u00010\u0018\u00a2\u0006\b\n\u0000\u001a\u0004\b+\u0010\'R\u0011\u0010\u0017\u001a\u00020\u0018\u00a2\u0006\b\n\u0000\u001a\u0004\b,\u0010\'R\u0011\u0010\u0006\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b-\u0010)R\u0015\u0010 \u001a\u0004\u0018\u00010\u000e\u00a2\u0006\n\n\u0002\u00100\u001a\u0004\b.\u0010/R\u0015\u0010!\u001a\u0004\u0018\u00010\u000e\u00a2\u0006\n\n\u0002\u00100\u001a\u0004\b1\u0010/R\u0011\u0010\u0005\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b2\u0010)R\u0011\u0010\u0007\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b3\u0010)R\u0013\u0010\u001b\u001a\u0004\u0018\u00010\u0018\u00a2\u0006\b\n\u0000\u001a\u0004\b4\u0010\'R\u0011\u0010\u000f\u001a\u00020\u000e\u00a2\u0006\b\n\u0000\u001a\u0004\b5\u00106R\u0011\u0010\u0014\u001a\u00020\u000e\u00a2\u0006\b\n\u0000\u001a\u0004\b7\u00106R\u0011\u0010\u0015\u001a\u00020\u0016\u00a2\u0006\b\n\u0000\u001a\u0004\b8\u00109R\u001a\u0010\u0002\u001a\u00020\u0003X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b:\u0010)\"\u0004\b;\u0010<R\u0017\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\u00010\f\u00a2\u0006\b\n\u0000\u001a\u0004\b=\u0010>R\u0015\u0010\u001e\u001a\u0004\u0018\u00010\u000e\u00a2\u0006\n\n\u0002\u00100\u001a\u0004\b?\u0010/R\u0015\u0010\u001f\u001a\u0004\u0018\u00010\u000e\u00a2\u0006\n\n\u0002\u00100\u001a\u0004\b@\u0010/R\u0011\u0010\u0004\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\bA\u0010)R\u0011\u0010\u0012\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\bB\u0010)R\u0013\u0010\u001a\u001a\u0004\u0018\u00010\u0018\u00a2\u0006\b\n\u0000\u001a\u0004\bC\u0010\'R\u0011\u0010\t\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\bD\u0010)R\u0015\u0010\"\u001a\u0004\u0018\u00010\u000e\u00a2\u0006\n\n\u0002\u00100\u001a\u0004\bE\u0010/R\u0015\u0010#\u001a\u0004\u0018\u00010\u000e\u00a2\u0006\n\n\u0002\u00100\u001a\u0004\bF\u0010/R\u0011\u0010\b\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\bG\u0010)R\u0011\u0010\n\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\bH\u0010)R\u0013\u0010\u0013\u001a\u0004\u0018\u00010\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\bI\u0010)R\u0011\u0010\u0010\u001a\u00020\u0011\u00a2\u0006\b\n\u0000\u001a\u0004\bJ\u0010KR\u0011\u0010\r\u001a\u00020\u000e\u00a2\u0006\b\n\u0000\u001a\u0004\bL\u00106\u00a8\u0006q"}, d2 = {"Lcom/tranxortrider/deliveryrider/models/Order;", "", "id", "", "orderId", "customerName", "customerAddress", "customerPhone", "restaurantName", "restaurantAddress", "restaurantPhone", "items", "", "totalAmount", "", "deliveryFee", "status", "Lcom/tranxortrider/deliveryrider/models/OrderStatus;", "paymentMethod", "specialInstructions", "distance", "estimatedDeliveryTime", "", "createdAt", "Ljava/util/Date;", "acceptedAt", "pickedUpAt", "deliveredAt", "canceledAt", "cancelReason", "latitude", "longitude", "customerLatitude", "customerLongitude", "restaurantLatitude", "restaurantLongitude", "batchId", "(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/util/List;DDLcom/tranxortrider/deliveryrider/models/OrderStatus;Ljava/lang/String;Ljava/lang/String;DILjava/util/Date;Ljava/util/Date;Ljava/util/Date;Ljava/util/Date;Ljava/util/Date;Ljava/lang/String;Ljava/lang/Double;Ljava/lang/Double;Ljava/lang/Double;Ljava/lang/Double;Ljava/lang/Double;Ljava/lang/Double;Ljava/lang/String;)V", "getAcceptedAt", "()Ljava/util/Date;", "getBatchId", "()Ljava/lang/String;", "getCancelReason", "getCanceledAt", "getCreatedAt", "getCustomerAddress", "getCustomerLatitude", "()Ljava/lang/Double;", "Ljava/lang/Double;", "getCustomerLongitude", "getCustomerName", "getCustomerPhone", "getDeliveredAt", "getDeliveryFee", "()D", "getDistance", "getEstimatedDeliveryTime", "()I", "getId", "setId", "(Ljava/lang/String;)V", "getItems", "()Ljava/util/List;", "getLatitude", "getLongitude", "getOrderId", "getPaymentMethod", "getPickedUpAt", "getRestaurantAddress", "getRestaurantLatitude", "getRestaurantLongitude", "getRestaurantName", "getRestaurantPhone", "getSpecialInstructions", "getStatus", "()Lcom/tranxortrider/deliveryrider/models/OrderStatus;", "getTotalAmount", "component1", "component10", "component11", "component12", "component13", "component14", "component15", "component16", "component17", "component18", "component19", "component2", "component20", "component21", "component22", "component23", "component24", "component25", "component26", "component27", "component28", "component29", "component3", "component4", "component5", "component6", "component7", "component8", "component9", "copy", "(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/util/List;DDLcom/tranxortrider/deliveryrider/models/OrderStatus;Ljava/lang/String;Ljava/lang/String;DILjava/util/Date;Ljava/util/Date;Ljava/util/Date;Ljava/util/Date;Ljava/util/Date;Ljava/lang/String;Ljava/lang/Double;Ljava/lang/Double;Ljava/lang/Double;Ljava/lang/Double;Ljava/lang/Double;Ljava/lang/Double;Ljava/lang/String;)Lcom/tranxortrider/deliveryrider/models/Order;", "equals", "", "other", "hashCode", "toString", "app_debug"})
public final class Order {
    @org.jetbrains.annotations.NotNull()
    private java.lang.String id;
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String orderId = null;
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String customerName = null;
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String customerAddress = null;
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String customerPhone = null;
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String restaurantName = null;
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String restaurantAddress = null;
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String restaurantPhone = null;
    @org.jetbrains.annotations.NotNull()
    private final java.util.List<java.lang.Object> items = null;
    private final double totalAmount = 0.0;
    private final double deliveryFee = 0.0;
    @org.jetbrains.annotations.NotNull()
    private final com.tranxortrider.deliveryrider.models.OrderStatus status = null;
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String paymentMethod = null;
    @org.jetbrains.annotations.Nullable()
    private final java.lang.String specialInstructions = null;
    private final double distance = 0.0;
    private final int estimatedDeliveryTime = 0;
    @org.jetbrains.annotations.NotNull()
    private final java.util.Date createdAt = null;
    @org.jetbrains.annotations.Nullable()
    private final java.util.Date acceptedAt = null;
    @org.jetbrains.annotations.Nullable()
    private final java.util.Date pickedUpAt = null;
    @org.jetbrains.annotations.Nullable()
    private final java.util.Date deliveredAt = null;
    @org.jetbrains.annotations.Nullable()
    private final java.util.Date canceledAt = null;
    @org.jetbrains.annotations.Nullable()
    private final java.lang.String cancelReason = null;
    @org.jetbrains.annotations.Nullable()
    private final java.lang.Double latitude = null;
    @org.jetbrains.annotations.Nullable()
    private final java.lang.Double longitude = null;
    @org.jetbrains.annotations.Nullable()
    private final java.lang.Double customerLatitude = null;
    @org.jetbrains.annotations.Nullable()
    private final java.lang.Double customerLongitude = null;
    @org.jetbrains.annotations.Nullable()
    private final java.lang.Double restaurantLatitude = null;
    @org.jetbrains.annotations.Nullable()
    private final java.lang.Double restaurantLongitude = null;
    @org.jetbrains.annotations.Nullable()
    private final java.lang.String batchId = null;
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String component1() {
        return null;
    }
    
    public final double component10() {
        return 0.0;
    }
    
    public final double component11() {
        return 0.0;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.tranxortrider.deliveryrider.models.OrderStatus component12() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String component13() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String component14() {
        return null;
    }
    
    public final double component15() {
        return 0.0;
    }
    
    public final int component16() {
        return 0;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.Date component17() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.util.Date component18() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.util.Date component19() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String component2() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.util.Date component20() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.util.Date component21() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String component22() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Double component23() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Double component24() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Double component25() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Double component26() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Double component27() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Double component28() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String component29() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String component3() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String component4() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String component5() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String component6() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String component7() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String component8() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.List<java.lang.Object> component9() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.tranxortrider.deliveryrider.models.Order copy(@org.jetbrains.annotations.NotNull()
    java.lang.String id, @org.jetbrains.annotations.NotNull()
    java.lang.String orderId, @org.jetbrains.annotations.NotNull()
    java.lang.String customerName, @org.jetbrains.annotations.NotNull()
    java.lang.String customerAddress, @org.jetbrains.annotations.NotNull()
    java.lang.String customerPhone, @org.jetbrains.annotations.NotNull()
    java.lang.String restaurantName, @org.jetbrains.annotations.NotNull()
    java.lang.String restaurantAddress, @org.jetbrains.annotations.NotNull()
    java.lang.String restaurantPhone, @org.jetbrains.annotations.NotNull()
    java.util.List<? extends java.lang.Object> items, double totalAmount, double deliveryFee, @org.jetbrains.annotations.NotNull()
    com.tranxortrider.deliveryrider.models.OrderStatus status, @org.jetbrains.annotations.NotNull()
    java.lang.String paymentMethod, @org.jetbrains.annotations.Nullable()
    java.lang.String specialInstructions, double distance, int estimatedDeliveryTime, @org.jetbrains.annotations.NotNull()
    java.util.Date createdAt, @org.jetbrains.annotations.Nullable()
    java.util.Date acceptedAt, @org.jetbrains.annotations.Nullable()
    java.util.Date pickedUpAt, @org.jetbrains.annotations.Nullable()
    java.util.Date deliveredAt, @org.jetbrains.annotations.Nullable()
    java.util.Date canceledAt, @org.jetbrains.annotations.Nullable()
    java.lang.String cancelReason, @org.jetbrains.annotations.Nullable()
    java.lang.Double latitude, @org.jetbrains.annotations.Nullable()
    java.lang.Double longitude, @org.jetbrains.annotations.Nullable()
    java.lang.Double customerLatitude, @org.jetbrains.annotations.Nullable()
    java.lang.Double customerLongitude, @org.jetbrains.annotations.Nullable()
    java.lang.Double restaurantLatitude, @org.jetbrains.annotations.Nullable()
    java.lang.Double restaurantLongitude, @org.jetbrains.annotations.Nullable()
    java.lang.String batchId) {
        return null;
    }
    
    @java.lang.Override()
    public boolean equals(@org.jetbrains.annotations.Nullable()
    java.lang.Object other) {
        return false;
    }
    
    @java.lang.Override()
    public int hashCode() {
        return 0;
    }
    
    @java.lang.Override()
    @org.jetbrains.annotations.NotNull()
    public java.lang.String toString() {
        return null;
    }
    
    public Order(@org.jetbrains.annotations.NotNull()
    java.lang.String id, @org.jetbrains.annotations.NotNull()
    java.lang.String orderId, @org.jetbrains.annotations.NotNull()
    java.lang.String customerName, @org.jetbrains.annotations.NotNull()
    java.lang.String customerAddress, @org.jetbrains.annotations.NotNull()
    java.lang.String customerPhone, @org.jetbrains.annotations.NotNull()
    java.lang.String restaurantName, @org.jetbrains.annotations.NotNull()
    java.lang.String restaurantAddress, @org.jetbrains.annotations.NotNull()
    java.lang.String restaurantPhone, @org.jetbrains.annotations.NotNull()
    java.util.List<? extends java.lang.Object> items, double totalAmount, double deliveryFee, @org.jetbrains.annotations.NotNull()
    com.tranxortrider.deliveryrider.models.OrderStatus status, @org.jetbrains.annotations.NotNull()
    java.lang.String paymentMethod, @org.jetbrains.annotations.Nullable()
    java.lang.String specialInstructions, double distance, int estimatedDeliveryTime, @org.jetbrains.annotations.NotNull()
    java.util.Date createdAt, @org.jetbrains.annotations.Nullable()
    java.util.Date acceptedAt, @org.jetbrains.annotations.Nullable()
    java.util.Date pickedUpAt, @org.jetbrains.annotations.Nullable()
    java.util.Date deliveredAt, @org.jetbrains.annotations.Nullable()
    java.util.Date canceledAt, @org.jetbrains.annotations.Nullable()
    java.lang.String cancelReason, @org.jetbrains.annotations.Nullable()
    java.lang.Double latitude, @org.jetbrains.annotations.Nullable()
    java.lang.Double longitude, @org.jetbrains.annotations.Nullable()
    java.lang.Double customerLatitude, @org.jetbrains.annotations.Nullable()
    java.lang.Double customerLongitude, @org.jetbrains.annotations.Nullable()
    java.lang.Double restaurantLatitude, @org.jetbrains.annotations.Nullable()
    java.lang.Double restaurantLongitude, @org.jetbrains.annotations.Nullable()
    java.lang.String batchId) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getId() {
        return null;
    }
    
    public final void setId(@org.jetbrains.annotations.NotNull()
    java.lang.String p0) {
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getOrderId() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getCustomerName() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getCustomerAddress() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getCustomerPhone() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getRestaurantName() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getRestaurantAddress() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getRestaurantPhone() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.List<java.lang.Object> getItems() {
        return null;
    }
    
    public final double getTotalAmount() {
        return 0.0;
    }
    
    public final double getDeliveryFee() {
        return 0.0;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.tranxortrider.deliveryrider.models.OrderStatus getStatus() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getPaymentMethod() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String getSpecialInstructions() {
        return null;
    }
    
    public final double getDistance() {
        return 0.0;
    }
    
    public final int getEstimatedDeliveryTime() {
        return 0;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.Date getCreatedAt() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.util.Date getAcceptedAt() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.util.Date getPickedUpAt() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.util.Date getDeliveredAt() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.util.Date getCanceledAt() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String getCancelReason() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Double getLatitude() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Double getLongitude() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Double getCustomerLatitude() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Double getCustomerLongitude() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Double getRestaurantLatitude() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Double getRestaurantLongitude() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String getBatchId() {
        return null;
    }
}