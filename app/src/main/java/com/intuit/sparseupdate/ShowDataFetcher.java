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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

@DgsComponent
public class ShowDataFetcher {

    private static final Logger LOGGER = LoggerFactory.getLogger(ShowDataFetcher.class);
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
                setField(targetClass, instance, key, value);
            } catch (InvocationTargetException e) {
                throw new RuntimeException(e);
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        });
        return instance;
    }

    private <T> void setField(Class<T> targetClass, T instance, String fieldName, Object fieldValue) throws IllegalAccessException, InvocationTargetException {
        // Almost same code as com.netflix.graphql.dgs.internal.DefaultInputObjectMapper.mapToJavaObject()
        Field field = ReflectionUtils.findField(targetClass, fieldName);
        String fieldSetterName = "is"+fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1)+"Defined";

        Field fieldSetter = ReflectionUtils.findField(targetClass, fieldSetterName);

        field.setAccessible(true);
        field.set(instance, fieldValue);

        fieldSetter.setAccessible(true);
        fieldSetter.set(instance, true);
    }

    private String convertCamelToScreamingSnakeCase(String input) {
        if (input == null) {
            return input;
        }
        String regex = "(\\p{javaLowerCase})(\\p{javaUpperCase}+)";
        String replacement = "$1_$2";
        return input.replaceAll(regex, replacement).toUpperCase();
    }

    private Show advancedMapper(Show beforeUpdate, UpdateShowInput input) {
        String title = beforeUpdate.getTitle();
        Integer releaseYear = beforeUpdate.getReleaseYear();

        System.out.println("beforeUpdate: " + beforeUpdate + ", input: " + input + "input.isTitleDefined: " + input.getIsTitleDefined());

        if(input.getIsTitleDefined()) {
            title = input.getTitle();
        }
        if(input.getIsReleaseYearDefined()) {
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