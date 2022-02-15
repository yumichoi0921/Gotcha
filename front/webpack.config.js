const path = require("path");

module.exports = {
  devServer: {
    historyApiFallback: true,
    historyApiFallback: {
      rewrites: [
        {
          from: /.*/,
          to: path.posix.join(config.dev.assertsPublicPath, "index.html"),
        },
      ],
    },
  },
};
