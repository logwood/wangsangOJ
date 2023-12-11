package com.yupi.wangsangOJ.service;

import com.yupi.wangsangOJ.model.dto.questionsubmit.QuestionSubmitAddRequest;
import com.yupi.wangsangOJ.model.entity.QuestionSubmit;
import com.baomidou.mybatisplus.extension.service.IService;
import com.yupi.wangsangOJ.model.entity.User;

/**
* @author 三木
* @description 针对表【question_submit(提交记录)】的数据库操作Service
* @createDate 2023-12-10 21:57:35
*/
public interface QuestionSubmitService extends IService<QuestionSubmit> {
    /**
     * 题目提交
     *
     * @param questionSubmitAddRequest
     * @param loginUser
     * @return
     */
    long doQuestionSubmit(QuestionSubmitAddRequest questionSubmitAddRequest, User loginUser);

}
