# Firestore Database Setup for TranxortRider

This guide explains how to set up your Firestore database structure using the provided script and how to remove redundant database initialization code from your Android app afterward.

## Prerequisites

1. Node.js installed on your computer
2. Firebase project with Firestore enabled
3. Firebase Admin SDK credentials

## Setup Instructions

### Step 1: Get Firebase Admin SDK Credentials

1. Go to the Firebase Console: https://console.firebase.google.com/
2. Select your project
3. Go to Project Settings > Service accounts
4. Click "Generate new private key"
5. Save the downloaded JSON file as `serviceAccountKey.json` in the same directory as the setup script

### Step 2: Install Dependencies and Run the Script

```bash
# Install Firebase Admin SDK
npm install firebase-admin

# Run the setup script
node firestore_setup.js
```

The script will create the following collections with sample documents:

- `admins`: Admin users who can approve rider applications and assign orders
- `users`: Basic user information for riders
- `riders`: Extended information specific to riders
- `orders`: Order information
- `batches`: Batch information for multiple orders assigned to a rider
- `userDocuments`: Document verification files uploaded by riders
- `notifications`: General notifications
- `rider_notifications`: Notifications specific to riders
- `earnings`: Rider earnings information

## Removing Database Creation Code from Your App

After running the script, you can remove the following database initialization code from your app:

### 1. Remove Collection Creation Code

In your `OnboardingRepository.kt` file, you can remove or comment out the code that creates collections during user registration:

```kotlin
// Remove this block
db.collection("users")
    .document(userId)
    .set(userData)
    .addOnSuccessListener {
        // Create rider entry as well
        val riderData = hashMapOf(
            "userId" to userId,
            "name" to fullName,
            // other fields...
        )
        
        db.collection("riders")
            .document(userId)
            .set(riderData)
            // ...
    }
```

### 2. Simplify Document Creation

Instead of creating documents from scratch, you can update existing documents:

```kotlin
// Before:
val userData = hashMapOf(
    "id" to userId,
    "name" to fullName,
    // other fields...
)
db.collection("users").document(userId).set(userData)

// After:
val userUpdates = hashMapOf<String, Any>(
    "name" to fullName,
    "phone" to phoneNumber,
    "updatedAt" to Date()
)
db.collection("users").document(userId).update(userUpdates)
```

### 3. Remove Schema Definition Code

If you have any schema definition code in your app that's only used for initial setup, you can remove it.

## Important Notes

1. **Keep Error Handling**: Don't remove error handling code when accessing the database.
2. **Keep Document Updates**: Keep code that updates documents with user-specific information.
3. **Keep Security Rules**: The script doesn't modify your security rules. Make sure you have proper security rules set up.

## Customizing the Setup

You can modify the `sampleData` object in the script to match your specific requirements:

- Change field names or values
- Add additional fields
- Add more sample documents
- Create additional collections

## Troubleshooting

If you encounter any issues:

1. Check that your `serviceAccountKey.json` file is valid and has the correct permissions
2. Make sure your Firebase project has Firestore enabled
3. Check the console output for any error messages

If you need to reset the database, you can delete the collections manually from the Firebase Console and run the script again. 