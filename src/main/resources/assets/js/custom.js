/**
 * Created by marko on 27.03.17..
 */

$(document).ready(function () {
    // sdataTables configuration
    $(document).ready(function () {
        $('#table').DataTable({
            "aoColumnDefs": [{
                'bSortable': false,
                'aTargets': [2]
            }],
            "pagingType": "simple_numbers",
            "order": [[ 0, "desc" ]],
            "responsive": true
        });
    });

    // check if any checkbox is selected
    var selected = [];
    $('input:checked').each(function () {
        selected.push($(this).attr('name'));
    });

    // if no checkbox is selected, disable delete button
    if (selected.length == 0) {
        $("#postDeleteButton").attr("disabled", true);
    }

    // check again on any change on any checkbox
    $(":checkbox").change(function () {
        if (this.checked) {
            $("#postDeleteButton").attr("disabled", false);
        }
        var selected = [];
        $('input:checked').each(function () {
            selected.push($(this).attr('name'));
        });

        if (selected.length == 0) {
            $("#postDeleteButton").attr("disabled", true);
        }
    });

    upvoteAlertFunction = function() {
        $("#upvoteAlert").css("display", "block");

    }

    downvoteAlertFunction = function() {
        $("#downvoteAlert").css("display", "block");

    }
});