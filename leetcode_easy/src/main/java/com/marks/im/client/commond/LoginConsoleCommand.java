package com.marks.im.client.commond;

import lombok.Data;
import org.springframework.stereotype.Service;

import java.util.Scanner;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LoginConsoleCommand </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/1/7 17:05
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
@Data
@Service("LoginConsoleCommand")
public class LoginConsoleCommand implements BaseCommand{
    public static final String KEY = "1";

    private String userName;
    private String password;

    @Override
    public String getKey() {
        return KEY;
    }

    @Override
    public String getTips() {
        return "登录";
    }

    @Override
    public void exec(Scanner scanner) {
        System.out.println("请输入用户信息(id@password)  ");
        String[] info = null;
        while (true) {
            String input = scanner.next();
            info = input.split("@");
            if (info.length != 2) {
                System.out.println("请按照格式输入(id@password):");
            }else {
                break;
            }
        }
        userName=info[0];
        password = info[1];
    }
}
