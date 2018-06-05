package cn.edu.jxau.lang;

import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * Desc:
 * ------------------------------------
 * Author:fulei04@meituan.com
 * Date:2018/6/4
 * Time:下午4:56
 */
@Data
public class Student {

    private String username;
    private String password;
    private Date birthday;
    private Integer age;
    private int id;
    private List<String> tagList;
}
