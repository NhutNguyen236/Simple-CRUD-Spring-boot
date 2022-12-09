$(function (){
    $("a.confirmDeletion").click(function (){
        if(!confirm("Confirm deletion here?")) return false;
    });
});