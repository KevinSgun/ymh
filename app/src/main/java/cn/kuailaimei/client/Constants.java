package cn.kuailaimei.client;

/**
 * Created by lu on 2016/6/16.
 */
public class Constants {

    public static final int VALIDATE_CODE_LENGTH = 6;

    public static final String BAI_DU_MAP = "baidumap";
    public static final String BAI_DU_SPLIT = "@";
    public static final String APP_ID = "wx7d71a97d624c4e02";
    public static String IS_BINDING_JPUSH_ALIAS = "binding_jpush_alias";

    public interface ActivityExtra {

        String CHECK_POSITION = "check_position";

        String LOGIN_ABOUT = "login_about";

        String SCORE_COUNT = "score_count";

        String SID = "sid";

        String TITLE = "title";
        String ID = "id";
        String STATUS = "status";

        String ORDER_ID = "order_id";
        String ORDER = "order";
        String SHOP_INFO = "shop_info";
        String DESIGNER_SERVICE = "designer_service";
        String DESIGNER = "designer";
        int REQUEST_FOR_CHOOSE_SPEC = 1002;
        String GOODS_DETAIL = "goods_detail";
        String SKU_LIST ="sku_list" ;
        String CHOOSE_SKU ="choose_sku" ;
        String STOCK_LIST ="stock_list" ;
        String CHOOSE_STOCK = "choose_stock";
        String LAUNCH_ORDER_MODE = "launch_order_mode";
        String ADDRESS_MODE = "address_mode";
        String IS_NEED_AREA = "is_need_area";

        int SELECT_PROVINCE_NAME =0x10 ;
        int SELECT_CITY_NAME =0x11;
        int SELECT_AREA_NAME = 0x12;
        String ADDRESS ="address" ;
        String PRICE ="price" ;
    }

    public interface FragmentExtra {

        String GROUP_ID = "group_id";
    }

    public interface ImageDefault {

//        int RECTANGLE_LANDSCAPE = R.drawable.;
    }
}
