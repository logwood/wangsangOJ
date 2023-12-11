package com.yupi.wangsangOJ.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yupi.wangsangOJ.common.ErrorCode;
import com.yupi.wangsangOJ.exception.BusinessException;
import com.yupi.wangsangOJ.model.dto.questionsubmit.QuestionSubmitAddRequest;
import com.yupi.wangsangOJ.model.entity.Question;
import com.yupi.wangsangOJ.model.entity.QuestionSubmit;
import com.yupi.wangsangOJ.model.entity.User;
import com.yupi.wangsangOJ.model.enums.QuestionSubmitLanguageEnum;
import com.yupi.wangsangOJ.model.enums.QuestionSubmitStatusEnum;
import com.yupi.wangsangOJ.service.QuestionService;
import com.yupi.wangsangOJ.service.QuestionSubmitService;
import com.yupi.wangsangOJ.mapper.QuestionSubmitMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
* @author 三木
* @description 针对表【question_submit(提交记录)】的数据库操作Service实现
* @createDate 2023-12-10 21:57:35
*/
@Service
public class QuestionSubmitServiceImpl extends ServiceImpl<QuestionSubmitMapper, QuestionSubmit> implements QuestionSubmitService{

    @Resource
    private QuestionService questionService;

    /**
     * 点赞
     *
     * @param questionSubmitAddRequest
     * @param loginUser
     * @return
     */
    @Override
    public long doQuestionSubmit(QuestionSubmitAddRequest questionSubmitAddRequest, User loginUser)
    {
        // todo 检验编程语言是否合法
        String language =questionSubmitAddRequest.getLanguage();
        QuestionSubmitLanguageEnum languageEnum=QuestionSubmitLanguageEnum.getEnumByValue(language);
        long  questionId=questionSubmitAddRequest.getQuestionId();
        // 判断实体是否存在，根据类别获取实体
        Question question = questionService.getById(questionSubmitAddRequest);
        if (question == null) {
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR);
        }
        // 是否已提交
        long userId = loginUser.getId();
        // 每个用户串行点赞
        // 锁必须要包裹住事务方法
        QuestionSubmit questionSubmit=new QuestionSubmit();
        questionSubmit.setUserId(userId);
        questionSubmit.setQuestionId(questionId);
        questionSubmit.setCode(questionSubmitAddRequest.getCode());
        questionSubmit.setLanguage(language);
        questionSubmit.setStatus(QuestionSubmitStatusEnum.WAITING.getValue());
        questionSubmit.setJudgeInfo("{}");
        // todo 设置初始状态
        boolean save=this.save(questionSubmit);
        if(!save){
            throw new BusinessException(ErrorCode.SYSTEM_ERROR,"数据插入失败");
        }
        return questionSubmit.getId();
    }
}




