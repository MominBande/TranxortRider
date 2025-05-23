// Generated by view binder compiler. Do not edit!
package com.tranxortrider.deliveryrider.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewbinding.ViewBinding;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.tranxortrider.deliveryrider.R;
import java.lang.NullPointerException;
import java.lang.Override;

public final class LayoutBottomNavigationBinding implements ViewBinding {
  @NonNull
  private final BottomNavigationView rootView;

  @NonNull
  public final BottomNavigationView bottomNavigationView;

  private LayoutBottomNavigationBinding(@NonNull BottomNavigationView rootView,
      @NonNull BottomNavigationView bottomNavigationView) {
    this.rootView = rootView;
    this.bottomNavigationView = bottomNavigationView;
  }

  @Override
  @NonNull
  public BottomNavigationView getRoot() {
    return rootView;
  }

  @NonNull
  public static LayoutBottomNavigationBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static LayoutBottomNavigationBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.layout_bottom_navigation, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static LayoutBottomNavigationBinding bind(@NonNull View rootView) {
    if (rootView == null) {
      throw new NullPointerException("rootView");
    }

    BottomNavigationView bottomNavigationView = (BottomNavigationView) rootView;

    return new LayoutBottomNavigationBinding((BottomNavigationView) rootView, bottomNavigationView);
  }
}
