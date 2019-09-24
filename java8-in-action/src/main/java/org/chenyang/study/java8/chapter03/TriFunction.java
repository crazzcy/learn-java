package org.chenyang.study.java8.chapter03;

/**
 * @author ChenYang
 * @param <T>
 * @param <S>
 * @param <M>
 * @param <R>
 */
@FunctionalInterface
public interface TriFunction<T,S,M,R> {
    R apply(T t, S s, M m);
}
