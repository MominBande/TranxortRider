/**
 * Firestore Database Setup Script for TranxortRider
 * 
 * This script initializes the Firestore database with the required collections and sample documents
 * based on the comprehensive schema design for the TranxortRider application.
 * 
 * Prerequisites:
 * 1. Node.js installed
 * 2. Firebase Admin SDK credentials (serviceAccountKey.json)
 * 
 * Usage:
 * 1. Save your Firebase service account key as serviceAccountKey.json in the same directory
 * 2. Run: npm install firebase-admin
 * 3. Run: node firestore_setup.js
 */

const admin = require('firebase-admin');
const serviceAccount = require('./serviceAccountKey.json');

// Initialize Firebase Admin SDK
admin.initializeApp({
  credential: admin.credential.cert(serviceAccount)
});

const db = admin.firestore();
const FieldValue = admin.firestore.FieldValue;

// Current timestamp
const now = admin.firestore.Timestamp.now();
const yesterday = admin.firestore.Timestamp.fromDate(new Date(Date.now() - 24 * 60 * 60 * 1000));
const lastWeek = admin.firestore.Timestamp.fromDate(new Date(Date.now() - 7 * 24 * 60 * 60 * 1000));

// Sample data
const sampleData = {
  // Sample admin
  admin: {
    id: 'admin123',
    email: 'admin@tranxortrider.com',
    name: 'Admin User',
    phone: '+1234567890',
    role: 'super_admin',
    permissions: {
      canApproveRiders: true,
      canAssignOrders: true,
      canViewAnalytics: true,
      canManageAdmins: true
    },
    createdAt: now,
    updatedAt: now,
    lastLogin: now,
    fcmToken: 'admin_fcm_token_sample',
    active: true
  },
  
  // Sample rider user
  user: {
    id: 'rider123',
    name: 'John Rider',
    email: 'rider@tranxortrider.com',
    phone: '+1234567890',
    address: '123 Rider Street',
    city: 'Rider City',
    state: 'Rider State',
    zipCode: '12345',
    vehicleType: 'Motorcycle',
    vehicleMake: 'Honda',
    vehicleModel: 'CBR',
    vehiclePlate: 'RD-1234',
    isAvailable: true,
    photoUrl: 'https://example.com/profile/rider123.jpg',
    fcmToken: 'rider_fcm_token_sample',
    createdAt: lastWeek,
    updatedAt: yesterday,
    isVerified: true,
    verificationStatus: 'verified'
  },
  
  // Sample rider profile
  rider: {
    userId: 'rider123',
    name: 'John Rider',
    email: 'rider@tranxortrider.com',
    phone: '+1234567890',
    status: 'active',
    isOnline: true,
    applicationStatus: 'approved',
    approvedBy: 'admin123',
    approvalDate: yesterday,
    location: {
      latitude: 37.7749,
      longitude: -122.4194,
      timestamp: Date.now()
    },
    totalEarnings: 1250.75,
    lastLocationUpdate: now,
    lastStatusUpdate: now,
    createdAt: lastWeek,
    documents: {
      governmentId: {
        url: 'https://kwbbgaxwqrfaousxwbtd.supabase.co/storage/v1/object/public/riders-info/riders/rider123/documents/government_id_123456.jpg',
        status: 'approved',
        reviewedBy: 'admin123',
        reviewDate: yesterday
      },
      driverLicense: {
        url: 'https://kwbbgaxwqrfaousxwbtd.supabase.co/storage/v1/object/public/riders-info/riders/rider123/documents/driver_license_123456.jpg',
        status: 'approved',
        reviewedBy: 'admin123',
        reviewDate: yesterday
      },
      vehicleRegistration: {
        url: 'https://kwbbgaxwqrfaousxwbtd.supabase.co/storage/v1/object/public/riders-info/riders/rider123/documents/vehicle_registration_123456.jpg',
        status: 'approved',
        reviewedBy: 'admin123',
        reviewDate: yesterday
      },
      vehicleInsurance: {
        url: 'https://kwbbgaxwqrfaousxwbtd.supabase.co/storage/v1/object/public/riders-info/riders/rider123/documents/vehicle_insurance_123456.jpg',
        status: 'approved',
        reviewedBy: 'admin123',
        reviewDate: yesterday
      }
    }
  },
  
  // Sample pending rider user
  pendingUser: {
    id: 'rider456',
    name: 'Jane Pending',
    email: 'pending@tranxortrider.com',
    phone: '+0987654321',
    address: '456 Pending Street',
    city: 'Pending City',
    state: 'Pending State',
    zipCode: '54321',
    vehicleType: 'Car',
    vehicleMake: 'Toyota',
    vehicleModel: 'Corolla',
    vehiclePlate: 'PD-5678',
    isAvailable: false,
    photoUrl: 'https://example.com/profile/rider456.jpg',
    fcmToken: 'pending_rider_fcm_token',
    createdAt: now,
    updatedAt: now,
    isVerified: false,
    verificationStatus: 'pending'
  },
  
  // Sample pending rider profile
  pendingRider: {
    userId: 'rider456',
    name: 'Jane Pending',
    email: 'pending@tranxortrider.com',
    phone: '+0987654321',
    status: 'pending',
    isOnline: false,
    applicationStatus: 'pending',
    location: {
      latitude: 37.7833,
      longitude: -122.4167,
      timestamp: Date.now()
    },
    totalEarnings: 0,
    lastLocationUpdate: now,
    lastStatusUpdate: now,
    createdAt: now,
    documents: {
      governmentId: {
        url: 'https://kwbbgaxwqrfaousxwbtd.supabase.co/storage/v1/object/public/riders-info/riders/rider456/documents/government_id_987654.jpg',
        status: 'pending',
        reviewedBy: null,
        reviewDate: null
      },
      driverLicense: {
        url: 'https://kwbbgaxwqrfaousxwbtd.supabase.co/storage/v1/object/public/riders-info/riders/rider456/documents/driver_license_987654.jpg',
        status: 'pending',
        reviewedBy: null,
        reviewDate: null
      },
      vehicleRegistration: {
        url: 'https://kwbbgaxwqrfaousxwbtd.supabase.co/storage/v1/object/public/riders-info/riders/rider456/documents/vehicle_registration_987654.jpg',
        status: 'pending',
        reviewedBy: null,
        reviewDate: null
      }
    }
  },
  
  // Sample order
  order: {
    id: 'order123',
    orderId: 'ORD123456',
    customerName: 'Customer Name',
    customerAddress: '456 Customer Street, Customer City',
    customerPhone: '+0987654321',
    restaurantName: 'Restaurant Name',
    restaurantAddress: '789 Restaurant Avenue, Restaurant City',
    restaurantPhone: '+1122334455',
    items: [
      {
        name: 'Item 1',
        quantity: 2,
        price: 9.99,
        notes: 'Extra sauce'
      },
      {
        name: 'Item 2',
        quantity: 1,
        price: 12.99,
        notes: 'No onions'
      }
    ],
    totalAmount: 32.97,
    deliveryFee: 4.99,
    status: 'PENDING',
    paymentMethod: 'CARD',
    specialInstructions: 'Please ring doorbell',
    distance: 3.5,
    estimatedDeliveryTime: 30,
    createdAt: now,
    updatedAt: now,
    acceptedAt: null,
    pickedUpAt: null,
    deliveredAt: null,
    completedAt: null,
    canceledAt: null,
    cancelReason: null,
    latitude: 37.7833,
    longitude: -122.4167,
    customerLatitude: 37.7749,
    customerLongitude: -122.4194,
    restaurantLatitude: 37.7833,
    restaurantLongitude: -122.4167,
    batchId: null,
    assignedRider: null,
    assignedBy: null,
    assignmentMethod: null,
    assignmentTimestamp: null,
    adminNotes: null,
    rejectedRiders: [],
    rejectionReasons: {},
    deliveryCode: '1234',
    packageCode: 'PKG-123456'
  },
  
  // Sample assigned order
  assignedOrder: {
    id: 'order456',
    orderId: 'ORD456789',
    customerName: 'Assigned Customer',
    customerAddress: '789 Assigned Street, Assigned City',
    customerPhone: '+1122334455',
    restaurantName: 'Assigned Restaurant',
    restaurantAddress: '123 Assigned Avenue, Assigned City',
    restaurantPhone: '+5566778899',
    items: [
      {
        name: 'Assigned Item 1',
        quantity: 1,
        price: 14.99,
        notes: 'Spicy'
      },
      {
        name: 'Assigned Item 2',
        quantity: 2,
        price: 8.99,
        notes: ''
      }
    ],
    totalAmount: 32.97,
    deliveryFee: 3.99,
    status: 'ASSIGNED',
    paymentMethod: 'CASH',
    specialInstructions: 'Leave at door',
    distance: 2.8,
    estimatedDeliveryTime: 25,
    createdAt: yesterday,
    updatedAt: now,
    acceptedAt: null,
    pickedUpAt: null,
    deliveredAt: null,
    completedAt: null,
    canceledAt: null,
    cancelReason: null,
    latitude: 37.7833,
    longitude: -122.4167,
    customerLatitude: 37.7749,
    customerLongitude: -122.4194,
    restaurantLatitude: 37.7833,
    restaurantLongitude: -122.4167,
    batchId: null,
    assignedRider: 'rider123',
    assignedBy: 'admin123',
    assignmentMethod: 'manual',
    assignmentTimestamp: now,
    adminNotes: 'Priority delivery',
    rejectedRiders: [],
    rejectionReasons: {},
    deliveryCode: '5678',
    packageCode: 'PKG-456789'
  },
  
  // Sample batch
  batch: {
    id: 'batch123',
    riderId: 'rider123',
    isActive: true,
    isCompleted: false,
    estimatedEarnings: 25.50,
    estimatedTimeMinutes: 45,
    createdAt: now,
    updatedAt: now,
    completedAt: null
  },
  
  // Sample batch order
  batchOrder: {
    orderId: 'order123',
    addedAt: now,
    sequence: 1
  },
  
  // Sample document
  document: {
    id: 'doc123',
    userId: 'rider123',
    documentType: 'DRIVER_LICENSE',
    fileName: 'driver_license_123456',
    fileUrl: 'https://kwbbgaxwqrfaousxwbtd.supabase.co/storage/v1/object/public/riders-info/riders/rider123/documents/driver_license_123456.jpg',
    status: 'approved',
    uploadedAt: lastWeek,
    updatedAt: yesterday,
    reviewedAt: yesterday,
    reviewedBy: 'admin123',
    rejectionReason: '',
    storageProvider: 'supabase',
    storagePath: 'riders/rider123/documents/driver_license_123456.jpg'
  },
  
  // Sample pending document
  pendingDocument: {
    id: 'doc456',
    userId: 'rider456',
    documentType: 'DRIVER_LICENSE',
    fileName: 'driver_license_987654',
    fileUrl: 'https://kwbbgaxwqrfaousxwbtd.supabase.co/storage/v1/object/public/riders-info/riders/rider456/documents/driver_license_987654.jpg',
    status: 'pending',
    uploadedAt: now,
    updatedAt: now,
    reviewedAt: null,
    reviewedBy: null,
    rejectionReason: '',
    storageProvider: 'supabase',
    storagePath: 'riders/rider456/documents/driver_license_987654.jpg'
  },
  
  // Sample notification
  notification: {
    id: 'notif123',
    userId: 'rider123',
    title: 'New Order Available',
    message: 'There is a new order available for pickup.',
    type: 'new_order',
    orderId: 'order123',
    isRead: false,
    timestamp: now
  },
  
  // Sample rider notification
  riderNotification: {
    id: 'rnotif123',
    riderId: 'rider123',
    type: 'order_assigned',
    title: 'Order Assigned',
    message: 'You have been assigned a new order.',
    orderId: 'order456',
    read: false,
    createdAt: now
  },
  
  // Sample admin notification
  adminNotification: {
    id: 'anotif123',
    adminId: 'admin123',
    type: 'rider_application',
    title: 'New Rider Application',
    message: 'A new rider has applied and is awaiting approval.',
    relatedId: 'rider456',
    read: false,
    createdAt: now,
    readAt: null
  },
  
  // Sample rider location
  riderLocation: {
    id: 'loc123',
    riderId: 'rider123',
    latitude: 37.7749,
    longitude: -122.4194,
    accuracy: 10.5,
    speed: 15.2,
    bearing: 90.0,
    timestamp: Date.now(),
    createdAt: now
  },
  
  // Sample admin rider tracking
  adminRiderTracking: {
    id: 'track123',
    riderId: 'rider123',
    latitude: 37.7749,
    longitude: -122.4194,
    speed: 15.2,
    bearing: 90.0,
    timestamp: Date.now(),
    createdAt: now
  },
  
  // Sample package scan
  packageScan: {
    id: 'scan123',
    orderId: 'order456',
    packageCode: 'PKG-456789',
    scannedAt: now,
    scannedBy: 'rider123',
    location: {
      latitude: 37.7749,
      longitude: -122.4194
    }
  },
  
  // Sample order assignment request
  orderAssignmentRequest: {
    id: 'req123',
    riderId: 'rider123',
    orderId: 'order123',
    requestedAt: now,
    status: 'PENDING',
    processedAt: null,
    processedBy: null
  },
  
  // Sample earnings
  earning: {
    id: 'earn123',
    riderId: 'rider123',
    orderId: 'order456',
    amount: 12.50,
    date: now,
    description: 'Completed delivery',
    orderReference: 'ORD456789',
    type: 'delivery',
    status: 'paid'
  },
  
  // Sample admin log
  adminLog: {
    id: 'log123',
    adminId: 'admin123',
    action: 'rider_approved',
    targetId: 'rider123',
    details: {
      notes: 'All documents verified',
      previousStatus: 'pending',
      newStatus: 'approved'
    },
    timestamp: yesterday,
    ipAddress: '192.168.1.1'
  }
};

/**
 * Create collections and documents
 */
async function setupDatabase() {
  try {
    console.log('Starting database setup...');
    
    // Create collections with sample documents
    const collections = [
      { name: 'admins', docId: 'admin123', data: sampleData.admin },
      { name: 'users', docId: 'rider123', data: sampleData.user },
      { name: 'users', docId: 'rider456', data: sampleData.pendingUser },
      { name: 'riders', docId: 'rider123', data: sampleData.rider },
      { name: 'riders', docId: 'rider456', data: sampleData.pendingRider },
      { name: 'orders', docId: 'order123', data: sampleData.order },
      { name: 'orders', docId: 'order456', data: sampleData.assignedOrder },
      { name: 'batches', docId: 'batch123', data: sampleData.batch },
      { name: 'userDocuments', docId: 'doc123', data: sampleData.document },
      { name: 'userDocuments', docId: 'doc456', data: sampleData.pendingDocument },
      { name: 'notifications', docId: 'notif123', data: sampleData.notification },
      { name: 'rider_notifications', docId: 'rnotif123', data: sampleData.riderNotification },
      { name: 'admin_notifications', docId: 'anotif123', data: sampleData.adminNotification },
      { name: 'rider_locations', docId: 'loc123', data: sampleData.riderLocation },
      { name: 'admin_rider_tracking', docId: 'track123', data: sampleData.adminRiderTracking },
      { name: 'packageScans', docId: 'scan123', data: sampleData.packageScan },
      { name: 'orderAssignmentRequests', docId: 'req123', data: sampleData.orderAssignmentRequest },
      { name: 'earnings', docId: 'earn123', data: sampleData.earning },
      { name: 'admin_logs', docId: 'log123', data: sampleData.adminLog }
    ];
    
    // Create batch for batch operations
    const batch = db.batch();
    
    // Add all documents to batch
    collections.forEach(collection => {
      const docRef = db.collection(collection.name).doc(collection.docId);
      batch.set(docRef, collection.data);
      console.log(`Added document to ${collection.name} collection: ${collection.docId}`);
    });
    
    // Commit the batch
    await batch.commit();
    console.log('Batch committed successfully');
    
    // Add order to batch's orders subcollection (separate operation)
    await db.collection('batches').doc('batch123')
      .collection('orders').doc('order123')
      .set(sampleData.batchOrder);
    console.log('Added order to batch orders subcollection');
    
    console.log('Database setup completed successfully!');
  } catch (error) {
    console.error('Error setting up database:', error);
  }
}

// Run the setup
setupDatabase().then(() => {
  console.log('Script execution completed.');
  process.exit(0);
}).catch(error => {
  console.error('Script execution failed:', error);
  process.exit(1);
}); 