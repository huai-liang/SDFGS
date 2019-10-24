/*
Navicat MySQL Data Transfer

Source Server         : localhost_3306
Source Server Version : 50712
Source Host           : localhost:3306
Source Database       : test

Target Server Type    : MYSQL
Target Server Version : 50712
File Encoding         : 65001

Date: 2019-10-18 10:05:16
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for dept
-- ----------------------------
DROP TABLE IF EXISTS `dept`;
CREATE TABLE `dept` (
  `DEPTNO` bigint(2) NOT NULL AUTO_INCREMENT,
  `DNAME` varchar(20) DEFAULT NULL,
  `LOC` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`DEPTNO`)
) ENGINE=InnoDB AUTO_INCREMENT=41 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of dept
-- ----------------------------
INSERT INTO `dept` VALUES ('10', 'ACCOUNTING', 'NEW YORK');
INSERT INTO `dept` VALUES ('20', 'RESEARCH', 'DALLAS');
INSERT INTO `dept` VALUES ('30', 'SALES', 'CHICAGO');
INSERT INTO `dept` VALUES ('40', 'OPERATIONS', 'BOSTON');

-- ----------------------------
-- Table structure for emp
-- ----------------------------
DROP TABLE IF EXISTS `emp`;
CREATE TABLE `emp` (
  `EMPNO` bigint(4) NOT NULL AUTO_INCREMENT,
  `ENAME` varchar(20) DEFAULT NULL,
  `JOB` varchar(20) DEFAULT NULL,
  `MGR` bigint(20) DEFAULT NULL,
  `HIREDATE` date DEFAULT NULL,
  `SAL` double DEFAULT NULL,
  `COMM` double DEFAULT NULL,
  `DEPTNO` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`EMPNO`)
) ENGINE=InnoDB AUTO_INCREMENT=7935 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of emp
-- ----------------------------
INSERT INTO `emp` VALUES ('7369', 'SMITH', 'CLERK', '7902', '1980-12-17', '800', null, '20');
INSERT INTO `emp` VALUES ('7499', 'ALLEN', 'SALESMAN', '7698', '1981-02-20', '1600', '300', '30');
INSERT INTO `emp` VALUES ('7521', 'WARD', 'SALESMAN', '7698', '1981-02-22', '1250', '500', '30');
INSERT INTO `emp` VALUES ('7566', 'JONES', 'MANAGER', '7839', '1981-04-02', '2975', null, '20');
INSERT INTO `emp` VALUES ('7654', 'MARTIN', 'SALESMAN', '7698', '1981-09-28', '1250', '1400', '30');
INSERT INTO `emp` VALUES ('7698', 'BLAKE', 'MANAGER', '7839', '1981-05-01', '2850', null, '30');
INSERT INTO `emp` VALUES ('7782', 'CLARK', 'MANAGER', '7839', '1981-06-09', '2450', null, '10');
INSERT INTO `emp` VALUES ('7788', 'SCOTT', 'ANALYST', '7566', '1987-04-19', '3000', null, '20');
INSERT INTO `emp` VALUES ('7839', 'KING', 'PRESIDENT', null, '1981-11-17', '5000', null, '10');
INSERT INTO `emp` VALUES ('7844', 'TURNER', 'SALESMAN', '7698', '1981-09-08', '1500', '0', '30');
INSERT INTO `emp` VALUES ('7876', 'ADAMS', 'CLERK', '7788', '1987-05-23', '1100', null, '20');
INSERT INTO `emp` VALUES ('7900', 'JAMES', 'CLERK', '7698', '1981-12-03', '950', null, '30');
INSERT INTO `emp` VALUES ('7902', 'FORD', 'ANALYST', '7566', '1981-12-03', '3000', null, '20');
INSERT INTO `emp` VALUES ('7934', 'MILLER', 'CLERK', '7782', '1982-01-23', '1300', null, '10');

-- ----------------------------
-- Table structure for product
-- ----------------------------
DROP TABLE IF EXISTS `product`;
CREATE TABLE `product` (
  `id` bigint(11) NOT NULL AUTO_INCREMENT,
  `productName` varchar(50) DEFAULT NULL,
  `dir_id` bigint(11) DEFAULT NULL,
  `salePrice` double(10,2) DEFAULT NULL,
  `supplier` varchar(50) DEFAULT NULL,
  `brand` varchar(50) DEFAULT NULL,
  `cutoff` double(2,2) DEFAULT NULL,
  `costPrice` double(10,2) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=21 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of product
-- ----------------------------
INSERT INTO `product` VALUES ('1', '罗技M90', '3', '90.00', '罗技', '罗技', '0.50', '35.00');
INSERT INTO `product` VALUES ('2', '罗技M100', '3', '49.00', '罗技', '罗技', '0.90', '33.00');
INSERT INTO `product` VALUES ('3', '罗技M115', '3', '99.00', '罗技', '罗技', '0.60', '38.00');
INSERT INTO `product` VALUES ('4', '罗技M125', '3', '80.00', '罗技', '罗技', '0.90', '39.00');
INSERT INTO `product` VALUES ('5', '罗技木星轨迹球', '3', '182.00', '罗技', '罗技', '0.80', '80.00');
INSERT INTO `product` VALUES ('6', '罗技火星轨迹球', '3', '349.00', '罗技', '罗技', '0.87', '290.00');
INSERT INTO `product` VALUES ('7', '罗技G9X', '3', '680.00', '罗技', '罗技', '0.70', '470.00');
INSERT INTO `product` VALUES ('8', '罗技M215', '2', '89.00', '罗技', '罗技', '0.79', '30.00');
INSERT INTO `product` VALUES ('9', '罗技M305', '2', '119.00', '罗技', '罗技', '0.82', '48.00');
INSERT INTO `product` VALUES ('10', '罗技M310', '2', '135.00', '罗技', '罗技', '0.92', '69.80');
INSERT INTO `product` VALUES ('11', '罗技M505', '2', '148.00', '罗技', '罗技', '0.92', '72.00');
INSERT INTO `product` VALUES ('12', '罗技M555', '2', '275.00', '罗技', '罗技', '0.88', '140.00');
INSERT INTO `product` VALUES ('13', '罗技M905', '2', '458.00', '罗技', '罗技', '0.88', '270.00');
INSERT INTO `product` VALUES ('14', '罗技MX1100', '2', '550.00', '罗技', '罗技', '0.76', '300.00');
INSERT INTO `product` VALUES ('15', '罗技M950', '2', '678.00', '罗技', '罗技', '0.78', '320.00');
INSERT INTO `product` VALUES ('16', '罗技MX Air', '2', '1299.00', '罗技', '罗技', '0.72', '400.00');
INSERT INTO `product` VALUES ('17', '罗技G1', '4', '155.00', '罗技', '罗技', '0.80', '49.00');
INSERT INTO `product` VALUES ('18', '罗技G3', '4', '229.00', '罗技', '罗技', '0.77', '96.00');
INSERT INTO `product` VALUES ('19', '罗技G500', '4', '399.00', '罗技', '罗技', '0.88', '130.00');
INSERT INTO `product` VALUES ('20', '罗技G700', '4', '699.00', '罗技', '罗技', '0.79', '278.00');

-- ----------------------------
-- Table structure for productdir
-- ----------------------------
DROP TABLE IF EXISTS `productdir`;
CREATE TABLE `productdir` (
  `id` bigint(11) NOT NULL AUTO_INCREMENT,
  `dirName` varchar(30) DEFAULT NULL,
  `parent_id` bigint(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of productdir
-- ----------------------------
INSERT INTO `productdir` VALUES ('1', '鼠标', null);
INSERT INTO `productdir` VALUES ('2', '无线鼠标', '1');
INSERT INTO `productdir` VALUES ('3', '有线鼠标', '1');
INSERT INTO `productdir` VALUES ('4', '游戏鼠标', '1');

-- ----------------------------
-- Table structure for productstock
-- ----------------------------
DROP TABLE IF EXISTS `productstock`;
CREATE TABLE `productstock` (
  `id` bigint(11) NOT NULL AUTO_INCREMENT,
  `product_id` bigint(11) DEFAULT NULL,
  `storeNum` int(10) DEFAULT NULL,
  `lastIncomeDate` datetime DEFAULT NULL,
  `lastOutcomeDate` datetime DEFAULT NULL,
  `warningNum` int(10) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=18 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of productstock
-- ----------------------------
INSERT INTO `productstock` VALUES ('1', '1', '182', '2015-03-12 20:33:00', '2015-03-12 20:33:04', '20');
INSERT INTO `productstock` VALUES ('2', '2', '27', '2015-03-02 20:33:28', '2015-03-09 20:33:40', '20');
INSERT INTO `productstock` VALUES ('3', '3', '89', '2015-02-28 20:34:13', '2015-03-12 20:34:19', '20');
INSERT INTO `productstock` VALUES ('4', '5', '19', '2015-03-01 20:34:43', '2015-03-12 20:34:48', '20');
INSERT INTO `productstock` VALUES ('5', '6', '3', '2015-02-01 20:35:12', '2015-03-02 20:35:16', '5');
INSERT INTO `productstock` VALUES ('6', '7', '2', '2015-02-02 20:35:59', '2015-02-27 20:36:05', '3');
INSERT INTO `productstock` VALUES ('7', '8', '120', '2015-03-12 20:36:31', '2015-03-12 20:36:33', '20');
INSERT INTO `productstock` VALUES ('8', '9', '58', '2015-03-02 20:36:50', '2015-03-12 20:36:53', '20');
INSERT INTO `productstock` VALUES ('9', '11', '28', '2015-03-02 20:37:12', '2015-03-12 20:37:15', '20');
INSERT INTO `productstock` VALUES ('10', '12', '8', '2015-03-02 20:37:35', '2015-03-09 20:37:38', '5');
INSERT INTO `productstock` VALUES ('11', '13', '3', '2015-03-02 20:37:58', '2015-03-12 20:38:01', '5');
INSERT INTO `productstock` VALUES ('12', '14', '6', '2015-03-02 20:38:20', '2015-03-07 20:38:23', '5');
INSERT INTO `productstock` VALUES ('13', '15', '2', '2015-02-02 20:38:38', '2015-02-24 20:38:44', '5');
INSERT INTO `productstock` VALUES ('14', '16', '3', '2015-02-02 20:39:05', '2015-02-06 20:39:09', '3');
INSERT INTO `productstock` VALUES ('15', '17', '49', '2015-03-02 20:39:36', '2015-03-12 20:39:40', '20');
INSERT INTO `productstock` VALUES ('16', '18', '14', '2015-03-02 20:39:57', '2015-03-09 20:40:01', '10');
INSERT INTO `productstock` VALUES ('17', '20', '7', '2015-03-02 20:40:22', '2015-03-03 20:40:25', '5');

-- ----------------------------
-- Table structure for t_dept
-- ----------------------------
DROP TABLE IF EXISTS `t_dept`;
CREATE TABLE `t_dept` (
  `id` bigint(20) NOT NULL COMMENT '部门id',
  `dept_name` varchar(255) DEFAULT NULL COMMENT '部门名称',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_dept
-- ----------------------------
INSERT INTO `t_dept` VALUES ('1', '开发部');
INSERT INTO `t_dept` VALUES ('2', '测试部');

-- ----------------------------
-- Table structure for t_file
-- ----------------------------
DROP TABLE IF EXISTS `t_file`;
CREATE TABLE `t_file` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '文件id',
  `file_name` varchar(40) DEFAULT NULL COMMENT '文件名称',
  `file_url` varchar(512) DEFAULT NULL COMMENT '文件rul',
  `file_path` varchar(512) NOT NULL COMMENT '文件所在服务器的地址',
  `business_id` bigint(20) NOT NULL COMMENT '业务id',
  `business_type` int(2) NOT NULL COMMENT '业务类型(1：代表商品模块)',
  `is_deleted` int(11) NOT NULL COMMENT '作废标记（1作废，0未作废）',
  `sort_no` int(11) NOT NULL COMMENT '序号（从0开始）',
  `gmt_create` datetime NOT NULL COMMENT '创建时间',
  `create_by` varchar(40) NOT NULL COMMENT '创建者(登录账号)',
  `gmt_modified` datetime DEFAULT NULL COMMENT '更新时间',
  `last_modified_by` varchar(40) DEFAULT NULL COMMENT '更新者(登录账号)',
  `version` int(11) NOT NULL COMMENT '版本号',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=67 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_file
-- ----------------------------
INSERT INTO `t_file` VALUES ('31', null, 'http://10.61.23.216:80/group1/M00/00/02/Cj0X2F2pBk2AeEsbAABGYMg-MHE537.jpg', 'group1/M00/00/02/Cj0X2F2pBk2AeEsbAABGYMg-MHE537.jpg', '17', '1', '0', '1', '2019-10-18 08:51:37', '1', '2019-10-18 08:51:37', '1', '1');
INSERT INTO `t_file` VALUES ('39', null, 'http://192.168.139.130/group1/M00/00/00/wKiLgl2eNEOAVQUxAACIVV53iAc602_big.jpg', '', '3', '1', '0', '1', '2019-10-18 09:12:27', '1', '2019-10-18 09:12:27', '1', '1');
INSERT INTO `t_file` VALUES ('43', null, 'http://192.168.139.130:80/group1/M00/00/00/wKiLgl2fKZWAduxpAAD4OM2PwGQ540.jpg', 'group1/M00/00/00/wKiLgl2fKZWAduxpAAD4OM2PwGQ540.jpg', '5', '1', '0', '1', '2019-10-18 09:14:10', '1', '2019-10-18 09:14:10', '1', '1');
INSERT INTO `t_file` VALUES ('45', null, 'http://10.61.23.216:80/group1/M00/00/03/Cj0X2F2pDHmAJT3aAAEkx-bMDGg573.jpg', 'group1/M00/00/03/Cj0X2F2pDHmAJT3aAAEkx-bMDGg573.jpg', '33', '1', '0', '1', '2019-10-18 09:19:43', '1', '2019-10-18 09:19:43', '1', '1');
INSERT INTO `t_file` VALUES ('62', null, null, 'group1/M00/00/03/Cj0X2F2pDn6AFrgCAACuG7VIAfA874.jpg', '34', '1', '0', '1', '2019-10-18 10:03:02', '1', '2019-10-18 10:03:02', '1', '1');
INSERT INTO `t_file` VALUES ('63', null, 'http://10.61.23.216:80/group1/M00/00/04/Cj0X2F2pHRaAE9IFAACIVV53iAc698.jpg', 'group1/M00/00/04/Cj0X2F2pHRaAE9IFAACIVV53iAc698.jpg', '34', '1', '0', '1', '2019-10-18 10:03:02', '1', '2019-10-18 10:03:02', '1', '1');
INSERT INTO `t_file` VALUES ('64', null, 'http://10.61.23.216:80/group1/M00/00/04/Cj0X2F2pHRmAUbQjAACuG7VIAfA126.jpg', 'group1/M00/00/04/Cj0X2F2pHRmAUbQjAACuG7VIAfA126.jpg', '34', '1', '0', '1', '2019-10-18 10:03:02', '1', '2019-10-18 10:03:02', '1', '1');
INSERT INTO `t_file` VALUES ('65', null, 'http://10.61.23.216:80/group1/M00/00/04/Cj0X2F2pHRyALIh8AAD4OM2PwGQ259.jpg', 'group1/M00/00/04/Cj0X2F2pHRyALIh8AAD4OM2PwGQ259.jpg', '34', '1', '0', '1', '2019-10-18 10:03:02', '1', '2019-10-18 10:03:02', '1', '1');
INSERT INTO `t_file` VALUES ('66', null, 'http://10.61.23.216:80/group1/M00/00/04/Cj0X2F2pHR6ANi-QAACIVV53iAc018.jpg', 'group1/M00/00/04/Cj0X2F2pHR6ANi-QAACIVV53iAc018.jpg', '34', '1', '0', '1', '2019-10-18 10:03:02', '1', '2019-10-18 10:03:02', '1', '1');

-- ----------------------------
-- Table structure for t_goods_cate
-- ----------------------------
DROP TABLE IF EXISTS `t_goods_cate`;
CREATE TABLE `t_goods_cate` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '分类id',
  `cate_name` varchar(40) NOT NULL COMMENT '分类名称',
  `parent_cate_id` bigint(20) DEFAULT NULL COMMENT '上级分类id',
  `cate_level` int(11) NOT NULL COMMENT '层级（第一层1，后续2,3等等）',
  `remark` varchar(400) DEFAULT NULL COMMENT '备注',
  `is_deleted` int(11) NOT NULL COMMENT '作废标记（1作废，0未作废）',
  `sort_no` int(11) NOT NULL COMMENT '序号（从0开始）',
  `gmt_create` datetime NOT NULL COMMENT '创建时间',
  `create_by` varchar(40) NOT NULL COMMENT '创建者(登录账号)',
  `gmt_modified` datetime DEFAULT NULL COMMENT '更新时间',
  `last_modified_by` varchar(40) DEFAULT NULL COMMENT '更新者(登录账号)',
  `version` int(11) NOT NULL COMMENT '版本号',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=43 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_goods_cate
-- ----------------------------
INSERT INTO `t_goods_cate` VALUES ('1', '办公家电', '0', '1', '第一层', '0', '0', '2019-09-30 14:47:48', '1', '2019-09-30 15:08:48', '1', '1');
INSERT INTO `t_goods_cate` VALUES ('2', '电视', '1', '2', '第二层', '0', '0', '2019-09-30 14:50:07', '1', '2019-09-30 14:50:07', '1', '1');
INSERT INTO `t_goods_cate` VALUES ('3', '空调', '1', '2', '第二层', '0', '0', '2019-09-30 14:50:34', '1', '2019-09-30 14:50:34', '1', '1');
INSERT INTO `t_goods_cate` VALUES ('4', '产品', '0', '1', '第一层', '0', '0', '2019-10-08 19:33:08', '1', '2019-10-16 18:21:18', null, '1');

-- ----------------------------
-- Table structure for t_goods_sku
-- ----------------------------
DROP TABLE IF EXISTS `t_goods_sku`;
CREATE TABLE `t_goods_sku` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'sku_id自增',
  `sku_name` varchar(200) NOT NULL COMMENT 'SKU名称',
  `one_cate_id` bigint(40) DEFAULT NULL COMMENT '一级分类id',
  `two_cate_id` bigint(40) DEFAULT NULL COMMENT '二级分类id',
  `cost_price` decimal(20,2) DEFAULT NULL COMMENT '成本价',
  `sale_price` decimal(20,2) DEFAULT NULL COMMENT '售价',
  `sale_count` int(20) NOT NULL DEFAULT '0' COMMENT '已售数量',
  `detail` text COMMENT '介绍',
  `putaway_time` datetime DEFAULT NULL COMMENT '上架时间',
  `lower_time` datetime DEFAULT NULL COMMENT '下架时间',
  `status` int(11) NOT NULL DEFAULT '1' COMMENT '1为未发布，2为在售，3为下架',
  `browse_count` bigint(20) DEFAULT '0' COMMENT '浏览量',
  `author` varchar(200) DEFAULT NULL COMMENT '作者',
  `advertising` varchar(500) DEFAULT NULL COMMENT '广告词',
  `isbn` varchar(100) DEFAULT NULL COMMENT 'ISBN',
  `stock` int(11) NOT NULL COMMENT '库存',
  `is_deleted` int(11) NOT NULL COMMENT '作废标记（1作废，0未作废）',
  `sort_no` int(11) NOT NULL COMMENT '序号（从0开始）',
  `gmt_create` datetime NOT NULL COMMENT '创建时间',
  `create_by` varchar(40) NOT NULL COMMENT '创建者(登录账号)',
  `gmt_modified` datetime DEFAULT NULL COMMENT '更新时间',
  `last_modified_by` varchar(40) DEFAULT NULL COMMENT '更新者(登录账号)',
  `version` int(11) NOT NULL COMMENT '版本号',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=42 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_goods_sku
-- ----------------------------
INSERT INTO `t_goods_sku` VALUES ('3', 'string', '0', '0', '10.00', '30.00', '0', '2222', '2019-10-10 14:43:17', null, '2', '0', 'string', 'string', 'string', '1000', '0', '0', '2019-10-10 14:43:17', '1', '2019-10-17 20:22:49', '1', '1');
INSERT INTO `t_goods_sku` VALUES ('5', '124', '1', '2', '1000.00', '100.00', '0', 'string', '2019-10-11 15:46:26', null, '1', '0', 'string', 'string', 'string', '10000', '0', '0', '2019-10-11 15:46:26', 'string', '2019-10-11 15:46:26', 'string', '1');
INSERT INTO `t_goods_sku` VALUES ('17', 'fes去212 ', '1', '2', '145.00', '111.00', '0', '124', '2019-10-18 08:24:48', null, '3', '0', null, '来了老弟', null, '999', '0', '0', '2019-10-18 08:24:48', '6', '2019-10-18 08:30:10', null, '1');
INSERT INTO `t_goods_sku` VALUES ('33', 'haha', '1', '2', '456.00', '1000.00', '0', 'lala', '2019-10-18 08:51:06', null, '2', '0', null, 'gg', null, '999', '0', '0', '2019-10-18 08:51:06', '6', '2019-10-18 08:51:37', null, '1');
INSERT INTO `t_goods_sku` VALUES ('34', '我去饿', '1', '2', '100.00', '13.00', '0', '1', '2019-10-18 08:59:44', null, '1', '0', null, '1', null, '12343', '0', '0', '2019-10-18 08:59:44', '6', '2019-10-18 08:59:44', null, '1');

-- ----------------------------
-- Table structure for t_shop_car
-- ----------------------------
DROP TABLE IF EXISTS `t_shop_car`;
CREATE TABLE `t_shop_car` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '购物车id',
  `sku_id` bigint(20) NOT NULL COMMENT 'sku_id',
  `count` int(10) DEFAULT NULL COMMENT 'sku数量',
  `is_deleted` int(11) NOT NULL COMMENT '作废标记（1作废，0未作废）',
  `sort_no` int(11) NOT NULL COMMENT '序号（从0开始）',
  `gmt_create` datetime NOT NULL COMMENT '创建时间',
  `create_by` varchar(40) NOT NULL COMMENT '创建者(登录账号)',
  `gmt_modified` datetime DEFAULT NULL COMMENT '更新时间',
  `last_modified_by` varchar(40) DEFAULT NULL COMMENT '更新者(登录账号)',
  `version` int(11) NOT NULL COMMENT '版本号',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_shop_car
-- ----------------------------

-- ----------------------------
-- Table structure for t_shuffling
-- ----------------------------
DROP TABLE IF EXISTS `t_shuffling`;
CREATE TABLE `t_shuffling` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `url` varchar(512) NOT NULL COMMENT '文件rul',
  `file_path` varchar(512) NOT NULL COMMENT '文件所在服务器的地址',
  `status` varchar(2) DEFAULT NULL COMMENT '状态，0启用，1禁用',
  `is_deleted` int(11) NOT NULL COMMENT '作废标记（1作废，0未作废）',
  `sort_no` int(11) NOT NULL COMMENT '序号（从0开始）',
  `gmt_create` datetime NOT NULL COMMENT '创建时间',
  `create_by` varchar(40) NOT NULL COMMENT '创建者(登录账号)',
  `gmt_modified` datetime DEFAULT NULL COMMENT '更新时间',
  `last_modified_by` varchar(40) DEFAULT NULL COMMENT '更新者(登录账号)',
  `version` int(11) NOT NULL COMMENT '版本号',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=38 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of t_shuffling
-- ----------------------------
INSERT INTO `t_shuffling` VALUES ('1', 'http://10.61.23.216:80/group1/M00/00/01/Cj0X2F2oNTaAK-PqAACIVV53iAc080.jpg', 'group1/M00/00/01/Cj0X2F2oNTaAK-PqAACIVV53iAc080.jpg', '1', '1', '1', '2019-10-17 17:47:22', '1', '2019-10-18 08:27:46', '1', '1');
INSERT INTO `t_shuffling` VALUES ('2', 'http://10.61.23.216:80/group1/M00/00/01/Cj0X2F2oNCmAakyQAACIVV53iAc115.jpg', 'group1/M00/00/01/Cj0X2F2oNCmAakyQAACIVV53iAc115.jpg', '0', '1', '1', '2019-10-17 17:47:32', '1', '2019-10-18 08:06:24', '1', '1');
INSERT INTO `t_shuffling` VALUES ('3', 'http://10.61.23.216:80/group1/M00/00/01/Cj0X2F2oOPKAB9gUAACuG7VIAfA001.jpg', 'group1/M00/00/01/Cj0X2F2oOPKAB9gUAACuG7VIAfA001.jpg', '1', '1', '1', '2019-10-17 17:48:34', '6', '2019-10-18 08:16:06', '6', '1');
INSERT INTO `t_shuffling` VALUES ('4', 'http://10.61.23.216:80/group1/M00/00/01/Cj0X2F2oOaeAEIzWAAD4OM2PwGQ546.jpg', 'group1/M00/00/01/Cj0X2F2oOaeAEIzWAAD4OM2PwGQ546.jpg', '1', '1', '1', '2019-10-17 17:51:35', '6', '2019-10-18 08:34:29', '6', '1');
INSERT INTO `t_shuffling` VALUES ('5', 'http://10.61.23.216:80/group1/M00/00/01/Cj0X2F2oOd-AIOQoAAD4OM2PwGQ507.jpg', 'group1/M00/00/01/Cj0X2F2oOd-AIOQoAAD4OM2PwGQ507.jpg', '0', '1', '1', '2019-10-17 17:52:31', '6', '2019-10-18 08:07:04', '6', '1');
INSERT INTO `t_shuffling` VALUES ('6', 'http://10.61.23.216:80/group1/M00/00/01/Cj0X2F2oPcWAU4ksAACuG7VIAfA660.jpg', 'group1/M00/00/01/Cj0X2F2oPcWAU4ksAACuG7VIAfA660.jpg', '0', '1', '1', '2019-10-17 18:09:11', '6', '2019-10-18 08:06:57', '6', '1');
INSERT INTO `t_shuffling` VALUES ('7', 'http://10.61.23.216:80/group1/M00/00/01/Cj0X2F2pAkqALk_vAAAfdz-8p1s707.gif', 'group1/M00/00/01/Cj0X2F2pAkqALk_vAAAfdz-8p1s707.gif', '0', '1', '1', '2019-10-18 08:07:39', '6', '2019-10-18 08:08:01', '6', '1');
INSERT INTO `t_shuffling` VALUES ('8', 'http://10.61.23.216:80/group1/M00/00/01/Cj0X2F2pAmmAQh4xAAAOY2vBfQg474.jpg', 'group1/M00/00/01/Cj0X2F2pAmmAQh4xAAAOY2vBfQg474.jpg', '0', '1', '1', '2019-10-18 08:08:10', '6', '2019-10-18 08:14:11', '6', '1');
INSERT INTO `t_shuffling` VALUES ('9', 'http://10.61.23.216:80/group1/M00/00/01/Cj0X2F2pAnuAQ79BAAG-bjzqzUg780.jpg', 'group1/M00/00/01/Cj0X2F2pAnuAQ79BAAG-bjzqzUg780.jpg', '0', '1', '1', '2019-10-18 08:08:29', '6', '2019-10-18 08:14:05', '6', '1');
INSERT INTO `t_shuffling` VALUES ('10', 'http://10.61.23.216:80/group1/M00/00/01/Cj0X2F2pAn-AGlasAAAX1YxXb3o203.jpg', 'group1/M00/00/01/Cj0X2F2pAn-AGlasAAAX1YxXb3o203.jpg', '0', '1', '1', '2019-10-18 08:08:32', '6', '2019-10-18 08:11:52', '6', '1');
INSERT INTO `t_shuffling` VALUES ('11', 'http://10.61.23.216:80/group1/M00/00/01/Cj0X2F2pAqqAVA4VAAN0-DXm6oI263.jpg', 'group1/M00/00/01/Cj0X2F2pAqqAVA4VAAN0-DXm6oI263.jpg', '0', '1', '1', '2019-10-18 08:09:15', '6', '2019-10-18 08:13:52', '6', '1');
INSERT INTO `t_shuffling` VALUES ('12', 'http://10.61.23.216:80/group1/M00/00/01/Cj0X2F2pA9mASRELAAG-bjzqzUg657.jpg', 'group1/M00/00/01/Cj0X2F2pA9mASRELAAG-bjzqzUg657.jpg', '0', '1', '1', '2019-10-18 08:14:17', '6', '2019-10-18 08:14:25', '6', '1');
INSERT INTO `t_shuffling` VALUES ('13', 'http://10.61.23.216:80/group1/M00/00/01/Cj0X2F2pA-uAVmBbAACuG7VIAfA767.jpg', 'group1/M00/00/01/Cj0X2F2pA-uAVmBbAACuG7VIAfA767.jpg', '0', '1', '1', '2019-10-18 08:14:36', '6', '2019-10-18 08:15:12', '6', '1');
INSERT INTO `t_shuffling` VALUES ('14', 'http://10.61.23.216:80/group1/M00/00/01/Cj0X2F2pA_OAE-c6AAA6vbd4OEQ014.png', 'group1/M00/00/01/Cj0X2F2pA_OAE-c6AAA6vbd4OEQ014.png', '0', '1', '1', '2019-10-18 08:14:45', '6', '2019-10-18 08:14:54', '6', '1');
INSERT INTO `t_shuffling` VALUES ('15', 'http://10.61.23.216:80/group1/M00/00/01/Cj0X2F2pBDaAIXcNAAAOY2vBfQg329.jpg', 'group1/M00/00/01/Cj0X2F2pBDaAIXcNAAAOY2vBfQg329.jpg', '1', '1', '1', '2019-10-18 08:15:52', '6', '2019-10-18 08:28:25', '6', '1');
INSERT INTO `t_shuffling` VALUES ('16', 'http://10.61.23.216:80/group1/M00/00/01/Cj0X2F2pBL-ATz4KAAFWHTXBniQ796.jpg', 'group1/M00/00/01/Cj0X2F2pBL-ATz4KAAFWHTXBniQ796.jpg', '0', '1', '1', '2019-10-18 08:18:07', '6', '2019-10-18 08:21:03', '6', '1');
INSERT INTO `t_shuffling` VALUES ('17', 'http://10.61.23.216:80/group1/M00/00/02/Cj0X2F2pBjCAJ3TqAAG-bjzqzUg977.jpg', 'group1/M00/00/02/Cj0X2F2pBjCAJ3TqAAG-bjzqzUg977.jpg', '0', '1', '1', '2019-10-18 08:24:17', '6', '2019-10-18 08:24:47', '6', '1');
INSERT INTO `t_shuffling` VALUES ('18', 'http://10.61.23.216:80/group1/M00/00/02/Cj0X2F2pBnmAYdhrAABB9cuThqY383.jpg', 'group1/M00/00/02/Cj0X2F2pBnmAYdhrAABB9cuThqY383.jpg', '0', '1', '1', '2019-10-18 08:25:30', '6', '2019-10-18 08:26:28', '6', '1');
INSERT INTO `t_shuffling` VALUES ('19', 'http://10.61.23.216:80/group1/M00/00/02/Cj0X2F2pBpSACDNHAABThRexaFA884.jpg', 'group1/M00/00/02/Cj0X2F2pBpSACDNHAABThRexaFA884.jpg', '0', '1', '1', '2019-10-18 08:25:56', '6', '2019-10-18 08:26:14', '6', '1');
INSERT INTO `t_shuffling` VALUES ('20', 'http://10.61.23.216:80/group1/M00/00/02/Cj0X2F2pBquALmp3AABo8mZghR8452.jpg', 'group1/M00/00/02/Cj0X2F2pBquALmp3AABo8mZghR8452.jpg', '0', '1', '1', '2019-10-18 08:26:19', '6', '2019-10-18 08:32:50', '6', '1');
INSERT INTO `t_shuffling` VALUES ('21', 'http://10.61.23.216:80/group1/M00/00/02/Cj0X2F2pBrWAVuGMAAAX7y4sKaQ068.png', 'group1/M00/00/02/Cj0X2F2pBrWAVuGMAAAX7y4sKaQ068.png', '1', '1', '1', '2019-10-18 08:26:30', '6', '2019-10-18 08:32:23', '6', '1');
INSERT INTO `t_shuffling` VALUES ('22', 'http://10.61.23.216:80/group1/M00/00/02/Cj0X2F2pBx6AcbmIAAACf_HWLNE006.jpg', 'group1/M00/00/02/Cj0X2F2pBx6AcbmIAAACf_HWLNE006.jpg', '1', '1', '1', '2019-10-18 08:28:15', '6', '2019-10-18 08:28:36', '6', '1');
INSERT INTO `t_shuffling` VALUES ('23', 'http://10.61.23.216:80/group1/M00/00/02/Cj0X2F2pB1GACryXAAHvm6FdcDs666.jpg', 'group1/M00/00/02/Cj0X2F2pB1GACryXAAHvm6FdcDs666.jpg', '0', '1', '1', '2019-10-18 08:29:07', '8', '2019-10-18 08:29:15', '8', '1');
INSERT INTO `t_shuffling` VALUES ('24', 'http://10.61.23.216:80/group1/M00/00/02/Cj0X2F2pB4mASlEiAAsLxn1JLyA868.jpg', 'group1/M00/00/02/Cj0X2F2pB4mASlEiAAsLxn1JLyA868.jpg', '1', '1', '1', '2019-10-18 08:30:09', '6', '2019-10-18 08:30:30', '6', '1');
INSERT INTO `t_shuffling` VALUES ('25', 'http://10.61.23.216:80/group1/M00/00/02/Cj0X2F2pCGuAckwfAAACf_HWLNE226.jpg', 'group1/M00/00/02/Cj0X2F2pCGuAckwfAAACf_HWLNE226.jpg', '0', '1', '1', '2019-10-18 08:33:47', '6', '2019-10-18 08:34:18', '6', '1');
INSERT INTO `t_shuffling` VALUES ('26', 'http://10.61.23.216:80/group1/M00/00/02/Cj0X2F2pCKOACLhmAAuLLO9vDZY213.jpg', 'group1/M00/00/02/Cj0X2F2pCKOACLhmAAuLLO9vDZY213.jpg', '0', '1', '1', '2019-10-18 08:34:44', '29', '2019-10-18 08:34:57', '29', '1');
INSERT INTO `t_shuffling` VALUES ('27', 'http://10.61.23.216:80/group1/M00/00/02/Cj0X2F2pCNKAFP-5AAD9PHQldlw06.jpeg', 'group1/M00/00/02/Cj0X2F2pCNKAFP-5AAD9PHQldlw06.jpeg', '1', '1', '1', '2019-10-18 08:35:32', '6', '2019-10-18 08:35:54', '6', '1');
INSERT INTO `t_shuffling` VALUES ('28', 'http://10.61.23.216:80/group1/M00/00/02/Cj0X2F2pCPOAGS2TAAACf_HWLNE352.jpg', 'group1/M00/00/02/Cj0X2F2pCPOAGS2TAAACf_HWLNE352.jpg', '0', '1', '1', '2019-10-18 08:36:03', '6', '2019-10-18 08:36:11', '6', '1');
INSERT INTO `t_shuffling` VALUES ('29', 'http://10.61.23.216:80/group1/M00/00/02/Cj0X2F2pCUWAJ4g7AAACf_HWLNE600.jpg', 'group1/M00/00/02/Cj0X2F2pCUWAJ4g7AAACf_HWLNE600.jpg', '0', '1', '1', '2019-10-18 08:37:25', '6', '2019-10-18 08:37:59', '6', '1');
INSERT INTO `t_shuffling` VALUES ('30', 'http://10.61.23.216:80/group1/M00/00/03/Cj0X2F2pCXGAIfUKAAHvm6FdcDs954.jpg', 'group1/M00/00/03/Cj0X2F2pCXGAIfUKAAHvm6FdcDs954.jpg', '0', '0', '1', '2019-10-18 08:38:10', '6', '2019-10-18 08:42:16', '6', '1');
INSERT INTO `t_shuffling` VALUES ('31', 'http://10.61.23.216:80/group1/M00/00/03/Cj0X2F2pCXeATSLUAD1Kg-azobo302.jpg', 'group1/M00/00/03/Cj0X2F2pCXeATSLUAD1Kg-azobo302.jpg', '0', '1', '1', '2019-10-18 08:38:16', '6', '2019-10-18 08:39:11', '6', '1');
INSERT INTO `t_shuffling` VALUES ('32', 'http://10.61.23.216:80/group1/M00/00/03/Cj0X2F2pCa6ALDn1AAAmh8acf4w824.png', 'group1/M00/00/03/Cj0X2F2pCa6ALDn1AAAmh8acf4w824.png', '0', '1', '1', '2019-10-18 08:39:10', '6', '2019-10-18 08:39:14', '6', '1');
INSERT INTO `t_shuffling` VALUES ('33', 'http://10.61.23.216:80/group1/M00/00/03/Cj0X2F2pCdWAEtYJAD1Kg-azobo013.jpg', 'group1/M00/00/03/Cj0X2F2pCdWAEtYJAD1Kg-azobo013.jpg', '1', '0', '1', '2019-10-18 08:39:50', '6', '2019-10-18 08:39:56', '6', '1');
INSERT INTO `t_shuffling` VALUES ('34', 'http://10.61.23.216:80/group1/M00/00/03/Cj0X2F2pCn2AMceWAAACZE-WE-E431.JPG', 'group1/M00/00/03/Cj0X2F2pCn2AMceWAAACZE-WE-E431.JPG', '0', '1', '1', '2019-10-18 08:42:37', '6', '2019-10-18 08:42:47', '6', '1');
INSERT INTO `t_shuffling` VALUES ('35', 'http://10.61.23.216:80/group1/M00/00/03/Cj0X2F2pCzmAZ6zHAAACf_HWLNE836.jpg', 'group1/M00/00/03/Cj0X2F2pCzmAZ6zHAAACf_HWLNE836.jpg', '0', '1', '1', '2019-10-18 08:45:46', '6', '2019-10-18 08:46:10', '6', '1');
INSERT INTO `t_shuffling` VALUES ('36', 'http://10.61.23.216:80/group1/M00/00/03/Cj0X2F2pC0GAGyVhAAACZE-WE-E627.JPG', 'group1/M00/00/03/Cj0X2F2pC0GAGyVhAAACZE-WE-E627.JPG', '0', '1', '1', '2019-10-18 08:45:53', '6', '2019-10-18 08:46:07', '6', '1');
INSERT INTO `t_shuffling` VALUES ('37', 'http://10.61.23.216:80/group1/M00/00/03/Cj0X2F2pDFuAVvMdAAEkx-bMDGg272.jpg', 'group1/M00/00/03/Cj0X2F2pDFuAVvMdAAEkx-bMDGg272.jpg', '0', '0', '1', '2019-10-18 08:50:36', '6', '2019-10-18 08:59:24', '6', '1');

-- ----------------------------
-- Table structure for t_sys_customer
-- ----------------------------
DROP TABLE IF EXISTS `t_sys_customer`;
CREATE TABLE `t_sys_customer` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '自增',
  `user_name` varchar(40) DEFAULT NULL COMMENT '用户姓名',
  `user_acct` varchar(100) DEFAULT NULL COMMENT '用户账号',
  `user_pwd` varchar(100) DEFAULT NULL COMMENT '密码',
  `user_type` int(11) NOT NULL COMMENT '//0客户 1用户',
  `admin_flag` int(11) NOT NULL COMMENT '管理员标记 1：是， 0：否',
  `id_card` varchar(18) DEFAULT NULL COMMENT '身份证号',
  `sex` int(11) DEFAULT NULL COMMENT '性别 0女1男',
  `phone` varchar(40) DEFAULT NULL COMMENT '手机号',
  `email` varchar(40) DEFAULT NULL COMMENT '邮箱',
  `remark` varchar(1024) DEFAULT NULL COMMENT '备注',
  `score` int(11) NOT NULL COMMENT '积分',
  `client_id` int(11) DEFAULT NULL COMMENT '客户端id',
  `is_deleted` int(11) NOT NULL COMMENT '作废标记（1作废，0未作废）',
  `sort_no` int(11) NOT NULL COMMENT '序号（从0开始）',
  `gmt_create` datetime NOT NULL COMMENT '创建时间',
  `create_by` varchar(40) NOT NULL COMMENT '创建者(登录账号)',
  `gmt_modified` datetime DEFAULT NULL COMMENT '更新时间',
  `last_modified_by` varchar(40) DEFAULT NULL COMMENT '更新者(登录账号)',
  `version` int(11) NOT NULL COMMENT '版本号',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=33 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_sys_customer
-- ----------------------------
INSERT INTO `t_sys_customer` VALUES ('6', 'string', 'string', 'b45cffe084dd3d20d928bee85e7b0f21', '1', '0', '431229', '0', '17670418939', 'string@qq.com', '第一次注册', '0', '1', '0', '0', '2019-10-14 10:17:37', 'string', '2019-10-14 10:17:37', 'string', '1');
INSERT INTO `t_sys_customer` VALUES ('15', 'lisi', 'lisi', 'b45cffe084dd3d20d928bee85e7b0f21', '1', '0', 'string', '1', '123456', '11@.com', 'xiaozhu', '0', '0', '0', '0', '2019-10-17 08:08:48', '1', '2019-10-17 08:08:48', '1', '1');
INSERT INTO `t_sys_customer` VALUES ('26', '12312', '123123', '202cb962ac59075b964b07152d234b70', '0', '0', '12312', '0', '1231', '123123', '123123', '123', '0', '0', '0', '2019-10-17 08:42:29', '1', '2019-10-17 08:42:29', '1', '1');
INSERT INTO `t_sys_customer` VALUES ('28', '1', '1', 'c4ca4238a0b923820dcc509a6f75849b', '1', '0', '1', '0', '1', '1', '1', '0', '0', '0', '0', '2019-10-17 09:09:31', '1', '2019-10-17 09:09:31', '1', '1');
INSERT INTO `t_sys_customer` VALUES ('29', '3', '2', 'c81e728d9d4c2f636f067f89cc14862c', '1', '0', '2', '0', '2', '2', '2', '0', '0', '0', '0', '2019-10-17 09:09:44', '1', '2019-10-17 09:09:44', '1', '1');
INSERT INTO `t_sys_customer` VALUES ('30', 'qwe', 'qwe', 'd064f3519426dcd30114b900431fc044', '0', '0', '123123', '0', '123123', '123123', '', '12312', '0', '0', '0', '2019-10-17 19:18:02', '1', '2019-10-17 19:18:02', '1', '1');

-- ----------------------------
-- Table structure for t_sys_menu
-- ----------------------------
DROP TABLE IF EXISTS `t_sys_menu`;
CREATE TABLE `t_sys_menu` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '菜单id',
  `menu_name` varchar(400) DEFAULT NULL COMMENT '菜单名称',
  `menu_url` varchar(400) DEFAULT NULL COMMENT '菜单链接',
  `parent_id` bigint(20) DEFAULT NULL COMMENT '父级菜单id',
  `menu_type` int(11) NOT NULL COMMENT '类型(1代表菜单2代表目录)',
  `menu_level` int(11) DEFAULT NULL COMMENT '菜单层次（项目根节点是0，下面一次+1）',
  `remark` varchar(1024) DEFAULT NULL COMMENT '备注',
  `is_deleted` int(11) NOT NULL COMMENT '作废标记（1作废，0未作废）',
  `sort_no` int(11) NOT NULL COMMENT '序号（从0开始）',
  `gmt_create` datetime NOT NULL COMMENT '创建时间',
  `create_by` varchar(40) NOT NULL COMMENT '创建者(登录账号)',
  `gmt_modified` datetime DEFAULT NULL COMMENT '更新时间',
  `last_modified_by` varchar(40) DEFAULT NULL COMMENT '更新者(登录账号)',
  `version` int(11) NOT NULL COMMENT '版本号',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=26 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_sys_menu
-- ----------------------------
INSERT INTO `t_sys_menu` VALUES ('1', '系统管理', '/HomePage', '0', '2', '0', '我是系统管理', '0', '1', '2019-10-15 16:58:57', '1', '2019-10-15 17:43:54', null, '1');
INSERT INTO `t_sys_menu` VALUES ('2', '用户管理', '/user', '1', '1', '1', '我是用户管理', '0', '1', '2019-10-15 16:59:42', '1', '2019-10-15 17:35:07', null, '1');
INSERT INTO `t_sys_menu` VALUES ('3', '菜单管理', '/menu', '1', '1', '1', '我是菜单管理', '0', '1', '2019-10-15 17:00:26', '1', '2019-10-15 17:35:13', null, '1');
INSERT INTO `t_sys_menu` VALUES ('4', '客户管理', '/customer', '0', '1', '0', '我是客户管理', '0', '1', '2019-10-15 17:02:46', '1', '2019-10-15 17:35:17', null, '1');
INSERT INTO `t_sys_menu` VALUES ('5', '商品管理', '/goods', '0', '1', '0', '我是商品管理', '0', '1', '2019-10-15 17:03:56', '1', '2019-10-15 17:45:03', null, '1');
INSERT INTO `t_sys_menu` VALUES ('6', '轮播图管理', '/bannerImg', '0', '1', '0', '我是轮播图管理', '0', '1', '2019-10-15 17:04:59', '1', '2019-10-15 17:44:18', null, '1');
INSERT INTO `t_sys_menu` VALUES ('7', '商品分类管理', '/goodsKind', '0', '1', '0', '我是商品分类管理', '0', '1', '2019-10-15 17:05:34', '1', '2019-10-15 17:45:15', null, '1');

-- ----------------------------
-- Table structure for t_sys_user
-- ----------------------------
DROP TABLE IF EXISTS `t_sys_user`;
CREATE TABLE `t_sys_user` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_name` varchar(255) DEFAULT NULL,
  `user_acct` varchar(255) DEFAULT NULL,
  `user_password` varchar(255) DEFAULT NULL,
  `user_email` varchar(255) DEFAULT NULL,
  `create_at` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_sys_user
-- ----------------------------
INSERT INTO `t_sys_user` VALUES ('1', '西门吹雪', 'ximen', '123456', '', '2019-09-23 10:29:37');
INSERT INTO `t_sys_user` VALUES ('2', '郭靖', 'guojing', 'gongjing123', 'guojing@qq.com', '2019-09-23 10:29:37');
INSERT INTO `t_sys_user` VALUES ('3', '黄蓉', 'huangrong', 'h23478', 'huangrong@qq.com', '2019-09-23 10:29:37');
INSERT INTO `t_sys_user` VALUES ('4', '杨过', 'yangguo', 'yg234789', 'yangguo@qq.com', '2019-09-23 10:29:37');
INSERT INTO `t_sys_user` VALUES ('5', '小龙女', 'xiaolongnv', 'xl234789', 'xiaolongnv@qq.com', '2019-09-23 10:29:37');

-- ----------------------------
-- Table structure for t_user
-- ----------------------------
DROP TABLE IF EXISTS `t_user`;
CREATE TABLE `t_user` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `userName` varchar(255) DEFAULT NULL COMMENT '用户姓名',
  `email` varchar(255) DEFAULT NULL COMMENT '邮箱',
  `address` varchar(255) DEFAULT NULL COMMENT '地址',
  `dept_id` bigint(20) DEFAULT NULL COMMENT '部门id',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_user
-- ----------------------------
INSERT INTO `t_user` VALUES ('10', '张三', '123@qq.com', '宁波', '1');
INSERT INTO `t_user` VALUES ('11', '段誉', 'duanyu@qq.com', '大理', '1');
INSERT INTO `t_user` VALUES ('12', '王姑娘', 'wgn@qq.com', '大理', '2');
