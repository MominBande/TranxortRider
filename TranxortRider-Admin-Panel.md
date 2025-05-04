# TranxortRider Admin Panel Documentation

## Overview

This document provides comprehensive information about the TranxortRider delivery app's database structure, functions, and requirements necessary for creating a separate admin panel. The TranxortRider app is a delivery platform that connects riders with orders, manages document verification, and tracks deliveries in real-time.

## Database Structure

The TranxortRider app uses Firebase Firestore as its primary database with Supabase for document storage. Below is the detailed database schema:

### Collections

#### 1. `users` Collection

Stores user account information for riders.

**Document ID**: [User UID from Firebase Authentication]

**Fields**:
- `id`: String (same as Document ID)
- `name`: String - User's full name
- `email`: String - User's email address
- `phone`: String - User's phone number
- `address`: String - User's address
- `city`: String - User's city
- `state`: String - User's state
- `zipCode`: String - User's ZIP code
- `vehicleType`: String - Type of vehicle (e.g., "Car", "Motorcycle", "Bicycle")
- `vehicleMake`: String - Make of the vehicle
- `vehicleModel`: String - Model of the vehicle
- `vehiclePlate`: String - License plate number
- `isAvailable`: Boolean - Whether the rider is available for deliveries
- `isVerified`: Boolean - Whether the rider is verified
- `verificationStatus`: String - Status of verification ("pending", "verified", "rejected")
- `photoUrl`: String - URL to the rider's profile photo
- `fcmToken`: String - Firebase Cloud Messaging token for push notifications
- `createdAt`: Timestamp - When the account was created
- `updatedAt`: Timestamp - When the account was last updated

#### 2. `riders` Collection

Extends the user information specifically for riders, including their application status and approval workflow.

**Document ID**: [Same as User UID]

**Fields**:
- `userId`: String - Reference to the user ID in the users collection
- `email`: String - Rider's email address
- `name`: String - Rider's full name
- `phone`: String - Rider's phone number
- `status`: String - Status of the rider (active, inactive, suspended)
- `applicationStatus`: String - Status of the application (pending, approved, rejected, ready_for_review)
- `isOnline`: Boolean - Whether the rider is currently online and accepting deliveries
- `approvedBy`: String - Admin ID who approved the application
- `approvalDate`: Timestamp - When the application was approved
- `approvalNotes`: String - Notes from the admin about approval
- `rejectedBy`: String - Admin ID who rejected the application
- `rejectionDate`: Timestamp - When the application was rejected
- `rejectionReason`: String - Reason for rejection
- `documents`: Map - Contains document information with the following structure:
  - `driverLicense`: Map
    - `url`: String - URL to the document
    - `status`: String - Status of the document (pending, approved, rejected)
    - `reviewedBy`: String - Admin ID who reviewed the document
    - `reviewDate`: Timestamp - When the document was reviewed
    - `reviewNotes`: String - Notes from the admin
  - `governmentId`: Map (same structure as driverLicense)
  - `vehicleRegistration`: Map (same structure as driverLicense)
  - `vehicleInsurance`: Map (same structure as driverLicense)
  - `workAuthorization`: Map (same structure as driverLicense)
- `location`: Map - Current location of the rider
  - `latitude`: Double
  - `longitude`: Double
  - `timestamp`: Number - Timestamp of the location update
- `lastLocationUpdate`: Timestamp - When the location was last updated
- `lastStatusUpdate`: Timestamp - When the rider status was last updated
- `createdAt`: Timestamp - When the rider was created
- `updatedAt`: Timestamp - When the rider was last updated
- `fcmToken`: String - Firebase Cloud Messaging token for push notifications
- `totalEarnings`: Double - Total earnings of the rider

#### 3. `orders` Collection

Stores information about delivery orders.

**Document ID**: Auto-generated

**Fields**:
- `orderId`: String - Unique order identifier
- `customerName`: String - Name of the customer
- `customerAddress`: String - Delivery address
- `customerPhone`: String - Customer's phone number
- `restaurantName`: String - Name of the restaurant
- `restaurantAddress`: String - Restaurant address
- `restaurantPhone`: String - Restaurant's phone number
- `items`: Array - List of items in the order
  - Each item is a Map with:
    - `name`: String - Item name
    - `price`: Double - Item price
    - `quantity`: Number - Item quantity
    - `notes`: String - Special instructions
- `totalAmount`: Double - Total order amount
- `deliveryFee`: Double - Delivery fee (rider's earnings)
- `status`: String - Order status (PENDING, ASSIGNED, ACCEPTED, PICKED_UP, COMPLETED, FAILED, CANCELLED)
- `paymentMethod`: String - Payment method (CASH, CARD, etc.)
- `specialInstructions`: String - Special delivery instructions
- `distance`: Double - Delivery distance in kilometers
- `estimatedDeliveryTime`: Number - Estimated delivery time in minutes
- `assignedRider`: String - ID of the assigned rider
- `assignedBy`: String - ID of the admin who assigned the order
- `assignmentMethod`: String - How the order was assigned (auto, manual)
- `assignmentTimestamp`: Timestamp - When the order was assigned
- `rejectedRiders`: Array - List of rider IDs who rejected the order
- `rejectionReasons`: Map - Reasons for rejection keyed by rider ID
- `createdAt`: Timestamp - When the order was created
- `acceptedAt`: Timestamp - When the order was accepted
- `pickedUpAt`: Timestamp - When the order was picked up
- `deliveredAt`: Timestamp - When the order was delivered
- `completedAt`: Timestamp - When the order was completed
- `canceledAt`: Timestamp - When the order was canceled
- `cancelReason`: String - Reason for cancellation
- `failedAt`: Timestamp - When the order failed
- `failReason`: String - Reason for failure
- `riderLocation`: Map - Location of the rider during delivery
  - `latitude`: Double
  - `longitude`: Double
- `customerLocation`: Map - Customer's location
  - `latitude`: Double
  - `longitude`: Double
- `restaurantLocation`: Map - Restaurant's location
  - `latitude`: Double
  - `longitude`: Double
- `batchId`: String - ID of the batch this order belongs to (if batched)

#### 4. `userDocuments` Collection

Stores information about uploaded verification documents.

**Document ID**: Auto-generated

**Fields**:
- `userId`: String - ID of the user who uploaded the document
- `documentType`: String - Type of document (DRIVER_LICENSE, GOVERNMENT_ID, etc.)
- `fileName`: String - Name of the file
- `fileUrl`: String - URL to the document file
- `status`: String - Status of the document (pending, approved, rejected)
- `uploadedAt`: Timestamp - When the document was uploaded
- `updatedAt`: Timestamp - When the document was last updated
- `reviewedAt`: Timestamp - When the document was reviewed
- `reviewedBy`: String - ID of the admin who reviewed the document
- `rejectionReason`: String - Reason for rejection
- `storageProvider`: String - Storage provider (supabase)
- `storagePath`: String - Path to the file in storage

#### 5. `admins` Collection

Stores information about admin users.

**Document ID**: [Admin UID from Firebase Authentication]

**Fields**:
- `name`: String - Admin's name
- `email`: String - Admin's email
- `role`: String - Admin's role (super_admin, admin, support)
- `permissions`: Map - Admin's permissions
  - `canApproveRiders`: Boolean
  - `canAssignOrders`: Boolean
  - `canViewEarnings`: Boolean
  - `canManageAdmins`: Boolean
- `createdAt`: Timestamp - When the admin was created
- `createdBy`: String - ID of the admin who created this admin
- `lastLogin`: Timestamp - When the admin last logged in

#### 6. `batches` Collection

Groups multiple orders for efficient delivery.

**Document ID**: Auto-generated

**Fields**:
- `riderId`: String - ID of the assigned rider
- `isActive`: Boolean - Whether the batch is active
- `isCompleted`: Boolean - Whether the batch is completed
- `estimatedEarnings`: Double - Estimated earnings for the batch
- `estimatedTimeMinutes`: Number - Estimated time to complete in minutes
- `createdAt`: Timestamp - When the batch was created
- `updatedAt`: Timestamp - When the batch was last updated
- `completedAt`: Timestamp - When the batch was completed

**Subcollection**: `orders`
- Document ID: [Order ID]
- Fields:
  - `orderId`: String - Reference to the order
  - `addedAt`: Timestamp - When the order was added to the batch
  - `sequence`: Number - Order sequence in the batch

#### 7. `earnings` Collection

Records rider earnings.

**Document ID**: Auto-generated

**Fields**:
- `riderId`: String - ID of the rider
- `orderId`: String - ID of the order
- `amount`: Double - Earning amount
- `date`: Timestamp - Date of the earning
- `description`: String - Description of the earning
- `orderReference`: String - Reference to the order

#### 8. `rider_locations` Collection

Tracks rider location history.

**Document ID**: Auto-generated

**Fields**:
- `riderId`: String - ID of the rider
- `latitude`: Double - Rider's latitude
- `longitude`: Double - Rider's longitude
- `accuracy`: Double - Location accuracy
- `speed`: Double - Rider's speed
- `bearing`: Double - Rider's bearing
- `timestamp`: Number - Timestamp of the location update
- `createdAt`: Timestamp - When the record was created

#### 9. `admin_logs` Collection

Logs admin actions.

**Document ID**: Auto-generated

**Fields**:
- `adminId`: String - ID of the admin
- `action`: String - Action performed
- `targetId`: String - ID of the target (rider, order, etc.)
- `details`: Map - Details of the action
- `timestamp`: Timestamp - When the action was performed

#### 10. `notifications` Collection

Stores notifications for users.

**Document ID**: Auto-generated

**Fields**:
- `userId`: String - ID of the user
- `title`: String - Notification title
- `message`: String - Notification message
- `type`: String - Notification type
- `isRead`: Boolean - Whether the notification has been read
- `timestamp`: Timestamp - When the notification was created

## External Storage

The app uses Supabase for document storage. Documents are stored in the `riders-info` bucket with the following structure:

- Path: `riders/{userId}/documents/{fileName}`
- File naming: `{documentType}_{timestamp}_{randomUUID}.{extension}`
- Public URLs are used for document access

## Admin Panel Functions

The admin panel should implement the following key functions:

### 1. Rider Management

#### Rider Approval Workflow
- View pending rider applications
- Access rider information and uploaded documents
- Approve or reject individual documents
- Approve or reject rider applications
- Add notes to approvals/rejections

```kotlin
// Example: Approve a rider document
suspend fun approveRiderDocument(documentId: String, adminId: String, notes: String?): Result<Boolean> {
    // Update document status in userDocuments collection
    db.collection("userDocuments").document(documentId).update(
        mapOf(
            "status" to "approved",
            "reviewedBy" to adminId,
            "reviewedAt" to Date(),
            "reviewNotes" to (notes ?: "")
        )
    )
    
    // Update document status in rider's profile
    db.collection("riders").document(userId).update(
        mapOf(
            "documents.$fieldName.status" to "approved",
            "documents.$fieldName.reviewedBy" to adminId,
            "documents.$fieldName.reviewDate" to Date()
        )
    )
    
    // Log admin action and send notification to rider
}

// Example: Approve a rider application
suspend fun approveRiderApplication(riderId: String, adminId: String, notes: String?): Result<Boolean> {
    // Update rider application status
    db.collection("riders").document(riderId).update(
        mapOf(
            "applicationStatus" to "approved",
            "approvedBy" to adminId,
            "approvalDate" to Date(),
            "approvalNotes" to (notes ?: "")
        )
    )
    
    // Update user verification status
    db.collection("users").document(riderId).update(
        mapOf(
            "isVerified" to true,
            "verificationStatus" to "verified"
        )
    )
    
    // Log admin action and send notification to rider
}
```

#### Rider Monitoring
- View active riders
- Track rider locations in real-time
- View rider performance metrics
- Suspend or reactivate riders

### 2. Order Management

#### Order Assignment
- View pending orders
- Manually assign orders to riders
- Unassign orders from riders
- Monitor order status

```kotlin
// Example: Assign order to rider
suspend fun adminAssignOrderToRider(orderId: String, riderId: String, adminNotes: String?): Result<Boolean> {
    // Update order with assignment details
    ordersCollection.document(orderId).update(
        "status", "ASSIGNED",
        "assignedRider", riderId,
        "assignedBy", adminId,
        "assignmentMethod", "manual",
        "assignmentTimestamp", Date(),
        "updatedAt", Date(),
        "adminNotes", adminNotes
    )
    
    // Log admin activity and send notification to rider
}
```

#### Order Tracking
- View order details
- Track order progress in real-time
- View order history
- Generate order reports

### 3. Earnings Management

- View rider earnings
- Process payments
- Generate earnings reports
- Manage payment disputes

```kotlin
// Example: Get rider earnings for a period
suspend fun getRiderEarnings(riderId: String, startDate: Date, endDate: Date): Result<List<Earning>> {
    val earningsQuery = earningsCollection
        .whereEqualTo("riderId", riderId)
        .whereGreaterThanOrEqualTo("date", startDate)
        .whereLessThanOrEqualTo("date", endDate)
        .orderBy("date", Query.Direction.DESCENDING)
    
    val earningsSnapshot = earningsQuery.get().await()
    
    // Convert to earnings objects and return
}
```

### 4. Analytics and Reporting

- View delivery metrics (completion rates, delivery times)
- Generate reports on rider performance
- View earnings reports
- Export data for analysis

### 5. Admin User Management

- Create and manage admin accounts
- Assign admin roles and permissions
- Track admin actions through logs

## Security Rules

The admin panel must implement proper security rules to ensure that only authorized admins can perform specific actions. Here's a sample of the Firestore security rules:

```javascript
// Function to check if the user is an admin
function isAdmin() {
  return isAuthenticated() && 
    exists(/databases/$(database)/documents/admins/$(request.auth.uid));
}

// Function to check if the admin has specific permission
function hasPermission(permission) {
  return isAdmin() && 
    get(/databases/$(database)/documents/admins/$(request.auth.uid)).data.permissions[permission] == true;
}

// Riders collection rules
match /riders/{riderId} {
  allow read: if isOwner(riderId) || isAdmin();
  allow update: if isOwner(riderId) || 
                 (isAdmin() && hasPermission("canApproveRiders"));
  allow delete: if isAdmin() && hasPermission("canApproveRiders");
}

// Orders collection rules
match /orders/{orderId} {
  allow read: if isApprovedRider() || isAdmin();
  allow create: if isAdmin();
  allow update: if (isAdmin() && hasPermission("canAssignOrders"));
  allow delete: if isAdmin();
}
```

## Integration with Supabase Storage

The admin panel needs to integrate with Supabase for document storage and retrieval:

```kotlin
// Supabase configuration
private const val SUPABASE_URL = "https://kwbbgaxwqrfaousxwbtd.supabase.co"
private const val SUPABASE_KEY = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6Imt3YmJnYXh3cXJmYW91c3h3YnRkIiwicm9sZSI6ImFub24iLCJpYXQiOjE3NDY3OTIzODIsImV4cCI6MjA2MjM2ODM4Mn0.j-JOBV0EeupFxMUiDFMiPnfBD7o5dTCsBN2TIe-FZq4"
private const val BUCKET_NAME = "riders-info"

// Initialize Supabase client
val client = createSupabaseClient(
    supabaseUrl = SUPABASE_URL,
    supabaseKey = SUPABASE_KEY
) {
    install(Storage)
}

// Get document bucket
val documentsBucket = client.storage[BUCKET_NAME]

// View document
val publicUrl = documentsBucket.publicUrl(storagePath)

// Delete document
documentsBucket.delete(storagePath)
```

## Real-time Updates

The admin panel should implement real-time updates using Firebase Firestore listeners:

```kotlin
// Listen for new rider applications
db.collection("riders")
    .whereEqualTo("applicationStatus", "pending")
    .addSnapshotListener { snapshot, error ->
        if (error != null) {
            // Handle error
            return@addSnapshotListener
        }
        
        snapshot?.documentChanges?.forEach { change ->
            when (change.type) {
                DocumentChange.Type.ADDED -> {
                    // New rider application
                }
                DocumentChange.Type.MODIFIED -> {
                    // Rider application updated
                }
                DocumentChange.Type.REMOVED -> {
                    // Rider application removed
                }
            }
        }
    }

// Track rider locations in real-time
db.collection("admin_rider_tracking")
    .whereGreaterThan("timestamp", System.currentTimeMillis() - 30 * 60 * 1000) // Last 30 minutes
    .addSnapshotListener { snapshot, error ->
        // Update rider locations on map
    }
```

## Notifications

The admin panel should implement notifications for important events:

```kotlin
// Create notification for admin
val adminNotificationData = hashMapOf(
    "type" to "rider_ready_for_review",
    "title" to "Rider Ready for Review",
    "message" to "$riderName has all required documents approved and is ready for final review.",
    "relatedId" to riderId,
    "read" to false,
    "createdAt" to Date()
)

// Get all admins with approval permission
val admins = db.collection("admins")
    .whereEqualTo("permissions.canApproveRiders", true)
    .get()
    .await()

// Create a notification for each admin
for (admin in admins.documents) {
    val adminId = admin.id
    db.collection("admin_notifications").add(
        adminNotificationData + mapOf("adminId" to adminId)
    )
}
```

## Technical Requirements

1. **Backend**:
   - Firebase Authentication for admin login
   - Firebase Firestore for data storage and real-time updates
   - Supabase integration for document storage
   - Cloud Functions for complex operations

## Conclusion

This document provides a comprehensive overview of the TranxortRider app's database structure and the requirements for building an admin panel. By following these guidelines, you can create a robust admin panel that integrates seamlessly with the existing rider app and provides all the necessary tools for managing riders, orders, and earnings. 