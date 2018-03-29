package com.roy.wenda.controller;

import com.roy.wenda.async.EventModel;
import com.roy.wenda.async.EventProducer;
import com.roy.wenda.async.EventType;
import com.roy.wenda.model.Comment;
import com.roy.wenda.model.EntityType;
import com.roy.wenda.model.HostHolder;
import com.roy.wenda.service.CommentService;
import com.roy.wenda.service.LikeService;
import com.roy.wenda.wendaUtil.WendaUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class LikeController {
    @Autowired
    HostHolder hostHolder;
    @Autowired
    LikeService likeService;
    @Autowired
    EventProducer eventProducer;
    @Autowired
    CommentService commentService;

    @RequestMapping(path = {"/like"}, method = RequestMethod.POST)
    @ResponseBody
    public String like(@RequestParam("commentId") int commentId) {
        if(hostHolder.getUser() == null){
            return WendaUtil.getJSONString(999);
        }
        Comment comment = commentService.getCommentById(commentId);
        eventProducer.fireEvent(new EventModel(EventType.LIKE).setActorId(hostHolder.getUser().getId())
        .setEntityId(commentId).setEntityType(EntityType.ENTITY_COMMENT)
        .setExt("questionId",String.valueOf(comment.getEntityId()))
        .setEntityOwnerId(comment.getUserId()));
        long likeCount = likeService.like(hostHolder.getUser().getId(), EntityType.ENTITY_COMMENT,commentId);
        return WendaUtil.getJSONString(0,String.valueOf(likeCount));
    }

    @RequestMapping(path = {"/dislike"}, method = RequestMethod.POST)
    @ResponseBody
    public String dislike(@RequestParam("commentId") int commentId) {
        if(hostHolder.getUser() == null){
            return WendaUtil.getJSONString(999);
        }
        long dislikeCount = likeService.dislike(hostHolder.getUser().getId(), EntityType.ENTITY_COMMENT,commentId);
        return WendaUtil.getJSONString(0,String.valueOf(dislikeCount));
    }
}
