INSERT INTO users (
    userName,
    fullName,
    password,
    account_non_expired,
    account_non_locked,
    credentials_non_expired,
    enabled
) VALUES (
             'admin',
             'Administrador',
             '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy',
             TRUE,
             TRUE,
             TRUE,
             TRUE
         );