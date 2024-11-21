package com.intuit.sparseupdate.generated.types;

import java.lang.reflect.*;

public class UpdateShowInputInvocationHandler  implements InvocationHandler  {
    private IUpdateShowInput target;

    public UpdateShowInputInvocationHandler(UpdateShowInput updateShowInput) {
        this.target = updateShowInput;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

        //For setters, set fields
        // https://amitstechblog.wordpress.com/2011/07/24/java-proxies-and-undeclaredthrowableexception/
        try {
            if(method.getName().equals("setIsTitleSet")) {
                System.out.println("getTitle invoked");

                // Update isTitleSet through reflection
                Field field = proxy.getClass().getDeclaredField("isTitleSet");
                field.setAccessible(true);
                field.set(field, "true");
                field.setAccessible(false);
            }

            if(method.getName().equals("setIsReleaseYearSet")) {
                System.out.println("getReleaseYear invoked");

                // Update isTitleSet through reflection
                Field field = proxy.getClass().getDeclaredField("isReleaseYearSet");
                field.setAccessible(true);
                field.set(field, "true");
                field.setAccessible(false);
            }

            return method.invoke(target, args);
        } catch (Exception e) {
            throw new Exception("Invocation Exception");
        }


    }
}