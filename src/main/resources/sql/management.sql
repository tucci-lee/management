/*
 Navicat Premium Data Transfer

 Source Server         : localhost
 Source Server Type    : MySQL
 Source Server Version : 50734
 Source Host           : localhost:3306
 Source Schema         : management

 Target Server Type    : MySQL
 Target Server Version : 50734
 File Encoding         : 65001

 Date: 17/05/2021 13:57:59
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for sys_dept
-- ----------------------------
DROP TABLE IF EXISTS `sys_dept`;
CREATE TABLE `sys_dept` (
  `id` bigint(20) NOT NULL,
  `name` varchar(10) NOT NULL COMMENT '部门名称',
  `pid` bigint(20) NOT NULL DEFAULT '0' COMMENT '父级id，0是顶级部门',
  `level` varchar(200) NOT NULL DEFAULT '0' COMMENT '层级，便于删除时将子级删除',
  `seq` int(3) NOT NULL DEFAULT '0' COMMENT '排序',
  `manager` varchar(10) NOT NULL COMMENT '部门管理人',
  `manager_phone` varchar(11) NOT NULL COMMENT '管理人手机',
  `creator` bigint(20) NOT NULL COMMENT '创建人',
  `create_time` bigint(13) NOT NULL COMMENT '创建时间',
  `updater` bigint(20) DEFAULT NULL COMMENT '修改人',
  `updated_time` bigint(13) DEFAULT NULL COMMENT '修改时间',
  `is_deleted` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否删除\n0-未删除\n1-删除',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='部门表，一个用户只能绑定一个部门';

-- ----------------------------
-- Records of sys_dept
-- ----------------------------
BEGIN;
INSERT INTO `sys_dept` VALUES (112963130878853120, '开发部', 0, '0', 0, '张三', '17777777777', 1, 1610506983637, NULL, NULL, 0);
INSERT INTO `sys_dept` VALUES (113678253725581312, '后端', 112963130878853120, '0,112963130878853120', 1, '李四', '18888888888', 1, 1610677482209, 1, 1620808384656, 0);
INSERT INTO `sys_dept` VALUES (156084981477670913, '前端', 112963130878853120, '0,112963130878853120', 0, '王五', '16666666666', 1, 1620788033984, 1, 1620808343329, 0);
COMMIT;

-- ----------------------------
-- Table structure for log_login
-- ----------------------------
DROP TABLE IF EXISTS `log_login`;
CREATE TABLE `log_login` (
  `id` bigint(20) NOT NULL,
  `account` varchar(10) NOT NULL COMMENT '登录账号',
  `os` varchar(20) NOT NULL COMMENT '操作系统',
  `browser` varchar(20) NOT NULL COMMENT '浏览器',
  `ip` varchar(15) NOT NULL COMMENT 'ip地址',
  `create_time` bigint(13) NOT NULL COMMENT '登录时间',
  `status` bit(1) NOT NULL DEFAULT b'0' COMMENT '登录状态\n0-失败\n1-成功',
  `msg` varchar(500) DEFAULT NULL COMMENT '信息',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='登录日志';

-- ----------------------------
-- Records of log_login
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for log_operation
-- ----------------------------
DROP TABLE IF EXISTS `log_operation`;
CREATE TABLE `log_operation` (
  `id` bigint(20) NOT NULL,
  `uid` bigint(20) NOT NULL COMMENT '操作人id',
  `account` varchar(10) NOT NULL COMMENT '操作人账号',
  `ip` varchar(15) NOT NULL COMMENT '用户ip',
  `url` varchar(500) NOT NULL COMMENT '请求URL',
  `method` varchar(500) NOT NULL COMMENT '执行方法',
  `params` varchar(1000) DEFAULT NULL COMMENT '参数',
  `result` varchar(1000) DEFAULT NULL COMMENT '执行成功后的返回信息',
  `description` varchar(20) NOT NULL COMMENT '描述',
  `err_msg` varchar(500) DEFAULT NULL COMMENT '执行失败后的异常信息',
  `create_time` bigint(13) NOT NULL COMMENT '创建时间',
  `status` bit(1) NOT NULL COMMENT '执行状态\n0-失败\n1-成功',
  PRIMARY KEY (`id`) USING BTREE,
  KEY `uid` (`uid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of log_operation
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for sys_res
-- ----------------------------
DROP TABLE IF EXISTS `sys_res`;
CREATE TABLE `sys_res` (
  `id` bigint(20) NOT NULL,
  `name` varchar(10) NOT NULL COMMENT '资源名称',
  `type` int(1) NOT NULL COMMENT '类型\n1-菜单\n2-权限',
  `url` varchar(100) DEFAULT NULL COMMENT 'url',
  `pid` bigint(20) NOT NULL DEFAULT '0' COMMENT '父级id，0是顶级目录',
  `level` varchar(200) NOT NULL DEFAULT '0' COMMENT '层级，便于删除时将子级删除，判断是否向下级修改',
  `res_char` varchar(50) DEFAULT NULL COMMENT '资源字符',
  `icon` varchar(100) DEFAULT NULL COMMENT '图标',
  `seq` int(3) NOT NULL DEFAULT '0' COMMENT '排序',
  `creator` bigint(20) NOT NULL COMMENT '创建人',
  `create_time` bigint(13) NOT NULL COMMENT '创建时间',
  `updater` bigint(20) DEFAULT NULL COMMENT '修改人',
  `updated_time` bigint(13) DEFAULT NULL COMMENT '修改时间',
  `is_deleted` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否删除\n0-未删除\n1-删除',
  PRIMARY KEY (`id`) USING BTREE,
  KEY `level` (`level`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='资源';

-- ----------------------------
-- Records of sys_res
-- ----------------------------
BEGIN;
INSERT INTO `sys_res` VALUES (112063924660076544, '系统管理', 1, NULL, 0, '0', NULL, NULL, 0, 1, 1610292596000, NULL, NULL, 0);
INSERT INTO `sys_res` VALUES (112064419969630208, '资源管理', 1, '/sys/res', 112063924660076544, '0,112063924660076544', NULL, NULL, 2, 1, 1610292714000, NULL, NULL, 0);
INSERT INTO `sys_res` VALUES (112064548923506688, '角色管理', 1, '/sys/role', 112063924660076544, '0,112063924660076544', NULL, NULL, 1, 1, 1610292745000, NULL, NULL, 0);
INSERT INTO `sys_res` VALUES (112065408583860224, '用户管理', 1, '/sys/user', 112063924660076544, '0,112063924660076544', NULL, NULL, 0, 1, 1610292949000, NULL, NULL, 0);
INSERT INTO `sys_res` VALUES (112304735788204032, '资源查询', 2, NULL, 112064419969630208, '0,112063924660076544,112064419969630208', 'sys:res:list', NULL, 0, 1, 1610350010000, NULL, NULL, 0);
INSERT INTO `sys_res` VALUES (112304837034508288, '资源添加', 2, NULL, 112064419969630208, '0,112063924660076544,112064419969630208', 'sys:res:add', NULL, 0, 1, 1610350034000, NULL, NULL, 0);
INSERT INTO `sys_res` VALUES (112304921109331968, '资源修改', 2, NULL, 112064419969630208, '0,112063924660076544,112064419969630208', 'sys:res:edit', NULL, 0, 1, 1610350054000, NULL, NULL, 0);
INSERT INTO `sys_res` VALUES (112305002013261824, '资源删除', 2, NULL, 112064419969630208, '0,112063924660076544,112064419969630208', 'sys:res:delete', NULL, 0, 1, 1610350073000, NULL, NULL, 0);
INSERT INTO `sys_res` VALUES (112766904753455104, '部门管理', 1, '/sys/dept', 112063924660076544, '0,112063924660076544', NULL, NULL, 3, 1, 1610460199683, NULL, NULL, 0);
INSERT INTO `sys_res` VALUES (113296483343663104, '用户查询', 2, NULL, 112065408583860224, '0,112063924660076544,112065408583860224', 'sys:user:list', NULL, 0, 1, 1610586461058, NULL, NULL, 0);
INSERT INTO `sys_res` VALUES (113296609537687552, '用户添加', 2, NULL, 112065408583860224, '0,112063924660076544,112065408583860224', 'sys:user:add', NULL, 0, 1, 1610586491144, NULL, NULL, 0);
INSERT INTO `sys_res` VALUES (113296742077693952, '用户修改', 2, NULL, 112065408583860224, '0,112063924660076544,112065408583860224', 'sys:user:edit', NULL, 0, 1, 1610586522744, NULL, NULL, 0);
INSERT INTO `sys_res` VALUES (113296815033417728, '用户删除', 2, NULL, 112065408583860224, '0,112063924660076544,112065408583860224', 'sys:user:delete', NULL, 0, 1, 1610586540138, NULL, NULL, 0);
INSERT INTO `sys_res` VALUES (113296905349365760, '用户锁定', 2, NULL, 112065408583860224, '0,112063924660076544,112065408583860224', 'sys:user:editLock', NULL, 0, 1, 1610586561671, NULL, NULL, 0);
INSERT INTO `sys_res` VALUES (113297095770767360, '密码修改', 2, NULL, 112065408583860224, '0,112063924660076544,112065408583860224', 'sys:user:editPwd', NULL, 0, 1, 1610586607071, NULL, NULL, 0);
INSERT INTO `sys_res` VALUES (113297279363842048, '角色查询', 2, NULL, 112064548923506688, '0,112063924660076544,112064548923506688', 'sys:role:list', NULL, 0, 1, 1610586650843, NULL, NULL, 0);
INSERT INTO `sys_res` VALUES (113297348314005504, '角色添加', 2, NULL, 112064548923506688, '0,112063924660076544,112064548923506688', 'sys:role:add', NULL, 0, 1, 1610586667282, NULL, NULL, 0);
INSERT INTO `sys_res` VALUES (113297416005877760, '角色修改', 2, NULL, 112064548923506688, '0,112063924660076544,112064548923506688', 'sys:role:edit', NULL, 0, 1, 1610586683421, NULL, NULL, 0);
INSERT INTO `sys_res` VALUES (113297479004323840, '角色删除', 2, NULL, 112064548923506688, '0,112063924660076544,112064548923506688', 'sys:role:delete', NULL, 0, 1, 1610586698441, NULL, NULL, 0);
INSERT INTO `sys_res` VALUES (113297703277953024, '部门查询', 2, NULL, 112766904753455104, '0,112063924660076544,112766904753455104', 'sys:dept:list', NULL, 0, 1, 1610586751912, NULL, NULL, 0);
INSERT INTO `sys_res` VALUES (113297768126087168, '部门添加', 2, NULL, 112766904753455104, '0,112063924660076544,112766904753455104', 'sys:dept:add', NULL, 0, 1, 1610586767373, NULL, NULL, 0);
INSERT INTO `sys_res` VALUES (113297841698373632, '部门修改', 2, NULL, 112766904753455104, '0,112063924660076544,112766904753455104', 'sys:dept:edit', NULL, 0, 1, 1610586784914, NULL, NULL, 0);
INSERT INTO `sys_res` VALUES (113297895578402816, '部门删除', 2, NULL, 112766904753455104, '0,112063924660076544,112766904753455104', 'sys:dept:delete', NULL, 0, 1, 1610586797761, NULL, NULL, 0);
INSERT INTO `sys_res` VALUES (113326873903104000, '日志管理', 1, NULL, 0, '0', NULL, NULL, 0, 1, 1610593706732, NULL, NULL, 0);
INSERT INTO `sys_res` VALUES (113327217995415552, '登陆日志', 1, '/log/login', 113326873903104000, '0,113326873903104000', NULL, NULL, 0, 1, 1610593788770, NULL, NULL, 0);
INSERT INTO `sys_res` VALUES (113328033569439744, '操作日志', 1, '/log/operation', 113326873903104000, '0,113326873903104000', NULL, NULL, 0, 1, 1610593983218, NULL, NULL, 0);
INSERT INTO `sys_res` VALUES (113329345681948672, '日志查询', 2, NULL, 113327217995415552, '0,113326873903104000,113327217995415552', 'log:login:list', NULL, 0, 1, 1610594296049, NULL, NULL, 0);
INSERT INTO `sys_res` VALUES (113329497700302848, '日志查询', 2, NULL, 113328033569439744, '0,113326873903104000,113328033569439744', 'log:operation:list', NULL, 0, 1, 1610594332293, NULL, NULL, 0);
INSERT INTO `sys_res` VALUES (113329647516647424, '监控管理', 1, NULL, 0, '0', NULL, NULL, 0, 1, 1610594368012, NULL, NULL, 0);
INSERT INTO `sys_res` VALUES (113329855000477696, 'druid监控', 1, '/monitor/druid', 113329647516647424, '0,113329647516647424', NULL, NULL, 0, 1, 1610594417480, NULL, NULL, 0);
INSERT INTO `sys_res` VALUES (113329976727568384, '监控查询', 2, NULL, 113329855000477696, '0,113329647516647424,113329855000477696', 'monitor:druid:list', NULL, 0, 1, 1610594446502, NULL, NULL, 0);
INSERT INTO `sys_res` VALUES (115137395384385536, 'Session监控', 1, '/monitor/session', 113329647516647424, '0,113329647516647424', NULL, NULL, 0, 1, 1611025368690, NULL, NULL, 0);
INSERT INTO `sys_res` VALUES (115138272526598144, 'session查询', 2, NULL, 115137395384385536, '0,113329647516647424,115137395384385536', 'monitor:session:list', NULL, 0, 1, 1611025577817, NULL, NULL, 0);
INSERT INTO `sys_res` VALUES (118103129718784000, 'session删除', 2, NULL, 115137395384385536, '0,113329647516647424,115137395384385536', 'monitor:session:delete', NULL, 0, 1, 1611732454856, NULL, NULL, 0);
INSERT INTO `sys_res` VALUES (156904536689082369, '定时任务', 1, '', 0, '0', NULL, NULL, 0, 1, 1620983431173, NULL, NULL, 0);
INSERT INTO `sys_res` VALUES (157940210242420737, '定时任务查询', 2, NULL, 157941337184796673, '0,156904536689082369,157941337184796673', 'task:schedule:list', NULL, 0, 1, 1621230354990, NULL, NULL, 0);
INSERT INTO `sys_res` VALUES (157941337184796673, '定时任务', 1, '/task/schedule', 156904536689082369, '0,156904536689082369', NULL, NULL, 0, 1, 1621230623674, NULL, NULL, 0);
INSERT INTO `sys_res` VALUES (157941556685307904, '定时任务修改', 2, NULL, 157941337184796673, '0,156904536689082369,157941337184796673', 'task:schedule:edit', NULL, 0, 1, 1621230676007, NULL, NULL, 0);
INSERT INTO `sys_res` VALUES (157941621902540800, '定时任务添加', 2, NULL, 157941337184796673, '0,156904536689082369,157941337184796673', 'task:schedule:add', NULL, 0, 1, 1621230691556, NULL, NULL, 0);
INSERT INTO `sys_res` VALUES (157941694925373441, '定时任务删除', 2, NULL, 157941337184796673, '0,156904536689082369,157941337184796673', 'task:schedule:delete', NULL, 0, 1, 1621230708966, NULL, NULL, 0);
INSERT INTO `sys_res` VALUES (157941808926556160, '定时任务状态', 2, NULL, 157941337184796673, '0,156904536689082369,157941337184796673', 'task:schedule:editStatus', NULL, 0, 1, 1621230736146, NULL, NULL, 0);
COMMIT;

-- ----------------------------
-- Table structure for sys_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE `sys_role` (
  `id` bigint(20) NOT NULL,
  `role_char` varchar(20) DEFAULT NULL COMMENT '角色字符',
  `name` varchar(50) NOT NULL COMMENT '角色名',
  `remarks` varchar(200) DEFAULT NULL COMMENT '备注',
  `creator` bigint(20) NOT NULL COMMENT '创建人',
  `create_time` bigint(13) NOT NULL COMMENT '创建时间',
  `updater` bigint(20) DEFAULT NULL COMMENT '修改人',
  `updated_time` bigint(13) DEFAULT NULL COMMENT '修改时间',
  `is_deleted` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否删除\n0-未删除\n1-删除',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='角色';

-- ----------------------------
-- Records of sys_role
-- ----------------------------
BEGIN;
INSERT INTO `sys_role` VALUES (112650695508754432, 'root', '系统管理员', '', 1, 1610432493240, NULL, NULL, 0);
INSERT INTO `sys_role` VALUES (156436743262830593, 'test', '测试角色', NULL, 1, 1620871900532, NULL, NULL, 0);
COMMIT;

-- ----------------------------
-- Table structure for sys_role_res
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_res`;
CREATE TABLE `sys_role_res` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `role_id` bigint(20) NOT NULL COMMENT '角色id',
  `res_id` bigint(20) NOT NULL COMMENT '资源id',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `role_res` (`role_id`,`res_id`) USING BTREE,
  KEY `res_id` (`res_id`) USING BTREE,
  KEY `role_id` (`role_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=725 DEFAULT CHARSET=utf8mb4 COMMENT='角色与资源关联表';

-- ----------------------------
-- Records of sys_role_res
-- ----------------------------
BEGIN;
INSERT INTO `sys_res` VALUES (112063924660076544, '系统管理', 1, NULL, 0, '0', NULL, NULL, 0, 1, 1610292596000, NULL, NULL, 0);
INSERT INTO `sys_res` VALUES (112064419969630208, '资源管理', 1, '/sys/res', 112063924660076544, '0,112063924660076544', NULL, NULL, 2, 1, 1610292714000, NULL, NULL, 0);
INSERT INTO `sys_res` VALUES (112064548923506688, '角色管理', 1, '/sys/role', 112063924660076544, '0,112063924660076544', NULL, NULL, 1, 1, 1610292745000, NULL, NULL, 0);
INSERT INTO `sys_res` VALUES (112065408583860224, '用户管理', 1, '/sys/user', 112063924660076544, '0,112063924660076544', NULL, NULL, 0, 1, 1610292949000, NULL, NULL, 0);
INSERT INTO `sys_res` VALUES (112304735788204032, '资源查询', 2, NULL, 112064419969630208, '0,112063924660076544,112064419969630208', 'sys:res:list', NULL, 0, 1, 1610350010000, NULL, NULL, 0);
INSERT INTO `sys_res` VALUES (112304837034508288, '资源添加', 2, NULL, 112064419969630208, '0,112063924660076544,112064419969630208', 'sys:res:add', NULL, 0, 1, 1610350034000, NULL, NULL, 0);
INSERT INTO `sys_res` VALUES (112304921109331968, '资源修改', 2, NULL, 112064419969630208, '0,112063924660076544,112064419969630208', 'sys:res:edit', NULL, 0, 1, 1610350054000, NULL, NULL, 0);
INSERT INTO `sys_res` VALUES (112305002013261824, '资源删除', 2, NULL, 112064419969630208, '0,112063924660076544,112064419969630208', 'sys:res:delete', NULL, 0, 1, 1610350073000, NULL, NULL, 0);
INSERT INTO `sys_res` VALUES (112766904753455104, '部门管理', 1, '/sys/dept', 112063924660076544, '0,112063924660076544', NULL, NULL, 3, 1, 1610460199683, NULL, NULL, 0);
INSERT INTO `sys_res` VALUES (113296483343663104, '用户查询', 2, NULL, 112065408583860224, '0,112063924660076544,112065408583860224', 'sys:user:list', NULL, 0, 1, 1610586461058, NULL, NULL, 0);
INSERT INTO `sys_res` VALUES (113296609537687552, '用户添加', 2, NULL, 112065408583860224, '0,112063924660076544,112065408583860224', 'sys:user:add', NULL, 0, 1, 1610586491144, NULL, NULL, 0);
INSERT INTO `sys_res` VALUES (113296742077693952, '用户修改', 2, NULL, 112065408583860224, '0,112063924660076544,112065408583860224', 'sys:user:edit', NULL, 0, 1, 1610586522744, NULL, NULL, 0);
INSERT INTO `sys_res` VALUES (113296815033417728, '用户删除', 2, NULL, 112065408583860224, '0,112063924660076544,112065408583860224', 'sys:user:delete', NULL, 0, 1, 1610586540138, NULL, NULL, 0);
INSERT INTO `sys_res` VALUES (113296905349365760, '用户锁定', 2, NULL, 112065408583860224, '0,112063924660076544,112065408583860224', 'sys:user:editLock', NULL, 0, 1, 1610586561671, NULL, NULL, 0);
INSERT INTO `sys_res` VALUES (113297095770767360, '密码修改', 2, NULL, 112065408583860224, '0,112063924660076544,112065408583860224', 'sys:user:editPwd', NULL, 0, 1, 1610586607071, NULL, NULL, 0);
INSERT INTO `sys_res` VALUES (113297279363842048, '角色查询', 2, NULL, 112064548923506688, '0,112063924660076544,112064548923506688', 'sys:role:list', NULL, 0, 1, 1610586650843, NULL, NULL, 0);
INSERT INTO `sys_res` VALUES (113297348314005504, '角色添加', 2, NULL, 112064548923506688, '0,112063924660076544,112064548923506688', 'sys:role:add', NULL, 0, 1, 1610586667282, NULL, NULL, 0);
INSERT INTO `sys_res` VALUES (113297416005877760, '角色修改', 2, NULL, 112064548923506688, '0,112063924660076544,112064548923506688', 'sys:role:edit', NULL, 0, 1, 1610586683421, NULL, NULL, 0);
INSERT INTO `sys_res` VALUES (113297479004323840, '角色删除', 2, NULL, 112064548923506688, '0,112063924660076544,112064548923506688', 'sys:role:delete', NULL, 0, 1, 1610586698441, NULL, NULL, 0);
INSERT INTO `sys_res` VALUES (113297703277953024, '部门查询', 2, NULL, 112766904753455104, '0,112063924660076544,112766904753455104', 'sys:dept:list', NULL, 0, 1, 1610586751912, NULL, NULL, 0);
INSERT INTO `sys_res` VALUES (113297768126087168, '部门添加', 2, NULL, 112766904753455104, '0,112063924660076544,112766904753455104', 'sys:dept:add', NULL, 0, 1, 1610586767373, NULL, NULL, 0);
INSERT INTO `sys_res` VALUES (113297841698373632, '部门修改', 2, NULL, 112766904753455104, '0,112063924660076544,112766904753455104', 'sys:dept:edit', NULL, 0, 1, 1610586784914, NULL, NULL, 0);
INSERT INTO `sys_res` VALUES (113297895578402816, '部门删除', 2, NULL, 112766904753455104, '0,112063924660076544,112766904753455104', 'sys:dept:delete', NULL, 0, 1, 1610586797761, NULL, NULL, 0);
INSERT INTO `sys_res` VALUES (113326873903104000, '日志管理', 1, NULL, 0, '0', NULL, NULL, 0, 1, 1610593706732, NULL, NULL, 0);
INSERT INTO `sys_res` VALUES (113327217995415552, '登陆日志', 1, '/log/login', 113326873903104000, '0,113326873903104000', NULL, NULL, 0, 1, 1610593788770, NULL, NULL, 0);
INSERT INTO `sys_res` VALUES (113328033569439744, '操作日志', 1, '/log/operation', 113326873903104000, '0,113326873903104000', NULL, NULL, 0, 1, 1610593983218, NULL, NULL, 0);
INSERT INTO `sys_res` VALUES (113329345681948672, '日志查询', 2, NULL, 113327217995415552, '0,113326873903104000,113327217995415552', 'log:login:list', NULL, 0, 1, 1610594296049, NULL, NULL, 0);
INSERT INTO `sys_res` VALUES (113329497700302848, '日志查询', 2, NULL, 113328033569439744, '0,113326873903104000,113328033569439744', 'log:operation:list', NULL, 0, 1, 1610594332293, NULL, NULL, 0);
INSERT INTO `sys_res` VALUES (113329647516647424, '监控管理', 1, NULL, 0, '0', NULL, NULL, 0, 1, 1610594368012, NULL, NULL, 0);
INSERT INTO `sys_res` VALUES (113329855000477696, 'druid监控', 1, '/monitor/druid', 113329647516647424, '0,113329647516647424', NULL, NULL, 0, 1, 1610594417480, NULL, NULL, 0);
INSERT INTO `sys_res` VALUES (113329976727568384, '监控查询', 2, NULL, 113329855000477696, '0,113329647516647424,113329855000477696', 'monitor:druid:list', NULL, 0, 1, 1610594446502, NULL, NULL, 0);
INSERT INTO `sys_res` VALUES (115137395384385536, 'Session监控', 1, '/monitor/session', 113329647516647424, '0,113329647516647424', NULL, NULL, 0, 1, 1611025368690, NULL, NULL, 0);
INSERT INTO `sys_res` VALUES (115138272526598144, 'session查询', 2, NULL, 115137395384385536, '0,113329647516647424,115137395384385536', 'monitor:session:list', NULL, 0, 1, 1611025577817, NULL, NULL, 0);
INSERT INTO `sys_res` VALUES (118103129718784000, 'session删除', 2, NULL, 115137395384385536, '0,113329647516647424,115137395384385536', 'monitor:session:delete', NULL, 0, 1, 1611732454856, NULL, NULL, 0);
INSERT INTO `sys_res` VALUES (157940210242420737, '定时任务查询', 2, NULL, 157941337184796673, '0,156904536689082369,157941337184796673', 'monitor:task:list', NULL, 0, 1, 1621230354990, NULL, NULL, 0);
INSERT INTO `sys_res` VALUES (157941337184796673, '定时任务', 1, '/monitor/task', 113329647516647424, '0,113329647516647424', NULL, NULL, 0, 1, 1621230623674, NULL, NULL, 0);
INSERT INTO `sys_res` VALUES (157941556685307904, '定时任务修改', 2, NULL, 157941337184796673, '0,113329647516647424,157941337184796673', 'monitor:task:edit', NULL, 0, 1, 1621230676007, NULL, NULL, 0);
INSERT INTO `sys_res` VALUES (157941621902540800, '定时任务添加', 2, NULL, 157941337184796673, '0,113329647516647424,157941337184796673', 'monitor:task:add', NULL, 0, 1, 1621230691556, NULL, NULL, 0);
INSERT INTO `sys_res` VALUES (157941694925373441, '定时任务删除', 2, NULL, 157941337184796673, '0,113329647516647424,157941337184796673', 'monitor:task:delete', NULL, 0, 1, 1621230708966, NULL, NULL, 0);
INSERT INTO `sys_res` VALUES (157941808926556160, '定时任务状态', 2, NULL, 157941337184796673, '0,113329647516647424,157941337184796673', 'monitor:task:editStatus', NULL, 0, 1, 1621230736146, NULL, NULL, 0);COMMIT;
COMMIT;
-- ----------------------------
-- Table structure for sys_user
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user` (
  `uid` bigint(20) NOT NULL,
  `account` varchar(10) NOT NULL COMMENT '账号',
  `password` varchar(60) NOT NULL COMMENT '密码',
  `phone` varchar(11) NOT NULL COMMENT '手机',
  `email` varchar(50) DEFAULT NULL COMMENT '邮箱',
  `is_lock` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否锁定\n0-未锁定\n1-锁定',
  `nickname` varchar(10) DEFAULT NULL COMMENT '昵称',
  `remarks` varchar(200) DEFAULT NULL COMMENT '备注',
  `dept_id` bigint(20) DEFAULT NULL COMMENT '所属部门id',
  `creator` bigint(20) NOT NULL COMMENT '创建人',
  `create_time` bigint(13) NOT NULL COMMENT '创建时间',
  `updater` bigint(20) DEFAULT NULL COMMENT '修改人',
  `updated_time` bigint(13) DEFAULT NULL COMMENT '修改时间',
  `is_deleted` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否删除\n0-未删除\n1-删除',
  PRIMARY KEY (`uid`) USING BTREE,
  KEY `dept_id` (`dept_id`) USING BTREE,
  KEY `account` (`account`),
  KEY `phone` (`phone`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户基本信息';

-- ----------------------------
-- Records of sys_user
-- ----------------------------
BEGIN;
INSERT INTO `sys_user` VALUES (1, 'root', '$2a$10$R.3BHsmpmhytjKTeChYK0uIhI307huBx5LmJxR70KDXFKi9eW2rCq', '17777777777', NULL, 0, '管理员', '系统管理员', 113678253725581312, 1, 1609852591000, 1, 1620873383339, 0);
INSERT INTO `sys_user` VALUES (156234307189342208, 'test', '$2a$10$glHRq0phcLWfRDIEbUDok.k6anPimO1VCq2uhYIxG7eeSaMlvUS3y', '13333333333', NULL, 0, '测试', '11', 156084981477670913, 1, 1620823636015, 1, 1620872262470, 0);
COMMIT;

-- ----------------------------
-- Table structure for sys_user_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_role`;
CREATE TABLE `sys_user_role` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `uid` bigint(20) NOT NULL COMMENT '用户id',
  `role_id` bigint(20) NOT NULL COMMENT '角色id',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `user_role` (`uid`,`role_id`) USING BTREE,
  KEY `uid` (`uid`) USING BTREE,
  KEY `role_id` (`role_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COMMENT='用户关联的角色';

-- ----------------------------
-- Records of sys_user_role
-- ----------------------------
BEGIN;
INSERT INTO `sys_user_role` VALUES (1, 1, 112650695508754432);
INSERT INTO `sys_user_role` VALUES (3, 156234307189342208, 156436743262830593);
COMMIT;

-- ----------------------------
-- Table structure for task_run_log
-- ----------------------------
DROP TABLE IF EXISTS `task_run_log`;
CREATE TABLE `task_run_log` (
  `id` bigint(20) NOT NULL,
  `task_schedule_id` bigint(20) NOT NULL COMMENT '定时任务id',
  `status` tinyint(1) NOT NULL COMMENT '运行状态',
  `msg` varchar(1000) NOT NULL COMMENT '运行信息',
  `run_time` bigint(13) NOT NULL COMMENT '执行时间',
  `create_time` bigint(13) NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE,
  KEY `task_schedule_id` (`task_schedule_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='定时任务执行日志';

-- ----------------------------
-- Records of task_run_log
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for task_schedule
-- ----------------------------
DROP TABLE IF EXISTS `task_schedule`;
CREATE TABLE `task_schedule` (
  `id` bigint(20) NOT NULL,
  `name` varchar(20) NOT NULL COMMENT '定时任务名称',
  `class_name` varchar(100) NOT NULL COMMENT '定时任务类名',
  `cron` varchar(50) NOT NULL COMMENT 'cron表达式',
  `type` int(1) NOT NULL DEFAULT '2' COMMENT '定时任务类型\n1-项目启动就执行\n2-手动执行',
  `remarks` varchar(200) DEFAULT NULL COMMENT '备注',
  `creator` bigint(20) NOT NULL COMMENT '创建人',
  `create_time` bigint(13) NOT NULL COMMENT '创建时间',
  `updater` bigint(20) DEFAULT NULL COMMENT '修改人',
  `updated_time` bigint(13) DEFAULT NULL COMMENT '修改时间',
  `is_deleted` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否删除\n0-未删除\n1-删除',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='定时任务信息';

-- ----------------------------
-- Records of task_schedule
-- ----------------------------
BEGIN;
INSERT INTO `task_schedule` VALUES (156916834656845824, '测试任务', 'cn.tucci.management.core.task.TestTask', '0 */5 * * * ?', 1, '测试定时任务1', 1, 1620986363237, 1, 1621000280203, 0);
INSERT INTO `task_schedule` VALUES (156981463915429889, 'ceshi', 'cn.tucci.management.core.task.TestTask', '*/10 * * * * ?', 2, NULL, 1, 1621001772053, NULL, NULL, 0);
COMMIT;

-- ----------------------------
-- Table structure for task_start_log
-- ----------------------------
DROP TABLE IF EXISTS `task_start_log`;
CREATE TABLE `task_start_log` (
  `id` bigint(20) NOT NULL,
  `task_schedule_id` bigint(20) NOT NULL COMMENT '定时任务id',
  `status` tinyint(1) NOT NULL COMMENT '启动状态',
  `msg` varchar(1000) DEFAULT NULL COMMENT '启动信息',
  `creator` bigint(20) NOT NULL COMMENT '创建人',
  `create_time` bigint(13) NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE,
  KEY `task_schedule_id` (`task_schedule_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='定时任务启动日志';

-- ----------------------------
-- Records of task_start_log
-- ----------------------------
BEGIN;
COMMIT;

SET FOREIGN_KEY_CHECKS = 1;
