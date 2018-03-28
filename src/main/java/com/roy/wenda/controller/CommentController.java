package com.roy.wenda.controller;

import com.roy.wenda.aspect.LogAspect;
import com.roy.wenda.model.Comment;
import com.roy.wenda.model.EntityType;
import com.roy.wenda.model.HostHolder;
import com.roy.wenda.service.CommentService;
import com.roy.wenda.wendaUtil.WendaUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Date;

@Controller
public class CommentController {
    private static final Logger logger = LoggerFactory.getLogger(CommentController.class);

    @Autowired
    HostHolder hostHolder;

    @Autowired
    CommentService commentService;

    @RequestMapping(path = {"/addComment"},method = RequestMethod.POST)
    public String addComment(@RequestParam("questionId") int questionId,
                             @RequestParam("content") String content){
        try {
            Comment comment = new Comment();
            comment.setContent(content);
            if (hostHolder.getUser() != null) {
                comment.setUserId(hostHolder.getUser().getId());
            } else {
                comment.setUserId(WendaUtil.ANONYMOUS_USERID);
            }
            comment.setCreatedDate(new Date());
            comment.setEntityType(EntityType.ENTITY_COMMENT);
            comment.setEntityId(questionId);
            commentService.addComment(comment);
        }catch (Exception e){
            logger.error("添加评论错误" + e.getMessage());
        }
        return "redirect:/question/"+questionId;
    }
}
