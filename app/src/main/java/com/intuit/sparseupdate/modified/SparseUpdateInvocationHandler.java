package com.intuit.sparseupdate.modified;

import java.lang.reflect.*;
import java.util.HashSet;
import java.util.Collections;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;

public class SparseUpdateInvocationHandler implements InvocationHandler  {
    private Object delegate;
    private Class<?>[] interfaces;
    private Set<String> fieldPresence;

    public SparseUpdateInvocationHandler(Object input, Class<?>[] interfaces, Map<String, Object> rawArgumentsMap) {
        this.delegate = input;
        this.interfaces = interfaces;
        this.fieldPresence = new HashSet<>(rawArgumentsMap.keySet());
    }

    public static Object getDynamicProxy(Object obj, Map<String, Object> rawArgumentsMap) {
        Class<?>[] inputObjInterfaces = obj.getClass().getInterfaces();
        Class<?>[] interfaces = new Class<?>[inputObjInterfaces.length+1];
        for (int i=0; i<inputObjInterfaces.length; i++) {
            interfaces[i]=inputObjInterfaces[i];
        }
        interfaces[interfaces.length-1] = IPresenceFields.class;

        return java.lang.reflect.Proxy.newProxyInstance(
                obj.getClass().getClassLoader(),
                interfaces,
                new SparseUpdateInvocationHandler(obj, interfaces, rawArgumentsMap));
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        //For setters, set fields
        // https://amitstechblog.wordpress.com/2011/07/24/java-proxies-and-undeclaredthrowableexception/
        // Use regex to extract property name inside

        try {
            Class declaringClass = method.getDeclaringClass();
            if (declaringClass == IPresenceFields.class && args.length == 1) {
                return fieldPresence.contains(args[0]);
            }
            return method.invoke(delegate, args);
        } catch (InvocationTargetException e) {
            throw e.getCause();
        }
    }
}