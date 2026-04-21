INSERT INTO sys_user (username, password)
SELECT 'user01', '123456'
WHERE NOT EXISTS (SELECT 1 FROM sys_user WHERE username = 'user01');

INSERT INTO sys_user (username, password)
SELECT 'user02', '123456'
WHERE NOT EXISTS (SELECT 1 FROM sys_user WHERE username = 'user02');

INSERT INTO sys_user (username, password)
SELECT 'user03', '123456'
WHERE NOT EXISTS (SELECT 1 FROM sys_user WHERE username = 'user03');

INSERT INTO sys_user (username, password)
SELECT 'user04', '123456'
WHERE NOT EXISTS (SELECT 1 FROM sys_user WHERE username = 'user04');

INSERT INTO sys_user (username, password)
SELECT 'user05', '123456'
WHERE NOT EXISTS (SELECT 1 FROM sys_user WHERE username = 'user05');

INSERT INTO sys_user (username, password)
SELECT 'user06', '123456'
WHERE NOT EXISTS (SELECT 1 FROM sys_user WHERE username = 'user06');

INSERT INTO user_info (real_name, phone, address, user_id)
SELECT '张三', '13800000001', '北京市朝阳区', id
FROM sys_user
WHERE username = 'user01'
  AND NOT EXISTS (
      SELECT 1 FROM user_info WHERE user_id = (SELECT id FROM sys_user WHERE username = 'user01')
  );

INSERT INTO user_info (real_name, phone, address, user_id)
SELECT '李四', '13900000002', '上海市浦东新区', id
FROM sys_user
WHERE username = 'user02'
  AND NOT EXISTS (
      SELECT 1 FROM user_info WHERE user_id = (SELECT id FROM sys_user WHERE username = 'user02')
  );
