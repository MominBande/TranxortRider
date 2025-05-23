// Generated by view binder compiler. Do not edit!
package com.tranxortrider.deliveryrider.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import com.tranxortrider.deliveryrider.R;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class ActivityDeliveryHistoryBinding implements ViewBinding {
  @NonNull
  private final RelativeLayout rootView;

  @NonNull
  public final MaterialButton btnBack;

  @NonNull
  public final Button btnDateRange;

  @NonNull
  public final Button btnSort;

  @NonNull
  public final Chip chipAll;

  @NonNull
  public final Chip chipCancelled;

  @NonNull
  public final Chip chipCompleted;

  @NonNull
  public final Chip chipFailed;

  @NonNull
  public final ChipGroup chipGroup;

  @NonNull
  public final LinearLayout dateFilterLayout;

  @NonNull
  public final RecyclerView deliveryHistoryRecyclerView;

  @NonNull
  public final TextView emptyMessageText;

  @NonNull
  public final LinearLayout emptyStateView;

  @NonNull
  public final TextView errorMessageText;

  @NonNull
  public final LinearLayout errorView;

  @NonNull
  public final HorizontalScrollView filterSection;

  @NonNull
  public final FrameLayout loadingView;

  @NonNull
  public final Button retryButton;

  @NonNull
  public final RelativeLayout topBar;

  private ActivityDeliveryHistoryBinding(@NonNull RelativeLayout rootView,
      @NonNull MaterialButton btnBack, @NonNull Button btnDateRange, @NonNull Button btnSort,
      @NonNull Chip chipAll, @NonNull Chip chipCancelled, @NonNull Chip chipCompleted,
      @NonNull Chip chipFailed, @NonNull ChipGroup chipGroup,
      @NonNull LinearLayout dateFilterLayout, @NonNull RecyclerView deliveryHistoryRecyclerView,
      @NonNull TextView emptyMessageText, @NonNull LinearLayout emptyStateView,
      @NonNull TextView errorMessageText, @NonNull LinearLayout errorView,
      @NonNull HorizontalScrollView filterSection, @NonNull FrameLayout loadingView,
      @NonNull Button retryButton, @NonNull RelativeLayout topBar) {
    this.rootView = rootView;
    this.btnBack = btnBack;
    this.btnDateRange = btnDateRange;
    this.btnSort = btnSort;
    this.chipAll = chipAll;
    this.chipCancelled = chipCancelled;
    this.chipCompleted = chipCompleted;
    this.chipFailed = chipFailed;
    this.chipGroup = chipGroup;
    this.dateFilterLayout = dateFilterLayout;
    this.deliveryHistoryRecyclerView = deliveryHistoryRecyclerView;
    this.emptyMessageText = emptyMessageText;
    this.emptyStateView = emptyStateView;
    this.errorMessageText = errorMessageText;
    this.errorView = errorView;
    this.filterSection = filterSection;
    this.loadingView = loadingView;
    this.retryButton = retryButton;
    this.topBar = topBar;
  }

  @Override
  @NonNull
  public RelativeLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static ActivityDeliveryHistoryBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static ActivityDeliveryHistoryBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.activity_delivery_history, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static ActivityDeliveryHistoryBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.btnBack;
      MaterialButton btnBack = ViewBindings.findChildViewById(rootView, id);
      if (btnBack == null) {
        break missingId;
      }

      id = R.id.btnDateRange;
      Button btnDateRange = ViewBindings.findChildViewById(rootView, id);
      if (btnDateRange == null) {
        break missingId;
      }

      id = R.id.btnSort;
      Button btnSort = ViewBindings.findChildViewById(rootView, id);
      if (btnSort == null) {
        break missingId;
      }

      id = R.id.chipAll;
      Chip chipAll = ViewBindings.findChildViewById(rootView, id);
      if (chipAll == null) {
        break missingId;
      }

      id = R.id.chipCancelled;
      Chip chipCancelled = ViewBindings.findChildViewById(rootView, id);
      if (chipCancelled == null) {
        break missingId;
      }

      id = R.id.chipCompleted;
      Chip chipCompleted = ViewBindings.findChildViewById(rootView, id);
      if (chipCompleted == null) {
        break missingId;
      }

      id = R.id.chipFailed;
      Chip chipFailed = ViewBindings.findChildViewById(rootView, id);
      if (chipFailed == null) {
        break missingId;
      }

      id = R.id.chipGroup;
      ChipGroup chipGroup = ViewBindings.findChildViewById(rootView, id);
      if (chipGroup == null) {
        break missingId;
      }

      id = R.id.dateFilterLayout;
      LinearLayout dateFilterLayout = ViewBindings.findChildViewById(rootView, id);
      if (dateFilterLayout == null) {
        break missingId;
      }

      id = R.id.deliveryHistoryRecyclerView;
      RecyclerView deliveryHistoryRecyclerView = ViewBindings.findChildViewById(rootView, id);
      if (deliveryHistoryRecyclerView == null) {
        break missingId;
      }

      id = R.id.emptyMessageText;
      TextView emptyMessageText = ViewBindings.findChildViewById(rootView, id);
      if (emptyMessageText == null) {
        break missingId;
      }

      id = R.id.emptyStateView;
      LinearLayout emptyStateView = ViewBindings.findChildViewById(rootView, id);
      if (emptyStateView == null) {
        break missingId;
      }

      id = R.id.errorMessageText;
      TextView errorMessageText = ViewBindings.findChildViewById(rootView, id);
      if (errorMessageText == null) {
        break missingId;
      }

      id = R.id.errorView;
      LinearLayout errorView = ViewBindings.findChildViewById(rootView, id);
      if (errorView == null) {
        break missingId;
      }

      id = R.id.filterSection;
      HorizontalScrollView filterSection = ViewBindings.findChildViewById(rootView, id);
      if (filterSection == null) {
        break missingId;
      }

      id = R.id.loadingView;
      FrameLayout loadingView = ViewBindings.findChildViewById(rootView, id);
      if (loadingView == null) {
        break missingId;
      }

      id = R.id.retryButton;
      Button retryButton = ViewBindings.findChildViewById(rootView, id);
      if (retryButton == null) {
        break missingId;
      }

      id = R.id.topBar;
      RelativeLayout topBar = ViewBindings.findChildViewById(rootView, id);
      if (topBar == null) {
        break missingId;
      }

      return new ActivityDeliveryHistoryBinding((RelativeLayout) rootView, btnBack, btnDateRange,
          btnSort, chipAll, chipCancelled, chipCompleted, chipFailed, chipGroup, dateFilterLayout,
          deliveryHistoryRecyclerView, emptyMessageText, emptyStateView, errorMessageText,
          errorView, filterSection, loadingView, retryButton, topBar);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}
