package com.example.ange.angeunit.mock;

import com.example.ange.angeunit.utils.StringUtil;
import com.example.ange.angeunit.utils.StringUtil2;

import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import static org.mockito.Matchers.anyString;

/**
 * Created by Administrator on 2016/10/2.
 */
public class MockTest {
    @Test
    public void add_test(){
       StringUtil stringUtil= Mockito.mock(StringUtil.class);
        StringUtil2 stringUtil2=new StringUtil2(stringUtil);
        stringUtil2.add("1","2");
        Mockito.verify(stringUtil).add(anyString(),anyString());
    }



}
