package com.tranxortrider.deliveryrider.repositories;

/**
 * Repository class for handling user onboarding operations using Firebase
 * This is a simplified version that assumes database collections already exist
 */
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u008a\u0001\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u000b\n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u000b\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\n\n\u0002\u0018\u0002\n\u0002\b\u0003\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J$\u0010\r\u001a\b\u0012\u0004\u0012\u00020\u000f0\u000e2\u0006\u0010\u0010\u001a\u00020\u0004H\u0086@\u00f8\u0001\u0000\u00f8\u0001\u0001\u00a2\u0006\u0004\b\u0011\u0010\u0012JB\u0010\u0013\u001a\u00020\u00142\u0006\u0010\u0010\u001a\u00020\u00042\u0006\u0010\u0015\u001a\u00020\u00042\u0006\u0010\u0016\u001a\u00020\u00042 \u0010\u0017\u001a\u001c\u0012\u0004\u0012\u00020\u000f\u0012\u0004\u0012\u00020\u0004\u0012\u0006\u0012\u0004\u0018\u00010\u0019\u0012\u0004\u0012\u00020\u00140\u0018H\u0002J\u00a2\u0001\u0010\u001a\u001a\u00020\u00142\u0006\u0010\u0010\u001a\u00020\u00042\u0006\u0010\u0015\u001a\u00020\u00042\u0006\u0010\u0016\u001a\u00020\u00042\n\b\u0002\u0010\u001b\u001a\u0004\u0018\u00010\u00042\n\b\u0002\u0010\u001c\u001a\u0004\u0018\u00010\u00042\n\b\u0002\u0010\u001d\u001a\u0004\u0018\u00010\u00042\n\b\u0002\u0010\u001e\u001a\u0004\u0018\u00010\u00042\n\b\u0002\u0010\u001f\u001a\u0004\u0018\u00010\u00042\n\b\u0002\u0010 \u001a\u0004\u0018\u00010\u00042\n\b\u0002\u0010!\u001a\u0004\u0018\u00010\u00042\n\b\u0002\u0010\"\u001a\u0004\u0018\u00010\u00042 \u0010\u0017\u001a\u001c\u0012\u0004\u0012\u00020\u000f\u0012\u0004\u0012\u00020\u0004\u0012\u0006\u0012\u0004\u0018\u00010\u0019\u0012\u0004\u0012\u00020\u00140\u0018H\u0002JF\u0010#\u001a\u00020\u00142\u0006\u0010$\u001a\u00020\u000426\u0010\u0017\u001a2\u0012\u0013\u0012\u00110\u000f\u00a2\u0006\f\b&\u0012\b\b\'\u0012\u0004\b\b((\u0012\u0013\u0012\u00110\u0004\u00a2\u0006\f\b&\u0012\b\b\'\u0012\u0004\b\b()\u0012\u0004\u0012\u00020\u00140%J\u0010\u0010*\u001a\u00020\u00042\u0006\u0010+\u001a\u00020,H\u0002J*\u0010-\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020/0.0\u000e2\u0006\u0010\u0010\u001a\u00020\u0004H\u0086@\u00f8\u0001\u0000\u00f8\u0001\u0001\u00a2\u0006\u0004\b0\u0010\u0012J0\u00101\u001a\u00020\u00142\u0006\u0010\u0010\u001a\u00020\u00042 \u0010\u0017\u001a\u001c\u0012\u0004\u0012\u00020\u000f\u0012\u0004\u0012\u00020\u0004\u0012\u0006\u0012\u0004\u0018\u000102\u0012\u0004\u0012\u00020\u00140\u0018JP\u00103\u001a\u00020\u00142\u0006\u00104\u001a\u0002052\u0006\u0010\u0010\u001a\u00020\u00042\u0006\u0010$\u001a\u00020\u00042\u0006\u0010+\u001a\u00020,2\u0006\u00106\u001a\u0002072 \u0010\u0017\u001a\u001c\u0012\u0004\u0012\u00020\u000f\u0012\u0004\u0012\u00020\u0004\u0012\u0006\u0012\u0004\u0018\u000108\u0012\u0004\u0012\u00020\u00140\u0018J\u00a0\u0001\u00109\u001a\u00020\u00142\u0006\u0010\u0010\u001a\u00020\u00042\u0006\u0010\u0015\u001a\u00020\u00042\u0006\u0010\u0016\u001a\u00020\u00042\n\b\u0002\u0010\u001b\u001a\u0004\u0018\u00010\u00042\n\b\u0002\u0010\u001c\u001a\u0004\u0018\u00010\u00042\n\b\u0002\u0010\u001d\u001a\u0004\u0018\u00010\u00042\n\b\u0002\u0010\u001e\u001a\u0004\u0018\u00010\u00042\n\b\u0002\u0010\u001f\u001a\u0004\u0018\u00010\u00042\n\b\u0002\u0010 \u001a\u0004\u0018\u00010\u00042\n\b\u0002\u0010!\u001a\u0004\u0018\u00010\u00042\n\b\u0002\u0010\"\u001a\u0004\u0018\u00010\u00042 \u0010\u0017\u001a\u001c\u0012\u0004\u0012\u00020\u000f\u0012\u0004\u0012\u00020\u0004\u0012\u0006\u0012\u0004\u0018\u00010\u0019\u0012\u0004\u0012\u00020\u00140\u0018JP\u0010:\u001a\u00020\u00142\u0006\u0010\u0010\u001a\u00020\u00042\u0006\u0010+\u001a\u00020,2\u0006\u0010;\u001a\u00020\u00042\b\b\u0002\u0010<\u001a\u00020\u00042\n\b\u0002\u0010=\u001a\u0004\u0018\u00010\u00042\u0018\u0010>\u001a\u0014\u0012\u0004\u0012\u00020\u000f\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020\u00140%H\u0002Ju\u0010?\u001a\u00020\u00142\u0006\u00104\u001a\u0002052\u0006\u0010\u0010\u001a\u00020\u00042\u0006\u0010+\u001a\u00020,2\u0006\u00106\u001a\u0002072M\u0010\u0017\u001aI\u0012\u0013\u0012\u00110\u000f\u00a2\u0006\f\b&\u0012\b\b\'\u0012\u0004\b\b((\u0012\u0013\u0012\u00110\u0004\u00a2\u0006\f\b&\u0012\b\b\'\u0012\u0004\b\b()\u0012\u0015\u0012\u0013\u0018\u00010\u0004\u00a2\u0006\f\b&\u0012\b\b\'\u0012\u0004\b\b(@\u0012\u0004\u0012\u00020\u00140\u0018J(\u0010A\u001a\u0004\u0018\u00010\u00042\u0006\u00104\u001a\u0002052\u0006\u0010B\u001a\u00020C2\u0006\u0010D\u001a\u00020\u0004H\u0082@\u00a2\u0006\u0002\u0010ER\u000e\u0010\u0003\u001a\u00020\u0004X\u0082D\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\bX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\nX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\fX\u0082\u0004\u00a2\u0006\u0002\n\u0000\u0082\u0002\u000b\n\u0002\b!\n\u0005\b\u00a1\u001e0\u0001\u00a8\u0006F"}, d2 = {"Lcom/tranxortrider/deliveryrider/repositories/OnboardingRepository;", "", "()V", "TAG", "", "auth", "Lcom/google/firebase/auth/FirebaseAuth;", "db", "Lcom/google/firebase/firestore/FirebaseFirestore;", "documentsBucket", "Lio/github/jan/supabase/storage/BucketApi;", "storageService", "Lcom/tranxortrider/deliveryrider/services/StorageService;", "checkAllDocumentsSubmitted", "Lkotlin/Result;", "", "userId", "checkAllDocumentsSubmitted-gIAlu-s", "(Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "createNewRiderProfile", "", "fullName", "phoneNumber", "onResponse", "Lkotlin/Function3;", "Lcom/tranxortrider/deliveryrider/api/SetupProfileResponse;", "createNewUserProfile", "address", "city", "state", "zipCode", "vehicleType", "vehicleMake", "vehicleModel", "vehiclePlate", "deleteDocument", "documentId", "Lkotlin/Function2;", "Lkotlin/ParameterName;", "name", "success", "message", "getDocumentFieldName", "documentType", "Lcom/tranxortrider/deliveryrider/api/DocumentType;", "getUserDocuments", "", "Lcom/tranxortrider/deliveryrider/models/Document;", "getUserDocuments-gIAlu-s", "getVerificationStatus", "Lcom/tranxortrider/deliveryrider/api/VerificationStatusResponse;", "resubmitDocument", "context", "Landroid/content/Context;", "documentUri", "Landroid/net/Uri;", "Lcom/tranxortrider/deliveryrider/api/DocumentUploadResponse;", "setupProfile", "updateRiderDocument", "fileUrl", "storageProvider", "storagePath", "callback", "uploadDocument", "response", "uploadToSupabase", "file", "Ljava/io/File;", "path", "(Landroid/content/Context;Ljava/io/File;Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "app_debug"})
public final class OnboardingRepository {
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String TAG = "OnboardingRepository";
    @org.jetbrains.annotations.NotNull()
    private final com.google.firebase.firestore.FirebaseFirestore db = null;
    @org.jetbrains.annotations.NotNull()
    private final com.google.firebase.auth.FirebaseAuth auth = null;
    @org.jetbrains.annotations.NotNull()
    private final io.github.jan.supabase.storage.BucketApi documentsBucket = null;
    @org.jetbrains.annotations.NotNull()
    private final com.tranxortrider.deliveryrider.services.StorageService storageService = null;
    
    public OnboardingRepository() {
        super();
    }
    
    /**
     * Setup user profile with personal information
     * This version updates existing documents rather than creating them from scratch
     */
    public final void setupProfile(@org.jetbrains.annotations.NotNull()
    java.lang.String userId, @org.jetbrains.annotations.NotNull()
    java.lang.String fullName, @org.jetbrains.annotations.NotNull()
    java.lang.String phoneNumber, @org.jetbrains.annotations.Nullable()
    java.lang.String address, @org.jetbrains.annotations.Nullable()
    java.lang.String city, @org.jetbrains.annotations.Nullable()
    java.lang.String state, @org.jetbrains.annotations.Nullable()
    java.lang.String zipCode, @org.jetbrains.annotations.Nullable()
    java.lang.String vehicleType, @org.jetbrains.annotations.Nullable()
    java.lang.String vehicleMake, @org.jetbrains.annotations.Nullable()
    java.lang.String vehicleModel, @org.jetbrains.annotations.Nullable()
    java.lang.String vehiclePlate, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function3<? super java.lang.Boolean, ? super java.lang.String, ? super com.tranxortrider.deliveryrider.api.SetupProfileResponse, kotlin.Unit> onResponse) {
    }
    
    /**
     * Create a new rider profile if it doesn't exist yet
     * This is a fallback method in case the database setup script wasn't run
     */
    private final void createNewRiderProfile(java.lang.String userId, java.lang.String fullName, java.lang.String phoneNumber, kotlin.jvm.functions.Function3<? super java.lang.Boolean, ? super java.lang.String, ? super com.tranxortrider.deliveryrider.api.SetupProfileResponse, kotlin.Unit> onResponse) {
    }
    
    /**
     * Create a new user profile if it doesn't exist yet
     * This is a fallback method in case the database setup script wasn't run
     */
    private final void createNewUserProfile(java.lang.String userId, java.lang.String fullName, java.lang.String phoneNumber, java.lang.String address, java.lang.String city, java.lang.String state, java.lang.String zipCode, java.lang.String vehicleType, java.lang.String vehicleMake, java.lang.String vehicleModel, java.lang.String vehiclePlate, kotlin.jvm.functions.Function3<? super java.lang.Boolean, ? super java.lang.String, ? super com.tranxortrider.deliveryrider.api.SetupProfileResponse, kotlin.Unit> onResponse) {
    }
    
    /**
     * Upload a document for verification
     */
    public final void uploadDocument(@org.jetbrains.annotations.NotNull()
    android.content.Context context, @org.jetbrains.annotations.NotNull()
    java.lang.String userId, @org.jetbrains.annotations.NotNull()
    com.tranxortrider.deliveryrider.api.DocumentType documentType, @org.jetbrains.annotations.NotNull()
    android.net.Uri documentUri, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function3<? super java.lang.Boolean, ? super java.lang.String, ? super java.lang.String, kotlin.Unit> onResponse) {
    }
    
    /**
     * Upload file to Supabase storage
     */
    private final java.lang.Object uploadToSupabase(android.content.Context context, java.io.File file, java.lang.String path, kotlin.coroutines.Continuation<? super java.lang.String> $completion) {
        return null;
    }
    
    /**
     * Update rider document in Firestore
     * This method adds a new document to the userDocuments collection
     * and also updates the rider's documents field for admin verification
     */
    private final void updateRiderDocument(java.lang.String userId, com.tranxortrider.deliveryrider.api.DocumentType documentType, java.lang.String fileUrl, java.lang.String storageProvider, java.lang.String storagePath, kotlin.jvm.functions.Function2<? super java.lang.Boolean, ? super java.lang.String, kotlin.Unit> callback) {
    }
    
    /**
     * Get the field name for a document type in the rider's documents map
     */
    private final java.lang.String getDocumentFieldName(com.tranxortrider.deliveryrider.api.DocumentType documentType) {
        return null;
    }
    
    /**
     * Get verification status for user
     */
    public final void getVerificationStatus(@org.jetbrains.annotations.NotNull()
    java.lang.String userId, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function3<? super java.lang.Boolean, ? super java.lang.String, ? super com.tranxortrider.deliveryrider.api.VerificationStatusResponse, kotlin.Unit> onResponse) {
    }
    
    /**
     * Resubmit a rejected document
     */
    public final void resubmitDocument(@org.jetbrains.annotations.NotNull()
    android.content.Context context, @org.jetbrains.annotations.NotNull()
    java.lang.String userId, @org.jetbrains.annotations.NotNull()
    java.lang.String documentId, @org.jetbrains.annotations.NotNull()
    com.tranxortrider.deliveryrider.api.DocumentType documentType, @org.jetbrains.annotations.NotNull()
    android.net.Uri documentUri, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function3<? super java.lang.Boolean, ? super java.lang.String, ? super com.tranxortrider.deliveryrider.api.DocumentUploadResponse, kotlin.Unit> onResponse) {
    }
    
    /**
     * Delete a document from storage
     * Also removes the document link from the rider profile
     */
    public final void deleteDocument(@org.jetbrains.annotations.NotNull()
    java.lang.String documentId, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function2<? super java.lang.Boolean, ? super java.lang.String, kotlin.Unit> onResponse) {
    }
}