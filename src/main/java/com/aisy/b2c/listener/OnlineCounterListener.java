package com.aisy.b2c.listener;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionAttributeListener;
import javax.servlet.http.HttpSessionBindingEvent;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import com.aisy.b2c.pojo.Client;

public class OnlineCounterListener implements HttpSessionListener,
        HttpSessionAttributeListener {
    public static int ONLINECOUNT; // 用于统计在线人数
    /**
     * 查询在线人名称，用于前台页面对于在线人名称的展示  
     */
    public static Set<String> ONLINEPERSONLIST = new HashSet<String>();
    /**
     * 用于查询在线人详细信息
     */
    private Map<String, Object> sessionMap = new HashMap<String, Object>();

    public void sessionCreated(HttpSessionEvent hse) {
         HttpSession session = hse.getSession();
         sessionMap.put(session.getId(), session);
         ONLINECOUNT = sessionMap.size();
    }

    public void sessionDestroyed(HttpSessionEvent hse) {
         ONLINECOUNT = sessionMap.size();
    }

    public void attributeAdded(HttpSessionBindingEvent hsbe) {

        String name = hsbe.getName();
        if ("Client".equals(name)) {
            Client userVO = (Client) hsbe.getValue();
            sessionMap.put(userVO.getClientId() + "", userVO);
            ONLINECOUNT = sessionMap.size();
            ONLINEPERSONLIST.add(userVO.getClientName());
        }
    }

    public void attributeRemoved(HttpSessionBindingEvent hsbe) {
        String name = hsbe.getName();
        if ("Client".equals(name)) {
        	Client userVO = (Client) hsbe.getValue();
            sessionMap.remove(userVO.getClientId() + "");
            ONLINECOUNT = sessionMap.size();
            ONLINEPERSONLIST.remove(userVO.getClientName());
        }

    }

    public void attributeReplaced(HttpSessionBindingEvent hsbe) {
    
        String name = hsbe.getName();
        if ("Client".equals(name)) {
        	Client userVO = (Client) hsbe.getValue();
            sessionMap.remove(userVO.getClientId() + "");
            ONLINECOUNT = sessionMap.size();
            ONLINECOUNT++;
            ONLINEPERSONLIST.remove(userVO.getClientName());
        }
    }

}
