#!/bin/bash

# Find all XML files in the layout directory
find app/src/main/res/layout -name "*.xml" -type f | while read file; do
  echo "Processing $file..."
  
  # Replace ?attr/backgroundColor with @color/light_background
  sed -i 's/android:background="?attr\/backgroundColor"/android:background="@color\/light_background"/g' "$file"
  
  # Replace ?attr/cardBackgroundColor with @color/white
  sed -i 's/app:cardBackgroundColor="?attr\/cardBackgroundColor"/app:cardBackgroundColor="@color\/white"/g' "$file"
  sed -i 's/android:background="?attr\/cardBackgroundColor"/android:background="@color\/white"/g' "$file"
done

echo "Theme attribute replacement complete!" 