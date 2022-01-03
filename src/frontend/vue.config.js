module.exports = {
    configureWebpack: {
        devtool: 'source-map',
        module: {
            // rules: [
            //     {
            //         test: /\.scss$/,
            //         use: [
            //             'vue-style-loader',
            //             'css-loader',
            //             {
            //                 loader: 'sass-loader',
            //                 options: {
            //                     indentedSyntax: true,
            //                     // sass-loader version >= 8
            //                     sassOptions: {
            //                         indentedSyntax: true
            //                     }
            //                 }
            //             }
            //         ]
            //     }
            // ]
        },
    },
    devServer: {
        proxy: "http://localhost:8090"
    },
}