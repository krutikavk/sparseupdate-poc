package com.intuit.sparseupdate.generated.types;

import org.springframework.util.StringUtils;

import java.lang.reflect.*;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UpdateShowInputInvocationHandler  implements InvocationHandler  {
    private UpdateShowInput delegate;
    private Set<String> fieldPresence;

    Pattern FIELD_NAME = Pattern.compile("is(?<name>\\w+)Set");

    public UpdateShowInputInvocationHandler(UpdateShowInput updateShowInput, Map<String, Object> rawArgumentsMap) {
        this.delegate = updateShowInput;
        this.fieldPresence = new HashSet<>();

        //Initialize fieldPresence = ["id", "title", "releaseYear"]
        for (Map.Entry<String,Object> entry : rawArgumentsMap.entrySet()) {
            fieldPresence.add(entry.getKey());
        }
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        //For setters, set fields
        // https://amitstechblog.wordpress.com/2011/07/24/java-proxies-and-undeclaredthrowableexception/
        // Use regex to extract property name inside



        try {
            Matcher m = FIELD_NAME.matcher(method.getName());
            if (!m.matches()) {
                return method.invoke(delegate, args);
            } else {
                return fieldPresence.contains(StringUtils.uncapitalize(m.group("name")));
            }

//            if(method.getName().equals("isTitleSet")) {
//                // if fieldPresence contains "title", update "title"
//                System.out.println("getTitle invoked");
//                if(fieldPresence.contains("title"))
//                    return true;
//                else
//                    return false;
//
//            }
//            if(method.getName().equals("isReleaseYearSet")) {
//                System.out.println("getReleaseYear invoked");
//                if(fieldPresence.contains("releaseYear"))
//                    return true;
//                else
//                    return false;
//            }
//            return method.invoke(delegate, args);
        } catch (InvocationTargetException e) {
            throw e.getCause();
        }
    }
}