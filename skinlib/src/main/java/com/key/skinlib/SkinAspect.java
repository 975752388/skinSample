package com.key.skinlib;

import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;

import java.util.Observer;

/**
 * @author key
 * @description:
 * @date :2020/8/17 10:42
 */
@Aspect
public class SkinAspect {

    @Pointcut("execution(* androidx.appcompat.app.AppCompatActivity+.onCreate(..))&&!within(androidx.appcompat.app.AppCompatActivity)")
    public void  pointOnCreate(){

    }
    @Pointcut("execution(* androidx.appcompat.app.AppCompatActivity+.onDestroy())")
    public void  pointOnDestroy(){

    }

    @After("pointOnDestroy()")
    public void afterDestroy(JoinPoint joinPoint){
        AppCompatActivity activity = (AppCompatActivity) joinPoint.getThis();
        if (activity.getLayoutInflater().getFactory2() instanceof Observer)
        SkinEngine.Companion.getInstance().deleteObserver((Observer)activity.getLayoutInflater().getFactory2() );
    }

    @Before("pointOnCreate()")
    public void beforePoint(JoinPoint joinPoint){
//        if (!"androidx.appcompat.app.AppCompatActivity".equals(joinPoint.getSignature().getDeclaringTypeName())) {
            AppCompatActivity activity = (AppCompatActivity) joinPoint.getThis();
            SkinFactory factory = new SkinFactory(activity,activity.getDelegate());
            activity.getLayoutInflater().setFactory2(factory);
            SkinEngine.Companion.getInstance().addObserver(factory);
//        }
    }
}
