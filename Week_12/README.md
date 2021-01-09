## Redis主从

 1. 1.把下载的redis安装包解压，
 2. 复制2份redis.windows.conf配置文件，分别命名为redis.windows6380.conf和redis.windows6381.conf
 3.  修改配置 对应的端口
 4.  启动服务
 

```
在redis安装目录下执行命令
redis-server.exe redis.windows.conf
redis-server.exe redis.windows6380.conf
redis-server.exe redis.windows6381.con
```

 5. 查看主从
 ![master_info](https://img-blog.csdnimg.cn/20210109141137108.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3RibWluZ3poYW8=,size_16,color_FFFFFF,t_70)

 ![slave_6380_info](https://img-blog.csdnimg.cn/2021010914121235.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3RibWluZ3poYW8=,size_16,color_FFFFFF,t_70)
![slave_6381_info](https://img-blog.csdnimg.cn/2021010914121237.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3RibWluZ3poYW8=,size_16,color_FFFFFF,t_70)
![master_setkey](https://img-blog.csdnimg.cn/20210109141211924.png)


## Redis哨兵（sentinel）

 1. 创建sentinel26379.conf文件，然后复制两份sentinel26380.conf、sentinel26381.conf sentinel26379.conf文件内容
 2. 启动哨兵服务

```
redis-server.exe sentinel26379.conf --sentinel
redis-server.exe sentinel26380.conf --sentinel
redis-server.exe sentinel26381.conf --sentinel
```

  **关闭6379服务，查看主从变化**

**6379没关闭之前，在6380服务看master是6379**

```
127.0.0.1:6380> info replication
# Replication
role:slave
master_host:127.0.0.1
master_port:6379
master_link_status:up
master_last_io_seconds_ago:1
master_sync_in_progress:0
slave_repl_offset:92870
slave_priority:100
slave_read_only:1
connected_slaves:0
master_repl_offset:0
repl_backlog_active:0
repl_backlog_size:1048576
repl_backlog_first_byte_offset:0
repl_backlog_histlen:0
```
**关闭6379之后，在6380服务可以看到master自动变成6381了**

```
127.0.0.1:6380> info Replication
# Replication
role:slave
master_host:127.0.0.1
master_port:6381
master_link_status:down
master_last_io_seconds_ago:-1
master_sync_in_progress:0
slave_repl_offset:1
master_link_down_since_seconds:jd
slave_priority:100
slave_read_only:1
connected_slaves:0
master_repl_offset:0
repl_backlog_active:0
repl_backlog_size:1048576
repl_backlog_first_byte_offset:0
repl_backlog_histlen:0
```
**这个时候再重新启动6379,6379会自动变成6381的slave**

```
127.0.0.1:6379> info Replication
# Replication
role:slave
master_host:127.0.0.1
master_port:6381
master_link_status:up
master_last_io_seconds_ago:0
master_sync_in_progress:0
slave_repl_offset:41823
slave_priority:100
slave_read_only:1
connected_slaves:0
master_repl_offset:0
repl_backlog_active:0
repl_backlog_size:1048576
repl_backlog_first_byte_offset:0
repl_backlog_histlen:0
```
**查看6381的信息**

```
127.0.0.1:6381> info replication
# Replication
role:master
connected_slaves:2
slave0:ip=127.0.0.1,port=6380,state=online,offset=1365957,lag=1
slave1:ip=127.0.0.1,port=6379,state=online,offset=1366090,lag=0
master_repl_offset:1366090
repl_backlog_active:1
repl_backlog_size:1048576
repl_backlog_first_byte_offset:317515
repl_backlog_histlen:1048576
```
