package cn.edu.jxau.lang;

import lombok.Data;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Desc:
 * ------------------------------------
 * Author:fulei04@meituan.com
 * Date:2018/6/4
 * Time:下午4:56
 */
@Data
public class Student {

    private Integer classId;
    private Integer age;
    private String name;
    private Integer id;
    private Date birthday;
    private Integer[] integerArr;
    private Map<String,Integer> integerMap;
}
