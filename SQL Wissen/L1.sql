
-- ============================================
-- PostgreSQL Transactions Demo
-- Author: Joshua Dmello (Demo Script)
-- Topics: BEGIN/COMMIT/ROLLBACK, TRUNCATE, SAVEPOINT, duplicates
-- ============================================

-- 0) Clean slate --------------------------------------------------------------
DROP TABLE IF EXISTS employee_demo;

CREATE TABLE employee_demo (
    id SERIAL PRIMARY KEY,
    name TEXT NOT NULL,
    age INT NOT NULL,
    CONSTRAINT uq_employee_demo UNIQUE (name, age)  -- to demo duplicates
);

-- Helper: Quick view function (inline as a query)
-- We'll use this SELECT often to check state:
-- SELECT id, name, age FROM employee_demo ORDER BY id;

-- 1) Show empty table ---------------------------------------------------------
SELECT 'Step 1: Fresh table' AS note;
SELECT * FROM employee_demo;

-- 2) COMMIT demo: data persists ----------------------------------------------
SELECT 'Step 2: BEGIN + INSERT + COMMIT (data persists)' AS note;
BEGIN;
    INSERT INTO employee_demo (name, age) VALUES
        ('Rajendra', 32),
        ('Anita', 29);
COMMIT;

SELECT * FROM employee_demo ORDER BY id;

-- 3) ROLLBACK demo: data is discarded ----------------------------------------
SELECT 'Step 3: BEGIN + INSERT + ROLLBACK (no changes saved)' AS note;
BEGIN;
    INSERT INTO employee_demo (name, age) VALUES
        ('Temp1', 20),
        ('Temp2', 21);
    -- View inside the transaction (visible to this transaction)
    SELECT 'Inside TX (before rollback)' AS context, * FROM employee_demo ORDER BY id;
ROLLBACK;

-- After rollback, Temp1/Temp2 should NOT exist
SELECT 'After rollback' AS context, * FROM employee_demo ORDER BY id;

-- 4) TRUNCATE inside a transaction + ROLLBACK --------------------------------
-- Note: In PostgreSQL, TRUNCATE is transactional (it can be rolled back).
SELECT 'Step 4: TRUNCATE inside BEGIN then ROLLBACK (table restored)' AS note;
BEGIN;
    TRUNCATE TABLE employee_demo;
    SELECT 'Inside TX after TRUNCATE' AS context, * FROM employee_demo;
ROLLBACK;

-- After rollback, original rows should be back
SELECT 'After rollback of TRUNCATE' AS context, * FROM employee_demo ORDER BY id;

-- 5) TRUNCATE + COMMIT (permanent) -------------------------------------------
SELECT 'Step 5: TRUNCATE + COMMIT (table cleared permanently)' AS note;
BEGIN;
    TRUNCATE TABLE employee_demo;
COMMIT;

SELECT 'After TRUNCATE commit' AS context, * FROM employee_demo;

-- 6) SAVEPOINT demo: partial rollback inside a transaction -------------------
SELECT 'Step 6: SAVEPOINT (partial rollback within a transaction)' AS note;
BEGIN;
    INSERT INTO employee_demo (name, age) VALUES
        ('Meera', 26),
        ('Karan', 24);

    SAVEPOINT sp_before_more;

    INSERT INTO employee_demo (name, age) VALUES
        ('RevertMe1', 22),
        ('RevertMe2', 23);

    -- Roll back only the last two inserts
    ROLLBACK TO SAVEPOINT sp_before_more;

    -- Continue and commit the earlier part
    INSERT INTO employee_demo (name, age) VALUES ('FinalInsideTX', 28);
COMMIT;

SELECT * FROM employee_demo ORDER BY id;

-- 7) Duplicate insert demo (unique constraint on (name, age)) ----------------
SELECT 'Step 7: Duplicate insert demo (expect one failure)' AS note;

-- First insert succeeds
INSERT INTO employee_demo (name, age) VALUES ('Sharma', 23);

-- Second insert with same (name, age) will raise a unique violation error
-- To keep the script going in psql, wrap duplicates in a block that handles errors.
DO $$
BEGIN
    BEGIN
        INSERT INTO employee_demo (name, age) VALUES ('Sharma', 23);
        INSERT INTO employee_demo (name, age) VALUES ('Sharma', 23);
        INSERT INTO employee_demo (name, age) VALUES ('Sharma', 23);
    EXCEPTION WHEN unique_violation THEN
        RAISE NOTICE 'Duplicate (name, age) detected. Skipping the rest of duplicate inserts.';
        -- Optionally: nothing else; we’re demonstrating the constraint
    END;
END$$;

SELECT * FROM employee_demo ORDER BY id;

-- 8) DELETE vs ROLLBACK demo -------------------------------------------------
SELECT 'Step 8: DELETE inside TX + ROLLBACK (rows restored)' AS note;
BEGIN;
    DELETE FROM employee_demo;         -- remove everything
    SELECT 'Inside TX after DELETE' AS context, COUNT(*) AS rows_now FROM employee_demo;
ROLLBACK;

SELECT 'After ROLLBACK of DELETE' AS context, COUNT(*) AS rows_restored FROM employee_demo;

-- 9) Cleanup (optional) -------------------------------------------------------
-- Uncomment if you want to leave no trace:
-- DROP TABLE IF EXISTS employee_demo;
