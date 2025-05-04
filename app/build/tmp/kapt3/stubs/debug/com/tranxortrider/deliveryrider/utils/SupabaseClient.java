package com.tranxortrider.deliveryrider.utils;

/**
 * Singleton for Supabase client
 */
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u00000\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u00c6\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u0006\u0010\u000b\u001a\u00020\fJ\u000e\u0010\r\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u0010J\u0006\u0010\u0011\u001a\u00020\u000eR\u000e\u0010\u0003\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000R\u0011\u0010\u0007\u001a\u00020\b\u00a2\u0006\b\n\u0000\u001a\u0004\b\t\u0010\n\u00a8\u0006\u0012"}, d2 = {"Lcom/tranxortrider/deliveryrider/utils/SupabaseClient;", "", "()V", "BUCKET_NAME", "", "SUPABASE_KEY", "SUPABASE_URL", "client", "Lio/github/jan/supabase/SupabaseClient;", "getClient", "()Lio/github/jan/supabase/SupabaseClient;", "getDocumentsBucket", "Lio/github/jan/supabase/storage/BucketApi;", "isNetworkAvailable", "", "context", "Landroid/content/Context;", "isSupabaseReachable", "app_debug"})
public final class SupabaseClient {
    @org.jetbrains.annotations.NotNull()
    private static final java.lang.String SUPABASE_URL = "https://kwbbgaxwqrfaousxwbtd.supabase.co";
    @org.jetbrains.annotations.NotNull()
    private static final java.lang.String SUPABASE_KEY = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6Imt3YmJnYXh3cXJmYW91c3h3YnRkIiwicm9sZSI6ImFub24iLCJpYXQiOjE3NDY3OTIzODIsImV4cCI6MjA2MjM2ODM4Mn0.j-JOBV0EeupFxMUiDFMiPnfBD7o5dTCsBN2TIe-FZq4";
    @org.jetbrains.annotations.NotNull()
    private static final java.lang.String BUCKET_NAME = "riders-info";
    @org.jetbrains.annotations.NotNull()
    private static final io.github.jan.supabase.SupabaseClient client = null;
    @org.jetbrains.annotations.NotNull()
    public static final com.tranxortrider.deliveryrider.utils.SupabaseClient INSTANCE = null;
    
    private SupabaseClient() {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final io.github.jan.supabase.SupabaseClient getClient() {
        return null;
    }
    
    /**
     * Get the storage bucket for rider documents
     */
    @org.jetbrains.annotations.NotNull()
    public final io.github.jan.supabase.storage.BucketApi getDocumentsBucket() {
        return null;
    }
    
    /**
     * Check if the device has internet connectivity
     */
    public final boolean isNetworkAvailable(@org.jetbrains.annotations.NotNull()
    android.content.Context context) {
        return false;
    }
    
    /**
     * Check if Supabase server is reachable
     */
    public final boolean isSupabaseReachable() {
        return false;
    }
}