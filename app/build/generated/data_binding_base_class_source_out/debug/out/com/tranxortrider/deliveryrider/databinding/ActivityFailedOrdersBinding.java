// Generated by view binder compiler. Do not edit!
package com.tranxortrider.deliveryrider.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.chip.Chip;
import com.google.android.material.imageview.ShapeableImageView;
import com.tranxortrider.deliveryrider.R;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class ActivityFailedOrdersBinding implements ViewBinding {
  @NonNull
  private final RelativeLayout rootView;

  @NonNull
  public final LayoutBottomNavigationBinding bottomNav;

  @NonNull
  public final MaterialButton btnDutyStatus;

  @NonNull
  public final MaterialButton btnNotifications;

  @NonNull
  public final MaterialButton btnSearch;

  @NonNull
  public final Chip chipAllOrders;

  @NonNull
  public final Chip chipCompleted;

  @NonNull
  public final Chip chipFailed;

  @NonNull
  public final Chip chipInProgress;

  @NonNull
  public final Chip chipPending;

  @NonNull
  public final LinearLayout emptyStateContainer;

  @NonNull
  public final HorizontalScrollView filterSection;

  @NonNull
  public final RelativeLayout main;

  @NonNull
  public final TextView notificationCount;

  @NonNull
  public final ShapeableImageView profileImage;

  @NonNull
  public final CardView profileSection;

  @NonNull
  public final RecyclerView rvFailedOrders;

  @NonNull
  public final LinearLayout statsSection;

  @NonNull
  public final RelativeLayout topBar;

  @NonNull
  public final TextView tvDeliveryCount;

  @NonNull
  public final TextView tvName;

  @NonNull
  public final TextView tvOnTimeRate;

  @NonNull
  public final TextView tvTitle;

  private ActivityFailedOrdersBinding(@NonNull RelativeLayout rootView,
      @NonNull LayoutBottomNavigationBinding bottomNav, @NonNull MaterialButton btnDutyStatus,
      @NonNull MaterialButton btnNotifications, @NonNull MaterialButton btnSearch,
      @NonNull Chip chipAllOrders, @NonNull Chip chipCompleted, @NonNull Chip chipFailed,
      @NonNull Chip chipInProgress, @NonNull Chip chipPending,
      @NonNull LinearLayout emptyStateContainer, @NonNull HorizontalScrollView filterSection,
      @NonNull RelativeLayout main, @NonNull TextView notificationCount,
      @NonNull ShapeableImageView profileImage, @NonNull CardView profileSection,
      @NonNull RecyclerView rvFailedOrders, @NonNull LinearLayout statsSection,
      @NonNull RelativeLayout topBar, @NonNull TextView tvDeliveryCount, @NonNull TextView tvName,
      @NonNull TextView tvOnTimeRate, @NonNull TextView tvTitle) {
    this.rootView = rootView;
    this.bottomNav = bottomNav;
    this.btnDutyStatus = btnDutyStatus;
    this.btnNotifications = btnNotifications;
    this.btnSearch = btnSearch;
    this.chipAllOrders = chipAllOrders;
    this.chipCompleted = chipCompleted;
    this.chipFailed = chipFailed;
    this.chipInProgress = chipInProgress;
    this.chipPending = chipPending;
    this.emptyStateContainer = emptyStateContainer;
    this.filterSection = filterSection;
    this.main = main;
    this.notificationCount = notificationCount;
    this.profileImage = profileImage;
    this.profileSection = profileSection;
    this.rvFailedOrders = rvFailedOrders;
    this.statsSection = statsSection;
    this.topBar = topBar;
    this.tvDeliveryCount = tvDeliveryCount;
    this.tvName = tvName;
    this.tvOnTimeRate = tvOnTimeRate;
    this.tvTitle = tvTitle;
  }

  @Override
  @NonNull
  public RelativeLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static ActivityFailedOrdersBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static ActivityFailedOrdersBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.activity_failed_orders, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static ActivityFailedOrdersBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.bottomNav;
      View bottomNav = ViewBindings.findChildViewById(rootView, id);
      if (bottomNav == null) {
        break missingId;
      }
      LayoutBottomNavigationBinding binding_bottomNav = LayoutBottomNavigationBinding.bind(bottomNav);

      id = R.id.btnDutyStatus;
      MaterialButton btnDutyStatus = ViewBindings.findChildViewById(rootView, id);
      if (btnDutyStatus == null) {
        break missingId;
      }

      id = R.id.btnNotifications;
      MaterialButton btnNotifications = ViewBindings.findChildViewById(rootView, id);
      if (btnNotifications == null) {
        break missingId;
      }

      id = R.id.btnSearch;
      MaterialButton btnSearch = ViewBindings.findChildViewById(rootView, id);
      if (btnSearch == null) {
        break missingId;
      }

      id = R.id.chipAllOrders;
      Chip chipAllOrders = ViewBindings.findChildViewById(rootView, id);
      if (chipAllOrders == null) {
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

      id = R.id.chipInProgress;
      Chip chipInProgress = ViewBindings.findChildViewById(rootView, id);
      if (chipInProgress == null) {
        break missingId;
      }

      id = R.id.chipPending;
      Chip chipPending = ViewBindings.findChildViewById(rootView, id);
      if (chipPending == null) {
        break missingId;
      }

      id = R.id.emptyStateContainer;
      LinearLayout emptyStateContainer = ViewBindings.findChildViewById(rootView, id);
      if (emptyStateContainer == null) {
        break missingId;
      }

      id = R.id.filterSection;
      HorizontalScrollView filterSection = ViewBindings.findChildViewById(rootView, id);
      if (filterSection == null) {
        break missingId;
      }

      RelativeLayout main = (RelativeLayout) rootView;

      id = R.id.notificationCount;
      TextView notificationCount = ViewBindings.findChildViewById(rootView, id);
      if (notificationCount == null) {
        break missingId;
      }

      id = R.id.profileImage;
      ShapeableImageView profileImage = ViewBindings.findChildViewById(rootView, id);
      if (profileImage == null) {
        break missingId;
      }

      id = R.id.profileSection;
      CardView profileSection = ViewBindings.findChildViewById(rootView, id);
      if (profileSection == null) {
        break missingId;
      }

      id = R.id.rvFailedOrders;
      RecyclerView rvFailedOrders = ViewBindings.findChildViewById(rootView, id);
      if (rvFailedOrders == null) {
        break missingId;
      }

      id = R.id.statsSection;
      LinearLayout statsSection = ViewBindings.findChildViewById(rootView, id);
      if (statsSection == null) {
        break missingId;
      }

      id = R.id.topBar;
      RelativeLayout topBar = ViewBindings.findChildViewById(rootView, id);
      if (topBar == null) {
        break missingId;
      }

      id = R.id.tvDeliveryCount;
      TextView tvDeliveryCount = ViewBindings.findChildViewById(rootView, id);
      if (tvDeliveryCount == null) {
        break missingId;
      }

      id = R.id.tvName;
      TextView tvName = ViewBindings.findChildViewById(rootView, id);
      if (tvName == null) {
        break missingId;
      }

      id = R.id.tvOnTimeRate;
      TextView tvOnTimeRate = ViewBindings.findChildViewById(rootView, id);
      if (tvOnTimeRate == null) {
        break missingId;
      }

      id = R.id.tvTitle;
      TextView tvTitle = ViewBindings.findChildViewById(rootView, id);
      if (tvTitle == null) {
        break missingId;
      }

      return new ActivityFailedOrdersBinding((RelativeLayout) rootView, binding_bottomNav,
          btnDutyStatus, btnNotifications, btnSearch, chipAllOrders, chipCompleted, chipFailed,
          chipInProgress, chipPending, emptyStateContainer, filterSection, main, notificationCount,
          profileImage, profileSection, rvFailedOrders, statsSection, topBar, tvDeliveryCount,
          tvName, tvOnTimeRate, tvTitle);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}
