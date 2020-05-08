package com.iccKevin.utils;

import org.aspectj.lang.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @description: 事务处理的工具类
 * @author: iccKevin
 * @create: 2020-05-08 08:31
 **/
//@Component("transaction")
//@Aspect
//public class AccountTransaction {
//
//    @Autowired
//    private ConnectionUtils cu;
//
//    @Pointcut("execution(void com.iccKevin.service.impl.AccountServiceImpl.transfer(..))")
//    private void sv(){};
//
//    /**
//     * 开启事务
//     */
//    @Before("sv()")
//    public void openTrans(){
//        try {
//            cu.getThreadConnection().setAutoCommit(false);
//            System.out.println("前置通知");
//        }catch (Exception e){
//            e.printStackTrace();
//        }
//    }
//
//    /**
//     * 提交事务
//     */
//    @AfterReturning("sv()")
//    public void commit(){
//        try {
//            cu.getThreadConnection().commit();
//            System.out.println("后置通知");
//        }catch (Exception e){
//            e.printStackTrace();
//        }
//    }
//
//    /**
//     * 回滚事务
//     */
//    @AfterThrowing("sv()")
//    public void rollback(){
//        try {
//            cu.getThreadConnection().rollback();
//            System.out.println("异常通知");
//        }catch (Exception e){
//            e.printStackTrace();
//        }
//    }
//
//    /**
//     * 释放连接
//     */
//    @After("sv()")
//    public void release(){
//        try {
//            // 先还回线程池中，再把线程和连接解绑
//            cu.getThreadConnection().close();
//            cu.removeConnection();
//            System.out.println("最终通知");
//        }catch (Exception e){
//            e.printStackTrace();
//        }
//    }
//}