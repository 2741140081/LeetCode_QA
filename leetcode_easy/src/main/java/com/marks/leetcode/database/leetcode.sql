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






-- LeetCode 570. 至少有5名直接下属的经理

-- 描述: 编写一个解决方案，找出至少有五个直接下属的经理。

-- E1:
DROP TABLE IF EXISTS employee;

CREATE TABLE IF NOT EXISTS Employee (id INT, NAME VARCHAR(255), department VARCHAR(255), managerId INT);
-- Truncate table Employee
INSERT INTO Employee (id, NAME, department, managerId) VALUES ('101', 'John', 'A', NULL);
INSERT INTO Employee (id, NAME, department, managerId) VALUES ('102', 'Dan', 'A', '101');
INSERT INTO Employee (id, NAME, department, managerId) VALUES ('103', 'James', 'A', '101');
INSERT INTO Employee (id, NAME, department, managerId) VALUES ('104', 'Amy', 'A', '101');
INSERT INTO Employee (id, NAME, department, managerId) VALUES ('105', 'Anne', 'A', '101');
INSERT INTO Employee (id, NAME, department, managerId) VALUES ('106', 'Ron', 'B', '101');


-- Result: AC 667ms(11.01%)
SELECT emp.name 
FROM employee AS emp
JOIN (
	SELECT COUNT(managerId) AS cnt, managerId
	FROM employee
	WHERE managerId IS NOT NULL
	GROUP BY managerId
) AS info ON emp.id = info.managerId
WHERE info.cnt >= 5


-- Result: AC 459ms(50.20%)
SELECT Employee.Name AS NAME
FROM (
  SELECT ManagerId AS Id
  FROM Employee
  GROUP BY ManagerId
  HAVING COUNT(Id) >= 5
) AS Manager JOIN Employee
ON Manager.Id = Employee.Id



-- LeetCode 577. 员工奖金

-- 描述: 编写解决方案，报告每个奖金 少于 1000 的员工的姓名和奖金数额。

-- E1:
DROP TABLE IF EXISTS employee;

CREATE TABLE IF NOT EXISTS Employee (empId INT, NAME VARCHAR(255), supervisor INT, salary INT);
CREATE TABLE IF NOT EXISTS Bonus (empId INT, bonus INT);
-- Truncate table Employee
INSERT INTO Employee (empId, NAME, supervisor, salary) VALUES ('3', 'Brad', NULL, '4000');
INSERT INTO Employee (empId, NAME, supervisor, salary) VALUES ('1', 'John', '3', '1000');
INSERT INTO Employee (empId, NAME, supervisor, salary) VALUES ('2', 'Dan', '3', '2000');
INSERT INTO Employee (empId, NAME, supervisor, salary) VALUES ('4', 'Thomas', '3', '4000');
-- Truncate table Bonus
INSERT INTO Bonus (empId, bonus) VALUES ('2', '500');
INSERT INTO Bonus (empId, bonus) VALUES ('4', '2000');


-- Result: AC 1377ms(49.97%)
SELECT emp.name, b.bonus
FROM employee AS emp
LEFT JOIN bonus AS b
ON emp.empId = b.empId
WHERE b.bonus < 1000 OR b.bonus IS NULL



-- LeetCode 584. 寻找用户推荐人

-- 描述: 找出那些 没有被 id = 2 的客户 推荐 的客户的姓名。

-- E1
DROP TABLE IF EXISTS customer;
CREATE TABLE IF NOT EXISTS Customer (id INT, NAME VARCHAR(25), referee_id INT);
-- Truncate table Customer
INSERT INTO Customer (id, NAME, referee_id) VALUES ('1', 'Will', NULL);
INSERT INTO Customer (id, NAME, referee_id) VALUES ('2', 'Jane', NULL);
INSERT INTO Customer (id, NAME, referee_id) VALUES ('3', 'Alex', '2');
INSERT INTO Customer (id, NAME, referee_id) VALUES ('4', 'Bill', NULL);
INSERT INTO Customer (id, NAME, referee_id) VALUES ('5', 'Zack', '1');
INSERT INTO Customer (id, NAME, referee_id) VALUES ('6', 'Mark', '2');


-- Result: AC 659ms(53.83%)
SELECT cust.name
FROM customer AS cust
WHERE cust.referee_id <> 2 OR cust.referee_id IS NULL;



-- LeetCode 585. 2016年的投资

-- 描述: 编写解决方案报告 2016 年 (tiv_2016) 所有满足下述条件的投保人的投保金额之和：
-- 	 他在 2015 年的投保额 (tiv_2015) 至少跟一个其他投保人在 2015 年的投保额相同。
--	 他所在的城市必须与其他投保人都不同（也就是说 (lat, lon) 不能跟其他任何一个投保人完全相同）。
--	 tiv_2016 四舍五入的 两位小数 。

-- E1:
CREATE TABLE IF NOT EXISTS Insurance (pid INT, tiv_2015 FLOAT, tiv_2016 FLOAT, lat FLOAT, lon FLOAT);
-- Truncate table Insurance
INSERT INTO Insurance (pid, tiv_2015, tiv_2016, lat, lon) VALUES ('1', '10', '5', '10', '10');
INSERT INTO Insurance (pid, tiv_2015, tiv_2016, lat, lon) VALUES ('2', '20', '20', '20', '20');
INSERT INTO Insurance (pid, tiv_2015, tiv_2016, lat, lon) VALUES ('3', '10', '30', '20', '20');
INSERT INTO Insurance (pid, tiv_2015, tiv_2016, lat, lon) VALUES ('4', '10', '40', '40', '40');

/*
pid 是投保人的投保编号。
tiv_2015 是该投保人在 2015 年的总投保金额，tiv_2016 是该投保人在 2016 年的总投保金额。
lat 是投保人所在城市的纬度。题目数据确保 lat 不为空。
lon 是投保人所在城市的经度。题目数据确保 lon 不为空。
*/
-- SUM(b.tiv_2016) AS 'tiv_2016' 
-- CAST(info_2.cnt / info_1.cnt AS DECIMAL(10,2)) AS 'Cancellation Rate'

-- fail
SELECT CAST(SUM(b.tiv_2016) AS DECIMAL(10,2)) AS 'tiv_2016'
FROM Insurance AS b
RIGHT JOIN (
	SELECT info.pid, info.tiv_2015 
	FROM (
		SELECT a.pid, a.tiv_2015, CONCAT(a.lat, "_", a.lon) AS location
		FROM Insurance AS a
	) AS info
	GROUP BY info.location
	HAVING COUNT(info.location) < 2
) AS c ON b.pid != c.pid AND b.tiv_2015 = c.tiv_2015
WHERE b.pid IN (
	SELECT info.pid
	FROM (
		SELECT a.pid, a.tiv_2015, CONCAT(a.lat, "_", a.lon) AS location
		FROM Insurance AS a
	) AS info
	GROUP BY info.location
	HAVING COUNT(info.location) < 2
);

-- Result: AC 944ms(18.63%)
SELECT
    CAST(SUM(insurance.TIV_2016) AS DECIMAL(10,2)) AS 'tiv_2016'
FROM
    insurance
WHERE
    insurance.TIV_2015 IN
    (
      SELECT
        TIV_2015
      FROM
        insurance
      GROUP BY TIV_2015
      HAVING COUNT(*) > 1
    )
    AND CONCAT(LAT, LON) IN
    (
      SELECT
        CONCAT(LAT, LON)
      FROM
        insurance
      GROUP BY LAT , LON
      HAVING COUNT(*) = 1
    )
;



-- LeetCode 586. 订单最多的客户

-- 描述: 查找下了 最多订单 的客户的 customer_number 。 测试用例生成后， 恰好有一个客户 比任何其他客户下了更多的订单。

-- E1:
DROP TABLE IF EXISTS orders;
CREATE TABLE IF NOT EXISTS orders (order_number INT, customer_number INT);
-- Truncate table orders
INSERT INTO orders (order_number, customer_number) VALUES ('1', '1');
INSERT INTO orders (order_number, customer_number) VALUES ('2', '2');
INSERT INTO orders (order_number, customer_number) VALUES ('3', '3');
INSERT INTO orders (order_number, customer_number) VALUES ('4', '3');


-- Result: AC 807ms(22.51%)
SELECT info.customer_number FROM (
	SELECT a.customer_number, COUNT(a.customer_number) AS cnt
	FROM orders AS a
	GROUP BY a.customer_number
) AS info 
ORDER BY info.cnt DESC
LIMIT 1

-- Result: AC 754ms(29.79%)
SELECT
    customer_number
FROM
    orders
GROUP BY customer_number
ORDER BY COUNT(*) DESC
LIMIT 1


-- LeetCode 595. 大的国家

-- 描述: 编写解决方案找出 大国 的国家名称、人口和面积。如果一个国家满足下述两个条件之一，则认为该国是 大国: 
--	 面积至少为 300 万平方公里（即，3000000 km2），或者 人口至少为 2500 万（即 25000000）

-- E1:

CREATE TABLE IF NOT EXISTS World (NAME VARCHAR(255), continent VARCHAR(255), AREA INT, population INT, gdp BIGINT)
-- Truncate table World
INSERT INTO World (NAME, continent, AREA, population, gdp) VALUES ('Afghanistan', 'Asia', '652230', '25500100', '20343000000');
INSERT INTO World (NAME, continent, AREA, population, gdp) VALUES ('Albania', 'Europe', '28748', '2831741', '12960000000');
INSERT INTO World (NAME, continent, AREA, population, gdp) VALUES ('Algeria', 'Africa', '2381741', '37100000', '188681000000');
INSERT INTO World (NAME, continent, AREA, population, gdp) VALUES ('Andorra', 'Europe', '468', '78115', '3712000000');
INSERT INTO World (NAME, continent, AREA, population, gdp) VALUES ('Angola', 'Africa', '1246700', '20609294', '100990000000');

-- Result: AC 454(16.60%)
SELECT w.name,  w.population, w.area
FROM world AS w
WHERE w.area >= 3000000 OR w.population >= 25000000




-- LeetCode 601. 体育馆的人流量

-- 描述: 编写解决方案找出每行的人数大于或等于 100 且 id 连续的三行或更多行记录。
-- 	 返回按 visit_date 升序排列 的结果表。

-- E1:

CREATE TABLE IF NOT EXISTS Stadium (id INT, visit_date DATE NULL, people INT);
-- Truncate table Stadium
INSERT INTO Stadium (id, visit_date, people) VALUES ('1', '2017-01-01', '10');
INSERT INTO Stadium (id, visit_date, people) VALUES ('2', '2017-01-02', '109');
INSERT INTO Stadium (id, visit_date, people) VALUES ('3', '2017-01-03', '150');
INSERT INTO Stadium (id, visit_date, people) VALUES ('4', '2017-01-04', '99');
INSERT INTO Stadium (id, visit_date, people) VALUES ('5', '2017-01-05', '145');
INSERT INTO Stadium (id, visit_date, people) VALUES ('6', '2017-01-06', '1455');
INSERT INTO Stadium (id, visit_date, people) VALUES ('7', '2017-01-07', '199');
INSERT INTO Stadium (id, visit_date, people) VALUES ('8', '2017-01-09', '188');

-- 每日人流量信息被记录在这三列信息中：序号 (id)、日期 (visit_date)、 人流量 (people)

-- wrong
SET @group = 0;
SET @group_copy = 0;
SET @cnt = 0;
SET @prev_id = 0;

SELECT b.id, b.visit_date, b.people
FROM (
	SELECT info.id, info.visit_date,info.people,
	@cnt := IF(@prev_id = info.id - 1, @cnt + 1, 1) AS cnt,
	@group_copy := IF(@cnt = 1, @group_copy + 1, @group_copy) AS g_id,
	@prev_id := info.id
	FROM (
		SELECT sta.id, sta.visit_date, sta.people
		FROM stadium AS sta
		WHERE sta.people >= 100
	) AS info
) AS b
WHERE b.g_id IN (
SELECT a.g_id
FROM (
	SELECT info.id, info.visit_date,info.people,
	@cnt := IF(@prev_id = info.id - 1, @cnt + 1, 1) AS cnt,
	@group := IF(@cnt = 1, @group + 1, @group) AS g_id,
	@prev_id := info.id
	FROM (
		SELECT sta.id, sta.visit_date, sta.people
		FROM stadium AS sta
		WHERE sta.people >= 100
	) AS info
) AS a
GROUP BY a.g_id
HAVING COUNT(g_id) >= 3
)

-- 知道为什么错误, mysql 版本需要8.0及其以上
WITH t1 AS(
    SELECT *,id - row_number() over(ORDER BY id) AS rk
    FROM stadium
    WHERE people >= 100
)

SELECT id,visit_date,people
FROM t1
WHERE rk IN(
    SELECT rk
    FROM t1
    GROUP BY rk
    HAVING COUNT(rk) >= 3
)















