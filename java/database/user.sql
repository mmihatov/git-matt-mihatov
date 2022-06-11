-- ********************************************************************************
-- This script creates the database users and grants them the necessary permissions
-- ********************************************************************************

CREATE USER book_library_owner
WITH PASSWORD 'finalcapstone';

GRANT ALL
ON ALL TABLES IN SCHEMA public
TO book_library_owner;

GRANT ALL
ON ALL SEQUENCES IN SCHEMA public
TO book_library_owner;

CREATE USER book_library_appuser
WITH PASSWORD 'finalcapstone';

GRANT SELECT, INSERT, UPDATE, DELETE
ON ALL TABLES IN SCHEMA public
TO book_library_appuser;

GRANT USAGE, SELECT
ON ALL SEQUENCES IN SCHEMA public
TO book_library_appuser;
