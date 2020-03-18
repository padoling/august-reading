var main = {
    init : function() {
        var _this = this;
        $('.btn-review').on('click', function() {
            var $card = $(this).closest('.card');
            _this.findBook($card);
        });
        $('.btn-write').on('click', function() {
            var $card = $(this).closest('.card');
            _this.bookSave($card);
        });
        $('#btn-search').on('click', function() {
            return _this.checkQuery();
        });
    },
    findBook : function($card) {
        var isbn = $card.find('.book-isbn').val();
        var pubdate = $card.find('.book-pubdate').text();

        $.ajax({
            type : 'GET',
            url : '/api/v1/book/info?isbn=' + isbn + '&pubdate=' + pubdate,
            dataType : 'text'
        }).done(function(bookId) {
            if(bookId != '') {
                window.location.href = '/book/' + bookId;
            } else {
                alert('등록된 리뷰가 없습니다.');
            }
        }).fail(function(error) {
            alert(JSON.stringify(error));
        });
    },
    bookSave : function($card) {
        var data = {
            title : $card.find('.book-title').text(),
            link : $card.find('.book-link').attr('href'),
            image : $card.find('.book-image').attr('src'),
            author : $card.find('.book-author').text(),
            publisher : $card.find('.book-publisher').text(),
            isbn : $card.find('.book-isbn').val(),
            description : $card.find('.book-description').text(),
            pubdate : $card.find('.book-pubdate').text()
        };

        console.log(JSON.stringify(data))

        $.ajax({
            type : 'POST',
            url : '/api/v1/book',
            dataType : 'json',
            contentType : 'application/json; charset=utf-8',
            data : JSON.stringify(data)
        }).done(function(bookId) {
            window.location.href = '/posts/write?bookId=' + bookId;
        }).fail(function(error) {
            alert(JSON.stringify(error));
        });
    },
    checkQuery : function() {
        var blank_pattern = /^\s+|\s+$/g;
        if($('#query').val().replace(/\s/g, "").length == 0) {
            alert('검색어를 입력해주세요.');
            return false;
        }
    }
}

main.init();