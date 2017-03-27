/**
 * Created by marko on 27.03.17..
 */

upVote = function (id) {
    $.ajax({
        url: '/api/' + id + '/upVote',
        type: 'PUT',
        success: function(result) {
            console.log("SUCCESS");
        }
    });
};