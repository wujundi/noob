package wujundi.noob.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import wujundi.noob.dao.TaskMapper;
import wujundi.noob.model.Task;

import java.util.List;

@Service
public class TaskService {

    @Autowired
    TaskMapper taskMapper;

    public int insertTask(String task_name){
        return taskMapper.insertTask(task_name);
    }


    public Task selectById(int id){
        return taskMapper.selectById(id);
    }


    public List<Task> selectAll(){
        return taskMapper.selectAll();
    }


    public int updateById(int id ,String name){
        return taskMapper.updateById(id, name);
    }


    public int deleteById(int id){
        return taskMapper.deleteById(id);
    }


}
