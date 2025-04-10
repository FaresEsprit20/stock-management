//package com.fares.stock.management.core.interceptor;
//
//
//import org.hibernate.EmptyInterceptor;
//import org.slf4j.MDC;
//import org.springframework.util.StringUtils;
//
//public class Interceptor extends EmptyInterceptor {
//
//    @Override
//    public String onPrepareStatement(String sql) {
//        if (StringUtils.hasLength(sql) && sql.toLowerCase().startsWith("select")) {
//            // select user.
//            final String entityName = sql.substring(7, sql.indexOf("."));
//            final String idEnterprise = MDC.get("idEnterprise");
//            if (StringUtils.hasLength(entityName)
//                    && !entityName.toLowerCase().contains("entreprise")
//                    && !entityName.toLowerCase().contains("roles")
//                    && StringUtils.hasLength(idEnterprise)) {
//
//                if (sql.contains("where")) {
//                    sql = sql + " and " + entityName + ".identerprise = " + idEnterprise;
//                } else {
//                    sql = sql + " where " + entityName + ".identerprise = " + idEnterprise;
//                }
//            }
//        }
//        return super.onPrepareStatement(sql);
//    }
//
//
//}
//
