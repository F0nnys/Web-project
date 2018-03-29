package com.roy.wenda.async.handler;

import com.roy.wenda.async.EventHandler;
import com.roy.wenda.async.EventModel;
import com.roy.wenda.async.EventType;
import com.roy.wenda.model.Message;
import com.roy.wenda.model.User;
import com.roy.wenda.service.MessageService;
import com.roy.wenda.service.UserService;
import com.roy.wenda.wendaUtil.WendaUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Component
public class LikeHandler implements EventHandler{

    @Autowired
    MessageService messageService;

    @Autowired
    UserService userService;


    @Override
    public void doHandle(EventModel eventModel) {
        Message message = new Message();
        message.setFromId(WendaUtil.SYSTEM_USERID);
        message.setToId(eventModel.getEntityOwnerId());
        message.setCreatedDate(new Date());
        User user = userService.getUser(eventModel.getActorId());
        message.setContent("用户"+user.getName()+"赞了你的评论，ht" +
                "tp://127.0.0.1:8080/question/"+eventModel.getExt("questionId"));
        messageService.addMessage(message);
    }

    @Override
    public List<EventType> getSupportEventTypes() {
        return Arrays.asList(EventType.LIKE);
    }
}
