// Generated by view binder compiler. Do not edit!
package com.tranxortrider.deliveryrider.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.google.android.material.card.MaterialCardView;
import com.tranxortrider.deliveryrider.R;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class ItemSearchResultBinding implements ViewBinding {
  @NonNull
  private final MaterialCardView rootView;

  @NonNull
  public final MaterialCardView cardView;

  @NonNull
  public final ImageView iconView;

  @NonNull
  public final TextView subtitleText;

  @NonNull
  public final TextView titleText;

  @NonNull
  public final TextView typeChip;

  private ItemSearchResultBinding(@NonNull MaterialCardView rootView,
      @NonNull MaterialCardView cardView, @NonNull ImageView iconView,
      @NonNull TextView subtitleText, @NonNull TextView titleText, @NonNull TextView typeChip) {
    this.rootView = rootView;
    this.cardView = cardView;
    this.iconView = iconView;
    this.subtitleText = subtitleText;
    this.titleText = titleText;
    this.typeChip = typeChip;
  }

  @Override
  @NonNull
  public MaterialCardView getRoot() {
    return rootView;
  }

  @NonNull
  public static ItemSearchResultBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static ItemSearchResultBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.item_search_result, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static ItemSearchResultBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      MaterialCardView cardView = (MaterialCardView) rootView;

      id = R.id.iconView;
      ImageView iconView = ViewBindings.findChildViewById(rootView, id);
      if (iconView == null) {
        break missingId;
      }

      id = R.id.subtitleText;
      TextView subtitleText = ViewBindings.findChildViewById(rootView, id);
      if (subtitleText == null) {
        break missingId;
      }

      id = R.id.titleText;
      TextView titleText = ViewBindings.findChildViewById(rootView, id);
      if (titleText == null) {
        break missingId;
      }

      id = R.id.typeChip;
      TextView typeChip = ViewBindings.findChildViewById(rootView, id);
      if (typeChip == null) {
        break missingId;
      }

      return new ItemSearchResultBinding((MaterialCardView) rootView, cardView, iconView,
          subtitleText, titleText, typeChip);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}
