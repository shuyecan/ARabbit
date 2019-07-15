package com.arabbit.model;


import android.util.Log;

import com.arabbit.bean.AlbumBean;
import com.arabbit.entity.EmptyEntity;
import com.arabbit.entity.GetBuildingListEntity;
import com.arabbit.entity.GetCityListEntity;
import com.arabbit.entity.GetClassListEntity;
import com.arabbit.entity.GetFloorListEntity;
import com.arabbit.entity.GetSchoolListEntity;
import com.arabbit.entity.GetSeatListEntity;
import com.arabbit.entity.GetTermListDetailByIdEntity;
import com.arabbit.entity.GetTermListEntity;
import com.arabbit.entity.GetUserCardEntity;
import com.arabbit.entity.GetUserInfoEntity;
import com.arabbit.entity.HadShopcpEntity;
import com.arabbit.entity.HadShopmbEntity;
import com.arabbit.entity.HadShopprEntity;
import com.arabbit.entity.HadShopptEntity;
import com.arabbit.entity.LoginFromPasswordEntity;
import com.arabbit.entity.PrizeListEntity;
import com.arabbit.entity.RegisterFromPhoneEntity;
import com.arabbit.entity.SeatListEntity;
import com.arabbit.entity.GiftListEntity;
import com.arabbit.entity.ProtionListEntity;
import com.arabbit.entity.ShopImgEntity;
import com.arabbit.entity.ShopInfoEntity;
import com.arabbit.entity.ShopcpListEntity;
import com.arabbit.entity.ShopmbListEntity;
import com.arabbit.entity.ShopmbgoodListEntity;
import com.arabbit.entity.ShopprListEntity;
import com.arabbit.entity.ShopptListEntity;
import com.arabbit.entity.UserHadgiftEntity;
import com.arabbit.entity.UserHadprizeEntity;
import com.arabbit.entity.UserHadproEntity;
import com.arabbit.entity.UserInfoEntity;
import com.arabbit.entity.UserJoingiftEntity;
import com.arabbit.net.ApiException;
import com.arabbit.rx.CommonSubscriber;
import com.arabbit.rx.CommonTransformer;
import com.arabbit.utils.SPUtils;

import java.util.Arrays;
import java.util.List;

import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by net4 on 2017/6/22.
 */

public class SocialModel extends SubModel {

    final String version = Config.getVersion();
    final String client = Config.client;

    public SocialModel(IViewLoad viewload) {
        super(viewload);
    }


    //发送短信
    public void sendSmsCode(String phone, String sms_type, final IModelResult<EmptyEntity> callback) {
        Subscription subscriber = httpService.sendSmsCode(version, client, phone, sms_type)
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .compose(new CommonTransformer<EmptyEntity>())
                .subscribe(new CommonSubscriber<EmptyEntity>(viewload) {
                    @Override
                    protected void onError(ApiException e) {
                        callback.OnError(e);
                    }

                    @Override
                    public void onNext(EmptyEntity sendSmsCode) {
                        callback.OnSuccess(sendSmsCode);
                    }
                });
        callback.AddSubscription(subscriber);
    }

    //验证验证码
    public void checkSms(String phone, String sms_code, String sms_type, final IModelResult<EmptyEntity> callback) {
        Subscription subscriber = httpService.checkSms(version, client, phone, sms_code, sms_type)
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .compose(new CommonTransformer<EmptyEntity>())
                .subscribe(new CommonSubscriber<EmptyEntity>(viewload) {
                    @Override
                    protected void onError(ApiException e) {
                        callback.OnError(e);
                    }

                    @Override
                    public void onNext(EmptyEntity sendSmsCode) {
                        callback.OnSuccess(sendSmsCode);
                    }
                });
        callback.AddSubscription(subscriber);
    }

    //手机号注册会员
    public void registerFromPhone(String account, String password, String nickname, String role, String avatar_img, String address, String school_id,
                                  final IModelResult<RegisterFromPhoneEntity> callback) {
        Subscription subscriber = httpService.registerFromPhone(version, client,
                account, password, nickname, role, avatar_img, address, school_id)
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .compose(new CommonTransformer<RegisterFromPhoneEntity>())
                .subscribe(new CommonSubscriber<RegisterFromPhoneEntity>(viewload) {
                    @Override
                    protected void onError(ApiException e) {
                        callback.OnError(e);
                    }

                    @Override
                    public void onNext(RegisterFromPhoneEntity sendSmsCode) {
                        callback.OnSuccess(sendSmsCode);
                    }
                });
        callback.AddSubscription(subscriber);
    }

    //密码登录
    public void loginFromPassword(String account, String password, String role, final IModelResult<LoginFromPasswordEntity> callback) {
        Subscription subscriber = httpService.loginFromPassword(version, client, account, password, role)
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .compose(new CommonTransformer<LoginFromPasswordEntity>())
                .subscribe(new CommonSubscriber<LoginFromPasswordEntity>(viewload) {
                    @Override
                    protected void onError(ApiException e) {
                        callback.OnError(e);
                    }

                    @Override
                    public void onNext(LoginFromPasswordEntity sendSmsCode) {
                        callback.OnSuccess(sendSmsCode);
                    }
                });
        callback.AddSubscription(subscriber);
    }


    //找回密码
    public void forgetPassword(String phone, String sms_code, String password, final IModelResult<EmptyEntity> callback) {
        Subscription subscriber = httpService.forgetPassword(version, client, phone, sms_code, password)
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .compose(new CommonTransformer<EmptyEntity>())
                .subscribe(new CommonSubscriber<EmptyEntity>(viewload) {
                    @Override
                    protected void onError(ApiException e) {
                        callback.OnError(e);
                    }

                    @Override
                    public void onNext(EmptyEntity sendSmsCode) {
                        callback.OnSuccess(sendSmsCode);
                    }
                });
        callback.AddSubscription(subscriber);
    }


    //退出登录
    public void logout(final IModelResult<EmptyEntity> callback) {
        String user_id = SPUtils.getString("user_id", "");
        String token = SPUtils.getString("token", "");
        Subscription subscriber = httpService.logout(version, client, user_id, token)
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .compose(new CommonTransformer<EmptyEntity>())
                .subscribe(new CommonSubscriber<EmptyEntity>(viewload) {
                    @Override
                    protected void onError(ApiException e) {
                        callback.OnError(e);
                    }

                    @Override
                    public void onNext(EmptyEntity sendSmsCode) {
                        callback.OnSuccess(sendSmsCode);
                    }
                });
        callback.AddSubscription(subscriber);
    }


    //短信登录
    public void loginFromSms(String phone, String password, final IModelResult<LoginFromPasswordEntity> callback) {
        Subscription subscriber = httpService.loginFromSms(version, client, phone, password)
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .compose(new CommonTransformer<LoginFromPasswordEntity>())
                .subscribe(new CommonSubscriber<LoginFromPasswordEntity>(viewload) {
                    @Override
                    protected void onError(ApiException e) {
                        callback.OnError(e);
                    }

                    @Override
                    public void onNext(LoginFromPasswordEntity sendSmsCode) {
                        callback.OnSuccess(sendSmsCode);
                    }
                });
        callback.AddSubscription(subscriber);
    }

    //获取用户详情
    public void getUserInfo(String target_user_id, final IModelResult<GetUserInfoEntity> callback) {
        String user_id = SPUtils.getString("user_id", "");
        String token = SPUtils.getString("token", "");
        Subscription subscriber = httpService.getUserInfo(version, client, user_id, token, target_user_id)
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .compose(new CommonTransformer<GetUserInfoEntity>())
                .subscribe(new CommonSubscriber<GetUserInfoEntity>(viewload) {
                    @Override
                    protected void onError(ApiException e) {
                        callback.OnError(e);
                    }

                    @Override
                    public void onNext(GetUserInfoEntity sendSmsCode) {
                        callback.OnSuccess(sendSmsCode);
                    }
                });
        callback.AddSubscription(subscriber);
    }


    //修改资料(头像)
    public void updateUserInfoAvatarImg(String avatar_img, final IModelResult<EmptyEntity> callback) {
        String user_id = SPUtils.getString("user_id", "");
        String token = SPUtils.getString("token", "");
        Subscription subscriber = httpService.updateUserInfoAvatarImg(version, client, user_id, token, avatar_img)
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .compose(new CommonTransformer<EmptyEntity>())
                .subscribe(new CommonSubscriber<EmptyEntity>(viewload) {
                    @Override
                    protected void onError(ApiException e) {
                        callback.OnError(e);
                    }

                    @Override
                    public void onNext(EmptyEntity sendSmsCode) {
                        callback.OnSuccess(sendSmsCode);
                    }
                });
        callback.AddSubscription(subscriber);
    }

    //修改资料(座位头像)
    public void updateUserInfoSeatImg(String seat_img, final IModelResult<EmptyEntity> callback) {
        String user_id = SPUtils.getString("user_id", "");
        String token = SPUtils.getString("token", "");
        Subscription subscriber = httpService.updateUserInfoSeatImg(version, client, user_id, token, seat_img)
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .compose(new CommonTransformer<EmptyEntity>())
                .subscribe(new CommonSubscriber<EmptyEntity>(viewload) {
                    @Override
                    protected void onError(ApiException e) {
                        callback.OnError(e);
                    }

                    @Override
                    public void onNext(EmptyEntity sendSmsCode) {
                        callback.OnSuccess(sendSmsCode);
                    }
                });
        callback.AddSubscription(subscriber);
    }

    //修改资料(email)
    public void updateUserInfoEmail(String email, final IModelResult<EmptyEntity> callback) {
        String user_id = SPUtils.getString("user_id", "");
        String token = SPUtils.getString("token", "");
        Subscription subscriber = httpService.updateUserInfoEmail(version, client, user_id, token, email)
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .compose(new CommonTransformer<EmptyEntity>())
                .subscribe(new CommonSubscriber<EmptyEntity>(viewload) {
                    @Override
                    protected void onError(ApiException e) {
                        callback.OnError(e);
                    }

                    @Override
                    public void onNext(EmptyEntity sendSmsCode) {
                        callback.OnSuccess(sendSmsCode);
                    }
                });
        callback.AddSubscription(subscriber);
    }

    //修改资料(nickname)
    public void updateUserInfoNickname(String nickname, final IModelResult<EmptyEntity> callback) {
        String user_id = SPUtils.getString("user_id", "");
        String token = SPUtils.getString("token", "");
        Subscription subscriber = httpService.updateUserInfoNickname(version, client, user_id, token, nickname)
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .compose(new CommonTransformer<EmptyEntity>())
                .subscribe(new CommonSubscriber<EmptyEntity>(viewload) {
                    @Override
                    protected void onError(ApiException e) {
                        callback.OnError(e);
                    }

                    @Override
                    public void onNext(EmptyEntity sendSmsCode) {
                        callback.OnSuccess(sendSmsCode);
                    }
                });
        callback.AddSubscription(subscriber);
    }


    //修改资料(password)
    public void updateUserPassword(String password, final IModelResult<EmptyEntity> callback) {
        String user_id = SPUtils.getString("user_id", "");
        String token = SPUtils.getString("token", "");
        Subscription subscriber = httpService.updateUserPassword(version, client, user_id, token, password)
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .compose(new CommonTransformer<EmptyEntity>())
                .subscribe(new CommonSubscriber<EmptyEntity>(viewload) {
                    @Override
                    protected void onError(ApiException e) {
                        callback.OnError(e);
                    }

                    @Override
                    public void onNext(EmptyEntity sendSmsCode) {
                        callback.OnSuccess(sendSmsCode);
                    }
                });
        callback.AddSubscription(subscriber);
    }


    //修改资料(gender)
    public void updateUserInfoGender(String gender, final IModelResult<EmptyEntity> callback) {
        String user_id = SPUtils.getString("user_id", "");
        String token = SPUtils.getString("token", "");
        Subscription subscriber = httpService.updateUserInfoGender(version, client, user_id, token, gender)
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .compose(new CommonTransformer<EmptyEntity>())
                .subscribe(new CommonSubscriber<EmptyEntity>(viewload) {
                    @Override
                    protected void onError(ApiException e) {
                        callback.OnError(e);
                    }

                    @Override
                    public void onNext(EmptyEntity sendSmsCode) {
                        callback.OnSuccess(sendSmsCode);
                    }
                });
        callback.AddSubscription(subscriber);
    }

    //修改资料(homepage)
    public void updateUserInfoHomepage(String homepage, final IModelResult<EmptyEntity> callback) {
        String user_id = SPUtils.getString("user_id", "");
        String token = SPUtils.getString("token", "");
        Subscription subscriber = httpService.updateUserInfoHomepage(version, client, user_id, token, homepage)
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .compose(new CommonTransformer<EmptyEntity>())
                .subscribe(new CommonSubscriber<EmptyEntity>(viewload) {
                    @Override
                    protected void onError(ApiException e) {
                        callback.OnError(e);
                    }

                    @Override
                    public void onNext(EmptyEntity sendSmsCode) {
                        callback.OnSuccess(sendSmsCode);
                    }
                });
        callback.AddSubscription(subscriber);
    }

    //修改资料(phone)电话
    public void updateUserInfoPhone(String homepage, final IModelResult<EmptyEntity> callback) {
        String user_id = SPUtils.getString("user_id", "");
        String token = SPUtils.getString("token", "");
        Subscription subscriber = httpService.updateUserInfoPhone(version, client, user_id, token, homepage)
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .compose(new CommonTransformer<EmptyEntity>())
                .subscribe(new CommonSubscriber<EmptyEntity>(viewload) {
                    @Override
                    protected void onError(ApiException e) {
                        callback.OnError(e);
                    }

                    @Override
                    public void onNext(EmptyEntity sendSmsCode) {
                        callback.OnSuccess(sendSmsCode);
                    }
                });
        callback.AddSubscription(subscriber);
    }


    //修改资料(detail_address)//地址详情
    public void updateUserInfoDetailAddress(String detail_address, final IModelResult<EmptyEntity> callback) {
        String user_id = SPUtils.getString("user_id", "");
        String token = SPUtils.getString("token", "");
        Subscription subscriber = httpService.updateUserInfoDetailAddress(version, client, user_id, token, detail_address)
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .compose(new CommonTransformer<EmptyEntity>())
                .subscribe(new CommonSubscriber<EmptyEntity>(viewload) {
                    @Override
                    protected void onError(ApiException e) {
                        callback.OnError(e);
                    }

                    @Override
                    public void onNext(EmptyEntity sendSmsCode) {
                        callback.OnSuccess(sendSmsCode);
                    }
                });
        callback.AddSubscription(subscriber);
    }

    //修改资料(address)//地区
    public void updateUserInfoArea(String area, final IModelResult<EmptyEntity> callback) {
        String user_id = SPUtils.getString("user_id", "");
        String token = SPUtils.getString("token", "");
        Subscription subscriber = httpService.updateUserInfoArea(version, client, user_id, token, area)
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .compose(new CommonTransformer<EmptyEntity>())
                .subscribe(new CommonSubscriber<EmptyEntity>(viewload) {
                    @Override
                    protected void onError(ApiException e) {
                        callback.OnError(e);
                    }

                    @Override
                    public void onNext(EmptyEntity sendSmsCode) {
                        callback.OnSuccess(sendSmsCode);
                    }
                });
        callback.AddSubscription(subscriber);
    }

    //修改资料(school)学校
    public void updateUserInfoSchool(String school_id, final IModelResult<EmptyEntity> callback) {
        String user_id = SPUtils.getString("user_id", "");
        String token = SPUtils.getString("token", "");
        Subscription subscriber = httpService.updateUserInfoSchool(version, client, user_id, token, school_id)
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .compose(new CommonTransformer<EmptyEntity>())
                .subscribe(new CommonSubscriber<EmptyEntity>(viewload) {
                    @Override
                    protected void onError(ApiException e) {
                        callback.OnError(e);
                    }

                    @Override
                    public void onNext(EmptyEntity sendSmsCode) {
                        callback.OnSuccess(sendSmsCode);
                    }
                });
        callback.AddSubscription(subscriber);
    }

    //修改资料(introduction)
    public void updateUserInfoIntroduction(String introduction, final IModelResult<EmptyEntity> callback) {
        String user_id = SPUtils.getString("user_id", "");
        String token = SPUtils.getString("token", "");
        Subscription subscriber = httpService.updateUserInfoIntroduction(version, client, user_id, token, introduction)
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .compose(new CommonTransformer<EmptyEntity>())
                .subscribe(new CommonSubscriber<EmptyEntity>(viewload) {
                    @Override
                    protected void onError(ApiException e) {
                        callback.OnError(e);
                    }

                    @Override
                    public void onNext(EmptyEntity sendSmsCode) {
                        callback.OnSuccess(sendSmsCode);
                    }
                });
        callback.AddSubscription(subscriber);
    }

    //历史座位表
    public void seatList(String target_user_id, String page, final IModelResult<SeatListEntity> callback) {
        String user_id = SPUtils.getString("user_id", "");
        String token = SPUtils.getString("token", "");
        Subscription subscriber = httpService.seatList(version, client, user_id, token, target_user_id, page)
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .compose(new CommonTransformer<SeatListEntity>())
                .subscribe(new CommonSubscriber<SeatListEntity>(viewload) {
                    @Override
                    protected void onError(ApiException e) {
                        callback.OnError(e);
                    }

                    @Override
                    public void onNext(SeatListEntity sendSmsCode) {
                        callback.OnSuccess(sendSmsCode);
                    }
                });
        callback.AddSubscription(subscriber);
    }

    //获取城市列表
    public void getCityList(final IModelResult<List<GetCityListEntity>> callback) {
        Subscription subscriber = httpService.getCityList(version, client)
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .compose(new CommonTransformer<List<GetCityListEntity>>())
                .subscribe(new CommonSubscriber<List<GetCityListEntity>>(viewload) {
                    @Override
                    protected void onError(ApiException e) {
                        callback.OnError(e);
                    }

                    @Override
                    public void onNext(List<GetCityListEntity> sendSmsCode) {
                        callback.OnSuccess(sendSmsCode);
                    }
                });
        callback.AddSubscription(subscriber);
    }

    //根据城市获取学校列表
    public void getSchoolList(String city, final IModelResult<List<GetSchoolListEntity>> callback) {
        Subscription subscriber = httpService.getSchoolList(version, client, city)
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .compose(new CommonTransformer<List<GetSchoolListEntity>>())
                .subscribe(new CommonSubscriber<List<GetSchoolListEntity>>(viewload) {
                    @Override
                    protected void onError(ApiException e) {
                        callback.OnError(e);
                    }

                    @Override
                    public void onNext(List<GetSchoolListEntity> sendSmsCode) {
                        callback.OnSuccess(sendSmsCode);
                    }
                });
        callback.AddSubscription(subscriber);
    }

    //获取学期列表
    public void getTermList(String school_id, String page, final IModelResult<GetTermListEntity> callback) {
        Subscription subscriber = httpService.getTermList(version, client, school_id, page)
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .compose(new CommonTransformer<GetTermListEntity>())
                .subscribe(new CommonSubscriber<GetTermListEntity>(viewload) {
                    @Override
                    protected void onError(ApiException e) {
                        callback.OnError(e);
                    }

                    @Override
                    public void onNext(GetTermListEntity sendSmsCode) {
                        callback.OnSuccess(sendSmsCode);
                    }
                });
        callback.AddSubscription(subscriber);
    }

    //获取教学楼列表
    public void getBuildingList(String school_id, String b_id, String term_id, final IModelResult<List<GetBuildingListEntity>> callback) {
        Log.e("aaa","获取展位提交的参数L:"+school_id+":"+b_id+":"+term_id);
        Subscription subscriber = httpService.getBuildingList(version, client, school_id, b_id, term_id)
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .compose(new CommonTransformer<List<GetBuildingListEntity>>())
                .subscribe(new CommonSubscriber<List<GetBuildingListEntity>>(viewload) {
                    @Override
                    protected void onError(ApiException e) {
                        callback.OnError(e);
                    }

                    @Override
                    public void onNext(List<GetBuildingListEntity> sendSmsCode) {
                        callback.OnSuccess(sendSmsCode);
                    }
                });
        callback.AddSubscription(subscriber);
    }

    //获取楼层列表
    public void getFloorList(String school_id, String building, final IModelResult<List<GetFloorListEntity>> callback) {
        Subscription subscriber = httpService.getFloorList(version, client, school_id, building)
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .compose(new CommonTransformer<List<GetFloorListEntity>>())
                .subscribe(new CommonSubscriber<List<GetFloorListEntity>>(viewload) {
                    @Override
                    protected void onError(ApiException e) {
                        callback.OnError(e);
                    }

                    @Override
                    public void onNext(List<GetFloorListEntity> sendSmsCode) {
                        callback.OnSuccess(sendSmsCode);
                    }
                });
        callback.AddSubscription(subscriber);
    }

    //获取课室列表
    public void getClassList(String floor_id, final IModelResult<List<GetClassListEntity>> callback) {
        Subscription subscriber = httpService.getClassList(version, client, floor_id)
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .compose(new CommonTransformer<List<GetClassListEntity>>())
                .subscribe(new CommonSubscriber<List<GetClassListEntity>>(viewload) {
                    @Override
                    protected void onError(ApiException e) {
                        callback.OnError(e);
                    }

                    @Override
                    public void onNext(List<GetClassListEntity> sendSmsCode) {
                        callback.OnSuccess(sendSmsCode);
                    }
                });
        callback.AddSubscription(subscriber);
    }

    //获取座位表
    public void getSeatList(String class_id, String term_id, final IModelResult<GetSeatListEntity> callback) {
        Subscription subscriber = httpService.getSeatList(version, client, class_id, term_id)
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .compose(new CommonTransformer<GetSeatListEntity>())
                .subscribe(new CommonSubscriber<GetSeatListEntity>(viewload) {
                    @Override
                    protected void onError(ApiException e) {
                        callback.OnError(e);
                    }

                    @Override
                    public void onNext(GetSeatListEntity sendSmsCode) {
                        callback.OnSuccess(sendSmsCode);
                    }
                });
        callback.AddSubscription(subscriber);
    }

    //根据座位id获取座位表
    public void getSeatListById(String seat_id, final IModelResult<GetSeatListEntity> callback) {
        Subscription subscriber = httpService.getSeatListById(version, client, seat_id)
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .compose(new CommonTransformer<GetSeatListEntity>())
                .subscribe(new CommonSubscriber<GetSeatListEntity>(viewload) {
                    @Override
                    protected void onError(ApiException e) {
                        callback.OnError(e);
                    }

                    @Override
                    public void onNext(GetSeatListEntity sendSmsCode) {
                        callback.OnSuccess(sendSmsCode);
                    }
                });
        callback.AddSubscription(subscriber);
    }

    //根据id获取学期详情
    public void getTermListDetailById(String term_id, final IModelResult<GetTermListDetailByIdEntity> callback) {
        Subscription subscriber = httpService.getTermListDetailById(version, client, term_id)
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .compose(new CommonTransformer<GetTermListDetailByIdEntity>())
                .subscribe(new CommonSubscriber<GetTermListDetailByIdEntity>(viewload) {
                    @Override
                    protected void onError(ApiException e) {
                        callback.OnError(e);
                    }

                    @Override
                    public void onNext(GetTermListDetailByIdEntity sendSmsCode) {
                        callback.OnSuccess(sendSmsCode);
                    }
                });
        callback.AddSubscription(subscriber);
    }

    //添加礼品
    public void addGift(String gfname,String gfimg, String gfinfo, String gfnum, String gfcontent, String gfstart, String gfend, String gffar,
                        String user_id, String school_id, String term_id,
                        final IModelResult<GiftListEntity.ListsBean> callback) {
        Subscription subscriber = httpService.addGift(version, client,
                gfname,gfimg, gfinfo, gfnum, gfcontent, gfstart, gfend, gffar, user_id, school_id, term_id)
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .compose(new CommonTransformer<GiftListEntity.ListsBean>())
                .subscribe(new CommonSubscriber<GiftListEntity.ListsBean>(viewload) {
                    @Override
                    protected void onError(ApiException e) {
                        callback.OnError(e);
                    }

                    @Override
                    public void onNext(GiftListEntity.ListsBean sendSmsCode) {
                        callback.OnSuccess(sendSmsCode);
                    }
                });
        callback.AddSubscription(subscriber);
    }

    //获取礼品列表
    public void getGiftList(String user_id, String term_id, final IModelResult<GiftListEntity> callback) {
        Subscription subscriber = httpService.getGiftList(version, client, user_id, term_id)
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .compose(new CommonTransformer<GiftListEntity>())
                .subscribe(new CommonSubscriber<GiftListEntity>(viewload) {
                    @Override
                    protected void onError(ApiException e) {
                        callback.OnError(e);
                    }

                    @Override
                    public void onNext(GiftListEntity sendSmsCode) {
                        callback.OnSuccess(sendSmsCode);
                    }
                });
        callback.AddSubscription(subscriber);
    }

    //获取礼品信息
    public void getGiftInfo(String gift_id, final IModelResult<GiftListEntity.ListsBean> callback) {
        String user_id = SPUtils.getString("user_id", "");
        String token = SPUtils.getString("token", "");
        Subscription subscriber = httpService.getGiftInfo(version, client, token, gift_id)
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .compose(new CommonTransformer<GiftListEntity.ListsBean>())
                .subscribe(new CommonSubscriber<GiftListEntity.ListsBean>(viewload) {
                    @Override
                    protected void onError(ApiException e) {
                        callback.OnError(e);
                    }

                    @Override
                    public void onNext(GiftListEntity.ListsBean sendSmsCode) {
                        callback.OnSuccess(sendSmsCode);
                    }
                });
        callback.AddSubscription(subscriber);
    }

    //获取我的礼品信息
    public void getmyGiftInfo(String gift_id, final IModelResult<GiftListEntity.ListsBean> callback) {
        String token = SPUtils.getString("token", "");
        Subscription subscriber = httpService.getmyGiftInfo(version, client, token, gift_id)
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .compose(new CommonTransformer<GiftListEntity.ListsBean>())
                .subscribe(new CommonSubscriber<GiftListEntity.ListsBean>(viewload) {
                    @Override
                    protected void onError(ApiException e) {
                        callback.OnError(e);
                    }

                    @Override
                    public void onNext(GiftListEntity.ListsBean sendSmsCode) {
                        callback.OnSuccess(sendSmsCode);
                    }
                });
        callback.AddSubscription(subscriber);
    }
    //修改礼品
    public void updateGift(String gift_id,String gfimg, String gfname, String gfinfo, String gfnum, String gfcontent,
                           String gfstart, String gfend, String gffar,
                           final IModelResult<GiftListEntity.ListsBean> callback) {
        Subscription subscriber = httpService.updateGift(version, client, gift_id,gfimg,
                gfname, gfinfo, gfnum, gfcontent, gfstart, gfend, gffar)
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .compose(new CommonTransformer<GiftListEntity.ListsBean>())
                .subscribe(new CommonSubscriber<GiftListEntity.ListsBean>(viewload) {
                    @Override
                    protected void onError(ApiException e) {
                        callback.OnError(e);
                    }

                    @Override
                    public void onNext(GiftListEntity.ListsBean sendSmsCode) {
                        callback.OnSuccess(sendSmsCode);
                    }
                });
        callback.AddSubscription(subscriber);
    }

    //删除礼品
    public void delGift(String gift_id, final IModelResult<List<GiftListEntity.ListsBean>> callback) {

        Subscription subscriber = httpService.delGift(version, client, gift_id)
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .compose(new CommonTransformer<List<GiftListEntity.ListsBean>>())
                .subscribe(new CommonSubscriber<List<GiftListEntity.ListsBean>>(viewload) {
                    @Override
                    protected void onError(ApiException e) {
                        callback.OnError(e);
                    }

                    @Override
                    public void onNext(List<GiftListEntity.ListsBean> sendSmsCode) {
                        callback.OnSuccess(sendSmsCode);
                    }
                });
        callback.AddSubscription(subscriber);
    }

    //添加抽奖活动
    public void addProtion(String type, String far, String starttime, String endtime, String rule,
                           String user_id, String school_id, String term_id,
                           final IModelResult<ProtionListEntity.ListsBean> callback) {
        Subscription subscriber = httpService.addProtion(version, client,
                type, far, starttime, endtime, rule, user_id, school_id, term_id)
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .compose(new CommonTransformer<ProtionListEntity.ListsBean>())
                .subscribe(new CommonSubscriber<ProtionListEntity.ListsBean>(viewload) {
                    @Override
                    protected void onError(ApiException e) {
                        callback.OnError(e);
                    }

                    @Override
                    public void onNext(ProtionListEntity.ListsBean sendSmsCode) {
                        callback.OnSuccess(sendSmsCode);
                    }
                });
        callback.AddSubscription(subscriber);
    }


    //获取抽奖活动列表
    public void getProtionList(String user_id, String term_id, final IModelResult<ProtionListEntity> callback) {
        Log.e("aaa","获取抽getProtionList奖列表："+user_id+"，term_id:"+term_id);
        Subscription subscriber = httpService.getProtionList(version, client, user_id, term_id)
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .compose(new CommonTransformer<ProtionListEntity>())
                .subscribe(new CommonSubscriber<ProtionListEntity>(viewload) {
                    @Override
                    protected void onError(ApiException e) {
                        callback.OnError(e);
                    }

                    @Override
                    public void onNext(ProtionListEntity sendSmsCode) {
                        callback.OnSuccess(sendSmsCode);
                    }
                });
        callback.AddSubscription(subscriber);
    }


    //获取抽奖活动信息
    public void getProtionInfo(String pt_id, final IModelResult<ProtionListEntity.ListsBean> callback) {
        String user_id = SPUtils.getString("user_id", "");
        String token = SPUtils.getString("token", "");
        Subscription subscriber = httpService.getProtionInfo(version, client, token, pt_id)
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .compose(new CommonTransformer<ProtionListEntity.ListsBean>())
                .subscribe(new CommonSubscriber<ProtionListEntity.ListsBean>(viewload) {
                    @Override
                    protected void onError(ApiException e) {
                        callback.OnError(e);
                    }

                    @Override
                    public void onNext(ProtionListEntity.ListsBean sendSmsCode) {
                        callback.OnSuccess(sendSmsCode);
                    }
                });
        callback.AddSubscription(subscriber);
    }

    //修改抽奖活动
    public void updateProtion(String pt_id, String type, String far, String starttime, String endtime,
                              String rule,
                              final IModelResult<ProtionListEntity.ListsBean> callback) {

        Log.e("aaa","提交的展会pt_id："+pt_id+",类型type："+type+"，范围距离far："+far+"，开始hi时间starttime："+starttime+"，结束时间：endtime"+endtime+"，规则："+rule);
        Subscription subscriber = httpService.updateProtion(version, client, pt_id,
                type, far, starttime, endtime, rule)
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .compose(new CommonTransformer<ProtionListEntity.ListsBean>())
                .subscribe(new CommonSubscriber<ProtionListEntity.ListsBean>(viewload) {
                    @Override
                    protected void onError(ApiException e) {
                        callback.OnError(e);
                    }

                    @Override
                    public void onNext(ProtionListEntity.ListsBean sendSmsCode) {
                        callback.OnSuccess(sendSmsCode);
                    }
                });
        callback.AddSubscription(subscriber);
    }

    //删除抽奖活动
    public void delProtion(String pt_id, final IModelResult<ProtionListEntity.ListsBean> callback) {

        Subscription subscriber = httpService.delProtion(version, client, pt_id)
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .compose(new CommonTransformer<ProtionListEntity.ListsBean>())
                .subscribe(new CommonSubscriber<ProtionListEntity.ListsBean>(viewload) {
                    @Override
                    protected void onError(ApiException e) {
                        callback.OnError(e);
                    }

                    @Override
                    public void onNext(ProtionListEntity.ListsBean sendSmsCode) {
                        callback.OnSuccess(sendSmsCode);
                    }
                });
        callback.AddSubscription(subscriber);
    }

    //添加奖品
    public void addPrize(String prname,String gfimg, String info, String content, String rank, String num,
                         String rate, String pt_id,
                         final IModelResult<PrizeListEntity.ListsBean> callback) {
        String user_id = SPUtils.getString("user_id", "");
        Subscription subscriber = httpService.addPrize(version, client,
                prname,gfimg, info, content, rank, num, rate, pt_id, user_id)
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .compose(new CommonTransformer<PrizeListEntity.ListsBean>())
                .subscribe(new CommonSubscriber<PrizeListEntity.ListsBean>(viewload) {
                    @Override
                    protected void onError(ApiException e) {
                        callback.OnError(e);
                    }

                    @Override
                    public void onNext(PrizeListEntity.ListsBean sendSmsCode) {
                        callback.OnSuccess(sendSmsCode);
                    }
                });
        callback.AddSubscription(subscriber);
    }

    //获取奖品列表
    public void getPrizeList(String pt_id, final IModelResult<PrizeListEntity> callback) {
        Subscription subscriber = httpService.getPrizeList(version, client, pt_id)
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .compose(new CommonTransformer<PrizeListEntity>())
                .subscribe(new CommonSubscriber<PrizeListEntity>(viewload) {
                    @Override
                    protected void onError(ApiException e) {
                        callback.OnError(e);
                    }

                    @Override
                    public void onNext(PrizeListEntity sendSmsCode) {
                        callback.OnSuccess(sendSmsCode);
                    }
                });
        callback.AddSubscription(subscriber);
    }

    //获取奖品信息
    public void getPrizeInfo(String pr_id, final IModelResult<PrizeListEntity.ListsBean> callback) {
        String user_id = SPUtils.getString("user_id", "");
        String token = SPUtils.getString("token", "");
        Subscription subscriber = httpService.getPrizeInfo(version, client, token, pr_id)
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .compose(new CommonTransformer<PrizeListEntity.ListsBean>())
                .subscribe(new CommonSubscriber<PrizeListEntity.ListsBean>(viewload) {
                    @Override
                    protected void onError(ApiException e) {
                        callback.OnError(e);
                    }

                    @Override
                    public void onNext(PrizeListEntity.ListsBean sendSmsCode) {
                        callback.OnSuccess(sendSmsCode);
                    }
                });
        callback.AddSubscription(subscriber);
    }

    //获取我的奖品信息
    public void getmyPrizeInfo(String pr_id, final IModelResult<PrizeListEntity.ListsBean> callback) {
        String user_id = SPUtils.getString("user_id", "");
        String token = SPUtils.getString("token", "");
        Subscription subscriber = httpService.getmyPrizeInfo(version, client, token, pr_id)
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .compose(new CommonTransformer<PrizeListEntity.ListsBean>())
                .subscribe(new CommonSubscriber<PrizeListEntity.ListsBean>(viewload) {
                    @Override
                    protected void onError(ApiException e) {
                        callback.OnError(e);
                    }

                    @Override
                    public void onNext(PrizeListEntity.ListsBean sendSmsCode) {
                        callback.OnSuccess(sendSmsCode);
                    }
                });
        callback.AddSubscription(subscriber);
    }


    //修改奖品
    public void updatePrize(String pr_id,String pr_img, String prname, String info, String content, String rank, String num,
                            String rate,
                              final IModelResult<PrizeListEntity.ListsBean> callback) {
        Subscription subscriber = httpService.updatePrize(version, client, pr_id,pr_img,
                prname, info, content, rank, num, rate)
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .compose(new CommonTransformer<PrizeListEntity.ListsBean>())
                .subscribe(new CommonSubscriber<PrizeListEntity.ListsBean>(viewload) {
                    @Override
                    protected void onError(ApiException e) {
                        callback.OnError(e);
                    }

                    @Override
                    public void onNext(PrizeListEntity.ListsBean sendSmsCode) {
                        callback.OnSuccess(sendSmsCode);
                    }
                });
        callback.AddSubscription(subscriber);
    }

    //删除奖品
    public void delPrize(String pr_id, final IModelResult<List<PrizeListEntity.ListsBean>> callback) {

        Subscription subscriber = httpService.delPrize(version, client, pr_id)
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .compose(new CommonTransformer<List<PrizeListEntity.ListsBean>>())
                .subscribe(new CommonSubscriber<List<PrizeListEntity.ListsBean>>(viewload) {
                    @Override
                    protected void onError(ApiException e) {
                        callback.OnError(e);
                    }

                    @Override
                    public void onNext(List<PrizeListEntity.ListsBean> sendSmsCode) {
                        callback.OnSuccess(sendSmsCode);
                    }
                });
        callback.AddSubscription(subscriber);
    }


    //添加用户名片
    public void addUserCard(String user_id, String real_name, String company, String job, String com_address, String com_page,
                            String user_phone, String user_tel, String user_fax, String user_email, String user_qq,
                            String wechat, String post_address, String com_intro,
                            final IModelResult<GetUserCardEntity.ListsBean> callback) {


        Subscription subscriber = httpService.addUserCard(version, client,
                user_id, real_name, company, job, com_address, com_page, user_phone, user_tel, user_fax,
                user_email, user_qq, wechat, post_address, com_intro)
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .compose(new CommonTransformer<GetUserCardEntity.ListsBean>())
                .subscribe(new CommonSubscriber<GetUserCardEntity.ListsBean>(viewload) {
                    @Override
                    protected void onError(ApiException e) {
                        callback.OnError(e);
                    }

                    @Override
                    public void onNext(GetUserCardEntity.ListsBean sendSmsCode) {
                        callback.OnSuccess(sendSmsCode);
                    }
                });
        callback.AddSubscription(subscriber);
    }


    //获取用户名片信息
    public void getUserCard(String target_user_id, final IModelResult<GetUserCardEntity.ListsBean> callback) {
        String token = SPUtils.getString("token", "");
        String user_id = SPUtils.getString("user_id", "");
        Subscription subscriber = httpService.getUserCard(version, client, token, user_id, target_user_id)
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .compose(new CommonTransformer<GetUserCardEntity.ListsBean>())
                .subscribe(new CommonSubscriber<GetUserCardEntity.ListsBean>(viewload) {
                    @Override
                    protected void onError(ApiException e) {
                        callback.OnError(e);
                    }

                    @Override
                    public void onNext(GetUserCardEntity.ListsBean sendSmsCode) {
                        callback.OnSuccess(sendSmsCode);
                    }
                });
        callback.AddSubscription(subscriber);
    }

    //修改用户名片
    public void updateUserCard(String real_name, String company, String job, String com_address, String com_page,
                               String user_phone, String user_tel, String user_fax, String user_email, String user_qq,
                               String wechat, String post_address, String com_intro, final IModelResult<GetUserCardEntity.ListsBean> callback) {
        String user_id = SPUtils.getString("user_id", "");
        Subscription subscriber = httpService.updateUserCard(version, client, user_id, real_name, company, job, com_address, com_page, user_phone, user_tel, user_fax,
                user_email, user_qq, wechat, post_address, com_intro)
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .compose(new CommonTransformer<GetUserCardEntity.ListsBean>())
                .subscribe(new CommonSubscriber<GetUserCardEntity.ListsBean>(viewload) {
                    @Override
                    protected void onError(ApiException e) {
                        callback.OnError(e);
                    }

                    @Override
                    public void onNext(GetUserCardEntity.ListsBean sendSmsCode) {
                        callback.OnSuccess(sendSmsCode);
                    }
                });
        callback.AddSubscription(subscriber);
    }

    //用户参加领取礼品活动
    public void userJoinGift(String user_id, String gift_id,String term_id, final IModelResult<UserJoingiftEntity.ListsBean> callback) {
        Log.e("aaa","提交的展会id："+term_id);
        Subscription subscriber = httpService.userJoinGift(version, client,
                user_id, gift_id,term_id)
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .compose(new CommonTransformer<UserJoingiftEntity.ListsBean>())
                .subscribe(new CommonSubscriber<UserJoingiftEntity.ListsBean>(viewload) {
                    @Override
                    protected void onError(ApiException e) {
                        callback.OnError(e);
                    }

                    @Override
                    public void onNext(UserJoingiftEntity.ListsBean sendSmsCode) {
                        callback.OnSuccess(sendSmsCode);
                    }
                });
        callback.AddSubscription(subscriber);
    }

    //用户领取礼品
    public void userHadGift(String user_id, String gift_id,String term_id, final IModelResult<UserHadgiftEntity.ListsBean> callback) {
        Log.e("aaa","提交的展会id："+term_id);
        Subscription subscriber = httpService.userHadGift(version, client,
                user_id, gift_id,term_id)
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .compose(new CommonTransformer<UserHadgiftEntity.ListsBean>())
                .subscribe(new CommonSubscriber<UserHadgiftEntity.ListsBean>(viewload) {
                    @Override
                    protected void onError(ApiException e) {
                        callback.OnError(e);
                    }

                    @Override
                    public void onNext(UserHadgiftEntity.ListsBean sendSmsCode) {
                        callback.OnSuccess(sendSmsCode);
                    }
                });
        callback.AddSubscription(subscriber);
    }

    //用户参与抽奖
    public void userHadPro(String user_id, String pt_id, final IModelResult<UserHadproEntity.ListsBean> callback) {
       Log.e("aaa","用户ID："+user_id+"，抽奖id："+pt_id);
        Subscription subscriber = httpService.userHadPro(version, client,
                user_id, pt_id)
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .compose(new CommonTransformer<UserHadproEntity.ListsBean>())
                .subscribe(new CommonSubscriber<UserHadproEntity.ListsBean>(viewload) {
                    @Override
                    protected void onError(ApiException e) {
                        callback.OnError(e);
                    }

                    @Override
                    public void onNext(UserHadproEntity.ListsBean sendSmsCode) {
                        callback.OnSuccess(sendSmsCode);
                    }
                });
        callback.AddSubscription(subscriber);
    }

    //用户中奖
    public void userHadPrize(String user_id, String pr_id, final IModelResult<UserHadprizeEntity.ListsBean> callback) {
        Subscription subscriber = httpService.userHadPrize(version, client,
                user_id, pr_id)
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .compose(new CommonTransformer<UserHadprizeEntity.ListsBean>())
                .subscribe(new CommonSubscriber<UserHadprizeEntity.ListsBean>(viewload) {
                    @Override
                    protected void onError(ApiException e) {
                        callback.OnError(e);
                    }

                    @Override
                    public void onNext(UserHadprizeEntity.ListsBean sendSmsCode) {
                        callback.OnSuccess(sendSmsCode);
                    }
                });
        callback.AddSubscription(subscriber);
    }

    //获取我的礼品列表
    public void getMyGiftList(String user_id, final IModelResult<UserHadgiftEntity> callback) {
        String token = SPUtils.getString("token", "");
        Subscription subscriber = httpService.getMyGiftList(version, client, token, user_id)
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .compose(new CommonTransformer<UserHadgiftEntity>())
                .subscribe(new CommonSubscriber<UserHadgiftEntity>(viewload) {
                    @Override
                    protected void onError(ApiException e) {
                        callback.OnError(e);
                    }

                    @Override
                    public void onNext(UserHadgiftEntity sendSmsCode) {
                        callback.OnSuccess(sendSmsCode);
                    }
                });
        callback.AddSubscription(subscriber);
    }

    //获取我参与的抽奖活动列表
    public void getMyProList(String user_id, final IModelResult<UserHadproEntity> callback) {
        Subscription subscriber = httpService.getMyProList(version, client, user_id)
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .compose(new CommonTransformer<UserHadproEntity>())
                .subscribe(new CommonSubscriber<UserHadproEntity>(viewload) {
                    @Override
                    protected void onError(ApiException e) {
                        callback.OnError(e);
                    }

                    @Override
                    public void onNext(UserHadproEntity sendSmsCode) {
                        callback.OnSuccess(sendSmsCode);
                    }
                });
        callback.AddSubscription(subscriber);
    }

    //获取我的奖品列表
    public void getMyPrize(String user_id, final IModelResult<UserHadprizeEntity> callback) {
        Subscription subscriber = httpService.getMyPrize(version, client, user_id)
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .compose(new CommonTransformer<UserHadprizeEntity>())
                .subscribe(new CommonSubscriber<UserHadprizeEntity>(viewload) {
                    @Override
                    protected void onError(ApiException e) {
                        callback.OnError(e);
                    }

                    @Override
                    public void onNext(UserHadprizeEntity sendSmsCode) {
                        callback.OnSuccess(sendSmsCode);
                    }
                });
        callback.AddSubscription(subscriber);
    }
    //参加领取礼品活动的用户列表
    public void getJoinUser(String gift_id,int page,final IModelResult<UserJoingiftEntity> callback) {
        Subscription subscriber = httpService.getJoinUser(version, client, page,gift_id)
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .compose(new CommonTransformer<UserJoingiftEntity>())
                .subscribe(new CommonSubscriber<UserJoingiftEntity>(viewload) {
                    @Override
                    protected void onError(ApiException e) {
                        callback.OnError(e);
                    }

                    @Override
                    public void onNext(UserJoingiftEntity sendSmsCode) {
                        callback.OnSuccess(sendSmsCode);
                    }
                });
        callback.AddSubscription(subscriber);
    }

    //领取礼品的用户列表
    public void getGiftUser(String gift_id,int page,final IModelResult<UserHadgiftEntity> callback) {
        Subscription subscriber = httpService.getGiftUser(version, client, page,gift_id)
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .compose(new CommonTransformer<UserHadgiftEntity>())
                .subscribe(new CommonSubscriber<UserHadgiftEntity>(viewload) {
                    @Override
                    protected void onError(ApiException e) {
                        callback.OnError(e);
                    }

                    @Override
                    public void onNext(UserHadgiftEntity sendSmsCode) {
                        callback.OnSuccess(sendSmsCode);
                    }
                });
        callback.AddSubscription(subscriber);
    }

    //参与抽奖活动的用户列表
    public void getProUser(String pt_id,int newpage, final IModelResult<UserHadproEntity> callback) {
        Subscription subscriber = httpService.getProUser(version, client,newpage,pt_id)
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .compose(new CommonTransformer<UserHadproEntity>())
                .subscribe(new CommonSubscriber<UserHadproEntity>(viewload) {
                    @Override
                    protected void onError(ApiException e) {
                        callback.OnError(e);
                    }

                    @Override
                    public void onNext(UserHadproEntity sendSmsCode) {
                        callback.OnSuccess(sendSmsCode);
                    }
                });
        callback.AddSubscription(subscriber);
    }


    //参与活动中奖的用户列表
    public void getWProUser(String pt_id,int pageno, final IModelResult<UserHadprizeEntity> callback) {
        Subscription subscriber = httpService.getWProUser(version, client,pageno, pt_id)
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .compose(new CommonTransformer<UserHadprizeEntity>())
                .subscribe(new CommonSubscriber<UserHadprizeEntity>(viewload) {
                    @Override
                    protected void onError(ApiException e) {
                        callback.OnError(e);
                    }

                    @Override
                    public void onNext(UserHadprizeEntity sendSmsCode) {
                        callback.OnSuccess(sendSmsCode);
                    }
                });
        callback.AddSubscription(subscriber);
    }


    //中奖的用户列表
    public void getPrizeUser(String pr_id,int page_no, final IModelResult<UserHadprizeEntity> callback) {
        Subscription subscriber = httpService.getPrizeUser(version, client,page_no, pr_id)
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .compose(new CommonTransformer<UserHadprizeEntity>())
                .subscribe(new CommonSubscriber<UserHadprizeEntity>(viewload) {
                    @Override
                    protected void onError(ApiException e) {
                        callback.OnError(e);
                    }

                    @Override
                    public void onNext(UserHadprizeEntity sendSmsCode) {
                        callback.OnSuccess(sendSmsCode);
                    }
                });
        callback.AddSubscription(subscriber);
    }


    //搜索用户
    public void searchUser(String key, final IModelResult<List<UserInfoEntity>> callback) {
        Subscription subscriber = httpService.searchUser(version, client, key)
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .compose(new CommonTransformer<List<UserInfoEntity>>())
                .subscribe(new CommonSubscriber<List<UserInfoEntity>>(viewload) {
                    @Override
                    protected void onError(ApiException e) {
                        callback.OnError(e);
                    }

                    @Override
                    public void onNext(List<UserInfoEntity> sendSmsCode) {
                        callback.OnSuccess(sendSmsCode);
                    }

                    @Override
                    public void onCompleted() {
                        super.onCompleted();
                    }
                });
        callback.AddSubscription(subscriber);
    }

    //上传相册
    public void uploadToAlbum(String user_id, String term_id,String url, final IModelResult<AlbumBean> callback) {
        Subscription subscriber = httpService.uploadToAlbum(version, client,
                user_id, term_id,url)
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .compose(new CommonTransformer<AlbumBean>())
                .subscribe(new CommonSubscriber<AlbumBean>(viewload) {
                    @Override
                    protected void onError(ApiException e) {
                        callback.OnError(e);
                    }

                    @Override
                    public void onNext(AlbumBean sendSmsCode) {
                        callback.OnSuccess(sendSmsCode);
                    }
                });
        callback.AddSubscription(subscriber);
    }
    //得到相册列表
    public void getAlbumList(String user_id, String term_id, final IModelResult<List<AlbumBean>> callback) {
        Subscription subscriber = httpService.getAlbumList(version, client,
                user_id, term_id)
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .compose(new CommonTransformer<List<AlbumBean>>())
                .subscribe(new CommonSubscriber<List<AlbumBean>>(viewload) {
                    @Override
                    protected void onError(ApiException e) {
                        callback.OnError(e);
                    }

                    @Override
                    public void onNext(List<AlbumBean> sendSmsCode) {
                        callback.OnSuccess(sendSmsCode);
                    }
                });
        callback.AddSubscription(subscriber);
    }
    //从相册中删除一张图片
    public void deletePhoto(String img_id ,final IModelResult<List<AlbumBean>> callback) {
        Log.e("aaa","删除图片了"+img_id);
        try {
            Subscription subscriber = httpService.deletePhoto(version,client,
                    img_id)
                    .observeOn(AndroidSchedulers.mainThread())
                    .unsubscribeOn(AndroidSchedulers.mainThread())
                    .subscribeOn(Schedulers.io())
                    .compose(new CommonTransformer<List<AlbumBean>>())
                    .subscribe(new CommonSubscriber<List<AlbumBean>>(viewload) {
                        @Override
                        protected void onError(ApiException e) {
                            try {
                                callback.OnError(e);
                            } catch (Exception e1) {
                                e1.printStackTrace();
                            }
                        }

                        @Override
                        public void onNext(List<AlbumBean> sendSmsCode) {
                            try {
                                callback.OnSuccess(sendSmsCode);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    });
            callback.AddSubscription(subscriber);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //上传公司活动视频到服务器
    public void uploadVideo(String user_id, String term_id,String url, final IModelResult<AlbumBean> callback) {
        Subscription subscriber = httpService.uploadVideo(version, client,
                user_id, term_id,url)
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .compose(new CommonTransformer<AlbumBean>())
                .subscribe(new CommonSubscriber<AlbumBean>(viewload) {
                    @Override
                    protected void onError(ApiException e) {
                        callback.OnError(e);
                    }

                    @Override
                    public void onNext(AlbumBean sendSmsCode) {
                        callback.OnSuccess(sendSmsCode);
                    }
                });
        callback.AddSubscription(subscriber);
    }

    //得到活动视频列表
    public void getVideoList(String user_id, String term_id, final IModelResult<List<AlbumBean>> callback) {
        Subscription subscriber = httpService.getVideoList(version, client,
                user_id, term_id)
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .compose(new CommonTransformer<List<AlbumBean>>())
                .subscribe(new CommonSubscriber<List<AlbumBean>>(viewload) {
                    @Override
                    protected void onError(ApiException e) {
                        callback.OnError(e);
                    }

                    @Override
                    public void onNext(List<AlbumBean> sendSmsCode) {
                        callback.OnSuccess(sendSmsCode);
                    }
                });
        callback.AddSubscription(subscriber);
    }

    //从活动视频中删除一个视频
    public void deleteVideo(String img_id ,final IModelResult<List<AlbumBean>> callback) {
        Log.e("aaa","删除图片了"+img_id);
        try {
            Subscription subscriber = httpService.deleteVideo(version,client,
                    img_id)
                    .observeOn(AndroidSchedulers.mainThread())
                    .unsubscribeOn(AndroidSchedulers.mainThread())
                    .subscribeOn(Schedulers.io())
                    .compose(new CommonTransformer<List<AlbumBean>>())
                    .subscribe(new CommonSubscriber<List<AlbumBean>>(viewload) {
                        @Override
                        protected void onError(ApiException e) {
                            try {
                                callback.OnError(e);
                            } catch (Exception e1) {
                                e1.printStackTrace();
                            }
                        }

                        @Override
                        public void onNext(List<AlbumBean> sendSmsCode) {
                            try {
                                callback.OnSuccess(sendSmsCode);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    });
            callback.AddSubscription(subscriber);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //添加秒爆促销活动信息
    public void addShopmb(String user_id,String title, String far, String starttime, String endtime,
                          String rule ,
                        final IModelResult<ShopmbListEntity.ListsBean> callback) {
        Subscription subscriber = httpService.addShopmb(version, client,
                user_id,title, far, starttime, endtime, rule)
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .compose(new CommonTransformer<ShopmbListEntity.ListsBean>())
                .subscribe(new CommonSubscriber<ShopmbListEntity.ListsBean>(viewload) {
                    @Override
                    protected void onError(ApiException e) {
                        callback.OnError(e);
                    }

                    @Override
                    public void onNext(ShopmbListEntity.ListsBean sendSmsCode) {
                        callback.OnSuccess(sendSmsCode);
                    }
                });
        callback.AddSubscription(subscriber);
    }

    //修改秒爆促销活动信息
    public void updateShopmb(String mb_id,String title, String far, String starttime, String endtime,
                             String rule ,
                             final IModelResult<ShopmbListEntity.ListsBean> callback) {
        Subscription subscriber = httpService.updateShopmb(version, client,
                mb_id,title, far, starttime, endtime, rule)
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .compose(new CommonTransformer<ShopmbListEntity.ListsBean>())
                .subscribe(new CommonSubscriber<ShopmbListEntity.ListsBean>(viewload) {
                    @Override
                    protected void onError(ApiException e) {
                        callback.OnError(e);
                    }

                    @Override
                    public void onNext(ShopmbListEntity.ListsBean sendSmsCode) {
                        callback.OnSuccess(sendSmsCode);
                    }
                });
        callback.AddSubscription(subscriber);
    }

    //获取店铺秒爆促销列表
    public void getShopmbList(String user_id, final IModelResult<ShopmbListEntity> callback) {
        Subscription subscriber = httpService.getShopmbList(version, client, user_id)
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .compose(new CommonTransformer<ShopmbListEntity>())
                .subscribe(new CommonSubscriber<ShopmbListEntity>(viewload) {
                    @Override
                    protected void onError(ApiException e) {
                        callback.OnError(e);
                    }

                    @Override
                    public void onNext(ShopmbListEntity sendSmsCode) {
                        callback.OnSuccess(sendSmsCode);
                    }
                });
        callback.AddSubscription(subscriber);
    }

    //获取店铺秒爆促销信息
    public void getShopmb(String mb_id, final IModelResult<ShopmbListEntity.ListsBean> callback) {
        String token = SPUtils.getString("token", "");
        Subscription subscriber = httpService.getShopmb(version, client, token, mb_id)
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .compose(new CommonTransformer<ShopmbListEntity.ListsBean>())
                .subscribe(new CommonSubscriber<ShopmbListEntity.ListsBean>(viewload) {
                    @Override
                    protected void onError(ApiException e) {
                        callback.OnError(e);
                    }

                    @Override
                    public void onNext(ShopmbListEntity.ListsBean sendSmsCode) {
                        callback.OnSuccess(sendSmsCode);
                    }
                });
        callback.AddSubscription(subscriber);
    }

    //删除店铺秒爆促销活动
    public void delShopmb(String mb_id, final IModelResult<ShopmbListEntity.ListsBean> callback) {

        Subscription subscriber = httpService.delShopmb(version, client, mb_id)
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .compose(new CommonTransformer<ShopmbListEntity.ListsBean>())
                .subscribe(new CommonSubscriber<ShopmbListEntity.ListsBean>(viewload) {
                    @Override
                    protected void onError(ApiException e) {
                        callback.OnError(e);
                    }

                    @Override
                    public void onNext(ShopmbListEntity.ListsBean sendSmsCode) {
                        callback.OnSuccess(sendSmsCode);
                    }
                });
        callback.AddSubscription(subscriber);
    }

    //添加秒爆商品
    public void addShopmbGood(String mb_id,String mbname, String info, String num, String mb_price,
                          String preprice ,String starttime ,String endtime ,String content ,String mbimg ,
                          final IModelResult<ShopmbgoodListEntity.ListsBean> callback) {
        Subscription subscriber = httpService.addShopmbGood(version, client,
                mb_id,mbname, info, num, mb_price, preprice, starttime, endtime, content, mbimg)
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .compose(new CommonTransformer<ShopmbgoodListEntity.ListsBean>())
                .subscribe(new CommonSubscriber<ShopmbgoodListEntity.ListsBean>(viewload) {
                    @Override
                    protected void onError(ApiException e) {
                        callback.OnError(e);
                    }

                    @Override
                    public void onNext(ShopmbgoodListEntity.ListsBean sendSmsCode) {
                        callback.OnSuccess(sendSmsCode);
                    }
                });
        callback.AddSubscription(subscriber);
    }

    //修改秒爆促销商品
    public void updateShopmbGood(String mbgood_id,String mbname, String info, String num, String mb_price,
                              String preprice ,String starttime ,String endtime ,String content ,String mbimg ,
                              final IModelResult<ShopmbgoodListEntity.ListsBean> callback) {
        Subscription subscriber = httpService.updateShopmbGood(version, client,
                mbgood_id,mbname, info, num, mb_price, preprice, starttime, endtime, content, mbimg)
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .compose(new CommonTransformer<ShopmbgoodListEntity.ListsBean>())
                .subscribe(new CommonSubscriber<ShopmbgoodListEntity.ListsBean>(viewload) {
                    @Override
                    protected void onError(ApiException e) {
                        callback.OnError(e);
                    }

                    @Override
                    public void onNext(ShopmbgoodListEntity.ListsBean sendSmsCode) {
                        callback.OnSuccess(sendSmsCode);
                    }
                });
        callback.AddSubscription(subscriber);
    }

    //获取店铺秒爆商品列表
    public void getShopmbgoodList(String mb_id, final IModelResult<ShopmbgoodListEntity> callback) {
        Subscription subscriber = httpService.getShopmbgoodList(version, client, mb_id)
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .compose(new CommonTransformer<ShopmbgoodListEntity>())
                .subscribe(new CommonSubscriber<ShopmbgoodListEntity>(viewload) {
                    @Override
                    protected void onError(ApiException e) {
                        callback.OnError(e);
                    }

                    @Override
                    public void onNext(ShopmbgoodListEntity sendSmsCode) {
                        callback.OnSuccess(sendSmsCode);
                    }
                });
        callback.AddSubscription(subscriber);
    }

    //获取店铺秒爆商品信息
    public void getMbgoodinfo(String mbgood_id, final IModelResult<ShopmbgoodListEntity.ListsBean> callback) {
        String token = SPUtils.getString("token", "");
        Subscription subscriber = httpService.getMbgoodinfo(version, client, token, mbgood_id)
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .compose(new CommonTransformer<ShopmbgoodListEntity.ListsBean>())
                .subscribe(new CommonSubscriber<ShopmbgoodListEntity.ListsBean>(viewload) {
                    @Override
                    protected void onError(ApiException e) {
                        callback.OnError(e);
                    }

                    @Override
                    public void onNext(ShopmbgoodListEntity.ListsBean sendSmsCode) {
                        callback.OnSuccess(sendSmsCode);
                    }
                });
        callback.AddSubscription(subscriber);
    }

    //删除店铺秒爆商品
    public void delShopmbgood(String mbgood_id, final IModelResult<ShopmbgoodListEntity.ListsBean> callback) {

        Subscription subscriber = httpService.delShopmbgood(version, client, mbgood_id)
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .compose(new CommonTransformer<ShopmbgoodListEntity.ListsBean>())
                .subscribe(new CommonSubscriber<ShopmbgoodListEntity.ListsBean>(viewload) {
                    @Override
                    protected void onError(ApiException e) {
                        callback.OnError(e);
                    }

                    @Override
                    public void onNext(ShopmbgoodListEntity.ListsBean sendSmsCode) {
                        callback.OnSuccess(sendSmsCode);
                    }
                });
        callback.AddSubscription(subscriber);
    }

    //添加店铺抽奖活动
    public void addShopPt(String user_id,String title, String type, String far, String starttime,
                              String endtime ,String rule ,
                              final IModelResult<ShopptListEntity.ListsBean> callback) {
        Subscription subscriber = httpService.addShopPt(version, client,
                user_id,title, type, far, starttime, endtime, rule)
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .compose(new CommonTransformer<ShopptListEntity.ListsBean>())
                .subscribe(new CommonSubscriber<ShopptListEntity.ListsBean>(viewload) {
                    @Override
                    protected void onError(ApiException e) {
                        callback.OnError(e);
                    }

                    @Override
                    public void onNext(ShopptListEntity.ListsBean sendSmsCode) {
                        callback.OnSuccess(sendSmsCode);
                    }
                });
        callback.AddSubscription(subscriber);
    }
    //修改店铺抽奖活动
    public void updateShopPt(String pt_id,String title, String type, String far, String starttime,
                          String endtime ,String rule ,
                          final IModelResult<ShopptListEntity.ListsBean> callback) {
        Subscription subscriber = httpService.updateShopPt(version, client,
                pt_id,title, type, far, starttime, endtime, rule)
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .compose(new CommonTransformer<ShopptListEntity.ListsBean>())
                .subscribe(new CommonSubscriber<ShopptListEntity.ListsBean>(viewload) {
                    @Override
                    protected void onError(ApiException e) {
                        callback.OnError(e);
                    }

                    @Override
                    public void onNext(ShopptListEntity.ListsBean sendSmsCode) {
                        callback.OnSuccess(sendSmsCode);
                    }
                });
        callback.AddSubscription(subscriber);
    }

    //获取店铺抽奖促销列表
    public void getShopptList(String user_id, final IModelResult<ShopptListEntity> callback) {
        Subscription subscriber = httpService.getShopptList(version, client, user_id)
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .compose(new CommonTransformer<ShopptListEntity>())
                .subscribe(new CommonSubscriber<ShopptListEntity>(viewload) {
                    @Override
                    protected void onError(ApiException e) {
                        callback.OnError(e);
                    }

                    @Override
                    public void onNext(ShopptListEntity sendSmsCode) {
                        callback.OnSuccess(sendSmsCode);
                    }
                });
        callback.AddSubscription(subscriber);
    }

    //删除店铺抽奖促销活动
    public void delShoppt(String pt_id, final IModelResult<ShopptListEntity.ListsBean> callback) {

        Subscription subscriber = httpService.delShoppt(version, client, pt_id)
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .compose(new CommonTransformer<ShopptListEntity.ListsBean>())
                .subscribe(new CommonSubscriber<ShopptListEntity.ListsBean>(viewload) {
                    @Override
                    protected void onError(ApiException e) {
                        callback.OnError(e);
                    }

                    @Override
                    public void onNext(ShopptListEntity.ListsBean sendSmsCode) {
                        callback.OnSuccess(sendSmsCode);
                    }
                });
        callback.AddSubscription(subscriber);
    }

    //获取店铺抽奖活动信息
    public void getShopptInfo(String pt_id, final IModelResult<ShopptListEntity.ListsBean> callback) {
        String token = SPUtils.getString("token", "");
        Subscription subscriber = httpService.getShopptInfo(version, client, token, pt_id)
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .compose(new CommonTransformer<ShopptListEntity.ListsBean>())
                .subscribe(new CommonSubscriber<ShopptListEntity.ListsBean>(viewload) {
                    @Override
                    protected void onError(ApiException e) {
                        callback.OnError(e);
                    }

                    @Override
                    public void onNext(ShopptListEntity.ListsBean sendSmsCode) {
                        callback.OnSuccess(sendSmsCode);
                    }
                });
        callback.AddSubscription(subscriber);
    }

    //添加店铺抽奖奖品
    public void addShopPrize(String prname,String gfimg, String info, String content, String rank, String num,
                         String rate, String pt_id, String price,
                         final IModelResult<ShopprListEntity.ListsBean> callback) {
        String user_id = SPUtils.getString("user_id", "");
        Subscription subscriber = httpService.addShopPrize(version, client,
                prname,gfimg, info, content, rank, num, rate, pt_id, price, user_id)
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .compose(new CommonTransformer<ShopprListEntity.ListsBean>())
                .subscribe(new CommonSubscriber<ShopprListEntity.ListsBean>(viewload) {
                    @Override
                    protected void onError(ApiException e) {
                        callback.OnError(e);
                    }

                    @Override
                    public void onNext(ShopprListEntity.ListsBean sendSmsCode) {
                        callback.OnSuccess(sendSmsCode);
                    }
                });
        callback.AddSubscription(subscriber);
    }

    //修改店铺抽奖奖品
    public void updateShopPrize(String prname,String pr_img,  String info, String content, String rank, String num,
                                String rate,String price,String pr_id,
                                final IModelResult<ShopprListEntity.ListsBean> callback) {
        Subscription subscriber = httpService.updateShopPrize(version, client, prname,pr_img,
                info, content, rank, num, rate, price, pr_id)
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .compose(new CommonTransformer<ShopprListEntity.ListsBean>())
                .subscribe(new CommonSubscriber<ShopprListEntity.ListsBean>(viewload) {
                    @Override
                    protected void onError(ApiException e) {
                        callback.OnError(e);
                    }

                    @Override
                    public void onNext(ShopprListEntity.ListsBean sendSmsCode) {
                        callback.OnSuccess(sendSmsCode);
                    }
                });
        callback.AddSubscription(subscriber);
    }
    //获取店铺抽奖奖品列表
    public void getShopprList(String pt_id, final IModelResult<ShopprListEntity> callback) {
        Subscription subscriber = httpService.getShopprList(version, client, pt_id)
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .compose(new CommonTransformer<ShopprListEntity>())
                .subscribe(new CommonSubscriber<ShopprListEntity>(viewload) {
                    @Override
                    protected void onError(ApiException e) {
                        callback.OnError(e);
                    }

                    @Override
                    public void onNext(ShopprListEntity sendSmsCode) {
                        callback.OnSuccess(sendSmsCode);
                    }
                });
        callback.AddSubscription(subscriber);
    }

    //获取店铺抽奖奖品信息
    public void getShopprInfo(String pr_id, final IModelResult<ShopprListEntity.ListsBean> callback) {
        String user_id = SPUtils.getString("user_id", "");
        String token = SPUtils.getString("token", "");
        Subscription subscriber = httpService.getShopprInfo(version, client, token, pr_id)
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .compose(new CommonTransformer<ShopprListEntity.ListsBean>())
                .subscribe(new CommonSubscriber<ShopprListEntity.ListsBean>(viewload) {
                    @Override
                    protected void onError(ApiException e) {
                        callback.OnError(e);
                    }

                    @Override
                    public void onNext(ShopprListEntity.ListsBean sendSmsCode) {
                        callback.OnSuccess(sendSmsCode);
                    }
                });
        callback.AddSubscription(subscriber);
    }


    //删除店铺抽奖奖品
    public void delShoppr(String pr_id, final IModelResult<List<ShopprListEntity.ListsBean>> callback) {

        Subscription subscriber = httpService.delShoppr(version, client, pr_id)
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .compose(new CommonTransformer<List<ShopprListEntity.ListsBean>>())
                .subscribe(new CommonSubscriber<List<ShopprListEntity.ListsBean>>(viewload) {
                    @Override
                    protected void onError(ApiException e) {
                        callback.OnError(e);
                    }

                    @Override
                    public void onNext(List<ShopprListEntity.ListsBean> sendSmsCode) {
                        callback.OnSuccess(sendSmsCode);
                    }
                });
        callback.AddSubscription(subscriber);
    }

    //添加打折降价商品
    public void addShopcpGood(String user_id,String cpname, String info, String num, String cp_price,
                              String preprice ,String pullon ,String content ,String cpimg ,
                              final IModelResult<ShopcpListEntity.ListsBean> callback) {
        Subscription subscriber = httpService.addShopcpGood(version, client,
                user_id,cpname, info, num, cp_price, preprice, pullon, content, cpimg)
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .compose(new CommonTransformer<ShopcpListEntity.ListsBean>())
                .subscribe(new CommonSubscriber<ShopcpListEntity.ListsBean>(viewload) {
                    @Override
                    protected void onError(ApiException e) {
                        callback.OnError(e);
                    }

                    @Override
                    public void onNext(ShopcpListEntity.ListsBean sendSmsCode) {
                        callback.OnSuccess(sendSmsCode);
                    }
                });
        callback.AddSubscription(subscriber);
    }

    //修改打折降价商品
    public void updateShopcpGood(String cp_id,String cpname, String info, String num, String cp_price,
                              String preprice ,String pullon ,String content ,String cpimg ,
                              final IModelResult<ShopcpListEntity.ListsBean> callback) {
        Subscription subscriber = httpService.updateShopcpGood(version, client,
                cp_id,cpname, info, num, cp_price, preprice, pullon, content, cpimg)
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .compose(new CommonTransformer<ShopcpListEntity.ListsBean>())
                .subscribe(new CommonSubscriber<ShopcpListEntity.ListsBean>(viewload) {
                    @Override
                    protected void onError(ApiException e) {
                        callback.OnError(e);
                    }

                    @Override
                    public void onNext(ShopcpListEntity.ListsBean sendSmsCode) {
                        callback.OnSuccess(sendSmsCode);
                    }
                });
        callback.AddSubscription(subscriber);
    }

    //获取店铺打折降价促销信息
    public void getShopcpList(String user_id, final IModelResult<ShopcpListEntity> callback) {
        Subscription subscriber = httpService.getShopcpList(version, client, user_id)
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .compose(new CommonTransformer<ShopcpListEntity>())
                .subscribe(new CommonSubscriber<ShopcpListEntity>(viewload) {
                    @Override
                    protected void onError(ApiException e) {
                        callback.OnError(e);
                    }

                    @Override
                    public void onNext(ShopcpListEntity sendSmsCode) {
                        callback.OnSuccess(sendSmsCode);
                    }
                });
        callback.AddSubscription(subscriber);
    }

    //获取店铺打折降价促销信息
    public void getShopcpgood(String cp_id, final IModelResult<ShopcpListEntity.ListsBean> callback) {
        String token = SPUtils.getString("token", "");
        Subscription subscriber = httpService.getShopcpgood(version, client, token, cp_id)
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .compose(new CommonTransformer<ShopcpListEntity.ListsBean>())
                .subscribe(new CommonSubscriber<ShopcpListEntity.ListsBean>(viewload) {
                    @Override
                    protected void onError(ApiException e) {
                        callback.OnError(e);
                    }

                    @Override
                    public void onNext(ShopcpListEntity.ListsBean sendSmsCode) {
                        callback.OnSuccess(sendSmsCode);
                    }
                });
        callback.AddSubscription(subscriber);
    }

    //删除店铺打折降价促销
    public void delShopcpgood(String cp_id, final IModelResult<ShopcpListEntity.ListsBean> callback) {

        Subscription subscriber = httpService.delShopcpgood(version, client, cp_id)
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .compose(new CommonTransformer<ShopcpListEntity.ListsBean>())
                .subscribe(new CommonSubscriber<ShopcpListEntity.ListsBean>(viewload) {
                    @Override
                    protected void onError(ApiException e) {
                        callback.OnError(e);
                    }

                    @Override
                    public void onNext(ShopcpListEntity.ListsBean sendSmsCode) {
                        callback.OnSuccess(sendSmsCode);
                    }
                });
        callback.AddSubscription(subscriber);
    }

    //用户购买秒爆商品
    public void userShopmb(String user_id, String mbgood_id,String mbname, String title,String mbimg, String mb_price,
                           String send_user_id, final IModelResult<HadShopmbEntity.ListsBean> callback) {
        Subscription subscriber = httpService.userShopmb(version, client,
                user_id, mbgood_id,mbname, title,mbimg, mb_price,send_user_id)
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .compose(new CommonTransformer<HadShopmbEntity.ListsBean>())
                .subscribe(new CommonSubscriber<HadShopmbEntity.ListsBean>(viewload) {
                    @Override
                    protected void onError(ApiException e) {
                        callback.OnError(e);
                    }

                    @Override
                    public void onNext(HadShopmbEntity.ListsBean sendSmsCode) {
                        callback.OnSuccess(sendSmsCode);
                    }
                });
        callback.AddSubscription(subscriber);
    }

    //购买了秒爆活动商品的用户列表
    public void getUserShopmbList(String mb_id,int page,final IModelResult<HadShopmbEntity> callback) {
        Subscription subscriber = httpService.getUserShopmbList(version, client, page,mb_id)
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .compose(new CommonTransformer<HadShopmbEntity>())
                .subscribe(new CommonSubscriber<HadShopmbEntity>(viewload) {
                    @Override
                    protected void onError(ApiException e) {
                        callback.OnError(e);
                    }

                    @Override
                    public void onNext(HadShopmbEntity sendSmsCode) {
                        callback.OnSuccess(sendSmsCode);
                    }
                });
        callback.AddSubscription(subscriber);
    }

    //购买秒爆商品的用户列表
    public void getUserShopmbGoodList(String mbgood_id,int page,final IModelResult<HadShopmbEntity> callback) {
        Subscription subscriber = httpService.getUserShopmbGoodList(version, client, page,mbgood_id)
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .compose(new CommonTransformer<HadShopmbEntity>())
                .subscribe(new CommonSubscriber<HadShopmbEntity>(viewload) {
                    @Override
                    protected void onError(ApiException e) {
                        callback.OnError(e);
                    }

                    @Override
                    public void onNext(HadShopmbEntity sendSmsCode) {
                        callback.OnSuccess(sendSmsCode);
                    }
                });
        callback.AddSubscription(subscriber);
    }


    //用户购买打折促销商品
    public void userShopcp(String user_id, String cp_id,String cpname,String cpimg,String info, String cp_price,String preprice,String buynum,String content,
                           String send_user_id, final IModelResult<HadShopcpEntity.ListsBean> callback) {
        Subscription subscriber = httpService.userShopcp(version, client,
                user_id, cp_id,cpname,cpimg,info, cp_price, preprice,buynum,content,send_user_id)
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .compose(new CommonTransformer<HadShopcpEntity.ListsBean>())
                .subscribe(new CommonSubscriber<HadShopcpEntity.ListsBean>(viewload) {
                    @Override
                    protected void onError(ApiException e) {
                        callback.OnError(e);
                    }

                    @Override
                    public void onNext(HadShopcpEntity.ListsBean sendSmsCode) {
                        callback.OnSuccess(sendSmsCode);
                    }
                });
        callback.AddSubscription(subscriber);
    }

    //购买打折促销商品的用户列表
    public void getUserShopcpList(String cp_id,int page,final IModelResult<HadShopcpEntity> callback) {
        Subscription subscriber = httpService.getUserShopcpList(version, client, page,cp_id)
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .compose(new CommonTransformer<HadShopcpEntity>())
                .subscribe(new CommonSubscriber<HadShopcpEntity>(viewload) {
                    @Override
                    protected void onError(ApiException e) {
                        callback.OnError(e);
                    }

                    @Override
                    public void onNext(HadShopcpEntity sendSmsCode) {
                        callback.OnSuccess(sendSmsCode);
                    }
                });
        callback.AddSubscription(subscriber);
    }


    //用户参与抽奖
    public void userHadShoppt(String user_id, String pt_id, final IModelResult<UserHadproEntity.ListsBean> callback) {
        Log.e("aaa","用户ID："+user_id+"，抽奖id："+pt_id);
        Subscription subscriber = httpService.userHadPro(version, client,
                user_id, pt_id)
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .compose(new CommonTransformer<UserHadproEntity.ListsBean>())
                .subscribe(new CommonSubscriber<UserHadproEntity.ListsBean>(viewload) {
                    @Override
                    protected void onError(ApiException e) {
                        callback.OnError(e);
                    }

                    @Override
                    public void onNext(UserHadproEntity.ListsBean sendSmsCode) {
                        callback.OnSuccess(sendSmsCode);
                    }
                });
        callback.AddSubscription(subscriber);
    }

    //用户中奖
    public void userHadShoppr(String user_id, String pr_id, final IModelResult<UserHadprizeEntity.ListsBean> callback) {
        Subscription subscriber = httpService.userHadPrize(version, client,
                user_id, pr_id)
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .compose(new CommonTransformer<UserHadprizeEntity.ListsBean>())
                .subscribe(new CommonSubscriber<UserHadprizeEntity.ListsBean>(viewload) {
                    @Override
                    protected void onError(ApiException e) {
                        callback.OnError(e);
                    }

                    @Override
                    public void onNext(UserHadprizeEntity.ListsBean sendSmsCode) {
                        callback.OnSuccess(sendSmsCode);
                    }
                });
        callback.AddSubscription(subscriber);
    }

    //获取我的秒爆商品列表
    public void getMyShoppmbList(String user_id, final IModelResult<HadShopmbEntity> callback) {
        String token = SPUtils.getString("token", "");
        Subscription subscriber = httpService.getMyShopmbList(version, client, token, user_id)
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .compose(new CommonTransformer<HadShopmbEntity>())
                .subscribe(new CommonSubscriber<HadShopmbEntity>(viewload) {
                    @Override
                    protected void onError(ApiException e) {
                        callback.OnError(e);
                    }

                    @Override
                    public void onNext(HadShopmbEntity sendSmsCode) {
                        callback.OnSuccess(sendSmsCode);
                    }
                });
        callback.AddSubscription(subscriber);
    }

    //获取我的秒爆商品信息
    public void getMyshopmbGood(String mbgood_id, final IModelResult<HadShopmbEntity.ListsBean> callback) {
        String token = SPUtils.getString("token", "");
        Subscription subscriber = httpService.getMyshopmbGood(version, client, token, mbgood_id)
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .compose(new CommonTransformer<HadShopmbEntity.ListsBean>())
                .subscribe(new CommonSubscriber<HadShopmbEntity.ListsBean>(viewload) {
                    @Override
                    protected void onError(ApiException e) {
                        callback.OnError(e);
                    }

                    @Override
                    public void onNext(HadShopmbEntity.ListsBean sendSmsCode) {
                        callback.OnSuccess(sendSmsCode);
                    }
                });
        callback.AddSubscription(subscriber);
    }


    //获取我参与的店铺抽奖活动列表
    public void getMyShopptList(String user_id, final IModelResult<UserHadproEntity> callback) {
        Subscription subscriber = httpService.getMyProList(version, client, user_id)
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .compose(new CommonTransformer<UserHadproEntity>())
                .subscribe(new CommonSubscriber<UserHadproEntity>(viewload) {
                    @Override
                    protected void onError(ApiException e) {
                        callback.OnError(e);
                    }

                    @Override
                    public void onNext(UserHadproEntity sendSmsCode) {
                        callback.OnSuccess(sendSmsCode);
                    }
                });
        callback.AddSubscription(subscriber);
    }

    //获取我的店铺抽奖奖品列表
    public void getMyShopPrList(String user_id, final IModelResult<HadShopprEntity> callback) {
        Subscription subscriber = httpService.getMyShopPrList(version, client, user_id)
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .compose(new CommonTransformer<HadShopprEntity>())
                .subscribe(new CommonSubscriber<HadShopprEntity>(viewload) {
                    @Override
                    protected void onError(ApiException e) {
                        callback.OnError(e);
                    }

                    @Override
                    public void onNext(HadShopprEntity sendSmsCode) {
                        callback.OnSuccess(sendSmsCode);
                    }
                });
        callback.AddSubscription(subscriber);
    }

    //获取我的店铺抽奖奖品信息
    public void getMyShopPrInfo(String pr_id, final IModelResult<HadShopprEntity.ListsBean> callback) {
        String user_id = SPUtils.getString("user_id", "");
        String token = SPUtils.getString("token", "");
        Subscription subscriber = httpService.getMyShopPrInfo(version, client, token,user_id ,pr_id)
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .compose(new CommonTransformer<HadShopprEntity.ListsBean>())
                .subscribe(new CommonSubscriber<HadShopprEntity.ListsBean>(viewload) {
                    @Override
                    protected void onError(ApiException e) {
                        callback.OnError(e);
                    }

                    @Override
                    public void onNext(HadShopprEntity.ListsBean sendSmsCode) {
                        callback.OnSuccess(sendSmsCode);
                    }
                });
        callback.AddSubscription(subscriber);
    }


    //获取我购买的打折商品列表
    public void getMyShopcpList(String user_id, final IModelResult<HadShopcpEntity> callback) {
        Subscription subscriber = httpService.getMyShopcpList(version, client, user_id)
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .compose(new CommonTransformer<HadShopcpEntity>())
                .subscribe(new CommonSubscriber<HadShopcpEntity>(viewload) {
                    @Override
                    protected void onError(ApiException e) {
                        callback.OnError(e);
                    }

                    @Override
                    public void onNext(HadShopcpEntity sendSmsCode) {
                        callback.OnSuccess(sendSmsCode);
                    }
                });
        callback.AddSubscription(subscriber);
    }

    //获取我购买的打折商品信息
    public void getMyshopcpGood(String cp_id, final IModelResult<HadShopcpEntity.ListsBean> callback) {
        String token = SPUtils.getString("token", "");
        Subscription subscriber = httpService.getMyshopcpGood(version, client, token, cp_id)
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .compose(new CommonTransformer<HadShopcpEntity.ListsBean>())
                .subscribe(new CommonSubscriber<HadShopcpEntity.ListsBean>(viewload) {
                    @Override
                    protected void onError(ApiException e) {
                        callback.OnError(e);
                    }

                    @Override
                    public void onNext(HadShopcpEntity.ListsBean sendSmsCode) {
                        callback.OnSuccess(sendSmsCode);
                    }
                });
        callback.AddSubscription(subscriber);
    }

    //参与店铺抽奖活动的用户列表
    public void getUserShopPtList(String pt_id,int newpage, final IModelResult<HadShopptEntity> callback) {
        Subscription subscriber = httpService.getUserShopPtList(version, client,newpage,pt_id)
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .compose(new CommonTransformer<HadShopptEntity>())
                .subscribe(new CommonSubscriber<HadShopptEntity>(viewload) {
                    @Override
                    protected void onError(ApiException e) {
                        callback.OnError(e);
                    }

                    @Override
                    public void onNext(HadShopptEntity sendSmsCode) {
                        callback.OnSuccess(sendSmsCode);
                    }
                });
        callback.AddSubscription(subscriber);
    }


    //参与店铺活动中奖的用户列表
    public void getWUserShopPtList(String pt_id,int pageno, final IModelResult<HadShopprEntity> callback) {
        Subscription subscriber = httpService.getWUserShopPtList(version, client,pageno, pt_id)
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .compose(new CommonTransformer<HadShopprEntity>())
                .subscribe(new CommonSubscriber<HadShopprEntity>(viewload) {
                    @Override
                    protected void onError(ApiException e) {
                        callback.OnError(e);
                    }

                    @Override
                    public void onNext(HadShopprEntity sendSmsCode) {
                        callback.OnSuccess(sendSmsCode);
                    }
                });
        callback.AddSubscription(subscriber);
    }


    //店铺抽奖中奖的用户列表
    public void getUserShopprList(String pr_id,int page_no, final IModelResult<HadShopprEntity> callback) {
        Subscription subscriber = httpService.getUserShopprList(version, client,page_no, pr_id)
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .compose(new CommonTransformer<HadShopprEntity>())
                .subscribe(new CommonSubscriber<HadShopprEntity>(viewload) {
                    @Override
                    protected void onError(ApiException e) {
                        callback.OnError(e);
                    }

                    @Override
                    public void onNext(HadShopprEntity sendSmsCode) {
                        callback.OnSuccess(sendSmsCode);
                    }
                });
        callback.AddSubscription(subscriber);
    }

    //修改店铺图片
    public void updateShopShopImgs(String[] file,final IModelResult<EmptyEntity> callback) {
        String user_id = SPUtils.getString("user_id", "");
        String token = SPUtils.getString("token", "");
        String imgs  = Arrays.toString(file);
        imgs = imgs.replace("[","[\"");
        imgs = imgs.replace("]","\"]");
        imgs = imgs.replace(",","\",\"");
        Subscription subscriber = httpService.updateShopImg(version, client, user_id,imgs )
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .compose(new CommonTransformer<EmptyEntity>())
                .subscribe(new CommonSubscriber<EmptyEntity>(viewload) {
                    @Override
                    protected void onError(ApiException e) {
                        callback.OnError(e);
                    }

                    @Override
                    public void onNext(EmptyEntity sendSmsCode) {
                        callback.OnSuccess(sendSmsCode);
                    }
                });
        callback.AddSubscription(subscriber);
    }

    //获取店铺图片
    public void getShopImgs(final IModelResult<ShopImgEntity> callback) {
        String user_id = SPUtils.getString("user_id", "");
        String token = SPUtils.getString("token", "");
        Subscription subscriber = httpService.getShopImgs(version, client, user_id)
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .compose(new CommonTransformer<ShopImgEntity>())
                .subscribe(new CommonSubscriber<ShopImgEntity>(viewload) {
                    @Override
                    protected void onError(ApiException e) {
                        callback.OnError(e);
                    }

                    @Override
                    public void onNext(ShopImgEntity sendSmsCode) {
                        callback.OnSuccess(sendSmsCode);
                    }
                });
        callback.AddSubscription(subscriber);
    }


    //获取店铺信息
    public void getShopInfo(final IModelResult<ShopInfoEntity> callback) {
        String user_id = SPUtils.getString("user_id", "");
        String token = SPUtils.getString("token", "");
        Subscription subscriber = httpService.getInfo(version, client, user_id)
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .compose(new CommonTransformer<ShopInfoEntity>())
                .subscribe(new CommonSubscriber<ShopInfoEntity>(viewload) {
                    @Override
                    protected void onError(ApiException e) {
                        Log.d("text",e+"");
                    }

                    @Override
                    public void onNext(ShopInfoEntity sendSmsCode) {
                        callback.OnSuccess(sendSmsCode);
                    }
                });
        callback.AddSubscription(subscriber);
    }


    //修改店铺营业状态
    public void updateInfoForstatus(int states,final IModelResult<EmptyEntity> callback) {
        String user_id = SPUtils.getString("user_id", "");
        String token = SPUtils.getString("token", "");
        Subscription subscriber = httpService.updateInfoForstatus(version, client, user_id,states)
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .compose(new CommonTransformer<EmptyEntity>())
                .subscribe(new CommonSubscriber<EmptyEntity>(viewload) {
                    @Override
                    protected void onError(ApiException e) {
                        callback.OnError(e);
                    }

                    @Override
                    public void onNext(EmptyEntity sendSmsCode) {
                        callback.OnSuccess(sendSmsCode);
                    }
                });
        callback.AddSubscription(subscriber);
    }

    //修改店铺经纬度
    public void updateInfoForaddress(String  lat,String lng,final IModelResult<EmptyEntity> callback) {
        String user_id = SPUtils.getString("user_id", "");
        String token = SPUtils.getString("token", "");
        Subscription subscriber = httpService.updateInfoForaddress(version, client, user_id,lat,lng)
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .compose(new CommonTransformer<EmptyEntity>())
                .subscribe(new CommonSubscriber<EmptyEntity>(viewload) {
                    @Override
                    protected void onError(ApiException e) {
                        callback.OnError(e);
                    }

                    @Override
                    public void onNext(EmptyEntity sendSmsCode) {
                        callback.OnSuccess(sendSmsCode);
                    }
                });
        callback.AddSubscription(subscriber);
    }



    //获取关注店铺
    public void getFocusShopList(final IModelResult<List<ShopInfoEntity>> callback) {
        String user_id = SPUtils.getString("user_id", "");
        String token = SPUtils.getString("token", "");
        Subscription subscriber = httpService.getFocusShopList(version, client, user_id)
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .compose(new CommonTransformer<List<ShopInfoEntity>>())
                .subscribe(new CommonSubscriber<List<ShopInfoEntity>>(viewload) {
                    @Override
                    protected void onError(ApiException e) {
                        callback.OnError(e);
                    }

                    @Override
                    public void onNext(List<ShopInfoEntity> sendSmsCode) {
                        callback.OnSuccess(sendSmsCode);
                    }
                });
        callback.AddSubscription(subscriber);
    }


    //获取附近店铺
    public void getNearbyShopList(String lng ,String lat, int far,String type,final IModelResult<List<ShopInfoEntity>> callback) {
        String user_id = SPUtils.getString("user_id", "");
        String token = SPUtils.getString("token", "");
        Subscription subscriber = httpService.getNearbyShopList(version, client, lng,lat,far,type)
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .compose(new CommonTransformer<List<ShopInfoEntity>>())
                .subscribe(new CommonSubscriber<List<ShopInfoEntity>>(viewload) {
                    @Override
                    protected void onError(ApiException e) {
                        callback.OnError(e);
                    }

                    @Override
                    public void onNext(List<ShopInfoEntity> sendSmsCode) {
                        callback.OnSuccess(sendSmsCode);
                    }
                });
        callback.AddSubscription(subscriber);
    }


    //获取附近的秒爆商品店铺
    public void getNearbyMbList(String lng ,String lat, int far,String type,final IModelResult<List<ShopInfoEntity>> callback) {
        String user_id = SPUtils.getString("user_id", "");
        String token = SPUtils.getString("token", "");
        Subscription subscriber = httpService.getNearbyMbList(version, client, lng,lat,far,type)
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .compose(new CommonTransformer<List<ShopInfoEntity>>())
                .subscribe(new CommonSubscriber<List<ShopInfoEntity>>(viewload) {
                    @Override
                    protected void onError(ApiException e) {
                        callback.OnError(e);
                    }

                    @Override
                    public void onNext(List<ShopInfoEntity> sendSmsCode) {
                        callback.OnSuccess(sendSmsCode);
                    }
                });
        callback.AddSubscription(subscriber);
    }


    //获取附近的打折商品店铺
    public void getNearbyCpList(String lng ,String lat, int far,String type,final IModelResult<List<ShopInfoEntity>> callback) {
        String user_id = SPUtils.getString("user_id", "");
        String token = SPUtils.getString("token", "");
        Subscription subscriber = httpService.getNearbyCpList(version, client, lng,lat,far,type)
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .compose(new CommonTransformer<List<ShopInfoEntity>>())
                .subscribe(new CommonSubscriber<List<ShopInfoEntity>>(viewload) {
                    @Override
                    protected void onError(ApiException e) {
                        callback.OnError(e);
                    }

                    @Override
                    public void onNext(List<ShopInfoEntity> sendSmsCode) {
                        callback.OnSuccess(sendSmsCode);
                    }
                });
        callback.AddSubscription(subscriber);
    }

    //获取附近的抽奖活动店铺
    public void getNearbyPtList(String lng ,String lat, int far,String type,final IModelResult<List<ShopInfoEntity>> callback) {
        String user_id = SPUtils.getString("user_id", "");
        String token = SPUtils.getString("token", "");
        Subscription subscriber = httpService.getNearbyPtList(version, client, lng,lat,far,type)
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .compose(new CommonTransformer<List<ShopInfoEntity>>())
                .subscribe(new CommonSubscriber<List<ShopInfoEntity>>(viewload) {
                    @Override
                    protected void onError(ApiException e) {
                        callback.OnError(e);
                    }

                    @Override
                    public void onNext(List<ShopInfoEntity> sendSmsCode) {
                        callback.OnSuccess(sendSmsCode);
                    }
                });
        callback.AddSubscription(subscriber);
    }


























}
