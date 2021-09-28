package org.xl.java.grammar.generic;

import java.io.Serializable;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.WildcardType;
import java.util.List;
import java.util.Map;

/**
 * 获取泛型信息示例
 *
 * @author xulei
 */
public class GenericDemo extends GenericParent<Double> implements Comparable<String>, Serializable {

    public Map<String, Bird<? extends String>> test(List<Bird<? extends Comparable<? super String>>> list) {
        return null;
    }

    private static void printType(Type type) {
        if (type instanceof ParameterizedType) {
            ParameterizedType parameterizedType = (ParameterizedType) type;
            // rawType
            Type rawType = parameterizedType.getRawType();
            System.out.println("ParameterizedType RawType: " + rawType);
            // actualTypeArguments
            Type[] actualTypeArguments = parameterizedType.getActualTypeArguments();
            for (Type actualTypeArgument : actualTypeArguments) {
                System.out.println("ParameterizedType ActualTypeArgument: " + actualTypeArgument);
                printType(actualTypeArgument);
            }
            // ownerType
            Type ownerType = parameterizedType.getOwnerType();
            System.out.println("ParameterizedType OwnerType: " + ownerType);
        }
        else if (type instanceof WildcardType) {
            WildcardType wildcardType = (WildcardType) type;
            // super限定类型
            Type[] lowerType = wildcardType.getLowerBounds();
            for (Type lower : lowerType) {
                System.out.println("WildcardType LowerBound: " + lower);
                printType(lower);
            }
            // extends限定类型
            Type[] upperType = wildcardType.getUpperBounds();
            for (Type upper : upperType) {
                System.out.println("WildcardType UpperBound: " + upper);
                printType(upper);
            }
        }
    }

    public static void main(String[] args) throws Exception {
        // 类型信息
        Type superType = GenericDemo.class.getGenericSuperclass();
        System.out.println("-----------------------------superType-----------------------------");
        System.out.println(superType);
        printType(superType);
        System.out.println("-------------------------------------------------------------------");

        System.out.println("-----------------------------interfaceType-------------------------");
        Type[] interfaceType = GenericDemo.class.getGenericInterfaces();
        for (Type type : interfaceType) {
            System.out.println(type);
            printType(type);
        }
        System.out.println("-------------------------------------------------------------------");

        // 参数泛型列表
        System.out.println("-----------------------------methodParameter-----------------------");
        Method method = GenericDemo.class.getMethod("test", List.class);
        Type[] genericParameterTypes = method.getGenericParameterTypes();
        for (Type genericParameterType : genericParameterTypes) {
            System.out.println(genericParameterType);
            printType(genericParameterType);
        }
        System.out.println("-------------------------------------------------------------------");

        // 返回值泛型列表
        System.out.println("-----------------------------returnParameter-----------------------");
        Type returnGenericParameterTypes = method.getGenericReturnType();
        System.out.println(returnGenericParameterTypes);
        printType(returnGenericParameterTypes);
        System.out.println("-------------------------------------------------------------------");
    }

    @Override
    public int compareTo(String o) {
        return 0;
    }

    public static class Bird<T> implements Comparable<T> {
        @Override
        public int compareTo(T o) {
            return 0;
        }
    }

}
