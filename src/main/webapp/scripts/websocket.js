
let websocket = {

    websocketForDiscovery: function ()
    {

        var websocket = new WebSocket("ws://localhost:8080/login/websocket/discovery");
        websocket.onopen = function (message) {processOnOpen(message);};
        websocket.onmessage = function (message) {processOnMessage(message)}
        websocket.onclose = function (message) {processOnClose(message);};
        websocket.onerror = function (message) {processOnError(message);};

        function processOnOpen(message){
            // messageTextArea.value += "Server Connect... \n";
        }

        function processOnMessage(message)
        {
            // messageTextArea.value += "Receive from Server => : "+message.data+"\n";

            $(".displayMessageBody").text(message.data);

            $("#displayMessageButton").click();

            discovery.getDiscoveryDevices();
        }

        function sendMessageToServer()
        {
/*
            if (textMessage.value!="close")
            {
                while (true)
                {
                    websocket.send(textMessage.value);
                }
                messageTextArea.value += "Send to the Server => : "+textMessage.value+"\n";
                textMessage.value = "";
            }
            else websocket.close();
*/
        }

        function processOnClose(message)
        {
/*
            websocket.send("Client Disconnected.....");
            messageTextArea.value += "Server Disconnected....\n";
*/
        }
        function processOnError(message)
        {
            // messageTextArea.value += "Error......"
            console.log(message);
        }

    }
}

