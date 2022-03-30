module.exports = {
    configureWebpack: {
        devtool: 'source-map',
        module: {
        },
    },
    devServer: {
        proxy: "http://localhost:8090"
    },
}