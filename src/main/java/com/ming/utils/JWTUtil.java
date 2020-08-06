package com.ming.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.ming.pojo.User;
import lombok.extern.slf4j.Slf4j;

import java.util.Date;

@Slf4j
public class JWTUtil {
    /**
     * 设置过期时间30天
     */
    public static final Long EXPIRE_TIME = 30*24*60*60*1000l;


    /**
     * 根据用户信息生成简单的token
     * @param user
     * @return
     */
    public static String generateToken(User user){
        //withAudience()存入需要保存在token的信息
        //Algorithm.HMAC256():使用HS256生成token,密钥则是用户的密码，唯一密钥的话可以保存在服务端。
        Algorithm algorithm = Algorithm.HMAC256(user.getPassword());
        return JWT.create()
                .withClaim("uid",user.getId())
                //设置过期时间为30天以后
                .withExpiresAt(new Date(System.currentTimeMillis()+EXPIRE_TIME))
                .sign(algorithm);
    }

    /**
     * 获取jwt中的用户id
     * @param token
     * @return
     */
    public static Integer getUid(String token){
       DecodedJWT jwt = JWT.decode(token);
       Claim uid = jwt.getClaim("uid");
       return uid.asInt();


    }

    /**
     * 校验token
     * @param token
     * @param user
     * @return
     */
    public static boolean verify(String token,User user){
        Algorithm algorithm = Algorithm.HMAC256(user.getPassword());
        JWTVerifier verifier = JWT.require(algorithm)
                .withClaim("uid",user.getId())
                .build();
        try {
            verifier.verify(token);
        }catch (JWTVerificationException e){
            return false;
        }
        return true;
    }

}
