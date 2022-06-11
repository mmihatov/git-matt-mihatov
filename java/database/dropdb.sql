-- **************************************************************
-- This script destroys the database and associated users
-- **************************************************************

-- The following line terminates any active connections to the database so that it can be destroyed
SELECT pg_terminate_backend(pid)
FROM pg_stat_activity
WHERE datname = 'book_library';

DROP DATABASE book_library;

DROP USER book_library_owner;
DROP USER book_library_appuser;
