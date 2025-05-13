
CREATE TABLE IF NOT EXISTS Tenants (
  tenant_id INT AUTO_INCREMENT PRIMARY KEY,
  email VARCHAR(100) NOT NULL UNIQUE,
  name VARCHAR(50) NOT NULL,
  password VARCHAR(255) NOT NULL,
  phone VARCHAR(20) NOT NULL,
  search_criteria JSON NOT NULL
);
CREATE TABLE IF NOT EXISTS landlords (
  landlord_id INT AUTO_INCREMENT PRIMARY KEY,
  email VARCHAR(100) NOT NULL UNIQUE,
  name VARCHAR(50) NOT NULL,
  password VARCHAR(255) NOT NULL,
  phone VARCHAR(20) NOT NULL,
  verified BOOLEAN NOT NULL DEFAULT FALSE,
  created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
  CREATE TABLE IF NOT EXISTS houses (
  house_id INT AUTO_INCREMENT PRIMARY KEY,
  address VARCHAR(100) NOT NULL UNIQUE,
  status ENUM('available', 'rented', 'maintenance') NOT NULL,
  information TEXT NOT NULL,
  facilities JSON NOT NULL,
  landlord_id INT NOT NULL,
  created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  FOREIGN KEY (landlord_id) REFERENCES landlords(landlord_id)
);
  CREATE TABLE IF NOT EXISTS rents(
  rent_id INT AUTO_INCREMENT PRIMARY KEY,
  tenant_id INT NOT NULL,
  house_id INT NOT NULL,
  deposit DECIMAL(10,2) NOT NULL,
  rent_criteria JSON NOT NULL,
  signing_date DATE NOT NULL,
  status ENUM('pending', 'active', 'expired', 'terminated') NOT NULL,
  subsidy BOOLEAN DEFAULT FALSE,
  created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

  FOREIGN KEY (tenant_id) REFERENCES tenants(tenant_id),
  FOREIGN KEY (house_id) REFERENCES houses(house_id)
 );
  CREATE TABLE IF NOT EXISTS house_photos (
  photo_id INT AUTO_INCREMENT PRIMARY KEY,
  house_id INT NOT NULL,
  photo_url VARCHAR(255) NOT NULL,
  photo_date DATETIME NOT NULL,
  is_primary BOOLEAN DEFAULT FALSE,
  FOREIGN KEY (house_id) REFERENCES houses(house_id)
);
  CREATE TABLE IF NOT EXISTS forums (
  post_id INT AUTO_INCREMENT PRIMARY KEY,
  title VARCHAR(100) NOT NULL,
  content TEXT NOT NULL,
  comment_count INT DEFAULT 0,
  share_count INT DEFAULT 0,
  like_count INT DEFAULT 0,
  dislike_count INT DEFAULT 0,
  tenant_id INT NOT NULL,
  post_date DATETIME NOT NULL,
  FOREIGN KEY (tenant_id) REFERENCES tenants(tenant_id)
);
  CREATE TABLE IF NOT EXISTS forum_photos (
  photo_id INT AUTO_INCREMENT PRIMARY KEY,
  post_id INT NOT NULL,
  photo_url VARCHAR(255) NOT NULL,
  photo_date DATETIME NOT NULL,
  FOREIGN KEY (post_id) REFERENCES forums(post_id)
);
  CREATE TABLE IF NOT EXISTS house_reviews (
  review_id INT AUTO_INCREMENT PRIMARY KEY,
  house_id INT NOT NULL,
  reviewer_id INT NOT NULL,
  reviewer_type ENUM('tenant', 'landlord') NOT NULL,
  review_date DATETIME NOT NULL,
  rating INT NOT NULL CHECK (rating BETWEEN 1 AND 5),
  comment TEXT,
  FOREIGN KEY (house_id) REFERENCES houses(house_id)
); 
  CREATE TABLE IF NOT EXISTS reports (
  report_id INT AUTO_INCREMENT PRIMARY KEY,
  reporter_id INT NOT NULL,
  reporter_type ENUM('tenant', 'landlord') NOT NULL,
  target_type ENUM('house', 'review', 'post', 'user') NOT NULL,
  target_id INT NOT NULL,
  report_date DATETIME NOT NULL,
  information TEXT NOT NULL,
  status ENUM('pending', 'resolved', 'rejected') DEFAULT 'pending'
);
  CREATE TABLE IF NOT EXISTS reviews (
  review_id INT AUTO_INCREMENT PRIMARY KEY,
  target_type ENUM('house', 'landlord', 'tenant') NOT NULL,
  target_id INT NOT NULL,
  reviewer_id INT NOT NULL,
  reviewer_type ENUM('tenant', 'landlord') NOT NULL,
  review_history TEXT,
  review_date DATETIME NOT NULL,
  rating INT NOT NULL CHECK (rating BETWEEN 1 AND 5));
