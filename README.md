## Spring

### spring 的体系结构

<img src="spring体系结构.png"></img>

### 工厂模式解耦

在实际开发中我们可以把三层的对象都使用配置文件配置起来，当启动服务器应用加载的时候，让一个类中的方法通过*读取配置文件*，把这些对象*创建*出来并*存起来*。在接下来的使用的时候，直接拿过来用就好了。

那么，这个读取配置文件，创建和获取三层对象的类就是工厂。

### BeanFactory 和 ApplicationContext 的区别

* BeanFactory 是 Spring 容器中的顶层接口。

* ApplicationContext 是它的子接口。

#### BeanFactory 和 ApplicationContext 的区别：

创建对象的时间点不一样。

* ApplicationContext：立即加载；只要一读取配置文件，默认情况下就会创建对象。

* BeanFactory：延迟加载；什么时候用什么时候创建对象。

### ApplicationContext 接口的实现类

* ClassPathXmlApplicationContext：从类的根路径下加载配置文件----推荐使用这种

* FileSystemXmlApplicationContext：从磁盘路径上加载配置文件，配置文件可以在磁盘的任意位置。

* AnnotationConfigApplicationContext:当我们使用注解配置容器对象时，需要使用此类来创建 spring 容器。它用来读取注解。

### bean 标签
* 作用：

  * 用于配置对象让 spring 来创建的。默认情况下它调用的是类中的无参构造函数。如果没有无参构造函数则不能创建成功。

* 属性：

  * id： 给对象在容器中提供一个唯一标识。用于获取对象。

  * class： 指定类的全限定类名。用于反射创建对象。默认情况下调用无参构造函数。

  * scope： 指定对象的作用范围。

    * singleton :默认值，单例的.

    * prototype :多例的.

    * request :WEB 项目中,Spring 创建一个 Bean 的对象,将对象存入到 request 域中.

    * session :WEB 项目中,Spring 创建一个 Bean 的对象,将对象存入到 session 域中.

    * global session :WEB 项目中,应用在 Portlet 环境.如果没有 Portlet 环境那么globalSession 相当于 session.

* init-method： 指定类中的初始化方法名称。

* destroy-method： 指定类中销毁方法名称。

### bean 的作用范围和生命周期
* 单例对象： scope="singleton"
  
  * 一个应用只有一个对象的实例。它的作用范围就是整个引用。

  * 生命周期：创建容器时，对象就被创建了。只要*容器*在，对象一直活着。当应用卸载，销毁容器时，对象就被销毁了。

* 多例对象： scope="prototype"

  * 每次访问对象时，都会重新创建对象实例。

  * 生命周期：当*使用对象*时，创建新的对象实例。只要对象在使用中，就一直活着。当对象长时间不用时，被 java 的*垃圾回收器*回收了。

### 实例化 Bean 的三种方式
第一种方式：使用默认无参构造函数
```xml
<!--在默认情况下：
它会根据默认无参构造函数来创建类对象。如果 bean 中没有默认无参构造函数，将会创建失败。-->
<bean id="accountService" class="com.itheima.service.impl.AccountServiceImpl"/>
```

第二种方式： spring 管理静态工厂-使用静态工厂的方法创建对象
```java
/**
* 模拟一个静态工厂，创建业务层实现类
*/
public class StaticFactory {
    public static IAccountService createAccountService(){
        return new AccountServiceImpl();
    }
}
```

```xml
<!-- 此种方式是:
使用 StaticFactory 类中的静态方法 createAccountService 创建对象，并存入 spring 容器
id 属性：指定 bean 的 id，用于从容器中获取
class 属性：指定静态工厂的全限定类名
factory-method 属性：指定生产对象的静态方法
-->
<bean id="accountService"
class="com.itheima.factory.StaticFactory"
factory-method="createAccountService"></bean>
```

第三种方式： spring 管理实例工厂-使用实例工厂的方法创建对象
```java
/**
* 模拟一个实例工厂，创建业务层实现类
* 此工厂创建对象，没有构造函数可以调用，必须先有工厂实例对象，再调用方法
*/
public class InstanceFactory {
    public IAccountService createAccountService(){
        return new AccountServiceImpl();
    }
}
```
```xml
<!-- 此种方式是：
先把工厂的创建交给 spring 来管理。
然后在使用工厂的 bean 来调用里面的方法
factory-bean 属性：用于指定实例工厂 bean 的 id。
factory-method 属性：用于指定实例工厂中创建对象的方法。
-->
<bean id="instanceFactory" class="com.itheima.factory.InstanceFactory"></bean>
<bean id="accountService"
factory-bean="instanceFactory"
factory-method="createAccountService"></bean>
```

### spring中的依赖注入
在当前类需要用到其他类的对象，由spring为我们提供，我们只需要在配置文件中说明.依赖关系的维护就称之为依赖注入。
* 能注入的数据：
1.基本类型和String
2.其他bean类型（在配置文件中或者注解配置过的bean）
3.复杂类型/集合类型
* 注入的方式：
#### 1.使用构造函数提供（了解）
使用的标签:constructor-arg
标签出现的位置：bean标签的内部
标签中的属性
    type：用于指定要注入的数据的数据类型，该数据类型也是构造函数中某个或某些参数的类型
    index：用于指定要注入的数据给构造函数中指定索引位置的参数赋值。索引的位置是从0开始
    name：用于指定给构造函数中指定名称的参数赋值（_常用的_）
    =============以上三个用于指定给构造函数中哪个参数赋值===============================
    value：用于提供基本类型和String类型的数据
    ref：用于指定其他的bean类型数据。它指的就是在spring的Ioc核心容器中出现过的bean对象

优势：
    在获取bean对象时，注入数据是必须的操作，否则对象无法创建成功。
弊端：
    改变了bean对象的实例化方式，使我们在创建对象时，如果用不到这些数据，也必须提供。

```xml
<bean id="accountService" class="com.itheima.service.impl.AccountServiceImpl">
    <constructor-arg name="name" value="泰斯特"></constructor-arg>
    <constructor-arg name="age" value="18"></constructor-arg>
    <constructor-arg name="birthday" ref="now"></constructor-arg>
</bean>

<!-- 配置一个日期对象 -->
<bean id="now" class="java.util.Date"></bean>
```
#### 2.使用set方法提供
更常用的方式
涉及的标签：property
出现的位置：bean标签的内部
标签的属性
    name：用于指定注入时所调用的set方法名称
    value：用于提供基本类型和String类型的数据
    ref：用于指定其他的bean类型数据。它指的就是在spring的Ioc核心容器中出现过的bean对象
优势：
    创建对象时没有明确的限制，可以直接使用默认构造函数
弊端：
    如果有某个成员必须有值，则获取对象是有可能set方法没有执行。
```xml
<bean id="accountService2" class="com.itheima.service.impl.AccountServiceImpl2">
    <property name="name" value="TEST" ></property>
    <property name="age" value="21"></property>
    <property name="birthday" ref="now"></property>
</bean>
```
```java
/**
 * 账户的业务层实现类
 */
public class AccountServiceImpl2 implements IAccountService {

    //如果是经常变化的数据，并不适用于注入的方式
    private String name;
    private Integer age;
    private Date birthday;

    public void setName(String name) {
        this.name = name;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public void  saveAccount(){
        System.out.println("service中的saveAccount方法执行了。。。"+name+","+age+","+birthday);
    }

}
```

* 如果是集合类的数据：
1.用于给List结构集合注入的标签：

    list array set
  
2.用于给Map结构集合注入的标签:

    map props

结构相同，标签可以互换

```xml
<bean id="accountService3" class="com.itheima.service.impl.AccountServiceImpl3">
    <property name="myStrs">
        <set>
            <value>AAA</value>
            <value>BBB</value>
            <value>CCC</value>
        </set>
    </property>

    <property name="myList">
        <array>
            <value>AAA</value>
            <value>BBB</value>
            <value>CCC</value>
        </array>
    </property>

    <property name="mySet">
        <list>
            <value>AAA</value>
            <value>BBB</value>
            <value>CCC</value>
        </list>
    </property>

    <property name="myMap">
        <props>
            <prop key="testC">ccc</prop>
            <prop key="testD">ddd</prop>
        </props>
    </property>

    <property name="myProps">
        <map>
            <entry key="testA" value="aaa"></entry>
            <entry key="testB" value="BBB"></entry>
        </map>
    </property>
</bean>
```
#### 3.使用注解提供
