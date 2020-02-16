var main = {
    init : function() {
        var _this = this;
        $('.info-collapse').on('show.bs.collapse', function() {
            _this.getInfo($(this));
        });
    },
    getInfo : function($collapse) {
        var isbn = $collapse.find('#isbn').val();
        var pubdate = $collapse.find('#pubdate').val();
        var $review = $collapse.find('#review');

        $.ajax({
            type : 'GET',
            url : '/api/v1/book/info?isbn=' + isbn + '&pubdate=' + pubdate,
            dataType : 'json'
        }).done(function(json, status) {
            console.log('response data : ' + JSON.stringify(json));
            console.log('status : ' + status);
            $review.text('리뷰 ' + json.postsCount + '개');
            if(json.postsCount > 0) {
                $review.append('<button type="button" class="btn btn-outline-primary btn-sm">리뷰 보기</button>');
            }
        }).fail(function(error) {
            alert(JSON.stringify(error));
        });
    }
}

main.init();