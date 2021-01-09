## Redis集群（cluster）

 **1. 安装ruby环境**
 双击下载的“rubyinstaller-3.0.0-1-x64.exe”安装即可，同样，为了操作方便，也是建议安装在盘符根目录下，如：C:\Ruby30-x64 ，安装会默认把ruby添加到path环境变量（需重启后，gem...才会生效)
 **2. 安装Ruby环境下Redis的驱动**
将下载的"Ruby环境下Redis的驱动文件(redis-3.2.2.gem)"拷贝到Ruby安装根目录（C:\Ruby30-x64 ）下。
![redis-3.2.2gem
](https://img-blog.csdnimg.cn/20210109164734753.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3RibWluZ3poYW8=,size_16,color_FFFFFF,t_70)

   然后执行安装命令如下：

```
gem install --local C:/Ruby27-x64/redis-3.2.2.gem
```
这里可能会执行命令失败，需要重启后环境变量才生效。

 **3. 将下载的“创建Redis集群的ruby脚本文件redis-trib.rb”文件拷贝到Redis安装根目录(F:\Program Files\Redis)下。**
 
![redis-trib](https://img-blog.csdnimg.cn/20210109164921394.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3RibWluZ3poYW8=,size_16,color_FFFFFF,t_70)

**4. 新建配置文件redis.windows-service-6380.conf**

```
port 6380      
loglevel notice    
logfile "server_log6380.txt"       
appendonly yes
appendfilename "appendonly.6380.aof"   
cluster-enabled yes                                    
cluster-config-file nodes.6380.conf
cluster-node-timeout 15000
cluster-slave-validity-factor 10
cluster-migration-barrier 1
cluster-require-full-coverage yes
```
复制5份,修改对应的port、logfile、appendfilename 、cluster-config-file

**5. **启动上面6个redis服务****

```
redis-server.exe redis.windows-service-6380.conf
redis-server.exe redis.windows-service-6381.conf
redis-server.exe redis.windows-service-6382.conf
redis-server.exe redis.windows-service-6383.conf
redis-server.exe redis.windows-service-6384.conf
redis-server.exe redis.windows-service-6385.conf
```

**6. **执行创建集群命令****

```
redis-trib.rb create --replicas 1 127.0.0.1:6380 127.0.0.1:6381 127.0.0.1:6382 127.0.0.1:6383 127.0.0.1:6384 127.0.0.1:6385
```

```
F:\Program Files\Redis>redis-trib.rb create --replicas 1 127.0.0.1:6380 127.0.0.1:6381 127.0.0.1:6382 127.0.0.1:6383 127.0.0.1:6384 127.0.0.1:6385
>>> Creating cluster
Connecting to node 127.0.0.1:6380: OK
Connecting to node 127.0.0.1:6381: OK
Connecting to node 127.0.0.1:6382: OK
Connecting to node 127.0.0.1:6383: OK
Connecting to node 127.0.0.1:6384: OK
Connecting to node 127.0.0.1:6385: OK
>>> Performing hash slots allocation on 6 nodes...
Using 3 masters:
127.0.0.1:6380
127.0.0.1:6381
127.0.0.1:6382
Adding replica 127.0.0.1:6383 to 127.0.0.1:6380
Adding replica 127.0.0.1:6384 to 127.0.0.1:6381
Adding replica 127.0.0.1:6385 to 127.0.0.1:6382
M: 5a1a16030291d4c707e26e6b8009a98db0ba54f3 127.0.0.1:6380
   slots:0-5460 (5461 slots) master
M: edb193125d24c47c15968cc9ee0f36507035584c 127.0.0.1:6381
   slots:5461-10922 (5462 slots) master
M: 64dbabe59b6b9a76a6bee4ab6b4418c1054f84b3 127.0.0.1:6382
   slots:10923-16383 (5461 slots) master
S: 2a4614dd4db551304d307ff5cf15ba297901316d 127.0.0.1:6383
   replicates 5a1a16030291d4c707e26e6b8009a98db0ba54f3
S: 6479a5abea88ee9ecbb35916aafa39b9d9aba69d 127.0.0.1:6384
   replicates edb193125d24c47c15968cc9ee0f36507035584c
S: e61cfdc519e98923685097dd30686cb902a95151 127.0.0.1:6385
   replicates 64dbabe59b6b9a76a6bee4ab6b4418c1054f84b3
Can I set the above configuration? (type 'yes' to accept): yes
>>> Nodes configuration updated
>>> Assign a different config epoch to each node
>>> Sending CLUSTER MEET messages to join the cluster
Waiting for the cluster to join...
>>> Performing Cluster Check (using node 127.0.0.1:6380)
M: 5a1a16030291d4c707e26e6b8009a98db0ba54f3 127.0.0.1:6380
   slots:0-5460 (5461 slots) master
M: edb193125d24c47c15968cc9ee0f36507035584c 127.0.0.1:6381
   slots:5461-10922 (5462 slots) master
M: 64dbabe59b6b9a76a6bee4ab6b4418c1054f84b3 127.0.0.1:6382
   slots:10923-16383 (5461 slots) master
M: 2a4614dd4db551304d307ff5cf15ba297901316d 127.0.0.1:6383
   slots: (0 slots) master
   replicates 5a1a16030291d4c707e26e6b8009a98db0ba54f3
M: 6479a5abea88ee9ecbb35916aafa39b9d9aba69d 127.0.0.1:6384
   slots: (0 slots) master
   replicates edb193125d24c47c15968cc9ee0f36507035584c
M: e61cfdc519e98923685097dd30686cb902a95151 127.0.0.1:6385
   slots: (0 slots) master
   replicates 64dbabe59b6b9a76a6bee4ab6b4418c1054f84b3
[OK] All nodes agree about slots configuration.
>>> Check for open slots...
>>> Check slots coverage...
[OK] All 16384 slots covered.
```

**7. **查看集群并检验****

```
F:\Program Files\Redis>redis-trib.rb check 127.0.0.1:6380
Connecting to node 127.0.0.1:6380: OK
Connecting to node 127.0.0.1:6383: OK
Connecting to node 127.0.0.1:6381: OK
Connecting to node 127.0.0.1:6385: OK
Connecting to node 127.0.0.1:6382: OK
Connecting to node 127.0.0.1:6384: OK
>>> Performing Cluster Check (using node 127.0.0.1:6380)
M: 5a1a16030291d4c707e26e6b8009a98db0ba54f3 127.0.0.1:6380
   slots:0-5460 (5461 slots) master
   1 additional replica(s)
S: 2a4614dd4db551304d307ff5cf15ba297901316d 127.0.0.1:6383
   slots: (0 slots) slave
   replicates 5a1a16030291d4c707e26e6b8009a98db0ba54f3
M: edb193125d24c47c15968cc9ee0f36507035584c 127.0.0.1:6381
   slots:5461-10922 (5462 slots) master
   1 additional replica(s)
S: e61cfdc519e98923685097dd30686cb902a95151 127.0.0.1:6385
   slots: (0 slots) slave
   replicates 64dbabe59b6b9a76a6bee4ab6b4418c1054f84b3
M: 64dbabe59b6b9a76a6bee4ab6b4418c1054f84b3 127.0.0.1:6382
   slots:10923-16383 (5461 slots) master
   1 additional replica(s)
S: 6479a5abea88ee9ecbb35916aafa39b9d9aba69d 127.0.0.1:6384
   slots: (0 slots) slave
   replicates edb193125d24c47c15968cc9ee0f36507035584c
[OK] All nodes agree about slots configuration.
>>> Check for open slots...
>>> Check slots coverage...
[OK] All 16384 slots covered.

F:\Program Files\Redis>redis-cli -c -h 127.0.0.1 -p 6380
127.0.0.1:6380> get name
-> Redirected to slot [5798] located at 127.0.0.1:6381
(nil)
127.0.0.1:6381> set name branchen
OK
```
**8.连6385端口校检**

```
F:\Program Files\Redis>redis-cli -c -h 127.0.0.1 -p 6385
127.0.0.1:6385> get name
-> Redirected to slot [5798] located at 127.0.0.1:6381
"branchen"
127.0.0.1:6381>
```
