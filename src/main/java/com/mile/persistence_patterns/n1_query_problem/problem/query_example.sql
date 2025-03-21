-- Selects all the customers
SELECT * FROM customer;

-- in the for loop, executes
-- loop 1
SELECT * FROM orders WHERE customer_id = ?;
-- loop 2
SELECT * FROM orders WHERE customer_id = ?;
-- loop n
SELECT * FROM orders WHERE customer_id = ?;

-- For each customer, executes the same query to fetch their orders
-- If we have 1000 customers, we'd execute 1001 queries instead of 1 :(