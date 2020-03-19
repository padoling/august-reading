var main = {
    init : function() {
        var _this = this;
        $('#check-nick').on('click', function() {
            _this.checkNick();
        });
        $('#nickname').change(function() {
            $('#nickname').attr("is-checked", "false");
            $('#checked-msg').empty();
        });
        $('#nickname').keydown(function() {
            _this.checkLength($(this));
        });
        $('#btn-submit').on('click', function() {
            _this.updateNickname();
        })
    },
    checkNick : function() {
        var _nickname = $('#nickname').val().replace(/\s/g, "");
        if(_nickname.length == 0) {
            alert('닉네임은 1자 이상 입력해야 합니다.');
        } else {
            var nickname = $('#nickname').val();

            $.ajax({
                type : 'GET',
                url : '/api/v1/user-nick?nickname=' + nickname,
                dataType : 'json'
            }).done(function(result) {
                if(result == true) {
                    $('#nickname').attr("is-checked", "false");
                    $('#checked-msg').empty();
                    $('#checked-msg').append('<p>이미 사용 중인 닉네임입니다.</p>');
                } else {
                    $('#nickname').attr("is-checked", "true");
                    $('#checked-msg').empty();
                    $('#checked-msg').append('<p>사용 가능한 닉네임입니다.</p>');
                }
            }).fail(function(error) {
                alert(JSON.stringify(error));
            });
        }
    },
    checkLength : function($input) {
        if($input.val().length > $input.attr('maxlength')) {
            alert('닉네임은 6자까지 입력 가능합니다.');
        }
    },
    updateNickname : function() {
        if($('#nickname').val().length == 0) {
            alert('닉네임은 1자 이상 입력해야 합니다.');
        } else if($('#nickname').attr("is-checked") == "false") {
            alert('닉네임 중복 확인을 해주세요.');
        } else {
            var data = {
                nickname : $('#nickname').val()
            };
            var id = $('#userId').val();

            $.ajax({
                type : 'PUT',
                url : '/api/v1/user/' + id,
                dataType : 'json',
                contentType : 'application/json; charset=utf-8',
                data : JSON.stringify(data)
            }).done(function(result) {
                alert('닉네임 등록 완료! id = ' + result);
                window.location.href = '/';
            }).fail(function(error) {
                alert(JSON.stringify(error));
            });
        }
    }
}

main.init();