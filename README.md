<h1> Telstra Server Side SMS API </h1>

The following project exposes the Telstra SMS API using a a spring boot application that has a RESTful method to invoke the Telstra SMS API.

manifest.yml
 
```
applications:
- name: pas-telstrasmsapi
  memory: 512M
  instances: 1
  host: pas-telstrasmsapi
  domain: mybluemix.net
  path: ./target/TelstraSMSAPI-1.0-SNAPSHOT.jar
  env:
   JBP_CONFIG_IBMJDK: "version: 1.8.+"
 ```
 
 Once deployed you can access the RESTful method as follows
 
 - Send SMS with default TEXT 
 
 ```
pas@Pass-MBP:~/ibm/customers/telstra/telstra-apis/test$ curl http://pas-telstrasmsapi.mybluemix.net/telstrasms?to=0411151350
{"messageId":"98C10335A0830AE1EC244DA8EE846A55"}
  ```
  
- Post SMS with TEXT and NUMBER to use
   
```
pasapicella@pas-macbook-pro:~/ibm/customers/telstra/telstra-apis/test$ curl --data "to=0411151350&body=hi pas" http://pas-telstrasmsapi.mybluemix.net/telstrasms
{"messageId":"7CCD0C2520E755DB44B46B45F6A3B9EC"}
```
  
Pas Apicella [pasapi at au1.ibm.com] is a Bluemix Technical Specialist at IBM Australia