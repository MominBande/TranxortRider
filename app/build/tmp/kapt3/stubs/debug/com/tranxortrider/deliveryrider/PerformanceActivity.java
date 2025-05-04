package com.tranxortrider.deliveryrider;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000v\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0003\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\b\u0010\u001d\u001a\u00020\u001eH\u0002J\b\u0010\u001f\u001a\u00020\u001eH\u0002J\u0012\u0010 \u001a\u00020\u001e2\b\u0010!\u001a\u0004\u0018\u00010\"H\u0014J\b\u0010#\u001a\u00020\u001eH\u0002J\b\u0010$\u001a\u00020\u001eH\u0002J\b\u0010%\u001a\u00020\u001eH\u0002J\b\u0010&\u001a\u00020\u001eH\u0002J\u0010\u0010\'\u001a\u00020\u001e2\u0006\u0010(\u001a\u00020)H\u0002J\u0010\u0010*\u001a\u00020\u001e2\u0006\u0010+\u001a\u00020,H\u0002J\b\u0010-\u001a\u00020\u001eH\u0002J\b\u0010.\u001a\u00020\u001eH\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\bX\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\u0004X\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\u000bX\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\f\u001a\u00020\rX\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u000e\u001a\u00020\u000fX\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0010\u001a\u00020\u0011X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0012\u001a\u00020\u000bX\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0013\u001a\u00020\u0014X\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0015\u001a\u00020\u0004X\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0016\u001a\u00020\u0017X\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0018\u001a\u00020\u0019X\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u001a\u001a\u00020\u001bX\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u001c\u001a\u00020\u0004X\u0082.\u00a2\u0006\u0002\n\u0000\u00a8\u0006/"}, d2 = {"Lcom/tranxortrider/deliveryrider/PerformanceActivity;", "Landroidx/appcompat/app/AppCompatActivity;", "()V", "averageDeliveryTimeText", "Landroid/widget/TextView;", "backButton", "Lcom/google/android/material/button/MaterialButton;", "contentView", "Landroidx/cardview/widget/CardView;", "deliveryCompletionRateText", "errorView", "Landroid/view/View;", "feedbackAdapter", "Lcom/tranxortrider/deliveryrider/adapters/FeedbackAdapter;", "feedbackRecyclerView", "Landroidx/recyclerview/widget/RecyclerView;", "firestoreService", "Lcom/tranxortrider/deliveryrider/services/FirestoreService;", "loadingView", "orderTypesChart", "Lcom/github/mikephil/charting/charts/PieChart;", "overallRatingText", "retryButton", "Landroid/widget/Button;", "sessionManager", "Lcom/tranxortrider/deliveryrider/utils/SessionManager;", "timePerformanceChart", "Lcom/github/mikephil/charting/charts/LineChart;", "totalCustomersServedText", "initViews", "", "loadPerformanceData", "onCreate", "savedInstanceState", "Landroid/os/Bundle;", "setupListeners", "setupOrderTypesChart", "setupRecyclerView", "setupTimePerformanceChart", "showError", "message", "", "showLoading", "isLoading", "", "updateFeedbackList", "updateUIWithData", "app_debug"})
public final class PerformanceActivity extends androidx.appcompat.app.AppCompatActivity {
    private com.google.android.material.button.MaterialButton backButton;
    private android.widget.TextView overallRatingText;
    private android.widget.TextView deliveryCompletionRateText;
    private android.widget.TextView averageDeliveryTimeText;
    private android.widget.TextView totalCustomersServedText;
    private com.github.mikephil.charting.charts.LineChart timePerformanceChart;
    private com.github.mikephil.charting.charts.PieChart orderTypesChart;
    private androidx.recyclerview.widget.RecyclerView feedbackRecyclerView;
    private android.view.View loadingView;
    private android.view.View errorView;
    private androidx.cardview.widget.CardView contentView;
    private android.widget.Button retryButton;
    private com.tranxortrider.deliveryrider.adapters.FeedbackAdapter feedbackAdapter;
    @org.jetbrains.annotations.NotNull()
    private final com.tranxortrider.deliveryrider.services.FirestoreService firestoreService = null;
    private com.tranxortrider.deliveryrider.utils.SessionManager sessionManager;
    
    public PerformanceActivity() {
        super();
    }
    
    @java.lang.Override()
    protected void onCreate(@org.jetbrains.annotations.Nullable()
    android.os.Bundle savedInstanceState) {
    }
    
    private final void initViews() {
    }
    
    private final void setupListeners() {
    }
    
    private final void setupRecyclerView() {
    }
    
    private final void loadPerformanceData() {
    }
    
    private final void updateUIWithData() {
    }
    
    private final void setupTimePerformanceChart() {
    }
    
    private final void setupOrderTypesChart() {
    }
    
    private final void updateFeedbackList() {
    }
    
    private final void showLoading(boolean isLoading) {
    }
    
    private final void showError(java.lang.String message) {
    }
}