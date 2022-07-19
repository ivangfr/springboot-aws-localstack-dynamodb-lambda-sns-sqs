let stompClient = null

function connectToWebSocket() {
    const socket = new SockJS('/news-websocket')
    stompClient = Stomp.over(socket)

    stompClient.connect({},
        function (frame) {
            console.log('Connected: ' + frame)
            $('.connWebSocket').find('i').removeClass('red').addClass('green')

            stompClient.subscribe('/topic/news', function (newsEvent) {
                const newsEventBody = JSON.parse(newsEvent.body)
                const news = newsEventBody.news
                const action = newsEventBody.action

                const $news = $('#' + news.id)
                if (action === 'REMOVE' && $news.length !== 0) {
                    $news.transition({
                        animation: 'flash',
                        onComplete: function() {
                          $news.remove()
                        }
                      }
                    )
                } else if (action === 'INSERT' && $news.length === 0) {
                    const newsItem = '<div class="item" id="'+news.id+'">' +
                                       '<div class="content">' +
                                         '<div class="meta">' +
                                           '<span>'+moment(news.publishedAt).format("DD-MMM-YYYY HH:mm:ss")+'</span>' +
                                         '</div>' +
                                         '<div class="ui divider"></div>' +
                                         '<div class="ui big header">'+news.title+'</div>' +
                                       '</div>' +
                                     '</div>'
                    $('#newsList').prepend(newsItem)
                    $('#' + news.id).transition('glow')
                }
            })
        },
        function() {
            showModal($('.modal.alert'), 'WebSocket Disconnected', 'WebSocket is disconnected. Maybe, news-consumer is down or restarting')
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
