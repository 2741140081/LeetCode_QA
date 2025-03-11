-- LeetCode 175.组合两个表 
-- 描述: 编写解决方案，报告 Person 表中每个人的姓、名、城市和州。如果 person_id 的地址不在 Address表中，则报告为 null 。

-- E1:
CREATE TABLE IF NOT EXISTS Person (personId INT, firstName VARCHAR(255), lastName VARCHAR(255));
CREATE TABLE IF NOT EXISTS Address (addressId INT, personId INT, city VARCHAR(255), state VARCHAR(255));

-- Truncate table Person
INSERT INTO Person (personId, lastName, firstName) VALUES ('1', 'Wang', 'Allen');
INSERT INTO Person (personId, lastName, firstName) VALUES ('2', 'Alice', 'Bob');
-- Truncate table Address
INSERT INTO Address (addressId, personId, city, state) VALUES ('1', '2', 'New York City', 'New York');
INSERT INTO Address (addressId, personId, city, state) VALUES ('2', '3', 'Leetcode', 'California');

-- Result: AC
SELECT p.firstName, p.lastName, a.city, a.state
FROM Person p 
LEFT JOIN Address a ON p.personId = a.personId



-- LeetCode 176. 第二高的薪水
-- 描述: 查询并返回 Employee 表中第二高的 不同 薪水 。如果不存在第二高的薪水，查询应该返回 null(Pandas 则返回 None) 。

-- E1:
CREATE TABLE IF NOT EXISTS Employee (id INT, salary INT);
-- Truncate table Employee
INSERT INTO Employee (id, salary) VALUES ('1', '100');
INSERT INTO Employee (id, salary) VALUES ('2', '200');
INSERT INTO Employee (id, salary) VALUES ('3', '300');


-- Result: AC 374ms(35.26%)
SELECT MAX(salary) AS SecondHighestSalary 
FROM Employee
WHERE salary < (SELECT MAX(salary) FROM Employee);



-- LeetCode 177. 第N高的薪水
-- 描述: 查询 Employee 表中第 n 高的工资。如果没有第 n 个最高工资，查询结果应该为 null 。

-- E1:

CREATE TABLE IF NOT EXISTS Employee (Id INT, Salary INT);
-- Truncate table Employee
INSERT INTO Employee (id, salary) VALUES ('1', '100');
INSERT INTO Employee (id, salary) VALUES ('2', '200');
INSERT INTO Employee (id, salary) VALUES ('3', '300');

-- n = 2

-- 输出:  getNthHighestSalary(2) = 200

-- Result: AC 654ms(25.51%)
-- 详细信息见函数getNthHighestSalary(n int)
SELECT getNthHighestSalary(2); -- 200

DROP TABLE IF EXISTS Employee;



-- LeetCode 178. 分数排名
/*
描述: 
编写一个解决方案来查询分数的排名。排名按以下规则计算:

分数应按从高到低排列。
如果两个分数相等，那么两个分数的排名应该相同。
在排名相同的分数后，排名数应该是下一个连续的整数。换句话说，排名之间不应该有空缺的数字。
按 score 降序返回结果表。
*/

-- E1:

CREATE TABLE IF NOT EXISTS Scores (id INT, score DECIMAL(3,2));
-- Truncate table Scores
INSERT INTO Scores (id, score) VALUES ('1', '3.5');
INSERT INTO Scores (id, score) VALUES ('2', '3.65');
INSERT INTO Scores (id, score) VALUES ('3', '4.0');
INSERT INTO Scores (id, score) VALUES ('4', '3.85');
INSERT INTO Scores (id, score) VALUES ('5', '4.0');
INSERT INTO Scores (id, score) VALUES ('6', '3.65');

/*
将 rank => score 对应起来
使用pre_score记录上一次的score, 判断此次score 是否与上次 pre_score相同
*/

-- Result: AC 589ms(28.02%)
SELECT
    score,
    'rank' -- rank 是一个保留字，用于表示排序的级别或位置。当您在 SQL 查询中使用保留字作为列名或别名时，需要用反引号 (`) 将其括起来，以避免语法错误
FROM (
    SELECT
        score,
        @rank := @rank + IF(@prev_score != score, 1, 0) AS rank,
        @prev_score := score
    FROM
        Scores,
        (SELECT @rank := 1, @prev_score := NULL) AS vars
    ORDER BY
        score DESC
) AS ranked_scores;


-- 180. 连续出现的数字

/*
描述:

找出所有至少连续出现三次的数字。
返回的结果表中的数据可以按 任意顺序 排列。
*/

-- E1:
CREATE TABLE IF NOT EXISTS LOGS (id INT, num INT);
-- Truncate table Logs
INSERT INTO LOGS (id, num) VALUES ('1', '1');
INSERT INTO LOGS (id, num) VALUES ('2', '1');
INSERT INTO LOGS (id, num) VALUES ('3', '1');
INSERT INTO LOGS (id, num) VALUES ('4', '2');
INSERT INTO LOGS (id, num) VALUES ('5', '1');
INSERT INTO LOGS (id, num) VALUES ('6', '2');
INSERT INTO LOGS (id, num) VALUES ('7', '2');

DROP TABLE IF EXISTS LOGS;

-- Result: AC 1022ms(9.87%)
SELECT DISTINCT num AS ConsecutiveNums 
FROM (
SELECT
id,
num,
@cnt := IF(@prev_num = num, @cnt + 1, 1) AS cnt,
@prev_num := num
FROM LOGS,
(SELECT @cnt := 1, @prev_num := NULL) AS vars
) AS logs_cnt WHERE cnt >= 3;



-- LeetCode 181. 超过经理收入的员工

-- 描述: 编写解决方案，找出收入比经理高的员工(Employee.name)。以 任意顺序 返回结果表。

-- E1:
CREATE TABLE IF NOT EXISTS Employee (id INT, NAME VARCHAR(255), salary INT, managerId INT);
-- Truncate table Employee
INSERT INTO Employee (id, NAME, salary, managerId) VALUES ('1', 'Joe', '70000', '3');
INSERT INTO Employee (id, NAME, salary, managerId) VALUES ('2', 'Henry', '80000', '4');
INSERT INTO Employee (id, NAME, salary, managerId) VALUES ('3', 'Sam', '60000', NULL);
INSERT INTO Employee (id, NAME, salary, managerId) VALUES ('4', 'Max', '90000', NULL);

DROP TABLE IF EXISTS Employee;

-- Result: AC 1096ms(10.39%)
SELECT emp.name AS Employee 
FROM Employee emp
WHERE emp.managerId IS NOT NULL 
AND salary > (
	SELECT e1.salary 
	FROM Employee e1
	WHERE e1.id = emp.managerId
	 )
-- Result: AC 511ms(44.17%)
SELECT a.name AS Employee FROM employee AS a JOIN Employee AS b ON a.managerId = b.id AND a.salary > b.salary;





-- LeetCode 182. 查找重复的电子邮箱

-- 编写解决方案来报告所有重复的电子邮件。 请注意，可以保证电子邮件字段不为 NULL。

-- E1:
DROP TABLE IF EXISTS Person;
CREATE TABLE IF NOT EXISTS Person (id INT, email VARCHAR(255))
-- Truncate table Person
INSERT INTO Person (id, email) VALUES ('1', 'a@b.com');
INSERT INTO Person (id, email) VALUES ('2', 'c@d.com');
INSERT INTO Person (id, email) VALUES ('3', 'a@b.com');


SELECT email, COUNT(email) AS cnt FROM person
GROUP BY email

-- Result: AC 683ms(13.80%)
SELECT a.email
FROM (SELECT email, COUNT(email) AS cnt FROM person
GROUP BY email) AS a WHERE a.cnt > 1



-- LeetCode 183. 从不订购的客户

-- 描述: 找出所有从不点任何东西的顾客。

-- E1:
CREATE TABLE IF NOT EXISTS Customers (id INT, NAME VARCHAR(255));
CREATE TABLE IF NOT EXISTS Orders (id INT, customerId INT);
-- Truncate table Customers
INSERT INTO Customers (id, NAME) VALUES ('1', 'Joe');
INSERT INTO Customers (id, NAME) VALUES ('2', 'Henry');
INSERT INTO Customers (id, NAME) VALUES ('3', 'Sam');
INSERT INTO Customers (id, NAME) VALUES ('4', 'Max');
-- Truncate table Orders
INSERT INTO Orders (id, customerId) VALUES ('1', '3');
INSERT INTO Orders (id, customerId) VALUES ('2', '1');



SELECT cust.id, cust.name, o.id, o.customerId
FROM customers AS cust LEFT JOIN orders AS o
ON cust.id = o.customerId
WHERE o.id IS NULL

-- Result: AC 988ms(14.77%)
SELECT cust.name AS Customers 
FROM customers AS cust LEFT JOIN orders AS o
ON cust.id = o.customerId
WHERE o.id IS NULL

-- Result: AC 1014ms(12.01%)
SELECT cust.name AS Customers
FROM customers AS cust
WHERE cust.id NOT IN (SELECT DISTINCT customerId FROM orders)





-- LeetCode 184. 部门工资最高的员工

-- 描述: 查找出每个部门中薪资最高的员工。Department.name, Employee.name, Employee.salary

-- E1: 

DROP TABLE IF EXISTS employee;
CREATE TABLE IF NOT EXISTS Employee (id INT, NAME VARCHAR(255), salary INT, departmentId INT);
CREATE TABLE IF NOT EXISTS Department (id INT, NAME VARCHAR(255));
-- Truncate table Employee
INSERT INTO Employee (id, NAME, salary, departmentId) VALUES ('1', 'Joe', '70000', '1');
INSERT INTO Employee (id, NAME, salary, departmentId) VALUES ('2', 'Jim', '90000', '1');
INSERT INTO Employee (id, NAME, salary, departmentId) VALUES ('3', 'Henry', '80000', '2');
INSERT INTO Employee (id, NAME, salary, departmentId) VALUES ('4', 'Sam', '60000', '2');
INSERT INTO Employee (id, NAME, salary, departmentId) VALUES ('5', 'Max', '90000', '1');
-- Truncate table Department
INSERT INTO Department (id, NAME) VALUES ('1', 'IT');
INSERT INTO Department (id, NAME) VALUES ('2', 'Sales');


-- Result: AC 1312ms(10.05%)
SELECT department.name AS 'Department', emp.name AS 'Employee', emp.salary
FROM employee AS emp 
JOIN (SELECT e.departmentId, MAX(e.salary) AS maxSalary
	FROM employee AS e
	GROUP BY e.departmentId ) AS info
ON emp.departmentId = info.departmentId AND emp.salary = info.maxSalary
LEFT JOIN department ON emp.departmentId = department.id



-- Result: AC 1197ms(16.28%)
SELECT d1.name Department ,e1.name Employee, e1.salary Salary 
FROM Employee e1 
JOIN Department d1 ON e1.departmentId=d1.id 
WHERE( e1.departmentId,salary) IN(SELECT departmentId ,MAX(salary) Salary FROM Employee GROUP BY departmentId);



-- LeetCode 196. 删除重复的电子邮箱

-- 描述: 编写解决方案 删除 所有重复的电子邮件，只保留一个具有最小 id 的唯一电子邮件。

-- E1:
DROP TABLE IF EXISTS person;
CREATE TABLE IF NOT EXISTS Person (Id INT, Email VARCHAR(255));
-- Truncate table Person
INSERT INTO Person (id, email) VALUES ('1', 'john@example.com');
INSERT INTO Person (id, email) VALUES ('2', 'bob@example.com');
INSERT INTO Person (id, email) VALUES ('3', 'john@example.com');


-- Result: AC 792ms(81.57%)
DELETE FROM person WHERE id NOT IN (
	SELECT info.id FROM (SELECT MIN(p1.id) AS id FROM person AS p1 GROUP BY p1.email) AS info
)

-- tips: MySQL 不允许在 DELETE 语句的 WHERE 子句中直接使用正在被更新的表。




-- LeetCode 197. 上升的温度

-- 描述: 编写解决方案，找出与之前（昨天的）日期相比温度更高的所有日期的 id 。

-- E1:

CREATE TABLE IF NOT EXISTS Weather (id INT, recordDate DATE, temperature INT);
-- Truncate table Weather
INSERT INTO Weather (id, recordDate, temperature) VALUES ('1', '2015-01-01', '10');
INSERT INTO Weather (id, recordDate, temperature) VALUES ('2', '2015-01-02', '25');
INSERT INTO Weather (id, recordDate, temperature) VALUES ('3', '2015-01-03', '20');
INSERT INTO Weather (id, recordDate, temperature) VALUES ('4', '2015-01-04', '30');

-- Result: AC 546ms(71.56%)
SELECT w1.id FROM
weather AS w1
JOIN weather AS w2 
ON w1.recordDate = DATE_ADD(w2.recordDate, INTERVAL 1 DAY) AND w1.temperature > w2.temperature

/*
DATE_ADD() 函数‌: 通过正向或负向间隔值实现日期加减操作
SELECT DATE_ADD('2025-01-05', INTERVAL 1 DAY) AS new_date; -- 增加一天: 2025-01-06

SELECT DATE_ADD('2025-01-05', INTERVAL -1 DAY) AS new_date; -- 减少一天: 2025-01-04

DATE_SUB() 函数‌: 通过正向或负向间隔值实现日期减加操作

SELECT DATE_SUB('2025-01-05', INTERVAL 1 DAY) AS new_date; -- 减少一天: 2025-01-04
SELECT DATE_SUB('2025-01-05', INTERVAL -1 DAY) AS new_date; -- 增加一天:  2025-01-06
*/




-- LeetCode 262. 行程和用户

-- 描述: 取消率 的计算方式如下：(被司机或乘客取消的非禁止用户生成的订单数量) / (非禁止用户生成的订单总数)。
-- 	 编写解决方案找出 "2013-10-01" 至 "2013-10-03" 期间有 至少 一次行程的非禁止用户（乘客和司机都必须未被禁止）的 取消率。
--	 非禁止用户即 banned 为 No 的用户，禁止用户即 banned 为 Yes 的用户。其中取消率 Cancellation Rate 需要四舍五入保留 两位小数 。

-- E1:
CREATE TABLE IF NOT EXISTS Trips (id INT, client_id INT, driver_id INT, city_id INT, STATUS ENUM('completed', 'cancelled_by_driver', 'cancelled_by_client'), request_at VARCHAR(50));
CREATE TABLE IF NOT EXISTS Users (users_id INT, banned VARCHAR(50), role ENUM('client', 'driver', 'partner'));
-- Truncate table Trips
INSERT INTO Trips (id, client_id, driver_id, city_id, STATUS, request_at) VALUES ('1', '1', '10', '1', 'completed', '2013-10-01');
INSERT INTO Trips (id, client_id, driver_id, city_id, STATUS, request_at) VALUES ('2', '2', '11', '1', 'cancelled_by_driver', '2013-10-01');
INSERT INTO Trips (id, client_id, driver_id, city_id, STATUS, request_at) VALUES ('3', '3', '12', '6', 'completed', '2013-10-01');
INSERT INTO Trips (id, client_id, driver_id, city_id, STATUS, request_at) VALUES ('4', '4', '13', '6', 'cancelled_by_client', '2013-10-01');
INSERT INTO Trips (id, client_id, driver_id, city_id, STATUS, request_at) VALUES ('5', '1', '10', '1', 'completed', '2013-10-02');
INSERT INTO Trips (id, client_id, driver_id, city_id, STATUS, request_at) VALUES ('6', '2', '11', '6', 'completed', '2013-10-02');
INSERT INTO Trips (id, client_id, driver_id, city_id, STATUS, request_at) VALUES ('7', '3', '12', '6', 'completed', '2013-10-02');
INSERT INTO Trips (id, client_id, driver_id, city_id, STATUS, request_at) VALUES ('8', '2', '12', '12', 'completed', '2013-10-03');
INSERT INTO Trips (id, client_id, driver_id, city_id, STATUS, request_at) VALUES ('9', '3', '10', '12', 'completed', '2013-10-03');
INSERT INTO Trips (id, client_id, driver_id, city_id, STATUS, request_at) VALUES ('10', '4', '13', '12', 'cancelled_by_driver', '2013-10-03');
-- Truncate table Users
INSERT INTO Users (users_id, banned, role) VALUES ('1', 'No', 'client');
INSERT INTO Users (users_id, banned, role) VALUES ('2', 'Yes', 'client');
INSERT INTO Users (users_id, banned, role) VALUES ('3', 'No', 'client');
INSERT INTO Users (users_id, banned, role) VALUES ('4', 'No', 'client');
INSERT INTO Users (users_id, banned, role) VALUES ('10', 'No', 'driver');
INSERT INTO Users (users_id, banned, role) VALUES ('11', 'No', 'driver');
INSERT INTO Users (users_id, banned, role) VALUES ('12', 'No', 'driver');
INSERT INTO Users (users_id, banned, role) VALUES ('13', 'No', 'driver');

-- Result: 959ms/20.37%
SELECT info_1.request_at AS 'Day', 
CAST(info_2.cnt / info_1.cnt AS DECIMAL(10,2)) AS 'Cancellation Rate'
FROM
(
	SELECT COUNT(t1.id) AS cnt, t1.request_at FROM
	trips AS t1
	WHERE t1.client_id NOT IN (
		SELECT u1.users_id FROM users AS u1
		WHERE u1.banned = 'Yes' AND u1.role = 'client'
	) 
	AND t1.driver_id NOT IN (
		SELECT u1.users_id FROM users AS u1
		WHERE u1.banned = 'Yes' AND u1.role = 'driver'
	)
	GROUP BY t1.request_at
) AS info_1
JOIN (
	SELECT t2.request_at, IFNULL(t3.cnt, 0) AS cnt
	FROM trips AS t2
	LEFT JOIN (
		SELECT COUNT(t1.id) AS cnt, t1.request_at FROM
		trips AS t1
		WHERE t1.client_id NOT IN (
			SELECT u1.users_id FROM users AS u1
			WHERE u1.banned = 'Yes' AND u1.role = 'client'
		) 
		AND t1.driver_id NOT IN (
			SELECT u1.users_id FROM users AS u1
			WHERE u1.banned = 'Yes' AND u1.role = 'driver'
		)
		AND t1.STATUS <> 'completed'
		GROUP BY t1.request_at
	) AS t3 
	ON t2.request_at = t3.request_at
	GROUP BY t2.request_at
) info_2
ON info_1.request_at = info_2.request_at
WHERE info_1.request_at BETWEEN '2013-10-01' AND '2013-10-03'


SELECT u1.users_id FROM users AS u1
WHERE u1.banned = 'Yes' AND u1.role = 'client'


-- Result: 1011ms(13.46%)
SELECT t.request_at AS 'Day' , ROUND(SUM(IF(STATUS = 'completed',0,1))/COUNT(*) , 2) AS 'Cancellation Rate'
FROM Trips t 
JOIN Users u1 ON t.client_id = u1.users_id AND u1.banned = 'NO' 
JOIN Users u2 ON t.driver_id = u2.users_id AND u2.banned = 'NO'
WHERE t.request_at BETWEEN '2013-10-01' AND '2013-10-03'
GROUP BY request_at












































