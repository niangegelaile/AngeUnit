package com.example.ange.angeunit.utils;

import java.util.Stack;

import rx.Subscription;

/**
 * Created by liquanan on 2016/11/10.
 * email :1369650335@qq.com
 */
public class SubscriptionCollectUtil {
    private static Stack<Subscription> collects;

    /**
     * 存储 Subscription
     * @param sb
     */
    public static synchronized void push(Subscription sb){
        if(collects==null){
            collects=new Stack<>();
        }
        collects.push(sb);
    }

    /**
     * 清空Subscription
     */
    public static synchronized void popAll(){
        if(collects!=null){
            while (!collects.isEmpty()){
                Subscription sb= collects.pop();
                if(sb!=null&&!sb.isUnsubscribed()){
                    sb.unsubscribe();
                }
            }
        }

    }
}
