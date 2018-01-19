const http = require('http');
const url = require('url');
const querystring = require("querystring");

const fs = require("fs");
const path = require("path");

const hostname = 'localhost';
const port = 4000;

// !!!!! Below variables need to be customized before using
const allowedDomain='https://114.115.140.214';  // The domain of the pathtraq server
const labwriterInputDir='C:\\SlideMate Parsers';
const labwriterInputDirDriver='C:\\';
// End customized variables section

const log4js = require('log4js');
log4js.configure({
  appenders: [
    { type: 'dateFile', filename: 'logs/labwriter-gw.log', pattern: '-yyyy-MM-dd.log', 'maxLogSize': 2048000, 'backups': 5}
  ]
});
var logger = log4js.getLogger();

const server = http.createServer((req, res) => {
  res.statusCode = 200;
res.setHeader('Content-Type', 'text/plain');
res.setHeader('Access-Control-Allow-Origin', allowedDomain);
res.setHeader('Access-Control-Allow-Credentials', true);

var arg = url.parse(req.url).query;
logger.info(`Input parameter is ${arg}.`);

var idParam = querystring.parse(arg).id;
if (idParam == null || idParam.length == 0) {
  logger.warn("id is empty");
  res.statusCode = 400;
  res.end();
  return console.error("id is empty");
}
var ids = idParam.split(",");

var typeParam = querystring.parse(arg).type;
if (typeof(typeParam) != "undefined") {
  var types = typeParam.split(",");
}
var filepath;
for (var i = 0; i < ids.length; i++) {
  logger.info(ids[i]);
  if (i == 0) {
    filepath = path.format({root:labwriterInputDirDriver,dir:labwriterInputDir,base:ids[i]});
    //filepath = path.format({dir:'.',base:ids[i]});
  }

  var content = ids[i];
  if (typeof(typeParam) != "undefined" && types[i] != "") {
    var note = types[i].replace(new RegExp(';',"gm"),'$');
    content = content+"$"+note;
  }

  fs.appendFileSync(filepath, content+"\n");
  /*  async may result in incorrect order so don't use it.
  fs.appendFile(filepath, content+"\n", function(err) {
    if (err) {
      logger.error(err);
      res.statusCode = 400;
      res.end();
      return console.error(err);
    }
  });
  */
}
res.end();
});

server.listen(port, hostname, () => {
  logger.info(`Server running at http://${hostname}:${port}/`);
});