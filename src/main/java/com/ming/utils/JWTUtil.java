package com.ming.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.ming.pojo.User;

public class JWTUtil {
    public static final Long EXPIRE_TIME = 24*60*60*1000l;


    /**
     * 根据用户信息生成简单的token
     * @param user
     * @return
     */
    public static String getToken(User user){
        String token = "";
        //withAudience()存入需要保存在token的信息
        //Algorithm.HMAC256():使用HS256生成token,密钥则是用户的密码，唯一密钥的话可以保存在服务端。
        token = JWT.create()
                //.withClaim("username",user.getUsername())
                .withAudience(user.getId().toString())
                .sign(Algorithm.HMAC256(user.getPassword()));
        return token;
    }

//    /**
//     *
//     * @param secret
//     * @return
//     */
//    public static String getToken(Integer uid,String secret){
//        //header
//        Map<String,Object> map = new HashMap<>();
//        map.put("alg","HS256");
//        map.put("typ","JWT");
//
//        long currentTimeMillis = System.currentTimeMillis();
//        Date expireTime = new Date(currentTimeMillis+EXPIRE_TIME);
//
//
//        String token = JWT.create()
//                .withHeader(map)
//                .withClaim("uid", uid)
//                .withExpiresAt(expireTime)//设置过期时间
//                .withIssuedAt(new Date())//设置签发时间
//                //对传递的字段进行加密操作，私匙
//                .sign(Algorithm.HMAC256(secret));
//
//        return token;
//    }

//    public static boolean isVerify(String token,Integer uid,String secret){
//        Algorithm algorithm = Algorithm.HMAC256(secret);
//        JWTVerifier verifier = JWT.require(algorithm).withClaim("name", uid).build();
//        try {
//            verifier.verify(token);
//        }catch (JWTVerificationException e){
//            return false;
//        }
//        return true;
//    }

//    public static boolean isVerify(String token,User user){
//        Algorithm algorithm = Algorithm.HMAC256(user.getPassword());
//        JWTVerifier verifier = JWT.require(algorithm).withClaim("username",user.getUsername()).build();
//        try {
//            verifier.verify(token);
//        }catch (JWTVerificationException e){
//            return false;
//        }
//        return true;
//    }

}
