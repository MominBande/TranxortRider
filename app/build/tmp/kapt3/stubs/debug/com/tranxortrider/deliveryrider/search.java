package com.tranxortrider.deliveryrider;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0010\u0000\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0007\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\u0016\u0010\u0018\u001a\u00020\u00192\f\u0010\u001a\u001a\b\u0012\u0004\u0012\u00020\u001c0\u001bH\u0002J\b\u0010\u001d\u001a\u00020\u0019H\u0002J\b\u0010\u001e\u001a\u00020\u0019H\u0002J\u0012\u0010\u001f\u001a\u00020\u00192\b\u0010 \u001a\u0004\u0018\u00010!H\u0014J\u0010\u0010\"\u001a\u00020\u00192\u0006\u0010#\u001a\u00020\bH\u0002J\u0010\u0010$\u001a\u00020\u00192\u0006\u0010#\u001a\u00020\bH\u0002J\b\u0010%\u001a\u00020\u0019H\u0002J\b\u0010&\u001a\u00020\u0019H\u0002J\b\u0010\'\u001a\u00020\u0019H\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\bX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\nX\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\fX\u0082.\u00a2\u0006\u0002\n\u0000R\u0014\u0010\r\u001a\b\u0012\u0004\u0012\u00020\u000f0\u000eX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0010\u001a\u00020\u0011X\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0012\u001a\u00020\u0013X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0010\u0010\u0014\u001a\u0004\u0018\u00010\u0015X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0016\u001a\u00020\u0017X\u0082.\u00a2\u0006\u0002\n\u0000\u00a8\u0006("}, d2 = {"Lcom/tranxortrider/deliveryrider/search;", "Landroidx/appcompat/app/AppCompatActivity;", "()V", "backButton", "Lcom/google/android/material/button/MaterialButton;", "currentSearchFilter", "Lcom/tranxortrider/deliveryrider/models/SearchResultType;", "currentSearchQuery", "", "emptySearchesContainer", "Landroid/view/View;", "orderRepository", "Lcom/tranxortrider/deliveryrider/repositories/OrderRepository;", "recentSearches", "", "Lcom/tranxortrider/deliveryrider/models/SearchResult;", "searchAdapter", "Lcom/tranxortrider/deliveryrider/adapters/SearchResultsAdapter;", "searchDebounceHandler", "Landroid/os/Handler;", "searchDebounceRunnable", "Ljava/lang/Runnable;", "searchInput", "Landroid/widget/EditText;", "displaySearchResults", "", "results", "", "", "initializeUI", "loadRecentSearches", "onCreate", "savedInstanceState", "Landroid/os/Bundle;", "performSearch", "query", "saveRecentSearch", "setupListeners", "showEmptySearchState", "showRecentSearches", "app_debug"})
public final class search extends androidx.appcompat.app.AppCompatActivity {
    private android.widget.EditText searchInput;
    private com.google.android.material.button.MaterialButton backButton;
    private android.view.View emptySearchesContainer;
    private com.tranxortrider.deliveryrider.adapters.SearchResultsAdapter searchAdapter;
    private com.tranxortrider.deliveryrider.repositories.OrderRepository orderRepository;
    @org.jetbrains.annotations.NotNull()
    private java.lang.String currentSearchQuery = "";
    @org.jetbrains.annotations.NotNull()
    private com.tranxortrider.deliveryrider.models.SearchResultType currentSearchFilter = com.tranxortrider.deliveryrider.models.SearchResultType.ALL;
    @org.jetbrains.annotations.NotNull()
    private android.os.Handler searchDebounceHandler;
    @org.jetbrains.annotations.Nullable()
    private java.lang.Runnable searchDebounceRunnable;
    @org.jetbrains.annotations.NotNull()
    private java.util.List<com.tranxortrider.deliveryrider.models.SearchResult> recentSearches;
    
    public search() {
        super();
    }
    
    @java.lang.Override()
    protected void onCreate(@org.jetbrains.annotations.Nullable()
    android.os.Bundle savedInstanceState) {
    }
    
    private final void initializeUI() {
    }
    
    private final void setupListeners() {
    }
    
    private final void performSearch(java.lang.String query) {
    }
    
    private final void displaySearchResults(java.util.List<? extends java.lang.Object> results) {
    }
    
    private final void showEmptySearchState() {
    }
    
    private final void saveRecentSearch(java.lang.String query) {
    }
    
    private final void showRecentSearches() {
    }
    
    private final void loadRecentSearches() {
    }
}