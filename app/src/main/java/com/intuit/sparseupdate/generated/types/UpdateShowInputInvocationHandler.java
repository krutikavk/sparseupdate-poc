package com.intuit.sparseupdate.generated.types;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class UpdateShowInputInvocationHandler implements InvocationHandler {
    private UpdateShowInput target;

    public UpdateShowInputInvocationHandler(UpdateShowInput obj) {
        target = obj;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        if(method.getName().equals("getTitle")) {
            System.out.println("getTitle invoked, args: " + args[0]);

        }
        if(method.getName().equals("getReleaseYear")) {
            System.out.println("getTitle invoked, args: " + args[0]);
        }
        return method.invoke(target, args);
    }
}