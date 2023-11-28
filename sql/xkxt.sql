/*
 Navicat Premium Data Transfer

 Source Server         : web
 Source Server Type    : MySQL
 Source Server Version : 80034
 Source Host           : localhost:3306
 Source Schema         : xkxt

 Target Server Type    : MySQL
 Target Server Version : 80034
 File Encoding         : 65001

 Date: 28/11/2023 23:57:32
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for add_course_table
-- ----------------------------
DROP TABLE IF EXISTS `add_course_table`;
CREATE TABLE `add_course_table`  (
  `student_id` int(0) NOT NULL COMMENT '学生id',
  `course_id` int(0) NOT NULL COMMENT '课程id',
  `text` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '申请理由',
  `status` int(0) NOT NULL DEFAULT 2 COMMENT '申请状态1：通过，0：拒绝，2：待处理',
  PRIMARY KEY (`course_id`, `student_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '加课申请表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for course_table
-- ----------------------------
DROP TABLE IF EXISTS `course_table`;
CREATE TABLE `course_table`  (
  `id` int(0) NOT NULL,
  `cno` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '课程号',
  `cname` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '课程名',
  `teacher_id` int(0) NOT NULL COMMENT '教师id',
  `capacity` int(0) NOT NULL COMMENT '课程容量',
  `selected_number'` int(0) NOT NULL COMMENT '选课人数',
  `date` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '上课时间',
  `major_id` int(0) NULL DEFAULT NULL COMMENT '所属专业id',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for cs_table
-- ----------------------------
DROP TABLE IF EXISTS `cs_table`;
CREATE TABLE `cs_table`  (
  `student_id` int(0) NOT NULL COMMENT '学生ID',
  `course_id` int(0) NOT NULL COMMENT '课程id',
  `need_book` int(0) NOT NULL COMMENT '是否要书，0：不要，1：要',
  PRIMARY KEY (`student_id`, `course_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '选课信息表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for dept_table
-- ----------------------------
DROP TABLE IF EXISTS `dept_table`;
CREATE TABLE `dept_table`  (
  `id` int(0) NOT NULL AUTO_INCREMENT,
  `name` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '院系名称',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '院系信息表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of dept_table
-- ----------------------------
INSERT INTO `dept_table` VALUES (1, '信息学院');

-- ----------------------------
-- Table structure for major_table
-- ----------------------------
DROP TABLE IF EXISTS `major_table`;
CREATE TABLE `major_table`  (
  `id` int(0) NOT NULL AUTO_INCREMENT,
  `name` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '专业名称',
  `dept_id` int(0) NOT NULL COMMENT '所属院系id',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '专业信息表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of major_table
-- ----------------------------
INSERT INTO `major_table` VALUES (1, '计算机科学与技术', 1);
INSERT INTO `major_table` VALUES (2, '软件工程', 1);

-- ----------------------------
-- Table structure for plan_table
-- ----------------------------
DROP TABLE IF EXISTS `plan_table`;
CREATE TABLE `plan_table`  (
  `major_id` int(0) NOT NULL COMMENT '专业id',
  `course_id` int(0) NOT NULL COMMENT '课程id',
  PRIMARY KEY (`course_id`, `major_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '培养方案' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for student_table
-- ----------------------------
DROP TABLE IF EXISTS `student_table`;
CREATE TABLE `student_table`  (
  `id` int(0) NOT NULL AUTO_INCREMENT,
  `sno` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '学号',
  `sname` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '姓名',
  `sgender` int(0) NOT NULL COMMENT '性别，0：男，1：女',
  `sage` int(0) NOT NULL COMMENT '年龄',
  `smajor_id` int(0) NOT NULL COMMENT '专业id',
  `sdept_id` int(0) NOT NULL COMMENT '院系id',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `student_table_pk`(`sno`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '学生表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of student_table
-- ----------------------------
INSERT INTO `student_table` VALUES (1, '2023001', '张三', 0, 20, 1, 101);
INSERT INTO `student_table` VALUES (2, '2023002', '李四', 1, 22, 2, 102);
INSERT INTO `student_table` VALUES (3, '2023003', '王五', 0, 21, 1, 101);
INSERT INTO `student_table` VALUES (4, '2023004', '赵六', 1, 23, 3, 103);
INSERT INTO `student_table` VALUES (7, '21003', '张三四', 1, 40, 1, 1);
INSERT INTO `student_table` VALUES (14, '21006', '张四五', 1, 40, 2, 1);

-- ----------------------------
-- Table structure for teacher_table
-- ----------------------------
DROP TABLE IF EXISTS `teacher_table`;
CREATE TABLE `teacher_table`  (
  `id` int(0) NOT NULL,
  `tno` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '教工号',
  `tname` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '教师姓名',
  `tjob` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '职称',
  `tdept_id` int(0) NOT NULL COMMENT '院系id',
  `tgender` int(0) NOT NULL COMMENT '性别0：男，1：女',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `tno`(`tno`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

SET FOREIGN_KEY_CHECKS = 1;
