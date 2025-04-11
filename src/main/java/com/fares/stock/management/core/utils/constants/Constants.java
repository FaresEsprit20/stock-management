package com.fares.stock.management.core.utils.constants;


public interface Constants {

    String APP_ROOT = "stock/management/v1";

    String SUPPLIER_ORDER_ENDPOINT = APP_ROOT + "/supplier/orders";
    String CREATE_SUPPLIER_ORDER_ENDPOINT = SUPPLIER_ORDER_ENDPOINT + "/create";
    String FIND_SUPPLIER_ORDER_BY_ID_ENDPOINT = SUPPLIER_ORDER_ENDPOINT + "/{supplierOrderId}";
    String FIND_SUPPLIER_ORDER_BY_CODE_ENDPOINT = SUPPLIER_ORDER_ENDPOINT + "/filter/{supplierOrderCode}";
    String FIND_ALL_SUPPLIER_ORDER_ENDPOINT = SUPPLIER_ORDER_ENDPOINT + "/all";
    String DELETE_SUPPLIER_ORDER_ENDPOINT = SUPPLIER_ORDER_ENDPOINT + "/delete/{supplierOrderId}";

    String ENTERPRISE_ENDPOINT = APP_ROOT + "/enterprises";

    String SUPPLIER_ENDPOINT = APP_ROOT + "/suppliers";

    String USER_ENDPOINT = APP_ROOT + "/users";

    String SALES_ENDPOINT = APP_ROOT + "/sales";

    String AUTHENTICATION_ENDPOINT = APP_ROOT + "/auth";

    String ACCOUNTS_ENDPOINT = APP_ROOT + "/accounts/management";


}

