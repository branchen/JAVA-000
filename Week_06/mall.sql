create table t_user
(
   id                   bigint(20) not null comment '主键',
   optlock              bigint(20) not null default 1 comment '版本号',
   user_name            varchar(50) comment '用户名',
   name                 varchar(255) comment '名称',
   mobile               varchar(50) comment '手机',
   email                varchar(50) comment '邮箱',
   password             varchar(255) comment '密码',
   head_image_attach_id bigint(20) comment '用户头像',
   status               int not null default 1 comment '状态，1：正常 2:已禁用 3=删除',
   balance              decimal(28,6) comment '账户余额',
   balance_encryption   varchar(255) comment '账户余额加密',
   register_time        datetime comment '注册时间',
   register_ip          varchar(255) comment '注册的ip',
   register_source      varchar(50) default 'PC' comment 'MOBI=手机端 PC=PC端',
   remark               varchar(255) comment '备注',
   primary key (id)
);

alter table t_user comment '用户表';
create unique index user_email_index on t_user
(
   email
);
create unique index user_mobile_index on t_user
(
   mobile
);
create unique index user_name_index on t_user
(
   user_name
);

create table t_order
(
   id                   bigint(20) not null,
   optlock              bigint(20) not null default 1 comment '版本号',
   create_time          datetime,
   update_time          datetime,
   order_number         varchar(50) not null comment '订单号',
   item_total           decimal(28,6) comment '商品总价',
   freight              decimal(28,6) default 0 comment '运费',
   total                decimal(28,6) comment '商品价格, 运费等其他费用',
   payment_total        decimal(28,6) comment '实际需要支付的金额（调价或优惠后）',
   buy_user_id          bigint(20),
   status               varchar(50) comment 'UNSUBMIT=未提交 SUBMIT=已提交 COMPLETE=已完成 CANCEL= 已关闭',
   pay_status           varchar(50) comment 'PENDING=待支付 PAYED=已支付 REFUND=已退款',
   shipment_status      varchar(50) comment 'UN_SEND=未发货 PART_SEND=部分发货 SEND=发货完成 RETURN=已退货',
   pay_time             datetime comment '支付时间',
   send_time            datetime comment '发货时间',
   order_address_id     bigint(20) comment '地址id',
   is_del               tinyint(1) default 0 comment '删除：0=否 1=是',
   completed_time       datetime,
   comment              varchar(255) comment '订单备注',
   shipping_method_id   bigint(20),   
   primary key (id)
);

create unique index order_number_index on t_order
(
   order_number
);

create table t_order_item
(
   id                   bigint(20) not null auto_increment,
   optlock              bigint(20) not null default 1 comment '版本号',
   order_id             bigint(20) comment '订单ID',,
   product_id           bigint(20) comment '商品ID',
   product_name         varchar(255) comment '商品名称',
   quantity             int comment '数量',
   unit_price           decimal(28,6) comment '单价',
   total_price          decimal(28,6) comment '总价',
   init_unit_price      decimal(28,6) comment '折扣前价格',
   comment              varchar(255) comment '备注',
   create_time          datetime,
   update_time          datetime,
   primary key (id)
);

create table t_order_address
(
   id                   bigint(20) not null auto_increment,
   optlock              bigint(20) not null default 1 comment '版本号',
   contact_name         varchar(255) comment '收货人姓名',
   contact_tel          varchar(255) comment '收货人电话',
   zip_code             varchar(255) comment '邮编',
   receive_province     bigint(20) comment '收货地址：省',
   receive_city         bigint(20) comment '收货地址：城市',
   receive_county       bigint(20) comment '收货地址：区',
   receive_address      varchar(255) comment '收货地址 详细地址',
   create_time          datetime comment '创建时间',
   update_time          datetime comment '修改时间',
   primary key (id)
);

create table t_product
(
   id                   bigint(20) not null auto_increment comment '主键',
   optlock              bigint(20) not null default 1 comment '版本号',
   name                 varchar(255),
   recommend            int default 0 comment '推荐值 0=未推荐 0标识推荐，越大约前面',
   status               int default 2 comment '1=上架 2=下架 3=删除',
   create_time          datetime,
   update_time          datetime,
   inventory            bigint(20) comment '可售库存',
   remark               varchar(255),
   primary key (id)
);

create table t_product_price
(
   id                   bigint(20) not null auto_increment comment '主键',
   optlock              bigint(20) not null default 1 comment '版本号',
   product_id           bigint(20) comment '商品ID',
   price                decimal(28,6) comment '价格',
   step_num             int comment '区间阶梯数',
   create_time          datetime comment '创建时间',
   primary key (id)
);
