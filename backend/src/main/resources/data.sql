INSERT INTO users (username, password, email, phone_number, role, created_at)
VALUES 
('admin', '$2a$10$rDZPYzXj.YFu.gq.22WHxOeYHWXXNYnX9VKOkARxwUYYGDOfjkOYi', 'admin@example.com', '0912345678', 'ADMIN', NOW()),
('test', '$2a$10$rDZPYzXj.YFu.gq.22WHxOeYHWXXNYnX9VKOkARxwUYYGDOfjkOYi', 'test@example.com', '0987654321', 'USER', NOW());

INSERT INTO properties (title, description, address, price, bedrooms, bathrooms, square_meters, owner_id, status)
SELECT 
    '測試房源',
    '這是一個測試用的房源描述，位於市中心，交通便利，環境優美。',
    '台北市信義區信義路五段7號',
    15000.00,
    2,
    1,
    30.5,
    u.id,
    'AVAILABLE'
FROM users u
WHERE u.username = 'test'
LIMIT 1;

INSERT INTO reviews (property_id, user_id, rating, comment, created_at)
SELECT 
    p.id,
    u.id,
    5,
    '非常好的房源，環境整潔，房東很親切！',
    NOW()
FROM properties p
CROSS JOIN users u
WHERE u.username = 'admin' AND p.title = '測試房源'
LIMIT 1;

INSERT INTO viewings (property_id, user_id, viewing_time, status, notes)
SELECT 
    p.id,
    u.id,
    DATE_ADD(NOW(), INTERVAL 1 DAY),
    'PENDING',
    '希望能在下午3點看房'
FROM properties p
CROSS JOIN users u
WHERE u.username = 'admin' AND p.title = '測試房源'
LIMIT 1; 