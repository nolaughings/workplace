$(function(){
    var data={
        os:navigator.platform,
        //pageId:'',
        pageTitle:document.title,
        referrer:document.referer,
        url:window.location.href,
        timeZone:new Date().getTimezoneOffset()/60
    };
    $.ajax({
            type: 'POST',
            url: "/plugs/page/view",
            data: data,
            success: function(json){
                console.log(json);
            }
        }
    );
})
