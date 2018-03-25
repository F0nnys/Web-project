package com.roy.wenda;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.roy.wenda.dao.QuestionDAO;
import com.roy.wenda.dao.UserDAO;
import com.roy.wenda.model.Question;
import com.roy.wenda.model.User;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Date;
import java.util.Random;


@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = WendaApplication.class)
@Sql("/init-schema.sql")
public class InitDatabaseTests {
    @Autowired
    UserDAO userDAO;

    @Autowired
    QuestionDAO questionDAO;

	@Test
	public void initDatabase() {
        Random rd = new Random();
        for(int i=0;i<10;i++) {
            User user = new User();
            user.setHeadUrl(String.format("http://images.nowcoder.com/head/%dt.png", rd.nextInt(1000)));
            user.setName(String.format("User%d", i));
            user.setPassword("");
            user.setSalt("");
            userDAO.addUser(user);

            user.setPassword(String.format("xxx%s", String.valueOf(i * i)));
            userDAO.updatePassword(user);

            Question question = new Question();
            question.setCommentCount(i);
            Date date = new Date();
            date.setTime(date.getTime() + 1000 * 3600 * i);
            question.setCreatedDate(date);
            question.setUserId(i + 1);
            question.setTitle(String.format("Title:%d", i));
            question.setContent(String.format("content:%d", i));
            questionDAO.addQuestion(question);
        }
        for(Question question:questionDAO.selectLatestQuestions(0,0,10)){
            System.out.println(question.getTitle()+"<br>");
        }
//        Assert.assertEquals("xxx0",userDAO.selectById(1).getPassword());
//        userDAO.deleteById(1);
//        Assert.assertNull(userDAO.selectById(1));
	}

}
