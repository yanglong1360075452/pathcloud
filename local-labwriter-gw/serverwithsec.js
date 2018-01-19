const https = require('https');
const url = require('url');
const querystring = require("querystring");

const fs = require("fs");
const path = require("path");

const hostname = 'localhost';
const port = 4000;

// !!!!! Below variables need to be customized before using
const allowedDomain='https://114.115.202.113';  // The domain of the pathtraq server
const labwriterInputDir='C:\\SlideMate Parsers';
const labwriterInputDirDriver='C:\\';
// End customized variables section

const log4js = require('log4js');
log4js.configure({
    appenders: [
        { type: 'file', filename: 'logs/labwriter-gw.log', 'maxLogSize': 2048000, 'backups': 5}
    ]
});
var logger = log4js.getLogger();

var keyFs = require('fs');

var options = {
    key: keyFs.readFileSync('keys/flucloud-private.pem'),
    //ca: keyFs.readFileSync('keys/flucloud-csr.pem'),
    cert: keyFs.readFileSync('keys/flucloud-certificate.txt')
};

https.createServer(options,function(req,res){
    res.writeHead(200);
    res.end('hello world\n');
}).listen(3000,'127.0.0.1');

const server = https.createServer(options, function(req, res){
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

    var filepath;
    for (var i = 0; i < ids.length; i++) {
        logger.info(ids[i]);
        if (i == 0) {
            //filepath = path.format({root:labwriterInputDirDriver,dir:labwriterInputDir,base:ids[i]});
            filepath = path.format({dir:".",base:ids[i]});
        }
        fs.appendFile(filepath, ids[i]+"\n", function(err) {
            if (err) {
                logger.error(err);
                res.statusCode = 400;
                res.end();
                return console.error(err);
            }
        });
    }
    res.end();
});

server.listen(port, hostname, () => {
    logger.info(`Server running at https://${hostname}:${port}/`);
});
