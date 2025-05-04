# TranxortRider - Database Schema and Security Rules

## Overview

This document outlines the complete database schema for the TranxortRider application, focusing on the permission system that allows riders to accept, reject, and mark orders as completed, and admins to assign orders to riders. The schema is designed for use with Firebase Firestore.

## Collections

### 1. `users` Collection

Stores user account information for riders.

**Document ID**: [User UID from Firebase Authentication]

**Fields**:
- `id`: String (same as Document ID)
- `name`: String - User's full name
- `email`: String - User's email address
- `phone`: String - User's phone number
- `address`: String - User's address
- `vehicleType`: String - Type of vehicle (e.g., "Car", "Motorcycle", "Bicycle")
- `vehicleMake`: String - Make of the vehicle
- `vehicleModel`: String - Model of the vehicle
- `vehiclePlate`: String - License plate number
- `isAvailable`: Boolean - Whether the rider is available for deliveries
- `photoUrl`: String - URL to the rider's profile photo
- `fcmToken`: String - Firebase Cloud Messaging token for push notifications
- `createdAt`: Timestamp - When the account was created
- `updatedAt`: Timestamp - When the account was last updated

### 2. `riders` Collection

Extends the user information specifically for riders, including their application status and approval workflow.

**Document ID**: [Same as User UID]

**Fields**:
- `userId`: String - Reference to the user ID in the users collection
- `email`: String - Rider's email address
- `name`: String - Rider's full name
- `phone`: String - Rider's phone number
- `status`: String - Status of the rider (active, inactive, suspended)
- `isOnline`: Boolean - Whether the rider is currently online and accepting deliveries
- `applicationStatus`: String - Status of the application (pending, approved, rejected)
- `approvedBy`: String - Admin ID who approved the application
- `approvalDate`: Timestamp - When the application was approved
- `rejectionReason`: String - Reason for rejection, if applicable
- `rejectedBy`: String - Admin ID who rejected the application
- `rejectionDate`: Timestamp - When the application was rejected
- `location`: Map
  - `latitude`: Double - Current latitude
  - `longitude`: Double - Current longitude
  - `timestamp`: Number - When the location was last updated (Unix timestamp)
- `totalEarnings`: Double - Total earnings of the rider
- `lastLocationUpdate`: Timestamp - When the location was last updated
- `lastStatusUpdate`: Timestamp - When the status (online/offline) was last updated
- `createdAt`: Timestamp - When the rider record was created
- `documents`: Map - Verification documents
  - `governmentId`: Map
    - `url`: String - URL to the document
    - `status`: String - Status of the document verification (pending, approved, rejected)
    - `reviewedBy`: String - Admin ID who reviewed the document
    - `reviewDate`: Timestamp - When the document was reviewed
  - `driverLicense`: Map (Same structure as governmentId)
  - `vehicleRegistration`: Map (Same structure as governmentId)
  - `vehicleInsurance`: Map (Same structure as governmentId)
  - `workAuthorization`: Map (Same structure as governmentId)
  - ... Other required documents

### 3. `orders` Collection

Stores all order information, including assignments to riders.

**Document ID**: [Auto-generated]

**Fields**:
- `id`: String (same as Document ID)
- `orderId`: String - Human-readable order ID for reference
- `customerName`: String - Name of the customer
- `customerAddress`: String - Delivery address
- `customerPhone`: String - Customer's phone number
- `restaurantName`: String - Name of the restaurant/merchant
- `restaurantAddress`: String - Address of the restaurant/merchant
- `restaurantPhone`: String - Restaurant's phone number
- `items`: Array of Maps - Items in the order
  - `name`: String - Item name
  - `quantity`: Number - Quantity ordered
  - `price`: Number - Price per item
  - `notes`: String - Special instructions for the item
- `totalAmount`: Number - Total order amount
- `deliveryFee`: Number - Delivery fee
- `status`: String - Order status (PENDING, ASSIGNED, ACCEPTED, PICKED_UP, COMPLETED, CANCELLED, FAILED)
- `paymentMethod`: String - Payment method used
- `specialInstructions`: String - Special delivery instructions
- `distance`: Number - Distance in kilometers
- `estimatedDeliveryTime`: Number - Estimated delivery time in minutes
- `createdAt`: Timestamp - When the order was created
- `updatedAt`: Timestamp - When the order was last updated
- `acceptedAt`: Timestamp - When the order was accepted by a rider
- `pickedUpAt`: Timestamp - When the order was picked up
- `deliveredAt`: Timestamp - When the order was delivered
- `completedAt`: Timestamp - When the order was marked as completed
- `canceledAt`: Timestamp - When the order was canceled
- `cancelReason`: String - Reason for cancellation
- `latitude`: Double - Order latitude (usually restaurant location)
- `longitude`: Double - Order longitude (usually restaurant location)
- `customerLatitude`: Double - Customer location latitude
- `customerLongitude`: Double - Customer location longitude
- `restaurantLatitude`: Double - Restaurant location latitude
- `restaurantLongitude`: Double - Restaurant location longitude
- `batchId`: String - ID of the batch this order belongs to (if any)
- `assignedRider`: String - ID of the rider assigned to this order
- `assignedBy`: String - Admin ID who assigned the order (if manually assigned)
- `assignmentMethod`: String - How the order was assigned ("automatic" or "manual")
- `assignmentTimestamp`: Timestamp - When the order was assigned
- `adminNotes`: String - Notes from admin about this order assignment
- `rejectedRiders`: Array of Strings - IDs of riders who rejected this order
- `rejectionReasons`: Map - Reasons riders gave for rejecting the order
  - `[riderId]`: String - Rejection reason for each rider ID
- `deliveryCode`: String - Code that needs to be entered to verify delivery
- `packageCode`: String - Barcode/QR code on the package

### 4. `batches` Collection

Stores information about batches of orders assigned to a rider (for multiple pickups/deliveries).

**Document ID**: [Auto-generated]

**Fields**:
- `riderId`: String - ID of the rider assigned to this batch
- `isActive`: Boolean - Whether this batch is currently active
- `isCompleted`: Boolean - Whether this batch has been completed
- `estimatedEarnings`: Number - Estimated earnings for the rider for this batch
- `estimatedTimeMinutes`: Number - Estimated time to complete the batch in minutes
- `createdAt`: Timestamp - When the batch was created
- `updatedAt`: Timestamp - When the batch was last updated
- `completedAt`: Timestamp - When the batch was completed

**Subcollection**: `orders`

**Document ID**: [Order ID]

**Fields**:
- `orderId`: String - Reference to the order
- `addedAt`: Timestamp - When the order was added to the batch
- `sequence`: Number - The sequence number for optimal delivery route

### 5. `admins` Collection

Stores information about administrators who can approve riders and assign orders.

**Document ID**: [Admin User UID]

**Fields**:
- `id`: String (same as Document ID)
- `email`: String - Admin's email address
- `name`: String - Admin's full name
- `phone`: String - Admin's phone number (optional)
- `role`: String - Admin role (can be "super_admin" or "manager")
- `permissions`: Map - Specific permissions
  - `canApproveRiders`: Boolean
  - `canAssignOrders`: Boolean
  - `canViewAnalytics`: Boolean
  - `canManageAdmins`: Boolean
- `createdAt`: Timestamp - When the admin account was created
- `updatedAt`: Timestamp - When the admin account was last updated
- `lastLogin`: Timestamp - When the admin last logged in
- `fcmToken`: String - Firebase Cloud Messaging token for notifications
- `active`: Boolean - Whether the admin account is active

### 6. `admin_logs` Collection

Tracks all administrative actions for auditing purposes.

**Document ID**: [Auto-generated]

**Fields**:
- `adminId`: String - ID of the admin who performed the action
- `action`: String - Type of action (e.g., "rider_approved", "order_assigned")
- `targetId`: String - ID of the affected resource (rider ID, order ID, etc.)
- `details`: Map - Additional details about the action
- `timestamp`: Timestamp - When the action was performed
- `ipAddress`: String - IP address of the admin (optional)

### 7. `notifications` Collection

Stores notifications for both riders and admins.

**Document ID**: [Auto-generated]

**Fields**:
- `userId`: String - ID of the user (rider or admin) who should receive the notification
- `title`: String - Notification title
- `message`: String - Notification message
- `type`: String - Type of notification (e.g., "order_assigned", "rider_approved")
- `orderId`: String - Reference to an order (if applicable)
- `isRead`: Boolean - Whether the notification has been read
- `timestamp`: Timestamp - When the notification was created

### 8. `rider_notifications` Collection

Specialized collection for notifications to riders.

**Document ID**: [Auto-generated]

**Fields**:
- `riderId`: String - ID of the rider
- `type`: String - Type of notification
- `title`: String - Notification title
- `message`: String - Notification message
- `orderId`: String - Reference to an order (if applicable)
- `read`: Boolean - Whether the notification has been read
- `createdAt`: Timestamp - When the notification was created

### 9. `admin_notifications` Collection

Specialized collection for notifications to admins.

**Document ID**: [Auto-generated]

**Fields**:
- `adminId`: String - ID of the admin
- `type`: String - Type of notification
- `title`: String - Notification title
- `message`: String - Notification message
- `relatedId`: String - ID of the related resource
- `read`: Boolean - Whether the notification has been read
- `createdAt`: Timestamp - When the notification was created
- `readAt`: Timestamp - When the notification was read (optional)

### 10. `rider_locations` Collection

Historical record of rider locations.

**Document ID**: [Auto-generated]

**Fields**:
- `riderId`: String - ID of the rider
- `latitude`: Double - Latitude coordinate
- `longitude`: Double - Longitude coordinate
- `accuracy`: Double - Accuracy of the location in meters (optional)
- `speed`: Double - Speed in km/h (optional)
- `bearing`: Double - Direction of travel in degrees (optional)
- `timestamp`: Number - Unix timestamp of when the location was recorded
- `createdAt`: Timestamp - When the record was created

### 11. `admin_rider_tracking` Collection

Used by admin panel for real-time tracking of riders.

**Document ID**: [Auto-generated]

**Fields**:
- `riderId`: String - ID of the rider
- `latitude`: Double - Latitude coordinate
- `longitude`: Double - Longitude coordinate
- `speed`: Double - Speed in km/h (optional)
- `bearing`: Double - Direction of travel in degrees (optional)
- `timestamp`: Number - Unix timestamp of when the location was recorded
- `createdAt`: Timestamp - When the record was created

### 12. `packageScans` Collection

Tracks package barcode/QR code scans.

**Document ID**: [Auto-generated]

**Fields**:
- `orderId`: String - Reference to the order
- `packageCode`: String - Barcode/QR code scanned
- `scannedAt`: Timestamp - When the package was scanned
- `scannedBy`: String - ID of the rider who scanned the package
- `location`: Map (optional) - Location where the scan occurred
  - `latitude`: Double
  - `longitude`: Double

### 13. `orderAssignmentRequests` Collection

Tracks requests from riders to be assigned to specific orders.

**Document ID**: [Auto-generated]

**Fields**:
- `riderId`: String - ID of the rider requesting assignment
- `orderId`: String - ID of the order they want to be assigned to
- `requestedAt`: Timestamp - When the request was made
- `status`: String - Status of the request (PENDING, APPROVED, REJECTED)
- `processedAt`: Timestamp - When the request was processed
- `processedBy`: String - ID of the admin who processed the request (if any)

### 14. `earnings` Collection

Tracks rider earnings.

**Document ID**: [Auto-generated]

**Fields**:
- `riderId`: String - ID of the rider
- `orderId`: String - ID of the related order
- `amount`: Number - Amount earned
- `date`: Timestamp - Date of the earning
- `description`: String - Description of the earning
- `orderReference`: String - Human-readable order reference

## Workflow for Admin Approval of Riders

1. Rider signs up and uploads verification documents
2. Admin reviews the rider's application from the admin panel:
   - Views the uploaded documents
   - Checks personal and vehicle information
3. Admin approves or rejects the rider:
   - If approved: Updates `applicationStatus` to "approved"
   - If rejected: Updates `applicationStatus` to "rejected" and provides a reason
4. System notifies the rider of the decision

## Workflow for Admin Assignment of Orders to Riders

1. Admin views the list of pending orders from the admin panel
2. Admin selects an order and assigns it to a specific rider:
   - Updates the order's `assignedRider` field
   - Sets `assignmentMethod` to "manual"
   - Sets `status` to "ASSIGNED"
3. System sends a notification to the rider about the assignment
4. Rider can:
   - Accept the assignment: Updates `status` to "ACCEPTED"
   - Reject the assignment: Updates `status` to "PENDING" and removes assignment

## Workflow for Riders to Accept/Reject/Complete Orders

### Orders assigned by the system:
1. Rider receives a notification about a new order
2. Rider can:
   - Accept: Updates `status` to "ACCEPTED"
   - Reject: Updates `rejectedRiders` array and provides a reason

### Orders assigned by an admin:
1. Rider receives a notification about an assigned order
2. Rider can:
   - Accept: Updates `status` to "ACCEPTED"
   - Reject: Updates `status` to "PENDING" and removes assignment data

### Order completion workflow:
1. Rider picks up the order: Updates `status` to "PICKED_UP"
2. Rider delivers the order: 
   - Scans delivery code or receives confirmation from customer
   - Updates `status` to "COMPLETED"
   - System calculates and records earnings

## Security Rules

The following Firebase Firestore security rules should be implemented to enforce proper permissions:

```
rules_version = '2';
service cloud.firestore {
  match /databases/{database}/documents {
    // Function to check if the user is authenticated
    function isAuthenticated() {
      return request.auth != null;
    }
    
    // Function to check if the user is accessing their own data
    function isOwner(userId) {
      return request.auth.uid == userId;
    }
    
    // Function to check if the user is an admin
    function isAdmin() {
      return exists(/databases/$(database)/documents/admins/$(request.auth.uid));
    }
    
    // Function to check if the admin has specific permission
    function hasPermission(permission) {
      return isAdmin() && 
        get(/databases/$(database)/documents/admins/$(request.auth.uid)).data.permissions[permission] == true;
    }
    
    // Function to check if a rider is approved
    function isApprovedRider() {
      return isAuthenticated() && 
        exists(/databases/$(database)/documents/riders/$(request.auth.uid)) &&
        get(/databases/$(database)/documents/riders/$(request.auth.uid)).data.applicationStatus == "approved";
    }
    
    // Function to check if the rider is assigned to an order
    function isAssignedRider(orderId) {
      return isApprovedRider() && 
        get(/databases/$(database)/documents/orders/$(orderId)).data.assignedRider == request.auth.uid;
    }
    
    // Users collection rules
    match /users/{userId} {
      allow read: if isOwner(userId) || isAdmin();
      allow create: if isAuthenticated() && isOwner(userId);
      allow update: if isOwner(userId) || isAdmin();
      allow delete: if isAdmin();
    }
    
    // Riders collection rules
    match /riders/{riderId} {
      allow read: if isOwner(riderId) || isAdmin();
      allow create: if isAuthenticated() && isOwner(riderId);
      allow update: if isOwner(riderId) || 
                     (isAdmin() && hasPermission("canApproveRiders"));
      allow delete: if isAdmin() && hasPermission("canApproveRiders");
    }
    
    // Orders collection rules
    match /orders/{orderId} {
      // Riders can read any order
      allow read: if isApprovedRider() || isAdmin();
      
      // Only admins can create orders
      allow create: if isAdmin();
      
      // Allow updates based on role and order status
      allow update: if 
        // Admins with permission can always update orders
        (isAdmin() && hasPermission("canAssignOrders")) ||
        
        // Assigned riders can update order status
        (isAssignedRider(orderId) && 
          (request.resource.data.status == "ACCEPTED" ||
           request.resource.data.status == "PICKED_UP" ||
           request.resource.data.status == "COMPLETED" ||
           request.resource.data.status == "FAILED")) ||
        
        // Any approved rider can reject an order
        (isApprovedRider() && 
          request.resource.data.diff(resource.data).affectedKeys()
            .hasOnly(["rejectedRiders", "rejectionReasons"]));
      
      // Only admins can delete orders
      allow delete: if isAdmin();
    }
    
    // Batches collection rules
    match /batches/{batchId} {
      allow read: if isApprovedRider() || isAdmin();
      allow create: if isApprovedRider() || (isAdmin() && hasPermission("canAssignOrders"));
      allow update: if 
        (isAdmin() && hasPermission("canAssignOrders")) ||
        (isApprovedRider() && resource.data.riderId == request.auth.uid);
      allow delete: if isAdmin() && hasPermission("canAssignOrders");
      
      // Batch orders subcollection
      match /orders/{orderId} {
        allow read: if isApprovedRider() || isAdmin();
        allow write: if 
          (isAdmin() && hasPermission("canAssignOrders")) ||
          (isApprovedRider() && get(/databases/$(database)/documents/batches/$(batchId)).data.riderId == request.auth.uid);
      }
    }
    
    // Admins collection rules
    match /admins/{adminId} {
      allow read: if isAdmin();
      allow write: if isAdmin() && hasPermission("canManageAdmins");
    }
    
    // Admin logs collection rules
    match /admin_logs/{logId} {
      allow read: if isAdmin();
      allow create: if isAdmin();
      allow update, delete: if false; // Logs should be immutable
    }
    
    // Notifications collection rules
    match /notifications/{notificationId} {
      allow read: if 
        isAuthenticated() && 
        resource.data.userId == request.auth.uid;
      
      allow create: if isAdmin();
      
      allow update: if 
        isAuthenticated() && 
        resource.data.userId == request.auth.uid &&
        request.resource.data.diff(resource.data).affectedKeys().hasOnly(["isRead", "readAt"]);
      
      allow delete: if 
        isAuthenticated() && 
        resource.data.userId == request.auth.uid;
    }
    
    // Rider notifications collection rules
    match /rider_notifications/{notificationId} {
      allow read: if 
        isAuthenticated() && 
        resource.data.riderId == request.auth.uid;
      
      allow create: if isAdmin();
      
      allow update: if 
        isAuthenticated() && 
        resource.data.riderId == request.auth.uid &&
        request.resource.data.diff(resource.data).affectedKeys().hasOnly(["read"]);
      
      allow delete: if 
        isAuthenticated() && 
        resource.data.riderId == request.auth.uid;
    }
    
    // Admin notifications collection rules
    match /admin_notifications/{notificationId} {
      allow read: if 
        isAdmin() && 
        resource.data.adminId == request.auth.uid;
      
      allow create: if isAdmin();
      
      allow update: if 
        isAdmin() && 
        resource.data.adminId == request.auth.uid &&
        request.resource.data.diff(resource.data).affectedKeys().hasOnly(["read", "readAt"]);
      
      allow delete: if 
        isAdmin() && 
        resource.data.adminId == request.auth.uid;
    }
    
    // Rider locations collection rules
    match /rider_locations/{locationId} {
      allow read: if isAdmin();
      allow create: if 
        isApprovedRider() && 
        request.resource.data.riderId == request.auth.uid;
      allow update, delete: if false; // Location records should be immutable
    }
    
    // Admin rider tracking collection rules
    match /admin_rider_tracking/{trackingId} {
      allow read: if isAdmin();
      allow create: if 
        isApprovedRider() && 
        request.resource.data.riderId == request.auth.uid;
      allow update, delete: if isAdmin();
    }
    
    // Package scans collection rules
    match /packageScans/{scanId} {
      allow read: if 
        isAdmin() || 
        (isApprovedRider() && resource.data.scannedBy == request.auth.uid);
      
      allow create: if 
        isApprovedRider() && 
        request.resource.data.scannedBy == request.auth.uid;
      
      allow update, delete: if false; // Scan records should be immutable
    }
    
    // Order assignment requests collection rules
    match /orderAssignmentRequests/{requestId} {
      allow read: if 
        isAdmin() || 
        (isApprovedRider() && resource.data.riderId == request.auth.uid);
      
      allow create: if 
        isApprovedRider() && 
        request.resource.data.riderId == request.auth.uid;
      
      allow update: if isAdmin() && hasPermission("canAssignOrders");
      
      allow delete: if false; // Assignment requests should be immutable
    }
    
    // Earnings collection rules
    match /earnings/{earningId} {
      allow read: if 
        isAdmin() || 
        (isApprovedRider() && resource.data.riderId == request.auth.uid);
      
      allow create: if isAdmin();
      
      allow update, delete: if false; // Earnings records should be immutable
    }
  }
}
```

## Admin Panel Functions

Admins should use the following functions to manage riders and orders:

1. **View Pending Rider Applications**
   - View list of rider applications with status "pending"
   - View rider details and uploaded documents
   - Approve or reject applications

2. **Manage Riders**
   - View all approved riders
   - View rider details (profile, vehicle info, etc.)
   - View rider performance metrics
   - Suspend or reactivate riders

3. **Order Assignment**
   - View all pending orders
   - Manually assign orders to specific riders
   - View current order assignments
   - Reassign orders if needed

4. **Real-time Tracking**
   - View real-time locations of active riders
   - View order delivery progress
   - Track specific rider movements

5. **Analytics and Reporting**
   - View delivery metrics (completion rates, delivery times)
   - Generate reports on rider performance
   - View earnings reports

## Implementation Instructions

1. **For Admin Order Assignment**:
   - Use `adminAssignOrder` method in OrderRepository
   - Provide order ID, rider ID, and optional notes
   - Monitor notification to the rider

2. **For Rider Approval**:
   - Use `adminApproveRider` or `adminRejectRider` methods
   - Provide rider ID and approval notes or rejection reason

3. **For Riders to Accept/Reject Orders**:
   - System-assigned orders: Use `acceptOrder` and `rejectOrder`
   - Admin-assigned orders: Use `acceptAdminAssignedOrder` and `rejectAdminAssignedOrder`

4. **For Order Completion**:
   - Use standard workflow: `pickupOrder` followed by `deliverOrder`
   - Verify delivery using codes or customer signature as required 