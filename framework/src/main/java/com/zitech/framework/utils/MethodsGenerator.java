package com.zitech.framework.utils;

import com.google.gson.reflect.TypeToken;
import com.zitech.framework.data.network.response.ApiResponse;

import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Map;

/**
 * Created by lu on 2016/6/1.
 */
public class MethodsGenerator {

    public enum SchedulersStrategy{
        applyComputationSchedulers,applyIoSchedulers,applyNewSchedulers,applyTrampolineSchedulers,applyExecutorSchedulers

    }

    public static void main(String[] args) {
//        create(AccountService.class,"getAccountService()", SchedulersStrategy.applyIoSchedulers);
    }

    private static String getSimpleName(Class type) {
        String typeName = type.getSimpleName();
        return String.valueOf(typeName.charAt(0)).toLowerCase() + typeName.substring(1);
    }

    public static void create(Class serviceClazz, String paramPrefix,SchedulersStrategy strategy) {
        create(serviceClazz, paramPrefix, new ArgNameMapping() {
            @Override
            public String map(Method method, Class argType, int argPosition) {
                if (argPosition == 0 && argType == String.class) {
                    return "token";
                } else if (Map.class.isAssignableFrom(argType)) {
                    return "args";
                } else {
                    String typeName = argType.getSimpleName();
                    if (typeName.endsWith("Request")) {
                        return "request";
                    } else {
                        return getSimpleName(argType);
                    }
                }
            }
        }, new ExtraMapping() {
            @Override
            public String map(Method method,SchedulersStrategy strategy) {
                Type returnType = method.getGenericReturnType();
                String[] typeNames = getTypeNames(method);
                boolean isHttpResponse = false;
                if (returnType instanceof ParameterizedType) {
                    ParameterizedType type = (ParameterizedType) returnType;
                    Type argType0 = type.getActualTypeArguments()[0];
                    Class rawType = TypeToken.get(argType0).getRawType();
                    if (ApiResponse.class.isAssignableFrom(rawType)) {
                        isHttpResponse = true;
                    }
                }
                StringBuilder buffer = new StringBuilder();
                if (isHttpResponse) {
                    StringBuilder mapTypeParam = new StringBuilder();
                    mapTypeParam.append(".map(new ");
                    mapTypeParam.append("HttpResultFunc");
                    mapTypeParam.append("<").append(typeNames[typeNames.length - 1]).append(">").append("())");
                    buffer.append(mapTypeParam);
                }
                buffer.append(".compose(SchedulersCompat.");
                buffer.append("<").append(typeNames[typeNames.length - 1]).append(">");
                buffer.append(strategy.name());
                buffer.append("())");
                return buffer.toString();
            }
        },strategy);
    }

    public static void create(Class serviceClazz, String serviceParamName, ArgNameMapping argMapping, ExtraMapping extraMapping,SchedulersStrategy strategy) {

        for (int i = 0; i < serviceClazz.getDeclaredMethods().length; i++) {
            Method m = serviceClazz.getDeclaredMethods()[i];
            StringBuilder buffer = new StringBuilder();
            buffer.append("public static ");
            String[] typeNames = getTypeNames(m);
            buffer.append("Observable");
            buffer.append("<").append(typeNames[typeNames.length - 1]).append(">");
            String methodName = m.getName();
            buffer.append(" " + methodName);
            buffer.append("(");
            Class[] argTypes = m.getParameterTypes();
            String[] argNames = new String[argTypes.length];
            for (int k = 0; k < argTypes.length; k++) {
                Class argType = argTypes[k];
                buffer.append(argType.getSimpleName()).append(" ");
                String argName = argMapping.map(m, argType, k);
                argNames[k] = argName;
                buffer.append(argName);
                if (k != argTypes.length - 1) {
                    buffer.append(",");
                }
            }
            buffer.append(")");
            buffer.append("{").append("\n");
            buffer.append("    ");
            buffer.append("return ");
            buffer.append(serviceParamName).append(".").append(methodName).append("(");
            for (int l = 0; l < argNames.length; l++) {
                buffer.append(argNames[l]);
                if (l != argNames.length - 1) {
                    buffer.append(",");
                }
            }
            buffer.append(")");
            String extra = extraMapping.map(m,strategy);
            buffer.append(extra);
            buffer.append(";");
            buffer.append("\n");
            buffer.append("}");
            buffer.append("\n");
            System.out.println(buffer);
        }
    }

    private static String[] getTypeNames(Method method) {
        String returnType = method.getGenericReturnType().toString();
        String[] typeNames = returnType.split("<");
        for (int i = 0; i < typeNames.length; i++) {
            String typeName = typeNames[i].substring(typeNames[i].lastIndexOf(".") + 1);
            typeNames[i] = typeName;
        }
        typeNames[typeNames.length - 1] = typeNames[typeNames.length - 1].replaceAll(">", "");
        return typeNames;

    }

    public interface ArgNameMapping {
        public String map(Method method, Class argType, int argPositon);
    }

    public interface ExtraMapping {
        public String map(Method method, SchedulersStrategy strategy);
    }
}
