package com.tranxortrider.deliveryrider.services;

/**
 * Service for handling Firebase Storage operations
 */
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u00004\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\u0006\u0010\t\u001a\u00020\nJ,\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\u00040\f2\u0006\u0010\r\u001a\u00020\u00042\u0006\u0010\u000e\u001a\u00020\u000fH\u0086@\u00f8\u0001\u0000\u00f8\u0001\u0001\u00a2\u0006\u0004\b\u0010\u0010\u0011R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082D\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\bX\u0082\u0004\u00a2\u0006\u0002\n\u0000\u0082\u0002\u000b\n\u0002\b!\n\u0005\b\u00a1\u001e0\u0001\u00a8\u0006\u0012"}, d2 = {"Lcom/tranxortrider/deliveryrider/services/StorageService;", "", "()V", "TAG", "", "auth", "Lcom/google/firebase/auth/FirebaseAuth;", "storage", "Lcom/google/firebase/storage/FirebaseStorage;", "getStorageReference", "Lcom/google/firebase/storage/StorageReference;", "uploadProfileImage", "Lkotlin/Result;", "userId", "imageUri", "Landroid/net/Uri;", "uploadProfileImage-0E7RQCE", "(Ljava/lang/String;Landroid/net/Uri;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "app_debug"})
public final class StorageService {
    @org.jetbrains.annotations.NotNull()
    private final com.google.firebase.storage.FirebaseStorage storage = null;
    @org.jetbrains.annotations.NotNull()
    private final com.google.firebase.auth.FirebaseAuth auth = null;
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String TAG = "StorageService";
    
    public StorageService() {
        super();
    }
    
    /**
     * Get a reference to the Firebase Storage
     *
     * @return StorageReference to the root of Firebase Storage
     */
    @org.jetbrains.annotations.NotNull()
    public final com.google.firebase.storage.StorageReference getStorageReference() {
        return null;
    }
}