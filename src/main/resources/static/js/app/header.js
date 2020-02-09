var main = {
    init : function() {
        var _this = this;
        $('#book-search').on('click', function() {
            var query = $('#book-query').val();
            location.href = '/book/list?query=' + query + '&start=1&display=10';
        });
    }
};

main.init();