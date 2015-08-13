select 
	binB.about as binBID,
	binC.about as binCID,
	binD.about as binDID,
	thermometer.about as thermometerID,
	toyBin.about as toyBinID
from topics
		{/almanac/observation/iotentity/_a2ae2f57_1362_3339_a7e4_d29799029cc5}.std:lastevent() as toyBin,
		{/almanac/observation/iotentity/_89319db8_9d3f_315c_a4c6_9e2132ae6a44}.std:lastevent() as binB,
		{/almanac/observation/iotentity/_a1be5ba7_bb8a_3b69_b376_06d89fbecee6}.std:lastevent() as binC,
		{/almanac/observation/iotentity/_597438b1_a750_3832_9205_57ede636ba67}.std:lastevent() as binD,
		{/almanac/observation/iotentity/_8e298178_bccf_390b_b612_8d5d5d990e40}.std:lastevent() as thermometer 
where 
		cast(toyBin.properties[0].ioTStateObservation[0].value,double) > 10.0 and 
		cast(binB.properties[0].ioTStateObservation[0].value,double) > 80.0 and
		cast(binC.properties[0].ioTStateObservation[0].value,double) > 80.0 and
		cast(binD.properties[0].ioTStateObservation[0].value,double) > 80.0 and
		cast(thermometer.properties[0].ioTStateObservation[0].value,double) > 35.0

{"Time":"2015-05-21T09:24:40.813+0000","ResultValue":"9eafcffc8bdd1f3ff62e2e4e2b3a2fa41973e1a340eeadd6c8b9c38a28012290","ResultType":"id","Datastream":{"id":"0"},"Sensor":{"id":"43507248521930584608553320857595913329369016028856864660635270168824312350546"}}
{"Time":"2015-05-21T09:24:40.813+0000","ResultValue":100.0,"ResultType":"ressult","Datastream":{"id":"1"},"Sensor":{"id":"43507248521930584608553320857595913329369016028856864660635270168824312350546"}}

{"Time":"2015-05-21T09:24:40.818+0000","ResultValue":"3a70ea31c465273d8bd0d94cfeaf434fbd63b0ff6cd767a7437efff72862361d","ResultType":"id","Datastream":{"id":"0"},"Sensor":{"id":"43507248521930584608553320857595913329369016028856864660635270168824312350546"}}
{"Time":"2015-05-21T09:24:40.818+0000","ResultValue":80.0,"ResultType":"ressult","Datastream":{"id":"1"},"Sensor":{"id":"43507248521930584608553320857595913329369016028856864660635270168824312350546"}}

{"Time":"2015-05-21T09:24:40.819+0000","ResultValue":"7b71581a4374c09cd3b3c6f7a20c6c41b5dcc33a9d4d0c9608a10e9a0652529b","ResultType":"id","Datastream":{"id":"0"},"Sensor":{"id":"43507248521930584608553320857595913329369016028856864660635270168824312350546"}}
{"Time":"2015-05-21T09:24:40.820+0000","ResultValue":99.99,"ResultType":"ressult","Datastream":{"id":"1"},"Sensor":{"id":"43507248521930584608553320857595913329369016028856864660635270168824312350546"}}

{"Time":"2015-05-21T09:24:40.821+0000","ResultValue":"9dd008feef3d3b7a74e362eb4a843a97fdb0ce692d5c68d84c870e82b89eec1d","ResultType":"id","Datastream":{"id":"0"},"Sensor":{"id":"43507248521930584608553320857595913329369016028856864660635270168824312350546"}}
{"Time":"2015-05-21T09:24:40.822+0000","ResultValue":100.0,"ResultType":"ressult","Datastream":{"id":"1"},"Sensor":{"id":"43507248521930584608553320857595913329369016028856864660635270168824312350546"}}

{"Time":"2015-05-21T09:24:40.823+0000","ResultValue":"9dd008feef3d3b7a74e362eb4a843a97fdb0ce692d5c68d84c870e82b89eec1d","ResultType":"id","Datastream":{"id":"0"},"Sensor":{"id":"43507248521930584608553320857595913329369016028856864660635270168824312350546"}}
{"Time":"2015-05-21T09:24:40.823+0000","ResultValue":100.0,"ResultType":"ressult","Datastream":{"id":"1"},"Sensor":{"id":"43507248521930584608553320857595913329369016028856864660635270168824312350546"}}

{"Time":"2015-05-21T09:24:40.824+0000","ResultValue":"43542c3963f67c1c3a7dd27b1d7c4f056c53221a3fe6362f98b7fa2691d73748","ResultType":"id","Datastream":{"id":"0"},"Sensor":{"id":"43507248521930584608553320857595913329369016028856864660635270168824312350546"}}
{"Time":"2015-05-21T09:24:40.824+0000","ResultValue":80.0,"ResultType":"ressult","Datastream":{"id":"1"},"Sensor":{"id":"43507248521930584608553320857595913329369016028856864660635270168824312350546"}}

{"Time":"2015-05-21T09:24:40.826+0000","ResultValue":"4d534519c29ed97354e30062616e0ff3078671cc8dd4db28446401cce2438c54","ResultType":"id","Datastream":{"id":"0"},"Sensor":{"id":"43507248521930584608553320857595913329369016028856864660635270168824312350546"}}
{"Time":"2015-05-21T09:24:40.826+0000","ResultValue":100.0,"ResultType":"ressult","Datastream":{"id":"1"},"Sensor":{"id":"43507248521930584608553320857595913329369016028856864660635270168824312350546"}}

{"Time":"2015-05-21T09:24:40.828+0000","ResultValue":"91586b39d337d53cdb4b262f1d8030f79c42fdb8610b5462eb287b5dbfffecd0","ResultType":"id","Datastream":{"id":"0"},"Sensor":{"id":"43507248521930584608553320857595913329369016028856864660635270168824312350546"}}
{"Time":"2015-05-21T09:24:40.828+0000","ResultValue":80.0,"ResultType":"ressult","Datastream":{"id":"1"},"Sensor":{"id":"43507248521930584608553320857595913329369016028856864660635270168824312350546"}}

{"Time":"2015-05-21T09:24:40.829+0000","ResultValue":"4f1beca0abb193c10af716df42b23ae91cf16e1919b6dfbf1b45a7481972d23d","ResultType":"id","Datastream":{"id":"0"},"Sensor":{"id":"43507248521930584608553320857595913329369016028856864660635270168824312350546"}}
{"Time":"2015-05-21T09:24:40.830+0000","ResultValue":99.99,"ResultType":"ressult","Datastream":{"id":"1"},"Sensor":{"id":"43507248521930584608553320857595913329369016028856864660635270168824312350546"}}

{"Time":"2015-05-21T09:24:40.831+0000","ResultValue":"d951cf2bbf1707bdee691cec0aff81177944700ca8c9e2e5c8baae9a3ecdb243","ResultType":"id","Datastream":{"id":"0"},"Sensor":{"id":"43507248521930584608553320857595913329369016028856864660635270168824312350546"}}
{"Time":"2015-05-21T09:24:40.831+0000","ResultValue":99.99,"ResultType":"ressult","Datastream":{"id":"1"},"Sensor":{"id":"43507248521930584608553320857595913329369016028856864660635270168824312350546"}}

{"Time":"2015-05-21T09:24:40.832+0000","ResultValue":"8049551a6880419604c24946ad0254a5043ae8a32aef2ea5b7176d1a874aa83b","ResultType":"id","Datastream":{"id":"0"},"Sensor":{"id":"43507248521930584608553320857595913329369016028856864660635270168824312350546"}}
{"Time":"2015-05-21T09:24:40.833+0000","ResultValue":80.0,"ResultType":"ressult","Datastream":{"id":"1"},"Sensor":{"id":"43507248521930584608553320857595913329369016028856864660635270168824312350546"}}

{"Time":"2015-05-21T09:24:40.834+0000","ResultValue":"46472f68a8e20eb15579c98f3f5b8baecb212d942dbec4be437baead82169329","ResultType":"id","Datastream":{"id":"0"},"Sensor":{"id":"43507248521930584608553320857595913329369016028856864660635270168824312350546"}}
{"Time":"2015-05-21T09:24:40.834+0000","ResultValue":99.99,"ResultType":"ressult","Datastream":{"id":"1"},"Sensor":{"id":"43507248521930584608553320857595913329369016028856864660635270168824312350546"}}

{"Time":"2015-05-21T09:24:40.836+0000","ResultValue":"228abbfd80edc891c2bb8eefa0f34a67a943e13d62bcd1b2ccf9d43f7267aec9","ResultType":"id","Datastream":{"id":"0"},"Sensor":{"id":"43507248521930584608553320857595913329369016028856864660635270168824312350546"}}
{"Time":"2015-05-21T09:24:40.837+0000","ResultValue":100.0,"ResultType":"ressult","Datastream":{"id":"1"},"Sensor":{"id":"43507248521930584608553320857595913329369016028856864660635270168824312350546"}}

{"Time":"2015-05-21T09:24:40.839+0000","ResultValue":"228abbfd80edc891c2bb8eefa0f34a67a943e13d62bcd1b2ccf9d43f7267aec9","ResultType":"id","Datastream":{"id":"0"},"Sensor":{"id":"43507248521930584608553320857595913329369016028856864660635270168824312350546"}}
{"Time":"2015-05-21T09:24:40.839+0000","ResultValue":100.0,"ResultType":"ressult","Datastream":{"id":"1"},"Sensor":{"id":"43507248521930584608553320857595913329369016028856864660635270168824312350546"}}

{"Time":"2015-05-21T09:24:40.841+0000","ResultValue":"4810a72d0a7a7d35f028b297fab0c9cf520bb99b1b4fd2847f0e14093abbda02","ResultType":"id","Datastream":{"id":"0"},"Sensor":{"id":"43507248521930584608553320857595913329369016028856864660635270168824312350546"}}
{"Time":"2015-05-21T09:24:40.841+0000","ResultValue":99.99,"ResultType":"ressult","Datastream":{"id":"1"},"Sensor":{"id":"43507248521930584608553320857595913329369016028856864660635270168824312350546"}}

{"Time":"2015-05-21T09:24:40.842+0000","ResultValue":"4810a72d0a7a7d35f028b297fab0c9cf520bb99b1b4fd2847f0e14093abbda02","ResultType":"id","Datastream":{"id":"0"},"Sensor":{"id":"43507248521930584608553320857595913329369016028856864660635270168824312350546"}}
{"Time":"2015-05-21T09:24:40.843+0000","ResultValue":99.99,"ResultType":"ressult","Datastream":{"id":"1"},"Sensor":{"id":"43507248521930584608553320857595913329369016028856864660635270168824312350546"}}