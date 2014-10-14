"use strict";
/*
	Virtualization Layer | MQTT module
		by Alexandre Alapetite http://alexandre.alapetite.fr
			from Alexandra Institute http://www.alexandra.dk
			for the ALMANAC European project http://www.almanac-project.eu
*/

module.exports = function (almanac) {
	var mqtt = require('mqtt');
	almanac.mqttClient = mqtt.createClient(almanac.config.hosts.mqttBroker.port, almanac.config.hosts.mqttBroker.host);

	almanac.mqttClient.on('message', function (topic, message) {
		try {
			almanac.log.verbose('VL', 'MQTT: ' + topic + ': ' + message);
			if (topic && (topic.indexOf('/iotentity') > 0)) {
				var json = JSON.parse(message);
				almanac.webSocket.emit('mqtt', {
						instance: almanac.config.hosts.virtualizationLayerPublic,
						topic: topic,
						body: json,
					});
				//almanac.storageManager.postMqttEvent(topic, json);	//Temporary forward to StorageManager
				almanac.peering.mqttPeering(topic, json);	//Peering with other VirtualizationLayers
			}
		} catch (ex) {
			almanac.log.warn('VL', 'MQTT message error: ' + ex);
		}
	});

	almanac.mqttClient.subscribe('/almanac/#');

	setTimeout(function () {
			almanac.mqttClient.publish('/almanac/0/info', JSON.stringify({
					info: 'VirtualizationLayer MQTT started',
				}));
		}, 5000);

	setInterval(function () {
			almanac.mqttClient.publish('/almanac/0/info', JSON.stringify({
					info: 'VirtualizationLayer alive',
				}));
		}, 60000);

	/*setInterval(function () {	//TODO: Remove after testing
			almanac.mqttClient.publish('/almanac/observations/iotentity/', '{"About":"dd2f87dd-4f80-455b-a939-e22f7f20a0c1","Properties":[{"IoTStateObservation":[{"Value":"3.36","PhenomenonTime":"2014-10-08T15:42:07.906Z","ResultTime":"2014-10-08T15:42:17.906Z"}],"About":"pipe1:PhMeter:getPh"}]}');
		}, 20000);*/
};