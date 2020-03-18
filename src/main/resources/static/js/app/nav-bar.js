var main = {
    init : function() {
        var _this = this;
        $('#nav-search').on('click', function() {
            return _this.checkQuery();
        });
    },
    checkQuery : function() {
        var blank_pattern = /^\s+|\s+$/g;
        if($('#nav-query').val().replace(/\s/g, "").length == 0) {
            alert('검색어를 입력해주세요.');
            return false;
        }
    }
}

main.init();