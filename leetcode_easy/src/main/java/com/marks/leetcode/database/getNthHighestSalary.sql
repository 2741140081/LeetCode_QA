DELIMITER $$

USE `marks`$$

DROP FUNCTION IF EXISTS `getNthHighestSalary`$$

CREATE DEFINER=`root`@`localhost` FUNCTION `getNthHighestSalary`(n INT) RETURNS INT(11)
BEGIN
    DECLARE nthSalary INT DEFAULT NULL;
    SET n = n-1; -- 初始化n
    
    SELECT DISTINCT salary INTO nthSalary
    FROM employee
    ORDER BY salary DESC
    LIMIT 1 OFFSET n;
    
    RETURN nthSalary;

    END$$

DELIMITER ;