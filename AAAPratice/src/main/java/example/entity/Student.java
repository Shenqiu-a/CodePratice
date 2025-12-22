package example.entity;

import lombok.ToString;
import org.springframework.beans.factory.InitializingBean;

/**
 * 功能：
 * 作者：yml
 * 日期：2024-12-0716:36
 */

@ToString
public class Student implements InitializingBean {

    private Teacher teacher;

    public void setMusicTeacher(Teacher teacher) {
        this.teacher = teacher;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println("初始化之后的操作(通过继承接口重写方法实现)");
        teacher.teach();
    }

    public void afterInit(){
        System.out.println("我也是初始化之后的操作(通过配置文件实现)");
    }

}
