package wujundi.noob.dao;

import org.apache.ibatis.annotations.*;
import wujundi.noob.model.Task;

import java.util.List;

@Mapper
public interface TaskMapper {

    String TABLE_NAME = "task";

    @Insert({"insert into ", TABLE_NAME
            ," (task_name) values" +
            " (#{task_name})"})
    int insertTask(String task_name);


    @Select({"select" +
            " id" +
            " ,task_name as name" +
            " from ", TABLE_NAME,
            " where id = #{id}"})
    Task selectById(int id);


    @Select({"select" +
            " id" +
            " ,task_name as name" +
            " from ", TABLE_NAME})
    List<Task> selectAll();


    @Update({"update ", TABLE_NAME
            ," set task_name = #{name}" +
            " where id=#{id}"})
    int updateById(@Param("id") int id, @Param("name") String name);


    @Delete({"delete from ", TABLE_NAME
            , " where id=#{id}"})
    int deleteById(@Param("id") int id);

}
