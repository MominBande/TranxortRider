// Generated by view binder compiler. Do not edit!
package com.tranxortrider.deliveryrider.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.tranxortrider.deliveryrider.R;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class ActivityThemeSettingsBinding implements ViewBinding {
  @NonNull
  private final ConstraintLayout rootView;

  @NonNull
  public final BottomNavigationView bottomNav;

  @NonNull
  public final MaterialButton btnBack;

  @NonNull
  public final MaterialButton btnCompany;

  @NonNull
  public final MaterialButton btnPreview;

  @NonNull
  public final TextInputLayout companyNameInputLayout;

  @NonNull
  public final TextInputEditText etCompanyName;

  @NonNull
  public final ImageView fontDropdownIcon;

  @NonNull
  public final MaterialCardView optionDark;

  @NonNull
  public final MaterialCardView optionLight;

  @NonNull
  public final MaterialCardView optionSystem;

  @NonNull
  public final ScrollView scrollView;

  @NonNull
  public final TextView titleText;

  @NonNull
  public final View topBarBackground;

  @NonNull
  public final TextView tvFontLabel;

  @NonNull
  public final TextView tvThemeDescription;

  private ActivityThemeSettingsBinding(@NonNull ConstraintLayout rootView,
      @NonNull BottomNavigationView bottomNav, @NonNull MaterialButton btnBack,
      @NonNull MaterialButton btnCompany, @NonNull MaterialButton btnPreview,
      @NonNull TextInputLayout companyNameInputLayout, @NonNull TextInputEditText etCompanyName,
      @NonNull ImageView fontDropdownIcon, @NonNull MaterialCardView optionDark,
      @NonNull MaterialCardView optionLight, @NonNull MaterialCardView optionSystem,
      @NonNull ScrollView scrollView, @NonNull TextView titleText, @NonNull View topBarBackground,
      @NonNull TextView tvFontLabel, @NonNull TextView tvThemeDescription) {
    this.rootView = rootView;
    this.bottomNav = bottomNav;
    this.btnBack = btnBack;
    this.btnCompany = btnCompany;
    this.btnPreview = btnPreview;
    this.companyNameInputLayout = companyNameInputLayout;
    this.etCompanyName = etCompanyName;
    this.fontDropdownIcon = fontDropdownIcon;
    this.optionDark = optionDark;
    this.optionLight = optionLight;
    this.optionSystem = optionSystem;
    this.scrollView = scrollView;
    this.titleText = titleText;
    this.topBarBackground = topBarBackground;
    this.tvFontLabel = tvFontLabel;
    this.tvThemeDescription = tvThemeDescription;
  }

  @Override
  @NonNull
  public ConstraintLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static ActivityThemeSettingsBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static ActivityThemeSettingsBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.activity_theme_settings, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static ActivityThemeSettingsBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.bottomNav;
      BottomNavigationView bottomNav = ViewBindings.findChildViewById(rootView, id);
      if (bottomNav == null) {
        break missingId;
      }

      id = R.id.btnBack;
      MaterialButton btnBack = ViewBindings.findChildViewById(rootView, id);
      if (btnBack == null) {
        break missingId;
      }

      id = R.id.btnCompany;
      MaterialButton btnCompany = ViewBindings.findChildViewById(rootView, id);
      if (btnCompany == null) {
        break missingId;
      }

      id = R.id.btnPreview;
      MaterialButton btnPreview = ViewBindings.findChildViewById(rootView, id);
      if (btnPreview == null) {
        break missingId;
      }

      id = R.id.companyNameInputLayout;
      TextInputLayout companyNameInputLayout = ViewBindings.findChildViewById(rootView, id);
      if (companyNameInputLayout == null) {
        break missingId;
      }

      id = R.id.etCompanyName;
      TextInputEditText etCompanyName = ViewBindings.findChildViewById(rootView, id);
      if (etCompanyName == null) {
        break missingId;
      }

      id = R.id.fontDropdownIcon;
      ImageView fontDropdownIcon = ViewBindings.findChildViewById(rootView, id);
      if (fontDropdownIcon == null) {
        break missingId;
      }

      id = R.id.optionDark;
      MaterialCardView optionDark = ViewBindings.findChildViewById(rootView, id);
      if (optionDark == null) {
        break missingId;
      }

      id = R.id.optionLight;
      MaterialCardView optionLight = ViewBindings.findChildViewById(rootView, id);
      if (optionLight == null) {
        break missingId;
      }

      id = R.id.optionSystem;
      MaterialCardView optionSystem = ViewBindings.findChildViewById(rootView, id);
      if (optionSystem == null) {
        break missingId;
      }

      id = R.id.scrollView;
      ScrollView scrollView = ViewBindings.findChildViewById(rootView, id);
      if (scrollView == null) {
        break missingId;
      }

      id = R.id.titleText;
      TextView titleText = ViewBindings.findChildViewById(rootView, id);
      if (titleText == null) {
        break missingId;
      }

      id = R.id.topBarBackground;
      View topBarBackground = ViewBindings.findChildViewById(rootView, id);
      if (topBarBackground == null) {
        break missingId;
      }

      id = R.id.tvFontLabel;
      TextView tvFontLabel = ViewBindings.findChildViewById(rootView, id);
      if (tvFontLabel == null) {
        break missingId;
      }

      id = R.id.tvThemeDescription;
      TextView tvThemeDescription = ViewBindings.findChildViewById(rootView, id);
      if (tvThemeDescription == null) {
        break missingId;
      }

      return new ActivityThemeSettingsBinding((ConstraintLayout) rootView, bottomNav, btnBack,
          btnCompany, btnPreview, companyNameInputLayout, etCompanyName, fontDropdownIcon,
          optionDark, optionLight, optionSystem, scrollView, titleText, topBarBackground,
          tvFontLabel, tvThemeDescription);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}
