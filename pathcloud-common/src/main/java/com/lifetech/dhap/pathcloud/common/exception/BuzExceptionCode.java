package com.lifetech.dhap.pathcloud.common.exception;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by gdw on 4/18/16.
 */
public class BuzExceptionCode {
    public static final Integer AccessDenied = 1;
    public static final Integer ErrorParam = 2;
    public static final Integer WrongEmail = 3;
    public static final Integer UsernameOrPasswordError = 4;
    public static final Integer UserNotExists = 5;
    public static final Integer UsernameExists = 6;
    public static final Integer RoleNotExists = 7;
    public static final Integer WrongPassword = 8;
    public static final Integer RoleNameExists = 9;
    public static final Integer PasswordSame = 10;
    public static final Integer RoleCannotOperate= 11;
    public static final Integer UserLock = 12;
    public static final Integer UserForbidden = 13;
    public static final Integer ApplicationNotExists = 14;
    public static final Integer ApplicationStatusChange = 15;
    public static final Integer TemplateNameExists = 16;
    public static final Integer TemplateNameNotExists = 17;
    public static final Integer LabelCannotDelete = 18;
    public static final Integer DehydratorInUse = 19;
    public static final Integer DehydratorNotExist = 20;
    public static final Integer BasketLocked = 21;
    public static final Integer DataError = 22;
    public static final Integer WrongPhoneNumber = 23;
    public static final Integer RecordNotExists = 24;
    public static final Integer StatusNotMatch = 25;
    public static final Integer RecordExists = 26;
    public static final Integer WatchedCountNotMatch = 27;
    public static final Integer DepartmentCategoryExists = 28;
    public static final Integer DepartmentNameExists = 29;
    public static final Integer NoFile = 30;
    public static final Integer EmbedPause = 31;
    public static final Integer ReportSign = 32;
    public static final Integer MustTracking = 33;
    public static final Integer MustTrackingSection = 34;
    public static final Integer MustTrackingDiagnose = 35;
    public static final Integer PathologyNotExists = 36;
    public static final Integer SettingError = 37;
    public static final Integer UsingFrozen = 38;
    public static final Integer CollectNotExists = 39;
    public static final Integer CollectExists = 40;
    public static final Integer InspectHospitalExist = 41;
    public static final Integer CanNotApply = 42;
    public static final Integer MarkerRepetition = 43;
    public static final Integer CanNotOperate = 44;

    private final static Map<Integer, String> errorMap = new HashMap<>();

    static {
        errorMap.put(AccessDenied, "权限不足");
        errorMap.put(ErrorParam, "参数错误");
        errorMap.put(WrongEmail, "邮箱格式错误");
        errorMap.put(UsernameOrPasswordError, "用户名或密码错误");
        errorMap.put(UserNotExists, "用户不存在");
        errorMap.put(UsernameExists, "用户名已存在");
        errorMap.put(RoleNotExists, "角色不存在");
        errorMap.put(WrongPassword, "旧密码验证有误");
        errorMap.put(RoleNameExists, "角色名已存在");
        errorMap.put(PasswordSame, "新旧密码相同");
        errorMap.put(RoleCannotOperate, "此角色不能操作");
        errorMap.put(UserLock, "用户被锁");
        errorMap.put(UserForbidden, "用户被禁用");
        errorMap.put(ApplicationNotExists, "此病理申请不存在");
        errorMap.put(ApplicationStatusChange, "此病理申请非未登记状态");
        errorMap.put(TemplateNameExists, "模板名已存在");
        errorMap.put(TemplateNameNotExists, "模板不存在");
        errorMap.put(LabelCannotDelete, "此标签不能删除");
        errorMap.put(DehydratorInUse, "设备正在使用");
        errorMap.put(DehydratorNotExist, "设备不存在");
        errorMap.put(BasketLocked, "脱水篮已被使用");
        errorMap.put(DataError, "数据错误");
        errorMap.put(WrongPhoneNumber, "电话号码格式有误");
        errorMap.put(RecordNotExists, "记录不存在");
        errorMap.put(StatusNotMatch, "状态不匹配，不能进行此操作");
        errorMap.put(RecordExists, "记录已存在");
        errorMap.put(WatchedCountNotMatch, "玻片未全部看完");
        errorMap.put(DepartmentCategoryExists, "科室类别已存在");
        errorMap.put(DepartmentNameExists, "科室名称已存在");
        errorMap.put(NoFile, "没有上传图片");
        errorMap.put(EmbedPause, "包埋暂停,不能包埋");
        errorMap.put(ReportSign, "报告已签收，不能打印签收单");
        errorMap.put(MustTracking, "此工作站必须追踪");
        errorMap.put(MustTrackingSection, "切片工作站必须追踪");
        errorMap.put(MustTrackingDiagnose, "诊断工作站必须追踪");
        errorMap.put(PathologyNotExists, "此病理不存在");
        errorMap.put(SettingError, "请先配置病理号生成规则");
        errorMap.put(UsingFrozen, "请在系统配置使用冰冻取材工作站");
        errorMap.put(CollectNotExists, "此收藏不存在");
        errorMap.put(CollectExists, "此收藏已存在");
        errorMap.put(InspectHospitalExist, "医院已存在");
        errorMap.put(CanNotApply, "相关申请已发报告,不能再申请");
        errorMap.put(MarkerRepetition, "标记物重复");
        errorMap.put(CanNotOperate, "不能进行此操作");
    }

    public static String getReasonByCode(Integer code, String reason){
        if(errorMap.containsKey(code)){
            return errorMap.get(code);
        }else{
            return reason;
        }
    }

    public static String getReasonByCode(Integer code){
        if(errorMap.containsKey(code)){
            return errorMap.get(code);
        }
        return "未知错误";
    }
}
