package com.nianien.idea.database.table;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 获取DataTable对象的工厂类,该工厂类针对每个Class创建全局唯一的DataTable实例
 *
 * @author skyfalling
 */
public class DataTableFactory {
    /**
     * 存储Datable实例的容器,针对每个Class类型全局唯一
     */
    private final static Map<Class, DataTable> container = new ConcurrentHashMap<Class, DataTable>();

    /**
     * 禁止实例化
     */
    private DataTableFactory() {
    }

    /**
     * 注册实体类型
     *
     * @param entityClass
     * @return
     */
    private static DataTable register(Class entityClass) {
        if (!container.containsKey(entityClass)) {
            synchronized (entityClass) {
                if (!container.containsKey(entityClass)) {
                    container.put(entityClass, new DataTableImpl(entityClass));
                }
            }
        }
        return container.get(entityClass);
    }

    /**
     * 获取DataTable对象,并指定表名
     *
     * @param tableName   表名
     * @param entityClass 实体类型
     * @return
     */
    public static DataTable get(final String tableName, Class entityClass) {
        return new DataTableWrapper(register(entityClass)) {
            @Override
            public String getName() {
                return tableName;
            }
        };
    }

    /**
     * 获取DataTable对象,采用默认表名
     *
     * @param entityClass 实体类型
     * @return
     */
    public static DataTable get(Class entityClass) {
        return new DataTableWrapper(register(entityClass));
    }


}