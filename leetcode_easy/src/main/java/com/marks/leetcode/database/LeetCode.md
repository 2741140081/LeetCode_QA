##### LeetCode 175: 组合两个表
###### 描述：
编写解决方案，报告 Person 表中每个人的姓、名、城市和州。如果 person_id 的地址不在 Address表中，则报告为 null 。

以 任意顺序 返回结果表。

```mysql
Create table If Not Exists Person (personId int, firstName varchar(255), lastName varchar(255))
Create table If Not Exists Address (addressId int, personId int, city varchar(255), state varchar(255))
Truncate table Person
insert into Person (personId, lastName, firstName) values ('1', 'Wang', 'Allen')
insert into Person (personId, lastName, firstName) values ('2', 'Alice', 'Bob')
Truncate table Address
insert into Address (addressId, personId, city, state) values ('1', '2', 'New York City', 'New York')
insert into Address (addressId, personId, city, state) values ('2', '3', 'Leetcode', 'California')

Sql:
SELECT p.firstName, p.lastName, a.city, a.state
FROM Person p 
LEFT JOIN Address a ON p.personId = a.personId

-- AC: 542ms
```

###### 总结

1. A inner join B：取交集
2. A left join B：取A全部，B没有对应的值，则为null
3. A right join B：取B全部，A没有对应的值，则为null
4. A full outer join B：取并集，彼此没有对应的值为null