package com.arabbit.net;

import com.arabbit.bean.AlbumBean;
import com.arabbit.entity.BaseResult;
import com.arabbit.entity.EmptyEntity;
import com.arabbit.entity.GetBuildingListEntity;
import com.arabbit.entity.GetCityListEntity;
import com.arabbit.entity.GetClassListEntity;
import com.arabbit.entity.GetFloorListEntity;
import com.arabbit.entity.GetSchoolListEntity;
import com.arabbit.entity.GetSeatListEntity;
import com.arabbit.entity.GetTermListDetailByIdEntity;
import com.arabbit.entity.GetTermListEntity;
import com.arabbit.entity.GetUserInfoEntity;
import com.arabbit.entity.HadShopmbEntity;
import com.arabbit.entity.HadShopcpEntity;
import com.arabbit.entity.HadShopprEntity;
import com.arabbit.entity.HadShopptEntity;
import com.arabbit.entity.LoginFromPasswordEntity;
import com.arabbit.entity.PrizeListEntity;
import com.arabbit.entity.RegisterFromPhoneEntity;
import com.arabbit.entity.SeatListEntity;
import com.arabbit.entity.GiftListEntity;
import com.arabbit.entity.ProtionListEntity;
import com.arabbit.entity.GetUserCardEntity;
import com.arabbit.entity.ShopImgEntity;
import com.arabbit.entity.ShopmbListEntity;
import com.arabbit.entity.ShopmbgoodListEntity;
import com.arabbit.entity.ShopprListEntity;
import com.arabbit.entity.ShopptListEntity;
import com.arabbit.entity.ShopcpListEntity;
import com.arabbit.entity.UserHadgiftEntity;
import com.arabbit.entity.UserHadprizeEntity;
import com.arabbit.entity.UserHadproEntity;
import com.arabbit.entity.UserJoingiftEntity;
import com.arabbit.entity.UserInfoEntity;

import java.util.List;

import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import rx.Observable;

/**
 * Created by net4 on 2017/6/22.
 */

public interface HttpService {


    /**
     * 返回实体类很有可能参数各种不对
     */
    //发送短信验证码
    @FormUrlEncoded
    @POST("register/sendSmsCode")
    Observable<BaseResult<EmptyEntity>> sendSmsCode(@Field("version") String version,
                                                    @Field("client") String client,
                                                    @Field("phone") String phone,
                                                    @Field("sms_type") String sms_type
    );

    //验证验证码
    @FormUrlEncoded
    @POST("register/checkSms")
    Observable<BaseResult<EmptyEntity>> checkSms(@Field("version") String version,
                                                 @Field("client") String client,
                                                 @Field("phone") String phone,
                                                 @Field("sms_code") String sms_code,
                                                 @Field("sms_type") String sms_type
    );

    //手机号注册会员
    @FormUrlEncoded
    @POST("register/registerFromPhone")
    Observable<BaseResult<RegisterFromPhoneEntity>> registerFromPhone(@Field("version") String version,
                                                                      @Field("client") String client,
                                                                      @Field("account") String phone,
                                                                      @Field("password") String password,
                                                                      @Field("nickname") String nickname,
                                                                      @Field("role") String role,
                                                                      @Field("avatar_img") String avatar_img,
                                                                      @Field("address") String address,
                                                                      @Field("school_id") String school_id
    );

    //密码登录
    @FormUrlEncoded
    @POST("register/loginFromPassword")
    Observable<BaseResult<LoginFromPasswordEntity>> loginFromPassword(@Field("version") String version,
                                                                      @Field("client") String client,
                                                                      @Field("account") String account,
                                                                      @Field("password") String password,
                                                                      @Field("role") String role);

    //找回密码
    @FormUrlEncoded
    @POST("register/forgetPassword")
    Observable<BaseResult<EmptyEntity>> forgetPassword(@Field("version") String version,
                                                       @Field("client") String client,
                                                       @Field("phone") String phone,
                                                       @Field("sms_code") String sms_code,
                                                       @Field("password") String password);

    //退出登录
    @FormUrlEncoded
    @POST("register/logout")
    Observable<BaseResult<EmptyEntity>> logout(@Field("version") String version,
                                               @Field("client") String client,
                                               @Field("user_id") String user_id,
                                               @Field("token") String token);

    //短信登录
    @FormUrlEncoded
    @POST("register/loginFromSms")
    Observable<BaseResult<LoginFromPasswordEntity>> loginFromSms(@Field("version") String version,
                                                                 @Field("client") String client,
                                                                 @Field("phone") String phone,
                                                                 @Field("sms_code") String sms_code);

    //获取用户详情
    @FormUrlEncoded
    @POST("user/getUserInfo")
    Observable<BaseResult<GetUserInfoEntity>> getUserInfo(@Field("version") String version,
                                                          @Field("client") String client,
                                                          @Field("user_id") String user_id,
                                                          @Field("token") String token,
                                                          @Field("target_user_id") String target_user_id
    );

    //修改资料(头像)
    @FormUrlEncoded
    @POST("user/updateUserInfo")
    Observable<BaseResult<EmptyEntity>> updateUserInfoAvatarImg(@Field("version") String version,
                                                                @Field("client") String client,
                                                                @Field("user_id") String user_id,
                                                                @Field("token") String token,
                                                                @Field("avatar_img") String avatar_img);

    //修改资料(座位头像)
    @FormUrlEncoded
    @POST("user/updateUserInfo")
    Observable<BaseResult<EmptyEntity>> updateUserInfoSeatImg(@Field("version") String version,
                                                              @Field("client") String client,
                                                              @Field("user_id") String user_id,
                                                              @Field("token") String token,
                                                              @Field("seat_img") String seat_img);


    //修改资料(email)
    @FormUrlEncoded
    @POST("user/updateUserInfo")
    Observable<BaseResult<EmptyEntity>> updateUserInfoEmail(@Field("version") String version,
                                                            @Field("client") String client,
                                                            @Field("user_id") String user_id,
                                                            @Field("token") String token,
                                                            @Field("email") String email

    );

    //修改资料(nickname)
    @FormUrlEncoded
    @POST("user/updateUserInfo")
    Observable<BaseResult<EmptyEntity>> updateUserInfoNickname(@Field("version") String version,
                                                               @Field("client") String client,
                                                               @Field("user_id") String user_id,
                                                               @Field("token") String token,
                                                               @Field("nickname") String nickname
    );

    //修改资料(phone)电话
    @FormUrlEncoded
    @POST("user/updateUserInfo")
    Observable<BaseResult<EmptyEntity>> updateUserInfoPhone(@Field("version") String version,
                                                            @Field("client") String client,
                                                            @Field("user_id") String user_id,
                                                            @Field("token") String token,
                                                            @Field("phone") String phone
    );

    //修改资料(password)密码
    @FormUrlEncoded
    @POST("user/updateUserInfo")
    Observable<BaseResult<EmptyEntity>> updateUserPassword(@Field("version") String version,
                                                           @Field("client") String client,
                                                           @Field("user_id") String user_id,
                                                           @Field("token") String token,
                                                           @Field("password") String password
    );

    //修改资料(gender)
    @FormUrlEncoded
    @POST("user/updateUserInfo")
    Observable<BaseResult<EmptyEntity>> updateUserInfoGender(@Field("version") String version,
                                                             @Field("client") String client,
                                                             @Field("user_id") String user_id,
                                                             @Field("token") String token,
                                                             @Field("gender") String gender

    );

    //修改资料(homepage)
    @FormUrlEncoded
    @POST("user/updateUserInfo")
    Observable<BaseResult<EmptyEntity>> updateUserInfoHomepage(@Field("version") String version,
                                                               @Field("client") String client,
                                                               @Field("user_id") String user_id,
                                                               @Field("token") String token,
                                                               @Field("homepage") String homepage

    );

    //修改资料(detail_address)//地址详情
    @FormUrlEncoded
    @POST("user/updateUserInfo")
    Observable<BaseResult<EmptyEntity>> updateUserInfoDetailAddress(@Field("version") String version,
                                                                    @Field("client") String client,
                                                                    @Field("user_id") String user_id,
                                                                    @Field("token") String token,
                                                                    @Field("detail_address") String address
    );

    //修改资料(address)//地区
    @FormUrlEncoded
    @POST("user/updateUserInfo")
    Observable<BaseResult<EmptyEntity>> updateUserInfoArea(@Field("version") String version,
                                                           @Field("client") String client,
                                                           @Field("user_id") String user_id,
                                                           @Field("token") String token,
                                                           @Field("address") String address
    );

    //修改资料(school_id)学校
    @FormUrlEncoded
    @POST("user/updateUserInfo")
    Observable<BaseResult<EmptyEntity>> updateUserInfoSchool(@Field("version") String version,
                                                             @Field("client") String client,
                                                             @Field("user_id") String user_id,
                                                             @Field("token") String token,
                                                             @Field("school_id") String school_id
    );

    //修改资料(introduction)
    @FormUrlEncoded
    @POST("user/updateUserInfo")
    Observable<BaseResult<EmptyEntity>> updateUserInfoIntroduction(@Field("version") String version,
                                                                   @Field("client") String client,
                                                                   @Field("user_id") String user_id,
                                                                   @Field("token") String token,
                                                                   @Field("introduction") String introduction

    );

    //历史座位表
    @FormUrlEncoded
    @POST("user/seatList")
    Observable<BaseResult<SeatListEntity>> seatList(@Field("version") String version,
                                                    @Field("client") String client,
                                                    @Field("user_id") String user_id,
                                                    @Field("token") String token,
                                                    @Field("target_user_id") String target_user_id,
                                                    @Field("page") String page

    );

    //获取城市列表
    @FormUrlEncoded
    @POST("classes/getCityList")
    Observable<BaseResult<List<GetCityListEntity>>> getCityList(@Field("version") String version,
                                                                @Field("client") String client

    );

    //根据城市获取学校列表
    @FormUrlEncoded
    @POST("classes/getSchoolList")
    Observable<BaseResult<List<GetSchoolListEntity>>> getSchoolList(@Field("version") String version,
                                                                    @Field("client") String client,
                                                                    @Field("city") String city

    );

    //获取学期列表
    @FormUrlEncoded
    @POST("classes/getTermList")
    Observable<BaseResult<GetTermListEntity>> getTermList(@Field("version") String version,
                                                          @Field("client") String client,
                                                          @Field("school_id") String school_id,
                                                          @Field("page") String page

    );

    //获取教学楼列表
    @FormUrlEncoded
    @POST("classes/getBuildingList")
    Observable<BaseResult<List<GetBuildingListEntity>>> getBuildingList(@Field("version") String version,
                                                                        @Field("client") String client,
                                                                        @Field("school_id") String school_id,
                                                                        @Field("b_id") String b_id,
                                                                        @Field("term_id") String term_id

    );

    //获取楼层列表
    @FormUrlEncoded
    @POST("classes/getFloorList")
    Observable<BaseResult<List<GetFloorListEntity>>> getFloorList(@Field("version") String version,
                                                                  @Field("client") String client,
                                                                  @Field("school_id") String school_id,
                                                                  @Field("building") String building

    );

    //获取课室列表
    @FormUrlEncoded
    @POST("classes/getClassList")
    Observable<BaseResult<List<GetClassListEntity>>> getClassList(@Field("version") String version,
                                                                  @Field("client") String client,
                                                                  @Field("floor_id") String floor_id
    );

    //获取座位表
    @FormUrlEncoded
    @POST("classes/getSeatList")
    Observable<BaseResult<GetSeatListEntity>> getSeatList(@Field("version") String version,
                                                          @Field("client") String client,
                                                          @Field("term_id") String term_id,
                                                          @Field("b_id") String b_id
    );

    //根据座位ID获取座位表
    @FormUrlEncoded
    @POST("classes/getSeatListById")
    Observable<BaseResult<GetSeatListEntity>> getSeatListById(@Field("version") String version,
                                                              @Field("client") String client,
                                                              @Field("seat_id") String seat_id
    );

    //根据id获取学期详情
    @FormUrlEncoded
    @POST("classes/getTermListDetailById")
    Observable<BaseResult<GetTermListDetailByIdEntity>> getTermListDetailById(@Field("version") String version,
                                                                              @Field("client") String client,
                                                                              @Field("term_id") String seat_id
    );

    //添加礼品
    @FormUrlEncoded
    @POST("promotion/addGift")
    Observable<BaseResult<GiftListEntity.ListsBean>> addGift(@Field("version") String version,
                                                             @Field("client") String client,
                                                             @Field("gfname") String gfname,
                                                             @Field("gfimg") String gfimg,
                                                             @Field("gfinfo") String gfinfo,
                                                             @Field("gfnum") String gfnum,
                                                             @Field("gfcontent") String gfcontent,
                                                             @Field("gfstart") String gfstart,
                                                             @Field("gfend") String gfend,
                                                             @Field("gffar") String gffar,
                                                             @Field("user_id") String user_id,
                                                             @Field("school_id") String school_id,
                                                             @Field("term_id") String term_id
    );


    //获取礼品列表
    @FormUrlEncoded
    @POST("promotion/getGiftList")
    Observable<BaseResult<GiftListEntity>> getGiftList(@Field("version") String version,
                                                       @Field("client") String client,
                                                       @Field("user_id") String user_id,
                                                       @Field("term_id") String term_id
    );


    //获取礼品信息
    @FormUrlEncoded
    @POST("promotion/getGiftInfo")
    Observable<BaseResult<GiftListEntity.ListsBean>> getGiftInfo(@Field("version") String version,
                                                                 @Field("client") String client,
                                                                 @Field("token") String token,
                                                                 @Field("gift_id") String gift_id

    );

    //获取礼品信息
    @FormUrlEncoded
    @POST("promotion/getmyGiftInfo")
    Observable<BaseResult<GiftListEntity.ListsBean>> getmyGiftInfo(@Field("version") String version,
                                                                   @Field("client") String client,
                                                                   @Field("token") String token,
                                                                   @Field("gift_id") String gift_id

    );

    //修改礼品
    @FormUrlEncoded
    @POST("promotion/updateGift")
    Observable<BaseResult<GiftListEntity.ListsBean>> updateGift(@Field("version") String version,
                                                                @Field("client") String client,
                                                                @Field("gift_id") String gift_id,
                                                                @Field("gfimg") String gfimg,
                                                                @Field("gfname") String gfname,
                                                                @Field("gfinfo") String gfinfo,
                                                                @Field("gfnum") String gfnum,
                                                                @Field("gfcontent") String gfcontent,
                                                                @Field("gfstart") String gfstart,
                                                                @Field("gfend") String gfend,
                                                                @Field("gffar") String gffar
    );

    //删除礼品
    @FormUrlEncoded
    @POST("promotion/delGift")
    Observable<BaseResult<List<GiftListEntity.ListsBean>>> delGift(@Field("version") String version,
                                                                   @Field("client") String client,
                                                                   @Field("gift_id") String gift_id

    );

    //添加奖品活动
    @FormUrlEncoded
    @POST("promotion/addProtion")
    Observable<BaseResult<ProtionListEntity.ListsBean>> addProtion(@Field("version") String version,
                                                                   @Field("client") String client,
                                                                   @Field("type") String gfname,
                                                                   @Field("far") String gfinfo,
                                                                   @Field("starttime") String gfnum,
                                                                   @Field("endtime") String gfcontent,
                                                                   @Field("rule") String gfstart,
                                                                   @Field("user_id") String user_id,
                                                                   @Field("school_id") String school_id,
                                                                   @Field("term_id") String term_id
    );


    //获取抽奖活动列表
    @FormUrlEncoded
    @POST("promotion/getProtionList")
    Observable<BaseResult<ProtionListEntity>> getProtionList(@Field("version") String version,
                                                             @Field("client") String client,
                                                             @Field("user_id") String user_id,
                                                             @Field("term_id") String term_id
    );


    //获取抽奖活动信息
    @FormUrlEncoded
    @POST("promotion/getProtionInfo")
    Observable<BaseResult<ProtionListEntity.ListsBean>> getProtionInfo(@Field("version") String version,
                                                                       @Field("client") String client,
                                                                       @Field("token") String token,
                                                                       @Field("pt_id") String pt_id

    );

    //修改抽奖活动
    @FormUrlEncoded
    @POST("promotion/updateProtion")
    Observable<BaseResult<ProtionListEntity.ListsBean>> updateProtion(@Field("version") String version,
                                                                      @Field("client") String client,
                                                                      @Field("pt_id") String pt_id,
                                                                      @Field("type") String gfname,
                                                                      @Field("far") String gfinfo,
                                                                      @Field("starttime") String gfnum,
                                                                      @Field("endtime") String gfcontent,
                                                                      @Field("rule") String gfstart
    );

    //删除抽奖活动
    @FormUrlEncoded
    @POST("promotion/delProtion")
    Observable<BaseResult<ProtionListEntity.ListsBean>> delProtion(@Field("version") String version,
                                                                   @Field("client") String client,
                                                                   @Field("pt_id") String pt_id

    );


    //添加奖品
    @FormUrlEncoded
    @POST("promotion/addPrize")
    Observable<BaseResult<PrizeListEntity.ListsBean>> addPrize(@Field("version") String version,
                                                               @Field("client") String client,
                                                               @Field("prname") String prname,
                                                               @Field("primg") String gfimg,
                                                               @Field("info") String info,
                                                               @Field("content") String content,
                                                               @Field("rank") String rank,
                                                               @Field("num") String num,
                                                               @Field("rate") String rate,
                                                               @Field("pt_id") String pt_id,
                                                               @Field("user_id") String user_id
    );

    //获取奖品列表
    @FormUrlEncoded
    @POST("promotion/getPrizeList")
    Observable<BaseResult<PrizeListEntity>> getPrizeList(@Field("version") String version,
                                                         @Field("client") String client,
                                                         @Field("pt_id") String pt_id
    );

    //获取奖品信息
    @FormUrlEncoded
    @POST("promotion/getPrizeInfo")
    Observable<BaseResult<PrizeListEntity.ListsBean>> getPrizeInfo(@Field("version") String version,
                                                                   @Field("client") String client,
                                                                   @Field("token") String token,
                                                                   @Field("pr_id") String pr_id

    );

    //获取奖品信息
    @FormUrlEncoded
    @POST("promotion/getmyPrizeInfo")
    Observable<BaseResult<PrizeListEntity.ListsBean>> getmyPrizeInfo(@Field("version") String version,
                                                                     @Field("client") String client,
                                                                     @Field("token") String token,
                                                                     @Field("pr_id") String pr_id

    );

    //修改奖品
    @FormUrlEncoded
    @POST("promotion/updatePrize")
    Observable<BaseResult<PrizeListEntity.ListsBean>> updatePrize(@Field("version") String version,
                                                                  @Field("client") String client,
                                                                  @Field("pr_id") String pr_id,
                                                                  @Field("primg") String pr_img,
                                                                  @Field("prname") String prname,
                                                                  @Field("info") String info,
                                                                  @Field("content") String content,
                                                                  @Field("rank") String rank,
                                                                  @Field("num") String num,
                                                                  @Field("rate") String rate
    );

    //删除奖品
    @FormUrlEncoded
    @POST("promotion/delPrize")
    Observable<BaseResult<List<PrizeListEntity.ListsBean>>> delPrize(@Field("version") String version,
                                                                     @Field("client") String client,
                                                                     @Field("pr_id") String pr_id

    );

    //添加用户名片
    @FormUrlEncoded
    @POST("user/addUserCard")
    Observable<BaseResult<GetUserCardEntity.ListsBean>> addUserCard(@Field("version") String version,
                                                                    @Field("client") String client,
                                                                    @Field("user_id") String user_id,
                                                                    @Field("real_name") String real_name,
                                                                    @Field("company") String company,
                                                                    @Field("job") String job,
                                                                    @Field("com_address") String com_address,
                                                                    @Field("com_page") String com_page,
                                                                    @Field("user_phone") String user_phone,
                                                                    @Field("user_tel") String user_tel,
                                                                    @Field("user_fax") String user_fax,
                                                                    @Field("user_email") String user_email,
                                                                    @Field("user_qq") String user_qq,
                                                                    @Field("wechat") String wechat,
                                                                    @Field("post_address") String post_address,
                                                                    @Field("com_intro") String com_intro
    );


    //获取用户名片
    @FormUrlEncoded
    @POST("user/getUserCard")
    Observable<BaseResult<GetUserCardEntity.ListsBean>> getUserCard(@Field("version") String version,
                                                                    @Field("client") String client,
                                                                    @Field("token") String token,
                                                                    @Field("user_id") String user_id,
                                                                    @Field("target_user_id") String target_user_id
    );

    //修改用户名片
    @FormUrlEncoded
    @POST("user/updateUserCard")
    Observable<BaseResult<GetUserCardEntity.ListsBean>> updateUserCard(@Field("version") String version,
                                                                       @Field("client") String client,
                                                                       @Field("user_id") String user_id,
                                                                       @Field("real_name") String real_name,
                                                                       @Field("company") String company,
                                                                       @Field("job") String job,
                                                                       @Field("com_address") String com_address,
                                                                       @Field("com_page") String com_page,
                                                                       @Field("user_phone") String user_phone,
                                                                       @Field("user_tel") String user_tel,
                                                                       @Field("user_fax") String user_fax,
                                                                       @Field("user_email") String user_email,
                                                                       @Field("user_qq") String user_qq,
                                                                       @Field("wechat") String wechat,
                                                                       @Field("post_address") String post_address,
                                                                       @Field("com_intro") String com_intro

    );

    //用户参加领取礼品活动
    @FormUrlEncoded
    @POST("promotion/userJoinGift")
    Observable<BaseResult<UserJoingiftEntity.ListsBean>> userJoinGift(@Field("version") String version,
                                                                      @Field("client") String client,
                                                                      @Field("user_id") String user_id,
                                                                      @Field("gift_id") String gift_id,
                                                                      @Field("term_id") String term_id

    );

    //用户领取礼品
    @FormUrlEncoded
    @POST("promotion/userHadGift")
    Observable<BaseResult<UserHadgiftEntity.ListsBean>> userHadGift(@Field("version") String version,
                                                                    @Field("client") String client,
                                                                    @Field("user_id") String user_id,
                                                                    @Field("gift_id") String gift_id,
                                                                    @Field("term_id") String term_id

    );

    //用户参与抽奖
    @FormUrlEncoded
    @POST("promotion/userHadPro")
    Observable<BaseResult<UserHadproEntity.ListsBean>> userHadPro(@Field("version") String version,
                                                                  @Field("client") String client,
                                                                  @Field("user_id") String user_id,
                                                                  @Field("pt_id") String gift_id

    );

    //用户中奖
    @FormUrlEncoded
    @POST("promotion/userHadPrize")
    Observable<BaseResult<UserHadprizeEntity.ListsBean>> userHadPrize(@Field("version") String version,
                                                                      @Field("client") String client,
                                                                      @Field("user_id") String user_id,
                                                                      @Field("pr_id") String pr_id

    );


    //获取我的礼品列表
    @FormUrlEncoded
    @POST("promotion/getMyGiftList")
    Observable<BaseResult<UserHadgiftEntity>> getMyGiftList(@Field("version") String version,
                                                            @Field("client") String client,
                                                            @Field("token") String token,
                                                            @Field("user_id") String user_id
    );

    //获取我参与的抽奖活动列表
    @FormUrlEncoded
    @POST("promotion/getMyProList")
    Observable<BaseResult<UserHadproEntity>> getMyProList(@Field("version") String version,
                                                          @Field("client") String client,
                                                          @Field("user_id") String user_id
    );

    //获取我的奖品列表
    @FormUrlEncoded
    @POST("promotion/getMyPrize")
    Observable<BaseResult<UserHadprizeEntity>> getMyPrize(@Field("version") String version,
                                                          @Field("client") String client,
                                                          @Field("user_id") String user_id
    );

    //参加领取礼品活动的用户列表
    @FormUrlEncoded
    @POST("promotion/getJoinUser")
    Observable<BaseResult<UserJoingiftEntity>> getJoinUser(@Field("version") String version,
                                                           @Field("client") String client,
                                                           @Field("page_no") int page_no,
                                                           @Field("gift_id") String gift_id
    );

    //领取礼品的用户列表
    @FormUrlEncoded
    @POST("promotion/getGiftUser")
    Observable<BaseResult<UserHadgiftEntity>> getGiftUser(@Field("version") String version,
                                                          @Field("client") String client,
                                                          @Field("page_no") int page_no,
                                                          @Field("gift_id") String gift_id
    );

    //参与抽奖活动的用户列表
    @FormUrlEncoded
    @POST("promotion/getProUser")
    Observable<BaseResult<UserHadproEntity>> getProUser(@Field("version") String version,
                                                        @Field("client") String client,
                                                        @Field("page_no") int page_no,
                                                        @Field("pt_id") String user_id
    );

    //参与活动中奖的用户列表
    @FormUrlEncoded
    @POST("promotion/getWProUser")
    Observable<BaseResult<UserHadprizeEntity>> getWProUser(@Field("version") String version,
                                                           @Field("client") String client,
                                                           @Field("pageno") int pageno,
                                                           @Field("pt_id") String user_id
    );

    //中奖的用户列表
    @FormUrlEncoded
    @POST("promotion/getPrizeUser")
    Observable<BaseResult<UserHadprizeEntity>> getPrizeUser(@Field("version") String version,
                                                            @Field("client") String client,
                                                            @Field("pageno") int pageno,
                                                            @Field("pr_id") String user_id
    );

    //搜索用户
    @FormUrlEncoded
    @POST("user/searchUser")
    Observable<BaseResult<List<UserInfoEntity>>> searchUser(@Field("version") String version,
                                                            @Field("client") String client,
                                                            @Field("key") String key

    );

    //上传图片到相册
    @FormUrlEncoded
    @POST("promotion/uploadToAlbum")
    Observable<BaseResult<AlbumBean>> uploadToAlbum(@Field("version") String version,
                                                    @Field("client") String client,
                                                    @Field("user_id") String user_id,
                                                    @Field("term_id") String term_id,
                                                    @Field("url") String url
    );

    //获取相册列表
    @FormUrlEncoded
    @POST("promotion/getAlbumList")
    Observable<BaseResult<List<AlbumBean>>> getAlbumList(@Field("version") String version,
                                                         @Field("client") String client,
                                                         @Field("user_id") String user_id,
                                                         @Field("term_id") String term_id

    );

    //删除相册中的图片
    @FormUrlEncoded
    @POST("promotion/deletePhoto")
    Observable<BaseResult<List<AlbumBean>>> deletePhoto(@Field("version") String version,
                                                        @Field("client") String client,
                                                        @Field("img_id") String img_id

    );


    //上传图片到相册
    @FormUrlEncoded
    @POST("promotion/uploadVideo")
    Observable<BaseResult<AlbumBean>> uploadVideo(@Field("version") String version,
                                                  @Field("client") String client,
                                                  @Field("user_id") String user_id,
                                                  @Field("term_id") String term_id,
                                                  @Field("url") String url
    );

    //获取相册列表
    @FormUrlEncoded
    @POST("promotion/getVideoList")
    Observable<BaseResult<List<AlbumBean>>> getVideoList(@Field("version") String version,
                                                         @Field("client") String client,
                                                         @Field("user_id") String user_id,
                                                         @Field("term_id") String term_id

    );


    //删除活动视频
    @FormUrlEncoded
    @POST("promotion/deleteVideo")
    Observable<BaseResult<List<AlbumBean>>> deleteVideo(@Field("version") String version,
                                                        @Field("client") String client,
                                                        @Field("img_id") String img_id

    );

    //添加秒爆促销活动信息
    @FormUrlEncoded
    @POST("shop/addShopmb")
    Observable<BaseResult<ShopmbListEntity.ListsBean>> addShopmb(@Field("version") String version,
                                                                 @Field("client") String client,
                                                                 @Field("user_id") String user_id,
                                                                 @Field("title") String title,
                                                                 @Field("far") String far,
                                                                 @Field("starttime") String starttime,
                                                                 @Field("endtime") String endtime,
                                                                 @Field("rule") String rule
    );

    //修改秒爆促销活动信息
    @FormUrlEncoded
    @POST("shop/updateShopmb")
    Observable<BaseResult<ShopmbListEntity.ListsBean>> updateShopmb(@Field("version") String version,
                                                                    @Field("client") String client,
                                                                    @Field("mb_id") String mb_id,
                                                                    @Field("title") String title,
                                                                    @Field("far") String far,
                                                                    @Field("starttime") String starttime,
                                                                    @Field("endtime") String endtime,
                                                                    @Field("rule") String rule
    );

    //获取店铺秒爆促销列表
    @FormUrlEncoded
    @POST("shop/getShopmbList")
    Observable<BaseResult<ShopmbListEntity>> getShopmbList(@Field("version") String version,
                                                           @Field("client") String client,
                                                           @Field("user_id") String user_id
    );

    //获取店铺秒爆促销信息
    @FormUrlEncoded
    @POST("shop/getShopmb")
    Observable<BaseResult<ShopmbListEntity.ListsBean>> getShopmb(@Field("version") String version,
                                                                 @Field("client") String client,
                                                                 @Field("token") String token,
                                                                 @Field("mb_id") String mb_id

    );

    //删除店铺秒爆促销活动
    @FormUrlEncoded
    @POST("shop/delShopmb")
    Observable<BaseResult<ShopmbListEntity.ListsBean>> delShopmb(@Field("version") String version,
                                                                 @Field("client") String client,
                                                                 @Field("mb_id") String mb_id

    );

    //添加秒爆促销商品
    @FormUrlEncoded
    @POST("shop/addShopmbGood")
    Observable<BaseResult<ShopmbgoodListEntity.ListsBean>> addShopmbGood(@Field("version") String version,
                                                                         @Field("client") String client,
                                                                         @Field("mb_id") String mb_id,
                                                                         @Field("mbname") String mbname,
                                                                         @Field("info") String info,
                                                                         @Field("num") String num,
                                                                         @Field("mb_price") String mb_price,
                                                                         @Field("preprice") String preprice,
                                                                         @Field("starttime") String starttime,
                                                                         @Field("endtime") String endtime,
                                                                         @Field("content") String content,
                                                                         @Field("mbimg") String mbimg
    );

    //修改秒爆促销商品
    @FormUrlEncoded
    @POST("shop/updateShopmbGood")
    Observable<BaseResult<ShopmbgoodListEntity.ListsBean>> updateShopmbGood(@Field("version") String version,
                                                                            @Field("client") String client,
                                                                            @Field("mbgood_id") String mbgood_id,
                                                                            @Field("mbname") String mbname,
                                                                            @Field("info") String info,
                                                                            @Field("num") String num,
                                                                            @Field("mb_price") String mb_price,
                                                                            @Field("preprice") String preprice,
                                                                            @Field("starttime") String starttime,
                                                                            @Field("endtime") String endtime,
                                                                            @Field("content") String content,
                                                                            @Field("mbimg") String mbimg
    );

    //获取店铺秒爆商品列表
    @FormUrlEncoded
    @POST("shop/getShopmbgoodList")
    Observable<BaseResult<ShopmbgoodListEntity>> getShopmbgoodList(@Field("version") String version,
                                                                   @Field("client") String client,
                                                                   @Field("mb_id") String mb_id
    );

    //获取店铺秒爆商品信息
    @FormUrlEncoded
    @POST("shop/getMbgoodinfo")
    Observable<BaseResult<ShopmbgoodListEntity.ListsBean>> getMbgoodinfo(@Field("version") String version,
                                                                         @Field("client") String client,
                                                                         @Field("token") String token,
                                                                         @Field("mbgood_id") String mbgood_id

    );

    //删除店铺秒爆商品
    @FormUrlEncoded
    @POST("shop/delShopmbgood")
    Observable<BaseResult<ShopmbgoodListEntity.ListsBean>> delShopmbgood(@Field("version") String version,
                                                                         @Field("client") String client,
                                                                         @Field("mbgood_id") String mbgood_id

    );

    //添加店铺抽奖活动
    @FormUrlEncoded
    @POST("shop/addShopPt")
    Observable<BaseResult<ShopptListEntity.ListsBean>> addShopPt(@Field("version") String version,
                                                                 @Field("client") String client,
                                                                 @Field("user_id") String user_id,
                                                                 @Field("title") String title,
                                                                 @Field("type") String type,
                                                                 @Field("far") String far,
                                                                 @Field("starttime") String starttime,
                                                                 @Field("endtime") String endtime,
                                                                 @Field("rule") String rule
    );

    //修改店铺抽奖活动
    @FormUrlEncoded
    @POST("shop/updateShopPt")
    Observable<BaseResult<ShopptListEntity.ListsBean>> updateShopPt(@Field("version") String version,
                                                                    @Field("client") String client,
                                                                    @Field("pt_id") String pt_id,
                                                                    @Field("title") String title,
                                                                    @Field("type") String type,
                                                                    @Field("far") String far,
                                                                    @Field("starttime") String starttime,
                                                                    @Field("endtime") String endtime,
                                                                    @Field("rule") String rule
    );

    //获取铺抽奖列表
    @FormUrlEncoded
    @POST("shop/getShopptList")
    Observable<BaseResult<ShopptListEntity>> getShopptList(@Field("version") String version,
                                                           @Field("client") String client,
                                                           @Field("user_id") String user_id
    );

    //删除店铺抽奖活动
    @FormUrlEncoded
    @POST("shop/delShoppt")
    Observable<BaseResult<ShopptListEntity.ListsBean>> delShoppt(@Field("version") String version,
                                                                 @Field("client") String client,
                                                                 @Field("pt_id") String mb_id

    );

    //获取店铺抽奖活动信息
    @FormUrlEncoded
    @POST("shop/getShopptInfo")
    Observable<BaseResult<ShopptListEntity.ListsBean>> getShopptInfo(@Field("version") String version,
                                                                     @Field("client") String client,
                                                                     @Field("token") String token,
                                                                     @Field("pt_id") String pt_id

    );

    //添加店铺抽奖奖品
    @FormUrlEncoded
    @POST("shop/addShopPrize")
    Observable<BaseResult<ShopprListEntity.ListsBean>> addShopPrize(@Field("version") String version,
                                                                    @Field("client") String client,
                                                                    @Field("prname") String prname,
                                                                    @Field("primg") String gfimg,
                                                                    @Field("info") String info,
                                                                    @Field("content") String content,
                                                                    @Field("rank") String rank,
                                                                    @Field("num") String num,
                                                                    @Field("rate") String rate,
                                                                    @Field("pt_id") String pt_id,
                                                                    @Field("price") String price,
                                                                    @Field("user_id") String user_id
    );

    //修改店铺抽奖奖品
    @FormUrlEncoded
    @POST("shop/updateShopPrize")
    Observable<BaseResult<ShopprListEntity.ListsBean>> updateShopPrize(@Field("version") String version,
                                                                       @Field("client") String client,
                                                                       @Field("prname") String prname,
                                                                       @Field("primg") String gfimg,
                                                                       @Field("info") String info,
                                                                       @Field("content") String content,
                                                                       @Field("rank") String rank,
                                                                       @Field("num") String num,
                                                                       @Field("rate") String rate,
                                                                       @Field("price") String price,
                                                                       @Field("pr_id") String pr_id
    );

    //获取店铺抽奖奖品列表
    @FormUrlEncoded
    @POST("shop/getShopprList")
    Observable<BaseResult<ShopprListEntity>> getShopprList(@Field("version") String version,
                                                           @Field("client") String client,
                                                           @Field("pt_id") String pt_id
    );

    //获取店铺抽奖奖品信息
    @FormUrlEncoded
    @POST("shop/getShopprInfo")
    Observable<BaseResult<ShopprListEntity.ListsBean>> getShopprInfo(@Field("version") String version,
                                                                     @Field("client") String client,
                                                                     @Field("token") String token,
                                                                     @Field("pr_id") String pr_id

    );


    //删除店铺抽奖奖品
    @FormUrlEncoded
    @POST("shop/delShoppr")
    Observable<BaseResult<List<ShopprListEntity.ListsBean>>> delShoppr(@Field("version") String version,
                                                                       @Field("client") String client,
                                                                       @Field("pr_id") String pr_id

    );

    //添加打折降价商品
    @FormUrlEncoded
    @POST("shop/addShopcpGood")
    Observable<BaseResult<ShopcpListEntity.ListsBean>> addShopcpGood(@Field("version") String version,
                                                                     @Field("client") String client,
                                                                     @Field("user_id") String user_id,
                                                                     @Field("cpname") String cpname,
                                                                     @Field("info") String info,
                                                                     @Field("num") String num,
                                                                     @Field("cp_price") String cp_price,
                                                                     @Field("preprice") String preprice,
                                                                     @Field("pullon") String pullon,
                                                                     @Field("content") String content,
                                                                     @Field("cpimg") String cpimg
    );

    //修改打折降价商品
    @FormUrlEncoded
    @POST("shop/updateShopcpGood")
    Observable<BaseResult<ShopcpListEntity.ListsBean>> updateShopcpGood(@Field("version") String version,
                                                                        @Field("client") String client,
                                                                        @Field("cp_id") String cp_id,
                                                                        @Field("cpname") String mbname,
                                                                        @Field("info") String info,
                                                                        @Field("num") String num,
                                                                        @Field("cp_price") String mb_price,
                                                                        @Field("preprice") String preprice,
                                                                        @Field("pullon") String pullon,
                                                                        @Field("content") String content,
                                                                        @Field("cpimg") String cpimg
    );

    //获取店铺打折降价促销列表
    @FormUrlEncoded
    @POST("shop/getShopcpList")
    Observable<BaseResult<ShopcpListEntity>> getShopcpList(@Field("version") String version,
                                                           @Field("client") String client,
                                                           @Field("user_id") String user_id
    );

    //获取店铺打折降价促销商品信息
    @FormUrlEncoded
    @POST("shop/getShopcpgood")
    Observable<BaseResult<ShopcpListEntity.ListsBean>> getShopcpgood(@Field("version") String version,
                                                                     @Field("client") String client,
                                                                     @Field("token") String token,
                                                                     @Field("cp_id") String cp_id

    );

    //删除店铺打折降价促销
    @FormUrlEncoded
    @POST("shop/delShopcpgood")
    Observable<BaseResult<ShopcpListEntity.ListsBean>> delShopcpgood(@Field("version") String version,
                                                                     @Field("client") String client,
                                                                     @Field("cp_id") String cp_id

    );


    //用户购买秒爆商品
    @FormUrlEncoded
    @POST("shop/userShopmb")
    Observable<BaseResult<HadShopmbEntity.ListsBean>> userShopmb(@Field("version") String version,
                                                                 @Field("client") String client,
                                                                 @Field("user_id") String user_id,
                                                                 @Field("mbgood_id") String mbgood_id,
                                                                 @Field("mbname") String mbname,
                                                                 @Field("title") String title,
                                                                 @Field("mbimg") String mbimg,
                                                                 @Field("mb_price") String mb_price,
                                                                 @Field("send_user_id") String send_user_id

    );

    //购买了秒爆活动商品的用户列表
    @FormUrlEncoded
    @POST("shop/getUserShopmbList")
    Observable<BaseResult<HadShopmbEntity>> getUserShopmbList(@Field("version") String version,
                                                              @Field("client") String client,
                                                              @Field("page_no") int page_no,
                                                              @Field("mb_id") String mb_id
    );

    //购买秒爆商品的用户列表
    @FormUrlEncoded
    @POST("shop/getUserShopmbGoodList")
    Observable<BaseResult<HadShopmbEntity>> getUserShopmbGoodList(@Field("version") String version,
                                                              @Field("client") String client,
                                                              @Field("page_no") int page_no,
                                                              @Field("mbgood_id") String mbgood_id
    );

    //用户购买打折促销商品
    @FormUrlEncoded
    @POST("shop/userShopcp")
    Observable<BaseResult<HadShopcpEntity.ListsBean>> userShopcp(@Field("version") String version,
                                                                 @Field("client") String client,
                                                                 @Field("user_id") String user_id,
                                                                 @Field("cp_id") String cp_id,
                                                                 @Field("cpname") String cpname,
                                                                 @Field("cpimg") String cpimg,
                                                                 @Field("cpimg") String info,
                                                                 @Field("cp_price") String cp_price,
                                                                 @Field("preprice") String preprice,
                                                                 @Field("buynum") String buynum,
                                                                 @Field("buynum") String content,
                                                                 @Field("send_user_id") String send_user_id

    );

    //购买打折促销商品的用户列表
    @FormUrlEncoded
    @POST("shop/getUserShopcpList")
    Observable<BaseResult<HadShopcpEntity>> getUserShopcpList(@Field("version") String version,
                                                              @Field("client") String client,
                                                              @Field("page_no") int page_no,
                                                              @Field("cp_id") String mbgood_id
    );


    //用户参与店铺抽奖
    @FormUrlEncoded
    @POST("shop/userShopPt")
    Observable<BaseResult<HadShopptEntity.ListsBean>> userShopPt(@Field("version") String version,
                                                                  @Field("client") String client,
                                                                  @Field("user_id") String user_id,
                                                                  @Field("pt_id") String pt_id

    );

    //用户店铺抽奖活动中奖
    @FormUrlEncoded
    @POST("shop/userShopPr")
    Observable<BaseResult<HadShopmbEntity.ListsBean>> userShopPr(@Field("version") String version,
                                                                      @Field("client") String client,
                                                                      @Field("user_id") String user_id,
                                                                      @Field("pr_id") String pr_id

    );


    //获取我的秒爆商品列表
    @FormUrlEncoded
    @POST("shop/getMyShopmbList")
    Observable<BaseResult<HadShopmbEntity>> getMyShopmbList(@Field("version") String version,
                                                            @Field("client") String client,
                                                            @Field("token") String token,
                                                            @Field("user_id") String user_id
    );
    //获取我的秒爆商品信息
    @FormUrlEncoded
    @POST("shop/getMyshopmbGood")
    Observable<BaseResult<HadShopmbEntity.ListsBean>> getMyshopmbGood(@Field("version") String version,
                                                                      @Field("client") String client,
                                                                      @Field("token") String token,
                                                                      @Field("mbgood_id") String mbgood_id

    );


    //获取我参与的抽奖活动列表
    @FormUrlEncoded
    @POST("shop/getMyShopPtList")
    Observable<BaseResult<UserHadproEntity>> getMyShopPtList(@Field("version") String version,
                                                          @Field("client") String client,
                                                          @Field("user_id") String user_id
    );

    //获取我的店铺抽奖奖品列表
    @FormUrlEncoded
    @POST("shop/getMyShopPrList")
    Observable<BaseResult<HadShopprEntity>> getMyShopPrList(@Field("version") String version,
                                                          @Field("client") String client,
                                                          @Field("user_id") String user_id
    );
    //获取我的店铺抽奖奖品信息
    @FormUrlEncoded
    @POST("shop/getmyShopPrInfo")
    Observable<BaseResult<HadShopprEntity.ListsBean>> getMyShopPrInfo(@Field("version") String version,
                                                                     @Field("client") String client,
                                                                     @Field("token") String token,
                                                                      @Field("user_id") String user_id,
                                                                     @Field("pr_id") String pr_id

    );

    //获取我购买的店铺打折商品列表
    @FormUrlEncoded
    @POST("shop/getMyShopcpList")
    Observable<BaseResult<HadShopcpEntity>> getMyShopcpList(@Field("version") String version,
                                                             @Field("client") String client,
                                                             @Field("user_id") String user_id
    );
    //获取我购买的店铺打折商品信息
    @FormUrlEncoded
    @POST("shop/getMyshopcpGood")
    Observable<BaseResult<HadShopcpEntity.ListsBean>> getMyshopcpGood(@Field("version") String version,
                                                                     @Field("client") String client,
                                                                     @Field("token") String token,
                                                                     @Field("cp_id") String cp_id

    );


    //参与店铺抽奖活动的用户列表
    @FormUrlEncoded
    @POST("shop/getUserShopPtList")
    Observable<BaseResult<HadShopptEntity>> getUserShopPtList(@Field("version") String version,
                                                        @Field("client") String client,
                                                        @Field("page_no") int page_no,
                                                        @Field("pt_id") String pt_id
    );

    //参与店铺抽奖活动中奖的用户列表
    @FormUrlEncoded
    @POST("shop/getWUserShopPtList")
    Observable<BaseResult<HadShopprEntity>> getWUserShopPtList(@Field("version") String version,
                                                           @Field("client") String client,
                                                           @Field("pageno") int pageno,
                                                           @Field("pt_id") String pt_id
    );

    //店铺中奖的用户列表
    @FormUrlEncoded
    @POST("shop/getUserShopprList")
    Observable<BaseResult<HadShopprEntity>> getUserShopprList(@Field("version") String version,
                                                              @Field("client") String client,
                                                              @Field("pageno") int pageno,
                                                              @Field("pr_id") String pt_id
    );

    //修改店铺图片
    @FormUrlEncoded
    @POST("shop/updateShopImg")
    Observable<BaseResult<EmptyEntity>> updateShopImg(@Field("version") String version,
                                                              @Field("client") String client,
                                                           @Field("user_id") String user_id,
                                                              @Field("imgs") String imgs
    );


    //获取店铺图片
    @FormUrlEncoded
    @POST("shop/getShopImgs")
    Observable<BaseResult<ShopImgEntity>> getShopImgs(@Field("version") String version,
                                                      @Field("client") String client,
                                                      @Field("user_id") String user_id
    );


    //获取店铺信息
    @FormUrlEncoded
    @POST("shop/getInfo")
    Observable<BaseResult<ShopImgEntity>> getInfo(@Field("version") String version,
                                                      @Field("client") String client,
                                                      @Field("user_id") String user_id
    );











































}
