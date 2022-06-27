# vertx-http2-test
vertx-http2-test

# reproducer steps
1. start the NettyHttpServer and it will listen port 8080
2. run junit case send in Http2ClientTest 
   > the case will print the log of "Connection evicted" and asyncResult also blocked
