package com.kontro.datasources.services.parametros;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

public class MultiRoutingDataSourceParametros extends AbstractRoutingDataSource{
	@Override
    protected Object determineCurrentLookupKey() {
        return DBContextHolderParametros.getCurrentDb();
    }

}
