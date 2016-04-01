package com.hewuzhe.ui.cons;

import com.hewuzhe.MegaVideo;
import com.hewuzhe.model.AboutUs;
import com.hewuzhe.model.Address;
import com.hewuzhe.model.Article;
import com.hewuzhe.model.ArticleCollection;
import com.hewuzhe.model.Cate;
import com.hewuzhe.model.Charge;
import com.hewuzhe.model.ChatList;
import com.hewuzhe.model.Classification;
import com.hewuzhe.model.Comment;
import com.hewuzhe.model.ConditionComment;
import com.hewuzhe.model.Dojo;
import com.hewuzhe.model.FlyDreamHeader;
import com.hewuzhe.model.Friend;
import com.hewuzhe.model.FriendCondition;
import com.hewuzhe.model.Group;
import com.hewuzhe.model.IntegralRecord;
import com.hewuzhe.model.LiveVideo;
import com.hewuzhe.model.MegaComment;
import com.hewuzhe.model.MegaGame;
import com.hewuzhe.model.MegaGameVideo;
import com.hewuzhe.model.MyDream;
import com.hewuzhe.model.NearPeople;
import com.hewuzhe.model.OtherImage;
import com.hewuzhe.model.OverTime;
import com.hewuzhe.model.Plan;
import com.hewuzhe.model.PrivateTrainerList;
import com.hewuzhe.model.Product;
import com.hewuzhe.model.ProductCollection;
import com.hewuzhe.model.ProductComment;
import com.hewuzhe.model.RecommendUser;
import com.hewuzhe.model.Record;
import com.hewuzhe.model.Res;
import com.hewuzhe.model.Result;
import com.hewuzhe.model.ScreenList;
import com.hewuzhe.model.ShopCar;
import com.hewuzhe.model.Site;
import com.hewuzhe.model.StudyOnlineCatItem;
import com.hewuzhe.model.StudyOnlineCate;
import com.hewuzhe.model.TeamAnnounce;
import com.hewuzhe.model.TeamIntroduce;
import com.hewuzhe.model.Tel;
import com.hewuzhe.model.TrainerLessonInfo;
import com.hewuzhe.model.TrainerLessonTwo;
import com.hewuzhe.model.UploadImage;
import com.hewuzhe.model.User;
import com.hewuzhe.model.Video;
import com.hewuzhe.model.VipPrice;
import com.hewuzhe.model.WebContents;
import com.hewuzhe.model.WrapFriend;

import java.util.ArrayList;

import retrofit.Call;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.Path;
import retrofit.http.Query;
import rx.Observable;

/**
 * Created by xianguangjin on 15/12/14.
 */
public interface ApiService {

    @GET("LoginAndRegister.asmx/Login")
    Observable<Res<User>> Login(@Query("username") String usernmae, @Query("password") String password);

    @GET("LoginAndRegister.asmx/ForgetPassword")
    Observable<Res> ForgetPassword(@Query("phone") String usernmae, @Query("password") String password);

    @GET("LoginAndRegister.asmx/ChangePassWord")
    Observable<Res> ChangePassWord(@Query("userid") int userid, @Query("oldpwd") String oldpwd, @Query("password") String password);

    @GET("LoginAndRegister.asmx/GetPayOverTime")
    Observable<Res<OverTime>> GetPayOverTime(@Query("userid") int userid);

    @GET("LoginAndRegister.asmx/Register")
    Observable<Res> RegisterAndLogin(@Query("phone") String usernmae, @Query("password") String password);

    @GET("LoginAndRegister.asmx/LoginByOtherWithInfor")
    Observable<Res<User>> LoginByOther(@Query("openid") String openid, @Query("nicname") String nicname, @Query("photopath") String photopath);

    @FormUrlEncoded
    @POST("LoginAndRegister.asmx/UpLoadPhoto")
    Observable<Res<UploadImage>> UpLoadPhoto(@Field("fileName") String fileName, @Field("filestream") String filestream, @Field("userid") int userid);

    @FormUrlEncoded
    @POST("Helianmeng.asmx/UpLoadImage")
    Observable<Res<UploadImage>> UpLoadImage(@Field("fileName") String fileName, @Field("filestream") String filestream);

    @GET("Hewuzhe.asmx/SaveMyDream")
    Call<Res> SavetMyDream(@Query("userid") int userid, @Query("mydream") String mydream, @Query("realizedream") String realizedream);

    @GET("Hewuzhe.asmx/SelectMyDream")
    Call<Res<MyDream>> SelectMyDream(@Query("userid") int userid);

    @GET("Hewuzhe.asmx/GetOnlineStudyListByPage")
    Observable<Res<ArrayList<Video>>> GetOnlineStudyList(@Query("startRowIndex") int startRowIndex, @Query("maximumRows") int maximumRows, @Query("categoryid") int categoryid, @Query("search") String search);

    @GET("Hewuzhe.asmx/SelectFavoriteByUserId")
    Call<Res<ArrayList<Video>>> SelectFavoriteByUserId(@Query("startRowIndex") int startRowIndex, @Query("maximumRows") int maximumRows, @Query("isVideo") boolean categoryid, @Query("userid") int userid);


    @GET("Hewuzhe.asmx/SelectFavoriteByUserId")
    Call<Res<ArrayList<ArticleCollection>>> SelectFavoriteByUserIdArticle(@Query("startRowIndex") int startRowIndex, @Query("maximumRows") int maximumRows, @Query("isVideo") boolean categoryid, @Query("userid") int userid);


    @GET("Hewuzhe.asmx/GetOnlineStudy")
    Observable<Res<Video>> GetOnlineStudy(@Query("id") int id, @Query("userid") int userid);

    @GET("Hedongli.asmx/{path}")
    Observable<Res<ArrayList<Video>>> getVideos(@Path("path") String path, @Query("startRowIndex") int startRowIndex, @Query("maximumRows") int maximumRows, @Query("search") String search);

    @GET("Hedongli.asmx/{path}")
    Observable<Res<ArrayList<Video>>> SelectGuanzhuVideoList(@Path("path") String path,@Query("startRowIndex") int startRowIndex, @Query("maximumRows") int maximumRows, @Query("userid") int userId);


    @GET("Hewuzhe.asmx/GetPlanByCate")
    Observable<Res<ArrayList<Plan>>> GetPlanByCate(@Query("userid") int userId, @Query("cateid") int cateId, @Query("startRowIndex") int startRowIndex, @Query("maximumRows") int maximumRows);


    @GET("Hedongli.asmx/GetChannel")
    Call<Res<ArrayList<Cate>>> GetChannel(@Query("startRowIndex") int startRowIndex, @Query("maximumRows") int maximumRows);

    @GET("Hedongli.asmx/SelectVideoByCategory")
    Observable<Res<ArrayList<Video>>> SelectVideoByCategory(@Query("startRowIndex") int startRowIndex, @Query("maximumRows") int maximumRows, @Query("cateid") int categoryid, @Query("search") String search);


    @FormUrlEncoded
    @POST("Hewuzhe.asmx/SaveOrEditVideoMessage")
    Observable<Res> SaveOrEditVideoMessage(@Field("id") int id, @Field("Title") String Title, @Field("ImagePath") String ImagePath, @Field("Content") String Content, @Field("VideoPath") String VideoPath, @Field("IsOriginal") String IsOriginal, @Field("IsFree") String IsFree, @Field("CategoryId") int CategoryId, @Field("UserId") int UserId, @Field("VideoDuration") String VideoDuration, @Field("uploadType") int uploadType);


    @FormUrlEncoded
    @POST("Hewuzhe.asmx/UpLoadVideo")
    Observable<Res<Video>> UpLoadVideo(@Field("fileName") String fileName, @Field("filestream") String filestream);

    @GET("Hedongli.asmx/GetOtherVideo")
    Observable<Res<ArrayList<Video>>> GetOtherVideo(@Query("userid") int userid, @Query("num") int num, @Query("id") int id);

    @GET("Hewuzhe.asmx/SelectCommentByMessageId")
    Observable<Res<ArrayList<Comment>>> SelectCommentByMessageId(@Query("messageId") int messageId);


    @GET("Helianmeng.asmx/SelectCommentByMessageId")
    Observable<Res<ArrayList<Comment>>> SelectCommentByMessageIdDT(@Query("id") int id);

    @GET("Hewuzhe.asmx/MessageRepeatAndFavorite")
    Observable<Res> SelectCommentByMessageId(@Query("id") int id, @Query("userid") int userid, @Query("flg") int flg, @Query("description") String description);

    @GET("Hewuzhe.asmx/MessageRepeatAndFavoriteCancel")
    Observable<Res> MessageRepeatAndFavoriteCancel(@Query("id") int id, @Query("userid") int userid, @Query("flg") int flg);

    @GET("Hewuzhe.asmx/GuanzhuTeacher")
    Observable<Res> guanzhuTeacher(@Query("teacherid") int teacherid, @Query("userid") int userid);

    @GET("Hewuzhe.asmx/CancelGuanzhuTeacher")
    Observable<Res> cancelGuanzhuTeacher(@Query("teacherid") int teacherid, @Query("userid") int userid);

    @GET("Helianmeng.asmx/MessageRepeatAndFavorite")
    Call<Res> MessageRepeatAndFavorite(@Query("id") int id, @Query("userid") int userid, @Query("flg") int flg);

    @GET("Hewuzhe.asmx/MessageComment")
    Observable<Res> MessageComment(@Query("id") int id, @Query("userid") int userid, @Query("comment") String comment);

    @GET("Helianmeng.asmx/MessageComment")
    Observable<Res> MessageCommentDT(@Query("id") int id, @Query("userid") int userid, @Query("comment") String comment);

    @GET("Helianmeng.asmx/CommentComment")
    Call<Res> CommentComment(@Query("id") int id, @Query("userid") int userid, @Query("comment") String comment);


    @GET("Helianmeng.asmx/SaveDongtai")
    Observable<Res> SaveDongtai(@Query("pathlist") String pathlist, @Query("userid") int userid, @Query("content") String content, @Query("videoImage") String videoImage, @Query("videopath") String videopath, @Query("videoDuration") String videoDuration);

    @GET("Hewuzhe.asmx/SavePlan")
    Observable<Res> SavePlan(@Query("piclist") String piclist, @Query("userid") int userid, @Query("content") String content, @Query("title") String title, @Query("cateid") int cateid, @Query("starttime") String starttime, @Query("endtime") String endtime);


    @GET("LoginAndRegister.asmx/UpdateUser")
    Observable<Res<User>> UpdateUser(@Query("userid") int userid);

    @GET("Helianmeng.asmx/{path}")
    Observable<Res<ArrayList<Cate>>> GetLianmengCate(@Path("path") String path);

    @GET("Helianmeng.asmx/GetMyDongtaiPage")
    Observable<Res<ArrayList<ConditionComment>>> GetMyDongtaiPage(@Query("userid") int userid, @Query("startRowIndex") int startRowIndex, @Query("maximumRows") int maximumRows);


    @GET("LoginAndRegister.asmx/ChangeInfor")
    Observable<Res> ChangeInfor(@Query("userid") int userid, @Query("nicname") String nicname, @Query("sexuality") int sexuality, @Query("height") int height, @Query("weight") int weight, @Query("homeAreaId") int homeAreaId, @Query("experience") int experience, @Query("description") String description, @Query("birthday") String birthday);


    @GET("Hewuzhe.asmx/GetCreditRecord")
    Observable<Res<ArrayList<IntegralRecord>>> GetCreditRecord(@Query("startRowIndex") int startRowIndex, @Query("maximumRows") int maximumRows, @Query("userid") int userid);

    @GET("Hewuzhe.asmx/GetBigCateForOnlineStudy")
    Observable<Res<ArrayList<StudyOnlineCate>>> GetBigCateForOnlineStudy();

    @GET("Hewuzhe.asmx/GetSmallCateForOnlineStudy")
    Observable<Res<ArrayList<StudyOnlineCatItem>>> GetSmallCateForOnlineStudy(@Query("categoryid") int categoryid);

    @GET("Hedongli.asmx/GetChannel")
    Observable<Res<ArrayList<StudyOnlineCatItem>>> GetChannel();


    @GET("Hewuzhe.asmx/SelectWuGuanPageByCityId")
    Observable<Res<ArrayList<Dojo>>> SelectWuGuanPageByCityId(@Query("startRowIndex") int startRowIndex, @Query("maximumRows") int maximumRows, @Query("cityid") int cityid);

    @GET("Hewuzhe.asmx/SelectWuGuanPageByCityName")
    Observable<Res<ArrayList<Dojo>>> SelectWuGuanPageByCityName(@Query("startRowIndex") int startRowIndex, @Query("maximumRows") int maximumRows, @Query("areaname") String areaname, @Query("lat") String lat, @Query("lng") String lng);

    @GET("LoginAndRegister.asmx/GetProvince")
    Call<Res<ArrayList<Address>>> getProvince();

    @GET("LoginAndRegister.asmx/GetCityByProvince")
    Call<Res<ArrayList<Address>>> GetCityByProvince(@Query("provinceCode") int provinceCode);

    @GET("LoginAndRegister.asmx/GetCountyByCity")
    Call<Res<ArrayList<Address>>> GetCountyByCity(@Query("cityCode") int cityCode);

    @GET("LoginAndRegister.asmx/GetCity")
    Observable<Res<ArrayList<Address>>> GetCity();

    @GET("Helianmeng.asmx/GetDongtaiPageByLianmeng")
    Call<Res<ArrayList<Article>>> GetDongtaiPageByLianmeng(@Query("startRowIndex") int startRowIndex, @Query("maximumRows") int maximumRows, @Query("cateid") int cateid, @Query("userid") int userid);

    @GET("Helianmeng.asmx/GetHezuoListByPage")
    Observable<Res<ArrayList<Article>>> GetHezuoListByPage(@Query("startRowIndex") int startRowIndex, @Query("maximumRows") int maximumRows, @Query("categoryid") int cateid, @Query("userid") int userid);

    @GET("Helianmeng.asmx/GetJianghuListByPage")
    Call<Res<ArrayList<Article>>> GetJianghuListByPage(@Query("startRowIndex") int startRowIndex, @Query("maximumRows") int maximumRows, @Query("categoryid") int cateid, @Query("userid") int userid);

    @GET("Helianmeng.asmx/{path}")
    Observable<Res<ArrayList<MegaGameVideo>>> SelectAllMatchMemberBySearch(@Path("path") String path, @Query("startRowIndex") int startRowIndex, @Query("maximumRows") int maximumRows, @Query("matchId") int matchId, @Query("membercode") int membercode, @Query("nicname") String nicname, @Query("flg") int flg);


    @GET("Helianmeng.asmx/GetFriendBySearch")
    Observable<Res<ArrayList<Friend>>> GetFriendBySearch(@Query("startRowIndex") int startRowIndex, @Query("maximumRows") int maximumRows, @Query("areaId") int areaId, @Query("nicName") String nicName, @Query("age") int age, @Query("sexuality") int sexuality, @Query("userid") int userid);

    @GET("Helianmeng.asmx/GetFriendByAreaCodeAndSearch")
    Observable<Res<ArrayList<Friend>>> GetFriendByAreaCodeAndSearch(@Query("startRowIndex") int startRowIndex, @Query("maximumRows") int maximumRows, @Query("areaCode") int areaId, @Query("nicName") String nicName, @Query("age") int age, @Query("sexuality") int sexuality, @Query("userid") int userid);

    @GET("Helianmeng.asmx/SaveFriend")
    Observable<Res> SaveFriend(@Query("userid") int userid, @Query("friendid") int friendid);

    @GET("Helianmeng.asmx/SelectFriended")
    Observable<Res<ArrayList<Friend>>> SelectFriended(@Query("userid") int userid);

    @GET("Helianmeng.asmx/JoinTeam")
    Observable<Res> JoinTeam(@Query("userid") int userid, @Query("teamid") int teamid, @Query("name") String name);

    @GET("Helianmeng.asmx/QuitGroup")
    Observable<Res> QuitGroup(@Query("userid") int userid, @Query("teamid") int teamid);

    @GET("Helianmeng.asmx/DeleteFriend")
    Observable<Res> DeleteFriend(@Query("userid") int userid, @Query("friendid") int friendid);


    @GET("Helianmeng.asmx/SelectTeamPage")
    Observable<Res<ArrayList<Group>>> SelectTeamPage(@Query("startRowIndex") int startRowIndex, @Query("maximumRows") int maximumRows, @Query("citycode") int citycode, @Query("name") String name);

    @GET("Helianmeng.asmx/{path}")
    Observable<Res<ArrayList<MegaGame>>> getGames(@Path("path") String path, @Query("startRowIndex") int startRowIndex, @Query("maximumRows") int maximumRows);

    @GET("Helianmeng.asmx/SelectMatchDetail")
    Call<Res<MegaGame>> SelectMatchDetail(@Query("matchId") int matchId);

    @GET("Helianmeng.asmx/DeleteFriended")
    Observable<Res> DeleteFriended(@Query("id") int id);

    @GET("Helianmeng.asmx/SelectMatchDetailWithIsJoin")
    Observable<Res<MegaGame>> SelectMatchDetailWithIsJoin(@Query("userid") int userid, @Query("matchId") int matchId);

    @GET("Helianmeng.asmx/GetLianmengDongtai")
    Observable<Res<Article>> GetLianmengDongtai(@Query("id") int id);

    @GET("Helianmeng.asmx/ShieldFriend")
    Observable<Res> ShieldFriend(@Query("friendid") int id, @Query("userid") int userid);

    @GET("Helianmeng.asmx/UnShieldFriend")
    Observable<Res> UnShieldFriend(@Query("friendid") int id, @Query("userid") int userid);

    @GET("Helianmeng.asmx/ShieldFriendNews")
    Observable<Res> ShieldFriendNews(@Query("friendid") int id, @Query("userid") int userid);

    @GET("Helianmeng.asmx/UnShieldFriendNews")
    Observable<Res> UnShieldFriendNews(@Query("friendid") int id, @Query("userid") int userid);

    @GET("LoginAndRegister.asmx/SelectMyFriend")
    Observable<Res<User>> SelectMyFriend(@Query("friend") int id, @Query("userid") int userid);

    @GET("LoginAndRegister.asmx/ChangeFriendRName")
    Observable<Res> ChangeFriendRName(@Query("friend") int id, @Query("userid") int userid, @Query("rname") String rname);

    @GET("Hewuzhe.asmx/DeletePlan")
    Observable<Res> DeletePlan(@Query("id") int id);

    @GET("Helianmeng.asmx/GetDongtaiPageByFriends")
    Observable<Res<ArrayList<FriendCondition>>> GetDongtaiPageByFriends(@Query("startRowIndex") int startRowIndex, @Query("maximumRows") int maximumRows, @Query("userid") int userid);


    @GET("Helianmeng.asmx/GetTeamDongtaiPageByTeamId")
    Observable<Res<ArrayList<FriendCondition>>> GetTeamDongtaiPageByTeamId(@Query("startRowIndex") int startRowIndex, @Query("maximumRows") int maximumRows, @Query("teamid") int teamid);

    @GET("Helianmeng.asmx/GetTeamDongtaiPageByUserId")
    Observable<Res<ArrayList<FriendCondition>>> GetTeamDongtaiPageByUserId(@Query("startRowIndex") int startRowIndex, @Query("maximumRows") int maximumRows, @Query("userid") int userid);

    @GET("Helianmeng.asmx/SelectDongtaiByFriendId")
    Observable<Res<ArrayList<FriendCondition>>> SelectDongtaiByFriendId(@Query("startRowIndex") int startRowIndex, @Query("maximumRows") int maximumRows, @Query("friendid") int friendid);

    @GET("Hewuzhe.asmx/GetUpRecord")
    Observable<Res<ArrayList<Record>>> GetUpRecord(@Query("startRowIndex") int startRowIndex, @Query("maximumRows") int maximumRows, @Query("userid") int userid);

    @GET("Helianmeng.asmx/SelectTeamAnnouncement")
    Call<Res<ArrayList<TeamAnnounce>>> SelectTeamAnnouncement(@Query("startRowIndex") int startRowIndex, @Query("maximumRows") int maximumRows, @Query("teamid") int teamid);

    @GET("Hewuzhe.asmx/GetPlan")
    Observable<Res<Plan>> getPlanDetail(@Query("id") int id);

    @GET("Helianmeng.asmx/SelectTeam")
    Observable<Res<Group>> SelectTeam(@Query("teamid") int id);

    @GET("Helianmeng.asmx/SelectTeamMember")
    Observable<Res<ArrayList<Friend>>> SelectTeamMember(@Query("teamid") int id, @Query("userid") int userId);

    @GET("Hewuzhe.asmx/SelectWuGuan")
    Call<Res<Dojo>> SelectWuGuan(@Query("id") int id);

    @GET("Hewuzhe.asmx/DeleteVideo")
    Observable<Res> DeleteVideo(@Query("id") int id);

    @GET("Helianmeng.asmx/SelectTeamIntroduce")
    Call<Res<TeamIntroduce>> SelectTeamIntroduce(@Query("teamid") int teamid);

    @GET("Hewuzhe.asmx/SelectImageByMessageId")
    Call<Res<ArrayList<OtherImage>>> SelectImageByMessageId(@Query("messageid") int id);

    @GET("Helianmeng.asmx/GetFriendForKeyValue")
    Observable<Res<ArrayList<WrapFriend>>> GetFriendForKeyValue(@Query("userid") int userid);

    @GET("Helianmeng.asmx/IsWuyou")
    Observable<Res<Boolean>> IsWuyou(@Query("userid") int userid, @Query("friendid") int friendid);

    @GET("Helianmeng.asmx/GetDongtaiById")
    Observable<Res<FriendCondition>> GetDongtaiById(@Query("userid") int userid, @Query("messid") int messid);

    @GET("Helianmeng.asmx/CommentMatch")
    Observable<Res> CommentMatch(@Query("userid") int userid, @Query("memberId") int memberId, @Query("comment") String comment);

    @GET("Helianmeng.asmx/GetMatchComment")
    Observable<Res<ArrayList<MegaComment>>> GetMatchComment(@Query("startRowIndex") int startRowIndex, @Query("maximumRows") int maximumRows, @Query("memberId") int memberId);


    @GET("Helianmeng.asmx/VotePerso")
    Observable<Res> VotePerso(@Query("userid") int userid, @Query("matchId") int matchId, @Query("voterId") int voterId);

    @GET("Helianmeng.asmx/VoteTeam")
    Observable<Res> VoteTeam(@Query("teamid") int teamid, @Query("matchId") int matchId, @Query("voterId") int voterId);

    @GET("Helianmeng.asmx/JoinMatch")
    Observable<Res> JoinMatch(@Query("userid") int userid, @Query("matchId") int matchId, @Query("matchVideo") String matchVideo, @Query("videoDuration") String videoDuration, @Query("matchDescription") String matchDescription, @Query("matchImage") String matchImage, @Query("title") String title);

    @GET("Helianmeng.asmx/{path}")
    Observable<Res<MegaVideo>> SelectMatchDetail(@Path("path") String path, @Query("userid") int userid, @Query("myId") int myId, @Query("matchId") int matchId, @Query("teamid") int teamid);

    @GET("LoginAndRegister.asmx/GetDreamHeader")
    Call<Res<FlyDreamHeader>> GetDreamHeader();

    @GET("LoginAndRegister.asmx/GetCharge")
    Observable<Charge> GetCharge(@Query("userid") int userid, @Query("channel") String channel, @Query("amount") int amount, @Query("description") String description, @Query("flg") int flg);

    @GET("Helianmeng.asmx/CancleJoinMatch")
    Observable<Res> CancleJoinMatch(@Query("userid") int userid, @Query("matchId") int matchId);

    @GET("LoginAndRegister.asmx/OpenHuiyuan")
    Observable<Res> OpenHuiyuan(@Query("userid") int userid, @Query("month") int month);

    @GET("Hewuzhe.asmx/SelectVideoLiveNew")
    Observable<Res<LiveVideo>> SelectVideoLive(@Query("userid") int userid, @Query("videoLiveId") int videoLiveId);

    @GET("Helianmeng.asmx/GetNoReadCommentNumByUserId")
    Observable<Res<String>> GetNoReadCommentNumByUserId(@Query("userid") int userid);

    @GET("Helianmeng.asmx/SelectNoReadMatch")
    Observable<Res> SelectNoReadMatch(@Query("userid") int userid);

    @GET("Helianmeng.asmx/DeleteNoReadMatch")
    Observable<Res> DeleteNoReadMatch(@Query("userid") int userid, @Query("flg") int flg);

    @GET("AboutUs.asmx/GetAuoutUs")
    Observable<Res<AboutUs>> GetAuoutUs();

    @GET("Helianmeng.asmx/DeleteComment")
    Observable<Res> DeleteComment(@Query("id") int id);

    @FormUrlEncoded
    @POST("Admin/Hander/SaveMessageAndImageNews.ashx")
    Observable<WebContents> SaveMessageAndImageNews(@Field("id") int id, @Field("flg") int flg);

    @GET("Hewuzhe.asmx/SelectVideoLiveList")
    Call<Res<ArrayList<LiveVideo>>> SelectVideoLiveList(@Query("startRowIndex") int startRowIndex, @Query("maximumRows") int maximumRows, @Query("userid") int userid);


    @GET("Hezhuangbei.asmx/SelectBigCategory")
    Call<Res<ArrayList<Classification>>> SelectBigCategory();

    @GET("Hezhuangbei.asmx/SelectSmallCategory")
    Call<Res<ArrayList<Classification>>> SelectSmallCategory(@Query("parentId") int parentId);

    @GET("Hezhuangbei.asmx/SelectAllDeliveryAddress")
    Call<Res<ArrayList<Site>>> SelectAllDeliveryAddress(@Query("userId") int userId);

    @GET("Hezhuangbei.asmx/SetDefaultDeliveryAddress")
    Call<Res> SetDefaultDeliveryAddress(@Query("userId") int userId, @Query("id") int id);

    @GET("Hezhuangbei.asmx/EditDeliveryAddress")
    Call<Res> EditDeliveryAddress(@Query("userId") int userId, @Query("id") int id, @Query("areaId") String areaId, @Query("address") String address, @Query("isDefaul") int isDefaul, @Query("receiverName") String receiverName, @Query("phone") String phone, @Query("codeForAddress") String codeForAddress);

    @GET("LoginAndRegister.asmx/GetCounty")
    Call<Res<ArrayList<Address>>> GetCounty();

    @GET("Hezhuangbei.asmx/DeleteDeliveryAddress")
    Call<Res> DeleteDeliveryAddress(@Query("userId") int userId, @Query("idList") String idList);

//区分==============================================================================================

    @GET("Hezhuangbei.asmx/SelectProductBySearch")
    Observable<Res<ArrayList<Product>>> SelectProductBySearch(@Query("startRowIndex") int startRowIndex, @Query("maximumRows") int maximumRows, @Query("search") String search, @Query("categoryId") int categoryId, @Query("isPriceAsc") int isPriceAsc, @Query("isSalesAsc") int isSalesAsc, @Query("isCommentAsc") int isCommentAsc, @Query("isNewAsc") int isNewAsc, @Query("isCredit") int isCredit, @Query("isRecommend") int isRecommend);

    @GET("Hezhuangbei.asmx/SelectProductNew")
    Observable<Res<Product>> SelectProductNew(@Query("id") int id, @Query("userid") int userid, @Query("maximumRows") int maximumRows);

    @GET("Hezhuangbei.asmx/SelectBasketProductNewNew")
    Observable<Res<ArrayList<ShopCar>>> SelectBasketProductNewNew(@Query("startRowIndex") int startRowIndex, @Query("maximumRows") int maximumRows, @Query("userId") int userId);

    @GET("Hezhuangbei.asmx/FavoriteProduct")
    Observable<Res> FavoriteProduct(@Query("userid") int userid, @Query("productId") int productId);

    @GET("Hezhuangbei.asmx/CancleFavoriteProduct")
    Observable<Res> CancleFavoriteProduct(@Query("userid") int userid, @Query("productId") int productId);

    @GET("Hezhuangbei.asmx/SelectCommentByProductId")
    Observable<Res<ArrayList<ProductComment>>> SelectCommentByProductId(@Query("productId") int productId, @Query("startRowIndex") int startRowIndex, @Query("maximumRows") int maximumRows);

    @GET("Hezhuangbei.asmx/InsertBasket")
    Observable<Res> InsertBasket(@Query("productId") int productId, @Query("num") int num, @Query("priceId") int priceId, @Query("userId") int userId, @Query("price") double price);

    @GET("Hezhuangbei.asmx/DeleteBasketProduct")
    Observable<Res> DeleteBasketProduct(@Query("idList") String idList, @Query("userId") int userId);

    @GET("Hezhuangbei.asmx/ChangeBasketProductNum")
    Observable<Res> ChangeBasketProductNum(@Query("id") int id, @Query("num") int num, @Query("userId") int userId);

    @GET("Hezhuangbei.asmx/BuyNow")
    Observable<Res> BuyNow(@Query("productId") int productId, @Query("num") int num, @Query("priceId") int priceId, @Query("userId") int userId, @Query("price") double price, @Query("deliveryId") int deliveryId, @Query("description") String description);

    @GET("Hezhuangbei.asmx/SelectFavoriteByUserIdAndSearch")
    Observable<Res<ArrayList<ProductCollection>>> SelectFavoriteByUserIdAndSearch(@Query("startRowIndex") int startRowIndex, @Query("maximumRows") int maximumRows, @Query("userId") int userId, @Query("search") String search);

    @GET("Hezhuangbei.asmx/GetPostage")
    Observable<Res<Result>> GetPostage(@Query("userid") int userid, @Query("idList") String idList);

    @GET("LoginAndRegister.asmx/ChangeTuiSong")
    Observable<Res> ChangeTuiSong(@Query("userid") int userid, @Query("istuisong") boolean istuisong);

    @GET("LoginAndRegister.asmx/SelectUserPayList")
    Observable<Res<ArrayList<VipPrice>>> SelectUserPayList();

    @GET("Hezhuangbei.asmx/SelectProductPhone")
    Observable<Res<ArrayList<Tel>>> SelectProductPhone();

    @GET("Hewuzhe.asmx/SelectTeacherListNew")
    Observable<Res<ArrayList<PrivateTrainerList>>> SelectTeacherListNew(@Query("userid") int userid,@Query("startRowIndex") int startRowIndex,@Query("maximumRows") int maximumRows,@Query("areaname") String areaname,@Query("lat") String lat,@Query("lng") String lng,@Query("search") String search,@Query("cateid") String cateid,@Query("length") int length);

    @GET("Hewuzhe.asmx/SelectLessonById")
    Observable<Res<TrainerLessonTwo>> SelectLessonById(@Query("userid") int userid, @Query("lessonid") int lessonid);

    @GET("Hewuzhe.asmx/JoinLessonByLessonId")
    Observable<Res> JoinLessonByLessonId(@Query("userid") int userid, @Query("Lessonid") int Lessonid, @Query("realname") String realname, @Query("phone") String phone , @Query("age") int age, @Query("sex") int sex, @Query("areaid") String areaid);

    @GET("Hedongli.asmx/SelectCategory")
    Observable<Res<ArrayList<StudyOnlineCatItem>>> SelectCategory();

    @GET("Hedongli.asmx/SelectVideoByRecommendCategory")
    Observable<Res<ArrayList<Video>>> SelectVideoByRecommendCategory(@Query("userid") int userid,@Query("startRowIndex") int startRowIndex, @Query("maximumRows") int maximumRows, @Query("cateid") int categoryid, @Query("search") String search);

    @GET("Hewuzhe.asmx/GetJoinLessonRecordByUserIdAndLessonId")
    Observable<Res<TrainerLessonInfo>> GetJoinLessonRecordByUserIdAndLessonId(@Query("userid") int userid, @Query("lessonid") int lessonid);

    @GET("Hewuzhe.asmx/CancelJoinLessonRecordById")
    Observable<Res> CancelJoinLessonRecordById(@Query("joinlessonrecordid") int joinlessonrecordid);

    @GET("Hewuzhe.asmx/SelectTeacherCateList")
    Observable<Res<ArrayList<ScreenList>>> SelectTeacherCateList(@Query("lat") double lat,@Query("lng") double lng,@Query("length") int length );

    @GET("Helianmeng.asmx/SelectRecommendDongTai")
    Observable<Res<ArrayList<ChatList>>> SelectRecommendDongTai(@Query("startRowIndex") int startRowIndex, @Query("maximumRows") int maximumRows,@Query("userid") int userid);

    @GET("Helianmeng.asmx/SelectRecommendUser")
    Observable<Res<ArrayList<RecommendUser>>> SelectRecommendUser(@Query("userid") int userid,@Query("isindexshow") int isindexshow,@Query("startRowIndex") int startRowIndex, @Query("maximumRows") int maximumRows,@Query("search") String search);

    @GET("Helianmeng.asmx/SelectNearbyUser")
    Observable<Res<ArrayList<NearPeople>>> SelectNearbyUser(@Query("startRowIndex") int startRowIndex,@Query("maximumRows") int maximumRows,@Query("userid") int userid, @Query("lat") String lat,@Query("lng") String lng,@Query("length") int length,@Query("age") int age,@Query("sexuality") int sexuality);
}