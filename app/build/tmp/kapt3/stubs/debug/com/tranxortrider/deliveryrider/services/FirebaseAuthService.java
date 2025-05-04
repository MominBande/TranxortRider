package com.tranxortrider.deliveryrider.services;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000>\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u000b\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J,\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\t0\b2\u0006\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\u000bH\u0086@\u00f8\u0001\u0000\u00f8\u0001\u0001\u00a2\u0006\u0004\b\r\u0010\u000eJ\b\u0010\u000f\u001a\u0004\u0018\u00010\tJ\u0006\u0010\u0010\u001a\u00020\u0011J\u000e\u0010\u0012\u001a\u00020\u00132\u0006\u0010\u0014\u001a\u00020\u0015J$\u0010\u0016\u001a\b\u0012\u0004\u0012\u00020\u00130\b2\u0006\u0010\n\u001a\u00020\u000bH\u0086@\u00f8\u0001\u0000\u00f8\u0001\u0001\u00a2\u0006\u0004\b\u0017\u0010\u0018J,\u0010\u0019\u001a\b\u0012\u0004\u0012\u00020\t0\b2\u0006\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\u000bH\u0086@\u00f8\u0001\u0000\u00f8\u0001\u0001\u00a2\u0006\u0004\b\u001a\u0010\u000eJ&\u0010\u001b\u001a\b\u0012\u0004\u0012\u00020\t0\b2\b\u0010\u001c\u001a\u0004\u0018\u00010\u0011H\u0086@\u00f8\u0001\u0000\u00f8\u0001\u0001\u00a2\u0006\u0004\b\u001d\u0010\u001eJ\u0006\u0010\u001f\u001a\u00020\u0013R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082.\u00a2\u0006\u0002\n\u0000\u0082\u0002\u000b\n\u0002\b!\n\u0005\b\u00a1\u001e0\u0001\u00a8\u0006 "}, d2 = {"Lcom/tranxortrider/deliveryrider/services/FirebaseAuthService;", "", "()V", "auth", "Lcom/google/firebase/auth/FirebaseAuth;", "googleSignInClient", "Lcom/google/android/gms/auth/api/signin/GoogleSignInClient;", "createUserWithEmailAndPassword", "Lkotlin/Result;", "Lcom/google/firebase/auth/FirebaseUser;", "email", "", "password", "createUserWithEmailAndPassword-0E7RQCE", "(Ljava/lang/String;Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getCurrentUser", "getGoogleSignInIntent", "Landroid/content/Intent;", "initializeGoogleSignIn", "", "context", "Landroid/content/Context;", "sendPasswordResetEmail", "sendPasswordResetEmail-gIAlu-s", "(Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "signInWithEmailAndPassword", "signInWithEmailAndPassword-0E7RQCE", "signInWithGoogle", "data", "signInWithGoogle-gIAlu-s", "(Landroid/content/Intent;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "signOut", "app_debug"})
public final class FirebaseAuthService {
    @org.jetbrains.annotations.NotNull()
    private final com.google.firebase.auth.FirebaseAuth auth = null;
    private com.google.android.gms.auth.api.signin.GoogleSignInClient googleSignInClient;
    
    public FirebaseAuthService() {
        super();
    }
    
    public final void initializeGoogleSignIn(@org.jetbrains.annotations.NotNull()
    android.content.Context context) {
    }
    
    @org.jetbrains.annotations.Nullable()
    public final com.google.firebase.auth.FirebaseUser getCurrentUser() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final android.content.Intent getGoogleSignInIntent() {
        return null;
    }
    
    public final void signOut() {
    }
}