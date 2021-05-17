1. spring bean依赖注入：禁止直接使用@Autowired在成员变量上注入。\
    必须在构造方法或者set方法注入

2. mybatis-plus：不需要展示到前端的字段添加@TableField(select = false)。\
    如果需要查询所有字段通过QueryWrapper.select(field -> true)查询

3. 断言使用cn.tucci.management.core.util.Assert。\
    这个类会统一抛出BusinessException，只需要捕获BusinessException

4. 统一异常处理GlobalExceptionHandler。\
    除了404会有页面返回，其他错误返回json（因为其他错误没有好的页面模板）
    
5. 不使用mybatis-plus的IService。

6. domain使用mybatis-plus填充createTime、updatedTime

7. 操作日志记录@Log只获取方法中的第一个参数，所以尽量将参数封装

8. 定时任务：\
    8.1 项目启动就开启的定时任务，通过SchedulingConfig#startSysTaskListener关闭 \
    8.2 定时任务启动的存储在scheduledFutureMap中，启动状态不存储数据库，根据这个map判断，所以启动停止都需要修改这个map \
    8.3 定时任务需要继承AbstractTask \
    8.4 定时任务bean需要加载到spring环境中