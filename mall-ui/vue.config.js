const {defineConfig} = require('@vue/cli-service')
const port=8001
module.exports = defineConfig({
    transpileDependencies: true,
    //关闭eslint校验
    lintOnSave: false,
    devServer: {
        port: port,
        open: true,
        proxy: {
            '/api': {
                target: `http://localhost:${port}`,
                ws: true,
                changeOrigin: true,
                pathRewrite:{
                    '^/api':''
                }
            }
        }
    }
})
