const path = require('path');
const HtmlWebpackPlugin = require('html-webpack-plugin');
const VueLoaderPlugin = require('vue-loader/lib/plugin');

module.exports = {
  // 入口文件
  entry: './main.js',
  // 输出配置
  output: {
    path: path.resolve(__dirname, 'dist'),
    filename: 'bundle.js'
  },
  // 模块加载器
  module: {
    rules: [
      // Vue组件加载器
      {
        test: /\.vue$/,
        loader: 'vue-loader'
      },
      // CSS加载器
      {
        test: /\.css$/,
        use: ['style-loader', 'css-loader']
      },
      // JavaScript加载器
      {
        test: /\.js$/,
        exclude: /node_modules/,
        loader: 'babel-loader'
      }
    ]
  },
  // 插件配置
  plugins: [
    // VueLoader插件
    new VueLoaderPlugin(),
    // HTML模板插件
    new HtmlWebpackPlugin({
      template: './index.html',
      filename: 'index.html'
    })
  ],
  // 开发服务器配置
  devServer: {
    static: {
      directory: path.join(__dirname, 'dist')
    },
    compress: true,
    port: 8080,
    open: true
  },
  // 解析配置
  resolve: {
    extensions: ['.js', '.vue'],
    alias: {
      'vue$': 'vue/dist/vue.esm.js'
    }
  }
};
