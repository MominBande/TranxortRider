// Generated by view binder compiler. Do not edit!
package com.tranxortrider.deliveryrider.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.tranxortrider.deliveryrider.R;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class ActivityBatchBinding implements ViewBinding {
  @NonNull
  private final CoordinatorLayout rootView;

  @NonNull
  public final CardView batchInfoCard;

  @NonNull
  public final BottomNavigationView bottomNavigation;

  @NonNull
  public final Button btnOptimizeRoute;

  @NonNull
  public final Button btnStartBatch;

  @NonNull
  public final LinearLayout emptyView;

  @NonNull
  public final FloatingActionButton fabAddOrder;

  @NonNull
  public final FrameLayout loadingView;

  @NonNull
  public final RecyclerView recyclerViewBatchOrders;

  @NonNull
  public final MaterialToolbar toolbar;

  @NonNull
  public final TextView tvBatchId;

  @NonNull
  public final TextView tvEstimatedEarnings;

  @NonNull
  public final TextView tvEstimatedTime;

  @NonNull
  public final TextView tvOrderCount;

  private ActivityBatchBinding(@NonNull CoordinatorLayout rootView, @NonNull CardView batchInfoCard,
      @NonNull BottomNavigationView bottomNavigation, @NonNull Button btnOptimizeRoute,
      @NonNull Button btnStartBatch, @NonNull LinearLayout emptyView,
      @NonNull FloatingActionButton fabAddOrder, @NonNull FrameLayout loadingView,
      @NonNull RecyclerView recyclerViewBatchOrders, @NonNull MaterialToolbar toolbar,
      @NonNull TextView tvBatchId, @NonNull TextView tvEstimatedEarnings,
      @NonNull TextView tvEstimatedTime, @NonNull TextView tvOrderCount) {
    this.rootView = rootView;
    this.batchInfoCard = batchInfoCard;
    this.bottomNavigation = bottomNavigation;
    this.btnOptimizeRoute = btnOptimizeRoute;
    this.btnStartBatch = btnStartBatch;
    this.emptyView = emptyView;
    this.fabAddOrder = fabAddOrder;
    this.loadingView = loadingView;
    this.recyclerViewBatchOrders = recyclerViewBatchOrders;
    this.toolbar = toolbar;
    this.tvBatchId = tvBatchId;
    this.tvEstimatedEarnings = tvEstimatedEarnings;
    this.tvEstimatedTime = tvEstimatedTime;
    this.tvOrderCount = tvOrderCount;
  }

  @Override
  @NonNull
  public CoordinatorLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static ActivityBatchBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static ActivityBatchBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.activity_batch, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static ActivityBatchBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.batchInfoCard;
      CardView batchInfoCard = ViewBindings.findChildViewById(rootView, id);
      if (batchInfoCard == null) {
        break missingId;
      }

      id = R.id.bottomNavigation;
      BottomNavigationView bottomNavigation = ViewBindings.findChildViewById(rootView, id);
      if (bottomNavigation == null) {
        break missingId;
      }

      id = R.id.btnOptimizeRoute;
      Button btnOptimizeRoute = ViewBindings.findChildViewById(rootView, id);
      if (btnOptimizeRoute == null) {
        break missingId;
      }

      id = R.id.btnStartBatch;
      Button btnStartBatch = ViewBindings.findChildViewById(rootView, id);
      if (btnStartBatch == null) {
        break missingId;
      }

      id = R.id.emptyView;
      LinearLayout emptyView = ViewBindings.findChildViewById(rootView, id);
      if (emptyView == null) {
        break missingId;
      }

      id = R.id.fabAddOrder;
      FloatingActionButton fabAddOrder = ViewBindings.findChildViewById(rootView, id);
      if (fabAddOrder == null) {
        break missingId;
      }

      id = R.id.loadingView;
      FrameLayout loadingView = ViewBindings.findChildViewById(rootView, id);
      if (loadingView == null) {
        break missingId;
      }

      id = R.id.recyclerViewBatchOrders;
      RecyclerView recyclerViewBatchOrders = ViewBindings.findChildViewById(rootView, id);
      if (recyclerViewBatchOrders == null) {
        break missingId;
      }

      id = R.id.toolbar;
      MaterialToolbar toolbar = ViewBindings.findChildViewById(rootView, id);
      if (toolbar == null) {
        break missingId;
      }

      id = R.id.tvBatchId;
      TextView tvBatchId = ViewBindings.findChildViewById(rootView, id);
      if (tvBatchId == null) {
        break missingId;
      }

      id = R.id.tvEstimatedEarnings;
      TextView tvEstimatedEarnings = ViewBindings.findChildViewById(rootView, id);
      if (tvEstimatedEarnings == null) {
        break missingId;
      }

      id = R.id.tvEstimatedTime;
      TextView tvEstimatedTime = ViewBindings.findChildViewById(rootView, id);
      if (tvEstimatedTime == null) {
        break missingId;
      }

      id = R.id.tvOrderCount;
      TextView tvOrderCount = ViewBindings.findChildViewById(rootView, id);
      if (tvOrderCount == null) {
        break missingId;
      }

      return new ActivityBatchBinding((CoordinatorLayout) rootView, batchInfoCard, bottomNavigation,
          btnOptimizeRoute, btnStartBatch, emptyView, fabAddOrder, loadingView,
          recyclerViewBatchOrders, toolbar, tvBatchId, tvEstimatedEarnings, tvEstimatedTime,
          tvOrderCount);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}
