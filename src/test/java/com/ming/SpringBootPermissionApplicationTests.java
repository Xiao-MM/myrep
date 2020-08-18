package com.ming;

import com.ming.dao.RoleMapper;
import com.ming.dao.UserMapper;
import com.ming.pojo.Role;
import com.ming.pojo.User;
import com.ming.utils.JWTUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
class SpringBootPermissionApplicationTests {

    @Autowired
    UserMapper userMapper;
    @Autowired
    RoleMapper roleMapper;
    @Test
    void contextLoads() {
    }

//    @Test
//    void testGetToken1(){
//        String token = JWTUtil.generateToken(new User(1, "zs", "123456"));
//        System.out.println(token);
//    }

    /**
     * 1. 为什么开启事务?
     * 由于使用了数据库连接池，默认每次查询完之后自动commite，这就导致两次查询使用的不是同一个sqlSessioin，根据一级缓存的原理，它将永远不会生效。
     * 2、两种一级缓存模式
     * 一级缓存的作用域有两种：session（默认）和statment，可通过设置local-cache-scope 的值来切换，默认为session。
     * 二者的区别在于session会将缓存作用于同一个sqlSesson，而statment仅针对一次查询，所以，local-cache-scope: statment可以理解为关闭一级缓存。
     */
    @Test
    @Transactional(rollbackFor = Throwable.class)
    void testFirstCache(){
        //第一次查询，缓存到一级缓存
        userMapper.selectByPrimaryKey(1);
        //第二次查询，从一级缓存中获取
        userMapper.selectByPrimaryKey(1);
    }
    @Test
    void testSecondCache(){
        //第一次查询，缓存到二级缓存
        userMapper.selectByPrimaryKey(1);
        //第二次查询，从二级缓存中获取
        userMapper.selectByPrimaryKey(1);
    }
//    @Test
//    void testSecondCacheLose(){
//        userMapper.selectByPrimaryKey(1);
//        userMapper.selectByPrimaryKey(1);
//        User user = new User(1,"ls","123333");
//        userMapper.updateByPrimaryKeySelective(user);
//        userMapper.selectByPrimaryKey(1);
//    }

    @Test
    void testGetToken2(){
//        String token = JWTUtil.getToken(1, "123456");
//        System.out.println(token);
//        DecodedJWT jwt = JWT.decode(token);
//        System.out.println(jwt.getHeader());
//        System.out.println(jwt.getSignature());
//        System.out.println(jwt.getClaim("uid").asInt());
//        System.out.println(jwt.getAlgorithm());
    }
//    @Test
//    void testShiro(){
//        Subject currentUser = SecurityUtils.getSubject();
//        Session session = currentUser.getSession();
//        session.setAttribute("key","value");
//
//        if (!currentUser.isAuthenticated()){
//            UsernamePasswordToken token = new UsernamePasswordToken("zs","123456");
//            token.setRememberMe(true);
//            currentUser.login(token);
//
//            try {
//                currentUser.login(token);
//            }catch (UnknownAccountException e){
//                System.out.println("用户不匹配");
//            }catch (IncorrectCredentialsException e){
//                System.out.println("密码错误");
//            }catch (LockedAccountException e){
//                System.out.println("账户被锁定");
//            }catch (AuthenticationException e){
//                System.out.println("授权异常");
//            }
//        }
//    }
    @Test
    void testMybatisLazyLoad(){
        Role role = roleMapper.findRoleById(1L);
        role.getPermissions().forEach(System.out::println);
    }
}
