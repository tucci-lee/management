package cn.tucci.management.core.task;

import cn.tucci.management.model.dao.task.TaskRunLogMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author tucci.lee
 */
@Component
public class TestTask extends LogTask{

    @Autowired
    public TestTask(TaskRunLogMapper taskRunLogMapper) {
        super(taskRunLogMapper);
    }

    @Override
    protected String execute() {
        System.out.println(111);
        return "ok";
    }
}
