package com.pxp.SQLite.demo.dialect;

import org.hibernate.MappingException;
import org.hibernate.dialect.identity.IdentityColumnSupportImpl;

public class SQLiteIdentityColumnSupport extends IdentityColumnSupportImpl {

    public SQLiteIdentityColumnSupport() {
        super();
    }

    @Override
    public boolean supportsIdentityColumns() {
        return true;
    }

    @Override
    public String getIdentitySelectString(String table, String column, int type) throws MappingException {
        return "SELECT last_insert_rowid()";
    }

    @Override
    public String getIdentityColumnString(int type) throws MappingException {
        return "INTEGER PRIMARY KEY AUTOINCREMENT";
    }

    @Override
    public boolean hasDataTypeInIdentityColumn() {
        return false; // As it is already in getIdentityColumnString()
    }
}
