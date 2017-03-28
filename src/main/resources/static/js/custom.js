/**
 * Created by marko on 27.03.17..
 */


addToIdArray = function (id) {
    $(document).ready(function () {
        window.location = '/post/addToIdArray/' + id;
    });
};

$('#upvoteLink').click(function (e) {
    e.preventDefault();
    console.log("YAYAY");
    return false;
});

