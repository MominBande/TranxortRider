package com.tranxortrider.deliveryrider.api;

/**
 * MOCK ApiClient for backward compatibility
 *
 * Note: This class is kept for compatibility with existing code, but the app
 * now uses Firebase Firestore directly for data operations instead of a custom API.
 */
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u00000\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u00c6\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000R\u0011\u0010\u0006\u001a\u00020\u0007\u00a2\u0006\b\n\u0000\u001a\u0004\b\b\u0010\tR\u000e\u0010\n\u001a\u00020\u000bX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\f\u001a\u00020\rX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0016\u0010\u000e\u001a\n \u0010*\u0004\u0018\u00010\u000f0\u000fX\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0011"}, d2 = {"Lcom/tranxortrider/deliveryrider/api/ApiClient;", "", "()V", "BASE_URL", "", "TAG", "apiService", "Lcom/tranxortrider/deliveryrider/api/ApiService;", "getApiService", "()Lcom/tranxortrider/deliveryrider/api/ApiService;", "loggingInterceptor", "Lokhttp3/logging/HttpLoggingInterceptor;", "okHttpClient", "Lokhttp3/OkHttpClient;", "retrofit", "Lretrofit2/Retrofit;", "kotlin.jvm.PlatformType", "app_debug"})
public final class ApiClient {
    @org.jetbrains.annotations.NotNull()
    private static final java.lang.String TAG = "MockApiClient";
    @org.jetbrains.annotations.NotNull()
    private static final java.lang.String BASE_URL = "https://mock-api.tranxortrider.com/api/";
    @org.jetbrains.annotations.NotNull()
    private static final okhttp3.logging.HttpLoggingInterceptor loggingInterceptor = null;
    @org.jetbrains.annotations.NotNull()
    private static final okhttp3.OkHttpClient okHttpClient = null;
    private static final retrofit2.Retrofit retrofit = null;
    @org.jetbrains.annotations.NotNull()
    private static final com.tranxortrider.deliveryrider.api.ApiService apiService = null;
    @org.jetbrains.annotations.NotNull()
    public static final com.tranxortrider.deliveryrider.api.ApiClient INSTANCE = null;
    
    private ApiClient() {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.tranxortrider.deliveryrider.api.ApiService getApiService() {
        return null;
    }
}