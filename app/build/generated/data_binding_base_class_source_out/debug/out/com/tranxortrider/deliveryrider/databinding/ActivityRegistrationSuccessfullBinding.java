// Generated by view binder compiler. Do not edit!
package com.tranxortrider.deliveryrider.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.imageview.ShapeableImageView;
import com.tranxortrider.deliveryrider.R;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class ActivityRegistrationSuccessfullBinding implements ViewBinding {
  @NonNull
  private final ConstraintLayout rootView;

  @NonNull
  public final MaterialButton btnContinue;

  @NonNull
  public final View confettiOverlay;

  @NonNull
  public final ShapeableImageView successIcon;

  @NonNull
  public final TextView successMessage;

  @NonNull
  public final TextView titleText;

  private ActivityRegistrationSuccessfullBinding(@NonNull ConstraintLayout rootView,
      @NonNull MaterialButton btnContinue, @NonNull View confettiOverlay,
      @NonNull ShapeableImageView successIcon, @NonNull TextView successMessage,
      @NonNull TextView titleText) {
    this.rootView = rootView;
    this.btnContinue = btnContinue;
    this.confettiOverlay = confettiOverlay;
    this.successIcon = successIcon;
    this.successMessage = successMessage;
    this.titleText = titleText;
  }

  @Override
  @NonNull
  public ConstraintLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static ActivityRegistrationSuccessfullBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static ActivityRegistrationSuccessfullBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.activity_registration_successfull, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static ActivityRegistrationSuccessfullBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.btnContinue;
      MaterialButton btnContinue = ViewBindings.findChildViewById(rootView, id);
      if (btnContinue == null) {
        break missingId;
      }

      id = R.id.confettiOverlay;
      View confettiOverlay = ViewBindings.findChildViewById(rootView, id);
      if (confettiOverlay == null) {
        break missingId;
      }

      id = R.id.successIcon;
      ShapeableImageView successIcon = ViewBindings.findChildViewById(rootView, id);
      if (successIcon == null) {
        break missingId;
      }

      id = R.id.successMessage;
      TextView successMessage = ViewBindings.findChildViewById(rootView, id);
      if (successMessage == null) {
        break missingId;
      }

      id = R.id.titleText;
      TextView titleText = ViewBindings.findChildViewById(rootView, id);
      if (titleText == null) {
        break missingId;
      }

      return new ActivityRegistrationSuccessfullBinding((ConstraintLayout) rootView, btnContinue,
          confettiOverlay, successIcon, successMessage, titleText);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}
