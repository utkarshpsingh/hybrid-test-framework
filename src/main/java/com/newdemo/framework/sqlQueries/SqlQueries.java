package com.newdemo.framework.sqlQueries;


public class SqlQueries {

	
	public static final String OrderLineKeyAndHoldType = 
            "select ORDER_LINE_KEY, HOLD_TYPE from omdb.yfs_order_hold_type "
            + "where ORDER_HEADER_KEY in (select ORDER_HEADER_KEY from omdb.yfs_order_header "
            + "where order_no in '%s')";
    
    public static final String orderHeaderKey = "select DISTINCT ORDER_HEADER_KEY from omdb.yfs_order_header "
            + "where order_no in '%s'";

	
	public static final String ITEM_IDS_FOR_ORDER_NO = "select ol.ITEM_ID "
			+ "from omdb.yfs_order_line ol, omdb.yfs_order_header oh "
			+ "where ol.order_header_key = oh.order_header_key " + "and oh.order_no = '%s'";

	public static final String ITEM_ID_FOR_ORDER_LINE = "select ITEM_ID " + "from omdb.yfs_order_line "
			+ "where order_line_key = '%s'";

}
