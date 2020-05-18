function deleteUser(id) {
    var res = confirm('Are you sure ??');
    if (res) {
        $.ajax({
            url: 'http://localhost:8081/api/admin/' + id,
            method: 'DELETE',
            success: function () {
                getAllUsers();
            },
            error: function (error) {
                alert(error);
            }
        })
    }
}