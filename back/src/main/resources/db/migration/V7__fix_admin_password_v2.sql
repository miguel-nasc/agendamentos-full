UPDATE users
SET password = '{bcrypt}$2b$10$kIhUjgHywmSYCyMkf5oJNukrdGxCWFxJBW9HBCBR2mq9930i0R1.a'
WHERE user_name = 'admin';