var main = {
    init : function() {
        var _this = this;
        $(document).ready(function() {
            _this.summernoteInit();
        });
        $('#btn-save').on('click', function() {
            _this.save();
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
    save : function() {
        var data = {
            subject : $('#subject').val(),
            content : $('#summernote').val(),
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