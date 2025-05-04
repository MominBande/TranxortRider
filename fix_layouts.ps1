# PowerShell script to fix theme attribute references in all layout XML files
$layoutDir = "app/src/main/res/layout"
$files = Get-ChildItem -Path $layoutDir -Filter "*.xml"

Write-Host "Found $($files.Count) layout files to process"

foreach ($file in $files) {
    Write-Host "Processing $($file.Name)..."
    
    # Read file content
    $content = Get-Content -Path $file.FullName -Raw
    
    # Replace theme attributes with direct color references
    $content = $content -replace 'android:background="\?attr/backgroundColor"', 'android:background="@color/light_background"'
    $content = $content -replace 'app:cardBackgroundColor="\?attr/cardBackgroundColor"', 'app:cardBackgroundColor="@color/white"'
    $content = $content -replace 'app:backgroundTint="\?attr/colorPrimary"', 'app:backgroundTint="@color/aesthgreen"'
    $content = $content -replace 'android:textColor="\?attr/colorOnPrimary"', 'android:textColor="@android:color/white"'
    $content = $content -replace 'android:textColor="\?android:textColorPrimary"', 'android:textColor="@color/light_text_primary"'
    $content = $content -replace 'android:textColor="\?android:textColorSecondary"', 'android:textColor="@color/light_text_secondary"'
    
    # Make sure selectableItemBackground attribute is kept since that's a standard Android attribute
    # $content = $content -replace 'android:background="\?attr/selectableItemBackground"', 'android:background="?android:attr/selectableItemBackground"'
    
    # Write back to file
    Set-Content -Path $file.FullName -Value $content
}

Write-Host "Layout fixing completed!" 