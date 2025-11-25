/*!
* Start Bootstrap - Simple Sidebar v6.0.6 (https://startbootstrap.com/template/simple-sidebar)
* Copyright 2013-2023 Start Bootstrap
* Licensed under MIT (https://github.com/StartBootstrap/startbootstrap-simple-sidebar/blob/master/LICENSE)
*/
// 
// Scripts
// 

window.addEventListener('DOMContentLoaded', event => {

    // Toggle the side navigation
    const sidebarToggle = document.body.querySelector('#sidebarToggle');
    if (sidebarToggle) {
        // Uncomment Below to persist sidebar toggle between refreshes
        // if (localStorage.getItem('sb|sidebar-toggle') === 'true') {
        //     document.body.classList.toggle('sb-sidenav-toggled');
        // }
        sidebarToggle.addEventListener('click', event => {
            event.preventDefault();
            document.body.classList.toggle('sb-sidenav-toggled');
            localStorage.setItem('sb|sidebar-toggle', document.body.classList.contains('sb-sidenav-toggled'));
        });
    }

});

// ajaxForm
function ajaxForm(a, b, c) {
    var d = !0
        , f = "application/x-www-form-urlencoded; charset=UTF-8";
    "object" === $.type(b) && b instanceof FormData && (f = d = !1);
    $.ajax({
        type: "post",
        url: a,
        cache: !1,
        data: b,
        processData: d,
        contentType: f,
        dataType: "json",
        success: function(e) {
            if ("N" == e.error)
                "undefined" !== $.type(e.redirect) && "" != $.trim(e.redirect) && ("reload" == $.trim(e.redirect) ? document.location.reload(!0) : document.location.href = $.trim(e.redirect));
            else {
                var g = 0;
                "undefined" !== $.type(e.inputArr) && $.each(e.inputArr, function(h, k) {
                    0 == g && $("#" + h).focus();
                    g++
                });
                "undefined" !== $.type(e.errorTitle) && "" != $.trim(e.errorTitle) && alert(e.errorMsg)
            }
            "function" === $.type(c) && c(e)
        },
        error: function(e, g, h) {

            console.log("status:", e.status);
            console.log("responseText:", e.responseText);
            console.log("responseJSON:", e.responseJSON);

            res = JSON.parse(e.responseText);

            if (res && res.errorMsg) {
                alert(res.errorMsg);   // 서버에서 내려준 메시지 출력
            } else {
                alert("[" + e.status + "] " + h);
            }
        }
    })
}