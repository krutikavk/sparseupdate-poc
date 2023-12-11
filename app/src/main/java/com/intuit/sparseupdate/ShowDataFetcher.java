package com.intuit.sparseupdate;

import com.intuit.sparseupdate.generated.DgsConstants;
import com.intuit.sparseupdate.generated.types.Show;
import com.intuit.sparseupdate.generated.types.UpdateShowInput;
import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsMutation;
import com.netflix.graphql.dgs.DgsQuery;
import com.netflix.graphql.dgs.exceptions.DgsBadRequestException;
import com.netflix.graphql.dgs.exceptions.DgsEntityNotFoundException;
import graphql.schema.DataFetchingEnvironment;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

import static java.util.Locale.ENGLISH;

@DgsComponent
public class ShowDataFetcher {

    private final Map<String,Show> data = new HashMap<>();

    public ShowDataFetcher() {
        Show one = createShow("one", "Show One", 2023);
        data.put(one.getId(), one);
    }

    @DgsQuery
    public Show show(String id) {
        return data.get(id);
    }

    @DgsMutation(field = DgsConstants.MUTATION.UpdateShow)
    public Show updateShowManualDeserialization(DataFetchingEnvironment dfe) throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {

        Map<String,Object> inputArgument = dfe.getArgument(DgsConstants.MUTATION.UPDATESHOW_INPUT_ARGUMENT.Input);
//        UpdateShowInput manuallyDeserializedInput = new ObjectMapper().convertValue(inputArgument, UpdateShowInput.class);
        Class<UpdateShowInput> targetClass = UpdateShowInput.class;
        UpdateShowInput inputThroughReflection = getInputArgument(inputArgument, targetClass);

        UpdateShowInput input = inputThroughReflection;

        if (input != null && input.getId() == null) {
            throw new DgsBadRequestException("Invalid ID");
        }
        Show show = data.get(input.getId());
        if (show == null) {
            String msg = String.format("Show with id %s not found", input.getId());
            throw new DgsEntityNotFoundException(msg);
        }

        Show updatedShow = advancedMapper(show, input);
        data.put(input.getId(), updatedShow);
        return updatedShow;
    }

    //This mapping is similar to com.netflix.graphql.dgs.internal.DefaultInputObjectMapper.mapToJavaObject()
    private <T> T getInputArgument(Map<String,Object> rawMap, Class<T> targetClass) throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        if (Objects.isNull(rawMap)  || rawMap.isEmpty()) {
            throw new IllegalArgumentException("Invalid input");
        }
        Constructor<T> ctor = ReflectionUtils.accessibleConstructor(targetClass);
        ReflectionUtils.makeAccessible(ctor);
        T instance = ctor.newInstance();
        rawMap.forEach((key, value) -> {
            try {
                invokeSetter(targetClass, instance, key, value);
            } catch (InvocationTargetException e) {
                throw new RuntimeException(e);
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        });
        return instance;
    }

    private <T> void invokeSetter(Class<T> targetClass, T instance, String fieldName, Object fieldValue) throws InvocationTargetException, IllegalAccessException {
        Method setter = setterMethod(targetClass, fieldName, fieldValue);
        setter.invoke(instance, fieldValue);
    }

    private <T> Method setterMethod(Class<T> targetClass, String fieldName, Object fieldValue) {
        String setterMethodName = getSetterMethodName(fieldName);
        //Reflection utils adds an empty array, can not find a method that matches any argument
        //So explicitly set the arguments array to null
        Class<?>[] arguments = null;
        if (fieldValue != null) {
            arguments = new Class<?>[] {fieldValue.getClass()};
        }
        Method setterMethod = ReflectionUtils.findMethod(targetClass, setterMethodName, arguments);
        if(setterMethod == null) {
            throw new RuntimeException("Unable to find setter method");
        }
        return setterMethod;
    }

    private String getSetterMethodName(String fieldName) {
        if (fieldName == null || fieldName.isEmpty()) {
            return fieldName;
        }
        return "set" + fieldName.substring(0, 1).toUpperCase(ENGLISH) + fieldName.substring(1);
    }

    private Show advancedMapper(Show beforeUpdate, UpdateShowInput input) {
        String title = beforeUpdate.getTitle();
        if (input.isSet(UpdateShowInput.Field.TITLE)) {
            title = input.getTitle();
        }
        Integer releaseYear = beforeUpdate.getReleaseYear();
        if (input.isSet(UpdateShowInput.Field.RELEASE_YEAR)) {
            releaseYear = input.getReleaseYear();
        }

        Show show = Show.newBuilder()
                .id(input.getId())
                .title(title)
                .releaseYear(releaseYear)
                .build();
        return show;
    }

    private Show createShow(String id, String title, Integer releaseYear) {
        if (id == null) {
            id = UUID.randomUUID().toString();
        }
        Show show = Show.newBuilder()
                .id(id)
                .title(title)
                .releaseYear(releaseYear)
                .build();
        return show;
    }

}