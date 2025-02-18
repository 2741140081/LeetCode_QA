-- LeetCode 175.组合两个表 

-- E1:
CREATE TABLE IF NOT EXISTS Person (personId INT, firstName VARCHAR(255), lastName VARCHAR(255));
CREATE TABLE IF NOT EXISTS Address (addressId INT, personId INT, city VARCHAR(255), state VARCHAR(255));

-- Truncate table Person
INSERT INTO Person (personId, lastName, firstName) VALUES ('1', 'Wang', 'Allen');
INSERT INTO Person (personId, lastName, firstName) VALUES ('2', 'Alice', 'Bob');
-- Truncate table Address
INSERT INTO Address (addressId, personId, city, state) VALUES ('1', '2', 'New York City', 'New York');
INSERT INTO Address (addressId, personId, city, state) VALUES ('2', '3', 'Leetcode', 'California');

-- 描述: 编写解决方案，报告 Person 表中每个人的姓、名、城市和州。如果 person_id 的地址不在 Address表中，则报告为 null 。

-- Result:
SELECT p.firstName, p.lastName, a.city, a.state
FROM Person p 
LEFT JOIN Address a ON p.personId = a.personId



-- LeetCode 176. 第二高的薪水

-- E1:
CREATE TABLE IF NOT EXISTS Employee (id INT, salary INT);
-- Truncate table Employee
INSERT INTO Employee (id, salary) VALUES ('1', '100');
INSERT INTO Employee (id, salary) VALUES ('2', '200');
INSERT INTO Employee (id, salary) VALUES ('3', '300');



