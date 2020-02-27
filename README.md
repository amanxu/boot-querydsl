* 本demo参考简书作者：恒宇少年<https://www.jianshu.com/p/99a5ec5c3bd5>
* Springboot 工程中引入如下maven配置：
```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-data-jpa</artifactId>
</dependency>

<!--queryDSL-->
<dependency>
    <groupId>com.querydsl</groupId>
    <artifactId>querydsl-jpa</artifactId>
    <version>${querydsl.version}</version>
</dependency>

<dependency>
    <groupId>com.querydsl</groupId>
    <artifactId>querydsl-apt</artifactId>
    <version>${querydsl.version}</version>
    <scope>provided</scope>
</dependency>    
```
* 引入QueryDSL的JPA与APT相关的依赖，由于QueryDSL框架需要使用插件为我们配置了@Entity注解的实体自动对应创建QBean来作为查询时的条件以及自动生成QPath，下面我们需要修改pom.xml配置文件添加QueryDSL插件，
如下代码块所示：
```xml
<!--添加QueryDSL插件支持-->
<plugin>
    <groupId>com.mysema.maven</groupId>
    <artifactId>apt-maven-plugin</artifactId>
    <version>1.1.3</version>
    <executions>
        <execution>
            <goals>
                <goal>process</goal>
            </goals>
            <configuration>
                <outputDirectory>target/generated-sources/java</outputDirectory>
                <processor>com.querydsl.apt.jpa.JPAAnnotationProcessor</processor>
            </configuration>
        </execution>
    </executions>
</plugin>
```
* QueryDSL的插件会自动扫描项目内配置了@Entity的实体类，并根据实体的内定义的字段以及关联类通过JPAAnnotationProcessor自动创建Q[实体类名称]的查询实体，
创建完成后会将实体存放到我们配置outputDirectory属性目录下。

1. 单表查询具体实现如下(请参考UserController)：
```java
@ApiOperation(value = "查询全部数据并根据id倒序")
@PostMapping(value = "/queryAll")
public List<UserBean> queryAll() {
    //使用queryDsl查询
    QUserBean qUserBean = QUserBean.userBean;
    //查询并返回结果集
    List<UserBean> userBeanList = queryFactory
            //查询源
            .selectFrom(qUserBean)
            //根据id倒序
            .orderBy(qUserBean.id.desc())
            //执行查询并获取结果集
            .fetch();
    return userBeanList;
}
```
2 . 多表关联查询，并通过自定义实体类接收查询结果实现如下(请参考OrderController)：
```java
@ApiOperation(value = "查询订单信息，关联查询用户信息,并通过自定义字段接收")
@GetMapping(value = "findOrderInfo")
public UserOrderDTO queryOrderDetail(@RequestParam("id") Long id) {

    QUserOrderBean qUserOrderBean = QUserOrderBean.userOrderBean;
    QUserBean qUserBean = QUserBean.userBean;

    UserOrderDTO userOrderDTO = queryFactory
            // 用户自定义BEAN接收查询结果
            .select(Projections.bean(UserOrderDTO.class,
                    qUserOrderBean.id,
                    qUserOrderBean.userId,
                    qUserOrderBean.productId,
                    qUserOrderBean.productName,
                    qUserOrderBean.createTime,
                    qUserBean.name,
                    qUserBean.address))
            .from(qUserOrderBean, qUserBean)
            // 多表关联查询
            .where(qUserOrderBean.id.eq(id)
                    .and(qUserOrderBean.userId.eq(qUserBean.id)))
            .fetchOne();
    return userOrderDTO;
}
```