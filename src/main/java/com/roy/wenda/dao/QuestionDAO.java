package com.roy.wenda.dao;


import com.roy.wenda.model.Question;
import com.roy.wenda.model.User;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface QuestionDAO {
    String TABLE_NAME = "question";
    String INSERT_FIELDS = "title,content,user_id,created_date,comment_count";
    String SELECT_FIELDS = "id,"+INSERT_FIELDS;

    @Insert({"insert into ",TABLE_NAME," ( ",INSERT_FIELDS," ) values(#{title}," +
            "#{content},#{userId},#{createdDate},#{commentCount})"})
    int addQuestion(Question question);

    List<Question> selectLatestQuestions(@Param("userId") int userId,
                                        @Param("offset") int offset,
                                        @Param("limit") int limit);

}
