var main = {
    init : function() {
        var _this = this;
        $('.info-collapse').on('show.bs.collapse', function() {
            _this.getInfo($(this));
        });
        $('.review-button').on('click', function() {
            var $collapse = $(this).closest('.collapse');
            _this.bookSave($collapse);
        })
    },
    getInfo : function($collapse) {
        var $book = $('#book' + $collapse.find('.book-index').val());
        var isbn = $collapse.find('.book-isbn').val();
        var pubdate = $book.find('.book-pubdate').text();
        var $review = $collapse.find('#review');

        $.ajax({
            type : 'GET',
            url : '/api/v1/book/info?isbn=' + isbn + '&pubdate=' + pubdate,
            dataType : 'json'
        }).done(function(json) {
            $review.text('리뷰 ' + json.postsCount + '개');
            if(json.postsCount > 0) {
                $review.append('<button type="button" class="btn btn-outline-primary btn-sm">리뷰 보기</button>');
            }
        }).fail(function(error) {
            alert(JSON.stringify(error));
        });
    },
    bookSave : function($collapse) {
        var $book = $('#book' + $collapse.find('.book-index').val());

        var data = {
            title : $book.find('.book-title').text(),
            link : $collapse.find('.book-link').attr('href'),
            image : $book.find('.book-image').attr('src'),
            author : $book.find('.book-author').text(),
            publisher : $book.find('.book-publisher').text(),
            isbn : $collapse.find('.book-isbn').val(),
            description : $book.find('.book-description').text(),
            pubdate : $book.find('.book-pubdate').text()
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
    }
}

main.init();