package com.tranxortrider.deliveryrider.api;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u00002\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\bf\u0018\u00002\u00020\u0001J2\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u00032\b\b\u0001\u0010\u0005\u001a\u00020\u00062\b\b\u0001\u0010\u0007\u001a\u00020\u00062\b\b\u0003\u0010\b\u001a\u00020\tH\u00a7@\u00a2\u0006\u0002\u0010\nJ(\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\f0\u00032\b\b\u0001\u0010\u0005\u001a\u00020\u00062\b\b\u0001\u0010\r\u001a\u00020\u000eH\u00a7@\u00a2\u0006\u0002\u0010\u000fJ(\u0010\u0010\u001a\b\u0012\u0004\u0012\u00020\f0\u00032\b\b\u0001\u0010\u0005\u001a\u00020\u00062\b\b\u0001\u0010\r\u001a\u00020\u000eH\u00a7@\u00a2\u0006\u0002\u0010\u000f\u00a8\u0006\u0011"}, d2 = {"Lcom/tranxortrider/deliveryrider/api/OpenRouteServiceApi;", "", "geocodeSearch", "Lretrofit2/Response;", "Lcom/tranxortrider/deliveryrider/api/GeocodingResponse;", "apiKey", "", "searchText", "size", "", "(Ljava/lang/String;Ljava/lang/String;ILkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getDirections", "Lcom/tranxortrider/deliveryrider/api/DirectionsResponse;", "requestBody", "Lcom/tranxortrider/deliveryrider/api/DirectionsRequest;", "(Ljava/lang/String;Lcom/tranxortrider/deliveryrider/api/DirectionsRequest;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getDirectionsWithApiKey", "app_debug"})
public abstract interface OpenRouteServiceApi {
    
    @retrofit2.http.POST(value = "v2/directions/driving-car/json")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object getDirections(@retrofit2.http.Header(value = "Authorization")
    @org.jetbrains.annotations.NotNull()
    java.lang.String apiKey, @retrofit2.http.Body()
    @org.jetbrains.annotations.NotNull()
    com.tranxortrider.deliveryrider.api.DirectionsRequest requestBody, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super retrofit2.Response<com.tranxortrider.deliveryrider.api.DirectionsResponse>> $completion);
    
    @retrofit2.http.POST(value = "v2/directions/driving-car/json")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object getDirectionsWithApiKey(@retrofit2.http.Query(value = "api_key")
    @org.jetbrains.annotations.NotNull()
    java.lang.String apiKey, @retrofit2.http.Body()
    @org.jetbrains.annotations.NotNull()
    com.tranxortrider.deliveryrider.api.DirectionsRequest requestBody, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super retrofit2.Response<com.tranxortrider.deliveryrider.api.DirectionsResponse>> $completion);
    
    @retrofit2.http.GET(value = "geocode/search")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object geocodeSearch(@retrofit2.http.Header(value = "Authorization")
    @org.jetbrains.annotations.NotNull()
    java.lang.String apiKey, @retrofit2.http.Query(value = "text")
    @org.jetbrains.annotations.NotNull()
    java.lang.String searchText, @retrofit2.http.Query(value = "size")
    int size, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super retrofit2.Response<com.tranxortrider.deliveryrider.api.GeocodingResponse>> $completion);
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 3, xi = 48)
    public static final class DefaultImpls {
    }
}