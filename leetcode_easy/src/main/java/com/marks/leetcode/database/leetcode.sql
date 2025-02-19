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
















































