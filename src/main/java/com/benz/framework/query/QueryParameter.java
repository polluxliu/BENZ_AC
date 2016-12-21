package com.benz.framework.query;

import javax.persistence.Query;
import java.lang.reflect.Field;

/**
 * Created by hongying.fu on 12/21/2016.
 */
public class QueryParameter {
    public void setQueryParameter(Query countQueryWithWhere, Query searchQueryWithWhere) throws IllegalAccessException{
        Class parameterClass = getClass();
        Field[] fields = parameterClass.getDeclaredFields();
        for (int i = 0; i < fields.length; i++) {
            Field f = fields[i];
            f.setAccessible(true);
            Object val = f.get(this);//得到此属性的值
            String type = f.getType().toString();//得到此属性的类型
            if (type.endsWith("String")) {
                String fieldVal = (String) val;
                if (fieldVal != null && !"".equals(fieldVal)) {
                    countQueryWithWhere.setParameter(f.getName(), fieldVal);
                    searchQueryWithWhere.setParameter(f.getName(), fieldVal);
                }
            }

        }
    }

    public String generateSQLWhere() throws IllegalAccessException {
        String sqlWhere = "";
        Class parameterClass = getClass();
        Field[] fields = parameterClass.getDeclaredFields();

        for (int i = 0; i < fields.length; i++) {
            Field f = fields[i];
            f.setAccessible(true);
            Object val = f.get(this);//得到此属性的值
            String type = f.getType().toString();//得到此属性的类型
            if (type.endsWith("String")) {
                String fieldVal = (String) val;
                if (fieldVal != null && !"".equals(fieldVal)) {
                    sqlWhere += f.getName() + " = :" + f.getName() + " and ";
                }
            }
        }

        if (sqlWhere.endsWith(" and ")) {
            sqlWhere = sqlWhere.substring(0, sqlWhere.lastIndexOf("and "));
        }

        return sqlWhere;
    }
}
