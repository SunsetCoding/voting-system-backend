# 实时投票系统

一个基于 Spring Boot + React 的实时投票系统，支持 WebSocket 实时数据推送。

## 功能特性

- ✅ 单选题投票（每人限投一次）
- ✅ 实时统计结果展示
- ✅ WebSocket 实时数据推送
- ✅ 图表可视化展示
- ✅ Docker 容器化部署

## 技术栈

### 后端
- Spring Boot 3.2.0
- Spring WebSocket
- Spring Data JPA
- MySQL 8.0
- Maven

### 前端
- React 18
- TypeScript
- Chart.js
- Axios
- WebSocket

## 快速开始

### 使用 Docker Compose（推荐）

```bash
# 克隆项目
git clone <repository-url>
cd voting-system

# 启动服务
docker-compose up -d

# 访问应用
# 前端: http://localhost:3000
# 后端: http://localhost:8080
```



```bash
# 克隆项目
git clone <repository-url>
cd voting-system

# 启动服务
docker-compose up -d

# 访问应用
# 前端: http://localhost:3000
# 后端: http://localhost:8080
```



## 本地开发

### 后端启动

```bash
cd backend

# 安装依赖
mvn clean install

# 启动 MySQL（需要先安装）
# 创建数据库: CREATE DATABASE voting;

# 修改 application.properties 中的数据库配置

# 启动应用
mvn spring-boot:run
```

### 前端启动

```bash
cd frontend

# 安装依赖
npm install

# 启动开发服务器
npm start

# 访问 http://localhost:3000
```



## API 文档

### 获取投票信息

```
GET /api/poll

响应示例:
{
  "id": 1,
  "question": "你最喜欢的编程语言是什么？",
  "options": [
    {
      "id": 1,
      "text": "Java",
      "voteCount": 10
    },
    ...
  ]
}
```

### 提交投票

```
POST /api/poll/vote

请求体:
{
  "optionId": 1,
  "voterIdentifier": "unique_voter_id"
}

响应: 返回更新后的投票信息
```

### WebSocket 连接

```
ws://localhost:8080/ws/poll

连接后会接收到实时的投票结果更新
```

### 系统架构

┌─────────────┐     ┌─────────────┐     ┌─────────────┐
│   Browser   │────▶│   Nginx     │────▶│   Backend   │
│   (React)   │◀────│  (Reverse   │◀────│  (Spring    │
│             │     │   Proxy)    │     │   Boot)     │
└─────────────┘     └─────────────┘     └─────────────┘
       │                                        │
       │                                        │
       └──────── WebSocket ────────────────────┘
                                               │
                                               ▼
                                        ┌─────────────┐
                                        │   MySQL     │
                                        │  Database   │
                                        └─────────────┘



## 部署说明

### docker部署

项目提供了完整的 Docker 配置：

+ `backend/Dockerfile` - 后端镜像构建
+ `frontend/Dockerfile` - 前端镜像构建
+ `docker-compose.yml` - 容器编排配置

### Kubernetes 部署

```yaml
# k8s-deployment.yaml
apiVersion: apps/v1
kind: Deployment
metadata:
  name: voting-backend
spec:
  replicas: 2
  selector:
    matchLabels:
      app: voting-backend
  template:
    metadata:
      labels:
        app: voting-backend
    spec:
      containers:
      - name: backend
        image: voting-backend:latest
        ports:
        - containerPort: 8080
        env:
        - name: SPRING_DATASOURCE_URL
          value: jdbc:mysql://mysql-service:3306/voting
---
apiVersion: v1
kind: Service
metadata:
  name: voting-backend-service
spec:
  selector:
    app: voting-backend
  ports:
  - port: 8080
    targetPort: 8080
  type: ClusterIP
```



## 注意事项

+ 投票限制基于浏览器本地存储，清除浏览器数据后可重新投票

+ WebSocket 连接断开会自动重连

+ 生产环境需要配置适当的 CORS 策略

+ 建议使用 HTTPS 和 WSS 协议



