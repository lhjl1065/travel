package com.lhjl.travel.web.servlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class BaseServlet extends HttpServlet {
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            //把从url中抽取出方法名,从而把请求分发到对应的方法
            String uri = req.getRequestURI().toString();
            int i = uri.lastIndexOf("/")+1;
            String methodName=null;
            if (uri.contains("?")){
                int j = uri.indexOf("?");
                methodName = uri.substring(i, j);

            }
            else {
                methodName = uri.substring(i);
            }
            System.out.println(methodName);
            Method method = this.getClass().getMethod(methodName, HttpServletRequest.class, HttpServletResponse.class);
            method.invoke(this, req, resp);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }
}
