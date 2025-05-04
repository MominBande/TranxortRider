package com.tranxortrider.deliveryrider.components;

/**
 * Helper class to manage drawer navigation throughout the app
 */
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000L\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\u0018\u00002\u00020\u0001B%\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\u0006\u0010\b\u001a\u00020\t\u00a2\u0006\u0002\u0010\nJ\u0014\u0010\u000f\u001a\u00020\u00102\n\u0010\u0011\u001a\u0006\u0012\u0002\b\u00030\u0012H\u0002J\u0006\u0010\u0013\u001a\u00020\u0014J\u0010\u0010\u0015\u001a\u00020\u00142\u0006\u0010\u0016\u001a\u00020\u0017H\u0016J\u000e\u0010\u0018\u001a\u00020\u00102\u0006\u0010\u0019\u001a\u00020\fJ\b\u0010\u001a\u001a\u00020\u0010H\u0002J\u0006\u0010\u001b\u001a\u00020\u0010R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\fX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\tX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\r\u001a\u00020\u000eX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u001c"}, d2 = {"Lcom/tranxortrider/deliveryrider/components/DrawerNavigation;", "Lcom/google/android/material/navigation/NavigationView$OnNavigationItemSelectedListener;", "activity", "Landroidx/appcompat/app/AppCompatActivity;", "toolbar", "Landroidx/appcompat/widget/Toolbar;", "drawerLayout", "Landroidx/drawerlayout/widget/DrawerLayout;", "navigationView", "Lcom/google/android/material/navigation/NavigationView;", "(Landroidx/appcompat/app/AppCompatActivity;Landroidx/appcompat/widget/Toolbar;Landroidx/drawerlayout/widget/DrawerLayout;Lcom/google/android/material/navigation/NavigationView;)V", "currentItemId", "", "sessionManager", "Lcom/tranxortrider/deliveryrider/utils/SessionManager;", "navigateTo", "", "activityClass", "Ljava/lang/Class;", "onBackPressed", "", "onNavigationItemSelected", "item", "Landroid/view/MenuItem;", "setCurrentScreen", "itemId", "setupDrawer", "updateUserInfo", "app_debug"})
public final class DrawerNavigation implements com.google.android.material.navigation.NavigationView.OnNavigationItemSelectedListener {
    @org.jetbrains.annotations.NotNull()
    private final androidx.appcompat.app.AppCompatActivity activity = null;
    @org.jetbrains.annotations.NotNull()
    private final androidx.appcompat.widget.Toolbar toolbar = null;
    @org.jetbrains.annotations.NotNull()
    private final androidx.drawerlayout.widget.DrawerLayout drawerLayout = null;
    @org.jetbrains.annotations.NotNull()
    private final com.google.android.material.navigation.NavigationView navigationView = null;
    @org.jetbrains.annotations.NotNull()
    private final com.tranxortrider.deliveryrider.utils.SessionManager sessionManager = null;
    private int currentItemId;
    
    public DrawerNavigation(@org.jetbrains.annotations.NotNull()
    androidx.appcompat.app.AppCompatActivity activity, @org.jetbrains.annotations.NotNull()
    androidx.appcompat.widget.Toolbar toolbar, @org.jetbrains.annotations.NotNull()
    androidx.drawerlayout.widget.DrawerLayout drawerLayout, @org.jetbrains.annotations.NotNull()
    com.google.android.material.navigation.NavigationView navigationView) {
        super();
    }
    
    /**
     * Set the current screen to highlight the correct drawer item
     */
    public final void setCurrentScreen(int itemId) {
    }
    
    /**
     * Update user information in the drawer header
     */
    public final void updateUserInfo() {
    }
    
    private final void setupDrawer() {
    }
    
    @java.lang.Override()
    public boolean onNavigationItemSelected(@org.jetbrains.annotations.NotNull()
    android.view.MenuItem item) {
        return false;
    }
    
    /**
     * Handle back button press to close drawer if open
     */
    public final boolean onBackPressed() {
        return false;
    }
    
    private final void navigateTo(java.lang.Class<?> activityClass) {
    }
}