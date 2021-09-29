$(function () {
  var $loginForm = $('#J_loginForm');
  var $usernameInput = $('#J_username');
  var $pswInput = $('#J_password');
  var formSubmitting = false;

  function error($input) {
    var message = $input.data('message');
    $input.addClass('error').next('.error-tips').text(message).slideDown(120);
  }

  function resetInput(inputArr) {
    inputArr.forEach(function ($input) {
      $input.on('keydown', function () {
        $input.removeClass('error').next('.error-tips').slideUp(120);

        $('.login-btn').next('.error-tips').slideUp(120);
      });
    });
  }

  function getQueryVariable(variable) {
    var query = window.location.search.substring(1);
    var vars = query.split('&');
    for (var i = 0; i < vars.length; i++) {
      var pair = vars[i].split('=');
      if (pair[0] == variable) {
        return pair[1];
      }
    }
    return null;
  }

  resetInput([$usernameInput, $pswInput]);

  $loginForm.on('submit', function (e) {
    if (!formSubmitting) {
      e.preventDefault();
    }

    var userName = $usernameInput.val().trim();
    var password = $pswInput.val().trim();

    if (!userName) {
      return error($usernameInput);
    } else if (!password) {
      return error($pswInput);
    } else {
      formSubmitting = true;

      $.ajax({
        url: '/business/sys/login',
        method: 'POST',
        dataType: 'json',
        contentType: 'application/json;charset=utf-8',
        data: JSON.stringify({
          userName: userName,
          password: password,
        }),
        success: function (resp) {
          if (resp && resp.ok) {
            localStorage.setItem('vontown-current', JSON.stringify(resp.data));

            var redirect = '/admin';
            var redirectVal = getQueryVariable('redirect');

            window.location.href = redirectVal
              ? decodeURIComponent(redirectVal)
              : redirect;
          } else {
            $('.login-btn')
              .next('.error-tips')
              .css('text-align', 'center')
              .text(resp.msg)
              .slideDown(120);
          }
        },
        complete: function () {
          formSubmitting = false;
        },
        error: function () {
          window.alert('网络请求错误，请刷新页面重试。');
        },
      });
    }
  });
});
