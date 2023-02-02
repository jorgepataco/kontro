package com.kontro.datasources.services.parametros;

import com.kontro.enums.DataSourceEnum;

public class DBContextHolderParametros {
	
	private static final ThreadLocal<DataSourceEnum> contextHolder = new ThreadLocal<>();
    public static void setCurrentDb(DataSourceEnum dbType) {
        contextHolder.set(dbType);
    }
    public static DataSourceEnum getCurrentDb() {
        return contextHolder.get();
    }
    public static void clear() {
        contextHolder.remove();
    }

}
