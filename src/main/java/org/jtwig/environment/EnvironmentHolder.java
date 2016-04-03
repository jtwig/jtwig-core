package org.jtwig.environment;

public class EnvironmentHolder {
    private static ThreadLocal<Environment> instance = new InheritableThreadLocal<>();

    public static Environment get () {
        return instance.get();
    }

    public static Environment set (Environment environment) {
        instance.set(environment);
        return environment;
    }
}
