var main = {
    init : function() {
        var _this = this;
        $(document).ready(function() {
            _this.summernoteInit();
        });
        $(window).bind('beforeunload', function() {
            return '변경사항이 저장되지 않을 수 있습니다.';
        });
        $('#btn-cancle').on('click', function() {
            _this.cancle();
        });
        $('#btn-save').on('click', function() {
            if(_this.verify()) {
                _this.save();
            }
        });
    },
    summernoteInit : function() {
        var _this = this;
        $('#summernote').summernote({
            height: 300,
            minHeight: null,
            maxHeight: null,
            focus: true,
            lang: 'ko-KR',
            callbacks: {
                onImageUpload: function(files) {
                    for(var i = files.length -1; i >= 0; i--) {
                        _this.sendFile(files[i], this);
                    }
                }
            }
        });
    },
    sendFile : function(file, el) {
        var form_data = new FormData();
        form_data.append('file', file);

        $.ajax({
            type : 'POST',
            url : '/api/v1/image',
            enctype : 'multipart/form-data',
            contentType : false,
            processData : false,
            data : form_data,
        }).done(function(url) {
            console.log("url : " + url);
            $(el).summernote('editor.insertImage', url);
            $('#imageBoard > ul').append('<li><img src="'+url+'" width=480 height="auto"/></li>');
        }).fail(function(error) {
            alert(JSON.stringify(error));
        });
    },
    verify : function() {
        var blank_pattern = /^\s+|\s+$/g;
        if($('#subject').val() == null || $('#subject').val().replace(blank_pattern, '') == '') {
            alert('제목을 입력해주세요.');
            return false;
        }
        if($('#summernote').val() == null || $('#summernote').val().replace(blank_pattern, '') == '') {
            alert('내용을 입력해주세요.');
            return false;
        }
        return true;
    },
    cancle : function() {
        var referrer = document.referrer;
        window.location.href = referrer;
    },
    save : function() {
        $(window).unbind('beforeunload');

        var data = {
            subject : $('#subject').val(),
            content : $('#summernote').val(),
            userId : $('#userId').val(),
            bookId : $('#bookId').val()
        };
        console.log(JSON.stringify(data));

        $.ajax({
            type : 'POST',
            url : '/api/v1/posts',
            dataType : 'json',
            contentType : 'application/json; charset=utf-8',
            data : JSON.stringify(data)
        }).done(function(id) {
            alert('리뷰 등록 완료!><');
            window.location.href = '/posts/' + id;
        }).fail(function(error) {
            alert(JSON.stringify(error));
        });
    }
}

main.init();