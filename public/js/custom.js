// "Add New Comments" button toggle for adding new comment
function addNewComments(){
    var lTable = document.getElementById("commentsTable");
    lTable.style.display = (lTable.style.display == "table") ? "none" : "table";
}

//To enable the click on the row functionality, in the Homepage
function rowClick(iUrl){
    document.location.href = iUrl;
}

//To enable the active nav section in the menu
$(function(){
    $('a').each(function(){
        if ($(this).prop('href') == window.location.href) {
            $(this).addClass('active'); $(this).parents('li').addClass('active');
        }
    });
});

