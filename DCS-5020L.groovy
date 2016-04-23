/**
 *	D-Link DCS-5020L v2.0.0
 *  Image Capture and Video Streaming courtesy Patrick Stuart (patrick@patrickstuart.com)
 *  
 *  Copyright 2015 blebson
 *
 *  Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 *  in compliance with the License. You may obtain a copy of the License at:
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software distributed under the License is distributed
 *  on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License
 *  for the specific language governing permissions and limitations under the License.
 *
 */
metadata {
	definition (name: "DCS-5020L-Test", author: "blebson") {
		capability "Image Capture"
		capability "Sensor"
		capability "Switch"
        capability "Switch Level"
        capability "Refresh"
        capability "Notification"
        capability "Configuration"
		capability "Video Camera"
		capability "Video Capture"
        
		attribute "hubactionMode", "string"
        attribute "position", "string"
        attribute "switch2", "string"
        
        command "left"
		command "right"
		command "up"
		command "down"
        command "home"
        command "presetOne"
        command "presetTwo"
        command "presetThree"
        command "nvOn"
        command "nvOff"
        command "nvAuto"
        command "presetCommand"
        command "start"
		command "stop"
        command "vidOn"
        command "vidOff"
	}

    preferences {
    input("CameraIP", "string", title:"Camera IP Address", description: "Please enter your camera's IP Address", required: true, displayDuringSetup: true)
    input("CameraPort", "string", title:"Camera Port", description: "Please enter your camera's HTTP Port", defaultValue: 80 , required: true, displayDuringSetup: true)
    input("VideoIP", "string", title:"Video IP Address", description: "Please enter your camera's IP Address (use external IP if you are using port forwarding)", required: true, displayDuringSetup: true)
    input("VideoPort", "string", title:"Video Port", description: "Please enter your camera's HTTP Port (use external Port if you are using port forwarding)", defaultValue: 80 , required: true, displayDuringSetup: true)
    input("CameraUser", "string", title:"Camera User", description: "Please enter your camera's username", required: false, displayDuringSetup: true)
    input("CameraPassword", "password", title:"Camera Password", description: "Please enter your camera's password", required: false, displayDuringSetup: true)
    input("CameraPresetOne", "string", title:"Camera Preset 1", description: "Please enter which preset view you want to use (other than 'Home')", defaultValue: 1 , required: true, displayDuringSetup: true)
	input("CameraPresetTwo", "string", title:"Camera Preset 2", description: "Please enter which preset view you want to use (other than 'Home')", defaultValue: 2 , required: false, displayDuringSetup: true)
    input("CameraPresetThree", "string", title:"Camera Preset 3", description: "Please enter which preset view you want to use (other than 'Home')", defaultValue: 3 , required: false, displayDuringSetup: true)
    }
    
    mappings {
   		path("/getInHomeURL") {
       		action:
       		[GET: "getInHomeURL"]
   		}
	}
    
	simulator {
    
	}

    tiles (scale: 2){
    multiAttributeTile(name: "videoPlayer", type: "videoPlayer", width: 6, height: 4) {
			tileAttribute("device.switch2", key: "CAMERA_STATUS") {
				attributeState("on", label: "Active", icon: "st.camera.dlink-indoor", action: "vidOff", backgroundColor: "#79b821", defaultState: true)
				attributeState("off", label: "Inactive", icon: "st.camera.dlink-indoor", action: "vidOn", backgroundColor: "#ffffff")
				attributeState("restarting", label: "Connecting", icon: "st.camera.dlink-indoor", backgroundColor: "#53a7c0")
				attributeState("unavailable", label: "Unavailable", icon: "st.camera.dlink-indoor", action: "refresh.refresh", backgroundColor: "#F22000")
			}

			tileAttribute("device.camera", key: "PRIMARY_CONTROL") {
				attributeState("on", label: "Active", icon: "st.camera.dlink-indoor", backgroundColor: "#79b821", defaultState: true)
				attributeState("off", label: "Inactive", icon: "st.camera.dlink-indoor", backgroundColor: "#ffffff")
				attributeState("restarting", label: "Connecting", icon: "st.camera.dlink-indoor", backgroundColor: "#53a7c0")
				attributeState("unavailable", label: "Unavailable", icon: "st.camera.dlink-indoor", backgroundColor: "#F22000")
			}

			tileAttribute("device.startLive", key: "START_LIVE") {
				attributeState("live", action: "start", defaultState: true)
			}

			tileAttribute("device.stream", key: "STREAM_URL") {
				attributeState("activeURL", defaultState: true)
			}
			tileAttribute("device.betaLogo", key: "BETA_LOGO") {
				attributeState("betaLogo", label: "", value: "", defaultState: true)
			}
            }
    	carouselTile("cameraDetails", "device.image", width: 3, height: 2) { }
        standardTile("take", "device.image", width: 1, height: 1, canChangeIcon: false, inactiveLabel: true, canChangeBackground: false) {
            state "take", label: "Take", action: "Image Capture.take", icon: "st.camera.camera", backgroundColor: "#FFFFFF", nextState:"taking"
            state "taking", label:'Taking', action: "", icon: "st.camera.take-photo", backgroundColor: "#53a7c0"
            state "image", label: "Take", action: "Image Capture.take", icon: "st.camera.camera", backgroundColor: "#FFFFFF", nextState:"taking"
        }
        standardTile("up", "device.image", width: 1, height: 1, canChangeIcon: false,  canChangeBackground: false, decoration: "flat") {
			state "up", label: "", action: "up", icon: "st.samsung.da.oven_ic_up", nextState: "moving"
            state "moving", label: "moving", action:"", backgroundColor: "#53a7c0", icon: "st.samsung.da.oven_ic_up"
		}
         standardTile("left", "device.image", width: 1, height: 1, canChangeIcon: false,  canChangeBackground: false, decoration: "flat") {
			state "left", label: "", action: "left", icon: "st.samsung.da.RAC_4line_01_ic_left", nextState: "moving"
            state "moving", label: "moving", action:"", backgroundColor: "#53a7c0", icon: "st.samsung.da.RAC_4line_01_ic_left"
		}
          standardTile("home", "device.image", width: 1, height: 1, canChangeIcon: false,  canChangeBackground: false) {
			state "home", label: "Home", action: "home", icon: "st.Home.home2", nextState: "moving"
            state "moving", label: "moving", action:"", backgroundColor: "#53a7c0", icon: "st.Home.home2"
		}
          standardTile("right", "device.image", width: 1, height: 1, canChangeIcon: false,  canChangeBackground: false, decoration: "flat") {
			state "right", label: "", action: "right", icon: "st.samsung.da.RAC_4line_03_ic_right", nextState: "moving"
            state "moving", label: "moving", action:"", backgroundColor: "#53a7c0", icon: "st.samsung.da.RAC_4line_03_ic_right"
		}
         standardTile("down", "device.image", width: 1, height: 1, canChangeIcon: false,  canChangeBackground: false, decoration: "flat") {
			state "down", label: "", action: "down", icon: "st.samsung.da.oven_ic_down", nextState: "moving"
            state "moving", label: "moving", action:"", backgroundColor: "#53a7c0", icon: "st.samsung.da.oven_ic_down"
		}
        valueTile("presetOne", "device.image", width: 1, height: 1, canChangeIcon: false,  canChangeBackground: false) {
			state "presetOne", label: "1", action: "presetOne", nextState: "moving"
            state "moving", label: "moving", action:"", backgroundColor: "#53a7c0"
		}
        valueTile("presetTwo", "device.image", width: 1, height: 1, canChangeIcon: false,  canChangeBackground: false) {
			state "presetTwo", label: "2", action: "presetTwo", nextState: "moving"
            state "moving", label: "moving", action:"", backgroundColor: "#53a7c0"
		}
        valueTile("presetThree", "device.image", width: 1, height: 1, canChangeIcon: false,  canChangeBackground: false) {
			state "presetThree", label: "3", action: "presetThree", nextState: "moving"
            state "moving", label: "moving", action:"", backgroundColor: "#53a7c0"
		}

        standardTile("refresh", "command.refresh", inactiveLabel: false) {
        	state "default", label:'refresh', action:"refresh.refresh", icon:"st.secondary.refresh-icon"        
    	}
        
        standardTile("motion", "device.switch", width: 2, height: 2, canChangeIcon: false) {
			state "off", label: 'Motion Off', action: "switch.on", icon: "st.motion.motion.inactive", backgroundColor: "#ccffcc", nextState: "on"
            state "on", label: 'Motion On', action: "switch.off", icon: "st.motion.motion.active", backgroundColor: "#EE0000", nextState: "off"            
		}
        controlTile("levelSliderControl", "device.level", "slider", height: 1, width: 5, inactiveLabel: false, range:"(0..100)") {
            state "level", label: "Motion Detection Sensitivity", action:"switch level.setLevel"
        }
        valueTile("Sensitivity", "device.level", inactiveLabel: false){
        	state "default", label:'${currentValue}%', unit:"%"
        }
         standardTile("nightVision", "device.switch3", width: 2, height: 2, canChangeIcon: false) {
			state "off", label: 'NV Off', action: "nvAuto", icon: "st.Weather.weather14", backgroundColor: "#ffff00", nextState: "toggle"
            state "toggle", label:'toggle', action: "", icon: "st.motion.motion.inactive", backgroundColor: "#53a7c0"
			state "on", label: 'NV On', action: "nvOff", icon: "st.Weather.weather4", backgroundColor: "#4169E1", nextState: "toggle"  
            state "auto", label: 'NV Auto', action: "nvOn", icon: "st.motion.motion.active", backgroundColor: "#ccffcc", nextState: "toggle"  
		}
        standardTile("videoStart", "device.image", inactiveLabel: false) {
        	state "start", action:"start", icon:"st.Entertainment.entertainment11"        
    	}
        valueTile("blank", "device.image"){
        	state "blank"
        }
                 
        main "motion"
        details(["videoPlayer", "presetOne", "presetTwo", "presetThree", "refresh", "up", "videoStart", "cameraDetails", "left", "home", "right", "take", "down", "blank",  "levelSliderControl", "Sensitivity", "nightVision", "motion"])
    }
}

def parse(String description) {
    log.debug "Parsing '${description}'"
    try{
    def map = [:]
	def retResult = []
	def descMap = parseDescriptionAsMap(description)
    def msg = parseLanMessage(description)
    
    
    log.debug msg
    log.debug "status ${msg.status}"
    log.debug "data ${msg.data}"
    
	//Image
	if (descMap["bucket"] && descMap["key"]) {
    log.debug "putImageInS3"
		putImageInS3(descMap)
	}      
    else if (descMap["headers"] && descMap["body"]){
    
    
    	def body = new String(descMap["body"].decodeBase64())
        log.debug "Body: ${body}"
        
    }
    
    if (msg.body) {
    
    //log.debug "Motion Enabled: ${msg.body.contains("enable=yes")}"
    //log.debug "Motion Disabled: ${msg.body.contains("enable=no")}"
    //log.debug "PIR Enabled: ${msg.body.contains("pir=yes")}"
    //log.debug "PIR Disabled: ${msg.body.contains("pir=no")}"
    
        if (msg.body.contains("MotionDetectionEnable=1")) {
            log.debug "Motion is on"
            sendEvent(name: "switch", value: "on");
        }
        else if (msg.body.contains("MotionDetectionEnable=0")) {
            log.debug "Motion is off"
            sendEvent(name: "switch", value: "off");
        }
        
        if(msg.body.contains("MotionDetectionSensitivity="))
        {
        	//log.debug msg.body        
        	String[] lines = msg.body.split( '\n' )
        	//log.debug lines[2]
            String[] sensitivity = lines[2].split( '=' )
            //log.debug sensitivity[1]
            int[] senseValue = sensitivity[1].toInteger()
            //log.debug senseValue
            
            sendEvent(name: "level",  value: "${senseValue[0]}")
            //sendEvent(name: "switch.setLevel", value: "${senseValue}")
        }      
         if (msg.body.contains( "DayNightMode=3")) {
            log.debug "Night Vision is on"
            sendEvent(name: "switch3", value: "on");
        }
        else if (msg.body.contains("DayNightMode=2")) {
            log.debug "Night Vision is off"
            sendEvent(name: "switch3", value: "off");
        }
        else if (msg.body.contains("DayNightMode=0")) {
            log.debug "Night Vision is auto"
            sendEvent(name: "switch3", value: "auto");
        }                   
    }        
}
catch (Exception e) { //needed to catch java.lang.ArrayIndexOutOfBoundsException when camera doesn't reply with anthing in the body
    	//log.debug "Hit Exception $e in Parse"
    }
    device.deviceNetworkId = "ID_WILL_BE_CHANGED_AT_RUNTIME_" + (Math.abs(new Random().nextInt()) % 99999 + 1)
    sendEvent(name: "image", value: "down");
    sendEvent(name: "image", value: "up");
    sendEvent(name: "image", value: "left");
    sendEvent(name: "image", value: "right");
    sendEvent(name: "image", value: "presetOne");
    sendEvent(name: "image", value: "presetTwo");
    sendEvent(name: "image", value: "presetThree");
    sendEvent(name: "image", value: "home");
}

// handle commands
def take() {
	def userpassascii = "${CameraUser}:${CameraPassword}"
	def userpass = "Basic " + userpassascii.encodeAsBase64().toString()
    def host = CameraIP 
    def hosthex = convertIPtoHex(host)
    def porthex = convertPortToHex(CameraPort)
    device.deviceNetworkId = "$hosthex:$porthex" 
    
    log.debug "The device id configured is: $device.deviceNetworkId"
    
    def path = "/image/jpeg.cgi"
    log.debug "path is: $path"
    
    def headers = [:] 
    headers.put("HOST", "$host:$CameraPort")
   	headers.put("Authorization", userpass)
    
    
    log.debug "The Header is $headers"
    
    def method = "GET"
       
    log.debug "The method is $method"
    
    try {
    def hubAction = new physicalgraph.device.HubAction(
    	method: method,
    	path: path,
    	headers: headers
        )
        	
    hubAction.options = [outputMsgToS3:true]
    log.debug hubAction
    return hubAction
    }
    catch (Exception e) {
    	log.debug "Hit Exception $e on $hubAction"
    }
}

def motionCmd(int motion)
{
	def userpassascii = "${CameraUser}:${CameraPassword}"
	def userpass = "Basic " + userpassascii.encodeAsBase64().toString()
    def host = CameraIP 
    def hosthex = convertIPtoHex(host)
    def porthex = convertPortToHex(CameraPort)
    device.deviceNetworkId = "$hosthex:$porthex" 
    
    log.debug "The device id configured is: $device.deviceNetworkId"
    
    def path = "/motion.cgi?MotionDetectionEnable=${motion}&ConfigReboot=no"
    log.debug "path is: $path"
    
    def headers = [:] 
    headers.put("HOST", "$host:$CameraPort")
    headers.put("Authorization", userpass)
    
    
    
    log.debug "The Header is $headers"
    
    
  try {
    def hubAction = new physicalgraph.device.HubAction(
    	method: "GET",
    	path: path,
    	headers: headers
        )
        	
   
    log.debug hubAction
    return hubAction
    
    }
    catch (Exception e) {
    	log.debug "Hit Exception $e on $hubAction"
    }
  
}

def sensitivityCmd(int percent)
{
	def userpassascii = "${CameraUser}:${CameraPassword}"
	def userpass = "Basic " + userpassascii.encodeAsBase64().toString()
    def host = CameraIP 
    def hosthex = convertIPtoHex(host)
    def porthex = convertPortToHex(CameraPort)
    device.deviceNetworkId = "$hosthex:$porthex" 
    
    log.debug "The device id configured is: $device.deviceNetworkId"
       
    
    log.debug "Sensitivity is ${percent}"
    
    def path = "/motion.cgi?MotionDetectionSensitivity=${percent}&ConfigReboot=No"
    log.debug "path is: $path"
        
    def headers = [:] 
    headers.put("HOST", "$host:$CameraPort")
    headers.put("Authorization", userpass)
    
    log.debug "The Header is $headers"
   
  try {
    def hubAction = new physicalgraph.device.HubAction(
    	method: "GET",
    	path: path,
    	headers: headers
        )
        	
   
    log.debug hubAction
    return hubAction
    
    }
    catch (Exception e) {
    	log.debug "Hit Exception $e on $hubAction"
    }
  
}
def nightCmd(String attr)
{
	def userpassascii = "${CameraUser}:${CameraPassword}"
	def userpass = "Basic " + userpassascii.encodeAsBase64().toString()
    def host = CameraIP 
    def hosthex = convertIPtoHex(host)
    def porthex = convertPortToHex(CameraPort)
    device.deviceNetworkId = "$hosthex:$porthex" 
    
    log.debug "The device id configured is: $device.deviceNetworkId"
    
    def headers = [:] 
    headers.put("HOST", "$host:$CameraPort")
    headers.put("Authorization", userpass)
    
    log.debug "The Header is $headers"
    
 def path = "/daynight.cgi?DayNightMode=${attr}&ConfigReboot=no"
 log.debug "path is: $path"
  try {
    def hubAction = new physicalgraph.device.HubAction(
    	method: "GET",
    	path: path,
    	headers: headers
        )
        	
   
    log.debug hubAction
    return hubAction
    
    }
    catch (Exception e) {
    	log.debug "Hit Exception $e on $hubAction"
    }
  
 
}

def putImageInS3(map) {
	log.debug "firing s3"
    def s3ObjectContent
    try {
        def imageBytes = getS3Object(map.bucket, map.key + ".jpg")
        
        if(imageBytes)
        {
            s3ObjectContent = imageBytes.getObjectContent()
            def bytes = new ByteArrayInputStream(s3ObjectContent.bytes)
            storeImage(getPictureName(), bytes)
        }
    }
    catch(Exception e) {
        log.error e
    }
	finally {
    //Explicitly close the stream
		if (s3ObjectContent) { s3ObjectContent.close() }
	}
}

def parseDescriptionAsMap(description) {
	description.split(",").inject([:]) { map, param ->
		def nameAndValue = param.split(":")
		map += [(nameAndValue[0].trim()):nameAndValue[1].trim()]
	}
}

private getPictureName() {
	def pictureUuid = java.util.UUID.randomUUID().toString().replaceAll('-', '')
    log.debug pictureUuid
    def picName = device.deviceNetworkId.replaceAll(':', '') + "_$pictureUuid" + ".jpg"
    return picName
}

private String convertIPtoHex(ipAddress) { 
    String hex = ipAddress.tokenize( '.' ).collect {  String.format( '%02x', it.toInteger() ) }.join()
    log.debug "IP address entered is $ipAddress and the converted hex code is $hex"
    return hex

}

private String convertPortToHex(port) {
	String hexport = port.toString().format( '%04x', port.toInteger() )
    log.debug hexport
    return hexport
}

private Integer convertHexToInt(hex) {
	Integer.parseInt(hex,16)
}


private String convertHexToIP(hex) {
	log.debug("Convert hex to ip: $hex") 
	[convertHexToInt(hex[0..1]),convertHexToInt(hex[2..3]),convertHexToInt(hex[4..5]),convertHexToInt(hex[6..7])].join(".")
}

private getHostAddress() {
    def parts = device.deviceNetworkId.split(":")
    log.debug device.deviceNetworkId
    def ip = convertHexToIP(parts[0])
    def port = convertHexToInt(parts[1])
    return ip + ":" + port
}


def on() {
	log.debug "Enabling motion detection"
    return motionCmd(1)    
}

def off() {
	log.debug "Disabling motion detection"
    return motionCmd(0)    
}
def nvOn() {
	log.debug "Enabling Night Vision"
    return nightCmd("3")   
    
}

def nvOff() {
	log.debug "Disabling Night Vision"
    return nightCmd("2")    
    
}

def nvAuto() {
	log.debug "Automatic Night Vision"
    return nightCmd("0")    
    
}

def refresh(){

	log.debug "Refresh"
	def userpassascii = "${CameraUser}:${CameraPassword}"
	def userpass = "Basic " + userpassascii.encodeAsBase64().toString()
    def host = CameraIP 
    def hosthex = convertIPtoHex(host)
    def porthex = convertPortToHex(CameraPort)
    device.deviceNetworkId = "$hosthex:$porthex" 
    
    log.debug "The device id configured is: $device.deviceNetworkId"
    def path = "/motion.cgi"
    log.debug "path is: $path"
    
    def headers = [:] 
    headers.put("HOST", "$host:$CameraPort")
    headers.put("Authorization", userpass)
    
    log.debug "The Header is $headers"
   
  try {
    def hubAction = new physicalgraph.device.HubAction(
    	method: "GET",
    	path: "/motion.cgi",
    	headers: headers
        )
        	
   
    log.debug hubAction
    return hubAction
    
    }
    catch (Exception e) {
    	log.debug "Hit Exception $e on $hubAction"
    }
  
  
}

def moveCmd(int move)
{
	def userpassascii = "${CameraUser}:${CameraPassword}"
	def userpass = "Basic " + userpassascii.encodeAsBase64().toString()
    def host = CameraIP 
    def hosthex = convertIPtoHex(host)
    def porthex = convertPortToHex(CameraPort)
    device.deviceNetworkId = "$hosthex:$porthex" 
    
    log.debug "The device id configured is: $device.deviceNetworkId"
    
    def path = "/pantiltcontrol.cgi?PanSingleMoveDegree=5&TiltSingleMoveDegree=5&PanTiltSingleMove=${move}"
    log.debug "path is: $path"
    
    def headers = [:] 
    headers.put("HOST", "$host:$CameraPort")
    headers.put("Authorization", userpass)
    
    
    
    log.debug "The Header is $headers"
    
    
  try {
    def hubAction = new physicalgraph.device.HubAction(
    	method: "GET",
    	path: path,
    	headers: headers
        )
        	
   
    log.debug hubAction
    return hubAction
    
    }
    catch (Exception e) {
    	log.debug "Hit Exception $e on $hubAction"
    }
  
}
def presetCmd(int preset)
{
	def userpassascii = "${CameraUser}:${CameraPassword}"
	def userpass = "Basic " + userpassascii.encodeAsBase64().toString()
    def host = CameraIP 
    def hosthex = convertIPtoHex(host)
    def porthex = convertPortToHex(CameraPort)
    device.deviceNetworkId = "$hosthex:$porthex" 
    
    log.debug "The device id configured is: $device.deviceNetworkId"
    
    def path = "/pantiltcontrol.cgi?PanTiltPresetPositionMove=${preset}"
    log.debug "path is: $path"
    
    def headers = [:] 
    headers.put("HOST", "$host:$CameraPort")
    headers.put("Authorization", userpass)
    
    
    
    log.debug "The Header is $headers"
    
    
  try {
    def hubAction = new physicalgraph.device.HubAction(
    	method: "GET",
    	path: path,
    	headers: headers
        )
        	
   
    log.debug hubAction
    return hubAction
    
    }
    catch (Exception e) {
    	log.debug "Hit Exception $e on $hubAction"
    }
  
}

def up()
{
	log.debug "Moving Up"
    moveCmd(1)
}

def left()
{
	log.debug "Moving Left"
    moveCmd(3)
}

def right()
{
	log.debug "Moving Right"
    moveCmd(5)
}

def down()
{
	log.debug "Moving Down"
    moveCmd(7)
}

def home()
{
	log.debug "Moving to Home position"
    moveCmd(4)
}

def presetOne()
{
	log.debug "Moving to Preset position"
    presetCmd(CameraPresetOne.toInteger())
}
def presetTwo()
{
	log.debug "Moving to Preset position"
    presetCmd(CameraPresetTwo.toInteger())
}
def presetThree()
{
	log.debug "Moving to Preset position"
    presetCmd(CameraPresetThree.toInteger())
}

def setLevel(percent) {
	log.debug "Executing 'setLevel'"
	return sensitivityCmd(percent)	   
}

def presetCommand(position) {
	presetCmd(position.toInteger())
}

def start() {
	log.trace "start()"
	def dataLiveVideo = [
		OutHomeURL  : "http://${CameraUser}:${CameraPassword}@${VideoIP}:${VideoPort}/mjpeg.cgi?channel=1.mjpeg",
		InHomeURL   : "http://${CameraUser}:${CameraPassword}@${VideoIP}:${VideoPort}/mjpeg.cgi?channel=1.mjpeg",
		ThumbnailURL: "http://cdn.device-icons.smartthings.com/camera/dlink-indoor@2x.png",
		cookie      : [key: "key", value: "value"]
	]

	def event = [
		name           : "stream",
		value          : groovy.json.JsonOutput.toJson(dataLiveVideo).toString(),
		data		   : groovy.json.JsonOutput.toJson(dataLiveVideo),
		descriptionText: "Starting the livestream",
		eventType      : "VIDEO",
		displayed      : false,
		isStateChange  : true
	]
	sendEvent(event)
}

def stop() {
	log.trace "stop()"
}

def vidOn() {
	log.trace "on()"
	// no-op
}

def vidOff() {
	log.trace "off()"
	// no-op
}

def installed(){
	configure()
}

def updated(){
	configure()
}

def configure(){
	sendEvent(name:"switch2", value:"on") 
}

def getInHomeURL() {
   [InHomeURL: cameraRTSP]
}
