
let createWebsocket = {

    websocketForDiscovery: function ()
    {
        var websocket = new WebSocket("wss://localhost:8443/endpoint");

        websocket.onopen = function (message) {processOnOpen(message);};

        websocket.onmessage = function (message) {processOnMessage(message);};

        websocket.onclose = function (message) {processOnClose(message);};

        websocket.onerror = function (message) {processOnError(message);};

        function processOnOpen(message)
        {

        }

        function processOnMessage(message)
        {
            let data = message.data;

            if(data.includes("Unsuccessfull"))
            {
                iziToast.error({
                    title: 'Discovery',
                    message: message.data,
                    position: 'topRight', // bottomRight, bottomLeft, topRight, topLeft, topCenter, bottomCenter, center
                    pauseOnHover: false
                });
            }
            else
            {
                iziToast.success({
                    title: 'Discovery',
                    message: message.data,
                    position: 'topRight', // bottomRight, bottomLeft, topRight, topLeft, topCenter, bottomCenter, center
                    pauseOnHover: false
                });
            }

            discovery.getDiscoveryDevices();
        }

        function sendMessageToServer()
        {

        }

        function processOnClose(message)
        {
            createWebsocket.websocketForDiscovery();
        }
        function processOnError(message)
        {
            console.log(message.data);
        }

    }
}

