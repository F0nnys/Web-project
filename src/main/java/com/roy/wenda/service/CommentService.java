package com.roy.wenda.service;

import com.roy.wenda.dao.CommentDAO;
import com.roy.wenda.model.Comment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.util.HtmlUtils;

import java.util.List;

@Service
public class CommentService {
    @Autowired
    CommentDAO commentDAO;

    @Autowired
    sensitiveService sensitiveService;

    public List<Comment> getCommentsByEntity(int entityId,int entityType){
        return commentDAO.selectCommentByEntity(entityId,entityType);
    }

    public int addComment(Comment comment){
        comment.setContent(HtmlUtils.htmlEscape(comment.getContent()));
        comment.setContent(sensitiveService.filter(comment.getContent()));
        return commentDAO.addComment(comment) > 0?comment.getId():0;
    }

    public int getCommentCount(int entityId,int entityType){
        return commentDAO.getCommentCount(entityId,entityType);
    }

    public boolean deleteComment(int commentId){
        return commentDAO.deleteComment(commentId,1) > 0;
    }
}