let stompClient = null

function connectToWebSocket() {
    const socket = new SockJS('/news-websocket')
    stompClient = Stomp.over(socket)

    stompClient.connect({},
        function (frame) {
            console.log('Connected: ' + frame)
            $('.connWebSocket').find('i').removeClass('red').addClass('green')

            stompClient.subscribe('/topic/news', function (news) {
                const newsBody = JSON.parse(news.body)
                const newsItem = '<div class="item">' +
                                   '<div class="content">' +
                                     '<div class="meta">' +
                                       '<span>'+moment(newsBody.publishedAt).format("DD-MMM-YYYY HH:mm:ss")+'</span>' +
                                     '</div>' +
                                     '<div class="ui divider"></div>' +
                                     '<div class="ui big header">'+newsBody.title+'</div>' +
                                   '</div>' +
                                 '</div>'

                $('#newsList').prepend(newsItem)
            })
        },
        function() {
            showModal($('.modal.alert'), 'WebSocket Disconnected', 'WebSocket is disconnected. Maybe, customer-service is down or restarting')
            $('.connWebSocket').find('i').removeClass('green').addClass('red')
        }
     )
}

function showModal($modal, header, description, fnApprove) {
    $modal.find('.header').text(header)
    $modal.find('.content').text(description)
    $modal.modal('show')
}

$(function () {
    connectToWebSocket()

    $('.connWebSocket').click(function() {
        connectToWebSocket()
    })
})
