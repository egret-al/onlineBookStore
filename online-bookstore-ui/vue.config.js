module.exports = {
  //webpack配置
  configureWebpack: {
    devServer: {
      port: 8001,
      open: true,
      //跨域
      proxy: {
        '/api': {
          target: 'http://127.0.0.1:9527/',
          changOrigin: true,  //允许跨域
          pathRewrite: {
            /* 重写路径，当我们在浏览器中看到请求的地址为：http://localhost:8080/api/core/getData/userInfo 时
              实际上访问的地址是：http://127.0.0.1:9527/core/getData/userInfo,因为重写了 /api
             */
            '^/api': '' 
          }
        },
      },
      //mock数据
      before(app) {
        //用户信息池
        let userPoor = [{
            username: 'zhangsan',
            password: '123456'
          },
          {
            username: 'lisi',
            password: '123456'
          }
        ]
        //注册接口
        app.get('/api/register', (req, res) => {
          const {
            username,
            password
          } = req.query
          const userLength = userPoor.filter(v => v.username == username).length
          if (userLength > 0) {
            res.json({
              success: false,
              message: '用户名已存在，注册失败！'
            })
          } else {
            res.json({
              success: true,
              message: '注册成功！'
            })
          }
        })
        //登录接口
        let tokenKey = 'rkc'
        app.get('/api/login', (req, res) => {
          const {
            username,
            password
          } = req.query
          if (username == 'zhangsan' && password == '123456' || username == 'lisi' && password == '123456') {
            res.json({
              code: 1,
              message: '登录成功',
              token: tokenKey + '-' + username + '-' + (new Date().getTime() + 60 * 60 * 1000)
            })
          } else {
            res.json({
              code: 0,
              message: '账号或者密码错误！'
            })
          }
        })
      }
    }
  },

  css: {
    loaderOptions: {
      stylus: {
        'resolve url': true,
        'import': [
          './src/theme'
        ]
      }
    }
  },

  pluginOptions: {
    'cube-ui': {
      postCompile: true,
      theme: true
    }
  }
}