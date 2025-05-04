package com.tranxortrider.deliveryrider.api;

/**
 * Interface for API endpoints related to authentication
 */
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u009e\u0001\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\bf\u0018\u00002\u00020\u0001J\"\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u00032\b\b\u0001\u0010\u0005\u001a\u00020\u00062\b\b\u0001\u0010\u0007\u001a\u00020\bH\'J\"\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u00040\u00032\b\b\u0001\u0010\u0005\u001a\u00020\u00062\b\b\u0001\u0010\u0007\u001a\u00020\nH\'J\"\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\u00040\u00032\b\b\u0001\u0010\u0005\u001a\u00020\u00062\b\b\u0001\u0010\u0007\u001a\u00020\fH\'J\u0018\u0010\r\u001a\b\u0012\u0004\u0012\u00020\u00040\u00032\b\b\u0001\u0010\u0007\u001a\u00020\u000eH\'J\u0018\u0010\u000f\u001a\b\u0012\u0004\u0012\u00020\u00100\u00032\b\b\u0001\u0010\u0011\u001a\u00020\u0006H\'J,\u0010\u0012\u001a\b\u0012\u0004\u0012\u00020\u00100\u00032\b\b\u0001\u0010\u0011\u001a\u00020\u00062\b\b\u0003\u0010\u0013\u001a\u00020\u00142\b\b\u0003\u0010\u0015\u001a\u00020\u0014H\'J,\u0010\u0016\u001a\b\u0012\u0004\u0012\u00020\u00100\u00032\b\b\u0001\u0010\u0011\u001a\u00020\u00062\b\b\u0003\u0010\u0013\u001a\u00020\u00142\b\b\u0003\u0010\u0015\u001a\u00020\u0014H\'J\u0018\u0010\u0017\u001a\b\u0012\u0004\u0012\u00020\u00180\u00032\b\b\u0001\u0010\u0005\u001a\u00020\u0006H\'J\u0018\u0010\u0019\u001a\b\u0012\u0004\u0012\u00020\u00100\u00032\b\b\u0001\u0010\u0011\u001a\u00020\u0006H\'J\u0018\u0010\u001a\u001a\b\u0012\u0004\u0012\u00020\u001b0\u00032\b\b\u0001\u0010\u0007\u001a\u00020\u001cH\'J\u0018\u0010\u001d\u001a\b\u0012\u0004\u0012\u00020\u001e0\u00032\b\b\u0001\u0010\u001f\u001a\u00020 H\'J\"\u0010!\u001a\b\u0012\u0004\u0012\u00020\u00040\u00032\b\b\u0001\u0010\u0005\u001a\u00020\u00062\b\b\u0001\u0010\u0007\u001a\u00020\bH\'J\"\u0010\"\u001a\b\u0012\u0004\u0012\u00020\u00040\u00032\b\b\u0001\u0010\u0005\u001a\u00020\u00062\b\b\u0001\u0010\u0007\u001a\u00020#H\'J\u0018\u0010$\u001a\b\u0012\u0004\u0012\u00020\u00040\u00032\b\b\u0001\u0010\u0007\u001a\u00020%H\'J\u0018\u0010&\u001a\b\u0012\u0004\u0012\u00020\'0\u00032\b\b\u0001\u0010\u0007\u001a\u00020(H\'J\u0018\u0010)\u001a\b\u0012\u0004\u0012\u00020*0\u00032\b\b\u0001\u0010+\u001a\u00020,H\'J\"\u0010-\u001a\b\u0012\u0004\u0012\u00020\u00040\u00032\b\b\u0001\u0010\u0011\u001a\u00020\u00062\b\b\u0001\u0010\u0007\u001a\u00020.H\'J,\u0010/\u001a\b\u0012\u0004\u0012\u0002000\u00032\b\b\u0001\u00101\u001a\u0002022\b\b\u0001\u00103\u001a\u0002022\b\b\u0001\u00104\u001a\u000202H\'J\u0018\u00105\u001a\b\u0012\u0004\u0012\u00020\u00040\u00032\b\b\u0001\u0010\u0007\u001a\u000206H\'\u00a8\u00067"}, d2 = {"Lcom/tranxortrider/deliveryrider/api/ApiService;", "", "acceptOrder", "Lretrofit2/Call;", "Lcom/tranxortrider/deliveryrider/api/BasicResponse;", "orderId", "", "request", "Lcom/tranxortrider/deliveryrider/api/OrderActionRequest;", "deliverOrder", "Lcom/tranxortrider/deliveryrider/api/OrderDeliverRequest;", "failOrder", "Lcom/tranxortrider/deliveryrider/api/OrderFailRequest;", "forgotPassword", "Lcom/tranxortrider/deliveryrider/api/ForgotPasswordRequest;", "getAssignedOrders", "Lcom/tranxortrider/deliveryrider/api/OrdersResponse;", "riderId", "getCompletedOrders", "page", "", "limit", "getFailedOrders", "getOrderDetails", "Lcom/tranxortrider/deliveryrider/api/OrderDetailsResponse;", "getPendingOrders", "getVerificationStatus", "Lcom/tranxortrider/deliveryrider/api/VerificationStatusResponse;", "Lcom/tranxortrider/deliveryrider/api/VerificationStatusRequest;", "login", "Lcom/tranxortrider/deliveryrider/api/LoginResponse;", "loginRequest", "Lcom/tranxortrider/deliveryrider/api/LoginRequest;", "pickupOrder", "rejectOrder", "Lcom/tranxortrider/deliveryrider/api/OrderRejectRequest;", "resetPassword", "Lcom/tranxortrider/deliveryrider/api/ResetPasswordRequest;", "setupProfile", "Lcom/tranxortrider/deliveryrider/api/SetupProfileResponse;", "Lcom/tranxortrider/deliveryrider/api/SetupProfileRequest;", "signup", "Lcom/tranxortrider/deliveryrider/api/SignupResponse;", "signupRequest", "Lcom/tranxortrider/deliveryrider/api/SignupRequest;", "updateLocation", "Lcom/tranxortrider/deliveryrider/api/LocationUpdateRequest;", "uploadDocument", "Lcom/tranxortrider/deliveryrider/api/DocumentUploadResponse;", "documentType", "Lokhttp3/MultipartBody$Part;", "userId", "document", "verifyCode", "Lcom/tranxortrider/deliveryrider/api/VerifyCodeRequest;", "app_debug"})
public abstract interface ApiService {
    
    /**
     * Login user with email and password
     */
    @retrofit2.http.POST(value = "login")
    @org.jetbrains.annotations.NotNull()
    public abstract retrofit2.Call<com.tranxortrider.deliveryrider.api.LoginResponse> login(@retrofit2.http.Body()
    @org.jetbrains.annotations.NotNull()
    com.tranxortrider.deliveryrider.api.LoginRequest loginRequest);
    
    /**
     * Sign up a new user
     */
    @retrofit2.http.POST(value = "signup")
    @org.jetbrains.annotations.NotNull()
    public abstract retrofit2.Call<com.tranxortrider.deliveryrider.api.SignupResponse> signup(@retrofit2.http.Body()
    @org.jetbrains.annotations.NotNull()
    com.tranxortrider.deliveryrider.api.SignupRequest signupRequest);
    
    /**
     * Request password reset
     */
    @retrofit2.http.POST(value = "forgot-password")
    @org.jetbrains.annotations.NotNull()
    public abstract retrofit2.Call<com.tranxortrider.deliveryrider.api.BasicResponse> forgotPassword(@retrofit2.http.Body()
    @org.jetbrains.annotations.NotNull()
    com.tranxortrider.deliveryrider.api.ForgotPasswordRequest request);
    
    /**
     * Verify verification code sent to email
     */
    @retrofit2.http.POST(value = "verify-code")
    @org.jetbrains.annotations.NotNull()
    public abstract retrofit2.Call<com.tranxortrider.deliveryrider.api.BasicResponse> verifyCode(@retrofit2.http.Body()
    @org.jetbrains.annotations.NotNull()
    com.tranxortrider.deliveryrider.api.VerifyCodeRequest request);
    
    /**
     * Reset password with new password
     */
    @retrofit2.http.POST(value = "reset-password")
    @org.jetbrains.annotations.NotNull()
    public abstract retrofit2.Call<com.tranxortrider.deliveryrider.api.BasicResponse> resetPassword(@retrofit2.http.Body()
    @org.jetbrains.annotations.NotNull()
    com.tranxortrider.deliveryrider.api.ResetPasswordRequest request);
    
    /**
     * Setup user profile
     */
    @retrofit2.http.POST(value = "setup-profile")
    @org.jetbrains.annotations.NotNull()
    public abstract retrofit2.Call<com.tranxortrider.deliveryrider.api.SetupProfileResponse> setupProfile(@retrofit2.http.Body()
    @org.jetbrains.annotations.NotNull()
    com.tranxortrider.deliveryrider.api.SetupProfileRequest request);
    
    /**
     * Upload document for verification
     */
    @retrofit2.http.Multipart()
    @retrofit2.http.POST(value = "upload-document")
    @org.jetbrains.annotations.NotNull()
    public abstract retrofit2.Call<com.tranxortrider.deliveryrider.api.DocumentUploadResponse> uploadDocument(@retrofit2.http.Part()
    @org.jetbrains.annotations.NotNull()
    okhttp3.MultipartBody.Part documentType, @retrofit2.http.Part()
    @org.jetbrains.annotations.NotNull()
    okhttp3.MultipartBody.Part userId, @retrofit2.http.Part()
    @org.jetbrains.annotations.NotNull()
    okhttp3.MultipartBody.Part document);
    
    /**
     * Get verification status
     */
    @retrofit2.http.POST(value = "verification-status")
    @org.jetbrains.annotations.NotNull()
    public abstract retrofit2.Call<com.tranxortrider.deliveryrider.api.VerificationStatusResponse> getVerificationStatus(@retrofit2.http.Body()
    @org.jetbrains.annotations.NotNull()
    com.tranxortrider.deliveryrider.api.VerificationStatusRequest request);
    
    /**
     * Get pending orders
     */
    @retrofit2.http.GET(value = "orders/pending")
    @org.jetbrains.annotations.NotNull()
    public abstract retrofit2.Call<com.tranxortrider.deliveryrider.api.OrdersResponse> getPendingOrders(@retrofit2.http.Query(value = "riderId")
    @org.jetbrains.annotations.NotNull()
    java.lang.String riderId);
    
    /**
     * Get assigned orders (accepted by rider)
     */
    @retrofit2.http.GET(value = "orders/assigned")
    @org.jetbrains.annotations.NotNull()
    public abstract retrofit2.Call<com.tranxortrider.deliveryrider.api.OrdersResponse> getAssignedOrders(@retrofit2.http.Query(value = "riderId")
    @org.jetbrains.annotations.NotNull()
    java.lang.String riderId);
    
    /**
     * Get completed orders
     */
    @retrofit2.http.GET(value = "orders/completed")
    @org.jetbrains.annotations.NotNull()
    public abstract retrofit2.Call<com.tranxortrider.deliveryrider.api.OrdersResponse> getCompletedOrders(@retrofit2.http.Query(value = "riderId")
    @org.jetbrains.annotations.NotNull()
    java.lang.String riderId, @retrofit2.http.Query(value = "page")
    int page, @retrofit2.http.Query(value = "limit")
    int limit);
    
    /**
     * Get failed orders
     */
    @retrofit2.http.GET(value = "orders/failed")
    @org.jetbrains.annotations.NotNull()
    public abstract retrofit2.Call<com.tranxortrider.deliveryrider.api.OrdersResponse> getFailedOrders(@retrofit2.http.Query(value = "riderId")
    @org.jetbrains.annotations.NotNull()
    java.lang.String riderId, @retrofit2.http.Query(value = "page")
    int page, @retrofit2.http.Query(value = "limit")
    int limit);
    
    /**
     * Get order details
     */
    @retrofit2.http.GET(value = "orders/{orderId}")
    @org.jetbrains.annotations.NotNull()
    public abstract retrofit2.Call<com.tranxortrider.deliveryrider.api.OrderDetailsResponse> getOrderDetails(@retrofit2.http.Path(value = "orderId")
    @org.jetbrains.annotations.NotNull()
    java.lang.String orderId);
    
    /**
     * Accept an order
     */
    @retrofit2.http.POST(value = "orders/{orderId}/accept")
    @org.jetbrains.annotations.NotNull()
    public abstract retrofit2.Call<com.tranxortrider.deliveryrider.api.BasicResponse> acceptOrder(@retrofit2.http.Path(value = "orderId")
    @org.jetbrains.annotations.NotNull()
    java.lang.String orderId, @retrofit2.http.Body()
    @org.jetbrains.annotations.NotNull()
    com.tranxortrider.deliveryrider.api.OrderActionRequest request);
    
    /**
     * Reject an order
     */
    @retrofit2.http.POST(value = "orders/{orderId}/reject")
    @org.jetbrains.annotations.NotNull()
    public abstract retrofit2.Call<com.tranxortrider.deliveryrider.api.BasicResponse> rejectOrder(@retrofit2.http.Path(value = "orderId")
    @org.jetbrains.annotations.NotNull()
    java.lang.String orderId, @retrofit2.http.Body()
    @org.jetbrains.annotations.NotNull()
    com.tranxortrider.deliveryrider.api.OrderRejectRequest request);
    
    /**
     * Mark order as picked up
     */
    @retrofit2.http.POST(value = "orders/{orderId}/pickup")
    @org.jetbrains.annotations.NotNull()
    public abstract retrofit2.Call<com.tranxortrider.deliveryrider.api.BasicResponse> pickupOrder(@retrofit2.http.Path(value = "orderId")
    @org.jetbrains.annotations.NotNull()
    java.lang.String orderId, @retrofit2.http.Body()
    @org.jetbrains.annotations.NotNull()
    com.tranxortrider.deliveryrider.api.OrderActionRequest request);
    
    /**
     * Mark order as delivered
     */
    @retrofit2.http.POST(value = "orders/{orderId}/deliver")
    @org.jetbrains.annotations.NotNull()
    public abstract retrofit2.Call<com.tranxortrider.deliveryrider.api.BasicResponse> deliverOrder(@retrofit2.http.Path(value = "orderId")
    @org.jetbrains.annotations.NotNull()
    java.lang.String orderId, @retrofit2.http.Body()
    @org.jetbrains.annotations.NotNull()
    com.tranxortrider.deliveryrider.api.OrderDeliverRequest request);
    
    /**
     * Mark order as failed
     */
    @retrofit2.http.POST(value = "orders/{orderId}/fail")
    @org.jetbrains.annotations.NotNull()
    public abstract retrofit2.Call<com.tranxortrider.deliveryrider.api.BasicResponse> failOrder(@retrofit2.http.Path(value = "orderId")
    @org.jetbrains.annotations.NotNull()
    java.lang.String orderId, @retrofit2.http.Body()
    @org.jetbrains.annotations.NotNull()
    com.tranxortrider.deliveryrider.api.OrderFailRequest request);
    
    /**
     * Update rider location
     */
    @retrofit2.http.PUT(value = "riders/{riderId}/location")
    @org.jetbrains.annotations.NotNull()
    public abstract retrofit2.Call<com.tranxortrider.deliveryrider.api.BasicResponse> updateLocation(@retrofit2.http.Path(value = "riderId")
    @org.jetbrains.annotations.NotNull()
    java.lang.String riderId, @retrofit2.http.Body()
    @org.jetbrains.annotations.NotNull()
    com.tranxortrider.deliveryrider.api.LocationUpdateRequest request);
    
    /**
     * Interface for API endpoints related to authentication
     */
    @kotlin.Metadata(mv = {1, 9, 0}, k = 3, xi = 48)
    public static final class DefaultImpls {
    }
}