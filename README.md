# JavaUtils

WebMVC,仿SpringMVC,轻量级!

## 为什么写?

由于SpringMVC越来越庞大,配置也越来越繁琐,想单独使用其中一个MVC模块时需要下一堆jar包,所以就写了一个简易的MVC.

## 优点!

使用简单,只需要导入1个jar包,并且是无xml配置文件.

## 缺点!

目前没有融入json转换,也就是说前端传json进来需要你手工获取并转换成对象.

## 使用教程

### 第一步 导入JavaUtils.jar包(这步不用讲)

### 第二步 添加监听器和环境信息

打开项目里的web.xml,然后添加以下信息.

```java
    <context-param>
        <param-name>ControllerListenerValue</param-name>
        <param-value>com.good.servlet</param-value>   <!-- 包路径换成你的controller所在包路径 -->
    </context-param>

    <listener>
        <listener-class>com.shaw.listener.ControllerListener</listener-class>
    </listener>
```
### 第三步 编写Controller代码

`注意:`所有的Controller类 必须要用 @Controller注解才可以生效!

#### 最基础的MVC模型

```java

@Controller
public class StudentServlet {

    @RequestBody("/getName1")
    public String getName1(HttpServletRequest request, HttpServletResponse response) {
        String param1 = request.getParameter("studentName");
        System.out.println("参数1:" + param1);
        request.setAttribute("param1", param1);
        return "index.jsp";
    }
    
}

```

请求地址:http://127.0.0.1:8080/getName1?studentName=测试
跳转到index.jsp页面并打印出:参数1:测试

#### 所有参数都需要用request.getParameter("xxx");来获取,一旦参数过多,那就非常痛苦了,现在来使用@AutoBody.

@AutoBody 可注解String,int,Integer.

```java

@Controller
public class StudentServlet {

    @RequestBody("/getName2")
    public String getName2(HttpServletRequest request, HttpServletResponse response, @AutoBody("studentName") String param1) {
        System.out.println("参数1:" + param1);
        request.setAttribute("param1", param1);
        return "index.jsp";
    }
    
}

```

请求地址:http://127.0.0.1:8080/getName2?studentName=测试
跳转到index.jsp页面并打印出:参数1:测试

以上方式确实省掉request.getParameter("studentName");但是函数要加需要加@AutoBody("studentName") String param1,而且参数多了以后,函数就会变得很长.

#### @AutoBody直接注解PBJO(必须包含get,set)

`PBJO(必须包含get,set)`

```java

public class Student {

    private String id;
    private String name;

    @Override
    public String toString() {
        return "Student{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                '}';
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}


@Controller
public class StudentServlet {

    @RequestBody("/getName3")
    public String getName3(HttpServletRequest request, HttpServletResponse response, @AutoBody Student student) {
        System.out.println("对象:" + student);
        request.setAttribute("param1", student.getStudentName());
        return "index.jsp";
    }
    
}


```

请求地址:http://127.0.0.1:8080/getName3?studentName=测试&studentId=1
跳转到index.jsp页面并打印出:对象:Student{studentId=1, studentName='测试'}

以上方式省掉request.getParameter("studentName");也方便使用,传承oop的思想,万物皆对象.

使用说明到此结束,就是这么简单!

## Bug很多,可以来测试以及玩,千万不要用在项目中,Bug很多,可以来测试以及玩,千万不要用在项目中,Bug很多,可以来测试以及玩,千万不要用在项目中.

# 欢迎大家提意见!

