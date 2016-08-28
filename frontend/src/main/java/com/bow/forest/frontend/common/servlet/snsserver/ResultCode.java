/*
c * 版权: Copyright 2006-2009 Huawei Tech. Co., Ltd. All Rights Reserved.
 * 描述: 响应码
 * 修改: 李春建
 * 操作时间: 2009-1-15
 * 操作: 添加
 */
package com.bow.forest.frontend.common.servlet.snsserver;

/**
 * 
 * @author wKF27989
 * 
 */
public interface ResultCode
{

    /**
     * 成功
     */
     int SUCCESS = 200;
    
     int COMMON_MOBILE_IS_NULL = -250;
    
     int COMMON_MSISDN_IS_ILLEGAL = -251;
    
     int CONTENT_ID_IS_NULL = -252;
    
     int CONTENT_ID_IS_ILLEGAL = -253;
    
     int CONTENT_TYPE_IS_NULL = -254;
    
     int CONTENT_TYPE_IS_ILLEGAL = -255;
    
     int COMMENT_IS_NULL = -256;
    
     int COMMENT_IS_TOO_LONG = -257;
    
     int COMMENT_IS_SENSITIVE = -258;
    
     int PUBLISHTYPE_IS_ILLEGAL = -259;
    
     int BLACK_USER_CAN_NOT_ADD_CONTENT_REVIEW = -260;
    
     int REVIEWID_IS_NULL = -260;
    
     int REVIEWID_IS_ILLEGAL = -261;
    
     int ACTION_IS_FREQUENT = -263;
    
     int REVIEW_IS_NOT_EXISTING = -264;
     
    /**
     * 删除日志失败 , 抓取任务中删除任务失败
     */
     int MBLOG_ID_DELETE_FAIL = 20301;

    /**
     * 服务异常 范围2000-2999
     */
    /* ******服务异常***start************* */

    /** 其他错误 */
     int OTHER_SERVER_ERROR = 2000;

    /** 非法的servlet请求 */
     int ERROR_REQUEST_INTERFACE = 2001;

    /** 数据逻辑异常 */
     int DATA_LOGIC_ERROR = 2002;

    /** * ftp 实例创建失败 */
     int FTP_CONSTRUCT_FAILD = 2003;

    /** 门户参数请求参数为空 */
     int PARAMETER_NULL = 2004;

    /** 门户参数不存在 */
     int PARAMETER_VALUE_NULL = 2005;

    /* ******服务异常***end************* */

    /* ******参数错误***start************* */
    /** * 手机号为空 */
     int MOBILE_IS_NULL = 4000;

    /** * 手机号非法 */
     int MOBILE_SHOULD_INTEGER = 4001;

    /** ** 手机号长度错误，非11位 */
     int MOBILE_LENGTH_ERROR = 4002;

    /** ** 分页编号为空 */
     int PAGE_NUM_IS_NULL = 4003;

    /** **分页编号为非数字 */
     int PAGE_NUM_SHOULD_INTEGER = 4004;

    /** **分页长度为空 */
     int PAGE_LEN_IS_NULL = 4005;

    /** **分页长度为非数字 */
     int PAGE_LEN_SHOULD_INTEGER = 4006;
    
    /** ** 每页显示条数为空 */
     int PAGE_COUNT_IS_NULL = 4005;

    /** **每页显示条数非数字 */
     int PAGE_COUNT_SHOULD_INTEGER = 4006;
   
    /** **门户类型为空 */
     int PORTALTYPE_IS_NULL = 4007;

    /** **门户类型为非数字 */
     int PORTALTYPE_SHOULD_INTEGER = 4008;

    /** ************ live start **************** */
    /** ** 直播间信息不存在 ** */
     int LIVE_LIVEROOM_IS_NULL = 3001;

    /** ** 结束时间大于开始时间 ** */
     int LIVE_BEGINTIME_GT_ENDTIME = 3002;

    /** ** 字符串格式错误 ** */
     int LIVE_PARAM_FORMAT_ERROR = 3003;

    /** ** sessionID格式错误 ** */
     int LIVE_SESSION_FORMAT_ERROR = 3004;

     int LIVE_IS_PREDICT = 3005;

    /** ** 直播间ID为空 */
     int LIVE_ROOMID_IS_NULL = 4009;

    /** ** 直播间ID为非数字 */
     int LIVE_ROOMID_NOT_NUMBER = 4010;

     int LIVE_FLOORS_NOT_NUMBER = 4146;

     int LIVE_FLOORS_IS_NULL = 4147;

    /** ** 留言间隔时间少于直播间间隔时间 */
     int LIVE_MESSAGE_LESS_INTERVALTIME = 4148;

    /** ** 留言时间为空 */
     int LIVE_MESSAGETIME_IS_NULL = 4011;

    /** ** 留言时间格式错误 */
     int LIVE_MESSAGETIME_FORMAT_ERROE = 4012;

    /** ** 留言内容为空 */
     int LIVE_MESSAGECONTENT_IS_NULL = 4013;

    /** ** 好友手机号码为空 */
     int LIVE_FRIENDMOBILE_IS_NULL = 4015;

    /** ** 好友手机号为非数字 */
     int LIVE_FRIENDMOBILE_NOT_NUMBER = 4016;

    /** ** 好友手机号长度错误 */
     int LIVE_FRIENDMOBILE_LENGTH_ERROR = 4017;

    /** ** 发送url为空 */
     int LIVE_SNEDURL_IS_NULL = 4018;

    /** ** 发送内容为空 */
     int LIVE_SENDMESSAGECONTENT_IS_NULL = 4019;

    /** ** 主持人ID为非数字 */
     int LIVE_EMCEEIDLIST_NOT_NUMBER = 4020;

    /** ** 直播状态为非数字 */
     int LIVE_ROOMSTATE_NOT_NUMBER = 4021;

    /** ** 开始时间格式错误 */
     int LIVE_STARTTIME_FORMAT_ERROR = 4022;

    /** ** 结束时间格式错误 */
     int LIVE_ENDTIME_FORMAT_ERROR = 4023;

    /** ** sessionId为空 */
     int LIVE_SESSIONID_IS_NULL = 4024;

    /** ** sessionID长度不足32位 */
     int LIVE_SESSION_LENGTH_ERROR = 4070;

    /** ** 创建时间为空 */
     int LIVE_CREATETIME_IS_NULL = 4025;

    /** ** 创建时间格式错误 */
     int LIVE_CREATETIME_FORMAT_ERROE = 4026;

    /** ** 排序为空 */
     int LIVE_ORDER_IS_NULL = 4014;

    /** ** 排序为非数字 */
     int LIVE_ORDER_NOT_NUMBER = 4027;

    /** ** 话题状态为空 */
     int LIVETOPIC_STATUS_IS_NULL = 4015;

    /** ** 话题状态为非数字 */
     int LIVETOPIC_STATUS_NOT_NUMBER = 4029;

    /** ** 主持人ID为空 */
     int LIVE_EMCEEIDLIST_IS_NULL = 4028;

    /** * 用户调查主题ID为空 */
     int LIVE_TOPIC_ID_IS_NULL = 4203;

    /** *用户调查主题ID应为整型 */
     int LIVE_TOPIC_ID_SHOULD_INTEGER = 4204;

    /** * 无此话题信息 */
     int LIVE_NO_TOPIC_INFO = 4205;

    /** * 无此留言信息 */
     int LIVE_NO_MESSAGE_INFO = 4208;

    /** * 留言ID为空 */
     int LIVE_MESSAGE_ID_IS_NULL = 4206;

    /** *留言ID应为整型 */
     int LIVE_MESSAGE_ID_SHOULD_INTEGER = 4207;

    /** ** 分页编号为空 */
    /** *********** live end ******************* */

    /* *************互帮互助 start************* */
    // add by dengkui at 2011.05.10 due to
    // 添加typeId ISNull 响应码
    // 添加typeId notNumber 响应码

    /** * 属于我的问题 */
     int THIS_IS_MYQUESTION = 3202;

    /** * 不属于我的问题 */
     int THIS_IS_NOT_MYQUESTION = 3203;

    /** ** 互帮互助分类维护类型typeID为空 */
     int HELP_TYPEIDLIST_IS_NULL = 4200;

    /** ** 互帮互助分类维护类型typeID为非数字 */
     int HELP_TYPEIDLIST_NOT_NUMBER = 4201;

    /** ** 互帮互助分类维护类型LABEL为非字母 */
     int HELP_TYPEIDLIST_NOT_LABEL = 4203;

    /** **互帮互助分类维护 排序order为空 */
     int HELP_ORDER_IS_NULL = 4202;

    /** **互帮互助分类维护 排序order排序为非数字 */
     int HELP_ORDER_NOT_NUMBER = 4203;

    /** ** 问题状态应为正整型 */
     int HELP_QUESTION_STATUS_ERROR = 4053;

    /** ** 分类编码应为整型 */
     int HELP_TYPCECODE_SHOULD_INTEGER = 4054;

    /** ** 问题ID为空 */
     int HELP_QUESTIONID_IS_NULL = 4055;

    /** **问题ID应为整型 */
     int HELP_QUESTIONID_SHOULD_INTEGER = 4056;

    /** **无此问题信息 */
     int HELP_NO_QUESTION_INFO = 3400;

    /** ** 此问题无最佳答案信息 */
     int HELP_NO_BEST_ANSWER_INFO = 3401;

    /** **问题标题为空 */
     int HELP_QUESTION_TITLE_IS_NULL = 4057;

    /** ***问题内容为空 */
     int HELP_QUESTION_CONTENT_IS_NULL = 4058;

    /** ***提问时间为空 */
     int HELP_QUESTION_CREATETIME_IS_NULL = 4059;

    /** **提问时间格式不正确 */
     int HELP_QUESTION_CREATETIME_FORMAT_ERROR = 4060;

    /** **提问失败 */
     int HELP_ADD_QUESTION_FAIL = 3406;

    /** *回答内容为空 */
     int HELP_ANSWER_CONTENT_IS_NULL = 4061;

    /** *获取回答总数失败 */
     int HELP_ANSWER_COUNT_FAILED = 4156;

    /** *获取问题浏览数失败 */
     int HELP_QUESTION_BROWSES_FAILED = 4157;

    /** *更新问题浏览数失败 */
     int HELP_QUESTION_UPDATEBROWSES_FAILED = 4158;

    /** *浏览数为空 */
     int HELP_QUESTION_BROWSES_ISNULL = 4159;

    /** **回答时间为空 */
     int HELP_ANSWER_CREATETIME_IS_NULL = 4062;

    /** **回答时间格式不正确 */
     int HELP_ANSWER_CREATETIME_FORMAT_ERROR = 4063;

    /*** 无答案详情 */
     int HELP_NO_ANSWER_INFO = 3402;

    /** **回答失败 */
     int HELP_ANSWER_FAIL = 3404;

    /** **更新updateTime失败 */
     int HELP_HELPQUESTION_FAIL = 3406;

    /*** 无答案详情 */
     int HELP_NO_OFFICIAL_ANSWER_INFO = 3405;

    /** **答案ID为空 */
     int HELP_ANSWER_ID_IS_NULL = 4064;

    /** **答案ID应为整型 */
     int HELP_ANSWER_ID_SHOULD_INTEGER = 4065;

    /** **结贴时间为空 */
     int HELP_SOLVEQUESTION_TIME_IS_NULL = 4066;

    /** **结贴时间格式不正确 */
     int HELP_SOLVEQUESTION_TIME_FORMAT_ERROR = 4067;

     int HELP_MOBILE_IS_NULL = 4068;

    /** **结贴失败 */
     int HELP_SOLVEQUESTION_ERROR = 3405;

    /** **追加提问失败 */
     int HELP_ADDTIONQUESTION_ERROR = 3406;

    /* *************互帮互助 end************* */

    /* *************用户调查 start************* */

    /** *用户调查主题期次类型应为整型 */
     int SURVEY_TOPIC_ISSUE_SHOULD_INTEGER = 4029;

    /** **用户调查主题状态应为整型 */
     int SURVEY_TOPIC_STATE_SHOULD_INTEGER = 4030;

    /** * 用户调查开始时间格式不正确 */
     int SURVEY_TOPIC_STARTTIME_FORMAT_ERROR = 4031;

    /** * 用户调查结束时间格式不正确 */
     int SURVEY_TOPIC_ENDTIME_FORMAT_ERROR = 4032;

    /** * 用户调查主题ID为空 */
     int SURVEY_TOPIC_ID_IS_NULL = 4033;

    /** *用户调查主题ID应为整型 */
     int SURVEY_TOPIC_ID_SHOULD_INTEGER = 4034;

    /** * 无当前用户调查主题信息 */
     int SURVEY_NO_TOPIC_INFO = 3200;

    /** * 用户调查题目ID为空 */
     int SURVEY_SUBJECTID_IS_NULL = 4035;

    /** * 用户调查题目ID应为整型 */
     int SURVEY_SUBJECTID_SHOULD_INTEGER = 4036;

    /** * 无当前用户调查题目信息 */
     int SURVEY_NO_SUBJECT_INFO = 3201;

    /** * 已参与此次用户调查 */
     int SURVEY_IS_JOINED = 3202;

    /** * 未参与此次用户调查 */
     int SURVEY_NO_JOIN = 3203;

    /** * 用户调查回答列表为空 */
     int SURVEY_ANSWERLIST_IS_NULL = 4037;

    /** * 用户调查回答列表格式输入不正确 */
     int SURVEY_ANSWERLIST_FORMAT_ERROR = 4038;

    /** ** 记录答案选项 */
     int ADD_SURVEY_ANSWER_FAIL = 3205;

    /** * 是否完成调查标识为空 */
     int SURVEY_ISCOMPLETE_IS_NULL = 4039;

    /** * 是否完成调查状态应为整数 */
     int SURVEY_ISCOMPLETE_SHOULD_INTEGER = 4040;

    /** * 参与调查时间为空 */
     int SURVEY_JOINTIME_IS_NULL = 4041;

    /** * 参与调查时间格式不正确 */
     int SURVEY_JOINTIME_FORMAT_ERROR = 4042;

    /** * 此次主题调查无参与者记录 */
     int SURVEY_NO_JOINER_INFO = 3204;

    /* *************用户调查 end************* */

    /* *************有奖问答 start************* */
    /** *有奖问答主题期次类型应为整型 */
     int QANSWER_TOPIC_ISSUE_SHOULD_INTEGER = 4041;

    /** **有奖问答主题状态应为整型 */
     int QANSWER_TOPIC_STATE_SHOULD_INTEGER = 4042;

    /** * 有奖问答开始时间格式不正确 */
     int QANSWER_TOPIC_STARTTIME_FORMAT_ERROR = 4043;

    /** * 有奖问答结束时间格式不正确 */
     int QANSWER_TOPIC_ENDTIME_FORMAT_ERROR = 4044;

    /** *有奖问答主题ID为空 */
     int QANSWER_TOPIC_ID_IS_NULL = 4045;

    /** ** 有奖问答主题ID应为整型 */
     int QANSWER_TOPIC_ID_SHOULD_INTEGER = 4046;

    /** *无当前有奖问答主题信息 */
     int QANSWER_NO_TOPIC_INFO = 3300;

    /** *有奖问答题目ID为空 */
     int QANSWER_SUBJECT_ID_IS_NULL = 4047;

    /** **有奖问答题目ID应为整型 */
     int QANSWER_SUBJECT_ID_SHOULD_INTEGER = 4048;

    /** *无当前有奖问答题目信息 */
     int QANSWER_NO_SUBJECT_INFO = 3301;

    /** **已参与此次有奖问答 */
     int QANSWER_IS_JOINED = 3302;

    /** **未参与此次有奖问答 */
     int QANSWER_NO_JOIN = 3303;

    /** **未答过此题 */
     int QANSWER_NO_JOIN_SUBJECT = 3304;

    /** **已答过此题 */
     int QANSWER_IS_JOINED_SUBJECT = 3305;

    /** * 有奖问答回答列表为空 */
     int QANSWER_ANSWER_LIST_IS_NULL = 4049;

    /** **有奖问答回答列表格式输入不正确 */
     int QANSWER_ANSWER_LIST_FORMAT_ERROR = 4050;

    /** *参与有奖问答时间为空 */
     int QANSWER_JOINTIME_IS_NULL = 4051;

    /** *参与有奖问答时间格式不正确 */
     int QANSWER_JOINTIME_FORMAT_ERROR = 4052;

    /** *是否最后一题为空 */
     int QANSWER_ISLASTRESULT_IS_NULL = 4068;

    /** *是否最后一题为非数字 */
     int QANSWER_ISLASTRESULT_SHOULD_NUMBER = 4069;

    /* *************有奖问答 end************* */

    /* *************书友会 start************* */
    /** *书友会板块ID为空 */
     int BOOKCLUB_FORUM_ID_IS_NULL = 4080;

    /** 书友会板块ID应为整型 */
     int BOOKCLUB_FORUM_ID_SHOULD_INTEGER = 4081;

    /** 书友会名称为空 */
     int BOOKCLUB_FORUM_NAME_IS_NULL = 4082;

    /** 书友会允许加入类型为空 */
     int BOOKCLUB_FORUM_ALLOWJOIN_IS_NULL = 4083;

    /** 书友会允许加入类型应为整型 */
     int BOOKCLUB_FORUM_ALLOWJOIN_SHOULD_INTEGER = 4084;

    /** ** 创建时间为空 */
     int BOOKCLUB_THREAD_CREATETIME_IS_NULL = 4085;

    /** ** 创建时间格式错误 */
     int BOOKCLUB_THREAD_CREATETIME_ERROR = 4086;

    /** 书友会板块信息为空 */
     int BOOKCLUB_FORUM_IS_NULL = 4087;

    /** 书友会 申请建立书友会 书友会已存在 */
     int BOOKCLUB_FORUM_APPLYFORUM_NAMEREPEAT = 4088;

    /** 用户信息为空 */
     int USER_MEMBER_IS_NULL = 4089;

    /** 申请创建书友会失败 */
     int ADD_BOOKCLUB_FORUM_FAIL = 3500;

    /** *书友会帖子ID为空 */
     int BOOKCLUB_THREADID_IS_NULL = 4090;

    /** 书友会帖子ID应为整型 **/
     int BOOKCLUB_THREADID_SHOULD_NUMBER = 4091;

    /** 书友会主题题目为空 **/
     int BOOKCLUB_THREAD_TITLE_IS_NULL = 4092;

    /** 发表主题内容为空 **/
     int BOOKCLUB_THREAD_CONTENT_IS_NULL = 4093;

    /** 回复内容为空 */
     int BOOKCLUB_POST_CONTENT_IS_NULL = 4094;

    /** 添加书友会成员失败 **/
     int APPLY_JOIN_BOOKCLUB_FAIL = 3501;

    /** 书友会的用户ID为空 **/
     int BOOKCLUB_MEMBER_CLUBMEMBERID_IS_NULL = 4095;

    /** 书友会的用户ID应为整型 **/
     int BOOKCLUB_MEMBER_CLUBMEMBERID_SHOULD_INTEGER = 4096;

    /** 书友会的审核状态为空 **/
     int BOOKCLUB_MEMBER_AUDITSTATUS_IS_NULL = 4097;

    /** 书友会的审核状态应为整型 **/
     int BOOKCLUB_MEMBER_AUDITSTATUS_SHOULD_INTEGER = 4098;

    /** 书友会的帖子状态为空 **/
     int BOOKCLUB_THREAD_AUDITSTATUS_IS_NULL = 4099;

    /** 书友会的帖子应为整型 **/
     int BOOKCLUB_THREAD_AUDITSTATUS_SHOULD_INTEGER = 4100;

    /** 书友会成员为空 **/
     int BOOKCLUB_MEMBER_IS_NULL = 4101;

    /** 审核加入书友会失败 **/
     int AUDIT_JOIN_BOOKCLUB_FAIL = 3502;

    /** 书友会回帖时间为空 **/
     int BOOKCLUB_POST_CREATETIME_IS_NULL = 4102;

    /** 书友会创建时间为空 **/
     int BOOKCLUB_FORUM_CREATETIME_IS_NULL = 4103;

    /** 书友会回帖时间错误 **/
     int BOOKCLUB_POST_CREATETIME_FORMAT_ERROR = 4104;

    /** 书友会创建时间格式错误 **/
     int BOOKCLUB_FORUM_CREATETIME_FORMAT_ERROR = 4105;

    /** 保存书友会版主失败 **/
     int ADD_BOOKCLUB_MODERATOR_FAIL = 3503;

    /** 已申请过书友会版主 **/
     int ALREADY_APPLY_MODERATOR = 3504;

    /** 添加书友会节点失败 **/
     int ADD_BOOKCLUB_FORUM_LAYER_FAIL = 3505;

    /** 书友会会员ID为空 **/
     int BOOKCLUB_MEMBER_CLUBMEMBERIDSTR_IS_NULL = 4106;

    /** 书友会会员ID列表错误 **/
     int BOOKCLUB_MEMBER_CLUBMEMBERIDSTR_ERROR = 4107;

    /** 书友会会员申请时间为空 **/
     int ADD_BOOKCLUB_FORUM_APPLYTIME_IS_NULL = 4108;

    /** 书友会版主申请时间为空 **/
     int BOOKCLUB_MODERATOR_APPLYTIME_IS_NULL = 4109;

    /** 建立书友会申请时间格式错误 **/
     int ADD_BOOKCLUB_FORUM_APPLYTIME_FORMAT_ERROR = 4110;

    /** 书友会版主申请时间错误 **/
     int BOOKCLUB_MODERATOR_APPLYTIME_FORMAT_ERROR = 4111;

    /** 书友会状态为空 **/
     int BOOKCLUB_FORUM_STATUS_IS_NULL = 4112;

    /** 书友会状态应为整型 **/
     int BOOKCLUB_FORUM_STATUS_SHOULD_INTEGER = 4113;

    /** 已经是该书友会成员不能再申请加入 **/
     int BOOKCLUB_MEMBER_HAS_EXIST = 3506;

    /** 书友会帖子详情为空 **/
     int BOOKCLUB_THREAD_INFO_IS_NULL = 3507;

    /** 书友会回帖ID为空 **/
     int BOOKCLUB_POST_POSTID_IS_NULL = 4114;

    /** 书友会回帖ID应为整型 **/
     int BOOKCLUB_POST_POSTID_SHOULD_NUMBER = 4115;

    /** 书友会回帖顶驳状态为空 **/
     int BOOKCLUB_POST_UPORDOWN_IS_NULL = 4116;

    /** 书友会回帖顶驳状态应为整型 **/
     int BOOKCLUB_POST_UPORDOWN_SHOULD_NUMBER = 4117;

    /** 对书友会回帖顶驳失败 **/
     int ADD_BOOKCLUB_POST_VIEWPOINT_FAIL = 3508;

    /** 用户在审核中 **/
     int BOOKCLUB_MEMBER_IS_AUDITING = 3509;

    /** 版主在审核中 **/
     int BOOKCLUB_MODERATOR_IS_AUDITING = 3510;

    /** 已经是版主不能再申请 **/
     int BOOKCLUB_MODERATOR_HAS_EXIST = 3511;

    /** 书友会的审核时间为空 **/
     int ADD_BOOKCLUB_FORUM_AUDITTIME_IS_NULL = 4118;

    /** 书友会的审核时间格式不正确 **/
     int ADD_BOOKCLUB_FORUM_AUDITTIME_FORMAT_ERROR = 4119;

    /** 书友会的会员状态为空 **/
     int BOOKCLUB_MEMBER_STATUS_IS_NULL = 4120;

    /** 书友会的会员状态应为整型 **/
     int BOOKCLUB_MEMBER_STATUS_SHOULD_INTEGER = 4121;

    /** 书友会的审核状态应为空 **/
     int BOOKCLUB_FORUM_AUDITSTATUS_IS_NULL = 4122;

    /** 书友会的审核状态应为整型 **/
     int BOOKCLUB_FORUM_AUDITSTATUS_SHOULD_INTEGER = 4123;

    /** 书友会的置顶状态为空 **/
     int BOOKCLUB_THREAD_UPORDOWN_IS_NULL = 4124;

    /** 书友会的置顶状态应为整型 **/
     int BOOKCLUB_THREAD_UPORDOWN_SHOULD_NUMBER = 4125;

    /** 帖子为空 **/
     int BOOKCLUB_THREAD_IS_NULL = 4126;

    /** 帖子加精状态为空 **/
     int BOOKCLUB_THREAD_DIGEST_IS_NULL = 4127;

    /** 帖子加精状态应为整型 **/
     int BOOKCLUB_THREAD_DIGEST_SHOULD_NUMBER = 4128;

    /** 回帖屏蔽状态为空 **/
     int BOOKCLUB_POST_SHIELD_IS_NULL = 4129;

    /** 回帖屏蔽状态应为整型 **/
     int BOOKCLUB_POST_SHIELD_SHOULD_NUMBER = 4130;

    /** 回帖状态为空 **/
     int BOOKCLUB_POST_STATUS_IS_NULL = 4131;

    /** 回帖状态应为整型 **/
     int BOOKCLUB_POST_STATUS_SHOULD_NUMBER = 4132;

    /** 书友会帖子状态列表格式错误 **/
     int BOOKCLUB_THREAD_STATUSSTR_ERROR = 4133;

    /** 书友会回帖状态格式错误 **/
     int BOOKCLUB_POST_STATUSSTR_ERROR = 4134;

    /** 书友会版主为空 **/
     int BOOKCLUB_MODERATOR_IS_NULL = 4135;

    /** 回帖为空 **/
     int BOOKCLUB_POST_IS_NULL = 4136;

    /** 用户调查查询调查结果排序响应码为空 */
     int SURVEY_ORDER_IS_NULL = 4137;

    /** 用户调查查询调查结果排序响应码为非数字 */
     int SURVEY_ORDER_NOT_NUMBER = 4138;

    /** 直播间图片规格为空 */
     int LIVE_STANDARD_IS_NULL = 4139;

    /** 直播间图片规格为非数字 */
     int LIVE_STANDARD_NOT_NUMBER = 4140;

    /* *************书友会 end************* */

    /* *************缓存 start************* */

     int LAYER_TEMPLATE_NODE_LAYERID_IS_NULL = 4140;

     int LAYER_TEMPLATE_NODE_LAYERID_NOT_NUMBER = 4141;

     int CIRCLE_PAGE_LABEL_PAGELABELID_IS_NULL = 4142;

     int CIRCLE_PAGE_LABEL_PAGELABELID_NOT_NUMBER = 4143;

     int PTL_PAGE_REALPATH_IS_NULL = 4144;

     int PTL_PAGE_FILENAME_IS_NULL = 4145;

    /* *************缓存 end************* */

    /* ***********用户同步 start*********** */

    /** 会员名称为空 */
     int USERSYNC_USERNAME_IS_NULL = 4151;

    /** 会员密码为空 */
     int USERSYNC_PASSWORD_IS_NULL = 4152;

    /** 会员id为空 */
     int USERSYNC_MEMBERID_IS_NULL = 4153;

    /** 扩展用户id为空 */
     int USERSYNC_MEMBERSEXTID_IS_NULL = 4154;

    /** 用户id为空 */
     int USERSYNC_USERID_IS_NULL = 4155;

    /** 通过会员名查询会员失败 */
     int USERSYNC_GET_USER_MEMBER_BY_NAME_FAIL = 3600;

    /** 添加会员失败 */
     int USERSYNC_ADD_USER_MEMBER_FAIL = 3601;

    /** 更新会员信息失败 */
     int USERSYNC_UPDATE_USER_MEMBER_FAIL = 3602;

    /** 添加扩展用户信息失败 */
     int USERSYNC_ADD_USER_MEMBER_EXT_FAIL = 3603;

    /** 通过会员编号查询扩展用户信息失败 */
     int USERSYNC_GET_USER_MEMBER_EXT_FAIL = 3604;

    /** 更新扩展用户信息失败 */
     int USERSYNC_UPDATE_USER_MEMBER_EXT_FAIL = 3605;

     int WHOLE_RESERVE_DATE_IS_NULL = 4164;

     int WHOLE_RESERVE_USERNAME_IS_NULL = 4165;

     int WHOLE_RESERVE_USERNAME_LENGTH_ERROR = 4166;

     int ISWHOLERESERVE_IS_NULL = 4177;

     int ISWHOLERESERVE_NOT_NUMBER = 4178;

    /* ***********用户同步 end*********** */
    /** 无等级规则记录 */
     int NO_GRADE_RECORD = 4230;

    /** SMS开关已关闭 */
     int SMS_SWITCH_CLOSED = 26604;

    /** push开关已关闭 */
     int PUSH_SWITCH_CLOSED = 26605;

    /********************** myspace start *********************************/
    /** 用户手机号为空 */
     int USER_MOBILE_IS_NULL = 4179;

    /** 查询类型为错误 */
     int QUERY_TYPE_IS_ERROR = 4180;

    /** 分页查询参数错误 */
     int PAGE_PARAMETER_IS_ERROR = 4184;

    /** 加关注或粉丝失败 */
     int ATTEND_FAIL = 4186;

    /** 关注用户在黑名单 */
     int TO_ATTEND_IN_BLACKLIST = 4187;

    /** 关注已存在 */
     int ATTEND_EXISTS = 4188;

    /** 取消关注或粉丝失败 */
     int CANCLE_FAIL = 4189;

    /** 没有搜索到用户 */
     int NO_USER_FOUND = 4190;

    /** 没有相互关注 */
     int NOT_INTER_ATTEND = 4191;

    /** 没有关注用户 **/
     int NOT_ATTEND = 4192;

    /** 关注自已 **/
     int ATTEND_SELF = 4193;

    /** 取消关注删除好友动态失败 */
     int DELETE_FRIEND_DYNAMIC_FIAL = 4194;

    /********************** myspace end ***********************************/

    /************************* 最新动态 **************************************/
    /** 记录id为空 */
     int NEWDYNAMIC_RECORDID_IS_NULL = 4600;

    /** 记录id不是数字 */
     int NEWDYNAMIC_RECORDID_NOT_NUMBER = 4601;

    /** 类型为空 */
     int NEWDYNAMIC_TYPE_IS_NULL = 4602;

    /** 类型不是数字 */
     int NEWDYNAMIC_TYPE_NOT_NUMBER = 4603;

    /***********************************************************************/

    /************************* 积分体系 **************************************/
    /** ID为空 */
     int INTEGRALCHANGE_ITEMIDLIST_IS_NULL = 4200;

    /** ID不是整数 */
     int INTEGRALCHANGE_ITEMIDLIST_NOT_NUMBER = 4201;

    /** 积分兑换数值为空 */
     int USER_INTEGRAL_IS_NULL = 4202;

    /** 积分兑换数值应为double */
     int USER_INTEGRAL_SHOULD_DOUBLE = 4203;

    /** 用户积分不够 */
     int USER_INTEGRAL_NOT_ENOUGH = 4444;

    /** 更新用户积分失败 */
     int UPDATE_USER_INTEGRAL_FAILED = 4443;

    /** 积分详情表中无此用户或无此用户积分信息 */
     int USER_INTEGRAL_NOT_EXSIS = 4445;

    /** 兑换额度为空 */
     int TICKETSUM_IS_NULL = 4204;

    /** 兑换额度不是整数 */
     int TICKETSUM_NOT_NUMBER = 4205;

    /** 插入积分明细表失败 */
     int INSERT_USER_INTEGRAL_DETAIL_FAILED = 4446;

    /***********************************************************************/

    /************************* 分享功能 **************************************/

    /** ** 分享ID为空 */
     int SHAREID_IS_NULL = 4500;

    /** **分享ID应为整型 */
     int SHARE_SHOULD_INTEGER = 4501;

    /** **分享理由不能为空 */
     int SHARE_REASON_IS_NULL = 4502;

    /** *更新分享理由失败 */
     int SHARE_REASON_UPDATE_FAILED = 4503;

    /** *图书id为空 */
     int BOOKID_IS_NULL = 4504;

    /** *图书未被分享过 */
     int NOT_SHARED = 4505;

    /** *分享信息不存在 */
     int NO_SHARE_INFO = 4506;

    /***********************************************************************/

    /************************ 模块定制信息 ***********************************/

    /** 定制key为空 */
     int KEY_IS_NULL = 4511;

    /** 定制value为空 */
     int VALUE_IS_NULL = 4512;

    /** 设置定制信息失败 */
     int SET_BLOCK_RESERVE_INFO_FAILED = 4513;

    /***********************************************************************/
    /**
     * 日志ID为空
     */
     int MBLOG_ID_IS_NULL = 20001;

    /**
     * 日志ID类型错误
     */
     int MBLOG_ID_IS_NOT_LONG = 20002;

    /**
     * 该日志不存在
     */
     int MBLOG_INFO_IS_NOT_EXSIT = 20003;

    /** 时间格式错误 */
     int DATE_FORMAT_ERROR = 20034;

    /**
     * 显示数量类型错误
     */
     int MBLOG_SHOWNUM_IS_NOT_NUMBER = 20004;

    /**
     * 帐号类型为空
     */
     int MBLOG_SYSID_IS_NULL = 20005;

    /**
     * 用户帐号为空
     */
     int MBLOG_USERID_IS_NULL = 20006;

    /**
     * 该日志评论不存在
     */
     int MBLOG_COMMENT_INFO_IS_NOT_EXSIT = 20104;

    /**
     * 图书ID为空
     */
     int REF_BOOK_ID_IS_NULL = 20201;

    /**
     * 图书ID类型错误
     */
     int REF_BOOK_ID_IS_NOT_LONG = 20202;

    /*******************************************************************/

    /** 悦·分享 主题ID为空 */
     int HAPPYSHARE_SUBJECTID_IS_NULL = 60001;

    /** 名家认证 对应的手机号为空 */
     int MSISDN_IS_NULL = 80001;

    /***********************************************/
    /**
     * 评论id为空
     */
     int COMMENT_ID_IS_NULL = 20007;

    /**
     * 评论id类型错误
     */
     int COMMENT_ID_IS_NOT_LONG = 20008;

    /***********************************************/

    /** 分类查询类型为空 */
     int LABEL_IS_NULL_OR_EMPTY = 20009;

    /** 分类查询类型错误 */
     int LABEL_IS_NOT_NUMBER = 20010;

    /** 昵称为空 **/
     int NICKNAME_IS_NULL = 20011;

    /** 账号类型为空 **/
     int ACCOUNTTYPE_IS_NULL = 20012;

    /** 微薄创建时间为空 **/
     int MBLOG_CREATETIME_IS_NULL = 20013;

    /** 微薄创建时间错误 **/
     int MBLOG_CREATETIME_FORMAT_ERROR = 20014;

    /** 该用户不是名家 **/
     int IS_NOT_CELEBRITY = 20015;

    /** 图书ID不正确 */
     int BOOK_ID_IS_ERROR = 20016;

    /** added by zKF40547 at 2011-12-16 for S063-5 begin */
    /** 账号类型为空 **/
     int ACCOUNT_TYPE_IS_NULL = 20019;

    /** 虚拟手机号格式不正确，以7开头 **/
     int XUNI_MSISDN_NOT_RIGHT = 20020;

    /** added by zKF40547 at 2011-12-16 for S063-5 end */

    /** added by hkf54903 at 2011-12-26 for saveOrUpdateWeiboUserInfo begin */

    /** 账号类型不能为空，新浪以1开头 **/
     int THIRDACCOUNT_ACCOUNTTYPE_IS_NULL = 20021;

    /** 账号类型不是数字 ，1位数字 **/
     int THIRDACCOUNT_ACCOUNTTYPE_IS_NOT_NUM = 20022;

    /** 账号类型超过一位数字，以1开头 **/
     int THIRDACCOUNT_ACCOUNTTYPE_IS_MORE_ONENUM = 20023;

    /** 账号不能为空， **/
     int THIRDACCOUNT_ACCOUNT_IS_NULL = 20024;

    /** 呢称不能为空 **/
     int THIRDACCOUNT_NICKNAME_IS_NULL = 20025;

    /** 虚拟手机号前缀不能为空 **/
     int THIRDACCOUNT_MSISDNPRE_IS_NULL = 20026;

    /** 虚拟手机号前缀不是数字 ，1位数字 **/
     int THIRDACCOUNT_MSISDNPRE_IS_NOT_NUM = 20027;

    /** 虚拟手机号前缀超过一位数字 ，1位数字 **/
     int THIRDACCOUNT_MSISDNPRE_IS_MORE_ONENUM = 20028;

    /** 微博类型不能为空 **/
     int THIRDACCOUNT_WEIBOTYPE_IS_NULL = 20029;

    /** 微博类型不是数字 ，1位数字 **/
     int THIRDACCOUNT_WEIBOTYPE_IS_NOT_NUM = 20030;

    /** 微博类型超过一位数字 ，1位数字 **/
     int THIRDACCOUNT_WEIBOTYPE_IS_MORE_ONENUM = 20031;

    /**
     * 获取日志序列失败
     */
     int GET_MBLOG_SEQ_ERROR = 20032;

    /**
     * 新浪微博ID为空
     */
     int MBLOG_WEIBOID_IS_NULL = 20033;

    /** added by hkf54903 at 2011-12-26 for saveOrUpdateWeiboUserInfo end */

    /**
     * 名家同步新浪微博时添加抓取评论任务 ####微博ID为空####
     */
     int COMMENTTESK_WEIBOID_IS_NULL = 40013;

    /**
     * 名家同步新浪微博时添加抓取评论任务 ####新浪微博ID为空####
     */
     int COMMENTTESK_BLOGID_SINA_IS_NULL = 40015;

    /**
     * 名家同步到外围网站的标记数据库id为空####
     */
     int COMMENTTESK_DB_TYPE_IS_NULL = 40016;

    /**
     * 名家同步新浪微博时添加抓取评论任务 ####账号类型为空####
     */
     int COMMENTTESK_ACCOUNTTYPE_IS_NULL = 40014;

    /**
     * 获取轮循链接 --页面标签id为空
     */
     int COMMENTTESK_PAGELEBALID_IS_NULL = 40020;

    /**
     * 是否会员字段空
     */
     int USER_IS_VIP_IS_NULL = 40021;

    /**
     * 积分字段为空
     */
     int INTEGRAL_DEDUCT_POINT_IS_NULL = 40022;

    /**
     * 积分字段格式不正确
     */
     int INTEGRAL_DEDUCT_POINT_ERROR = 40023;

    /**
     * 修改昵称接口的action字段非法
     */
     int ACTION_IS_INVALID = 40024;

    /**
     * 修改昵称接口的nicknameComment字段过长
     */
     int NICKNAMECOMMENT_IS_TOO_LONG = 40025;

    /**
     * 关注关系不存在
     */
     int NO_ATTENTION_RELATION = 40026;

    /**
     * 粉丝关系不存在
     */
     int NO_FANS_RELATION = 40027;

    /**
     * 此号码已经是悦大使
     */
     int MSISDN_IS_ALREADY_STAR_AUTHOR = 40028;

    /**
     * 此号码的申请信息已经是待审核状态
     */
     int MSISDN_IS_WAITING_FOR_AUDITING = 40029;

    /**
     * 此号码今天已经报到过了
     */
     int MSISDN_IS_ALREADY_CHECKIN = 40030;

    /**
     * 排序参数非法
     */
     int ORDER_PARAM_IS_INVALID = 40031;

    /**
     * 更新用户报到记录失败
     */
     int UPADTE_USER_CHECKIN_INFO_FAIL = 40033;

    /**
     * 虚拟手机号不能发私信
     */
     int THIRDACCOUNT_MSISDN_CANNOT_SEND_MSG = 40034;

    /**
     * IP不能为空
     */
     int IP_IS_NULL = 40035;

    /**
     * IP格式错误
     */
     int IP_FORMAT_ERROR = 40036;

    /**
     * 分页编号应为非负整数
     * */
     int PAGE_NUM_SHOULD_POSITIVE_INTEGER = 40037;

    /**
     * 分页长度应为非负整数
     * */
     int PAGE_LEN_SHOULD_POSITIVE_INTEGER = 40038;

    /**
     * 第三方账号的虚拟号不能申请悦大使
     */
     int THIRDACCOUNT_CANNOT_APPLY_STAR_AUTHOR = 40039;

    /**
     * 领读图书id为空
     */
     int LEADER_BOOKID_IS_NULL = 500305;

    /**
     * 用户账号为空
     */
     int USERID_IS_NULL = 22000;

    /**
     * 言论类型错误
     */
     int COMMENTTYPE_ERROR = 40040;

    /**
     * 用户在黑名单中
     */
     int MSISDN_IN_BLACKLIST = 40041;

    /**
     * 未绑定第三方渠道
     */
     int MSISDN_UNBIND_THIRD_CHANNEL = 40042;

    /**
     * 暂无图书阅读信息
     */
     int NO_BOOK_READ_INFO = 500307;

    /**
     * 变更类型错误
     */
     int USERCHANGE_TYPE_ERROR = 500308;

    /**
     * 日志或评论审核参数为空
     */
     int AUDITCHANGE_PARAM_NULL = 500309;

    /**
     * 日志或评论审核类型错误
     */
     int AUDITCHANGE_TYPE_ERROR = 500310;

    /**
     * 日志或评论批量审核失败
     */
     int AUDITCHANGE_FAIL = 500311;

    /**
     * 请求记录条数非法
     */
     int NUMBER_SHOUD_INTEGRAL = 500333;

    /**
     * 状态值非法(只能为0 隐藏 1 显示)
     */
     int STATUS_IS_NOT_TRUE = 500334;

    /**
     * 请求记录数为空
     */
     int NUMBER_IS_NULL = 500335;

    /**
     * 请求状态为空
     */
     int STATUS_IS_NULL = 500336;

    /** 阅读号为空 */
     int USER_ACCOUNT_IS_NULL = 500337;

    /** 阅读号非法 */
     int NOT_USER_ACCOUNT = 500338;

    /** 任务id为空 */
     int TASK_ID_IS_NULL = 500339;

    /** 任务不存在 */
     int TASK_ID_IS_NOT_EXIST = 500340;

    /** 任务失效 */
     int TASK_IS_INVALIDATION = 500341;

    /** 任务关闭 */
     int TASK_IS_CLOSE = 500342;

    /** 超出领取任务最大次数 */
     int RECEIVE_TASK_IS_EXCEED_MAX_NUM = 500343;

    /** 用户已经领取任务 */
     int USER_HAS_ALREADY_RECEIVE_TASK = 500344;

    /** 用户已经领取任务但没有超过最大次数 */
     int USER_HAS_ALREADY_RECEIVE_TASK_BUT_CAN_RECEIVE = 500345;

    /** 用户未领取任务 */
     int USER_HAS_NOT_RECEIVE_TASK = 500346;

    /** 完成任务超出限制 */
     int USER_ACHIEVE_TASK_EXCEED_MAX_NUM = 500347;

    /** 查询任务列表时领取状态为空 */
     int RECEIVE_STATE_IS_NULL = 500348;

    /** 查询的任务状态不存在 */
     int RECEIVE_STATE_IS_NOT_EXIST = 500349;

    /**
     * 阅读号不能为空
     */
     int CREATE_BOOKSHEET_MSISDN_IS_NULL = 500501;

    /**
     * 书单标题不能为空
     */
     int CREATE_BOOKSHEET_TITLE_IS_NULL = 500502;

    /**
     * 阅读号输入错误
     */
     int CREATE_BOOKSHEET_MSISDN_IS_ERROR = 500503;

    /**
     * 书单标题超过长度限制
     */
     int CREATE_BOOKSHEET_TITLE_LENGTH_ERROR = 500504;

    /**
     * 书单描述超过长度限制
     */
     int CREATE_BOOKSHEET_DESC_LENGTH_ERROR = 500505;

    /**
     * 用户创建书单个数已达到上限
     */
     int CREATE_BOOKSHEET_LIMIT_ERROR = 500506;

    /**
     * 书单ID不能为空
     */
     int MODIFY_BOOKSHEET_ID_IS_NULL = 500507;

    /**
     * 修改书单时错误，手机号和书单ID不匹配
     */
     int MODIFY_BOOKSHEET_ERROR = 500508;

    /**
     * 删除书单异常
     */
     int DELETE_BOOKSHEET_ERROR = 500509;

    /**
     * 图书ID为空
     */
     int CREATE_BOOKCATALOG_BOOKID_IS_NULL = 500510;

    /**
     * 创建书目异常
     */
     int CREATE_BOOKCATALOG_ERROR = 500511;
     
    /**
     * 删除书目异常
     */
     int DELETE_BOOKCATALOG_ERROR = 500512;

    /**
     * 创建书目超过最大限制
     */
     int CREATE_BOOKCATALOG_LIMIT_ERROR = 500513;

    /**
     * 隐私设置值为空
     */
     int UPDATE_PERSISSION_IS_NULL = 500514;

    /**
     * 更新隐私设置异常
     */
     int UPDATE_PERSISSION_ERROR = 500515;

     int BOOKSHEET_ID_IS_NOT_NUMBER = 500516;

     int BOOKCATALOG_ID_IS_NOT_NUMBER = 500517;

     int BOOKSHEET_PERMISISSION_IS_NOT_NUMBER = 500518;

     int BOOKSHEET_STATE_IS_NOT_NUMBER = 500519;

     int BOOKCATALOG_ID_IS_NULL = 500520;

     int BOOKSHEET_SHARE_ERROR = 500521;

     int BOOKSHEET_DELETE_SHARE_ERROR = 500522;

    /** 2402书单错误码 start */

    /** 排行榜类型不存在 */
     int BOOKSHEET_RANK_TYPE_IS_NOT_EXIST = 500523;

     int BOOKSHEET_RANK_TYPE_IS_NULL = 500524;

     int BOOKSHEET_RANK_TYPE_RANK_START_IS_ERROR = 500525;

     int BOOKSHEET_RANK_TYPE_RANK_COUNT_IS_ERROR = 500526;

    /** 图书ID为空 */
     int BOOK_IS_NULL = 500527;

    /** 2402书单错误码 end */

     int CREATE_BOOKCATALOG_IS_EXIST = 500523;

    /** 查询的书评人等级列表为空 liudong add */
     int SELECT_REVIEW_LEVEL_IS_NULL = 500528;

    /** 图书分类ID不能为空 */
     int BOOKCLASSID_IS_NULL = 6001;

     int BOOKCLASSID_IS_NOT_DIGIST = 6002;

    //3054返回码
    //阅读号为空
     int SHEET_MSISDN_IS_NULL = 305401;

    //阅读号非法
     int SHEET_MSISDN_IS_ILLEGAL = 305402;
    
    //书单ID为空
     int SHEET_ID_IS_NULL = 305403;

    //书单ID非法
     int SHEET_ID_IS_ILLEGAL = 305404;
    
    //评分为空
     int SHEET_SCORE_IS_NULL = 305405;

    //评分非法
     int SHEET_SCORE_IS_ILLEGAL = 305406;
    
    //用户已评分
     int SHEET_IS_SCORED = 305407;
    
    //书单ID不存在
     int SHEET_ID_NOT_EXIST = 305408;
    
    // ================book review begin ======================
    /*** 调用平台接口生产书券失败 */
     int INVOKE_CHANGEBOOKCOUPONSSNS_FAILED = 7000;

    /*** 书评人评分表信息为空 */
     int REVIEW_SCORE_INFO_IS_NULL = 7001;

    /*** 书评人已领取工资 */
     int REVIEW_SALARY_HAVE_BEEN_RECEIVED = 7002;

    /*** 书评人可领取工资为0 */
     int REVIEW_SALARY_IS_ZERO = 7003;

    /**
     * 顶驳人号码为游客
     */
     int REVIEW_MSISDN_IS_GUEST = 7004;

    /**
     * 顶驳操作非法
     */
     int REVIEW_OPT_IS_ILLEGAL = 7005;

    /**
     * 重复顶驳
     */
     int REVIEW_OPT_REPEAT = 7006;

    /**
     * 图书非数字
     */
     int BOOKID_IS_NOT_DIGIST = 7007;

    /**
     * 图书或分类ID超过最大长度
     */
     int BOOKID_BEYOND_MAX_LENGTH = 7008;

    /**
     * 图书或分类ID超过最大长度
     */
     int BOOKCLASSID_BEYOND_MAX_LENGTH = 7009;

    /**
     * 帖子ID超过最大长度
     */
     int PID_BEYOND_MAX_LENGTH = 7010;

    /**
     * 帖子分数超过最大界限
     */
     int REVIEW_SCORE_OUT_OF_RANGE = 7011;

    // ================book review end ======================
    
	/**
	 * 注释内容
	 */
	 int DUIBA_REPEATED_NOTIFICATIONS = 70011;

	/**
	 * 注释内容
	 */
	 int DUIBA_ORDERNUM_IS_NULL_ = 70012;

	/**
	 * 注释内容
	 */
	 int DUIBA_ORDER_IS_NULL = 70013;

	/**
	 * 注释内容
	 */
	 int DUIBA_ORDER_STATUS_IS_INVALID = 70014;
    

    /** 榜单类型非法 **/
     int BOOK_REVIEW_TYPE_ILLEGAL = 7071;

    // ===============funs system begin================
    /** 粉丝体系：阅读号为空 */
     int FANS_MSISDN_IS_NULL = 8000;

    /** 粉丝体系： 阅读号非法 */
     int FANS_MSISDN_IS_ILLEGAL = 8001;

    /** 粉丝体系：图书ID为空 */
     int FANS_BOOKID_IS_NULL = 8002;

    /** 粉丝体系：图书ID非法 */
     int FANS_BOOKID_IS_ILLEGAL = 8003;

    /** 粉丝体系：图书名称为空 */
     int FANS_BOOKNAME_IS_NULL = 8004;

    /** 粉丝体系：论坛ID为空 */
     int FANS_FORUMID_IS_NULL = 8005;

    /** 粉丝体系：论坛ID非法 */
     int FANS_FORUMID_IS_ILLEGAL = 8006;

    /** 粉丝体系：图书分类ID为空 */
     int FANS_BOOKCLASSID_IS_NULL = 8007;

    /** 粉丝体系：图书分类ID非法 */
     int FANS_BOOKCLASSID_IS_ILLEGAL = 8008;

    /** 粉丝体系：全站粉丝等级信息为空 */
     int GLOBAL_FANS_LEVEL_INFO_NULL = 8009;

    /** 粉丝体系：全站粉丝等级规则为空 */
     int GLOBAL_FANS_LEVEL_RULE_INFO_NULL = 8010;

    /** 粉丝体系：图书是否出版 参数为空 **/
     int BOOK_ISPUBLISH_ISNULL = 8011;

    /*** 粉丝体系：图书是否出版 参数非法 ***/
     int BOOK_ISPUBLISH_IS_ILLEGAL = 8012;

    /** 粉丝体系：图书分区类型 参数为空 **/
     int BOOK_TYPE_ISNULL = 8013;

    /** 粉丝体系：图书分区类型 参数非法 **/
     int BOOK_TYPE_IS_ILLEGAL = 8014;

    /** 粉丝体系：升级到的全站粉丝等级为空 */
     int REACHED_LEVEL_NULL = 8015;

    /** 粉丝体系：升级到的全站粉丝等级不合法 */
     int REACHED_LEVEL_IS_ILLEGAL = 8016;

    /** 粉丝体系：升级到的全站粉丝等级越界 */
     int REACHED_LEVEL_OUT_OF_RANGE = 8017;

    /** 粉丝体系：升级到的全站粉丝等级不大于当前等级 */
     int REACHED_LEVEL_IS_NOT_GREATER_THAN_CURRENT_LEVEL = 8018;

    /** 粉丝体系：用户全站粉丝等级信息为空 */
     int GLOBAL_FANS_LEVEL_INFO_IS_NULL = 8019;

    /** 粉丝体系：升级所需的粉丝值不足 */
     int GLOBAL_REMAINING_FANS_SCORE_IS_DEFICIENT = 8020;

    /** 粉丝体系：图书粉丝等级规则为空 */
     int BOOK_FANS_LEVEL_RULE_INFO_NULL = 8801;

    /** 粉丝体系：全站粉丝变更类型为空 */
     int FANS_SCORE_CHANGE_TYPE_IS_NULL = 8030;

    /** 粉丝体系：全站粉丝变更类型不为整数 */
     int FANS_SCORE_CHANGE_TYPE_IS_NOT_INTEGER = 8031;

    /** 粉丝体系：全站粉丝变更类型不合法 */
     int FANS_SCORE_CHANGE_TYPE_IS_ILLEGAL = 8032;

    /** 粉丝体系： 图书粉丝值汇总表查不到相应记录 */
     int FANS_TOTAL_IS_NULL = 8033;

    /** 粉丝体系： 粉丝排行榜类型非法 */
     int FANS_RANKINGTYPE_IS_ILLEGAL = 8113;

    /** 粉丝体系： 图书分类土豪区映射表返回数据为空 */
     int BOOKCLASS_LUXURYAREA_IS_NULL = 8040;

    /** 粉丝体系： 图书粉丝等级权益关联表返回数据为空 */
     int BOOKFANSRIGHT_RELATION_IS_NULL = 8041;

    // ===============funs system end================

    // ===============props system begin================

    /** 道具体系： 道具类型为空 */
     int PROPS_TYPE_IS_NULL = 8200;

    /** 道具体系： 道具类型非法 */
     int PROPS_TYPE_IS_ILLEGAL = 8201;

    /** 道具体系： 到期类型为空 */
     int EXPIRE_TYPE_IS_NULL = 8202;

    /** 道具体系： 到期类型非法 */
     int EXPIRE_TYPE_IS_ILLEGAL = 8203;

    /** 道具体系： 是否返回数量为0道具为空 */
     int RETURN_TYPE_IS_NULL = 8204;

    /** 道具体系：是否返回数量为0道具非法 */
     int RETURN_TYPE_IS_ILLEGAL = 8205;

    /** 道具体系：阅读号为空 */
     int PROPS_MSISDN_IS_NULL = 9000;

    /** 道具体系： 阅读号非法 */
     int PROPS_MSISDN_IS_ILLEGAL = 9001;

    /** 道具体系：道具ID为空 */
     int PROPS_PROPSID_IS_NULL = 9002;

    /** 道具体系：道具ID非法 */
     int PROPS_PROPSID_IS_ILLEGAL = 9003;

    /** 道具体系：道具不存在 */
     int PROPS_IS_NULL = 9004;

    /** 道具体系：查询不到该道具推荐信息 */
     int PROPS_IS_NOT_RECOMMEND = 9005;

    /** 道具体系：道具不是礼包 */
     int PROPS_IS_NOT_PACKAGE = 9006;

    /** 道具体系：等级不符不能领取礼包 */
     int LEVEL_NOT_ENOUGHT_GET_PACKAGE = 9007;

    /** 道具体系：非VIP不能领取礼包 */
     int NOT_VIP_NOT_GET_PACKAGE = 9008;

    /** 道具体系：已领取过，不能重复领取 */
     int NOT_GET_PACKAGR_AGAIN = 9009;

    /** 道具体系：普通用户不能领取 */
     int USER_NOT_GET_PACKAGR = 9012;

    /** 道具体系：道具使用数量为空 */
     int PROPS_USENUM_IS_NULL = 9010;

    /** 道具体系：道具使用数量非法 */
     int PROPS_USENUM_IS_ILLEGAL = 9011;

    /** 道具体系：道具信息表无此道具，或该道具已下架或删除 */
     int PROPS_IS_NOT_FOUND = 9100;

    /** 道具体系：该道具不是书券道具和论坛道具 */
     int PROPS_ISNOT_BOOKFUROM = 9200;

    /** 道具体系：排行类型为空 */
     int PROPS_TOPTYPE_IS_NULL = 9201;

    /** 道具体系： 排行类型非法 */
     int PROPS_TOPTYPE_IS_ILLEGAL = 9202;

    /** 道具体系：start为空 */
     int PROPS_START_IS_NULL = 9203;

    /** 道具体系： statrt非法 */
     int PROPS_START_IS_ILLEGAL = 9204;

    /** 道具体系：count为空 */
     int PROPS_COUNT_IS_NULL = 9205;

    /** 道具体系： count非法 */
     int PROPS_COUNT_IS_ILLEGAL = 9206;

    /** 道具体系：帖子ID为空 */
     int PROPS_POSTID_IS_NULL = 9209;

    /** 道具体系：帖子ID非法 */
     int PROPS_POSTID_IS_ILLEGAL = 9210;

    /** 道具体系：版块ID为空 */
     int PROPS_SECTIONID_IS_NULL = 9211;

    /** 道具体系：版块ID非法 */
     int PROPS_SECTIONID_IS_ILLEGAL = 9212;

    /** 道具体系：调用书券领取接口失败 */
     int PROPS_GETTICKET_IS_FAILD = 9213;

    /** 道具体系：调用论坛使用道具接口失败 */
     int PROPS_USEOFPROPS_IS_FAILD = 9214;

    /** 道具体系：没有此道具的购买信息，无法使用 */
     int PROPS_PROPS_ISNOT_BUY = 9217;

    /** 道具体系：有道具过期，无法使用 */
     int PROPS_PROPS_IS_OVERDUE = 9218;

    /** 道具体系：论坛道具使用重复 */
     int PROPS_USEOFPROPS_REPETITION = 9219;

    /** 道具体系：道具数量不足，无法使用 */
     int PROPS_PROPSNUM_ISNOT_ENOUGH = 9215;

    /** 道具体系：论坛道具不能使用多个 */
     int PROPS_FORUMROPS_ISNOT_ONE = 9216;

    /** 道具体系：道具购买数量为空 */
     int PROPS_QUANTITY_IS_NULL = 9501;

    /** 道具体系：道具购买数量非法 */
     int PROPS_QUANTITY_IS_ILLEGAL = 9502;

    /** 道具体系：道具购买方式为空 */
     int PROPS_PAYMODEL_IS_NULL = 9503;

    /** 道具体系：道具购买方式非法 */
     int PROPS_PAYMODEL_IS_ILLEGAL = 9504;

    /** 道具体系：用户非会员，不能用会员消费积分购买道具 */
     int COUND_NOT_BUY_FOR_NON_VIP = 9505;

    /** 道具体系：道具无会员积分价 */
     int PROPS_HAS_NO_PRICE = 9506;

    /** 道具体系：消费积分不足，不能购买 */
     int COUND_NOT_BUY_FOR_POVERTY = 9507;

    /** 道具体系：会员信息为null */
     int VIP_INFO_IS_NULL = 9508;

    /** 道具体系：道具不可购买 */
     int PROPS_COULD_NOT_BE_BOUGHT = 9509;

    /** 道具体系：无礼包道具 */
     int USER_PACKAGE_NUM_IS_NULL = 9520;

    /** 道具体系：图书ID为空 */
     int BOOK_ID_IS_NULL = 9521;

    /** 道具体系：图书ID超过最大长度 */
     int BOOK_ID_MAX_LENGTH = 9522;

    /** 道具体系：图书分类ID为空 */
     int BOOK_CLASS_ID_IS_NULL = 9523;

    /** 道具体系：图书分类ID超过最大长度 */
     int BOOK_CLASS_ID_MAX_LENGTH = 9524;

    /** 道具体系：图书分类ID超过最大长度 */
     int LEVEL_WEIGHT_INFO_IS_NULL = 9525;

    /** 道具体系：礼包过期 */
     int USER_PACKAGE_IS_TIMEOUT = 9580;

    /** 道具体系：礼包中无道具 */
     int NO_PROPS_IN_THE_PACKAGE = 9581;
    
    /** 道具体系：兑换类型非法 */
     int EXCHANGE_TYPE_IS_ILLEGAL = 4212;
    
    /** 扣减类型非法 */
     int DEDUCT_TYPE_IS_ILLEGAL = 4214;
    
    /** 订单id非法 */
     int ORDER_ID_IS_ILLEGAL = 4213;
    
    /** 无订单信息 */
     int ORDER_IS_NULL = 4215;
    
    /** 道具名称非法 */
     int PROPS_NAME_ILLEGAL = 4220;
    
    /** 扣减结果非法 */
     int DEDUCT_RESULT_IS_ILLEGAL = 4222;
    
    /** 道具体系： 道具类型非法 */
     int PROPSTYPE_IS_ILLEGAL = 4217;
    
    /** 道具体系： statrt非法 */
     int PROPS_START_ILLEGAL = 4218;
    
    /** 道具体系： 分页大小pageSize非法 */
     int PROPS_PAGESIZE_IS_ILLEGAL = 4219;

    // ===============props system end================

    // ===============author system begin================

    /** 作家体系：阅读号为空 */
     int AUTHOR_MSISDN_IS_NULL = 9600;

    /** 作家体系：阅读号非法 */
     int AUTHOR_MSISDN_IS_ILLEGAL = 9601;

    /** 作家体系：笔名为空 */
     int AUTHOR_AUPENNAME_IS_NULL = 9602;

    /** 作家体系：姓名为空 */
     int AUTHOR_AUNAME_IS_NULL = 9603;

    /** 作家体系：身份证扫描件（ 正）为空 */
     int AUTHOR_CERTIFICATEPATH_IS_NULL = 9604;

    /** 作家体系：身份证扫描件（ 反）为空 */
     int AUTHOR_UNCERTIFICATEPATH_IS_NULL = 9605;

    /** 作家体系：QQ号为空 */
     int AUTHOR_QQ_IS_NULL = 9606;

    /** 作家体系：作者简介为空 */
     int AUTHOR_DESCRIBE_IS_NULL = 9607;

    /** 作家体系：手机钱包帐号为空 */
     int AUTHOR_BURSE_IS_NULL = 9608;

    /** 作家体系：认证状态为空 */
     int AUTHOR_AUTHENTICATION_IS_NULL = 9609;

    /** 作家体系： 认证状态类型错误 */
     int AUTHOR_AUTHENTICATION_TYP_FALSE = 9612;

    /** 作家体系： 是否副本为空 */
     int AUTHOR_ECTYPE_IS_NULL = 9620;

    /** 作家体系：分类为空 */
     int AUTHOR_CLASSIFY_IS_NULL = 9621;

    /** 作家体系：申请认证时间为空 */
     int AUTHOR_CREATEDATE_IS_NULL = 9622;

    /** 作家体系：支付请求list为空 */
     int USER_PAY_REQUEST_LIST_NULL = 9610;

    /** 作家体系：支付请求项为空 */
     int USER_PAY_REQUEST_ITEM_NULL = 9611;

    /** 作家体系：requestId为空 */
     int USER_PAY_REQUEST_ID_NULL = 9612;

    /** 作家体系：amount为空 */
     int USER_PAY_AMOUNT_NULL = 9613;

    /** 作家体系：remark为空 */
     int USER_PAY_REMARK_NULL = 9614;

    /** 作家体系：receiveNo为空 */
     int USER_PAY_RECEIVENO_NULL = 9615;

    /** 作家体系：找不到msisdn */
     int AUTHOR_MSISDN_NOT_EXIST = 9650;

    /** 作家体系：更新认证状态返回错误 */
     int AUTHOR_UPDATE_AUTHENTICATION_FALSE = 9661;

    /** 作家体系: 合同变更状态类型错误 */
     int SIGN_STATUS_TYP_FALSE = 9672;

    /** 作家体系: 合同变更状态类型为空 */
     int SIGN_STATUS_TYP_NULL = 9673;

    /** 作家体系: 标志位transactionId为空 */
     int TRANSACTION_ID_IS_NULL = 9674;

    /** 作家体系: 标志位transactionId非法 */
     int TRANSACTION_ID_IS_ILLEGAL = 9675;

    /** 作家体系: 身份证号码为空 */
     int IDNUMBER_IS_NULL = 9676;

    /** 作家体系: 身份证号码非法 */
     int IDNUMBER_IS_ILLEGAL = 9677;

    // ===============author system end================

    // =============== vip system begin================
    /** 会员体系：阅读号为空 */
     int VIP_MSISDN_IS_NULL = 10001;

    /** 会员体系：阅读号非法 */
     int VIP_MSISDN_IS_ILLEGAL = 10002;

    /** 会员体系：起始位置为空 */
     int VIP_START_IS_NULL = 10003;

    /** 会员体系：起始位置非法 */
     int VIP_START_IS_ILLEGAL = 10004;

    /** 会员体系：条数为空 */
     int VIP_COUNT_IS_NULL = 10005;

    /** 会员体系：条数非法 */
     int VIP_COUNT_IS_ILLEGAL = 10006;

    /** 会员体系：等级积分扣减明细返回数据为空 */
     int VIP_VIPLEVELSCOREDEDUCTIONDETAIL_IS_NULL = 10007;

    /** 会员体系：等级特权信息返回数据为空 */
     int VIP_VIPPRIVILEGEINFO_IS_NULL = 10008;

    /** 会员体系：等级规则信息返回数据为空 */
     int VIP_VIPRULES_IS_NULL = 10009;

    /** 会员体系：包月产品ID为空 */
     int PRODUCTID_IS_NULL = 10010;

    /** 会员体系：包月产品ID非法 */
     int PRODUCTID_IS_ILLEGAL = 10011;

    /**
     * 获取会员等级权益信息话单为空
     */
     int GET_VIP_PRIVILEGE_LOG_IS_NULL = 10012;

    /** 会员体系：用户等级名称返回数据出错 */
     int GET_LEVEL_NAME_FALSE = 10021;

    /** 会员体系：会员等级图标返回出错 */
     int GET_VIP_LEVEL_LOGO_FALSE = 10032;

    /** 会员体系：会员可消费积分返回出错 */
     int GET_DE_VIP_SCORE_INFOLIST_FALSE = 10043;

    /** 会员体系：vip等级积分越界 */
     int VIP_LEVEL_SCORE_IS_OUT = 10100;

    /** 会员体系：扣减等级积分事件类型为null */
     int DEDUCTION_EVENT_TYPE_IS_NULL = 10101;

    /** 会员体系：扣减等级积分事件类型不合法 */
     int DEDUCTION_EVENT_TYPE_IS_ILLEGAL = 10102;

    /** 会员体系：汇总表中获取字段信息为null */
     int VIP_COLUMN_IS_NULL = 10200;

    /** 会员体系：批量下发的会员等级积分为null */
     int CHANGED_LEVELSCORE_IS_NULL = 10300;

    /** 会员体系：批量下发的会员等级积分不合法 */
     int CHANGED_LEVELSCORE_IS_ILLEGAL = 10301;

    // =============== vip system end =================
    // ================batchupdatevipscoreservice start=============
    /**
     * 批量下发
     */
     int INTEGRAL_IS_NULL = 10014;

    // ================batchupdatevipscoreservice end=============

    /** 书单体系：书单ID不存在 */
     int SHEET_IS_NULL = 10601;

    /** 书单体系：书单点击量非法 */
     int CLICKNUM_IS_ERROR = 10602;

    /** 书单体系：书单点击量为空 */
     int CLICKNUM_IS_NULL = 10603;

    /** 书单体系：书单已删除 */
     int SHEET_IS_ERROR = 10604;

    /** 书单体系：书单点击失败 */
     int SHEET_CLICK_ERROR = 10605;

    /**
     * 书单ID为空
     */
     int BOOKSHEET_ID_IS_NULL = 12000;

    /**
     * 书单ID非法
     */
     int BOOKSHEET_ID_IS_ILLEGAL = 12001;

    /**
     * 书单ID不存在
     */
     int BOOKSHEET_IS_NOT_EXISTING = 12002;

    /**
     * 书单非公开
     */
     int BOOKSHEET_IS_NOT_OPEN = 12003;

    /**
     * 书单已被删除
     */
     int BOOKSHEET_IS_DELETED = 12004;

    /**
     * 不能赞自己的书单
     */
     int COULD_NOT_GLORIFY_OWN_BOOKSHEET = 12005;

    /**
     * 已点赞
     */
     int HAVE_GLORIFIED_THIS_BOOKSHEET = 12006;

    /**
     * 阅读号为空
     */
     int SHEET_DYNAMEIC_MSISDN_NULL = 11001;

    /**
     * 阅读号非法
     */
     int SHEET_DYNAMEIC_MSISDN_ILLEGAL = 11002;

    /**
     * 书单ID为空
     */
     int SHEET_DYNAMEIC_SHEETID_NULL = 11003;

    /**
     * 书单ID非法
     */
     int SHEET_DYNAMEIC_SHEETID_ILLEGAL = 11004;

    /**
     * 排行范围为空
     */
     int SHEET_RANKING_TYPE_NULL = 11012;

    /**
     * 排行范围为空
     */
     int SHEET_RANKING_TYPE_ILLEGAL = 11013;

    /**
     * 起始位置为空
     */
     int SHEET_START_NULL = 11014;

    /**
     * 起始位置非法
     */
     int SHEET_START_ILLEGAL = 11015;

    /**
     * 条数为空
     */
     int SHEET_COUNT_NULL = 11016;

    /**
     * 条数非法
     */
     int SHEET_COUNT_ILLEGAL = 11017;

    /**
     * 无评分资格-不是会员且无图书粉丝值
     */
     int NOREVIEWPROXY_NOVIP_NOBOOKFANS = 80081;

    /**
     * 无评分权限-非连载书且已对该书评分过
     */
     int NOREVIEWPROXY_NOSERIAL_HASREVIEWED = 80082;

    /**
     * 无评分权限-连载图书且该书已经评分过但是图书粉丝值没有增加
     */
     int NOREVIEWPROXY_SERIAL_NOTADDBOOKFANS = 80083;

    /**
     * 连载字段非法
     */
     int SERIAL_ILLEGAL = 80084;

    /**
     * 是否第一次评分字段非法
     */
     int FIRST_REVIEW_ILLEGAL = 80085;

    /**
     * 经度值非法
     */
     int LONGITUDE_IS_ILLEGAL = 80086;

    /**
     * 维度值非法
     */
     int LATITUDE_IS_ILLEGAL = 80087;

    /**
     * 用户的位置信息不存在
     */
     int LOCATION_IS_NOT_EXIST = 80088;

    /** *图书id为空 */
     int BOOK_BOOKID_IS_NULL = 10801;

    /** *图书id错误 */
     int BOOK_BOOKID_IS_ERROR = 10802;

    /** 书单id不存在 */
     int SHEETID_IS_NOT_EXIST = 500529;

    /** 图书id为空 */
     int BOOKLIST_BOOKID_IS_NULL = 500530;

    /** 书单id超过最大限制 */
     int BOOKSHEETID_IS_OVER_LIMIT = 500531;
    
    /**书单标题不能含有一级敏感词*/
     int BOOKSHEET_TITLE_HAS_SENSITIVEWORD=600610;
    
    /**书单描述不能含有一级敏感词*/
     int BOOKSHEET_DESCRIPTION_HAS_SENSITIVEWORD=600611;

    /** 类型格式错误 */
     int TYPE_IS_ERROR = 500540;

    /** 类型为空 */
     int TYPE_IS_NULL = 500541;

    /** 获取榜单失败 */
     int GET_RANKING_LIST_ERROR = 500542;

    /** 起始位置非法 */
     int START_IS_ERROR = 500543;

    /** 条数非法 */
     int COUNT_IS_ERROR = 500544;

    /** 起始位置为空 */
     int START_IS_NULL = 500545;

    /** 条数为空 */
     int COUNT_IS_NULL = 500546;
    
    /**
     * 用户书单收藏限制
     */
     int USER_BOOKSHEET_COLLECT_LIMIT=500547;
    
    /**
     * 书单已经被收藏
     */
     int USER_BOOKSHEET_IS_COLLECT=500548;
    
    /**
     * 书单已经被取消收藏
     */
     int USER_BOOKSHEET_NOT_COLLECT=500549;
    
    /**
     * 收藏书单异常
     */
    int COLLECT_BOOKSHEET_ERROR = 500550;
   
   /**
    * 取消收藏书单异常
    */
    int CANCEL_COLLECT_BOOKSHEET_ERROR = 500551;
   
   
   /**
    * 该收藏不存在
    */
    int USER_BOOKSHEET_NOT_EXSIT_COLLECT=500553;   
   
   /**
    * 该用户没有收藏书单记录
    */
   int USER_NO_COLLECT_BOOKSHEET = 500554;
  
  /**
   * 标签类型为空
   */
   int LABELTYPE_IS_NULL = 500555;
  
  /**
   * 标签类型非法
   */
   int LABELTYPE_IS_ERROR = 500556;
  
  /**
   * 一级标签非法
   */
   int ONELABEL_IS_ERROR = 500557;
  
  /**
   * 一级标签不存在
   */
   int ONELABEL_IS_NOT_EXSIT = 500558; 
  
 
  
  /**
   * 书单id不存在
   */
   int LABEL_BOOKSHEET_IS_NOT_EXSIT = 500559;  
  
  /**
   * 书单id非法
   */
   int LABEL_BOOKSHEET_IS_ERROR = 500560;   
  
  /**
   * 书单id为空
   */
   int LABEL_BOOKSHEET_IS_NULL = 500561;  
  
  
  /**
   * 标签ID为空
   */
   int LABELID_IS_NULL = 500562;  
  
  /**
   * 标签ID非法
   */
   int LABELID_IS_ERROR = 500563; 
  
  
  /**
   * 标签ID不存在
   */
   int LABELID_IS_NOT_EXSIT = 500566; 
  
  /**
   * 更新书单标签异常
   */
   int UPDATE_LABEL_ERROR = 500567; 
  
  
  /**
   * 排序非法
   */
   int LABEL_ORDERTYPE_ERROR = 500571; 
  
  
  /**
   * 一级标签为空
   */
   int LABEL_QUERY_ERROR = 500000;   
  

    // ================bookstealSystem start=============

    /*
     * 访问人阅读号为空
     */
     int MSISDN_VISIT_IS_NULL = 30010;

    /*
     * 访问人阅读号非法
     */
     int MSISDN_VISIT_IS_ILLEGAL = 30011;

    /*
     * 访问行为为空
     */
     int VISIT_BEHAVIOR_IS_NULL = 30012;

    /*
     * 访问行为非法
     */
     int VISIT_BEHAVIOR_IS_ILLEGAL = 30013;

    /*
     * 阅读号为空
     */
     int MSISDN_VISITED_IS_NULL = 30014;

    /*
     * 被访问人阅读号非法
     */
     int MSISDN_VISITED_IS_ILLEGAL = 30015;

    /*
     * bookID为空
     */
     int MSISDN_BOOKID_IS_NULL = 30016;

    /*
     * bookID非法
     */
     int MSISDN_BOOKID_IS_ILLEGAL = 30017;

    /*
     * 分页数据为空
     */
     int PAGING_IS_NULL = 30020;

    /*
     * 分页数据非法
     */
     int PAGING_IS_ILLEGAL = 30021;

    /*
     * 起始位置为空
     */
     int MSISDN_START_IS_NULL = 30022;

    /*
     * 起始位置非法
     */
     int MSISDN_START_IS_ILLEGAL = 30023;
    
    /*
     * 请求分类为空
     */
     int MSISDN_TYPE_IS_NULL = 30024;

    /*
     * 请求分类非法
     */
     int MSISDN_TYPE_IS_ILLEGAL = 30025;
    
    // ================bookstealSystem end===============

    /*
     * 类型为空
     */
     int RANKINGTYPE_IS_NULL = 12100;

    /*
     * 类型非法
     */
     int RANKINGTYPE_IS_ERROR = 12101;

    /*
     * start为空
     */
     int RANKINGTYPE_START_IS_NULL = 12102;

    /*
     * start非法
     */
     int RANKINGTYPE_START_IS_ERROR = 12103;

    /*
     * count为空
     */
     int RANKINGTYPE_COUNT_IS_NULL = 12104;

    /*
     * count非法
     */
     int RANKINGTYPE_COUNT_IS_ERROR = 12105;
    
    
    /**
     * 书单标签关联失败
     */
     int BOOKSHEET_RELEVANCE_FAIL = 510000;
    /**
     * 传入id与标签库不一致
     */
     int BOOKSHEET_TAGID_WRONG = 510001;
    /**
     *超过系统上限
     */
     int BOOKSHEET_TAGID_COUNT_WRONG = 510003;
    /**
     * tag参数错误
     */
     int BOOKSHEET_TAGID_PARAMETER_WRONG = 510002;
    /**
     * tag参数错误
     */
     int BOOKSHEET_LABELID_PARAMETER_WRONG = 510004;
    
    /**
     * 阅读号为空
     */
     int GETVIPSCOREDETAIL_MSISDN_IS_NULL=500600;
    
    /**
     * 阅读号非法
     */
     int GETVIPSCOREDETAIL_MSISDN_IS_ERROR=500601;
    
    /**
     * 起始位置为空
     */
     int GETVIPSCOREDETAIL_START_IS_NULL=500602;
    
    /**
     * 起始位置非法
     */
     int GETVIPSCOREDETAIL_START_IS_ERROR=500603;
    
    /**
     * 总数为空
     */
     int GETVIPSCOREDETAIL_COUNT_IS_NULL=500604;
    
    /**
     * 总数为非法
     */
     int GETVIPSCOREDETAIL_COUNT_IS_ERROR=500605;
    
    /**
     * 查询类型为空
     */
     int GETVIPSCOREDETAIL_TYPE_IS_NULL=500606;
    
    /**
     * 查询类型非法
     */
     int GETVIPSCOREDETAIL_TYPE_IS_ERROR=500607;
    
    /**
     * 图书粉丝值排行榜列表为空
     */
     int BOOK_FANS_SCORE_RANKING_NULL=600123;
    
    /*************************用户签到异常码定义开始*****************************/
    //参数阅读号为空
     int SIGN_IN_MSISDN_IS_NULL = 601001;
    
    //阅读号非法
     int SIGN_IN_MSISDN_IS_ILLEGAL = 601002;
    
    //起始位置为空	
     int SIGN_IN_START_IS_NULL = 601100;
    
    //起始位置非数字
     int SIGN_IN_START_IS_ILLEGAL = 601102;
    
    //条数为空
     int SIGN_IN_COUNT_IS_NULL = 601103;
    
    //条数非数字
     int SIGN_IN_COUNT_IS_ILLEGAL = 601104;
    
    //查询月份为空
     int SIGN_IN_MONTH_IS_NULL = 601105;
    
    //查询月份非法
     int SIGN_IN_MONTH_IS_ILLEGAL = 601006;
    
    
     int SIGN_IN_REPEAT = 601003;
    
    //查询开始日期为空
     int BEGIN_DATE_IS_NULL = 601003;
    
    //查询开始日期非法
     int BEGIN_DATE_IS_ILLEGAL = 601004;
    
    //查询结束日期为空
     int END_DATE_IS_NULL = 601005;
    
    //查询结束日期非法
     int END_DATE_IS_ILLEGAL = 601006;
    
    //查询日期超出范围
     int DATE_OUT_OF_RANGE = 601007;
    
    /*************************用户签到异常码定义结束*****************************/

    //打赏id为空
     int RECORDID_IS_NULL = 601001;
    
    //没有打赏动态
     int NO_REWARDS_DYNAMIC = 601002;

    /*************************** 兑吧 ************************************/

    /**
     * 扣减积分大于用户积分余额
     */
     int DUIBA_FANSSCORE_REMAINING_NOT_ENOUGH = 70002;

    /**
     * 扣减积分为空或非数字
     */
     int DUIBA_SCORE_ILLEGAL = 70003;

    /**
     * facePrize非数字
     */
     int DUIBA_FACEPRIZE_IS_NOT_NUM = 70004;

    /**
     * actualPrize为空或非数字
     */
     int DUIBA_ACTUALPRIZE_IS_NOT_NUM = 70005;

    /**
     * type为空
     */
     int DUIBA_TYPE_IS_NULL = 70006;

    /**
     * description为空
     */
     int DUIBA_DESCRIPTION_IS_NULL = 70007;

    /**
     * orderNum为空
     */
     int DUIBA_ORDERNUM_IS_NULL = 70008;

    /**
     * 年份非法
     */
     int PUT_YEAR_IS_ILLEGAL = 95003;
       
    /**
     * 抽奖扣除积分msisdn为空
     */
     int LOTTERY_MSISDN_IS_NULL = 30014;
    
    /**
     * 抽奖扣除积分msisdn非法
     */
     int LOTTERY_MSISDN_IS_ILLEGAL = 22231;
    
    /**
     * 抽奖扣除积分descore非法
     */
     int LOTTERY_SCORE_ILLEGAL = 5022;

    /**
     * 抽奖扣除积分不足
     */
     int LOTTERY_SCORE_NOT_ENOUGH = 5023;
   
    /**
     * 其他错误
     */
     int LOTTERY_OTHER_ERROR = 5025;
    
    /**
     * 抽奖积分参数descript为空
     */
     int DESC_IS_NULL = 5024;
}
