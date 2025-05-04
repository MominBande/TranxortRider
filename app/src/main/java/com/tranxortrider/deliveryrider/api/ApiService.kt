package com.tranxortrider.deliveryrider.api

import com.tranxortrider.deliveryrider.models.Order
import com.tranxortrider.deliveryrider.models.OrderStatus
import com.tranxortrider.deliveryrider.models.User
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Part
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * Interface for API endpoints related to authentication
 */
interface ApiService {
    
    /**
     * Login user with email and password
     */
    @POST("login")
    fun login(@Body loginRequest: LoginRequest): Call<LoginResponse>
    
    /**
     * Sign up a new user
     */
    @POST("signup")
    fun signup(@Body signupRequest: SignupRequest): Call<SignupResponse>
    
    /**
     * Request password reset
     */
    @POST("forgot-password")
    fun forgotPassword(@Body request: ForgotPasswordRequest): Call<BasicResponse>
    
    /**
     * Verify verification code sent to email
     */
    @POST("verify-code")
    fun verifyCode(@Body request: VerifyCodeRequest): Call<BasicResponse>
    
    /**
     * Reset password with new password
     */
    @POST("reset-password")
    fun resetPassword(@Body request: ResetPasswordRequest): Call<BasicResponse>

    /**
     * Setup user profile
     */
    @POST("setup-profile")
    fun setupProfile(@Body request: SetupProfileRequest): Call<SetupProfileResponse>

    /**
     * Upload document for verification
     */
    @Multipart
    @POST("upload-document")
    fun uploadDocument(
        @Part documentType: MultipartBody.Part,
        @Part userId: MultipartBody.Part,
        @Part document: MultipartBody.Part
    ): Call<DocumentUploadResponse>

    /**
     * Get verification status
     */
    @POST("verification-status")
    fun getVerificationStatus(@Body request: VerificationStatusRequest): Call<VerificationStatusResponse>
    
    /**
     * Get pending orders
     */
    @GET("orders/pending")
    fun getPendingOrders(@Query("riderId") riderId: String): Call<OrdersResponse>
    
    /**
     * Get assigned orders (accepted by rider)
     */
    @GET("orders/assigned")
    fun getAssignedOrders(@Query("riderId") riderId: String): Call<OrdersResponse>
    
    /**
     * Get completed orders
     */
    @GET("orders/completed")
    fun getCompletedOrders(
        @Query("riderId") riderId: String,
        @Query("page") page: Int = 1,
        @Query("limit") limit: Int = 10
    ): Call<OrdersResponse>
    
    /**
     * Get failed orders
     */
    @GET("orders/failed")
    fun getFailedOrders(
        @Query("riderId") riderId: String,
        @Query("page") page: Int = 1,
        @Query("limit") limit: Int = 10
    ): Call<OrdersResponse>
    
    /**
     * Get order details
     */
    @GET("orders/{orderId}")
    fun getOrderDetails(@Path("orderId") orderId: String): Call<OrderDetailsResponse>
    
    /**
     * Accept an order
     */
    @POST("orders/{orderId}/accept")
    fun acceptOrder(@Path("orderId") orderId: String, @Body request: OrderActionRequest): Call<BasicResponse>
    
    /**
     * Reject an order
     */
    @POST("orders/{orderId}/reject")
    fun rejectOrder(@Path("orderId") orderId: String, @Body request: OrderRejectRequest): Call<BasicResponse>
    
    /**
     * Mark order as picked up
     */
    @POST("orders/{orderId}/pickup")
    fun pickupOrder(@Path("orderId") orderId: String, @Body request: OrderActionRequest): Call<BasicResponse>
    
    /**
     * Mark order as delivered
     */
    @POST("orders/{orderId}/deliver")
    fun deliverOrder(@Path("orderId") orderId: String, @Body request: OrderDeliverRequest): Call<BasicResponse>
    
    /**
     * Mark order as failed
     */
    @POST("orders/{orderId}/fail")
    fun failOrder(@Path("orderId") orderId: String, @Body request: OrderFailRequest): Call<BasicResponse>
    
    /**
     * Update rider location
     */
    @PUT("riders/{riderId}/location")
    fun updateLocation(@Path("riderId") riderId: String, @Body request: LocationUpdateRequest): Call<BasicResponse>
}

/**
 * Request data class for login
 */
data class LoginRequest(
    val email: String,
    val password: String
)

/**
 * Response data class for login
 */
data class LoginResponse(
    val success: Boolean,
    val message: String,
    val user: User?
)

/**
 * Request data class for signup
 */
data class SignupRequest(
    val email: String,
    val acceptedTerms: Boolean
)

/**
 * Response data class for signup
 */
data class SignupResponse(
    val success: Boolean,
    val message: String,
    val userId: String?,
    val verificationRequired: Boolean = true
)

/**
 * Basic response data class
 */
data class BasicResponse(
    val success: Boolean,
    val message: String
)

/**
 * Request data class for forgot password
 */
data class ForgotPasswordRequest(
    val email: String
)

/**
 * Request data class for verification code
 */
data class VerifyCodeRequest(
    val email: String,
    val code: String
)

/**
 * Request data class for reset password
 */
data class ResetPasswordRequest(
    val email: String,
    val code: String,
    val newPassword: String
)

/**
 * Request data class for setting up user profile
 */
data class SetupProfileRequest(
    val userId: String,
    val fullName: String,
    val phoneNumber: String,
    val address: String? = null,
    val city: String? = null,
    val state: String? = null,
    val zipCode: String? = null,
    val vehicleType: String? = null
)

/**
 * Response data class for setting up user profile
 */
data class SetupProfileResponse(
    val success: Boolean,
    val message: String,
    val user: User?
)

/**
 * Request data class for getting verification status
 */
data class VerificationStatusRequest(
    val userId: String
)

/**
 * Document type enum
 */
enum class DocumentType {
    GOVERNMENT_ID,
    DRIVER_LICENSE,
    VEHICLE_REGISTRATION,
    VEHICLE_INSURANCE,
    WORK_AUTHORIZATION
}

/**
 * Document upload response
 */
data class DocumentUploadResponse(
    val success: Boolean,
    val message: String,
    val documentId: String?,
    val documentType: String?
)

/**
 * Verification status response
 */
data class VerificationStatusResponse(
    val success: Boolean,
    val message: String,
    val verificationStatus: String,
    val documentsStatus: Map<String, String>
)

/**
 * Response data class for orders
 */
data class OrdersResponse(
    val success: Boolean,
    val message: String,
    val orders: List<Order>,
    val totalCount: Int,
    val page: Int,
    val totalPages: Int
)

/**
 * Response data class for order details
 */
data class OrderDetailsResponse(
    val success: Boolean,
    val message: String,
    val order: Order?
)

/**
 * Request data class for order actions (accept, pickup)
 */
data class OrderActionRequest(
    val riderId: String
)

/**
 * Request data class for order rejection
 */
data class OrderRejectRequest(
    val riderId: String,
    val reason: String
)

/**
 * Request data class for order delivery
 */
data class OrderDeliverRequest(
    val riderId: String,
    val signature: String? = null,
    val deliveryPhoto: String? = null,
    val deliveryNotes: String? = null
)

/**
 * Request data class for failed delivery
 */
data class OrderFailRequest(
    val riderId: String,
    val reason: String,
    val photo: String? = null,
    val notes: String? = null
)

/**
 * Request data class for rider location updates
 */
data class LocationUpdateRequest(
    val latitude: Double,
    val longitude: Double,
    val accuracy: Float? = null,
    val speed: Float? = null,
    val bearing: Float? = null
) 