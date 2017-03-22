var stompClient = null;

function setConnected(connected) {
    $("#connect").prop("disabled", connected);
    $("#disconnect").prop("disabled", !connected);
    if (connected) {
        $("#conversation").show();
    }
    else {
        $("#conversation").hide();
    }
    $("#greetings").html("");
}

function connect() {
    var socket = new SockJS('/guest-notify');
    stompClient = Stomp.over(socket);
    stompClient.connect({}, function (frame) {
        setConnected(true);
        console.log('Connected: ' + frame);
        //订阅群发通道
        stompClient.subscribe('/topic/all/notify', function (greeting) {
            showGreeting(JSON.parse(greeting.body).content);
        });
        //订阅单发通道
        stompClient.subscribe("/user/queue/guest",function (greeting) {
            showGreeting(JSON.parse(greeting.body).content);
        });
        //订阅认证单发
        stompClient.subscribe('/user/queue/auth', function (greeting) {
            showGreeting(JSON.parse(greeting.body).content);
        });
    });
}

function disconnect() {
    if (stompClient != null) {
        stompClient.disconnect();
    }
    setConnected(false);
    console.log("Disconnected");
}

function sendName() {
    stompClient.send("/ws/all/notify", {}, JSON.stringify({'content': $("#name").val()}));
}

/**
 * 发送token，服务端存储非认证身份
 */
function sendToken() {
    stompClient.send("/ws/guest/register",{},JSON.stringify({'token':'123456'}));
}

function showGreeting(message) {
    $("#greetings").append("<tr><td>" + message + "</td></tr>");
    $("#name").val("");
}

$(function () {
    $("form").on('submit', function (e) {
        e.preventDefault();
    });
    $( "#connect" ).click(function() { connect(); });
    $( "#disconnect" ).click(function() { disconnect(); });
    $( "#send" ).click(function() { sendName(); });
    $( "#sendToken" ).click(function() { sendToken(); });
});