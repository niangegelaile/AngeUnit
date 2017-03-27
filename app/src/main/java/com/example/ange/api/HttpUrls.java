package com.example.ange.api;

/**
 * Created by Administrator on 2017/1/4 0004.
 */

public interface HttpUrls {
    //2.1 登录
    String LOGIN="api/user/userLogin.do";
    //2.3 获取用户信息
    String USER_INFO="api/user/getUserInfoById.do";
    //2.4 修改用户密码
    String UPDATE_PASSWORD="api/user/updatePassword.do";
    String GetSubDepartment="api/user/getSubDepartment.do";
    //3.3 app版本
    String APP_VERSION="api/appVersion/selectAppVersion.do";
    //4.2 服务预约列表
    String SERVICE_LIST="api/service/selectServiceAppointment.do";
    //4.1 服务预约统计
    String SERVICE_STATISTICS="api/service/serviceAppointmentStatistics.do";
    //4.3 服务预约详情
    String SERVICE_DETAIL ="api/service/selectServiceAppointmentById.do";
    //4.5
    String SERVICE_insert="api/service/insertServiceAppointment.do";
    //4.6 服务预约收费
    String SERVICE_charge="api/service/chargeServiceAppointment.do";
    //4.7 服务预约开始服务
    String SERVICE_start="api/service/startServiceAppointment.do";
    //4.8 服务预约结束服务
    String SERVICE_end="api/service/endServiceAppointment.do";
    //5.1 活动管理(本月累计)
    String ACTIVITY_selectMonthTotalByOfficeId="api/activity/selectMonthTotalByOfficeId.do";
    //5.2 活动管理(显示活动项数据)
    String ACTIVITY_selectActivityByOffId="api/activity/selectActivityByOffId.do";
    //5.3 活动管理(点击进入活动报名详情页)
    String ACTIVITY_findActivitySignupList="api/activity/findActivitySignupList.do";
    //5.4 活动管理(状态变更,报名状态,收费状态,签到状态,实际签到人数)
    String ACTIVITY_activitySignupUpdate="api/activity/activitySignupUpdate.do";
    //5.5 活动管理(点击活动报名记录，查看报名记录详情)
    String ACTIVITY_selectActivitySignupById="api/activity/selectActivitySignupById.do";
    //5.6 活动管理(已结束活动统计)
    String ACTIVITY_selectEndActivityTotal="api/activity/selectEndActivityTotal.do";
    //6.1 会员信息
    String MEMBER_selectMember="api/member/selectMember.do";
    //6.2 会员信息详情
    String MEMBER_selectMemberByCarId="api/member/selectMemberByCarId.do";

}
