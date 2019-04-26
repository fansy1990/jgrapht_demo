console.log("Hello Demo");

var wsbroker = "localhost";  // mqtt websocket enabled broker
var wsport = 15675; // port for above
var client = new Paho.MQTT.Client(wsbroker, wsport, "/ws",
    "myclientid_");
console.info(client)
client.onConnectionLost = function (responseObject) {
    console.info("CONNECTION LOST - " + responseObject.errorMessage);
};

client.onMessageArrived = function (message) {
    console.info("RECEIVE ON " + message.destinationName + " PAYLOAD " + message.payloadString);
    if(message.payloadString == "DONE"){
        client.disconnect();
        console.info("关闭Client："+client.clientId)
    }
};

var options = {
    timeout: 3,
    keepAliveInterval: 30,
    onSuccess: function () {
        console.info("CONNECTION SUCCESS");
        client.subscribe('hello', {qos: 1});
    },
    onFailure: function (message) {
        console.info("CONNECTION FAILURE - " + message.errorMessage);
    }
};
if (location.protocol == "https:") {
    options.useSSL = true;
}
console.info("CONNECT TO " + wsbroker + ":" + wsport);
client.connect(options);