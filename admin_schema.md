# Admin Schema for TranxortRider Database

## Admins Collection

The `admins` collection will store information about administrators who can approve rider applications and manage order assignments.

```
Collection: "admins"
Document ID: [unique admin ID]
Fields:
- id: String (same as document ID)
- email: String
- name: String
- phone: String (optional)
- role: String (can be "super_admin" or "manager")
- permissions: Map<String, Boolean> (specific permissions)
  - canApproveRiders: Boolean
  - canAssignOrders: Boolean
  - canViewAnalytics: Boolean
  - canManageAdmins: Boolean
- createdAt: Timestamp
- updatedAt: Timestamp
- lastLogin: Timestamp
- fcmToken: String (for notifications)
- active: Boolean (if admin account is active)
```

## Rider Application Workflow

The rider approval process will use the existing `riders` collection but with extended fields:

```
Collection: "riders"
Document ID: [rider user ID]
Additional Fields:
- applicationStatus: String (pending, approved, rejected)
- approvedBy: String (admin ID who approved the application)
- approvalDate: Timestamp
- rejectionReason: String (if application was rejected)
- documents: Map<String, Object> (verification documents)
  - governmentId: Object
    - url: String
    - status: String (pending, approved, rejected)
    - reviewedBy: String (admin ID)
    - reviewDate: Timestamp
  - driverLicense: Object
    - url: String
    - status: String (pending, approved, rejected)
    - reviewedBy: String (admin ID)
    - reviewDate: Timestamp
  - vehicleRegistration: Object
    - url: String
    - status: String (pending, approved, rejected)
    - reviewedBy: String (admin ID)
    - reviewDate: Timestamp
  - vehicleInsurance: Object
    - url: String
    - status: String (pending, approved, rejected)
    - reviewedBy: String (admin ID)
    - reviewDate: Timestamp
  - workAuthorization: Object
    - url: String
    - status: String (pending, approved, rejected)
    - reviewedBy: String (admin ID)
    - reviewDate: Timestamp
```

## Order Assignment

Extend the `orders` collection to track order assignments:

```
Collection: "orders"
Document ID: [order ID]
Additional Fields:
- assignedRider: String (rider ID)
- assignedBy: String (admin ID who assigned the order)
- assignmentMethod: String ("automatic" or "manual")
- assignmentTimestamp: Timestamp
- adminNotes: String (notes from admin about this order assignment)
```

## Admin Activity Log

New collection to track admin activities:

```
Collection: "admin_logs"
Document ID: [auto-generated]
Fields:
- adminId: String (admin who performed the action)
- action: String (e.g., "rider_approved", "order_assigned", "rider_rejected")
- targetId: String (ID of the affected resource - rider ID, order ID, etc.)
- details: Map<String, Any> (additional details about the action)
- timestamp: Timestamp
- ipAddress: String (optional)
```

## Admin Notifications

New collection for admin notifications:

```
Collection: "admin_notifications"
Document ID: [auto-generated]
Fields:
- adminId: String (recipient admin)
- type: String (e.g., "new_rider_application", "rider_document_uploaded")
- title: String
- message: String
- relatedId: String (ID of the related resource)
- read: Boolean
- createdAt: Timestamp
- readAt: Timestamp (optional)
```

## Relationships
- Admins can approve multiple riders (one-to-many)
- Admins can assign multiple orders (one-to-many)
- Admins can have multiple activity logs (one-to-many)
- Admins can have multiple notifications (one-to-many) 