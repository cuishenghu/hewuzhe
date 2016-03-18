package com.hewuzhe.ui.http;

/**
 * @author Administrator ������·��
 */
public class UrlContants {

    // public static final String SERVERIP = "121.42.194.222";
    // public static final String SERVERIP = "192.168.1.175";
    public static final String SERVERIP = "http://120.27.115.235";

    // public static final String BASE_URL = "http://121.42.194.222/api.php?";
    // public static final String BASE_URL = "http://192.168.1.175/api.php?";
    public static final String BASE_URL = "http://120.27.115.235/";

    // public static final String IMAGE_URL = "http://121.42.194.222/Uploads/";
    // public static final String IMAGE_URL = "http://192.168.1.175/Uploads/";
    public static final String IMAGE_URL = "http://120.27.115.235/";

    // public static final String ORDERPAY =
    // "http://121.42.194.222/pingxx/api/pay.php";// ֧��
    // public static final String ORDERPAY =
    // "http://192.168.1.175/pingxx/api/pay.php";// ֧��
    public static final String ORDERPAY = "http://115.28.67.86/yl/pingxx/api/pay.php";// ֧��

    //    public static final String BASEURL = BASE_URL ;//第一种方法
    public static final String BASEURL = BASE_URL + "%s";//第二种方法

    public static final String jsonData = "data";

    public static final String jsonProductList = "ProductList";

    public static final String ERROR = "{\"code\":400,\"message\":\"请求失败\",\"datas\":null}";

    public static final String ZERODATA = "{\"code\":200,\"message\":\"没有数据\",\"datas\":\"\"}";

    public static final String GETRECOMMENDPRODUCT = "Hezhuangbei.asmx/GetRecomendCategoryAndProduct?";//获取主页推荐的商品

    public static final String CANCLEORDER = "Hezhuangbei.asmx/CancleProduct?";//取消订单

    public static final String PRODUCTFORDUIHUANLIST = "Hezhuangbei.asmx/SelectProductBySearch?";//获取积分兑换的商品列表

    public static final String PRODUCTDUIHUANLIST = "Hezhuangbei.asmx/SelectPageChangeBillByUserId?";//根据用户ID分页查询兑换记录

    public static final String CHANGENOW = "Hezhuangbei.asmx/ChangeNow?";//立即兑换

    public static final String CHANGEINFO = "Hezhuangbei.asmx/ChangeDetail?";//兑换详情

    public static final String GETORDERBYUSERID = "Hezhuangbei.asmx/SelectPageBillByUserId?";//获取订单 根据用户ID分页查询订单

    public static final String CONFIRMRECEIVED = "Hezhuangbei.asmx/Acceptance?";//确认收货

    public static final String COMMENTORDER = "Hezhuangbei.asmx/CommentProduct?";//评价订单

    public static final String SELECTPRODUCTBYORDERNO = "Hezhuangbei.asmx/SelectBillProduct?";//根据订单ID分页查看订单商品

    public static final String GETCHARGE = "Hezhuangbei.asmx/GetCharge?";//获取charge

    public static final String GETADDRESSBYAREAID = "Hezhuangbei.asmx/GetDeliveryAddressById?";//根据地址ID获取收货地址

    public static final String SELECTDEFAULTADDRESS = "Hezhuangbei.asmx/SelectDefaultDeliveryAddress";//查询默认收货地址，若没有默认收货地址，则返回第一个收货地址

    public static final String GETUSERINFO = "LoginAndRegister.asmx/UpdateUser?";//根据用户id查找用户,用于更新用户信息

    public static final String DELETEORDER = "Hezhuangbei.asmx/DeleteBill?";//删除订单

    public static final String SUBMITBASKTE = "Hezhuangbei.asmx/SubmitBasket?";//购物车结算

    public static final String BUYNOW = "Hezhuangbei.asmx/BuyNow?";//立即购买

    public static final String SHIELDNEWSFRIEND = "Helianmeng.asmx/ShieldNewsFriend?";//获取被屏蔽信息的舞友

    public static final String BANNARLIST = "Hezhuangbei.asmx/SelectIndexImage";//bannar

    public static final String SELECTBILLCOUNT = "Hezhuangbei.asmx/SelectBillCount?";//根据用户ID，查询订单个数

    public static final String CONTACTSMATCH =  "Helianmeng.asmx/ContactsMatch?";   //通讯录匹配，未注册不返回，注册之后判断是否为好友

    public static final String SAVEFRIEND =  "Helianmeng.asmx/SaveFriend?";   //添加武友

    public static final String SELECTTEACHERBYID =  "Hewuzhe.asmx/SelectTeacherById?";   //根据教练的ID查询教练信息

    public static final String SELECTVIDEOBYTEACHERID =  "Hewuzhe.asmx/SelectVideoByTeacherId?";   //根据私教ID获取私教视频

    public static final String SELECTLESSONBYTEACHERID =  "Hewuzhe.asmx/SelectLessonByTeacherId?"; //根据私教ID获取私教课程

    public static final String SELECTGUANZHUBYTEACHERID =  "Hewuzhe.asmx/SelectGuanzhuByTeacherId?";   //根据私教ID获取私教关注

    public static final String SELECTFENSIBYTEACHERID =  "Hewuzhe.asmx/SelectFensiByTeacherId?";   //根据私教ID获取私教粉丝

    public static final String ISWUYOU =  "Helianmeng.asmx/IsWuyou?";   // /* 根据id判断是否为武友 true:是 false:不是 */



    public static String getUrl(String token) {
        if (token == null || token.equals("")) {
            return BASE_URL;
        }
//        return BASEURL + token;//第一种方法
       String s= String.format(BASEURL, token);
        return String.format(BASEURL, token);//第二种方法
    }
}
